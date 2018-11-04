<template>
  <div id='BorrowingOutDetails' @click.stop="hideDeleteDOM">
    <div class="BorrowingOutDetails-main">
      <ul class="main-details">
        <li>
          <p>出库性质</p>
          <p>借用出库</p>
        </li>
        <li @click="getWarehouses">
          <label for="inp-storage" class="inp-title">出库仓库</label>
          <input type="text" name="site" id="inp-storage" class="inp-txt" placeholder="请选择出库仓库" readonly="readonly" v-model="inWarehouseName" />
          <div class="inp-icon"><i></i></div>
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
            <li v-for="(itemParts, index) in listDataArr" :key="index" @click.stop="showMask($event)">
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
                    <div class="information-num"><span class="parts1">出库数量 :</span><input @click.stop="tempNum1 = '1'" type="text" :placeholder="'备件: ' + itemParts.account"  :data-sparePartId='itemParts.sparePartId' :data-stockId="itemParts.stockId" :data-stockStatus="itemParts.status" class="parts2 numTag" v-model="itemParts.outCount"></div>
                    <div class="information-unique"><span class="parts1">唯一标识 :</span>{{itemParts.inventoryType == 'unique'?'是':'否'}}</div>
                  </div>
                  <div class="text-location">
                    <span class="parts1">出库位置 :</span>
                    <p>房间号 :<span class="parts2">{{itemParts.houseNo || '暂无!'}}</span></p>
                    <p>货柜 :<span class="parts2">{{itemParts.shelfNumber || '暂无!'}}</span></p>
                  </div>
                  <div class="text-status">
                    <p class="parts1">备件状态 :<span class="parts2">{{itemParts.stockStatus=='normal'?'好件':'坏件'}}</span></p>
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
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
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
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut'
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  export default {
    name: "BorrowingOutDetails",
    data() {
      return {
        switchHomeOrdinarySideOut: false, //右侧弹出层显示隐藏
        HomeOrdinarySideOutTitle: '入库仓库', //右侧弹出层标题
        HomeOrdinarySideOutData: [], //右侧弹出层数据
        inWarehouseName: '',
        inWarehouseId: '',
        swtichLoading: false,
        tempNum1: "1",
        applyEntity: [],
        applyEntityPromp: null,
        outStockApplyDetailEntities: [],
        childParamObj: {},
        listDataArr: [],
        listTotalDataArr: [],
        warehouseId: "",
        switchGlobalErrorInformation: false,
        EquipmentRepairCancelBtn: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        currentQrCodeDOM: null,
        currentQrCode: null,
        currentStockId: null,
        detailsTextarea: "",
        replaceQrCode: false,
        indexQrCode: null,
        paramsObj: {
          userKey: localStorage.getItem('userKey'),
          warehouseId: '',
          status: "all"
        }
      };
    },
    methods: {
      /* 方法 */
      getWarehouses() {
        let $this = this;
        this.requests.getWarehousesByUser().then(function(data) {
          if (data.result == "success") {
            const DATA = data.data;
            $this.HomeOrdinarySideOutData = [];
            for (let i = 0, II = DATA.length; i < II; i++) {
              $this.HomeOrdinarySideOutData.push({
                sideOutId: DATA[i].id,
                sideOutText: DATA[i].text
              });
            }
            $this.switchHomeOrdinarySideOut = true;
          }
        });
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        this.inWarehouseName = val.text;
        this.inWarehouseId = val.sideOutId;
      },
      addParts() {
        if (this.inWarehouseName == '') {
          this.EquipmentRepairErrorTitle = "添加错误";
          this.EquipmentRepairError = '请选择出库仓库 !';
          this.switchGlobalErrorInformation = true;
          return;
        }
        let stockIdArr = [];
        for (let i = 0, II = this.listDataArr.length; i < II; i++) {
          stockIdArr.push(this.listDataArr[i].stockId);
        }
        this.$router.replace({
          name: "BorrowOutboundSelectList",
          query: {
            warehouseId: this.inWarehouseId,
            checkedStockId: stockIdArr
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
            this.listDataArr[this.indexQrCode].qrCode = this.currentQrCode;
            this.$set(this.listDataArr, this.indexQrCode, this.listDataArr[this.indexQrCode])
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
              this.listDataArr[this.indexQrCode].qrCode = '';
            } else {
              if (THISOBJ.qrCode != data.data && THISOBJ.qrCode != "") {
                this.EquipmentRepairErrorTitle = "警告";
                this.EquipmentRepairError = "和原有的二维码不一致,是否替换!";
                this.EquipmentRepairCancelBtn = true;
                this.switchGlobalErrorInformation = true;
                this.currentStockId = THISOBJ.stockId;
                this.currentQrCode = data.data;
              } else {
                THISOBJ.qrCode = data.data;
                this.api.outboundQrCodeDataAllprompObj(THISOBJ).then(data => {
                  // CURRENTQRCODE.text(THISOBJ.qrCode);
                  this.listDataArr[this.indexQrCode].qrCode = THISOBJ.qrCode;
                  this.$set(this.listDataArr, this.indexQrCode, this.listDataArr[this.indexQrCode])
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
        console.log(this.listDataArr);
        const THIS = $(event.currentTarget),
          THISINDEX = THIS.attr("data-index");
        this.listDataArr.splice(THISINDEX, 1);
        THIS.contents(".list-mask").hide();
      },
      goOutbound() { // 出库
        if (this.listDataArr.length == 0) {
          this.EquipmentRepairErrorTitle = "出库错误";
          this.EquipmentRepairError = '请至少添加一个备件 !';
          this.switchGlobalErrorInformation = true;
          return;
        }
        this.swtichLoading = true;
        const INP = $('.numTag'),
              TEMPIDARR = [],
              TEMPNUMARR = [];
        for(let i = 0, II = INP.length; i < II; i ++) {
          TEMPIDARR.push(INP.eq(i).attr("data-stockId"));
          TEMPNUMARR.push(INP.eq(i).val());
        }
        // 比对出库数量是否大于总数
        for(let i = 0, II = this.listDataArr.length; i < II; i ++) {
          if(this.listDataArr[i].outCount > this.listDataArr[i].account) {
              this.EquipmentRepairErrorTitle = "出库数量错误";
              this.EquipmentRepairError = '请检查出库数量 !';
              this.switchGlobalErrorInformation = true;
              return;
          }
          console.log(this.listDataArr)
          if(this.listDataArr[i].inventoryType == 'unique' && !this.listDataArr[i].qrCode) {
              this.EquipmentRepairErrorTitle = "出库错误";
              this.EquipmentRepairError = '请对唯一标识的备件绑定二维码 !';
              this.switchGlobalErrorInformation = true;
              return;
          }
        }

        const ObjectData = {
          outOrderType: 'borrowOut',
          outWarehouseId: this.inWarehouseId,
          remark: this.detailsTextarea
        };
        let dataObject = {
          applyFormBean: ObjectData,
          detailFormBeans: this.listDataArr,
          userKey: this.common.getLocalStorage('userKey')
        };
        this.requests.insertBorrowOutStock(dataObject).then((responend) => {
          this.$router.push({
            path: '/Outbound'
          });
        })
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalErrorInformation,
      HomeOrdinarySideOut
    },
    mounted: function() {
      /* 初始化数据 */
    },
    watch: {
      /* 监听 */
      inWarehouseId: function(val) {
        this.paramsObj.warehouseId = val;
        this.requests.getSparePartInWarehouse(this.paramsObj).then(data => {
          this.listTotalDataArr = data.data;
          for(let i = 0, II = this.listTotalDataArr.length; i < II; i ++) {
            this.listTotalDataArr[i].outCount = 1;
          }
          console.log(this.listTotalDataArr)
        });
      },
      $route(to, from) {
        if (to.name == "BorrowingOutDetails" && from.name == "BorrowOutboundSelectList") {
          // 比对数据
          let paramsArr = this.$route.query.params;
          this.listDataArr = [];
          for (let i = 0, II = paramsArr.length; i < II; i++) {
            for (let v = 0, VV = this.listTotalDataArr.length; v < VV; v++) {
              if (paramsArr[i] == this.listTotalDataArr[v].stockId) {
                this.listDataArr.push(this.listTotalDataArr[v]);
              }
            }
          }
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #BorrowingOutDetails {
    width: 100%;
    height: 100%;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .BorrowingOutDetails-main {
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
          align-items: center;
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
          label {
            font-size: $widthWeb22;
            color: #bfbfbf;
          }
          .inp-txt {
            flex: 1;
            display: inline-block;
            font-size: $widthWeb22;
            line-height: 80px;
            text-align: right;
            color: #333;
            background: transparent;
          }
          .inp-icon {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            width: 36px;
            i {
              // margin-right: 20px;
              width: 16px;
              height: 27px;
              background-image: url("../../../../assets/home/arrow.png");
              background-size: 100% 100%;
            }
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
        padding-bottom: 26px;
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
    .gloobalLoading {
      height: 100%;
    }
  }
</style>
