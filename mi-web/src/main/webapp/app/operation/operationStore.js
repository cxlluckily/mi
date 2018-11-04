Ext.define('App.operation.operationStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'subject/getOperationList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});
