Ext.define('App.server.serverAddOrUpdateForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'serverAddOrUpdateForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 120,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0px',
        // padding: '0px',
        borderSpacing: '0px',
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'deviceNoRegistId',
                    name: 'deviceNoRegistId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '<font color="red">*</font>服务器名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入服务器名称!',
                    // regex: App.common.regExpValidator.telPhones,
                    // regexText: App.common.regExpValidator.telPhoneText
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '<font color="red">*</font>IP地址',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入IP地址!',
                    // regex: App.common.regExpValidator.telPhones,
                    // regexText: App.common.regExpValidator.telPhoneText
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '<font color="red">*</font>服务器用户名',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入服务器用户名!',
                    // regex: App.common.regExpValidator.telPhones,
                    // regexText: App.common.regExpValidator.telPhoneText
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '<font color="red">*</font>服务器密码',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入服务器密码!',
                    // regex: App.common.regExpValidator.telPhones,
                    // regexText: App.common.regExpValidator.telPhoneText
                }
            ]
        }
    ]
});

Ext.define('App.server.serverAddOrUpdateWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 400,
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
    // bodyStyle: {
    //     'overflow-y': 'auto',
    //     'overflow-x': 'hidden'
    // },
    buttonAlign: "center",
    xtype: 'serverAddOrUpdateWindow',
    controller: 'serverController',
    items: [
        {
            xtype: 'serverAddOrUpdateForm'
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});