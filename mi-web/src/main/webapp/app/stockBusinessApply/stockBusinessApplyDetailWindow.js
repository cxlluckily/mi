Ext.define('App.stockBusinessApply.stockBusinessApplyDetailGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockBusinessApplyDetailGrid',
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
            text: 'operationSubjectId',
            dataIndex: 'operationSubjectId',
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
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex: 1
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex: 1,
            hidden: true
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
            hidden: true
        },
        {
            text: '单位',
            dataIndex: 'units',
            flex: 1
        },
        {
            text: '库存上限',
            dataIndex: 'upperLimit',
            flex: 1,
            hidden: true
        },
        {
            text: '库存下限',
            dataIndex: 'lowerLimit',
            flex: 1,
            hidden: true
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            hidden: true
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
            text: '<font color="red">*</font>申请数量',
            dataIndex: 'applyCount',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'numberfield',
                    name: 'applyCount'
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


Ext.define('App.stockBusinessApply.stockBusinessApplyDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockBusinessApplyDetailForm',
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
                                    var viewAction = component.up('window').viewAction;
                                    if(viewAction == 'update'){
                                        component.readOnly = true;
                                        component.hideTrigger = true;
                                        component.disable = true;
                                    }
                                    //给所有下拉列表赋值开始
                                    var inWarehouseId = form.getForm().getValues().inWarehouseId[0];
                                    var outWarehouseId = form.getForm().getValues().outWarehouseId[0];
                                    var combos = form.items.items[0].items.items;
                                    Ext.each(combos, function (item, index) {
                                        if (item.items.items[0]) {
                                            if (item.items.items[0].name == 'inWarehouseId') {
                                                item.items.items[0].setValue(inWarehouseId)
                                            } else if (item.items.items[0].name == 'outWarehouseId') {
                                                item.items.items[0].setValue(outWarehouseId)
                                            }
                                        }
                                    });
                                    //给所有下拉列表赋值结束

                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'use') {
                                        component.doDestroy();
                                    }
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
                                select: function (combo, record, eOpts) {
                                    combo.up('window').down('grid').getStore().setData([]);
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '入库仓库',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.stockWarehouse'
                            },
                            queryMode: 'local',
                            name: 'inWarehouseId',
                            editable: false,
                            displayField: 'name',
                            valueField: 'id',
                            // multiSelect: true,
                            hidden: true,
                            blankText: '请选择入库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'use') {
                                        component.doDestroy();
                                    }
                                    this.store.load();
                                },
                                render: function (component) {
                                },
                                select: function (combo, record, eOpts) {
                                    combo.up('window').down('grid').getStore().setData([]);
                                }
                            }
                        }
                    ]
                },

                //调拨
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '入库仓库',
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
                            allowBlank: false,
                            blankText: '请选择入库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var viewAction = component.up('window').viewAction;
                                    if(viewAction == 'update'){
                                        component.readOnly = true;
                                        component.hideTrigger = true;
                                        component.disable = true;
                                    }
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'transfer') {
                                        component.doDestroy();
                                    }
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
                                // change: function (tf, nval, oval, opts) {
                                //     var form = tf.up('form');
                                //     var combo = form.down('combo[name="outWarehouseId"]');
                                //     combo.store.load();
                                // },
                                // select: function (combo, record, eOpts) {
                                //     combo.up('window').down('grid').getStore().setData([]);
                                // }
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '出库仓库',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.stockWarehouse'
                            },
                            queryMode: 'local',
                            name: 'outWarehouseId',
                            editable: false,
                            displayField: 'name',
                            valueField: 'id',
                            // multiSelect: true,
                            allowBlank: false,
                            blankText: '请选择出库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var viewAction = component.up('window').viewAction;
                                    if(viewAction == 'update'){
                                        component.readOnly = true;
                                        component.hideTrigger = true;
                                        component.disable = true;
                                    }
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'transfer') {
                                        component.doDestroy();
                                    }
                                    component.getStore().load();
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
                                    if (grid.getStore().getData().length == 0) {
                                        return;
                                    }
                                    if (oldValue != null && newValue != component.oldValue) {
                                        Ext.Msg.confirm('提示信息', '切换出库仓库，会清空备件信息，是否确认清空？', function (c) {
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
                },
                //返还
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
                            // multiSelect: true,
                            allowBlank: false,
                            blankText: '请选择出库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var viewAction = component.up('window').viewAction;
                                    if(viewAction == 'update'){
                                        component.readOnly = true;
                                        component.hideTrigger = true;
                                        component.disable = true;
                                    }
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'return') {
                                        component.doDestroy();
                                    }
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
                                change: function (component, newValue, oldValue, opts) {
                                    var form = component.up('form');
                                    var combo = form.down('combo[name="inWarehouseId"]');
                                    combo.store.proxy.extraParams.entityId = newValue;
                                    combo.store.load();
                                    var grid = component.up('window').down('grid');
                                    if (grid.getStore().getData().length == 0) {
                                        return;
                                    }
                                    if (oldValue != null && newValue != component.oldValue) {
                                        Ext.Msg.confirm('提示信息', '切换出库仓库，会清空备件信息，是否确认清空？', function (c) {
                                            if (c == 'yes') {
                                                component.oldValue = newValue;
                                                grid.getStore().setData([]);
                                            } else {
                                                component.oldValue = oldValue;
                                                component.setValue(oldValue);
                                            }
                                        })
                                    }
                                },
                                select: function (combo, record, eOpts) {
                                    var form = combo.up('form');
                                    form.getForm().setValues({inWarehouseId: ''});
                                    // combo.up('window').down('grid').getStore().setData([]);
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '入库仓库',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.stockWarehouseParentListStore'
                            },
                            queryMode: 'local',
                            name: 'inWarehouseId',
                            editable: false,
                            displayField: 'text',
                            valueField: 'id',
                            // multiSelect: true,
                            allowBlank: false,
                            blankText: '请选择入库仓库!',
                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var viewAction = component.up('window').viewAction;
                                    if(viewAction == 'update'){
                                        component.readOnly = true;
                                        component.hideTrigger = true;
                                        component.disable = true;
                                    }
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'return') {
                                        component.doDestroy();
                                    }
                                },
                                render: function (component) {//渲染
                                    component.getStore().on("load", function (s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if (r.length > 0) {
                                            if (!value[component.name]) {
                                                component.setValue(r[0][component.valueField]);
                                            }
                                        }
                                    });
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
                            name: 'applyRemark',
                            fieldLabel: '备注',
                            maxLength: 200,
                            maxLengthText: '最多可输入200个字'

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
                }
            ]
        }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplyDetailWindow', {
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
    xtype: 'stockBusinessApplyDetailWindow',
    controller: 'stockBusinessApplyController',
    items: [
        {
            xtype: 'stockBusinessApplyDetailForm'
        },
        {
            xtype: 'stockBusinessApplyDetailGrid'
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