Ext.define('App.sysRole.sysRoleStoreModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'roleId',
            type: 'string'
        },
        {
            name: 'roleName',
            type: 'string'
        },
        {
            name: 'status',
            type: 'string'
        },
        {
            name: 'createUser',
            type: 'string'
        },
        {
            name: 'createTime',
            type: 'string'
        }
    ]
});

Ext.define('App.sysRole.sysRoleStore', {
    extend: 'App.common.commonStore',
    model: "App.sysRole.sysRoleStoreModel",
    // pageSize: 10,
    autoLoad: true,
    proxy: {
        url: ctx + 'roleInfo/getRolePagerInfo.do',
        extraParams: {
            status:'',
            roleName:''
        },
        reader: {
            type: 'json',
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    listeners:{
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('sysRoleViewForm').getForm().getValues();
            var new_params = {
                roleName: formValues.roleName,
                status: formValues.status
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
