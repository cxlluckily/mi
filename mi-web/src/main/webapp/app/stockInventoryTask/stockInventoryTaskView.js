Ext.define('App.stockInventoryTask.stockInventoryTaskView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.stockInventoryTask.stockInventoryTaskStore',
        'App.stockInventoryTask.stockInventoryTaskController',
        'App.stockInventoryTask.stockInventoryTaskAddWindow',
        'App.stockInventoryTask.stockInventoryTaskUpdateWindow',
        'App.stockInventoryTask.stockInventoryTaskLookWindow',
    ],
    store: {
        xclass: 'App.stockInventoryTask.stockInventoryTaskStore'
    },
    controller: {
        xclass: 'App.stockInventoryTask.stockInventoryTaskController'
    },
    xtype: 'stockInventoryTaskView',
    fullscreen: true,
    tbar: [
        {
            id: 'stockInventoryTaskViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 900,
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
                                    xtype: 'textfield',
                                    fieldLabel: '仓库',
                                    name: 'warehouseName'
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
                                    fieldLabel: '负责人',
                                    name: 'headPerson'
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
                                                id: "before",
                                                name: "待盘库"
                                            },
                                            {
                                                id: "ongoing",
                                                name: "盘库中"
                                            },
                                            {
                                                id: "already",
                                                name: "盘库完成"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'status',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        change: function (combo, newValue, oldValue, eOpts) {
                                            if (!newValue) {
                                                combo.setValue('all');
                                            }
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
                                    fieldLabel: "盘库开始",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -3))
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
                                    fieldLabel: "盘库结束",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, +3))
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
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
                                    iconCls: 'fa-refresh'
                                },
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus'
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
        },
        {
            text: 'inventoryTaskId',
            dataIndex: 'inventoryTaskId',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: 'warehouseId',
            dataIndex: 'warehouseId',
            flex: 1,
            align: 'left',
            hidden: true
        },
        {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        },
        {
            text: '批次号',
            dataIndex: 'batchNo',
            flex: 1,
            align: 'left'
        },
        {
            text: '负责人',
            dataIndex: 'headPerson',
            flex: 1,
            align: 'left'
        }, {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            align: 'left',
            renderer: function (value) {
                if (value == 'before') {
                    return '待盘库'
                }
                if (value == 'ongoing') {
                    return '盘库中'
                }
                if (value == 'already') {
                    return '盘库完成'
                }
                return ''
            }
        }, {
            text: '开始时间',
            dataIndex: 'beginTIme',
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
                        return Y + M + D;
                    }

                    return timetrans(value)
                }
                return '';
            }
        },
        {
            text: '结束时间',
            dataIndex: 'endTIme',
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
                        return Y + M + D;
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
            // handler: 'onEditRowAction',
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
                    tooltip: '盘库',
                    iconCls: "fa-list-ul",
                    flag: 'inventory',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.status != 'already'&&r.data.headPerson == window.sessionStorage['realName']) {
                            return 'fa-list-ul';
                        } else {
                            return 'x-hidden';
                        }
                    }
                },
                {
                    tooltip: '修改',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.status == 'before'&&r.data.createUser == window.sessionStorage['realName']) {
                            return 'fa-edit';
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
                    //     if (r.data.status == 'already') {
                    //         return 'fa-eye';
                    //     } else {
                    //         return 'x-hidden';
                    //     }
                    // }
                }
            ]
        }
    ]
});