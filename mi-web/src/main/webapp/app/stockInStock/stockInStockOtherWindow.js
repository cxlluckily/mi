Ext.define('App.stockInStock.stockInStockOtherDetailGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore'
    ],
    store: {},
    xtype: 'stockInStockOtherDetailGrid',
    fullscreen: true,
    height: 250,
    style: {
        border: "1px solid #5fa2dd"
    },
    listeners: {
        beforerender: function (combo) {
            //保存初始化备件数量
            var storeData = combo.getStore().getData();
            var rootData = [];
            Ext.each(storeData.items, function (item, index) {
                rootData.push(item.data);
            });
            var spareParts = {};
            Ext.each(rootData, function (item, index) {
                if (spareParts[item.sparePartId] == undefined) {
                    spareParts[item.sparePartId] = {
                        sparePartId: item.sparePartId,
                        partName: item.partName,
                        inStockAcount: item.inStockAcount,
                        inventoryType:item.inventoryType
                    }
                } else {
                    spareParts[item.sparePartId].inStockAcount += item.inStockAcount;
                }
            });
            combo.spareParts = spareParts;
        }
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'outStockApplyDetailId',
            dataIndex: 'outStockApplyDetailId',
            flex: 1,
            hidden: true
        },
        {
            text: '类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: 'sparePartTypeId',
            dataIndex: 'sparePartTypeId',
            flex: 1,
            hidden: true
        },
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
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
        // {
        //     text: '物资编码',
        //     dataIndex: 'materiaCoding',
        //     flex: 1
        // },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            width:270
        },
        {
            text: '待入库数量',
            dataIndex: 'inStockAcount',
            flex: 1
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            editor: {
                field: {
                    xtype: 'combo',
                    emptyText: '--请选择--',
                    store: {
                        data: [
                            {
                                id: "notUnique",
                                name: "非唯一标识"
                            },
                            {
                                id: "unique",
                                name: "唯一标识"
                            }
                        ]
                    },
                    queryMode: 'local',
                    name: 'inventoryType',
                    editable: false,
                    displayField: 'name',
                    valueField: 'id',
                    // multiSelect:true,
                    //设置为只读
                    readOnly: true,
                    hideTrigger: true,
                    disable: true,
                    listeners: {
                        beforerender: function (combo) {//渲染
                            var firstValue = combo.getStore().getData().items[0].id;
                            combo.setValue(firstValue);
                        },
                        // select: function (tf, nval, oval, opts) {
                        //     tf.up('grid').getController().clickSearch();
                        // }
                    }
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
                return returnValue;
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
            text: '货架编号',
            dataIndex: 'goodsShelvesId',
            flex: 1,
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
                listeners: {
                    beforerender: function (component) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = "";
                        component.store.warehouseId = formValues.inWarehouseId;
                        component.store.load();
                    },
                    change: function (component, newValue, oldValue, eOpts) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = component.rawValue;
                        component.store.warehouseId = formValues.inWarehouseId;
                        component.store.load();
                    },
                    expand: function (component, newValue, oldValue, eOpts) {
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = "";
                        component.store.warehouseId = formValues.inWarehouseId;
                        component.store.load();
                    },
                    blur: function (component, event, eOpts) {
                        if (typeof component.value == 'string') {
                            if (component.store.data.items[0]) {
                                var value = component.store.data.items[0].data[component.valueField];
                                component.setValue(value);
                            }
                        }
                    }
                }
            },
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                if (value === null) {
                    // this.editor = null;
                    // metaData.column.setEditor(false)
                }
                var comboData = metaData.column.getEditor().getStore().data;
                var returnValue = "";

                if (comboData.length != 0) {
                    Ext.each(comboData.items, function (item) {
                        if (item.data.goodsShelvesId == value) {
                            returnValue = item.data.shelfNumber;
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
            text: '<font color="red">*</font>入库数量',
            dataIndex: 'alreadyInCount',
            flex: 1,
            field: {
                xtype: 'numberfield',
                name: 'alreadyInCount'
            }
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

Ext.define('App.stockInStock.stockInStockOtherDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInStockOtherDetailForm',
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
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'outStockApplyNO',
                            fieldLabel: '关联单据号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'outUser',
                            fieldLabel: '出库人'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'outWarehouseName',
                            fieldLabel: '出库仓库'
                        },
                        {
                            xtype: 'textfield',
                            name: 'inStockApplyNo',
                            fieldLabel: 'inStockApplyNo',
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
                            name: 'inStockId',
                            fieldLabel: 'inStockId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'inStockType',
                            fieldLabel: 'inStockType',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'inStockStatus',
                            fieldLabel: 'inStockStatus',
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
                            name: 'applyId',
                            fieldLabel: 'applyId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'inWarehouseId',
                            fieldLabel: 'inWarehouseId',
                            hidden: true
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: "displayfield",
                            name: "outDate",
                            fieldLabel: "出库时间",
                            renderer: function (value) {
                                if (value) {
                                    function timetrans(date) {
                                        var date = new Date(date);
                                        Y = date.getFullYear() + '-';
                                        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                                        D = ('0' + date.getDate()).slice(-2) + ' ';
                                        h = ('0' + date.getHours()).slice(-2) + ':';
                                        m = ('0' + date.getMinutes()).slice(-2) + ':';
                                        s = ('0' + date.getSeconds()).slice(-2);
                                        return Y + M + D;
                                    }

                                    return timetrans(value)
                                }
                                return '';
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'inWarehouseName',
                            fieldLabel: '入库仓库'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: "datefield",
                            name: "inDate",
                            fieldLabel: "入库时间",
                            editable: false,
                            emptyText: "--请选择--",
                            format: "Y-m-d",//日期的格式
                            value: Ext.util.Format.date(Ext.Date.add(new Date()))
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'outRemark',
                            fieldLabel: '出库备注'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'textareafield',
                            name: 'remark',
                            fieldLabel: '入库备注',
                            maxLength:200
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockInStock.stockInStockOtherWindow', {
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
    xtype: 'stockInStockOtherWindow',
    controller: 'stockInStockController',
    items: [
        {
            xtype: 'stockInStockOtherDetailForm'
            // hidden:true
        },
        {
            xtype: 'stockInStockOtherDetailGrid'
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