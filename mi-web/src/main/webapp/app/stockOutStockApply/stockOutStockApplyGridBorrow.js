Ext.define('App.stockOutStockApply.stockOutStockApplyGridBorrow', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.stockOutStockApply.stockOutStockApplyStore',
        'App.stockOutStockApply.stockOutStockApplyController'
    ],
    store: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyStore'
    },
    controller: {
        xclass: 'App.stockOutStockApply.stockOutStockApplyController'
    },
    xtype: 'stockOutStockApplyGridBorrow',
    listeners: {
        beforerender: function () {
            this.store.viewId = this.id;
            this.store.outOrderType = this.outOrderType;
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
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '仓库',
                                    emptyText: '--请选择--',
                                    store: {
                                        xclass: 'App.common.userStockWarehouseListStore',
                                        // listeners:{
                                        //     load:function (store, records, successful, operation, eOpts) {
                                        //         records.unshift({id:'0',text:'全部'});
                                        //         store.setData(records);
                                        //     }
                                        // }
                                    },
                                    queryMode: 'local',
                                    name: 'outWarehouseId',
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
                                                r.unshift({id: '0', text: '全部'});
                                                s.setData(r);
                                                combo.setValue(r[0][combo.valueField]);
                                                var p = combo.up('grid');
                                                p.getController().clickSearch();
                                            });
                                        },
                                        select: function (tf, nval, oval, opts) {
                                            tf.up('grid').getController().clickSearch();
                                        },
                                        change: function (tf, nval, oval, opts) {
                                            if (nval != 0 && !nval) {
                                                tf.setValue('0');
                                            } else {
                                                tf.up('grid').getController().clickSearch();
                                            }
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '单据号',
                                    name: 'outStockApplyNO'
                                }
                            ]
                        },
                        {
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '领取人',
                                    name: 'applyUser',
                                    hidden: true
                                }
                            ]
                        },
                        {
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '状态',
                                    emptyText: '--请选择--',
                                    value:'alreadyOut',
                                    store: {
                                        data: [
                                            {
                                                id: "alreadyOut",
                                                name: "已出库"
                                            }
                                        ]
                                    },
                                    hidden: true,
                                    queryMode: 'local',
                                    name: 'outApplyStatus',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var outOrderType = combo.up('grid').store.outOrderType;
                                            if (outOrderType == 'borrowOut') {
                                                combo.getStore().setData([{
                                                    id: "alreadyOut",
                                                    name: "已出库"
                                                }])
                                            }
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
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "beginTime",
                                    fieldLabel: "出库开始时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                                    // altFormats: "Y/m/d|Ymd"
                                }
                            ]
                        },
                        {
                            columnWidth: .35,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "endTime",
                                    fieldLabel: "出库结束时间",
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
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'reset',
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
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '出库',
                                    action: 'out',
                                    iconCls: 'fa-sign-out'
                                },
                                // {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '二维码',
                                //     action: 'bind',
                                //     iconCls: 'fa-qrcode',
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
            text: 'outStockApplyId',
            dataIndex: 'outStockApplyId',
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
            text: 'operationSubjectId',
            dataIndex: 'operationSubjectId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: 'outWarehouseId',
            dataIndex: 'outWarehouseId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: 'outOrderType',
            dataIndex: 'outOrderType',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '出库仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        }, {
            text: '单据号',
            dataIndex: 'outStockApplyNO',
            flex: 1,
            align: 'left'
        }, {
            text: '出库人',
            dataIndex: 'outUser',
            flex: 1,
            align: 'left'
        }, {
            text: '出库人ID',
            dataIndex: 'outUserId',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            align: 'left'
        },
        {
            text: '出库时间',
            dataIndex: 'outDate',
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
            text: '出库备件数',
            dataIndex: 'partCount',
            flex: 1,
            align: 'left'
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
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'borrowLook',
                    handler: 'actionFn'
                },
                {
                    tooltip: '二维码',
                    iconCls: "fa-qrcode",
                    flag: 'bind',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.inStockStatus !== 'alreadyIn') {
                            return 'fa-qrcode';
                        } else {
                            return 'x-hidden';
                        }
                    }
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
            //             sel.view.ownerCt.down('button[action="bind"]').disable();
            //             sel.view.ownerCt.down('button[action="look"]').disable();
            //         } else {
            //             sel.view.ownerCt.down('button[action="bind"]').enable();
            //             sel.view.ownerCt.down('button[action="look"]').enable();
            //         }
            //     }
            // }
        }
    }
});