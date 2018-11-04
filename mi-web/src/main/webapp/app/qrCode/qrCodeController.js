Ext.define('App.qrCode.qrCodeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.qrCodeController',
    refViews:{
        window: 'App.qrCode.qrCodeAddOrEditWindow',
        View: 'qrCodeView',
        addUrl: 'code/batchInsert.do',
        deleteUrl: 'code/batchDelete.do',
    },
    control: {
        'qrCodeView button[action="search"]': {
            click: 'clickSearch'
        },
        'qrCodeView button[action="resets"]': {
            click: 'clickResets'
        },
        'qrCodeView button[action="add"]': {
            click: 'clickAdd'
        },
        'qrCodeView button[action="batchDel"]': {
            click: 'batchDel'
        },
        'qrCodeAddOrEditWindow button[action="save"]': {
            click: 'clickSave'
        }
        ,
        'qrCodeView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.View)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(btn){
        btn.up('form').getForm().reset();
        this.clickSearch();
    },
    clickAdd: function (btn) {
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.down('form').getForm().setValues({status: 'start_using'});
        extWindow.show();
    },
    batchDel: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1)
        {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条需要删除记录！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        }
        Ext.Msg.confirm('提示信息', '您是否执行删除操作？', function (c) {
            if (c == 'yes')
            {
                var data = {
                    qrCodeIds: []
                };
                Ext.each(records, function (item) {
                    data.qrCodeIds.push(item.data.qrCodeId);
                });
                var url = me.refViews.deleteUrl;
                var config = {
                    btn:btn,
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var result = Ext.decode(response.responseText);
                        if (result.result == 'success')
                        {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '批量删除成功',
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
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid())
        {
            return;
        }
        var data = form.getValues();
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add')
        {
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
    },
    //导出页面
    clickExportUpload: function (btn) {
    
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        tiaojian+="qrCode="+ formValues.qrCode;
        tiaojian+="&partName="+formValues.partName;
        tiaojian+="&status="+formValues.status;
        tiaojian+="&beginTime="+ formValues.beginTime  + " 00:00:00";
        tiaojian+="&endTime="+formValues.endTime + " 23:59:59";
       
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'code/qrCodeExport.do'+tiaojian);
        
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