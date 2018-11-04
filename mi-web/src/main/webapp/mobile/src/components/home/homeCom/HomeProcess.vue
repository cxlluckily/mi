<template>
  <div id='HomeProcess'>
    <div class="HomeProcess-main">
      <div class="main-title">
        <div class="title-icon">
          <i></i>
          <p>进度情况</p>
        </div>
        <p class="title-right" v-show="whetherRepaired == 'Repaired'">故障维修流程结束!</p>
      </div>
      <div class="main-process">
        <ul class="process-list">
          <li v-for="(processItem, index) in processData" :key="index">
            <div class="list-timer">
              <p>{{processItem.createTime | initDate('ymd')}}</p>
              <p>{{processItem.createTime | initDate('hms')}}</p>
            </div>
            <div class="list-icon">
              <i></i>
            </div>
            <div class="list-name">
              <p>
                <span v-if="'Reported' == processItem.status">已上报</span>
                <span v-else-if="'Dispatched' ==  processItem.status">已派单</span>
                <span v-else-if="'Repairing' ==  processItem.status">已接修</span>
                <span v-else-if="'Failed' ==  processItem.status">维修失败</span>
                <span v-else-if="'Repaired' ==  processItem.status">已修复</span>
                <span v-else-if="'Rated' ==  processItem.status">已评价</span>
                <span>( 由</span>
                <span class="name-initiator">&nbsp;{{processItem.initiator}}&nbsp;</span>
                <span v-if="'Reported' ==  processItem.status">上报</span>
                <span v-else-if="'Dispatched' ==  processItem.status">派单</span>
                <span v-else-if="'Repairing' ==  processItem.status">接修</span>
                <span v-else-if="'Failed' ==  processItem.status">维修失败</span>
                <span v-else-if="'Repaired' ==  processItem.status">修复</span>
                <span v-else-if="'Rated' ==  processItem.status">评价</span>
                <span class="name-initiator" v-if="processItem.targetPerson">
                  <span>给</span> {{processItem.targetPerson}}</span>
                <span>&nbsp;)</span>
              </p>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HomeProcess",
    data() {
      return {
        whetherRepaired: null
      };
    },
    props: ["processData"],
    methods: {
      /* 方法 */
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function() {
      /* 初始化数据 */
    },
    updated: function() {
      if (this.processData.length != 0) {
        this.whetherRepaired = this.processData[0].status;
      }
    },
    watch: {
      /* 监听 */
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #HomeProcess {
    width: 100%;
    height: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    .HomeProcess-main {
      width: 100%;
      height: 100%;
      padding: 8px 30px 25px;
      border: solid 1px #dfdcdc;
      box-sizing: border-box;
      border-radius: 15px;
      background-color: #fff;
      .main-title {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 18px;
        width: 100%;
        height: 74px;
        border-bottom: 1px solid #dfdcdc;
        .title-icon {
          display: flex;
          align-items: center;
          font-size: 0;
          i {
            display: inline-block;
            margin-right: 13px;
            width: 30px;
            height: 29px;
            background: url("../../../assets/home/process.png") no-repeat;
            background-size: 100% 100%;
          }
        }
        p {
          color: #333;
          font-size: .44rem;
          line-height: .44rem;
          font-weight: 700;
        }
        .title-right {
          color: #fd8521;
        }
      }
      .main-process {
        width: 100%;
        padding-left: 64px;
        box-sizing: border-box;
        .process-list {
          li {
            display: flex;
            align-items: center;
            margin-bottom: 28px;
            &:nth-last-child(1) .list-icon::before {
              content: " ";
              height: 0;
              border: 0;
            }
            &:nth-child(1) .list-icon {
              padding: 0;
              width: 27px;
              height: 22px;
              i {
                width: 27px;
                height: 22px;
                background: url("../../../assets/home/state_current.png") no-repeat;
                background-size: 100% 100%;
              }
            }
            .list-timer {
              flex-shrink: 0;
              text-align: center;
              p {
                &:nth-child(1) {
                  color: #333;
                  font-size: .36rem;
                  line-height: .36rem;
                  margin-bottom: 10px;
                }
                &:nth-child(2) {
                  color: #999;
                  font-size: .28rem;
                  line-height: .28rem;
                }
              }
            }
            .list-icon {
              position: relative;
              padding-left: 4px;
              margin: 0 22px 0 39px;
              width: 27px;
              height: 22px;
              line-height: 22px;
              box-sizing: border-box;
              &::before {
                content: " ";
                position: absolute;
                top: 32px;
                left: 10px;
                height: 35px;
                border-left: 2px solid #dfdcdc;
              }
              i {
                display: inline-block;
                width: 15px;
                height: 15px;
                background: url("../../../assets/home/state.png") no-repeat;
                background-size: 100% 100%;
              }
            }
            .list-name {
              display: flex;
              flex-wrap: wrap;
              span {
                color: #333;
                font-size: .36rem;
                line-height: .36rem;
              }
              .name-initiator {
                color: #fd8521;
                span {
                  color: #333;
                }
              }
            }
          }
        }
      }
    }
  }
</style>
