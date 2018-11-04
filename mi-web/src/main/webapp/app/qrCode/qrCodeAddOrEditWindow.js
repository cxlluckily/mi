Ext.define('App.qrCode.qrCodeAddOrEditForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'qrCodeAddOrEditForm',
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
                    name: 'count',
                    fieldLabel: '<font color="red">*</font>生成数量',
                    labelAlign: 'right',
                    regex:/^[1-9]\d*$/,
                    regexText:'数量必须为正整数',
                    allowBlank: false,
                    blankText:'请输入正整数的数量',
                    autoHeight: true,
                },
            ]
        }
    ]
});


Ext.define('App.qrCode.qrCodeAddOrEditWindow', {
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
    xtype: 'qrCodeAddOrEditWindow',
    controller: 'qrCodeController',
    items: [
        {
            xtype: 'qrCodeAddOrEditForm'
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