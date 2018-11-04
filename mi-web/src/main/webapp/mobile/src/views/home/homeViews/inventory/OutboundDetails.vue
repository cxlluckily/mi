<template>
  <div id='OutboundDetails'>
    <div class="OutboundDetails-main">
      <ul class="main-details">
        <li v-for="(itemDetails, index) in applyEntity" :key="index">
          <p>{{itemDetails.title}}</p>
          <p v-if="itemDetails.initTimer">{{itemDetails.text | initDate}}</p>
          <p v-else>{{itemDetails.text}}</p>
        </li>
      </ul>
      <div class="main-listing">
        <p class="listing-title">出库清单</p>
        <!-- <div class="listing-inventory">
                        <ul class="inventory-list">
                          <li v-for="listItem in outStockApplyDetailEntities" :key="listItem.outStockApplyDetailId">
                            <div class="list-img">
                              <img src="../../../../assets/home/pic3.png">
                            </div>
                            <div class="list-content">
                              <p>{{listItem.partName}}</p>
                              <p>出库数量 :<span>{{listItem.alreadyOutCount}}{{listItem.units}}</span></p>
                            </div>
                          </li>
                        </ul>
                      </div> -->
        <div class="listing-outbound">
          <ul class="outbound-list">
            <li v-for="(itemParts, index) in outStockDetailEntities" :key="index">
              <p>{{itemParts.partName}}<span>{{itemParts.outCount}}个</span></p>
              <div class="list-parts">
                <div class="parts-img">
                  <img v-if="itemParts.imageUrl" :src="itemParts.imageUrl">
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
                    <p>{{itemParts.shelfNumber || '暂无!'}}</p>
                    <p>货架号</p>
                  </li>
                </ul>
              </div>
              <div class="list-qrCode">
                <p>二维码 :<span>{{itemParts.qrCode || '暂无二维码!'}}</span></p>
                <p>备件状态 :<span>{{itemParts.stockStatus == 'normal'?'好件':'坏件'}}</span></p>
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
    name: "OutboundDetails",
    data() {
      return {
        swtichLoading: true,
        applyEntity: [],
        outStockApplyDetailEntities: [],
        outStockDetailEntities: [],
      };
    },
    methods: {
      /* 方法 */
      initData() {
        const PROMPARR = this.$route.params.id.split(','),
          PROMPOBJ = {
            outStockApplyId: PROMPARR[0],
            operationSubjectId: PROMPARR[1]
          }
        this.api.outboundDetailsDataAllprompObj(PROMPOBJ).then((data) => {
          const DATA = data.data,
            APPLYENTITY = DATA.applyEntity, // 出库详情信息
            OUTSTOCHAPPLYDATEILENTITIES = DATA.outStockApplyDetailEntities, // 出库库存详情
            OUTSTOCHDETAILENTITIES = DATA.outStockDetailEntities; // 出库详情
          console.log(DATA)
          this.applyEntity.push({
            title: '关联单据号',
            text: APPLYENTITY.applyNo || '暂无数据!'
          }, {
            title: '申请人',
            text: APPLYENTITY.applyUser || '暂无数据!'
          }, {
            title: '审核人',
            text: APPLYENTITY.auditUser || '暂无数据!'
          }, {
            title: '申请单备注',
            text: APPLYENTITY.applyRemark || '暂无数据!'
          }, {
            title: '出库单号',
            text: APPLYENTITY.outStockApplyNO || '暂无数据!'
          }, {
            title: '出库仓库',
            text: APPLYENTITY.warehouseName || '暂无数据!'
          }, {
            title: '出库性质',
            text: APPLYENTITY.outOrderType || '暂无数据!'
          }, {
            title: '状态',
            text: APPLYENTITY.outApplyStatus == 'toBeOut' ? '待出库' : '已出库'
          }, {
            title: '出库人',
            text: APPLYENTITY.outUser || '暂无数据!'
          }, {
            title: '操作时间',
            text: APPLYENTITY.modifyTime || '暂无数据!',
            initTimer: true
          }, {
            title: '出库备注',
            text: APPLYENTITY.remark || '暂无备注!'
          })
          switch (this.applyEntity[6].text) {
            case 'useOut':
              this.applyEntity[6].text = '领用出库';
              break;
            case 'transferOut':
              this.applyEntity[6].text = '调拨出库';
              break;
            case 'borrowOut':
              this.applyEntity[6].text = '借用出库';
              break;
            case 'returnOut':
              this.applyEntity[6].text = '返还出库';
              break;
          }
          this.outStockApplyDetailEntities = OUTSTOCHAPPLYDATEILENTITIES;
          this.outStockDetailEntities = OUTSTOCHDETAILENTITIES;
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
            path: '/Outbound'
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
  #OutboundDetails {
    width: 100%;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .OutboundDetails-main {
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
            font-size: .40rem;
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
          font-size: .44rem;
          color: #fd8521;
          line-height: .44rem;
          box-sizing: border-box;
        }
        .listing-inventory {
          width: 100%;
          padding: 30px 25px 0 36px;
          box-sizing: border-box;
          .inventory-list {
            width: 100%;
            max-height: 500px;
            overflow: hidden;
            overflow-y: auto;
            li {
              width: 100%;
              display: flex;
              align-items: center;
              margin-bottom: 30px;
              .list-img {
                margin-right: 22px;
                width: 70px;
                height: 70px;
                img {
                  display: inline-block;
                  width: 100%;
                  height: 100%;
                }
              }
              .list-content {
                flex: 1;
                padding-bottom: 8px;
                border-bottom: 1px solid #dfdcdc;
                p {
                  font-size: .36rem;
                  color: #333;
                  line-height: 40px;
                  &:nth-last-child(1) {
                    color: #999;
                    span {
                      margin-left: 28px;
                      color: #fd8521;
                    }
                  }
                }
              }
            }
          }
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
                font-size: .36rem;
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
                      font-size: .36rem;
                      color: #333;
                      line-height: 36px;
                      &:nth-last-child(1) {
                        margin-top: 4px;
                        font-size: .28rem;
                        color: #bfbfbf;
                        line-height: 26px;
                      }
                    }
                  }
                }
              }
              .list-qrCode {
                display: flex;
                justify-content: space-between;
                p {
                  color: #bfbfbf;
                  font-size: .36rem;
                  span {
                    color: #333;
                    font-size: .28rem;
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
