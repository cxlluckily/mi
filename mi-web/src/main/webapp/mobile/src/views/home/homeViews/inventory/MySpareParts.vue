<template>
  <div id='MySpareParts'>
    <HomeInventoryTitle :inventoryTitle="childInventoryTitle" :inventoryRouterPath="childInventoryRouterPath" :swtichInventorySearch="childswtichInventorySearch">
    </HomeInventoryTitle>
    <div class="MySpareParts-main">
      <div class="main-content">
        <mt-loadmore :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
          <HomeInventoryList :inventoryListData="childInventoryListData"></HomeInventoryList>
        </mt-loadmore>
      </div>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <!-- Empty prompt -->
    <div class="gloobalEmptyPrompt" v-show="swtichEmptyPrompt">
      <div class="emptyPrompt">暂 无 数 据 !</div>
    </div>
    <router-view class="routerView"></router-view>
  </div>
</template>

<script>
  import HomeInventoryTitle from "@/components/home/homeCom/HomeInventoryTitle";
  import HomeInventoryList from "@/components/home/homeCom/HomeInventoryList";
  export default {
    name: "MySpareParts",
    data() {
      return {
        swtichLoading: true,
        swtichEmptyPrompt: false,
        childInventoryTitle: "领用",
        childInventoryRouterPath: "UseApplyList?applyType=use",
        childswtichInventorySearch: false,
        bottomText: "上拉加载更多...",
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        prompObj: {
          start: 0,
          limit: 8
        },
        childInventoryListData: []
      };
    },
    methods: {
      /* 方法 */
      issuedLoad() {
        this.RDParamObj.start = this.RDParamObj.start += 5;
        this.initData({
          initLoadmore: true
        });
      },
      initData({
        prompObj = this.prompObj,
        loading = false
      }) {
        this.api.mySparePartsDataAll(prompObj).then(data => {
          const DATA = data.data.rows;
          this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
          for (let i = 0, II = DATA.length; i < II; i++) {
            this.childInventoryListData.push({
              inventoryListId: DATA[i].sparePartId,
              inventoryListUserId: DATA[i].userDeviceId,
              inventoryListName: DATA[i].partName,
              inventoryListType: DATA[i].categoryName,
              inventoryListModel: DATA[i].specificationModel,
              inventoryListState: DATA[i].status,
              inventoryListNum: DATA[i].applyCount,
              inventoryListImage: DATA[i].imageUrl
            });
          }
          if (this.childInventoryListData.length >= 7) {
            $('.MySpareParts-main').css({
              height: '100%'
            })
          }
          if (this.childInventoryListData.length >= data.data.totalCount) {
            this.allLoaded = true;
          } else {
            this.allLoaded = false;
          }
          this.$nextTick(function() {
            this.swtichLoading = false;
          });
        });
      }
    },
    components: {
      /* 复用组件名称 */
      HomeInventoryTitle,
      HomeInventoryList
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData({
        loading: true
      });
    },
    watch: {
      /* 监听 */
      childInventoryListData: function(val) {
        if (val.length >= 7) {
          $('.MySpareParts-main').css({
            height: '100%'
          })
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #MySpareParts {
    overflow: hidden;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    } // .emptyPrompt {
    //   background: url("../../../../assets/home/nosubject.png");
    //   background-size: 100% 100%;
    // }
    .MySpareParts-main {
      padding: 0 20px 20px;
      width: 100%;
      max-height: calc(100% - 66px); // height: calc(100% - 66px);
      box-sizing: border-box;
      overflow: hidden;
      .main-content {
        padding: 14px 25px 82px;
        width: 100%;
        height: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 16px;
        overflow: scroll;
      }
    }
    .routerView {
      z-index: 200;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
    }
  }
</style>
