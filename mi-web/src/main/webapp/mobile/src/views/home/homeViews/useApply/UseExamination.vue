<template>
  <section id="UseExamination">
    <div class="UseExamination-main">
      <div class="editFaultInfor_contain">
        <div class="editFaultInfo_form" v-if="getmessage.inWareHouseName!=null">
          <label for="Storage_warehouse">入库仓库</label>
          <input type="text" readonly="readonly" id="Storage_warehouse" v-model="getmessage.inWareHouseName">
        </div>
        <div class="editFaultInfo_form" v-else>
          <label for="Storage_INwarehouse">入库仓库</label>
          <input type="text" readonly="readonly" id="Storage_INwarehouse" v-model="getmessage.outWareHouseName">
        </div>
        <div class="editFaultInfo_form">
          <label for="Outbound_warehouse">出库仓库</label>
          <input type="text" readonly="readonly" id="Outbound_warehouse" v-model="getmessage.outWareHouseName">
        </div>
        <div class="editFaultInfo_form">
          <label for="Outbound_warehouse">申请性质</label>
          <input type="text" readonly="readonly" id="Outbound_warehouse" v-model="applyType">
        </div>
        <div class="editFaultInfo_form">
          <label for="customerMessage">备注</label>
          <textarea id="customerMessage" rows="3" maxlength="200" v-model="getmessage.applyRemark" readonly="readonly">
                  </textarea>
        </div>
        <div class="editFaultInfo_form">
          <div class="checkedbox">
            <mt-radio v-model="value" :options="options">
            </mt-radio>
          </div>
        </div>
        <div class="editFaultInfo_form">
          <label for="Audit_opinion">审核意见</label>
          <textarea id="Audit_opinion" rows="3" maxlength="200" v-model="auditRemark" placeholder="请在这里填写你的意见">
                  </textarea>
        </div>
        <div class="editFaultInfo_form">
          <label for="AppliNo">申请单号</label>
          <input type="text" placeholder="请填写申请单号" id="AppliNo" readonly="readonly" v-model="getmessage.applyNo">
        </div>
        <div class="editFaultInfo_form">
          <label for="person">申请人</label>
          <input type="text" placeholder="请填写申请人" id="person" readonly="readonly" v-model="getmessage.applyUser">
        </div>
        <div class="editFaultInfo_form">
          <label>申请时间</label>
          <span class="fault-timer">{{getmessage.applyTime|initDate}}</span>
        </div>
      </div>
      <div class="editFaultInfor_contain" v-if="getmessage.details!=undefined&&getmessage.details.length>0">
        <p class="editFaultInfor_title">申请清单</p>
        <ul class="editFaultInfor_ul">
          <li v-for="(item,index) in getmessage.details">
            <div class="flex_pic">
              <span class="pic_img" v-if="item.imageUrl!='noImage'">
                        <img :src="item.imageUrl" alt=""></span>
              <span class="pic_img" v-if="item.imageUrl=='noImage'">
                        <img src="../../../../assets/home/pic2.png" alt=""></span>
            </div>
            <div class="flex_text">
              <p class="flex_text_title">{{item.partName}}</p>
              <div class="flex_text_con">
                <div class="flex_text_p">
                  <label>型号</label>
                  <span>{{item.specificationModel}}</span>
                </div>
                <div class="flex_text_p">
                  <label>申请数量</label>
                  <span class="flex_text_num">{{item.applyCount}}</span>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="auditApplyInfo">{{btnText}}</button>
    </div>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </section>
</template>

<script>
  import Toast from 'mint-ui';
  export default {
    name: 'editFaultInformation',
    data() {
      return {
        swtichLoading: true,
        auditRemark: '',
        getmessage: {},
        getApplyInfoByApply: {},
        outWareHouseName: '',
        applyRemark: '',
        showType: '',
        applyId: '',
        outWarehouseId: "",
        btnText: "审核",
        options: [{
            label: '审核通过',
            value: 'reviewPass'
          },
          {
            label: '审核不通过',
            value: 'reviewNoPass'
          }
        ],
        value: 'reviewPass',
        applyType: ''
      }
    },
    methods: {
      /* 方法 */
      auditApplyInfo() {
        const USERKEY = localStorage.getItem('userKey');
        const _this = this;
        let data = this.getmessage;
        data.detailEntities = this.getmessage.details;
        data.details = null;
        data.userKey = USERKEY;
        data.applyStatus = this.value;
        if (this.auditRemark == '') {
          data.auditRemark = this.getmessage.auditRemark;
        } else {
          data.auditRemark = this.auditRemark;
        }
        this.requests.auditApplyInfo(data).then(function(responend) {
          if (responend.result == 'success') {
            Toast.Toast({
              message: '审核成功',
              duration: 1000
            });
            _this.$router.push({
              path: '/UseApplyAuditList',
              query: {
                applyType: 'all'
              }
            });
          } else {
            Toast.Toast({
              message: '提交失败',
              duration: 1000
            });
          }
        })
      },
      type(text) {
        let temp = '';
        return temp;
      },
      getApplyInfoByApplyId() {
        const USERKEY = localStorage.getItem('userKey');
        const _this = this;
        let data = {
          applyId: _this.$route.query.applyId,
          userKey: USERKEY
        };
        this.requests.getApplyInfoByApplyId(data).then(function(responend) {
          console.log(responend.data)
          if (responend.result == 'success') {
            if (responend.data.applyStatus != 'toBeReview') {
              _this.$router.replace({
                path: '/UseExaminationlook',
                query: {
                  'applyId': _this.$route.query.applyId,
                  'taskNotice': 'true'
                }
              });
              return;
            }
            _this.getmessage = responend.data;
            switch (_this.getmessage.applyType) {
              case 'borrow':
                _this.applyType = '借用申请';
                break;
              case 'return':
                _this.applyType = '返还申请';
                break;
              case 'use':
                _this.applyType = '领用申请';
                break;
              case 'transfer':
                _this.applyType = '调拨申请';
                break;
            }
          }
        })
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    mounted() {
      this.$nextTick(function() {
        this.swtichLoading = false;
        this.getApplyInfoByApplyId();
      });
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
    }
  }
</script>

<style lang="scss" scope type="text/scss">
  @import "@/style/global.scss";
  #UseExamination {
    width: 100%;
    height: 100%;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .UseExamination-main {
      width: 100%;
      height: 100%;
      padding-bottom: 80px;
      box-sizing: border-box;
      overflow: auto;
    }
    .editFaultInfor_contain {
      width: 94%;
      margin: 10px auto;
      background: #fff;
      border: 1px solid #e8e8e6;
      /*px*/
      border-radius: 10px;
      padding: 20px 0;
      .editFaultInfo_form {
        padding: 20px 0;
        margin: 0 20px;
        border-bottom: 1px solid #e8e8e6;
        /*px*/
        .checkedbox {
          .mint-radio {
            vertical-align: middle;
            .mint-radio-core {
              width: .44rem;
              height: .44rem;
              &::after {
                width: .28rem;
                height: .28rem;
              }
            }
          }
          .mint-radiolist {
            display: flex;
            .mint-radiolist-title {
              display: none;
            }
            .mint-cell {
              width: 50%;
              .mint-radiolist-label {
                display: flex;
                width: 100%;
                align-items: center;
                .mint-radio-label {
                  width: 100%;
                  display: inline-block;
                }
              }
            }
          }
        }
        .label {
          display: inline-block;
          width: 150px;
        }
        input[type='text'] {
          width: 75%;
          font-size: $widthWeb16;
          line-height: 44px;
          text-align: right;
        }
        .fault-timer {
          @extend input[type='text'];
          display: inline-block;
        }
        .icon_array {
          display: inline-block;
          background: url('../../../../assets/home/arrow.png') no-repeat center;
          width: 30px;
          height: 30px;
          vertical-align: middle;
        }
        textarea {
          @extend input[type='text'];
          text-align: left;
          text-indent: 15px;
          vertical-align: top;
        }
        label {
          width: 130px;
          display: inline-block;
          font-size: $widthWeb16;
        }
        .btn-add {
          display: inline-block;
          padding: 15px;
          border-radius: 10px;
          font-size: $widthWeb16;
          background: #fd8822;
          color: #fff;
        }
      }
      .editFaultInfor_title {
        padding: 10px 15px;
        color: #fb7204;
        font-size: $widthWeb16;
      }
      .editFaultInfor_ul {
        padding: 10px 0;
        li {
          display: flex;
          width: 96%;
          margin: 10px auto;
          align-items: center;
          .flex_pic {
            flex: 2;
            .pic_img {
              width: 130px;
              display: inline-block;
              img {
                display: inline-block;
                width: 100%;
              }
            }
          }
          .deleDade {
            .checkbox {
              border: 1px solid #ddd;
              /*px*/
              padding: 10px 20px;
              border-radius: 5px;
            }
          }
          .flex_text {
            flex: 8;
            padding-left: 15px;
            font-size: $widthWeb16;
            .flex_text_title {
              line-height: 40px;
            }
            .flex_text_con {
              .flex_text_p {
                display: flex;
                line-height: 40px;
                label {
                  color: #8f8f94;
                  white-space: nowrap;
                }
                .flex_text_num {
                  display: inline-block;
                  width: 40%;
                  margin-left: 30px;
                  border-bottom: 1px solid #cfcfcf;
                  /*px*/
                  text-align: center;
                }
              }
            }
          }
        }
      }
    }
  }
</style>
