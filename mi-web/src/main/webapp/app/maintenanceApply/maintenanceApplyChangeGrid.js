Ext.define('App.maintenanceApply.maintenanceApplyChangeGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        data: []
    },
    xtype: 'maintenanceApplyChangeGrid',
    fullscreen: true,
    width: '100%',
    autoScroll: true,
    style: {
        border: "1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    columnLines: true,
    columns:
        [

            {
                text:'背包ID',
                dataIndex:'userDeviceId',
                flex:1,
                hidden:true
            },
            {
                text: '新部件',
                dataIndex: 'newPartName',
                flex: 1
            },
            {
                text: '新部件库存id',
                dataIndex: 'newStockId',
                flex: 1,
                hidden: true
            },
            {
                text: '新部件id',
                dataIndex: 'newSparePartId',
                flex: 1,
                hidden: true
            },
            {
                text: '数量',
                dataIndex: 'replaceCount',
                flex: 1,
                // editor:{
                //     field: {
                //         xtype: 'numberfield',
                //         name: 'replaceCount'
                //     }
                // }
            },
            {
                text: '库存类型',
                dataIndex: 'inventoryType',
                flex: 1,
                renderer: function (value, record) {
                    if (value == "unique") {
                        return "唯一标识"
                    }
                    if (value == "notUnique") {
                        return "非唯一标识"
                    }
                }
            },
            {
                text: '新部件二维码',
                dataIndex: 'newQrCode',
                width: 270
            },
            {
                text: '旧部件二维码',
                dataIndex: 'oldQrCode',
                width: 270,
                // editor: {
                //     completeOnEnter: false,
                //     field: {
                //         xtype: 'textfield',
                //         name: 'code'
                //     }
                // }
            },
            {
                text: '旧部件库存id',
                dataIndex: 'oldStockId',
                flex: 1,
                hidden: true
            },
            {
                text: '旧部件id',
                dataIndex: 'oldSparePartId',
                flex: 1,
                hidden: true
            },
            {
                text: '旧部件类型id',
                dataIndex: 'oldSparePartTypeId',
                flex: 1,
                hidden: true
            }
        ]
})
;