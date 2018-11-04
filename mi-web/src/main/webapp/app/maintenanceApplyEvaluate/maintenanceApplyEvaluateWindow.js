Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    width: '100%',
    padding:10,
    xtype: 'maintenanceApplyEvaluateForm',
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
                                            fieldLabel: '故障描述'
                                        },
                                        {
                                            xtype: 'commonImagesLook',
                                            imgType:'report',
                                            title:'现场图片'
                                        }
                                    ]
                                },
                                {
                                    title: "维修情况",
                                    items: [
                                        {
                                            xtype: 'maintenanceApplyChangeGrid'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'supplyErrors',
                                            fieldLabel: '故障现象补充'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'maintenanceDescribe',
                                            fieldLabel: '维修故障描述'
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
                                            name: 'solutions',
                                            fieldLabel: '处理措施'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            name: 'processDescribe',
                                            fieldLabel: '处理描述'
                                        },
                                        {
                                            xtype: 'commonImagesLook',
                                            imgType:'repair',
                                            title:'现场图片'
                                        }
                                    ]
                                },
                                {
                                    title: "评价情况",
                                    items: [
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '评价维修结果',
                                            columns: 5,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {boxLabel: '修复', name: 'appraiseWasFinished', inputValue: 'yes'},
                                                {boxLabel: '未修复', name: 'appraiseWasFinished', inputValue: 'no'}
                                            ]
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'speedAppraise',
                                            fieldLabel: '响应速度',
                                            allowBlank: false,
                                            hidden:true
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'serviceAppraise',
                                            fieldLabel: '服务速度',
                                            allowBlank: false,
                                            hidden:true
                                        },
                                        {
                                            xtype: 'textfield',
                                            name: 'operationSpecificationAppraise',
                                            fieldLabel: '操作规范',
                                            allowBlank: false,
                                            hidden:true
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '<font color="red">*</font>响应速度',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'speedAppraise',
                                                    style:{
                                                        fontSize:'32px'
                                                    },
                                                    listeners:{
                                                        change:function (component) {
                                                            var value = component.getValue();
                                                            component.up('form').down('textfield[name='+component.valueName+']').setValue(value);
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '<font color="red">*</font>服务速度',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'serviceAppraise',
                                                    style:{
                                                        fontSize:'32px'
                                                    },
                                                    listeners:{
                                                        change:function (component) {
                                                            var value = component.getValue();
                                                            component.up('form').down('textfield[name='+component.valueName+']').setValue(value);
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '<font color="red">*</font>操作规范',
                                            columns: 2,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {
                                                    xtype: 'rating',
                                                    valueName: 'operationSpecificationAppraise',
                                                    style:{
                                                        fontSize:'32px'
                                                    },
                                                    listeners:{
                                                        change:function (component) {
                                                            var value = component.getValue();
                                                            component.up('form').down('textfield[name='+component.valueName+']').setValue(value);
                                                        }
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'textareafield',
                                            fieldLabel: '回访评价',
                                            name:'appraiseDescribe',
                                            emptyText: '最多输入200个字',
                                            maxLength: 200,
                                            maxLengthText:'该输入项的最大长度是200个字'
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

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyEvaluateGrid',
    hideHeaders: true,
    fullscreen: true,
    // height: 150,
    width:'100%',
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

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateChangeGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyEvaluateChangeGrid',
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
            hidden:true
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
            hidden:true
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

Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateWindow', {
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
        'App.maintenanceApply.maintenanceApplyLogGrid',
        'App.maintenanceApply.maintenanceApplyChangeGrid',
    ],
    buttonAlign: "center",
    xtype: 'maintenanceApplyEvaluateWindow',
    controller: 'maintenanceApplyEvaluateController',
    items: [
        {
            xtype: 'maintenanceApplyEvaluateForm'
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