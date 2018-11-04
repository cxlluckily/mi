<template>
  <div id='HomeManagementList'>
    <ul class="HomeInventoryList-list">
      <li @click="listDetails($event,itemList)" v-for="(itemList , index) in inventoryListData" :key="index"
          :data-inventoryListId="itemList.sparePartId+','+itemList.warehouseId">
        <div class="list-title">
          <span></span>
          <p>{{itemList.categoryName}}</p>
        </div>
        <div class="list-details">
          <em class="pic_img">
            <img v-if="itemList.imageUrl" :src="itemList.imageUrl">
            <img v-else src="../../../assets/home/s_pic1.png">
          </em>
          <div class="details-flex">
            <p>备件类型 :<span>{{itemList.partName}}</span></p>
            <p>备件情况 :<span>{{itemList.status=='normal'?'好件':'坏件'}}</span></p>
          </div>
          <div class="details-flex">
            <p>库存类型 :<span>{{itemList.inventoryType=='notUnique'?'非唯一':'唯一'}}</span></p>
            <p>拥有数量 :<span class="G-num">{{itemList.account || '暂无!'}}</span></p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "HomeManagementList",
    data() {
      return {};
    },
    props: ["inventoryListData"],
    methods: {
      /* 方法 */
      listDetails(event,itemList) {
        let detailsId = $(event.currentTarget).attr("data-inventoryListId");
        this.$router.push({
          path: "/InventoryManagementDetails/" + itemList.sparePartId + ',' + itemList.warehouseId + ',' + itemList.inventoryType + ',' + itemList.status
        });
      }
    }
  };
</script>

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";

  #HomeManagementList {
    width: 100%;
    .HomeInventoryList-list {
      padding: 14px 25px 20px;
      width: 100%;
      background-color: #fff;
      border: solid 1px #dfdcdc;
      box-sizing: border-box;
      border-radius: 16px;
      & > li {
        padding-bottom: 14px;
        width: 100%;
        border-bottom: 1px solid #dfdcdc;
        box-sizing: border-box;
        .list-title {
          display: flex;
          align-items: center;
          height: 56px;
          font-size: 0;
          span {
            margin-right: 8px;
            display: inline-block;
            width: 9px;
            height: 9px;
            background-color: #fd8521;
          }
          p {
            font-size: $widthWeb18;
            color: #333;
          }
        }
        .list-details {
          display: flex;
          justify-content: space-between;
          font-size: $widthWeb18;
          padding: 15px 0;
          color: #999;
          align-items: center;
          .details-flex {
            flex: 1;
            width: 50%;
          }
          em {
            display: inline-block;
            width: 80px;
            height: 80px;
            vertical-align: middle;
            padding: 5px 10px 5px 0;
            img {
              display: inline-block;
              width: 100%;
              height: 100%;
            }
          }
          span {
            color: #333;
            margin-left: 20px;
            width: 90px;
            display: inline-block;
          }
          .G-num {
            color: #fd8521;
          }
          p {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            padding: 5px 0;
          }
        }
      }
    }
  }
</style>
