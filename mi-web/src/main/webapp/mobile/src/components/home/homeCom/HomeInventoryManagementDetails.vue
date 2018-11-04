<template>
  <div id='HomeInventoryManagementDetails'>
    <div class="HomeInventoryManagementDetails-swipe">
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
    <div class="table-content">
      <ul class="HomeInventoryManagementDetails-main" v-for="(itemMain, index) in inventoryDetailsData" :key="index">
        <li>
          <div class="list-title">
            <i></i>
            <p>{{itemMain.listName}}</p>
          </div>
          <!--<ul class="list-siteDescription">-->
            <!--<li v-for="(itemList, index) in itemMain.listArr" :key="index">-->
              <!--<p class="siteDescription-site">{{itemList.InventoryDetailsName || '暂无!'}}</p>-->
              <!--<p class="siteDescription-instructions">{{itemList.InventoryDetailsVice}}</p>-->
            <!--</li>-->
          <!--</ul>-->
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
      <div class="list-details-table">
        <ul class="list-details-ul">
          <li v-for="(itemList, index) in inventoryTablesData" :key="index">
            <div class="table_flex">
              <p class="accound">{{itemList.account}}个</p>
              <p class="table-box">
                <span class="accound_text">数量</span>
              </p>
            </div>
            <div class="table_flex">
              <p class="accound">{{itemList.status=='normal'?'好':'坏'}}</p>
              <p class="table-box">
                <span class="accound_text">状态</span>
              </p>
            </div>
            <div class="table_flex">
              <p class="accound">{{itemList.houseNo?(itemList.houseNo+' '+itemList.shelfNumber):'未放入货架'}}</p>
              <p class="table-box">
                <span class="accound_text">存放位置</span>
              </p>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="globalFooter">
      <router-link to="/InventoryManagement" tag="button">返 回</router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HomeInventoryManagementDetails",
    data() {
      return {
        cloningInventoryImg: []
      };
    },
    props: ["inventoryDetailsData", "inventoryDetailsImages","inventoryTablesData"],
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

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";
  #HomeInventoryManagementDetails {
    width: 100%;
    height: 100%;
    padding: 20px;
    box-sizing: border-box;
    .HomeInventoryManagementDetails-swipe {
      width: 100%;
      height: 289px;
      img {
        width: 100%;
        height: 289px;
        border-radius: 18px;
      }
    }
    .table-content{
      background:#fff;
      border: solid 1px #dfdcdc;
      border-radius: 15px;
      margin-top: 20px;
      .HomeInventoryManagementDetails-main {
        &>li {
          position: relative;
          padding: 8px 30px 0;
          width: 100%;
          height: 100%;
          box-sizing: border-box;
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
            margin-bottom: 20px;
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
              line-height: 32px;
              color: #333;
              &>p:nth-child(1) {
                flex-shrink: 0;
                padding-left: 26px;
                margin-right: 30px;
                width: 155px;
                height: 32px;
                background-color: #eeeeee;
                box-sizing: border-box;
              }
            }
          }
        }
      }
      .list-details-table {
        /*&:after{*/
          /*content:'';*/
          /*height:90px;*/
          /*display:block;*/
        /*}*/
        ul>li {
          width: 92%;
          margin:10px auto;
          display: flex;
          font-size: $widthWeb18;
          line-height: 32px;
          color: #333;
          padding-right:10px;
          &>p:nth-child(1) {
            flex-shrink: 0;
            padding-left: 26px;
            margin-right: 30px;
            width: 155px;
            height: 32px;
            background-color: #eeeeee;
            box-sizing: border-box;
          }
        }
        .list-details-ul{
          li{
            &:nth-child(even){
              background:#eeeeee;
            }
            &:nth-child(odd){
              background:#faf6eb;
            }
          }
          .table_flex{
            flex:1;
            padding:15px 0;
            &:first-child{
              .accound{
                color:#fc871e;
              }
            }
            &:nth-child(2){
              text-align:left;
              .accound{
                margin-left:40px;
              }
            }
            &:nth-child(3){
              text-align:center;
            }
            .table-box{
              padding:5px 0;
              .accound_text{
                font-size:$widthWeb14;
                border:2px solid #d0d0d0;/*px*/
                padding:2px 5px;
                border-radius:10px;
                color:#b3b2b0;
                margin-left: 30px;
                display:inline-block;
                text-align: center;
              }
            }
            .accound{
              font-size:$widthWeb18;
              padding:5px;
              word-break:keep-all;
              white-space:nowrap;
              overflow:hidden;
              text-overflow:ellipsis;
              margin-left: 30px;
            }
          }
        }
      }

    }
  }
</style>
