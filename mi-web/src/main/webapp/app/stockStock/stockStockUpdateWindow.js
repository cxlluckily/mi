Ext.define('App.stockStock.stockStockUpdateGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockStockUpdateGrid',
    fullscreen: true,
    height: 400,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    listeners: {
        beforerender: function (combo) {
            //保存初始化备件数量
            var storeData = combo.getStore().getData();
            var rootData = [];
            Ext.each(storeData.items, function (item, index) {
                rootData.push(item.data);
            });
            var spareParts = Ext.apply({}, rootData[0]);
            combo.spareParts = spareParts;
        }
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
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '名称',
            dataIndex: 'partName',
            flex: 1,
            align: 'left'
        }, {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                if (value == 'unique') {
                    return '唯一标识'
                }
                if (value == 'notUnique') {
                    return '非唯一标识'
                }
                return '';
            }
        },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            flex: 1,
        },
        {
            text: '设备编号',
            dataIndex: 'equipmentNO',
            flex: 1,
            editor: {
                field: {
                    xtype: 'textfield',
                    name: 'equipmentNO'
                }
            }
        },

        {
            text: '设备序列号',
            dataIndex: 'serialNumber',
            flex: 1,
            editor: {
                field: {
                    xtype: 'textfield',
                    name: 'serialNumber'
                }
            }
        },
        {
            text: '设备状态',
            dataIndex: 'status',
            flex: 1,
            editor: {
                field: {
                    xtype: 'combo',
                    emptyText: '--请选择--',
                    store: {
                        data: [
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
                    valueField: 'id'
                }
            },
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                var comboData = metaData.column.getEditor().getStore().data;
                var returnValue = "";
                if (comboData.length != 0) {
                    Ext.each(comboData.items, function (item) {
                        if (item.data.id == value) {
                            returnValue = item.data.name;
                        }
                    });
                }
                if (returnValue == "") {
                    returnValue = value;
                }
                return returnValue;
            }
        },
        {
            text: '供应商',
            dataIndex: 'supplierId',
            flex: 1,
            editor: {
                xtype: 'combo',
                triggerAction: 'all',
                name: 'supplierId',
                valueField: "id",
                displayField: "name",
                editable: false,
                emptyText: '--请选择--',
                store: {
                    xclass: 'App.common.stockSupplierListStore'
                }
            },
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                var comboData = metaData.column.getEditor().getStore().data;
                var returnValue = "";
                if (comboData.length != 0) {
                    Ext.each(comboData.items, function (item) {
                        if (item.data.id == value) {
                            returnValue = item.data.name;
                        }
                    });
                } else {
                    returnValue = record.data.supplierName;
                }
                if (returnValue == "") {
                    returnValue = value;
                }
                return returnValue;
            }
        },
        {
            text: '货架编号',
            dataIndex: 'goodsShelvesId',
            flex: 1,
            emptyText: '--请选择--',
            editor: {
                xtype: 'combo',
                emptyText: '--请选择--',
                store: {
                    xclass: 'App.common.getShelvesByContentStore'
                },
                queryMode: 'remote',
                name: 'goodsShelvesId',
                displayField: 'shelfNumber',
                valueField: 'goodsShelvesId',
                selectOnFocus: true,
                listeners: {
                    beforerender: function (component) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = "";
                        component.store.warehouseId = formValues.warehouseId;
                        component.store.load();
                    },
                    change: function (component, newValue, oldValue, eOpts) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = component.rawValue;
                        component.store.warehouseId = formValues.warehouseId;
                        component.store.load();
                    },
                    blur: function (component, event, eOpts) {
                        if (typeof component.value == 'string') {
                            if (component.store.data.items[0]) {
                                var value = component.store.data.items[0].data[component.valueField];
                                component.setValue(value);
                            }
                        }
                    },
                    expand:function (component) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = "";
                        component.store.warehouseId = formValues.warehouseId;
                        component.store.load();
                    }
                }
            },
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                var comboData = metaData.column.getEditor().getStore().data;
                var returnValue = "";
                if (comboData.length != 0) {
                    Ext.each(comboData.items, function (item) {
                        if (item.data.goodsShelvesId == value) {
                            returnValue = item.data.shelfNumber;
                        }
                    });
                } else {
                    returnValue = record.data.houseNo + ' ' + record.data.shelfNumber;
                }
                return returnValue;
            }
        },
        {
            text: '<font color="red">*</font>数量',
            dataIndex: 'account',
            flex: 1,
            editor: {
                field: {
                    xtype: 'numberfield',
                    name: 'account'
                }
            }
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
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
                    tooltip: '拆分',
                    iconCls: "fa-copy",
                    flag: 'copy',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.inventoryType !== 'unique') {
                            return 'fa-copy';
                        } else {
                            return 'x-hidden';
                        }
                    }
                },
                {
                    tooltip: '删除',
                    iconCls: "fa-trash",
                    flag: 'delete',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.aDelete) {
                            return 'fa-trash';
                        } else {
                            return 'x-hidden';
                        }
                    }
                }
            ]
        }
    ]
});

Ext.define('App.stockStock.stockStockUpdateForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockStockUpdateForm',
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
                    xtype: 'textfield',
                    name: 'warehouseId',
                    fieldLabel: 'warehouseId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'partName',
                    fieldLabel: 'partName',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'status',
                    fieldLabel: 'status',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'houseNo',
                    fieldLabel: 'houseNo',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'inventoryType',
                    fieldLabel: 'inventoryType',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'sparePartId',
                    fieldLabel: 'sparePartId',
                    hidden: true
                }
            ]
        }
    ]
});

Ext.define('App.stockStock.stockStockUpdateWindow', {
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
    xtype: 'stockStockUpdateWindow',
    controller: 'stockStockController',
    items: [
        {
            xtype: 'stockStockUpdateForm'
        },
        {
            xtype: 'stockStockUpdateGrid'
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