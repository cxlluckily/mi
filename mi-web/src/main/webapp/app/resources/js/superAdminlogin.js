(function(){
    $('#submit').on('click', function(e) {
        e.preventDefault();
        if($("#from").serializeArray()[0].value==''){
            alert('请输入操作员!');
        }else if($("#from").serializeArray()[1].value==''){
            alert("请输入密码!");
        }else{
            $.ajax({
                url : './superAdmin/superAdminLogin.do',
                type:'post',
                data: $("#from").serializeArray(),
                dataType:'json',
                success : function(data,stat,xhr){
                    var result = data.result;
                    if(result == 'success'){
                        window.location.replace('./index.jsp');
                    } else {
                        alert(data.message);
                    }
                },
                error : function(xhr, stat, exception){
                    alert(data.message);
                }
            });
        }

    });
    
//     function getCookie(name)
//     {
//         var strcookie = document.cookie;//获取cookie字符串
//         var arrcookie = strcookie.split("; ");//分割
// //遍历匹配
// //         console.log("arrcookie=",arrcookie);
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
//     if (isName === 'true')
//     {
//         $.ajax({
//             url: './superAdmin/superAdminAutoLogin.do',
//             type: 'post',
//             data: $("#from").serializeArray(),
//             dataType: 'json',
//             success: function (response, stat, xhr) {
//                 var result = response.result;
//                 if (result == 'success')
//                 {
//                     var json = response.data;
//                     var jsonString = JSON.stringify(json);
//                     window.sessionStorage.menuJson = jsonString;
//                     window.location.replace('./index.jsp');
//                 }
//                 else
//                 {
//                     alert(response.message)
//                 }
//             },
//             error: function (xhr, stat, exception) {
//                 alert(xhr.message)
//             }
//         });
//     }

}());