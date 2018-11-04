Ext.define('App.stockOutStockApply.stockOutStockApplyLookDetailGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store: {},
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyLookDetailGrid',
    fullscreen: true,
    height: 200,
    style:{
        border:"1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1
        },
        {
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1
        },
        {
            text: '状态',
            dataIndex: 'stockStatus',
            flex: 1,
            renderer: function (value, record) {
                if (value == "normal") {
                    return "好件"
                }
                if (value == "bad") {
                    return "坏件"
                }
            }
        },
        {
            text: '待出库数量',
            dataIndex: 'outCount',
            flex: 1
        },
        {
            text: '已选择数量',
            dataIndex: 'alreadyOutCount',
            flex: 1
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyLookDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    // height:200,
    xtype: 'stockOutStockApplyLookDetailForm',
    autoScroll:true,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '0 10 0 10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'applyNo',
                    fieldLabel: '关联单据号'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'applyUser',
                    fieldLabel: '申请人'
                },
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'auditUser',
                    fieldLabel: '审核人'
                }
            ]
        },{
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'inWareHouseName',
                    fieldLabel: '入库仓库'
                }
            ]
        },{
            xtype: 'container',
            layout: 'column',
            columnWidth:1,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'applyRemark',
                    fieldLabel: '申请单备注'
                }
            ]
        },{
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'outStockApplyNO',
                    fieldLabel: '出库单号'
                }
            ]
        },{
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'outUser',
                    fieldLabel: '出库人'
                }
            ]
        },{
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'warehouseName',
                    fieldLabel: '出库仓库'
                }
            ]
        }
        ,{
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'remark',
                    fieldLabel: '出库备注'
                }
            ]
        }
        ,
        {
            xtype: 'container',
            layout: 'column',
            hidden:true,
            items: [
                {
                    xtype: 'textfield',
                    name: 'outApplyStatus',
                    fieldLabel: 'outApplyStatus',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'applyId',
                    fieldLabel: 'applyId',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'applyUser',
                    fieldLabel: 'applyUser',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'partCount',
                    fieldLabel: 'partCount',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseId',
                    fieldLabel: 'warehouseId',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'outStockApplyId',
                    fieldLabel: 'outStockApplyId',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'outUser',
                    fieldLabel: 'outUser',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'applyDate',
                    fieldLabel: 'applyDate',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseName',
                    fieldLabel: 'warehouseName',
                    hidden:true
                }
            ]
        }
    ]
});


Ext.define('App.stockOutStockApply.stockOutStockApplyLookOutGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store: {
        data: []
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyLookOutGrid',
    fullscreen: true,
    height: 200,
    style: {
        border: "1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: 'stockId',
            dataIndex: 'stockId',
            flex: 1,
            hidden: true
        },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1
        },
        {
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1,
            // hidden: true
        },
        // {
        //     text: '仓库',
        //     dataIndex: 'warehouseName',
        //     flex: 1
        // },
        {
            text: '房间号',
            dataIndex: 'houseNo',
            flex: 1
        },
        {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            flex: 1
        },
        {
            text: '状态',
            dataIndex: 'stockStatus',
            flex: 1,
            renderer: function (value, record) {
                if (value == "normal") {
                    return "好件"
                }
                if (value == "bad") {
                    return "坏件"
                }
            }
        },
        {
            text: '发放数量',
            dataIndex: 'outCount',
            flex: 1
        },
        {
            text: '序列号',
            dataIndex: 'serialNumber',
            flex: 1
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer:function (value, record) {
                if (value == "notUnique") {
                    return "非唯一标识"
                }
                if (value == "unique") {
                    return "唯一标识"
                }
            }
        },
        {
            text: '内部二维码',
            dataIndex: 'qrCode',
            width:200
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyLookWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1100,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'stockOutStockApplyLookWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyLookDetailForm'
        },
        {
            xtype: 'stockOutStockApplyLookDetailGrid'
        },
        {
            xtype: 'stockOutStockApplyLookOutGrid'
            // hidden:true
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