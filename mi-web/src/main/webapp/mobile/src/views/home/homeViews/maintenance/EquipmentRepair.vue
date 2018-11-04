<template>
  <div id='EquipmentRepair'>
    <HomeMaintenanceTitle :maintenanceTitle="RepairDispatchingTitle" :maintenancePath="ReviewEvaluationPath"></HomeMaintenanceTitle>
    <div class="EquipmentRepair-main">
      <form autocomplete="off">
        <div class="fromInptxt" @click="siteList">
          <label for="chooseSite" class="inp-title">选择站点</label>
          <input type="text" unselectable="on" onfocus="this.blur()" name="site" id="chooseSite" class="inp-txt" placeholder="请选择线路及站点" readonly="readonly" v-model="inpSite">
          <div class="arrow-icon">
            <i></i>
          </div>
        </div>
        <div class="fromInptxt" @click="deviceList">
          <label for="deviceSite" class="inp-title">设备类型</label>
          <input type="text" unselectable="on" onfocus="this.blur()" name="type" id="deviceSite" class="inp-txt" placeholder="请选择设备类型" readonly="readonly" v-model="inpType">
          <div class="arrow-icon">
            <i></i>
          </div>
        </div>
        <div class="fromInptxt qrFromInptxt">
          <label for="deviceCoding" class="inp-title">设备编码</label>
          <input type="text" unselectable="on" onfocus="this.blur()" name="coding" id="deviceCoding" class="inp-txt" placeholder="请选择编码" readonly="readonly" v-model="inpCoding">
          <div class="icon">
            <div class="qrCode" @click="qrCodeDevice">
              <i></i>
            </div>
            <div class="arrow" @click="deviceCoding">
              <i></i>
            </div>
          </div>
        </div>
        <div class="fromInptxt qrFromInptxt mb-18">
          <label for="errorCode" class="inp-title">错误代码</label>
          <input type="text" name="error" id="errorCode" class="inp-txt" placeholder="可输入或扫码" v-model="inpErrorCode">
          <div class="icon">
            <div class="qrCode" @click="qrCodeError">
              <i></i>
            </div>
          </div>
        </div>
        <div class="fault mlr-44">
          <div class="title">
            <p>故障现象</p>
          </div>
          <GlobalCheckbox :checkboxData="GlobalCheckboxData" @checkboxCurrentType="getCheckboxCurrentType">
          </GlobalCheckbox>
        </div>
        <div class="description mlr-44">
          <div class="title">
            <p>故障描述</p>
            <!-- <span>( 最多可输入200个汉子 )</span> -->
          </div>
          <textarea class="textBox" placeholder="请输入故障描述" v-model="richText">
                                                        </textarea>
        </div>
        <div class="uploadImg mlr-44">
          <div class="title">
            <p>上传图片</p>
            <span></span>
          </div>
          <GlobalUploadImg :swtichWxUploadImage="goWxUploadImage" :swtichWxUploadData="childWxUploadData" @wxUploadImageData="getWxUploadImageData" @wxLocalImageData="getWxLocalImageData">
          </GlobalUploadImg>
        </div>
        <div class="btn">
          <button type="button" @click="submitFrom">提 交</button>
        </div>
      </form>
    </div>
    <HomeSiteList :switchLienSite="switchHomeSiteList" @switchHomeSiteList="getHomeSiteList" @HomeCurrentType='getHomeCurrentType'>
    </HomeSiteList>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle">
    </GlobalErrorInformation>
    <GlobalSuccessInformation v-show="switchGlobalSuccessInformation" @hideSuccessCurrentCom="getHideSuccessCurrentCom" :successInformation="EquipmentRepairSuccess" :successInformationTitle="EquipmentRepairSuccessTitle" :successText="EquipmentRepairSuccessText"
      :routerLinkText="EquipmentRepairRouterLinkText">
    </GlobalSuccessInformation>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import GlobalCheckbox from "@/components/globalCom/GlobalCheckbox";
  import GlobalUploadImg from "@/components/globalCom/GlobalUploadImg";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import GlobalSuccessInformation from "@/components/globalCom/GlobalSuccessInformation";
  import HomeSiteList from "@/components/home/homeCom/HomeSiteList";
  import HomeOrdinarySideOut from "@/components/home/homeCom/HomeOrdinarySideOut";
  import HomeMaintenanceTitle from "@/components/home/homeCom/HomeMaintenanceTitle";
  export default {
    name: "EquipmentRepair",
    data() {
      return {
        RepairDispatchingTitle: "报修记录",
        ReviewEvaluationPath: 'bx',
        swtichLoading: false,
        switchHomeSiteList: false, // 站点默认为 hide
        switchHomeOrdinarySideOut: false, // 类型默认为 hide
        HomeOrdinarySideOutData: [], // 初始化类型数据
        inpSite: "", // 站点 v-model
        inpType: "", // 类型 v-model
        inpCoding: "", // 编码 v-model
        inpCodings: "", // 编码 v-model 缓存变量
        inpErrorCode: "", // 错误代码 v-model
        richText: "", // 故障描述 v-model
        inpObjID: {}, // 储存选中的 ID
        HomeOrdinarySideOutTitle: "", // 设备类型的标题
        GlobalCheckboxData: [], // 故障现象数据
        EquipmentRepairErrorTitle: "",
        EquipmentRepairError: "",
        EquipmentRepairSuccess: "",
        EquipmentRepairSuccessTitle: "",
        EquipmentRepairSuccessText: "继续申请报修",
        EquipmentRepairRouterLinkText: "查看报修记录",
        switchGlobalErrorInformation: false,
        switchGlobalSuccessInformation: false,
        goWxUploadImage: false,
        wxUploadImageData: [],
        childWxUploadData: 0,
        wxLocalImageData: []
      };
    },
    methods: {
      // 接收 站点 传过来的值
      getHomeCurrentType(val) {
        this.inpSite = val.text;
        this.inpObjID.lineId = val.lineId;
        this.inpObjID.stationId = val.stationId;
      },
      // 站点显示隐藏
      siteList() {
        this.switchHomeSiteList = true;
      },
      // 站点显示隐藏
      getHomeSiteList(val) {
        this.switchHomeSiteList = val;
      },
      // 点击设备类型请求数据, 并更新到子组件中
      deviceList() {
        // 请求类型数据
        this.HomeOrdinarySideOutTitle = "选择设备类型";
        this.api.deviceTypeDataAll().then(data => {
          const DATA = data.data;
          this.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].sparePartTypeId,
              sideOutText: DATA[i].categoryName
            });
          }
        });
        this.switchHomeOrdinarySideOut = true;
      },
      // 点击设备编号请求数据, 并更新到子组件中
      deviceCoding() {
        const THIS = this;
        this.HomeOrdinarySideOutTitle = "选择设备编号";
        if (!this.inpObjID.sparePartTypeId || !this.inpObjID.stationId) {
          this.EquipmentRepairErrorTitle = "查询错误";
          this.EquipmentRepairError = "请选择站点和类型";
          this.switchGlobalErrorInformation = true;
          return;
        }
        let paramObj = {
          lineId: this.inpObjID.lineId,
          stationId: this.inpObjID.stationId,
          sparePartTypeId: this.inpObjID.sparePartTypeId
        };
        this.api.deviceCodingDataAll(paramObj).then(function(data) {
          const DATA = data.data;
          THIS.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            THIS.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].equipmentId,
              sideOutText: DATA[i].equipmentNo
            });
          }
        });
        this.switchHomeOrdinarySideOut = true;
      },
      // (设备类型选/设备编号)传过来隐藏子组件的 Boolean
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      // (设备类型/设备编号)选中的值和ID
      getHomeSelectedType(val) {
        if (val.title == "选择设备类型") {
          this.inpType = val.text;
          this.inpObjID.sparePartTypeId = val.sideOutId;
        } else {
          this.inpCoding = val.text;
          this.inpObjID.equipmentId = val.sideOutId;
        }
      },
      // 故障现象选中的ID
      getCheckboxCurrentType(val) {
        this.inpObjID.errors = val;
        this.errorsArr = [];
        for (let i = 0, LL = val.length; i < LL; i++) {
          this.errorsArr.push(val[i]);
        }
      },
      getWxLocalImageData(val) {
        this.wxLocalImageData = val;
      },
      getWxUploadImageData(val) {
        this.wxUploadImageData = val;
        this.serverData();
      },
      // 提交表单
      submitFrom() {
        if (!this.inpCoding) {
          this.EquipmentRepairErrorTitle = "提交错误";
          this.EquipmentRepairError = "请选择设备编码!";
          this.switchGlobalErrorInformation = true;
          return;
        }
        this.swtichLoading = true; // 启动 loadding
        if (this.wxLocalImageData.length != 0) {
          this.goWxUploadImage = true; // 启动微信上传图片
          return;
        }
        this.serverData();
      },
      // 请求封装
      serverData() {
        const fromData = {
          equipmentId: this.inpObjID.equipmentId,
          lineId: this.inpObjID.lineId,
          stationId: this.inpObjID.stationId,
          categoryName: this.inpType,
          errorCode: this.inpErrorCode,
          breakDescribe: this.richText,
          errors: this.inpObjID.errors,
          images: this.wxUploadImageData
        };
        if (!this.inpCoding) {
          this.EquipmentRepairErrorTitle = "提交错误";
          this.EquipmentRepairError = "请选择设备编码!";
          this.switchGlobalErrorInformation = true;
          return;
        }
        this.swtichLoading = true; // 显示 loading 图片
        this.api.repairSubmitDataAll(fromData).then(data => {
          this.EquipmentRepairSuccessTitle = "设备报修";
          this.EquipmentRepairSuccess = "提交成功!";
          this.switchGlobalSuccessInformation = true;
          this.initInp();
          this.swtichLoading = false;
        });
      },
      // 初始化输入框
      initInp() {
        this.inpSite = ""; // 站点 v-model
        this.inpType = ""; // 类型 v-model
        this.inpCoding = ""; // 编码 v-model
        this.inpErrorCode = ""; // 错误代码 v-model
        this.richText = ""; // 故障描述 v-model
        this.GlobalCheckboxData = [];
        this.HomeOrdinarySideOutData = [];
        this.inpObjID = {};
        this.goWxUploadImage = false;
        this.wxLocalImageData = [];
        this.wxUploadImageData = [];
        ++this.childWxUploadData;
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      getHideSuccessCurrentCom(val) {
        this.switchGlobalSuccessInformation = val;
      },
      qrCodeDevice() {
        this.wxApi.WxScanQRCode().then(data => {
          this.api.scanningQrCodeDataAll(data).then((data) => {
            const DATA = data.data;
            if (DATA == null) {
              this.EquipmentRepairErrorTitle = "扫码错误";
              this.EquipmentRepairError = '设备不存在!';
              this.switchGlobalErrorInformation = true;
              return;
            }
            this.inpSite = DATA.stationName;
            this.inpObjID.lineId = DATA.lineId;
            this.inpObjID.stationId = DATA.stationId
            this.inpType = DATA.categoryName;
            this.inpCodings = DATA.equipmentNo;
            this.inpObjID.equipmentId = DATA.equipmentId;
            this.inpObjID.sparePartTypeId = DATA.sparePartTypeId;
            this.$nextTick(function() {
              this.inpCoding = DATA.equipmentNo
            })
          })
        });
      },
      qrCodeError() {
        this.wxApi.WxScanQRCode().then(data => {
          this.inpErrorCode = data;
        });
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalCheckbox,
      GlobalUploadImg,
      GlobalErrorInformation,
      GlobalSuccessInformation,
      HomeSiteList,
      HomeOrdinarySideOut,
      HomeMaintenanceTitle
    },
    mounted: function() {
      /* 初始化数据 */
    },
    watch: {
      /* 监听 */
      inpCoding: function(val) {
        if (!val) {
          return;
        }
        this.api.faultPhenomenonDataAll(this.inpObjID.equipmentId).then(data => {
          this.GlobalCheckboxData = data.data;
        });
      },
      inpSite: function(val) {
        this.GlobalCheckboxData = [];
        this.inpCoding = "";
      },
      inpType: function(val) {
        this.GlobalCheckboxData = [];
        this.inpCoding = "";
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #EquipmentRepair {
    box-sizing: border-box;
    overflow: hidden;
    overflow-y: auto;
    .loadingGif {
      background: url("../../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .EquipmentRepair-main {
      margin: 0 20px 24px;
      background-color: #fff;
      border: solid 1px #dfdcdc;
      border-radius: 15px;
      box-sizing: border-box; // input 样式
      .fromInptxt {
        position: relative;
        margin-top: 6px;
        padding: 0 25px;
        font-size: 0;
        height: 74px;
        box-sizing: border-box;
        .inp-title {
          position: absolute;
          top: 50%;
          left: 44px;
          transform: translateY(-50%);
          font-size: $widthWeb22;
          color: #333;
        }
        .inp-txt {
          padding: 0 65px 0 116px;
          width: 100%;
          height: 100%;
          font-size: $widthWeb22;
          text-align: right;
          box-sizing: border-box;
          border-bottom: 1px solid #dfdcdc;
        }
        .arrow-icon {
          position: absolute;
          bottom: 0;
          right: 4px;
          display: flex;
          justify-content: flex-end;
          align-items: center;
          width: 60px;
          height: 100%;
          text-align: center;
          line-height: 54px;
          i {
            margin-right: 20px;
            display: inline-block;
            width: 16px;
            height: 27px;
            background: url("../../../../assets/home/arrow.png");
            background-size: 100% 100%;
          }
        }
      }
      .qrFromInptxt {
        margin-top: 14px;
        #deviceCoding,
        #errorCode {
          text-align: center;
        }
        .inp-txt {
          padding: 0 116px 0;
        }
        .icon {
          position: absolute;
          right: 7px;
          top: 0px;
          height: 100%;
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
      }
      .mb-18 {
        margin-bottom: 18px;
      } // 公共样式
      .mlr-44 {
        margin: 0 44px;
      }
      .title {
        display: flex;
        font-size: 0;
        line-height: 48px;
        p {
          font-size: $widthWeb22;
          color: #333;
        }
        span {
          margin-left: 10px;
          font-size: $widthWeb16;
          color: #bfbfbf;
        }
      } // Check 样式
      .fault {
        margin-bottom: 18px;
      }
      .description {
        margin-bottom: 18px;
        .textBox {
          margin-top: 9px;
          padding: 21px 18px;
          width: 100%;
          height: 154px;
          font-size: $widthWeb18;
          color: #333;
          resize: none;
          border: solid 1px #bfbfbf;
          box-sizing: border-box;
        }
      }
      .uploadImg {
        margin-bottom: 34px;
      }
      .btn {
        margin-bottom: 30px;
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
  }
</style>
