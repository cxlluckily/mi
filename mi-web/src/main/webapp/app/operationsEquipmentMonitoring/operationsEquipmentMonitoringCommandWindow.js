Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentMonitoringCommandForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        // padding: '10,10,10,10',
        width: '100%',
        margin: '0px',
        padding: '0px',
        borderSpacing: '0px',
    },
    listeners: {
        // beforerender: function (component) {
        //     var viewAction = component.up('window').viewAction;
        // }
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'textfield',
            name: 'equipmentIds',
            hidden: true
        },
        {
            // xtype: 'container',
            // layout: 'form',
            // columnWidth: 1,
            html: '<div id="myAceEditor3" style="height: 530px;width: 450px"></div>',
            listeners: {
                afterrender: function (component) {
                    component.up('form').editor = ace.edit("myAceEditor3");
                    component.up('form').editor.setTheme("ace/theme/monokai");
                    component.up('form').editor.setReadOnly(true);
                    // component.up('form').editor.setOption("wrap", "free");
                    component.up('form').editor.setFontSize(16);
                }
            }
        }
    ]
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandDetailStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'mqttLogs/getOneEquipmentMqttLogsList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationsEquipmentMonitoringCommandDetailGridId').getForm().getValues();
            formValues.sendBeginTime += ' 00:00:00';
            formValues.sendEndTime += ' 23:59:59';
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandDetailGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'operationsEquipmentMonitoringCommandDetailGrid',
    fullscreen: true,
    store: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandDetailStore',
        data: []
    },
    // 数据列表
    columnLines: true,
    height: 550,
    border: '1px solid #5fa2dd',
    // width: 550,
    listeners: {},
    tbar: [
        {
            id: 'operationsEquipmentMonitoringCommandDetailGridId',
            xtype: 'form',
            layout: 'column',
            fullscreen: true,
            width: '100%',
            fieldDefaults: {
                anchor: '100%',
                labelAlign: 'right',
                labelWidth: 100,
                // padding: '10,10,10,10',
                width: '100%',
                margin: '10 10 10 0',
                padding: '0px',
                borderSpacing: '0px',
            },
            items: [
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'textfield',
                    fieldLabel: 'equipmentId',
                    name: 'equipmentId',
                    hidden: true
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'textfield',
                    fieldLabel: 'commandCategory',
                    name: 'commandCategory',
                    hidden: true
                },
                {
                    columnWidth: .45,
                    layout: 'form',
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
                    // multiSelect:true,
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'combo',
                    fieldLabel: '执行状态',
                    emptyText: '全部',
                    value: '-1',
                    store: {
                        data: [
                            {
                                id: "-1",
                                name: "全部"
                            },
                            {
                                id: "to_be_executed",
                                name: "待执行"
                            },
                            {
                                id: "executing",
                                name: "执行中"
                            },
                            {
                                id: "finished",
                                name: "执行完成"
                            }
                        ]
                    },
                    queryMode: 'local',
                    name: 'executeStatus',
                    editable: false,
                    displayField: 'name',
                    valueField: 'id',
                    // multiSelect:true,
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: "datefield",
                    name: "sendBeginTime",
                    fieldLabel: "发送开始",
                    editable: false,
                    emptyText: "--请选择--",
                    format: "Y-m-d",//日期的格式
                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: "datefield",
                    name: "sendEndTime",
                    fieldLabel: "发送结束",
                    editable: false,
                    emptyText: "--请选择--",
                    format: "Y-m-d",//日期的格式
                    value: Ext.util.Format.date(Ext.Date.add(new Date()))
                },
                {
                    margin: 10,
                    xtype: 'button',
                    text: '查询',
                    action: 'searchCommand',
                    iconCls: 'fa-search'
                }
                // {
                //     columnWidth: .66,
                //     layout: 'column',
                //     border: false,
                //     items: [
                //         {
                //             margin: 10,
                //             xtype: 'button',
                //             text: '查询',
                //             action: 'searchDetail',
                //             iconCls: 'fa-search'
                //         }
                //     ]
                // }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        },
        {
            text: 'mqttCommandLogId',
            dataIndex: 'mqttCommandLogId',
            flex: 1,
            hidden: true
        },
        {
            text: '批次',
            dataIndex: 'batchNo',
            width:150
        },
        {
            text: '命令类型',
            dataIndex: 'commandType',
            flex: 1,
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
        // {
        //     text: '命令代码',
        //     dataIndex: 'commandCategory',
        //     width: 150,
        // },
        {
            text: '执行状态',
            dataIndex: 'executeStatus',
            flex: 1,
            renderer: function (value) {
                if (value == 'to_be_executed') {
                    return '待执行'
                }
                if (value == 'executing') {
                    return '执行中'
                }
                if (value == 'finished') {
                    return '执行完成'
                }
                return ''
            }
        },
        {
            text: '发送时间',
            dataIndex: 'createTime',
            width: 150,
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
                    }

                    return timetrans(value)
                }
                return '';
            }
        },
        {
            text: '应答时间',
            dataIndex: 'responseTime',
            width: 150,
            hidden:true,
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
                    }

                    return timetrans(value)
                }
                return '';
            }
        },
        {
            text: '完成时间',
            dataIndex: 'finishTime',
            width: 150,
            hidden:true,
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
                    }

                    return timetrans(value)
                }
                return '';
            }
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            flex: 1,
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
                    flag: 'lookDetailByEquipment',
                    handler: 'actionFn'
                }
            ]
        }
    ],
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1200,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    // autoScroll:true,
    // bodyStyle: {
    //     'overflow-y': 'auto',
    //     'overflow-x': 'hidden'
    // },
    // listeners: {
    //     beforeclose(panel, eOpts) {
    //         clearInterval(panel.down('grid').interval);
    //         panel.down('grid').store.setData([]);
    //         panel.down('grid').interval = '';
    //     }
    // },
    buttonAlign: "center",
    xtype: 'operationsEquipmentMonitoringCommandWindow',
    controller: 'operationsEquipmentMonitoringController',
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    columnWidth: .59,
                    layout: 'form',
                    border: false,
                    padding: '0',
                    borderSpacing: '0px',
                    items: [
                        {
                            xtype: 'operationsEquipmentMonitoringCommandDetailGrid'
                        }
                    ]
                },
                {
                    columnWidth: .4,
                    layout: 'form',
                    border: false,
                    padding: '0',
                    borderSpacing: '0px',
                    items: [
                        {
                            xtype: 'operationsEquipmentMonitoringCommandForm'
                        }
                    ]
                }
            ]
        }
    ],
    buttons: [
        {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});