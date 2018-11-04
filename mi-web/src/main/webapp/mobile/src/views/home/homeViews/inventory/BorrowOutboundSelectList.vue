<template>
    <div id='BorrowOutboundSelectLis'>
        <div class="BorrowOutboundSelectLis-main">
            <div class="BorrowOutboundSelectLis-from">
                <div class="fromSearch">
                    <label for="sea"></label>
                    <input type="text" id="sea" placeholder="物品名称/所属分类/品牌/型号" v-model="inpSearch">
                </div>
                <div class="fromInptxt" @click="supplier">
                    <label for="deviceSite" class="inp-title">供应商</label>
                    <input type="text" unselectable="on" onfocus="this.blur()" id="deviceSite" class="inp-txt" placeholder="请选择供应商" readonly="readonly" v-model="inpSupplier">
                    <div class="arrow-icon">
                        <i></i>
                    </div>
                </div>
                <div class="fromInptxt" @click="shelvesList">
                    <label for="deviceSite" class="inp-title">存放位置</label>
                    <input type="text" unselectable="on" onfocus="this.blur()" id="deviceSite" class="inp-txt" placeholder="请选择存放位置" readonly="readonly" v-model="inpLocation">
                    <div class="arrow-icon">
                        <i></i>
                    </div>
                </div>
            </div>
            <div class="BorrowOutboundSelectLis-list">
                <div class="list-empty" v-show='listDataArr.length == 0'>暂 无 数 据 !</div>
                <ul>
                    <li v-for="itemList in listDataArr" :key="itemList.stockId">
                        <label :for="itemList.stockId">
                                <div class="list-checkbox">
                                <i :class="{'iconSelected' : '-1' != checkedNames.indexOf(itemList.stockId)}"
                                    :data-sparePartId="itemList.sparePartId"></i>
                                <input type="checkbox"
                                        style="display: none;"
                                        :id="itemList.stockId"
                                        :value="itemList.stockId"
                                        v-model="checkedNames">
                                <span>{{itemList.partName}}</span>
                            </div>
                            <div class="list-details">
                                <div class="details-img">
                                    <img v-if="itemList.imageUrl" :src="itemList.imageUrl">
                                    <img v-else src="../../../../assets/home/s_pic1.png">
                                </div>
                                <div class="details-content">
                                    <div class="content-left">
                                        <p><span>分类 :</span><span>{{itemList.categoryName}}</span></p>
                                        <p><span>型号 :</span><span>{{itemList.specificationModel}}</span></p>
                                    </div>
                                    <div class="content-right">
                                        <p><span>品牌 :</span><span>{{itemList.brand}}</span></p>
                                        <p><span>唯一标识 :</span><span>{{itemList.inventoryType == 'unique'?'是':'否'}}</span></p>
                                    </div>
                                    <div class="details-status">
                                        <p><span>备件状态 :</span><span>{{itemList.stockStatus=='normal'?'好件':'坏件'}}</span></p>
                                    </div>
                                    <div class="details-qrCode" v-if="itemList.inventoryType == 'unique'">
                                        <p><span>二维码 :</span><span>{{itemList.qrCode || '暂无!'}}</span></p>
                                    </div>
                                </div>
                            </div>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
        <div class="globalFooter">
            <button @click="goFather">确定</button>
        </div>
        <!-- 错误提示 -->
        <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EquipmentRepairError" :errorInformationTitle="EquipmentRepairErrorTitle">
        </GlobalErrorInformation>
        <!-- 侧出 -->
        <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut" :ordinarySideOutTitle='HomeOrdinarySideOutTitle' :ordinarySideOutData="HomeOrdinarySideOutData" @switchHomeOrdinarySideOut="getHomeDeviceType" @HomeSelectedType='getHomeSelectedType'>
        </HomeOrdinarySideOut>
        <!-- 存放位置 -->
        <HomeShelvesList :switchLienSite="switchHomeShelvesList" :switchWarehouseId="warehouseId" @switchHomeShelvesList="getHomeShelvesList" @HomeCurrentType='getHomeShelvesType'>
        </HomeShelvesList>
        <!-- loading -->
        <div class="gloobalLoading" v-show="swtichLoading">
            <div class="loadingGif"></div>
        </div>
    </div>
</template>

<script>
import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
import HomeOrdinarySideOut from "@/components/home/homeCom/HomeOrdinarySideOut";
import HomeShelvesList from "@/components/home/homeCom/HomeShelvesList";
export default {
  name: "BorrowOutboundSelectLis",
  data() {
    return {
      swtichLoading: true,
      inpSearch: "",
      inpSupplier: "",
      inpLocation: "",
      paramsObj: {
          userKey: localStorage.getItem('userKey'),
          status:"all",
      },
      listDataArr: [],
      checkedNames: [],
      switchGlobalErrorInformation: false,
      EquipmentRepairErrorTitle: "",
      EquipmentRepairError: "",
      switchHomeOrdinarySideOut: false, // 类型默认为 hide
      HomeOrdinarySideOutData: [], // 初始化类型数据
      HomeOrdinarySideOutTitle: "", // 设备类型的标题
      switchHomeShelvesList: false,
      warehouseId: this.$route.query.warehouseId
    };
  },
  methods: {
    /* 方法 */
    initData(paramsObj) {
      this.requests.getSparePartInWarehouse(paramsObj).then(data => {
        this.listDataArr = data.data;
        this.$nextTick(function() {
          this.swtichLoading = false;
        });
        console.log(this.listDataArr);
      });
    },
    goFather() {
      this.$router.replace({
        path: "/BorrowingOutDetails",
        query: {
          params: this.checkedNames,
        }
      });
    },
    getHideCurrentCom(val) {
      this.switchGlobalErrorInformation = val;
    },
    supplier() {
      this.requests.getSupplierList().then(data => {
        const DATA = data.data;
        this.HomeOrdinarySideOutTitle = "选择供应商";
        this.HomeOrdinarySideOutData = [{sideOutId:'',sideOutText:'全部'}];
        for (let i = 0, II = DATA.length; i < II; i++) {
          this.HomeOrdinarySideOutData.push({
            sideOutId: DATA[i].id,
            sideOutText: DATA[i].name
          });
        }
        this.$nextTick(function() {
          this.switchHomeOrdinarySideOut = true;
        });
      });
    },
    getHomeDeviceType(val) {
      this.switchHomeOrdinarySideOut = val;
    },
    getHomeSelectedType(val) {
      this.paramsObj.supplierId = val.sideOutId;
      this.inpSupplier = val.text;
    },
    shelvesList() {
      this.switchHomeShelvesList = true;
    },
    // 货架显示隐藏
    getHomeShelvesList(val) {
      this.switchHomeShelvesList = val;
    },
    // 接收 货架 传过来的值
    getHomeShelvesType(val) {
      console.log(val);
      this.inpLocation = val.houseNoAndShelfNumber;
    }
  },
  beforeRouteLeave(to, from, next) {
    console.log(to)
    console.log(from)
    if (to.path == "/Outbound" && from.path == "/BorrowOutboundSelectList") {
      next({
        path: "/BorrowingOutDetails"
      });
    } else {
      next();
    }
  },
  components: {
    /* 复用组件名称 */
    GlobalErrorInformation,
    HomeOrdinarySideOut,
    HomeShelvesList
  },
  mounted: function() {
    /* 初始化数据 */
    this.paramsObj.warehouseId = this.$route.query.warehouseId;
    this.checkedNames = this.$route.query.checkedStockId;
    this.initData(this.paramsObj);
  },
  watch: {
    /* 监听 */
    inpSupplier: function(val) {
      this.initData(this.paramsObj);
    },
    inpLocation: function(val) {
      const VALARR = val.split(" ");
      this.paramsObj.houseNo = VALARR[0];
      this.paramsObj.shelfNumber = VALARR[1];
      this.initData(this.paramsObj);
    },
    inpSearch: function(val) {
      console.log(val);
      this.paramsObj.searchContent = val;
      this.initData(this.paramsObj);
    }
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#BorrowOutboundSelectLis {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box; // input 样式
  .loadingGif {
    background: url("../../../../assets/home/loading.gif");
    background-size: 100% 100%;
  }
  .BorrowOutboundSelectLis-main {
    width: 100%;
    height: calc(100% - 80px);
    padding: 20px 25px;
    background-color: #fff;
    border: solid 1px #dfdcdc;
    border-radius: 15px;
    box-sizing: border-box;
    .BorrowOutboundSelectLis-from {
      .fromSearch {
        position: relative;
        width: 100%;
        height: 55px;
        label {
          position: absolute;
          top: 8px;
          right: 14px;
          width: 39px;
          height: 39px;
          background: url("../../../../assets/home/icon_search.png") no-repeat;
          background-size: 100% 100%;
        }
        input {
          width: 100%;
          height: 55px;
          font-size: $widthWeb18;
          color: #333;
          text-align: center;
          border-radius: 15px;
          border: 1px solid #dfdcdc;
          box-sizing: border-box;
        }
      }
      .fromInptxt {
        position: relative;
        margin-top: 6px;
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
    }
    .BorrowOutboundSelectLis-list {
      width: 100%;
      height: calc(100% - 230px);
      padding-left: 10px;
      overflow: hidden;
      overflow-y: auto;
      box-sizing: border-box;
      .list-empty {
        width: 100%;
        height: 100%;
        padding-top: 50px;
        font-size: $widthWeb25;
        color: #666;
        text-align: center;
        box-sizing: border-box;
      }
      li {
        width: 100%;
        margin-top: 24px;
        &:nth-child(1) {
          margin-top: 34px;
        }
        label {
          width: 100%;
          .list-checkbox {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            font-size: 0;
            i {
              margin-right: 20px;
              display: inline-block;
              width: 18px;
              height: 18px;
              background: url("../../../../assets/home/checkbox_no.png")
                no-repeat;
              background-size: 100% 100%;
            }
            .iconSelected {
              background: url("../../../../assets/home/checkbox_sel.png")
                no-repeat;
              background-size: 100% 100%;
            }
            span {
              font-size: $widthWeb18;
              color: #333;
            }
          }
          .list-details {
            display: flex;
            align-items: center;
            width: calc(100% - 38px);
            margin-left: 38px;
            padding-bottom: 16px;
            border-bottom: 1px solid #dfdcdc;
            box-sizing: border-box;
            .details-img {
              display: inline-block;
              width: 70px;
              height: 70px;
              margin-right: 20px;
              flex-shrink: 0;
              img {
                width: 100%;
                height: 100%;
              }
            }
            .details-content {
              display: flex;
              width: calc(100% - 90px);
              flex-shrink: 0;
              flex-wrap: wrap;
              p {
                font-size: $widthWeb18;
                line-height: $widthWeb18 + 16px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                span {
                  color: #999;
                  &:nth-last-child(1) {
                    color: #333;
                    margin-left: 20px;
                  }
                }
              }
              .content-left {
                width: 50%;
                padding-right: 5px;
                box-sizing: border-box;
                flex-shrink: 0;
              }
              .content-right {
                width: 50%;
                padding-left: 5px;
                box-sizing: border-box;
                flex-shrink: 0;
              }
              .details-qrCode {
                width: 100%;
                flex-shrink: 0;
              }
            }
          }
        }
      }
    }
  }
}
</style>
