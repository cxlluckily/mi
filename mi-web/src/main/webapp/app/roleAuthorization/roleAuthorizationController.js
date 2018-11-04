Ext.define('App.roleAuthorization.roleAuthorizationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.roleAuthorizationController',
    refViews: {
        window: 'App.roleAuthorization.roleAuthorizationWindow',
        view: 'roleAuthorizationView'
    },
    control: {
        'roleAuthorizationView button[action="addUser"]': {
            click: 'openUserSelect'
        },
        'roleAuthorizationView button[action="delUser"]': {
            click: 'delSelectUser'
        },
        'roleAuthorizationView button[action="save"]': {
            click: 'saveAuth'
        },
        'roleAuthorizationView button[action="clear"]': {
            click: 'clickClear'
        },
        'roleAuthorizationView button[action="reset"]': {
            click: 'clickReset'
        },
        'roleAuthorizationView [actioncolumn]': {
            click: 'actionFn'
        },
        'roleAuthorizationWindow button[action="confirm"]': {
            click: 'addSelectUser'
        },
        'roleAuthorizationWindow button[action="resets"]': {
            click: 'clickResets'
        }
    },
    saveAuth: function (btn) {
        var userIds = [];
        var userGrid = Ext.ComponentQuery.query('authList')[0];
        var userData = userGrid.getStore().getData().items;
        Ext.each(userData, function (item) {
            var rec = item.data;
            userIds.push(rec.userId);
        });
        if (userIds.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择授权人员！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var warehousegrid = Ext.ComponentQuery.query('authWarehouseList')[0];
        var roles = btn.ownerCt.ownerCt.down('checkboxgroup[name="roleIds"]').getValue()
        var roleIds = [];
        for (var k in roles) {
            roleIds.push(k);
        }
        var workSections = btn.ownerCt.ownerCt.down('checkboxgroup[name="workSectionIds"]').getValue()
        var workSectionIds = [];
        for (var k in workSections) {
            workSectionIds.push(k);
        }
        var warehouseIds = [];
        var datas = warehousegrid.getStore().getData().items;
        Ext.each(datas, function (item) {
            var rec = item.data;
            var verified = rec.verified;
            if (verified) {
                warehouseIds.push(rec.warehouseId);
            }
        });
        if (roleIds.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择授权的角色！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var params = {
            userKey: window.sessionStorage['userKey'],
            operationSubjectId: window.sessionStorage['operationSubjectId'],
            userIds: userIds,
            roleIds: roleIds,
            warehouseIds: warehouseIds,
            workSectionIds: workSectionIds
        };
        var config = {
            url: ctx + 'userPermission/insertBatchAuthorizationInfo.do',
            data: params,
            success: function (response, opts) {
                var responseData = Ext.decode(response.responseText);
                if (responseData.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '授权成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
            },
            failure: function (response, opts) {
                console.log('server-side failure with status code '
                    + response.status);
                Ext.Msg.show({
                    title: '错误',
                    message: response,
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.error
                });
            }
        };
        App.common.Ajax.request(config);
    },
    delSelectUser: function (btn) {
        var grid = btn.up('grid');
        var records = grid.getSelectionModel().getSelection();
        grid.getStore().remove(records);
    },
    addSelectUser: function (btn) {
        var grid = btn.up('window').down('grid');
        var records = grid.getSelectionModel().getSelection();
        var userGrid = Ext.ComponentQuery.query('authList')[0];
        var store = userGrid.getStore();
        var storeArray = store.getData().items;
        var repeatData = [];
        for (var i = 0; i < records.length; i++) {
            var record = records[i];
            storeArray.forEach(function (item) {
                if (item.data.userId == record.data.userId) {
                    repeatData.push(record.data.realName);
                }
            });
        }
        if (repeatData.length > 0) {
            var text = repeatData.join(',');
            Ext.Msg.show({
                title: '警告',
                message: text + '已选择，请重新选择！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        for (var i = 0; i < records.length; i++) {
            var record = records[i];
            var count = store.getCount();
            record.id = 'new_' + count;
            store.insert(count, record);
        }
        btn.up('window').close();
    },
    clickSearch: function (btn) {
        var values = btn.up('form').getForm().getValues();
        var store = btn.up('window').down('grid').getStore();
        store.proxy.extraParams = Ext.apply(store.proxy.extraParams, values);
        store.reload();
    },
    clickResets: function (btn) {
        Ext.getCmp('roleAuthForm').form.reset();
        this.clickSearch(btn);
    },
    clickReset: function (btn) {
        var me = this;
        btn.up('roleAuthorizationView').down('authList').getStore().setData([]);
        btn.up('roleAuthorizationView').down('authWarehouseList').getStore().setRoot('');
        me.loadRoleIds();
        me.loadWorkSectionIds();
    },
    loadRoleIds:function(){
        var me = this;
        var view = me.getView();
        var config = {
            url: ctx + 'roleInfo/all.do',
            data: {
                operationSubjectId: window.sessionStorage['operationSubjectId'],
                userKey: window.sessionStorage['userKey'],
                status: 'start_using'
            },
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    var data = obj.data;
                    var checkboxGroup = view.down('checkboxgroup[name="roleIds"]');
                    checkboxGroup.removeAll();
                    var checkItems = [];
                    Ext.each(data, function (item) {
                        item.isCheck = item.isCheck == 'true';
                        var checkItem = {
                            boxLabel: item.roleName,
                            name: item.roleId,
                            checked: item.isCheck
                        };
                        checkItems.push(checkItem);
                    });
                    checkboxGroup.add(checkItems);
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
    },
    loadWorkSectionIds: function () {
        var me = this;
        var view = me.getView();
        var config = {
            url: ctx + 'workSection/getWorkSectionInfo.do',
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
                    var data = obj.data.rows;
                    var checkboxGroup = view.down('checkboxgroup[name="workSectionIds"]');
                    checkboxGroup.removeAll();
                    var checkItems = [];
                    Ext.each(data, function (item) {
                        item.isCheck = item.isCheck == 'true';
                        var checkItem = {
                            boxLabel: item.sectionName,
                            name: item.workSectionId,
                            checked: item.isCheck,
                            listeners: {
                                change: function (component) {
                                    var workSections = component.up('roleAuthorizationView').down('checkboxgroup[name="workSectionIds"]').getValue();
                                    var workSectionIds = [];
                                    for (var k in workSections) {
                                        workSectionIds.push(k);
                                    }
                                    var warehouseInfoCopy = JSON.parse(component.up('roleAuthorizationView').down('textfield[name="warehouseInfoCopy"]').getValue());
                                    var warehouseInfo = [];
                                    Ext.each(warehouseInfoCopy, function (item) {
                                        Ext.each(workSectionIds, function (idItem) {
                                            if (item.workSectionId == idItem) {
                                                warehouseInfo.push(item);
                                            }
                                        });
                                    });

                                    var warehouseList = component.up('roleAuthorizationView').down('authWarehouseList');
                                    var root = {
                                        expanded: true
                                    };
                                    root.children = warehouseInfo;
                                    warehouseList.getStore().setRoot(root);
                                    warehouseList.expandAll();
                                }
                            }
                        };
                        checkItems.push(checkItem);
                    });
                    checkboxGroup.add(checkItems);
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
    },

    openUserSelect: function (btn) {
        var window = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action});
        window.show();
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add') {
            // var oId = Ext.getCmp(this.refViews.view).getValues();
            var id = record.data.parentOrgId;
            var window = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag});
            window.down('form').getForm().setValues({parentOrgId: id, status: 'start_using'});
            window.show();
        } else if (flag == 'addChild') {
            var id = record.data.orgId;
            var window = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag});
            window.down('form').getForm().setValues({parentOrgId: id, status: 'start_using'});
            window.show();
        } else if (flag == 'edit') {
            var window = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag});
            window.down('form').getForm().setValues(record.data);
            window.show();
        } else if (flag == 'delete') {
            var me = this;
            if (!record) {
                Ext.Msg.show({
                    title: '警告',
                    message: '请选择一条记录进行删除！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            }
            Ext.Msg.confirm('提示信息', '确定要删除吗？', function (c) {
                if (c == 'yes') {
                    var data = {
                        orgId: record.data.orgId
                    };
                    Ext.apply(data, {userKey: window.sessionStorage['userKey']});

                    var config = {
                        url: ctx + me.thisDeleteUrl,
                        data: data,
                        success: function (response, opts) {
                            var responseData = Ext.decode(response.responseText);
                            if (responseData.result == "success") {
                                Ext.Msg.show({
                                    title: '提示',
                                    message: '删除成功！',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.INFO
                                });
                                me.clickSearch();
                            } else {
                                Ext.Msg.show({
                                    title: '警告',
                                    message: responseData.message,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                            }
                        },

                        failure: function (response, opts) {
                            console.log('server-side failure with status code '
                                + response.status);
                            Ext.Msg.show({
                                title: '错误',
                                message: response,
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.error
                            });
                        }
                    };
                    App.common.Ajax.request(config);
                }
            });
        }
    },
    clickClear: function (btn) {
        console.log('reset');
        // btn.up('roleAuthorizationView').onRender();
        console.log(btn.up('roleAuthorizationView'));
        // btn.up('roleAuthorizationView').reload();
    }
});
