(function () {
    $('#submit').on('click', function (e) {
        e.preventDefault();
        if ($("#from").serializeArray()[0].value == '') {
            alert('请输入手机号!');
        } else if ($("#from").serializeArray()[1].value == '') {
            alert("请输入密码!");
        } else {
            $.ajax({
                url: './login.do',
                type: 'post',
                data: $("#from").serializeArray(),
                dataType: 'json',
                success: function (response, stat, xhr) {
                    var result = response.result;
                    if (result == 'success') {

                        var json = response.data;
                        var jsonString = JSON.stringify(json);
                        window.sessionStorage.menuJson = jsonString;
                        window.localStorage.menuJson = jsonString;
                        window.location.replace('./index.jsp');
                    }
                    else {
                        alert(response.message);
                    }
                },
                error: function (xhr, stat, exception) {
                    console.log(xhr);
                    alert('网络缓慢，请刷新后重试！');
                }
            });
        }
    });
    $('#codeLogin').on('click', function (e) {
        e.preventDefault();
        $('#usernameByCode')[0].value = $('#username')[0].value;
        $('#loginByPassword').addClass('login-hidden');
        $('#loginByCode').removeClass('login-hidden');
    });
    $('#passwordLogin').on('click', function (e) {
        e.preventDefault();
        $('#username')[0].value = $('#usernameByCode')[0].value;
        $('#loginByCode').addClass('login-hidden');
        $('#loginByPassword').removeClass('login-hidden');
    });
    $('#getCode').on('click', function (e) {
        let _this = this;
        if ($("#codeFrom").serializeArray()[0].value == '') {
            alert('请输入手机号!');
        } else {
            $(_this).addClass('no-drop');
            _this.innerText = '正在获取验证码...';
            let postData = {
                phone: $("#codeFrom").serializeArray()[0].value
            };
            $.ajax({
                url: './phoneCode.do',
                type: 'post',
                data: JSON.stringify(postData),
                contentType: 'application/json',
                dataType: 'json',
                success: function (response, stat, xhr) {
                    if (response.result == 'success') {
                        let time = 60;
                        _this.innerText = time + '秒后重新获取';
                        let timeOut = setInterval(function () {
                            time--;
                            if (time == 0) {
                                clearInterval(timeOut);
                                $(_this).removeClass('no-drop');
                                _this.innerText = '获取验证码';
                            } else {
                                _this.innerText = time + '秒后重新获取';
                            }
                        }, 1000);
                    } else {
                        $(_this).removeClass('no-drop');
                        _this.innerText = '获取验证码';
                        alert(response.message);
                    }
                },
                error: function (xhr, stat, exception) {
                    console.log(xhr);
                    alert('网络缓慢，请刷新后重试！');
                }
            });
        }


    });
    $('#codeSubmit').on('click', function (e) {
        e.preventDefault();
        if ($("#codeFrom").serializeArray()[0].value == '') {
            alert('请输入手机号!');
        } else if ($("#codeFrom").serializeArray()[1].value == '') {
            alert("请输入验证码!");
        } else {
            let postData = {
                phone: $("#codeFrom").serializeArray()[0].value,
                code: $("#codeFrom").serializeArray()[1].value
            };
            $.ajax({
                url: './phoneCodeLogin.do',
                type: 'post',
                data: JSON.stringify(postData),
                contentType: 'application/json',
                dataType: 'json',
                success: function (response, stat, xhr) {
                    var result = response.result;
                    if (result == 'success') {
                        var json = response.data;
                        var jsonString = JSON.stringify(json);
                        window.sessionStorage.menuJson = jsonString;
                        window.localStorage.menuJson = jsonString;
                        window.location.replace('./index.jsp');
                    }
                    else {
                        alert(response.message);
                    }
                },
                error: function (xhr, stat, exception) {
                    console.log(xhr);
                    alert('网络缓慢，请刷新后重试！');
                }
            });
        }
    });

//     function getCookie(name)
//     {
//         var strcookie = document.cookie;//获取cookie字符串
//         var arrcookie = strcookie.split("; ");//分割
// //遍历匹配
//         for (var i = 0; i < arrcookie.length; i++)
//         {
//             var arr = arrcookie[i].split("=");
//             if (arr[0] == name)
//             {
//                 return arr[1];
//             }
//         }
//         return "";
//     }
//
//     var isName = getCookie("isName");
//     document.getElementById("isName").value = true;
//     if (isName === 'true')
//     {
//         $.ajax({
//             url: './AutoLogin.do',
//             type: 'post',
//             data: $("#from").serializeArray(),
//             dataType: 'json',
//             success: function (response, stat, xhr) {
//                 var result = response.result;
//                 if (result == 'success')
//                 {
//                 	debugger;
//                     var json = response.data;
//                     var jsonString = JSON.stringify(json);
//                     window.sessionStorage.menuJson = jsonString;
//                     window.localStorage.menuJson = jsonString;
//                     window.location.replace('./index.jsp');
//                 }
//                 else
//                 {
//
//                 }
//             },
//             error: function (xhr, stat, exception) {
//
//             }
//         });
//     }

}())