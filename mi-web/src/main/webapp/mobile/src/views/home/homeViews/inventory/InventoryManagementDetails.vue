<template>
  <div id='InventoryManagementDetails'>
    <HomeInventoryManagementDetails
      :inventoryDetailsData="childInventoryDetailsData"
      :inventoryDetailsImages="childInventoryDetailsImages"
      :inventoryTablesData="childInventoryTablesData"
    ></HomeInventoryManagementDetails>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import HomeInventoryManagementDetails from "@/components/home/homeCom/HomeInventoryManagementDetails";

  export default {
    name: "InventoryManagementDetails",
    data() {
      return {
        swtichLoading: true,
        childInventoryDetailsData: [],
        childInventoryDetailsImages: [],
        childInventoryTablesData: [],
        prompObj: {
          sparePartId: "",
        },
        pramara: {
          inventoryType: "",
          sparePartId: "",
          status: "",
          warehouseId: "",
          limit: 20,
          start: 0
        }
      };
    },
    methods: {
      /* 方法 */
      initData() {
        const IDARR = this.$route.params.id.split(",");
        const _this = this;
        this.pramara.sparePartId = IDARR[0];
        this.pramara.warehouseId = IDARR[1];
        this.pramara.inventoryType = IDARR[2];
        this.pramara.status = IDARR[3];
        this.api.getPagerstockPartDetailInfo(this.pramara).then(data => {
          const DATA = data.data.partSparePartEntity;
          const DATAIMG = data.data.partSparePartImageEntities;
          this.childInventoryDetailsData.push({
            listName: DATA.partName,
            listArr: [],
            listDetailsArr: [
              {
                InventoryDetailsName: DATA.warehouseName,
                InventoryDetailsVice: "所属仓库"
              },
              {
                InventoryDetailsName: DATA.categoryName,
                InventoryDetailsVice: "备件类型"
              },
              {
                InventoryDetailsName: DATA.status == "start_using" ? "好件" : "坏件",
                InventoryDetailsVice: "备件状态"
              },
              {
                InventoryDetailsName: DATA.brand,
                InventoryDetailsVice: "品牌"
              },
              {
                InventoryDetailsName: DATA.materiaCoding,
                InventoryDetailsVice: "物资编号"
              },
              {
                InventoryDetailsName: DATA.specificationModel,
                InventoryDetailsVice: "备件型号"
              },
              {
                InventoryDetailsName: DATA.size,
                InventoryDetailsVice: "尺寸"
              },
              {
                InventoryDetailsName: DATA.units,
                InventoryDetailsVice: "单位"
              },
              {
                InventoryDetailsName: DATA.material,
                InventoryDetailsVice: "材质"
              },
              {
                InventoryDetailsName: DATA.serialNumber,
                InventoryDetailsVice: "数量"
              },
              {
                InventoryDetailsName: DATA.inventoryType == 'unique' ? '唯一标识' : '非唯一标识',
                InventoryDetailsVice: "唯一标识"
              },
              {
                InventoryDetailsName: DATA.remark,
                InventoryDetailsVice: "备注"
              }
            ]
          });
          for (let i = 0, II = DATAIMG.length; i < II; i++) {
            this.childInventoryDetailsImages.push(DATAIMG[i].fullImageUrl);
          }
          this.$nextTick(function () {
            this.swtichLoading = false;
          });
          this.requests.getPagerDetailInfo(this.pramara).then(responsble => {
            const dataData = responsble.data.rows;
            if (responsble.result == "success") {
              _this.childInventoryTablesData = dataData;
              var totalCount = 0;
              _this.childInventoryTablesData.forEach(function (item) {
                totalCount += item.account;
              });
              _this.childInventoryDetailsData[0].listDetailsArr[9].InventoryDetailsName = totalCount;
            }
          })
        });

      }
    },
    components: {
      /* 复用组件名称 */
      HomeInventoryManagementDetails
    },
    mounted: function () {
      /* 初始化数据 */
      this.initData();
    },
    watch: {
      /* 监听 */
    }
  };
</script>

<style lang='scss'>
  #InventoryManagementDetails {
    background-color: #faf6ec;
    overflow-y: scroll;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
  }
</style>
