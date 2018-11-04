<template>
  <div id='Outbound'>
    <HomeInventoryTitle :inventoryTitle="childInventoryTitle" :inventoryRouterPath="childInventoryRouterPath" :swtichInventorySearch="childswtichInventorySearch" :inventoryType="childInventoryType" @timerDeta='getTimerDeta'>
    </HomeInventoryTitle>
    <div class="Outbound-main">
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="issuedLoad" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill='noInitialize'>
        <ul class="main-list">
          <li v-for="itemList in childInventoryOrderNoListData" @click.stop="contentDetails($event,itemList.inventoryOrderStatus)" :key="itemList.inventoryOrderID" :data-detailsId='itemList.inventoryOrderID +","+itemList.inventoryOrderSubjectID'>
            <div class="list-title">
              <i></i>
              <p>单据号 : {{itemList.inventoryOrderApplyNo}}</p>
              <div :class="{'title-state':itemList.inventoryOrderStatus == '待出库'}"></div>
            </div>
            <ul class="list-content">
              <li>
                <div>{{itemList.inventoryOrderName}}</div>
                <div>仓库名称</div>
              </li>
              <li>
                <div>{{itemList.inventoryOrderNum}}</div>
                <div>出库备件数</div>
              </li>
            </ul>
            <div class="list-footer">
              <p v-if="itemList.inventoryOrderType == 'useOut'">出库性质 : 领用出库</p>
              <p v-else-if="itemList.inventoryOrderType == 'transferOut'">出库性质 : 调拨出库</p>
              <p v-else-if="itemList.inventoryOrderType == 'borrowOut'">出库性质 : 借用出库</p>
              <p v-else-if="itemList.inventoryOrderType == 'returnOut'">出库性质 : 返还出库</p>
              <p>出库时间 : <span class="fault-timer">{{itemList.inventoryOrderTimer | initDate}}</span></p>
              <button @click.stop="btnFun($event)" v-if="itemList.inventoryOrderStatus == '待出库'" :data-detailsId='itemList.inventoryOrderID +","+itemList.inventoryOrderSubjectID'>出库</button>
            </div>
          </li>
        </ul>
      </mt-loadmore>
    </div>
    <router-view class="routerView"></router-view>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <!-- Empty prompt -->
    <div class="gloobalEmptyPrompt" v-show="swtichEmptyPrompt">
      <!-- <div class="emptyPrompt"></div> -->
      <div class="emptyPrompt">暂 无 数 据 !</div>
    </div>
  </div>
</template>

<script>
import HomeInventoryListing from "@/components/home/homeCom/HomeInventoryListing";
import HomeInventoryTitle from "@/components/home/homeCom/HomeInventoryTitle";
import HomeInventoryOrderNoList from "@/components/home/homeCom/HomeInventoryOrderNoList";
export default {
  name: "Outbound",
  data() {
    return {
      swtichLoading: true,
      swtichEmptyPrompt: false,
      bottomText: "上拉加载更多...",
      allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
      noInitialize: false, // 是否默认填充数据
      childInventoryTitle: "借用出库",
      childInventoryType: "ck",
      // childInventoryRouterPath: "/NewLibrary",
      childInventoryRouterPath: "/BorrowingOutDetails",
      childswtichInventorySearch: true,
      prompObj: {
        applyUser: "",
        outStockApplyNO: "",
        beginTime: "",
        endTime: "",
        outApplyStatus: "all",
        outOrderType: "all",
        limit: 5,
        start: 0,
        outWarehouseId: "0"
      },
      childInventoryOrderNoListData: []
    };
  },
  methods: {
    /* 方法 */
    contentDetails(event, type) {
      // if(type == '待出库') return;
      // const DETAILS = $(event.currentTarget).attr("data-detailsId");
      // this.$router.push({
      //   path: "/OutboundDetails/" + DETAILS
      // });
      const DETAILSDETAILS = $(event.currentTarget).attr("data-detailsId"),
        DETAILS = $(event.currentTarget)
          .attr("data-detailsId")
          .split(",");
      if (type == "待出库") {
        this.$router.push({
          path: "/OutboundThrow",
          query: {
            outStockApplyId: DETAILS[0],
            operationSubjectId: DETAILS[1]
          }
        });
      } else {
        this.$router.push({
          path: "/OutboundDetails/" + DETAILSDETAILS
        });
      }
    },
    btnFun(event) {
      const DETAILS = $(event.currentTarget)
        .attr("data-detailsId")
        .split(",");
      this.$router.push({
        path: "/OutboundThrow",
        query: {
          outStockApplyId: DETAILS[0],
          operationSubjectId: DETAILS[1]
        }
      });
    },
    issuedLoad() {
      this.prompObj.start = this.prompObj.start += 5;
      this.initData({
        initLoadmore: true
      });
    },
    initAllLoaded() {
      let mainHeight = $(".Outbound-main").height();
      let loadmoreHeight = $(".mint-loadmore").height();
      if (mainHeight >= loadmoreHeight) {
        this.allLoaded = true;
      }
    },
    // 获取前一个月的时间
    timerMonth() {
      const nowdate = new Date();
      nowdate.setMonth(nowdate.getMonth() - 3);
      const y = nowdate.getFullYear(),
        m = nowdate.getMonth() + 1,
        d = nowdate.getDate(),
        dateMonths =
          y + "-" + (m >= 10 ? m : "0" + m) + "-" + (d >= 10 ? d : "0" + d);
      console.log(dateMonths);
      return dateMonths;
    },
    // 当前时间
    initTimer(timer) {
      if (timer == "") {
        return "";
      }
      var date = new Date(timer);
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      var d = date.getDate();
      d = d < 10 ? "0" + d : d;
      return y + "-" + m + "-" + d;
    },
    getTimerDeta(val) {
      this.swtichLoading = true;
      if (val.beginTime != "") {
        this.prompObj.beginTime = val.beginTime + " 00:00:00";
      }
      if (val.endTime != "") {
        this.prompObj.endTime = val.endTime + " 23:59:59";
      }
      this.prompObj.outApplyStatus = val.inStockStatus;
      this.prompObj.outOrderType = val.inStockType;
      this.prompObj.outWarehouseId = val.warehouseId;
      this.prompObj.limit = 5;
      this.prompObj.start = 0;
      this.childInventoryOrderNoListData = [];
      this.initData({
        loadding: true,
        initTimer: true
      });
    },
    initData({ initTimer = false, initLoadmore = false, loadding = false }) {
      if (initTimer) {
        this.prompObj.beginTime = this.timerMonth() + " 00:00:00";
        let strTimer1 = new Date().getTime();
        this.prompObj.endTime = this.initTimer(strTimer1) + " 23:59:59";
      }
      this.api.outboundInitDataAllprompObj(this.prompObj).then(data => {
        const DATA = data.data.rows;
        console.log(DATA);
        this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
        for (let i = 0, II = DATA.length; i < II; i++) {
          this.childInventoryOrderNoListData.push({
            inventoryOrderID: DATA[i].outStockApplyId,
            inventoryOrderSubjectID: DATA[i].operationSubjectId,
            inventoryOrderName: DATA[i].warehouseName,
            inventoryOrderApplyNo: DATA[i].outStockApplyNO,
            inventoryOrderTimer: DATA[i].applyDate,
            inventoryOrderStatus: DATA[i].outApplyStatus,
            inventoryOrderType: DATA[i].outOrderType,
            inventoryOrderNum: DATA[i].alreadyOutCount
          });
        }
        if (this.childInventoryOrderNoListData.length >= data.data.totalCount) {
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
    }
  },
  components: {
    /* 复用组件名称 */
    HomeInventoryTitle,
    HomeInventoryOrderNoList,
    HomeInventoryListing
  },
  mounted: function() {
    /* 初始化数据 */
    this.initAllLoaded();
    this.initData({
      loadding: true,
      initTimer: true
    });
  },
  watch: {
    /* 监听 */
    $route(to, from) {
      console.log(to);
      console.log(from);
    }
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#Outbound {
  width: 100%;
  .loadingGif {
    background: url("../../../../assets/home/loading.gif");
    background-size: 100% 100%;
  }
  .Outbound-main {
    width: 100%;
    padding: 0 20px;
    height: calc(100% - 66px);
    box-sizing: border-box;
    overflow: scroll;
    .main-list {
      & > li {
        position: relative;
        margin-bottom: 20px;
        padding: 0 30px;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 15px;
        .list-title {
          display: flex;
          align-items: center;
          width: 100%;
          height: 80px;
          font-size: 0;
          i {
            display: inline-block;
            margin-right: 13px;
            width: 27px;
            height: 33px;
            background-image: url("../../../../assets/home/sheet.png");
            background-size: 100% 100%;
          }
          p {
            color: #333;
            font-size: .44rem;
            line-height: .44rem;
            font-weight: 700;
          }
          div {
            position: absolute;
            top: 0;
            right: 0;
            width: 125px;
            height: 74px;
            background-image: url("../../../../assets/home/label_outofstorage2.png");
            background-size: 100% 100%;
          }
          .title-state {
            background-image: url("../../../../assets/home/label_outofstorage.png");
          }
        }
        .list-content {
          border-top: solid 1px #dfdcdc;
          border-bottom: solid 1px #dfdcdc;
          padding: 20px 0;
          display: flex;
          width: 100%;
          li {
            flex: 1;
            text-align: center;
            border-right: solid 1px #dfdcdc;
            &:last-child {
              border-right: solid 0px #dfdcdc;
            }
            div:nth-child(1) {
              padding: 12px 0 16px 0;
              // font-weight: 700;
              font-size: .36rem;
              color: #333;
            }
            div:nth-child(2) {
              padding-bottom: 12px;
              font-size: .32rem;
              line-height: .32rem;
              color: #999;
            }
          }
        }
        .list-footer {
          padding: 15px 0;
          position: relative;
          p {
            font-size: .36rem;
            line-height: .36rem;
            &:nth-child(1) {
              padding: 8px 0 12px 0;
            }
            &:nth-child(2) {
              padding-bottom: 8px;
            }
          }
          button {
            position: absolute;
            top: 50%;
            right: 15px;
            transform: translateY(-50%);
            padding: 8px 20px;
            font-size: .36rem;
            line-height: .36rem;
            color: #fff;
            background-color: #fd8521;
            border-radius: 6px;
          }
          .fault-timer {
            color: #fb7204;
          }
        }
        .list-btn {
          padding-right: 20px;
          width: 100%;
          text-align: right;
          box-sizing: border-box;
          button {
            padding: 9px 22px;
            font-size: .32rem;
            line-height: .32rem;
            color: #fafafc;
            background-color: #fd8521;
            border-radius: 6px;
          }
        }
      }
    }
  }
  .routerView {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 200;
    width: 100%;
    height: 100%;
    background-color: #faf6ec;
  }
}
</style>
