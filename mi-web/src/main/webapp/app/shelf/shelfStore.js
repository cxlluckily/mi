Ext.define('App.shelf.shelfModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'shelfNumber',
            type: 'string'
        },
        {
            name: 'containerType',
            type: 'string'
        },
        {
            name: 'goodsShelvesId',
            type: 'number'
        },
        {
            name: 'sectionName',
            type: 'string'
        },
        {
            name: 'warehouseName',
            type: 'string'
        },
        {
            name: 'status',
            type: 'string'
        },
        {
            name: 'remark',
            type: 'string'
        }
    ]
});

Ext.define('App.shelf.shelfStore', {
    extend: 'App.common.commonStore',
    model: "App.shelf.shelfModel",
    proxy: {
        url: ctx + 'shelves/getPagerList.do',
        reader: {
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('shelfViewFormId').getForm().getValues();
            var new_params = Ext.apply(formValues, {
                userKey: window.sessionStorage['userKey'],
                pageNumber: store.currentPage,
                pageSize: this.pageSize
            });
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

