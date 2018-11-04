<template>
  <div id='PutStorageDetails'>
    <div class="PutStorageDetails-main">
      <ul class="main-details">
        <li v-for="(itemDetails, index) in detailsData" :key="index">
          <p>{{itemDetails.title}}</p>
          <p v-if="itemDetails.initTimer">{{itemDetails.text | initDate}}</p>
          <p v-else>{{itemDetails.text}}</p>
        </li>
      </ul>
      <div class="main-listing">
        <p class="listing-title">入库清单</p>
        <ul class="listing-list">
          <li v-for="(itemParts, index) in detailsListingData" :key="index">
            <p>{{itemParts.partName}}<span>{{itemParts.count}}个</span></p>
            <div class="list-parts">
              <div class="parts-img">
                <img src="../../../../assets/home/pic3.png" alt="">
              </div>
              <ul>
                <li>
                  <p>{{itemParts.inventoryType == 'notUnique'? '否':'是'}}</p>
                  <p>唯一标识</p>
                </li>
                <li>
                  <p>{{itemParts.houseNo||'无'}}</p>
                  <p>房间号</p>
                </li>
                <li>
                  <p>{{itemParts.shelfNumber||'无'}}</p>
                  <p>货架号</p>
                </li>
              </ul>

            </div>
            <div style="padding-top: 10px">
              <span style="color: #bfbfbf">二维码：</span>
              <span>{{!itemParts.qrCode?"暂无二维码显示":itemParts.qrCode}}</span>
              <span style="float: right;padding-right: 10px">{{itemParts.status=='normal'?'好件':'坏件'}}</span>
              <span style="color: #bfbfbf;float: right">备件状态：</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="goBack">返 回</button>
    </div>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "PutStorageDetails",
    data() {
      return {
        swtichLoading: true,
        inStockId: this.$route.query.inStockId,
        detailsData: [],
        detailsListingData: [],
      };
    },
    methods: {
      /* 方法 */
      initData() {
        // let query = this.$route.query;
        // const PROMPARR = this.$route.params.id.split(','),
        let _this = this;
        let PROMPOBJ = {
          inStockId: this.inStockId,
        };
        _this.api.storageDetailsDataAllprompObj(PROMPOBJ).then((data) => {
          const DATA = data.data;
          _this.detailsData.push({
            title: '入库日期',
            text: DATA.inDate,
            initTimer: true
          }, {
            title: '入库仓库',
            text: DATA.inWarehouseName
          }, {
            title: '入库性质',
            text: DATA.inStockType
          }, {
            title: '状态',
            text: DATA.inStockStatus == 'toBeIn' ? '待入库' : '已入库'
          }, {
            title: '操作人',
            text: DATA.inStockUser
          }, {
            title: '操作时间',
            text: DATA.modifyTime,
            initTimer: true
          }, {
            title: '备注',
            text: DATA.remark || '暂无备注!'
          });
          switch (_this.detailsData[2].text) {
            case 'newIn':
              this.detailsData[2].text = '新品入库';
              break;
            case 'useIn':
              this.detailsData[2].text = '归还入库';
              break;
            case 'transferIn':
              this.detailsData[2].text = '调拨入库';
              break;
            case 'borrowIn':
              this.detailsData[2].text = '借用入库';
              break;
            case 'returnIn':
              this.detailsData[2].text = '返还入库';
              break;
          }
          _this.detailsListingData = data.data.splitDetails;
          _this.swtichLoading = false;
        })
      },
      goBack() {
        this.$router.replace({
          path: '/PutStorage'
        });
      }
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function () {
      /* 初始化数据 */
      this.initData();
      if (window.history && window.history.pushState) {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBack, false);
      }
    },
    watch: {
      /* 监听 */
    },
    destroyed() {
      window.removeEventListener('popstate', this.goBack, false);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";

  #PutStorageDetails {
    position: absolute;
    top: 0;
    left: 0;
    overflow-y: auto !important;
    padding: 20px 20px 100px 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background-color: #faf6ec;
    .gloobalLoading {
      position: fixed;
      top: 0;
      left: 0;
      z-index: 1000;
      width: 100%;
      height: 100%;
      background-color: #f6f7eb;
    }
    /*width: 100%;*/
    /*height: calc(100% - 80px) !important;*/
    /*overflow-y: auto !important;*/
    /*padding: 20px;*/
    /*box-sizing: border-box;*/
    .PutStorageDetails-main {
      width: 100%;
      .main-details {
        margin-bottom: 20px;
        padding: 20px;
        width: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        box-sizing: border-box;
        border-radius: 18px;
        & > li {
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
        & > li:nth-last-child(1) {
          & p:nth-last-child(1) {
            text-align: left;
          }
        }
      }
      .main-listing {
        padding-top: 26px;
        width: 100%;
        background-color: #fff;
        border: solid 1px #dfdcdc;
        border-radius: 18px;
        box-sizing: border-box;
        .listing-title {
          width: 100%;
          padding-left: 36px;
          font-size: $widthWeb22;
          color: #fd8521;
          line-height: 22px;
          box-sizing: border-box;
        }
        .listing-list {
          width: 100%;
          padding: 20px;
          box-sizing: border-box;
          & > li {
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
              & > ul {
                display: flex;
                width: 100%;
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
    /*.footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 80px;
      text-align: right;
      background-color: #eeeeee;
      button {
        width: 220px;
        height: 80px;
        font-size: $widthWeb25;
        color: #fff;
        line-height: 80px;
        background-color: #fc6806;
      }
    }*/
  }
</style>
