Ext.define('App.operation.addOrEditForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'addOrEditForm',
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
                    xtype: 'textfield',
                    name: 'operationSubjectId',
                    fieldLabel: 'operationSubjectId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>地区',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.regionDDLStore'
                    },
                    queryMode: 'local',
                    name: 'regionId',
                    editable: false,
                    displayField: 'areaName',
                    valueField: 'regionId',
                    allowBlank: false,
                    blankText: '请选择地区!'
                },
                {
                    xtype: 'textfield',
                    name: 'subjectCode',
                    fieldLabel: '编号',
                    labelAlign: 'right',
                    autoHeight: true,
                },
                {
                    xtype: 'textfield',
                    name: 'subjectName',
                    fieldLabel: '<font color="red">*</font>主体名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入主体名称!',
                    listeners: {
                        beforerender: function () {//渲染
                            var extWindow = this.up('window');
                            if (extWindow.viewAction == 'update')
                            {
                                this.readOnly = true;
                                this.fieldLabel = '主体名称';
                            }
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'loginName',
                    fieldLabel: '<font color="red">*</font>登录名',
                    labelAlign: 'right',
                    allowBlank: false,
                    autoHeight: true,
                    blankText:'请输入登录名',
                    listeners: {
                        beforerender: function () {//渲染
                            var extWindow = this.up('window');
                            if (extWindow.viewAction == 'update')
                            {
                                this.readOnly = true;
                                this.allowBlank= true;
                                this.fieldLabel = '登录名';
                            }
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'password',
                    fieldLabel: '<font color="red">*</font>密码',
                    labelAlign: 'right',
                    blankText:'请输入密码',
                    allowBlank: false,
                    autoHeight: true,
                    minLength:6,
                    minLengthText:'密码不少于6位',
                    regex: /^[0-9a-zA-Z]+$/,
                    regexText:'密码可以是纯数字，或是字母数字组合',
                    listeners: {
                        beforerender: function () {//渲染
                            var extWindow = this.up('window');
                            if (extWindow.viewAction == 'update')
                            {
                                this.hidden = true;
                                this.allowBlank= true;
                            }
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'contacts',
                    fieldLabel: '联系人',
                    labelAlign: 'right',
                    autoHeight: true,
                },
                {
                    xtype: 'textfield',
                    name: 'contactPhone',
                    fieldLabel: '联系电话',
                    labelAlign: 'right',
                    autoHeight: true,
                    // regex:App.common.regExpValidator.telPhones,
                    // regexText:App.common.regExpValidator.telPhoneText
                },
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength:200,
                    maxLengthText:'最多允许输入200个字'
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
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
        }
    ]
});


Ext.define('App.operation.addOrEditWindow', {
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
    xtype: 'addOrEditWindow',
    controller: 'operationController',
    items: [
        {
            xtype: 'addOrEditForm'
        }
    ],
    buttons: [
        {   id:'saveId',
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