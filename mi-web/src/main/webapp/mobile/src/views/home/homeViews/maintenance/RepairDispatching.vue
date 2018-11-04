<template>
  <div id='RepairDispatching'>
    <HomeMaintenanceTitle :maintenanceTitle="RepairDispatchingTitle" :maintenancePath="ReviewEvaluationPath"></HomeMaintenanceTitle>
    <div class="RepairDispatching-main">
      <mt-loadmore v-show="selected == 'issued'" :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData" @HomeListRefresh='setListRefresh' @HomeListIndex='getListIndex'>
        </GlobalTroubleNumber>
      </mt-loadmore>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <!-- Empty prompt -->
    <div class="gloobalEmptyPrompt" v-show="swtichEmptyPrompt">
      <!-- <div class="emptyPrompt"></div> -->
      <div class="emptyPrompt">暂 无 数 据 !</div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  import GlobalTroubleNumber from "@/components/globalCom/GlobalTroubleNumber";
  import HomeMaintenanceTitle from "@/components/home/homeCom/HomeMaintenanceTitle";
  export default {
    name: "RepairDispatching",
    data() {
      return {
        swtichLoading: true,
        swtichEmptyPrompt: false,
        RepairDispatchingTitle: "派工记录",
        ReviewEvaluationPath: 'pd',
        bottomText: "上拉加载更多...",
        selected: "issued", // 切换 (我发出的/发给我的) 页面切换
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        RDParamObj: {
          applyStatus: "toBeDispatch,noRepair",
          searchType: 'pdtj',
          type: "phone",
          start: 0,
          limit: 5
        },
        childTicketNumberData: [],
        childAddPersonnel: [],
        indexData: ""
      };
    },
    methods: {
      // 列表数据
      initData({
        initLoadmore = false,
        loadding = false
      }) {
        console.log(initLoadmore, loadding);
        this.api.dispatchingDataAll(this.RDParamObj).then(data => {
          const DATA = data.data.rows;
          this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
          for (let i = 0, L = DATA.length; i < L; i++) {
            this.childTicketNumberData.push({
              maintenanceApplyId: DATA[i].maintenanceApplyId,
              ticketNumber: DATA[i].applyNO,
              listDetails: [{
                  listTitle: DATA[i].lineName,
                  listVice: "线路"
                },
                {
                  listTitle: DATA[i].stationName,
                  listVice: "车站"
                },
                {
                  listTitle: DATA[i].location,
                  listVice: "设备位置"
                }
              ],
              faultText: DATA[i].breakDescribe,
              faultTimer: DATA[i].createTime,
              stationId: DATA[i].stationId,
              lineId: DATA[i].lineId,
              copyInfo: DATA[i].copyInfo,
              childRouterUrl: "/RDSendSingle/" + DATA[i].maintenanceApplyId,
              correspondingComponent: "/RepairDispatching"
            });
          }
          if (this.childTicketNumberData.length >= data.data.totalCount) {
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
        });
      },
      getListIndex(val) {
        this.indexData = val;
      },
      issuedLoad() {
        this.RDParamObj.start = this.RDParamObj.start += 5;
        this.initData({
          initLoadmore: true
        });
      },
      setListRefresh() {
        this.swtichLoading = true;
        this.childTicketNumberData = [];
        this.RDParamObj.start = 0;
        this.initData({
          loadding: true
        });
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalTroubleNumber,
      HomeMaintenanceTitle
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData({
        loadding: true
      });
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        if (to.path == "/RepairDispatching" && this.indexData != "") {
          this.RDParamObj.limit = this.childTicketNumberData.length;
          this.RDParamObj.start = 0;
          this.api.dispatchingRecordDataAll(this.RDParamObj).then(data => {
            const DATA = data.data.rows;
            this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
            this.childTicketNumberData[this.indexData] = [];
            for (let i = 0, II = DATA.length; i < II; i++) {
              if (i == this.indexData) {
                let dataIndex = {
                  maintenanceApplyId: DATA[i].maintenanceApplyId,
                  ticketNumber: DATA[i].applyNO,
                  listDetails: [{
                      listTitle: DATA[i].lineName,
                      listVice: "线路"
                    },
                    {
                      listTitle: DATA[i].stationName,
                      listVice: "车站"
                    },
                    {
                      listTitle: DATA[i].location,
                      listVice: "设备位置"
                    }
                  ],
                  faultText: DATA[i].breakDescribe,
                  faultTimer: DATA[i].createTime,
                  stationId: DATA[i].stationId,
                  lineId: DATA[i].lineId,
                  copyInfo: DATA[i].copyInfo,
                  childRouterUrl: "/RDSendSingle/" + DATA[i].maintenanceApplyId,
                  correspondingComponent: "/RepairDispatching"
                };
                this.childTicketNumberData.splice([this.indexData], 1, {});
                this.$nextTick(function() {
                  this.childTicketNumberData.splice(
                    [this.indexData],
                    1,
                    dataIndex
                  );
                });
              }
            }
            if (this.childTicketNumberData.length >= data.data.totalCount) {
              this.allLoaded = true;
            } else {
              this.allLoaded = false;
            }
          });
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #RepairDispatching {
    overflow: hidden;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    // .emptyPrompt {
    //   background: url("../../../../assets/home/nosubject.png");
    //   background-size: 100% 100%;
    // }
    .RepairDispatching-main {
      height: calc(100% - 66px);
      overflow: scroll;
    }
  }
</style>
