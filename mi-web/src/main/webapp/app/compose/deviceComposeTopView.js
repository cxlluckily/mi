Ext.define('App.compose.deviceComposeTopView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.compose.deviceComposeStore',
        'App.compose.deviceComposeViewWindow',
        'App.compose.deviceComposeController',
        'App.compose.deviceComposeWindow'
    ],
    store: {
        xclass: 'App.compose.deviceComposeStore'
    },
    controller: {
        xclass: 'App.compose.deviceComposeController'
    },
    xtype: 'deviceComposeTopView',
    id: 'deviceComposeTopView',
    fullscreen: true,
    tbar: [
        {
            id: 'deviceComposeTopViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 1100,
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
                    // title: '作业跟踪',
                    items: [
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '备件类型',
                                    name: 'categoryName'
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
                                    fieldLabel: '备件名称',
                                    name: 'partName'
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
                                    fieldLabel: '设备名称',
                                    name: 'name'
                                }
                            ]
                        },
                        
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '状态',
                                    anchor: '90%',
                                    emptyText: '全部',
                                    value:'all',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "start_using",
                                                name: "可用"
                                            },
                                            {
                                                id: "stop_using",
                                                name: "不可用"
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
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .7,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },  {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'addFirst',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '删除',
                                    action: 'delete',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
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
            text: 'deviceComposeId',
            dataIndex: 'deviceComposeId',
            flex:1,
            align: 'left',
            hidden: true
        }, {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex:1,
            align: 'left'
        }, {
            text: '备件名称',
            dataIndex: 'partName',
            flex:1,
            align: 'left'
        }, {
            text: '名称',
            dataIndex: 'name',
            flex:1,
            align: 'left'
        }
        , {
            text: '品牌',
            dataIndex: 'brand',
            flex:1,
            align: 'left'
        }, {
            text: '型号',
            dataIndex: 'composeModel',
            flex:1,
            align: 'left'
        }, {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using")
                {
                    return "可用"
                }
                if (value == "stop_using")
                {
                    return "不可用"
                }
            }
        }, {
            text: '创建人',
            dataIndex: 'createUser',
            flex:1,
            align: 'left'
        }, {
            text: '创建时间',
            dataIndex: 'insertDate',
            flex:1,
            align: 'left'
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length < 1)
                    {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="delete"]').disable();
    
                    }
                    else if (selected.length == 1)
                    {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                        sel.view.ownerCt.down('button[action="delete"]').enable();
                    }
                    else
                    {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="delete"]').disable();
                    }
                }
            }
        }
    }
});