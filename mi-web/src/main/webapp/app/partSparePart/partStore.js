Ext.define('App.partSparePart.partModel', {
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

Ext.define('App.partSparePart.partStore', {
    extend: 'App.common.commonStore',
    model: "App.partSparePart.partModel",
    proxy: {
        url: ctx + 'sparePart/getSparePartInfo.do'
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
            var sparePartTypeId = -1;
            if (partTypeSelect) {
                sparePartTypeId = partTypeSelect.data.sparePartTypeId;
            } else {
                Ext.Msg.show({
                    title: '警告',
                    message: '请先选择一个备件类型！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
            }
            var formValues = Ext.getCmp('partViewFormId').getForm().getValues();
            Ext.apply(formValues, {sparePartTypeId: sparePartTypeId});
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});