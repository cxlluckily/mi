Ext.define('App.sysUser.sysUserPersonalForm', {
    extend: 'Ext.form.Panel',
    xtype: 'sysUserPersonalForm',
    autoScroll: true,
    fullscreen: true,
    requires: [
        'App.sysUser.sysUserWarehouseList'
    ],
    items: [{
        xtype: 'fieldset',
        items: [{
            xtype: 'fieldset',
            layout: 'form',
            title: '个人信息',
            items: [{
                xtype: 'textfield',
                name: 'realName',
                fieldLabel: '姓名',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                readOnly: true
            }, {
                xtype: 'textfield',
                name: 'warehouseInfoCopy',
                fieldLabel: '仓库初始化数据',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                readOnly: true,
                hidden: true
            }, {
                xtype: 'textfield',
                name: 'userId',
                fieldLabel: 'ID',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                blankText: '请输入姓名!',
                hidden: true
            }]
        },
            {
                xtype: 'fieldset',
                layout: 'form',
                defaults: {
                    anchor: '100%'
                },
                title: '角色',
                items: [{
                    xtype: 'checkboxgroup',
                    columns: 6,
                    name: 'roleIds',
                    items: []
                }]
            },
            {
                layout: 'form',
                xtype: 'fieldset',
                title: '维修员所负责的工区',
                items: [{
                    xtype: 'checkboxgroup',
                    name: 'workSectionIds',
                    columns: 6,
                    items: []
                }]
            },
            {
                xtype: 'fieldset',
                title: '库管负责的仓库及维修员对应取件的仓库',
                items: [{
                    xtype: 'userAuthWarehouseList'
                }]
            }
        ]
    }],
    listeners: {
        beforerender: function () {

        }
    }
});

Ext.define('App.sysUser.sysUserPersonalWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    recordData: '{recordData}',
    width: 800,
    height: 600,
    autoScroll: true,
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
    xtype: 'sysUserPersonalWindow',
    controller: 'sysUserController',
    items: [{
        xtype: 'sysUserPersonalForm'
    }],
    buttons: [{
        text: '保存',
        action: 'saveWarehouse'
    }, {
        text: '取消',
        handler: function (a) {
            this.up('window').close();
        }
    }],
    listeners: {
        beforerender: function (w) {
            var config = {
                url: ctx + 'userPermission/getUserAuthorizationInfoByUserId.do',
                data: {
                    "userKey": window.sessionStorage['userKey'],
                    "userId": w.recordData.userId
                },
                success: function (response) {
                    var response = JSON.parse(response.responseText);
                    var roleInfoArr = response.data.roleInfo; // 角色
                    var roleCheckGroups = w.down('checkboxgroup[name="roleIds"]'); // 获取弹出框的DOM
                    Ext.each(roleInfoArr, function (item) {
                        var checkItem = {
                            xtype: 'checkbox',
                            boxLabel: item.roleName,
                            labelAlign: 'right',
                            name: item.roleId,
                            autoHight: true,
                            checked: item.isChecked
                        };
                        roleCheckGroups.add(checkItem);
                    });
                    var sections = response.data.workSectionInfo; // 工区
                    var checkGroups = w.down('checkboxgroup[name="workSectionIds"]'); // 获取弹出框的DOM
                    Ext.each(sections, function (item) {
                        var checkItem = {
                            xtype: 'checkbox',
                            boxLabel: item.sectionName,
                            labelAlign: 'right',
                            name: item.workSectionId,
                            autoHight: true,
                            checked: item.isChecked,
                            listeners: {
                                change: function (component) {
                                    var workSections = component.up('window').down('checkboxgroup[name="workSectionIds"]').getValue();
                                    var workSectionIds = [];
                                    for (var k in workSections) {
                                        workSectionIds.push(k);
                                    }
                                    var warehouseInfoCopy = JSON.parse(component.up('window').down('textfield[name="warehouseInfoCopy"]').getValue());
                                    var warehouseInfo = [];
                                    Ext.each(warehouseInfoCopy, function (item) {
                                        Ext.each(workSectionIds, function (idItem) {
                                            if (item.workSectionId == idItem) {
                                                warehouseInfo.push(item);
                                            }
                                        });
                                    });
                                    var warehouseList = component.up('window').down('userAuthWarehouseList');
                                    var root = {
                                        expanded: true
                                    };
                                    root.children = warehouseInfo;
                                    warehouseList.getStore().setRoot(root);
                                    warehouseList.expandAll();
                                }
                            }
                        };
                        checkGroups.add(checkItem);
                    });
                    var warehouseInfoArr = response.data.warehouseInfo;
                    var warehouseInfoCopy = JSON.stringify(response.data.warehouseInfo);
                    w.down('sysUserPersonalForm').getForm().setValues({warehouseInfoCopy: warehouseInfoCopy});
                    response.data.warehouseInfoCopy = warehouseInfoCopy;

                    var warehouseInfo = [];
                    Ext.each(warehouseInfoArr, function (item) {
                        Ext.each(response.data.workSectionInfo, function (idItem) {
                            if (idItem.isChecked && item.workSectionId == idItem.workSectionId) {
                                warehouseInfo.push(item);
                            }
                        });
                    });

                    var warehouseionInfoCheckArr = []; // 缓存获取来的数据
                    var warehouseList = w.down('userAuthWarehouseList');
                    var root = {
                        expanded: true
                    };
                    root.children = warehouseInfo;
                    warehouseList.getStore().setRoot(root);
                    warehouseList.expandAll();
                },
                failure: function (response) {
                    console.log('server-side failure with status code ' + response.status);
                }
            };
            App.common.Ajax.request(config);
        }
    }
});