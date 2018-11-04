Ext.define('App.stockOutStockApply.stockOutStockApplyGrid', {
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
    xtype: 'stockOutStockApplyGrid',
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
                                        listeners: {}
                                    },
                                    queryMode: 'local',
                                    name: 'outWarehouseId',
                                    editable: false,
                                    displayField: 'text',
                                    valueField: 'id',
                                    // multiSelect: true,
                                    listeners: {
                                        beforerender: function (component, ss) {
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
                                                tf.setValue(0);
                                            } else {
                                                tf.up('grid').getController().clickSearch();
                                            }
                                        }
                                    }
                                }
                            ]
                        },

                        // {
                        //     columnWidth: .25,
                        //     layout: 'form',
                        //     border: false,
                        //     items: [
                        //         {
                        //             xtype: 'combo',
                        //             fieldLabel: '测试',
                        //             emptyText: '--请选择--',
                        //             store: {
                        //                 xclass: 'App.common.getSparePartList'
                        //             },
                        //             queryMode: 'remote',
                        //             name: 'outWarehouseId',
                        //             displayField: 'partName',
                        //             valueField: 'sparePartId',
                        //             selectOnFocus: true,
                        //             // reference: 'states',
                        //             // publishes: 'value',
                        //             // typeAhead: true,
                        //             // autoSelect:true,
                        //             // typeAheadDelay: 200,
                        //             // lazyRender:true,
                        //             // forceSelection: true,//限定选择的值是列表中的 一个
                        //             // allowBlank : false,
                        //             // blankText:"请选择一个状态，状态不可以为空!",
                        //             // multiSelect: true,
                        //             listeners: {
                        //                 change: function (component, newValue, oldValue, eOpts) {
                        //                     component.store.searchContent = component.rawValue;
                        //                     component.store.load();
                        //                     component.expand();
                        //                 },
                        //                 blur:function(component, event, eOpts){
                        //                     if(typeof component.value == 'string'){
                        //                         var value = component.store.data.items[0].data[component.valueField];
                        //                         component.setValue(value);
                        //                     }
                        //                 }
                        //             }
                        //         }
                        //     ]
                        // },

                        {
                            columnWidth: .33,
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
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '申请单号',
                                    name: 'applyNo'
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
                                    fieldLabel: '领取人',
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
                                    emptyText: '全部',
                                    value: 'all',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "toBeOut",
                                                name: "待出库"
                                            },
                                            {
                                                id: "alreadyOut",
                                                name: "已出库"
                                            }
                                        ]
                                    },
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
                            columnWidth: .33,
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
                            columnWidth: .33,
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
                            columnWidth: .5,
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
                                // }, {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '出库',
                                //     action: 'out',
                                //     iconCls: 'fa-sign-out',
                                //     disabled: true
                                // }, {
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
            text: '申请单号',
            dataIndex: 'applyNo',
            flex: 1,
            align: 'left'
        }, {
            text: '申请日期',
            dataIndex: 'applyDate',
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
            text: '领取人',
            dataIndex: 'applyUser',
            width: 80,
            align: 'left'
        },
        {
            text: '出库人',
            dataIndex: 'outUser',
            width: 80,
            align: 'left'
        },
        {
            text: '出库人ID',
            dataIndex: 'outUserId',
            flex: 1,
            align: 'left',
            hidden: true
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
            width: 100,
            align: 'left'
        }, {
            text: '状态',
            dataIndex: 'outApplyStatus',
            flex: 1
        }
        , {
            text: '已出库数量',
            dataIndex: 'alreadyOutCount',
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
                    tooltip: '出库',
                    iconCls: "fa-sign-out",
                    flag: 'out',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.outApplyStatus == '待出库') {
                            return 'fa-sign-out';
                        } else {
                            return 'x-hidden';
                        }
                    }
                },
                {
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'look',
                    handler: 'actionFn'
                },
                {
                    tooltip: '二维码',
                    iconCls: "fa-qrcode",
                    flag: 'bind',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.outApplyStatus !== '待出库' && r.data.inStockStatus !== 'alreadyIn') {
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
            //             sel.view.ownerCt.down('button[action="out"]').disable();
            //             sel.view.ownerCt.down('button[action="bind"]').disable();
            //             sel.view.ownerCt.down('button[action="look"]').disable();
            //         } else {
            //             sel.view.ownerCt.down('button[action="look"]').enable();
            //             if (sel.view.ownerCt.down('form').getValues().outApplyStatus !== 'toBeOut') {
            //                 // 出库做好后修改
            //                 sel.view.ownerCt.down('button[action="out"]').disable();
            //                 // sel.view.ownerCt.down('button[action="bind"]').disable();
            //                 sel.view.ownerCt.down('button[action="bind"]').enable();
            //             } else {
            //                 sel.view.ownerCt.down('button[action="out"]').enable();
            //                 // sel.view.ownerCt.down('button[action="bind"]').enable();
            //                 sel.view.ownerCt.down('button[action="bind"]').disable();
            //             }
            //         }
            //     }
            // }
        }
    }
});