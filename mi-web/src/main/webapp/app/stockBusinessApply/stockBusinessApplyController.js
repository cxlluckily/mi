Ext.define('App.stockBusinessApply.stockBusinessApplyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockBusinessApplyController',
    refViews: {
        view: 'stockBusinessApplyView',
        detailWindow: 'App.stockBusinessApply.stockBusinessApplyDetailWindow',
        selectWindow: 'App.stockBusinessApply.stockBusinessApplySelectWindow',
        selectAddWindow: 'App.stockBusinessApply.stockBusinessApplySelectAddWindow',
        bindWindow: 'App.stockBusinessApply.stockBusinessApplyBindWindow',
        lookWindow: 'App.stockBusinessApply.stockBusinessApplyLookWindow',
        detailUrl: 'applyInfo/getApplyInfoByApplyId.do',
        addUrl: 'applyInfo/addApplyInfo.do',
        updateUrl: 'applyInfo/updateApplyInfo.do',
        deleteUrl: 'applyInfo/deleteApplyInfo.do'
    },
    control: {
        'stockBusinessApplyView button[action="search"]': {
            click: 'clickSearch'
        },
        'stockBusinessApplyView button[action="resets"]': {
            click: 'clickResets'
        },
        'stockBusinessApplyView button[action="add"]': {
            click: 'clickAdd'
        },
        'stockBusinessApplyView button[action="look"]': {
            click: 'clickLook'
        },
        'stockBusinessApplyView button[action="update"]': {
            click: 'clickUpdate'
        },
        'stockBusinessApplyView button[action="delete"]': {
            click: 'clickDelete'
        },
        'stockBusinessApplyDetailWindow button[action="choose"]': {
            click: 'clickChoose'
        },
        'stockBusinessApplyDetailWindow button[action="save"]': {
            click: 'clickSave'
        },
        'stockBusinessApplySelectWindow button[action="search"]': {
            click: 'clickWindowSearch'
        },
        'stockBusinessApplySelectWindow button[action="save"]': {
            click: 'clickSelectSave'
        },
        'stockBusinessApplySelectWindow button[action="reset"]': {
            click: 'clickSelectReset'
        },
        'stockBusinessApplySelectAddWindow button[action="save"]': {
            click: 'clickAddSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'add') {
            var selectGrid = grid.up('window').down('stockBusinessApplySelectGridSelect');
            var selectGridData = selectGrid.store.getData().items;
            var checkOk = true;
            var checkIndex = 0;
            Ext.each(selectGridData, function (item, index) {
                if (item.data.sparePartId == record.data.sparePartId && record.data.stockStatus == item.data.stockStatus) {
                    checkOk = false;
                    checkIndex = index + 1;
                }
            });
            if (!checkOk) {
                Ext.Msg.show({
                    title: '警告',
                    message: '请在已选择列表中第' + checkIndex + '行中修改申请数量！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
                return;
            }
            var data = Ext.apply(record.data, {applyCount: 1});
            var store = selectGrid.getStore();
            var count = store.getCount();
            store.insert(count, data);

        } else if (flag == 'delete') {
            grid.getStore().remove(record);
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0].getActiveTab();
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
        btn.up('form').getForm().reset();
        btn.up('form').getForm().setValues({applyStatus: 'all'});
        this.clickSearch();
    },
    clickWindowSearch: function (btn) {
        btn.up('window').down('grid').store.load();
    },
    clickSelectReset:function(btn){
        var formValues = btn.up('form').getForm().getValues();
        btn.up('form').getForm().reset();
        btn.up('form').getForm().setValues({applyType:formValues.applyType,
            status:formValues.applyType=='return'?'bad':'normal',
            outWarehouseId:formValues.outWarehouseId});
        btn.up('window').down('grid').store.load();
        // btn.up('window').down('stockBusinessApplySelectGridSelect').store.load()
    },
    clickAdd: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.detailWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var applyType = btn.up('grid').applyType;
        extWindow.down('form').getForm().setValues({applyType: applyType});
        extWindow.show();
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
            btn:btn,
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
                    var detailGrid = extWindow.down('stockBusinessApplyLookGrid');
                    var listGrid = extWindow.down('stockBusinessApplyLookInfoListGrid');
                    var detailForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.details);
                    listGrid.store.setData(result.data.infoList);
                    detailForm.getForm().setValues(result.data);
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
    clickUpdate: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
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
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.detailWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('stockBusinessApplyDetailGrid');
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
    clickChoose: function (btn) {
        var me = this;
        var detailGrid = btn.up('window').down('grid');
        var selectGridData = [];
        var detailGridData = detailGrid.store.getData().items;
        Ext.each(detailGridData, function (item, index) {
            selectGridData.push(item.data);
        });
        var extWindow = Ext.create(me.refViews.selectWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var selectGrid = extWindow.down('stockBusinessApplySelectGridSelect');
        var formValues = btn.up('window').down('form').getForm().getValues();
        extWindow.down('form').getForm().setValues({applyType:formValues.applyType,outWarehouseId: formValues.outWarehouseId})
        selectGrid.store.setData(selectGridData);
        extWindow.show();
    },
    clickSave: function (btn) {
        var me = this;
        var win = btn.up('window');
        var detailFormData = win.down('form').getForm().getValues();
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '提示',
                message: '请填写必填项并确认输入格式正确！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        if (detailFormData.inWarehouseId == detailFormData.outWarehouseId) {
            Ext.Msg.show({
                title: '提示',
                message: '出库仓库与入库仓库不能相同！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var detailGridData = win.down('grid').store.getData().items;
        var data = detailFormData;
        var detailEntities = [];
        var checkOk = true;
        var checkIndex = 0;
        Ext.each(detailGridData, function (item, index) {
            if ((Number(item.data.applyCount) <= 0 || Number(item.data.applyCount) % 1) && checkOk) {
                checkOk = false;
                checkIndex = index + 1;
            }
            detailEntities.push(item.data);
        });
        if (detailEntities.length == 0) {
            Ext.Msg.show({
                title: '提示',
                message: '请至少选择一个备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        if (!checkOk) {
            Ext.Msg.show({
                title: '提示',
                message: '第' + checkIndex + '行申请数量必须为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        data.detailEntities = detailEntities;
        Ext.apply(data, {userKey: window.sessionStorage['userKey']});
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add') {
            url = me.refViews.addUrl;
        }
        if (viewAction == 'update') {
            url = me.refViews.updateUrl;
        }
        var config = {
            btn:btn,
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
    },
    clickAddSave: function (btn) {
        var formValue = btn.up('window').down('form').getForm().getValues();
        var selectWindow = Ext.ComponentQuery.query("stockBusinessApplySelectWindow")[0];
        var selectGrid = selectWindow.down('stockBusinessApplySelectGridSelect');
        var store = selectGrid.getStore();
        var count = store.getCount();
        store.insert(count, formValue);
        btn.up('window').close();
    },
    clickSelectSave: function (btn) {
        var selectGridData = btn.up('window').down('stockBusinessApplySelectGridSelect').getStore().getData().items;
        var newSelectGridData = [];
        Ext.each(selectGridData, function (item) {
            newSelectGridData.push(item.data);
        });
        var detailGridStore = Ext.ComponentQuery.query("stockBusinessApplyDetailWindow")[0].down('stockBusinessApplyDetailGrid').store;
        detailGridStore.setData(newSelectGridData);
        btn.up('window').close();
    },
    clickDelete: function (btn) {
        var me = this;
        Ext.Msg.confirm('提示信息', '确定要删除吗？', function (c) {
            if (c == 'yes') {
                var records = me.getView().getSelection();
                var record = records[0];
                var url = me.refViews.deleteUrl;
                var data = {applyId: record.data.applyId};
                var config = {
                    btn:btn,
                    url: url,
                    data: data,
                    success: function (response, opts) {
                        var responseData = Ext.decode(response.responseText);
                        if (responseData.result == "success") {
                            Ext.Msg.show({
                                title: '提示',
                                message: responseData.message,
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
                        me.clickSearch();
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
});