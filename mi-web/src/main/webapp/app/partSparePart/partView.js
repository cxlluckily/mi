Ext.define('App.partSparePart.partView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.partSparePart.partController',
        'App.partSparePart.partWindow'
    ],
    store: {
        xclass: 'App.partSparePart.partStore'
    },
    controller: {
        xclass: 'App.partSparePart.partController'
    },
    xtype: 'partView',
    id: 'partView',
    fullscreen: true,
    tbar: [
        {
            id: 'partViewFormId',
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
                        marginBottom:'0'
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
                                    fieldLabel: '名称',
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
                                    fieldLabel: '品牌',
                                    name: 'brand'
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
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                },{
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
                                    disabled : true
                                },
                                // {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '导入',
                                //     action: 'importDownload',
                                //     iconCls: 'fa-download'
                                // }
                                ,{
                                    margin: 10,
                                    xtype: 'button',
                                    text: '导入',
                                    action: 'importTypeDownload',
                                    iconCls: 'fa-download'
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
            text: '名称',
            dataIndex: 'partName',
            flex: 1
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
            flex: 1
        },
        {
            text: '尺寸',
            dataIndex: 'size',
            flex: 1
        },
        {
            text: '材质',
            dataIndex: 'material',
            flex: 1
        },
        {
            text: '单位',
            dataIndex: 'units',
            flex: 1
        },
        {
            text: '库存上限',
            dataIndex: 'upperLimit',
            flex: 1
        },
        {
            text: '库存下限',
            dataIndex: 'lowerLimit',
            flex: 1
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            hidden:true
        },
        {
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
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length < 1) {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                    } else {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                    }
                }
            }
        }
    }
});

