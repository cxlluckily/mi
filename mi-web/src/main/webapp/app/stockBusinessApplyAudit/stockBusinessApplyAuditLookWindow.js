Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditLookGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
    ],
    store: {},
    xtype: 'stockBusinessApplyAuditLookGrid',
    fullscreen: true,
    height: 170,
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
            width: 150
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            width: 150
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
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
            text: '申请数量',
            dataIndex: 'applyCount',
            flex: 1
        }
    ]
});

Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockBusinessApplyAuditLookForm',
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
                            padding:'5 10 0 10',
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
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items:[
                {
                    xtype: 'displayfield',
                    name: 'applyNo',
                    fieldLabel: '申请单号'
                },
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .5,
            items:[
                {
                    xtype: 'displayfield',
                    name: 'applyUser',
                    fieldLabel: '申请人'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .5,
            items:[
                {
                    xtype: 'displayfield',
                    name: 'applyTime',
                    fieldLabel: '申请时间',
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
                                return Y + M + D + h + m + s;
                            }

                            return timetrans(value)
                        }
                        return '';
                    }
                },
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'applyRemark',
                    fieldLabel: '备注',
                    editable: false
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'auditUser',
                    fieldLabel: '审核人'
                },
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .5,
            items: [

                {
                    xtype: 'displayfield',
                    name: 'auditTime',
                    fieldLabel: '审核时间',
                    editable: false,
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
                                return Y + M + D + h + m + s;
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
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'applyStatus',
                            fieldLabel: '状态',
                            editable: false,
                            renderer:function (value) {
                                if(value == 'toBeReview'){
                                    return '待审核'
                                }
                                if(value == 'reviewPass'){
                                    return '审核通过'
                                }
                                if(value == 'reviewNoPass'){
                                    return '审核不通过'
                                }
                                if(value == 'toBeOut'){
                                    return '待领取'
                                }
                                if(value == 'toBeIn'){
                                    return '待归还'
                                }
                                if(value == 'over'){
                                    return '已完成'
                                }
                            }
                        },
                        {
                            xtype: 'displayfield',
                            name: 'auditRemark',
                            fieldLabel: '审核意见',
                            editable: false
                        },
                        {
                            xtype: 'textfield',
                            name: 'applyType',
                            fieldLabel: 'applyType',
                            editable: false,
                            hidden:true
                        }
                    ]
                }
        
            ]
        },
        {
            xtype: 'container',//隐藏域
            layout: 'column',
            hidden: true,
            items: [
                {
                    xtype: 'textfield',
                    name: 'applyId',
                    fieldLabel: 'applyId'
                },
                {
                    xtype: 'textfield',
                    name: 'applyNo',
                    fieldLabel: 'applyNo'
                },
                {
                    xtype: 'textfield',
                    name: 'applyUserId',
                    fieldLabel: 'applyUserId'
                },
                {
                    xtype: 'textfield',
                    name: 'loginOperationSubjectId',
                    fieldLabel: 'loginOperationSubjectId'
                },
                {
                    xtype: 'textfield',
                    name: 'operationUserId',
                    fieldLabel: 'operationUserId'
                },
                {
                    xtype: 'textfield',
                    name: 'operationUserName',
                    fieldLabel: 'operationUserName'
                },
                {
                    xtype: 'textfield',
                    name: 'userLoginInfo',
                    fieldLabel: 'userLoginInfo'
                }
            ]
        }
    ]
});

Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditLookWindow', {
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
    xtype: 'stockBusinessApplyAuditLookWindow',
    controller: 'stockBusinessApplyAuditController',
    items: [
        {
            xtype: 'stockBusinessApplyAuditLookForm'
        },
        {
            xtype: 'stockBusinessApplyAuditLookGrid'
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