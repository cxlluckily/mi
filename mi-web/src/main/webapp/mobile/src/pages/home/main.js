// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Home from './Home'
import router from '../../router/home'
import '../../api/home/homeMint'
import '../../api/V_jQuery'
import 'lib-flexible'
// import "../../style/global.scss";
// 全局过滤器 , 时间格式化
Vue.filter('initDate', function (inputTime, format) {
  if(!inputTime||inputTime == '') {
    return;
  }
  var date = new Date(+inputTime);
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? "0" + m : m;
  var d = date.getDate();
  d = d < 10 ? "0" + d : d;
  var h = date.getHours();
  h = h < 10 ? "0" + h : h;
  var minute = date.getMinutes();
  var second = date.getSeconds();
  minute = minute < 10 ? "0" + minute : minute;
  second = second < 10 ? "0" + second : second;
  switch (format) {
    case 'ymd':
      return y + "-" + m + "-" + d;
      break;
    case 'hms':
      return h + ":" + minute + ":" + second;
      break;
    default:
      return y + "-" + m + "-" + d + ' ' + h + ":" + minute + ":" + second;
      break;
  }
});

// 引入接口
import api from '../../api/home/api'
import wxApi from '../../api/home/wxSdkApi'
import requests from '../../api/home/request'
import common from '../../api/home/common'

// 把接口绑定都 vue 原型上
Vue.prototype.api = api;
Vue.prototype.wxApi = wxApi;
Vue.prototype.requests = requests;
Vue.prototype.common = common;
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#home',
  router,
  render: h => h(Home)
})
