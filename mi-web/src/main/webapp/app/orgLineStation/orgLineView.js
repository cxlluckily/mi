Ext.define('App.orgLineStation.orgLineView', {
        extend: 'Ext.tree.Panel',
        xtype: 'orgLineView',
        require: [
            'Ext.data.*',
            'Ext.grid.*',
            'Ext.tree.*',
            'Ext.grid.column.Check',
            "App.common.selectStore",
            "App.orgLineStation.orgLineController"
        ],
        controller: {
            xclass: 'App.orgLineStation.orgLineController'
        },
        listeners: {
            render:function(){
                this.getController().loadList(this);

            }
            // render: function (combo) {//渲染
            //     combo.getStore().on("load", function (s, r, o) {
            //         combo.setValue(r[0].get('id'));
            //     });
            // },
            // change: function () {
            //     var p = Ext.ComponentQuery.query('orgLineView')[0];
            //     p.getController().loadList(p);
            // }
        },
        rootVisible: false,
        lines: false,
        useArrows: true,
        hideHeaders: true,
        collapseFirst: false,
        width: 150,
        minWidth: 100,
        // height: 200,
        split: true,
        // collapsible: true,
        bufferedRenderer: false,
        // listeners: {
        //     beforecheckchange: 'onBeforeCheckChange'
        // },
        reserveScrollbar: true,
        multiSelect: false,
        columns: [
            {
                xtype: 'treecolumn',
                flex: .6,
                dataIndex: 'lineName'
            },
            {
                // xtype: 'treecolumn',
                flex: .4,
                dataIndex: 'status',
                renderer: function (value, record) {
                    // if (value == "start_using") {
                    //     return "可用"
                    // }
                    if (value == "stop_using") {
                        return "(不可用)"
                    }
                }
            }],
        tbar: [

            {
                id: 'orgLineViewFormId',
                xtype: 'form',
                fullscreen: true,
                width: '100%',
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
                            //     xtype: 'combo',
                            //     emptyText: '公司名称',
                            //     id: "orgOrganizationSelectId",
                            //     width: '100%',
                            //     store: {
                            //         xclass: 'App.common.operationSubjectListStore'
                            //     },
                            //     queryMode: 'local',
                            //     name: 'operationSubjectId',
                            //     editable: false,
                            //     displayField: 'name',
                            //     valueField: 'id',
                            //     // multiSelect:true,
                            //     listeners: {
                            //         render: function (combo) {//渲染
                            //             combo.getStore().on("load", function (s, r, o) {
                            //                 combo.setValue(r[0].get('id'));
                            //             });
                            //         },
                            //         change: function () {
                            //             var p = Ext.ComponentQuery.query('orgLineView')[0];
                            //             p.getController().loadList(p);
                            //         }
                            //     }
                            // },

                            // {
                            //     xtype: "button",
                            //     text: '搜索',
                            //     action: "search",
                            //     margin: '10 10 10 0'
                            // },
                            {
                                xtype: "button",
                                text: '新增',
                                action: "add",
                                margin: '10 5 10 0',
                                iconCls: 'fa-plus-circle'
                            },
                            {
                                xtype: "button",
                                text: '修改',
                                action: "update",
                                margin: '10 5 10 0',
                                iconCls: 'fa-edit'
                            }
                            // ,{
                            //     margin: '10 10 10 0',
                            //     xtype: 'button',
                            //     text: '导入',
                            //     action: 'importLineDownload',
                            //     iconCls: 'fa-download'
                            // }
                            , {
                                margin: '10 5 10 0',
                                xtype: 'button',
                                text: '全部导出',
                                action: 'exportLineUpload',
                                iconCls: 'fa-upload'
                            }
                        ]
                    }
                ]
            }
        ]
    }
);
