<template>
  <div id='RDSendSingle'>
    <div class="RDSendSingle-main">
      <div class="RDSendSingle-site">
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData"></GlobalTroubleNumber>
      </div>
      <div class="RDSendSingle-process">
        <HomeProcess :processData="childProcessData"></HomeProcess>
      </div>
      <div class="RDSendSingle-fault">
        <HomeFaultDetails :faultDetailsData="childFaultDetailsData"></HomeFaultDetails>
      </div>
      <div class="RDSendSingle-sendSingle">
        <div class="sendSingle-personnel">
          <p class="personnel-personnels">维修人员 :</p>
          <span class="personnel-down" @click="stationName($event)">{{personnelObj.text || '请选择维修人员'}}</span>
        </div>
        <!-- <div class="sendSingle-addPersonnel">
                            <div class="addPersonnel-btn" @click="addressBookName">
                              <i></i>
                              <p>添加抄送</p>
                            </div>
                            <div class="addPersonnel-name" ref="nameScroll">
                              <ul ref="scrollXWidth" class="nameId">
                                <li v-for="(itemName, index) in nameIdArr" :data-index=index :data-copyToUserId="itemName.copyToUserId" :key="index" @click="deleteName($event)">
                                  <i></i>
                                  <p>{{itemName.copyToUser}}</p>
                                </li>
                              </ul>
                            </div>
                          </div> -->
      </div>
    </div>
    <div class="globalFooter">
      <button @click="btnSubmit">派 单</button>
    </div>
    <!-- 派单提示弹框 -->
    <div class="RDSendSingle-sendSingleWin" v-show="switchSendSingleWin">
      <div class="sendSingleWin-main">
        <div class="main-title">
          <p>派单</p>
          <div class="title-icon" @click="closeWin">
            <i></i>
          </div>
        </div>
        <div class="main-details">
          <p>您正在将维修工单</p>
          <p>{{ticketNumber}}</p>
          <p>分配给
            <span>{{personnelObj.text}}</span>
          </p>
        </div>
        <div class="main-btn">
          <button class="btn-cancel" @click="closeWin">取消</button>
          <button class="btn-determine" @click="submitWin">确定</button>
        </div>
      </div>
    </div>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle">
    </GlobalErrorInformation>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  import GlobalTroubleNumber from "@/components/globalCom/GlobalTroubleNumber";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import HomeProcess from "@/components/home/homeCom/HomeProcess";
  import HomeFaultDetails from "@/components/home/homeCom/HomeFaultDetails";
  import HomeOrdinarySideOut from "@/components/home/homeCom/HomeOrdinarySideOut";
  export default {
    name: "RDSendSingle",
    data() {
      return {
        swtichLoading: true,
        childTicketNumberData: [],
        childProcessData: [],
        switchHomeOrdinarySideOut: false, // 类型默认为 hide
        HomeOrdinarySideOutTitle: "", // 设备类型的标题
        HomeOrdinarySideOutData: [], // 初始化类型数据
        personnelObj: {},
        ticketNumber: '',
        childFaultDetailsData: [],
        nameIdArr: [],
        nameID: [],
        stationId: "",
        switchGlobalErrorInformation: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        switchSendSingleWin: false
      };
    },
    methods: {
      /* 方法 */
      addressBookName() {
        this.nameID = [];
        let liDOM = $(".addPersonnel-name li");
        this.nameID.push(
          this.$route.params.id
          .split(",")
          .splice(0, 1)
          .join()
        );
        for (let i = 0, II = liDOM.length; i < II; i++) {
          this.nameID.push(liDOM.eq(i).attr("data-copyToUserId"));
        }
        this.$router.push({
          path: "/MultipleChoiceAddressBookDetails/" + this.nameID
        });
      },
      deleteName(event) {
        // let _thisIndex = $(event.currentTarget).attr("data-index");
        let _this = $(event.currentTarget);
        let thisWidth = event.currentTarget.clientWidth;
        let thisId = _this.attr("data-copyToUserId");
        let listArr = _this.closest(".nameId").find("li");
        let listIdArr = [];
        for (let i = 0, II = listArr.length; i < II; i++) {
          listIdArr.push(listArr.eq(i).attr("data-copyToUserId"));
        }
        let ulDOM = _this.closest(".nameId");
        let liDOM = _this.closest(".nameId").find("li");
        let divDOM = _this.closest(".addPersonnel-name");
        var liWidthSum = 0;
        for (let i = 0, II = liDOM.length; i < II; i++) {
          liWidthSum += liDOM[i].clientWidth;
        }
        liWidthSum -= thisWidth;
        ulDOM.css({
          width: liWidthSum
        });
        listIdArr.splice(listIdArr.indexOf(thisId), 1);
        _this.remove();
        // this.nameIdArr.splice(_thisIndex, 1);
      },
      stationName(event) {
        const THIS = this;
        this.switchHomeOrdinarySideOut = true;
        this.HomeOrdinarySideOutTitle = "请选择维修人员";
        this.HomeOrdinarySideOutData = [];
        this.api.maintenancePersonnelDataAll(THIS.stationId).then(function(data) {
          const DATA = data.data;
          for (let i = 0, II = DATA.length; i < II; i++) {
            THIS.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].userId,
              sideOutText: DATA[i].realName
            });
          }
        });
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        this.personnelObj = val;
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      closeWin() {
        this.switchSendSingleWin = false;
      },
      btnSubmit() {
        if (!this.personnelObj.text) {
          this.switchGlobalErrorInformation = true;
          this.EquipmentRepairErrorTitle = "派单错误!";
          this.EquipmentRepairError = "请选择维修人员!";
          return;
        }
        this.switchSendSingleWin = true;
      },
      submitWin() {
        const paramObj = {
          maintenanceApplyId: this.$route.params.id,
          maintenanceUser: this.personnelObj.text,
          maintenanceUserID: this.personnelObj.sideOutId,
          userIds: []
        };
        let liDOM = $(".addPersonnel-name li");
        for (let i = 0, II = liDOM.length; i < II; i++) {
          paramObj.userIds.push(liDOM.eq(i).attr("data-copyToUserId"));
        }
        this.api.SendSingleDataAll(paramObj).then((data) => {
          this.switchSendSingleWin = false;
          this.$router.push({
            path: "/RepairDispatching"
          });
        });
      },
      childInitBScroll() {
        this.$nextTick(function() {
          let ulDOM = $(".nameId");
          let divDOM = $(".addPersonnel-name");
          var liWidthSum = 0;
          for (let i = 0, II = ulDOM.length; i < II; i++) {
            liWidthSum = 0;
            let liDOM = ulDOM.eq(i).find("li");
            for (let v = 0, VV = liDOM.length; v < VV; v++) {
              liWidthSum += liDOM[v].clientWidth;
            }
            ulDOM.eq(i).css({
              width: liWidthSum
            });
            new BScroll(divDOM[i], {
              startX: 0,
              click: true,
              scrollX: true,
              scrollY: false
            });
          }
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
      HomeFaultDetails,
      HomeOrdinarySideOut,
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
      const URLID = this.$route.params.id.split(",").splice(0, 1).join();
      let childFaultDetailsArr = []; // 故障现象集合
      this.nameIdArr = [];
      this.api.SendSingleDetailsDataAll(URLID).then((data) => {
        const DATA = data.data;
        if (!(DATA.logs[0].status == 'Failed' || DATA.logs[0].status == 'Reported')) {
          this.$router.replace({
            path: '/RepairHistoryDetails/' + URLID,
            query: {
              'taskNotice': 'true'
            }
          });
          return;
        }
        this.ticketNumber = DATA.applyNO;
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
        this.stationId = DATA.stationId;
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
          swtichMaintenance: DATA.applyStatus == 'noRepair',
          repairErrorPhenomenon: DATA.repairErrorPhenomenon,
          solution: DATA.solution,
          maintenanceDescribe: DATA.maintenanceDescribe,
          swtichChangeInfo: DATA.applyStatus == 'noRepair',
          changeInfo: DATA.changeInfo,
        });
        for (let v = 0, VV = DATA.copyInfo.length; v < VV; v++) {
          this.nameIdArr.push(DATA.copyInfo[v]);
        }
        this.$nextTick(function() {
          this.swtichLoading = false;
        })
      });
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
    },
    watch: {
      /* 监听 */
      $route(to, from) {
        if (to.name == "RDSendSingle" && from.name == "multipleChoiceAddressBookDetails") {
          console.log(this.$route.params.id)
          const URLID = this.$route.params.id.split(",").splice(0, 1).join();
          this.nameIdArr = [];
          this.api.SendSingleDetailsDataAll(URLID).then((data) => {
            const DATA = data.data;
            for (let v = 0, VV = DATA.copyInfo.length; v < VV; v++) {
              this.nameIdArr.push(DATA.copyInfo[v]);
              this.$nextTick(function() {
                let ulDOM = $(".nameId");
                let divDOM = $(".addPersonnel-name");
                var liWidthSum = 0;
                for (let i = 0, II = ulDOM.length; i < II; i++) {
                  liWidthSum = 0;
                  let liDOM = ulDOM.eq(i).find("li");
                  for (let v = 0, VV = liDOM.length; v < VV; v++) {
                    liWidthSum += liDOM[v].clientWidth;
                  }
                  ulDOM.eq(i).css({
                    width: liWidthSum
                  });
                  new BScroll(divDOM[i], {
                    startX: 0,
                    click: true,
                    scrollX: true,
                    scrollY: false
                  });
                }
              });
            }
          });
        }
      }
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #RDSendSingle {
    position: absolute;
    top: 0;
    left: 0;
    padding-top: 20px;
    width: 100%;
    height: 100% !important;
    box-sizing: border-box;
    background-color: #faf6ec;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .RDSendSingle-site {
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
    .RDSendSingle-main {
      width: 100%;
      height: calc(100% - 70px);
      overflow: hidden;
      overflow-y: auto;
    }
    .RDSendSingle-fault {
      margin-top: 20px;
    }
    .RDSendSingle-sendSingle {
      margin-top: 35px;
      padding-left: 50px;
      padding-bottom: 25px;
      padding-right: 40px;
      .sendSingle-personnel {
        display: flex;
        align-items: center;
        margin-bottom: 20px;
        .personnel-personnels {
          color: #333;
          font-size: .36rem;
          line-height: .36rem;
        }
        .personnel-down {
          display: inline-block;
          margin-left: 22px;
          width: 330px;
          height: 40px;
          color: #999;
          font-size: .32rem;
          line-height: 40px;
          text-align: center;
          border: 1px solid #dfdcdc;
          background-color: #fff;
          border-radius: 18px;
          box-sizing: border-box;
        }
      }
      .sendSingle-addPersonnel {
        display: flex;
        .addPersonnel-btn {
          display: flex;
          flex-shrink: 0;
          align-items: center;
          padding: 12px 17px 12px 9px;
          font-size: 0;
          border: solid 1px #f9caa6;
          background-color: #fbe9da;
          border-radius: 10px;
          box-sizing: border-box;
          i {
            margin-right: 6px;
            display: inline-block;
            width: 20px;
            height: 20px;
            background: url("../../../../assets/home/dispatching.png") no-repeat;
            background-size: 100% 100%;
          }
          p {
            font-size: .28rem;
            color: #333;
          }
        }
      }
      .addPersonnel-name {
        margin-left: 20px;
        flex-shrink: 0;
        flex: 1;
        overflow: hidden;
        ul {
          display: flex;
          width: 100%;
          height: 100%;
          font-size: 0;
          li {
            flex-shrink: 0;
            position: relative;
            padding: 13px 20px;
            font-size: 0;
            text-align: center;
            &:nth-child(1) {
              padding-left: 0;
            }
            i {
              position: absolute;
              top: 3px;
              right: 0;
              display: inline-block;
              width: 13px;
              height: 13px;
              background: url("../../../../assets/home/mini_colse1.png") no-repeat;
              background-size: 100% 100%;
            }
            p {
              font-size: .32rem;
              color: #333;
            }
          }
        }
      }
    }
    .RDSendSingle-sendSingleWin {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 200;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.3);
      .sendSingleWin-main {
        position: absolute;
        top: 44%;
        left: 50%;
        transform: translate(-50%, -50%);
        padding-bottom: 76px;
        width: 540px;
        border-radius: 15px;
        background-color: #fff;
        .main-title {
          position: relative;
          width: 100%;
          height: 80px;
          color: #f15510;
          font-size: .6rem;
          font-weight: 700;
          line-height: 80px;
          text-align: center;
          background-color: #fce4bd;
          border-radius: 15px 15px 0 0;
          .title-icon {
            position: absolute;
            top: 0;
            right: 0;
            width: 95px;
            height: 100%;
            i {
              display: inline-block;
              width: 35px;
              height: 35px;
              background: url("../../../../assets/home/close.png") no-repeat;
              background-size: 100% 100%;
            }
          }
        }
        .main-details {
          width: 100%;
          padding: 36px 0 36px 74px;
          box-sizing: border-box;
          font-size: .44rem;
          p {
            &:nth-child(1) {
              line-height: 56px;
            }
            &:nth-child(2) {
              margin: 0 10px 0 24px;
              color: #f15510;
              line-height: 56px;
            }
            &:nth-child(3) {
              line-height: 56px;
              span {
                color: #f15510;
              }
            }
          }
        }
        .main-btn {
          width: 100%;
          text-align: center;
          font-size: 0;
          button {
            padding: 9px 37px;
            font-size: .44rem;
          }
          .btn-cancel {
            margin-right: 26px;
            color: #fafafc;
            background-color: #b9b7b4;
          }
          .btn-determine {
            color: #fafafc;
            background-color: #fd8521;
          }
        }
      }
    }
  }
</style>
