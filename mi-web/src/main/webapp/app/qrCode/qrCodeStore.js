Ext.define('App.qrCode.qrCodeStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'code/getQRCodeList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('qrCodeViewFormId').getForm().getValues();
            formValues.beginTime = formValues.beginTime + " 00:00:00";
            formValues.endTime = formValues.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});
