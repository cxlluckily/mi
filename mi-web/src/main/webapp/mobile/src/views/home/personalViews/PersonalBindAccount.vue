<template>
  <div id="PersonalBindAccount">
    <div class="login">
      <div class="login-logo"></div>
      <div class="login-form">
        <form autocomplete="off">
          <div class="label-input">
            <label for="usertext"
                   class="user-icon"></label>
            <input type="text"
                   name="usertext"
                   id="usertext"
                   placeholder="请输入手机号"
                   autocomplete="off"
                   v-model="parmams.userName">
          </div>
          <div class="label-input">
            <label for="pwd"
                   class="pwd-icon"></label>
            <input :type="codeLogin?'text':'password'"
                   name="pwd"
                   id="pwd"
                   :placeholder="codeLogin?'请输入验证码':'请输入密码'"
                   autocomplete="off"
                   v-model="parmams.password">
            <div class="getCode" v-show="codeLogin" @click="getCode">{{codeMessage}}</div>
          </div>
          <button type='button'
                  class="btn"
                  @click="goHome">绑&nbsp;&nbsp;&nbsp;定
          </button>

          <div class="changeOther">
            <span @click="changeOther">{{codeLogin?'手机不在身边？ 密码登录':'忘记密码？ 验证码登录'}}</span>
            <img src="../../../assets/index/arrow2.png"/>
          </div>
        </form>
      </div>
    </div>
    <div class="gloobalLoading" v-if="showLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>
<script>
  import Toast from 'mint-ui'
  export default {
    name: 'PersonalBindAccount',
    data() {
      return {
        parmams:{
          userName: '',
          password: '',
          openId:''
        },
        codeLogin: false,
        hasGetCode: false,
        codeMessage: '获取验证码',
        docmHeight: document.documentElement.clientHeight, //默认屏幕高度
        showLoading:false
      }
    },
    methods: {
      goHome(){
        if(this.codeLogin){
          this.bindWechatOpenIdByPhone();
        }else{
          this.bindWechatOpenId();
        }
      },
      bindWechatOpenId(){
        const _this=this;
        if(this.parmams.userName ==''){
          Toast.Toast({
            message:"请输入手机号",
            className:'fontClass'
          });
          return;
        }
        if(this.parmams.password ==''){
          Toast.Toast({
            message:"密码不为空",
            className:'fontClass'
          });
          return;
        }
        let data=this.parmams;
        let openId=this.$route.query.openId;
        data.openId=openId;
        // this.showLoading=true;
        this.requests.bindWechatOpenId(data).then(function(response){
          if(response.result=='success'){
            Toast.Toast({
              message:"账号绑定成功",
              className:'fontClass',
              duration: 500
            });
            setTimeout(function(){
              window.location.href='index.html';
            },500);
          }else{
            Toast.Toast({
              message:response.message,
              className:'fontClass'
            });
          }
          // _this.showLoading=false;
        })
      },
      bindWechatOpenIdByPhone(){
        const _this=this;
        if(this.parmams.userName ==''){
          Toast.Toast({
            message:"请输入手机号",
            className:'fontClass'
          });
          return;
        }
        if(this.parmams.password ==''){
          Toast.Toast({
            message:"请输入验证码",
            className:'fontClass'
          });
          return;
        }
        let data = {
          phone:_this.parmams.userName,
          code:_this.parmams.password,
          openId:_this.$route.query.openId,
        };
        // this.showLoading=true;
        this.api.bindWechatOpenIdByPhone(data).then(function(response){
          let responseData = response.data
          if(responseData.result=='success'){
            Toast.Toast({
              message:"账号绑定成功",
              className:'fontClass',
              duration: 500
            });
            setTimeout(function(){
              window.location.href='index.html';
            },500);
          }else{
            Toast.Toast({
              message:responseData.message,
              className:'fontClass'
            });
          }
          // _this.showLoading=false;
        })
      },
      getCode() {
        let _this = this;
        if(_this.hasGetCode){
          return;
        }
        let postData = {
          phone: _this.parmams.userName
        };
        _this.codeMessage = '正在获取验证码...';
        _this.hasGetCode = true;
        this.api
          .getCode(postData)
          .then(function (data) {
            let response = data.data;
            if (response.result == 'success') {
              let time = 60;
              _this.codeMessage = time + '秒后重新获取';
              let timeOut = setInterval(function () {
                time--;
                if (time == 0) {
                  clearInterval(timeOut);
                  _this.hasGetCode = false;
                  _this.codeMessage = '获取验证码';
                } else {
                  _this.codeMessage = time + '秒后重新获取';
                }
              }, 1000);
            } else {
              _this.hasGetCode = false;
              _this.codeMessage = '获取验证码';
              Toast.Toast({
                message: response.message,
                duration: 1000
              });
            }
          });
      },
      changeOther(){
        this.codeLogin = !this.codeLogin;
        // this.parmams.userName = '';
        this.parmams.password = '';
      }
    },
    mounted(){
      $('#PersonalBindAccount').height(this.docmHeight);
    }
  }
</script>
<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";
  .fontClass{
    span {
      font-size:28px;
    }
  }
  #PersonalBindAccount {
    width: 100%;
    height: 100%;
    background: url("../../../assets/index/login_bg.jpg") no-repeat;
    background-size: 100% 100%;
    z-index:10;
    position:relative;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .login {
      position: absolute;
      top: 23%;
      left: 0;
      height: 77%;
      padding: 0 97px;
      width: 100%;
      box-sizing: border-box;
      .login-logo {
        margin-bottom: 91px;
        width: 100%;
        height: 79px;
        background: url("../../../assets/index/logo.png") no-repeat;
        background-size: 100% 100%;
      }
      .login-form {
        width: 100%;
        form {
          width: 100%;
          .label-input{
            position: relative;
            .position{
              position: absolute;
              top: 50px;
              left: 47px;
              width: 31px;
              height: 40px;
            }
            .user-icon {
             @extend .position;
              background: url("../../../assets/index/login_user.png") no-repeat;
              background-size: 100% 100%;
            }
            .pwd-icon {
              @extend .position;
              background: url("../../../assets/index/login_pwd.png") no-repeat;
              background-size: 100% 100%;
            }
            input {
              padding-left: 162px;
              padding-top: 34px;
              width: 526px;
              height: 110px;
              font-size: 24px;
              color: #fff;
              background-color: transparent;
              border-bottom: 2px solid #feb67a;/*px*/
              box-sizing: border-box;
            }
            #pwd{
              padding-right: 200px;
            }
            ::-webkit-input-placeholder {
              color: #fff;
              font-size: 24px;
            }
            .getCode {
              position: absolute;
              top: 0px;
              right: 15px;
              background: rgba(255, 255, 255, .5);
              margin-top: 49px;
              border-radius: 5px;
              padding: 0 20px;
              height: 46px;
              font-size: 20px;
              line-height: 46px;
              color: #ea5805;
            }
          }

          .btn {
            display: block;
            margin-top: 90px;
            width: 526px;
            height: 74px;
            font-size: 40px;
            color: #fedcbd;
            background-color: transparent;
            border: 2px solid #feb67a;/*px*/
            border-radius: 37px;
          }

          .changeOther {
            text-align: right;
            color: #fff;
            font-size: 24px;
            margin-top: 60px;
            vertical-align: middle;
            span {

            }
            img {
              vertical-align: middle;
            }
          }
        }
      }
    }
  }
</style>
