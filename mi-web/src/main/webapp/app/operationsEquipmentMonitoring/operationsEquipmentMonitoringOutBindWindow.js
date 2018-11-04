Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringOutBindForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentMonitoringOutBindForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 120,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0px',
        // padding: '0px',
        borderSpacing: '0px',
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'deviceNoRegistId',
                    name: 'deviceNoRegistId',
                    hidden: true
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '设备唯一ID',
                    name: 'deviceuId'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '设备编码',
                    name: 'deviceCode'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '站点代码',
                    name: 'stationCode'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: '创建时间',
                    name: 'createTime',
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
                    xtype: 'combo',
                    fieldLabel: '绑定设备编号',
                    store: {
                        xclass: 'App.common.getOnlineEquipmentList'
                    },
                    // value:'-1',
                    // displayValue:'全部',
                    queryMode: 'local',
                    name: 'equipmentId',
                    editable: false,
                    displayField: 'equipmentNo',
                    valueField: 'equipmentId',
                    allowBlank: false,
                    emptyText:'请选择设备编号',
                    blankText: '请选择设备编号',
                    listeners: {
                        render: function (combo) {
                            combo.getStore().on("beforeload", function (s, r, o) {
                                let form = combo.up('form');
                                let stationCode = form.down('displayfield[name=stationCode]').getValue();
                                Ext.apply(s.proxy.extraParams, {
                                    stationCode: stationCode,
                                    registType: 1,
                                    start: 0,
                                    limit: 999999
                                });
                            });
                        },
                    }
                }
            ]
        }
    ]
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringOutBindWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 400,
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
    buttonAlign: "center",
    xtype: 'operationsEquipmentMonitoringOutBindWindow',
    controller: 'operationsEquipmentMonitoringController',
    items: [
        {
            xtype: 'operationsEquipmentMonitoringOutBindForm'
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});