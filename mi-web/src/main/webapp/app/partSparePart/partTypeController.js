Ext.define('App.partSparePart.partTypeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.partTypeController',
    refViews: {
        view: 'partTypeView',
        addUrl: 'line/insertLine.do',
        listUrl: 'sparePartType/getSparePartTypeInfo.do',
        updateUrl: 'line/updateLine.do',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        importTypeUrl: 'sparePart/importSparePartTypeList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'partTypeView': {
            selectionchange: 'selectionchange'
        },
        'partTypeView button[action="add"]': {
            click: 'clickAdd'
        },
        'partTypeView button[action="update"]': {
            click: 'clickUpdate'
        },
        'partTypeView button[action="reset"]': {
            click: 'clickSearch'
        },
        'partTypeWindow button[action="save"]': {
            click: 'clickSave'
        },
        'partTypeView button[action="importTypeDownload"]': {
            click: 'clickImportTypeDownload'
        },
        'partTypeView button[action="exportTypeUpload"]': {
            click: 'clickExportTypeUpload'
        }
    },
    selectionchange: function (component, selected, eOpts) {
        var p = Ext.ComponentQuery.query('partView')[0];
        if(selected.length == 0){
            p.getStore().setData([]);
            return;
        }
        p.getController().clickSearch(p);
    },
    loadList: function (p) {
       
        var me = this;
        var config = {
            url: ctx + this.refViews.listUrl,
            data: {
                categoryName: '',
                status:'start_using'
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

    clickSearch: function () {
        var p = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        p.getController().loadList(p);
    },
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('partTypeViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.thisWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.down('form').getForm().setValues(
            {
                operationSubjectId: formValues.operationSubjectId,
                status: 'start_using'
            }
        );
        extWindow.show();
    },
    clickUpdate: function (btn) {
        var records = Ext.ComponentQuery.query(this.refViews.view)[0].getSelectionModel().getSelection();
        if (records.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        } else {
            var record = records[0];
            Ext.apply(record.data, {userKey: window.sessionStorage['userKey']});
            var extWindow = Ext.create(this.thisWindow, {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls
            });
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickSave: function (btn) {
        var me = this;
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
        var data = form.getValues();
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
            btn: btn,
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
    clickImportTypeDownload: function (btn) {
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData:{
                    url:ctx+this.refViews.importTypeUrl,
                    downUrl:ctx+this.refViews.downUrl,
                    filename:"BeiJianTypeMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        extWindow.show();
    },
    //导出页面
    clickExportTypeUpload: function (btn) {
        
       // var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
       // tiaojian+="partName="+ formValues.partName;
       // tiaojian+="&brand="+formValues.brand;
        tiaojian+="&status=all";
        
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'sparePart/sparePartTypeExport.do'+tiaojian);
        
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