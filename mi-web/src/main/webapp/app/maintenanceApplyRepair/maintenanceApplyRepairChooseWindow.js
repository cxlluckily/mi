Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChooseTree', {
        extend: 'Ext.tree.Panel',
        xtype: 'maintenanceApplyRepairChooseTree',
        checkPropagation: 'both',
        rootVisible: false,
        lines: false,
        useArrows: true,
        // hideHeaders: true,
        collapseFirst: false,
        split: true,
        // collapsible: true,
        bufferedRenderer: false,
        // reserveScrollbar: true,
        multiSelect: false,
        animate: true,
        // singleExpand: true,
        columns: [
            {
                xtype: 'treecolumn',
                flex: 1,
                dataIndex: 'partName',
                text: '备件名称'
            },
            {
                text: '二维码',
                dataIndex: 'qrCode',
                flex: 1
            },
            {
                text: 'stockId',
                dataIndex: 'stockId',
                flex: 1,
                hidden:true
            },
            {
                text: 'sparePartId',
                dataIndex: 'sparePartId',
                flex: 1,
                hidden:true
            },
            {
                text: 'sparePartTypeId',
                dataIndex: 'sparePartTypeId',
                flex: 1,
                hidden: true
            },
            {
                text: 'deviceComposeId',
                dataIndex: 'deviceComposeId',
                flex: 1,
                hidden: true
            }
        ],
        tbar: [
            {
                id: 'maintenanceApplyRepairChooseTreeFormId',
                xtype: 'form',
                fullscreen: true,
                width: '100%',
                hidden: true,
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
                        items: []
                    }
                ]
            }
        ]
    }
);

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairChooseWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 600,
    height: 600,
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
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    xtype: 'maintenanceApplyRepairChooseWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        // {
        //     xtype: 'maintenanceApplyRepairChooseForm'
        // },
        {
            xtype: 'maintenanceApplyRepairChooseTree'
        }
    ],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});