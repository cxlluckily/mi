Ext.define('App.shelf.shelfController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.shelfController',
    refViews:{
        window: 'App.shelf.shelfWindow',
        view: 'shelfView'
    },
    control: {
        'shelfView button[action="search"]': {
            click: 'clickSearch'
        },
        'shelfView button[action="add"]': {
            click: 'clickAdd'
        },
        'shelfView button[action="column"]': {
            click: 'actionFn'
        },
        // 'shelfView button[action="look"]': {
        //     click: 'clickLook'
        // },
        'shelfView button[action="resets"]': {
            click: 'clickResets'
        },
        'shelfWindow button[action="save"]': {
            click: 'clickSave'
        },
        'shelfModifyWindow button[action="save"]': {
            click: 'clickSaveModify'
        }
    },

    // 每一条数据的 删除 , 修改功能
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        switch (flag) {
            case 'update':
                var w = Ext.create('App.shelf.shelfModifyWindow', {
                    title: '修改',
                    iconCls: 'fa-edit'
                });
                w.down('form').getForm().setValues(record.data);
                w.show();
                break;
            case 'look':
                var w = Ext.create('App.shelf.shelfLookWindow', {
                    title: '查看',
                    iconCls: 'fa-eye'
                });
                w.down('form').getForm().setValues(record.data);
                w.show();
                break;
            default:
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
                            goodsShelvesId: record.data.goodsShelvesId
                        };
                        Ext.apply(data, {
                            userKey: window.sessionStorage['userKey']
                        });
                        var config = {
                            url: ctx + 'shelves/deleteShelvesInfo.do',
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
                                console.log('server-side failure with status code ' +
                                    response.status);
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
                break;
        }
    },
    // 查看页面
    clickLook:function(btn){
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
                    var extWindow = Ext.create(me.refViews.lookWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
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
    // 查询功能
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().proxy.extraParams = Ext.getCmp('shelfViewFormId').getForm().getValues();
        pageList.getStore().load();
    },
    clickResets:function(){
        Ext.getCmp('shelfViewFormId').form.reset();
        this.clickSearch()
    },
    // 新增功能
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('shelfViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.refViews.window, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').getForm().setValues({
            operationSubjectName: formValues.operationSubjectName,
            operationSubjectId: formValues.operationSubjectId,
            workSectionId: 0,
            status: 'start_using'
        });
        extWindow.show();
    },
    // 新增保存功能
    clickSave: function (btn) { 
        var me = this;
        var form = btn.up('window').down('form');
        var valueObj = form.getForm().getValues();
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        // valueObj.containerType = form.down('combo[name="containerType"]').getRawValue();
        var config = {
            btn:btn,
            url: ctx + 'shelves/addShelvesInfo.do',
            data: valueObj,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '新增成功！',
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
                console.log('server-side failure with status code ' +
                    response.status);
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
    // 修改功能
    clickSaveModify: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        var valueObj = form.getForm().getValues();
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '提示',
                message: '请填写必填项并确认输入格式正确！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var config = {
            btn:btn,
            url: ctx + 'shelves/updateShelvesInfo.do',
            data: valueObj,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '修改成功！',
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
                console.log('server-side failure with status code ' +
                    response.status);
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