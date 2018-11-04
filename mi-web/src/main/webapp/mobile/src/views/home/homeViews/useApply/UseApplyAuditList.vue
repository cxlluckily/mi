<template>
  <div id='UseApplyAuditList'>
    <div class="fault-btn">
      <!--<span @click.stop="addbtnStorage('add','0')">新增</span>-->
      <button @click="searchSubmit">查询</button>
      <!--<i class="query_icon"></i>-->
    </div>
    <div class="UseApplyAuditList-main">
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded"
                   ref="loadmore" :autoFill='noInitialize'>
        <ul class="FaultInformation-list">
          <li v-for="(item , index) in tableDate" v-if="tableDate.length" :key="index">
            <section class="link_a">
              <div class="list-title">
                <i></i>
                <p>
                  单据号:{{item.applyNo}}
                </p>
                <div class="state-ling" v-if="item.applyType=='use'"></div>
                <div class="state-bo" v-if="item.applyType=='transfer'"></div>
                <div class="state-back" v-if="item.applyType=='return'"></div>
              </div>
              <div class="list-fault">
                <div class="fault-describe">
                  <div class="fault-apply">{{item.applyUser}}</div>
                  <div class="label-apply">申请人</div>
                </div>
                <div class="fault-describe">
                  <div class="fault-apply" v-if="item.applyType=='return'">返还申请</div>
                  <div class="fault-apply" v-if="item.applyType=='use'">领用申请</div>
                  <div class="fault-apply" v-if="item.applyType=='transfer'">调拨申请</div>
                  <div class="label-apply">申请类型</div>
                </div>
                <div class="fault-describe">
                  <div class="fault-apply" v-if="item.applyStatus=='toBeReview'">
                    待审核
                  </div>
                  <div class="fault-apply" v-if="item.applyStatus=='reviewPass'">
                    审核通过
                  </div>
                  <div class="fault-apply" v-if="item.applyStatus=='reviewNoPass'">
                    审核不通过
                  </div>
                  <div class="fault-apply" v-if="item.applyStatus=='over'">
                    完成
                  </div>
                  <div class="fault-apply" v-if="item.applyStatus=='toBeOut'">
                    待领取
                  </div>
                  <div class="fault-apply" v-if="item.applyStatus=='toBeIn'">
                    待归还
                  </div>
                  <div class="label-apply">状态</div>
                  <div class="GlobalTrouble-arrow" @click="addbtnStorage('edit',item)"><i></i></div>
                </div>
              </div>
              <div class="list-footer">
                <div class="fault-f">
                  <span class="label-f">出库仓库 : </span>
                  <span class="fault-opanm">{{item.outWarehouseName}}</span>
                </div>
                <div class="fault-f">
                  <span class="label-f">申请时间 : </span>
                  <span class="fault-timer">{{item.applyTime|initDate}}</span>
                </div>
              </div>
            </section>
          </li>
        </ul>
      </mt-loadmore>
    </div>
    <!--侧边栏右滑-->
    <!-- <Search :isSearch="isSearchCom" @switchSearchCom=getSwitchSearchCom @SearchCom="SearchCom" @searchStatus="searchStatus">
            </Search> -->
    <Search :switchHomeSearch="isSearchCom" :switchHomeSearchApplyType="true" @switchSearchCom=getSwitchSearchCom
            @SearchData="SearchCom">
    </Search>
    <!--<div class="globalFooter">-->
    <!--<button @click="$router.back(-1)">返回</button>-->
    <!--</div>-->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <div class="gloobalEmptyPrompt" v-if="!tableDate.length">
      <div class="emptyPrompt">暂无“{{emptyMessage}}”数据</div>
    </div>
  </div>
</template>

<script>
  import Search from "@/components/home/homeCom/Recipients";
  import Toast from "mint-ui";

  export default {
    name: "UseApplyAuditList",
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
        applyStatus: "toBeReview",
        paramsObj: {},
        emptyMessage: '待审核'
      };
    },
    methods: {
      /* 方法 */
      addbtnStorage(type, item) {
        let applyType = this.$route.query.applyType;
        if (item.applyStatus === "toBeReview") {
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
      },
      // 下拉刷新
      issuedLoad() {
        this.paramsObj.start = this.paramsObj.start += 5;
        this.requests.getPagerInfoForAudit(this.paramsObj).then(respont => {
          if (respont.result == "success") {
            for (let i = 0, II = respont.data.rows.length; i < II; i++) {
              this.tableDate.push(respont.data.rows[i]);
            }
            if (this.tableDate.length >= respont.data.totalCount) {
              this.allLoaded = true;
            } else {
              this.allLoaded = false;
            }
            this.$nextTick(function () {
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
      searchStatus(val) {
        this.applyStatus = val;
      },
      SearchCom(val) {
        const USERKEY = localStorage.getItem("userKey");
        const _this = this;
        this.paramsObj = {
          userKey: USERKEY,
          applyStatus: val.status,
          applyType: val.applyType,
          beginTime: val.start_time + "00:00:00",
          documentNo: val.troubleNum,
          endTime: val.end_time + "23:59:59",
          start: 0,
          limit: 5
        };
        this.tableDate = [];
        this.requests
          .getPagerInfoForAudit(this.paramsObj)
          .then((respont) => {
            if (respont.result == "success") {
              for (let i = 0, II = respont.data.rows.length; i < II; i++) {
                this.tableDate.push(respont.data.rows[i]);
              }
              if (this.tableDate.length >= respont.data.totalCount) {
                this.allLoaded = true;
              } else {
                this.allLoaded = false;
              }
            } else {
              Toast(respont.message);
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
        this.paramsObj = {
          userKey: USERKEY,
          applyStatus: "toBeReview",
          applyType: "all",
          beginTime: this.formatDate(beginTime) + " 00:00:00",
          documentNo: "",
          endTime: this.formatDate(endTime.getTime()) + " 23:59:59",
          start: 0,
          limit: 5
        };
        _this.requests.getPagerInfoForAudit(this.paramsObj).then(respont => {
          if (respont.result == "success") {
            for (let i = 0, II = respont.data.rows.length; i < II; i++) {
              this.tableDate.push(respont.data.rows[i]);
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
    mounted: function () {
      this.initData();
      sessionStorage.setItem("userApplyEditEntity", null);
      sessionStorage.setItem("isFirst", "yes");
      this.$nextTick(function () {
        this.swtichLoading = false;
      });
    },
    update() {
      this.initData();
    },
    watch: {
      paramsObj: function (val) {
        switch (val.applyStatus) {
          case 'all':
            this.emptyMessage = '全部';
            break;
          case 'toBeReview':
            this.emptyMessage = '待审核';
            break;
          case 'reviewPass':
            this.emptyMessage = '审核通过';
            break;
          case 'reviewNoPass':
            this.emptyMessage = '审核不通过';
            break;
          case 'over':
            this.emptyMessage = '已完成';
            break;
          case 'toBeOut':
            this.emptyMessage = '待领取';
            break;
          case 'toBeIn':
            this.emptyMessage = '待归还';
            break;
        }
      }
    }
  };
</script>

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";

  #UseApplyAuditList {
    width: 100%;
    height: 100%;
    padding-bottom: 10px;
    overflow: auto;
    overflow: hidden;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .NoDataGif {
      background: url("../../../../assets/home/nosubject.png");
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
      }
    }
    .UseApplyAuditList-main {
      width: 100%;
      padding: 0 20px;
      height: calc(100% - 144px);
      box-sizing: border-box;
      overflow: scroll;
    }
    .FaultInformation-list {
      position: relative;
      & > li {
        position: relative;
        padding: 8px 30px 25px;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        /*px*/
        box-sizing: border-box;
        border-radius: 15px;
        margin-bottom: 20px;
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
            margin-bottom: 18px;
            width: 100%;
            height: 74px;
            font-size: 0;
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
            margin-bottom: 10px;
            border-bottom: 2px solid #e0dcdd;
            /*px*/
            .fault-describe {
              flex: 1;
              border-right: 2px solid #e0dcdd;
              /*px*/
              margin: 10px 0 20px;
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
                border-right: none;
              }
              .fault-apply {
                font-size: .36rem;
                text-align: center;
                padding: 5px 0;
              }
              .label-apply {
                font-size: .28rem;
                text-align: center;
                padding: 10px 0;
                color: #9a9a9a;
              }
            }
          }
          .list-footer {
            .fault-f {
              padding: 5px 0;
              .fault-timer {
                color: #fc851f;
                font-size: .36rem;
              }
              .label-f {
                font-size: .36rem;
              }
              .fault-opanm {
                @extend .label-f;
              }
            }
          }
        }
      }
    }
  }
</style>
