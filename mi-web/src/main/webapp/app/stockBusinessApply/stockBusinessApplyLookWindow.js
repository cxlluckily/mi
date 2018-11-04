Ext.define('App.stockBusinessApply.stockBusinessApplyLookGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
    ],
    store: {},
    xtype: 'stockBusinessApplyLookGrid',
    fullscreen: true,
    height: 250,
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
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1
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
            hidden:true
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
            hidden:true
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
            text: '实际出库数量',
            dataIndex: 'alreadyOutCount',
            flex: 1
        },
        {
            text: '申请数量',
            dataIndex: 'applyCount',
            flex: 1
        }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplyLookInfoListGrid', {
    extend: 'Ext.grid.Panel',
    requires: [],
    store: {},
    xtype: 'stockBusinessApplyLookInfoListGrid',
    fullscreen: true,
    hideHeaders: true,
    // height: 250,
    style: {
        border: "1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'user',
            dataIndex: 'user',
            flex: 1,
            hidden: true
        },
        {
            text: '时间',
            dataIndex: 'time',
            width: 200
        },
        {
            text: '操作',
            dataIndex: 'type',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                var value = '';
                if (record.data.type == 'toBeReview') {
                    value = '待审核（由 ' + record.data.user + ' 申请）'
                }
                if (record.data.type == 'reviewPass') {
                    value = '审核通过（由 ' + record.data.user + ' 审核通过）'
                }
                if (record.data.type == 'reviewNoPass') {
                    value = '审核不通过（由 ' + record.data.user + ' 审核不通过）'
                }
                if (record.data.type == 'toBeIn') {
                    value = '待入库（由 ' + record.data.user + ' 出库）'
                }
                if (record.data.type == 'over') {
                    value = '完成（由 ' + record.data.user + ' 入库）'
                }
                return value;
            }
        },
        {
            text: '备注及审核意见',
            dataIndex: 'remark',
            width: 300
        }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplyLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockBusinessApplyLookForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '0 10 5 10',
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
                                xclass: 'App.common.stockWarehouse'
                            },
                            queryMode: 'local',
                            name: 'outWarehouseId',
                            editable: false,
                            displayField: 'name',
                            valueField: 'id',
                            allowBlank: false,
                            blankText: '请选择出库仓库!',
                            padding:'5 10 0 10',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');

                                    //给所有下拉列表赋值开始
                                    var inWarehouseId = form.getForm().getValues().inWarehouseId[0];
                                    var outWarehouseId = form.getForm().getValues().outWarehouseId[0];
                                    var combos = form.items.items[0].items.items;
                                    Ext.each(combos,function (item,index) {
                                        if(item.items.items[0]){
                                            if(item.items.items[0].name == 'inWarehouseId'){
                                                item.items.items[0].setValue(inWarehouseId)
                                            }else if(item.items.items[0].name == 'outWarehouseId'){
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
                                render : function(component) {//渲染
                                    component.getStore().on("load", function(s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if(!value[component.name]){
                                            component.setValue(r[0].get(component.valueField));
                                        }
                                    });
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
                            hidden:true,
                            blankText: '请选择入库仓库!',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'use') {
                                        component.doDestroy();
                                    }
                                    this.store.load();
                                },
                                render:function (component) {
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
                                xclass: 'App.common.stockWarehouse'
                            },
                            queryMode: 'local',
                            name: 'inWarehouseId',
                            editable: false,
                            displayField: 'name',
                            valueField: 'id',
                            // multiSelect: true,
                            allowBlank: false,
                            blankText: '请选择入库仓库!',
                            padding:'5 10 0 10',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'transfer') {
                                        component.doDestroy();
                                    }
                                    this.store.load();
                                },
                                render : function(component) {//渲染
                                    component.getStore().on("load", function(s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if(!value[component.name]){
                                            component.setValue(r[0].get(component.valueField));
                                        }
                                    });
                                },
                                change: function (tf, nval, oval, opts) {
                                    var form = tf.up('form');
                                    var combo = form.down('combo[name="outWarehouseId"]');
                                    combo.store.load();
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
                            padding:'5 10 0 10',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'transfer') {
                                        component.doDestroy();
                                    }
                                },
                                render : function(component) {//渲染
                                    component.getStore().on("load", function(s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if(!value[component.name]){
                                            component.setValue(r[0].get(component.valueField));
                                        }
                                    });
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
                            padding:'5 10 0 10',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'return') {
                                        component.doDestroy();
                                    }
                                    this.store.load();
                                },
                                render : function(component) {//渲染
                                    component.getStore().on("load", function(s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if(!value[component.name]){
                                            component.setValue(r[0].get(component.valueField));
                                        }
                                    });
                                },
                                change: function (tf, nval, oval, opts) {
                                    var form = tf.up('form');
                                    var combo = form.down('combo[name="inWarehouseId"]');
                                    combo.store.load();
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
                            allowBlank: false,
                            blankText: '请选择入库仓库!',
                            padding:'5 10 0 10',
                            //设置为只读
                            readOnly : true,
                            hideTrigger : true,
                            disable:true,

                            listeners: {
                                beforerender: function (component) {
                                    var form = component.up('form');
                                    var applyType = form.getForm().getValues().applyType;
                                    if (applyType !== 'return') {
                                        component.doDestroy();
                                    }
                                },
                                render : function(component) {//渲染
                                    component.getStore().on("load", function(s, r, o) {
                                        var form = component.up('form');
                                        var value = form.getForm().getValues();
                                        if(!value[component.name]){
                                            component.setValue(r[0].get(component.valueField));
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
                    xtype: 'displayfield',
                    name: 'applyNo',
                    fieldLabel: '申请单号'
                },
                // {
                //     xtype: 'displayfield',
                //     name: 'applyRemark',
                //     fieldLabel: '备注',
                //     editable: false
                // },
                {
                    xtype: 'displayfield',
                    name: 'applyStatus',
                    fieldLabel: '状态',
                    renderer: function (value, record) {
                        if (value == "toBeReview") {
                            return "待审核"
                        } else if (value == "reviewPass") {
                            return "审核通过"
                        } else if (value == "reviewNoPass") {
                            return "审核不通过"
                        } else if (value == "over") {
                            return "已完成"
                        } else if (value == "toBeOut") {
                            return "待出库"
                        } else if (value == "toBeIn") {
                            return "待入库"
                        }
                    }
                },
                // {
                //     xtype: 'displayfield',
                //     name: 'auditRemark',
                //     fieldLabel: '审核意见',
                //     editable: false
                // },
                {
                    xtype: 'textfield',
                    name: 'applyType',
                    fieldLabel: 'applyType',
                    editable: false,
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
                    name: 'inWarehouseId',
                    fieldLabel: 'inWarehouseId',
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
        },
        // {
        //     xtype: 'container',
        //     layout: 'column',
        //     columnWidth: 1,
        //     items: [
        //         {
        //             xtype: 'stockBusinessApplyLookInfoListGrid',
        //             // name: 'applyUser',
        //             // fieldLabel: '申请人'
        //         }
        //     ]
        // },
        // {
        //     xtype: 'container',
        //     layout: 'column',
        //     columnWidth: .5,
        //     items: [
        //         {
        //             xtype: 'displayfield',
        //             name: 'applyUser',
        //             fieldLabel: '申请人'
        //         }
        //     ]
        // },
        // {
        //     xtype: 'container',
        //     layout: 'column',
        //     columnWidth: .5,
        //     items: [
        //         {
        //             xtype: 'displayfield',
        //             name: 'auditUser',
        //             fieldLabel: '审核人'
        //         },
        //     ]
        // },
        // {
        //     xtype: 'container',
        //     layout: 'column',
        //     columnWidth: .5,
        //     items: [
        //         {
        //             xtype: 'displayfield',
        //             name: 'outUser',
        //             fieldLabel: '出库人'
        //         }
        //     ]
        // },
        // {
        //     xtype: 'container',
        //     layout: 'column',
        //     columnWidth: .5,
        //     items: [
        //         {
        //             xtype: 'displayfield',
        //             name: 'inStockUser',
        //             fieldLabel: '入库人'
        //         }
        //     ]
        // }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplyLookWindow', {
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
    xtype: 'stockBusinessApplyLookWindow',
    controller: 'stockBusinessApplyController',
    items: [
        {
            xtype: 'stockBusinessApplyLookForm'
        },
        {
            xtype: 'stockBusinessApplyLookInfoListGrid'
        },
        {
            xtype: 'stockBusinessApplyLookGrid'
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