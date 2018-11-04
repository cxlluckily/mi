Ext.define('App.stockInStock.stockInStockNewLookDetailGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    xtype: 'stockInStockNewLookDetailGrid',
    fullscreen: true,
    height: 400,
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
            text: '供应商',
            dataIndex: 'supplierName',
            flex: 1
        },
        {
            text: '货架编号',
            dataIndex: 'shelfNumber',
            flex: 1,
            renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {
                return (record.data.houseNo||'') + ' ' + (record.data.shelfNumber||'');
            }
        },
        {
            text: '单价',
            dataIndex: 'price',
            flex: 1
        },
        {
            text: '入库数量',
            dataIndex: 'count',
            flex: 1
        }
    ]
});

Ext.define('App.stockInStock.stockInStockNewLookDetailForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInStockNewLookDetailForm',
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
                    xtype: 'container',
                    columnWidth: .3,
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
                    columnWidth: .3,
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
                    columnWidth: .3,
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
                    columnWidth: .3,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'warehouseName',
                            fieldLabel: '入库仓库'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .7,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'remark',
                            fieldLabel: '备注'
                        }
                    ]
                },
            ]
        }
    ]
});

Ext.define('App.stockInStock.stockInStockNewLookWindow', {
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
    xtype: 'stockInStockNewLookWindow',
    controller: 'stockInStockController',
    items: [
        {
            xtype: 'stockInStockNewLookDetailForm'
            // hidden:true
        },
        {
            xtype: 'stockInStockNewLookDetailGrid'
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