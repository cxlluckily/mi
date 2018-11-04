Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowLookOutGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'stockOutStockApplyBorrowLookOutGrid',
    fullscreen: true,
    height: 350,
    style: {
        border: "1px solid #5fa2dd"
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
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1
        },
        {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1
        },
        {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1
        },
        {
            text: '型号',
            dataIndex: 'specificationModel',
            flex: 1,
        },
        // {
        //     text: '仓库',
        //     dataIndex: 'warehouseName',
        //     flex: 1
        // },
        {
            text: '房间号',
            dataIndex: 'houseNo',
            flex: 1
        },
        {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            flex: 1
        },
        {
            text: '发放数量',
            dataIndex: 'outCount',
            flex: 1
        },
        {
            text: '序列号',
            dataIndex: 'serialNumber',
            flex: 1
        },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer:function (value, record) {
                if (value == "notUnique") {
                    return "非唯一标识"
                }
                if (value == "unique") {
                    return "唯一标识"
                }
            }
        },
        {
            text: '状态',
            dataIndex: 'stockStatus',
            flex: 1,
            renderer: function (value, record) {
                if (value == "normal") {
                    return "好件"
                }
                if (value == "bad") {
                    return "坏件"
                }
            }
        },
        {
            text: '内部二维码',
            dataIndex: 'qrCode',
            width: 200
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowLookDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    // height:200,
    xtype: 'stockOutStockApplyBorrowLookDetailForm',
    autoScroll: true,
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
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'outStockApplyNO',
                    fieldLabel: '出库单号'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'warehouseName',
                    fieldLabel: '出库仓库'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'outUser',
                    fieldLabel: '出库人'
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:.5,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'outDate',
                    fieldLabel: '出库时间',
                    renderer: function (value) {
                        if (value) {
                            function timetrans(date) {
                                var date = new Date(date);
                                Y = date.getFullYear() + '-';
                                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                                D = ('0' + date.getDate()).slice(-2) + ' ';
                                h = ('0' + date.getHours()).slice(-2) + ':';
                                m = ('0' + date.getMinutes()).slice(-2) + ':';
                                s = ('0' + date.getSeconds()).slice(-2);
                                return Y + M + D + h + m + s;
                            }

                            return timetrans(value)
                        }
                        return '';
                    }
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth:1,
            items: [
                {
                    xtype: 'displayfield',
                    name: 'remark',
                    fieldLabel: '备注'
                },
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    xtype: 'textfield',
                    name: 'outApplyStatus',
                    fieldLabel: 'outApplyStatus',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyId',
                    fieldLabel: 'applyId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyUser',
                    fieldLabel: 'applyUser',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'partCount',
                    fieldLabel: 'partCount',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseId',
                    fieldLabel: 'warehouseId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'outStockApplyId',
                    fieldLabel: 'outStockApplyId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'outUser',
                    fieldLabel: 'outUser',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'applyDate',
                    fieldLabel: 'applyDate',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'warehouseName',
                    fieldLabel: 'warehouseName',
                    hidden: true
                }
            ]
        }
    ]
});

Ext.define('App.stockOutStockApply.stockOutStockApplyBorrowLookWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1100,
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
    xtype: 'stockOutStockApplyBorrowLookWindow',
    controller: 'stockOutStockApplyController',
    items: [
        {
            xtype: 'stockOutStockApplyBorrowLookDetailForm'
        },
        {
            xtype: 'stockOutStockApplyBorrowLookOutGrid'
        }
    ],
    buttons: [
        {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});