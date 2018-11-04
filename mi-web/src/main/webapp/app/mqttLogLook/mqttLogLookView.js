Ext.define('App.mqttLogLook.mqttLogLookView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.mqttLogLook.mqttLogLookStore',
        'App.mqttLogLook.mqttLogLookController',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentWindow',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController'
    ],
    store: {
        xclass: 'App.mqttLogLook.mqttLogLookStore'
    },
    controller: {
        xclass: 'App.mqttLogLook.mqttLogLookController'
    },
    xtype: 'mqttLogLookView',
    fullscreen: true,
    tbar: [
        {
            id: 'mqttLogLookViewFormId',
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
                        } ,
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '命令类型',
                                    emptyText: '全部',
                                    value: '-1',
                                    store: {
                                        data: [
                                            {
                                                id: "-1",
                                                name: "全部"
                                            },
                                            {
                                                id: "preinstall",
                                                name: "预设"
                                            },
                                            {
                                                id: "shell",
                                                name: "shell"
                                            },
                                            {
                                                id: "custom",
                                                name: "自定义"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'commandType',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    
                                }
                            ]
                        } ,
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '发送人',
                                    name: 'createUser'
                                }
                            ]
                        },
                        {
                            columnWidth: .66,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 5,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
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
            text: 'mqttCommandBatchId',
            dataIndex: 'mqttCommandBatchId',
            flex: 1,
            hidden: true
        },
        {
            text: '批次号',
            dataIndex: 'batchNo',
            flex: 1
        },
        {
            text: '命令类型',
            dataIndex: 'commandType',
            flex: 1,
            align: 'left',
            renderer: function (value) {
                if (value == 'preinstall') {
                    return '预设'
                }
                if (value == 'shell') {
                    return 'shell'
                }
                if (value == 'custom') {
                    return '自定义'
                }
                return ''
            }
        },
        {
            text: '命令类别',
            dataIndex: 'commandCategory',
            flex: 1
        },
        {
            text: '发送时间',
            dataIndex: 'createTime',
            flex: 1,
            renderer: function (value) {
                if (value) {
                    function timetrans(date) {
                        var date = new Date(date);
                        Y = date.getFullYear() + '-';
                        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                        D = ('0' + date.getDate()).slice(-2) + ' ';
                        h = ('0' + date.getHours()).slice(-2) + ':';
                        m = ('0' + date.getMinutes()).slice(-2) + ':';
                        s = ('0' + date.getSeconds()).slice(-2);
                        return Y + M + D + h + m + s;
                    };
                    return timetrans(value);
                } else {
                    return '';
            
                }
            }
        },
        {
            text: '发送人',
            dataIndex: 'createUser',
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
                // margin: "0px 10px"
            },
            items: [
                {
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'look',
                    handler: 'actionFn'
                }
            ]
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
                        sel.view.ownerCt.down('button[action="execution"]').disable();
                    }
                    else {
                        sel.view.ownerCt.down('button[action="execution"]').enable();
                    }
                }
            }
        }
    }
});