Ext.define('App.supplier.SupplierModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'supplierId',
            type: 'int'
        },
        {
            name: 'operationSubjectId',
            type: 'int'
        },
        {
            name: 'supplierName',
            type: 'string'
        },
        {
            name: 'abbreviation',
            type: 'string'
        },
        {
            name: 'brandName',
            type: 'string'
        },
        {
            name: 'contacts',
            type: 'string'
        },
        {
            name: 'contactInfo',
            type: 'string'
        },
        {
            name: 'address',
            type: 'string'
        },
        {
            name: 'email',
            type: 'string'
        },
        {
            name: 'taxpayerNumber',
            type: 'string'
        },
        {
            name: 'bankOfDeposit',
            type: 'string'
        },
        {
            name: 'bankAccount',
            type: 'string'
        },
        {
            name: 'accountName',
            type: 'string'
        },
        {
            name: 'remark',
            type: 'string'
        },
        {
            name: 'status',
            type: 'string'
        },
        {
            name: 'createUser',
            type: 'string'
        },
        {
            name: 'createTime',
            type: 'string'
        },
        {
            name: 'modifyUser',
            type: 'string'
        },
        {
            name: 'modifyTime',
            type: 'string'
        }
    ]
});
Ext.define('App.supplier.supplierStore', {
	extend : 'App.common.commonStore',
	model : 'App.supplier.SupplierModel',
	proxy : {
		type: 'ajax',
		extraParams: {status: 'all'},
        url: ctx + 'supplier/list.do'
	},
	autoLoad : true,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('supplierViewFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});