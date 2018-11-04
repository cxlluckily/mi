Ext.define('App.roleAuthorization.roleAuthorizationList', {
    extend: 'Ext.grid.Panel',
    xtype: 'authList',
    height: 260,
    fullscreen: true,
    bodyStyle: "width:100%",
    viewConfig: {
        forceFit: true
    },
    store: {
        model: 'App.roleAuthorization.sysUserModel'
    },
    selModel: {
        selType: 'checkboxmodel',
        mode: 'MULTI',
        allowDeselect: true
    },
    tbar: [{
        xtype: 'button',
        text: '新增人员',
        action: 'addUser',
        iconCls: 'fa-plus-circle'
    }, {
        xtype: 'button',
        text: '移除',
        action: 'delUser',
        iconCls: 'fa-minus-circle'
    }],
    columnLines: true,
    columns: [{
        text: '序号',
        xtype: 'rownumberer',
        width: '10px',
        align: 'center'
    },
        {
            text: '姓名',
            dataIndex: 'realName',
            align: 'left',
            flex: 1
        },
        {
            text: '手机号',
            dataIndex: 'phone',
            align: 'left',
            flex: 1
        },
        {
            text: '用户名',
            dataIndex: 'userName',
            align: 'left',
            flex: 1,
            hidden:true
        },
        {
            text: '工号',
            dataIndex: 'workNumber',
            align: 'left',
            flex: 1,
            itemId: 'colType'
        },
        {
            text: '职位',
            dataIndex: 'position',
            align: 'left',
            flex: 1
        },
        {
            text: '部门',
            dataIndex: 'orgName',
            align: 'left',
            flex: 1
        }
        ]
    // plugins: {
    //     ptype: 'rowediting',
    //     clicksToEdit: 2
    // }
});

Ext.define('App.roleAuthorization.roleAuthorizationView', {
    extend: 'Ext.Panel',
    xtype: 'roleAuthorizationView',
    id: 'roleAuthorizationView',
    requires: [
        'App.common.selectStore',
        'App.common.selectWindow',
        'App.roleAuthorization.roleAuthorizationController',
        'App.roleAuthorization.roleAuthorizationWarehouseList',
        'App.roleAuthorization.roleAuthorizationUserStore',
        'App.roleAuthorization.roleAuthorizationWindow'
    ],
    controller: {
        xclass: 'App.roleAuthorization.roleAuthorizationController'
    },
    autoScroll: true,
    items: [{
        xtype: 'fieldset',
        items: [
            {
                xtype: 'textfield',
                name: 'warehouseInfoCopy',
                fieldLabel: '仓库初始化数据',
                hidden: true
            },
            {
                xtype: 'fieldset',
                title: '人员列表',
                items: [{
                    xtype: 'authList'
                }]
            },
            {
                xtype: 'fieldset',
                // layout: 'form',
                defaults: {
                    anchor: '100%'
                },
                title: '角色',
                items: [
                    {
                        xtype: 'checkboxgroup',
                        columns: 6,
                        name: 'roleIds',
                        defaults: {
                            xtype: 'checkbox',
                            labelAlign: 'right',
                            columnWidth: 1,
                            labelWidth: 300,
                            margin: 0,
                            padding: 0
                        },
                        items: []
                    }
                ],
                listeners: {
                    beforerender: function (comp) {
                        var controller = comp.up('roleAuthorizationView').getController();
                        controller.loadRoleIds();
                    }
                }
            },
            {
                // layout: 'form',
                xtype: 'fieldset',
                title: '维修员所负责的工区',
                items: [{
                    xtype: 'checkboxgroup',
                    name: 'workSectionIds',
                    columns: 6,
                    defaults: {
                        xtype: 'checkbox',
                        labelAlign: 'right',
                        columnWidth: 1,
                        labelWidth: 300,
                        margin: 0,
                        padding: 0
                    },
                    items: []
                }],
                listeners: {
                    beforerender: function (comp) {
                        var controller = comp.up('roleAuthorizationView').getController();
                        controller.loadWorkSectionIds();
                    }
                }
            },
            {
                xtype: 'fieldset',
                title: '库管负责的仓库及维修员对应取件的仓库',
                items: [{
                    xtype: 'authWarehouseList'
                }]
            }]
    }],
    buttons: [
        {
            text: '重置',
            margin: 10,
            action: 'reset',
            // hidden:true
        },
        {
            text: '保存',
            margin: 10,
            action: 'save'
        }
    ],
    listeners: {
        beforerender: function (me) {
            var config = {
                url: ctx + 'warehouse/getPagerInfo.do',
                data: {
                    operationSubjectId: window.sessionStorage['operationSubjectId'],
                    userKey: window.sessionStorage['userKey'],
                    start: 0,
                    limit: 1000,
                    status: 'start_using'
                },
                success: function (response) {
                    var obj = Ext.decode(response.responseText);
                    if (obj.result == "success") {
                        var root = {
                            expanded: true
                        };
                        root.children = obj.data;
                        var grid = me.down('authWarehouseList');
                        var warehouseInfoCopy = JSON.stringify(obj.data);
                        me.down('textfield[name="warehouseInfoCopy"]').setValue(warehouseInfoCopy);
                        // grid.getStore().setRoot(root);
                        // grid.expandAll();
                    } else if (obj.result == "failure") {
                        Ext.Msg.show({
                            title: '提示',
                            message: obj.message,
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                },
                failure: function (response) {
                    console.log('server-side failure with status code ' + response.status);
                }
            };
            App.common.Ajax.request(config);
        }
    }
});