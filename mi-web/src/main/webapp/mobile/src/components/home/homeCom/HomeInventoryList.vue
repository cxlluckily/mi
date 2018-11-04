<template>
  <div id='HomeInventoryList'>
    <ul class="HomeInventoryList-list">
      <li @click="listDetails($event)" v-for="itemList in inventoryListData" :key="itemList.inventoryListUserId" :data-inventoryListId="itemList.inventoryListId+','+itemList.inventoryListUserId">
        <div class="list-title">
          <span></span>
          <p>{{itemList.inventoryListName}}</p>
        </div>
        <div class="list-details">
          <div class="details-image">
            <img v-if="itemList.inventoryListImage" :src="itemList.inventoryListImage">
            <img v-else src="../../../assets/home/s_pic1.png">
          </div>
          <div class="details-details">
            <div class="details-left">
              <p>备件类型 :<span>{{itemList.inventoryListType || '暂无!'}}</span></p>
              <p>备件状态 :<span>{{itemList.inventoryListState || '暂无!'}}</span></p>
            </div>
            <div class="details-right">
              <p>备件型号 :<span>{{itemList.inventoryListModel || '暂无!'}}</span></p>
              <p>拥有数量 :<span class="G-num">{{itemList.inventoryListNum || '暂无!'}}</span></p>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "HomeInventoryList",
    data() {
      return {};
    },
    props: ["inventoryListData"],
    methods: {
      /* 方法 */
      listDetails(event) {
        console.log(event);
        let detailsId = $(event.currentTarget).attr("data-inventoryListId");
        this.$router.push({
          path: "/SparePartsDetails/" + detailsId
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
  #HomeInventoryList {
    width: 100%;
    .HomeInventoryList-list {
      // padding: 14px 25px 82px;
      // width: 100%;
      // background-color: #fff;
      // border: solid 1px #dfdcdc;
      // box-sizing: border-box;
      // border-radius: 16px;
      &>li {
        padding-bottom: 14px;
        width: 100%;
        border-bottom: 1px solid #dfdcdc;
        box-sizing: border-box;
        .list-title {
          display: flex;
          align-items: center;
          font-size: 0;
          span {
            margin: 20px 8px 16px 0;
            display: inline-block;
            width: 9px;
            height: 9px;
            background-color: #fd8521;
          }
          p {
            padding: 20px 0 16px;
            font-size: .38rem;
            line-height: .38rem;
            color: #333;
          }
        }
        .list-details {
          display: flex; // justify-content: space-between;
          align-items: center;
          color: #999;
          .details-image {
            flex-shrink: 0;
            width: 70px;
            height: 70px;
            margin-right: 28px;
            img {
              width: 100%;
              height: 100%;
            }
          }
          .details-details {
            width: calc(100% - 98px);
            display: flex;
            justify-content: space-between;
            box-sizing: border-box;
            .details-left {
              width: 50%;
              flex-shrink: 0;
              p {
                font-size: .36rem;
                line-height: .36rem;
                padding: 10px 0 8px;
                &:nth-last-child(1) {
                  padding: 8px 0 10px 0;
                }
              }
            }
            .details-right {
              width: 50%;
              flex-shrink: 0;
              p {
                font-size: .36rem;
                line-height: .36rem;
                padding: 10px 0 8px;
                &:nth-last-child(1) {
                  padding: 8px 0 10px 0;
                }
              }
            }
          }
          span {
            color: #333;
            margin-left: 20px;
          }
          .G-num {
            color: #fd8521;
          }
          p {
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
      }
    }
  }
</style>
