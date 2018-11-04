<template>
  <div id='EMAfterRepairing'>
    <div class="EMAfterRepairing-main">
      <div class="EMAfterRepairing-site">
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData">
        </GlobalTroubleNumber>
      </div>
      <div class="EMAfterRepairing-process">
        <HomeProcess :processData="childProcessData"></HomeProcess>
      </div>
      <div class="EMAfterRepairing-fault">
        <HomeFaultDetails :faultDetailsData="childFaultDetailsData"></HomeFaultDetails>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="btnSubmit">接 修</button>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import GlobalTroubleNumber from "@/components/globalCom/GlobalTroubleNumber";
  import HomeProcess from "@/components/home/homeCom/HomeProcess";
  import HomeFaultDetails from "@/components/home/homeCom/HomeFaultDetails";
  export default {
    name: "EMAfterRepairing",
    data() {
      return {
        swtichLoading: true,
        childTicketNumberData: [],
        childProcessData: [],
        childFaultDetailsData: []
      };
    },
    methods: {
      /* 方法 */
      btnSubmit() {
        this.swtichLoading = true;
        // console.log(this.$route.params.id);
        this.api.orderDataAll(this.$route.params.id).then((data) => {
          this.$router.replace({
            path: "/EMCompleteMaintenance/" + this.$route.params.id
          });
        });
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalTroubleNumber,
      HomeProcess,
      HomeFaultDetails
    },
    mounted: function() {
      /* 初始化数据 */
      const THIS = this;
      let childFaultDetailsArr = []; // 故障现象集合
      THIS.nameIdArr = [];
      let nameId = this.$route.params.id
        .split(",")
        .splice(0, 1)
        .join();
      console.log(nameId);
      this.api.SendSingleDetailsDataAll(nameId).then(function(data) {
        const DATA = data.data;
        if (DATA.logs[0].status != 'Dispatched') {
          THIS.$router.replace({
            path: '/RepairHistoryDetails/' + nameId,
            query: {
              'taskNotice': 'true'
            }
          });
          return;
        }
        THIS.childTicketNumberData.push({
          ticketNumber: DATA.applyNO, // applyNO
          maintenanceApplyId: DATA.maintenanceApplyId, // maintenanceApplyId
          listDetails: [{
              listTitle: DATA.categoryName, // location
              listVice: "设备类型"
            },
            {
              listTitle: DATA.equipmentNO, // location
              listVice: "设备编码"
            },
            {
              listTitle: DATA.lineName, // lineName
              listVice: "线路"
            },
            {
              listTitle: DATA.stationName, // stationName
              listVice: "车站"
            }
          ],
          faultText: DATA.breakDescribe, // breakDescribe
          faultTimer: DATA.createTime, // createTime
          stationId: DATA.stationId // 车站 ID
        });
        for (var i = 0, LL = DATA.logs.length; i < LL; i++) {
          THIS.childProcessData.push({
            initiator: DATA.logs[i].initiator,
            targetPerson: DATA.logs[i].targetPerson,
            createTime: DATA.logs[i].createTime,
            modifyTime: DATA.logs[i].modifyTime,
            status: DATA.logs[i].status
          });
        }
        for (var k = 0, KK = DATA.reportErrorPhenomenon.length; k < KK; k++) {
          if (DATA.reportErrorPhenomenon[k].checked) {
            childFaultDetailsArr.push(
              DATA.reportErrorPhenomenon[k].breakAbbreviated
            );
          }
        }
        THIS.childFaultDetailsData.push({
          faultPhenomenon: childFaultDetailsArr,
          faultCode: DATA.errorCode,
          faultDescription: DATA.breakDescribe,
          faultImaegs: DATA.reportedImages,
        });
        for (let v = 0, VV = DATA.copyInfo.length; v < VV; v++) {
          THIS.nameIdArr.push(DATA.copyInfo[v]);
        }
        // THIS.childFaultDetailsData.push(childFaultDetailsObj);
        console.log(THIS.childFaultDetailsData);
        THIS.$nextTick(function() {
          THIS.swtichLoading = false;
        })
      });
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
    },
    watch: {
      /* 监听 */
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #EMAfterRepairing {
    position: absolute;
    top: 0;
    left: 0;
    padding-top: 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background-color: #faf6ec;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .GlobalTroubleNumber-arrow {
      display: none;
    }
    .EMAfterRepairing-main {
      width: 100%;
      height: calc(100% - 80px);
      overflow: hidden;
      overflow-y: auto;
      .EMAfterRepairing-site {
        .list-siteDescription {
          li:nth-child(1) {
            flex: 20;
          }
          li:nth-child(2) {
            flex: 22;
          }
          li:nth-child(3) {
            flex: 30;
          }
          li:nth-child(4) {
            flex: 28;
          }
        }
      }
    }
    .EMAfterRepairing-fault {
      margin-top: 20px;
    }
  }
</style>
