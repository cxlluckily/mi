<template>
  <div id='REDetails'>
    <div class="REDetails-main">
      <div class="REDetails-site">
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData">
        </GlobalTroubleNumber>
      </div>
      <div class="REDetails-process">
        <HomeProcess :processData="childProcessData"></HomeProcess>
      </div>
      <div class="REDetails-fault">
        <HomeFaultDetails :faultDetailsData="childFaultDetailsData"></HomeFaultDetails>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="btnSubmit">评 价</button>
    </div>
    <!-- 评价提示框 -->
    <div class="evaluationWin" v-show="switchEvaluationWin">
      <div class="evaluationWin-bgc">
        <div class="evaluationWin-title">
          <p>回访评价</p>
          <div class="title-icon">
            <i @click="closeWin"></i>
          </div>
        </div>
        <div class="evaluationWin-main">
          <ul class="main-list">
            <li>
              <p class="list-G-text">维修结果:</p>
              <div class="list-radio">
                <label for="radioRepair" class="radio-repair" @click="radio = 'radioRepair'">
                                          <span :class="{'selectedRadio': radio == 'radioRepair'}"></span>
                                          修复
                                          <input type="radio"
                                                 name="repair"
                                                 id="radioRepair"
                                                 value="yes"
                                                 v-model="repairText"
                                                 checked>
                                        </label>
                <label for="radioNotRepair" class="radio-repair" @click="radio = 'radioNotRepair'">
                                          <span :class="{'selectedRadio': radio == 'radioNotRepair'}"></span>
                                          未修复
                                          <input type="radio"
                                                 name="repair"
                                                 value="no"
                                                 v-model="repairText"
                                                 id="radioNotRepair">
                                        </label>
              </div>
            </li>
            <li>
              <p class="list-G-text">响应速度:</p>
              <div class="list-star">
                <ul>
                  <li data-star='1' @click="entityStar($event)">
                    <i></i>
                  </li>
                  <li data-star='2' @click="entityStar($event)">
                    <i></i>
                  </li>
                  <li data-star='3' @click="entityStar($event)">
                    <i></i>
                  </li>
                  <li data-star='4' @click="entityStar($event)">
                    <i></i>
                  </li>
                  <li data-star='5' @click="entityStar($event)">
                    <i></i>
                  </li>
                </ul>
              </div>
            </li>
            <li>
              <p class="list-G-text">服务速度:</p>
              <div class="list-star">
                <ul>
                  <li data-star='1' @click="entityStarService($event)">
                    <i></i>
                  </li>
                  <li data-star='2' @click="entityStarService($event)">
                    <i></i>
                  </li>
                  <li data-star='3' @click="entityStarService($event)">
                    <i></i>
                  </li>
                  <li data-star='4' @click="entityStarService($event)">
                    <i></i>
                  </li>
                  <li data-star='5' @click="entityStarService($event)">
                    <i></i>
                  </li>
                </ul>
              </div>
            </li>
            <li>
              <p class="list-G-text">操作规范:</p>
              <div class="list-star">
                <ul>
                  <li data-star='1' @click="entityStarOperation($event)">
                    <i></i>
                  </li>
                  <li data-star='2' @click="entityStarOperation($event)">
                    <i></i>
                  </li>
                  <li data-star='3' @click="entityStarOperation($event)">
                    <i></i>
                  </li>
                  <li data-star='4' @click="entityStarOperation($event)">
                    <i></i>
                  </li>
                  <li data-star='5' @click="entityStarOperation($event)">
                    <i></i>
                  </li>
                </ul>
              </div>
            </li>
            <li>
              <p class="list-G-text">回访评价:</p>
              <div class="list-textBox">
                <textarea placeholder="请输入回访评述" v-model="entityTextBox">
                                        </textarea>
              </div>
            </li>
          </ul>
        </div>
        <div class="evaluationWin-fun">
          <button class="btn-close" @click="closeWin">取消</button>
          <button class="btn-determine" @click="evaluationBtnSubmit">确定</button>
        </div>
      </div>
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
    name: "REDetails",
    data() {
      return {
        swtichLoading: true,
        childTicketNumberData: [],
        childProcessData: [],
        childFaultDetailsData: [],
        switchEvaluationWin: false,
        repairText: "",
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
    // rated
    methods: {
      /* 方法 */
      btnSubmit() {
        this.switchEvaluationWin = true;
      },
      closeWin() {
        this.switchEvaluationWin = false;
      },
      entityStar(event) {
        let thisIndex = $(event.currentTarget).attr("data-star");
        let listArr = $(event.currentTarget)
          .closest("ul")
          .find("li");
        this.paramObj.speedAppraise = thisIndex;
        listArr.find("i").removeClass("star");
        for (let i = 0; i < thisIndex; i++) {
          listArr
            .eq(i)
            .find("i")
            .addClass("star");
        }
      },
      entityStarService(event) {
        let thisIndex = $(event.currentTarget).attr("data-star");
        let listArr = $(event.currentTarget)
          .closest("ul")
          .find("li");
        this.paramObj.serviceAppraise = thisIndex;
        listArr.find("i").removeClass("star");
        for (let i = 0; i < thisIndex; i++) {
          listArr
            .eq(i)
            .find("i")
            .addClass("star");
        }
      },
      entityStarOperation(event) {
        let thisIndex = $(event.currentTarget).attr("data-star");
        let listArr = $(event.currentTarget)
          .closest("ul")
          .find("li");
        this.paramObj.operationSpecificationAppraise = thisIndex;
        listArr.find("i").removeClass("star");
        for (let i = 0; i < thisIndex; i++) {
          listArr
            .eq(i)
            .find("i")
            .addClass("star");
        }
      },
      evaluationBtnSubmit(event) {
        const THIS = this;
        this.paramObj.appraiseDescribe = this.entityTextBox;
        // this.paramObj.applyStatus = this.repairText || "yes";
        // this.paramObj.maintenanceApplyId = this.$route.params.id;
        // this.paramObj.appraiseWasFinished = this.repairText || "yes";
        this.paramObj.applyStatus = this.radio == 'radioRepair' ? 'yes' : "no";
        this.paramObj.maintenanceApplyId = this.$route.params.id;
        this.paramObj.appraiseWasFinished = this.radio == 'radioRepair' ? 'yes' : "no";
        // console.log(this.paramObj)
        this.api.starDataAll(this.paramObj).then(function(data) {
          (THIS.paramObj = {
            speedAppraise: "",
            serviceAppraise: "",
            operationSpecificationAppraise: "",
            appraiseDescribe: "",
            applyStatus: "",
            maintenanceApplyId: ""
          }),
          $(".list-star li i").removeClass("star");
          THIS.switchEvaluationWin = false;
          THIS.$router.push({
            path: "/ReviewEvaluation"
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
      let childFaultDetailsArr = []; // 故障现象集合
      let nameId = this.$route.params.id;

      this.api.SendSingleDetailsDataAll(nameId).then((data) => {
        const DATA = data.data;
        if (DATA.logs[0].status == 'Rated') {
          this.$router.replace({
            path: '/RepairHistoryDetails/' + nameId
          });
          return;
        }
        this.childTicketNumberData.push({
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
          maintenanceDescriptio: DATA.processDescribe,
          maintenanceImages: DATA.repairImages,
          repairErrorPhenomenon: DATA.repairErrorPhenomenon,
          solution: DATA.solution,
          maintenanceDescribe: DATA.maintenanceDescribe,
          swtichMaintenance: true,
          swtichChangeInfo: true,
          changeInfo: DATA.changeInfo,
        });
        this.$nextTick(function() {
          this.swtichLoading = false;
        });
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
  #REDetails {
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
    .REDetails-site {
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
    .REDetails-main {
      width: 100%;
      height: calc(100% - 70px);
      overflow: hidden;
      overflow-y: auto;
    }
    .REDetails-fault {
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
            &>li {
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
                  &>li {
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
                  width: 290px;
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
