<template>
  <div id='PutStorageEdit'>
    <div class="PutStorageEdit-main">
      <ul class="main-details">
        <li v-for="(itemDetails, index) in detailsData" :key="index">
          <p>{{itemDetails.title}}</p>
          <p v-if="itemDetails.initTimer">{{itemDetails.text | initDate}}</p>
          <p v-else>{{itemDetails.text}}</p>
        </li>
        <li @click="timerPicker($event)">
          <p>入库日期</p>
          <p>
            {{details.inDate}}
            <img class="icon-img" src="../../../../assets/home/arrow.png"/>
          </p>
        </li>
        <li class="list-textarea">
          <p>备注<span>( 最多可输入200个字 )</span></p>
          <textarea class="textBox" placeholder="请输入备注信息" v-model="details.remark"></textarea>
        </li>
      </ul>
      <div class="main-listing">
        <p class="listing-title">入库清单</p>
        <!--<div class="listing-list-box" ref="listing">-->
        <ul class="listing-list">
          <li v-for="(itemParts, index) in detailsListingData" :key="index" :class="{canDelete:itemParts.canDelete}">
            <!--<p>{{itemParts.partName}}<span>{{itemParts.count}}个</span></p>-->
            <ul>
              <li>
                <div class="first-child" @click="getPartListBySparePartTypeId(itemParts)">
                  <input type="text" v-model="itemParts.partName" readonly="readonly"/>
                </div>
                <div class="last-child text-right">
                  <div @click="getQrCode(itemParts,index)" class="qrCode-bind"
                       v-if="itemParts.inventoryType == 'unique'">
                    <div class="qrCode-icon"></div>
                    <div class="qrCode-text">扫码入库</div>
                  </div>
                  <!--<div class="add-icon-box">-->
                  <!--<div class="img-box-margin">-->
                  <img class="add-image" v-if="itemParts.inventoryType == 'notUnique'&&!itemParts.canDelete"
                       src="../../../../assets/home/add.png" @click="copyItemClick(itemParts,index)"/>
                  <!--</div>-->
                  <!--<div class="img-box-margin">-->
                  <img class="add-image" v-if="itemParts.canDelete" src="../../../../assets/home/mini_colse1.png"
                       @click="deleteItemClick(itemParts,index)"/>
                  <!--</div>-->
                  <!--<div class="add-icon">&nbsp;</div>-->
                  <!--</div>-->
                </div>
              </li>
              <li>
                <div class="first-child text-right" style="flex: 1">
                  <div class="input-label">
                    二维码：
                  </div>
                  <div class="input-field">
                    <input type="text" v-model="itemParts.qrCode" :readonly="inStockType=='useIn'?false:'readonly'"/>
                  </div>
                </div>
                <!--<div>-->
                <!--</div>-->
              </li>
              <li>
                <div class="first-child text-right">
                  <div class="input-label">
                    入库数量：
                  </div>
                  <div class="input-field">
                    <input type="text" @input="handleInput($event,itemParts,index)" v-model="itemParts.inStockAcount"/>
                  </div>
                </div>
                <div class="last-child">
                  <div class="input-label">
                    唯一标识：
                  </div>
                  <div class="display-inline" @click="inventoryTypeClick($event,itemParts,'inventoryType','unique')">
                    <div class="img-box" v-if="itemParts.inventoryType == 'notUnique'">
                      <img src="../../../../assets/home/radio_no.png" alt=""/>
                    </div>
                    <div class="img-box" v-if="itemParts.inventoryType == 'unique'">
                      <img src="../../../../assets/home/radio_sel.png" alt=""/>
                    </div>
                    <div class="last-child-text">
                      是
                    </div>
                  </div>
                  <div class="display-inline" @click="inventoryTypeClick($event,itemParts,'inventoryType','notUnique')">
                    <div class="img-box" v-if="itemParts.inventoryType == 'unique'">
                      <img src="../../../../assets/home/radio_no.png" alt=""/>
                    </div>
                    <div class="img-box" v-if="itemParts.inventoryType == 'notUnique'">
                      <img src="../../../../assets/home/radio_sel.png" alt=""/>
                    </div>
                    <div class="last-child-text">
                      否
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="first-child text-right">
                  <div class="input-label">
                    存放位置：
                  </div>
                  <div class="input-field" @click="shelvesList(itemParts)">
                    <input type="text" v-model="itemParts.shelfNumber" readonly="readonly"/>
                  </div>
                </div>
                <div class="last-child">
                  <div class="input-label">
                    备件状态：
                  </div>
                  <div class="display-inline" @click="inventoryTypeClick($event,itemParts,'status','normal')">
                    <div class="img-box" v-if="itemParts.status == 'bad'">
                      <img src="../../../../assets/home/radio_no.png" alt=""/>
                    </div>
                    <div class="img-box" v-if="itemParts.status == 'normal'">
                      <img src="../../../../assets/home/radio_sel.png" alt=""/>
                    </div>
                    <div class="last-child-text">
                      好
                    </div>
                  </div>
                  <div class="display-inline" @click="inventoryTypeClick($event,itemParts,'status','bad')">
                    <div class="img-box" v-if="itemParts.status == 'normal'">
                      <img src="../../../../assets/home/radio_no.png" alt=""/>
                    </div>
                    <div class="img-box" v-if="itemParts.status == 'bad'">
                      <img src="../../../../assets/home/radio_sel.png" alt=""/>
                    </div>
                    <div class="last-child-text">
                      坏
                    </div>
                  </div>
                </div>
              </li>
            </ul>
          </li>
        </ul>
        <!--</div>-->
      </div>
    </div>
    <div class="globalFooter">
      <button @click="inStockClick">入库</button>
    </div>
    <!--测出 备件-->
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <HomeShelvesList :switchLienSite="switchHomeShelvesList" :switchWarehouseId="details.inWarehouseId"
                     @switchHomeShelvesList="getHomeShelvesList" @HomeCurrentType='getHomeShelvesType'>
    </HomeShelvesList>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom"
                            :errorInformation="GlobalTroubleNumberError"
                            :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
    <GlobalSuccessInformationCommon v-show="switchGlobalSuccessInformation"
                                    @hideSuccessCurrentCom="getHideSuccessCurrentCom"
                                    :successInformation="successInformation"
                                    :successInformationTitle="successInformationTitle" :successText="successText">
    </GlobalSuccessInformationCommon>
    <mt-datetime-picker v-model="pickDate" ref="pickDate" type="date" year-format="{value} 年" month-format="{value} 月"
                        date-format="{value} 日" @confirm="getConfirm">
    </mt-datetime-picker>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut';
  import HomeShelvesList from "@/components/home/homeCom/HomeShelvesList";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import GlobalSuccessInformationCommon from "@/components/globalCom/GlobalSuccessInformationCommon";

  export default {
    name: "PutStorageEdit",
    data() {
      return {
        swtichLoading: true,
        details: {},
        inStockId: this.$route.query.inStockId,
        detailsData: [],
        detailsListingData: [],
        inStockType:'',
        switchHomeOrdinarySideOut: false, //右侧弹出层显示隐藏
        HomeOrdinarySideOutTitle: '备件', //右侧弹出层标题
        HomeOrdinarySideOutData: [], //右侧弹出层数据
        switchHomeShelvesList: false,
        //成功提示
        switchGlobalSuccessInformation: false,
        successInformation: '',
        successInformationTitle: '',
        successText: '',
        //错误提示
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: '',
        GlobalTroubleNumberErrorTitle: '',
        //时间选择
        pickDate: ''
      };
    },
    methods: {
      /* 方法 */
      initData() {
        let PROMPOBJ = {
          inStockId: this.inStockId,
        };
        this.api.storageDetailsDataAllprompObj(PROMPOBJ).then((data) => {
          const DATA = data.data;
          if (DATA.inStockStatus == 'alreadyIn') {
            this.$router.replace({
              path: '/PutStorageDetails',
              query: {
                'inStockId': this.inStockId
              }
            });
            return;
          }
          this.inStockType = DATA.inStockType;
          this.detailsData.push({
              title: '入库单号',
              text: DATA.inStockApplyNo
            }, {
              title: '入库仓库',
              text: DATA.inWarehouseName
            }, {
              title: '入库性质',
              text: DATA.inStockType
            }, {
              title: '状态',
              text: DATA.inStockStatus == 'toBeIn' ? '待入库' : '已入库'
            },
            // {
            //   title: '备注',
            //   text: DATA.remark || '暂无备注!'
            // }
          );
          switch (this.detailsData[2].text) {
            case 'newIn':
              this.detailsData[2].text = '新品入库';
              break;
            case 'useIn':
              this.detailsData[2].text = '归还入库';
              break;
            case 'transferIn':
              this.detailsData[2].text = '调拨入库';
              break;
            case 'borrowIn':
              this.detailsData[2].text = '借用入库';
              break;
            case 'returnIn':
              this.detailsData[2].text = '返还入库';
              break;
          }
          // {
          //   title: '入库日期',
          //   text: DATA.inDate,
          //   initTimer: true
          // },
          // {
          //   title: '操作人',
          //   text: DATA.inStockUser
          // }, {
          //   title: '操作时间',
          //   text: DATA.modifyTime,
          //   initTimer: true
          // },
          this.details = JSON.parse(JSON.stringify(DATA));
          this.details.inDate = this.common.getDate('yyyy-MM-dd');
          this.detailsListingData = JSON.parse(JSON.stringify(DATA.details));
          this.detailsListingDataCopy = JSON.parse(JSON.stringify(DATA.details));
          this.detailsListingData.forEach(function (item, index) {
            // item.qrCode = '';
            item.shelfNumber = '';
            item.houseNo = '';
            item.isCopy = false;
          });
          this.swtichLoading = false;
        })
      },
      // 时间选择
      timerPicker(event) {
        this.pickDate = this.details.inDate;
        this.$refs.pickDate.open();
      },
      // 时间确认
      getConfirm(val) {
        this.details.inDate = typeof val == 'object' ? this.common.formatDate(this.pickDate, 'ymd') : this.pickDate;
        this.pickDate = this.details.inDate;
      },
      //调用扫描二维码
      getQrCode(item, index) {
        let $this = this;
        if ($this.scrollClick) {
          return;
        }
        $this.scrollClick = true;
        setTimeout(function () {
          $this.scrollClick = false;
        }, 200);
        $this.changeItem = item;
        $this.changeIndex = index;
        this.wxApi.WxScanQRCode().then(qrCode => {
          if (qrCode) {
            let requestData = {
              canBeUse: $this.inStockType=='useIn',
              qrCode: qrCode,
              userKey: $this.common.getLocalStorage('userKey')
            };
            $this.requests.getStockByQrCode(requestData).then(function (data) {
              if (data.result == 'success') {
                if (data.data == 'canbeuse') {
                  $this.changeItem.qrCode = qrCode;
                  $this.$set($this.detailsListingData, index, $this.changeItem);
                } else {
                  if (data.data.sparePartTypeId !== $this.changeItem.sparePartTypeId) {
                    $this.errorMessageShow('温馨提示', '扫描备件与入库备件备件类型不同，请核对后重试！');
                    return;
                  }
                  if (data.data.houseNo && data.data.shelfNumber) {
                    data.data.shelfNumber = data.data.houseNo + ' ' + data.data.shelfNumber;
                  }
                  // let inStockAcount = JSON.parse(JSON.stringify($this.changeItem)).inStockAcount;
                  // Object.assign($this.changeItem, data.data);
                  $this.changeItem.sparePartId = data.data.sparePartId;
                  $this.changeItem.partName = data.data.partName;
                  // $this.changeItem.goodsShelvesId = data.data.goodsShelvesId;
                  $this.changeItem.status = data.data.status;
                  $this.changeItem.inventoryType = data.data.inventoryType;
                  // $this.changeItem.shelfNumber = data.data.shelfNumber;
                  $this.changeItem.qrCode = qrCode;
                  // $this.changeItem.inStockAcount = inStockAcount;
                  $this.$set($this.detailsListingData, index, $this.changeItem);
                }
              } else {
                $this.errorMessageShow('温馨提示', data.message);
              }
            })
          }
        });
      },
      inventoryTypeClick($event, item, key, value) {
        let $this = this;
        $event.stopPropagation();
        item[key] = value;
        if (key == 'inventoryType' && value == 'notUnique') {
          item.qrCode = '';
        }
        // $this.detailsListingData.forEach(function (checkItem, index) {
        //   if (item.inStockDetailId == checkItem.inStockDetailId) {
        //     checkItem[key] = value;
        //     $this.$set($this.detailsListingData, index, checkItem);
        //   }
        // });
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        this.changeItem.partName = val.text;
        this.changeItem.sparePartId = val.sideOutId;
      },
      // 获取
      getPartListBySparePartTypeId(item) {
        this.changeItem = item;
        let $this = this;
        var data = {
          sparePartTypeId: item.sparePartTypeId,
          userKey: this.common.getLocalStorage('userKey')
        };
        this.requests.getPartListBySparePartTypeId(data).then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].sparePartId,
              sideOutText: DATA[i].partName
            });
          }
          // $this.HomeOrdinarySideOutTitle = '供应商';
          $this.switchHomeOrdinarySideOut = true;
        })
      },
      // 货架显示
      shelvesList(item) {
        this.changeItem = item;
        this.switchHomeShelvesList = true;
      },
      // 货架显示隐藏
      getHomeShelvesList(val) {
        this.switchHomeShelvesList = val;
      },
      // 接收 货架 传过来的值
      getHomeShelvesType(val) {
        this.changeItem.goodsShelvesId = val.goodsShelvesId;
        this.changeItem.shelfNumber = val.houseNoAndShelfNumber;
        this.changeItem.houseNo = val.houseNoAndShelfNumber;
      },
      copyItemClick(item, index) {
        let $this = this;
        if (this.scrollClick) {
          return;
        }
        this.scrollClick = true;
        let newItem = JSON.parse(JSON.stringify(item));
        newItem.canDelete = true;
        this.detailsListingData.splice(index + 1, 0, newItem);
        setTimeout(function () {
          $this.scrollClick = false;
        }, 200);
      },
      deleteItemClick(item, index) {
        let $this = this;
        if (this.scrollClick) {
          return;
        }
        this.scrollClick = true;
        $this.detailsListingData.splice(index, 1);
        setTimeout(function () {
          $this.scrollClick = false;
        }, 200);
      },
      handleInput($event, item, index) {
        let $this = this;
        if (!$event.target.value) {
          $event.target.value = 0;
        }
        let newValue = Number($event.target.value.replace(/[^\d]/g, ''));
        if (Number(newValue) == 0) {
          newValue = 1;
        }
        item.inStockAcount = newValue;
        $event.target.value = item.inStockAcount;
        $this.$set($this.detailsListingData, index, item);
      },
      inStockClick() {
        let $this = this;
        let checkCountIndex = null;
        if ($this.swtichLoading) {
          return;
        }
        $this.detailsListingDataCopy.forEach(function (sItem, sIndex) {
          let sItemTotalCount = Number(sItem.inStockAcount);
          let totalCount = 0;
          $this.detailsListingData.forEach(function (fItem, fIndex) {
            if (sItem.inStockDetailId == fItem.inStockDetailId) {
              totalCount += Number(fItem.inStockAcount);
            }
          });
          if (sItemTotalCount !== totalCount && !checkCountIndex) {
            checkCountIndex = sIndex + 1;
          }
        });
        if (checkCountIndex) {
          $this.errorMessageShow('温馨提示', '第' + checkCountIndex + '条数据拆分前后数量需保持一致！');
          return;
        }
        let checkQrCodeIndex = true;
        $this.detailsListingData.forEach(function (fItem, fIndex) {
          fItem.alreadyInCount = fItem.inStockAcount;
          fItem.inWarehouseId = $this.details.inWarehouseId;
          $this.detailsListingData.forEach(function (sItem, sIndex) {
            if (checkQrCodeIndex && fItem.qrCode && sItem.qrCode && fIndex != sIndex && fItem.qrCode == sItem.qrCode) {
              checkQrCodeIndex = false;
            }
          });
        });
        if (!checkQrCodeIndex) {
          $this.errorMessageShow('温馨提示', '有二维码重复，不能绑定相同的二维码！');
          return;
        }
        let data = JSON.parse(JSON.stringify($this.details));
        let date = new Date();
        let hms = ' ' + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':' + ('0' + date.getSeconds()).slice(-2);
        data.inDate += hms;
        let s = data.inDate.replace(/-/g, "/");
        let newData = new Date(s);
        data.inDate = newData.getTime();
        data.detailEntities = $this.detailsListingData;
        data.userKey = $this.common.getLocalStorage('userKey');
        $this.swtichLoading = true;
        this.requests.commitStockIn(data).then(function (data) {
          $this.swtichLoading = false;
          if (data.result == 'success') {
            $this.showSuccessCurrentCom();
          } else {
            $this.errorMessageShow('温馨提示', data.message);
          }
        })
      },
      showSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = true;
        this.successInformation = '入库成功';
        this.successInformationTitle = '温馨提示';
        this.successText = '确定';
      },
      getHideSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = false;
        this.common.setSessionStorage('PutStorageRefresh', true);
        // this.$router.go(-1);
        this.goBack();
      },
      getHideCurrentCom() {
        this.switchGlobalErrorInformation = false;
      },
      errorMessageShow(title, message) {
        this.switchGlobalErrorInformation = true;
        this.GlobalTroubleNumberError = message;
        this.GlobalTroubleNumberErrorTitle = title;
      },
      goBack() {
        this.$router.replace({
          path: '/PutStorage'
        });
      }
    },
    components: {
      /* 复用组件名称 */
      HomeOrdinarySideOut,
      HomeShelvesList,
      GlobalErrorInformation,
      GlobalSuccessInformationCommon
    },
    mounted: function () {
      /* 初始化数据 */
      this.initData();
      if (window.history && window.history.pushState) {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBack, false);
      }
    },
    watch: {
      /* 监听 */
    },
    updated: function () {
      // let listing = this.$refs.listing;
      // // ,observeDOM:false
      // new BScroll(listing, {click: true, observeDOM: false});
    },
    destroyed() {
      window.removeEventListener('popstate', this.goBack, false);
    }
  }
</script>

<style lang='scss'>
  @import "@/style/global.scss";

  #PutStorageEdit {
    position: absolute;
    top: 0;
    left: 0;
    overflow-y: auto !important;
    padding: 20px 20px 100px 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background-color: #faf6ec;
    /*width: 100%;*/
    /*height: calc(100% - 80px) !important;*/
    /*overflow-y: auto !important;*/
    /*padding: 20px;*/
    /*box-sizing: border-box;*/
    .text-right {
      text-align: right;
    }
    .display-inline {
      display: inline-block;
    }
    .gloobalLoading {
      position: fixed;
      top: 0;
      left: 0;
      z-index: 1000;
      width: 100%;
      height: 100%;
      background-color: #f6f7eb;
    }
    .PutStorageEdit-main {
      width: 100%;
      /*height: calc(100% - 70px);*/
      .main-details {
        margin-bottom: 20px;
        padding: 20px;
        width: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 18px;
        & > li {
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
            .icon-img {
              margin-top: 18px;
              margin-left: 20px;
              height: 27px;
            }
          }
          & p:nth-last-child(1) {
            width: 66%;
            color: #333;
          }
        }
        & > li:nth-last-child(1) {
          & p:nth-last-child(1) {
            text-align: left;
          }
        }
        .list-textarea {
          display: block;
          padding: 0;
          border: 0;
          & > p {
            font-size: $widthWeb22;
            padding-left: 20px;
            text-align: left;
            span {
              margin-left: 10px;
              font-size: $widthWeb18;
              color: #666;
            }
          }
          .textBox {
            /*margin-top: 9px;*/
            padding: 20px 20px;
            width: 100%;
            height: 110px;
            font-size: $widthWeb18;
            color: #333;
            resize: none;
            border: solid 1px #bfbfbf;
            box-sizing: border-box;
          }
        }
      }
      .main-listing {
        /*padding: 15px 35px 0px 35px;*/
        padding: 15px 0px 0px 0px;
        width: 100%;
        /*height: calc(100% - 570px);*/
        background-color: #fff;
        border: solid 1px #dfdcdc;
        border-radius: 18px;
        box-sizing: border-box;
        .listing-title {
          width: 100%;
          padding-left: 35px;
          font-size: $widthWeb22;
          color: #fd8521;
          /*line-height: 22px;*/
          line-height: 44px;
          box-sizing: border-box;
        }
        /*.listing-list-box {*/
        /*height: calc(100% - 70px);*/
        /*!*padding: 0 35px;*!*/
        /*overflow: hidden;*/
        /*}*/
        .listing-list {
          width: 100%;
          /*padding: 0 35px;*/
          box-sizing: border-box;
          & > li.canDelete {
            background: #eee;
            & > ul {
              border-bottom: dashed 1px #fd8521;
            }
          }
          & > li {
            /*padding-left: 18px;*/
            /*padding-top: 10px;*/
            /*padding-bottom: 25px;*/
            padding: 10px 35px 25px 35px;
            /*border-bottom: solid 1px #dfdcdc;*/
            box-sizing: border-box;
            input {
              font-size: $widthWeb18;
              width: 100%;
              border-bottom: 1px solid #c0c0c0;
              background: transparent;
              /*padding-left: 1em;*/
              text-indent: 1em;
              line-height: 35px;
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
            & > ul {
              padding-bottom: 25px;
              border-bottom: solid 1px #dfdcdc;
              & > li {
                padding-top: 15px;
                height: 35px;
                display: flex;
                .qrCode-bind {
                  /*margin: 4px 0;*/
                  padding: 0 5px;
                  line-height: 25px;
                  font-size: $widthWeb12;
                  border-radius: 5px;
                  display: inline-block;
                  background: #fbe9db;
                  border: 1px solid #fd8521;
                  .qrCode-icon {
                    display: inline-block;
                    /*margin: 5px;*/
                    height: 15px;
                    width: 15px;
                    line-height: 25px;
                    background-image: url("../../../../assets/home/scan.png");
                    background-size: 100% 100%;
                  }
                  .qrCode-text {
                    display: inline-block;
                    line-height: 25px;
                  }
                }
                /*<!--.add-icon-box {-->*/
                /*<!--position: relative;-->*/
                /*<!--margin: 5px 0;-->*/
                /*<!--!*padding: 0 5px;*!-->*/
                /*<!--line-height: 25px;-->*/
                /*<!--font-size: $widthWeb12;-->*/
                /*<!--!*border-radius: 5px;*!-->*/
                /*<!--display: inline-block;-->*/
                /*<!--}-->*/
                .add-image {
                  display: inline-block;
                  margin: 5px 0px 0px 25px;
                  height: 25px;
                  width: 25px;
                }
                .add-icon {
                  display: inline-block;
                  /*line-height: 25px;*/
                  /*margin-left: 25px;*/
                  margin: 5px 0px 5px 25px;
                  height: 25px;
                  width: 25px;
                  /*transform: translateY(2.5px);*/
                  background-image: url("../../../../assets/home/add.png");
                  background-size: 100% 100%;
                  background-repeat: no-repeat;
                  background-position: center center;
                }
                .input-label {
                  font-size: $widthWeb18;
                  display: inline-block;
                  float: left;
                  color: #bfbfbf;
                  line-height: 35px;
                  width: 120px;
                  white-space: nowrap;
                  text-align: left;
                  /*width: 5em;*/
                }
                .input-field {
                  margin-left: .3em;
                  /*line-height: 35px;*/
                  display: inline-block;
                  width: calc(100% - 5.5em);
                }
                & > div.first-child {
                  /*flex: .55;*/
                  height: 35px;
                  font-size: $widthWeb18;
                  width: 365px;
                }
                & > div.last-child {
                  /*flex: .45;*/
                  /*height: 35px;*/
                  line-height: 35px;
                  /*width: 245px;*/
                  width: 270px;
                  font-size: $widthWeb18;
                  padding-left: 30px;
                  .img-box {
                    display: inline-block;
                    margin-top: 5px;
                    height: 25px;
                    width: 25px;
                    img {
                      height: 100%;
                      width: 100%;
                    }
                  }
                  & > .img-box-margin {
                    display: inline-block;
                    margin-top: 5px;
                    margin-left: 25px;
                    height: 25px;
                    width: 25px;
                    img {
                      height: 100%;
                      width: 100%;
                    }
                  }
                  .last-child-text {
                    line-height: 35px;
                    display: inline-block;
                    font-size: $widthWeb18;
                  }
                }
              }
            }
            .list-parts {
              width: 100%;
              display: flex;
              align-items: center;
              .parts-img {
                margin-right: 14px;
                width: 70px;
                height: 70px;
                img {
                  display: inline-block;
                  width: 100%;
                  height: 100%;
                }
              }
              & > ul {
                display: flex;
                width: 100%;
                li {
                  flex: 1;
                  text-align: center;
                  border-right: solid 1px #dfdcdc;
                  box-sizing: border-box;
                  &:nth-last-child(1) {
                    border: 0;
                  }
                  p {
                    font-size: $widthWeb18;
                    color: #333;
                    line-height: 36px;
                    &:nth-last-child(1) {
                      margin-top: 4px;
                      font-size: $widthWeb14;
                      color: #bfbfbf;
                      line-height: 26px;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    .footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 80px;
      text-align: right;
      background-color: #eeeeee;
      button {
        width: 220px;
        height: 80px;
        font-size: $widthWeb25;
        color: #fff;
        line-height: 80px;
        background-color: #fc6806;
      }
    }
  }
</style>
