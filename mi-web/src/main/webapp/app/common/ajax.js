Ext.define('App.common.Ajax', {
    singleton: true,
    method: 'POST',
    headers: {
        userKey: window.sessionStorage['userKey']
    },
    config: {
        alias: 'common.ajax',
    },
    request: function (opt) {
        if(opt.btn){
            opt.btn.disable();
        }
        if (!opt.method)
        {
            opt.method = this.method;
        }
        opt.headers = this.headers;
        var params = {
            userKey: window.sessionStorage['userKey'],
            operationSubjectId: window.sessionStorage['operationSubjectId']
        };
        Ext.apply(params, opt.data);
        var me = this;
        Ext.Ajax.request({
            url: opt.url,
            headers: me.headers,
            jsonData: Ext.encode(params),
            method: me.method,
            success: function (response, opts) {
                // var obj = Ext.decode(response.responseText);
                var result = Ext.decode(response.responseText);
                if (result.statusCode === 401)
                {
                    if(!window.isLogout){
                        window.isLogout = true;
                        alert("对不起，您的登录状态有误，请重新登录后重试！");
                    }
                    if (window.sessionStorage["userType"] === 'USER')
                    {
                        window.sessionStorage.clear();
                        window.location.href = 'login.html';
                    }
                    else if (window.sessionStorage["userType"] === 'sysAdmin')
                    {
                        window.sessionStorage.clear();
                        window.location.replace(ctx + 'superAdminLogin.html');
                    }
                    else
                    {
                        window.sessionStorage.clear();
                        window.location.href = 'login.html';
                    }
                    return;
                }
                if(opt.btn){
                    opt.btn.enable();
                }
                opt.success(response, opts);
            },
            failure: function (response, opts) {
                if(opt.btn){
                    opt.btn.enable();
                }
                console.log('server-side failure with status code ' + response.status);
                // opt.failure(response, opts);
                var errorMessage = response;
                if(errorMessage){
                    errorMessage = '网络缓慢，请刷新后重试！';
                }
                Ext.Msg.show({
                    title: '错误',
                    message: errorMessage,
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.error
                });
            }
        });
    }/*,
	constructor: function (config) {  
        this.initConfig(config);  
    }  */
});

