// Ext.define('App.dataDict.dataDictSearchForm', {
//     extend: 'Ext.form.Panel',
//     xtype: 'dataDictSearchForm',
//     fullScreen: true,
//     layout: 'form',
//     buttonAlign: 'center',
//     controller: {
//         xclass: 'App.dataDict.dataDictController'
//     },
//     items: [{
//         xtype: 'fieldset',
//         columnWidth: 1,
//         layout: 'column',
//         fieldDefaults: {
//             labelAlign: 'right',
//             labelWidth: 70,
//             width: 150
//         },
//         items: [
//             {
//                 xtype: 'textfield',
//                 name: 'dataDictionaryId',
//                 hidden: true
//             }, {
//                 xtype: 'textfield',
//                 name: 'dataLabel',
//                 fieldLabel: '标签类型',
//                 columnWidth: 0.2
//             }, {
//                 xtype: 'textfield',
//                 name: 'code',
//                 fieldLabel: '编码',
//                 columnWidth: 0.2
//             }, {
//                 xtype: 'textfield',
//                 name: 'name',
//                 fieldLabel: '名称',
//                 columnWidth: 0.2
//             }, {
//                 xtype: 'combo',
//                 name: 'status',
//                 fieldLabel: '状态',
//                 emptyText: '全部',
//                 value: 'all',
//                 queryMode: 'local',
//                 displayField: 'name',
//                 valueField: 'code',
//                 editable: false,
//                 store: {
//                     fields: ['name', 'code'],
//                     data: [
//                         {"code": "all", "name": "全部"},
//                         {"code": "start_using", "name": "可用"},
//                         {"code": "stop_using", "name": "不可用"}
//                     ]
//                 },
//                 columnWidth: 0.2
//             }, {
//                 xtype: 'button',
//                 text: '查  询',
//                 maxWidth: 60,
//                 handler: 'clickSearch',
//                 margin: '0 0 0 10',
//                 columnWidth: 0.2
//             }, {
//                 xtype: 'button',
//                 text: '重置',
//                 maxWidth: 60,
//                 handler: 'clickResets',
//                 margin: '0 0 0 10',
//                 columnWidth: 0.2
//             }]
//     }]
// });

Ext.define('App.dataDict.dataDictView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.dataDict.dataDictStore',
        'App.dataDict.dataDictController',
        'App.dataDict.dataDictWindow'
    ],
    store: {
        xclass: 'App.dataDict.dataDictStore'
    },
    controller: {
        xclass: 'App.dataDict.dataDictController'
    },
    // height: 470,
    xtype: 'dataDictView',
    fullscreen: true,
    columnLines: true,
    tbar: [
        {
            id: 'dataDictViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 900,
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    border: false,
                    style: {
                        backgroundColor: '#fff',
                        marginTop: '-1px',
                        marginBottom: '0'
                    },
                    items: [
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'dataDictionaryId',
                                    hidden: true
                                },
                                {
                                    xtype: 'textfield',
                                    name: 'dataLabel',
                                    fieldLabel: '标签类型'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'code',
                                    fieldLabel: '编码'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            labelWidth: 200,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'name',
                                    fieldLabel: '名称'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            labelWidth: 200,
                            items: [
                                {
                                    xtype: 'combo',
                                    name: 'status',
                                    fieldLabel: '状态',
                                    emptyText: '全部',
                                    value: 'all',
                                    queryMode: 'local',
                                    displayField: 'name',
                                    valueField: 'code',
                                    editable: false,
                                    store: {
                                        fields: ['name', 'code'],
                                        data: [
                                            {"code": "all", "name": "全部"},
                                            {"code": "start_using", "name": "可用"},
                                            {"code": "stop_using", "name": "不可用"}
                                        ]
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .66,
                            layout: 'form',
                            border: false,
                            labelWidth: 200,
                            items: [
                                {
                                    xtype: 'button',
                                    text: '查  询',
                                    handler: 'clickSearch',
                                    margin: '0 0 0 10',
                                    iconCls: 'fa-search'
                                }, {
                                    xtype: 'button',
                                    text: '重置',
                                    handler: 'clickResets',
                                    margin: '0 0 0 10',
                                    iconCls: 'fa-refresh'
                                }, {
                                    margin: '0 0 0 10',
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: '0 0 0 10',
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit'
                                }
                            ]
                        }

                    ]
                }
            ]
        },

        // {
        //     xtype: 'form',
        //     width: 900,
        //     items: [{
        //         columnWidth: .69,
        //         layout: 'column',
        //         border: false,
        //         items: [{
        //             margin: 10,
        //             xtype: 'button',
        //             text: '新增',
        //             action: 'add',
        //             iconCls: 'fa-plus-circle'
        //         }, {
        //             margin: 10,
        //             xtype: 'button',
        //             text: '修改',
        //             action: 'update',
        //             iconCls: 'fa-edit'
        //         }
        //             // , {
        //             // 	margin : 10,
        //             // 	xtype : 'button',
        //             // 	text : '删除',
        //             // 	action : 'delete',
        //             // 	iconCls : 'fa-minus-circle'
        //             // }
        //         ]
        //     }]
        // }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },
    // 数据列表
    columnLines: true,
    columns: [{
        text: '序号',
        xtype: 'rownumberer',// 多选框
        width: '10px',
        align: 'center'
    }, {
        text: 'id',
        dataIndex: 'dataDictionaryId',
        flex: 1,
        align: 'left',
        hidden: true
    }, {
        text: '标签',
        dataIndex: 'dataLabel',
        flex: 1,
        sortable: true
    }, {
        text: '类型编码',
        dataIndex: 'code',
        flex: 1,
        sortable: true
    }, {
        text: '类型名称',
        dataIndex: 'name',
        flex: 1,
        sortable: true
    }, {
        text: '状态',
        dataIndex: 'status',
        flex: 1,
        sortable: true,
        renderer: function (value, record) {
            if (value == "start_using") {
                return "可用"
            }
            if (value == "stop_using") {
                return "不可用"
            }
        }
    }, {
        text: '排序',
        dataIndex: 'sort',
        flex: 1,
        sortable: true
    }, {
        text: '描述',
        dataIndex: 'remark',
        flex: 1,
        sortable: true
    }, {
        text: '创建人',
        dataIndex: 'createUser',
        flex: 1,
        sortable: true
    }, {
        text: '创建时间',
        dataIndex: 'createTime',
        flex: 1,
        sortable: true,
        hidden: true
    },
        // {
        //     xtype: 'actioncolumn',
        //     text: '操作',
        //     width: 150,
        //     menuDisabled: true,
        //     align: 'center',
        //     handler: 'onEditRowAction',
        //     isDisabled: 'isRowEditDisabled',
        //     fieldDefaults: {
        //         style: {
        //             margin: "20px 10px"
        //         }
        //     },
        //     defaults: {
        //         margin: "0px 5px"
        //     },
        //     items: [{
        //         tooltip: '修改',
        //         iconCls: "fa-edit",
        //         flag: 'update',
        //         handler: 'actionFn',
        //     }
        //         // , {
        //         // 	tooltip : '删除',
        //         // 	iconCls : "fa-trash",
        //         // 	flag : 'delete',
        //         // 	handler: 'actionFn',
        //         // }
        //     ]
        // }
    ],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length > 0) {
                        // sel.view.ownerCt.down('button[action="delete"]')
                        // 		.enable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .enable();
                    } else {
                        // sel.view.ownerCt.down('button[action="delete"]')
                        // 		.disable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .disable();
                    }
                }
            }
        }
    }
});

// Ext.define('App.dataDict.dataDictView', {
//     extend: 'Ext.Panel',
//     requires: [
//         'App.dataDict.dataDictView',
//         // 'App.dataDict.dataDictSearchForm'
//     ],
//     xtype: 'dataDictView',
//     fullscreen: true,
//     items: [
//         // {xtype: 'dataDictSearchForm'},
//         {
//             xtype: 'dataDictView'
//         }
//     ]
// });