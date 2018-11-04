Ext.define('App.dataDict.dataDictModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'dataDictionaryId',
            type: 'int'
        },
        {
            name: 'dataLabel',
            type: 'string'
        },
        {
            name: 'code',
            type: 'string'
        },
        {
            name: 'name',
            type: 'string'
        },
        {
            name: 'sort',
            type: 'int'
        },
        {
            name: 'status',
            type: 'string'
        },
        {
            name: 'remark',
            type: 'string'
        },
        {
            name: 'createUser',
            type: 'string'
        },
        {
            name: 'createTime',
            type: 'date'
        },
        {
            name: 'modifyUser',
            type: 'string'
        },
        {
            name: 'modifyTime',
            type: 'date'
        }]
});
Ext.define('App.dataDict.dataDictStore', {
	extend : 'App.common.commonStore',
	model : 'App.dataDict.dataDictModel',
	proxy : {
        url: ctx + 'dataDict/list.do'
	},
	autoLoad : true,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('dataDictViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});