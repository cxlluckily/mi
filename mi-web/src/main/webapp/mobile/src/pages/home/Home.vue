<template>
  <div id='Home'>
    <!-- 切换的视图 -->
    <router-view class="routerView" :class="{'routerViewH100': !hidshow}"></router-view>
    <!-- 底部 Tab 栏 -->
    <footer class="tabBar" v-show="hidshow">
      <ul>
        <router-link tag="li" @click.native="selectedPath($event)" data-selected='homeSelected' :class="{'selectedOpacity' : selected == 'homeSelected'}" to="/HomeView">
          <i></i>
          <p>首页</p>
        </router-link>
        <!--MyTaskView-->
        <router-link tag="li" @click.native="selectedPath($event)" data-selected='myTaskSelected' :class="{'selectedOpacity' : selected == 'myTaskSelected'}" to="/MyTaskView">
          <i></i>
          <p>我的任务</p>
        </router-link>
        <router-link tag="li" @click.native="selectedPath($event)" data-selected='personalSelected' :class="{'selectedOpacity' : selected == 'personalSelected'}" to="/PersonalView">
          <i></i>
          <p>个人中心</p>
        </router-link>
      </ul>
    </footer>
    <!-- loading -->
    <div class="gloobalLoading" style="height: 100% !important;" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        selected: "homeSelected",
        docmHeight: document.documentElement.clientHeight, //默认屏幕高度
        showHeight: document.documentElement.clientHeight, //实时屏幕高度
        hidshow: true, //显示或者隐藏footer,
        swtichLoading: true,
      };
    },
    methods: {
      selectedPath(event) {
        const TITLE_TEXT = '数字运维' + ' - ' + $(event.currentTarget).find('p').text()
        $('title').text(TITLE_TEXT)
        }
      },
      components: {},
      mounted: function() {
        // let userKey = localStorage.getItem('userKey');
        // if(userKey==='')
        // {
        //   window.location.href = "index.html";
        // }
        /* 初始化数据 */
        this.wxApi.WxConfig();
        window.onresize = () => {
          return (() => {
            this.showHeight = document.body.clientHeight;
          })();
        };
      },
      watch: {
        /* 监听 */
        $route(to, from) {
          if (from.path == '/' && to.path == '/NewLibrarySelect') {
            this.$router.go(-1);
          } else {
            this.swtichLoading = false;
          }
          if (to.path == '/MyTaskView') {
            this.selected = 'myTaskSelected';
          } else if (to.path == '/PersonalView' || to.path == '/PersonalPassMannerge' || to.path == '/NoticeAnnouncement' || to.path == '/PersonalRessbook') {
            this.selected = 'personalSelected';
          } else {
            this.selected = 'homeSelected';
          }
        },
        showHeight: function(val) {
          if (this.docmHeight > val) {
            this.hidshow = false;
            $('.EMCompleteMaintenance-footer').hide()
            $('.EMCompleteMaintenance-main').addClass('mainHeight100');
          } else {
            this.hidshow = true;
            $('.EMCompleteMaintenance-footer').show()
            $('.EMCompleteMaintenance-main').removeClass('mainHeight100');
          }
        }
      }
    };
</script>

<style lang='scss'>
  @import "../../style/global.scss";
  #Home {
    width: 100%;
    height: 100%;
    background-color: #faf6ec;
    .loadingGif {
      background: url("../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .tabBar {
      position: fixed;
      left: 0;
      bottom: 0;
      width: 100%;
      background: url("../../assets/home/footer_bg.jpg") repeat-x;
      background-size: 100% 100%;
      ul {
        display: flex;
        li {
          flex: 1;
          text-align: center; // opacity: 0.5;
          opacity: 0.5;
          &:nth-child(1) {
            margin-top: 8px;
            i {
              display: inline-block;
              margin-bottom: 6px;
              width: 41px;
              height: 32px;
              background: url("../../assets/home/footer_home_s.png") no-repeat;
              background-size: 100% 100%;
            }
          }
          &:nth-child(2) {
            margin-top: 6px;
            i {
              display: inline-block;
              margin-bottom: 7px;
              height: 34px;
              width: 32px;
              background: url("../../assets/home/footer_task_s.png") no-repeat;
              background-size: 100% 100%;
            }
          }
          &:nth-child(3) {
            margin-top: 8px;
            i {
              display: inline-block;
              margin-bottom: 6px;
              width: 37px;
              height: 33px;
              background: url("../../assets/home/footer_center_s.png") no-repeat;
              background-size: 100% 100%;
            }
          }
          p {
            color: #fff;
            font-size: $widthWeb16;
            line-height: $widthWeb16;
          }
        }
        .selectedOpacity {
          opacity: 1;
        }
      }
    }
    .routerViewH100 {
      width: 100%;
      height: 100%;
    }
  }
</style>
