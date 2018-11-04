<template>
  <div id='NewLibrary' @click.stop="sparePartHideDeleteAll">
    <div class="NewLibrary-main">
      <ul class="main-list">
        <li @click="timerPicker($event)">
          <label for="inp-timer" class="inp-title">入库日期</label>
          <input type="text" name="timer" id="inp-timer" class="inp-txt" placeholder="请选择日期" readonly="readonly"
                 v-model="inDate"/>
          <div class="inp-icon"><i></i></div>
        </li>
        <li @click="getWarehouses">
          <label for="inp-storage" class="inp-title">入库仓库</label>
          <input type="text" name="site" id="inp-storage" class="inp-txt" placeholder="请选择入库仓库" readonly="readonly"
                 v-model="inWarehouseName"/>
          <div class="inp-icon"><i></i></div>
        </li>
        <li class="list-textarea">
          <p>备注<span>( 最多可输入200个字 )</span></p>
          <textarea class="textBox" placeholder="请输入备注信息" v-model="applyRemark"></textarea>
        </li>
      </ul>
      <div class="main-add">
        <div class="listing-addList" @click="addLink">
          <i class="addList-icon"></i>
          <p>物品添加</p>
        </div>
        <div style="clear: both"></div>
        <!--<button @click="addLink"><i></i>物品添加</button>-->
      </div>

      <div class="main-listing">
        <p class="listing-title">入库清单</p>
        <!--<div class="listing-list-box" ref="sparePartList">-->
          <ul class="listing-list">
            <li v-for="(item,index) in detailEntities" @click.stop="sparePartShowDelete($event,item)">
              <div class="list-parts-delete" :class="{'show':item.readyDelete}" @click.stop="sparePartHideDelete($event,item)">
                <img src="../../../../assets/home/icon_del.png" @click.stop="sparePartDelete(item,index)"/>
              </div>
              <p>{{item.partName}}</p>
              <div class="list-parts">
                <div class="parts-img">
                  <img v-if="item.imageUrl!=='noImage'" :src="item.imageUrl" alt="">
                  <img v-if="item.imageUrl=='noImage'" src="../../../../assets/home/pic2.png" alt=""/>
                </div>
                <ul>
                  <li>
                    <p>{{item.inventoryType=='unique'?'是':'否'}}</p>
                    <p>唯一标识</p>
                  </li>
                  <li>
                    <p>{{item.shelfNumber.split(' ')[0]||'无'}}</p>
                    <p>房间号</p>
                  </li>
                  <li>
                    <p>{{item.shelfNumber.split(' ')[1]||'无'}}</p>
                    <p>货架号</p>
                  </li>
                </ul>
              </div>
              <div style="clear: both"></div>
              <div class="list-num">
                <button @click.stop="clickMinus(item)">-</button>
                <input type="text" @click.stop="" @input="handleInput($event,item,index)" v-model="item.alreadyInCount"/>
                <button @click.stop="clickPlus(item)">+</button>
              </div>
            </li>
          </ul>
        <!--</div>-->
      </div>
    </div>
    <div class="globalFooter">
      <button @click="save">入 库</button>
      <!--<button @click="test">测试</button>-->
    </div>
    <mt-datetime-picker v-model="pickDate" ref="pickDate" type="date" year-format="{value} 年"
                        month-format="{value} 月" date-format="{value} 日" @confirm="getConfirm">
    </mt-datetime-picker>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle'
                         :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType"
                         @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom"
                            :errorInformation="GlobalTroubleNumberError"
                            :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
    <GlobalSuccessInformationCommon v-show="switchGlobalSuccessInformation"
                                    @hideSuccessCurrentCom="getHideSuccessCurrentCom"
                                    :successInformation="successInformation"
                                    :successInformationTitle="successInformationTitle" :successText="successText">
    </GlobalSuccessInformationCommon>
    <!--<router-view></router-view>-->
    <router-view class="routerView"></router-view>
  </div>
</template>

<script>
  // import Toast from 'mint-ui';
  import BScroll from "better-scroll";
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut';
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import GlobalSuccessInformationCommon from "@/components/globalCom/GlobalSuccessInformationCommon";

  export default {
    name: "NewLibrary",
    data() {
      return {
        //成功提示
        switchGlobalSuccessInformation: false,
        successInformation: '',
        successInformationTitle: '',
        successText: '',
        //错误提示
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: '',
        GlobalTroubleNumberErrorTitle: '',
        //入库单详情
        pickDate: '',
        inWarehouseId: '',
        inWarehouseName: '',
        applyRemark: '',
        inDate: this.common.getDate('yyyy-MM-dd'),
        detailEntities: [],
        switchHomeOrdinarySideOut: false,//右侧弹出层显示隐藏
        HomeOrdinarySideOutTitle: '入库仓库',//右侧弹出层标题
        HomeOrdinarySideOutData: [],//右侧弹出层数据
      };
    },
    methods: {
      /* 方法 */
      // test(){
      //   console.log(this.$router);
      //   // this.$router
      // },
      // 初始化
      initData() {
        //初始化入库详情
        let newLibraryJson = this.common.getSessionStorage('newLibraryJson', true, true);
        if (newLibraryJson) {
          Object.assign(this, newLibraryJson);
        }
        //初始化入库备件列表
        let detailEntities = this.common.getSessionStorage('checkSparePartList', true, true);
        if (detailEntities) {
          detailEntities.forEach(function (item, index) {
            if (!item.alreadyInCount) {
              item.alreadyInCount = 1;
            }
            item.readyDelete = false;
          });
          this.detailEntities = detailEntities;
        }
        // //备件列表滚动初始化
        // let sparePartList = this.$refs.sparePartList;
        // new BScroll(sparePartList, {click: true});
      },
      handleInput($event, item, index) {
        let $this = this;
        if (!$event.target.value) {
          $event.target.value = '1';
        }
        item.alreadyInCount = $event.target.value.replace(/[^\d]/g, '');
        if (Number(item.alreadyInCount) == 0) {
          item.alreadyInCount = 1;
        }
        $event.target.value = item.alreadyInCount;
        $this.$set($this.detailEntities, index, item);
      },
      clickMinus(item) {
        if (!item.alreadyInCount) {
          item.alreadyInCount = 1;
        } else if (item.alreadyInCount > 1) {
          item.alreadyInCount--;
        } else {
          item.alreadyInCount = 1;
        }
      },
      clickPlus(item) {
        if (!item.alreadyInCount) {
          item.alreadyInCount = 1;
        } else {
          item.alreadyInCount++;
        }
      },
      sparePartHideDeleteAll(){
        this.detailEntities.forEach(function (item,index) {
          item.readyDelete = false;
        })
      },
      sparePartDelete(item,index){
        this.detailEntities.splice(index, 1);
      },
      sparePartShowDelete($event,item){
        this.sparePartHideDeleteAll();
        item.readyDelete = true;
      },
      sparePartHideDelete($event,item){
        item.readyDelete = false;
      },
      alreadyInCountBlur(item) {
        if (!item.alreadyInCount || item.alreadyInCount < 1) {
          item.alreadyInCount = 1;
        }
      },
      // 时间选择
      timerPicker(event) {
        this.pickDate = this.inDate;
        this.$refs.pickDate.open();
      },
      // 时间确认
      getConfirm(val) {
        this.inDate = typeof val == 'object'?this.common.formatDate(this.pickDate, 'ymd'):this.pickDate;
      },
      // 获取用户所负责仓库
      getWarehouses() {
        let $this = this;
        this.requests.getWarehousesByUser().then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].id,
              sideOutText: DATA[i].text
            });
          }
          $this.switchHomeOrdinarySideOut = true;
        })
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        this.inWarehouseName = val.text;
        this.inWarehouseId = val.sideOutId;
      },
      getHideCurrentCom() {
        this.switchGlobalErrorInformation = false;
      },
      errorMessageShow(title, message) {
        this.switchGlobalErrorInformation = true;
        this.GlobalTroubleNumberError = message;
        this.GlobalTroubleNumberErrorTitle = title;
      },

      getHideSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = false;
        this.common.setSessionStorage('PutStorageRefresh',true);
        // this.$router.go(-1);
        this.goBack();
      },

      showSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = true;
        this.successInformation = '入库成功';
        this.successInformationTitle = '温馨提示';
        this.successText = '确定';
      },

      addLink() {
        let $this = this;
        if (!this.inWarehouseId) {
          $this.errorMessageShow("温馨提示", "请先选择仓库");
          return;
        }
        var newLibraryJson = {
          inWarehouseId: this.inWarehouseId,
          inWarehouseName: this.inWarehouseName,
          applyRemark: this.applyRemark,
          inDate: this.inDate
        };
        var checkSparePartList = this.detailEntities;
        checkSparePartList.forEach(function (item, index) {
          item.isOld = true;
        });
        this.common.setSessionStorage('newLibraryJson', newLibraryJson, true);
        this.common.setSessionStorage('checkSparePartList', checkSparePartList, true);
        this.$router.push({name: 'NewLibrarySelect', query: {warehouseId: this.inWarehouseId}});
      },

      // 保存
      save() {
        let $this = this;
        var detailEntities = $this.detailEntities;
        if (detailEntities.length == 0) {
          $this.errorMessageShow("温馨提示", "请至少选择一个备件");
          return;
        }
        detailEntities.forEach(function (item, index) {
          item.status = 'normal';
        });

        let data = {
          userKey: $this.common.getLocalStorage('userKey'),
          inWarehouseId: $this.inWarehouseId,
          inStockApplyNo: "",
          inStockType: "newIn",
          inStockStatus: "",
          applyId: "",
          inDate: $this.inDate,
          remark: $this.applyRemark,
          detailEntities: $this.detailEntities
        };

        var date = new Date();
        var hms = ' '+('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':'+('0' + date.getSeconds()).slice(-2);
        data.inDate += hms;
        var s = data.inDate.replace(/-/g,"/");
        var newData = new Date(s);
        data.inDate = newData.getTime();

        this.requests.addNewStockInApply(data).then(function (data) {
          if(data.result == 'success'){
            $this.showSuccessCurrentCom();
          }else{
            $this.errorMessageShow('温馨提示',data.message);
          }
        });
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
    },
    destroyed() {
      window.removeEventListener('popstate', this.goBack, false);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";

  #NewLibrary {
    /*width: 100%;*/
    /*!*height: calc(100% - 80px) !important;*!*/
    /*height: 100% !important;*/
    /*overflow-y: auto !important;*/
    /*!*overflow: hidden;*!*/
    /*padding: 20px 20px 100px 20px;*/
    /*!*padding: 20px;*!*/
    /*line-height: 1;*/
    /*box-sizing: border-box;*/

    position: absolute;
    top: 0;
    left: 0;
    overflow-y: auto !important;
    padding: 20px 20px 100px 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background-color: #faf6ec;

    .NewLibrary-main {
      width: 100%;
      min-height: 100%;
      padding: 0 20px 20px;
      background-color: #fff;
      border: solid 1px #dfdcdc;
      border-radius: 18px;
      box-sizing: border-box;
      .main-list {
        width: 100%;
        padding-top: 20px;
        box-sizing: border-box;
        li {
          display: flex;
          /*padding: 32px 0px 26px 20px;*/
          padding-left: 20px;
          font-size: $widthWeb22;
          /*line-height: 74px;*/
          line-height: 80px;
          border-bottom: solid 1px #dfdcdc;
          box-sizing: border-box;
          .inp-txt {
            flex: 1;
            display: inline-block;
            /*height: 72px;*/
            line-height: 80px;
            text-align: right;
            color: #333;
            background: transparent;
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
        .list-textarea {
          display: block;
          padding: 0;
          border: 0;
          & > p {
            font-size: $widthWeb22;
            padding-left: 20px;
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
      .listing-addList {
        float: right;
        /*margin-right: 20px;*/
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
          padding: 14px 24px 14px 16px;
          font-size: $widthWeb18;
          line-height: $widthWeb18;
        }
      }
      .main-add {
        width: 100%;
        /*height: 46px;*/
        text-align: right;
        margin-bottom: 50px;
        button {
          /*width: 152px;*/
          padding: 0 20px;
          height: 46px;
          font-size: $widthWeb18;
          background-color: #eee;
          box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.35);
          border-radius: 23px;
          i{
            display: inline-block;
            width: 25px;
            height: 25px;
            margin-left: 18px;
            background-image: url("../../../../assets/home/add.png");
            background-size: 100% 100%;
          }
        }
      }
      .main-listing {
        width: 100%;
        /*height: calc(100% - 500px);*/
        background-color: #fff;
        padding-left: 10px;
        box-sizing: border-box;
        .listing-title {
          width: 100%;
          /*padding-left: 36px;*/
          font-size: $widthWeb25;
          color: #fd8521;
          line-height: 1;
          box-sizing: border-box;
          margin-bottom: 30px;
        }
        /*.listing-list-box {*/
          /*height: calc(100% - 30px);*/
          /*overflow: hidden;*/
        /*}*/
        .listing-list {
          width: 100%;
          /*padding-left: 16px;*/
          box-sizing: border-box;
          & > li {
            /*padding-left: 18px;*/
            position: relative;
            margin-bottom: 30px;
            box-sizing: border-box;
            &:nth-last-child(1) {
              border: 0;
            }
            p {
              font-size: $widthWeb18;
              color: #333;
              line-height: 1;
            }
            .list-parts-delete{
              position: absolute;
              top: 0;
              left: 0;
              height: 100%;
              width: 100%;
              background: rgba(0,0,0,.5);
              text-align: center;
              padding: 8px;
              opacity: 0;
              transition: .2s linear all;
              pointer-events:none;
              &.show{
                pointer-events:auto;
                opacity: 1;
              }
              img{
                height: 100%;
              }
            }
            .list-parts {
              width: 100%;
              /*display: flex;*/
              margin-top: 20px;
              .parts-img {
                float: left;
                margin-right: 20px;
                width: 70px;
                height: 70px;
                img {
                  /*display: inline-block;*/
                  width: 70px;
                  height: 70px;
                }
              }
              & > ul {
                display: flex;
                float: left;
                width: calc(100% - 90px);
                padding-bottom: 10px;
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
                    font-size: $widthWeb18;
                    color: #333;
                    line-height: 32px;
                    &:nth-last-child(1) {
                      font-size: $widthWeb14;
                      color: #bfbfbf;
                      line-height: 28px;
                    }
                  }
                }
              }
            }
            .list-num {
              margin-top: 20px;
              width: 100%;
              height: 34px;
              font-size: 0;
              text-align: right;
              button {
                padding: 0;
                width: 42px;
                height: 34px;
                color: #666;
                font-size: $widthWeb22;
                text-align: center;
                line-height: 34px;
                background-color: #fff;
                border: solid 1px #eee;
                box-sizing: border-box;
              }
              input {
                width: 77px;
                height: 34px;
                font-size: $widthWeb22;
                color: #fd8521;
                text-align: center;
                background-color: #eee;
              }
            }
          }
        }
      }
    }
    /*<!--.footer {-->*/
      /*<!--position: fixed;-->*/
      /*<!--bottom: 0;-->*/
      /*<!--left: 0;-->*/
      /*<!--width: 100%;-->*/
      /*<!--height: 80px;-->*/
      /*<!--text-align: right;-->*/
      /*<!--background-color: #eeeeee;-->*/
      /*<!--z-index: 300;-->*/
      /*<!--button {-->*/
        /*<!--width: 220px;-->*/
        /*<!--height: 80px;-->*/
        /*<!--font-size: $widthWeb25;-->*/
        /*<!--color: #fff;-->*/
        /*<!--line-height: 80px;-->*/
        /*<!--background-color: #fc6806;-->*/
      /*<!--}-->*/
    /*<!--}-->*/
    .routerView {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 200;
      width: 100%;
      height: calc(100% - 80px);
      background-color: #faf6ec;
    }
  }
</style>
