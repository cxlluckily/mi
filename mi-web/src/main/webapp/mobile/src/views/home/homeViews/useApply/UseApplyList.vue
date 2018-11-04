<template>
  <div id='UseApplyList'>
    <div class="fault-btn">
      <button @click.stop="addbtnStorage('add','0')">新增</button>
      <button @click="searchSubmit">查询</button>
    </div>
    <div class="UseApplyList-main">
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <ul class="FaultInformation-list">
          <li v-for="(itemList , index) in tableDate" v-if="tableDate.length" :key="index">
            <section class="link_a">
              <div class="list-title">
                <i></i>
                <p>
                  单据号:{{itemList.applyNo}}
                </p>
                <div class="state-ling" v-if="itemList.applyType=='use'"></div>
                <div class="state-bo" v-if="itemList.applyType=='transfer'"></div>
                <div class="state-back" v-if="itemList.applyType=='return'"></div>
                <div class="state-back" v-if="itemList.applyType=='borrow'"></div>
              </div>
              <div class="list-fault">
                <div class="fault-describe">
                  <div class="fault-apply">{{itemList.applyUser}}</div>
                  <div class="label-apply">申请人</div>
                </div>
                <div class="fault-describe">
                  <div class="fault-apply">{{itemList.account}}</div>
                  <div class="label-apply">申请数量</div>
                </div>
                <div class="fault-describe">
                  <p class="fault-apply" v-if="itemList.applyStatus=='toBeReview'">待审核</p>
                  <p class="fault-apply" v-else-if="itemList.applyStatus=='reviewPass'">审核通过</p>
                  <p class="fault-apply" v-else-if="itemList.applyStatus=='reviewNoPass'">审核不通过</p>
                  <p class="fault-apply" v-else-if="itemList.applyStatus=='over'">完成</p>
                  <p class="fault-apply" v-else-if="itemList.applyStatus=='toBeOut'">待出库</p>
                  <p class="fault-apply" v-else-if="itemList.applyStatus=='toBeIn'">待入库</p>
                  <div class="label-apply">状态</div>
                  <div class="GlobalTrouble-arrow" @click="addbtnStorage('edit',itemList)"><i></i></div>
                </div>
              </div>
              <div class="list-fault">
                <div class="fault-describe">
                  <span class="label-timer">申请时间 : </span>
                  <span class="fault-timer">{{itemList.applyTime | initDate}}</span>
                </div>
              </div>
            </section>
          </li>
        </ul>
      </mt-loadmore>
    </div>
    <!--<div class="globalFooter">-->
    <!--<button @click="$router.back(-1)">返回</button>-->
    <!--</div>-->
    <!--侧边栏右滑-->
    <Search :switchHomeSearch="isSearchCom" @switchSearchCom=getSwitchSearchCom @SearchData="SearchCom">
    </Search>
    <div class="gloobalLoading" v-if="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <div class="gloobalEmptyPrompt" v-show="!tableDate.length">
      <div class="emptyPrompt">暂 无 数 据 !</div>
    </div>
  </div>
</template>

<script>
  import Search from "@/components/home/homeCom/Recipients";
  import Toast from "mint-ui";
  export default {
    name: "UseApplyList",
    data() {
      return {
        bottomText: "上拉加载更多...",
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        swtichLoading: true,
        applyType: "return",
        isSearchCom: false,
        tableDate: [],
        seacher: {},
        applyId: "",
        applyStatus: "all",
        paramsObj: {}
      };
    },
    methods: {
      /* 方法 */
      addbtnStorage(type, item) {
        let applyType = this.$route.query.applyType;
        /*设置applyType为了在选择列表中获取;*/
        sessionStorage.setItem("applyType", applyType);
        switch (applyType) {
          case "use":
            if (type == "add") {
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({
                name: "UseApplyListEdit",
                query: {
                  applyId: 0
                }
              });
            } else {
              if (item.applyStatus === "toBeReview") {
                sessionStorage.setItem("showType", "edit");
                this.$router.push({
                  name: "UseApplyListEdit",
                  query: {
                    applyId: item.applyId
                  }
                });
              } else {
                this.$router.push({
                  name: "UseApplylook",
                  query: {
                    applyId: item.applyId
                  }
                });
              }
            }
            break;
          case "transfer":
            if (type == "add") {
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({
                name: "TransferApplyEdit",
                query: {
                  applyId: 0
                }
              });
            } else {
              if (item.applyStatus === "toBeReview") {
                sessionStorage.setItem("showType", "edit");
                this.$router.push({
                  name: "TransferApplyEdit",
                  query: {
                    applyId: item.applyId
                  }
                });
              } else {
                this.$router.push({
                  name: "TransferApplyLook",
                  query: {
                    applyId: item.applyId
                  }
                });
              }
            }
            break;
          case "return":
            if (type == "add") {
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({
                name: "ReturnApplyEdit",
                query: {
                  applyId: 0
                }
              });
            } else {
              if (item.applyStatus === "toBeReview") {
                sessionStorage.setItem("showType", "edit");
                this.$router.push({
                  name: "ReturnApplyEdit",
                  query: {
                    applyId: item.applyId
                  }
                });
              } else {
                this.$router.push({
                  name: "ReturnApplyLook",
                  query: {
                    applyId: item.applyId
                  }
                });
              }
            }
            break;
          case "approval":
            if (type == "add") {
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({
                name: "UseExamination",
                query: {
                  applyId: 0
                }
              });
            } else {
              if (item.applyStatus === "toBeReview") {
                sessionStorage.setItem("showType", "edit");
                this.$router.push({
                  name: "UseExamination",
                  query: {
                    applyId: item.applyId
                  }
                });
              } else {
                this.$router.push({
                  name: "UseExaminationlook",
                  query: {
                    applyId: item.applyId
                  }
                });
              }
            }
            break;
        }
      },
      // 下拉加载数据
      issuedLoad() {
        this.paramsObj.start = this.paramsObj.start += 5;
        this.requests.requisitionInfor(this.paramsObj).then(respont => {
          if (respont.result == "success") {
            for (let i = 0, II = respont.data.rows.length; i < II; i++) {
              this.tableDate.push(respont.data.rows[i])
            }
            console.log(this.tableDate)
            if (this.tableDate.length >= respont.data.totalCount) {
              this.allLoaded = true;
            } else {
              this.allLoaded = false;
            }
            this.$nextTick(function() {
              this.$refs.loadmore.onBottomLoaded();
            });
          } else {
            return;
          }
        });
      },
      getSwitchSearchCom(is) {
        this.isSearchCom = is;
      },
      SearchCom(val) {
        const USERKEY = localStorage.getItem("userKey");
        const _this = this;
        const applyType = this.$route.query.applyType;
        this.paramsObj = {
          userKey: USERKEY,
          applyStatus: val.status,
          applyType: applyType,
          beginTime: val.start_time + "00:00:00",
          documentNo: val.troubleNum,
          endTime: val.end_time + "23:59:59",
          start: 0,
          limit: 5
        };
        this.tableDate = [];
        this.requests.requisitionInfor(this.paramsObj).then((respont) => {
          if (respont.result == "success") {
            for (let i = 0, II = respont.data.rows.length; i < II; i++) {
              this.tableDate.push(respont.data.rows[i])
            }
            if (this.tableDate.length >= respont.data.totalCount) {
              this.allLoaded = true;
            } else {
              this.allLoaded = false;
            }
          } else {
            return;
          }
        });
      },
      searchSubmit() {
        this.isSearchCom = !this.isSearchCom;
      },
      initData() {
        const USERKEY = localStorage.getItem("userKey");
        sessionStorage.setItem("isFirst", "yes");
        const beginTime = new Date();
        const endTime = new Date();
        beginTime.setDate(beginTime.getDate() - 30);
        const _this = this;
        const applyType = this.$route.query.applyType;
        this.paramsObj = {
          userKey: USERKEY,
          applyStatus: "all",
          applyType: applyType,
          beginTime: this.formatDate(beginTime) + "00:00:00",
          documentNo: "",
          endTime: this.formatDate(endTime.getTime()) + "23:59:59",
          start: 0,
          limit: 5
        };
        this.requests.requisitionInfor(this.paramsObj).then(respont => {
          if (respont.result == "success") {
            for (let i = 0, II = respont.data.rows.length; i < II; i++) {
              this.tableDate.push(respont.data.rows[i])
            }
            if (this.tableDate.length >= respont.data.totalCount) {
              this.allLoaded = true;
            } else {
              this.allLoaded = false;
            }
            console.log(_this.tableDate);
          } else {
            return;
          }
        });
      },
      formatDate(time) {
        const date = new Date(time);
        let year = date.getFullYear(),
          month = date.getMonth() + 1,
          day = date.getDate();
        let newTime = year + "-" + month + "-" + day + " ";
        return newTime;
      },
      checked(index) {
        this.applyId = this.tableDate[index].applyId;
      }
    },
    components: {
      Search
    },
    mounted: function() {
      this.initData();
      sessionStorage.setItem("userApplyEditEntity", null);
      sessionStorage.setItem("isFirst", "yes");
      this.$nextTick(function() {
        this.swtichLoading = false;
      });
    },
    update() {
      this.initData();
    }
  };
</script>

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";
  #UseApplyList {
    width: 100%;
    height: calc(100% - 80px);
    overflow: auto;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .fault-btn {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 100%;
      height: 100%;
      padding-right: 20px;
      height: 66px;
      box-sizing: border-box;
      button {
        padding: 10px 20px;
        font-size: $widthWeb16;
        line-height: $widthWeb16;
        color: #fff;
        background-color: #fd8521;
        border-radius: 6px;
        &:nth-child(1) {
          margin-right: 20px;
        }
      }
    }
    .UseApplyList-main {
      width: 100%;
      padding: 0 20px;
      height: calc(100% - 70px);
      box-sizing: border-box;
      overflow: scroll;
    }
    .FaultInformation-list {
      // width: 100%;
      // padding: 0 20px;
      // height: calc(100% - 70px);
      // box-sizing: border-box;
      // overflow: scroll;
      &>li {
        position: relative;
        padding: 8px 30px 25px;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        /*px*/
        box-sizing: border-box;
        border-radius: 15px;
        margin: 15px 0;
        &:first-child {
          margin-top: 0;
        }
        .checkbox {
          position: absolute;
          bottom: 0px;
          right: 15px;
          padding: 10px 20px;
          border-radius: 5px;
          border: 1px solid #ddd;
          background: #fb7204;
          color: #fff;
        }
        .link_a {
          display: inline-block;
          color: #000;
          width: 100%;
          .list-title {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            width: 100%;
            height: 74px;
            font-size: .44rem;
            border-bottom: 1px solid #dfdcdc;
            /*px*/
            i {
              display: inline-block;
              margin-right: 13px;
              width: 30px;
              height: 33px;
              background-image: url("../../../../assets/home/sheet.png");
              background-size: 100% 100%;
            }
            p {
              color: #333;
              font-size: .44rem;
              font-weight: 700;
            }
            .title-state {
              position: absolute;
              top: 0;
              right: 0;
              width: 125px;
              height: 74px;
              background-image: url("../../../../assets/home/label_putintostorage.png");
              background-size: 100% 100%;
            }
            .state-back {
              position: absolute;
              top: 0;
              right: 0;
              width: 125px;
              height: 74px;
              background-image: url("../../../../assets/home/label_back.png");
              background-size: 100% 100%;
            }
            .state-ling {
              position: absolute;
              top: 0;
              right: 0;
              width: 125px;
              height: 74px;
              background-image: url("../../../../assets/home/label_ling.png");
              background-size: contain;
            }
            .state-bo {
              position: absolute;
              top: 0;
              right: 0;
              width: 125px;
              height: 74px;
              background-image: url("../../../../assets/home/label_bo.png");
              background-size: 100% 100%;
            }
          }
          .list-fault {
            display: flex;
            justify-content: space-between;
            align-content: center;
            margin-bottom: 12px;
            border-bottom: 2px solid #e0dcdd;
            /*px*/
            .GlobalTrouble-arrow {
              position: absolute;
              top: 102px;
              right: 14px;
              width: 48px;
              height: 54px;
              text-align: center;
              line-height: 54px;
              i {
                display: inline-block;
                width: 16px;
                height: 27px;
                background: url("../../../../assets/home/arrow.png") no-repeat;
                background-size: 100% 100%;
              }
            }
            &:last-child {
              border-bottom: none;
            }
            .fault-describe {
              flex: 1;
              border-right: 2px solid #e0dcdd;
              /*px*/
              margin: 10px 0 20px;
              &:last-child {
                border-right: none;
                margin: 0;
              }
              .fault-apply {
                font-size: .36rem;
                text-align: center;
                padding: 10px 0;
              }
              .label-apply {
                font-size: .28rem;
                text-align: center;
                padding: 5px 0;
                color: #9a9a9a;
              }
              .fault-timer {
                color: #fc851f;
                font-size: .36rem;
              }
              .label-timer {
                font-size: .36rem;
              }
            }
          }
        }
      }
    }
  }
</style>
