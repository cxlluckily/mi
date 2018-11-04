<template>
  <div id='HomeShelvesList' v-show="switchCurrentCom">
    <div class="HomeShelvesList-main">
      <div class="main-left" @click="closeSiteList" v-show="switchLienSite"></div>
      <transition name="slide-fade" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:after-leave="afterLeave">
        <div class="main-right clearfix" v-show='switchLienSite'>
          <div class="right-search">
            <label for="sea" @click="houseNoList">
                <input type="text"
                       id="sea"
                       placeholder="请选择房间号"
                       v-model="houseNo" readonly="readonly">
                <i class="search-icon"></i>
            </label>
            <label class="reset" @click="reset">清 空</label>
          </div>
          <div class="right-list" v-show="switchfocusList">
            <div class="list-line" ref='lineScroll'>
              <ul>
                <li v-show="ShelfList.length>0" @click="switchItemList = 'Shelf'" :class="{'selectedLine': switchItemList == 'Shelf'}">货架</li>
                <li v-show="ContainerList.length>0" @click="switchItemList = 'Container'" :class="{'selectedLine': switchItemList == 'Container'}">货柜</li>
                <!--<li v-for="(itemLine, index) in lineArr" :key="index" :data-lineId="itemLine.lineId" @click="switchItemList = index" :class="{'selectedLine': switchItemList == index}">-->
                  <!--{{itemLine.lineName}}-->
              </ul>
            </div>
            <div class="list-site" ref='siteScroll'>
              <ul v-show="switchItemList == 'Shelf'">
                <li v-for="(itemSiteList, index) in ShelfList" :key="index" :data-stationId="itemSiteList.goodsShelvesId" @click="currentListDOM($event,itemSiteList)">
                  {{itemSiteList.shelfNumber}}
                </li>
              </ul>
              <ul v-show="switchItemList == 'Container'">
                <li v-for="(itemSiteList, index) in ContainerList" :key="index" :data-stationId="itemSiteList.goodsShelvesId" @click="currentListDOM($event,itemSiteList)">
                  {{itemSiteList.shelfNumber}}
                </li>
              </ul>
            </div>
          </div>
          <!--<div class="right-searchList" v-show="!switchfocusList">-->
            <!--<ul>-->
              <!--<li v-for="(itemSearch, index) in searchArr" :key="index" :data-lineId='itemSearch.lineId' :data-stationId='itemSearch.stationId' @click="currentListDOM($event)">-->
                <!--<p>{{itemSearch.stationName}}</p>-->
                <!--<p>{{itemSearch.lineName}}</p>-->
              <!--</li>-->
            <!--</ul>-->
          <!--</div>-->
        </div>
      </transition>
    </div>
    <HomeOrdinarySideOut :switchHomeOrdinarySideOut="switchHomeOrdinarySideOut"
                         :ordinarySideOutTitle='HomeOrdinarySideOutTitle'
                         :ordinarySideOutData="HomeOrdinarySideOutData"
                         @switchHomeOrdinarySideOut="getHomeDeviceType"
                         @HomeSelectedType='getHomeSelectedType'>
    </HomeOrdinarySideOut>
  </div>
</template>

<script>
import BScroll from "better-scroll";
import HomeOrdinarySideOut from '@/components/home/homeCom/HomeOrdinarySideOut';
export default {
  name: "HomeShelvesList",
  data() {
    return {
      switchHomeOrdinarySideOut: false,//右侧弹出层显示隐藏
      HomeOrdinarySideOutTitle: '房间号',//右侧弹出层标题
      HomeOrdinarySideOutData: [],//右侧弹出层数据

      switchHomeShelvesList: false, // 告诉父组件, 关闭当前组件
      switchCurrentCom: false, // 动画结束之后关闭页面
      // switchWarehouseId:'',
      houseNo:'',//房间号
      getShelvesIng:false,//货架数据正在加载
      ShelfList:[],//货架数据
      ContainerList:[],//货柜数据
      lineArr: [], // 站点数据
      siteArr: [], // 线路数据
      searchArr: [], // 搜索数据
      switchItemList: "Shelf", // 默认选中第一条线路
      currentTypeObj: {}, // 储存点击的值和ID
      switchfocusList: true, // 搜索和站点页面的切换
      searchModel: "" // 搜索 input
    };
  },
  props: ["switchLienSite","switchWarehouseId"],
  methods: {
    // 选择房间号
    houseNoList(){
      this.switchHomeOrdinarySideOut = true;
    },
    getHomeDeviceType(val) {
      this.switchHomeOrdinarySideOut = val;
    },
    reset(){
      this.currentTypeObj = {
        houseNoAndShelfNumber: '',
        goodsShelvesId: ''
      };
      this.$emit("HomeCurrentType", this.currentTypeObj);
      this.$emit("switchHomeShelvesList", this.switchHomeShelvesList);
    },
    getHomeSelectedType(val) {
      let $this = this;
      if(!$this.getShelvesIng){
        $this.getShelvesIng = true;
        $this.houseNo = val.text;
        let data = {
          warehouseId:$this.switchWarehouseId,
          houseNo:$this.houseNo,
          userKey:$this.common.getLocalStorage('userKey')
        };
        this.requests.getShelvesMap(data).then(function(data) {
          let DATA = data.data;
          $this.getShelvesIng = false;
          $this.ShelfList = DATA.Shelf;
          $this.ContainerList = DATA.Container;
          $this.switchItemList = 'Shelf';
          return;
          $this.HomeOrdinarySideOutData = [];
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].houseNo,
              sideOutText: DATA[i].houseNo
            });
          }
          return;
          $this.lineArr = dataArr;
          for (let i = 0, LL = dataArr.length; i < LL; i++) {
            $this.siteArr.push(dataArr[i].stations);
          }
        });
      }


    },

    //  点击站点获取站点值,ID, 并传给父组件和关闭此组件
    currentListDOM(event,item) {
      const THISDOM = $(event.currentTarget);
      this.currentTypeObj = {
        houseNoAndShelfNumber: this.houseNo + ' ' + item.shelfNumber,
        goodsShelvesId: item.goodsShelvesId
      };
      this.$emit("HomeCurrentType", this.currentTypeObj);
      this.$emit("switchHomeShelvesList", this.switchHomeShelvesList);
    },
    // 点击黑色区域关闭此组件
    closeSiteList() {
      this.$emit("switchHomeShelvesList", this.switchHomeShelvesList);
    },
    // 过渡动画( 开始 )
    beforeEnter(el) {
      this.switchCurrentCom = true;
    },
    // 过渡动画( 过渡 )
    enter(el, done) {
      el.offsetWidth;
      el.style.transition = "transform 0.2s ease-in-out";
      done();
    },
    // 过渡动画( 结束 )
    afterLeave: function(el) {
      this.switchCurrentCom = false;
      this.searchModel = "";
      this.switchItemList = "Shelf";
    },
    // 初始化数据
    initData() {
      const $this = this;
      let data = {
        warehouseId:$this.switchWarehouseId,
        userKey:$this.common.getLocalStorage('userKey')
      };
      this.requests.getShelvesHouseList(data).then(function(data) {
        let DATA = data.data;
        $this.HomeOrdinarySideOutData = [];
        // $this.HomeOrdinarySideOutData.push({
        //   sideOutId: '清空',
        //   sideOutText: '清空'
        // });
        if(DATA){
          for (let i = 0, II = DATA.length; i < II; i++) {
            $this.HomeOrdinarySideOutData.push({
              sideOutId: DATA[i].houseNo,
              sideOutText: DATA[i].houseNo
            });
          }
        }
      });
    }
  },
  components: {
    /* 复用组件名称 */
    HomeOrdinarySideOut
  },
  mounted: function() {
    /* 初始化数据 */
    this.initData();
  },
  updated: function() {
    // 数据更新完后的生命钩子
    let lineScroll = this.$refs.lineScroll;
    let siteScroll = this.$refs.siteScroll;
    new BScroll(lineScroll, {
      click: true
    });
    new BScroll(siteScroll, {
      click: true
    });
  },
  watch: {
    //  监听 搜索 input 值的变化
    switchLienSite:function (val) {
      if(!val){
        this.houseNo = '';
        this.ShelfList = [];
        this.ContainerList = [];
        this.switchItemList = "Shelf"
      }
    },
    switchWarehouseId:function (val) {
      if(!val){
        return;
      }
      this.initData();
    }
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#HomeShelvesList {
  position: fixed;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 100;
  overflow: hidden;
  .HomeShelvesList-main {
    width: 100%;
    height: 100%;
    .main-left {
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
    }
    .slide-fade-enter,
    .slide-fade-leave-to {
      transform: translateX(100%);
    }
    .main-right {
      position: absolute;
      top: 0;
      right: 0;
      // width: 386px;
      width: 480px;
      height: 100%;
      background-color: #fff;
      .right-search {
        width: 100%;
        height: 70px;
        background-color: #e2e2e2;
        display: flex;
        justify-content: center;
        align-items: center;
        .reset{
          margin-left: 20px;
          width: 100px;
          font-size: .32rem;
          color: #fff;
          background: #fc6806;
          text-align: center;
          line-height: 42px;
          border: solid 1px #bfbfbf;
          box-sizing: border-box;
          border-radius: 18px;
        }
        label {
          position: relative;
          input {
            padding: 0 48px 0 38px;
            // width: 230px;
            width: 320px;
            font-size: .32rem;
            color: #333;
            line-height: 48px;
            text-align: center;
            border: solid 1px #bfbfbf;
            box-sizing: border-box;
            border-radius: 18px;
          }
          .search-icon {
            z-index: 100;
            position: absolute;
            top: 18px;
            right: 18px;
            width: 28px;
            height: 14px;
            /*background: url("../../../assets/home/icon_search2.png");*/
            background: url("../../../assets/home/arrow_down.png");
            background-size: 100% 100%;
          }
        }
      }
      .right-list {
        display: flex;
        width: 100%;
        height: calc(100% - 69px);
        background-color: #fff;
        .list-line {
          // width: 156px;
           width: 200px;
          height: 100%;
          border-right: 1px solid #dfdcdc;
          box-sizing: border-box;
          overflow: hidden;
          li {
            width: 100%;
            height: 62px;
            font-size: $widthWeb18;
            color: #333;
            text-align: center;
            line-height: 62px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
          .selectedLine {
            background-color: #eee;
          }
        }
        .list-site {
          // width: 230px;
          width: 280px;
          height: 100%;
          overflow: hidden;
          li {
            width: 100%;
            height: 62px;
            font-size: $widthWeb18;
            color: #999;
            text-align: center;
            line-height: 62px;
            border-bottom: 1px solid#dfdcdc;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            box-sizing: border-box;
          }
        }
      }
      .right-searchList {
        width: 100%;
        height: calc(100% - 69px);
        li {
          display: flex;
          justify-content: space-between;
          padding: 0 20px;
          width: 100%;
          text-align: center;
          box-sizing: border-box;
          border-bottom: 1px solid#dfdcdc;
          p {
            width: 50%;
            height: 62px;
            color: #333;
            font-size: $widthWeb18;
            line-height: 62px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            &:nth-last-child(1) {
              color: #999;
            }
          }
        }
      }
    }
  }
}
</style>
