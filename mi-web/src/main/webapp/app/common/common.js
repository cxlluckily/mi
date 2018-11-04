function logout()
{
    var userKey = window.sessionStorage['userKey'];
    var config = {
        url: ctx + 'loginOut.do',
        success: function (response, opts) {
            window.isLogout = true;
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
        },
        failure: function (response, opts) {
            console.log('server-side failure with status code'
                + response.status);
            Ext.Msg.show({
                title: '错误',
                message: response,
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.error
            });
        }
    };
    App.common.Ajax.request(config);
}
