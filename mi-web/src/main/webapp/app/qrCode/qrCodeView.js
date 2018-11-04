Ext.define('App.qrCode.qrCodeView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.qrCode.qrCodeStore',
        'App.qrCode.qrCodeController',
        'App.qrCode.qrCodeAddOrEditWindow'
    ],
    store: {
        xclass: 'App.qrCode.qrCodeStore'
    },
    controller: {
        xclass: 'App.qrCode.qrCodeController'
    },
    xtype: 'qrCodeView',
    id: 'qrCodeView',
    fullscreen: true,
    tbar: [
        {
            id: 'qrCodeViewFormId',
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
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '二维码号',
                                    name: 'qrCode'
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
                        //             fieldLabel: '设备编号',
                        //             name: 'equipmentNO'
                        //         }
                        //     ]
                        // },
                        {
                            columnWidth: .33,
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
                            columnWidth: .33,
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

                        // {
                        //     columnWidth: .25,
                        //     layout: 'form',
                        //     border: false,
                        //     items: [
                        //         {
                        //             xtype: 'textfield',
                        //             fieldLabel: '设备序号',
                        //             name: 'serialNumber'
                        //         }
                        //     ]
                        // },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "beginTime",
                                    fieldLabel: "申请开始时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                                    // altFormats: "Y/m/d|Ymd"
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "endTime",
                                    fieldLabel: "申请结束时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date()))
                                    // altFormats: "Y/m/d|Ymd"
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
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                },
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '批量新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '批量删除',
                                    action: 'batchDel',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }
                                , {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导出',
                                    action: 'exportUpload',
                                    iconCls: 'fa-upload'
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
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
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
            text: 'qrCodeId',
            dataIndex: 'qrCodeId',
            flex:1,
            align: 'left',
            hidden: true
        }, {
            text: '二维码号码',
            dataIndex: 'qrCode',
            flex:2,
            align: 'left',
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'qrCode',
                    editable:false
                }
            }
        },
        // {
        //     text: '设备编号',
        //     dataIndex: 'equipmentNO',
        //     flex:1,
        //     align: 'left'
        // },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex:1,
            align: 'left'
        }, {
            text: '备件名称',
            dataIndex: 'partName',
            flex:1,
            align: 'left'
        },
        {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex:1,
            align: 'left'
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex:1,
            align: 'left'
        },
        // {
        //     text: '设备序号',
        //     dataIndex: 'serialNumber',
        //     flex:1,
        //     align: 'left'
        // },
        {
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
                        sel.view.ownerCt.down('button[action="batchDel"]').disable();
                    }
                    else if (selected.length == 1)
                    {
                        sel.view.ownerCt.down('button[action="batchDel"]').enable();
                    }
                    else
                    {
                        sel.view.ownerCt.down('button[action="batchDel"]').enable();
                    }
                }
            }
        }
    }
});