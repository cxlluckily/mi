Ext.define('App.stockInStock.stockInStockLookDetailGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockInStockLookDetailGrid',
    fullscreen: true,
    height: 350,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '类型',
            dataIndex: 'sparePartId',
            flex: 1,
            hidden: true
        },
        {
            text: 'outStockApplyDetailId',
            dataIndex: 'outStockApplyDetailId',
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
            hidden: true
        },
        {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex: 1
        },

        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                if (value == 'unique') {
                    return '唯一标识'
                }
                if (value == 'notUnique') {
                    return '非唯一标识'
                }
                return '';
            }
        },

        {
            text: '设备状态',
            dataIndex: 'status',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                if (value == 'normal') {
                    return '好件'
                }
                if (value == 'bad') {
                    return '坏件'
                }
                return '';
            }
        },
        {
            text: '二维码',
            dataIndex: 'qrCode',
            width: 200
        },
        {
            text: '供应商',
            dataIndex: 'supplierName',
            flex: 1
        },
        {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                return (record.data.houseNo || '') + ' ' + (record.data.shelfNumber || '');
            }
        },
        {
            text: '入库数量',
            dataIndex: 'inStockAcount',
            flex: 1
        }
    ]
});

Ext.define('App.stockInStock.stockInStockLookDetailTab', {
    extend: 'Ext.tab.Panel',
    xtype: 'stockInStockLookDetailTab',
    fullscreen: true,
    items: [
        {
            title: "入库单详情",
            inStockType: "inStock",
            xclass: "App.stockInStock.stockInStockLookDetailGrid"
        },
        {
            title: "入库备件详情",
            inStockType: "inStockDetail",
            xclass: "App.stockInStock.stockInStockLookDetailGrid"
        }
    ]
});

Ext.define('App.stockInStock.stockInStockLookDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInStockLookDetailForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '0 10 0 10',
        margin: '0',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'applyNo',
                            fieldLabel: '申请单号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'applyUser',
                            fieldLabel: '申请人'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'applyTime',
                            fieldLabel: '申请时间',
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
                    columnWidth: .33,
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
                    columnWidth: .33,
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
                    columnWidth: .33,
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
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'inStockApplyNo',
                            fieldLabel: '入库单号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'inStockUser',
                            fieldLabel: '入库人'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'inDate',
                            fieldLabel: '入库时间',
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
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'inWarehouseName',
                            fieldLabel: '入库仓库'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .33,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'outWarehouseName',
                            fieldLabel: '出库仓库'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'remark',
                            fieldLabel: '备注'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockInStock.stockInStockLookWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1200,
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
    xtype: 'stockInStockLookWindow',
    controller: 'stockInStockController',
    items: [
        {
            xtype: 'stockInStockLookDetailForm'
            // hidden:true
        },
        {
            xtype: 'stockInStockLookDetailTab'
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