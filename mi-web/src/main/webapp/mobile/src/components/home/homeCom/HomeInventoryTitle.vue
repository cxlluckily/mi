<template>
  <div id='HomeInventoryTitle'>
    <div class="HomeInventoryTitle-main">
      <div class="HomeInventoryTitle-title" v-if="inventoryRouterPath">
        <router-link :to="inventoryRouterPath" tag="button">{{inventoryTitle}}</router-link>
      </div>
      <div class="HomeInventoryTitle-search" v-show="swtichInventorySearch">
        <button @click="switchSearchBox">查询</button>
      </div>
    </div>
    <!-- 遮罩层 -->
    <div class="HomeInventoryTitle-mask" @click="switchHomeSearch = false" v-show="switchHomeSearch"></div>
    <!-- 搜索框 -->
    <transition name="slide-fade" v-on:enter="enter">
      <div class="HomeInventoryTitle-searchBox" v-show="switchHomeSearch">
        <ul class="searchBox-list">
          <li>
            <p class="list-title">仓库</p>
            <div class="list-options">
              <ul>
                <li v-for="warehouseIdItem in warehouseIdData" :key="warehouseIdItem.id" @click="currentWarehouse = warehouseIdItem.id" class='list-line3'>
                  <p :class="{'options-getText':currentWarehouse==warehouseIdItem.id}">{{warehouseIdItem.text}}</p>
                </li>
              </ul>
            </div>
          </li>
          <li>
            <p class="list-title">{{inventoryType == 'ck'?'出库性质':'入库性质'}}</p>
            <div class="list-options">
              <ul>
                <li v-for="inStockTypeItem in inStockTypeData" :key="inStockTypeItem.inStockType" @click="currentInStockType = inStockTypeItem.inStockType">
                  <p :class="{'options-getText':currentInStockType==inStockTypeItem.inStockType}">
                    {{inStockTypeItem.title}}</p>
                </li>
              </ul>
            </div>
          </li>
          <li v-if="swtichStatus != 'JL'">
            <p class="list-title">状态</p>
            <div class="list-options">
              <ul>
                <li v-for="inStockStatusItem in inStockStatusData" :key="inStockStatusItem.status" @click="currentInStockStatus = inStockStatusItem.status" class='list-line3'>
                  <p :class="{'options-getText':currentInStockStatus==inStockStatusItem.status}">
                    {{inStockStatusItem.title}}</p>
                </li>
              </ul>
            </div>
          </li>
          <li>
            <p class="list-title">时间</p>
            <div class="list-options">
              <div class="options-timer">
                <input type="text" data-timer="timerStart" @click="timerPicker($event,timerStart)" class="timer" placeholder="请选择起始时间" readonly="readonly" v-model="timerStart" />
                <!--<div class="timer" :class="{timerNull:!timerStart}" data-timer="timerStart" @click="timerPicker($event)">{{timerStart | initDate('ymd') || '请选择起始时间'}}</div>-->
                <div class="timer-text">至</div>
                <input type="text" data-timer="timerEnd" @click="timerPicker($event,timerEnd)" class="timer" placeholder="请选择终止时间" readonly="readonly" v-model="timerEnd" />
                <!--<div class="timer" :class="{timerNull:!timerEnd}" data-timer="timerEnd" @click="timerPicker($event)">{{timerEnd | initDate('ymd') || '请选择终止时间'}}</div>-->
              </div>
            </div>
          </li>
        </ul>
        <div class="searchBox-btn">
          <button class="btn-reset" @click="resetText">重置</button>
          <button class="btn-determine" @click="determineSubmit">确定</button>
        </div>
      </div>
    </transition>
    <mt-datetime-picker v-model="pickerVisible" ref="picker" type="date" year-format="{value} 年" month-format="{value} 月" date-format="{value} 日" @confirm="getConfirm">
    </mt-datetime-picker>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle">
    </GlobalErrorInformation>
  </div>
</template>

<script>
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  export default {
    name: "HomeInventoryTitle",
    data() {
      return {
        pickerVisible: "",
        switchHomeSearch: false,
        currentInStockType: "all",
        currentInStockStatus: "all",
        currentWarehouse: "",
        currentWarehouseBackup: "",
        inStockTypeData: [],
        inStockStatusData: [],
        warehouseIdData: [],
        stateTimer: "",
        timerStart: this.common.getDate('yyyy-MM-dd', -30),
        timerEnd: this.common.getDate('yyyy-MM-dd'),
        switchGlobalErrorInformation: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: ""
      };
    },
    props: ["inventoryTitle", "inventoryRouterPath", "swtichInventorySearch", 'inventoryType', 'swtichStatus'],
    methods: {
      /* 方法 */
      switchSearchBox() {
        this.switchHomeSearch = true;
      },
      resetText() {
        this.currentInStockType = "all";
        this.currentInStockStatus = "all";
        // if(this.inventoryType == 'ck'){
        //   this.currentInStockStatus = "toBeOut";
        // }else{
        //   this.currentInStockStatus = "toBeIn";
        // }
        this.currentWarehouse = this.currentWarehouseBackup;
        this.timerStart = this.common.getDate('yyyy-MM-dd', -30);
        this.timerEnd = this.common.getDate('yyyy-MM-dd');
      },
      determineSubmit() {
        const PROMPOBJ = {
          beginTime: this.initTimer(this.timerStart),
          endTime: this.initTimer(this.timerEnd),
          inStockStatus: this.currentInStockStatus,
          inStockType: this.currentInStockType,
          warehouseId: this.currentWarehouse
        };
        this.$emit('timerDeta', PROMPOBJ);
        this.switchHomeSearch = false;
      },
      timerPicker(event, value) {
        this.stateTimer = $(event.currentTarget).attr("data-timer");
        this.pickerVisible = this[this.stateTimer];
        this.$refs.picker.open();
      },
      getConfirm(val) {
        var dateVal = typeof val == 'object' ? this.common.getDate('yyyy-MM-dd', 0, val) : val;
        if (this.stateTimer == "timerStart") {
          if (dateVal > this.timerEnd) {
            this.switchGlobalErrorInformation = true;
            this.EquipmentRepairErrorTitle = "错误信息";
            this.EquipmentRepairError = "起始时间不得大于终止时间!";
            return;
          }
          this.timerStart = dateVal;
        } else {
          if (this.timerStart > dateVal) {
            this.switchGlobalErrorInformation = true;
            this.EquipmentRepairErrorTitle = "错误信息";
            this.EquipmentRepairError = "起始时间不得大于终止时间!";
            return;
          }
          this.timerEnd = dateVal;
        }
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      // 仓库初始化数据
      initWarehouseData() {
        this.api.warehouseTreeDataAll().then(data => {
          this.warehouseIdData = data.data;
          this.warehouseIdData.unshift({
            id: '0',
            text: '全部'
          });
          // this.currentWarehouse = data.data[0].id;
          // this.currentWarehouseBackup = data.data[0].id;
          this.currentWarehouse = '0';
          this.currentWarehouseBackup = '0';
        });
      },
      // 默认选中的时间
      initTimer(strTimer) {
        if (strTimer == '') {
          return '';
        }
        var date = new Date(strTimer);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? "0" + m : m;
        var d = date.getDate();
        d = d < 10 ? "0" + d : d;
        return y + "-" + m + "-" + d;
      },
      // 过渡动画( 过渡 )
      enter(el, done) {
        el.offsetWidth;
        el.style.transition = "transform 0.3s ease-in-out";
        done();
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
      this.initWarehouseData();
      let strTimer = new Date().getTime();
      this.pickerVisible = this.initTimer(strTimer);
      if (this.inventoryType == 'ck') {
        this.inStockTypeData = [];
        this.inStockStatusData = [];
        // this.currentInStockStatus = 'toBeOut';
        this.inStockTypeData = [{
            title: "全部",
            inStockType: "all"
          },
          {
            title: "领用出库",
            inStockType: "useOut"
          },
          {
            title: "调拨出库",
            inStockType: "transferOut"
          },
          {
            title: "借用出库",
            inStockType: "borrowOut"
          },
          {
            title: "返还出库",
            inStockType: "returnOut"
          }
        ];
        this.inStockStatusData = [{
            title: "全部",
            status: "all"
          },
          {
            title: "待出库",
            status: "toBeOut"
          },
          {
            title: "已出库",
            status: "alreadyOut"
          }
        ];
      } else {
        // this.currentInStockStatus = 'toBeIn';
        this.inStockTypeData = [{
            title: "全部",
            inStockType: "all"
          },
          {
            title: "新品入库",
            inStockType: "newIn"
          },
          {
            title: "归还入库",
            inStockType: "useIn"
          },
          {
            title: "调拨入库",
            inStockType: "transferIn"
          },
          {
            title: "返还入库",
            inStockType: "returnIn"
          },
          {
            title: "借用入库",
            inStockType: "borrowIn"
          }
        ];
        this.inStockStatusData = [{
            title: "全部",
            status: "all"
          },
          {
            title: "待入库",
            status: "toBeIn"
          },
          {
            title: "已入库",
            // status: "stored"
            status: "alreadyIn"
          }
        ];
      }
    },
    watch: {
      /* 监听 */
      inventoryType: function(val) {
        if (this.inventoryType == 'ck') {
          // this.currentInStockStatus = 'toBeOut';
          this.inStockTypeData = [];
          this.inStockStatusData = [];
          this.inStockTypeData = [{
              title: "全部",
              inStockType: "all"
            },
            {
              title: "领用出库",
              inStockType: "useOut"
            },
            {
              title: "调拨出库",
              inStockType: "transferOut"
            },
            {
              title: "借用出库",
              inStockType: "borrowOut"
            },
            {
              title: "返还出库",
              inStockType: "returnOut"
            }
          ];
          this.inStockStatusData = [{
              title: "全部",
              status: "all"
            },
            {
              title: "待出库",
              status: "toBeOut"
            },
            {
              title: "已出库",
              status: "alreadyOut"
            }
          ];
        } else {
          // this.currentInStockStatus = 'toBeIn';
          this.inStockTypeData = [{
              title: "全部",
              inStockType: "all"
            },
            {
              title: "新品入库",
              inStockType: "newIn"
            },
            {
              title: "归还入库",
              inStockType: "useIn"
            },
            {
              title: "调拨入库",
              inStockType: "transferIn"
            },
            {
              title: "返还入库",
              inStockType: "returnIn"
            },
            {
              title: "借用入库",
              inStockType: "borrowIn"
            }
          ];
          this.inStockStatusData = [{
              title: "全部",
              status: "all"
            },
            {
              title: "待入库",
              status: "toBeIn"
            },
            {
              title: "已入库",
              // status: "stored"
              status: "alreadyIn"
            }
          ];
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #HomeInventoryTitle {
    width: 100%;
    padding-right: 20px;
    height: 66px;
    box-sizing: border-box;
    .HomeInventoryTitle-main {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 100%;
      height: 100%;
      .HomeInventoryTitle-title button {
        margin-right: 20px;
      }
      button {
        padding: 10px 20px;
        font-size: $widthWeb16;
        line-height: $widthWeb16;
        color: #fff;
        background-color: #fd8521;
        border-radius: 6px;
      }
    }
    .HomeInventoryTitle-mask {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 200;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
    }
    .HomeInventoryTitle-searchBox {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 200;
      width: 100%;
      background-color: #eee;
      .searchBox-list {
        &>li {
          width: 100%;
          .list-title {
            padding-left: 20px;
            width: 100%;
            height: 62px;
            font-size: .40rem;
            line-height: 62px;
            box-sizing: border-box;
          }
          .list-options {
            &>ul {
              display: flex;
              flex-wrap: wrap;
              width: 100%;
              padding: 12px 6px;
              box-sizing: border-box;
              background-color: #fff;
              .list-line3 {
                width: 33.3333333%;
                p {
                  width: 188px;
                  font-size: .34rem;
                }
              }
              li {
                width: 25%;
                height: 50px;
                line-height: 50px;
                text-align: center;
                margin: 10px 0;
                p {
                  display: inline-block;
                  width: 140px; // height: 100%;
                  font-size: .34rem;
                  color: #333;
                  line-height: 50px;
                  border: 1px solid #dfdcdc;
                  background-color: #eee;
                  border-radius: 16px;
                  box-sizing: border-box;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                }
                .options-getText {
                  background-color: #fbe9da;
                  border: solid 1px #f9caa6;
                }
              }
            }
            .options-timer {
              display: flex;
              justify-content: center;
              align-items: center;
              flex-wrap: wrap;
              width: 100%;
              padding: 25px 6px;
              box-sizing: border-box;
              background-color: #fff;
              .timer {
                width: 280px;
                height: 56px;
                font-size: .36rem;
                color: #333;
                text-align: center;
                line-height: 56px;
                border: 1px solid #dfdcdc;
                border-radius: 16px;
                box-sizing: border-box;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }
              .timerNull {
                color: #bfbfbf;
              }
              .timer-text {
                margin: 0 34px;
                font-size: .36rem;
              }
            }
          }
        }
      }
      .searchBox-btn {
        width: 100%;
        padding: 38px 0 44px;
        font-size: 0;
        text-align: center;
        box-sizing: border-box;
        button {
          width: 134px;
          height: 46px;
          color: #fff;
          font-size: .36rem;
          background-color: #b7b7b7;
          border-radius: 16px;
        }
        .btn-determine {
          margin-left: 46px;
          background-color: #fd8521;
        }
      }
    }
    .slide-fade-enter,
    .slide-fade-leave-to {
      transform: translateY(-100%);
    }
  }
</style>
