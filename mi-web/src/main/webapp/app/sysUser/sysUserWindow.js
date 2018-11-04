Ext.define('App.sysUser.sysUserForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'sysUserForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    listeners: {
        beforerender: function (combo) {
            var imageSrc = combo.getValues().fullPhotoUrl;
            var viewAction = combo.up('window').viewAction;
            if (viewAction != 'update') {
                Ext.getCmp('browseImage').hidden = true;
            } else {
                Ext.getCmp('browseImage').autoEl.src = imageSrc;
            }
        }
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            hidden: true,
            items: [
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'operationSubjectName',
                            fieldLabel: '公司',
                            labelAlign: 'right',
                            autoHeight: true,
                            fieldStyle: {
                                margin: 0,
                                padding: "8px 0px"
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'userId',
                            fieldLabel: 'userId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'wasDefaultManager',
                            fieldLabel: 'wasDefaultManager',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'userKey',
                            fieldLabel: 'userKey',
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
                            xtype: 'textfield',
                            name: 'userName',
                            fieldLabel: '<font color="red">*</font>用户名',
                            labelAlign: 'right',
                            autoHeight: true,
                            // allowBlank: false,
                            blankText: '请输入用户名!',
                            // readOnly:true,
                            maxLength: 16,
                            hidden:true,
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update') {
                                        this.readOnly = true;
                                        this.fieldLabel = '用户名';
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            name: 'phone',
                            fieldLabel: '<font color="red">*</font>手机号',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入手机号!',
                            regex: App.common.regExpValidator.telPhones,
                            regexText: App.common.regExpValidator.telPhoneText
                        },
                        {
                            xtype: 'textfield',
                            name: 'workNumber',
                            fieldLabel: '<font color="red">*</font>工号',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入工号!',
                            maxLength: 8
                        },
                        {
                            xtype: 'combo',
                            fieldLabel: '部门',
                            anchor: '90%',
                            emptyText: '--双击选择--',
                            queryMode: 'local',
                            name: 'orgId',
                            editable: false,
                            displayField: 'text',
                            valueField: 'id',
                            listeners: {
                                afterrender: function (combo, opt) {
                                    var rec = combo.up('window').recordData;
                                    combo.setValue(rec.orgId);
                                    combo.setRawValue(rec.orgName);
                                },
                                expand: function (cb, e, opt) {
                                    var config = {
                                        title: this.fieldLabel,
                                        treeParams: {
                                            url: 'data/loadTree.do',
                                            rootProperty: 'data',
                                            component: cb,
                                            params: {
                                                type: "organization"
                                            }
                                        }
                                    };
                                    var w = Ext.create('App.common.selectWindow', config);
                                    w.show()
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            name: 'position',
                            fieldLabel: '职位',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: '状态',
                            labelAlign: 'right',
                            autoHeight: true,
                            value:'可用',
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    var formValues = extWindow.down('form').getForm().getValues();
                                    if (formValues.wasDefaultManager !== '1'){
                                        this.hidden = true;
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '状态',
                            defaultType: 'radiofield',
                            id: 'sysUserStatusRadioBox',
                            defaults: {
                                width: 100,
                                padding: '0 10'
                            },
                            layout: 'hbox',
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    var formValues = extWindow.down('form').getForm().getValues();
                                    if (formValues.wasDefaultManager == '1'){
                                        this.hidden = true;
                                    }
                                }
                            },
                            items: [
                                {
                                    boxLabel: '可用',
                                    name: 'status',
                                    inputValue: 'start_using',
                                    id: 'sysUserStatusRadioOne'
                                },
                                {
                                    boxLabel: '不可用',
                                    name: 'status',
                                    inputValue: 'stop_using',
                                    id: 'sysUserStatusRadioTwo'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [

                        {
                            xtype: 'textfield',
                            name: 'password',
                            inputType:"password",
                            fieldLabel: '<font color="red">*</font>密码',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入密码!',
                            minLength: 6,
                            minLengthText: '密码不少于6位',
                            regex: App.common.regExpValidator.password,
                            regexText: App.common.regExpValidator.passwordText,
                            maxLength: 16,
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update') {
                                        // this.allowBlank = true;
                                        this.hidden = true;
                                        this.disable(true);
                                    }
                                }
                            }

                            // getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                            //     var extWindow = this.up('window');
                            //     if (extWindow.viewAction == 'update') {
                            //         return 'x-hidden';
                            //     }
                            // }
                        },

                        {
                            xtype: 'textfield',
                            name: 'realName',
                            fieldLabel: '<font color="red">*</font>姓名',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入姓名!',
                            maxLength: 16
                        },
                        {
                            xtype: 'textfield',
                            name: 'email',
                            fieldLabel: '邮箱',
                            labelAlign: 'right',
                            autoHeight: true,
                            regex: App.common.regExpValidator.email,
                            regexText: '请输入正确的邮箱格式'
                        },
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '性别',
                            defaultType: 'radiofield',
                            // id: 'sysUserSexRadioBox',
                            defaults: {
                                width: 100,
                                padding: '0 10'
                            },
                            layout: 'hbox',
                            items: [
                                {
                                    boxLabel: '男',
                                    name: 'sex',
                                    inputValue: 'male',
                                    // id: 'sysUserSexRadioOne'
                                },
                                {
                                    boxLabel: '女',
                                    name: 'sex',
                                    inputValue: 'female',
                                    // id: 'sysUserSexRadioTwo'
                                },
                                // {
                                //     boxLabel: '未知',
                                //     name: 'sex',
                                //     inputValue: 'none',
                                //     // id: 'sysUserSexRadioThree'
                                // }
                            ]
                        }


                    ]
                }
            ]
        },

        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'photoUrl',
                            fieldLabel: '头像路径',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
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
                            fieldLabel: '用户头像',
                            name: 'photoUrl',
                            buttonText: '选择文件',
                            listeners: {
                                change: function (component) {
                                    var fileDom = component.getEl().down('input[type=file]');
                                    var files = fileDom.dom.files;
                                    var str = '';
                                    if (files.length > 1) {
                                        Ext.Msg.show({
                                            title: '警告',
                                            message: '最多允许上传1张图片，请重新选择图片！',
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.WARNING
                                        });
                                        component.up('form').getForm().setValues({isHavePic: false});
                                        component.setRawValue('');
                                        component.multipleFn(component);
                                        return;
                                    }
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
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'fullPhotoUrl',
                            fieldLabel: '头像路径',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'box',
                            id: 'browseImage',
                            name: "fullPhotoUrl",
                            fieldLabel: "预览图片",
                            autoEl: {
                                tag: 'img',
                                src: '',
                                height: '100px',
                                style: {
                                    margin: '10px 20px'
                                }
                            }
                        }
                    ]
                }

            ]
        },

        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText: '最多输入200个字'
                }
            ]
        }
    ]
});


Ext.define('App.sysUser.sysUserWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    recordData: '{recordData}',
    width: 800,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'sysUserWindow',
    controller: 'sysUserController',
    items: [
        {
            xtype: 'sysUserForm'
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