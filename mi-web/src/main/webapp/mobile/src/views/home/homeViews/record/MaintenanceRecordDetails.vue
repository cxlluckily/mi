<template>
  <div id='MaintenanceRecordDetails'>
    <div class="MaintenanceRecordDetails-main">
      <div class="MaintenanceRecordDetails-site">
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData">
        </GlobalTroubleNumber>
      </div>
      <div class="MaintenanceRecordDetails-process">
        <HomeProcess :processData="childProcessData"></HomeProcess>
      </div>
      <div class="MaintenanceRecordDetails-fault">
        <HomeFaultDetails :faultDetailsData="childFaultDetailsData"></HomeFaultDetails>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="btnSubmit">返 回</button>
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
  name: "MaintenanceRecordDetails",
  data() {
    return {
      swtichLoading: true,
      childTicketNumberData: [],
      childProcessData: [],
      childFaultDetailsData: [],
      entityTextBox: "",
      radio: "radioRepair",
      paramObj: {
        speedAppraise: "",
        serviceAppraise: "",
        operationSpecificationAppraise: "",
        appraiseDescribe: "",
        wasFinished: "",
        maintenanceApplyId: ""
      }
    };
  },
  methods: {
    /* 方法 */
    btnSubmit() {
      this.$router.go(-1);
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
    let childFaultDetailsArr = []; // 故障现象集合
    let nameId = this.$route.params.id.split(",").splice(0, 1).join();
    this.api.SendSingleDetailsDataAll(nameId).then((data)=> {
      const DATA = data.data;
      this.childTicketNumberData.push({
        ticketNumber: DATA.applyNO, // applyNO
        maintenanceApplyId: DATA.maintenanceApplyId, // maintenanceApplyId
        listDetails: [
          {
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
        this.childProcessData.push({
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
      this.childFaultDetailsData.push({
        faultPhenomenon: childFaultDetailsArr,
        faultCode: DATA.errorCode,
        faultDescription: DATA.breakDescribe,
        faultImaegs: DATA.reportedImages,
        maintenanceResults: DATA.wasFinished,
        repairErrorPhenomenon: DATA.repairErrorPhenomenon,
        solution: DATA.solution,
        maintenanceDescribe: DATA.maintenanceDescribe,
        maintenanceDescriptio: DATA.processDescribe,
        maintenanceImages: DATA.repairImages,
        swtichMaintenance: DATA.applyStatus == 'repaired' || DATA.applyStatus == 'noRepair' || DATA.applyStatus =='rated',

        swtichEvaluation:  DATA.applyStatus == 'rated',
        appraiseWasFinished: DATA.appraiseWasFinished,
        speedAppraise:DATA.speedAppraise,
        serviceAppraise: DATA.serviceAppraise,
        operationSpecificationAppraise: DATA.operationSpecificationAppraise,
        appraiseDescribe: DATA.appraiseDescribe,

        swtichChangeInfo: DATA.applyStatus == 'repaired' || DATA.applyStatus == 'noRepair' || DATA.applyStatus =='rated',
        changeInfo: DATA.changeInfo,
      });
      this.$nextTick(function() {
        this.swtichLoading = false;
      });
    });
  },
  watch: {
    /* 监听 */
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#MaintenanceRecordDetails {
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
  .MaintenanceRecordDetails-site {
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
  .GlobalTroubleNumber-arrow {
    display: none;
  }
  .MaintenanceRecordDetails-main {
    width: 100%;
    height: calc(100% - 70px);
    overflow: hidden;
    overflow-y: auto;
  }
  .MaintenanceRecordDetails-fault {
    margin: 20px 0;
  }
  .evaluationWin {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 200;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    .evaluationWin-bgc {
      position: absolute;
      top: 24%;
      left: 50%;
      transform: translateX(-50%);
      width: 540px;
      background-color: #f6f6ec;
      border-radius: 15px;
      .evaluationWin-title {
        position: relative;
        background-color: #fce4bd;
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
        text-align: center;
        p {
          color: #f15510;
          font-size: $widthWeb25;
          line-height: 80px;
        }
        .title-icon {
          position: absolute;
          top: 50%;
          right: 16px;
          width: 70px;
          height: 70px;
          transform: translateY(-50%);
          text-align: center;
          i {
            display: inline-block;
            width: 35px;
            height: 35px;
            margin-top: 14px;
            background-image: url("../../../../assets/home/close.png");
            background-size: 100% 100%;
          }
        }
      }
      .evaluationWin-main {
        padding: 17px 48px 0 42px;
        box-sizing: border-box;
        .main-list {
          & > li {
            display: flex;
            .list-G-text {
              margin-right: 26px;
              color: #333;
              font-size: $widthWeb25;
              line-height: 60px;
            }
            .list-radio {
              font-size: 0;
              padding-left: 8px;
              .radio-repair {
                margin-right: 30px;
                font-size: $widthWeb22;
                color: #666;
                line-height: 60px;
                span {
                  display: inline-block;
                  width: 18px;
                  height: 18px;
                  background: url("../../../../assets/home/radio_no.png");
                  background-size: 100% 100%;
                }
                .selectedRadio {
                  background: url("../../../../assets/home/radio_sel.png");
                  background-size: 100% 100%;
                }
                &:nth-last-child(1) {
                  margin-right: 0px;
                }
              }
              input {
                display: none;
              }
            }
            .list-star {
              ul {
                display: flex;
                & > li {
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  width: 42px;
                  height: 60px;
                  i {
                    display: inline-block;
                    width: 32px;
                    height: 30px;
                    background-image: url("../../../../assets/home/fivepoitedstar2.png");
                    background-size: 100% 100%;
                  }
                  .star {
                    background-image: url("../../../../assets/home/fivepoitedstar.png");
                    background-size: 100% 100%;
                  }
                }
              }
            }
            .list-textBox {
              textarea {
                margin-top: 9px;
                padding: 21px 18px;
                width: 318px;
                height: 143px;
                font-size: $widthWeb18;
                color: #333;
                resize: none;
                border: solid 1px #bfbfbf;
                box-sizing: border-box;
              }
            }
          }
        }
      }
      .evaluationWin-fun {
        padding: 32px 48px 40px 0;
        width: 100%;
        text-align: right;
        font-size: 0;
        box-sizing: border-box;
        button {
          width: 124px;
          height: 40px;
          font-size: $widthWeb22;
          color: #fff;
        }
        .btn-close {
          background-color: #b9b7b4;
          margin-right: 26px;
        }
        .btn-determine {
          background-color: #fd8521;
        }
      }
    }
  }
}
</style>
