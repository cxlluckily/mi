Ext.define('App.stockInventoryTask.stockInventoryTaskLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockInventoryTaskLookForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10 10 0 10',
        width: '100%',
        margin:'0 0 0 0'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'inventoryTaskId',
                            fieldLabel: 'inventoryTaskId',
                            hidden:true
                        },
                        {
                            xtype: 'displayfield',
                            name: 'beginTIme',
                            fieldLabel: '开始日期',
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
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'endTIme',
                            fieldLabel: '结束日期',
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
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'warehouseName',
                            fieldLabel: '盘点仓库'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'headPerson',
                            fieldLabel: '负责人'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'createUser',
                            fieldLabel: '创建人'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'createTime',
                            fieldLabel: '创建时间',
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
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'status',
                            fieldLabel: '盘点状态',
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
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'Count',
                            fieldLabel: '盈亏数量',
                            hidden:true,
                            listeners:{
                                beforerender:function (combo) {
                                    if(combo.up('form').down('displayfield[name=status]').value == 'already'){
                                        combo.hidden = false;
                                    };
                                }
                            }
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
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'stockInventoryTaskLookGrid'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockInventoryTask.stockInventoryTaskLookGrid', {
    extend: 'Ext.grid.Panel',
    store: {
    },
    xtype: 'stockInventoryTaskLookGrid',
    fullscreen: true,
    width: '100%',
    height:300,
    autoScroll: true,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // tbar: [
    //     {
    //         xtype: 'button',
    //         text: '保存',
    //         action: 'choose'
    //     },
    //     {
    //         xtype: 'button',
    //         text: '结束盘点',
    //         action: 'choose'
    //     }
    // ],
    columnLines: true,
    columns:
        [
            {
                text: 'inventoryDetailId',
                dataIndex: 'inventoryDetailId',
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
                text: '状态',
                dataIndex: 'status',
                flex: 1,
                renderer: function (value, record) {
                    if (value == "normal") {
                        return "好件"
                    }
                    if (value == "bad") {
                        return "坏件"
                    }
                },
            },
            {
                text: '库存数量',
                dataIndex: 'beforeAccount',
                flex: 1
            },
            {
                text: '盘库数量',
                dataIndex: 'aftierAccount',
                flex: 1
            },
            {
                text: '备注',
                dataIndex: 'inventoryDescribe',
                flex: 1
            },
        ]
});

Ext.define('App.stockInventoryTask.stockInventoryTaskLookWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
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
    xtype: 'stockInventoryTaskLookWindow',
    controller: 'stockInventoryTaskController',
    items: [
        {
            xtype: 'stockInventoryTaskLookForm'
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