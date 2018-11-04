<template>
  <div id="PersonalPassMannerge">
    <div class="login">
      <div class="login-logo"></div>
      <div class="login-form">
        <form autocomplete="off">
          <div class="label-input" v-show="!codeLogin">
            <label for="userpass"
                   class="pwd-icon"></label>
            <input type="password"
                   name="userpass"
                   id="userpass"
                   placeholder="请输入旧密码"
                   autocomplete="off"
                   v-model="parmams.oldPassword" @change="change(parmams.oldPassword)">
          </div>
          <!--<div class="label-input" v-show="codeLogin">-->
            <!--<label for="usertext"-->
                   <!--class="user-icon"></label>-->
            <!--<input type="text"-->
                   <!--name="usertext"-->
                   <!--id="usertext"-->
                   <!--placeholder="请输入手机号"-->
                   <!--autocomplete="off"-->
                   <!--v-model="parmams.phone">-->
          <!--</div>-->
          <div class="label-input" v-show="codeLogin">
            <label for="code"
                   class="pwd-icon"></label>
            <input type="text"
                   name="pwd"
                   id="code"
                   placeholder="请输入验证码"
                   autocomplete="off"
                   v-model="parmams.code">
            <div class="getCode" @click="getCode">{{codeMessage}}</div>
          </div>
          <div class="label-input">
            <label for="pwd"
                   class="pwd-icon"></label>
            <input type="password"
                   name="pwd"
                   id="pwd"
                   placeholder="请输入新密码"
                   autocomplete="off"
                   v-model="parmams.newPassword" @change="change(parmams.newPassword)">
          </div>
          <div class="label-input">
            <label for="pwd2"
                   class="pwd-icon2"></label>
            <input type="password"
                   name="pwd"
                   id="pwd2"
                   placeholder="请输入确认密码"
                   autocomplete="off"
                   v-model="parmams.password" @change="change(parmams.password)">
          </div>
          <button type='button'
                  class="btn"
                  @click="changeConfirm">保&nbsp;&nbsp;&nbsp;存
          </button>
          <div class="changeOther">
            <span @click="changeOther">{{codeLogin?'手机不在身边？ 旧密码验证':'忘记密码？ 验证码验证'}}</span>
            <img src="../../../assets/index/arrow2.png"/>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import Toast from 'mint-ui'

  export default {
    name: 'PersonalPassMannerge',
    data() {
      return {
        parmams: {
          phone: this.common.getLocalStorage('phone'),
          code: '',
          newPassword: '',
          oldPassword: '',
          password: '',
          userKey: localStorage.getItem('userKey'),
        },
        codeLogin: false,
        hasGetCode: false,
        codeMessage: '获取验证码',
        docmHeight: document.documentElement.clientHeight, //默认屏幕高度
      }
    },
    methods: {
      /* 方法 */
      /*密码管理*/
      change(val) {
        if (val.length < 6) {
          Toast.Toast({
            message: "密码长度不少于6位",
            className: 'fontClass',
          });
          return;
        }
        const regex = /^[0-9a-zA-Z]*$/g;
        if (!regex.test(val)) {
          Toast.Toast({
            message: "你输入的密码不是数字或者字母",
            className: 'fontClass',
          });
          return;
        }
      },
      changeConfirm() {
        if (this.codeLogin) {
          this.modifyPasswordByCode();
        } else {
          this.modifyPassword();
        }
      },
      modifyPassword() {
        const _this = this;
        if (this.parmams.oldPassword == '') {
          Toast.Toast({
            message: "请输入旧密码",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.newPassword == '') {
          Toast.Toast({
            message: "请输入新密码",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.password == '') {
          Toast.Toast({
            message: "请输入确认密码",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.oldPassword == this.parmams.newPassword) {
          Toast.Toast({
            message: "旧密码和新密码相同",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.newPassword != this.parmams.password) {
          Toast.Toast({
            message: "新密码和确认密码不一致",
            className: 'fontClass',
          });
          return;
        }
        const regex = /^[0-9a-zA-Z]*$/;
        if (!regex.test(this.parmams.oldPassword)) {
          Toast.Toast({
            message: "你输入的旧密码不是数字或者字母",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.newPassword.length < 6) {
          Toast.Toast({
            message: "新密码长度不少于6位",
            className: 'fontClass',
          });
          return;
        }
        if (!regex.test(this.parmams.newPassword)) {
          Toast.Toast({
            message: "你输入的新密码不是数字或者字母",
            className: 'fontClass',
          });
          return;
        }
        let data = this.parmams;
        this.requests.modifyPassword(data).then(function (response) {
          if (response.result == 'success') {
            Toast.Toast({
              message: "密码修改成功",
              className: 'fontClass',
            });
            window.location.href = "index.html";
          } else {
            Toast.Toast(response.message)
          }
        })
      },
      modifyPasswordByCode() {
        const _this = this;
        if (this.parmams.phone == '') {
          Toast.Toast({
            message: "请输入手机号",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.newPassword == '') {
          Toast.Toast({
            message: "请输入新密码",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.password == '') {
          Toast.Toast({
            message: "请输入确认密码",
            className: 'fontClass',
          });
          return;
        }
        if (this.parmams.newPassword != this.parmams.password) {
          Toast.Toast({
            message: "新密码和确认密码不一致",
            className: 'fontClass',
          });
          return;
        }

        if (this.parmams.newPassword.length < 6) {
          Toast.Toast({
            message: "密码长度不少于6位",
            className: 'fontClass',
          });
          return;
        }
        const regex = /^[0-9a-zA-Z]*$/;
        if (!regex.test(this.parmams.newPassword)) {
          Toast.Toast({
            message: "你输入的密码不是数字或者字母",
            className: 'fontClass',
          });
          return;
        }
        let data = {
          phone: this.parmams.phone,
          code: this.parmams.code,
          newPassword: this.parmams.newPassword,
          userKey: this.parmams.userKey,
        };
        this.requests.modifyPasswordbyphone(data).then(function (response) {
          if (response.result == 'success') {
            Toast.Toast({
              message: "密码修改成功",
              className: 'fontClass',
            });
            window.location.href = "index.html";
          } else {
            Toast.Toast(response.message)
          }
        })
      },
      changeOther() {
        this.codeLogin = !this.codeLogin;
        this.parmams.code='';
        this.parmams.newPassword='';
        this.parmams.oldPassword='';
        this.parmams.password='';
        this.parmams.userKey=localStorage.getItem('userKey');
      },
      getCode() {
        let _this = this;
        if (_this.hasGetCode) {
          return;
        }
        let postData = {
          phone: _this.parmams.phone,
          userKey: _this.parmams.userKey
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
    },
    components: {},
    mounted: function () {
      $('#PersonalPassMannerge').height(this.docmHeight)
    },
  }
</script>

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";

  .fontClass {
    span {
      font-size: 28px;
    }
  }

  #PersonalPassMannerge {
    width: 100%;
    height: 100%;
    background: url("../../../assets/index/login_bg.jpg") no-repeat;
    background-size: 100% 100%;
    .login {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      left: 0;
      height: 77%;
      padding: 0 97px;
      width: 100%;
      box-sizing: border-box;
      .login-logo {
        margin-bottom: 50px;
        width: 100%;
        height: 79px;
        background: url("../../../assets/index/logo.png") no-repeat;
        background-size: 100% 100%;
      }
      .login-form {
        width: 100%;
        form {
          width: 100%;
          .label-input {
            position: relative;
            .user-icon {
              position: absolute;
              top: 50px;
              left: 47px;
              width: 31px;
              height: 40px;
              background: url("../../../assets/index/login_user.png") no-repeat;
              background-size: 100% 100%;
            }
            .pwd-icon {
              position: absolute;
              top: 50px;
              left: 47px;
              width: 31px;
              height: 42px;
              background: url("../../../assets/index/login_pwd.png") no-repeat;
              background-size: 100% 100%;
            }
            .pwd-icon2 {
              position: absolute;
              top: 50px;
              left: 47px;
              width: 31px;
              height: 42px;
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
              border-bottom: 1px solid #feb67a;
              box-sizing: border-box;
            }
            #code {
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
            margin-top: 50px;
            width: 526px;
            height: 74px;
            font-size: 40px;
            color: #fedcbd;
            background-color: transparent;
            border: solid 1px #fedcbd;
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
