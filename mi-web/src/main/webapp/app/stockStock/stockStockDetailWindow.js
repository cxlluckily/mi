Ext.define('App.stockStock.stockStockDetailGridStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'stockInfo/getPagerDetailInfo.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.ComponentQuery.query('stockStockDetailForm')[0].getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.stockStock.stockStockDetailGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass:'App.stockStock.stockStockDetailGridStore'
    },
    xtype: 'stockStockDetailGrid',
    fullscreen: true,
    height: 400,
    style:{
        border:"1px solid #5fa2dd"
    },
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
        }, {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex:1,
            align: 'left',
            hidden: true
        }, {
            text: '名称',
            dataIndex: 'partName',
            flex:1,
            align: 'left'
        }, {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex:1,
            align: 'left'
        }, {
            text: '供货商',
            dataIndex: 'supplierName',
            flex:1,
            align: 'left'
        }, {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex:1,
            align: 'left'
        }, {
            text: '数量',
            dataIndex: 'account',
            flex:1,
            align: 'left'
        },
        {
            text: '房间号',
            dataIndex: 'houseNo',
            flex:1,
            align: 'left'
        }, {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            flex:1,
            align: 'left'
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "notUnique")
                {
                    return "非唯一标识"
                }
                if (value == "unique")
                {
                    return "唯一标识"
                }
            }
        }
        , {
            text: '二维码',
            dataIndex: 'qrCode',
            flex:1,
            align: 'left'
        }
        ,
        {
            text: '备件状态',
            dataIndex: 'status',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "normal")
                {
                    return "好件"
                }
                if (value == "bad")
                {
                    return "坏件"
                }
            }
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 60,
            menuDisabled: true,
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
                    tooltip: '修改',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn'
                }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    }
});

Ext.define('App.stockStock.stockStockDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockStockDetailForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',//隐藏域
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: .25,
                    items:[
                        {
                            xtype: 'textfield',
                            name: 'supplierName',
                            fieldLabel: '供应商',
                        }
                    ]
                },
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: .25,
                    items:[
                        {
                            margin: 10,
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search'
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'container',//隐藏域
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    name: 'warehouseId',
                    fieldLabel: 'warehouseId',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'partName',
                    fieldLabel: 'partName',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'status',
                    fieldLabel: 'status',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'houseNo',
                    fieldLabel: 'houseNo',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'inventoryType',
                    fieldLabel: 'inventoryType',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'sparePartId',
                    fieldLabel: 'sparePartId',
                    hidden:true
                }
            ]
        }
    ]
});

Ext.define('App.stockStock.stockStockDetailWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1200,
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
    xtype: 'stockStockDetailWindow',
    controller: 'stockStockController',
    items: [
        {
            xtype: 'stockStockDetailForm'
        },
        {
            xtype: 'stockStockDetailGrid'
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