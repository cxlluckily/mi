Ext.define('App.superSys.superUserView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        // 'App.common.selectWindow',
        // 'App.common.comboBoxTree',
        'App.superSys.superUserStore',
        'App.superSys.superUserController',
        'App.superSys.superUserWindow'
    ],
    store: {
        xclass: 'App.superSys.superUserStore'
    },
    controller: {
        xclass: 'App.superSys.superUserController'
    },
    xtype: 'superUserView',
    id: 'superUserView',
    fullscreen: true,
    tbar: [
        {
            id: 'superUserViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 1000,
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
                                    fieldLabel: '用户名',
                                    name: 'userName'
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
                                    fieldLabel: '姓名',
                                    name: 'realName'
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
                                    fieldLabel: '电话',
                                    name: 'phone'
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
                                }, {
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
                                    action: 'add',
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
                                    text: '初始化密码',
                                    action: 'init',
                                    iconCls: 'fa-refresh',
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
            text: 'adminId',
            dataIndex: 'adminId',
            flex:1,
            align: 'left',
            hidden: true
        }, {
            text: '用户名',
            dataIndex: 'userName',
            flex:1,
            align: 'left'
        }, {
            text: '姓名',
            dataIndex: 'realName',
            flex:1,
            align: 'left'
        }, {
            text: '手机号',
            dataIndex: 'phone',
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
                        sel.view.ownerCt.down('button[action="init"]').disable();
                    }
                    else if (selected.length == 1)
                    {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                        sel.view.ownerCt.down('button[action="init"]').enable();
                    }
                    else
                    {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="init"]').enable();
                    }
                }
            }
        }
    }
});