<template>
  <div id="app">
    <div class="login">
      <div class="login-logo"></div>
      <div class="login-form">
        <form autocomplete="off">
          <div>
            <label for="user"
                   class="user-icon"></label>
            <input type="text"
                   name="user"
                   id="user"
                   placeholder="请输入手机号"
                   v-model="userModel">
          </div>
          <div>
            <label for="pwd"
                   class="pwd-icon"></label>
            <input :type="codeLogin?'text':'password'"
                   name="pwd"
                   id="pwd"
                   :placeholder="codeLogin?'请输入验证码':'请输入密码'"
                   v-model="pwdMpdel">
            <div class="getCode" v-show="codeLogin" @click="getCode">{{codeMessage}}</div>
          </div>
          <button type='button'
                  class="btn"
                  @click="goHome">登&nbsp;&nbsp;&nbsp;录
          </button>
          <div class="changeOther">
            <span @click="changeOther">{{codeLogin?'手机不在身边？ 密码登录':'忘记密码？ 验证码登录'}}</span>
            <img src="../../assets/index/arrow2.png"/>
          </div>
        </form>
      </div>
    </div>
    <div class="gloobalLoading" v-show="isWechatAutoLogin">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import Toast from 'mint-ui'
  export default {
    name: "App",
    data() {
      return {
        userModel: "",
        pwdMpdel: "",
        isWechatAutoLogin: false,
        codeLogin: false,
        hasGetCode: false,
        codeMessage: '获取验证码',
        docmHeight: document.documentElement.clientHeight, //默认屏幕高度
      };
    },
    methods: {
      goHome() {
        let _this = this;
        if(_this.codeLogin){
          if(this.userModel ==''){
            Toast.Toast({
              message:'请输入手机号'
            });
            return;
          }
          if(this.pwdMpdel ==''){
            Toast.Toast({
              message:'请输入验证码'
            });
            return;
          }
          let data = {
            phone:_this.userModel,
            code:_this.pwdMpdel
          };
          _this.api
            .h5phoneCodeLogin(data)
            .then(function (response) {
              let responseData = response.data;
              if (responseData.result == "success") {
                localStorage.setItem("userKey", responseData);
                // window.location.replace("home.html");
                window.location.href = "home.html";
              } else {
                Toast.Toast({
                  message:responseData.message
                });
              }
            });
        }else{
          if(this.userModel ==''){
            Toast.Toast({
              message:'请输入手机号'
            });
            return;
          }
          if(this.pwdMpdel ==''){
            Toast.Toast({
              message:'请输入密码'
            });
            return;
          }
          _this.api
            .resarchTreeDataAll(_this.userModel, _this.pwdMpdel)
            .then(function (data) {
              if (data.result == "success") {
                localStorage.setItem("userKey", data.data);
                window.location.href = "home.html";
              }
              else {
                Toast.Toast({
                  message:data.message
                });
              }
            });
        }
      },
      getLoginUrl(state) {
        this.api
          .getLoginUrl(state)
          .then(function (data) {
            window.location.href = data.data;
          });
      },

      wechatAutoLogin(data) {
        this.api
          .wechatAutoLogin(data)
          .then(function (responseData) {
            localStorage.setItem("userKey", responseData.data.userKey)
            window.location.href = responseData.data.url + '?openId=' + responseData.data.openId;
          });
      },

      getQueryString: function (queryStringName) {
        try {
          var returnValue = "";
          var URLString = new String(document.location);
          var serachLocation = -1;
          var queryStringLength = queryStringName.length;
          do {
            serachLocation = URLString.indexOf(queryStringName + "\=");
            if (serachLocation != -1) {
              if ((URLString.charAt(serachLocation - 1) == '?') || (URLString.charAt(serachLocation - 1) == '&')) {
                URLString = URLString.substr(serachLocation);
                break;
              }
              URLString = URLString.substr(serachLocation + queryStringLength + 1);
            }

          }
          while (serachLocation != -1)
          {
            if (serachLocation != -1) {
              var seperatorLocation = URLString.indexOf("&");
              if (seperatorLocation == -1) {
                returnValue = URLString.substr(queryStringLength + 1);
              }
              else {
                returnValue = URLString.substring(queryStringLength + 1, seperatorLocation);
              }
            }
          }
          return returnValue;
        }
        catch (e) {
          return "";
        }
      },
      getCode() {
        let _this = this;
        if(_this.hasGetCode){
          return;
        }
        let postData = {
          phone: this.userModel
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
      changeOther() {
        this.codeLogin = !this.codeLogin;
        // this.userModel = '';
        this.pwdMpdel = '';
      }
    },
    mounted: function () {
      if (this.isWechatAutoLogin == true) {
        try {
          let code = this.getQueryString('code');
          let state = this.getQueryString('state');
          if (code == '') {
            this.getLoginUrl(state);
          }
          else {
            let data = {
              code:code,
              state:state
            };
            this.wechatAutoLogin(data);
          }
        }
        catch (e) {

        }
      }
      $('#app').height(this.docmHeight)
    },
    components: {}
  };
</script>

<style lang='scss'>
  @import "../../style/global.scss";

  #app {
    position: relative;
    width: 100%;
    height: 100%;
    background: url("../../assets/index/login_bg.jpg") no-repeat;
    background-size: 100% 100%;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../assets/home/loading.gif");
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
        background: url("../../assets/index/logo.png") no-repeat;
        background-size: 100% 100%;
      }
      .login-form {
        width: 100%;
        form {
          width: 100%;
          position: relative;
          & > div {
            position: relative;
          }
          .user-icon {
            position: absolute;
            top: 50px;
            left: 47px;
            width: 31px;
            height: 40px;
            background: url("../../assets/index/login_user.png") no-repeat;
            background-size: 100% 100%;
          }
          .pwd-icon {
            position: absolute;
            /*top: 160px;*/
            top: 50px;
            left: 47px;
            width: 31px;
            height: 42px;
            background: url("../../assets/index/login_pwd.png") no-repeat;
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
            border-bottom: 1px solid #feb67a;
            box-sizing: border-box;
          }
          #pwd{
            padding-right: 200px;
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
          ::-webkit-input-placeholder {
            color: #fff;
            font-size: 24px;
          }
          .btn {
            display: block;
            margin-top: 90px;
            width: 526px;
            height: 74px;
            font-size: 40px;
            color: #fedcbd;
            background-color: transparent;
            border: solid 1px #fedcbd;
            border-radius: 37px;
          }
        }
      }
    }
  }
</style>



