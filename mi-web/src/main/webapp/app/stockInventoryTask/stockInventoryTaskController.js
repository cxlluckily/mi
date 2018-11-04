Ext.define('App.stockInventoryTask.stockInventoryTaskController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockInventoryTaskController',
    refViews: {
        addWindow: 'App.stockInventoryTask.stockInventoryTaskAddWindow',
        updateWindow: 'App.stockInventoryTask.stockInventoryTaskUpdateWindow',
        lookWindow: 'App.stockInventoryTask.stockInventoryTaskLookWindow',
        view: 'stockInventoryTaskView',
        saveUrl: 'inventoryTask/updateInventoryTask.do',
        detailUrl: 'inventoryTask/getInventoryDetailEntity.do',
        addUrl: 'inventoryTask/insertOne.do',
        updateUrl: 'inventoryTask/updateOne.do'
    },
    control: {
        'stockInventoryTaskView button[action="search"]': {
            click: 'clickSearch'
        },
        'stockInventoryTaskView button[action="resets"]': {
            click: 'clickResets'
        },
        'stockInventoryTaskView button[action="add"]': {
            click: 'clickAdd'
        },
        'stockInventoryTaskAddWindow button[action="save"]': {
            click: 'clickAddSave'
        },
        'stockInventoryTaskUpdateWindow button[action="save"]': {
            click: 'clickUpdateSave'
        },
        'stockInventoryTaskUpdateWindow button[action="end"]': {
            click: 'clickUpdateSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'inventory') {
            var url = me.refViews.detailUrl;
            var data = {
                inventoryTaskId: record.data.inventoryTaskId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.updateWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form').getForm();
                        var grid = extWindow.down('grid');
                        form.setValues(result.data);
                        grid.getStore().setData(result.data.listDetail);
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
        else if (flag == 'update') {
            var url = me.refViews.detailUrl;
            var data = {
                inventoryTaskId: record.data.inventoryTaskId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.addWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form').getForm();
                        // var grid = extWindow.down('grid');
                        form.setValues(result.data);
                        // grid.getStore().setData(result.data.listDetail);
                        extWindow.show();
                    } else {
                        Ext.Msg.show({
                            title: '警告',
                            message: result.message,
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                }
            };
            App.common.Ajax.request(config);
        }
        else if (flag == 'look') {
            var url = me.refViews.detailUrl;
            var data = {
                inventoryTaskId: record.data.inventoryTaskId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.lookWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form').getForm();
                        var grid = extWindow.down('grid');
                        form.setValues(result.data);
                        grid.getStore().setData(result.data.listDetail);
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
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
        btn.up('form').getForm().reset();
        this.clickSearch();
    },
    clickAdd: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.addWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.show();
    },
    clickAddSave: function (btn) {
        var me = this;
        var viewAction = btn.up('window').viewAction;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        if (!form_up.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            // Ext.Msg.show({
            //     title: '警告',
            //     message: '请填写必填项并确认输入格式正确！',
            //     buttons: Ext.Msg.OK,
            //     icon: Ext.Msg.WARNING
            // });
            return;
        }
        var data = form.getValues();
        if(viewAction == 'update'){
            var url = me.refViews.updateUrl;
        }else{
            var url = me.refViews.addUrl;
        }
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    if(viewAction == 'update'){
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }else{
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    btn.up('window').close();
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
    clickUpdateSave: function (btn) {
        var me = this;
        var extWindow = btn.up('window');
        var formValues = extWindow.down('form').getForm().getValues();
        var gridData = extWindow.down('grid').getStore().getData().items;
        var entities = [];
        var checkIndex = false;
        Ext.each(gridData, function (item, index) {
            entities.push(item.data);
            if (!checkIndex && !App.common.regExpValidator.integer.test(item.data.aftierAccount)) {
                checkIndex = index + 1;
            }
        });
        if (checkIndex) { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行盘库数量应为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var data = {
            entities: entities,
            inventoryTaskId: formValues.inventoryTaskId,
            status: btn.action == 'save' ? 'ongoing' : 'already'
        };
        var url = me.refViews.saveUrl;
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '保存成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
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
        if(data.status == 'already'){
            Ext.Msg.confirm('提示信息', '结束盘点后无法再次修改，您确定要结束盘点吗？', function (c) {
                if (c == 'yes') {
                    App.common.Ajax.request(config);
                }
            })
        }else{
            App.common.Ajax.request(config);
        }
    }
});