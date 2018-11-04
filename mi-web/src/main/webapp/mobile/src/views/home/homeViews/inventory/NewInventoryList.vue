<template>
  <div id='NewInventoryList'>
    <section class="container_timeline" v-if="ArrayList.length">
      <mt-loadmore :bottomPullText='bottomText' :bottom-method="bottomMethod" :bottom-all-loaded="allLoaded"
                   ref="loadmore" :autoFill='noInitialize'>
        <div class="time-vertical">
          <section class="liSection" v-for="(item) in ArrayList">
            <b></b>
            <span class="titles">{{item.warehouseName}}</span>
            <div class="timeline_context">
              <div class="timeline-contains">
                <div class="time-block">
                    <span class="span_start">
                      <p class="time-title">开始时间</p>
                      <p class="time-c">{{item.beginTImeTxt}}</p>
                    </span>
                  <span class="span_start">
                              <p class="time-title">&nbsp;</p>
                              <p class="time-c time-icons"></p>
                        </span>
                  <span class="span_start">
                              <p class="time-title">结束时间</p>
                              <p class="time-c">{{item.endTImeTxt}}</p>
                        </span>
                </div>
                <div :class="className(item)" @click="itemClick(item)"></div>
              </div>
            </div>

          </section>
        </div>
      </mt-loadmore>
    </section>
    <!--<div class="globalFooter">-->
    <!--<button @click="$router.back(-1)">返回</button>-->
    <!--</div>-->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
    <div class="gloobalEmptyPrompt" v-show="!ArrayList.length">
      <div class="emptyPrompt" v-cloak>暂 无 数 据 !</div>
    </div>
    <div class="footer">
      <button @click="back">返回</button>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'Inventory',
    data() {
      return {
        bottomText: "上拉加载更多...",
        allLoaded: false,
        noInitialize: false, // 是否默认填充数据
        ArrayList: [],
        swtichLoading: true
      }
    },
    methods: {
      jumpLinks() {
        this.$router.push({path: '/NewInventoryInfo'})
      },
      bottomMethod() {
        let _this = this;
        _this.$refs.loadmore.onBottomLoaded();
        _this.getlistData();
      },
      getlistData() {
        const _this = this;
        let data = {
          userKey:this.common.getLocalStorage('userKey'),
          status:'all',
          start:this.ArrayList.length,
          limit:20,
          searchType:'createUserId'
        };
        this.requests.getInventoryTaskList(data).then(function (respont) {
          if (respont.result == 'success') {
            var array = respont.data.rows;
            for(let i=0;i<array.length;i++){
              _this.ArrayList.push(array[i]);
            }
            if(_this.ArrayList.length == respont.data.totalCount){
              _this.allLoaded = true;
            }
            _this.swtichLoading = false;
            // _this.$nextTick(function () {
            //   _this.$refs.loadmore.onBottomLoaded();
            // });
          }
        });
      },
      className(item) {
        return {
          "status1-block": item.status == 'before',
          "status2-block": item.status == 'ongoing',
          'status3-block': item.status == 'already'
        }
      },
      itemClick(item){
        if(item.status=='before'){
          this.$router.push({path: 'EditInventoryInfo',query: { taskId:item.inventoryTaskId}})
        }else{
          this.$router.push({path: 'InventoryDetaile',query: { taskId:item.inventoryTaskId,look:'look'}})
        }
        return;
      },
      back(){
        this.$router.push({
          path: '/NewInventoryInfo'
        })
      }
    },
    components: {/* 复用组件名称 */},
    mounted: function () {
      const _this = this;
      _this.getlistData();
      history.pushState(null, null, document.URL);
      window.addEventListener('popstate', this.back, false);
      this.$nextTick(function () {

      })
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.back);
    }
  }
</script>
<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";

  [v-cloak] {
    display: none
  }

  #NewInventoryList {
    overflow: auto;
    margin: auto;
    .gloobalLoading{
      height: 100%;
    }
    .btn-contains {
      text-align: right;
      width: 94%;
      padding: 15px 20px;
      margin: auto;
      .btn-orange {
        padding: 10px 20px;
        border-radius: 5px;
        display: inline-block;
        font-size: $widthWeb16;
        color: #fff;
        background-color: #fd8622;
      }
    }
    /*.mt-box{*/
    /*height: calc(100% - 90px);*/
    /*overflow: hidden;*/
    /*}*/
    .container_timeline {
      background: #fff;
      margin: 20px 20px;
      width: calc(100% - 40px);
      padding: 20px 20px 20px 0;
      height: calc(100% - 40px);
      box-sizing: border-box;
      overflow: auto;
      border: 1px solid #e4e0de; /*px*/
      border-radius: 10px;
      .time-vertical {
        margin-left: 20px;
        border-left: 2px solid #e1dddc; /*px*/
        .liSection {
          position: relative;
          margin-bottom: 15px;
          span.titles {
            display: block;
            margin-left: 20px;
            margin-bottom: 15px;
            color: #fb8521;
            font-size: $widthWeb22;
          }
          b:after {
            content: '';
            position: absolute;
            top: 0;
            left: -16px;
            width: 20px;
            height: 20px;
            border: 6px solid #faf6ec; /*px*/
            background: #fd8622;
            border-radius: 26px;
          }
          .timeline_context {
            background: #e5e3e4;
            margin-left: 20px;
            overflow: hidden;
            .timeline-contains {
              margin: 10px 0;
              background: #eeeeee;
              display: flex;
              justify-content: space-between;
              .time-block {
                padding: 10px;
                display: flex;
                align-items: center;
                .span_start {
                  .time-title {
                    font-size: $widthWeb12;
                    color: #bababa;
                  }
                  .time-c {
                    font-size: $widthWeb16;
                    white-space: nowrap;
                  }
                  .time-icons {
                    background: url('../../../../assets/home/a_r.png') no-repeat center;
                    background-size: contain;
                    width: 1rem;
                    height: 5px;
                  }
                  &:nth-child(2) .time-c {
                    padding: 0 0.1rem;
                  }
                }
              }
              .status1-block {
                width: 100%;
                background: url('../../../../assets/home/pd1.png') no-repeat right;
                background-size: contain;
              }
              .status2-block {
                width: 100%;
                background: url('../../../../assets/home/pd2.png') no-repeat right;
                background-size: contain;
              }
              .status3-block {
                width: 100%;
                background: url('../../../../assets/home/pd3.png') no-repeat right;
                background-size: contain;
              }
              .displayInlink {
                display: block;
                width: 100%;
                height: 100%;
              }
            }
          }
        }
      }
    }
    [v-cloak] {
      display: none;
    }
    .footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 80px;
      text-align: right;
      background-color: #eeeeee;
      z-index: 2;
      button {
        width: 220px;
        height: 80px;
        font-size: $widthWeb25;
        color: #fff;
        line-height: 80px;
        background-color: #fc6806;
      }
    }
  }
</style>
