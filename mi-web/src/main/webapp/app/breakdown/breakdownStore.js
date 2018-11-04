Ext.define('App.breakdown.breakdownStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'partBreakdownInfo/getBreakdownInfoList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('breakdownViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        },
        afterload:function(store,options){
        }
    }
});
