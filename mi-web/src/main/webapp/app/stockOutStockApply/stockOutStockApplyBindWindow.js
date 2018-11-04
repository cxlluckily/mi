Ext.define('App.stockOutStockApply.stockOutStockApplyBindForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockOutStockApplyBindForm',
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
                    name: 'gridDataCopy',
                    fieldLabel: 'gridDataCopy'
                },
                {
                    xtype: 'textfield',
                    name: 'outApplyStatus',
                    fieldLabel: 'outApplyStatus'
                },
                {
                    xtype: 'textfield',
                    name: 'applyId',
                    fieldLabel: 'applyId'
                },
                {
                    xtype: 'textfield',
                    name: 'outStockApplyNO',
                    fieldLabel: 'outStockApplyNO'
                },
                {
                    xtype: 'textfield',
                    name: 'applyUser',
                    fieldLabel: 'applyUser'
                },
                {
                    xtype: 'textfield',
                    name: 'partCount',
                    fieldLabel: 'partCount'
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseId',
                    fieldLabel: 'warehouseId'
                },
                {
                    xtype: 'textfield',
                    name: 'outStockApplyId',
                    fieldLabel: 'outStockApplyId'
                },
                {
                    xtype: 'textfield',
                    name: 'outUser',
                    fieldLabel: 'outUser'
                },
                {
                    xtype: 'textfield',
                    name: 'applyDate',
                    fieldLabel: 'applyDate'
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseName',
                    fieldLabel: 'warehouseName'
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBindGridStore', {
    extend: 'App.common.commonStore',
    // model: "App.stockOutStockApply.stockOutStockApplyModel",
    pageSize: 0,
    proxy: {
        url: ctx + 'outStockApply/getBindqrCodeStockInfo.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var view = Ext.getCmp(store.viewId);
            var formValues = view.up('window').down('form').getForm().getValues();
            Ext.apply(store.proxy.extraParams, formValues);
        },
        load: function (store, records, successful, operation, eOpts) {
            var form = Ext.ComponentQuery.query('stockOutStockApplyBindForm')[0];
            var gridData = [];
            Ext.each(records,function (item) {
                gridData.push(item.data)
            });
            var gridDataCopy = JSON.stringify(gridData);
            form.getForm().setValues({gridDataCopy:gridDataCopy});
        }
    }
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBindGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        // 'App.common.selectStore',//通用下拉列表数据，可不引用
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyBindGridStore'
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyBindGrid',
    fullscreen: true,
    height: 400,
    style: {
        border: "1px solid #5fa2dd"
    },
    listeners: {
        beforerender: function () {
            this.store.viewId = this.id;
            this.store.load();
        }
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: 'stockId',
            dataIndex: 'stockId',
            flex: 1,
            hidden: true
        },
        {
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        // {
        //     text: '名称',
        //     dataIndex: 'partName',
        //     flex: 1
        // },
        {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        // {
        //     text: '设备编号',
        //     dataIndex: 'equipmentNO',
        //     flex: 1
        // },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1,
            hidden: true
        },
        {
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1,
            // hidden: true
        },
        {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            hidden: true
        },
        {
            text: '房间号',
            dataIndex: 'houseNo',
            width: 100
        },
        {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            width: 100
        },
        // {
        //     text: '序列号',
        //     dataIndex: 'serialNumber',
        //     flex: 1
        // },
        {
            text: '内部二维码',
            dataIndex: 'qrCode',
            width: 270,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'qrCode'
                }
            }
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBindWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
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
    xtype: 'stockOutStockApplyBindWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyBindForm',
            hidden: true
        },
        {
            xtype: 'stockOutStockApplyBindGrid'
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