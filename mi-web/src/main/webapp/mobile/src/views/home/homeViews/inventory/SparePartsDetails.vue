<template>
  <div id='SparePartsDetails'>
    <HomeInventoryDetails :inventoryDetailsData="childInventoryDetailsData" :inventoryDetailsImages="childInventoryDetailsImages"></HomeInventoryDetails>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import HomeInventoryDetails from "@/components/home/homeCom/HomeInventoryDetails";
  export default {
    name: "SparePartsDetails",
    data() {
      return {
        swtichLoading: true,
        childInventoryDetailsData: [],
        childInventoryDetailsImages: [],
        prompObj: {
          sparePartId: "",
          userDeviceId: ""
        }
      };
    },
    methods: {
      /* 方法 */
      initData() {
        const IDARR = this.$route.params.id.split(",");
        this.prompObj.sparePartId = IDARR[0];
        this.prompObj.userDeviceId = IDARR[1];
        this.api.mySparePartsDetailsDataAll(this.prompObj).then(data => {
          const DATA = data.data.partSparePartEntity;
          const DATAIMG = data.data.partSparePartImageEntities;
          this.childInventoryDetailsData.push({
            listName: DATA.partName,
            // listArr: [
            //   {
            //     InventoryDetailsName: DATA.categoryName,
            //     InventoryDetailsVice: "备件类型"
            //   },
            //   {
            //     InventoryDetailsName: DATA.specificationModel,
            //     InventoryDetailsVice: "备件型号"
            //   },
            //   {
            //     InventoryDetailsName: DATA.materiaCoding,
            //     InventoryDetailsVice: "物资编号"
            //   },
            //   {
            //     InventoryDetailsName: DATA.status == "start_using" ? "好件" : "坏件",
            //     InventoryDetailsVice: "备件状态"
            //   }
            // ],
            listDetailsArr: [{
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
                InventoryDetailsVice: "序列号"
              },
              {
                InventoryDetailsName: DATA.equipmentNO,
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
          this.$nextTick(function() {
            this.swtichLoading = false;
          })
        });
      }
    },
    components: {
      /* 复用组件名称 */
      HomeInventoryDetails
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData();
    },
    watch: {
      /* 监听 */
    }
  };
</script>

<style lang='scss'>
  #SparePartsDetails {
    background-color: #faf6ec;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
  }
</style>
