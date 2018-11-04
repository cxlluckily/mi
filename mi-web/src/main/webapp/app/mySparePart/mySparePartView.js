Ext.define('App.mySparePart.mySparePartView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.mySparePart.mySparePartStore',
        // 'App.mySparePart.mySparePartController'
    ],
    store: {
        xclass: 'App.mySparePart.mySparePartStore'
    },
    // controller: {
    //     xclass: 'App.mySparePart.mySparePartController'
    // },
    xtype: 'mySparePartView',
    id: 'mySparePartView',
    fullscreen: true,
    // tbar: [
    //     {
    //         id: 'mySparePartViewFormId',
    //         xtype: 'form',
    //         fullscreen: true,
    //         width: 1100,
    //         items: [
    //             {
    //                 xtype: 'fieldset',
    //                 layout: 'column',
    //                 border: false,
    //                 style: {
    //                     backgroundColor: '#fff',
    //                     marginTop: '-1px',
    //                     marginBottom: '0'
    //                 },
    //                 // title: '作业跟踪',
    //                 items: [
    //                     {
    //                         columnWidth: .25,
    //                         layout: 'form',
    //                         border: false,
    //                         items: [
    //                             {
    //                                 xtype: 'textfield',
    //                                 fieldLabel: '编号',
    //                                 name: 'subjectCode'
    //                             }
    //                         ]
    //                     },
    //                     {
    //                         columnWidth: .25,
    //                         layout: 'form',
    //                         border: false,
    //                         items: [
    //                             {
    //                                 xtype: 'textfield',
    //                                 fieldLabel: '主体名称',
    //                                 name: 'subjectName'
    //                             }
    //                         ]
    //                     },
    //                     {
    //                         columnWidth: .25,
    //                         layout: 'form',
    //                         border: false,
    //                         items: [
    //                             {
    //                                 xtype: 'textfield',
    //                                 fieldLabel: '联系人',
    //                                 name: 'contacts'
    //                             }
    //                         ]
    //                     },
    //                     {
    //                         columnWidth: .25,
    //                         layout: 'form',
    //                         border: false,
    //                         items: [
    //                             {
    //                                 xtype: 'combo',
    //                                 fieldLabel: '状态',
    //                                 anchor: '90%',
    //                                 emptyText: '--请选择--',
    //                                 store: {
    //                                     data: [
    //                                         {
    //                                             id: "start_using",
    //                                             name: "可用"
    //                                         },
    //                                         {
    //                                             id: "stop_using",
    //                                             name: "不可用"
    //                                         }
    //                                         // {
    //                                         //     id: "all",
    //                                         //     name: "全部"
    //                                         // }
    //                                     ]
    //                                 },
    //                                 queryMode: 'local',
    //                                 name: 'status',
    //                                 editable: false,
    //                                 displayField: 'name',
    //                                 valueField: 'id',
    //                                 // multiSelect:true,
    //                                 listeners: {
    //                                     beforerender: function (combo) {//渲染
    //                                         var firstValue = combo.getStore().getData().items[0].id;
    //                                         combo.setValue(firstValue);
    //                                     }
    //                                 }
    //                             }
    //                         ]
    //                     },
    //                     {
    //                         columnWidth: .7,
    //                         layout: 'column',
    //                         border: false,
    //                         items: [
    //                             {
    //                                 margin: 10,
    //                                 // columnWidth: .25,
    //                                 xtype: 'button',
    //                                 text: '查询',
    //                                 action: 'search',
    //                                 iconCls: 'fa-search'
    //                             },  {
    //                                 margin: 10,
    //                                 xtype: 'button',
    //                                 text: '重置',
    //                                 action: 'resets',
    //                                 iconCls: 'fa-refresh'
    //                             }, {
    //                                 margin: 10,
    //                                 // columnWidth: .25,
    //                                 xtype: 'button',
    //                                 text: '新增',
    //                                 action: 'add',
    //                                 iconCls: 'fa-plus-circle'
    //                             }, {
    //                                 margin: 10,
    //                                 // columnWidth: .25,
    //                                 xtype: 'button',
    //                                 text: '修改',
    //                                 action: 'update',
    //                                 iconCls: 'fa-edit',
    //                                 disabled: true
    //                             }
    //                         ]
    //                     }
    //                 ]
    //             }
    //         ]
    //     }
    // ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },
    
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '照片',
            dataIndex: 'imageUrl',
            width: 100,
            align: 'center',
            // hidden: true,
            renderer: function (value, metaData, record) {
                if(value){
                    var img = '<img src=' + value + ' width="80" />';
                    return img;
                }
            }
        },
        
        {
            text: '仓库名称',
            dataIndex: 'warehouseName',
            flex: 1,
            align: 'left'
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
        }
        , {
            text: '品牌',
            dataIndex: 'brand',
            flex: 1,
            align: 'left'
        }, {
            text: '物资编码',
            dataIndex: 'materiaCoding',
            flex: 1,
            align: 'left'
        }, {
            text: '规格型号',
            dataIndex: 'specificationModel',
            flex: 1,
            align: 'left'
        }, {
            text: '持有数量',
            dataIndex: 'applyCount',
            flex: 1,
            align: 'left'
        }
        // , {
        //     text: '领取时间',
        //     dataIndex: 'applyTime',
        //     flex: 1,
        //     align: 'left'
        // }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            // selectionchange: {
            //     fn: function (sel, selected, eOpts) {
            //         if (selected.length < 1)
            //         {
            //             sel.view.ownerCt.down('button[action="update"]').disable();
            //         }
            //         else if (selected.length == 1)
            //         {
            //             sel.view.ownerCt.down('button[action="update"]').enable();
            //         }
            //         else
            //         {
            //             sel.view.ownerCt.down('button[action="update"]').disable();
            //         }
            //     }
            // }
        }
    }
});