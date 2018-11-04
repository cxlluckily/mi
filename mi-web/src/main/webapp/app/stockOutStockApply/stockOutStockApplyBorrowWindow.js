Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
    ],
    store: {},
    xtype: 'stockOutStockApplyBorrowGrid',
    fullscreen: true,
    height: 300,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    tbar: [{
        xtype: 'button',
        text: '选择备件',
        action: 'choose'
    }],
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
            flex: 1,
            hidden: true
        },
        {
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1,
            // hidden: true
        },
        {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            hidden: true
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
            text: '<font style="color: red">*</font>发放数量',
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
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                if (value == 'unique') {
                    return '唯一标识'
                } else if (value == 'notUnique') {
                    return '非唯一标识'
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

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockOutStockApplyBorrowForm',
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
                //领用
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '出库仓库',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.userStockWarehouseListStore'
                            },
                            queryMode: 'local',
                            name: 'outWarehouseId',
                            editable: false,
                            displayField: 'text',
                            valueField: 'id',
                            allowBlank: false,
                            blankText: '请选择出库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    this.store.load();
                                },
                                render: function (component) {//渲染
                                    component.getStore().on("load", function (s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if (!value[component.name]) {
                                            component.setValue(r[0].get(component.valueField));
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
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'textareafield',
                            name: 'remark',
                            fieldLabel: '备注',
                            maxLength: 200,
                            maxLengthText: '该输入项的最大长度是200个字'
                        },
                        {
                            xtype: 'textfield',
                            name: 'applyType',
                            fieldLabel: 'applyType',
                            editable: false,
                            hidden: true
                        }
                    ]
                },
                {
                    xtype: 'textfield',
                    name: 'applyId',
                    fieldLabel: 'applyId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyNo',
                    fieldLabel: 'applyNo',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyStatus',
                    fieldLabel: 'applyStatus',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyUser',
                    fieldLabel: 'applyUser',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyUserId',
                    fieldLabel: 'applyUserId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'loginOperationSubjectId',
                    fieldLabel: 'loginOperationSubjectId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'operationSubjectId',
                    fieldLabel: 'operationSubjectId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'operationUserId',
                    fieldLabel: 'operationUserId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'operationUserName',
                    fieldLabel: 'operationUserName',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'userKey',
                    fieldLabel: 'userKey',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'userLoginInfo',
                    fieldLabel: 'userLoginInfo',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'outOrderType',
                    fieldLabel: 'outOrderType',
                    hidden: true
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowWindow', {
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
    xtype: 'stockOutStockApplyBorrowWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyBorrowForm'
        },
        {
            xtype: 'stockOutStockApplyBorrowGrid'
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