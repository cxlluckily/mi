<template>
  <div id='HomeCom' class="wrapper">
    <div class="HomeComBetterScroll">
      <div class="content" v-for="(itemPath, index) in routerViewPath" :key="index">
        <div class="title">
          <span class="colorPiece"></span>
          <p>{{itemPath.text}}</p>
        </div>
        <ul class="lists">
          <router-link v-for="(itemList,index) in itemPath.children" :key="index" @click.native="selectedPath($event)" tag="li" :to="'/' + itemList.xclass">
            <div>
              <div class="lists-img">
                <img :src=" itemList.iconCls" alt="图片">
              </div>
              <p>{{itemList.text}}</p>
            </div>
          </router-link>
        </ul>
      </div>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import BScroll from "better-scroll";
  export default {
    name: "HomeCom",
    data() {
      return {
        swtichLoading: true,
        routerViewPath: []
      };
    },
    methods: {
      /* 方法 */
      selectedPath(event) {
        const TITLE_TEXT = '数字运维' + ' - ' + $(event.currentTarget).find('p').text()
        $('title').text(TITLE_TEXT)
      }
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function() {
      /* 初始化数据 */
      this.api.getPhoneTreeDataAll().then(data => {
        this.routerViewPath = data.data;
        this.$nextTick(function() {
          this.swtichLoading = false;
        });
      });
      let wrapper = document.querySelector(".wrapper");
      new BScroll(wrapper, {
        click: true
      });
    },
    watch: {
      /* 监听 */
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #HomeCom {
    padding-bottom: 21px;
    box-sizing: border-box;
    overflow: hidden;
    .loadingGif {
      background: url("../../../assets/home/loading.gif");
      background-size: 100% 100%;
    }
    .HomeComBetterScroll {
      .content {
        .title {
          display: flex;
          align-items: center;
          width: 100%;
          height: 72px;
          color: #333;
          font-size: $widthWeb25;
          font-weight: 700;
          .colorPiece {
            display: inline-block;
            margin-right: 15px;
            width: 10px;
            height: 23px;
            background-color: #fd8521;
          }
        }
        .lists {
          display: flex;
          flex-wrap: wrap;
          background-color: #fff;
          border-top: 1px solid #ebeae6;
          border-left: 1px solid #ebeae6;
          box-sizing: border-box;
          li {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 20%;
            height: 150px;
            border-right: 1px solid #ebeae6;
            border-bottom: 1px solid #ebeae6;
            box-sizing: border-box;
            div {
              margin: 0 auto;
              .lists-img {
                display: flex;
                justify-content: center;
                align-items: center;
                width: 84px;
                height: 84px;
                background-color: #f2f1ee;
                border-radius: 50%;
                img {
                  width: 48px;
                  height: 48px;
                }
              }
              p {
                margin-top: 12px;
                font-size: $widthWeb18;
                line-height: $widthWeb18;
                color: #333;
                text-align: center;
              }
            }
          }
        }
      }
    }
  }
</style>
