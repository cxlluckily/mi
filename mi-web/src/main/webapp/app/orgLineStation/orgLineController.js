Ext.define('App.orgLineStation.orgLineController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.orgLineController',
    refViews:{
        window: 'App.orgLineStation.orgLineWindow',
        view: 'orgLineView',
        addUrl: 'line/insertLine.do',
        listUrl: 'line/getLineInfo.do',
        updateUrl: 'line/updateLine.do',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        importLineUrl: 'station/importLineStationList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'orgLineView': {
            selectionchange: 'selectionchange'
        },
        'orgLineView button[action="search"]': {
            click: 'clickSearch'
        },
        'orgLineView button[action="add"]': {
            click: 'clickAdd'
        },
        'orgLineView button[action="update"]': {
            click: 'clickUpdate'
        },
        'orgLineWindow button[action="save"]': {
            click: 'clickSave'
        },
        'orgLineView button[action="importLineDownload"]': {
            click: 'clickImportLineDownload'
        },
        'orgLineView button[action="exportLineUpload"]': {
            click: 'clickExportLineUpload'
        }
    },
    selectionchange: function () {
        var p = Ext.ComponentQuery.query('orgStationView')[0];
        p.getController().clickSearch(p);
    },
    clickResets:function(){
        var form = Ext.getCmp('orgStationViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    loadList: function (p) {
        var me = this;
        var formData = Ext.getCmp("orgLineViewFormId").getForm().getValues();
        var config = {
            url: ctx + this.refViews.listUrl,
            data: {
                userKey: window.sessionStorage['userKey'],
                operationSubjectId: formData.operationSubjectId
            },
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    var root = {
                        expanded: true
                    };
                    root.children = obj.data.rows;
                    Ext.each(root.children, function (item) {
                        item.leaf = true;
                    });
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
        // pageList.down('pagingtoolbar').moveFirst();
        p.getController().loadList(p);
    },
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('orgLineViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
        extWindow.down('form').getForm().setValues(
            {
                operationSubjectId: formValues.operationSubjectId,
                status:'start_using'
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
            var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
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
    clickImportLineDownload: function (btn) {
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData:{
                    url:ctx+this.refViews.importLineUrl,
                    downUrl:ctx+this.refViews.downUrl,
                    filename:"XianLuCheZhanMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        extWindow.show();
    },
    //导出页面
    clickExportLineUpload: function (btn) {
        
        // var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        // tiaojian+="partName="+ formValues.partName;
        // tiaojian+="&brand="+formValues.brand;
        tiaojian+="&status=all&isExprot=1";
        
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'station/lineStationExport.do'+tiaojian);
        
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