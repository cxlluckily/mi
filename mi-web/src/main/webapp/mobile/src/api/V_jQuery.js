(function (window) {
  function V(arg) {
    return new V_jQuery(arg);
  }

  function myAddEvent(obj, evt, fn) { //事件绑定，将会让一个事件发生多个
    if (obj.attachEvent) {
      obj.attachEvent('on' + evt, fn); //attachEvent方法只支持IE浏览器,执行顺序是，后绑定的先执行．
    } else {
      //功能相同的指令是addEventListener,该指令支持FF等浏览器，并且是W3C标准
      obj.addEventListener(evt, fn, false); //执行顺序是，先绑定的先执行
    }
  }

  function getStyle(obj, attr) {
    if (obj.currentStyle) { //行内样式
      return obj.currentStyle[attr];
    } else { //嵌入样式，外联样式
      return getComputedStyle(obj, false)[attr];
    }
  }

  //arg类型：
  //1.函数
  //2.字符串:  #xxx,.xxx,X
  //3.对象
  function V_jQuery(arg) {
    //用来保存选中的元素
    this.elements = [];

    switch (typeof arg) {
      case 'function':
        myAddEvent(window, 'load', arg); //使用window.onload当多个时间发生时，会产生覆盖
        break;
      case 'string':
        switch (arg.charAt(0)) {
          case '#': //Id
            //#id
            var obj = document.querySelector(arg);
            this.elements.push(obj);
            break;
          case '.': //className
            //.class
            this.elements = document.querySelectorAll(arg);
            break;
          default: //TagName
            this.elements = document.querySelectorAll(arg);
        }
        break;
      case 'object':
        this.elements.push(arg);
    }
  }
  //点击事件
  V_jQuery.prototype.click = function (fn) {
    var i = 0;
    for (i = 0; i < this.elements.length; i++) {
      myAddEvent(this.elements[i], 'click', fn);
    }
  };

  V_jQuery.prototype.show = function () {
    for (var i = 0; i < this.elements.length; i++) {
      this.elements[i].style.display = 'block';
    }
  };
  V_jQuery.prototype.hide = function () {
    for (var i = 0; i < this.elements.length; i++) {
      this.elements[i].style.display = 'none';
    }
  };
  V_jQuery.prototype.hover = function (over, out) {
    for (var i = 0; i < this.elements.length; i++) {
      myAddEvent(this.elements[i], 'mouseover', over);
      myAddEvent(this.elements[i], 'mouseout', out);
    }
  };
  V_jQuery.prototype.addClass = function (blank) {
    var obj_class = this.elements.className, //获取 class 内容.
      blank = (obj_class != '') ? ' ' : ''; //判断获取到的 class 是否为空, 如果不为空在前面加个'空格'.
    var added = obj_class + blank; //组合原来的 class 和需要添加的 class.
    this.elements.className = added; //替换原来的 class.
    console.log(this.elements.className)

  };
  V_jQuery.prototype.css = function (attr, value) {
    if (arguments.length == 2) {
      for (var i = 0; i < this.elements.length; i++) {
        this.elements[i].style[attr] = value;
      }
    } else {
      return getStyle(this.elements[0], attr);
    }
  }
  return window.V = V;
}(window))
