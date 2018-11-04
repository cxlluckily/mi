Ext.define('App.stockInStock.stockInStockGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass: 'App.stockInStock.stockInStockStore'
    },
    controller: {
        xclass: 'App.stockInStock.stockInStockController'
    },
    xtype: 'stockInStockGrid',
    listeners: {
        beforerender: function () {
            this.store.viewId = this.id;
            this.store.inStockType = this.inStockType;
            // this.store.load();
        }
    },
    fullscreen: true,
    tbar: [
        {
            xtype: 'form',
            fullscreen: true,
            width: 1000,
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    border: false,
                    style: {
                        backgroundColor: '#fff',
                        marginTop: '-1px',
                        marginBottom: '0'
                    },
                    items: [
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '仓库',
                                    emptyText: '--请选择--',
                                    store: {
                                        xclass: 'App.common.userStockWarehouseListStore',
                                        listeners:{
                                            load:function (store, records, successful, operation, eOpts) {
                                                records.unshift({id:'0',text:'全部'});
                                                store.setData(records);
                                            }
                                        }
                                    },
                                    queryMode: 'local',
                                    name: 'warehouseId',
                                    editable: false,
                                    displayField: 'text',
                                    valueField: 'id',
                                    // multiSelect: true,
                                    listeners: {
                                        beforerender: function (component) {
                                            component.store.load();
                                        },
                                        render: function (combo) {//渲染
                                            combo.getStore().on("load", function (s, r, o) {
                                                combo.setValue(r[0][combo.valueField]);
                                                var p = combo.up('grid');
                                                p.getController().clickSearch();
                                            });
                                        },
                                        select: function (tf, nval, oval, opts) {
                                            tf.up('grid').getController().clickSearch();
                                        }
                                    }
                                }
                            ]
                        },

                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '单据号',
                                    name: 'inStockApplyNo'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '申请人',
                                    name: 'applyUser'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '状态',
                                    emptyText: '--请选择--',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "toBeIn",
                                                name: "待入库"
                                            },
                                            {
                                                id: "alreadyIn",
                                                name: "已入库"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'inStockStatus',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        select: function (tf, nval, oval, opts) {
                                            tf.up('grid').getController().clickSearch();
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "beginTime",
                                    fieldLabel: "入库开始时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "endTime",
                                    fieldLabel: "入库结束时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date()))
                                    // altFormats: "Y/m/d|Ymd"
                                }
                            ]
                        },
                        {
                            columnWidth: 1,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },{
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                },
                                // {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '查看',
                                //     action: 'look',
                                //     iconCls: 'fa-eye',
                                //     disabled: true
                                // },
                                // {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '入库',
                                //     action: 'in',
                                //     iconCls: 'fa-sign-in',
                                //     disabled: true
                                // }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },

    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        }, {
            text: 'inStockId',
            dataIndex: 'inStockId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: 'applyId',
            dataIndex: 'applyId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: 'warehouseId',
            dataIndex: 'warehouseId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '仓库名称',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        }, {
            text: '单据号',
            dataIndex: 'inStockApplyNo',
            flex: 1,
            align: 'left'
        }, {
            text: '出库单号',
            dataIndex: 'outStockApplyNO',
            flex: 1,
            align: 'left'
        }, {
            text: '申请人',
            dataIndex: 'applyUser',
            flex: 1,
            align: 'left'
        }, {
            text: '申请日期',
            dataIndex: 'applyTime',
            flex: 1,
            align: 'left',
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
                    };
                    return timetrans(value);
                } else {
                    return '';

                }
            }
        }, {
            text: '入库人',
            dataIndex: 'inStockUser',
            flex: 1,
            align: 'left'
        }, {
            text: '领取人',
            dataIndex: 'applyUser',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '出库人ID',
            dataIndex: 'outUserId',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '入库时间',
            dataIndex: 'inDate',
            flex: 1,
            align: 'left',
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
                    };
                    return timetrans(value);
                } else {
                    return '';

                }
            }
        }, {
            text: '总备件数',
            dataIndex: 'sumOfCount',
            flex: 1,
            align: 'left'
        }, {
            text: '状态',
            dataIndex: 'inStockStatus',
            flex: 1,
            renderer: function (value, record) {
                if (value == "toBeIn") {
                    return "待入库"
                }
                else
                {
                    return "已入库"
                }
            }
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 100,
            menuDisabled: true,
            align: 'center',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                bodyStyle: 'padding:0px 10px'
                // margin: "0px 10px"
            },
            items: [
                {
                    tooltip: '入库',
                    iconCls: "fa-sign-in",
                    flag: 'in',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.inStockStatus == 'toBeIn') {
                            return 'fa-sign-in';
                        } else {
                            return 'x-hidden';
                        }
                    }
                },
                {
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'look',
                    handler: 'actionFn',
                    // getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                    //     if (r.data.inStockStatus !== 'toBeIn') {
                    //         return 'fa-eye';
                    //     } else {
                    //         return 'x-hidden';
                    //     }
                    // }
                }
            ]
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            // selectionchange: {
            //     fn: function (sel, selected, eOpts) {
            //         if (selected.length < 1) {
            //             sel.view.ownerCt.down('button[action="in"]').disable();
            //         } else {
            //             if (sel.view.ownerCt.down('form').getValues().inStockStatus !== 'toBeIn') {
            //                 sel.view.ownerCt.down('button[action="in"]').disable();
            //                 sel.view.ownerCt.down('button[action="look"]').enable();
            //             } else {
            //                 sel.view.ownerCt.down('button[action="in"]').enable();
            //                 sel.view.ownerCt.down('button[action="look"]').disable();
            //             }
            //         }
            //     }
            // }
        }
    }
});