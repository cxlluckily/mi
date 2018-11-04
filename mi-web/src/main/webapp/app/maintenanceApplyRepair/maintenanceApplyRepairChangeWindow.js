Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChangeForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'maintenanceApplyRepairChangeForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'oldSparePartId',
                            fieldLabel: '旧部件id'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'oldPartName',
                            fieldLabel: '旧部件'
                        }
                    ]
                }
            ]
        }
    ]
});

// Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChangeGridStore', {
//     extend: 'App.common.commonStore',
//     pageSize: 20,
//     proxy: {
//         url: ctx + 'data/load.do',
//         reader: {
//             rootProperty: 'data'
//         }
//     },
//     listeners: {
//         beforeload: function (store, options) {
//             var new_params = {
//                 type: 'warehouse'
//             };
//             Ext.apply(store.proxy.extraParams, new_params);
//         }
//     }
// });

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChangeGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        // xclass: 'App.maintenanceApplyRepair.maintenanceApplyRepairChangeGridStore'
    },
    xtype: 'maintenanceApplyRepairChangeGrid',
    fullscreen: true,
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        },
        {
            text: 'userDeviceId',
            dataIndex: 'userDeviceId',
            flex: 1,
            hidden:true
        },
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden:true
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer: function (value, record) {
                if (value == "unique") {
                    return "唯一标识"
                }
                if (value == "notUnique") {
                    return "非唯一标识"
                }
            }
        },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            width:270
        },
        {
            text: '<font color="red">*</font>替换数量',
            dataIndex: 'replaceCount',
            flex: 1,
            editor:{
                field: {
                    xtype: 'numberfield',
                    name: 'replaceCount'
                }
            }
        },
        {
            text: '背包数量',
            dataIndex: 'count',
            flex: 1
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true
    }
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChangeWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 800,
    height:600,
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
    xtype: 'maintenanceApplyRepairChangeWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        {
            xtype: 'maintenanceApplyRepairChangeGrid'
        },
        {
            xtype:'maintenanceApplyRepairChangeForm',
            hidden:true
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