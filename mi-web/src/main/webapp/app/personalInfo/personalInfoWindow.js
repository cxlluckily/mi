Ext.apply(Ext.form.VTypes, {
    confirmPwd : function(val, field) {
        if (field.confirmPwd) {
            var firstPwdId = field.confirmPwd.first;
            var secondPwdId = field.confirmPwd.second;
            this.firstField = Ext.getCmp(firstPwdId);
            this.secondField = Ext.getCmp(secondPwdId);
            var firstPwd = this.firstField.getValue();
            var secondPwd = this.secondField.getValue();
            if (firstPwd !== secondPwd) {
                return true;
            } else {
                return false;
            }
        }
    },
    confirmPwdText : '输入的新旧密码不能相同!'
});
Ext.define('App.personalInfo.personalInfoForm', {
    extend: 'Ext.form.Panel',
    xtype: 'personalInfoForm',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    xtype: 'textfield',
                    id:'oldPassword',
                    name: 'oldPassword',
                    fieldLabel: '<font color="red">*</font>旧密码',
                    autoComplete: 'off',
                    labelAlign: 'right',
                    autoHight: true,
                    tooltip: '请输入密码!',
                    width:500,
                    allowBlank: false,
                    blankText: '密码不能为空',
                    inputType:"password",
                    regex:App.common.regExpValidator.password,
                    regexText:App.common.regExpValidator.passwordText
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    xtype: 'textfield',
                    inputType:"password",
                    id:'newPassword',
                    name: 'newPassword',
                    fieldLabel: '<font color="red">*</font>新密码',
                    autoComplete: 'off',
                    labelAlign: 'right',
                    tooltip: '请输入密码!',
                    autoHight: true,
                    width:500,
                    confirmPwd : {
                        first : 'oldPassword',
                        second : 'newPassword'
                    },
                    vtype : 'confirmPwd',
                    allowBlank: false,
                    blankText:'密码不能为空',
                    regex:App.common.regExpValidator.password,
                    regexText:App.common.regExpValidator.passwordText
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    xtype: 'textfield',
                    inputType:"password",
                    name: 'password',
                    fieldLabel: '<font color="red">*</font>确认密码',
                    autoComplete: 'off',
                    tooltip: '请输入密码!',
                    labelAlign: 'right',
                    width:500,
                    autoHight: true,
                    allowBlank: false,
                    blankText:'密码不能为空',
                    regex:App.common.regExpValidator.password,
                    regexText:App.common.regExpValidator.passwordText
                }
            ]
        }
    ],
    buttons: [
        {
            text: '保存',
            margin: 10,
            action: 'savePass'
        }
    ]
});
Ext.define('App.personalInfo.personalInfoWindow', {
    extend: 'Ext.window.Window',    //这个UI组件是创建一个窗口，当任何事件发生时应该弹出窗口。窗口基本上是一个面板
    title: '{title}',
    width: 600,
    plain: false,
    iconCls: '{viewAction == "add"?"fa-plus-circle":"fa-edit"}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'personalInfoWindow',
    viewAction : '{viewAction}',
    controller: {
        xclass:'App.personalInfo.personalInfoController'
    },
    items: [
        {
            xtype: 'personalInfoForm'
        }
    ]
});