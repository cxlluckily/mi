Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringModel', {
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

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringStore', {
    extend: 'App.common.commonStore',
    model: "App.operationsEquipmentMonitoring.operationsEquipmentMonitoringModel",
    proxy: {
        url: ctx + 'onlineEquipment/getOnlineEquipmentList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationsEquipmentMonitoringGridFormId').getForm().getValues();
            formValues.sparePartTypeId = formValues.sparePartTypeId == 'all'?'-1':formValues.sparePartTypeId;
            formValues.stationId = !formValues.stationId?'-1':formValues.stationId;
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});