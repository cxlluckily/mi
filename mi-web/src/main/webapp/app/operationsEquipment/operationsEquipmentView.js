Ext.define('App.operationsEquipment.operationsEquipmentView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.operationsEquipment.operationsEquipmentStore',
        'App.operationsEquipment.operationsEquipmentController',
        'App.operationsEquipment.operationsEquipmentWindow',
        'App.operationsEquipment.operationsEquipmentLookWindow',
        'App.common.importWindow',
        'App.common.messageWindow'
    ],
    store: {
        xclass: 'App.operationsEquipment.operationsEquipmentStore'
    },
    controller: {
        xclass: 'App.operationsEquipment.operationsEquipmentController'
    },
    xtype: 'operationsEquipmentView',
    fullscreen: true,
    tbar: [
        {
            id: 'operationsEquipmentViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 900,
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    border: false,
                    style: {
                        backgroundColor: '#fff',
                        marginTop: '-1px',
                        marginBottom: '0'
                    },
                    items: [
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '站点',
                                    anchor: '90%',
                                    value:'all',
                                    rawValue:'--双击选择--',
                                    emptyText: '--双击选择--',
                                    queryMode: 'local',
                                    name: 'stationId',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'code',
                                    allowBlank: false,
                                    blankText: '请选择站点!',
                                    listeners: {
                                        expand: function (cb, e, opt) {
                                            var w = Ext.create('App.addMaintenanceApply.addMaintenanceApplyStationWindow', {component: cb});
                                            w.show();
                                        },
                                        // change: function (component) {
                                        //     component.up('form').getForm().setValues({equipmentId: ''})
                                        // }
                                    }
                                },

                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '设备类型',
                                    emptyText: '--请选择--',
                                    store: {
                                        xclass: 'App.common.getEquipmentTypeFirst',
                                        includeAll:true
                                    },
                                    value:'all',
                                    queryMode: 'local',
                                    name: 'sparePartTypeId',
                                    editable: false,
                                    displayField: 'categoryName',
                                    valueField: 'sparePartTypeId',
                                    allowBlank: false,
                                    blankText: '全部'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '设备编号',
                                    name: 'equipmentNo'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '备件名称',
                                    name: 'partName'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '设备名称',
                                    name: 'equipmentName'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '物资编码',
                                    name: 'materiaCoding'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '规格型号',
                                    name: 'specificationModel'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '品牌',
                                    name: 'brand'
                                }
                            ]
                        },
                        {
                            columnWidth: .66,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus'
                                },
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导入',
                                    action: 'importDownload',
                                    iconCls: 'fa-download'
                                }
                                , {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导出',
                                    action: 'exportUpload',
                                    iconCls: 'fa-upload'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
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
            text: 'stationId',
            dataIndex: 'stationId',
            flex: 1,
            hidden: true
        },
        {
            text: 'sparePartTypeId',
            dataIndex: 'sparePartTypeId',
            flex: 1,
            hidden: true
        },
        {
            text: 'equipmentId',
            dataIndex: 'equipmentId',
            flex: 1,
            hidden: true
        },

        {
            text: '图片',
            dataIndex: 'imageUrl',
            width: 100,
            align: 'center',
            renderer: function (value, metaData, record) {
                if(value){
                    var img = '<img src=' + value + ' width="80" />';
                    return img;
                }
            }
        },
        {
            text: '线路',
            dataIndex: 'lineName',
            flex:1
        },
        {
            text: '站点',
            dataIndex: 'stationName',
            flex:1
        },
        {
            text: '设备类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '设备名称',
            dataIndex: 'equipmentName',
            flex:1
        },
        {
            text: '设备编号',
            dataIndex: 'equipmentNo',
            flex: 1
        },
        {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            align: 'left',
            renderer: function (value) {
                if (value == 'start_using') {
                    return '可用'
                }
                if (value == 'stop_using') {
                    return '不可用'
                }
                return ''
            }
        },

       

        {
            text: '所在位置',
            dataIndex: 'location',
            width:200,
            flex: 1,
            align: 'left'
        },
        {
            text: '序列号',
            dataIndex: 'serialNumber',
            flex:1,
            hidden:true
        },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            flex:1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex:1,
            hidden:true
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex:1,
            hidden:true
        },
        {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex:1,
            hidden:true
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
            menuDisabled: true,
            align: 'center',
            // handler: 'onEditRowAction',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                bodyStyle: 'padding:0px 10px'
                // margin: "0px 10px"
            },
            items: [
                {
                    tooltip: '修改',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn'
                },
                {
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'look',
                    handler: 'actionFn'
                },
                // {
                //     tooltip: '删除',
                //     iconCls: "fa-trash",
                //     flag: 'delete',
                //     handler: 'actionFn',
                //     getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                //         if (r.data.status == 'start_using') {
                //             return 'fa-trash';
                //         } else {
                //             return 'x-hidden';
                //         }
                //     }
                // },
            ]
        }
    ]
});