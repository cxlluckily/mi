Ext.define('App.supplier.supplierForm', {
    extend: 'Ext.form.Panel',
    xtype: 'supplierForm',
    fullscreen: true,
    layout: 'form',
    buttonAlign: 'center',
    controller: {
        xclass: 'App.supplier.supplierController'
    },
    fieldDefaults: {
        labelAlign: 'right',
        labelWidth: 260
    },
    items: [{
        xtype: 'textfield',
        name: 'supplierId',
        hidden: true
    }, {
        xtype: 'textfield',
        name: 'supplierName',
        fieldLabel: '<font color="red">*</font>供应商名称',
        labelAlign: 'right',
        allowBlank: false,
        blankText: '请输入供应商名称!',
        maxLength: 16,
        maxLengthText: '最多允许输入16个字',
        listeners: {
            beforerender: function () {
                var extWindow = this.up('window');
                if (extWindow.viewAction == 'update') {
                    this.readOnly = true;
                    this.fieldStyle = 'background:#ddd';
                    // this.fieldLabel ='<font color="red">*</font>供应商名称';
                }
            }
        }
    },
    //     {
    //     xtype: 'textfield',
    //     name: 'brandName',
    //     fieldLabel: '<font color="red">*</font>品牌名称',
    //     labelAlign: 'right',
    //     allowBlank: false,
    //     blankText: '请输入品牌名称!',
    //     maxLength: 16,
    //     maxLengthText: '最多允许输入16个字',
    //     listeners: {
    //         beforerender: function () {
    //             var extWindow = this.up('window');
    //             if (extWindow.viewAction == 'update') {
    //                 this.readOnly = true;
    //                 this.fieldStyle = 'background:#ddd';
    //             }
    //         }
    //     }
    // },
        {
        xtype: 'textfield',
        name: 'contacts',
        fieldLabel: '联系人',
        labelAlign: 'right',
        blankText: '请输入联系人!',
        maxLength: 10,
        maxLengthText: '最多允许输入10个字',
    }, {
        xtype: 'textfield',
        name: 'contactInfo',
        fieldLabel: '联系方式',
        labelAlign: 'right',
        // blankText : '请输入联系电话!',
        // regex: /^1\d{10}$/,
        // regexText : '请输入正确的电话格式'
        //,
        // listeners: {
        //     change: function (field, newValue, oldValue) {
        // 	if(newValue) {
        //             var mobileTrue = /^1\d{10}$/.test(newValue);
        //             if(mobileTrue) {
        //                 return field.regex = /^1\d{10}$/
        //             }else {
        //                 return field.regex = /([0-9]{3,4}-)?[0-9]{7,8}/
        //             }
        //         }
        //     }
        // }
    }, {
        xtype: 'textfield',
        name: 'email',
        fieldLabel: '电子邮件',
        labelAlign: 'right',
        blankText: '请输入电子邮件!',
        regex: App.common.regExpValidator.email,
        regexText: '请输入正确的邮箱格式'
    }, {
        xtype: 'radiogroup',
        //columns: 1,
        fieldLabel: '状态',
        vertical: false,
        items: [{
            boxLabel: '可用',
            name: 'status',
            inputValue: 'start_using'
        }, {
            boxLabel: '不可用',
            name: 'status',
            inputValue: 'stop_using'
        }]
    }, {
        xtype: 'textfield',
        name: 'taxpayerNumber',
        fieldLabel: '纳税人识别号',
        labelAlign: 'right',
        // regex:/^[^~#^$@%&!?%*]$/gi,
        // regexText: '纳税人识别号不能输入特殊字符！',
        blankText: '请输入纳税人识别号!',
        maxLength: 20,
        maxLengthText: '最多输入20个字'
    }, {
        xtype: 'textfield',
        name: 'bankOfDeposit',
        fieldLabel: '开户银行',
        labelAlign: 'right',
        blankText: '请输入开户银行!',
        maxLength: 50
    }, {
        xtype: 'textfield',
        name: 'bankAccount',
        fieldLabel: '银行账户',
        labelAlign: 'right',
        blankText: '请输入银行账户!',
        maxLength: 19
    }, {
        xtype: 'textfield',
        name: 'accountName',
        fieldLabel: '划款户名',
        labelAlign: 'right',
        blankText: '请输入划款户名!',
        maxLength: 16
    }, {
        xtype: 'textfield',
        name: 'address',
        fieldLabel: '地址',
        labelAlign: 'right',
        blankText: '请输入地址!',
        maxLength: 50,
        maxLengthText: '不能超过50个字'
    }, {
        xtype: 'textareafield',
        name: 'remark',
        fieldLabel: '备注',
        labelAlign: 'right',
        anchor: '80%',
        allowBlank: true,
        maxLength: 200,
        maxLengthText: '不能超过200个字'
    }]

});

// 测试
Ext.define('App.supplier.supplierWindow', {
    extend: 'Ext.window.Window',
    width: 750,
    height: 550,
    autoScroll: true,
    title: '{title}',
    viewAction: '{viewAction}',
    iconCls: '{optAction == "add"?"fa-plus-circle":"fa-edit"}',
    //resizable : true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    //autoRender : true,
    buttonAlign: "center",
    xtype: 'supplierWindow',
    controller: {
        xclass: 'App.supplier.supplierController'
    },
    items: [{
        xtype: 'supplierForm'
    }],
    buttons: [{
        text: '保存',
        action: 'save'
    }, {
        text: '取消',
        handler: function (a) {
            this.up('window').close();
        }
    }]
});
