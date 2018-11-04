Ext.define('App.stockBusinessApply.stockBusinessApplyModel', {
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

Ext.define('App.stockBusinessApply.stockBusinessApplyStore', {
    extend: 'App.common.commonStore',
    model: "App.stockBusinessApply.stockBusinessApplyModel",//字段限定类型，需要新增行数据时使用
    proxy: {
        url: ctx + 'applyInfo/getPagerInfoByUser.do'
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var view = Ext.getCmp(store.viewId);
            var formValues = view.down('form').getForm().getValues();
            var new_params = Ext.apply(formValues, {
                applyType:store.applyType
            });
            new_params.beginTime = new_params.beginTime + " 00:00:00";
            new_params.endTime = new_params.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
