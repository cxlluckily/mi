Ext.define('App.stockInStock.stockInStockNewSelectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInStockNewSelectForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    style:{
        paddingBottom:'1px'
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
                            xtype: 'combo',
                            fieldLabel: '备件类型',
                            emptyText: '--双击选择--',
                            queryMode: 'local',
                            name: 'sparePartTypeId',
                            editable: false,
                            displayField: 'categoryName',
                            valueField: 'sparePartTypeId',
                            listeners: {
                                expand: function (cb, e, opt) {
                                    var config = {
                                        title: this.fieldLabel,
                                        treeParams: {
                                            url: 'sparePartType/getSparePartTypeInfo.do',
                                            rootProperty: 'data',
                                            component: cb,
                                            params: {
                                                status: "start_using"
                                            }
                                        }
                                    };
                                    var w = Ext.create('App.common.selectWindow', config);
                                    w.show();
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
                            xtype: 'textfield',
                            name: 'searchContent',
                            fieldLabel: '名称'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search'
                        },
                    ]
                },
                {   columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items:[
                        {   xtype: 'button',
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

Ext.define('App.stockInStock.stockInStockNewSelectGridStore', {
    extend: 'App.common.commonStore',
    pageSize: 0,
    proxy: {
        url: ctx + 'baseData/getSparePartList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var view = Ext.getCmp(store.viewId);
            var formValues = view.up('window').down('form').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.stockInStock.stockInStockNewSelectGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass: 'App.stockInStock.stockInStockNewSelectGridStore'
    },
    height:260,
    xtype: 'stockInStockNewSelectGrid',
    fullscreen: true,
    style: {
        border: "1px solid #5fa2dd"
    },
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
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
            text: '备件类型',
            dataIndex: 'categoryName',
            width: 150
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            width: 150
        },
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            width: 150,
            hidden: true
            // formatter: 'this.formatHours'
        },
        {
            text: 'sparePartTypeId',
            dataIndex: 'sparePartTypeId',
            width: 150,
            hidden: true
            // formatter: 'this.formatHours'
        },
        {
            text: 'sparePartPid',
            dataIndex: 'sparePartPid',
            flex: 1,
            hidden: true
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
            text: '尺寸',
            dataIndex: 'size',
            flex: 1
        },
        {
            text: '材质',
            dataIndex: 'material',
            flex: 1,
            hidden:true
        },
        {
            text: '单位',
            dataIndex: 'units',
            flex: 1,
        },
        {
            text: '库存上限',
            dataIndex: 'upperLimit',
            flex: 1,
            hidden:true
        },
        {
            text: '库存下限',
            dataIndex: 'lowerLimit',
            flex: 1,
            hidden:true
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            hidden: true
        },
        {
            text: '状态',
            dataIndex: 'status',
            width: 100,
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using") {
                    return "可用"
                }
                if (value == "stop_using") {
                    return "不可用"
                }
            },
            hidden: true
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
                    flag: 'add',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

/*新增之后显示的表*/
Ext.define('App.stockInStock.stockInStockNewDetail', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockInStockNewDetail',
    fullscreen: true,
    height: 260,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'id',
            dataIndex: 'id',
            width: 150,
            hidden:true
        },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            width: 150
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            width: 150
        },
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            width: 150,
            hidden: true
        },
        {
            text: 'sparePartTypeId',
            dataIndex: 'sparePartTypeId',
            width: 150,
            hidden: true
        },
        {
            text: 'sparePartPid',
            dataIndex: 'sparePartPid',
            flex: 1,
            hidden: true
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
            text: '尺寸',
            dataIndex: 'size',
            flex: 1
        },
        {
            text: '材质',
            dataIndex: 'material',
            flex: 1,
            hidden:true
        },
        {
            text: '单位',
            dataIndex: 'units',
            flex: 1,
        },
        {
            text: '库存上限',
            dataIndex: 'upperLimit',
            flex: 1,
            hidden:true
        },
        {
            text: '库存下限',
            dataIndex: 'lowerLimit',
            flex: 1,
            hidden:true
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            hidden: true
        },
        {
            text: '状态',
            dataIndex: 'status',
            width: 100,
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using") {
                    return "可用"
                }
                if (value == "stop_using") {
                    return "不可用"
                }
            },
            hidden: true
        },
        {
            text: '<font color="red">*</font>入库数量',
            dataIndex: 'alreadyInCount',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'numberfield',
                    minValue:1,
                    name: 'alreadyInCount'
                },
                allowBlank: false
            }
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
            menuDisabled: true,
            align: 'center',
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

Ext.define('App.stockInStock.stockInStockSelectGridSelectModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'materiaCoding',
            type: 'string'
        },
        {
            name: 'specificationModel',
            type: 'string'
        },
        {
            name: 'remark',
            type: 'string'
        },
        {
            name: 'units',
            type: 'string'
        },
        {
            name: 'lowerLimit',
            type: 'number'
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
            name: 'modifyUser',
            type: 'string'
        },
        {
            name: 'sparePartId',
            type: 'number'
        },
        {
            name: 'modifyTime',
            type: 'number'
        },
        {
            name: 'size',
            type: 'string'
        },
        {
            name: 'material',
            type: 'string'
        },
        {
            name: 'createTime',
            type: 'number'
        },
        {
            name: 'sparePartPid',
            type: 'number'
        },
        {
            name: 'upperLimit',
            type: 'number'
        },
        {
            name: 'createUser',
            type: 'string'
        },
        {
            name: 'sparePartTypeId',
            type: 'number'
        },
        {
            name: 'brand',
            type: 'string'
        },
        {
            name: 'status',
            type: 'string'
        },
        {
            name: 'applyCount',
            type: 'string'
        }
    ]
});

Ext.define('App.stockInStock.stockInStockSelectGridSelectStore', {
    extend: 'Ext.data.Store',
    model: "App.stockInStock.stockInStockSelectGridSelectModel"
});


Ext.define('App.stockInStock.stockInStockNewSelectWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
    // height:600,
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
    xtype: 'stockInStockNewSelectWindow',
    controller: 'stockInStockController',
    items: [
        {
            xtype: 'stockInStockNewSelectForm'
        },
        {
            xtype: 'stockInStockNewSelectGrid'
        },
        {
            xtype: 'stockInStockNewDetail',
        },
        {
           xtype:'stockInStockNewDetailGrid',
            hidden:true
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'actionsave'
        },
        {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});