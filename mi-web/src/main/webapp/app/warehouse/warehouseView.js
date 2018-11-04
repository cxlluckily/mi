Ext.define('App.warehouse.warehouseView', {
    extend: 'Ext.tree.Panel',
    xtype: 'warehouseView',
    id: 'warehouseView',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.warehouse.warehouseController',
        'App.warehouse.warehouseAddWindow',
        'App.warehouse.warehouseWindow',
    ],
    controller: {
        xclass: 'App.warehouse.warehouseController'
    },
    rowLines: true,
    lines: true,
    width: 600,
    height: 370,
    reserveScrollbar: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    tbar: [{
        id: 'warehouseViewFormId',
        xtype: 'form',
        fullscreen: true,
        width: 1100,
        items: [{
            xtype: 'fieldset',
            layout: 'column',
            border: false,
            style: {
                backgroundColor: '#fff',
                marginTop: '-1px',
                marginBottom: '0'
            },
            items: [{
                columnWidth: .25,
                layout: 'form',
                border: false,
                items: [
                    {
                    xtype: 'combo',
                    queryMode: 'local',
                    name: 'workSectionId',
                    editable: false,
                    store: {
                        xclass: 'App.common.globalWorkAreaListStore',
                        includeAll:'true'
                    },
                    displayField: 'name',
                    valueField: 'id',
                    emptyText: '全部',
                    fieldLabel: '所属工区',
                    listeners: {
                        render: function (combo) {
                            combo.getStore().on("load", function (s, r, o) {
                                var firstValue = combo.getStore().getData().items[0].id;
                                combo.setValue(firstValue);
                                var p = Ext.ComponentQuery.query('warehouseView')[0];
                                p.getController().loadList(p);
                            });
                        },
                        change: function (tf, nval, oval, opts) {
                            if(nval!=0&&!nval){
                                tf.setValue(0);
                            }else{
                                tf.up('warehouseView').getController().clickSearch();
                            }
                        },
                        afterload:function (store, records, successful, operation, eOpts){
                            records.unshift({id:0,name:'全部'});
                            store.setData(records);
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
                        fieldLabel: '仓库名称',
                        name: 'warehouseName'
                    }],
                    hidden:true
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '负 责 人',
                        name: 'headPerson'
                    }],
                    hidden:true
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '联系电话',
                        name: 'contactNumber'
                    }],
                    hidden:true
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [{
                        xtype: 'combo',
                        fieldLabel: '状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态',
                        emptyText: '全部',
                        value: 'all',
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
                            beforerender: function (combo) {//渲染
                                var firstValue = combo.getStore().getData().items[0].id;
                                combo.setValue(firstValue);
                            },
                            change: function (tf, nval, oval, opts) {
                                tf.up('warehouseView').getController().clickSearch();
                            },
                        }
                    }],
                    hidden:true
                },
                {
                    columnWidth: .69,
                    layout: 'column',
                    border: false,
                    items: [{
                        margin: 10,
                        xtype: 'button',
                        text: '查询',
                        action: 'search',
                        iconCls: 'fa-search',
                    },
                        {
                            margin: 10,
                            xtype: 'button',
                            text: '重置',
                            action: 'resets',
                            iconCls: 'fa-refresh',
                        },
                        {
                            margin: 10,
                            xtype: 'button',
                            text: '新增一级仓库',
                            action: 'add',
                            iconCls: 'fa-plus-circle'
                        }
                    ]
                }
            ]
        }]
    }],
    columns: [
        {
            xtype: 'treecolumn',
            text: '仓库名称',
            dataIndex: 'warehouseName',
            width: 200,
            sortable: true
        },
        {
            text: '所属工区',
            dataIndex: 'sectionName',
            width: 200,
            sortable: true
        },
        {
            xtype: 'treecolumn',
            text: 'parentWarehouseId',
            dataIndex: 'parentWarehouseId',
            width: 250,
            sortable: true,
            hidden:true
        },
        {
            xtype: 'treecolumn',
            text: 'workSectionId',
            dataIndex: 'workSectionId',
            width: 250,
            sortable: true,
            hidden:true
        },
        {
            text: '所在位置',
            dataIndex: 'location',
            width: 200,
            sortable: true
        }, {
            text: '负责人',
            dataIndex: 'headPerson',
            width: 100,
            sortable: true,
            align: 'center'
            // formatter: 'this.formatHours'
        }, {
            text: '联系电话',
            dataIndex: 'contactNumber',
            width: 100,
            sortable: true
        }, {
            text: '仓库等级',
            dataIndex: 'description',
            width: 100,
            sortable: true
        },
        // {
        //     text: '运营主体',
        //     dataIndex: 'subjectName',
        //     width: 150,
        //     sortable: true
        // },
        {
            text: '状态',
            dataIndex: 'status',
            width: 100,
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using")
                {
                    return "可用"
                }
                if (value == "stop_using")
                {
                    return "不可用"
                }
            }
        }, {
            xtype: 'actioncolumn',
            text: '操作',
            width: 150,
            menuDisabled: true,
            // tooltip: 'Edit task',
            align: 'center',
            // iconCls: 'tree-grid-edit-task',
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
                // getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                //     if (r.data.activated == 1) {
                //         return 'x-hidden';
                //     }
                // }
            }, {
                tooltip: '删除',
                iconCls: "fa-trash",
                flag: 'delete',
                handler: 'actionFn',
                getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                    if (r.data.children.length == 0)
                    {
                        return 'x-hidden';
                    }
                    else
                    {
                        return 'x-hidden';
                    }
                }
            }, {
                tooltip: '新增同级',
                iconCls: "fa-plus-circle",
                flag: 'add',
                handler: 'actionFn',
                getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                    if (r.data.depth != 1)
                    {
                        return 'fa-plus-circle';
                    }
                    else
                    {
                        return 'x-hidden';
                    }
                }
            }, {
                tooltip: '新增下级',
                iconCls: "fa-plus-square",
                flag: 'addChild',
                handler: 'actionFn'
            }]
        }]
});