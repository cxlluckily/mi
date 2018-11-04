Ext.define('App.menu.menuAddOrEditForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'menuAddOrEditForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 100,
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
                    name: 'functionTreeId',
                    fieldLabel: 'functionTreeId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'parentTreeId',
                    fieldLabel: 'parentTreeId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'treeName',
                    fieldLabel: '<font color="red">*</font>节点名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    maxLength: 32
                },
                {
                    xtype: 'textfield',
                    name: 'xclass',
                    fieldLabel: '路由路径',
                    labelAlign: 'right',
                    autoHeight: true,
                    // allowBlank: false,
                    maxLength: 128
                },
                // {
                //     xtype: 'textfield',
                //     name: 'id',
                //     fieldLabel: '前端页签ID',
                //     labelAlign: 'right',
                //     autoHeight: true,
                //     // allowBlank: false,
                //     maxLength: 64
                // },
                {
                    xtype: 'textfield',
                    name: 'permissionCode',
                    fieldLabel: '权限代码',
                    labelAlign: 'right',
                    autoHeight: true,
                    // allowBlank: false,
                    maxLength: 256
                },
                {
                    xtype: 'textfield',
                    name: 'iconCls',
                    fieldLabel: '<font color="red">*</font>图标',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    maxLength: 128
                },
                {
                    xtype: 'numberfield',
                    name: 'sort',
                    fieldLabel: '<font color="red">*</font>排序位置',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    regex: App.common.regExpValidator.integer,
                    regexText : App.common.regExpValidator.integerMsg,
                    maxLength: 3
                }
                ,
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '<font color="red">*</font>功能树分类',
                    defaultType: 'radiofield',
                    defaults: {
                        width: 100,
                        padding: '0 10'
                    },
                    layout: 'hbox',
                    items: [
                        {
                            boxLabel: '电脑端',
                            name: 'treeType',
                            inputValue: 'manage',
                        },
                        {
                            boxLabel: '移动端',
                            name: 'treeType',
                            inputValue: 'phone',
                        }
                    ]
                }
                ,
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '<font color="red">*</font>状态',
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


Ext.define('App.menu.menuAddOrEditWindow', {
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
    xtype: 'menuAddOrEditWindow',
    controller: 'menuController',
    items: [
        {
            xtype: 'menuAddOrEditForm'
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