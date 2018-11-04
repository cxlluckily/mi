<template>
 <div id='GlobalSuccessInformation'>
   <div class="GlobalSuccessInformation-main">
    <div class="main-title">
      {{successInformationTitle}}
    </div>
    <div class="main-content">
      <div class="content-img"></div>
      <div class="conttent-information">
        {{successInformation}}
      </div>
    </div>
    <div class="main-btn">
      <button @click="switchCurrentCom">{{successText}}</button>
      <router-link v-show="routerLinkText != ''" to="/RepairDisHistory/bx" tag="button">{{routerLinkText}}</router-link>
    </div>
   </div>
 </div>
</template>

<script>
export default {
  name: "GlobalSuccessInformation",
  data() {
    return {};
  },
  props: ["successInformationTitle", "successInformation","successText","routerLinkText"],
  methods: {
    /* 方法 */
    switchCurrentCom() {
      this.$emit("hideSuccessCurrentCom", false);
    }
  },
  components: {
    /* 复用组件名称 */
  },
  mounted: function() {
    /* 初始化数据 */
  },
  watch: {
    /* 监听 */
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#GlobalSuccessInformation {
  z-index: 500;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  .GlobalSuccessInformation-main {
    position: absolute;
    top: 24%;
    left: 50%;
    transform: translateX(-50%);
    background-color: #faf7ed;
    border-radius: 14px;
    .main-title {
      width: 542px;
      height: 78px;
      font-size: $widthWeb30;
      color: #f15510;
      line-height: 78px;
      text-align: center;
      background-color: #fce5be;
      border-top-left-radius: 14px;
      border-top-right-radius: 14px;
    }
    .main-content {
      width: 100%;
      padding: 28px 112px 40px;
      box-sizing: border-box;
      text-align: center;
      .content-img {
        margin-bottom: 30px;
        display: inline-block;
        width: 156px;
        height: 144px;
        background-image: url("../../assets/home/ok.png");
        background-size: 100% 100%;
      }
      .conttent-information {
        color: #333;
        font-size: $widthWeb25;
        line-height: 36px;
      }
    }
    .main-btn {
      margin-bottom: 60px;
      width: 100%;
      text-align: center;
      button {
        padding: 9px 37px;
        color: #fff;
        font-size: $widthWeb22;
        line-height: $widthWeb22;
        background-color: #fd8521;
      }
    }
  }
}
</style>
