<template>
  <section id="ReturnApply">
    <div class="editFaultInfor_contain">
      <div class="editFaultInfo_form">
        <label for="ApplicationDate">返还时间</label>
        <input type="text" placeholder="请选择申请日期" readonly="readonly" id="ApplicationDate" v-model="start_time">
      </div>
      <div class="editFaultInfo_form" @click="deviceList">
        <label for="warehouse"><i style="color:red" v-show="applyId==0">*</i>出库仓库</label>
        <input type="text" placeholder="请选择出库仓库" readonly="readonly" id="warehouse" v-model="outWareHouseName">
        <i class="icon_array" v-show="applyId==0"></i>
      </div>
      <div class="editFaultInfo_form" @click="getloadUpTree">
        <label for="Inwarehouse"><i style="color:red" v-show="applyId==0">*</i>入库仓库</label>
        <input type="text" placeholder="请选择入库仓库" readonly="readonly" id="Inwarehouse" v-model="inWareHouseName">
        <i class="icon_array" v-show="applyId==0"></i>
      </div>
      <div class="editFaultInfo_form">
        <label for="nature">申请性质</label>
        <input type="text" placeholder="请填写申请性质" readonly="readonly" id="nature" value="返还申请">
      </div>
      <div class="editFaultInfo_form">
        <label for="customerMessage">备注</label>
        <textarea id="customerMessage" rows="3" maxlength="200" placeholder="请在这里输入备注说明，200字以内..." v-model="applyRemark">
            </textarea>
      </div>
      <div class="editFaultInfo_form">
        <i style="color:red">*</i>
        <div class="btn-add" @click="addLink()">
          <i class="addList-icon"></i>
          <p>物品添加</p>
        </div>
      </div>
    </div>
    <div class="editFaultInfor_contain" v-if="listArray.length">
      <p class="editFaultInfor_title">返还清单</p>
      <ul class="editFaultInfor_ul">
        <li v-for="(item,index) in listArray">
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
                <label>返还数量</label>
                <input type="number" class="flex_text_num" v-model="item.applyCount">
              </div>
            </div>
          </div>
          <div class="deleDade">
            <span class="checkbox" @click="deleDate(index)">
                    删除
                </span>
          </div>
        </li>
      </ul>
    </div>
    <div class="globalFooter">
      <button @click="saveAddOrEdit">{{btnText}}</button>
    </div>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <!--日期选择器-->
    <mt-datetime-picker v-model="pickerVisible" ref="datePicker" type="date" year-format="{value} 年" month-format="{value} 月" date-format="{value} 日" @confirm="handleConfirm">
    </mt-datetime-picker>
    <GlobalConfirmInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" @hideConfirmCom="gethideConfirm" :Information="EquipmentRepairError" :GlobalConfirmInfoTitle="EquipmentRepairErrorTitle">
    </GlobalConfirmInformation>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </section>
</template>

<script>
  import Toast from 'mint-ui';
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut'
  import GlobalConfirmInformation from "@/components/globalCom/GlobalConfirmInformation";
  export default {
    name: 'ReturnApply',
    data() {
      return {
        swtichLoading: true,
        pickerVisible: '',
        listArray: [],
        getApplyInfoByApply: {},
        start_time: new Date().getFullYear() + '年' + (new Date().getMonth() + 1) + '月' + new Date().getDate() + '日',
        switchHomeOrdinarySideOut: false,
        HomeOrdinarySideOutTitle: "",
        HomeOrdinarySideOutData: [],
        TransferSideOutData: [],
        outWareHouseName: '',
        inWareHouseName: '',
        applyRemark: '',
        showType: '',
        applyId: '',
        outWarehouseId: "",
        inWarehouseId: "",
        isEdit: false,
        btnText: "保存",
        switchGlobalErrorInformation: false,
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
      }
    },
    methods: {
      /* 方法 */
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
        this.outWareHouseName = '';
        this.outWarehouseId = '';
        this.listArray = []
      },
      gethideConfirm(val) {
        this.switchGlobalErrorInformation = val;
      },
      errorMessageShow(title, message) {
        this.EquipmentRepairErrorTitle = title;
        this.EquipmentRepairError = message;
        this.switchGlobalErrorInformation = true;
      },
      deviceList() {
        if(this.applyId!=0){
          return;
        }
        const _THIS = this;
        _THIS.HomeOrdinarySideOutTitle = "选择仓库";
        const USERKEY = localStorage.getItem('userKey');
        let data = {
          userKey: USERKEY,
          type: "warehouse",
          range: "warehouse"
        };
        this.requests.deviceTypeDataAll(data).then(function(data) {
          const DATA = data.data;
          _THIS.HomeOrdinarySideOutData = [];
          for (let i = 0, DataAll = DATA.length; i < DataAll; i++) {
            _THIS.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].id,
              sideOutText: DATA[i].text
            });
          }
        });
        this.switchHomeOrdinarySideOut = !this.switchHomeOrdinarySideOut;
      },
      getloadUpTree() {
        if(this.applyId!=0){
          return;
        }
        const _THIS = this;
        _THIS.HomeOrdinarySideOutTitle = "选择入库仓库";
        const USERKEY = localStorage.getItem('userKey');
        let data = {
          userKey: USERKEY,
          type: "warehouse",
          range: "warehouse",
          showMode: "list",
          entityId: this.outWarehouseId
        };
        this.requests.getloadUpTree(data).then(function(data) {
          const DATA = data.data;
          _THIS.HomeOrdinarySideOutData = [];
          if (DATA.length != 0) {
            for (let i = 0, retunData = DATA.length; i < retunData; i++) {
              _THIS.HomeOrdinarySideOutData.push({
                sideOutId: DATA[i].id,
                sideOutText: DATA[i].text
              });
            }
          }
        });
        this.switchHomeOrdinarySideOut = !this.switchHomeOrdinarySideOut;
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      openPicker() {
        this.$refs.datePicker.open();
      },
      getHomeSelectedType(val) {
        if (val.title == "选择仓库") {
          this.outWareHouseName = val.text;
          this.outWarehouseId = val.sideOutId;
        }
        if (val.title == "选择入库仓库") {
          this.inWareHouseName = val.text;
          this.inWarehouseId = val.sideOutId;
        }
      },
      addLink() {
        const applyIds = this.$route.query.applyId;
        if (this.outWarehouseId === '') {
          this.errorMessageShow('提示信息', '请先选择仓库');
          return;
        }
        sessionStorage.setItem('applyId', applyIds);
        let entity = {
          outWarehouseId: this.outWarehouseId,
          outWareHouseName: this.outWareHouseName,
          inWarehouseId: this.inWarehouseId,
          inWareHouseName: this.inWareHouseName,
          applyRemark: this.applyRemark
        };
        sessionStorage.setItem("userApplyEditEntity", JSON.stringify(entity));
        sessionStorage.setItem("isFirst", "no");
        this.$router.push({
          path: '/UseApplySelectPart'
        });
      },
      handleConfirm(data) {
        let date = this.$options.filter.initDate(data);
        this.start_time = date;
      },
      isHave(sparePartId) {
        for (let i = 0; i < this.listArray.length; i++) {
          if (this.listArray[i].sparePartId === sparePartId) {
            return true;
          }
        }
        return false;
      },
      initDate() {
        if (sessionStorage.getItem("dataObj") === '') {
          return;
        }
        let getArray = JSON.parse(decodeURIComponent(sessionStorage.getItem("dataObj")));
        if (getArray == null) {
          return;
        } else {
          //新添加的
          for (let i = 0; i < getArray.length; i++) {
            if (!this.isHave(getArray[i].sparePartId)) {
              this.listArray.push(getArray[i]);
            }
          }
          sessionStorage.setItem("dataObj", encodeURIComponent(JSON.stringify(this.listArray)));
        }
      },
      saveAddOrEdit() {
        if (this.swtichLoading) {
          return;
        }
        const USERKEY = localStorage.getItem('userKey');
        const applyId = this.$route.query.applyId;
        const _this = this;
        if (this.outWareHouseName == '') {
          Toast.Toast({
            message: '请选择出库仓库',
            duration: 1000
          });
          return;
        }
        if (this.inWareHouseName == '') {
          Toast.Toast({
            message: '请选择入库仓库',
            duration: 1000
          });
          return;
        }
        if (this.listArray.length == 0) {
          Toast.Toast({
            message: '请点击添加物品',
            duration: 3000
          });
          return;
        } else {
          for (let i = 0; i < this.listArray.length; i++) {
            if (this.listArray[i].applyCount == 0) {
              Toast.Toast({
                message: '数量必须大于0',
                duration: 3000
              });
              return;
            }
          }
        }
        let data = {
          userKey: USERKEY,
          detailEntities: this.listArray,
          applyType: 'return',
          outWarehouseId: this.outWarehouseId,
          applyRemark: this.applyRemark,
          applyId: applyId,
          inWarehouseId: this.inWarehouseId
        };
        this.swtichLoading = true;
        if (this.isEdit) {
          this.requests.updateApplyInfo(data).then(function(responend) {
            _this.swtichLoading = false;
            if (responend.result == 'success') {
              Toast.Toast({
                message: '更新成功',
                duration: 3000
              });
              _this.$router.push({
                path: '/UseApplyList',
                query: {
                  applyType: 'return'
                }
              });
            } else {
              Toast.Toast({
                message: '提交失败',
                duration: 3000
              });
            }
          })
        } else {
          this.requests.addApplyInfo(data).then(function(responend) {
            _this.swtichLoading = false;
            if (responend.result == 'success') {
              Toast.Toast({
                message: '提交成功',
                duration: 3000
              });
              _this.$router.push({
                path: '/UseApplyList',
                query: {
                  applyType: 'return'
                }
              });
            } else {
              Toast.Toast({
                message: '提交失败',
                duration: 3000
              });
            }
          });
        }
      },
      getApplyInfoByApplyId() {
        const USERKEY = localStorage.getItem('userKey');
        const _this = this;
        let data = {
          applyId: _this.$route.query.applyId,
          userKey: USERKEY
        };
        this.requests.getApplyInfoByApplyId(data).then(function(responend) {
          if (responend.result == 'success') {
            if (responend.data.applyStatus != 'toBeReview') {
              _this.$router.replace({
                path: '/ReturnApplyLook',
                query: {
                  'applyId': data.applyId,
                  'taskNotice': 'true'
                }
              });
              return;
            }
            _this.listArray = responend.data.details;
            sessionStorage.setItem("dataObj", encodeURIComponent(JSON.stringify(_this.listArray)));
            sessionStorage.setItem("userApplyEditEntity", JSON.stringify(responend.data));
            _this.initPageValue();
            _this.initDate();
          }
        })
      },
      initPageValue() {
        let userApplyEditEntity = JSON.parse(sessionStorage.getItem("userApplyEditEntity"));
        if (userApplyEditEntity != null) {
          this.outWarehouseId = userApplyEditEntity.outWarehouseId;
          this.outWareHouseName = userApplyEditEntity.outWareHouseName;
          this.inWarehouseId = userApplyEditEntity.inWarehouseId;
          this.inWareHouseName = userApplyEditEntity.inWareHouseName;
          this.applyRemark = userApplyEditEntity.applyRemark;
          this.applyId = userApplyEditEntity.applyId||0;
        }
      },
      deleDate(datas) {
        this.listArray.splice(datas, 1);
        sessionStorage.setItem("dataObj", encodeURIComponent(JSON.stringify(this.listArray)));
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    components: {
      HomeOrdinarySideOut,
      GlobalConfirmInformation
    },
    mounted() { /* 初始化数据 */
      this.applyId = this.$route.query.applyId;
      if (this.$route.query.applyId != 0) {
        this.isEdit = true;
        this.btnText = "更新";
      }
      if (this.isEdit) {
        if (sessionStorage.getItem("isFirst") == "yes") {
          this.getApplyInfoByApplyId();
        }
      } else {
        this.applyId = 0;
      }
      if (sessionStorage.getItem("isFirst") === "no") {
        // this.getApplyInfoByApplyId();
        this.initPageValue();
        this.initDate();
      }
      this.showType = sessionStorage.getItem("showType");
      this.$nextTick(function() {
        this.swtichLoading = false;
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
  #ReturnApply {
    overflow: auto;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
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
        input {
          width: 70%;
          font-size: $widthWeb16;
          text-align: right;
          line-height: 30px;
        }
        .icon_array {
          display: inline-block;
          background: url('../../../../assets/home/arrow.png') no-repeat center;
          width: 30px;
          height: 30px;
          vertical-align: middle;
        }
        textarea {
          @extend input;
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
          margin-left: 10px;
          display: inline-flex;
          align-items: center;
          background-color: #eee;
          box-shadow: 0 3px 3px 0 rgba(0, 0, 0, 0.3);
          border-radius: 24px;
          vertical-align: middle;
          .addList-icon {
            display: inline-block;
            width: 25px;
            height: 25px;
            margin-left: 18px;
            background-image: url("../../../../assets/home/add.png");
            background-size: 100% 100%;
          }
          p {
            display: inline-block;
            padding: 14px 24px 14px 16px;
            font-size: $widthWeb18;
            line-height: $widthWeb18;
          }
        }
      }
      .editFaultInfor_title {
        padding: 30px 15px;
        color: #fb7204;
        font-size: $widthWeb16;
      }
      .editFaultInfor_ul {
        padding: 15px 0;
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
              line-height: 60px;
            }
            .flex_text_con {
              .flex_text_p {
                display: flex;
                line-height: 60px;
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
