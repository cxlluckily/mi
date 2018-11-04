<template>
  <div id='GlobalTroubleNumber'>
    <div class="GlobalTroubleNumber-main">
      <ul class="GlobalTroubleNumber-list">
        <li v-for="(troubleNumberItem, index) in ticketNumberData" :key="index">
          <div class="list-title">
            <i></i>
            <p>故障单号 : {{troubleNumberItem.ticketNumber}}</p>
          </div>
          <ul class="list-siteDescription">
            <li v-for="(listItem, index) in troubleNumberItem.listDetails" :key="index">
              <p class="siteDescription-site">{{listItem.listTitle || '暂无!'}}</p>
              <p class="siteDescription-instructions">{{listItem.listVice}}</p>
            </li>
          </ul>
          <div class="list-faultDescription">
            <p>故障描述 : {{troubleNumberItem.faultText || '暂无描述!'}}</p>
            <p>上报时间 :{{troubleNumberItem.allState}}
              <span class="faultDescription-timer">{{troubleNumberItem.faultTimer | initDate}}</span>
            </p>
          </div>
          <!-- 箭头 -->
          <div class="GlobalTroubleNumber-arrow" @click="multiFun" v-if="troubleNumberItem.childRouterUrl">
            <router-link :to="troubleNumberItem.childRouterUrl" tag="i"></router-link>
          </div>
          <!-- 维修派工页 -->
          <div class="GlobalTroubleNumber-sendSingle" v-if="troubleNumberItem.correspondingComponent == '/RepairDispatching'">
            <div class="sendSingle-personnel">
              <p class="personnel-personnels">维修人员 :</p>
              <input class="personnel-down" placeholder="请选择维修人员" unselectable="on" onfocus="this.blur()" :data-stationId="troubleNumberItem.stationId" readonly :data-index="index" @click="stationName($event)">
              <button type="button" class="personnel-btn" @click="openWin" :data-index="index" :data-ticketNumber="troubleNumberItem.ticketNumber" :data-maintenanceApplyId="troubleNumberItem.maintenanceApplyId" :data-lineId="troubleNumberItem.lineId">派 单</button>
            </div>
            <!-- <div class="GlobalTroubleNumber-addPersonnel">
                        <div class="addPersonnel-btn"
                             @click="addressBookName">
                          <i></i>
                          <p @click="routerLink($event)"
                             :data-index="index"
                             :data-maintenanceApplyId="troubleNumberItem.maintenanceApplyId">
                            添加抄送
                          </p>
                        </div>
                        <div class="addPersonnel-name"
                             ref="nameScroll">
                          <ul ref="scrollXWidth"
                              class="nameId">
                            <li v-for="(itemName, index) in troubleNumberItem.copyInfo"
                                :key="index"
                                :data-index="index"
                                :data-copyToUserId="itemName.copyToUserId"
                                @click="deleteName($event)">
                              <i></i>
                              <p>{{itemName.copyToUser}}</p>
                            </li>
                          </ul>
                        </div>
                      </div> -->
          </div>
          <!-- 设备维修 -->
          <!--todo:赵开军确认看看有没有问题-->
          <div class="GlobalTroubleNumber-maintenance" v-if="troubleNumberItem.correspondingComponent == '/EquipmentMaintenance'">
            <i v-if="troubleNumberItem.applyStatus == 'toBeDispatch'" class="title-toBeDispatch"></i>
            <i v-else-if="troubleNumberItem.applyStatus == 'toBeRepair'" class="title-toBeRepair"></i>
            <i v-else-if="troubleNumberItem.applyStatus == 'maintenance'" class="title-maintenance"></i>
            <i v-else-if="troubleNumberItem.applyStatus == 'repaired'" class="title-repaired"></i>
            <i v-else-if="troubleNumberItem.applyStatus == 'noRepair'" class="title-noRepair"></i>
            <i v-else-if="troubleNumberItem.applyStatus == 'rated'" class="title-rated"></i>
            <!--<i :class="{'maintenance-faultHandling':troubleNumberItem.childRouterUrl=='/EMCompleteMaintenance/'+troubleNumberItem.maintenanceApplyId}"></i>-->
          </div>
          <!-- 单子记录 -->
          <div class="GlobalTroubleNumber-maintenance" v-if="troubleNumberItem.switchState">
            <i v-if="troubleNumberItem.allApplyStatus == 'toBeDispatch'" class="title-toBeDispatch"></i>
            <i v-else-if="troubleNumberItem.allApplyStatus == 'toBeRepair'" class="title-toBeRepair"></i>
            <i v-else-if="troubleNumberItem.allApplyStatus == 'maintenance'" class="title-maintenance"></i>
            <i v-else-if="troubleNumberItem.allApplyStatus == 'repaired'" class="title-repaired"></i>
            <i v-else-if="troubleNumberItem.allApplyStatus == 'noRepair'" class="title-noRepair"></i>
            <i v-else-if="troubleNumberItem.allApplyStatus == 'rated'" class="title-rated"></i>
          </div>
          <!-- 评价 -->
          <div class="GlobalTroubleNumber-evaluation" v-if="troubleNumberItem.currentComponent == '/ReviewEvaluation'">
            <div class="evaluation-main">
              <button @click="evaluationFun($event)" :data-entity="troubleNumberItem.maintenanceApplyId">评 价</button>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!-- 派单提示弹框 -->
    <div class="sendSingleWin" v-show="switchSendSingleWin">
      <div class="sendSingleWin-main">
        <div class="main-title">
          <p>派单</p>
          <div class="title-icon" @click="closeWin">
            <i></i>
          </div>
        </div>
        <div class="main-details">
          <p>您正在将维修工单</p>
          <p>{{personnelObj.ticketNumber}}</p>
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
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="GlobalTroubleNumberError" :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  import HomeOrdinarySideOut from "@/components/home/homeCom/HomeOrdinarySideOut";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  export default {
    name: "GlobalTroubleNumber",
    data() {
      return {
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: "",
        GlobalTroubleNumberErrorTitle: "",
        switchHomeOrdinarySideOut: false, // 类型默认为 hide
        HomeOrdinarySideOutTitle: "", // 设备类型的标题
        HomeOrdinarySideOutData: [], // 初始化类型数据
        personnelindex: null,
        personnelObj: {},
        switchSendSingleCom: false, // 派单相关的组件展示
        switchSendSingleWin: false, // 派单弹出框
        switchAddressBookCom: false, // 切换通讯录
        nameIDArr: [],
        switchEvaluationWin: false,
        radio: "radioRepair",
        repairText: "",
        entityTextBox: "",
        paramObj: {
          speedAppraise: "",
          serviceAppraise: "",
          operationSpecificationAppraise: "",
          appraiseDescribe: "",
          applyStatus: "",
          maintenanceApplyId: ""
        },
        routerLinkIndex: "",
        inpTextData: ""
      };
    },
    props: ["ticketNumberData"],
    methods: {
      /* 方法 */
      addressBookName() {
        this.switchAddressBookCom = !this.switchAddressBookCom;
      },
      deleteName(event) {
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
      },
      openWin() {
        const _THIS = $(event.currentTarget);
        let inpVal = _THIS.prev(".personnel-down").val();
        let thisIndex = _THIS.attr("data-index");
        let listIdArr = $(".nameId")
          .eq(thisIndex)
          .find("li");
        if (inpVal == "") {
          this.switchGlobalErrorInformation = true;
          this.GlobalTroubleNumberError = "请选择维修人员";
          this.GlobalTroubleNumberErrorTitle = "派单错误";
          return;
        }
        this.personnelObj.nameIdArr = [];
        for (let i = 0, II = listIdArr.length; i < II; i++) {
          this.personnelObj.nameIdArr.push(
            listIdArr.eq(i).attr("data-copytouserid")
          );
        }
        this.switchSendSingleWin = true;
        this.personnelObj.ticketNumber = _THIS.attr("data-ticketNumber");
        this.personnelObj.maintenanceApplyId = _THIS.attr(
          "data-maintenanceApplyId"
        );
        this.personnelObj.maintenanceUserID = _THIS.attr("data-lineId");
        console.log(this.personnelObj);
      },
      closeWin() {
        this.switchSendSingleWin = false;
        this.switchEvaluationWin = false;
      },
      submitWin() {
        const paramObj = {
          maintenanceApplyId: this.personnelObj.maintenanceApplyId,
          maintenanceUser: this.personnelObj.text,
          maintenanceUserID: this.personnelObj.sideOutId,
          userIds: this.personnelObj.nameIdArr
        };
        console.log(paramObj);
        const THIS = this;
        this.switchSendSingleWin = false;
        this.api.SendSingleDataAll(paramObj).then(function(data) {
          THIS.$emit("HomeListRefresh", "");
        });
      },
      // 多功能箭头函数, 点击跳转不同的组件
      multiFun() {
        // console.log(1)
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = false;
      },
      // 弹出维修人员
      stationName(event) {
        const THIS = this;
        const _THIS = $(event.currentTarget);
        this.personnelindex = _THIS.attr("data-index");
        this.switchHomeOrdinarySideOut = true;
        this.HomeOrdinarySideOutTitle = "请选择维修人员";
        this.HomeOrdinarySideOutData = [];
        this.api.maintenancePersonnelDataAll(_THIS.attr("data-stationId"))
          .then(function(data) {
            const DATA = data.data;
            for (let i = 0, II = DATA.length; i < II; i++) {
              THIS.HomeOrdinarySideOutData.push({
                sideOutId: DATA[i].userId,
                sideOutText: DATA[i].realName
              });
            }
          });
      },
      // (设备类型选/设备编号)传过来隐藏子组件的 Boolean
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      // (设备类型/设备编号)选中的值和ID
      getHomeSelectedType(val) {
        console.log(val);
        this.personnelObj = val;
        this.$nextTick(function() {
          $(".personnel-down")
            .eq(this.personnelindex)
            .val(val.text);
        });
      },
      routerLink(event) {
        const _THIS = $(event.currentTarget);
        let listId = _THIS
          .closest(".GlobalTroubleNumber-addPersonnel")
          .find(".addPersonnel-name li");
        this.nameIDArr = [$(event.currentTarget).attr("data-maintenanceApplyId")];
        this.routerLinkIndex = _THIS.attr("data-index");
        this.inpTextData = _THIS
          .closest(".GlobalTroubleNumber-sendSingle")
          .find(".personnel-down")
          .val();
        this.$emit("HomeListIndex", _THIS.attr("data-index"));
        for (let i = 0, II = listId.length; i < II; i++) {
          this.nameIDArr.push(listId.eq(i).attr("data-copyToUserId"));
        }
        this.$router.push({
          path: "/MultipleChoiceAddressBook/" + this.nameIDArr
        });
      },
      evaluationFun(event) {
        this.switchEvaluationWin = true;
        let thisId = $(event.currentTarget).attr("data-entity");
        this.paramObj.maintenanceApplyId = thisId;
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
        this.paramObj.applyStatus = this.repairText || "yes";
        this.paramObj.appraiseWasFinished = this.repairText || "yes";
        this.api.starDataAll(this.paramObj).then(function(data) {
          (THIS.paramObj = {
            speedAppraise: "",
            serviceAppraise: "",
            operationSpecificationAppraise: "",
            appraiseDescribe: "",
            wasFinished: "",
            maintenanceApplyId: ""
          }),
          $(".list-star li i").removeClass("star");
          THIS.switchEvaluationWin = false;
          THIS.$emit("HomeListRefresh", "");
        });
      },
      initBScroll() {
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
    },
    components: {
      /* 复用组件名称 */
      // GlobalAddressBook
      HomeOrdinarySideOut,
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
    },
    updated: function() {
      this.initBScroll();
    },
    watch: {
      /* 监听 */
      ticketNumberData: function(val) {
        this.initBScroll();
        $(".GlobalTroubleNumber-list .personnel-down")
          .eq(this.routerLinkIndex)
          .val("");
        if (this.inpTextData != "") {
          this.$nextTick(function() {
            let inp = $(".GlobalTroubleNumber-list .personnel-down")
              .eq(this.routerLinkIndex)
              .val(this.inpTextData);
            console.log(inp);
          });
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #GlobalTroubleNumber {
    width: 100%;
    height: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    .slide-fade-enter-active {
      transition: all 0.3s ease;
    }
    .slide-fade-leave-active {
      transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
    }
    .slide-fade-enter,
    .slide-fade-leave-to {
      transform: translateX(10px);
      opacity: 0;
    }
    .GlobalTroubleNumber-main {
      .GlobalTroubleNumber-list {
        width: 100%;
        height: 100%;
        &>li {
          position: relative;
          margin-bottom: 20px;
          padding: 8px 30px 25px;
          width: 100%;
          height: 100%;
          background-color: #fff;
          border: solid 1px #dfdcdc;
          box-sizing: border-box;
          border-radius: 15px;
          .list-title {
            display: flex;
            align-items: center;
            margin-bottom: 18px;
            width: 100%;
            height: 74px;
            font-size: 0;
            border-bottom: 1px solid #dfdcdc;
            i {
              display: inline-block;
              margin-right: 13px;
              width: 27px;
              height: 33px;
              background: url("../../assets/home/sheet.png") no-repeat;
              background-size: 100% 100%;
            }
            p {
              color: #333;
              font-size: .44rem;
              line-height: .44rem;
              font-weight: 700;
            }
          }
          .list-siteDescription {
            display: flex;
            margin-bottom: 20px;
            padding-right: 4%;
            font-size: 0;
            box-sizing: border-box;
            li {
              width: 33.333333%;
              text-align: center;
              border-right: 1px solid #dfdcdc;
              box-sizing: border-box;
              .siteDescription-site {
                width: 100%;
                color: #333;
                font-size: .36rem;
                padding: 12px 0 6px 0;
                line-height: .36rem;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }
              .siteDescription-instructions {
                color: #999;
                font-size: $widthWeb16;
                padding: 8px 0 10px 0;
                line-height: $widthWeb16;
              }
            }
            li:nth-child(1) {
              flex: 29;
            }
            li:nth-child(2) {
              flex: 28;
            }
            li:nth-last-child(1) {
              flex: 43;
              border: 0;
            }
          }
          .list-faultDescription {
            margin-bottom: 12px;
            p {
              font-size: .36rem;
              line-height: .36rem;
              padding-bottom: 16px;
              &:nth-last-child(1) {
                padding-bottom: 0;
              }
              .faultDescription-timer {
                color: #fb7204;
              }
            }
          }
        }
      }
      .GlobalTroubleNumber-arrow {
        position: absolute;
        top: 110px;
        right: 14px;
        width: 48px;
        height: 54px;
        text-align: center;
        line-height: 54px;
        i {
          display: inline-block;
          width: 16px;
          height: 27px;
          background: url("../../assets/home/arrow.png") no-repeat;
          background-size: 100% 100%;
        }
      }
      .GlobalTroubleNumber-sendSingle {
        padding-top: 4px;
        border-top: 1px solid #dfdcdc;
        box-sizing: border-box;
        .sendSingle-personnel {
          position: relative;
          display: flex;
          align-items: center;
          width: 100%;
          height: 80px;
          .personnel-personnels {
            color: #333;
            font-size: .36rem;
          }
          .personnel-down {
            margin-left: 16px;
            padding-left: 38px;
            width: 330px;
            height: 40px;
            font-size: .32rem;
            line-height: 40px;
            border: 1px solid #dfdcdc;
            border-radius: 16px;
            box-sizing: border-box;
          }
          .personnel-btn {
            position: absolute;
            right: 0;
            padding: 6px 34px;
            font-size: .44rem;
            color: #fff;
            line-height: .44rem;
            background-color: #fd8521;
          }
        }
      }
      .GlobalTroubleNumber-addPersonnel {
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
          i {
            margin-right: 6px;
            display: inline-block;
            width: 20px;
            height: 20px;
            background: url("../../assets/home/dispatching.png") no-repeat;
            background-size: 100% 100%;
          }
          p {
            font-size: .28rem;
            color: #333;
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
                background: url("../../assets/home/mini_colse1.png") no-repeat;
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
      .GlobalTroubleNumber-maintenance {
        position: absolute;
        top: 0;
        right: 0;
        width: 125px;
        height: 74px;
        i {
          display: inline-block;
          width: 100%;
          height: 100%;
          background: url("../../assets/home/label_wx2.png") no-repeat;
          background-size: 100% 100%;
        }
        .maintenance-faultHandling {
          background: url("../../assets/home/label_wx1.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-toBeDispatch {
          background: url("../../assets/home/label_tobedispatch.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-toBeRepair {
          background: url("../../assets/home/label_toberepair.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-maintenance {
          background: url("../../assets/home/label_maintenance.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-repaired {
          background: url("../../assets/home/label_repaired2.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-noRepair {
          background: url("../../assets/home/label_norepair.png") no-repeat;
          background-size: 100% 100%;
        }
        .title-rated {
          background: url("../../assets/home/label_rated.png") no-repeat;
          background-size: 100% 100%;
        }
      }
      .GlobalTroubleNumber-evaluation {
        padding-top: 18px;
        border-top: 1px solid #dfdcdc;
        box-sizing: border-box;
        .evaluation-main {
          text-align: right;
          button {
            padding: 6px 34px;
            font-size: .44rem;
            color: #fff;
            line-height: .44rem;
            background-color: #fd8521;
          }
        }
      }
    }
    .sendSingleWin {
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
          font-size: .60rem;
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
              background: url("../../assets/home/close.png") no-repeat;
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
            font-size: .44rem;
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
              background-image: url("../../assets/home/close.png");
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
                font-size: .36rem;
                line-height: 60px;
              }
              .list-radio {
                font-size: 0;
                padding-left: 8px;
                .radio-repair {
                  margin-right: 30px;
                  font-size: .36rem;
                  color: #666;
                  line-height: 60px;
                  span {
                    display: inline-block;
                    width: 18px;
                    height: 18px;
                    background: url("../../assets/home/radio_no.png");
                    background-size: 100% 100%;
                  }
                  .selectedRadio {
                    background: url("../../assets/home/radio_sel.png");
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
                      background-image: url("../../assets/home/fivepoitedstar2.png");
                      background-size: 100% 100%;
                    }
                    .star {
                      background-image: url("../../assets/home/fivepoitedstar.png");
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
                  font-size: .36rem;
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
            font-size: .36rem;
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
