Ext.define('App.stockBusinessApply.stockBusinessApplySelectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockBusinessApplySelectForm',
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
                            fieldLabel: '备件名称'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    hidden:true,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'applyType',
                            fieldLabel: '申请单类型'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    hidden:true,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'outWarehouseId',
                            fieldLabel: 'outWarehouseId'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    hidden:true,
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
                                    var applyType = combo.up('form').getForm().getValues().applyType;
                                    if(applyType=='return'){
                                        combo.setValue('bad');
                                    }else{
                                        combo.setValue('normal');
                                    }
                                    // console.log(applyType);
                                    // var firstValue = combo.getStore().getData().items[0].id;
                                    // combo.setValue(firstValue);
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
                {   columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items:[
                        {   xtype: 'button',
                            text: '重置',
                            action: 'reset',
                            iconCls: 'fa-refresh'
                        },
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplySelectGridAllStore', {
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

Ext.define('App.stockBusinessApply.stockBusinessApplySelectGridAll', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        // 'App.stockBusinessApply.stockBusinessApplyController'
    ],
    store: {
        xclass: 'App.stockBusinessApply.stockBusinessApplySelectGridAllStore'
    },
    // controller: {
    //     xclass: 'App.stockBusinessApply.stockBusinessApplyController'
    // },
    xtype: 'stockBusinessApplySelectGridAll',
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

Ext.define('App.stockBusinessApply.stockBusinessApplySelectGridSelect', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.stockBusinessApply.stockBusinessApplyController'
    ],
    // controller: {
    //     xclass: 'App.stockBusinessApply.stockBusinessApplyController'
    // },
    store: {
        data: []
    },
    xtype: 'stockBusinessApplySelectGridSelect',
    fullscreen: true,
    height: 200,
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
                    minValue:1,
                    name: 'applyCount'
                },
                allowBlank: false
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
                    tooltip: '删除',
                    iconCls: "fa-trash",
                    flag: 'delete',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.stockBusinessApply.stockBusinessApplySelectGridSelectModel', {
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

Ext.define('App.stockBusinessApply.stockBusinessApplySelectGridSelectStore', {
    extend: 'Ext.data.Store',
    model: "App.stockBusinessApply.stockBusinessApplySelectGridSelectModel"
});

Ext.define('App.stockBusinessApply.stockBusinessApplySelectWindow', {
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
    xtype: 'stockBusinessApplySelectWindow',
    controller: 'stockBusinessApplyController',
    items: [
        {
            xtype: 'stockBusinessApplySelectForm'
        },
        {
            xtype: 'stockBusinessApplySelectGridAll'
        },
        {
            xtype: 'stockBusinessApplySelectGridSelect',
            // hidden:true
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        },{
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});