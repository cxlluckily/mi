Ext.define('App.orgWorkSection.orgWorkSectionView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',//通用下拉列表数据，可不引用
        'App.orgWorkSection.orgWorkSectionStore',
        'App.orgWorkSection.orgWorkSectionController',
        'App.orgWorkSection.orgWorkSectionStationForm',
        'App.orgWorkSection.orgWorkSectionWindow'
    ],
    store: {
        xclass: 'App.orgWorkSection.orgWorkSectionStore'
    },
    controller: {
        xclass: 'App.orgWorkSection.orgWorkSectionController'
    },

    xtype: 'orgWorkSectionView',
    id: 'orgWorkSectionView',
    fullscreen: true,
    tbar: [
        {
            id: 'orgWorkSectionViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 1200,
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    border: false,
                    style: {
                        backgroundColor: '#fff',
                        marginTop: '-1px'
                    },
                    items: [
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '工区编码',
                                    name: 'sectionCode'
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
                                    fieldLabel: '工区名称',
                                    name: 'sectionName'
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
                                    fieldLabel: '负责人',
                                    name: 'headPerson'
                                }
                            ]
                        },
                        // {
                        //     columnWidth: .25,
                        //     layout: 'form',
                        //     border: false,
                        //     labelWidth: 200,
                        //     items: [
                        //         {
                        //             xtype: 'combo',
                        //             fieldLabel: '公司',
                        //             emptyText: '--请选择--',
                        //             store: {
                        //                 xclass: 'App.common.operationSubjectListStore'
                        //             },
                        //             queryMode: 'local',
                        //             name: 'operationSubjectId',
                        //             editable: false,
                        //             displayField: 'name',
                        //             valueField: 'id',
                        //             listeners: {
                        //                 render: function (combo) {//渲染
                        //                     combo.getStore().on("load", function (s, r, o) {
                        //                         combo.setValue(r[0].get('id'));//第一个值
                        //                         var p = Ext.ComponentQuery.query('orgWorkSectionView')[0];
                        //                         p.getController().clickSearch();
                        //                     });
                        //                 },
                        //                 change: function (tf, nval, oval, opts) {
                        //                     Ext.getCmp('orgWorkSectionViewFormId').getForm().setValues({operationSubjectName: tf.rawValue});
                        //                 }
                        //             }
                        //         },
                        //         {
                        //             xtype: 'textfield',
                        //             fieldLabel: '公司名',
                        //             // id: 'operationSubjectName',
                        //             name: 'operationSubjectName',
                        //             hidden: true
                        //         }
                        //     ]
                        // },
                        {
                            columnWidth: .25,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '状态',
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
                            columnWidth: .6,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                },{
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                },{
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled : true
                                }
                                // ,
                                //  {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '工区车站关系导入',
                                //     action: 'importDownload',
                                //     iconCls: 'fa-download'
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
            text: '工区编码',
            dataIndex: 'sectionCode',
            flex: 1,
            align: 'left'
        },
        {
            text: '工区名称',
            dataIndex: 'sectionName',
            flex: 1,
            align: 'left'
        },
        {
            text: '负责人',
            dataIndex: 'headPerson',
            flex: 1,
            align: 'left'
        },
        {
            text: '电话',
            dataIndex: 'phone',
            flex: 1,
            align: 'left'
        },
        {
            text: '备注',
            dataIndex: 'remark',
            flex: 1,
            align: 'left'
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
                    } else if (selected.length == 1) {
                        sel.view.ownerCt.down('button[action="update"]').enable();
                    }
                }
            }
        }
    }
});