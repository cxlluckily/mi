Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringStore',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringWindow',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentWindow',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandWindow',
        'App.common.importWindow',
        'App.common.messageWindow'
    ],
    store: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringStore'
    },
    controller: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController'
    },
    xtype: 'operationsEquipmentMonitoringGrid',
    fullscreen: true,
    tbar: [
        {
            id: 'operationsEquipmentMonitoringGridFormId',
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
                                            let stationCombo = component.up('form').
                                            down('combo[name="stationId"]');
                                            // stationCombo.setValue('-1');
                                            Ext.apply(stationCombo.store.proxy.extraParams, {lineId: component.getValue()});
                                            stationCombo.store.load();
                                        }
                                    }
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
                                    fieldLabel: '站点',
                                    emptyText: '全部',
                                    store: {
                                        xclass: 'App.common.getStationListByLineId',
                                        autoLoad:false,
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
                                    // value:'-1',
                                    // displayValue:'全部',
                                    queryMode: 'local',
                                    name: 'stationId',
                                    editable: false,
                                    displayField: 'stationName',
                                    valueField: 'stationId',
                                    allowBlank: false,
                                    blankText: '全部'
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
                                    fieldLabel: '设备类型',
                                    emptyText: '--请选择--',
                                    store: {
                                        xclass: 'App.common.getEquipmentTypeFirst',
                                        includeAll: true
                                    },
                                    value: 'all',
                                    queryMode: 'local',
                                    name: 'sparePartTypeId',
                                    editable: false,
                                    displayField: 'categoryName',
                                    valueField: 'sparePartTypeId',
                                    allowBlank: false,
                                    blankText: '全部'
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
                                    fieldLabel: '设备唯一ID',
                                    name: 'deviceuId'
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
                                    fieldLabel: '设备名称',
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
                                    emptyText: '全部',
                                    value: '-1',
                                    store: {
                                        data: [
                                            {
                                                id: "-1",
                                                name: "全部"
                                            },
                                            {
                                                id: "INSERVICE",
                                                name: "正常服务"
                                            },
                                            {
                                                id: "MAINTAINCE",
                                                name: "维修模式"
                                            },
                                            {
                                                id: "OUTSERVICE",
                                                name: "暂停服务"
                                            },
                                            {
                                                id: "CLOSESERAVICE",
                                                name: "关闭服务"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'deviceStatus',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
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
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '批量执行',
                                    action: 'execution',
                                    iconCls: 'fa-edit',
                                    disabled: true
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
            text: 'stationId',
            dataIndex: 'stationId',
            flex: 1,
            hidden: true
        },
        {
            text: 'sparePartTypeId',
            dataIndex: 'sparePartTypeId',
            flex: 1,
            hidden: true
        },
        {
            text: 'equipmentId',
            dataIndex: 'equipmentId',
            flex: 1,
            hidden: true
        },

        // {
        //     text: '图片',
        //     dataIndex: 'imageUrl',
        //     width: 100,
        //     align: 'center',
        //     renderer: function (value, metaData, record) {
        //         if(value){
        //             var img = '<img src=' + value + ' width="80" />';
        //             return img;
        //         }
        //     }
        // },
        {
            text: '线路',
            dataIndex: 'lineName',
            flex: 1
        },
        {
            text: '站点',
            dataIndex: 'stationName',
            flex: 1
        },
        {
            text: '设备类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '设备编号',
            dataIndex: 'equipmentNo',
            flex: 1
        },
        {
            text: '设备唯一ID',
            dataIndex: 'deviceuId',
            flex: 1
        },
        {
            text: '状态',
            dataIndex: 'deviceStatus',
            flex: 1,
            align: 'left',
            renderer: function (value) {
                if (value == 'INSERVICE') {
                    return '<font color="#32cd32">正常服务</font>'
                }
                if (value == 'MAINTAINCE') {
                    return '<font color="orange">维修模式</font>'
                }
                if (value == 'OUTSERVICE') {
                    return '<font color="red">暂停服务</font>'
                }
                if (value == 'CLOSESERAVICE') {
                    return '关闭服务'
                }
                return ''
            }
        },


        {
            text: '所在位置',
            dataIndex: 'location',
            width: 200,
            flex: 1,
            align: 'left'
        },
        {
            text: '序列号',
            dataIndex: 'serialNumber',
            flex: 1,
            hidden: true
        },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            flex: 1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1,
            hidden: true
        },
        {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex: 1,
            hidden: true
        },
        {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex: 1,
            hidden: true
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
                    flag: 'lookCommand',
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