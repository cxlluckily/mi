Ext.define('App.common.commonStore', {
    extend: 'Ext.data.Store',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        headers: {
            userKey: window.sessionStorage['userKey']
        },
        actionMethods: {
            read: 'POST',
            create: 'POST',
            update: 'POST',
            destroy: 'POST'
        },
        extraParams: {
            userKey: window.sessionStorage['userKey'],
            operationSubjectId: window.sessionStorage['operationSubjectId']
        },
        paramsAsJson: true,
        reader: {
            type: 'json',
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    constructor: function () {
        var me = this;
        me.callParent(arguments);
        me.on("load", function (store, records, successful, eOpts) {
            var data = Ext.decode(eOpts._response.responseText);
            var result = data.result;
            if (data.statusCode === 401)
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
            // if (result == 'failure')
            // {
            //     // Ext.Msg.show({
            //     // 	title : '错误',
            //     // 	message : data.message,
            //     // 	buttons : Ext.Msg.OK,
            //     // 	icon : Ext.Msg.error
            //     // });
            // }
        });
    },
    autoLoad: true
});