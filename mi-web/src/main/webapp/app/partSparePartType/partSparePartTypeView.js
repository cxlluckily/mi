Ext.define('App.partSparePartType.partSparePartTypeView', {
    extend: 'Ext.tree.Panel',
    xtype: 'partSparePartTypeView',
    id: 'partSparePartTypeView',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.partSparePartType.partSparePartTypeController',
        'App.partSparePartType.partSparePartTypeWindow'
    ],
    controller: {
        xclass: 'App.partSparePartType.partSparePartTypeController'
    },
    listeners:{
        beforerender:function (e) {
            e.getController().clickSearch();
        }
    },
    rowLines: true,
    lines: true,
    width: 600,
    height: 370,
    reserveScrollbar: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    // singleExpand: true,
    tbar:
        [
            {
                id: 'partSparePartTypeViewFormId',
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
                                        fieldLabel: '备件类型',
                                        name: 'categoryName'
                                    }
                                ],
                                hidden:true
                            },
                            // {
                            //     columnWidth: .33,
                            //     layout: 'form',
                            //     border: false,
                            //     items: [
                            //         {
                            //             xtype: 'combo',
                            //             emptyText: '公司名称',
                            //             fieldLabel: '公司名称',
                            //             id: "partSparePartTypeSelect",
                            //             width: 230,
                            //             store: {
                            //                 xclass: 'App.common.operationSubjectListStore'
                            //             },
                            //             queryMode: 'local',
                            //             name: 'operationSubjectId',
                            //             editable: false,
                            //             displayField: 'name',
                            //             valueField: 'id',
                            //             // multiSelect:true,
                            //             listeners: {
                            //                 render: function (combo) {//渲染
                            //                     combo.getStore().on("load", function (s, r, o) {
                            //                         combo.setValue(r[0].get('id'));//第一个值
                            //                         var p = Ext.ComponentQuery.query('partSparePartTypeView')[0];
                            //                         p.getController().loadList(p);
                            //                     });
                            //                 }
                            //             }
                            //         }
                            //     ]
                            // },

                            {
                                columnWidth: .33,
                                layout: 'column',
                                border: false,
                                items: [
                                    // {
                                    //     margin: 10,
                                    //     xtype: 'button',
                                    //     text: '查询',
                                    //     action: 'search',
                                    //     iconCls: 'fa-search'
                                    // },
                                    {
                                        margin: 10,
                                        xtype: 'button',
                                        text: '新增一级分类',
                                        action: 'addFirst',
                                        iconCls: 'fa-plus-circle'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ],
    columns:
        [
            {
                xtype: 'treecolumn',
                text: '分类名称',
                dataIndex: 'categoryName',
                width: 250
            },
            {
                text: 'sparePartTypeId',
                dataIndex: 'sparePartTypeId',
                width: 150,
                hidden: true
                // formatter: 'this.formatHours'
            },
            {
                text: 'operationSubjectId',
                dataIndex: 'operationSubjectId',
                width: 150,
                hidden: true
            },
            {
                text: 'sparePartTypeIds',
                dataIndex: 'sparePartTypeIds',
                width: 150,
                hidden: true
            },
            {
                text: 'parentPartId',
                dataIndex: 'parentPartId',
                flex: 1,
                hidden: true
            },
            {
                text: '排序位置',
                dataIndex: 'sort',
                flex: 1
            },
            {
                text: '备注',
                dataIndex: 'remark',
                flex: 1
            },
            {
                text: '状态',
                dataIndex: 'status',
                width: 100,
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
                items: [
                    {
                        tooltip: '修改',
                        iconCls: "fa-edit",
                        flag: 'update',
                        handler: 'actionFn'
                    },
                    // {
                    //     tooltip: '删除',
                    //     iconCls: "fa-trash",
                    //     flag: 'delete',
                    //     handler: 'actionFn',
                    //     getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                    //         if ((!r.data.children || r.data.children.length == 0)) {
                    //             return 'fa-trash';
                    //         } else {
                    //             return 'x-hidden';
                    //         }
                    //     }
                    // },
                    {
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
                    },
                    {
                        tooltip: '新增下级',
                        iconCls: "fa-plus-square",
                        flag: 'addChild',
                        handler: 'actionFn'
                    }
                ]
            }]
// selModel: {
//     selType: 'checkboxmodel',
//     mode: 'SINGLE',
//     allowDeselect: true,
//     listeners: {}
// }
})
;