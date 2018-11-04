Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockBusinessApplyAuditController',
    refViews:{
        window: 'App.stockBusinessApplyAudit.stockBusinessApplyAuditWindow',
        lookWindow: 'App.stockBusinessApplyAudit.stockBusinessApplyAuditLookWindow',
        view: 'stockBusinessApplyAuditView',
        detailUrl: 'applyInfo/getApplyInfoByApplyId.do',
        saveUrl: 'applyInfo/auditApplyInfo.do',
    },
    control: {
        'stockBusinessApplyAuditView button[action="search"]': {
            click: 'clickSearch'
        },
        'stockBusinessApplyAuditView button[action="look"]': {
            click: 'clickLook'
        },
        'stockBusinessApplyAuditView button[action="reset"]': {
            click: 'clickReset'
        },
        'stockBusinessApplyAuditView button[action="update"]': {
            click: 'clickUpdate'
        },
        'stockBusinessApplyAuditWindow button[action="save"]': {
            click: 'clickSave'
        },

    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'update') {
            var url = me.refViews.detailUrl;
            var data = {
                userKey: window.sessionStorage['userKey'],
                applyId: record.data.applyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.window, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('grid');
                        var detailForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.details);
                        detailForm.getForm().setValues(record.data);
                        extWindow.show();
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
        else if (flag == 'look') {
            var url = me.refViews.detailUrl;
            var data = {
                userKey: window.sessionStorage['userKey'],
                applyId: record.data.applyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.lookWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('grid');
                        var detailForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.details);
                        detailForm.getForm().setValues(record.data);
                        extWindow.show();
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
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0].getActiveTab();
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickReset:function(btn){
        btn.up('form').getForm().reset();
        this.clickSearch();
    },
    clickUpdate: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行审核！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            userKey: window.sessionStorage['userKey'],
            applyId: record.data.applyId
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.window, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('grid');
                    var detailForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.details);
                    detailForm.getForm().setValues(record.data);
                    extWindow.show();
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
    },
    clickLook: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行查看！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            userKey: window.sessionStorage['userKey'],
            applyId: record.data.applyId
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.lookWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('grid');
                    var detailForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.details);
                    detailForm.getForm().setValues(record.data);
                    extWindow.show();
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
    },
    clickSave: function (btn) {
        var me = this;
        var win = btn.up('window');
        var detailFormData = win.down('form').getForm().getValues();
        var form = btn.up('window').down('form');
        var detailGridData = win.down('grid').store.getData().items;
        var data = detailFormData;
        var detailEntities = [];
        Ext.each(detailGridData, function (item, index) {
            detailEntities.push(item.data);
        });
        data.detailEntities = detailEntities;
        if (data.applyStatus == undefined || data.applyStatus == "") {
            Ext.Msg.show({
                title: '警告',
                message: '请选择审核状态！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return
        }
        var url = me.refViews.saveUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '审核成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
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