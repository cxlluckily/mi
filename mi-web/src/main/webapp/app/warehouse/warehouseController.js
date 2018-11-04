Ext.define('App.warehouse.warehouseController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.warehouseController',
    refViews:{
        window: 'App.warehouse.warehouseWindow',
        addWindow: 'App.warehouse.warehouseAddWindow',
        view: 'warehouseView', // 初始化数据接口
        addUrl: 'warehouse/addWarehouseInfo.do', // 新增接口
        updateUrl: 'warehouse/updateWarehouseInfo.do', // 修改接口
        deleteUrl: 'warehouse/deleteWarehouseInfo.do', // 删除接口
    },
    control: {
        'warehouseView button[action="search"]': {
            click: 'clickSearch'
        },
        'warehouseView button[action="add"]': {
            click: 'clickAdd'
        },
        'warehouseView [actioncolumn]': {
            click: 'actionFn'
        },
        'warehouseView button[action="resets"]': {
            click: 'clickResets'
        },
        'warehouseWindow button[action="save"]': {
            click: 'clickSave'
        },
        'warehouseAddWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    clickAdd: function (btn, rowIndex) {
        var w = Ext.create(this.refViews.addWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        w.down('form').getForm().setValues({description: "1级仓库"});
        w.show();
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        
        var flag = item.flag;
        var entity = {
            sectionName: record.data.sectionName,
            parentWarehouseId: record.data.parentWarehouseId,
            parentOrgId: record.data.parentOrgId,
            status: "start_using",
            parentId: record.data.parentId,
            workSectionIdHidden: record.data.workSectionId,
            workSectionId: record.data.workSectionId,
            warehouseId: record.data.warehouseId,
            flag: flag,
        };
        if (flag == 'add')
        {
            var w = Ext.create(this.refViews.window, {
                title: item.tooltip,
                viewAction: flag,
                iconCls: item.iconCls
            });
            entity.warehouseId = 0;
            Ext.apply(entity, {description: record.data.depth + '级仓库'})
            w.down('form').getForm().setValues(entity);
            w.show();
        }
        else if (flag == 'addChild')
        {
            var id = record.data.parentOrgId;
            var w = Ext.create(this.refViews.window, {
                title: item.tooltip,
                viewAction: flag,
                iconCls: item.iconCls
            });
            Ext.apply(entity, {description: (record.data.depth + 1) + '级仓库'})
            w.down('form').getForm().setValues(entity);
            var parent = Ext.ComponentQuery.query('textfield[name="parentWarehouseId"]')[0];
            parent.setValue(record.data.warehouseId);
            w.show();
        }
        else if (flag == 'update')
        {
            var w = Ext.create(this.refViews.window, {
                title: item.tooltip,
                viewAction: flag,
                iconCls: item.iconCls
            });
            Ext.apply(record.data,{workSectionIdHidden: record.data.workSectionId});
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
                        warehouseId: record.data.warehouseId
                    };
                    Ext.apply(data, {
                        userKey: window.sessionStorage['userKey']
                    });
                    
                    var config = {
                        url: ctx + me.refViews.deleteUrl,
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
        }
    },
    loadList: function (p) {
        var me = this;
        var formData = Ext.getCmp("warehouseViewFormId").getForm().getValues();
        formData.userKey = window.sessionStorage['userKey']; // 获取 userKey ID window.sessionStorage['userKey']
        var config = {
            url: ctx + 'warehouse/getPagerInfo.do',
            data: formData,
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
    
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.getController().loadList(pageList);
    },
    clickResets: function (btn) {
        btn.up('form').form.reset();
        // this.clickSearch();
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        var valueObj = form.getForm().getValues();
        valueObj.userKey = window.sessionStorage['userKey'];
        if (!form.getForm().isValid())
        {
            Ext.Msg.show({
                title: '提示',
                message: '请填写必填项或等待数据加载完成！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        if (!(valueObj.selectedStationIdList instanceof Array))
        {
            valueObj.selectedStationIdList = [valueObj.selectedStationIdList ? valueObj.selectedStationIdList : '']
        }
        if (valueObj.selectedStationIdList[0] == '')
        {
            Ext.Msg.show({
                title: '提示',
                message: '请选择站点或等待站点数据加载完成！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        valueObj.parentWarehouseId = valueObj.parentWarehouseId ? valueObj.parentWarehouseId : '-1';
        var data = valueObj;
        var url = '';
        var viewAction = btn.up('window').viewAction;
        // if (viewAction == 'add')
        // {
        //     url = me.refViews.addUrl;
        // }
        // if (viewAction == 'addChild')
        // {
        //     data.parentWarehouseId = data.warehouseId;
        //     url = me.refViews.addUrl;
        // }
        Ext.apply(data, {
            operationSubjectId: window.sessionStorage['operationSubjectId']
        });
        url = me.refViews.addUrl;
        if (viewAction == 'update')
        {
            url = me.refViews.updateUrl;
        }
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success")
                {
                    if (viewAction == 'add' || viewAction == 'addChild')
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