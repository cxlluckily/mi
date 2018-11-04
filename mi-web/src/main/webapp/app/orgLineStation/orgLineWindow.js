Ext.define('App.orgLineStation.orgLineForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'orgLineForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    // listeners: {
    // beforerender: function (combo) {
    //     var imageSrc = combo.getValues().fullPhotoUrl;
    //     Ext.getCmp('browseImage').autoEl.src = imageSrc;
    // }
    // },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    name: 'operationSubjectId',
                    fieldLabel: '公司',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'lineId',
                    fieldLabel: 'ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'lineName',
                    fieldLabel: '<font color="red">*</font>线路名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入线路名称!',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'lineCode',
                    fieldLabel: '<font color="red">*</font>线路编码',
                    labelAlign: 'right',
                    autoHeight: true,
                    regex:App.common.regExpValidator.NumberRexs,
                    regexText:App.common.regExpValidator.NumberTexts,
                    allowBlank: false,
                    maxLength: 8
                },
                {
                    xtype: 'textfield',
                    name: 'headPerson',
                    fieldLabel: '负责人',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '电话',
                    labelAlign: 'right',
                    autoHeight: true,
                    // regexText : '请输入正确的电话格式',
                    // regex: '',
                    // listeners: {
                    //     change: function (field, newValue, oldValue) {
                    //         if(newValue) {
                    //             var mobileTrue = /^1\d{10}$/.test(newValue);
                    //             if(mobileTrue) {
                    //                 return field.regex = /^1\d{10}$/
                    //             }else {
                    //                 return field.regex = /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/
                    //             }
                    //         }
                    //     }
                    // }
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    id: 'orgLineStatusRadioBox',
                    defaults: {
                        width:100,
                        padding:'0 10'
                    },
                    layout: 'hbox',
                    items: [
                        {
                            boxLabel: '可用',
                            name: 'status',
                            inputValue: 'start_using',
                            id: 'orgLineStatusRadioOne'
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            id: 'orgLineStatusRadioTwo'
                        }
                    ]
                },
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText:'最多允许输入200个字'
                }
            ]
        }
    ]
});


Ext.define('App.orgLineStation.orgLineWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 600,
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
    xtype: 'orgLineWindow',
    controller: 'orgLineController',
    items: [
        {
            xtype: 'orgLineForm'
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