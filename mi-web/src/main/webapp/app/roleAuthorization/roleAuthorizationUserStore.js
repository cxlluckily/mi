Ext.define('App.roleAuthorization.sysUserModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'userId',
            type: 'int'
        },
        {
            name: 'orgId',
            type: 'int'
        },
        {
            name: 'orgName',
            type: 'string'
        },
        {
            name: 'userName',
            type: 'string'
        },
        {
            name: 'realName',
            type: 'string'
        },
        {
            name: 'position',
            type: 'string'
        },
        {
            name: 'workNumber',
            type: 'string'
        }
    ]
});

Ext.define('App.roleAuthorization.roleAuthorizationUserStore',{
    extend: 'App.common.commonStore',
    model: "App.roleAuthorization.sysUserModel",
    pageSize: 20,
    proxy: {
        url: ctx + 'user/all.do',
        reader: {
            type: 'json',
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: true
});


