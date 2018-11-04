<template>
  <div id='OutboundThrow' @click.stop="hideDeleteDOM">
    <div class="OutboundThrow-main">
      <ul class="main-details">
        <li v-for="(itemDetails, index) in applyEntity" :key="index">
          <p>{{itemDetails.title}}</p>
          <p v-if="itemDetails.initTimer">{{itemDetails.text | initDate}}</p>
          <p v-else>{{itemDetails.text}}</p>
        </li>
        <li class="details-textarea">
          <p>备注</p>
          <textarea placeholder="请输入备注信息!" v-model="detailsTextarea"></textarea>
        </li>
      </ul>
      <div class="main-listing">
        <p class="listing-title">出库清单</p>
        <div class="listing-inventory">
          <ul class="inventory-list">
            <li v-for="listItem in outStockApplyDetailEntities" :key="listItem.outStockApplyDetailId">
              <div class="list-img">
                <img v-if="listItem.imageUrl" :src="listItem.imageUrl">
                <img v-else src="../../../../assets/home/pic3.png">
              </div>
              <div class="list-content">
                <p>{{listItem.partName}}</p>
                <p>出库数量 :<span>{{listItem.outCount}}&nbsp;{{listItem.units}}</span></p>
                <p>备件状态 :<span>{{listItem.stockStatus=='normal'?'好件':'坏件'}}</span></p>
              </div>
            </li>
          </ul>
        </div>
        <div class="listing-addList" @click="addParts">
          <i class="addList-icon"></i>
          <p>物品添加</p>
        </div>
        <div style='clear: both;'></div>
        <div class="listing-outbound">
          <ul class="outbound-list">
            <li v-for="(itemParts, index) in outStockDetailEntities" :key="index" @click.stop="showMask($event)">
              <div class="list-mask">
                <div class="mask" @click.stop="CurrentHideDeleteDOM($event)">
                  <img src="../../../../assets/home/icon_del.png" :data-index="index" @click="deleteDOM($event)">
                </div>
              </div>
              <div class="list-parts">
                <div class="parts-img">
                  <img v-if="itemParts.imageUrl" :src="itemParts.imageUrl">
                  <img v-else src="../../../../assets/home/pic3.png">
                </div>
                <div class="parts-text">
                  <div class="text-titleQrcode">
                    <div class="titleQrcode-title">
                      <span></span>
                      <p>{{itemParts.partName}}</p>
                    </div>
                    <div v-if="itemParts.inventoryType == 'unique'" class="titleQrcode-qrCode" @click.stop="contrastQrCode($event)" :data-index="index" :data-sparePartId="itemParts.sparePartId" :data-stockId="itemParts.stockId" :data-qrCode='itemParts.qrCode'>
                      <button>二维码绑定</button>
                    </div>
                  </div>
                  <div class="text-information">
                    <div class="information-num"><span class="parts1">出库数量 :</span><input @click.stop="tempNum1 = '1'" type="text" :placeholder="'备件: ' + itemParts.account" :data-stockId="itemParts.stockId" :data-sparePartId='itemParts.sparePartId' :data-stockStatus="itemParts.status"
                        class="parts2 numTag" v-model="itemParts.outCount"></div>
                    <div class="information-unique"><span class="parts1">唯一标识 :</span>{{itemParts.inventoryType == 'unique'?'是':'否'}}</div>
                  </div>
                  <div class="text-location">
                    <span class="parts1">出库位置 :</span>
                    <p>房间号 :<span class="parts2">{{itemParts.houseNo || '暂无!'}}</span></p>
                    <p>货柜 :<span class="parts2">{{itemParts.shelfNumber || '暂无!'}}</span></p>
                  </div>
                  <div class="text-status">
                    <p class="parts1">备件状态 :<span class="parts2">{{itemParts.status=='normal'?'好件':'坏件'}}</span></p>
                  </div>
                  <div class="text-qrcode" v-if="itemParts.inventoryType == 'unique'">
                    <p class="parts1">二维码 :<span class="parts2">{{itemParts.qrCode}}</span></p>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="goOutbound">出库</button>
    </div>
    <!-- 错误提示 -->
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" @eventCurrentCom='getEventCurrentCom' :errorCancelBtn='EquipmentRepairCancelBtn' :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle">
    </GlobalErrorInformation>
    <router-view class="routerView"></router-view>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  export default {
    name: "OutboundThrow",
    data() {
      return {
        swtichLoading: true,
        tempNum1: "1",
        applyEntity: [],
        applyEntityPromp: null,
        outStockApplyDetailEntities: [],
        outStockDetailEntities: [],
        childParamObj: {},
        listDataArr: [],
        warehouseId: "",
        switchGlobalErrorInformation: false,
        EquipmentRepairCancelBtn: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        currentQrCodeDOM: null,
        currentQrCode: null,
        currentStockId: null,
        replaceBoolean: false,
        detailsTextarea: "",
        replaceQrCode: false,
        indexQrCode: null
      };
    },
    methods: {
      /* 方法 */
      initData() {
        const PROMPARR = {
          outStockApplyId: this.$route.query.outStockApplyId,
          operationSubjectId: this.$route.query.operationSubjectId
        };
        this.api.outboundDetailsDataAllprompObj(PROMPARR).then(data => {
          const DATA = data.data,
            APPLYENTITY = DATA.applyEntity, // 出库详情信息
            OUTSTOCHAPPLYDATEILENTITIES = DATA.outStockApplyDetailEntities, // 出库库存详情
            OUTSTOCHDETAILENTITIES = DATA.outStockDetailEntities; // 出库详情
          if (APPLYENTITY.outApplyStatus == 'alreadyOut') {
            this.$router.replace({
              path: "/OutboundDetails/" + PROMPARR.outStockApplyId + ',' + PROMPARR.operationSubjectId,
              query: {
                'taskNotice': 'true'
              }
            });
            return;
          }
          console.log(DATA);
          this.applyEntityPromp = DATA.applyEntity;
          this.applyEntity.push({
            title: '关联单据号',
            text: APPLYENTITY.applyNo || '暂无数据!'
          }, {
            title: '申请人',
            text: APPLYENTITY.applyUser || '暂无数据!'
          }, {
            title: '审核人',
            text: APPLYENTITY.auditUser || '暂无数据!'
          }, {
            title: '申请单备注',
            text: APPLYENTITY.applyRemark || '暂无数据!'
          }, {
            title: '出库单号',
            text: APPLYENTITY.outStockApplyNO || '暂无数据!'
          }, {
            title: '出库仓库',
            text: APPLYENTITY.warehouseName || '暂无数据!'
          }, {
            title: '出库性质',
            text: APPLYENTITY.outOrderType || '暂无数据!'
          }, {
            title: '状态',
            text: APPLYENTITY.outApplyStatus == 'toBeOut' ? '待出库' : '已出库'
          }, {
            title: '出库人',
            text: APPLYENTITY.outUser || '暂无数据!'
          }, {
            title: '操作时间',
            text: APPLYENTITY.modifyTime || '暂无数据!',
            initTimer: true
          }, {
            title: '出库备注',
            text: DATA.remark || '暂无备注!'
          })
          // this.childParamObj.status = "all";
          switch (this.applyEntity[6].text) {
            case 'useOut':
              this.applyEntity[6].text = '领用出库';
              this.childParamObj.status = "normal";
              break;
            case 'transferOut':
              this.applyEntity[6].text = '调拨出库';
              this.childParamObj.status = "normal";
              break;
            case 'borrowOut':
              this.applyEntity[6].text = '借用出库';
              this.childParamObj.status = "all";
              break;
            case 'returnOut':
              this.applyEntity[6].text = '返还出库';
              this.childParamObj.status = "bad";
              break;
          }
          this.outStockApplyDetailEntities = OUTSTOCHAPPLYDATEILENTITIES;
          this.outStockDetailEntities = OUTSTOCHDETAILENTITIES;
          // 为下个页面传值
          this.childParamObj.tempOutStockApplyId = PROMPARR.outStockApplyId;
          this.childParamObj.tempOperationSubjectId = PROMPARR.operationSubjectId;
          this.childParamObj.warehouseId = APPLYENTITY.outWarehouseId;
          this.warehouseId = APPLYENTITY.outWarehouseId;
          this.childParamObj.outStockApplyDetailId = APPLYENTITY.outStockApplyId;
          this.childParamObj.operationSubjectId =
            APPLYENTITY.loginOperationSubjectId;
          let sparePartIdArr = [];
          for (let i = 0, II = OUTSTOCHAPPLYDATEILENTITIES.length; i < II; i++) {
            sparePartIdArr.push(OUTSTOCHAPPLYDATEILENTITIES[i].sparePartId + ",");
          }
          let sparePartIdCountArr = [];
          for (let i = 0, II = OUTSTOCHAPPLYDATEILENTITIES.length; i < II; i++) {
            sparePartIdCountArr.push(
              OUTSTOCHAPPLYDATEILENTITIES[i].outCount + ","
            );
          }
          this.childParamObj.sparePartId = sparePartIdArr.join("");
          this.childParamObj.sparePartIdCount = sparePartIdCountArr.join("");
          this.childParamObj.sparePartId = this.childParamObj.sparePartId.slice(
            0,
            this.childParamObj.sparePartId.length - 1
          );
          this.childParamObj.sparePartIdCount = this.childParamObj.sparePartIdCount.slice(
            0,
            this.childParamObj.sparePartIdCount.length - 1
          );
          // 合并重复的数据用于比较数量
          let setSparePartIdArr = this.childParamObj.sparePartId.split(","),
            setSparePartIdCountArr = this.childParamObj.sparePartIdCount.split(
              ","
            ),
            setSparePartIdArr1 = [...new Set(setSparePartIdArr)],
            setStorage = [];
          for (let i = 0, II = setSparePartIdArr1.length; i < II; i++) {
            var temp = 0;
            for (let v = 0, VV = setSparePartIdArr.length; v < VV; v++) {
              if (setSparePartIdArr1[i] == setSparePartIdArr[v]) {
                temp += +setSparePartIdCountArr[v];
              }
            }
            setStorage.push(temp);
          }
          this.childParamObj.sparePartId = setSparePartIdArr1.join(",");
          this.childParamObj.sparePartIdCount = setStorage.join(",");
          // 比对数据
          this.api.addPartsDataAllprompObj(this.childParamObj).then(data => {
            this.listDataArr = data.data;
          });
          this.$nextTick(function() {
            this.swtichLoading = false;
          });
        });
      },
      addParts() {
        let params = [];
        for (let i = 0, II = this.outStockDetailEntities.length; i < II; i++) {
          params.push(this.outStockDetailEntities[i].stockId);
        }
        this.$router.replace({
          name: "outboundAddParts",
          query: {
            params: this.childParamObj,
            checkedArr: params,
            warehouseId: this.warehouseId
          }
        });
      },
      getEventCurrentCom(val) {
        if (this.replaceBoolean) {
          this.EquipmentRepairCancelBtn = false;
          this.replaceBoolean = false;
          return;
        }
        if (val) {
          const THISOBJ = {
            stockId: this.currentStockId,
            qrCode: this.currentQrCode
          };
          this.api.outboundQrCodeDataAllprompObj(THISOBJ).then(data => {
            // $(this.currentQrCodeDOM[0]).text(this.currentQrCode);
            this.outStockDetailEntities[this.indexQrCode].qrCode = this.currentQrCode;
            this.$set(this.outStockDetailEntities, this.indexQrCode, this.outStockDetailEntities[this.indexQrCode])
            this.EquipmentRepairCancelBtn = false;
          });
        }
      },
      contrastQrCode(event) {
        const THIS = $(event.currentTarget),
          CURRENTQRCODE = THIS.parent(".text-titleQrcode")
          .parent(".parts-text")
          .find(".text-qrcode .parts2"),
          THISOBJ = {
            stockId: THIS.attr("data-stockId"),
            sparePartId: THIS.attr("data-sparePartId"),
            qrCode: THIS.attr("data-qrCode") ? THIS.attr("data-qrCode") : ""
          };
        this.indexQrCode = THIS.attr('data-index');
        this.wxApi.WxScanQRCode().then(data => {
          this.api.validationQrCodeDataAllprompObj(data).then(data => {
            if (data.statusCode == "201") {
              this.EquipmentRepairErrorTitle = "警告";
              this.EquipmentRepairError = data.message;
              this.EquipmentRepairCancelBtn = true;
              this.switchGlobalErrorInformation = true;
              this.currentQrCode = '';
              // CURRENTQRCODE.text('');
              this.outStockDetailEntities[this.indexQrCode].qrCode = '';
            } else {
              if (THISOBJ.qrCode != data.data && THISOBJ.qrCode != "") {
                this.EquipmentRepairErrorTitle = "警告";
                this.EquipmentRepairError = "和原有的二维码不一致,是否替换!";
                this.EquipmentRepairCancelBtn = true;
                this.switchGlobalErrorInformation = true;
                this.currentStockId = THISOBJ.stockId;
                this.currentQrCode = data.data;
                // this.currentQrCodeDOM = CURRENTQRCODE;
              } else {
                THISOBJ.qrCode = data.data;
                this.api.outboundQrCodeDataAllprompObj(THISOBJ).then(data => {
                  // CURRENTQRCODE.text(THISOBJ.qrCode);
                  this.outStockDetailEntities[this.indexQrCode].qrCode = THISOBJ.qrCode;
                  this.$set(this.outStockDetailEntities, this.indexQrCode, this.outStockDetailEntities[this.indexQrCode])
                  this.EquipmentRepairCancelBtn = false;
                });
              }
            }
          });
        });
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      CurrentHideDeleteDOM(event) {
        $(event.currentTarget)
          .parent()
          .hide();
      },
      hideDeleteDOM() {
        $(".list-mask").hide();
      },
      showMask(event) {
        $(event.currentTarget)
          .find(".list-mask")
          .show();
      },
      deleteDOM(event) {
        console.log(this.outStockDetailEntities);
        const THIS = $(event.currentTarget),
          THISINDEX = THIS.attr("data-index");
        this.outStockDetailEntities.splice(THISINDEX, 1);
        THIS.contents(".list-mask").hide();
      },
      goOutbound() {
        const INP = $(".numTag"),
          SPAREPARTIDARR = this.childParamObj.sparePartId.split(","),
          SPAREPARTIDCOUNTARR = this.childParamObj.sparePartIdCount.split(","),
          TEMPIDARR = [],
          TEMPNUMARR = [],
          TEMPSTATUSARR = [],
          PAROMPOBJ = {
            applyEntity: this.applyEntityPromp,
            outStockApplyDetailEntities: this.outStockApplyDetailEntities,
            operationSubjectId: this.$route.query.operationSubjectId
          },
          QRCODEDOM = $(".text-qrcode").find(".parts2");
        this.applyEntityPromp.remark = this.detailsTextarea;
        for (let i = 0, II = INP.length; i < II; i++) {
          TEMPIDARR.push(INP.eq(i).attr("data-sparepartid"));
          TEMPNUMARR.push(INP.eq(i).val());
          TEMPSTATUSARR.push(INP.eq(i).attr("data-stockStatus"))
        }
        // let tempIdArr = [...new Set(TEMPIDARR)],
        //     tempstaus = [...new Set(TEMPSTATUSARR)],
        //     setStorage = [];
        // for (let i = 0, II = tempIdArr.length; i < II; i++) {
        //     var temp = 0;
        //     for (let v = 0, VV = TEMPIDARR.length; v < VV; v++) {
        //         if (tempIdArr[i] == TEMPIDARR[v]) {
        //             temp += +TEMPNUMARR[v];
        //         }
        //     }
        //     setStorage.push(temp);
        // }
        // console.log(tempIdArr)
        // console.log(setStorage)
        // console.log(tempstaus)
        for (let i = 0, II = this.outStockApplyDetailEntities.length; i < II; i++) {
          var outCount = this.outStockApplyDetailEntities[i].outCount;
          for (let v = 0, VV = TEMPIDARR.length; v < VV; v++) {
            if (this.outStockApplyDetailEntities[i].sparePartId == TEMPIDARR[v] && this.outStockApplyDetailEntities[i].stockStatus == TEMPSTATUSARR[v]) {
              if ((outCount -= TEMPNUMARR[v]) < 0) {
                this.EquipmentRepairErrorTitle = "数量错误";
                this.EquipmentRepairError = "超出申请数量!";
                this.switchGlobalErrorInformation = true;
                this.replaceBoolean = true;
                return;
              }
            }
          }
        }
        let contrastArr = [];
        for (let i = 0, II = this.outStockDetailEntities.length; i < II; i++) {
          for (let v = 0, VV = this.outStockApplyDetailEntities.length; v < VV; v++) {
            if (this.outStockDetailEntities[i].sparePartId == this.outStockApplyDetailEntities[v].sparePartId && this.outStockDetailEntities[i].status == this.outStockApplyDetailEntities[v].stockStatus) {
              contrastArr.push(this.outStockApplyDetailEntities[v])
            }
          }
        }
        console.log(contrastArr)
        if (contrastArr.length != this.outStockDetailEntities.length) {
          this.EquipmentRepairErrorTitle = "出库错误";
          this.EquipmentRepairError = "备件状态不同!";
          this.switchGlobalErrorInformation = true;
          this.replaceBoolean = true;
          return;
        }
        // 加出库数量
        for (let a = 0, AA = this.outStockDetailEntities.length; a < AA; a++) {
          // this.outStockDetailEntities[a].outCount = TEMPNUMARR[a];
          this.outStockDetailEntities[a].qrCode = QRCODEDOM.eq(a).text();
          for (let b = 0, BB = this.outStockApplyDetailEntities.length; b < BB; b++) {
            if (this.outStockDetailEntities[a].sparePartId == this.outStockApplyDetailEntities[b].sparePartId) {
              this.outStockDetailEntities[a].outStockApplyDetailId = this.outStockApplyDetailEntities[b].outStockApplyDetailId;
              this.outStockApplyDetailEntities[b].alreadyOutCount += (+this.outStockDetailEntities[a].outCount);
            }
          }
        }
        if (this.outStockDetailEntities.length == 0) {
          this.EquipmentRepairErrorTitle = "出库错误";
          this.EquipmentRepairError = "请选择出库的物品!";
          this.switchGlobalErrorInformation = true;
          this.replaceBoolean = true;
          return;
        }
        PAROMPOBJ.outStockDetailEntities = this.outStockDetailEntities;
        console.log(PAROMPOBJ.outStockDetailEntities)
        this.swtichLoading = true;
        this.api.goOutboundDataAllprompObj(PAROMPOBJ).then(data => {
          console.log(data);
          if (data.result == "success") {
            this.$router.push({
              path: "/Outbound"
            });
          }
          this.$nextTick(function() {
            this.swtichLoading = false;
          });
        });
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData();
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        if (to.name == "outboundThrow" && from.name == "outboundAddParts") {
          // 比对数据
          this.api.addPartsDataAllprompObj(this.childParamObj).then(data => {
            this.listDataArr = data.data;
            let paramsArr = this.$route.query.params;
            if (!paramsArr) return;
            if (this.listDataArr.length != 0) {
              // $('.listing-outbound .text-qrcode').find('.parts2').text('');
              this.outStockDetailEntities = [];
              for (let i = 0, II = this.listDataArr.length; i < II; i++) {
                for (let k = 0, KK = paramsArr.length; k < KK; k++) {
                  if (this.listDataArr[i].stockId == paramsArr[k]) {
                    this.listDataArr[i].outCount = this.listDataArr[i].outCount || 1;
                    this.outStockDetailEntities.push(this.listDataArr[i]);
                  }
                }
              }
              console.log(this.outStockDetailEntities);
            }
          });
        }
      }
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #OutboundThrow {
    width: 100%;
    height: 100%;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .OutboundThrow-main {
      width: 100%;
      padding: 20px 20px 100px;
      background-color: #faf6ec;
      box-sizing: border-box;
      .main-details {
        margin-bottom: 20px;
        padding: 20px;
        width: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 18px;
        &>li {
          padding: 0 20px;
          width: 100%;
          display: flex;
          justify-content: space-between;
          border-bottom: solid 1px #dfdcdc;
          box-sizing: border-box;
          &:nth-last-child(1) {
            border: 0;
          }
          p {
            font-size: $widthWeb22;
            color: #bfbfbf;
            line-height: 64px;
            text-align: right;
          }
          & p:nth-last-child(1) {
            width: 66%;
            color: #333;
          }
        }
        .details-textarea {
          display: block;
          padding: 0;
          p {
            text-align: left;
            padding-left: 20px;
          }
          textarea {
            width: 630px;
            height: 113px;
            padding: 20px;
            font-size: $widthWeb18;
            border: solid 1px #dfdcdc;
            resize: none;
            box-sizing: border-box;
          }
        }
      }
      .main-listing {
        padding-top: 26px;
        width: 100%;
        height: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        border-radius: 18px;
        box-sizing: border-box;
        overflow: hidden;
        overflow-y: auto;
        .listing-title {
          width: 100%;
          padding-left: 36px;
          font-size: $widthWeb22;
          color: #fd8521;
          line-height: $widthWeb22;
          box-sizing: border-box;
        }
        .listing-inventory {
          width: 100%;
          padding: 30px 25px 0 36px;
          box-sizing: border-box;
          .inventory-list {
            width: 100%;
            max-height: 500px;
            overflow: hidden;
            overflow-y: auto;
            li {
              width: 100%;
              display: flex;
              align-items: center;
              margin-bottom: 30px;
              .list-img {
                margin-right: 22px;
                width: 70px;
                height: 70px;
                img {
                  display: inline-block;
                  width: 100%;
                  height: 100%;
                }
              }
              .list-content {
                flex: 1;
                padding-bottom: 8px;
                border-bottom: 1px solid #dfdcdc;
                p {
                  font-size: $widthWeb18;
                  color: #333;
                  line-height: 40px;
                  &:nth-child(n+2) {
                    color: #999;
                    span {
                      margin-left: 28px;
                      color: #fd8521;
                    }
                  }
                }
              }
            }
          }
        }
        .listing-addList {
          float: right;
          margin-right: 20px;
          display: inline-flex;
          align-items: center;
          background-color: #eee;
          box-shadow: 0 3px 3px 0 rgba(0, 0, 0, 0.3);
          border-radius: 24px;
          .addList-icon {
            display: inline-block;
            width: 25px;
            height: 25px;
            margin-left: 18px;
            background-image: url("../../../../assets/home/add.png");
            background-size: 100% 100%;
          }
          p {
            display: inline-block;
            padding: 12px 24px 12px 16px;
            font-size: $widthWeb18;
          }
        }
        .listing-outbound {
          width: 100%;
          margin-top: 32px;
          padding: 20px;
          box-sizing: border-box;
          .outbound-list {
            width: 100%;
            &>li {
              position: relative;
              padding-left: 18px;
              padding-bottom: 20px;
              margin-bottom: 22px;
              border-bottom: solid 1px #dfdcdc;
              box-sizing: border-box;
              .list-mask {
                display: none;
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                .mask {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  width: 100%;
                  height: 100%;
                  background-color: rgba(0, 0, 0, 0.5);
                  img {
                    width: 100px;
                    height: 100px;
                  }
                }
              }
              &:nth-last-child(1) {
                border: 0;
              }
              p {
                font-size: $widthWeb18;
                color: #333;
                line-height: 58px;
                span {
                  margin-left: 24px;
                  color: #fd8521;
                }
              }
              .list-parts {
                width: 100%;
                display: flex;
                align-items: center;
                .parts-img {
                  margin-right: 24px;
                  width: 70px;
                  height: 70px;
                  img {
                    display: inline-block;
                    width: 100%;
                    height: 100%;
                  }
                }
                .parts-text {
                  width: calc(100% - 94px);
                  flex-shrink: 0;
                  .text-titleQrcode {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    height: $widthWeb18 + 8px;
                    .titleQrcode-title {
                      display: flex;
                      align-items: center;
                      span {
                        margin-right: 8px;
                        width: 10px;
                        height: 10px;
                        background-color: #fd8521;
                      }
                      p {
                        font-size: $widthWeb18;
                        line-height: $widthWeb18 + 8px;
                      }
                    }
                    .titleQrcode-qrCode {
                      margin-right: 30px;
                      button {
                        padding: 2px 8px;
                        font-size: 20px;
                        background-color: #fbe9da;
                        border: solid 1px #f9caa6;
                        border-radius: 4px;
                      }
                    }
                  }
                  .text-information {
                    display: flex;
                    align-items: center;
                    height: $widthWeb18 + 18px;
                    .information-num {
                      height: 100%;
                      display: flex;
                      align-items: center;
                      input {
                        width: 142px;
                        border-bottom: 1px solid #dfdcdc;
                        text-align: center;
                      }
                    }
                    .information-unique {
                      margin-left: 74px;
                      font-size: $widthWeb18;
                    }
                  }
                  .text-status,
                  .text-qrcode {
                    display: flex;
                    height: $widthWeb18 + 18px;
                    width: 100%;
                    align-items: center;
                  }
                  .text-location {
                    display: flex;
                    align-items: center;
                    height: $widthWeb18 + 18px;
                    width: 100%;
                    white-space: nowrap;
                    p {
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                    }
                    .parts2 {
                      margin: 0 18px;
                    }
                  }
                  .parts1 {
                    font-size: $widthWeb18;
                    color: #bfbfbf;
                    margin-right: 18px;
                  }
                  .parts2 {
                    font-size: $widthWeb18;
                    color: #fd8521;
                  }
                }
              }
            }
          }
        }
      }
    }
    .routerView {
      position: fixed;
      top: 0;
      left: 0;
      z-index: 500;
      width: 100%;
      height: 100%;
      background-color: #faf6ec;
    }
  }
</style>
