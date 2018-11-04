Ext.define('App.supplier.supplierView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.supplier.supplierStore',
        'App.supplier.supplierWindow',
        'App.supplier.supplierController'
    ],
    store: {
        xclass: 'App.supplier.supplierStore'
    },
    controller: {
        xclass: 'App.supplier.supplierController'
    },
    xtype: 'supplierView',
    fullscreen: true,
    autoScroll: true,
    tbar: [
        {
            id: 'supplierViewFormId',
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
                                    name: 'supplierName',
                                    fieldLabel: '供应商名称',
                                    labelAlign: 'right',
                                    blankText: '请输入供应商名称!'
                                }
                            ]
                        },
                        // {
                        //     columnWidth: .25,
                        //     layout: 'form',
                        //     border: false,
                        //     items: [
                        //         {
                        //             xtype: 'textfield',
                        //             name: 'brandName',
                        //             fieldLabel: '品牌名称',
                        //             labelAlign: 'right',
                        //             blankText: '请输入品牌名称!'
                        //         }
                        //     ]
                        // },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'contacts',
                                    fieldLabel: '联系人',
                                    labelAlign: 'right',
                                    blankText: '请输入联系人!'
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
                                    name: 'contactInfo',
                                    fieldLabel: '联系电话',
                                    labelAlign: 'right'
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
                                    value: 'all',
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
                                    queryMode: 'local',
                                    name: 'status',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    listeners: {
                                        beforerender: function (combo) {
                                            combo.setValue("all");
                                        },
                                        change: function (combo) {
                                            var ct = Ext.ComponentQuery.query('supplierView')[0].getController();
                                            ct.clickSearch();
                                        }
                                    }
                                }
                            ]
                        },


                        {
                            columnWidth: .7,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    margin: '0 10 0 0',
                                    xtype: 'button',
                                    text: '查  询',
                                    iconCls: 'fa-search',
                                    handler: 'clickSearch'
                                }, {
                                    margin: '0 10 0 0',
                                    xtype: 'button',
                                    text: '重置',
                                    iconCls: 'fa-refresh',
                                    handler: 'clickResets'
                                },
                                {
                                    margin: '0 10 0 0',
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: '0 10 0 0',
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }, {
                                    margin: '0 10 0 0',
                                    xtype: 'button',
                                    text: '删除',
                                    action: 'delete',
                                    iconCls: 'fa-minus-circle',
                                    disabled: true,
                                    hidden:true
                                }
                            ]
                        }
                    ]
                }]
        }],
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
            text: '供应商ID',
            dataIndex: 'supplierId',
            flex:1,
            align: 'left',
            hidden: true

        }, {
            text: '供应商名称',
            dataIndex: 'supplierName',
            flex:1,
            sortable: true
        }, {
            text: '拼音缩写',
            dataIndex: 'abbreviation',
            flex:1,
            sortable: true,
            hidden: true,
        },
        // {
        //     text: '品牌名称',
        //     dataIndex: 'brandName',
        //     flex:1,
        //     sortable: true
        // },
        {
            text: '联系人',
            dataIndex: 'contacts',
            flex:1,
            sortable: true
        }, {
            text: '联系方式',
            dataIndex: 'contactInfo',
            flex:1,
            sortable: true
        }, {
            text: '地址',
            dataIndex: 'address',
            flex:1,
            sortable: true
        }, {
            text: '电子邮件',
            dataIndex: 'email',
            flex:1,
            sortable: true
        }, {
            text: '状态',
            dataIndex: 'status',
            flex:1,
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
            text: '纳税人识别号',
            dataIndex: 'taxpayerNumber',
            flex:1,
            sortable: true,
            hidden: true

        }, {
            text: '开户银行',
            dataIndex: 'bankOfDeposit',
            flex:1,
            sortable: true,
            hidden: true
        }, {
            text: '银行账户',
            dataIndex: 'bankAccount',
            flex:1,
            sortable: true,
            hidden: true
        }, {
            text: '划款户名',
            dataIndex: 'accountName',
            width: 100,
            sortable: true,
            hidden: true
        },
        // {
        //     xtype: 'actioncolumn',
        //     text: '操作',
        //     width: 80,
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
        //     }, {
        //         tooltip: '删除',
        //         iconCls: "fa-trash",
        //         flag: 'delete',
        //         handler: 'actionFn',
        //     }]
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
                        sel.view.ownerCt.down('button[action="delete"]')
                            .enable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .enable();
                    } else {
                        sel.view.ownerCt.down('button[action="delete"]')
                            .disable();
                        sel.view.ownerCt.down('button[action="update"]')
                            .disable();
                    }
                }
            }
        }
    }
});