// mitest.shankephone.com/wechat/configJsSDK.do
import axios from 'axios'

export default {
  // 微信 JS-SDK 配置
  WxConfig() {
    const url = HOST + "/wechat/configJsSDK.do";
    axios.post(url, {
      url: location.href.split('#')[0]
    }).then(function (response) {
      const DATA = response.data.data;
      wx.config({
        // debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: DATA.appId, // 必填，公众号的唯一标识
        timestamp: DATA.timestamp, // 必填，生成签名的时间戳
        nonceStr: DATA.nonceStr, // 必填，生成签名的随机串
        signature: DATA.signature, // 必填，签名
        jsApiList: ["scanQRCode", "chooseImage", "uploadImage", "downloadImage"] // 必填，需要使用的JS接口列表
      });
    }).catch(function (error) {
      console.log(error);
    });
  },
  // 微信二维码扫描
  WxScanQRCode() {
    return new Promise(function (resolve) {
      wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function (res) {
          resolve(res.resultStr); // 当needResult 为 1 时，扫码返回的结果
        }
      });
    })
  },
  // 微信拍照或从手机相册中选图接口
  WxChooseImage(initPage) {
    return new Promise(function (resolve) {
      wx.chooseImage({
        count: initPage || 3, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
          resolve(res.localIds); // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
        }
      });
    })
  },
  // 微信上传图片接口
  WxUploadImage(serverurl) {
    return new Promise(function (resolve) {
      wx.uploadImage({
        localId: serverurl, // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 0, // 默认为1，显示进度提示
        success: function (res) {
          resolve(res.serverId); // 返回图片的服务器端ID
        }
      });
    })
  },
  // 微信下载图片接口
  WxDownloadImage(serverurl) {
    return new Promise(function (resolve) {
      wx.downloadImage({
        serverId: serverurl, // 需要下载的图片的服务器端ID，由uploadImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
          resolve(res.localId); // 返回图片下载后的本地ID
        }
      });
    })
  }
}
// document.getElementById('url').value = location.href.split('#')[0];
