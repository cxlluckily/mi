Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditStore',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditController'
    ],
    store: {
        xclass: 'App.stockBusinessApplyAudit.stockBusinessApplyAuditStore'
    },
    controller: {
        xclass: 'App.stockBusinessApplyAudit.stockBusinessApplyAuditController'
    },
    xtype: 'stockBusinessApplyAuditGrid',
    listeners: {
        beforerender: function () {
            this.store.viewId = this.id;
            this.store.applyType = this.applyType;
            this.store.load();
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
                            columnWidth: .3,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '单据号',
                                    name: 'documentNo'
                                }
                            ]
                        },
                        
                        {
                            columnWidth: .3,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '状态',
                                    value:'toBeReview',
                                    emptyText: '待审核',
                                    store: {
                                        data: [
                                            {
                                                id: "toBeReview",
                                                name: "待审核"
                                            },
                                            {
                                                id: "reviewPass",
                                                name: "审核通过"
                                            },
                                            {
                                                id: "reviewNoPass",
                                                name: "审核不通过"
                                            },
                                            {
                                                id: "toBeOut",
                                                name: "待领取"
                                            },
                                            {
                                                id: "toBeIn",
                                                name: "待归还"
                                            },
                                            {
                                                id: "over",
                                                name: "已完成"
                                            },
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'applyStatus',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        change: function (tf, nval, oval, opts) {
                                            tf.up('grid').getController().clickSearch();
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .3,
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
                            columnWidth: .3,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "beginTime",
                                    fieldLabel: "申请开始时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                                }
                            ]
                        },
                        {
                            columnWidth: .3,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "endTime",
                                    fieldLabel: "申请结束时间",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date()))
                                }
                            ]
                        },
                        {
                            columnWidth: .39,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'reset',
                                    iconCls: 'fa-refresh'
                                },
                                // {
                                //     margin: 10,
                                //     xtype: 'button',
                                //     text: '审核',
                                //     action: 'update',
                                //     iconCls: 'fa-edit',
                                //     disabled: true
                                // },
                                // {
                                //     margin: 10,
                                //     xtype: 'button',
                                //     text: '查看',
                                //     action: 'look',
                                //     iconCls: 'fa-eye',
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
            text: 'applyId',
            dataIndex: 'applyId',
            flex: 1,
            hidden: true
        }, {
            text: 'outWarehouseId',
            dataIndex: 'outWarehouseId',
            flex: 1,
            hidden: true
        }, {
            text: 'inWarehouseId',
            dataIndex: 'inWarehouseId',
            flex: 1,
            hidden: true
        }
        , {
            text: '出库仓库',
            dataIndex: 'outWarehouseName',
            width: 150
        }, {
            text: '单据号',
            dataIndex: 'applyNo',
            width: 180
        }, {
            text: '申请人',
            dataIndex: 'applyUser',
            flex: 1,
        }, {
            text: 'applyUserId',
            dataIndex: 'applyUserId',
            flex: 1,
            hidden: true
        },
        {
            text: '申请时间',
            dataIndex: 'applyTime',
            width: 150,
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
        // , {
        //     text: '备注',
        //     dataIndex: 'applyRemark',
        //     flex: 1
        // }
        ,
        {
            text: '状态',
            dataIndex: 'applyStatus',
            flex: 1,
            renderer: function (value, record) {
                if (value == "toBeReview") {
                    return "待审核"
                } else if (value == "reviewPass") {
                    return "审核通过"
                } else if (value == "reviewNoPass") {
                    return "审核不通过"
                } else if (value == "over") {
                    return "已完成"
                } else if (value == "toBeOut") {
                    return "待出库"
                } else if (value == "toBeIn") {
                    return "待入库"
                }
            }
        }, {
            text: '申请类型',
            dataIndex: 'applyType',
            flex: 1,
            renderer: function (value, record) {
                if (value == "use") {
                    return "领用申请"
                }
                if (value == "transfer") {
                    return "调拨申请"
                }
                if (value == "return") {
                    return "返还申请"
                }
            }
        }, {
            text: '审核人',
            dataIndex: 'auditUser',
            flex: 1
        }, {
            text: 'auditUserId',
            dataIndex: 'auditUserId',
            flex: 1,
            hidden: true
        }, {
            text: '审核时间',
            dataIndex: 'auditTime',
            width: 150,
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
                    tooltip: '审核',
                    iconCls: "fa-check-circle-o",
                    flag: 'update',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.applyStatus == 'toBeReview') {
                            return 'fa-check-circle-o';
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
                }
            ]
        }
        // , {
        //     text: '审核意见',
        //     dataIndex: 'auditRemark',
        //     flex: 1
        // }
    ],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            // selectionchange: {
            //     fn: function (sel, selected, eOpts) {
            //         if (selected.length < 1) {
            //             sel.view.ownerCt.down('button[action="update"]').disable();
            //             sel.view.ownerCt.down('button[action="look"]').disable();
            //         } else {
            //             if (sel.view.ownerCt.down('form').getValues().applyStatus == 'toBeReview') {
            //                 sel.view.ownerCt.down('button[action="update"]').enable();
            //                 sel.view.ownerCt.down('button[action="look"]').disable();
            //             } else {
            //                 sel.view.ownerCt.down('button[action="update"]').disable();
            //                 sel.view.ownerCt.down('button[action="look"]').enable();
            //             }
            //         }
            //     }
            // }
        }
    }
});