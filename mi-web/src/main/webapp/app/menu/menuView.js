Ext.define('App.menu.menuView', {
    extend: 'Ext.tree.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.menu.menuController',
        'App.menu.menuAddOrEditWindow'
    ],
    controller: {
        xclass: 'App.menu.menuController'
    },
    xtype: 'menuView',
    id: 'menuView',
    fullscreen: true,
    reserveScrollbar: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    listeners: {
        render: function (combo) {//渲染
            var p = Ext.ComponentQuery.query('menuView')[0];
            p.getController().loadList(p);
        }
    },
    tbar: [
        {
            id: 'menuViewFormId',
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
                            hidden: true,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '功能节点名称',
                                    name: 'treeName'
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            hidden: true,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '权限代码',
                                    name: 'permissionCode'
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
                                    fieldLabel: '菜单类型',
                                    anchor: '90%',
                                    emptyText: '电脑端',
                                    value:'manage',
                                    store: {
                                        data: [
                                            // {
                                            //     id: "all",
                                            //     name: "全部"
                                            // }
                                            {
                                                id: "manage",
                                                name: "电脑端"
                                            },
                                            {
                                                id: "phone",
                                                name: "移动端"
                                            }

                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'treeType',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    listeners: {
                                        beforerender: function (combo) {
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        change:function(ant,valu){
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            hidden: true,
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
                            columnWidth: .25,
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
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    columns:
        [
            {
                xtype: 'treecolumn',
                text: '节点名称',
                dataIndex: 'treeName',
                width: 160,
                sortable: true
            }, {
            text: 'functionTreeId',
            dataIndex: 'functionTreeId',
            width: 250,
            sortable: true,
            hidden:true,
            align: 'center'
        }, {
            text: 'parentTreeId',
            dataIndex: 'parentTreeId',
            width: 250,
            sortable: true,
            hidden:true,
            align: 'center'
        }, {
            text: '路由路径',
            dataIndex: 'xclass',
            flex: 1,
            sortable: true,
            align: 'left'
        },
        //     {
        //     text: '前端页签ID',
        //     dataIndex: 'id',
        //     flex: 1,
        //     sortable: true
        // },
            {
            text: '权限',
            flex: 1,
            dataIndex: 'permissionCode',
            sortable: true
        }
        , {
            text: '图标',
            dataIndex: 'iconCls',
            width: 100,
            sortable: true
        }, {
            text: '排序位置',
            dataIndex: 'sort',
            width: 100,
            sortable: true
        }, {
            text: '类型',
            dataIndex: 'treeType',
            width: 100,
            sortable: true,
            renderer: function (value, record) {
                if (value == "manage")
                {
                    return "电脑端"
                }
                if (value == "phone")
                {
                    return "移动端"
                }
            }
        }, {
            text: '状态',
            dataIndex: 'status',
            width: 100,
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
            xtype: 'actioncolumn',
            text: '操作',
            width: 150,
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
                    tooltip: '修改',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn',
                }, {
                    tooltip: '新增同级',
                    iconCls: "fa-plus-circle",
                    flag: 'add',
                    handler: 'actionFn'
                }, {
                    tooltip: '新增下级',
                    iconCls: "fa-plus-square",
                    flag: 'addChild',
                    handler: 'actionFn'
                }
            ]
        }]
});