Ext.define('App.stockOutStockApply.stockOutStockApplyDetailGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store:{
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyDetailGrid',
    fullscreen: true,
    height:350,
    style:{
        border:"1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '类型',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden:true
        },
        {
            text: 'outStockApplyDetailId',
            dataIndex: 'outStockApplyDetailId',
            flex: 1,
            hidden:true
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
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
            menuDisabled: true,
            // tooltip: 'Edit task',
            align: 'center',
            // iconCls: 'tree-grid-edit-task',
            handler: 'onEditRowAction',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                margin: "0px 5px"
            },
            items: [
                {
                    tooltip: '选择',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockOutStockApplyDetailForm',
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
            columnWidth:.5,
            items: [
                {
                    xtype: 'textfield',
                    name: 'outStockApplyNO',
                    fieldLabel: '出库单号',
                    editable:false
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'textfield',
                    name: 'warehouseName',
                    fieldLabel: '出库仓库',
                    editable:false
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'textfield',
                    name: 'applyUser',
                    fieldLabel: '申请人',
                    editable:false
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'textfield',
                    name: 'auditUser',
                    fieldLabel: '审核人',
                    editable:false
                }
            ]
        }
        ,{
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText: '最多输入200个字'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    xtype: 'textfield',
                    name: 'outOrderType',
                    fieldLabel: 'outOrderType',
                    hidden:true
                },
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
                    name: 'partCount',
                    fieldLabel: 'partCount',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'outWarehouseId',
                    fieldLabel: 'outWarehouseId',
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
                }
            ]
        }
    ]
});


Ext.define('App.stockOutStockApply.stockOutStockApplyOutGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        // 'App.stockOutStockApply.stockOutStockApplyStore',
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    // store: {
    //     xclass: 'App.stockOutStockApply.stockOutStockApplyStore'
    // },
    store:{
        data:[

        ]
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyOutGrid',
    fullscreen: true,
    height:300,
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
            hidden:true
        },
        {
            text: 'stockId',
            dataIndex: 'stockId',
            flex: 1,
            hidden:true
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
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1
        },
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
            text: '可用数量',
            dataIndex: 'account',
            flex: 1
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
            flex: 1
        },
        // {
        //     xtype: 'actioncolumn',
        //     text: '操作',
        //     width: 60,
        //     menuDisabled: true,
        //     align: 'center',
        //     // iconCls: 'tree-grid-edit-task',
        //     handler: 'onEditRowAction',
        //     isDisabled: 'isRowEditDisabled',
        //     fieldDefaults: {
        //         style: {
        //             margin: "20px 10px"
        //         }
        //     },
        //     defaults: {
        //         margin: "0px 5px"
        //     },
        //     items: [
        //         {
        //             tooltip: '删除',
        //             iconCls: "fa-trash",
        //             flag: 'delete',
        //             handler: 'actionFn'
        //         }
        //     ]
        // }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyDetailWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
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
    xtype: 'stockOutStockApplyDetailWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyDetailForm'
        },
        {
            xtype: 'stockOutStockApplyDetailGrid'
        },
        {
            xtype: 'stockOutStockApplyOutGrid',
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