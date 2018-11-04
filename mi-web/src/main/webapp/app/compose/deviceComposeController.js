Ext.define('App.compose.deviceComposeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.deviceComposeController',
    refViews: {
        window: 'App.compose.deviceComposeWindow',
        viewWindow: 'App.compose.deviceComposeViewWindow',
        view: 'deviceComposeView',
        topView: 'deviceComposeTopView',
        addUrl: 'deviceCompose/insertOne.do',
        updateUrl: 'deviceCompose/updateOne.do',
        listUrl: 'deviceCompose/getComposeList.do',
        listTreeUrl: 'deviceCompose/getOneTreeData.do',
        deleteUrl: 'deviceCompose/deleteOne.do',
    },
    control: {
        'deviceComposeTopView button[action="search"]': {
            click: 'clickSearch'
        },
        'deviceComposeTopView button[action="addFirst"]': {
            click: 'clickAddFirst'
        },
        'deviceComposeView [actioncolumn]': {
            click: 'actionFn'
        },
        'deviceComposeWindow button[action="save"]': {
            click: 'clickSave'
        },
        'deviceComposeTopView button[action="resets"]': {
            click: 'clickResets'
        },
        'deviceComposeTopView button[action="delete"]': {
            click: 'clickDelete'
        },
        'deviceComposeTopView button[action="update"]': {
            click: 'clickUpdateTop'
        },
        'deviceComposeView button[action="update"]': {
            click: 'clickUpdate'
        },
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add')//新增同级
        {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({
                parentPartId: record.data.parentPartId,
                composePids: record.data.composePids,
                composePid: record.data.composePid,
                sparePartId: record.data.sparePartId,
                status: 'start_using'
            });
            w.show();
        }
        else if (flag == 'addChild')//新增下级
        {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({
                parentPartId: record.data.sparePartTypeId,
                composePids: record.data.composePids,
                composePid: record.data.deviceComposeId,
                sparePartId: record.data.sparePartId,
                status: 'start_using'
            });
            w.show();
        }
        else if (flag == 'update')
        {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues(record.data);
            w.show();
        }
        
    },
    clickUpdateTop: function (btn) {
        var records = this.getView().getSelection();
        if (records.length < 1)
        {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条准备修改的记录！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var data = {
            deviceComposeId: 0,
        };
        Ext.each(records, function (item) {
            data.deviceComposeId = item.data.deviceComposeId;
        });
        var w = Ext.create(this.refViews.viewWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        w.down('form').getForm().setValues(data);
        w.show();
    },
    clickUpdate: function (btn) {
        var records = Ext.ComponentQuery.query(this.viewWindow)[0].getSelectionModel().getSelection();
        var record = records[0];
        
        Ext.apply(record.data, {});
        var extWindow = Ext.create(this.thisWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    }
    ,
    clickDelete: function () {
        var records = this.getView().getSelection();
        var me = this;
        if (records.length < 1)
        {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条准备删除的记录！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        
        Ext.Msg.confirm('提示信息', '确定要执行删除吗？', function (c) {
            if (c == 'yes')
            {
                var data = {
                    deviceComposeId: 0
                };
                Ext.each(records, function (item) {
                    data.deviceComposeId = item.data.deviceComposeId;
                });
                var url = me.refViews.deleteUrl;
                var config = {
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var result = Ext.decode(response.responseText);
                        if (result.result == 'success')
                        {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '数据删除成功！',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.INFO
                            });
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
        })
    },
    loadTreeList: function (p) {
        var paraData = p.down('form').getForm().getValues();
        var config = {
            url: ctx + this.refViews.listTreeUrl,
            data: paraData,
            success: function (response) {
                
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success")
                {
                    var root = {
                        expanded: true
                    };
                    root.children = obj.data;
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
    clickAddFirst: function (btn) {
        var w = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        w.down('form').getForm().setValues({
            sparePartId: 0,
            parentPartId: -1,
            composePid: "-1",
            deviceComposeId: 0,
            status: "start_using"
        });
        w.show();
    },
    
    clickSearch: function () {
        var p = Ext.ComponentQuery.query(this.refViews.topView)[0];
        pageList.down('pagingtoolbar').moveFirst();
        p.getStore().load();
    },
    clickResets: function (btn) {
        btn.up('form').form.reset();
        this.clickSearch();
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
        var viewAction = btn.up('window').viewAction;
        Ext.apply(data, {flag: viewAction});
        var url = '';
        if (viewAction == 'add' || viewAction == 'addChild' || viewAction == 'addFirst')
        {
            url = me.refViews.addUrl;
        }
        if (viewAction == 'update')
        {
            url = me.refViews.updateUrl;
        }
        var config = {
            url: ctx + url,
            data: data,
            
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success")
                {
                    if (viewAction == 'addFirst')
                    {
                        var data = {
                            deviceComposeId: result.data,
                        };
                        var w = Ext.create(me.refViews.viewWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
                        w.down('form').getForm().setValues(data);
                        w.show();
                    }
                    if (viewAction == 'update')
                    {
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                        var p = Ext.ComponentQuery.query('deviceComposeViewWindow')[0].down('deviceComposeView');
                        me.loadTreeList(p);
                    }
                    if (viewAction == 'add' || viewAction == 'addChild')
                    {
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                        var p = Ext.ComponentQuery.query('deviceComposeViewWindow')[0].down('deviceComposeView');
                        me.loadTreeList(p);
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
