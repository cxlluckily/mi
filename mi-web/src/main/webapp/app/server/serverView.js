Ext.define('App.server.serverView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.server.serverStore',
        'App.server.serverController'
    ],
    store: {
        xclass: 'App.server.serverStore',
        autoLoad:false,
        data:[
            {
                serverName:'服务器1',
                ipAddress:'192.168.1.1',
                account:'13'
            }
        ]
    },
    controller: {
        xclass: 'App.server.serverController'
    },
    xtype: 'serverView',
    fullscreen: true,
    tbar: [
        {
            id: 'serverViewFormId',
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
                                    fieldLabel: '服务器名称',
                                    name: 'serverName'
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
                                    fieldLabel: 'IP地址',
                                    name: 'ipAddress'
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
                                    fieldLabel: '设备唯一编码',
                                    name: 'deviceuId'
                                }
                            ]
                        },
                        {
                            columnWidth: 1,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus'
                                },
                                {
                                    margin: 10,
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
        },
        {
            text: 'serverId',
            dataIndex: 'serverId',
            flex: 1,
            hidden: true
        },
        {
            text: '服务器名称',
            dataIndex: 'serverName',
            flex: 1
        },
        {
            text: 'IP地址',
            dataIndex: 'ipAddress',
            flex: 1
        },
        {
            text: '绑定设备数量',
            dataIndex: 'account',
            flex: 1
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
            menuDisabled: true,
            align: 'center',
            // handler: 'onEditRowAction',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                bodyStyle: 'padding:0px 10px'
            },
            items: [
                {
                    tooltip: '操作',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn'
                }
            ]
        }
    ],
    // selModel: {
    //     selType: 'checkboxmodel',
    //     // mode: 'SINGLE',
    //     allowDeselect: true,
    //     listeners: {
    //         selectionchange: {
    //             fn: function (sel, selected, eOpts) {
    //                 // if (selected.length < 1) {
    //                 //     sel.view.ownerCt.down('button[action="execution"]').disable();
    //                 // }
    //                 // else {
    //                 //     sel.view.ownerCt.down('button[action="execution"]').enable();
    //                 // }
    //             }
    //         }
    //     }
    // }
});