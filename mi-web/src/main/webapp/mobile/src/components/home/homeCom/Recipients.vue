<template>
  <div id='Recipients' v-show="switchCurrentCom">
    <div class="Recipients-main">
    </div>
    <!-- 遮罩层 -->
    <!-- switchHomeSearchMask -->
    <div class="Recipients-mask" @click="closeMask" v-show="switchHomeSearchMask"></div>
    <!-- 搜索框 -->
    <transition name="slide-fade" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:after-leave="afterLeave">
      <div class="Recipients-searchBox" v-show="switchHomesearchBox">
        <ul class="searchBox-list">
          <li>
            <p class="list-title">单号</p>
            <div class="list-options">
              <div class="options-timer">
                <input type="text" class="timer input-block" placeholder="请输入维修单号" v-model="applyNO" />
              </div>
            </div>
          </li>
          <li v-if="switchHomeSearchApplyType">
            <p class="list-title">性质</p>
            <div class="list-options">
              <ul>
                <li v-for="inStockTypeItem in applyTypeData" :key="inStockTypeItem.inStockType" @click="currentApplyType = inStockTypeItem.inStockType">
                  <p :class="{'options-getText':currentApplyType==inStockTypeItem.inStockType}">
                    {{inStockTypeItem.title}}</p>
                </li>
              </ul>
            </div>
          </li>
          <li>
            <p class="list-title">状态</p>
            <div class="list-options">
              <ul>
                <li v-for="inStockTypeItem in statusData" :key="inStockTypeItem.inStockType" @click="currentInStockType = inStockTypeItem.inStockType">
                  <p :class="{'options-getText':currentInStockType==inStockTypeItem.inStockType}">{{inStockTypeItem.title}}</p>
                </li>
              </ul>
            </div>
          </li>
          <li>
            <p class="list-title">时间</p>
            <div class="list-options">
              <div class="options-timer">
                <input type="text" data-timer="timerStart" @click="timerPicker($event,timerStart)" class="timer" placeholder="请选择起始时间" readonly="readonly" v-model="timerStart" />
                <div class="timer-text">至</div>
                <input type="text" data-timer="timerEnd" @click="timerPicker($event,timerEnd)" class="timer" placeholder="请选择终止时间" readonly="readonly" v-model="timerEnd" />
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
    name: "Recipients",
    data() {
      return {
        pickerVisible: "",
        switchHomeSearchMask: false,
        switchHomesearchBox: false,
        switchCurrentCom: false,
        stateTimer: "",
        applyNO: "",
        timerStart: this.common.getDate("yyyy-MM-dd", -60),
        timerEnd: this.common.getDate("yyyy-MM-dd"),
        switchGlobalErrorInformation: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        currentInStockType: "all",
        currentApplyType: "all",
        statusData: [{
            title: "全部",
            inStockType: "all"
          },
          {
            title: "待审核",
            inStockType: "toBeReview"
          },
          {
            title: "审核通过",
            inStockType: "reviewPass"
          },
          {
            title: "审核不通过",
            inStockType: "reviewNoPass"
          },
          {
            title: "待领取",
            inStockType: "toBeOut"
          },
          {
            title: "待归还",
            inStockType: "toBeIn"
          },
          {
            title: "已完成",
            inStockType: "over"
          }
        ],
        applyTypeData: [{
            title: "全部",
            inStockType: "all"
          },
          {
            title: "领用申请",
            inStockType: "use"
          },
          {
            title: "调拨申请",
            inStockType: "transfer"
          },
          {
            title: "返还申请",
            inStockType: "return"
          }
        ]
      };
    },
    props: ["switchHomeSearch", "switchHomeSearchApplyType", 'switchStatus'],
    methods: {
      /* 方法 */
      // 数据重置
      resetText() {
        this.applyNO = "";
        if(this.switchHomeSearchApplyType){
          this.currentInStockType = 'toBeReview';
        }else{
          this.currentInStockType = 'all';
        }
        this.currentApplyType = "all";
        this.timerStart = this.common.getDate("yyyy-MM-dd", -60);
        this.timerEnd = this.common.getDate("yyyy-MM-dd");
      },
      // 确定按钮
      determineSubmit() {
        const PROMPOBJ = {
          troubleNum: this.applyNO,
          start_time: this.initTimer(this.timerStart) + " ",
          end_time: this.initTimer(this.timerEnd) + " ",
          applyType: this.currentApplyType,
          status: this.currentInStockType
        };
        this.$emit("SearchData", PROMPOBJ);
        this.$emit("switchSearchCom", false);
        this.switchHomesearchBox = false;
      },
      // 时间选择打开
      timerPicker(event, value) {
        this.stateTimer = $(event.currentTarget).attr("data-timer");
        this.pickerVisible = this[this.stateTimer];
        this.$refs.picker.open();
      },
      // 时间选择确定
      getConfirm(val) {
        var dateVal =
          typeof val == "object" ?
          this.common.getDate("yyyy-MM-dd", 0, val) :
          val;
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
      // 关闭错误提示
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      // 默认选中的时间
      initTimer(strTimer) {
        if (strTimer == "") {
          return "";
        }
        var date = new Date(strTimer);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? "0" + m : m;
        var d = date.getDate();
        d = d < 10 ? "0" + d : d;
        return y + "-" + m + "-" + d;
      },
      // 过渡动画( 开始 )
      beforeEnter(el) {
        this.switchCurrentCom = true;
      },
      // 过渡动画( 过渡 )
      enter(el, done) {
        el.offsetWidth;
        el.style.transition = "transform 0.3s ease-in-out";
        done();
      },
      // 过渡动画( 过渡 )
      afterLeave: function(el) {
        this.switchCurrentCom = false;
      },
      closeMask() {
        this.switchHomeSearchMask = false;
        this.switchHomesearchBox = false;
        this.$emit("switchSearchCom", false);
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
      if(this.switchHomeSearchApplyType){
        this.currentInStockType = 'toBeReview';
      }
      let strTimer = new Date().getTime();
      this.pickerVisible = this.initTimer(strTimer);
      if (this.switchStatus) {
        this.statusData = [];
        this.statusData = [{
            title: "全部",
            inStockType: "all"
          },
          {
            title: "审核通过",
            inStockType: "reviewPass"
          },
          {
            title: "审核不通过",
            inStockType: "reviewNoPass"
          },
          {
            title: "待领取",
            inStockType: "toBeOut"
          },
          {
            title: "待归还",
            inStockType: "toBeIn"
          },
          {
            title: "已完成",
            inStockType: "over"
          }
        ]
      }
    },
    watch: {
      /* 监听 */
      switchHomeSearch: function(val) {
        this.switchHomeSearchMask = val;
        this.switchHomesearchBox = val;
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #Recipients {
    width: 100%;
    padding-right: 20px;
    height: 66px;
    box-sizing: border-box;
    .Recipients-main {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 100%;
      height: 100%;
      .Recipients-title button {
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
    .Recipients-mask {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 200;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
    }
    .Recipients-searchBox {
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
            font-size: $widthWeb22;
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
              li {
                width: 25%;
                height: 50px;
                line-height: 50px;
                text-align: center;
                margin: 10px 0;
                p {
                  display: inline-block;
                  width: 140px;
                  height: 100%;
                  font-size: $widthWeb18;
                  color: #333;
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
                font-size: $widthWeb18;
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
                font-size: $widthWeb18;
              }
              .input-block {
                width: 100% !important;
                margin: 0 30px;
              }
              .input-block::placeholder {
                color: #bfbfbf;
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
          font-size: $widthWeb18;
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
    } // 日历样式
    // .mint-datetime-picker {
    //   height: 390px;
    //   .picker-toolbar {
    //     height: 50px;
    //     .mint-datetime-action {
    //       height: 100%;
    //       font-size: $widthWeb22;
    //       color: #fd8521;
    //       line-height: 50px;
    //       &:nth-child(1) {
    //         color: #bfbfbf;
    //       }
    //     }
    //   }
    //   .picker-items {
    //     .picker-item {
    //       font-size: $widthWeb22;
    //     }
    //   }
    // }
  }
</style>
