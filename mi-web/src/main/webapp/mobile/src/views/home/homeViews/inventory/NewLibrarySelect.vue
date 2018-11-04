<template>
  <div id='NewLibrarySelect'>
    <div class="NewLibrarySelect-main">
      <div class="head">
        <input class="head-input" type="text" name="user" placeholder="请输入物品名称" v-model="searchContent"/>
        <div class="search-icon" @click="getSparePartList"></div>
      </div>
      <ul class="main-list">
        <li @click="getSupplierList">
          <label class="inp-title">供应商</label>
          <input type="text" name="timer" id="inp-supplier" class="inp-txt" placeholder="请选择供应商" readonly="readonly"
                 v-model="supplierName"/>
          <div class="inp-icon"><i></i></div>
        </li>
        <!--<li @click="getShelfNumberList">-->
        <li @click="shelvesList">
          <label for="inp-houseNoAndShelfNumber" class="inp-title">存放位置</label>
          <input type="text" name="site" id="inp-houseNoAndShelfNumber" class="inp-txt" placeholder="请选择房间号及货架货柜号"
                 readonly="readonly" v-model="houseNoAndShelfNumber"/>
          <div class="inp-icon"><i></i></div>
        </li>
      </ul>
      <div class="array-list" ref="sparePartList">
        <ul class="array-list-content">
          <li v-for="(item,index) in sparePartList" @click="itemClick($event,item,index)">
            <div class="item-checkbox">
              <!--<input type="checkbox" :value="item.sparePartId" :id="item.sparePartId"-->
              <!--v-model="item.check" @click="checked(item)"/>-->
              <!--<label :for="item.sparePartId"></label>-->
              <label :class="{'hasCheck':item.check}"></label>
            </div>
            <div class="item-content">
              <div class="item-content-title">
                {{item.partName}}
              </div>
              <div class="item-content-body">
                <div class="item-content-body-image">
                  <img v-if="item.imageUrl!=='noImage'" :src="item.imageUrl" alt="">
                  <img v-if="item.imageUrl=='noImage'" src="../../../../assets/home/pic2.png" alt=""/>
                </div>
                <div class="item-content-body-text">
                  <div class="item">
                    <div class="name-min">
                      分类：
                    </div>
                    <div class="value">
                      {{item.categoryName}}
                    </div>
                  </div>
                  <div class="item">
                    <div class="name">
                      品牌：
                    </div>
                    <div class="value">
                      {{item.brand}}
                    </div>
                  </div>
                  <div class="item">
                    <div class="name-min">
                      型号：
                    </div>
                    <div class="value">
                      {{item.specificationModel}}
                    </div>
                  </div>
                  <div class="item">
                    <div class="name">
                      唯一标识：
                    </div>
                    <div class="value">
                      <div @click="inventoryTypeClick($event,item,'unique')">
                        <div v-if="item.inventoryType == 'notUnique'||item.inventoryType == 'no'">
                          <img src="../../../../assets/home/radio_no.png" alt=""/>
                        </div>
                        <div v-if="item.inventoryType == 'unique'">
                          <img src="../../../../assets/home/radio_sel.png" alt=""/>
                        </div>
                        <div>是</div>
                      </div>

                      <div @click="inventoryTypeClick($event,item,'notUnique')">
                        <div v-if="item.inventoryType == 'unique'||item.inventoryType == 'no'">
                          <img src="../../../../assets/home/radio_no.png" alt=""/>
                        </div>
                        <div v-if="item.inventoryType == 'notUnique'">
                          <img src="../../../../assets/home/radio_sel.png" alt=""/>
                        </div>
                        <div>否</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="save">确定</button>
    </div>
    <!--测出 供应商-->
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle'
                         :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType"
                         @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom"
                            :errorInformation="GlobalTroubleNumberError"
                            :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
    <HomeShelvesList :switchLienSite="switchHomeShelvesList"
                  :switchWarehouseId="warehouseId"
                  @switchHomeShelvesList="getHomeShelvesList" @HomeCurrentType='getHomeShelvesType'>
    </HomeShelvesList>
    <!--<router-view class="routerView"></router-view>-->
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut';
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import HomeShelvesList from "@/components/home/homeCom/HomeShelvesList";
  export default {
    name: "NewLibrarySelect",
    data() {
      return {
        // 货架
        switchHomeShelvesList:false,
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: '',
        GlobalTroubleNumberErrorTitle: '',
        searchContent: '',
        supplierId: '',
        supplierName: '',
        goodsShelvesId: '',
        houseNoAndShelfNumber: '',
        warehouseId: this.$route.query.warehouseId,
        switchHomeOrdinarySideOut: false,//右侧弹出层显示隐藏
        HomeOrdinarySideOutTitle: '供应商',//右侧弹出层标题
        HomeOrdinarySideOutData: [],//右侧弹出层数据
        sparePartList: [],
        checkSparePartList: [],
        newLibraryJson: {},
        init: false,
        isSave: false
      };
    },
    methods: {
      /* 方法 */
      // 货架显示
      shelvesList() {
        this.switchHomeShelvesList = true;
      },
      // 货架显示隐藏
      getHomeShelvesList(val) {
        this.switchHomeShelvesList = val;
      },
      // 接收 货架 传过来的值
      getHomeShelvesType(val) {
        this.goodsShelvesId = val.goodsShelvesId;
        this.houseNoAndShelfNumber = val.houseNoAndShelfNumber;
      },
      // 初始化
      initData() {
        let newLibraryJson = this.common.getSessionStorage('newLibraryJson', true, true);
        if (newLibraryJson) {
          this.newLibraryJson = newLibraryJson;
        }
        let checkSparePartList = this.common.getSessionStorage('checkSparePartList', true, true);
        if (checkSparePartList) {
          this.checkSparePartList = checkSparePartList;
        }
        this.getSparePartList();
        // this.inDate = this.getDate('yyyy-MM-dd');
      },
      // 时间选择
      timerPicker(event) {
        this.pickDate = this.inDate;
        this.$refs.pickDate.open();
      },
      // 时间确认
      getConfirm(val) {
        this.inDate = this.common.formatDate(this.pickDate, 'ymd');
      },
      // 获取用户所负责仓库
      getSupplierList() {
        let $this = this;
        this.requests.getSupplierList().then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          $this.HomeOrdinarySideOutData.push({
            sideOutId: '',
            sideOutText: '无'
          });
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].code,
              sideOutText: DATA[i].name
            });
          }
          $this.HomeOrdinarySideOutTitle = '供应商';
          $this.switchHomeOrdinarySideOut = true;
        })
      },
      // 获取用户所属工区房间货架
      getShelfNumberList() {
        let $this = this;
        let data = {
          userKey: localStorage.getItem('userKey'),
          warehouseId: this.warehouseId
        };
        this.requests.getShelfNumberList(data).then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].goodsShelvesId,
              sideOutText: DATA[i].shelfNumber
            });
          }
          $this.HomeOrdinarySideOutTitle = '存放位置';
          $this.switchHomeOrdinarySideOut = true;
        })
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        if (val.title == '供应商') {
          this.supplierName = val.text;
          this.supplierId = val.sideOutId;
        } else {
          this.houseNoAndShelfNumber = val.text;
          this.goodsShelvesId = val.sideOutId;
        }
      },
      getSparePartList() {
        let $this = this;
        let data = {
          userKey: localStorage.getItem('userKey'),
          searchContent: $this.searchContent,
          sparePartTypeId: ""
        };
        this.requests.getSparePartList(data).then(function (data) {
          const DATA = data.data;
          for (let i = 0; i < DATA.length; i++) {
            DATA[i].inventoryType = 'no';
            $this.checkSparePartList.forEach(function (item, index) {
              if (item.sparePartId == DATA[i].sparePartId && !item.isOld) {
                DATA[i].inventoryType = item.inventoryType;
                DATA[i].check = true;
              }
            });
          }
          $this.sparePartList = DATA;
          if (!$this.init) {
            $this.init = true;
            let sparePartList = $this.$refs.sparePartList;
            new BScroll(sparePartList, {click: true});
          }
        })
      },
      inventoryTypeClick($event, item, value) {
        let $this = this;
        $event.stopPropagation();
        item.inventoryType = value;
        $this.checkSparePartList.forEach(function (checkItem, index) {
          if (item.sparePartId == checkItem.sparePartId && !item.isOld) {
            checkItem.inventoryType = value;
            $this.$set($this.checkSparePartList, index, checkItem);
          }
        });
        // this.checkSparePartList = this.checkSparePartList;
      },
      itemClick($event, item, index) {
        let newItem = item;
        newItem.check = !newItem.check;
        this.$set(this.sparePartList, index, newItem);
        if (newItem.check) {
          this.checkSparePartList.push(newItem);
        } else {
          let deleteIndex = '';
          this.checkSparePartList.forEach(function (item, index) {
            if (item.sparePartId == newItem.sparePartId && !item.isOld) {
              deleteIndex = index;
            }
          });
          this.checkSparePartList.splice(deleteIndex, 1);
        }
      },

      getHideCurrentCom() {
        this.switchGlobalErrorInformation = false;
      },

      errorMessageShow(title, message) {
        this.switchGlobalErrorInformation = true;
        this.GlobalTroubleNumberError = message;
        this.GlobalTroubleNumberErrorTitle = title;
      },

      // 保存
      save() {
        let $this = this;
        $this.isSave = true;
        if ($this.checkSparePartList.length > 0) {
          // if (!$this.supplierId) {
          //   $this.errorMessageShow("添加错误", "请选择供应商");
          //   return;
          // }
          // if (!$this.goodsShelvesId) {
          //   $this.errorMessageShow("添加错误", "请选择存放位置");
          //   return;
          // }
          let checkInventoryType = true;
          let checkSparePartList = $this.checkSparePartList;
          checkSparePartList.forEach(function (item, index) {
            if(item.inventoryType == 'no'&&checkInventoryType){
              checkInventoryType = false;
            }
            if (!item.isOld) {
              item.supplierId = $this.supplierId;
              item.supplierName = $this.supplierName;
              item.goodsShelvesId = $this.goodsShelvesId;
              item.shelfNumber = $this.houseNoAndShelfNumber;
            }
          });
          if(!checkInventoryType){
            $this.errorMessageShow("添加错误", "请选择所有唯一标识");
            return;
          }
          $this.common.setSessionStorage('checkSparePartList', checkSparePartList, true);
        }
        this.$router.go(-1);
      },

    },
    components: {
      /* 复用组件名称 */
      HomeOrdinarySideOut,
      GlobalErrorInformation,
      HomeShelvesList
    },
    beforeDestroy: function () {
      let $this = this;
      if (!$this.isSave) {
        let oldCheckSparePartList = [];
        $this.checkSparePartList.forEach(function (item,index) {
          if(item.isOld){
            oldCheckSparePartList.push(item);
          }
        });
        $this.common.setSessionStorage('checkSparePartList', oldCheckSparePartList, true);
      }

        $this.common.setSessionStorage('newLibraryJson', $this.newLibraryJson, true);
    },
    mounted: function () {
      /* 初始化数据 */
      this.initData();
      // this.$nextTick(function() {
      //   let sparePartList = this.$refs.sparePartList;
      //   new BScroll(sparePartList, {});
      // });
    },
    watch: {
      /* 监听 */
      checkSparePartList(newValue, oldValue) {
        // this.common.setSessionStorage('checkSparePartList',newValue,true);
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";

  #NewLibrarySelect {
    width: 100%;
    padding: 20px;
    box-sizing: border-box;
    .NewLibrarySelect-main {
      width: 100%;
      height: 100%;
      padding: 20px;
      background-color: #fff;
      border: solid 1px #dfdcdc;
      border-radius: 18px;
      box-sizing: border-box;
      .head {
        position: relative;
        .head-input {
          width: 100%;
          text-align: center;
          font-size: $widthWeb18;
          line-height: 55px;
          border: 1px solid #c7c7c7;
          border-radius: 18px;
        }
        .search-icon {
          position: absolute;
          z-index: 2;
          top: 10px;
          right: 17px;
          width: 39px;
          height: 39px;
          background: url("../../../../assets/home/icon_search.png") no-repeat;
          background-size: 100% 100%;
        }
      }
      .main-list {
        width: 100%;
        box-sizing: border-box;
        li {
          display: flex;
          padding-left: 20px;
          font-size: $widthWeb22;
          line-height: 76px;
          border-bottom: solid 1px #dfdcdc;
          box-sizing: border-box;
          .inp-txt {
            flex: 1;
            display: inline-block;
            height: 74px;
            line-height: 74px;
            text-align: right;
            color: #333;
          }
          .inp-icon {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            width: 60px;
            /*height: 74px;*/
            i {
              margin-right: 20px;
              width: 16px;
              height: 27px;
              background-image: url("../../../../assets/home/arrow.png");
              background-size: 100% 100%;
            }
          }
        }
      }
      .array-list {
        height: calc(100% - 210px);
        overflow: hidden;
        .array-list-content {
          padding: 10px;
          /*height: 100%;*/
          li {
            height: 127px;
            padding-top: 25px;
            /*display:flex;*/
            /*justify-content: center;*/
            .item-checkbox {
              position: relative;
              float: left;
              width: 40px;
              height: 100%;
              text-align: left;
              input {
                vertical-align: middle;
                visibility: hidden;
              }
              input[type='checkbox']:checked + label:after {
                background: url("../../../../assets/home/checkbox_sel.png") no-repeat center;
                background-size: contain;
                width: 22px;
                height: 22px;
              }
              label {
                position: absolute;
                left: 0;
                top: 0;
                &:after {
                  position: absolute;
                  top: 4px;
                  left: 0px;
                  width: 22px;
                  height: 22px;
                  background: url("../../../../assets/home/checkbox_no.png") no-repeat center;
                  background-size: contain;
                  content: '';
                }
              }
              label.hasCheck:after {
                background: url("../../../../assets/home/checkbox_sel.png") no-repeat center;
                background-size: contain;
                width: 22px;
                height: 22px;
              }
            }
            .item-content {
              position: relative;
              float: left;
              /*display: flex;*/
              width: calc(100% - 40px);
              height: 100%;
              .item-content-title {
                font-size: $widthWeb18;
              }
              .item-content-body {
                position: relative;
                font-size: $widthWeb18;
                padding: 20px 0 15px 0;
                height: 70px;
                border-bottom: 1px solid #dfdcdc;
                .item-content-body-image {
                  float: left;
                  width: 70px;
                  height: 70px;
                  img{
                    width: 100%;
                    height: 100%;
                  }
                }
                .item-content-body-text {
                  float: left;
                  width: calc(100% - 90px);
                  height: 70px;
                  padding-left: 20px;
                  .item {
                    float: left;
                    width: 50%;
                    height: 35px;
                    line-height: 35px;
                    font-size: $widthWeb18;
                    .name {
                      float: left;
                      width: 50%;
                      white-space: nowrap;
                      color: #bfbfbf;
                    }
                    .name-min {
                      float: left;
                      width: 30%;
                      white-space: nowrap;
                      color: #bfbfbf;
                    }
                    .value {
                      float: left;
                      width: 50%;
                      overflow:hidden;
                      text-overflow:ellipsis;
                      white-space:nowrap;
                      div {
                        display: inline-block;
                        img {
                          margin-top: 7px;
                          height: 20px;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      .main-add {
        width: 100%;
        height: 46px;
        text-align: right;
        margin-bottom: 40px;
        button {
          width: 152px;
          height: 46px;
          background-color: #eee;
          box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.35);
          border-radius: 23px;
        }
      }
      .main-listing {
        width: 100%;
        background-color: #fff;
        .listing-title {
          width: 100%;
          padding-left: 36px;
          font-size: 22px;
          color: #fd8521;
          line-height: 42px;
          box-sizing: border-box;
        }
        .listing-list {
          width: 100%;
          padding-left: 16px;
          box-sizing: border-box;
          & > li {
            padding-left: 18px;
            box-sizing: border-box;
            &:nth-last-child(1) {
              border: 0;
            }
            p {
              font-size: 18px;
              color: #333;
              line-height: 58px;
            }
            .list-parts {
              width: 100%;
              display: flex;
              .parts-img {
                margin-right: 14px;
                width: 70px;
                height: 70px;
                img {
                  display: inline-block;
                  width: 70px;
                  height: 70px;
                }
              }
              & > ul {
                display: flex;
                width: 100%;
                padding-bottom: 20px;
                border-bottom: solid 1px #dfdcdc;
                box-sizing: border-box;
                li {
                  flex: 1;
                  text-align: center;
                  border-right: solid 1px #dfdcdc;
                  box-sizing: border-box;
                  &:nth-last-child(1) {
                    border: 0;
                  }
                  p {
                    font-size: 18px;
                    color: #333;
                    line-height: 36px;
                    &:nth-last-child(1) {
                      margin-top: 4px;
                      font-size: 14px;
                      color: #bfbfbf;
                      line-height: 26px;
                    }
                  }
                }
              }
            }
            .list-num {
              margin-top: 18px;
              width: 100%;
              height: 34px;
              font-size: 0;
              text-align: right;
              button {
                padding: 0;
                width: 42px;
                height: 34px;
                color: #666;
                font-size: 22px;
                text-align: center;
                line-height: 34px;
                background-color: #fff;
                border: solid 1px #eee;
                box-sizing: border-box;
              }
              input {
                width: 77px;
                height: 34px;
                font-size: 22px;
                color: #fd8521;
                text-align: center;
                background-color: #eee;
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
      height: 70px;
      text-align: right;
      background-color: #eeeeee;
      button {
        width: 220px;
        height: 70px;
        font-size: 28px;
        color: #fff;
        line-height: 70px;
        background-color: #fc6806;
      }
    }
  }
</style>
