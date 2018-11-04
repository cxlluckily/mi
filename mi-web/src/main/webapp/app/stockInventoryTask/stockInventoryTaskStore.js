Ext.define('App.stockInventoryTask.stockInventoryTaskModel', {
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

Ext.define('App.stockInventoryTask.stockInventoryTaskStore', {
    extend: 'App.common.commonStore',
    model: "App.stockInventoryTask.stockInventoryTaskModel",
    proxy: {
        url: ctx + 'inventoryTask/getInventoryTaskList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('stockInventoryTaskViewFormId').getForm().getValues();
            formValues.beginTime = formValues.beginTime + " 00:00:00";
            formValues.endTime = formValues.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});