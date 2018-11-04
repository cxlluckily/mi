Ext.define('App.mySparePart.mySparePartStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'mySparePart/getMySparePartList.do'
    },
    // listeners: {
    //     beforeload: function (store, options) {
    //         var formValues = Ext.getCmp('mySparePartViewFormId').getForm().getValues();
    //         Ext.apply(store.proxy.extraParams, formValues);
    //     }
    // }
});
