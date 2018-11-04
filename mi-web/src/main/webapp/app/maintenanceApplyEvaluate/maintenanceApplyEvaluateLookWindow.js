Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    width: '100%',
    padding: 10,
    xtype: 'maintenanceApplyEvaluateLookForm',
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
                            xtype: 'maintenanceApplyEvaluateLookGrid'
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
                            listeners: {
                                beforerender: function (component) {
                                    // var formValues = component.up('maintenanceApplyEvaluateLookForm').getForm().getValues();
                                    // var status = component.up('maintenanceApplyEvaluateLookForm').down('displayfield[name="applyStatus"]').value;
                                    // console.log(status);
                                    // console.log(formValues);
                                }
                            },
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
                                            xtype: 'radiogroup',
                                            name: 'imgBox',
                                            fieldLabel: "现场图片",
                                            columns: 6,
                                            listeners: {
                                                beforerender: function ($this) {
                                                    var formValues = $this.up('form').getForm().getValues();
                                                    var pics = [formValues.pic1, formValues.pic2, formValues.pic3];
                                                    // 新增预览图
                                                    Ext.each(pics, function (item, index) {
                                                        var img = {
                                                            xtype: 'box',
                                                            name: "fullPhotoUrl",
                                                            autoEl: {
                                                                tag: 'img',
                                                                src: 'app/resources/images/userPhoto.png',
                                                                // src: item,
                                                                height: '100px',
                                                                style: {
                                                                    margin: '10px 20px'
                                                                }
                                                            }
                                                        };
                                                        $this.add(img);
                                                    });
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    title: "维修情况",
                                    items: [
                                        {
                                            xtype: 'maintenanceApplyEvaluateLookChangeGrid'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'supplyErrors',
                                            fieldLabel: '故障现象补充'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'breakDescribe',
                                            fieldLabel: '故障描述'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            fieldLabel: '维修结果',
                                            name: 'wasFinished',
                                            renderer: function (value, record) {
                                                if (value == "yes") {
                                                    return "修复"
                                                }
                                                if (value == "no") {
                                                    return "未修复"
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'breakDescribe',
                                            fieldLabel: '处理措施'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'maintenanceDescribe',
                                            fieldLabel: '处理描述'
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            name: 'imgBox',
                                            fieldLabel: "现场图片",
                                            columns: 6,
                                            listeners: {
                                                beforerender: function ($this) {
                                                    var formValues = $this.up('form').getForm().getValues();
                                                    var pics = [formValues.pic1, formValues.pic2, formValues.pic3];
                                                    // 新增预览图
                                                    Ext.each(pics, function (item, index) {
                                                        var img = {
                                                            xtype: 'box',
                                                            name: "fullPhotoUrl",
                                                            autoEl: {
                                                                tag: 'img',
                                                                src: 'app/resources/images/userPhoto.png',
                                                                // src: item,
                                                                height: '100px',
                                                                style: {
                                                                    margin: '10px 20px'
                                                                }
                                                            }
                                                        };
                                                        $this.add(img);
                                                    });
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    title: "评价情况",
                                    items: [
                                        {
                                            xtype: 'displayfield',
                                            fieldLabel: '维修结果',
                                            name: 'wasFinished',
                                            renderer: function (value, record) {
                                                if (value == "yes") {
                                                    return "修复"
                                                }
                                                if (value == "no") {
                                                    return "未修复"
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'speedAppraise',
                                            value: 0,
                                            fieldLabel: '响应速度',
                                            allowBlank: false,
                                            hidden: true,
                                            listeners: {
                                                beforerender: function (component, newV, oldV, event) {
                                                    component.up('form').down('rating[valueName=' + component.name + ']').setValue(component.getValue());
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'serviceAppraise',
                                            fieldLabel: '服务速度',
                                            allowBlank: false,
                                            hidden: true,
                                            listeners: {
                                                beforerender: function (component, newV, oldV, event) {
                                                    component.up('form').down('rating[valueName=' + component.name + ']').setValue(component.getValue());
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'operationSpecificationAppraise',
                                            fieldLabel: '操作规范',
                                            allowBlank: false,
                                            hidden: true,
                                            listeners: {
                                                beforerender: function (component, newV, oldV, event) {
                                                    component.up('form').down('rating[valueName=' + component.name + ']').setValue(component.getValue());
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '响应速度',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'speedAppraise',
                                                    style: {
                                                        fontSize: '32px'
                                                    },
                                                    trackOver: false,
                                                    listeners: {
                                                        change: function (component, newV, oldV, event) {
                                                            component.setValue(component.up('form').down('textfield[name=' + component.valueName + ']').getValue());
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '服务速度',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'serviceAppraise',
                                                    style: {
                                                        fontSize: '32px'
                                                    },
                                                    trackOver: false,
                                                    listeners: {
                                                        change: function (component) {
                                                            component.setValue(component.up('form').down('textfield[name=' + component.valueName + ']').getValue());
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '操作规范',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'operationSpecificationAppraise',
                                                    style: {
                                                        fontSize: '32px'
                                                    },
                                                    trackOver: false,
                                                    listeners: {
                                                        change: function (component) {
                                                            component.setValue(component.up('form').down('textfield[name=' + component.valueName + ']').getValue());
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'displayfield',
                                            fieldLabel: '回访评价',
                                            name: 'appraiseDescribe'
                                        }
                                    ]
                                },
                            ]
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyEvaluateLookGrid',
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

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookChangeGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyEvaluateLookChangeGrid',
    fullscreen: true,
    width: '100%',
    autoScroll: true,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '旧部件id',
            dataIndex: 'oldSparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: '旧部件',
            dataIndex: 'oldPartName',
            flex: 1
        },
        {
            text: '旧部件二维码',
            dataIndex: 'oldSparePartNO',
            width: 270,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'code'
                }
            }
        },
        {
            text: '新部件id',
            dataIndex: 'newSparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: '新部件',
            dataIndex: 'newPartName',
            flex: 1
        },
        {
            text: '数量',
            dataIndex: 'count',
            flex: 1
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer: function (value, record) {
                if (value == "unique") {
                    return "唯一标识"
                }
                if (value == "notUnique") {
                    return "非唯一标识"
                }
            }
        },
        {
            text: '新部件二维码',
            dataIndex: 'newSparePartNO',
            width: 270,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'qrCode'
                }
            }
        }
    ]
});

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookWindow', {
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
        'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateController'
    ],
    buttonAlign: "center",
    xtype: 'maintenanceApplyEvaluateLookWindow',
    controller: 'maintenanceApplyEvaluateController',
    items: [
        {
            xtype: 'maintenanceApplyEvaluateLookForm'
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