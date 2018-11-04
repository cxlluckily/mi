Ext.define('App.stockInStock.stockInStockNewDetailGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockInStockNewDetailGrid',
    fullscreen: true,
    height: 400,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    tbar: [{
        xtype: 'button',
        text: '新增备件',
        action: 'add'
    }],
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '防止重复字段',
            dataIndex: 'repeatNumber',
            flex: 1,
            hidden: true
        },
        {
            text: '类型',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: 'outStockApplyDetailId',
            dataIndex: 'outStockApplyDetailId',
            flex: 1,
            hidden: true
        },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1,
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
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex: 1
        },

        {
            text: '设备状态',
            dataIndex: 'status',
            flex: 1,
            editor:{
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
                }
                if(returnValue == ""){
                    returnValue = value;
                }
                return returnValue;
            }
        },
        {
            text: '货架编号',
            dataIndex: 'goodsShelvesId',
            flex:1,
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
                    beforerender:function(component){
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
                    focus : function(component){
                        component.expand();
                    },
                    expand:function(component){
                        var formValues = component.up('window').down('form').getValues();
                        component.store.searchContent = component.rawValue;
                        component.store.warehouseId = formValues.inWarehouseId;
                        component.store.load();
                    },
                    blur: function (component, event, eOpts) {
                        if (typeof component.value == 'string') {
                            if(component.store.data.items[0]){
                                var value = component.store.data.items[0].data[component.valueField];
                                component.setValue(value);
                            }
                        }
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
                }
                if(returnValue == ""){
                    returnValue = value;
                }
                return returnValue;
            }
        },
        {
            text: '单价',
            dataIndex: 'price',
            flex: 1,
            editor:{
                field: {
                    xtype: 'numberfield',
                    minValue:0,
                    name: 'price'
                }
            }
        },
        {
            text: '<font color="red">*</font>库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            editor:{
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
                    listeners: {
                        // beforerender: function (combo) {//渲染
                        //     var firstValue = combo.getStore().getData().items[0].id;
                        //     combo.setValue(firstValue);
                        // },
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
            text: '<font color="red">*</font>入库数量',
            dataIndex: 'alreadyInCount',
            flex: 1,
            editor:{
                field: {
                    xtype: 'numberfield',
                    minValue:1,
                    name: 'alreadyInCount'
                }
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
                    tooltip: '删除',
                    iconCls: "fa-trash",
                    flag: 'delete',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.stockInStock.stockInStockNewDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInStockNewDetailForm',
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
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '仓库',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.userStockWarehouseListStore'
                            },
                            queryMode: 'local',
                            name: 'inWarehouseId',
                            editable: false,
                            displayField: 'text',
                            valueField: 'id',
                            // multiSelect: true,
                            listeners: {
                                beforerender: function (component) {
                                    component.store.load();
                                },
                                render: function (combo) {//渲染
                                    combo.getStore().on("load", function (s, r, o) {
                                        if(r[0]!=undefined){
                                            combo.setValue(r[0].get(combo.valueField));
                                        }
                                    });
                                },
                                change: function (component, newValue, oldValue, eOpts) {
                                    var grid = component.up('window').down('grid');
                                    if(grid.getStore().getData().length == 0){
                                        return;
                                    }
                                    if(oldValue!=null&&newValue!=component.oldValue){
                                        Ext.Msg.confirm('提示信息', '切换仓库，会清空备件信息，是否确认清空？', function (c) {
                                            if (c == 'yes') {
                                                component.oldValue = newValue;
                                                var grid = component.up('window').down('grid');
                                                grid.getStore().setData([]);
                                            } else {
                                                component.oldValue = oldValue;
                                                component.setValue(oldValue);
                                            }
                                        })
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            name: 'inStockApplyNo',
                            fieldLabel: 'inStockApplyNo',
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
                            name: 'applyId',
                            fieldLabel: 'applyId',
                            hidden: true
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
                    xtype: 'textareafield',
                    columnWidth: 1,
                    name: 'remark',
                    fieldLabel: '备注',
                    maxLength:200
                }
            ]
        }
    ]
});

Ext.define('App.stockInStock.stockInStockNewWindow', {
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
    xtype: 'stockInStockNewWindow',
    controller: 'stockInStockController',
    items: [
        {
            xtype: 'stockInStockNewDetailForm'
            // hidden:true
        },
        {
            xtype: 'stockInStockNewDetailGrid'
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