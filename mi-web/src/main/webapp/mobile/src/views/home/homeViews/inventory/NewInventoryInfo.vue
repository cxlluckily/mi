<template>
  <div id="NewInventoryInfo">
    <div class="NewInventoryInfo-title">
      <p>
        <i></i>
        <span @click="JumpRouting">盘库记录</span>
      </p>
    </div>
    <section class="NewInventoryInfo_form">
      <div class="custom-input" @click="openPicker('start')">
        <label for="start_time">开始时间</label>
        <input class="input" id="start_time" name='start' readonly="readonly" type="text"
               placeholder="请选择起始时间" v-model="PostData.beginTIme">
        <div class="icon_array"></div>
      </div>
      <div class="custom-input" @click="openPicker('end')">
        <label for="end_time">结束时间</label>
        <input class="input" id="end_time" name='end' readonly="readonly" type="text"
               placeholder="请选择结束时间" v-model="PostData.endTIme">
        <div class="icon_array"></div>
      </div>
      <div class="custom-input" @click="getWarehousesByUserinfo">
        <label for="warehouse">盘点仓库</label>
        <input class="input" type="text" id="warehouse" v-model="PostData.warehouseName" readonly="readonly"
               placeholder="请选择盘点仓库">
        <div class="icon_array"></div>
      </div>
      <div class="custom-input" @click="findUserByPermission">
        <label for="supervisor">盘点负责人</label>
        <input class="input" type="text" id="supervisor" readonly="readonly" v-model="PostData.headPerson"
               placeholder="请选择盘点负责人">
        <div class="icon_array"></div>
      </div>
      <div class="list-textarea">
        <p>备注<span>( 最多可输入200个字 )</span></p>
        <textarea class="textBox" placeholder="请输入备注信息" v-model="PostData.remark"></textarea>
      </div>
      <div class="btn">
        <button type="button" @click="AddInformation">提 交</button>
      </div>
    </section>
    <!--<div class="footer">-->
    <!--<button @click="AddInformation()">提交</button>-->
    <!--</div>-->
    <!--日期选择器-->
    <mt-datetime-picker v-model="pickerVisible"
                        ref="picker"
                        type="date"
                        year-format="{value} 年"
                        month-format="{value} 月"
                        date-format="{value} 日"
                        @confirm="handleConfirm">
    </mt-datetime-picker>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle'
                         :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType"
                         @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom"
                            :errorInformation="GlobalTroubleNumberError"
                            :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
    <GlobalSuccessInformationCommon v-show="switchGlobalSuccessInformation"
                                    @hideSuccessCurrentCom="getHideSuccessCurrentCom"
                                    :successInformation="successInformation"
                                    :successInformationTitle="successInformationTitle" :successText="successText">
    </GlobalSuccessInformationCommon>
  </div>
</template>
<script>
  import {Toast} from 'mint-ui'
  import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut';
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import GlobalSuccessInformationCommon from "@/components/globalCom/GlobalSuccessInformationCommon";

  export default {
    name: 'NewInventoryInfo',
    data() {
      return {
        //成功提示
        switchGlobalSuccessInformation: false,
        successInformation: '',
        successInformationTitle: '',
        successText: '',
        //错误提示
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: '',
        GlobalTroubleNumberErrorTitle: '',
        switchHomeOrdinarySideOut: false,//右侧弹出层显示隐藏
        HomeOrdinarySideOutTitle: '盘点负责人',//右侧弹出层标题
        HomeOrdinarySideOutData: [],//右侧弹出层数据
        PostData: {
          userKey: this.common.getLocalStorage('userKey'),
          beginTIme: this.common.getDate('yyyy-MM-dd'),
          endTIme: this.common.getDate('yyyy-MM-dd', +30),
          headPerson: '',
          headPersonUserId: '',
          warehouseId: '',
          warehouseName: '',
          remark: ''
        },
        slots: [
          {
            values: [{status: 'before', name: '待盘库'}, {status: 'ongoing', name: '盘库中'}, {
              status: 'already',
              name: '盘库完成'
            }]
          },
        ],
        slotWare: [
          {values: []},
        ],
        pickerVisible: '',
        type: '',
        statusName: '',
        warehouseName: '',
        startdates: new Date('1970/1/1'),
        showpopup: false,
        warehousepopup: false
      }
    },
    methods: {
      AddInformation(PostData) {
        const _this = this;
        const beginTIme = this.PostData.beginTIme;
        const endTIme = this.PostData.endTIme;
        const headPersonUserId = this.PostData.headPersonUserId;
        const warehouseId = this.PostData.warehouseId;
        if (beginTIme == '') {
          this.errorMessageShow('温馨提示', '选择开始时间');
        } else if (endTIme == '') {
          this.errorMessageShow('温馨提示', '请选择结束时间');
        } else if (headPersonUserId == '') {
          this.errorMessageShow('温馨提示', '请选择盘点负责人');
        } else if (warehouseId == '') {
          this.errorMessageShow('温馨提示', '请选择盘点仓库');
        } else {
          this.requests.insertOne(this.PostData).then(function (respont) {
            if (respont.result == 'success') {
              _this.showSuccessCurrentCom();
              _this.PostData = {
                userKey: _this.common.getLocalStorage('userKey'),
                beginTIme: _this.common.getDate('yyyy-MM-dd'),
                endTIme: _this.common.getDate('yyyy-MM-dd', +30),
                headPerson: '',
                headPersonUserId: '',
                warehouseId: '',
                warehouseName: '',
                remark: ''
              };
              // _this.timeOut = setTimeout(() => {
              //   _this.getHideSuccessCurrentCom();
              // }, 2000);
            } else {
              _this.errorMessageShow('温馨提示', respont.message);
            }
          });
        }
      },
      openPicker(type) {
        this.type = type;
        if (this.type == 'start') {
          this.pickerVisible = this.PostData.beginTIme;
        } else {
          this.pickerVisible = this.PostData.endTIme;
        }
        this.$refs.picker.open();
      },
      handleConfirm(data) {
        let date = this.formatDate(data);
        if (this.type == 'start') {
          this.PostData.beginTIme = date;
        }
        if (this.type == 'end') {
          this.PostData.endTIme = date;
        }
      },
      onValuesChange(picker, values) {
        const _this = this;
        if (values != undefined) {
          picker.setSlotValue(1, values);
          this.PostData.status = values[0].status;
          this.statusName = values[0].name;
        }
      },
      cancel() {
        this.showpopup = false;
      },
      confirm() {
        this.cancel()
      },
      formatDate(time) {
        const date = new Date(time);
        let year = date.getFullYear(),
          month = date.getMonth() + 1,
          day = date.getDate();
        let newTime = year + '-' +
          month + '-' +
          day + ' ';
        return newTime;
      },
      cancelWarehouse() {
        this.warehousepopup = false;
      },
      confirmWarehouse() {
        this.cancelWarehouse();
      },
      trimConfirm(text) {
        Toast({
          message: text,
          position: 'bottom',
          duration: 3000
        });
        return;
      },
      getHideCurrentCom() {
        this.switchGlobalErrorInformation = false;
      },
      errorMessageShow(title, message) {
        this.switchGlobalErrorInformation = true;
        this.GlobalTroubleNumberError = message;
        this.GlobalTroubleNumberErrorTitle = title;
      },
      getHideSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = false;
        // clearTimeout(this.timeOut);
        // this.$router.push({path: '/Inventory'});
      },

      showSuccessCurrentCom() {
        this.switchGlobalSuccessInformation = true;
        this.successInformation = '提交成功';
        this.successInformationTitle = '温馨提示';
        this.successText = '确定';
      },
      getWarehousesByUsers() {
        const _this = this;
        this.requests.getWarehousesByUser().then(function (respont) {
          if (respont.result == 'success') {
            var respones = respont.data;
            if (respones.length > 0) {
              for (let item = 0; item < respones.length; item++) {
                let data = {};
                data.name = respones[item].text;
                data.id = respones[item].id;
                _this.slotWare[0].values.push(data);
              }
            } else {
              return;
            }
          }
        })
      },
      onValuesWarehouseId(picker, values) {
        if (values != undefined) {
          picker.setSlotValue(1, values);
          if (values[0] != undefined) {
            this.PostData.warehouseId = values[0].id;
            this.warehouseName = values[0].name;
          }
        }
      },
      // 获取有盘库节点，用户列表
      findUserByPermission() {
        let $this = this;
        if (!$this.PostData.warehouseId) {
          $this.errorMessageShow('温馨提示', '请选择盘点仓库！');
          return;
        }
        let data = {
          userKey: $this.common.getLocalStorage('userKey'),
          warehouseId: $this.PostData.warehouseId
        };
        this.requests.findUserByWarehousePermission(data).then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          $this.HomeOrdinarySideOutTitle = '盘点负责人';
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].userId,
              sideOutText: DATA[i].realName
            });
          }
          $this.switchHomeOrdinarySideOut = true;
        })
      },

      // 根据userId返回用户负责仓库
      getWarehousesByUserinfo() {
        let $this = this;
        this.requests.getWarehousesByUser().then(function (data) {
          const DATA = data.data;
          $this.HomeOrdinarySideOutData = [];
          $this.HomeOrdinarySideOutTitle = '盘点仓库';
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].code,
              sideOutText: DATA[i].text
            });
          }
          $this.switchHomeOrdinarySideOut = true;
        })
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        if (val.title == '盘点负责人') {
          this.PostData.headPerson = val.text;
          this.PostData.headPersonUserId = val.sideOutId;
        } else {
          this.PostData.warehouseName = val.text;
          this.PostData.warehouseId = val.sideOutId;
          this.PostData.headPerson = '';
          this.PostData.headPersonUserId = '';
        }
      },
      JumpRouting() {
        this.$router.push({path: 'NewInventoryList'})
      }
    },
    components: {
      /* 复用组件名称 */
      HomeOrdinarySideOut,
      GlobalErrorInformation,
      GlobalSuccessInformationCommon
    },
    mounted: function () {
      this.getWarehousesByUsers();
    }
  }

</script>
<style lang="scss" scope type="text/scss">
  @import "@/style/global.scss";

  #NewInventoryInfo {
    width: 100%;
    overflow-y: scroll;
    .NewInventoryInfo-title {
      height: 66px;
      padding-right: 20px;
      box-sizing: border-box;
      p {
        margin-top: 16px;
        float: right;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 0px;
        color: #f56301;
        /*line-height: 66px;*/
        padding: 6px 17px;
        line-height: 28px;
        border: 1px solid #f56301;
        border-radius: 15px;
        span {
          font-size: 24px;
          line-height: 24px;
        }
        i {
          display: inline-block;
          margin-right: 7px;
          width: 14px;
          height: 16px;
          background: url("../../../../assets/home/record.png") no-repeat;
          background-size: 100% 100%;
        }
      }
    }
    .NewInventoryInfo_form {
      width: 94%;
      background: #fff;
      border: 1px solid #e3e7e0; /*px*/
      margin: 0px auto;
      padding: 20px 0;
      border-radius: 10px;
      .list-textarea {
        display: block;
        padding: 0 25px;
        border: 0;
        & > p {
          font-size: $widthWeb22;
          /*padding-left: 25px;*/
          color: #bfbfbf;
          line-height: 80px;
          span {
            margin-left: 10px;
            font-size: $widthWeb18;
            color: #666;
          }
        }
        .textBox {
          /*margin-top: 9px;*/
          padding: 20px 20px;
          width: 100%;
          height: 110px;
          font-size: $widthWeb18;
          color: #333;
          resize: none;
          border: solid 1px #bfbfbf;
          box-sizing: border-box;
        }
      }
      .custom-input {
        margin: 0 25px;
        display: flex;
        align-items: center;
        border-bottom: 1px solid #eee; /*px*/
        height: 80px;
        .input {
          width: 82%;
          line-height: 78px;
          text-align: right;
          vertical-align: middle;
          padding-right: 10px;
          font-size: $widthWeb25;
        }
        label {
          white-space: nowrap;
          padding-right: 15px;
          color: #bfbfbf;
          font-size: $widthWeb25;
          width: 25%;
        }
        .icon_array {
          background: url('../../../../assets/home/arrow.png') no-repeat center;
          background-size: contain;
          width: 30px;
          height: 30px;
          display: inline-block;
        }
      }
    }
    .NewInventoryInfo_btn {
      text-align: right;
      width: 94%;
      margin: 20px auto;
      .NewInventoryInfo_submit {
        background: #fd8723;
        display: inline-block;
        padding: 10px 30px;
        color: #fff;
      }
    }
    .picker-toolbar {
      padding: 5px 0;
    }
    .footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 80px;
      text-align: right;
      background-color: #eeeeee;
      z-index: 2;
      button {
        width: 220px;
        height: 80px;
        font-size: $widthWeb25;
        color: #fff;
        line-height: 80px;
        background-color: #fc6806;
      }
    }
    .btn {
      margin: 30px 0;
      text-align: center;
      button {
        padding: 10px 38px;
        color: #fff;
        font-size: $widthWeb22;
        line-height: $widthWeb22;
        background-color: #fd8521;
      }
    }
  }
</style>
