Ext.define('App.orgOrganization.orgOrganizationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.orgOrganizationController',
    refViews: {
        window: 'App.orgOrganization.orgOrganizationWindow',
        view: 'orgOrganizationView',
        addUrl: 'organization/insertOne.do',
        updateUrl: 'organization/updateOne.do',
        deleteUrl: 'organization/updateStatusIsStopUsing.do'
    },
    control: {
        'orgOrganizationView button[action="search"]': {
            click: 'clickSearch'
        },
        'orgOrganizationView [actioncolumn]': {
            click: 'actionFn'
        },
        'orgOrganizationWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add') {
            // var oId = Ext.getCmp(this.refViews.view).getValues();
            var id = record.data.parentOrgId;
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({parentOrgId: id, status: 'start_using'});
            w.show();
        } else if (flag == 'addChild') {
            var id = record.data.orgId;
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({parentOrgId: id, status: 'start_using'});
            w.show();
        } else if (flag == 'update') {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues(record.data);
            w.show();
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
                        url: ctx + me.refViews.deleteUrl,
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
    loadList: function (p) {
        var me = this;
        var formData = Ext.getCmp("orgOrganizationViewFormId").getForm().getValues();
        var config = {
            url: ctx + 'organization/listOrganization.do',
            data: {},
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    var root = {
                        expanded: true
                    };
                    root.children = obj.data.rows;
                    p.getStore().setRoot(root);
                    p.expandAll();
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

    clickSearch: function () {
        var p = Ext.ComponentQuery.query(this.refViews.view)[0];
        p.getController().loadList(p);
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var data = form.getValues();
        Ext.apply(data, {userKey: window.sessionStorage['userKey']});
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add' || viewAction == 'addChild') {
            url = me.refViews.addUrl;
        }
        if (viewAction == 'update') {
            url = me.refViews.updateUrl;
        }
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    if (viewAction == 'add' || viewAction == 'addChild') {
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    if (viewAction == 'update') {
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    form.up('window').close();
                    me.clickSearch();
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: result.message,
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
