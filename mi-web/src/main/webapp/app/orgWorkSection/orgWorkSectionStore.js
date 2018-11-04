Ext.define('App.orgWorkSection.orgWorkSectionModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sectionName',
            type: 'string'
        },
        {
            name: 'sectionCode',
            type: 'string'
        },
        {
            name: 'operationSubjectId',
            type: 'number'
        },
        {
            name: 'workSectionId',
            type: 'number'
        },
        {
            name: 'headPerson',
            type: 'string'
        },
        {
            name: 'phone',
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
            name: 'modifyTime',
            type: 'number'
        },
        {
            name: 'createTime',
            type: 'number'
        }
    ]
});

Ext.define('App.orgWorkSection.orgWorkSectionStore', {
    extend: 'App.common.commonStore',
    model: "App.orgWorkSection.orgWorkSectionModel",
    proxy: {
        url: ctx + 'workSection/getWorkSectionInfo.do',
        reader: {
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('orgWorkSectionViewFormId').getForm().getValues();
            var new_params = Ext.apply(formValues, {
                pageNumber: store.currentPage,
                pageSize: this.pageSize
            });
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

