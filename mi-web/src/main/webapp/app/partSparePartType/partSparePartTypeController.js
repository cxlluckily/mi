Ext.define('App.partSparePartType.partSparePartTypeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.partSparePartTypeController',
    refViews:{
        window: 'App.partSparePartType.partSparePartTypeWindow',
        view: 'partSparePartTypeView',
        addUrl: 'sparePartType/insertOne.do',
        updateUrl: 'sparePartType/updateOne.do',
        listUrl: 'sparePartType/getSparePartTypeInfo.do'
    },
    control: {
        'partSparePartTypeView button[action="search"]': {
            click: 'clickSearch'
        },
        'partSparePartTypeView button[action="addFirst"]': {
            click: 'clickAddFirst'
        },
        'partSparePartTypeView [actioncolumn]': {
            click: 'actionFn'
        },
        'partSparePartTypeWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add') {
            // var formData = Ext.getCmp("partSparePartTypeViewFormId").getForm().getValues();
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag,iconCls:item.iconCls});
            w.down('form').getForm().setValues({
                parentPartId: record.data.parentPartId,
                operationSubjectId:record.data.operationSubjectId,
                sparePartTypeIds:record.data.sparePartTypeIds.split(record.data.sparePartTypeId)[0],
                status: 'start_using'});
            w.show();
        } else if (flag == 'addChild') {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag,iconCls:item.iconCls});
            w.down('form').getForm().setValues({
                parentPartId: record.data.sparePartTypeId,
                operationSubjectId:record.data.operationSubjectId,
                sparePartTypeIds:record.data.sparePartTypeIds,
                status: 'start_using'});
            w.show();
        } else if (flag == 'update') {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag,iconCls:item.iconCls});
            w.down('form').getForm().setValues(record.data);
            w.show();
        }

    },
    loadList: function (p) {
        var me = this;
        var formData = Ext.getCmp("partSparePartTypeViewFormId").getForm().getValues();
        var config = {
            url: ctx + this.refViews.listUrl,
            data: {
                userKey: window.sessionStorage['userKey'],
                operationSubjectId: formData.operationSubjectId,
                categoryName: formData.categoryName
            },
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    var root = {
                        expanded: true
                    };
                    root.children = obj.data;
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
    clickAddFirst:function(btn){
        var formData = Ext.getCmp("partSparePartTypeViewFormId").getForm().getValues();
        var w = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
        w.down('form').getForm().setValues({
            parentPartId: '-1',
            operationSubjectId:formData.operationSubjectId,
            status: 'start_using'});
        w.show();
    },

    clickSearch: function () {
        var p = Ext.ComponentQuery.query(this.refViews.view)[0];
        p.getController().loadList(p);
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            // Ext.Msg.show({
            //     title: '提示',
            //     message: '请填写必填项并确认输入格式正确！',
            //     buttons: Ext.Msg.OK,
            //     icon: Ext.Msg.INFO
            // });
            return;
        }
        var viewAction = btn.up('window').viewAction;
        var data = form.getValues();
        Ext.apply(data, {flag: viewAction});
        var url = '';
        if (viewAction == 'add' || viewAction == 'addChild'||viewAction == 'addFirst') {
            url = me.refViews.addUrl;
        }
        if (viewAction == 'update') {
            url = me.refViews.updateUrl;
        }
        var config = {
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
