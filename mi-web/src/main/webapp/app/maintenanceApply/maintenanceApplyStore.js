Ext.define('App.maintenanceApply.maintenanceApplyModel', {
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

Ext.define('App.maintenanceApply.maintenanceApplyStore', {
    extend: 'App.common.commonStore',
    model: "App.maintenanceApply.maintenanceApplyModel",
    proxy: {
        url: ctx + 'repairApply/getRepairApplyPageInfo.do',
        reader: {
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('maintenanceApplyViewFormId').getForm().getValues();
            var new_params = Ext.apply(formValues, {
                type:'pc'
            });
            new_params.beginTime = new_params.beginTime + " 00:00:00";
            new_params.endTime = new_params.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
