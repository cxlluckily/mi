Ext.define('App.sysRole.sysRoleView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.sysRole.sysRoleStore',
        'App.sysRole.sysRoleController',
        'App.sysRole.sysRoleWindow',
        'App.sysRole.sysRoleLookWindow'
    ],
    store: {
        xclass: 'App.sysRole.sysRoleStore'
    },
    controller: {
        xclass: 'App.sysRole.sysRoleController'
    },
    xtype: 'sysRoleView',
    id: 'sysRoleView',
    fullscreen: true,
    tbar: [{
        xtype: 'form',
        id: 'sysRoleViewForm',
        width: 900,
        items: [
            {
                columnWidth: .69,
                layout: 'column',
                style: 'marginTop:15px',
                border: false,
                items: [{
                    xtype: 'textfield',
                    name: 'roleName',
                    width: 200,
                    fieldLabel: '角色名称',
                    labelAlign: 'right',
                    labelWidth: 80
                }, {
                    xtype: 'combo',
                    fieldLabel: '状态',
                    labelAlign: 'right',
                    name: 'status',
                    width: 200,
                    value: 'all',
                    editable:false,
                    emptyText: '全部',
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
                    displayField: 'name',
                    valueField: 'id'
                },
                    {
                        xtype: 'button',
                        text: '查  询',
                        maxWidth: 60,
                        margin: '0, 0, 0, 10',
                        handler: 'clickSearch'
                    }, {
                        xtype: 'button',
                        text: '重置',
                        maxWidth: 60,
                        margin: '0, 0, 0, 10',
                        handler: 'clickResets'
                    }
                ]
            },
            {
                columnWidth: .69,
                layout: 'column',
                border: false,
                items: [
                    {
                        margin: 10,
                        xtype: 'button',
                        text: '新增',
                        action: 'add',
                        iconCls: 'fa-plus-circle'
                    }, {
                        margin: 10,
                        xtype: 'button',
                        text: '修改',
                        action: 'update',
                        iconCls: 'fa-edit'
                    },
                    // {
                    //     margin: 10,
                    //     xtype: 'button',
                    //     text: '删除',
                    //     action: 'delete',
                    //     iconCls: 'fa-minus-circle'
                    // },
                    {
                        margin: 10,
                        xtype: 'button',
                        text: '查看',
                        action: 'look',
                        iconCls: 'fa-pencil'
                    }]
            }]
    }],
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
        text: '角色名称',
        dataIndex: 'roleName',
        flex: 1,
        align: 'left',
        sortable: true

    }, {
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
        }
    }, {
        text: '修改人',
        dataIndex: 'modifyUser',
        flex: 1,
        align: 'left',
        sortable: true

    }, {
        text: '修改时间',
        dataIndex: 'modifyTime',
        flex: 1,
        align: 'left',
        sortable: true,
        renderer: function (value) {
            if (value) {
                function timetrans(date) {
                    var date = new Date(date);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = ('0'+date.getDate()).slice(-2) + ' ';
                    h = ('0'+date.getHours()).slice(-2) + ':';
                    m = ('0'+date.getMinutes()).slice(-2) + ':';
                    s = ('0'+date.getSeconds()).slice(-2);
                    return Y + M + D;
                }
                return timetrans(value)
            }
            return '';
        }

    }],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length > 0) {
                        // sel.view.ownerCt.down('button[action="delete"]')
                        //     .enable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .enable();
                    } else {
                        // sel.view.ownerCt.down('button[action="delete"]')
                        //     .disable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .disable();
                    }
                }
            }
        }
    }
});