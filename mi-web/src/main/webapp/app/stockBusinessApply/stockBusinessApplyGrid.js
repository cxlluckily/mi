Ext.define('App.stockBusinessApply.stockBusinessApplyGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.stockBusinessApply.stockBusinessApplyStore',
        'App.stockBusinessApply.stockBusinessApplyController',
        'App.stockBusinessApply.stockBusinessApplyDetailWindow',
        'App.stockBusinessApply.stockBusinessApplyLookWindow',
        'App.stockBusinessApply.stockBusinessApplySelectWindow',
        'App.stockBusinessApply.stockBusinessApplySelectAddWindow'
    ],
    store: {
        xclass: 'App.stockBusinessApply.stockBusinessApplyStore'
    },
    controller: {
        xclass: 'App.stockBusinessApply.stockBusinessApplyController'
    },
    xtype: 'stockBusinessApplyGrid',
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
                                    emptyText: '--请选择--',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
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
                            columnWidth: .4,
                            layout: 'form',
                            border: false,
                            items: []
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
                                    // altFormats: "Y/m/d|Ymd"
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
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh',
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查看',
                                    action: 'look',
                                    iconCls: 'fa-eye',
                                    disabled: true
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '删除',
                                    action: 'delete',
                                    iconCls: 'fa-minus-circle',
                                    disabled: true
                                }
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
        }, {
            text: '单据号',
            dataIndex: 'applyNo',
            width: 180
        }, {
            text: '申请人',
            dataIndex: 'applyUser',
            flex: 1,
            hidden: true
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
        }, {
            text: '备注',
            dataIndex: 'applyRemark',
            flex: 1
        }, {
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
        }, {
            text: '审核意见',
            dataIndex: 'auditRemark',
            flex: 1
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length < 1) {
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="delete"]').disable();
                        sel.view.ownerCt.down('button[action="look"]').disable();
                    } else {
                        sel.view.ownerCt.down('button[action="look"]').enable();
                        if (selected[0].data.applyStatus == 'toBeReview') {
                            sel.view.ownerCt.down('button[action="update"]').enable();
                            sel.view.ownerCt.down('button[action="delete"]').enable();
                        } else {
                            sel.view.ownerCt.down('button[action="update"]').disable();
                            sel.view.ownerCt.down('button[action="delete"]').disable();
                        }
                    }
                }
            }
        }
    }
});