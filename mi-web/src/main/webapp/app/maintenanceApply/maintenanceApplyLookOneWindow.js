Ext.define('App.maintenanceApply.maintenanceApplyLookOneForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    width: '100%',
    padding: 10,
    xtype: 'maintenanceApplyLookOneForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 100,
        padding: '10px 10px 0px 0px',
        width: '100%',
        margin: '0'
    },
    items: [
        {
            xtype: 'fieldset',
            title: '故障位置',
            layout: 'column',
            width: '100%',
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'userKey',
                            value: window.sessionStorage['userKey'],
                            fieldLabel: 'userKey',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'maintenanceApplyId',
                            fieldLabel: 'maintenanceApplyId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'stationId',
                            fieldLabel: 'stationId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'operationSubjectId',
                            fieldLabel: 'operationSubjectId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'applyNO',
                            fieldLabel: '申请单号',
                            labelAlign: 'right',
                            autoHeight: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'categoryName',
                            fieldLabel: '设备类型',
                            labelAlign: 'right',
                            autoHeight: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'lineName',
                            fieldLabel: '线路',
                            labelAlign: 'right',
                            autoHeight: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'applyUser',
                            fieldLabel: '报修人',
                            labelAlign: 'right',
                            autoHeight: true
                        }

                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'applyStatus',
                            fieldLabel: '状态',
                            labelAlign: 'right',
                            autoHeight: true,
                            renderer: function (value, record) {
                                if (value == "toBeDispatch") {
                                    return "待派单"
                                }
                                if (value == "toBeRepair") {
                                    return "待接修"
                                }
                                if (value == "maintenance") {
                                    return "维修中"
                                }
                                if (value == "repaired") {
                                    return "已修复"
                                }
                                if (value == "noRepair") {
                                    return "未修复"
                                }
                                if (value == "rated") {
                                    return "已评价"
                                }
                            }
                        },
                        {
                            xtype: 'displayfield',
                            name: 'equipmentNO',
                            fieldLabel: '设备编号',
                            labelAlign: 'right',
                            autoHeight: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'stationName',
                            fieldLabel: '站点',
                            labelAlign: 'right',
                            autoHeight: true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'applyDate',
                            fieldLabel: '报修时间',
                            labelAlign: 'right',
                            autoHeight: true,
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
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'fieldset',
            title: '进度情况',
            layout: 'column',
            width: '100%',
            items: [
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'maintenanceApplyLogGrid'
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'fieldset',
            title: '故障维修',
            layout: 'column',
            width: '100%',
            items: [
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'tabpanel',
                            border: false,
                            width: '100%',
                            defaults: {
                                bodyPadding: 10,
                                scrollable: true,
                                closable: false,
                                border: false
                            },
                            tabPosition: 'top',
                            tabRotation: 0,
                            items: [
                                {
                                    title: "故障情况",
                                    items: [
                                        {
                                            xtype: 'displayfield',
                                            name: 'errors',
                                            fieldLabel: '故障现象',
                                            labelAlign: 'right',
                                            autoHeight: true
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'errorCode',
                                            fieldLabel: '错误代码',
                                            labelAlign: 'right',
                                            autoHeight: true
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'breakDescribe',
                                            fieldLabel: '故障描述',
                                            labelAlign: 'right',
                                            autoHeight: true
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'images',
                                            fieldLabel: '图片',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            hidden:true
                                        },
                                        {
                                            xtype: 'commonImagesLook',
                                            imgType:'report',
                                            title:'现场图片'
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApply.maintenanceApplyLookOneWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
    height: 650,
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
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    requires: [
        'App.maintenanceApply.maintenanceApplyController',
        'App.maintenanceApply.maintenanceApplyLogGrid',
        'App.maintenanceApply.maintenanceApplyChangeGrid'
    ],
    buttonAlign: "center",
    xtype: 'maintenanceApplyLookOneWindow',
    controller: 'maintenanceApplyController',
    items: [
        {
            xtype: 'maintenanceApplyLookOneForm'
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