Ext.define('App.partSparePart.partTypeView', {
        extend: 'Ext.tree.Panel',
        xtype: 'partTypeView',
        require: [
            'Ext.data.*',
            'Ext.grid.*',
            'Ext.tree.*',
            'Ext.grid.column.Check',
            "App.common.selectStore",
            "App.partSparePart.partTypeController"
        ],
        // checkPropagation: 'none',
        controller: {
            xclass: 'App.partSparePart.partTypeController'
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
        reserveScrollbar: true,
        multiSelect: false,
        // singleExpand: true,
        listeners: {
            beforerender: function (combo) {//渲染
                var p = Ext.ComponentQuery.query('partTypeView')[0];
                p.getController().loadList(p);
            }
        },
        columns: [
            {
                xtype: 'treecolumn',
                flex: 1,
                dataIndex: 'categoryName',
                text: '分类名称'
            }
        ],
        tbar: [

            {
                id: 'partTypeViewFormId',
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
                            {
                                xtype: "button",
                                text: '重置',
                                action: "reset",
                                margin: '5 5 5 0',
                                iconCls: 'fa-refresh'
                            }
                            // ,{
                            //     margin: '5 5 5 0',
                            //     xtype: 'button',
                            //     text: '导入',
                            //     action: 'importTypeDownload',
                            //     iconCls: 'fa-download'
                            // }
                            , {
                                margin:'5 5 5 0',
                                xtype: 'button',
                                text: '全部导出',
                                action: 'exportTypeUpload',
                                iconCls: 'fa-upload'
                            }
                        ]
                    }
                ]
            }
        ]
    }
);