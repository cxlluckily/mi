<template>
  <div id='HomeSiteList' v-show="switchCurrentCom">
    <div class="HomeSiteList-main">
      <div class="main-left" @click="closeSiteList" v-show="switchLienSite"></div>
      <transition name="slide-fade" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:after-leave="afterLeave">
        <div class="main-right clearfix" v-show='switchLienSite'>
          <div class="right-search">
            <label for="sea">
                <input type="text"
                       id="sea"
                       placeholder="请输入站点名"
                       v-model="searchModel">
                <i class="search-icon"></i>
            </label>
          </div>
          <div class="right-list" v-show="switchfocusList">
            <div class="list-line" ref='lineScroll'>
              <ul>
                <li v-for="(itemLine, index) in lineArr" :key="index" :data-lineId="itemLine.lineId" @click="switchItemList = index" :class="{'selectedLine': switchItemList == index}">
                  {{itemLine.lineName}}
                </li>
              </ul>
            </div>
            <div class="list-site" ref='siteScroll'>
              <ul v-for="(itemSite, index) in siteArr" :key="index" v-if="switchItemList == index">
                <li v-for="(itemSiteList, index) in itemSite" :key="index" :data-stationId="itemSiteList.stationId" @click="currentListDOM($event)">
                  {{itemSiteList.stationName}}
                </li>
              </ul>
            </div>
          </div>
          <div class="right-searchList" v-show="!switchfocusList">
            <ul>
              <li v-for="(itemSearch, index) in searchArr" :key="index" :data-lineId='itemSearch.lineId' :data-stationId='itemSearch.stationId' @click="currentListDOM($event)">
                <p>{{itemSearch.stationName}}</p>
                <p>{{itemSearch.lineName}}</p>
              </li>
            </ul>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import BScroll from "better-scroll";
export default {
  name: "HomeSiteList",
  data() {
    return {
      switchHomeSiteList: false, // 告诉父组件, 关闭当前组件
      switchCurrentCom: false, // 动画结束之后关闭页面
      lineArr: [], // 站点数据
      siteArr: [], // 线路数据
      searchArr: [], // 搜索数据
      switchItemList: "0", // 默认选中第一条线路
      currentTypeObj: {}, // 储存点击的值和ID
      switchfocusList: true, // 搜索和站点页面的切换
      searchModel: "" // 搜索 input
    };
  },
  props: ["switchLienSite"],
  methods: {
    //  点击站点获取站点值,ID, 并传给父组件和关闭此组件
    currentListDOM(event) {
      const THISDOM = $(event.currentTarget);
      this.currentTypeObj = {};
      this.currentTypeObj.text = THISDOM.find("p")
        .eq(0)
        .text();
      this.currentTypeObj.lineId = THISDOM.attr("data-lineId");
      this.currentTypeObj.stationId = THISDOM.attr("data-stationId");
      if (!this.currentTypeObj.text) {
        this.currentTypeObj.text = THISDOM.text().trim();
      }
      if (!this.currentTypeObj.lineId) {
        this.currentTypeObj.lineId = $(".selectedLine").attr("data-lineId");
      }
      this.currentTypeObj.stationId = THISDOM.attr("data-stationId");
      console.log(this.currentTypeObj);
      this.$emit("HomeCurrentType", this.currentTypeObj);
      this.$emit("switchHomeSiteList", this.switchHomeSiteList);
    },
    // 点击黑色区域关闭此组件
    closeSiteList() {
      this.$emit("switchHomeSiteList", this.switchHomeSiteList);
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
      this.switchItemList = "0";
    },
    // 初始化数据
    initData() {
      const _this = this;
      this.api.siteListDataAll("").then(function(data) {
        let dataArr = data.data;
        _this.lineArr = dataArr;
        for (let i = 0, LL = dataArr.length; i < LL; i++) {
          _this.siteArr.push(dataArr[i].stations);
        }
      });
    }
  },
  components: {
    /* 复用组件名称 */
  },
  mounted: function() {
    /* 初始化数据 */
    this.initData();
  },
  updated: function() {
    // 数据更新完后的生命钩子
    let lineScroll = this.$refs.lineScroll;
    let siteScroll = this.$refs.siteScroll;
    console.log(siteScroll)
    new BScroll(lineScroll, {
      click: true
    });
    new BScroll(siteScroll, {
      click: true
    });
  },
  watch: {
    //  监听 搜索 input 值的变化
    searchModel: function(val) {
      const _this = this;
      _this.switchfocusList = val == "" ? true : false;
      _this.api.siteListDataAll(val).then(function(data) {
        _this.searchArr = data.data;
      });
    }
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#HomeSiteList {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 100;
  overflow: hidden;
  .HomeSiteList-main {
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
        label {
          position: relative;
          input {
            // padding: 4px 48px 4px 38px;
            padding: 0 48px;
            width: 380px;
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
            top: 10px;
            right: 18px;
            width: 28px;
            height: 28px;
            background-image: url("../../../assets/home/icon_search2.png");
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
            font-size: .36rem;
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
            font-size: .36rem;
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
            font-size: .36rem;
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
