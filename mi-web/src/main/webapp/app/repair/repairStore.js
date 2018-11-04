Ext.define('App.repair.repairStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url:ctx +'partBreakdownRepairInfo/getPartBreakdownRepairInfoList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('repairViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        },
        afterload:function(store,options){
        }
    }
});
