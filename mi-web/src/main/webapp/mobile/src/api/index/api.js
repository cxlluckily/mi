import axios from 'axios'
import qs from 'qs';

export default {
  // 首页登录
  resarchTreeDataAll(username, password) {
    const url = HOST + "/h5Login.do";
    return new Promise(function (resolve) {
      axios.post(url, qs.stringify({
        username: username,
        password: password
      })).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  getLoginUrl(state) {
    const url = HOST + "/wechat/authorize.do?state=" + state;
    return new Promise(function (resolve) {
      axios.get(url, {}).then(function (response) {
        resolve(response)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  wechatAutoLogin(data) {
    const url = HOST + "/wechat/userInfo.do?code=" + data.code + "&state=" + data.state;
    return new Promise(function (resolve) {
      axios.get(url, {}).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  updateUserLoginIp(openId) {
    const url = HOST + "/wechat/updateUserLoginIp.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        openId: openId
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  getCode(data) {
    const url = HOST + "/phoneCode.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  h5phoneCodeLogin(data) {
    const url = HOST + "/h5phoneCodeLogin.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response)
      }).catch(function (error) {
        console.log(error);
      });
    })
  }
}
