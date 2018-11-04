Ext.define('App.stockOutStockApply.stockOutStockApplySelectAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockOutStockApplySelectAddForm',
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
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'sparePartId',
                            fieldLabel: 'sparePartId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'stockId',
                            fieldLabel: 'stockId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'supplierName',
                            fieldLabel: 'supplierName',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'serialNumber',
                            fieldLabel: 'serialNumber',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'specificationModel',
                            fieldLabel: 'specificationModel',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'inDate',
                            fieldLabel: 'inDate',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'specificationModel',
                            fieldLabel: 'specificationModel',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'materiaCoding',
                            fieldLabel: 'materiaCoding',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'equipmentNO',
                            fieldLabel: 'equipmentNO',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'categoryName',
                            fieldLabel: 'categoryName',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'partName',
                            fieldLabel: 'partName',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'account',
                            fieldLabel: 'account',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'inventoryType',
                            fieldLabel: 'inventoryType',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'qrCode',
                            fieldLabel: 'qrCode',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'size',
                            fieldLabel: 'size',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'houseNo',
                            fieldLabel: 'houseNo',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'shelfNumber',
                            fieldLabel: 'shelfNumber',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'category',
                            fieldLabel: 'category',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'brand',
                            fieldLabel: 'brand',
                            hidden: true
                        },
                        // {
                        //     xtype: 'textfield',
                        //     name: 'outCount',
                        //     fieldLabel: 'outCount',
                        //     hidden: true
                        // },
                        {
                            xtype: 'numberfield',
                            name: 'outCount',
                            fieldLabel: '发放数量'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplySelectAddWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 300,
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
    xtype: 'stockOutStockApplySelectAddWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplySelectAddForm'
        }
    ],
    buttons: [
        {
            text: '确认',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});