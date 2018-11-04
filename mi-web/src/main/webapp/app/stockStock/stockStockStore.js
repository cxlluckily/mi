Ext.define('App.stockStock.stockStockStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'stockInfo/getPagerInfo.do'
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('stockStockViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});
