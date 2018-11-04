Ext.define('App.maintenanceApply.maintenanceApplyForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    width: '100%',
    padding: 10,
    xtype: 'maintenanceApplyForm',
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
                            value:window.sessionStorage['userKey'],
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
                            xtype: 'maintenanceApplyGrid'
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
                                            xtype: 'textfield',
                                            name: 'pic1',
                                            fieldLabel: 'pic3',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            hidden: true
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'pic2',
                                            fieldLabel: 'pic3',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            hidden: true
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'pic3',
                                            fieldLabel: 'pic3',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            hidden: true
                                        },
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
        },
        {
            xtype: 'fieldset',
            title: '维修抄送',
            layout: 'column',
            width: '100%',
            items: [
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '维修人员',
                            name: 'maintenanceUser',
                            hidden:true
                        },
                        {
                            xtype: 'combo',
                            fieldLabel: '<font color="red">*</font>维修人员',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.getRepairerInfo'
                            },
                            queryMode: 'local',
                            name: 'maintenanceUserID',
                            editable: false,
                            displayField: 'realName',
                            valueField: 'userId',
                            allowBlank: false,
                            blankText: '请选择维修人员!',
                            // multiSelect: true,
                            listeners: {
                                beforerender: function (component) {
                                    var formValues = component.up('maintenanceApplyForm').getForm().getValues();
                                    Ext.apply(component.store.proxy.extraParams,{stationId:formValues.stationId});
                                    component.store.load();
                                },
                                change:function (component, newValue, oldValue, eOpts) {
                                    var displayValue = component.getRawValue();
                                    component.up('maintenanceApplyForm').getForm().setValues({maintenanceUser:displayValue});
                                }
                            }
                        },
                        {
                            xtype: 'combo',
                            fieldLabel: '抄送人',
                            hidden:true,
                            emptyText: '--双击选择--',
                            store: {
                                xclass: 'App.common.getConcatPeopleList'
                            },
                            queryMode: 'local',
                            name: 'userIds',
                            editable: false,
                            displayField: 'realName',
                            valueField: 'userId',
                            multiSelect: true,
                            listeners: {
                                beforerender: function (component) {
                                    var store = component.getStore();
                                    Ext.apply(store.proxy.extraParams, {type: 'pc', searchContent: ''});
                                    store.load();
                                },
                                expand: function (cb, e, opt) {
                                    var value = cb.getValue();
                                    var rawValue = cb.getRawValue();
                                    var rawData = rawValue.split(', ');
                                    var selectData = [];
                                    Ext.each(value, function (item, index) {
                                        var data = {
                                            userId: item,
                                            realName: rawData[index]
                                        };
                                        selectData.push(data);
                                    });
                                    var w = Ext.create('App.maintenanceApply.maintenanceApplySelectWindow');
                                    w.down('maintenanceApplySelectGridSelect').getStore().setData(selectData);
                                    w.show();
                                }
                            }
                        }

                    ]
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApply.maintenanceApplyGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyGrid',
    hideHeaders: true,
    fullscreen: true,
    // height: 150,
    width: '100%',
    autoScroll: true,
    style: {
        border: "1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'userId',
            dataIndex: 'userId',
            flex: 1,
            hidden: true
        },
        {
            text: '时间',
            dataIndex: 'createTime',
            width: 200,
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
            text: '描述',
            dataIndex: 'initiator',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                var value = '';
                if (record.data.status == 'Reported') {
                    value = '已上报（由 ' + record.data.initiator + ' 上报）'
                }
                if (record.data.status == 'Dispatched') {
                    value = '已派单（由 ' + record.data.initiator + ' 指派给 ' + record.data.targetPerson + '）'
                }
                if (record.data.status == 'Repairing') {
                    value = '已接修（由 ' + record.data.initiator + ' 接修）'
                }
                if (record.data.status == 'Failed') {
                    value = '维修失败（由 ' + record.data.initiator + ' 维修失败）'
                }
                if (record.data.status == 'Repaired') {
                    value = '已修复（由 ' + record.data.initiator + ' 维修完成）'
                }
                if (record.data.status == 'Rated') {
                    value = '已评价（由 ' + record.data.initiator + ' 评价）'
                }
                return value;
            }
        },
        {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            hidden: true
        },
        {
            text: '目标人',
            dataIndex: 'targetPerson',
            flex: 1,
            hidden: true
        }
    ]
});

Ext.define('App.maintenanceApply.maintenanceApplyWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 800,
    height: 600,
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
    buttonAlign: "center",
    xtype: 'maintenanceApplyWindow',
    controller: 'maintenanceApplyController',
    items: [
        {
            xtype: 'maintenanceApplyForm'
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});