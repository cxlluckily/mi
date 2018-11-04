export default {
  // 获取时间
  // format:"yyyy-MM-dd hh:mm:ss"
  // dateValue:"-30"
  // date:"yyyy-MM-dd hh:mm:ss"格式
  getDate(format, dateValue, date, type) {
    if (date&&type) {
      date = new Date(Date.parse(date.replace(/-/g, "/")));
    }else if(date&&!type){
      date = date;
    }
    else {
      date = new Date();
    }
    format = format ? format : "yyyy-MM-dd hh:mm:ss";
    dateValue = dateValue ? dateValue : 0;
    if (dateValue != 0) {
      dateValue = dateValue * 24 * 60 * 60 * 1000;
      var time = date.getTime() + dateValue;
      date.setTime(time);
    }
    var o = {
      "M+": date.getMonth() + 1, //月份
      "d+": date.getDate(), //日
      "H+": date.getHours(), //小时
      "h+": date.getHours(), //小时
      "m+": date.getMinutes(), //分
      "s+": date.getSeconds(), //秒
      "q+": Math.floor((date.getMonth() + 3) / 3), //季度
      "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
      if (new RegExp("(" + k + ")").test(format)) {
        format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      }
    }
    return format;
  },
  formatDate(inputTime, format) {
    if (inputTime == '') {
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
  },

  //获取localStorage，sessionStorage
  //key 键值 string
  //value 值
  //isJson 是否为json true false
  //Delete 是否删除 true false
  getLocalStorage(key, isJson, Delete) {
    let returnValue = window.localStorage[key];
    let isJsonTrue = isJson ? true : false;
    let isDelete = Delete ? true : false;
    if (!returnValue) {
      return ''
    }
    if(isJsonTrue){
      returnValue = JSON.parse(returnValue);
    }
    if(isDelete){
      window.localStorage.removeItem(key);
    }
    return returnValue;
  },
  setLocalStorage(key, value, isJson) {
    let isJsonTrue = isJson ? true : false;
    let saveValue = value;
    if(isJsonTrue){
      saveValue = JSON.stringify(saveValue);
    }
    window.localStorage[key] = saveValue;
  },
  getSessionStorage(key, isJson, Delete) {
    let returnValue = window.sessionStorage[key];
    let isJsonTrue = isJson ? true : false;
    let isDelete = Delete ? true : false;
    if (!returnValue) {
      return ''
    }
    if(isJsonTrue){
      returnValue = JSON.parse(returnValue);
    }
    if(isDelete){
      window.sessionStorage.removeItem(key);
    }
    return returnValue;
  },
  setSessionStorage(key, value, isJson) {
    let isJsonTrue = isJson ? true : false;
    let saveValue = value;
    if(isJsonTrue){
      saveValue = JSON.stringify(saveValue);
    }
    window.sessionStorage[key] = saveValue;
  }
}
