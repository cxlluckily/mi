<template>
  <div id='HomeInventoryDetails'>
    <div class="HomeInventoryDetails-swipe">
      <mt-swipe :auto="3000" v-if="inventoryDetailsImages.length != 0">
        <mt-swipe-item v-for="(itemSwipe, index) in inventoryDetailsImages" :key="index">
          <img :src="itemSwipe" alt="现场图片">
        </mt-swipe-item>
      </mt-swipe>
      <mt-swipe :auto="3000" v-else>
        <mt-swipe-item v-if="inventoryDetailsImages.length == 0">
          <img src="../../../assets/home/banner.png" alt="现场图片">
        </mt-swipe-item>
      </mt-swipe>
    </div>
    <ul class="HomeInventoryDetails-main" v-for="(itemMain, index) in inventoryDetailsData" :key="index">
      <li>
        <div class="list-title">
          <i></i>
          <p>{{itemMain.listName}}</p>
        </div>
        <ul class="list-siteDescription">
          <li v-for="(itemList, index) in itemMain.listArr" :key="index">
            <p class="siteDescription-site">{{itemList.InventoryDetailsName || '暂无!'}}</p>
            <p class="siteDescription-instructions">{{itemList.InventoryDetailsVice}}</p>
          </li>
        </ul>
        <div class="list-details">
          <ul>
            <li v-for="(itemDetails,index) in itemMain.listDetailsArr" :key="index">
              <p>{{itemDetails.InventoryDetailsVice}}:</p>
              <p>{{itemDetails.InventoryDetailsName || '暂无!'}}</p>
            </li>
          </ul>
        </div>
      </li>
    </ul>
    <div class="globalFooter">
      <router-link to="/MySpareParts" tag="button">返 回</router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HomeInventoryDetails",
    data() {
      return {
        cloningInventoryImg: []
      };
    },
    props: ["inventoryDetailsData", "inventoryDetailsImages"],
    methods: {
      /* 方法 */
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
  #HomeInventoryDetails {
    width: 100%;
    height: 100%;
    padding-top: 20px;
    box-sizing: border-box;
    .HomeInventoryDetails-swipe {
      width: 100%;
      height: 289px;
      padding: 0 20px;
      box-sizing: border-box;
      img {
        width: 100%;
        height: 289px;
        border-radius: 18px;
      }
    }
    .HomeInventoryDetails-main {
      margin-bottom: 80px;
      padding: 20px;
      background-color: #f6f7eb;
      width: 100%;
      box-sizing: border-box;
      &>li {
        position: relative;
        padding: 8px 30px 25px;
        width: 100%;
        height: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 15px;
        .list-title {
          display: flex;
          align-items: center;
          margin-bottom: 18px;
          width: 100%;
          height: 74px;
          font-size: 0;
          border-bottom: 1px solid #dfdcdc;
          i {
            display: inline-block;
            margin-right: 10px;
            width: 30px;
            height: 30px;
            background-image: url("../../../assets/home/detail.png");
            background-size: 100% 100%;
          }
          p {
            color: #333;
            font-size: $widthWeb25;
            font-weight: 700;
          }
        }
        .list-siteDescription {
          display: flex;
          // margin-bottom: 20px;
          padding-right: 4%;
          font-size: 0;
          box-sizing: border-box;
          li {
            flex: 1;
            text-align: center;
            border-right: 1px solid #dfdcdc;
            box-sizing: border-box;
            &:nth-last-child(1) {
              border: 0;
            }
            .siteDescription-site {
              color: #333;
              font-size: $widthWeb18;
              font-weight: 700;
              line-height: 38px;
            }
            .siteDescription-instructions {
              color: #999;
              font-size: 16px;
              line-height: 28px;
            }
          }
        }
        .list-details {
          ul>li {
            margin-bottom: 10px;
            padding-right: 36px;
            display: flex;
            font-size: $widthWeb18;
            line-height: 42px;
            color: #333;
            &>p:nth-child(1) {
              flex-shrink: 0;
              padding: 12px 0 8px 26px;
              margin-right: 30px;
              width: 155px;
              height: 42px;
              font-size: $widthWeb18;
              line-height: $widthWeb18;
              background-color: #eeeeee;
              box-sizing: border-box;
            }
          }
        }
      }
    }
  }
</style>
