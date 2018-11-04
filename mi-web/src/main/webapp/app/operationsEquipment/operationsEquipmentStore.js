Ext.define('App.operationsEquipment.operationsEquipmentModel', {
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

Ext.define('App.operationsEquipment.operationsEquipmentStore', {
    extend: 'App.common.commonStore',
    model: "App.operationsEquipment.operationsEquipmentModel",
    proxy: {
        url: ctx + 'operationsEquipment/getPagerInfo.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationsEquipmentViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});