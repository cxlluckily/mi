Ext.define('App.operationsEquipment.operationsEquipmentController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.operationsEquipmentController',
    refViews: {
        window: 'App.operationsEquipment.operationsEquipmentWindow',
        lookWindow: 'App.operationsEquipment.operationsEquipmentLookWindow',
        view: 'operationsEquipmentView',
        deleteUrl: 'operationsEquipment/deleteEquipment.do',
        detailUrl: 'operationsEquipment/getEquipment.do',
        addUrl: 'operationsEquipment/addEquipment.do',
        updateUrl: 'operationsEquipment/updateEquipment.do',
        importUrl: 'operationsEquipment/importEquipmentList.do',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'operationsEquipmentView button[action="search"]': {
            click: 'clickSearch'
        },
        'operationsEquipmentView button[action="resets"]': {
            click: 'clickResets'
        },
        'operationsEquipmentView button[action="add"]': {
            click: 'clickAdd'
        },
        'operationsEquipmentWindow button[action="save"]': {
            click: 'clickSave'
        },
        'operationsEquipmentView button[action="importDownload"]': {
            click: 'clickImportDownload'
        }
        ,
        'operationsEquipmentView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'update')
        {
            var url = me.refViews.detailUrl;
            var data = {
                equipmentId: record.data.equipmentId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success")
                    {
                        var extWindow = Ext.create(me.refViews.window, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form').getForm();
                        // var grid = extWindow.down('grid');
                        form.setValues(result.data);
                        // grid.getStore().setData(result.data.listDetail);
                        extWindow.show();
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
        else if (flag == 'look')
        {
            var url = me.refViews.detailUrl;
            var data = {
                equipmentId: record.data.equipmentId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success")
                    {
                        var extWindow = Ext.create(me.refViews.lookWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form').getForm();
                        var grid = extWindow.down('grid');
                        form.setValues(result.data);
                        grid.getStore().setData(result.data.spareParts);
                        Ext.each(result.data.images, function (item) {
                            item.picUrl = item.imageUrl;
                        });
                        var imagesPanel = extWindow.down('commonImagesLook');
                        imagesPanel.down('dataview').getStore().setData(result.data.images);
                        extWindow.show();
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
        else if (flag == 'delete')
        {
            Ext.Msg.confirm('提示信息', '确定要删除吗？', function (c) {
                if (c == 'yes')
                {
                    var data = {
                        equipmentId: record.data.equipmentId
                    };
                    var url = me.refViews.deleteUrl;
                    var config = {
                        url: ctx + url,
                        data: data,
                        success: function (response, opts) {
                            var responseText = Ext.decode(response.responseText);
                            if (responseText.result == 'success')
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
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
       
        var form = Ext.getCmp('operationsEquipmentViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickAdd: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.window, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').getForm().setValues({status: 'start_using'});
        extWindow.show();
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        if (!form_up.getForm().isValid())
        { // 验证表单 , 如果为空, 不让发送请求
            // Ext.Msg.show({
            //     title: '警告',
            //     message: '请填写必填项并确认输入格式正确！',
            //     buttons: Ext.Msg.OK,
            //     icon: Ext.Msg.WARNING
            // });
            return;
        }
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add')
        {
            var url = me.refViews.addUrl;
        }
        else
        {
            var url = me.refViews.updateUrl;
        }
        var data = form.getValues();
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
                    else
                    {
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                    }
                    btn.up('window').close();
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
    },
    clickDelete: function (btn) {
        var me = this;
        var records = me.getView().getSelection();
        if (records.length < 1)
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
                    equipmentId: records[0].data.equipmentId
                };
                var url = me.refViews.deleteUrl;
                var config = {
                    btn:btn,
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var responseText = Ext.decode(response.responseText);
                        if (responseText.result == 'success')
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
    clickImportDownload: function (btn) {
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData: {
                    url: ctx + this.refViews.importUrl,
                    downUrl: ctx + this.refViews.downUrl,
                    filename: "XianShangSheBeiMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    //导出页面
    clickExportUpload: function (btn) {
    
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        tiaojian+="stationId="+ formValues.stationId;
        tiaojian+="&sparePartTypeId="+formValues.sparePartTypeId;
        tiaojian+="&equipmentNo="+formValues.equipmentNo;
        tiaojian+="&partName="+formValues.partName;
        tiaojian+="&materiaCoding="+formValues.materiaCoding;
        tiaojian+="&specificationModel="+formValues.specificationModel;
        tiaojian+="&brand="+formValues.brand;
        
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'operationsEquipment/equipmentExport.do'+tiaojian);
        
        var userKeyInput = document.createElement("input");
        userKeyInput.setAttribute("id", Math.random());
        userKeyInput.setAttribute("type", "text");
        userKeyInput.setAttribute("name", "userKey");
        userKeyInput.setAttribute("value", window.sessionStorage['userKey']);
        form.append(userKeyInput);
        
        var operationInput = document.createElement("input");
        operationInput.setAttribute("id", Math.random());
        operationInput.setAttribute("type", "text");
        operationInput.setAttribute("name", "operationSubjectId");
        operationInput.setAttribute("value", window.sessionStorage['operationSubjectId']);
        form.append(operationInput);
    
     
        
        document.getElementsByTagName("body")[0].append(form);
        setTimeout(function () {
            form.submit();
            setTimeout(function () {
                form.remove();
            }, 1000);
        }, 100);
    }
});