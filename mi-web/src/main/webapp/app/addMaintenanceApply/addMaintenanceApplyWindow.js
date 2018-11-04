Ext.define('App.addMaintenanceApply.addMaintenanceApplyForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    width: 800,
    xtype: 'addMaintenanceApplyForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    controller: {
        xclass: 'App.addMaintenanceApply.addMaintenanceApplyController'
    },
    // listeners: {
    //     beforerender: function (combo) {
    //         var imageSrc = combo.getValues().fullPhotoUrl;
    //         Ext.getCmp('browseImage').autoEl.src = imageSrc;
    //     }
    // },
    autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'form',
            items: [
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>站点',
                    anchor: '90%',
                    emptyText: '--双击选择--',
                    queryMode: 'local',
                    name: 'stationId',
                    editable: false,
                    displayField: 'name',
                    valueField: 'code',
                    allowBlank: false,
                    blankText: '请选择站点!',
                    listeners: {
                        afterrender: function (combo, opt) {
                            // 修改页可使用，重新赋值
                            // var rec = combo.up('window').recordData;
                            // combo.setValue(rec.orgId);
                            // combo.setRawValue(rec.orgName);
                        },
                        expand: function (cb, e, opt) {
                            var w = Ext.create('App.addMaintenanceApply.addMaintenanceApplyStationWindow', {component: cb});
                            w.show();
                        },
                        change: function (component) {
                            component.up('form').getForm().setValues({equipmentId: ''})
                        }
                    }
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>设备类型',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.getEquipmentTypeFirst'
                    },
                    queryMode: 'local',
                    name: 'sparePartTypeId',
                    editable: false,
                    displayField: 'categoryName',
                    valueField: 'sparePartTypeId',
                    allowBlank: false,
                    blankText: '请选择设备类型!',
                    // multiSelect: true,
                    listeners: {
                        change: function (component) {
                            component.up('form').getForm().setValues({equipmentId: ''})
                        }
                    }
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>设备编号',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.getEquipmentListByStationAndSparePartType'
                    },
                    queryMode: 'local',
                    name: 'equipmentId',
                    editable: false,
                    displayField: 'equipmentNo',
                    valueField: 'equipmentId',
                    allowBlank: false,
                    blankText: '请选择设备编号!',
                    listeners: {
                        focus: function (component) {
                            var formValues = component.up('form').getForm().getValues();
                            if (!formValues.stationId || !formValues.sparePartTypeId) {
                                // Ext.Msg.show({
                                //     title: '警告',
                                //     message: '请选择站点和设备类型！',
                                //     buttons: Ext.Msg.OK,
                                //     icon: Ext.Msg.WARNING
                                // });
                                Ext.toast('请选择站点和设备类型！');
                                // component.nextNode().focus();
                                // component.nextNode().blur();
                                return;
                            }
                            Ext.apply(component.store.proxy.extraParams, {
                                stationId: formValues.stationId,
                                sparePartTypeId: formValues.sparePartTypeId
                            });
                            component.store.load();
                        },
                        change: function (component, newValue, oldValue, eOpts) {
                            var checkboxGroup = component.up('form').down('checkboxgroup');
                            component.up('form').getForm().setValues({errors: []});
                            if (newValue == null) {
                                checkboxGroup.removeAll();
                            } else {
                                // component.up('form').getForm().setValues({errors:[]});
                                var config = {
                                    url: ctx + 'repairApply/getErrorPhenomenonByEquipmentId.do',
                                    data: {equipmentId: newValue},
                                    success: function (response, opts) {
                                        var result = Ext.decode(response.responseText);
                                        if (result.result == "success") {
                                            checkboxGroup.removeAll();
                                            Ext.each(result.data, function (item) {
                                                var checkItem = {
                                                    xtype: 'checkbox',
                                                    boxLabel: item.breakAbbreviated,
                                                    name: 'errors',
                                                    inputValue: item.breakdownInfoId
                                                };
                                                checkboxGroup.add(checkItem);
                                            });
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
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'errorCode',
                    fieldLabel: '错误代码',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'userKey',
                    fieldLabel: 'userKey',
                    value: window.sessionStorage['userKey'],
                    hidden: true
                },
                {
                    xtype: 'checkboxgroup',
                    fieldLabel: '故障现象',
                    defaultType: 'checkbox',
                    defaults: {
                        labelAlign: 'right',
                        columnWidth: 1,
                        labelWidth: 300,
                        margin: 0,
                        padding: 0,
                    },
                    columns: 1,
                    items: []
                },
                {
                    xtype: 'textareafield',
                    name: 'breakDescribe',
                    fieldLabel: '故障描述',
                    maxLength: 256,
                    maxLengthText:'该输入项的最大长度是256个字'
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
                            }else{
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
                // {
                //     xtype: 'radiogroup',
                //     name: 'imgBox',
                //     fieldLabel: "预览图片",
                //     columns: 6
                // },
            ]
        }
    ]
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'addMaintenanceApplyGrid',
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

Ext.define('App.addMaintenanceApply.addMaintenanceApplyWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 800,
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
    xtype: 'addMaintenanceApplyWindow',
    controller: 'addMaintenanceApplyController',
    items: [
        {
            xtype: 'addMaintenanceApplyForm'
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