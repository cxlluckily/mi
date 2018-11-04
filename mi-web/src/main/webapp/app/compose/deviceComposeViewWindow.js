Ext.define('App.compose.deviceComposeView', {
    extend: 'Ext.tree.Panel',
    xtype: 'deviceComposeView',
    id: 'deviceComposeView',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.compose.deviceComposeController',
        'App.compose.deviceComposeWindow'
    ],
    controller: {
        xclass: 'App.compose.deviceComposeController'
    },
    listeners:{
        beforerender:function () {
            var p = this;
            this.getController().loadTreeList(p);
        }
    },
    rowLines: true,
    lines: true,
    width: 700,
    height: 370,
    reserveScrollbar: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    tbar:
        [
            {
                id: 'deviceComposeViewFormId',
                xtype: 'form',
                fullscreen: true,
                width: 700,
                items: [
                    {
                        xtype: 'textfield',
                        fieldLabel: 'deviceComposeId',
                        name: 'deviceComposeId',
                        hidden:true
                    },
                    {
                        xtype: 'textfield',
                        fieldLabel: 'sparePartId',
                        name: 'sparePartId',
                        hidden:true
                    }
                ]
            }
        ],
    columns:
        [
            {
                text: 'sparePartTypeId',
                dataIndex: 'sparePartTypeId',
                hidden: true
            },
            {
                text: 'deviceComposeId',
                dataIndex: 'deviceComposeId',
                hidden: true
            },
            {
                text: 'composePid',
                dataIndex: 'composePid',
                hidden: true
            },
            {
                xtype: 'treecolumn',
                text: '备件类型',
                width: 180,
                dataIndex: 'categoryName',
        
            },
            {
                text: '备件',
                width: 120,
                dataIndex: 'partName',
        
            },
            {
                text: '名称',
                width: 100,
                dataIndex: 'name',
                
            },
            {
                text: '品牌',
                width: 100,
                dataIndex: 'brand',
            },
            
            {
                text: '型号',
                width: 80,
                dataIndex: 'composeModel',
               
            },
            {
                xtype: 'actioncolumn',
                text: '操作',
                width: 100,
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
                items: [
                    {
                        tooltip: '修改',
                        iconCls: "fa-edit",
                        flag: 'update',
                        handler: 'actionFn'
                    },
                    {
                        tooltip: '新增同级',
                        iconCls: "fa-plus-circle",
                        flag: 'add',
                        handler: 'actionFn',
                        getClass: function (v, metadata, r, rowIndex, colIndex, store) {
                            if (r.data.composePid == -1) {
                                return 'x-hidden';
                            }
                            else
                            {
                                return 'fa-plus-circle';
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
})
;

Ext.define('App.compose.deviceComposeViewWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 700,
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
    xtype: 'deviceComposeViewWindow',
    controller: 'deviceComposeController',
    items: [
        {
            xtype: 'deviceComposeView'
        }
    ]
});

