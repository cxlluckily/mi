<template>
  <div id='RepairDisHistory'>
    <div class="RepairDisHistory-main">
      <mt-loadmore v-show="selected == 'issued'" :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData" @HomeListRefresh='setListRefresh'>
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
  export default {
    name: "RepairDisHistory",
    data() {
      return {
        swtichLoading: true,
        swtichEmptyPrompt: false,
        bottomText: "上拉加载更多...",
        selected: "issued", // 切换 (我发出的/发给我的) 页面切换
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        childTicketNumberData: [],
        REParamObj: {
          applyStatus: "repaired,noRepair,rated",
          searchType: this.$route.params.state,
          type: "phone",
          start: 0,
          limit: 5,
          beginTime: this.formatDate(new Date().setDate(new Date().getDate() - 60)),
          endTime: this.formatDate(new Date().getTime())
        },
        totalCount: ""
      };
    },
    methods: {
      /* 方法 */
      formatDate(time) {
        const date = new Date(time);
        let year = date.getFullYear(),
          month = date.getMonth() + 1,
          day = date.getDate(),
          hour = date.getHours(),
          min = date.getMinutes(),
          sec = date.getSeconds();
        let newTime = year + '-' +
          month + '-' +
          day + ' ' +
          hour + ':' +
          min + ':' +
          sec;
        return newTime;
      },
      issuedLoad() {
        this.REParamObj.start = this.REParamObj.start += 5;
        this.initData({
          val: this.REParamObj,
          initLoaded: true
        });
      },
      initData({
        val = this.REParamObj,
        initLoaded = false,
        loadding = false
      }) {
        this.api.dispatchingDataAll(val).then((data) => {
          const DATA = data.data.rows;
          this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
          for (let i = 0, L = data.data.rows.length; i < L; i++) {
            this.childTicketNumberData.push({
              ticketNumber: DATA[i].applyNO, // applyNO
              maintenanceApplyId: DATA[i].maintenanceApplyId, // maintenanceApplyId
              listDetails: [{
                  listTitle: DATA[i].lineName, // lineName
                  listVice: "线路"
                },
                {
                  listTitle: DATA[i].stationName, // stationName
                  listVice: "车站"
                },
                {
                  listTitle: DATA[i].location, // location
                  listVice: "设备位置"
                }
              ],
              faultText: DATA[i].breakDescribe, // breakDescribe
              faultTimer: DATA[i].createTime, // createTime
              childRouterUrl: "/RepairHistoryDetails/" + DATA[i].maintenanceApplyId,
              currentComponent: "/RepairDisHistory",
              allApplyStatus: DATA[i].applyStatus,
              switchState: true
            });
          }
          this.totalCount = data.data.totalCount;
          if (this.childTicketNumberData.length >= data.data.totalCount) {
            this.allLoaded = true;
          }
          if (loadding) {
            this.$nextTick(function() {
              this.swtichLoading = false;
            });
          }
          if (initLoaded) {
            this.$refs.loadmore.onBottomLoaded();
          }
        });
      },
      setListRefresh(val) {
        this.swtichLoading = true;
        this.childTicketNumberData = [];
        this.initData({
          loadding: true
        });
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalTroubleNumber,
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData({
        val: this.REParamObj,
        loadding: true
      });
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        this.childTicketNumberData = [];
        this.REParamObj.start = 0;
        if (to.path == '/RepairDisHistory') {
          this.swtichLoading = true;
        }
        this.initData({
          val: this.REParamObj,
          loadding: true
        });
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #RepairDisHistory {
    overflow: hidden;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .gloobalEmptyPrompt {
      margin: 0;
      height: calc(100% - 80px);
      padding-top: 66px;
      box-sizing: border-box;
      // .emptyPrompt {
      //   background: url("../../../../assets/home/nosubject.png");
      //   background-size: 100% 100%;
      // }
    }
    .RepairDisHistory-main {
      height: calc(100% - 20px);
      margin: 20px 0;
      overflow: scroll;
    }
  }
</style>
