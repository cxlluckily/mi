<template>
  <div id='Search'>
    <div class="content">
      <div class="left" @click="leftSearch"></div>
      <transition name="slide-fade">
        <div class="right" v-show="isSearch">
          <div class="right-inp">
            <div class="title">
              <p>故障单号</p>
            </div>
            <div class="fromText">
              <input type="text" placeholder="请收入故障单号" v-model="troubleNum">
            </div>
          </div>
          <div class="right-state">
            <div class="title">
              <p>状态</p>
            </div>
            <div class="state">
              <ul>
                <li>
                  <p class="isState">待处理</p>
                </li>
                <li>
                  <p>已报到</p>
                </li>
                <li>
                  <p>已完成</p>
                </li>
                <li>
                  <p>已维修</p>
                </li>
              </ul>
            </div>
          </div>
          <div class="right-timer">
            <div class="title">
              <p>上报时间</p>
            </div>
            <div class="fromTimer">
              <input @click="inp" unselectable="on" onfocus="this.blur()" readonly="readonly" type="text" placeholder="请输入起始时间">
              <div class="solid">
                <!--<span></span>-->
              </div>
              <input @click="inp" unselectable="on" onfocus="this.blur()" readonly="readonly" type="text" placeholder="请输入终止时间">
            </div>
          </div>
          <div class="right-submit">
            <span @click="resetIpt">重置</span>
            <span @click="submitButton">提交</span>
          </div>
          <!--日期选择器-->
      <mt-datetime-picker v-model="pickerVisible"  ref="picker" type="date" year-format="{value} 年" month-format="{value} 月" date-format="{value} 日" @confirm="handleConfirm">
      </mt-datetime-picker>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Search',
    data() {
      return {
       pickerVisible: '',
        switchSearch: false,
        troubleNum: '', // 故障单号
      }
    },
    props: ['isSearch'],
    methods: {
      handleConfirm(data) {
        console.log(data)
      },
      inp() {
        this.$refs.picker.open()
      },
      leftSearch() {
        this.$emit('switchSearchCom', this.switchSearch);
      },
      // 清空表单
      resetIpt() {
        this.troubleNum = '';
      },
      initData() {
        const USERKEY = localStorage.getItem('userKey');
        sessionStorage.setItem("isFirst","yes");
        const beginTime = new Date();
        const endTime = new Date();
        beginTime.setDate(beginTime.getDate() - 30);
        const _this = this;
        let data = {
          userKey: USERKEY,
          applyStatus: "all",
          applyType: "use",
          beginTime: this.formatDate(beginTime),
          documentNo: '',
          endTime: this.formatDate(endTime.getTime()),
          start: 0,
          limit: 20
        };
        this.requests.requisitionInfor(data).then(function (respont) {
          if (respont.result == 'success') {
            _this.tableDate = respont.data.rows;
          } else {
            return;
          }
        })
        this.$emit('switchSearchCom', this.switchSearch);
      },
      submitButton() {
        let editDate=this.dataArray;
        this.$emit('SearchCom', editDate);
        this.$emit('switchSearchCom', this.switchSearch);
      },
    },
    components: { /* 复用组件名称 */ },
    mounted: function() { /* 初始化数据 */ },
    watch: {
      isSearch: function() {
        if (this.isSearch) {
          document.getElementById('Search').style.display = 'block';
        } else {
          setTimeout(function() {
            document.getElementById('Search').style.display = 'none';
          }, 300)
        }
      }
    }
  }
</script>

<style lang='scss'>
  @import '../../../style/global.scss';
  #Search {
    display: none;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
    background-color: rgba(127, 127, 127, .8);
    overflow: hidden;
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
  .title {
    width: 100%;
    height: 60px;
    line-height:60px;
  p {
    margin-left: 21px;
    font-size: 22px;
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
    font-size: 18px;
    line-height: 58px;
    text-align: center;
    border: solid 1px #bfbfbf;/*px*/
    border-radius: 18px;
  }
  }
  }
  .right-state {
  .state {
    width: 100%;
    background-color: #fff;
  ul {
    padding: 26px 15px;
  li {
    width: 90%;
    height: 58px;
    box-sizing: border-box;
    text-align: center;
    line-height: 58px;
    margin:15px auto;
  p {
    width: 100%;
    height: 100%;
    font-size: 18px;
    color: #999;
    border: solid 1px #999999;/*px*/
    box-sizing: border-box;
    border-radius: 18px;
  }
  .isState {
    color: #fff;
    background-color: #fd8a22;
    border: solid 1px #fd8a22;/*px*/
  }
  }
  }
  }
  }
  .right-timer {
  .fromTimer {
    padding: 26px 30px;
    width: 100%;
    line-height: 58px;
    background-color: #fff;
    font-size: 0;
    box-sizing: border-box;
  input {
    display: inline-block;
    font-size:18px;
    border: solid 1px #bfbfbf;/*px*/
    box-sizing: border-box;
    text-align: center;
    border-radius: 10px;
    padding:15px;
  }
  .solid {
    display: inline-block;
    width: 10px;
    height: 100%;
    font-size: 18px;
    text-align: center;
    box-sizing: border-box;
  span {
    margin-bottom: 4px;
    display: inline-block;
    width: 29px;
    height: 3px;
    background-color: #999;
  }
  }
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
    font-size: 25px;
    display:inline-block;
    line-height:80px;
    text-align: center;
  &:nth-child(1) {
     color: #fff;
     box-sizing: border-box;
     background-color: #1a1a1a;
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
</style>
