Ext.define('App.shelf.shelfView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore', //通用下拉列表数据，可不引用
        'App.shelf.shelfStore',
        'App.shelf.shelfController',
        'App.shelf.shelfLookWindow',
        'App.shelf.shelfModifyWindow',
        'App.shelf.shelfWindow',
    ],
    store: {
        xclass: 'App.shelf.shelfStore'
    },
    controller: {
        xclass: 'App.shelf.shelfController'
    },
    xtype: 'shelfView',
    id: 'shelfView',
    fullscreen: true,
    tbar: [{
        id: 'shelfViewFormId',
        xtype: 'form',
        fullscreen: true,
        width: 1100,
        items: [{
            xtype: 'fieldset',
            layout: 'column',
            border: false,
            style: {
                backgroundColor: '#fff',
                marginTop: '-1px'
            },
            items: [{
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    labelWidth: 200,
                    items: [{
                        xtype: 'combo',
                        emptyText: '全部',
                        queryMode: 'local',
                        name: 'warehouseId',
                        editable: false,
                        store: {
                            xclass: 'App.common.userStockWarehouseListStore',
                            includeAll:'true',
                            autoLoad:true
                        },
                        displayField: 'text',
                        valueField: 'id',
                        fieldLabel: '所属仓库',
                        listeners: {
                            render: function (combo) {
                                combo.getStore().on("load", function (s, r, o) {
                                    var firstValue = combo.getStore().getData().items[0].id;
                                    combo.setValue(firstValue);
                                    this.value=firstValue;
                                    var pageList = Ext.ComponentQuery.query('shelfView')[0];
                                    pageList.getStore().proxy.extraParams = {
                                        "userKey": window.sessionStorage['userKey'],
                                        "warehouseId": pageList,
                                        "shelfNumber": "",
                                        "houseNo":"",
                                        "containerType": "Shelf"
                                    };
                                    pageList.getStore().load();
                                });
                            },
                            change: function (tf, nval, oval, opts) {
                                if(!nval){
                                    tf.setValue(tf.getStore().value)
                                }
                                tf.up('shelfView').getController().clickSearch();
                            }
                        }
                    }]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '房间号',
                        name: 'houseNo'
                    }]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '货柜编码',
                        name: 'shelfNumber'
                    }]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'combo',
                        fieldLabel: '类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型',
                        emptyText: '全部',
                        value:'all',
                        store: {
                            data: [
                            {
                                id: "all",
                                name: "全部"
                            },{
                                id: "Shelf",
                                name: "货架"
                            },{
                                id: "Container",
                                name: "货柜"
                            }
                            ]
                        },
                        queryMode: 'local',
                        name: 'containerType',
                        editable: false,
                        displayField: 'name',
                        valueField: 'id',
                        listeners: {
                            beforerender: function (combo) {
                                var firstValue = combo.getStore().getData().items[0].id;
                                combo.setValue(firstValue);
                            }
                        }
                    }]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'combo',
                        fieldLabel: '状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态',
                        emptyText: '全部',
                        value:'all',
                        store: {
                            data: [
                                {
                                    id: "all",
                                    name: "全部"
                                },
                                {
                                    id: "start_using",
                                    name: "可用"
                                },
                                {
                                    id: "stop_using",
                                    name: "不可用"
                                }
                            ]
                        },
                        queryMode: 'local',
                        name: 'status',
                        editable: false,
                        displayField: 'name',
                        valueField: 'id',
                        listeners: {
                            beforerender: function (combo) {
                                var firstValue = combo.getStore().getData().items[0].id;
                                combo.setValue(firstValue);
                            }
                        }
                    }]
                },
                {
                    columnWidth: .6,
                    layout: 'column',
                    border: false,
                    items: [{
                            margin: 10,
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search'
                        },{
                            margin: 10,
                            xtype: 'button',
                            text: '重 置',
                            action: 'resets',
                            iconCls: 'fa-refresh'
                        },{
                            margin: 10,
                            xtype: 'button',
                            text: '新增',
                            action: 'add',
                            iconCls: 'fa-plus-circle'
                        }
                        // , {
                        //     margin: 10,
                        //     // columnWidth: .25,
                        //     xtype: 'button',
                        //     text: '导入',
                        //     action: 'importDownload',
                        //     iconCls: 'fa-download'
                        // }
                        // , {
                        //     margin: 10,
                        //     // columnWidth: .25,
                        //     xtype: 'button',
                        //     text: '导出',
                        //     action: 'exportUpload',
                        //     iconCls: 'fa-upload'
                        // }
                        // {
                        //     margin: 10,
                        //     xtype: 'button',
                        //     text: '查看',
                        //     action: 'look',
                        //     iconCls: 'fa-eye',
                        //     disabled: true
                        // },
                        // , {
                        //     margin: 10,
                        //     // columnWidth: .25,
                        //     xtype: 'button',
                        //     text: '修改',
                        //     action: 'update',
                        //     iconCls: 'fa-edit',
                        //     disabled : true
                        // }
                        // {
                        //     margin: 10,
                        //     // columnWidth: .25,
                        //     xtype: 'button',
                        //     text: '删除',
                        //     action: 'delete',
                        //     iconCls: 'fa-minus-circle',
                        //     disabled : true
                        // }
                        // , {
                        //     margin: 10,
                        //     // columnWidth: .25,
                        //     xtype: 'button',
                        //     text: '导出',
                        //     action: 'delete',
                        //     iconCls: 'fa-cloud-download'
                        // }
                    ]
                }
            ]
        }]
    }],
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
            xtype: 'rownumberer',
            width: '10px',
            align: 'center'
        },
        {
            text: '所属工区',
            dataIndex: 'sectionName',
            flex: 1,
            align: 'left'
        },
        {
            text: '所属仓库',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
        },
        {
            text: '类型',
            dataIndex: 'containerType',
            flex: 1,
            align: 'left',
            renderer: function (value, record) {
                if (value == "Shelf") {
                    return "货架"
                }else if (value == "Container") {
                    return "货柜"
                }
            }
        },
        {
            text: '房间号',
            dataIndex: 'houseNo',
            flex: 1,
            align: 'left'
        },
        {
            text: '货架/货柜编号',
            dataIndex: 'shelfNumber',
            flex: 1,
            align: 'left'
        },
        {
            text: '货架编号',
            dataIndex: 'goodsShelvesId',
            flex: 1,
            align: 'left',
            hidden:true
        },
        {
            text: '状态',
            dataIndex: 'status',
            flex: 1,
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using") {
                    return "可用"
                }
                if (value == "stop_using") {
                    return "不可用"
                }
            }
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            align: 'left'
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 150,
            menuDisabled: true,
            align: 'center',
            handler: 'onEditRowAction',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                margin: "0px 5px"
            },
            items: [{
                tooltip: '修改',
                iconCls: "fa-edit",
                flag: 'update',
                handler: 'actionFn',
            }, {
                tooltip: '查看',
                iconCls: "fa-eye",
                flag: 'look',
                handler: 'actionFn',
            }
            // , {
            //     tooltip: '删除',
            //     iconCls: "fa-trash",
            //     flag: 'delete',
            //     handler: 'actionFn'
            // }
        ]
        }
    ],
    // selModel: {
    //     selType: 'checkboxmodel',
    //     // mode: 'SINGLE',
    //     allowDeselect: true,
    //     listeners: {
    //         selectionchange: {
    //             fn: function (sel, selected, eOpts) {
    //                 if (selected.length < 1)
    //                 {
    //                     sel.view.ownerCt.down('button[action="look"]').disable();
    //                 }
    //                 else if (selected.length == 1)
    //                 {
    //                     sel.view.ownerCt.down('button[action="look"]').enable();
    //                 }
    //                 else
    //                 {
    //                     sel.view.ownerCt.down('button[action="look"]').disable();
    //                 }
    //             }
    //         }
    //     }
    // }
});