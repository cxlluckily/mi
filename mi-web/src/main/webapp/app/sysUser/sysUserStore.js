Ext.define('App.sysUser.sysUserModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'id',
            type: 'string'
        },
        {
            name: 'name',
            type: 'string'
        }
    ]
});

Ext.define('App.sysUser.sysUserStore', {
    extend: 'App.common.commonStore',
    model: "App.sysUser.sysUserModel",
    proxy: {
        url: ctx + 'user/getUserInfo.do'
    },
    autoLoad:false,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('sysUserViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.sysUser.sysOperationSubjectModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'id',
            type: 'string'
        },
        {
            name: 'name',
            type: 'string'
        }
    ]
});

Ext.define('App.sysUser.sysOperationSubjectStore', {
    extend: 'App.common.commonStore',
    model: "App.sysUser.sysUserModel",
    proxy: {
        url: 'app/json/sysOperationSubject.json',
        reader: {
            rootProperty: ''
        }
    },
    autoLoad: true,
    listeners: {
        datachanged: function (store, eOpts) {
        },
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('sysUserViewFormId').getForm().getValues();
            var new_params = {
                name: formValues.name,
                id: formValues.id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }

});