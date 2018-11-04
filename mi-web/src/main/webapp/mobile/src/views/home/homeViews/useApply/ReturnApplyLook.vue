<template>
  <div id='UseApplylook'>
    <div class="UseApplylook-main">
      <ul class="main-details">
        <li v-for="(itemDetails, index) in detailsData" :key="index">
          <p>{{itemDetails.title}}</p>
          <p v-if="itemDetails.initTimer">{{itemDetails.text | initDate}}</p>
          <p v-else>{{itemDetails.text}}</p>
        </li>
      </ul>
      <div class="main-listing">
        <p class="listing-title">{{listingTitle}}</p>
        <div class="listing-outbound">
          <ul class="outbound-list">
            <li v-for="(itemParts, index) in partsDetailsData" :key="index">
              <p>{{itemParts.partName}}<span>{{itemParts.applyCount}}个</span></p>
              <div class="list-parts">
                <div class="parts-img">
                  <img v-if="itemParts.imageUrl != 'noImage'" :src="itemParts.imageUrl">
                  <img v-else src="../../../../assets/home/pic3.png">
                </div>
                <ul>
                  <li>
                    <p>{{itemParts.inventoryType == 'notUnique'? '否':'是'}}</p>
                    <p>唯一标识</p>
                  </li>
                  <li>
                    <p>{{itemParts.houseNo || '暂无!'}}</p>
                    <p>房间号</p>
                  </li>
                  <li>
                    <p>{{itemParts.stockStatus == 'normal'?'好件':'坏件'}}</p>
                    <p>备件状态</p>
                  </li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="fallback">返回</button>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "UseApplylook",
    data() {
      return {
        swtichLoading: true,
        detailsData: [],
        partsDetailsData: [],
        listingTitle: ''
      };
    },
    methods: {
      /* 方法 */
      initData() {
        const USERKEY = localStorage.getItem('userKey'),
          PROMPOBJ = {
            applyId: this.$route.query.applyId,
            userKey: USERKEY
          };
        this.requests.getApplyInfoByApplyId(PROMPOBJ).then((data) => {
          const DATA = data.data;
          this.partsDetailsData = DATA.details; // 出库详情信息
          this.detailsData.push({
            title: '申请单号',
            text: DATA.applyNo || '暂无数据!'
          }, {
            title: '出库仓库',
            text: DATA.outWareHouseName || '暂无数据!'
          }, {
            title: '申请人',
            text: DATA.applyUser || '暂无数据!'
          }, {
            title: '申请性质',
            text: DATA.applyType
          }, {
            title: '申请时间',
            text: DATA.applyTime,
            initTimer: true
          }, {
            title: '申请单状态',
            text: DATA.applyStatus
          }, {
            title: '备注',
            text: DATA.applyRemark || '暂无数据!',
          }, {
            title: '审核人',
            text: DATA.auditUser || '暂无数据!',
          }, {
            title: '审核时间',
            text: DATA.auditTime,
            initTimer: true
          }, {
            title: '审核意见',
            text: DATA.auditRemark || '暂无数据!',
          })
          switch (this.detailsData[3].text) {
            case 'borrow':
              this.detailsData[3].text = '借用申请';
              this.listingTitle = '借用清单';
              break;
            case 'return':
              this.detailsData[3].text = '返还申请';
              this.listingTitle = '返还清单';
              break;
            case 'use':
              this.detailsData[3].text = '领用申请';
              this.listingTitle = '领用清单';
              break;
            case 'transfer':
              this.detailsData[3].text = '调拨申请';
              this.listingTitle = '调拨清单';
              break;
          }
          switch (this.detailsData[5].text) {
            case 'toBeIn':
              this.detailsData[5].text = '待归还';
              break;
            case 'toBeReview':
              this.detailsData[5].text = '待审核';
              break;
            case 'reviewPass':
              this.detailsData[5].text = '审核通过';
              break;
            case 'reviewNoPass':
              this.detailsData[5].text = '审核不通过';
              break;
            case 'over':
              this.detailsData[5].text = '已完成';
              break;
            case 'toBeOut':
              this.detailsData[5].text = '待出库';
              break;
          }
          this.$nextTick(function() {
            this.swtichLoading = false;
          });
        })
      },
      fallback() {
        if (this.$route.query.taskNotice == 'true') {
          this.goBackMyTask()
        } else {
          this.$router.push({
            path: '/UseApplyList',
            query: {
              'applyType': 'return'
            }
          })
        }
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData();
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
  #UseApplylook {
    width: 100%;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .UseApplylook-main {
      width: 100%;
      padding: 20px 20px 100px;
      background-color: #faf6ec;
      box-sizing: border-box;
      .main-details {
        margin-bottom: 20px;
        padding: 20px;
        width: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 18px;
        &>li {
          padding: 0 20px;
          width: 100%;
          display: flex;
          justify-content: space-between;
          border-bottom: solid 1px #dfdcdc;
          box-sizing: border-box;
          &:nth-last-child(1) {
            border: 0;
          }
          p {
            font-size: $widthWeb22;
            color: #bfbfbf;
            line-height: 64px;
            text-align: right;
          }
          & p:nth-last-child(1) {
            width: 66%;
            color: #333;
          }
        }
      }
      .main-listing {
        padding-top: 26px;
        width: 100%;
        height: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        border-radius: 18px;
        box-sizing: border-box;
        overflow: hidden;
        overflow-y: auto;
        .listing-title {
          width: 100%;
          padding-left: 36px;
          font-size: $widthWeb22;
          color: #fd8521;
          line-height: $widthWeb22;
          box-sizing: border-box;
        }
        .listing-outbound {
          width: 100%;
          padding: 20px;
          box-sizing: border-box;
          .outbound-list {
            width: 100%;
            &>li {
              padding-left: 18px;
              padding-bottom: 20px;
              border-bottom: solid 1px #dfdcdc;
              box-sizing: border-box;
              &:nth-last-child(1) {
                border: 0;
              }
              p {
                font-size: $widthWeb18;
                color: #333;
                line-height: 58px;
                span {
                  margin-left: 24px;
                  color: #fd8521;
                }
              }
              .list-parts {
                width: 100%;
                display: flex;
                align-items: center;
                .parts-img {
                  margin-right: 14px;
                  width: 70px;
                  height: 70px;
                  img {
                    display: inline-block;
                    width: 100%;
                    height: 100%;
                  }
                }
                &>ul {
                  flex: 1;
                  display: flex;
                  li {
                    flex: 1;
                    text-align: center;
                    border-right: solid 1px #dfdcdc;
                    box-sizing: border-box;
                    &:nth-last-child(1) {
                      border: 0;
                    }
                    p {
                      font-size: $widthWeb18;
                      color: #333;
                      line-height: 36px;
                      &:nth-last-child(1) {
                        margin-top: 4px;
                        font-size: $widthWeb14;
                        color: #bfbfbf;
                        line-height: 26px;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
</style>
