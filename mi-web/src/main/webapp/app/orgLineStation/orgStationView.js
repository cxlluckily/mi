Ext.define('App.orgLineStation.orgStationView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.orgLineStation.orgStationStore',
        'App.orgLineStation.orgStationController',
        'App.common.importWindow',
        'App.common.messageWindow'
    ],
    store: {
        xclass: 'App.orgLineStation.orgStationStore'
    },
    controller: {
        xclass: 'App.orgLineStation.orgStationController'
    },

    xtype: 'orgStationView',
    id: 'orgStationView',
    fullscreen: true,
    tbar: [
        {
            id: 'orgStationViewFormId',
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
                                    fieldLabel: '站点编码',
                                    name: 'stationCode'
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
                                    fieldLabel: '站点名称',
                                    name: 'stationName'
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
                                    fieldLabel: '负责人',
                                    name: 'headPerson'
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
                                    fieldLabel: '站点状态',
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
                            columnWidth: .66,
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
                                },{
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                },{
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled : true
                                },{
                                    margin: 10,
                                    xtype: 'button',
                                    text: '导入',
                                    action: 'importLineDownload',
                                    iconCls: 'fa-download'
                                }
                                // , {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '导入',
                                //     action: 'importDownload',
                                //     iconCls: 'fa-download'
                                // }
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
        id:'orgStationViewPaging',
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
            text: 'stationId',
            dataIndex: 'stationId',
            width: 100,
            align: 'left',
            hidden: true
        },
        {
            text: '编码',
            dataIndex: 'stationCode',
            width: 100,
            align: 'left'
        },
        {
            text: '名称',
            dataIndex: 'stationName',
            width: 100,
            align: 'left'
        },
        {
            text: '负责人',
            dataIndex: 'headPerson',
            width: 100,
            align: 'left'
        },
        {
            text: '电话',
            dataIndex: 'phone',
            width: 150,
            align: 'left'
        },
        {
            text: '起始站',
            dataIndex: 'wasBegin',
            width: 100,
            align: 'left',
            renderer: function (value, record) {
                if (value) {
                    return "是"
                }
            }
        },
        {
            text: '终点站',
            dataIndex: 'wasEnd',
            width: 100,
            align: 'left',
            renderer: function (value, record) {
                if (value) {
                    return "是"
                }
            }
        },
        {
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
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length == 1) {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                    } else {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                    }
                }
            }
        }
    }
});
