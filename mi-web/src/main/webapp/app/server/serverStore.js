Ext.define('App.server.serverModel', {
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

Ext.define('App.server.serverStore', {
    extend: 'App.common.commonStore',
    model: "App.server.serverModel",
    proxy: {
        url: ctx + 'onlineEquipment/getOnlineEquipmentList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('serverViewFormId').getForm().getValues();
            formValues.sparePartTypeId = formValues.sparePartTypeId == 'all'?'-1':formValues.sparePartTypeId;
            formValues.stationId = !formValues.stationId?'-1':formValues.stationId;
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});