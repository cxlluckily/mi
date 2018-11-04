<template>
  <div id='PutStorage'>
    <!-- <div class="lll">
      <HomeInventoryListing></HomeInventoryListing>
    </div> -->
    <HomeInventoryTitle :inventoryTitle="childInventoryTitle" :inventoryRouterPath="childInventoryRouterPath" :swtichInventorySearch="childswtichInventorySearch" @timerDeta='getTimerDeta'>
    </HomeInventoryTitle>
    <div class="PutStorage-main">
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <HomeInventoryOrderNoList :inventoryOrderNoListData="childInventoryOrderNoListData"></HomeInventoryOrderNoList>
      </mt-loadmore>
    </div>
    <router-view class="routerView"></router-view>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import HomeInventoryListing from "@/components/home/homeCom/HomeInventoryListing";
  import HomeInventoryTitle from "@/components/home/homeCom/HomeInventoryTitle";
  import HomeInventoryOrderNoList from "@/components/home/homeCom/HomeInventoryOrderNoList";
  export default {
    name: "PutStorage",
    data() {
      return {
        swtichLoading: true,
        bottomText: "上拉加载更多...",
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        childInventoryTitle: "新增入库",
        childInventoryRouterPath: "/NewLibrary",
        childswtichInventorySearch: true,
        prompObj: {
          beginTime: "",
          endTime: "",
          inStockStatus: "all",
          inStockType: "all",
          limit: 5,
          start: 0,
          warehouseId: "0"
        },
        childInventoryOrderNoListData: []
      };
    },
    methods: {
      /* 方法 */
      btnData() {},
      issuedLoad() {
        // this.prompObj.start = this.prompObj.start += 5;
        this.prompObj.start = this.childInventoryOrderNoListData.length;
        this.initData({
          initLoadmore: true
        });
      },
      initAllLoaded() {
        let mainHeight = $(".PutStorage-main").height();
        let loadmoreHeight = $(".mint-loadmore").height();
        if (mainHeight >= loadmoreHeight) {
          this.allLoaded = true;
        }
      },
      // 获取前一个月的时间
      timerMonth() {
        const nowdate = new Date();
        nowdate.setMonth(nowdate.getMonth() - 3);
        const y = nowdate.getFullYear(),
          m = nowdate.getMonth() + 1,
          d = nowdate.getDate(),
          dateMonths = y + '-' + (m >= 10 ? m : '0' + m) + '-' + (d >= 10 ? d : '0' + d);
        return dateMonths;
      },
      // 当前时间
      initTimer(timer) {
        if (timer == '') {
          return '';
        }
        var date = new Date(timer);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? "0" + m : m;
        var d = date.getDate();
        d = d < 10 ? "0" + d : d;
        return y + "-" + m + "-" + d;
      },
      getTimerDeta(val) {
        this.swtichLoading = true;
        if (val.beginTime != '') {
          this.prompObj.beginTime = val.beginTime + ' 00:00:00';
        }
        if (val.endTime != '') {
          this.prompObj.endTime = val.endTime + ' 23:59:59';
        }
        this.prompObj.inStockStatus = val.inStockStatus;
        this.prompObj.inStockType = val.inStockType;
        this.prompObj.warehouseId = val.warehouseId;
        this.childInventoryOrderNoListData = [];
        this.prompObj.start = 0;
        this.initData({
          loadding: true,
          initTimer: false
        });
      },
      initData({
        initTimer = false,
        initLoadmore = false,
        loadding = false
      }) {
        if (initTimer) {
          this.prompObj.beginTime = this.timerMonth() + ' 00:00:00';
          let strTimer1 = new Date().getTime();
          this.prompObj.endTime = this.initTimer(strTimer1) + ' 23:59:59';
        }
        this.api.storageInitDataAllprompObj(this.prompObj).then((data) => {
          const DATA = data.data.rows;
          for (let i = 0, II = DATA.length; i < II; i++) {
            this.childInventoryOrderNoListData.push({
              inventoryOrderID: DATA[i].inStockId,
              inventoryOrderSubjectID: DATA[i].operationSubjectId,
              inventoryOrderName: DATA[i].warehouseName,
              inventoryOrderApplyNo: DATA[i].inStockApplyNo,
              inventoryOrderTimer: DATA[i].inDate,
              inventoryOrderStatus: DATA[i].inStockStatus,
              inventoryOrderType: DATA[i].inStockType,
              inventoryOrderNum: DATA[i].sumOfCount
            })
          }
          if (this.childInventoryOrderNoListData.length >= data.data.totalCount) {
            this.allLoaded = true;
          } else {
            this.allLoaded = false;
          }
          if (loadding) {
            this.$nextTick(function() {
              this.swtichLoading = false;
            });
          }
          if (initLoadmore) {
            this.$nextTick(function() {
              this.$refs.loadmore.onBottomLoaded();
            });
          }
        })
      },
      goBack(){
        this.$router.replace({path: '/'});
      },
      addGoBack(){
        if (window.history && window.history.pushState) {
          history.pushState(null, null, document.URL);
          window.addEventListener('popstate', this.goBack, false);
        }
      },
      destroyGoBack(){
        window.removeEventListener('popstate', this.goBack, false);
      }
    },
    components: {
      /* 复用组件名称 */
      HomeInventoryTitle,
      HomeInventoryOrderNoList,
      HomeInventoryListing
    },
    mounted: function() {
      /* 初始化数据 */
      this.initAllLoaded();
      this.initData({
        loadding: true,
        initTimer: true
      });
      this.addGoBack();
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        if(to.path == '/PutStorage'){
          let refresh = this.common.getSessionStorage('PutStorageRefresh',false,true);
          if(refresh == 'true'){
            this.childInventoryOrderNoListData = [];
            this.prompObj.start = 0;
            this.initData({
              loadding: true,
              initTimer: true
            });
          }
          this.addGoBack();
        }else{
          this.destroyGoBack();
        }
      }
    },
    destroyed(){
      this.destroyGoBack();
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #PutStorage {
    width: 100%;
    .lll {
      width: 100%;
      height: 100%;
      padding: 20px;
      box-sizing: border-box;
    }
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .PutStorage-main {
      width: 100%;
      padding: 0 20px;
      height: calc(100% - 66px);
      box-sizing: border-box;
      overflow: scroll;
    }
    /*.routerView {*/
      /*position: absolute;*/
      /*top: 0;*/
      /*left: 0;*/
      /*z-index: 200;*/
      /*width: 100%;*/
      /*height: 100%;*/
      /*background-color: #faf6ec;*/
    /*}*/
  }
</style>
