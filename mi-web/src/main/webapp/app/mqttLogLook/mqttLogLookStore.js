
Ext.define('App.mqttLogLook.mqttLogLookStore', {
    extend: 'App.common.commonStore',
  
    proxy: {
        url: ctx + 'mqttLogs/getMqttLogsList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('mqttLogLookViewFormId').getForm().getValues();
            formValues.beginTime = formValues.beginTime + " 00:00:00";
            formValues.endTime = formValues.endTime + " 23:59:59";
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});