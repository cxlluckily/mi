<template>
  <div id='HomeInventorySearch'>
    <div class="content">
      <div class="left" @click="leftSearch"></div>
      <transition name="slide-fade">
        <div class="right" v-show="isSearch">
          <div class="right-inp">
            <div class="title">
              <p>仓库</p>
            </div>
            <div class="fromText">
              <input class="input" type="text" id="warehouse" v-model="warehouseName" @click="warehouseId=true" readonly="readonly">
              <mt-popup v-model="warehouseId" position="bottom" style="width:100%">
                <div class="picker-toolbar" style="width:90%;margin:15px auto;">
                  <span class="mint-datetime-cancel" @click='warehouseIds'>取消</span>
                  <span class="mint-datetime-confirm" @click='warehouseIds'>确定</span>
                </div>
                <mt-picker  :slots="slotWare"  valueKey="name" @change="onValuesWarehouseId"></mt-picker>
              </mt-popup>
            </div>
          </div>
          <div class="right-state">
            <div class="title">
              <p>备件名称</p>
            </div>
            <div class="fromText">
              <input class="input"  type="text" v-model="sendData.partName">
            </div>
          </div>
          <div class="right-state">
            <div class="title">
              <p>库存类型</p>
            </div>
            <div class="fromText">
            <input class="input"  type="text" id="status" v-model="spareState" @click="Inventory=true" readonly="readonly">
            <mt-popup v-model="Inventory" position="bottom" style="width:100%">
              <div class="picker-toolbar" style="width:90%;margin:15px auto;">
                <span class="mint-datetime-cancel" @click='Inventorys'>取消</span>
                <span class="mint-datetime-confirm" @click='Inventorys'>确定</span>
              </div>
              <mt-picker  :slots="slots"  valueKey="name" @change="spareStateChange"></mt-picker>
            </mt-popup>
            </div>
          </div>
          <div class="right-state">
            <div class="title">
              <p>备件状态</p>
            </div>
            <div class="fromText">
              <input class="input"  type="text"  v-model="InventoryType" @click="warehouse=true" readonly="readonly">
              <mt-popup v-model="warehouse" position="bottom" style="width:100%">
                <div class="picker-toolbar" style="width:90%;margin:15px auto;">
                  <span class="mint-datetime-cancel" @click='cancelWare'>取消</span>
                  <span class="mint-datetime-confirm" @click='cancelWare'>确定</span>
                </div>
                <mt-picker  :slots="slotsInventory"  valueKey="name" @change="onValuesChange"></mt-picker>
              </mt-popup>
            </div>
          </div>
          <div class="right-state">
            <div class="title">
              <p>备件类型</p>
            </div>
            <div class="fromText">
              <input class="input"  type="text"  v-model="warehouseNameType" @click="wardeviceList"  readonly="readonly">
            </div>
          </div>
          <div class="right-submit">
            <span @click="submitButton">查询</span>
          </div>
        </div>
      </transition>
    </div>
    <HomeManegerSideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle'
                         :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType"
                         @HomeSelectedType='getHomeSelectedType'>
    </HomeManegerSideOut>
  </div>
</template>

<script>
  import HomeManegerSideOut from '@/components/home/homeCom/HomeManegerSideOut'
  export default {
    name: 'HomeInventorySearch',
    data() {
      return {
        switchHomeOrdinarySideOut:false,
        HomeOrdinarySideOutTitle: "",
        HomeOrdinarySideOutData: [],
        pickerVisible: '',
        switchSearch: false,
        warehouseId:false,
        Inventory:false,
        warehouse:false,
        warehouseName:'',
        spareState:'',
        InventoryType:'',
        warehouseNameType:'全部',
        changeArray:[],
        slots:[
          {values: [{status:'all',name:'全部'},{status:'notUnique',name:'非唯一标识'},{status:'unique',name:'唯一标识'}]}
        ],
        slotsInventory:[
          {values: [{status:'all',name:'全部'},{status:'normal',name:'好件'},{status:'bad',name:'坏件'}]},
        ],
        slotWare:[
          {values: []},
        ],
        sendData:{
          InventoryType:'all',
          WarehouseId:'',
          sparePartTypeId:'',
          sparestatus:'all',
          partName:''
        },
      }
    },
    props: ['isSearch'],
    methods: {
      getHomeDeviceType(val) {
        this.switchHomeOrdinarySideOut = val;
      },
      cancelWare(){
        this.warehouse=false;
      },
      warehouseIds(){
        this.warehouseId=false;
      },
      Inventorys(){
        this.Inventory=false;
      },
      wardeviceList() {
        const _THIS = this;
        _THIS.HomeOrdinarySideOutTitle = "选择备件类型";
        this.requests.getSparePartTypeKongGeList().then(function(respont){
          if(respont.result=='success'){
            var respones=respont.data;
            if(respones.length>0){
              var object={
                categoryNameKongGe:'全部',
                parentPartId:'',
                sparePartTypeId:''
              }
              respones.unshift(object);
              _THIS.HomeOrdinarySideOutData=respones;
            }else{
              return;
            }
          }
        });
        this.switchHomeOrdinarySideOut = !this.switchHomeOrdinarySideOut;
      },
      onValuesWarehouseId(picker, values){
        if (values!=undefined) {
          picker.setSlotValue(1, values);
          if(values[0]!=undefined){
            this.sendData.WarehouseId = values[0].id;
            this.warehouseName=values[0].name;
          }
        }
      },
      getHomeSelectedType(val) {
        if (val.title == "选择备件类型") {
          val.text=val.text.replace(/---/g, "");
          this.warehouseNameType = val.text;
          this.sendData.sparePartTypeId = val.sideOutId;
        }
      },
      spareStateChange(picker,values){
        if (values!=undefined) {
          picker.setSlotValue(1, values);
          this.sendData.InventoryType = values[0].status;
          this.spareState=values[0].name;
        }
      },
      onValuesChange(picker, values) {
        if (values!=undefined) {
          picker.setSlotValue(1, values);
          this.sendData.sparestatus = values[0].status;
          this.InventoryType=values[0].name;
        }
      },

      leftSearch() {
        this.$emit('switchSearchCom', this.switchSearch);
      },
      // 提交表单
      submitButton() {
        let editDate=this.sendData;
        this.$emit('SearchCom', editDate);
        this.$emit('switchSearchCom', this.switchSearch);
      },
      getWarehousesByUsers(){
        const _this=this;
        this.requests.getWarehousesByUser().then(function(respont) {
          if(respont.result=='success'){
            var respones=respont.data;
            if(respones.length>0){
              for(let item=0;item<respones.length;item++){
                let data={};
                data.name=respones[item].text;
                data.id=respones[item].id;
                _this.slotWare[0].values.push(data);
              }
            }else{
              return;
            }
          }
        })
      },
    },
    mounted() {
     this.getWarehousesByUsers();
    },
    components: {
      HomeManegerSideOut
    },
    watch: {
      isSearch() {
        if (this.isSearch) {
          document.getElementById('HomeInventorySearch').style.display = 'block';
        } else {
          setTimeout(function() {
            document.getElementById('HomeInventorySearch').style.display = 'none';
          }, 300)
        }
      }
    }
  }
</script>

<style lang='scss' type="text/scss" scope>
  @import '../../../style/global.scss';
  #HomeInventorySearch {
    display: none;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
    background-color: rgba(127, 127, 127, .8);
    overflow: hidden;
    .picker-item {
      font-size: $widthWeb16;
    }
    .picker-toolbar {
      span {
        font-size: $widthWeb16;
      }
    }
  .content {
    width: 100%;
    height: 100%;
    position: relative;
    top: 0;
    left: 0;
  .left {
    position: absolute;
    top: 0;
    left: 0;
    width: 500px;
    height: 100%;
  }
  .slide-fade-enter-active {
    transition: transform .2s ease-in-out;
  }
  .slide-fade-leave-active {
    transition: transform .2s ease-in-out;
  }
  .slide-fade-enter,
  .slide-fade-leave-to {
    transform: translateX(100%);
  }
  .right {
    position: absolute;
    top: 0;
    right: 0;
    height: 100%;
    width: 400px;
    background-color: #eee;
    overflow-y:scroll;
  .title {
    width: 100%;
    height: 60px;
    line-height:60px;
  p {
    margin-left: 21px;
    font-size: $widthWeb14;
    color: #333;
  }
  }
  .right-inp {
  .fromText {
    width: 100%;
    height: 110px;
    text-align: center;
    line-height: 100px;
    background-color: #fff;
  input {
    width: 90%;
    height: 58px;
    font-size: $widthWeb18;
    line-height: 58px;
    text-align: center;
    border: solid 1px #bfbfbf;/*px*/
    border-radius: 18px;
  }
  }
  }
  .right-state {
    .fromText{
      width: 100%;
      height: 110px;
      text-align: center;
      line-height: 100px;
      background-color: #fff;
    }
    .input{
      width: 90%;
      height: 58px;
      font-size: $widthWeb18;
      line-height: 58px;
      text-align: center;
      border: solid 1px #bfbfbf;/*px*/
      border-radius: 18px;
    }
  .state {
    width: 100%;
    background-color: #fff;
  }
  }
  .right-submit {
    width: 90%;
    height: 80px;
    font-size: 0;
    margin:30px auto;
  span{
    width: 50%;
    height: 80px;
    font-size: $widthWeb25;
    display:inline-block;
    line-height:80px;
    text-align: center;
  &:nth-child(1) {
     color: #fff;
     box-sizing: border-box;
    background-color: #fd8a22;
   }
  &:nth-child(2) {
     color: #fff;
     background-color: #fd8a22;
   }
  }
  }
  ::-webkit-input-placeholder {
    color: #bfbfbf;
  }
  }
  }
  }
  .mint-datetime-action{
   line-height:30px;
  }
</style>
