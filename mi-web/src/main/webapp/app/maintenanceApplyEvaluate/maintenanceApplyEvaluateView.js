Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateStore',
        'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateController',
        'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateWindow',
        'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookWindow',
        'App.maintenanceApply.maintenanceApplyLookOneWindow',
        'App.maintenanceApply.maintenanceApplyLookTwoWindow',
        'App.maintenanceApply.maintenanceApplyLookThreeWindow',
        'App.maintenanceApply.maintenanceApplyLookFourWindow',
    ],
    store: {
        xclass: 'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateStore'
    },
    controller: {
        xclass: 'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateController'
    },
    xtype: 'maintenanceApplyEvaluateView',
    fullscreen: true,
    tbar: [
        {
            id: 'maintenanceApplyEvaluateViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 1100,
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
                            columnWidth: .26,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '站点',
                                    name: 'stationName'
                                }
                            ]
                        },
                        {
                            columnWidth: .26,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '设备类型',
                                    name: 'categoryName'
                                }
                            ]
                        },
                        {
                            columnWidth: .26,
                            layout: 'form',
                            border: false,
                            hidden: true,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '维修人',
                                    name: 'maintenanceUser'
                                }
                            ]
                        },
                        {
                            columnWidth: .26,
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
                                                id: "repaired,rated",
                                                name: "全部"
                                            },
                                            {
                                                id: "repaired",
                                                name: "已修复"
                                            },
                                            {
                                                id: "rated",
                                                name: "已评价"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'applyStatus',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        change:function ( combo, newValue, oldValue, eOpts) {
                                            if(!newValue){
                                                combo.setValue('repaired,rated');
                                            }
                                        }
                                    }
                                }
                            ]
                        },

                        {
                            columnWidth: .26,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "beginTime",
                                    fieldLabel: "申报开始",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1))
                                }
                            ]
                        },
                        {
                            columnWidth: .26,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: "datefield",
                                    name: "endTime",
                                    fieldLabel: "申报结束",
                                    editable: false,
                                    emptyText: "--请选择--",
                                    format: "Y-m-d",//日期的格式
                                    value: Ext.util.Format.date(Ext.Date.add(new Date()))
                                }
                            ]
                        },
                        {
                            columnWidth: .45,
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
            text: 'maintenanceApplyId',
            dataIndex: 'maintenanceApplyId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '申请单号',
            dataIndex: 'applyNO',
            flex: 1,
            align: 'left'
        }, {
            text: '线路',
            dataIndex: 'lineName',
            flex: 1,
            align: 'left'
        }, {
            text: '站点',
            dataIndex: 'stationName',
            flex: 1,
            align: 'left'
        }, {
            text: '设备编号',
            dataIndex: 'equipmentNo',
            flex: 1,
            align: 'left'
        }, {
            text: '设备类型',
            dataIndex: 'categoryName',
            flex: 1,
            align: 'left'
        }, {
            text: '故障现象',
            dataIndex: 'errorPhenomenon',
            flex: 1,
            align: 'left'
        }, {
            text: '报修人',
            dataIndex: 'applyUser',
            flex: 1,
            align: 'left'
        }, {
            text: '报修时间',
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
        },
        {
            text: '状态',
            dataIndex: 'applyStatus',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "toBeDispatch") {
                    return "待派单"
                }
                if (value == "toBeRepair") {
                    return "待接修"
                }
                if (value == "maintenance") {
                    return "维修中"
                }
                if (value == "repaired") {
                    return "已修复"
                }
                if (value == "noRepair") {
                    return "未修复"
                }
                if (value == "rated") {
                    return "已评价"
                }
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
                margin: "0px 5px"
            },
            items: [
                {
                    tooltip: '评价',
                    iconCls: "fa-star",
                    flag: 'evaluate',
                    handler: 'actionFn',
                    getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                        if (r.data.applyStatus == 'repaired') {
                            return 'fa-star';
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
    ]
});