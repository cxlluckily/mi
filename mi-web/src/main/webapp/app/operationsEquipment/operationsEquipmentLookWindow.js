Ext.define('App.operationsEquipment.operationsEquipmentLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentLookForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 100,
        padding: '0 10 0 10',
        width: '100%',
        margin: '0'
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            fieldLabel: '设备类型',
                            name:'categoryName'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            fieldLabel: '备件名称',
                            name:'partName'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            fieldLabel: '站点',
                            name:'stationName'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'equipmentName',
                            fieldLabel: '设备名称'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'equipmentNo',
                            fieldLabel: '设备编号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'qrCode',
                            fieldLabel: '二维码'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'serialNumber',
                            fieldLabel: '序列号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'location',
                            fieldLabel: '所在位置'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'status',
                            fieldLabel: '状态',
                            renderer:function (value) {
                                if(value=='start_using'){
                                    return '可用'
                                }
                                if(value=='stop_using'){
                                    return '不可用'
                                }
                                return ''
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'materiaCoding',
                            fieldLabel: '物资编码'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'units',
                            fieldLabel: '单位'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'brand',
                            fieldLabel: '品牌'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'specificationModel',
                            fieldLabel: '规格型号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'size',
                            fieldLabel: '尺寸'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'material',
                            fieldLabel: '材质'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'remark',
                            fieldLabel: '备注'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'commonImagesLook',
                            title: '设备图片'
                        }
                    ]
                },
                // {
                //     xtype: 'container',
                //     columnWidth: 1,
                //     items: [
                //         {
                //             xtype: 'operationsEquipmentLookGrid'
                //         }
                //     ]
                // }
            ]
        }
    ]
});

Ext.define('App.operationsEquipment.operationsEquipmentLookGrid', {
    extend: 'Ext.grid.Panel',
    store: {

    },
    xtype: 'operationsEquipmentLookGrid',
    fullscreen: true,
    title:'备件更换记录',
    // 数据列表
    margin:'10 0 0 0',
    border:'1px solid #5fa2dd',
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
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '设备类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex:1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex:1
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex:1
        }
    ]
});

Ext.define('App.operationsEquipment.operationsEquipmentLookWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
    height:650,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    // autoScroll:true,
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    buttonAlign: "center",
    xtype: 'operationsEquipmentLookWindow',
    controller: 'operationsEquipmentController',
    items: [
        {
            xtype: 'operationsEquipmentLookForm'
        },
        {
            xtype: 'operationsEquipmentLookGrid'
        }
    ],
    buttons: [
        {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});