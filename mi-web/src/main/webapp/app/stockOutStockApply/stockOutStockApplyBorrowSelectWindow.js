Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockOutStockApplyBorrowSelectForm',
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
                            name: 'warehouseId',
                            fieldLabel: 'warehouseId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'searchContent',
                            fieldLabel: '备件名称'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '状态',
                            emptyText: '全部',
                            value:'all',
                            store: {
                                data: [
                                    {
                                        id: "all",
                                        name: "全部"
                                    },
                                    {
                                        id: "normal",
                                        name: "好件"
                                    },
                                    {
                                        id: "bad",
                                        name: "坏件"
                                    }
                                ]
                            },
                            queryMode: 'local',
                            name: 'status',
                            editable: false,
                            displayField: 'name',
                            valueField: 'id',
                            // multiSelect:true,
                            listeners: {
                                beforerender: function (combo) {//渲染
                                    var firstValue = combo.getStore().getData().items[0].id;
                                    combo.setValue(firstValue);
                                }
                            }
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            // margin: 10,
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            // margin: 10,
                            xtype: 'button',
                            text: '重置',
                            action: 'reset',
                            iconCls: 'fa-refresh'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectGridAllStore', {
    extend: 'App.common.commonStore',
    pageSize: 0,
    proxy: {
        url: ctx + 'baseData/getSparePartInWarehouse.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var view = Ext.getCmp(store.viewId);
            var formValues = view.up('window').down('form').getForm().getValues();
            // Ext.apply(formValues, {status:'normal'});
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectGridAll', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyBorrowSelectGridAllStore'
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyBorrowSelectGridAll',
    fullscreen: true,
    height: 200,
    style: {
        border: "1px solid #5fa2dd"
    },
    listeners: {
        beforerender: function () {
            this.store.viewId = this.id;
            this.store.load();
        }
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
            flex: 1,
            // hidden:true
        },
        {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            hidden:true
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
            text: '序列号',
            dataIndex: 'serialNumber',
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
            text: '内部二维码',
            dataIndex: 'qrCode',
            width:200
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
                    tooltip: '新增',
                    iconCls: "fa-plus",
                    flag: 'addBorrow',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectGridSelect', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    store:{
        data:[
        ]
    },
    xtype: 'stockOutStockApplyBorrowSelectGridSelect',
    fullscreen: true,
    height:200,
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
            flex: 1,
            // hidden:true
        },
        {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            hidden:true
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
            text: '<font color="red">*</font>发放数量',
            dataIndex: 'outCount',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'numberfield',
                    name: 'outCount'
                }
            }
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
            text: '内部二维码',
            dataIndex: 'qrCode',
            width:200
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
                    tooltip: '删除',
                    iconCls: "fa-trash",
                    flag: 'delete',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectGridSelectModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sparePartId',
            type: 'string'
        },
        {
            name: 'stockId',
            type: 'string'
        },
        {
            name: 'supplierName',
            type: 'string'
        },
        {
            name: 'serialNumber',
            type: 'string'
        },
        {
            name: 'specificationModel',
            type: 'string'
        },
        {
            name: 'inDate',
            type: 'string'
        },
        {
            name: 'materiaCoding',
            type: 'string'
        },
        {
            name: 'equipmentNO',
            type: 'string'
        },
        {
            name: 'categoryName',
            type: 'string'
        },
        {
            name: 'partName',
            type: 'string'
        },
        {
            name: 'account',
            type: 'number'
        },
        {
            name: 'qrCode',
            type: 'string'
        },
        {
            name: 'size',
            type: 'string'
        },
        {
            name: 'houseNo',
            type: 'string'
        },
        {
            name: 'shelfNumber',
            type: 'string'
        },
        {
            name: 'equipmentName',
            type: 'string'
        },
        {
            name: 'category',
            type: 'string'
        },
        {
            name: 'brand',
            type: 'string'
        },
        {
            name:'inventoryType',
            type:'string'
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectGridSelectStore', {
    extend: 'Ext.data.Store',
    model: "App.stockOutStockApply.stockOutStockApplyBorrowSelectGridSelectModel"
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowSelectWindow', {
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
    xtype: 'stockOutStockApplyBorrowSelectWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyBorrowSelectForm'
        },
        {
            xtype: 'stockOutStockApplyBorrowSelectGridAll'
        },
        {
            xtype: 'stockOutStockApplyBorrowSelectGridSelect'
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