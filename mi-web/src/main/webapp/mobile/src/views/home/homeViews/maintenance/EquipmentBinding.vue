<template>
  <div id='EquipmentBinding'>
    <div class="EquipmentBinding-main">
      <ul class="main-list">
        <li @click="siteList">
          <label for="inp-site" class="inp-title">选择线路站点</label>
          <input type="text" name="site" id="inp-site" class="inp-txt" placeholder="请选择线路及站点" readonly="readonly" v-model="inpSiteText">
          <div class="inp-icon"><i></i></div>
        </li>
        <li @click="bindingType">
          <label for="inp-type" class="inp-title">设备类型</label>
          <input type="text" name="coding" id="inp-type" class="inp-txt" placeholder="请选择设备类型" readonly="readonly" v-model="inpTypeText">
          <div class="inp-icon"><i></i></div>
        </li>
        <li  @click="coding">
          <label for="inp-coding" class="inp-title">设备编号</label>
          <input type="text" name="coding" id="inp-coding" class="inp-txt" placeholder="请选择设备编号" readonly="readonly" v-model="inpConingText">
          <div class="inp-icon"><i></i></div>
          <!-- <div class="iconQeCodeArrow">
            <div class="qrCode" @click="qrCodeDevice">
              <i></i>
            </div>
            <div class="arrow" @click="coding">
              <i></i>
            </div>
          </div> -->
        </li>
        <li @click="qrCode">
          <label for="inp-qrCode" class="inp-title">扫描二维码</label>
          <input type="text" unselectable="on" onfocus="this.blur()" name="qrCode" id="inp-qrCode" class="inp-txt" placeholder="二维码编码" readonly="readonly" v-model="inpQrCodeText">
          <div class="inp-icon"><i class="icon-qrCode"></i></div>
        </li>
      </ul>
      <div class="main-btn" @click="binding"><button>绑 定</button></div>
    </div>
    <HomeSiteList :switchLienSite="switchHomeSiteList" @switchHomeSiteList="getHomeSiteList" @HomeCurrentType='getHomeCurrentType'>
    </HomeSiteList>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalListMask :switchListMask="switchHomeListMask" :listMaskData="childListMaskData" @switchHomeListMask="getHomeListMask" @HomeListMaskData="getHomeListMaskData">
    </GlobalListMask>
    <div class="EquipmentBinding-bindingComplete" v-show="switchBindingComplete">
      <div class="bindingComplete-main">
        <div class="main-title">
          <p>设备绑定</p>
          <div class="title-icon" @click="close"><i></i></div>
        </div>
        <div class="main-content">
          <div class="content-img">
            <i></i>
          </div>
          <p>设备绑定成功!</p>
        </div>
        <div class="main-determine">
          <button @click="determine">确 定</button>
        </div>
      </div>
    </div>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle"></GlobalErrorInformation>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import HomeSiteList from "@/components/home/homeCom/HomeSiteList";
  import HomeOrdinarySideOut from "@/components/home/homeCom/HomeOrdinarySideOut";
  import GlobalListMask from "@/components/globalCom/GlobalListMask";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  export default {
    name: "EquipmentBinding",
    data() {
      return {
        swtichLoading: false,
        switchHomeSiteList: false, // 站点默认为 hide
        switchHomeListMask: false, // 站点默认为 hide
        switchBindingComplete: false,
        switchHomeOrdinarySideOut: false, // 类型默认为 hide
        HomeOrdinarySideOutTitle: "选择设备类型", // 设备类型的标题
        HomeOrdinarySideOutData: [], // 初始化类型数据
        inpSiteText: "",
        inpTypeText: "",
        inpConingText: "",
        inpQrCodeText: "",
        codingStationId: "",
        childListMaskData: [],
        bindingCodingPrompObj: {},
        bindingPrompObj: {},
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        switchGlobalErrorInformation: false
      };
    },
    methods: {
      /* 方法 */
      siteList() {
        this.switchHomeSiteList = !this.switchHomeSiteList;
      },
      getHomeSiteList(val) {
        this.switchHomeSiteList = val;
      },
      getHomeCurrentType(val) {
        this.codingStationId = val.stationId;
        this.inpSiteText = val.text;
        this.bindingCodingPrompObj.lineId = val.lineId;
        this.bindingCodingPrompObj.stationId = val.stationId;
      },
      bindingType() {
        const THIS = this;
        this.api.deviceTypeDataAll().then(function(data) {
          const DATA = data.data;
          THIS.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            THIS.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].sparePartTypeId,
              sideOutText: DATA[i].categoryName
            });
          }
        });
        THIS.switchHomeOrdinarySideOut = true;
      },
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      getHomeSelectedType(val) {
        this.inpTypeText = val.text;
        this.bindingCodingPrompObj.sparePartTypeId = val.sideOutId;
      },
      coding() {
        if (this.inpTypeText == "" || this.inpSiteText == "") {
          this.EquipmentRepairErrorTitle = "查询错误";
          this.EquipmentRepairError = "请选择站点和类型";
          this.switchGlobalErrorInformation = true;
          return;
        }
        let paramObj = {
          lineId: this.bindingCodingPrompObj.lineId,
          stationId: this.bindingCodingPrompObj.stationId,
          sparePartTypeId: this.bindingCodingPrompObj.sparePartTypeId
        };
        this.api.deviceCodingDataAll(paramObj).then(data => {
          const DATA = data.data;
          this.childListMaskData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            this.childListMaskData.push({
              listMaskId: DATA[i].equipmentId,
              listMaskText: DATA[i].equipmentNo,
              listMaskQrCode: DATA[i].qrCode
            });
          }
        });
        this.switchHomeListMask = true;
      },
      getHomeListMask(val) {
        this.switchHomeListMask = val;
      },
      getHomeListMaskData(val) {
        let valArr = val.split(",");
        this.inpConingText = valArr[0];
        this.bindingPrompObj.equipmentId = valArr[1];
      },
      qrCode() {
        this.wxApi.WxScanQRCode().then(data => {
          this.api.bindingQrCodeDataAll(data).then((data) => {
            if (data.statusCode == '201') {
              this.EquipmentRepairErrorTitle = "扫码错误";
              this.EquipmentRepairError = data.message;
              this.switchGlobalErrorInformation = true;
              return;
            }
            this.inpQrCodeText = data.data;
            this.bindingPrompObj.qrCode = data.data;
          })
        });
      },
      binding() {
        if (!this.bindingPrompObj.qrCode) {
          this.bindingPrompObj.qrCode = "";
        }
        if (this.inpTypeText == "") {
          this.EquipmentRepairErrorTitle = "绑定错误";
          this.EquipmentRepairError = "请选择设备编码!";
          this.switchGlobalErrorInformation = true;
          return;
        }
        this.swtichLoading = true;
        this.api.bindingDeviceDataAll(this.bindingPrompObj).then(data => {
          if (data.result == "success") {
            this.swtichLoading = false;
            this.switchBindingComplete = true;
          } else {
            this.swtichLoading = false;
            this.EquipmentRepairErrorTitle = "绑定错误";
            this.EquipmentRepairError = data.message;
            this.switchGlobalErrorInformation = true;
          }
        });
      },
      qrCodeDevice() {
        this.wxApi.WxScanQRCode().then(data => {
          this.api.scanningQrCodeDataAll(data).then((data) => {
            const DATA = data.data;
            if (DATA == null) {
              this.EquipmentRepairErrorTitle = "扫码错误";
              this.EquipmentRepairError = '该二维码不存在!';
              this.switchGlobalErrorInformation = true;
              return;
            }
            this.inpSiteText = DATA.stationName;
            this.bindingPrompObj.lineId = DATA.lineId;
            this.bindingPrompObj.stationId = DATA.stationId;
            this.inpTypeText = DATA.categoryName;
            this.bindingPrompObj.equipmentId = DATA.equipmentId;
            this.$nextTick(function() {
              this.inpConingText = DATA.equipmentNo;
            })
          })
        });
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      close() {
        this.switchBindingComplete = false;
      },
      determine() {
        this.switchBindingComplete = false;
        // this.initInp();
      },
      initInp() {
        this.inpSiteText = "";
        this.inpTypeText = "";
        this.inpConingText = "";
        this.codingStationId = "";
        this.bindingCodingPrompObj = {};
        this.bindingPrompObj = {};
      }
    },
    components: {
      /* 复用组件名称 */
      HomeSiteList,
      GlobalListMask,
      HomeOrdinarySideOut,
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
    },
    watch: {
      /* 监听 */
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #EquipmentBinding {
    width: 100%;
    height: 100%;
    padding: 20px;
    box-sizing: border-box;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .EquipmentBinding-main {
      width: 100%;
      padding: 20px;
      background-color: #fff;
      border: solid 1px #dfdcdc;
      box-sizing: border-box;
      border-radius: 15px;
      .main-list {
        margin-bottom: 320px;
        width: 100%;
        padding-top: 26px;
        li {
          display: flex;
          padding-left: 20px;
          width: 100%;
          height: 74px;
          font-size: $widthWeb22;
          line-height: 74px;
          border-bottom: solid 1px #dfdcdc;
          box-sizing: border-box;
          #inp-site {
            color: #fc6806;
          }
          .inp-title {
            // display: inline-block;
            // width: 800px;
            // overflow: hidden;
            // text-overflow: ellipsis;
            white-space: nowrap;
          }
          .inp-txt {
            flex: 1; // display: inline-block;
            height: 72px;
            text-align: right;
            color: #333;
          }
          .inp-icon {
            flex-shrink: 0;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            width: 40px;
            height: 74px;
              margin-left: 20px;
            i {
              margin-right: 20px;
              width: 16px;
              height: 27px;
              background-image: url("../../../../assets/home/arrow.png");
              background-size: 100% 100%;
            }
            .icon-qrCode {
              margin-right: 15px;
              width: 29px;
              height: 28px;
              background-image: url("../../../../assets/home/scan.png");
              background-size: 100% 100%;
            }
          }
          .iconQeCodeArrow {
            height: 74px;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            .arrow {
              display: flex;
              justify-content: flex-end;
              align-items: center;
              width: 60px;
              height: 100%;
              i {
                margin-right: 20px;
                display: inline-block;
                width: 16px;
                height: 27px;
                background: url("../../../../assets/home/arrow.png");
                background-size: 100% 100%;
              }
            }
            .qrCode {
              display: flex;
              justify-content: flex-end;
              align-items: center;
              width: 60px;
              height: 100%;
              text-align: center;
              i {
                margin-right: 20px;
                display: inline-block;
                width: 29px;
                height: 28px;
                background: url("../../../../assets/home/scan.png");
                background-size: 100% 100%;
              }
            }
          }
          #inp-qrCode,
          #inp-coding {
            text-align: center;
          }
        }
      }
      .main-btn {
        width: 100%;
        text-align: center;
        margin-bottom: 86px;
        button {
          width: 124px;
          height: 40px;
          color: #fff;
          font-size: $widthWeb22;
          background-color: #fd8521;
        }
      }
    }
    .EquipmentBinding-bindingComplete {
      z-index: 200;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
      .bindingComplete-main {
        position: absolute;
        top: 344px;
        left: 50%;
        transform: translateX(-50%);
        width: 540px;
        background-color: #faf6ec;
        border-radius: 14px;
        .main-title {
          margin-bottom: 36px;
          position: relative;
          color: #f15510;
          font-size: $widthWeb25;
          text-align: center;
          line-height: 80px;
          background-color: #fce4bd;
          border-top-left-radius: 14px;
          border-top-right-radius: 14px;
          .title-icon {
            position: absolute;
            top: 0;
            right: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 105px;
            height: 100%;
            i {
              display: inline-block;
              width: 35px;
              height: 35px;
              background-image: url("../../../../assets/home/close.png");
              background-size: 100% 100%;
            }
          }
        }
        .main-content {
          margin-bottom: 58px;
          width: 100%;
          text-align: center;
          .content-img {
            width: 100%;
            height: 143px;
            margin-bottom: 30px;
            i {
              display: inline-block;
              width: 155px;
              height: 143px;
              background-image: url("../../../../assets/home/ok.png");
              background-size: 100% 100%;
            }
          }
          p {
            color: #333;
            font-size: $widthWeb25;
          }
        }
        .main-determine {
          margin-bottom: 60px;
          width: 100%;
          text-align: center;
          button {
            width: 124px;
            height: 40px;
            color: #fff;
            font-size: $widthWeb22;
            background-color: #fd8521;
          }
        }
      }
    }
  }
</style>
