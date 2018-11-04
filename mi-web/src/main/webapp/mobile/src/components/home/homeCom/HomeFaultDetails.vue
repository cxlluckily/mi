<template>
  <div id='HomeFaultDetails'>
    <div class="HomeFaultDetails-main">
      <div class="main-title">
        <div class="title-Tab">
          <p>故障情况</p>
          <span></span>
        </div>
      </div>
      <div class="main-content">
        <ul class="content-Tab" v-for="(itemMain, index) in faultDetailsData" :key="index">
          <li class="Tab-list" v-if="itemMain.swtichChangeInfo">
            <p class="Tab-ins">更换部件 :</p>
            <ul class="list-parts">
              <li v-for="(itemParts, index) in itemMain.changeInfo" :key="index">
                <div class="parts-replace" v-if="itemParts.oldQrCode"></div>
                <div class="parts-main">
                  <div class="main-new">
                    <div class="parts-new">
                      <i></i>
                      <p>{{itemParts.newPartName}}</p>
                    </div>
                    <p class="parts-num">{{itemParts.replaceCount}}个</p>
                  </div>
                  <div class="main-only">
                    <p>是否唯一标识: <span>{{itemParts.inventoryType == 'unique'? '是':'否'}}</span></p>
                    <p v-if="itemParts.newQrCode != ''">标识号: <span>{{itemParts.newQrCode}}</span></p>
                  </div>
                  <div class="main-oldQrCode" v-if="itemParts.oldQrCode != ''">
                    <i></i>
                    <p>旧件标识: <span class="oldQrCode-qrCode">{{itemParts.oldQrCode}}</span></p>
                  </div>
                </div>
              </li>
            </ul>
          </li>
          <li>
            <p class="Tab-ins">故障现象 :</p>
            <ul class="Tab-text">
              <li v-for="(itemText, index) in itemMain.faultPhenomenon" :key="index">{{itemText}}</li>
            </ul>
          </li>
          <li>
            <p class="Tab-ins">错误代码 :</p>
            <span>{{itemMain.faultCode}}</span>
          </li>
          <li>
            <p class="Tab-ins">故障描述 :</p>
            <span>{{itemMain.faultDescription}}</span>
          </li>
          <li>
            <p class="Tab-ins">故障图片 :</p>
            <ul class="Tab-Img">
              <li @click="previewFault($event)" v-for="(itemImg,index) in itemMain.faultImaegs" :key="index"
                  :data-index="index">
                <img :src="itemImg.picUrl" alt="现场图片">
              </li>
            </ul>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">维修结果 :</p>
            <span>{{itemMain.maintenanceResults == 'yes'?'已修复':'未修复'}}</span>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">故障现象补充 :</p>
            <ul class="Tab-text">
              <li v-for="(itemText, index) in itemMain.repairErrorPhenomenon" v-if="itemText.checked" :key="index">
                {{itemText.breakAbbreviated}}
              </li>
            </ul>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">处理措施 :</p>
            <ul class="Tab-text">
              <li v-for="(itemText, index) in itemMain.solution" :key="index">{{itemText.processMode}}</li>
            </ul>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">维修故障描述 :</p>
            <span>{{itemMain.maintenanceDescribe}}</span>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">问题描述 :</p>
            <span>{{itemMain.maintenanceDescriptio}}</span>
          </li>
          <li v-show="itemMain.swtichMaintenance">
            <p class="Tab-ins">维修图片 :</p>
            <ul class="Tab-Img">
              <li @click="previewFault($event)" v-for="(itemImg, index) in itemMain.maintenanceImages" :key="index"
                  :data-index="index">
                <img :src="itemImg.picUrl" alt="现场图片">
              </li>
            </ul>
          </li>
          <li class="Tab-title" v-show="itemMain.swtichEvaluation">
            <p>评价内容</p>
          </li>
          <li v-show="itemMain.swtichEvaluation">
            <p class="Tab-ins">评价结果 :</p>
            <span>{{itemMain.appraiseWasFinished=='yes'?"已修复":"未修复"}}</span>
          </li>
          <li v-show="itemMain.swtichEvaluation">
            <p class="Tab-ins">响应速度 :</p>
            <ul class="list-star" v-for="(itemStar,index) in 5" :key="index">
              <li><i :class="{'star':itemMain.speedAppraise > index}"></i></li>
            </ul>
          </li>
          <li v-show="itemMain.swtichEvaluation">
            <p class="Tab-ins">服务速度 :</p>
            <ul class="list-star" v-for="(itemStar,index) in 5" :key="index">
              <li><i :class="{'star':itemMain.serviceAppraise > index}"></i></li>
            </ul>
          </li>
          <li v-show="itemMain.swtichEvaluation">
            <p class="Tab-ins">操作规范 :</p>
            <ul class="list-star" v-for="(itemStar,index) in 5" :key="index">
              <li><i :class="{'star':itemMain.operationSpecificationAppraise > index}"></i></li>
            </ul>
          </li>
          <li v-show="itemMain.swtichEvaluation">
            <p class="Tab-ins">回访评价 :</p>
            <span>{{itemMain.appraiseDescribe}}</span>
          </li>
        </ul>
      </div>
    </div>
    <div class="HomeFaultDetails-previewImg" v-if="switchPreviewImg">
      <div class="previewImg-swipe">
        <mt-swipe :auto="0" :defaultIndex="previewSwipeImgIndex">
          <mt-swipe-item v-for="(itemSwipe, index) in previewSwipeImg" :key="index">
            <div class="backgroundImage" :style="itemSwipe"></div>
          </mt-swipe-item>
        </mt-swipe>
        <div class="closeIcon" @click="closePreviewImg">
          <i></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HomeFaultDetails",
    data() {
      return {
        switchPreviewImg: false,
        previewSwipeImgIndex: 0,
        previewSwipeImg: []
      };
    },
    props: ["faultDetailsData"],
    methods: {
      /* 方法 */
      previewFault(event) {
        this.previewSwipeImg = [];
        const THIS = $(event.currentTarget),
          LISTINDEX = THIS.attr("data-index"),
          LISTARR = THIS.closest(".Tab-Img").find("li"),
          IMGARR = [],
          SWIPEINDEX = $('.previewImg-swipe').find('.mint-swipe-item'),
          INDICATOR = $('.previewImg-swipe').find('.mint-swipe-indicator');
        for (let i = 0, II = LISTARR.length; i < II; i++) {
          let arr = {
            backgroundImage:'url('+LISTARR.eq(i).find("img")[0].src+')'
          };
          // IMGARR.push(LISTARR.eq(i).find("img")[0].src);
          IMGARR.push(arr);
        }
        this.previewSwipeImg = IMGARR;
        this.previewSwipeImgIndex = +LISTINDEX;
        this.switchPreviewImg = true;
      },
      closePreviewImg() {
        this.switchPreviewImg = false;
      }
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function () {
      /* 初始化数据 */
      console.log(this.faultDetailsData);
    },
    watch: {
      /* 监听 */
      faultDetailsData: function (val) {
        console.log(val);
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";

  #HomeFaultDetails {
    width: 100%;
    height: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    .HomeFaultDetails-main {
      width: 100%;
      height: 100%;
      padding: 12px 0 38px;
      border: solid 1px #dfdcdc;
      box-sizing: border-box;
      border-radius: 15px;
      background-color: #fff;
      .main-title {
        display: flex;
        margin: 0 30px;
        box-sizing: border-box;
        border-bottom: 1px solid #dfdcdc;
        p {
          padding: 16px;
          color: #333;
          font-size: .44rem;
          line-height: .44rem;
          font-weight: 700;
        }
        span {
          position: absolute;
          bottom: 0;
          left: 50%;
          transform: translateX(-50%);
          width: 46px;
          height: 4px;
          background-color: #fd8521;
        }
        .title-Tab {
          margin-left: 110px;
          position: relative;
        }
      }
      .main-content {
        padding-top: 16px;
        .content-Tab {
          & > li {
            display: flex;
            padding: 0 30px;
            width: 100%;
            box-sizing: border-box;
            p {
              font-size: .36rem;
              color: #999;
              line-height: 54px;
            }
            span {
              font-size: .36rem;
              color: #999;
              line-height: .36rem;
              padding: 12px 0 12px;
            }
            .Tab-ins {
              flex-shrink: 0;
              margin-right: 32px;
              color: #333;
            }
            .Tab-text {
              padding-top: 10px;
              font-size: .36rem;
              color: #999;
              line-height: 34px;
            }
            .Tab-Img {
              display: flex;
              flex-wrap: wrap;
              // justify-content: space-between;
              width: 100%;
              font-size: 0;
              li {
                margin-bottom: 10px;
                width: 33.3333333%;
                height: 150px;
                text-align: center;
                &:nth-last-child(1) {
                  margin: 0;
                }
                img {
                  width: 150px;
                  height: 150px;
                }
              }
            }
          }
          .Tab-title {
            width: 100%;
            height: 60px;
            background-color: #eee;
            p {
              font-size: .40rem;
              line-height: 60px;
              color: #fd8521;
            }
          }
          .list-star {
            display: flex;
            & > li {
              display: flex;
              align-items: center;
              justify-content: center;
              width: 42px;
              height: 54px;
              i {
                display: inline-block;
                width: 32px;
                height: 30px;
                background-image: url("../../../assets/home/fivepoitedstar2.png");
                background-size: 100% 100%;
              }
              .star {
                background-image: url("../../../assets/home/fivepoitedstar.png");
                background-size: 100% 100%;
              }
            }
          }
          .Tab-list {
            display: block;
            .list-parts {
              width: 100%;
              li {
                display: flex;
                align-items: center;
                width: 100%;
                padding-bottom: 10px;
                border-bottom: 1px solid #dfdcdc;
                &:nth-last-child(1) {
                  border: none;
                }
                .parts-replace {
                  width: 63px;
                  height: 104px;
                  background-image: url('../../../assets/home/cge_l.png');
                  background-size: 100% 100%;
                }
                .parts-replace2 {
                  width: 63px;
                  height: 74px;
                  background-image: url('../../../assets/home/cge_s.png');
                  background-size: 100% 100%;
                }
                .parts-main {
                  flex: 1;
                  margin-left: 6px;
                  .main-new {
                    display: flex;
                    align-items: center;
                    .parts-new {
                      display: flex;
                      align-items: center;
                      height: 36px;
                      i {
                        margin-right: 10px;
                        display: inline-block;
                        width: 22px;
                        height: 22px;
                        background-image: url("../../../assets/home/icon_new.png");
                        background-size: 100% 100%;
                      }
                      p {
                        font-size: .44rem;
                        color: #333;
                      }
                    }
                    .parts-num {
                      margin-left: 36px;
                      line-height: 36px;
                      color: #fd8521;
                    }
                  }
                  .main-only {
                    padding-left: 32px;
                    margin-bottom: 10px;
                    p {
                      font-size: .28rem;
                      line-height: .28rem;
                      color: #666;
                      span {
                        font-size: .28rem;
                        line-height: .28rem;
                      }
                    }
                  }
                  .main-oldQrCode {
                    display: flex;
                    align-items: center;
                    height: 36px;
                    i {
                      margin-right: 10px;
                      display: inline-block;
                      width: 22px;
                      height: 22px;
                      background-image: url("../../../assets/home/icon_old.png");
                      background-size: 100% 100%;
                    }
                    p {
                      font-size: .44rem;
                      color: #333;
                      span {
                        color: #fd8521;
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
    .HomeFaultDetails-previewImg {
      position: absolute;
      top: 0;
      left: 0;
      z-index: 300;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
      .previewImg-swipe {
        /*width: 600px;*/
        width: 100%;
        padding: 0 40px;
        height: 600px;
        margin: auto;
        margin-top: 40%;
        box-sizing: border-box;
        .mint-swipe-indicators{
          bottom: 0;
        }
        .mint-swipe-indicator{
          background: rgba(0,0,0,.5) !important;
          margin: 0 !important;
        }
        .closeIcon {
          display: flex;
          justify-content: center;
          align-items: flex-end;
          width: 100%;
          height: 70px;
          border-radius: 18px;
          i {
            display: inline-block;
            width: 35px;
            height: 35px;
            background-image: url("../../../assets/home/close.png");
            background-size: 100% 100%;
          }
        }
        img {
          width: 100%;
          height: 100%;
          border-radius: 18px;
        }
        div {
          width: 100%;
          height: 100%;
          border-radius: 18px;
          background-position: center center;
          background-repeat: no-repeat;
          background-size: contain;
        }
      }
    }
  }
</style>
