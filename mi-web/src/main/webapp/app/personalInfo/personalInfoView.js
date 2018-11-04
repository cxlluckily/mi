Ext.define('App.personalInfo.personalInfoView', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    autoScroll: true,
    requires: [
        'App.personalInfo.personalInfoStore',
        'App.personalInfo.personalInfoController'
    ],
    controller: {
        xclass: 'App.personalInfo.personalInfoController'
    },
    xtype: 'personalInfoView',
    listeners: {
        beforerender: function (component, opt) {
            component.getController().getFormValues(component);
        }
    },
    fullscreen: true,
    items: [
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
                            xtype: 'displayfield',
                            name: 'subjectName',
                            fieldLabel: '公司',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            readOnly: true,
                            maxLength: 16
                        },
                        {
                            xtype: 'displayfield',
                            name: 'workNumber',
                            fieldLabel: '工号',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            maxLength: 8
                        },
                        // {
                        //     xtype: 'displayfield',
                        //     name: 'userName',
                        //     fieldLabel: '用户名',
                        //     labelWidth: 120,
                        //     labelAlign: 'right',
                        //     autoHeight: true,
                        //     allowBlank: false,
                        //     hidden:true
                        // },
                        {
                            xtype: 'displayfield',
                            name: 'wasDefaultManager',
                            fieldLabel: 'wasDefaultManager',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            hidden:true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'roleName',
                            fieldLabel: '角色',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16
                        },
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '性别',
                            name: 'sex',
                            labelWidth: 120,
                            defaultType: 'radiofield',
                            id: 'sysUserSexRadioBox',
                            defaults: {
                                width: 50,
                                padding: '0 5'
                            },
                            layout: 'hbox',
                            items: [
                                {
                                    boxLabel: '男',
                                    name: 'sex',
                                    inputValue: 'male',
                                    id: 'sysUserSexRadioOne'
                                },
                                {
                                    boxLabel: '女',
                                    name: 'sex',
                                    inputValue: 'female',
                                    id: 'sysUserSexRadioTwo'
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
                            xtype: 'displayfield',
                            name: 'orgName',
                            fieldLabel: '部门',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16
                        },
                        {
                            xtype: 'displayfield',
                            name: 'realName',
                            fieldLabel: '姓名',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true
                        }, {
                            xtype: 'displayfield',
                            name: 'position',
                            fieldLabel: '职位',
                            labelWidth: 120,
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false
                        }
                    ]
                }
            ]
        },

        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .6,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'phone',
                    fieldLabel: '手机号',
                    labelAlign: 'right',
                    autoHeight: true,
                    width: 300,
                    labelWidth: 120,
                    blankText: '请输入手机号!',
                    regex:/^1\d{10}$/,
                    regexText : '请输入11位手机格式'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .7,
            items: [
                {
                    xtype: 'textfield',
                    name: 'email',
                    fieldLabel: '邮箱',
                    labelWidth: 120,
                    labelAlign: 'right',
                    width: 300,
                    autoHeight: true,
                    regex: App.common.regExpValidator.email,
                    regexText: '请输入正确的邮箱格式'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .7,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'lastLoginTime',
                    width: 300,
                    labelWidth: 120,
                    fieldLabel: '上次登录时间',
                    labelAlign: 'right',
                    autoHeight: true,
                },
                {
                    xtype: 'displayfield',
                    name: 'lastLoginIp',
                    width: 300,
                    labelWidth: 120,
                    fieldLabel: '上次登录IP',
                    labelAlign: 'right',
                    autoHeight: true,
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .5,
            items: [
                {
                    xtype: 'container',
                    items: [
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
                            width: 300,
                            labelWidth: 120,
                            fieldLabel: '头像上传',
                            name: 'photoUrl',
                            buttonText: '浏览',
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
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            height:150,
            padding:'0 0 0 0',
            margin:'0 0 0 135',
            items: [
                {
                    xtype: 'textfield',
                    name: 'fullPhotoUrl',
                    hidden: true
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .6,
            items: [
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    labelWidth: 120,
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText: '最多输入200个字'
                }
            ]
        }
    ],
    buttons: [
        {
            text: '保存',
            margin: 10,
            action: 'save'
        }, {
            text: '修改密码',
            margin: 10,
            action: 'editpass'
        }
    ]
});