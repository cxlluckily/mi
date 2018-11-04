Ext.define('App.stockOutStockApply.stockOutStockApplyModel', {
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

Ext.define('App.stockOutStockApply.stockOutStockApplyStore', {
    extend: 'App.common.commonStore',
    model: "App.stockOutStockApply.stockOutStockApplyModel",
    proxy: {
        url: ctx + 'outStockApply/getOutStockApplyInfo.do',
        reader: {
            type: 'json',
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var view = Ext.getCmp(store.viewId);
            var formValues = view.down('form').getForm().getValues();
            var new_params = Ext.apply(formValues, {
                outOrderType:store.outOrderType
            });
            new_params.beginTime = new_params.beginTime + " 00:00:00";
            new_params.endTime = new_params.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
