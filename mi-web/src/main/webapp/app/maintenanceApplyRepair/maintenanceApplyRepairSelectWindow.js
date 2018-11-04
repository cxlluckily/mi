Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'maintenanceApplyRepairSelectForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            items: [
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'searchContent',
                            fieldLabel: '姓名',
                            emptyText:'请输入姓名'
                        }
                    ]
                },
                {
                    columnWidth: .25,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            // margin: 10,
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridAllStore', {
    extend: 'App.common.commonStore',
    // model: "App.sysRole.sysRoleStoreModel",
    pageSize: 20,
    proxy: {
        url: ctx + 'roleInfo/getRolePagerInfo.do',
        reader: {
            type: 'json',
            rootProperty: 'data.rows',
            totalProperty: 'data.totalCount'
        }
    },
    autoLoad: true
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridAll', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass: 'App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridAllStore'
    },
    xtype: 'maintenanceApplyRepairSelectGridAll',
    fullscreen: true,
    height: 200,
    style: {
        border: "1px solid #5fa2dd"
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'userId',
            dataIndex: 'userId',
            flex: 1,
            hidden:true
        },
        {
            text: '姓名',
            dataIndex: 'realName',
            flex: 1
        },
        {
            text: '角色',
            dataIndex: 'roleName',
            flex: 1
        },
        {
            text: '部门',
            dataIndex: 'orgName',
            flex: 1
        },
        {
            text: '职位',
            dataIndex: 'position',
            flex: 1
        },
        {
            text: '手机号',
            dataIndex: 'phone',
            flex: 1
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 60,
            menuDisabled: true,
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
                    tooltip: '新增',
                    iconCls: "fa-plus",
                    flag: 'add',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridSelect', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.maintenanceApplyRepair.maintenanceApplyRepairController'
    ],
    controller: {
        xclass: 'App.maintenanceApplyRepair.maintenanceApplyRepairController'
    },
    store:{
        data:[
        ]
    },
    xtype: 'maintenanceApplyRepairSelectGridSelect',
    fullscreen: true,
    height:200,
    style:{
        border:"1px solid #5fa2dd"
    },
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: 'userId',
            dataIndex: 'userId',
            flex: 1,
            hidden:true
        },
        {
            text: '姓名',
            dataIndex: 'realName',
            flex: 1
        },
        {
            text: '角色',
            dataIndex: 'roleName',
            flex: 1
        },
        {
            text: '部门',
            dataIndex: 'orgName',
            flex: 1
        },
        {
            text: '职位',
            dataIndex: 'position',
            flex: 1
        },
        {
            text: '手机号',
            dataIndex: 'phone',
            flex: 1
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            width: 60,
            menuDisabled: true,
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
                    tooltip: '删除',
                    iconCls: "fa-trash",
                    flag: 'delete',
                    handler: 'actionFn'
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridSelectModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'sparePartId',
            type: 'string'
        },
        {
            name: 'stockId',
            type: 'string'
        },
        {
            name: 'supplierName',
            type: 'string'
        },
        {
            name: 'serialNumber',
            type: 'string'
        },
        {
            name: 'inDate',
            type: 'string'
        },
        {
            name: 'materiaCoding',
            type: 'string'
        },
        {
            name: 'equipmentNO',
            type: 'string'
        },
        {
            name: 'categoryName',
            type: 'string'
        },
        {
            name: 'partName',
            type: 'string'
        },
        {
            name: 'account',
            type: 'number'
        },
        {
            name: 'qrCode',
            type: 'string'
        },
        {
            name: 'size',
            type: 'string'
        },
        {
            name: 'houseNo',
            type: 'string'
        },
        {
            name: 'shelfNumber',
            type: 'string'
        },
        {
            name: 'equipmentName',
            type: 'string'
        },
        {
            name: 'category',
            type: 'string'
        },
        {
            name: 'brand',
            type: 'string'
        },
        {
            name:'inventoryType',
            type:'string'
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridSelectStore', {
    extend: 'Ext.data.Store',
    model: "App.maintenanceApplyRepair.maintenanceApplyRepairSelectGridSelectModel"
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairSelectWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
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
    xtype: 'maintenanceApplyRepairSelectWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        {
            xtype: 'maintenanceApplyRepairSelectForm'
        },
        {
            xtype: 'maintenanceApplyRepairSelectGridAll'
        },
        {
            xtype: 'maintenanceApplyRepairSelectGridSelect'
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