Ext.define('App.shelf.shelfForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'shelfForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 90,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: []
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'combo',
                    emptyText: '所属仓库',
                    queryMode: 'local',
                    name: 'warehouseId',
                    editable: false,
                    store: {
                        xclass: 'App.common.userStockWarehouseListStore',
                        autoLoad: true
                    },
                    displayField: 'text',
                    valueField: 'code',
                    fieldLabel: '所属仓库',
                    listeners: {
                        render: function (combo) { //渲染
                            combo.getStore().on("load", function (s, r, o) {
                                var firstValue = combo.getStore().getData().items[0][combo.valueField];
                                combo.setValue(firstValue);
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'houseNo',
                    fieldLabel: '房间号',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入房间号!',
                    maxLength: 16
                },
                {
                    xtype: 'combo',
                    queryMode: 'local',
                    name: 'containerType',
                    editable: false,
                    store: {
                        data: [
                            {
                                id: "Shelf",
                                name: "货架"
                            }, {
                                id: "Container",
                                name: "货柜"
                            }
                        ]
                    },
                    displayField: 'name',
                    valueField: 'id',
                    fieldLabel: '类型',
                    listeners: {
                        beforerender: function (combo) { //渲染
                            var firstValue = combo.getStore().getData().items[0].id;
                            combo.setValue(firstValue);
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'code',
                    fieldLabel: '货架/货柜编号',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入货架/货柜编号!',
                    maxLength: 10
                },
                {
                    xtype: 'textfield',
                    name: 'layerNumber',
                    fieldLabel: '层数',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入层数!',
                    maxLength: 16,
                    regex: App.common.regExpValidator.natural,
                    regexText: App.common.regExpValidator.naturalText
                },
                {
                    xtype: 'textfield',
                    name: 'cellNumber',
                    fieldLabel: '格数',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入格数!',
                    maxLength: 16,
                    regex: App.common.regExpValidator.natural,
                    regexText: App.common.regExpValidator.naturalText
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    id: 'shelfStatusRadioBox',
                    defaults: {
                        width: 100,
                        padding: '0 10'
                    },
                    layout: 'hbox',
                    items: [{
                        boxLabel: '可用',
                        name: 'status',
                        inputValue: 'start_using',
                        id: 'shelfStatusRadioOne'
                    },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            id: 'shelfStatusRadioTwo'
                        }
                    ]
                }, {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText: '最多允许输入200个字'
                }
            ]
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


Ext.define('App.shelf.shelfWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 610,
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
    xtype: 'shelfWindow',
    controller: 'shelfController',
    items: [{
        xtype: 'shelfForm'
    }]
});