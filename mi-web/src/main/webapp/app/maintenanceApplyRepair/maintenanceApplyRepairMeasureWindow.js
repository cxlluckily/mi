Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMeasureForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'maintenanceApplyRepairMeasureForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 50,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columns: 1,
            items: [
                {
                    xtype: 'textareafield',
                    name: 'other',
                    fieldLabel: '其他'
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMeasureGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass: 'App.common.getTreatmentInfoList',
        listeners:{
            load:function (store, records, successful, operation, eOpts) {
                var measureGrid = Ext.ComponentQuery.query("maintenanceApplyRepairMeasureGrid")[0];
                var measureGridHide = Ext.ComponentQuery.query("maintenanceApplyRepairMeasureGridHide")[0];
                var measureGridHideData = measureGridHide.getStore().getData().items;
                var select = [];
                var selectModel = [];
                Ext.each(measureGridHideData,function (item) {
                    selectModel.push(item.data);
                });
                console.log(measureGridHideData);
                Ext.each(records,function (record,index) {
                    Ext.each(measureGridHideData,function (item) {
                        if(record.data.repairInfoId == item.data.repairInfoId){
                            select.push(index);
                        }
                    });
                });
                // measureGrid.setSelection(selectModel);
                // if(select.length>0){
                //     measureGrid.setSelectionModel(selectModel);
                //     measureGrid.getSelectionModel().selectRows(select);
                // }
            }
        }
    },
    xtype: 'maintenanceApplyRepairMeasureGrid',
    fullscreen: true,
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    height: 400,
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',
            width: '10px',
            align: 'center'
        },
        {
            text: 'repairInfoId',
            dataIndex: 'repairInfoId',
            flex: 1,
            hidden: true
        },
        {
            text: '故障描述',
            dataIndex: 'processMode',
            flex: 1
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true
    }
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMeasureGridHide', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'maintenanceApplyRepairMeasureGridHide',
    fullscreen: true,
    height: 400,
    hidden: true,
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',
            width: '10px',
            align: 'center'
        },
        {
            text: 'repairInfoId',
            dataIndex: 'repairInfoId',
            flex: 1,
            hidden: true
        },
        {
            text: '故障描述',
            dataIndex: 'processMode',
            flex: 1
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true
    }
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMeasureWindow', {
    extend: 'Ext.window.Window',
    title: '处理措施',
    width: 600,
    height: 600,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    buttonAlign: "center",
    xtype: 'maintenanceApplyRepairMeasureWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        {
            xtype: 'maintenanceApplyRepairMeasureGrid'
        },
        {
            xtype: 'maintenanceApplyRepairMeasureGridHide'
        },
        {
            xtype: 'maintenanceApplyRepairMeasureForm'
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});