Ext.define('App.stockStock.stockStockView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.common.importWindow',
        'App.common.messageWindow',
        'App.stockStock.stockStockStore',
        'App.stockStock.stockStockController',
        'App.stockStock.stockStockDetailWindow',
        'App.stockStock.stockStockUpdateWindow'
    ],
    store: {
        xclass: 'App.stockStock.stockStockStore'
    },
    controller: {
        xclass: 'App.stockStock.stockStockController'
    },
    xtype: 'stockStockView',
    // id: 'stockStockView',
    fullscreen: true,
    tbar: [
        {
            id: 'stockStockViewFormId',
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
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '仓库',
                                    emptyText: '所属仓库',
                                    store: {
                                        xclass: 'App.common.userStockWarehouseListStore'
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
                                                combo.setValue(r[0].data[combo.valueField]);
                                                combo.up('grid').getController().clickSearch();
                                            });
                                        },
                                        change: function (tf, nval, oval, opts) {
                                            if(nval!=0&&nval!=null){
                                                tf.setValue(nval);
                                            }else{
                                                tf.setValue(tf.getStore().getData().items[0].id);
                                                tf.up('grid').getController().clickSearch();
                                            }
                                        },
                                        afterRender : function(combo) {
                                           var firstValue=combo.lastValue;
                                            combo.setValue(firstValue);
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '备件类型',
                                    emptyText: '--双击选择--',
                                    queryMode: 'local',
                                    name: 'sparePartTypeId',
                                    editable: false,
                                    displayField: 'categoryName',
                                    valueField: 'sparePartTypeId',
                                    listeners: {
                                        expand: function (cb, e, opt) {
                                            var config = {
                                                title: this.fieldLabel,
                                                treeParams: {
                                                    url: 'sparePartType/getSparePartTypeInfo.do',
                                                    rootProperty: 'data',
                                                    component: cb,
                                                    params: {
                                                        status: "start_using"
                                                    }
                                                }
                                            };
                                            var w = Ext.create('App.common.selectWindow', config);
                                            w.show();
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '备件名称',
                                    name: 'partName'
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '房间号',
                                    name: 'houseNo',
                                    hidden:true
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '库存类型',
                                    emptyText: '全部',
                                    value:'all',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "notUnique",
                                                name: "非唯一标识"
                                            },
                                            {
                                                id: "unique",
                                                name: "唯一标识"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'inventoryType',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '备件状态',
                                    anchor: '90%',
                                    emptyText: '全部',
                                    value:'all',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "normal",
                                                name: "好件"
                                            },
                                            {
                                                id: "bad",
                                                name: "坏件"
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
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .7,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },{
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'reset',
                                    iconCls: 'fa-refresh'
                                },  {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查看',
                                    action: 'look',
                                    iconCls: 'fa-eye',
                                    disabled: true
                                } , {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导入',
                                    action: 'importDownload',
                                    iconCls: 'fa-download'
                                }
                                // , {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '导出',
                                //     action: 'exportUpload',
                                //     iconCls: 'fa-upload'
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
            text: 'sparePartId',
            dataIndex: 'sparePartId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '备件类型',
            dataIndex: 'categoryName',
            flex: 1,
            align: 'left'
        }, {
            text: '备件名称',
            dataIndex: 'partName',
            flex: 1,
            align: 'left'
        }, {
            text: '仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        }, {
            text: '数量',
            dataIndex: 'account',
            flex: 1,
            align: 'left'
        },
        // {
        //     text: '房间号',
        //     dataIndex: 'houseNo',
        //     flex:1,
        //     align: 'left'
        // }, {
        //     text: '货架编号',
        //     dataIndex: 'shelfNumber',
        //     flex:1,
        //     align: 'left'
        // },
        {
            text: '库存类型',
            dataIndex: 'inventoryType',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "notUnique") {
                    return "非唯一标识"
                }
                if (value == "unique") {
                    return "唯一标识"
                }
            }
        }, {
            text: '备件状态',
            dataIndex: 'status',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "normal") {
                    return "好件"
                }
                if (value == "bad") {
                    return "坏件"
                }
            }
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
                        sel.view.ownerCt.down('button[action="look"]').disable();
                    }
                    else if (selected.length == 1) {
                        sel.view.ownerCt.down('button[action="look"]').enable();
                    }
                }
            }
        }
    }
});