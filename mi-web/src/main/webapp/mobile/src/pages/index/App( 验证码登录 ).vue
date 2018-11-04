<template>
  <div id="app">
    <div class="login">
      <div class="login-logo"></div>
      <div class="login-form">
        <form autocomplete="off">
          <div>
            <label for="user" class="user-icon"></label>
            <input type="text" name="user" id="user" placeholder="请输入手机号">
          </div>
          <div>
            <label for="pwd" class="pwd-icon"></label>
            <input type="text" name="pwd" id="pwd" placeholder="请输入验证码">
            <button type="button" class="verification"  @click="getVerification" :disabled = 'isDisabled'>{{switchText}}</button>
          </div>
          <button type='button' class="btn" @click="goHome">登&nbsp;&nbsp;&nbsp;录</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "App",
    data() {
      return {
        isDisabled: false,
        switchText: '获取验证码'
      };
    },
    methods: {
      goHome() {
        window.location.href = 'home.html'
      },
      getVerification() {
        let _this = this;
        let trmer;
        let seconds = 30;
        _this.isDisabled = true;
        clearInterval(trmer);
        trmer = setInterval(function() {
          seconds --;
          if(seconds === 0) {
            _this.switchText = '获取验证码';
            _this.isDisabled = false;
            clearInterval(trmer);
            return;
          }
          _this.switchText = `剩余${seconds}秒`;
        }, 1000) ;
      }
    },
    mounted: function() {},
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
    .login {
      position: absolute;
      top: 23%;
      left: 0;
      padding: 0 0.97rem;
      width: 100%;
      height: 0.3rem;
      box-sizing: border-box;
      .login-logo {
        margin-bottom: g-rem(91);
        width: 100%;
        height: g-rem(79);
        background: url("../../assets/index/logo.png") no-repeat;
        background-size: 100% 100%;
      }
      .login-form {
        width: 100%;
        form {
          width: 100%;
          position: relative;
          div {
            font-size: 0;
            .user-icon {
              position: absolute;
              top: g-rem(50);
              left: g-rem(47);
              width: g-rem(31);
              height: g-rem(40);
              background: url("../../assets/index/login_user.png") no-repeat;
              background-size: 100% 100%;
            }
            .pwd-icon {
              position: absolute;
              top: g-rem(160);
              left: g-rem(47);
              width: g-rem(31);
              height: g-rem(42);
              background: url("../../assets/index/login_pwd.png") no-repeat;
              background-size: 100% 100%;
            }
            #user {
              padding-left: g-rem(162);
              padding-top: g-rem(34);
              width: g-rem(526);
              height: g-rem(110);
              font-size: g-rem(24);
              color: #fff;
              background-color: transparent;
              border-bottom: 1px solid #feb67a;
              box-sizing: border-box;
            }
            #pwd {
              padding-left: g-rem(162);
              padding-top: g-rem(34);
              width: g-rem(367);
              height: g-rem(110);
              font-size: g-rem(24);
              color: #fff;
              background-color: transparent;
              border-bottom: 1px solid #feb67a;
              box-sizing: border-box;
            }
            .verification {
              float: right;
              margin-top: g-rem(66);
              padding: 0;
              font-size: g-rem(23);
              color: #fff;
              background-color: transparent;
              text-align: center;
            }
            ::-webkit-input-placeholder {
              color: #fff;
              font-size: g-rem(24);
            }
          }
          .btn {
            display: block;
            margin-top: g-rem(90);
            width: g-rem(526);
            height: g-rem(74);
            font-size: g-rem(40);
            color: #fedcbd;
            background-color: transparent;
            border: solid 1px #fedcbd;
            border-radius: g-rem(37);
          }
        }
      }
    }
  }
</style>
