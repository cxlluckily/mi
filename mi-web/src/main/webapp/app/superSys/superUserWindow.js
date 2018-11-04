Ext.define('App.superSys.superUserForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'superUserForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
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
                            xtype: 'textfield',
                            name: 'adminId',
                            fieldLabel: 'adminId',
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
                            name: 'userName',
                            fieldLabel: '<font color="red">*</font>用户名',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入用户名!',
                            listeners: {
                                beforerender: function () {//渲染
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update')
                                    {
                                        this.readOnly = true;
                                        this.fieldLabel = '用户名';
                                    }
                                }
                            }
                        },
                        
                        {
                            xtype: 'textfield',
                            name: 'phone',
                            fieldLabel: '手机号',
                            labelAlign: 'right',
                            autoHeight: true,
                            regex:App.common.regExpValidator.telPhones,
                            regexText:App.common.regExpValidator.telPhoneText
                        },
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '状态',
                            // name: 'username',
                            defaultType: 'radiofield',
                            id: 'superUserStatusRadioBox',
                            defaults: {
                                width: 100,
                                padding: '0 10'
                            },
                            layout: 'hbox',
                            items: [
                                {
                                    boxLabel: '可用',
                                    name: 'status',
                                    inputValue: 'start_using',
                                    id: 'superUserStatusRadioOne'
                                },
                                {
                                    boxLabel: '不可用',
                                    name: 'status',
                                    inputValue: 'stop_using',
                                    id: 'superUserStatusRadioTwo'
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
                            name: 'realName',
                            fieldLabel: '<font color="red">*</font>姓名',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入姓名!'
                        },
                        
                        {
                            xtype: 'textfield',
                            name: 'password',
                            fieldLabel: '<font color="red">*</font>密码',
                            labelAlign: 'right',
                            autoHeight: true,
                            inputType:'password',
                            allowBlank: false,
                            blankText: '请输入密码!',
                            regex:App.common.regExpValidator.password,
                            regexText:App.common.regExpValidator.passwordText,
                            minLength:6,
                            minLengthText:'密码不能少于6位',
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update')
                                    {
                                        this.hidden = true;
                                        this.allowBlank = true;
                                    }
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
});


Ext.define('App.superSys.superUserWindow', {
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
    xtype: 'superUserWindow',
    controller: 'superUserController',
    items: [
        {
            xtype: 'superUserForm'
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