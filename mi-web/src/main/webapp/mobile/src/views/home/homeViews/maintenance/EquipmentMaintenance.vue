<template>
  <div id='EquipmentMaintenance'>
    <HomeMaintenanceTitle :maintenanceTitle="EquipmentMaintenanceTitle" :maintenancePath="ReviewEvaluationPath"></HomeMaintenanceTitle>
    <div class="EquipmentMaintenance-main">
      <mt-loadmore v-show="selected == 'issued'" :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData"></GlobalTroubleNumber>
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
    name: "EquipmentMaintenance",
    data() {
      return {
        swtichLoading: true,
        swtichEmptyPrompt: false,
        EquipmentMaintenanceTitle: "维修记录",
        ReviewEvaluationPath: 'wx',
        bottomText: "上拉加载更多...",
        selected: "issued", // 切换 (我发出的/发给我的) 页面切换
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        childRouterUrlRepairing: "/EMAfterRepairing",
        childRouterUrlComplete: "/EMCompleteMaintenance",
        childTicketNumberData: [],
        paramObj: {
          applyStatus: "toBeRepair,maintenance",
          searchType: 'wxywx',
          start: 0,
          limit: 5
        }
      };
    },
    methods: {
      /* 方法 */
      issuedLoad() {
        this.paramObj.start = this.paramObj.start += 2;
        this.initDataNoDown({
          val: this.paramObj,
          initLoaded: true
        });
      },
      initDataNoDown({
        val = this.paramObj,
        initLoaded = false,
        loadding = false
      }) {
        this.api.maintenanceInitDataAll(val).then(data => {
          let dataArr = data.data.rows;
          this.swtichEmptyPrompt = dataArr.length == 0 ? true : false;
          for (let i = 0, II = dataArr.length; i < II; i++) {
            this.childTicketNumberData.push({
              ticketNumber: dataArr[i].applyNO, // applyNO
              applyStatus:dataArr[i].applyStatus,
              maintenanceApplyId: dataArr[i].maintenanceApplyId, // maintenanceApplyId
              listDetails: [{
                  listTitle: dataArr[i].lineName, // lineName
                  listVice: "线路"
                },
                {
                  listTitle: dataArr[i].stationName, // stationName
                  listVice: "车站"
                },
                {
                  listTitle: dataArr[i].location, // location
                  listVice: "设备位置"
                }
              ],
              faultText: dataArr[i].breakDescribe, // breakDescribe
              faultTimer: dataArr[i].createTime, // createTime
              stationId: dataArr[i].stationId, // 车站 ID
              childRouterUrl: dataArr[i].applyStatus == "toBeRepair" ?
                "/EMAfterRepairing/" + dataArr[i].maintenanceApplyId // 接修
                :
                "/EMCompleteMaintenance/" + dataArr[i].maintenanceApplyId, // 故障处理
              correspondingComponent: "/EquipmentMaintenance"
            });
          }
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
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalTroubleNumber,
      HomeMaintenanceTitle
    },
    mounted: function() {
      /* 初始化数据 */
      this.initDataNoDown({
        loadding: true
      });
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        this.paramObj.start = 0;
        this.childTicketNumberData = [];
        if (to.path == "/EquipmentMaintenance") {
          this.swtichLoading = true;
        }
        // if (to.name == "EMAfterRepairing" && from.name == "EMCompleteMaintenance") {
        //   this.$router.push({
        //     path: "/EquipmentMaintenance"
        //   });
        // }
        this.initDataNoDown({
          val: this.paramObj,
          loadding: true
        });
      }
    }
  };
</script>

<style lang='scss'>
  #EquipmentMaintenance {
    overflow: hidden;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    // .emptyPrompt {
    //   background: url("../../../../assets/home/nosubject.png");
    //   background-size: 100% 100%;
    // }
    .EquipmentMaintenance-main {
      width: 100%;
      height: calc(100% - 66px);
      overflow: scroll;
    }
  }
</style>
