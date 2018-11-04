Ext.define('App.superSys.superUserStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'superAdmin/getSuperAdminList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('superUserViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});
