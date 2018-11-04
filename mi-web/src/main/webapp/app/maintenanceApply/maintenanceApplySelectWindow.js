Ext.define('App.maintenanceApply.maintenanceApplySelectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'maintenanceApplySelectForm',
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

Ext.define('App.maintenanceApply.maintenanceApplySelectGridAll', {
    extend: 'Ext.grid.Panel',
    store: {
        xclass: 'App.common.getConcatPeopleList',
        listeners:{
            load:function (store, records, successful, operation, eOpts) {
                var selectGrid = Ext.ComponentQuery.query('maintenanceApplySelectGridSelect')[0];
                var selectGridData = selectGrid.getStore().getData();
                var selectGridDataArray = [];
                Ext.each(selectGridData.items,function (item) {
                    Ext.each(records,function (record) {
                        if(item.data.userId == record.data.userId){
                            selectGridDataArray.push(record.data);
                        }
                    });
                });
                selectGrid.getStore().setData(selectGridDataArray);
            }
        }
    },
    xtype: 'maintenanceApplySelectGridAll',
    fullscreen: true,
    height: 200,
    style: {
        border: "1px solid #5fa2dd"
    },
    listeners:{
        beforerender:function (component) {
            var store = component.getStore();
            var formValues = component.up('window').down('form').getForm().getValues();
            Ext.apply(formValues, {type:'pc'});
            Ext.apply(store.proxy.extraParams, formValues);
            store.load();
        }
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

Ext.define('App.maintenanceApply.maintenanceApplySelectGridSelect', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.maintenanceApply.maintenanceApplyController'
    ],
    controller: {
        xclass: 'App.maintenanceApply.maintenanceApplyController'
    },
    store:{
        data:[
        ]
    },
    xtype: 'maintenanceApplySelectGridSelect',
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

Ext.define('App.maintenanceApply.maintenanceApplySelectGridSelectModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'realName',
            type: 'string'
        },
        {
            name: 'userId',
            type: 'string'
        }
    ]
});

Ext.define('App.maintenanceApply.maintenanceApplySelectGridSelectStore', {
    extend: 'Ext.data.Store',
    model: "App.maintenanceApply.maintenanceApplySelectGridSelectModel"
});

Ext.define('App.maintenanceApply.maintenanceApplySelectWindow', {
    extend: 'Ext.window.Window',
    title: '抄送人',
    width: 1000,
    plain: false,
    iconCls: 'fa-user',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'maintenanceApplySelectWindow',
    controller: 'maintenanceApplyController',
    items: [
        {
            xtype: 'maintenanceApplySelectForm'
        },
        {
            xtype: 'maintenanceApplySelectGridAll'
        },
        {
            xtype: 'maintenanceApplySelectGridSelect'
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