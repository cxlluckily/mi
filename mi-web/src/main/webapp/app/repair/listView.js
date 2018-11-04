Ext.define('App.repair.listView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.repair.repairStore',
        'App.repair.repairController',
        'App.repair.repairWindow'
    ],
    store: {
        xclass: 'App.repair.repairStore'
    },
    controller: {
        xclass: 'App.repair.repairController'
    },
    xtype: 'listView',
    fullscreen: true,
    tbar: [
        {
            id: 'repairViewFormId',
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
                    items: [
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '备件分类',
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
                                    name: 'partName',
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
                                    fieldLabel: '维修Id',
                                    name: 'cueCode'
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
                                    fieldLabel: '故障ID',
                                    name: 'breakdownKey',
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
                                    fieldLabel: '故障简称',
                                    name: 'breakAbbreviated'
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
                                    fieldLabel: '故障原因',
                                    name: 'reason'
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
                                    value: 'all',
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
                                    listeners: {
                                        beforerender: function (combo) {
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
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                }, {
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
                                    iconCls: 'fa-edit',
                                    disabled: true
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'reset',
                                    iconCls: 'fa-refresh',
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
            xtype: 'rownumberer',
            width: '10px',
            align: 'center'
        }, {
            text: 'repairInfoId',
            dataIndex: 'repairInfoId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '备件分类',
            dataIndex: 'categoryName',
            flex: 1,
            align: 'left'
        }
        , {
            text: '父级备件分类',
            dataIndex: 'parentCategoryName',
            flex: 1,
            align: 'left'
        }
        , {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1,
            align: 'left'
        },
        {
            text: '故障Id',
            dataIndex: 'breakdownKey',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '备件故障',
            dataIndex: 'breakdownInfoId',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '维修Id',
            dataIndex: 'cueCode',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '故障简称',
            dataIndex: 'breakAbbreviated',
            flex: 1,
            align: 'left'
        }, {
            text: '故障原因',
            dataIndex: 'reason',
            flex: 1,
            align: 'left'
        },
        {
            text: '维修描述',
            dataIndex: 'repairDescription',
            flex: 1,
            align: 'left'
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
            text: '创建人',
            dataIndex: 'createUser',
            flex: 1,
            align: 'left'
        }, {
            text: '创建时间',
            dataIndex: 'insertDate',
            flex: 1,
            align: 'left'
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length < 1) {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                    }
                    else if (selected.length == 1) {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                    }
                    else {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                    }
                }
            }
        }
    }
});
