Ext.define('App.stockInventoryTask.stockInventoryTaskAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'stockInventoryTaskAddForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'form',
            items: [
                {
                    xtype: "datefield",
                    name: "beginTIme",
                    fieldLabel: "盘点开始",
                    editable: false,
                    emptyText: "--请选择--",
                    format: "Y-m-d",//日期的格式
                    value: Ext.util.Format.date(Ext.Date.add(new Date())),
                    listeners: {
                        beforerender: function (combo) {
                            combo.setValue(Ext.util.Format.date(Ext.Date.add(new Date(combo.value))));
                        }
                    }
                },
                {
                    xtype: "datefield",
                    name: "endTIme",
                    fieldLabel: "盘点结束",
                    editable: false,
                    emptyText: "--请选择--",
                    format: "Y-m-d",//日期的格式
                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, +1)),
                    listeners: {
                        beforerender: function (combo) {
                            combo.setValue(Ext.util.Format.date(Ext.Date.add(new Date(combo.value))));
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'headPerson',
                    fieldLabel: '负责人',
                    maxLength: 16,
                    editable: false,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'inventoryTaskId',
                    fieldLabel: 'inventoryTaskId',
                    maxLength: 16,
                    editable: false,
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '仓库',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.userStockWarehouseListStore',
                        autoLoad: true
                    },
                    queryMode: 'local',
                    name: 'warehouseId',
                    editable: false,
                    displayField: 'text',
                    valueField: 'id',
                    allowBlank: false,
                    blankText: '请选择仓库!',
                    hideTrigger: false,
                    listeners: {
                        beforerender: function (combo) {
                            var extWindow = combo.up('window');
                            if (extWindow.viewAction == 'update') {
                                combo.editable = false;
                                combo.readOnly = true;
                                // combo.hideTrigger = true;
                                combo.setHideTrigger(true);
                                combo.disable = true;
                            }
                        },
                        change(combo, record, eOpts) {
                            let headPersonUserIdCombo = combo.up('form').down('combo[name=headPersonUserId]');
                            let headPersonField = combo.up('form').down('textfield[name=headPerson]');
                            if (record.data) {
                                var id = record.data[combo.valueField];
                                headPersonField.setValue('');
                                headPersonUserIdCombo.setValue('');
                            } else {
                                var id = record;
                            }
                            // let name = record.data[combo.displayField];
                            headPersonUserIdCombo.store.setData([]);
                            headPersonUserIdCombo.store.proxy.extraParams.warehouseId = id;
                            headPersonUserIdCombo.store.load();
                        }
                    }
                },
                {
                    xtype: 'combo',
                    fieldLabel: '负责人',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.findUserByWarehousePermission'
                    },
                    queryMode: 'local',
                    name: 'headPersonUserId',
                    editable: false,
                    displayField: 'realName',
                    valueField: 'userId',
                    allowBlank: false,
                    blankText: '请选择负责人!',
                    listeners: {
                        select(combo, record, eOpts) {
                            let userName = record.data[combo.displayField];
                            let headPersonField = combo.up('form').down('textfield[name=headPerson]');
                            headPersonField.setValue(userName);
                        }
                    }
                },
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    anchor: '80%',
                    allowBlank: true,
                    maxLength: 200,
                    maxLengthText: '不能超过200个字'
                }
            ]
        }
    ]
});

Ext.define('App.stockInventoryTask.stockInventoryTaskAddWindow', {
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
    // autoScroll:true,
    // bodyStyle: {
    //     'overflow-y': 'auto',
    //     'overflow-x': 'hidden'
    // },
    buttonAlign: "center",
    xtype: 'stockInventoryTaskAddWindow',
    controller: 'stockInventoryTaskController',
    items: [
        {
            xtype: 'stockInventoryTaskAddForm'
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