<template>
  <div id='HomeManegerSideOut' v-show="switchCurrentCom">
    <div class="HomeOrdinarySideOut-main">
      <div class="main-left" @click="closeDeviuceType" v-show="switchHomeOrdinarySideOut"></div>
      <transition name="slide-fade" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:after-leave="afterLeave">
        <div class="main-right clearfix" v-show="switchHomeOrdinarySideOut">
          <p class="right-title">{{ordinarySideOutTitle}}</p>
          <div class="right-type" ref='typeScroll'>
            <ul>
              <li v-for="itemType in ordinarySideOutData" :key="itemType.sparePartTypeId" :data-sideOutId="itemType.sparePartTypeId" @click="selectedType($event)">
                <p>{{itemType.categoryNameKongGe}}</p>
              </li>
            </ul>
            <p class="errorMessage" v-if="ordinarySideOutData.length == 0">暂无数据!</p>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  export default {
    name: "HomeManegerSideOut",
    data() {
      return {
        switchHomeDeviceType: false, // 告诉父组件, 关闭当前组件
        switchCurrentCom: false, // 动画结束之后关闭页面
        currentTypeObj: {} // 储存点击的值和ID
      };
    },
    props: [
      "switchHomeOrdinarySideOut",
      "ordinarySideOutTitle",
      "ordinarySideOutData"
    ],
    methods: {
      /* 方法 */
      selectedType(event) {
        const THISDOM = $(event.currentTarget);
        this.currentTypeObj.text = THISDOM.text().trim();
        this.currentTypeObj.sideOutId = THISDOM.attr("data-sideOutId");
        this.currentTypeObj.title = this.ordinarySideOutTitle;
        this.$emit("HomeSelectedType", this.currentTypeObj);
        this.$emit("switchHomeOrdinarySideOut", this.switchHomeDeviceType);
      },
      // 点击黑色区域关闭此组件
      closeDeviuceType() {
        this.$emit("switchHomeOrdinarySideOut", this.switchHomeDeviceType);
      },
      // 过渡动画( 开始 )
      beforeEnter(el) {
        this.switchCurrentCom = true;
      },
      // 过渡动画( 过渡 )
      enter(el, done) {
        el.offsetWidth;
        el.style.transition = "transform 0.2s ease-in-out";
        done();
      },
      // 过渡动画( 过渡 )
      afterLeave: function(el) {
        this.switchCurrentCom = false;
      }
    },
    updated: function() {
        let typeScroll = this.$refs.typeScroll;
        new BScroll(typeScroll, {
          click: true
        });
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #HomeManegerSideOut {
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 100;
    overflow: hidden;
    .HomeOrdinarySideOut-main {
      width: 100%;
      height: 100%;
      .main-left {
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
      }
      .slide-fade-enter,
      .slide-fade-leave-to {
        transform: translateX(100%);
      }
      .main-right {
        position: absolute;
        top: 0;
        right: 0;
        width: 350px;
        height: 100%;
        background-color: #fff;
        .right-title {
          padding-left: 16px;
          width: 100%;
          height: 58px;
          line-height: 58px;
          font-size: $widthWeb18;
          background-color: #e2e2e2;
          box-sizing: border-box;
        }
        .right-type {
          width: 100%;
          height: calc(100% - 58px);
          padding-left: 20px;
          box-sizing: border-box;
          overflow: hidden;
          .errorMessage {
            margin-right: 20px;
            text-align: center;
            font-size: $widthWeb18;
            color: #f15510;
            line-height: 100px;
          }
          li {
            padding-left: 42px;
            height: 66px;
            line-height: 66px;
            color: #999;
            border-bottom: 1px solid #dfdcdc;
            box-sizing: border-box;
            p {
              font-size: $widthWeb16;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
          }
        }
      }
    }
  }
</style>
