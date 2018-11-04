Ext.define('App.orgLineStation.orgStationModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'lineId',
            type: 'string'
        },
        {
            name: 'stationCode',
            type: 'string'
        },
        {
            name: 'stationName',
            type: 'string'
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
            name: 'pinyin',
            type: 'string'
        },
        {
            name: 'wasBegin',
            type: 'bool'
        },
        {
            name: 'wasEnd',
            type: 'bool'
        },
        {
            name: 'status',
            type: 'string'
        }
    ]
});

Ext.define('App.orgLineStation.orgStationStore', {
    extend: 'App.common.commonStore',
    model: "App.orgLineStation.orgStationModel",
    proxy: {
        url: ctx + 'station/getStationInfo.do',
        reader: {
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
            var formValues = Ext.getCmp('orgStationViewFormId').getForm().getValues();
            var lineId = -1;
            if(orgLineSelect){
                lineId = orgLineSelect.data.lineId;
            }else{
                Ext.Msg.show({
                    title: '警告',
                    message: '请先选择一条线路！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
            }
            var new_params = Ext.apply(formValues,{
                lineId:lineId
            });
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
