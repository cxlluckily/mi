<template>
  <div id='GlobalListMask' v-show="switchListMask">
    <div class="GlobalListMask-main">
      <div class="main-title">选择设备编号</div>
      <ul class="main-list">
        <li v-for="itemList in listMaskData" :key="itemList.listMaskId">
          <label @click="radioId = itemList.listMaskId" :for="itemList.listMaskId" :class="{'list-label' : radioId == itemList.listMaskId}">
              <input type="radio" :id="itemList.listMaskId" name="repair" :value="itemList.listMaskText+ ',' +itemList.listMaskId" v-model="radioName">
              <span>{{itemList.listMaskText}}</span>
              <i :class="{'list-icon': itemList.listMaskQrCode != ''}"></i>
              </label>
        </li>
        <li v-if="listMaskData.length == '0'" class="errorMessage">暂无数据!</li>
      </ul>
      <div class="main-btn">
        <button class="btn-close" @click="colse">取 消</button>
        <button class="btn-determine" @click="determine">确 定</button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "GlobalListMask",
    data() {
      return {
        radioName: "",
        GlobalListMask: false,
        radioId: ""
      };
    },
    props: ["switchListMask", "listMaskData"],
    methods: {
      /* 方法 */
      colse() {
        this.$emit("switchHomeListMask", this.GlobalListMask);
      },
      determine() {
        this.$emit("switchHomeListMask", this.GlobalListMask);
        this.$emit("HomeListMaskData", this.radioName);
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
  #GlobalListMask {
    z-index: 200;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    .GlobalListMask-main {
      position: absolute;
      top: 156px;
      left: 0;
      padding-top: 22px;
      width: 100%;
      background-color: #eee;
      .main-title {
        padding-left: 22px;
        font-size: $widthWeb22;
        line-height: 48px;
      }
      .main-list {
        padding: 36px 38px 66px;
        display: flex;
        flex-wrap: wrap;
        background-color: #fff;
        li {
          width: 25%;
          text-align: center;
          margin-bottom: 40px;
          &:nth-last-child(-n + 4) {
            margin: 0;
          }
          label {
            position: relative;
            display: inline-block;
            width: 140px;
            height: 50px;
            text-align: center;
            line-height: 50px;
            background-color: #eeeeee;
            border: solid 1px #dadada;
            border-radius: 22px;
            input {
              display: none;
            }
            span {
              font-size: $widthWeb18;
              color: #333;
            }
            .list-icon {
              position: absolute;
              bottom: 6px;
              right: 4px;
              width: 19px;
              height: 14px;
              background-image: url("../../assets/home/binding.png");
              background-size: 100% 100%;
            }
          }
          .list-label {
            background-color: #fbe9da;
            border: solid 1px #f9caa6;
          }
        }
        .errorMessage {
          text-align: center;
          font-size: $widthWeb18;
          color: #f15510;
        }
      }
      .main-btn {
        width: 100%;
        padding: 36px 0 42px;
        font-size: 0;
        text-align: center;
        background-color: #eee;
        button {
          padding: 14px 44px;
          color: #fff;
          font-size: $widthWeb18;
          line-height: $widthWeb18;
          background-color: #b7b7b7;
          border-radius: 26px;
        }
        .btn-determine {
          margin-left: 46px;
          background-color: #fd8521;
        }
      }
    }
  }
</style>
