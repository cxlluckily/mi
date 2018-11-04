Ext.define('App.operation.operationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.operationController',
    refViews: {
        window: 'App.operation.addOrEditWindow',
        view: 'operationView',
        addUrl: 'subject/insertOne.do',
        updateUrl: 'subject/updateOne.do',
        initUrl: 'subject/updateAdminPassword.do',
    },
    control: {
        'operationView button[action="search"]': {
            click: 'clickSearch'
        },
        'operationView button[action="add"]': {
            click: 'clickAdd'
        },
        'operationView button[action="update"]': {
            click: 'clickUpdate'
        },
        'operationView button[action="resets"]': {
            click: 'clickResets'
        },
        'operationView button[action="init"]': {
            click: 'clickInit'
        },
        'addOrEditWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    clickInit: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        } else {
            var record = records[0];
            var url = me.refViews.initUrl;
            var data = {
                operationSubjectIds: [record.data.operationSubjectId]
            };
            var config = {
                btn: btn,
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        Ext.Msg.show({
                            title: '提示',
                            message: '初始化密码成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                        // me.clickSearch();
                        // form.up('window').close();
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
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function () {
        Ext.getCmp('operationViewFormId').form.reset();
        this.clickSearch()
    },
    clickAdd: function (btn) {
        var extWindow = Ext.create(this.refViews.window, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').getForm().setValues({status: 'start_using'});
        extWindow.show();
    },
    clickUpdate: function (btn) {
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        } else {
            var record = records[0];
            var extWindow = Ext.create(this.refViews.window, {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                recordData: record.data
            });
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var data = form.getValues();
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add') {
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
                    if (viewAction == 'add') {
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
                    Ext.getCmp('saveId').setDisabled(true);
                    me.clickSearch();
                    form.up('window').close();
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: result.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                    Ext.getCmp('saveId').setDisabled(false);
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
                Ext.getCmp('saveId').setDisabled(false);
            }
        };
        App.common.Ajax.request(config);
    }
});