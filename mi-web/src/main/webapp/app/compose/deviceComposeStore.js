Ext.define('App.compose.deviceComposeStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'deviceCompose/getComposeList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('deviceComposeTopViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});
