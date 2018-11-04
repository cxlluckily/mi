Ext.define('App.main.NavStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'NavStore',

    constructor: function (config) {
        var me = this;
        me.callParent([
            Ext.apply({
                root: {
                    text: '功能概览',
                    id: 'all',
                    expanded: true,
                    iconCls: 'fa-home',
                    children: me.getNavItems()
                }
            }, config)
        ]);
    },

    getNavItems: function () {
        if(window.sessionStorage["userKey"] == 'null'){
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
        }
        var userType = window.sessionStorage.userType;
        if (userType === 'sysAdmin')
        {
            return [
                {
                    text: '系统管理',
                    id: 'job',
                    expanded: true,
                    iconCls: 'fa-users',
                    children: [
                        {id: 'superAdmin', xclass: "App.superSys.superUserView", iconCls: 'fa-user', text: '超级用户管理', leaf: true},
                        {id: 'operationView', xclass: "App.operation.operationView", iconCls: 'fa-user', text: '运营主体管理', leaf: true},
                        {id: 'menu', xclass: "App.menu.menuView", iconCls: 'fa-user', text: '菜单管理', leaf: true}/*,
                        {id: 'dataDict', xclass: "App.dataDict.dataDictView", iconCls: 'fa-user', text: '数据字典', leaf: true},*/
                    ]
                }
            ];
        }
        else if(userType === 'USER')
        {
            var jsonString = window.localStorage.menuJson;

            var json = JSON.parse(jsonString);
            return json;
        }
    }
});
