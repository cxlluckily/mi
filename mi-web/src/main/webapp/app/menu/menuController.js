Ext.define('App.menu.menuController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.menuController',
    refViews:{
        window: 'App.menu.menuAddOrEditWindow',
        view: 'menuView',
        addUrl: 'menu/insertOne.do',
        updateUrl: 'menu/updateOne.do'
    },
    control: {
        'menuView button[action="search"]': {
            click: 'clickSearch'
        },
        'menuView button[action="resets"]': {
            click: 'clickReset'
        },
        'menuView [actioncolumn]': {
            click: 'actionFn'
        },
        'menuAddOrEditWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add')
        {
            // var oId = Ext.getCmp(this.refViews.view).getValues();
            var id = record.data.parentTreeId;
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            // var treeTypeValue = Ext.getCmp(this.refViews.view).down("form").getForm().getValues().treeType;
            w.down('form').getForm().setValues({parentTreeId: id, status: 'start_using', treeType: record.data.treeType});
            w.show();
        }
        else if (flag == 'addChild')
        {
            var id = record.data.functionTreeId;
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({parentTreeId: id, status: 'start_using', treeType: record.data.treeType});
            w.show();
        }
        else if (flag == 'update')
        {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues(record.data);
            w.show();
        }
        else if (flag == 'delete')
        {
            var me = this;
            if (!record)
            {
                Ext.Msg.show({
                    title: '警告',
                    message: '请选择一条记录进行删除！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            }
            Ext.Msg.confirm('提示信息', '确定要删除吗？', function (c) {
                if (c == 'yes')
                {
                    var data = {
                        orgId: record.data.orgId
                    };
                    Ext.apply(data, {userKey: window.sessionStorage['userKey']});
                    
                    var config = {
                        url: ctx + me.thisDeleteUrl,
                        data: data,
                        success: function (response, opts) {
                            var responseData = Ext.decode(response.responseText);
                            if (responseData.result == "success")
                            {
                                Ext.Msg.show({
                                    title: '提示',
                                    message: '删除成功！',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.INFO
                                });
                                me.clickSearch();
                            }
                            else
                            {
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
    clickSearch: function () {
        var p = Ext.ComponentQuery.query(this.refViews.view)[0];
        p.getController().loadList(p);
    },
    clickReset:function(btn){
      btn.up('form').form.reset();
    this.clickSearch();
    },
    loadList: function (p) {
        var me = this;
        var formData = Ext.getCmp("menuViewFormId").getForm().getValues();
        var config = {
            url: ctx + 'menu/getAllMenuList.do',
            data: formData,
            success: function (response) {
                var obj = Ext.decode(response.responseText)
                if (obj.result == "success")
                {
                    var root = {
                        expanded: true
                    };
                    root.children = obj.data.rows;
                    p.getStore().setRoot(root);
                    p.expandAll();
                }
                else if (obj.result == "failure")
                {
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
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid())
        { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '提示',
                message: '请填写必填项并确认输入格式正确！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var data = form.getValues();
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'update')
        {
            url = me.refViews.updateUrl;
        }else{
            url = me.refViews.addUrl;
        }
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success")
                {
                    if (viewAction == 'add')
                    {
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    if (viewAction == 'update')
                    {
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    form.up('window').close();
                    me.clickSearch();
                }
                else
                {
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