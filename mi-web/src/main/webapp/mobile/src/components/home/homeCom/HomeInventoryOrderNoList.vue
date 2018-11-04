<template>
  <div id='HomeInventoryOrderNoList'>
    <ul class="HomeInventoryOrderNoList-list">
      <li @click.stop="contentDetails($event,itemList,itemList.inventoryOrderID)" v-for="itemList in inventoryOrderNoListData" :key="itemList.inventoryOrderID" :data-detailsId='itemList.inventoryOrderID +","+itemList.inventoryOrderSubjectID'>
        <div class="list-title">
          <i></i>
          <p>单据号 : {{itemList.inventoryOrderApplyNo}}</p>
          <!-- "alreadyIn" 已入库
                        "toBeIn" 待入库 -->
          <div :class="{'title-state':itemList.inventoryOrderStatus == 'toBeIn'}"></div>
        </div>
        <ul class="list-content">
          <li>
            <div>{{itemList.inventoryOrderName}}</div>
            <div>仓库名称</div>
          </li>
          <li>
            <div>{{itemList.inventoryOrderNum}}</div>
            <div>总备件数</div>
          </li>
        </ul>
        <div class="list-footer">
          <p v-if="itemList.inventoryOrderType == 'newIn'">入库性质 : 新品入库</p>
          <p v-else-if="itemList.inventoryOrderType == 'useIn'">入库性质 : 归还入库</p>
          <p v-else-if="itemList.inventoryOrderType == 'transferIn'">入库性质 : 调拨入库</p>
          <p v-else-if="itemList.inventoryOrderType == 'borrowIn'">入库性质 : 借用入库</p>
          <p v-else-if="itemList.inventoryOrderType == 'returnIn'">入库性质 : 返还入库</p>
          <p v-if="itemList.inventoryOrderTimer">入库时间 : <span class="fault-timer">{{itemList.inventoryOrderTimer | initDate}}</span></p>
          <button @click.stop="btnFun($event,itemList.inventoryOrderID)" v-if="itemList.inventoryOrderStatus == 'toBeIn'" :data-detailsId='itemList.inventoryOrderID +","+itemList.inventoryOrderSubjectID'>入库
            </button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "HomeInventoryOrderNoList",
    data() {
      return {};
    },
    props: ['inventoryOrderNoListData'],
    methods: {
      /* 方法 */
      contentDetails(event, itemList, id) {
        if (itemList.inventoryOrderStatus == 'toBeIn') {
          this.btnFun(event, id);
          return;
        }
        this.$router.push({
          name: 'PutStorageDetails',
          query: {
            inStockId: id
          }
        });
      },
      btnFun(event, id) {
        event.stopPropagation();
        this.$router.push({
          name: 'PutStorageEdit',
          query: {
            inStockId: id
          }
        });
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
  #HomeInventoryOrderNoList {
    width: 100%;
    height: 100%;
    line-height: 1;
    .HomeInventoryOrderNoList-list {
      &>li {
        position: relative;
        margin-bottom: 20px;
        /*padding: 8px 30px 25px;*/
        padding: 0 30px;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 15px;
        .list-title {
          display: flex;
          align-items: center;
          /*margin-bottom: 18px;*/
          width: 100%;
          height: 80px;
          font-size: 0;
          /*border-bottom: 1px solid #dfdcdc;*/
          i {
            display: inline-block;
            margin-right: 13px;
            width: 27px;
            height: 33px;
            background-image: url("../../../assets/home/sheet.png");
            background-size: 100% 100%;
          }
          p {
            color: #333;
            font-size: $widthWeb25;
            font-weight: 700;
          }
          div {
            position: absolute;
            top: 0;
            right: 0;
            width: 125px;
            height: 74px;
            background-image: url("../../../assets/home/label_putintostorage2.png");
            background-size: 100% 100%;
          }
          .title-state {
            background-image: url("../../../assets/home/label_putintostorage.png");
          }
        }
        .list-content {
          border-top: solid 1px #dfdcdc;
          border-bottom: solid 1px #dfdcdc;
          padding: 20px 0;
          display: flex;
          /*margin-bottom: 20px;*/
          width: 100%; // display: flex;
          // justify-content: space-between;
          // flex-wrap: wrap;
          li {
            /*display: flex;*/
            flex: 1;
            text-align: center;
            border-right: solid 1px #dfdcdc;
            &:last-child {
              border-right: solid 0px #dfdcdc;
            }
            div:nth-child(1) {
              padding: 8px; // font-weight: 700;
              /*width: 100%;*/
              font-size: $widthWeb22;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
              color: #333;
            }
            div:nth-child(2) {
              /*width: 100%;*/
              padding: 7px;
              font-size: $widthWeb16;
              color: #999;
            }
          }
        }
        .list-footer {
          padding: 15px 0;
          position: relative;
          p {
            font-size: $widthWeb18;
            &:nth-child(1) {
              padding: 8px 0;
            }
            &:nth-child(2) {
              padding: 7px 0;
            }
          }
          button {
            position: absolute;
            top: 50%;
            right: 15px;
            transform: translateY(-50%);
            padding: 10px 20px;
            border-radius: 6px;
            font-size: .36rem;
            line-height: .36rem;
            background-color: #fd8521;
            color: #fff;
          }
          .fault-timer {
            color: #fb7204;
          }
        }
        .list-btn {
          padding-right: 20px;
          width: 100%;
          text-align: right;
          box-sizing: border-box;
          button {
            padding: 9px 22px;
            font-size: $widthWeb16;
            color: #fafafc;
            background-color: #fd8521;
            border-radius: 6px;
          }
        }
      }
    }
  }
</style>
