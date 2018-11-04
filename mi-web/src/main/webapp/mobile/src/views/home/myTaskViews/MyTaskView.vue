<template>
  <div id='MyTaskCom'>
    <div class="tabContent" ref="wrapper">
      <!--v-show = "selected == 'issued'"-->
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="loadBottom" :bottom-all-loaded="allLoaded" :auto-fill="false" ref="loadmore">
        <!--:top-method="loadTop"-->
        <ul class="lists">
          <li v-for="items in listsData">
            <!--<router-link  class="iconEvent" tag='div' to="/NoticeAnnouncement"><i></i></router-link>-->
            <div @click="itemClick(items)" class="iconEvent"><i></i></div>
            <div class="title">
              <div class="title-orderNo">
                <i></i>
                <div class="orderNo-fault">
                  <p><span>任务单号:</span>{{items.busiNo}}</p>
                </div>
              </div>
              <!--<p class="title-state" v-show="items.title=='维修申请'"></p>-->
              <p class="title-toBeIn" v-show="items.title.indexOf('入库')>=0"></p>
              <p class="title-toBeOut" v-show="items.title.indexOf('出库')>=0"></p>
              <p class="title-toBeReview" v-show="items.title.indexOf('申请')>=0"></p>
              <p class="state-back" v-show="items.title=='待派单'"></p>
              <p class="state-ling" v-show="items.title=='待接修'"></p>
              <p class="label_pending" v-show="items.title=='待录入维修结果'"></p>
              <p class="label_pending" v-show="items.title=='待评价'"></p>
              <p class="state-norepair" v-show="items.title=='未修复'"></p>
            </div>
            <div class="content">
              <div class="content_status">状态：{{items.title}}</div>
              <div class="content_remaker">描述：{{items.taskDescribe}}</div>
            </div>
          </li>
          <!-- <div class="noData" v-show="!listsData.length">
            <div class="NoDataGif"></div>
          </div> -->
        </ul>
      </mt-loadmore>
    </div>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <!-- Empty prompt -->
    <div class="gloobalEmptyPrompt" v-show="swtichEmptyPrompt">
      <!-- <div class="emptyPrompt"></div> -->
      <div class="emptyPrompt">暂 无 待 办 任务 !</div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'MyTaskCom',
    data() {
      return {
        swtichEmptyPrompt: false,
        bottomText: '上拉加载更多...',
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        isAutoFill: false,
        wrapperHeight: 0,
        listsData: [],
        searchKey: {
          userKey: localStorage.getItem('userKey'),
          start: 0,
          limit: 10
        },
        courrentNo: 0,
        swtichLoading: true
      }
    },
    methods: {
      loadTop() {
        this.courrentNo = 0;
        this.getPendingTaskList('top');
      },
      loadBottom() {
        // this.courrentNo += 1;
        // this.searchKey.limit = this.courrentNo * 5;
        // this.searchKey.start = this.courrentNo * 5;
        this.getPendingTaskList('bottom');
      },
      getPendingTaskList(type) {
        const _this = this;
        let data = this.searchKey;
        _this.$refs.loadmore.onBottomLoaded();
        this.requests.getPendingTaskList(data).then(function(response) {
          if (response.result == 'success') {
            _this.swtichLoading = false;
            const DATA = response.data.rows;
            _this.swtichEmptyPrompt = DATA.length == 0 ? true : false;
            for (let i = 0, L = DATA.length; i < L; i++) {
              _this.listsData.push(DATA[i]);
            }
            _this.searchKey.start = _this.listsData.length;
            if (_this.listsData.length >= response.data.totalCount) {
              _this.allLoaded = true;
            } else {
              _this.allLoaded = false;
            }
          }
          // if (type == 'top') {
          //   _this.$refs.loadmore.onTopLoaded();
          // } else if (type == 'bottom') {
          //   _this.$refs.loadmore.onBottomLoaded();
          // }
        })
      },
      itemClick: function(item) {
        this.$router.push(item.phoneUrl);
        // 如果页面需要显示查看状态，调用
        // console.log(item);
        // var data = {
        //   userkey: this.common.getLocalStorage('userKey'),
        //   status: 'alreadyRead',
        //   busiNo: item.busiNo,
        //   taskType: item.taskType
        // };
        // this.requests.updateTaskSatuts(data).then(function (response) {
        //   if (response.result == 'success') {
        //   }
        // });
        // return;
      }
    },
    mounted() {
      this.$nextTick(function() {
        this.wrapperHeight =
          document.documentElement.clientHeight -
          this.$refs.wrapper.getBoundingClientRect().top;
        // this.swtichLoading = false;
      });
    },
    created() {
      this.$nextTick(function() {
        this.getPendingTaskList();
      })
    },
  }
</script>

<style lang='scss' type="text/scss" scope>
  @import "../../../style/global.scss";
  #MyTaskCom {
    .gloobalEmptyPrompt {
      margin-top: 0;
      height: calc(100% - 80px);
    }
    .loadingGif {
      background: url("../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .NoDataGif {
      background: url("../../../assets/home/nosubject.png");
      background-size: 100% 100%;
    }
    .tabContent {
      width: 100%;
      height: calc(100%);
      overflow: auto;
      .mint-loadmore {
        .mint-loadmore-content {
          .lists {
            padding: 20px 20px 0;
            box-sizing: border-box;
            &>li {
              position: relative;
              margin-bottom: 20px;
              padding: 9px 30px 28px;
              border: solid 1px #dfdcdc;
              box-sizing: border-box;
              background-color: #fff;
              border-radius: 14px;
              .iconEvent {
                position: absolute;
                top: 120px;
                right: 40px;
                width: 32px;
                height: 54px;
                text-align: right;
                line-height: 54px;
                i {
                  display: inline-block;
                  width: 16px;
                  height: 27px;
                  background: url('../../../assets/home/arrow.png') no-repeat;
                  background-size: 100% 100%;
                }
              }
              .title {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
                width: 100%;
                height: 80px;
                border-bottom: 1px solid #dfdcdc;
                box-sizing: border-box;
                padding-bottom: 10px;
                .title-orderNo {
                  float: left;
                  width: 490px;
                  line-height: .44rem;
                  .orderNo-fault {
                    p {
                      width: calc(100% - 40px);
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                    }
                  }
                  i {
                    float: left;
                    margin-right: 13px;
                    width: 27px;
                    height: 32px;
                    background: url('../../../assets/home/sheet.png') no-repeat;
                    background-size: 100% 100%;
                  }
                  p {
                    font-size: .44rem;
                    font-weight: 700;
                    color: #333;
                  }
                }
                .title-state {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_wx1.png");
                  background-size: 100% 100%;
                }
                .title-toBeIn {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_putintostorage.png");
                  background-size: 100% 100%;
                }
                .title-toBeOut {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_outofstorage.png");
                  background-size: 100% 100%;
                }
                .title-toBeReview {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_check.png");
                  background-size: 100% 100%;
                }
                .state-back {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_tobedispatch.png");
                  background-size: 100% 100%;
                }
                .state-ling {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_toberepair.png");
                  background-size: contain;
                }
                .state-norepair {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_norepair.png");
                  background-size: 100% 100%;
                }
                .label_pending {
                  float: right;
                  width: 125px;
                  height: 74px;
                  background-image: url("../../../assets/home/label_pending.png");
                  background-size: 100% 100%;
                }
              }
              .content {
                margin-bottom: 18px;
                .content_status {
                  @extend .content_remaker
                }
                .content_remaker {
                  padding: 10px 0;
                  font-size: .36rem;
                }
              }
            }
          }
        }
      }
    }
  }
</style>
