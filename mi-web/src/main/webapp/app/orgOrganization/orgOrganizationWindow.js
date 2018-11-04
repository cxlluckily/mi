Ext.define('App.orgOrganization.orgOrganizationForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'orgOrganizationForm',
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
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    name: 'parentOrgId',
                    fieldLabel: '父节点ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    blankText: '请输入父节点ID!',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'orgId',
                    fieldLabel: '节点ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    blankText: '请输入节点ID!',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'orgName',
                    fieldLabel: '<font color="red">*</font>机构名称',
                    // style:{
                    //     background:"#ff6700"
                    // },
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入机构名称!',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'headPerson',
                    fieldLabel: '负责人',
                    labelAlign: 'right',
                    autoHeight: true,
                    blankText: '请输入负责人!',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'contactNumber',
                    fieldLabel: '联系电话',
                    labelAlign: 'right',
                    autoHeight: true,
                    // blankText: '请输入联系电话!',
                    // regex: /^1\d{10}$/,
                    // regexText:'请输入11位有效的号码',
                    // listeners: {
                    //     change: function (field, newValue, oldValue) {
                    //         if(newValue) {
                    //             var mobileTrue = /^1(3|4|5|7|8)\d{9}$/.test(newValue);
                    //             if(mobileTrue) {
                    //                 return field.regex = /^1(3|4|5|7|8)\d{9}$/
                    //             }else {
                    //                 return field.regex = /([0-9]{3,4}-)?[0-9]{7,8}/
                    //             }
                    //         }
                    //     }
                    // }
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    id: 'orgOrganizationStatusRadioBox',
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
                            id: 'orgOrganizationStatusRadioOne'
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            id: 'orgOrganizationStatusRadioTwo'
                        }
                    ]
                },
                {
                    xtype: 'textareafield',
                    name: 'description',
                    fieldLabel: '机构描述',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 400,
                    maxLengthText:'最多可输入400个字'
                }
            ]
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


Ext.define('App.orgOrganization.orgOrganizationWindow', {
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
    xtype: 'orgOrganizationWindow',
    controller: 'orgOrganizationController',
    items: [
        {
            xtype: 'orgOrganizationForm'
        }
    ]
});