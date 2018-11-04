Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentMonitoringEquipmentForm',
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

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentDetailStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'mqttLogs/getMqttLogsDeviceList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationsEquipmentMonitoringEquipmentDetailGridId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentDetailGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'operationsEquipmentMonitoringEquipmentDetailGrid',
    fullscreen: true,
    store: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentDetailStore',
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
            id: 'operationsEquipmentMonitoringEquipmentDetailGridId',
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
                    fieldLabel: '批次ID',
                    name: 'mqttCommandBatchId',
                    hidden: true
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'combo',
                    fieldLabel: '线路',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.getAllLine',
                        includeAll: true
                    },
                    value: '-1',
                    queryMode: 'local',
                    name: 'lineId',
                    editable: false,
                    displayField: 'lineName',
                    valueField: 'lineId',
                    allowBlank: false,
                    blankText: '全部',
                    listeners: {
                        select: function (component) {
                            // console.log(component.getValue());
                            let stationCombo = component.up('form').down('combo[name="stationId"]');
                            // stationCombo.setValue('-1');
                            Ext.apply(stationCombo.store.proxy.extraParams, {lineId: component.getValue()});
                            stationCombo.store.load();
                        }
                    }
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'combo',
                    fieldLabel: '站点',
                    emptyText: '全部',
                    store: {
                        xclass: 'App.common.getStationListByLineId',
                        autoLoad: false,
                        includeAll: true,
                        listeners: {
                            load: function (store, records, successful, operation, eOpts) {
                                if (store.includeAll) {
                                    if (records.length > 0) {
                                        records.unshift({stationId: '-1', stationName: '全部'});
                                        store.setData(records);
                                        Ext.ComponentQuery.query('operationsEquipmentMonitoringGrid')[0].down('combo[name="stationId"]').setValue('-1');
                                    }
                                }
                            }
                        }
                    },
                    value: '-1',
                    displayValue: '全部',
                    queryMode: 'local',
                    name: 'stationId',
                    editable: false,
                    displayField: 'stationName',
                    valueField: 'stationId',
                    allowBlank: false,
                    blankText: '全部'

                },
                // {
                //     columnWidth: .45,
                //     layout: 'form',
                //     xtype: 'combo',
                //     fieldLabel: '设备类型',
                //     emptyText: '--请选择--',
                //     store: {
                //         xclass: 'App.common.getEquipmentTypeFirst',
                //         includeAll: true
                //     },
                //     value: 'all',
                //     queryMode: 'local',
                //     name: 'sparePartTypeId',
                //     editable: false,
                //     displayField: 'categoryName',
                //     valueField: 'sparePartTypeId',
                //     allowBlank: false,
                //     blankText: '全部'
                // },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'textfield',
                    fieldLabel: '设备类型',
                    name: 'categoryName'
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'textfield',
                    fieldLabel: '设备名称',
                    name: 'partName'
                },
                {
                    columnWidth: .45,
                    layout: 'form',
                    xtype: 'textfield',
                    fieldLabel: '设备唯一ID',
                    name: 'deviceuId'
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
                    margin: 10,
                    xtype: 'button',
                    text: '查询',
                    action: 'searchDetail',
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
            text: 'equipmentId',
            dataIndex: 'equipmentId',
            flex: 1,
            hidden: true
        },
        {
            text: '设备类型',
            dataIndex: 'categoryName',
            flex: 1,
            hidden: true
        },
        {
            text: '设备名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '站点编码',
            dataIndex: 'stationCode',
            flex: 1
        },
        {
            text: '设备唯一ID',
            dataIndex: 'deviceuId',
            flex: 1
        },
        {
            text: 'deviceCode',
            dataIndex: 'deviceCode',
            flex: 1,
            hidden: true
        },
        {
            text: '命令类型',
            dataIndex: 'commandType',
            flex: 1,
            hidden: true,
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
                    flag: 'lookDetail',
                    handler: 'actionFn'
                }
            ]
        }
    ],
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentWindow', {
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
    xtype: 'operationsEquipmentMonitoringEquipmentWindow',
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
                            xtype: 'operationsEquipmentMonitoringEquipmentDetailGrid'
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
                            xtype: 'operationsEquipmentMonitoringEquipmentForm'
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