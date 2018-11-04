Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMaintainForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    padding: 10,
    width: '100%',
    xtype: 'maintenanceApplyRepairMaintainForm',
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
                            name: 'equipmentId',
                            fieldLabel: 'equipmentId',
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
                            xtype: 'maintenanceApplyRepairMaintainGrid'
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
                                            name: 'addErrors',
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
                                            imgType: 'report',
                                            title: '现场图片'
                                        }
                                    ]
                                },
                                {
                                    title: "维修情况",
                                    items: [
                                        {
                                            xtype: 'maintenanceApplyRepairMaintainChangeGrid'
                                        },
                                        {
                                            xtype: 'checkboxgroup',
                                            fieldLabel: '故障现象',
                                            name: 'reportErrorsGroup',
                                            defaultType: 'checkbox',
                                            cls: 'x-check-group-alt',
                                            defaults: {
                                                columnWidth: 1,
                                                padding: '0 0 0 0',
                                                xtype: 'checkbox',
                                                name: 'reportedErrors',
                                                style: {
                                                    paddingRight: '20px'
                                                },
                                                listeners: {
                                                    change: function (component, newValue, oldValue) {
                                                        var formValues = component.up('form').getForm().getValues();
                                                        var solutionsCheckGroup = component.up('form').down('[name="solutionsCheckGroup"]');
                                                        if (!formValues.errors && !formValues.reportedErrors) {
                                                            solutionsCheckGroup.removeAll();
                                                            return;
                                                        }
                                                        var errors = !formValues.errors ? [] : Array.isArray(formValues.errors) ? formValues.errors : [formValues.errors] || [];
                                                        var reportedErrors = !formValues.reportedErrors ? [] : Array.isArray(formValues.reportedErrors) ? formValues.reportedErrors : [formValues.reportedErrors] || [];
                                                        var errorsData = [];
                                                        Ext.each(errors, function (item, index) {
                                                            errorsData.push(item);
                                                        });
                                                        Ext.each(reportedErrors, function (reportItem, index) {
                                                            var check = true;
                                                            Ext.each(errors, function (item, index) {
                                                                if (reportItem == item) {
                                                                    check = false;
                                                                }
                                                            });
                                                            if (check) {
                                                                errorsData.push(reportItem);
                                                            }
                                                        });
                                                        var url = 'repairApply/getTreatmentInfoList.do';
                                                        var data = {
                                                            errors: errorsData,
                                                            searchContent: ''
                                                        };
                                                        var config = {
                                                            url: ctx + url,
                                                            data: data,
                                                            success: function (response, opts) {
                                                                var result = Ext.decode(response.responseText);
                                                                if (result.result == "success") {
                                                                    solutionsCheckGroup.removeAll();
                                                                    var items = [];
                                                                    Ext.each(result.data, function (item) {
                                                                        var checkItem = {
                                                                            boxLabel: item.repairDescription,
                                                                            inputValue: item.repairInfoId
                                                                        };
                                                                        items.push(checkItem);
                                                                    });
                                                                    solutionsCheckGroup.add(items);
                                                                } else {
                                                                    Ext.Msg.show({
                                                                        title: '警告',
                                                                        message: result.message,
                                                                        buttons: Ext.Msg.OK,
                                                                        icon: Ext.Msg.WARNING
                                                                    });
                                                                }
                                                            },

                                                            failure: function (response, opts) {
                                                                console.log('server-side failure with status code '
                                                                    + response.status);
                                                                Ext.Msg.show({
                                                                    title: '错误',
                                                                    message: response,
                                                                    buttons: Ext.Msg.OK,
                                                                    icon: Ext.Msg.error
                                                                });
                                                            }
                                                        };
                                                        App.common.Ajax.request(config);
                                                    }
                                                }
                                            },
                                            columns: 2,
                                            items: [],
                                            listeners: {}
                                        },
                                        {
                                            xtype: 'checkboxgroup',
                                            fieldLabel: '故障现象补充',
                                            name: 'errorsCheckGroup',
                                            defaultType: 'checkbox',
                                            cls: 'x-check-group-alt',
                                            defaults: {
                                                columnWidth: 1,
                                                padding: '0 0 0 0',
                                                xtype: 'checkbox',
                                                name: 'errors',
                                                style: {
                                                    paddingRight: '20px'
                                                },
                                                listeners: {
                                                    change: function (component, newValue, oldValue) {
                                                        var formValues = component.up('form').getForm().getValues();
                                                        var solutionsCheckGroup = component.up('form').down('[name="solutionsCheckGroup"]');
                                                        if (!formValues.errors && !formValues.reportedErrors) {
                                                            solutionsCheckGroup.removeAll();
                                                            return;
                                                        }
                                                        var errors = !formValues.errors ? [] : Array.isArray(formValues.errors) ? formValues.errors : [formValues.errors] || [];
                                                        var reportedErrors = !formValues.reportedErrors ? [] : Array.isArray(formValues.reportedErrors) ? formValues.reportedErrors : [formValues.reportedErrors] || [];
                                                        var errorsData = [];
                                                        Ext.each(errors, function (item, index) {
                                                            errorsData.push(item);
                                                        });
                                                        Ext.each(reportedErrors, function (reportItem, index) {
                                                            var check = true;
                                                            Ext.each(errors, function (item, index) {
                                                                if (reportItem == item) {
                                                                    check = false;
                                                                }
                                                            });
                                                            if (check) {
                                                                errorsData.push(reportItem);
                                                            }
                                                        });
                                                        var url = 'repairApply/getTreatmentInfoList.do';
                                                        var data = {
                                                            errors: errorsData,
                                                            searchContent: ''
                                                        };
                                                        var config = {
                                                            url: ctx + url,
                                                            data: data,
                                                            success: function (response, opts) {
                                                                var result = Ext.decode(response.responseText);
                                                                if (result.result == "success") {
                                                                    solutionsCheckGroup.removeAll();
                                                                    var items = [];
                                                                    Ext.each(result.data, function (item) {
                                                                        var checkItem = {
                                                                            boxLabel: item.repairDescription,
                                                                            inputValue: item.repairInfoId
                                                                        };
                                                                        items.push(checkItem);
                                                                    });
                                                                    solutionsCheckGroup.add(items);
                                                                } else {
                                                                    Ext.Msg.show({
                                                                        title: '警告',
                                                                        message: result.message,
                                                                        buttons: Ext.Msg.OK,
                                                                        icon: Ext.Msg.WARNING
                                                                    });
                                                                }
                                                            },

                                                            failure: function (response, opts) {
                                                                console.log('server-side failure with status code '
                                                                    + response.status);
                                                                Ext.Msg.show({
                                                                    title: '错误',
                                                                    message: response,
                                                                    buttons: Ext.Msg.OK,
                                                                    icon: Ext.Msg.error
                                                                });
                                                            }
                                                        };
                                                        App.common.Ajax.request(config);
                                                    }
                                                }
                                            },
                                            columns: 2,
                                            items: [],
                                            listeners: {
                                                // beforerender: function (component) {
                                                //     var formValues = component.up('form').getForm().getValues();
                                                //     var solutionsCheckGroup = component.up('form').down('[name="solutionsCheckGroup"]');
                                                //     if (!formValues.errors) {
                                                //         solutionsCheckGroup.removeAll();
                                                //         return;
                                                //     }
                                                //     var url = 'repairApply/getTreatmentInfoList.do';
                                                //
                                                //     var data = {
                                                //         errors: Array.isArray(formValues.errors) ? formValues.errors : [formValues.errors],
                                                //         searchContent: ''
                                                //     };
                                                //     var config = {
                                                //         url: ctx + url,
                                                //         data: data,
                                                //         success: function (response, opts) {
                                                //             var result = Ext.decode(response.responseText);
                                                //             if (result.result == "success") {
                                                //                 solutionsCheckGroup.removeAll();
                                                //                 var items = [];
                                                //                 Ext.each(result.data, function (item) {
                                                //                     var checkItem = {
                                                //                         boxLabel: item.repairDescription,
                                                //                         inputValue: item.repairInfoId
                                                //                     };
                                                //                     items.push(checkItem);
                                                //                 });
                                                //                 solutionsCheckGroup.add(items);
                                                //             } else {
                                                //                 Ext.Msg.show({
                                                //                     title: '警告',
                                                //                     message: result.message,
                                                //                     buttons: Ext.Msg.OK,
                                                //                     icon: Ext.Msg.WARNING
                                                //                 });
                                                //             }
                                                //         },
                                                //
                                                //         failure: function (response, opts) {
                                                //             console.log('server-side failure with status code '
                                                //                 + response.status);
                                                //             Ext.Msg.show({
                                                //                 title: '错误',
                                                //                 message: response,
                                                //                 buttons: Ext.Msg.OK,
                                                //                 icon: Ext.Msg.error
                                                //             });
                                                //         }
                                                //     };
                                                //     App.common.Ajax.request(config);
                                                // }
                                            }
                                        },
                                        {
                                            xtype: 'textareafield',
                                            name: 'maintenanceDescribe',
                                            fieldLabel: '维修故障描述',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            emptyText: '最多输入200个字',
                                            maxLength: 200,
                                            maxLengthText: '该输入项的最大长度是200个字'
                                        },
                                        {
                                            xtype: 'radiogroup',
                                            fieldLabel: '维修结果',
                                            columns: 5,
                                            vertical: true,
                                            defaults: {
                                                padding: '0 0 0 0'
                                            },
                                            items: [
                                                {boxLabel: '修复', name: 'wasFinished', inputValue: 'yes'},
                                                {boxLabel: '未修复', name: 'wasFinished', inputValue: 'no'}
                                            ]
                                        },

                                        {
                                            xtype: 'checkboxgroup',
                                            fieldLabel: '处理措施',
                                            name: 'solutionsCheckGroup',
                                            defaultType: 'checkbox',
                                            cls: 'x-check-group-alt',
                                            layout: {
                                                type: 'table',
                                                columns: 2
                                            },
                                            defaults: {
                                                columnWidth: 1,
                                                padding: '0 0 0 0',
                                                xtype: 'checkbox',
                                                name: 'solutions',
                                                style: {
                                                    paddingRight: '20px'
                                                }
                                            },
                                            items: []
                                        },

                                        {
                                            xtype: 'textareafield',
                                            name: 'other',
                                            fieldLabel: '其他处理措施',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            emptyText: '最多输入200个字',
                                            maxLength: 200,
                                            maxLengthText: '该输入项的最大长度是200个字'
                                        },
                                        {
                                            xtype: 'textareafield',
                                            name: 'processDescribe',
                                            fieldLabel: '处理描述',
                                            labelAlign: 'right',
                                            autoHeight: true,
                                            emptyText: '最多输入200个字',
                                            maxLength: 200,
                                            maxLengthText: '该输入项的最大长度是200个字'
                                        },
                                        {
                                            xtype: 'fieldcontainer',
                                            fieldLabel: '是否保存头像',
                                            defaultType: 'checkbox',
                                            hidden: true,
                                            defaults: {
                                                width: 100,
                                                padding: '0 10'
                                            },
                                            layout: 'hbox',
                                            items: [
                                                {
                                                    boxLabel: '是',
                                                    name: 'isHavePic',
                                                    inputValue: true
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'filefield',
                                            fieldLabel: '图片',
                                            name: 'photoUrl',
                                            buttonText: '选择文件',
                                            listeners: {
                                                change: function (component) {
                                                    var fileDom = component.getEl().down('input[type=file]');
                                                    var files = fileDom.dom.files;
                                                    var str = '';
                                                    // if (files.length > 3) {
                                                    //     Ext.Msg.show({
                                                    //         title: '警告',
                                                    //         message: '最多允许上传3张图片，请重新选择图片！',
                                                    //         buttons: Ext.Msg.OK,
                                                    //         icon: Ext.Msg.WARNING
                                                    //     });
                                                    //     component.up('form').getForm().setValues({isHavePic: false});
                                                    //     component.setRawValue('');
                                                    //     component.multipleFn(component);
                                                    //     return;
                                                    // }
                                                    for (var i = 0; i < files.length; i++) {
                                                        var picType = files[i].name.toLowerCase();
                                                        str += picType;
                                                        str += ' ';
                                                    }
                                                    var typeCheck = true;
                                                    Ext.each(files, function (item) {
                                                        var names = item.name.split('.');
                                                        var type = names[names.length - 1];
                                                        if (type != 'jpg' && type != 'jpeg' && type != 'png') {
                                                            typeCheck = false;
                                                        }
                                                    });
                                                    if (!typeCheck) {
                                                        Ext.Msg.show({
                                                            title: '提示',
                                                            message: '仅支持jpg、jpeg、png格式的图片',
                                                            buttons: Ext.Msg.OK,
                                                            icon: Ext.Msg.WARNING
                                                        });
                                                        component.up('form').getForm().setValues({isHavePic: false});
                                                        component.setRawValue('');
                                                        component.multipleFn(component);
                                                        return;
                                                    }
                                                    if (files.length == 0) {
                                                        component.up('form').getForm().setValues({isHavePic: false});
                                                    } else {
                                                        component.up('form').getForm().setValues({isHavePic: true});
                                                    }
                                                    component.setRawValue(str);
                                                    component.multipleFn(component);
                                                },
                                                afterrender: function (component) {
                                                    component.multipleFn(component);
                                                }
                                            },
                                            multipleFn: function ($this) {
                                                // var typeArray = ["application/x-shockwave-flash", "audio/MP3", "image/*", "flv-application/octet-stream"];
                                                var typeArray = ["image/*"];
                                                var fileDom = $this.getEl().down('input[type=file]');
                                                fileDom.dom.setAttribute("multiple", "multiple");
                                                fileDom.dom.setAttribute("accept", typeArray.join(","));
                                            }
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

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMaintainGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyRepairMaintainGrid',
    fullscreen: true,
    // height: 150,
    width: '100%',
    autoScroll: true,
    hideHeaders: true,
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

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMaintainChangeGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyRepairMaintainChangeGrid',
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
    tbar: [
        {
            xtype: 'button',
            text: '更换部件',
            action: 'choose'
        }
    ],
    listeners: {
        beforeedit: function (editor, context, eOpts) {
            if (context.record.data.inventoryType == 'notUnique' && context.field == 'oldQrCode') {
                return false;
            }
        }
    },
    columnLines: true,
    columns:
        [
            {
                text: '背包ID',
                dataIndex: 'userDeviceId',
                flex: 1,
                hidden: true
            },
            {
                text: '新部件',
                dataIndex: 'newPartName',
                flex: 1
            },
            {
                text: '新部件库存id',
                dataIndex: 'newStockId',
                flex: 1,
                hidden: true
            },
            {
                text: '新部件id',
                dataIndex: 'newSparePartId',
                flex: 1,
                hidden: true
            },
            {
                text: '数量',
                dataIndex: 'replaceCount',
                flex: 1,
                editor: {
                    field: {
                        xtype: 'numberfield',
                        name: 'replaceCount',
                        padding: 0
                    }
                }
            },
            {
                text: '背包数量',
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
                dataIndex: 'newQrCode',
                width: 270
            },
            {
                text: '旧部件二维码',
                dataIndex: 'oldQrCode',
                width: 270,
                editor: {
                    completeOnEnter: false,
                    field: {
                        xtype: 'textfield',
                        name: 'code',
                        padding: 0
                    }
                }
            },
            {
                text: '旧部件库存id',
                dataIndex: 'oldStockId',
                flex: 1,
                hidden: true
            },
            {
                text: '旧部件id',
                dataIndex: 'oldSparePartId',
                flex: 1,
                hidden: true
            },
            {
                text: '旧部件类型id',
                dataIndex: 'oldSparePartTypeId',
                flex: 1,
                hidden: true
            },
            {
                text: 'canDelete',
                dataIndex: 'canDelete',
                flex: 1,
                hidden: true
            },
            {
                xtype: 'actioncolumn',
                text: '操作',
                width: 60,
                menuDisabled: true,
                align: 'center',
                fieldDefaults: {
                    style: {
                        margin: "20px 10px"
                    }
                },
                defaults: {
                    margin: "0px 5px"
                },
                items: [
                    {
                        tooltip: '删除',
                        iconCls: "fa-trash",
                        flag: 'delete',
                        handler: 'actionFn',
                        getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                            if (r.data.canDelete) {
                                return 'fa-trash';
                            } else {
                                return 'x-hidden';
                            }
                        }
                    }
                ]
            }
        ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairMaintainWindow', {
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
    buttonAlign: "center",
    xtype: 'maintenanceApplyRepairMaintainWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        {
            xtype: 'maintenanceApplyRepairMaintainForm'
        }
    ],
    buttons: [
        {
            text: '维修完成',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});