Ext.define('App.partSparePartType.partSparePartTypeForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'partSparePartTypeForm',
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
                    name: 'operationSubjectId',
                    fieldLabel: '运营主体ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'sparePartTypeIds',
                    fieldLabel: 'sparePartTypeIds',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'parentPartId',
                    fieldLabel: '父节点ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'sparePartTypeId',
                    fieldLabel: '节点ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                },
                {
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'categoryName',
                            fieldLabel: '<font color="red">*</font>分类名称',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入分类名称!',
                            maxLength: 30
                        },
                    ]
                },
                {
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'sort',
                            fieldLabel: '排序位置',
                            labelAlign: 'right',
                            regex: App.common.regExpValidator.integer,
                            regexText : App.common.regExpValidator.integerMsg,
                            maxLength: 3
                        },
                    ]
                },
                {
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '状态',
                            defaultType: 'radiofield',
                            id: 'partSparePartTypeStatusRadioBox',
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
                                    id: 'partSparePartTypeStatusRadioOne'
                                },
                                {
                                    boxLabel: '不可用',
                                    name: 'status',
                                    inputValue: 'stop_using',
                                    id: 'partSparePartTypeStatusRadioTwo'
                                }
                            ]
                        },
                    ]
                },
                {
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
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


Ext.define('App.partSparePartType.partSparePartTypeWindow', {
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
    xtype: 'partSparePartTypeWindow',
    controller: 'partSparePartTypeController',
    items: [
        {
            xtype: 'partSparePartTypeForm'
        }
    ]
});