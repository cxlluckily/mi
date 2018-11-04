Ext.define('App.orgOrganization.orgOrganizationView', {
    extend: 'Ext.tree.Panel',
    xtype: 'orgOrganizationView',
    id: 'orgOrganizationView',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.orgOrganization.orgOrganizationController',
        'App.orgOrganization.orgOrganizationWindow'
    ],
    controller: {
        xclass: 'App.orgOrganization.orgOrganizationController'
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
    listeners: {
        render: function (combo) {//渲染
            var p = Ext.ComponentQuery.query('orgOrganizationView')[0];
            p.getController().loadList(p);
        }
    },
    tbar: [
        {
            id: 'orgOrganizationViewFormId',
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
                        // {
                        //     columnWidth: .3,
                        //     layout: 'form',
                        //     border: false,
                        //     items: [
                        //         {
                        //             xtype: 'combo',
                        //             emptyText: '公司名称',
                        //             // id: "orgOrganizationSelect",
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
                        //                         var p = Ext.ComponentQuery.query('orgOrganizationView')[0];
                        //                         p.getController().loadList(p);
                        //                     });
                        //                 }
                        //             }
                        //         }
                        //     ]
                        // },
                        // {
                        //     columnWidth: .69,
                        //     layout: 'column',
                        //     border: false,
                        //     items: [
                        //         {
                        //             margin: 10,
                        //             xtype: 'button',
                        //             text: '查询',
                        //             action: 'search',
                        //             iconCls: 'fa-search'
                        //         }
                        //     ]
                        // }
                    ]
                }
            ]
        }
    ],
    columns:
        [
            {
                xtype: 'treecolumn',
                text: '机构名称',
                dataIndex: 'orgName',
                width: 250,
                sortable: true
            }, {
            text: '负责人',
            dataIndex: 'headPerson',
            width: 150,
            sortable: true,
            align: 'center'
            // formatter: 'this.formatHours'
        }, {
            text: '联系电话',
            dataIndex: 'contactNumber',
            width: 150,
            sortable: true
        }, {
            text: '机构描述',
            dataIndex: 'description',
            flex: 1,
            sortable: true
        }, {
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
            items: [
                {
                    tooltip: '修改',
                    iconCls: "fa-edit",
                    flag: 'update',
                    handler: 'actionFn',
                    // getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                    //     if (r.data.activated == 1) {
                    //         return 'x-hidden';
                    //     }
                    // }
                },
                // {
                //     tooltip: '删除',
                //     iconCls: "fa-trash",
                //     flag: 'delete',
                //     handler: 'actionFn',
                //     getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                //         if ((!r.data.children || r.data.children.length == 0) && r.data.wasCanDelete) {
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
                        if (r.data.depth != 1) {
                            return 'fa-plus-circle';
                        } else {
                            return 'x-hidden';
                        }
                    }
                }, {
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