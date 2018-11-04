Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOutStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'onlineEquipment/getDeviceNoRegistList.do'
    },
    listeners: {
        beforeload: function (store, options) {
            var formValues = Ext.getCmp('operationsEquipmentMonitoringGridOutFormId').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        }
    }
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOut', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOutStore',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController'
    ],
    store: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOutStore',
    },
    controller: {
        xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController'
    },
    xtype: 'operationsEquipmentMonitoringGridOut',
    fullscreen: true,
    tbar: [
        {
            id: 'operationsEquipmentMonitoringGridOutFormId',
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
                                    fieldLabel: '设备编码',
                                    name: 'deviceCode'
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
                                    fieldLabel: '站点代码',
                                    name: 'stationCode'
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
            text: 'deviceNoRegistId',
            dataIndex: 'deviceNoRegistId',
            flex: 1,
            hidden: true
        },
        {
            text: '设备唯一ID',
            dataIndex: 'deviceuId',
            flex: 1
        },
        {
            text: '设备编码',
            dataIndex: 'deviceCode',
            flex: 1
        },
        {
            text: '站点代码',
            dataIndex: 'stationCode',
            flex: 1
        },
        {
            text: '创建时间',
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
                    }

                    return timetrans(value)
                }
                return '';
            }
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
                    tooltip: '绑定',
                    iconCls: "fa-edit",
                    flag: 'bind',
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