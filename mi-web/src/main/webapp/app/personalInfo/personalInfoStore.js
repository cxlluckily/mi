//Store.js:它包含本地缓存的数据
Ext.define('App.personalInfo.personalInfoStoreModel', {
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

Ext.define('App.personalInfo.personalInfoStore', {
    extend: 'Ext.data.Store',
    model: "App.personalInfo.personalInfoStoreModel",
    proxy: {
        type: 'ajax',
        url: ctx + 'user/getOneUserInfo.do'
    },
    listeners: {
        beforeload : function (store, options) {
            var formValues = Ext.getCmp('personalInfoView').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});