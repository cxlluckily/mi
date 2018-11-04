<template>
  <div id='InventoryManagement'>
    <!--<span class="btn-success">盘点</span>-->
    <div class="Management_btn"><span class="btn-success" @click="searchSubmit">查询</span></div>
    <div class="InventoryManagement-main">
      <mt-loadmore
        :bottomPullText='bottomText'
        :bottom-method="getList"
        :bottom-all-loaded="allLoaded"
        ref="loadmore"
        :autoFill='noInitialize'>
        <HomeManagementList :inventoryListData="childInventoryListData"
                            v-if="childInventoryListData.length"></HomeManagementList>
      </mt-loadmore>
    </div>
    <div class="gloobalEmptyPrompt" v-show="!childInventoryListData.length">
      <!--<div class="emptyPrompt">暂 无 数 据 <br/>请 点 击 查 询 !</div>-->
      <div class="emptyPrompt">{{emptyPrompt}}</div>
    </div>
    <!--<div class="globalFooter">-->
      <!--<button @click="$router.back(-1)">返回</button>-->
    <!--</div>-->
    <router-view class="routerView"></router-view>
    <!--侧边栏右滑-->
    <HomeInventorySearch :isSearch="isSearchCom" @switchSearchCom=getSwitchSearchCom @SearchCom="SearchCom">
    </HomeInventorySearch>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom"
                            :errorInformation="GlobalTroubleNumberError"
                            :errorInformationTitle="GlobalTroubleNumberErrorTitle"></GlobalErrorInformation>
  </div>
</template>

<script>
  import HomeInventorySearch from '@/components/home/homeCom/HomeInventorySearch'
  import HomeManagementList from "@/components/home/homeCom/HomeManagementList";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation"
  import Toast from 'mint-ui'

  export default {
    name: "InventoryManagement",
    data() {
      return {
        //错误提示
        switchGlobalErrorInformation: false,
        GlobalTroubleNumberError: '',
        GlobalTroubleNumberErrorTitle: '',

        emptyPrompt: '请点击查询，选择仓库进行查看！',
        childInventoryTitle: "盘点",
        bottomText: "上拉加载更多...",
        allLoaded: false, // mint-ui Loadmore 如果数据加载完毕把它变成 true , 就不会触发上拉刷新了
        noInitialize: false, // 是否默认填充数据
        isSearchCom: false,
        prompObj: {
          inventoryType: "all",
          start: 0,
          limit: 20,
          status: "all",
          warehouseId: "",
          partName: '',
          sparePartTypeId: ""
        },
        childInventoryListData: [],

      };
    },
    methods: {
      /* 方法 */
      initData() {
        let _this = this;
        _this.requests.getWarehousesByUser().then(function (respont) {
          if (respont.result == 'success') {
            var respones = respont.data;
            if (respones.length > 0) {
              _this.prompObj.warehouseId = respones[0].code;
              _this.getList();
            } else {
              _this.emptyPrompt = '您没有仓库权限!';
            }
          }
        });
      },
      getList() {
        let _this = this;
        _this.$refs.loadmore.onBottomLoaded();
        _this.prompObj.start = _this.childInventoryListData.length;
        _this.api.inventoryManagementDataAll(_this.prompObj).then((data) => {
          if (data.result == 'success') {
            if (data.result == 'success') {
              if (data.data.rows.length > 0) {
                for (let i = 0; i < data.data.rows.length; i++) {
                  _this.childInventoryListData.push(data.data.rows[i]);
                }
                if (_this.childInventoryListData.length == 0) {
                  _this.errorMessageShow("温馨提示", "没有查找到备件！");
                } else if (_this.childInventoryListData.length == data.data.totalCount) {
                  _this.allLoaded = true;
                }
              } else {

              }
            } else {
              _this.errorMessageShow("温馨提示", data.message);
            }
          }
        })
      },

      getHideCurrentCom() {
        this.switchGlobalErrorInformation = false;
      },
      errorMessageShow(title, message) {
        this.switchGlobalErrorInformation = true;
        this.GlobalTroubleNumberError = message;
        this.GlobalTroubleNumberErrorTitle = title;
      },

      searchSubmit() {
        this.isSearchCom = !this.isSearchCom;
      },
      getSwitchSearchCom(is) {
        this.isSearchCom = is;
      },
      SearchCom(val) {
        let _this = this;
        this.prompObj.inventoryType = val.InventoryType;
        this.prompObj.status = val.sparestatus;
        this.prompObj.warehouseId = val.WarehouseId;
        this.prompObj.partName = val.partName;
        this.prompObj.sparePartTypeId = val.sparePartTypeId;
        this.childInventoryListData = [];
        this.getList();
      },
    },
    components: {
      /* 复用组件名称 */
      HomeManagementList,
      HomeInventorySearch,
      GlobalErrorInformation
    },
    mounted: function () {
      /* 初始化数据 */
      const _this = this;
      _this.initData();
    }
  };
</script>

<style lang='scss' scope type="text/scss">
  #InventoryManagement {
    .Management_btn {
      text-align: right;
      .btn-success {
        display: inline-block;
        padding: 10px 20px;
        margin: 10px 20px;
        background: #fd8622;
        color: #fff;
        border-radius: 5px;
      }
    }
    .InventoryManagement-main {
      padding: 0 20px;
      height: calc(100% - 66px);
      overflow: scroll;
    }
    .routerView {
      z-index: 200;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
    }
  }
</style>
