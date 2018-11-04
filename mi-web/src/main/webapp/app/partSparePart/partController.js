Ext.define('App.partSparePart.partController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.partController',
    refViews:{
        window: 'App.partSparePart.partWindow',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        view: 'partView',
        addUrl: 'sparePart/insertOne.do',
        updateUrl: 'sparePart/updateOne.do',
        listUrl: 'sparePart/getSparePartInfo.do',
        detailUrl: 'sparePart/getSparePartDetail.do',
        importUrl: 'sparePart/importSparePartList.do',
        importTypeUrl: 'sparePart/importSparePartTypeList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'partView button[action="search"]': {
            click: 'clickSearch'
        },
        'partView button[action="add"]': {
            click: 'clickAddFirst'
        },
        'partView button[action="update"]': {
            click: 'clickUpdate'
        },
        'partView button[action="resets"]': {
            click: 'clickResets'
        },
        'partView [actioncolumn]': {
            click: 'actionFn'
        },
        'partWindow button[action="save"]': {
            click: 'clickUpload'
        },
        'partWindow [actioncolumn]': {
            click: 'deleteImg'
        },
        'partView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
        'partView button[action="importTypeDownload"]': {
            click: 'clickImportTypeDownload'
        },
        'partView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        }
        // 'partWindow button[action="save"]': {
        //     click: 'clickSave'
        // }
    },
    deleteImg: function (grid, rowIndex, colIndex, item, e, record) {
        var partSparePartImageEntities = grid.up('form').getValues().partSparePartImageEntities;
        var deleteIndex = 0;
        Ext.each(partSparePartImageEntities,function (imgItem,index) {
            if(imgItem.sparePartImageId == record.data.sparePartImageId){
                deleteIndex = index;
            }
            if(index == (partSparePartImageEntities.length-1)){
                partSparePartImageEntities.splice(index,1);
            }
        });
        grid.up('form').form.setValues({partSparePartImageEntities:partSparePartImageEntities});
        grid.getStore().remove(record);
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var flag = item.flag;
        if (flag == 'add') {
            // var formData = Ext.getCmp("partViewFormId").getForm().getValues();
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({
                sparePartPid: record.data.sparePartPid,
                sparePartTypeId: record.data.sparePartTypeId,
                status: 'start_using'
            });
            w.show();
        } else if (flag == 'addChild') {
            var w = Ext.create(this.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
            w.down('form').getForm().setValues({
                sparePartPid: record.data.sparePartId,
                sparePartTypeId: record.data.sparePartTypeId,
                status: 'start_using'
            });
            w.show();
        } else if (flag == 'update') {
            var me = this;
            var url = me.refViews.detailUrl;
            var data = {
                userKey: window.sessionStorage['userKey'],
                sparePartId: record.data.sparePartId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var w = Ext.create(me.refViews.window, {title: item.tooltip, viewAction: flag, iconCls: item.iconCls});
                        var formData = result.data;
                        w.down('form').getForm().setValues(formData);
                        w.show();
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
    loadList: function (p) {
        var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
        var config = {
            url: ctx + this.refViews.listUrl,
            data: {
                sparePartTypeId: partTypeSelect.data.sparePartTypeId,
                status: 'all'
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
    clickResets:function(btn){
        // btn.up('form').form.reset();
        // var p = Ext.ComponentQuery.query(this.refViews.view)[0];
        // p.getStore().load();
    
        var form = Ext.getCmp('partViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickAddFirst: function (btn) {
        var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
        if (!partTypeSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一个备件类型！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var w = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        w.down('form').getForm().setValues({
            sparePartPid: -1,
            sparePartTypeId: partTypeSelect.data.sparePartTypeId,
            status: 'start_using'
        });
        w.show();
    },

    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
        // p.getController().loadList(p);
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
        if (viewAction == 'add' || viewAction == 'addChild' || viewAction == 'addFirst') {
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
    },
    clickUpload: function (btn) {
        var me = this;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        if (!form_up.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '警告',
                message: '请填写必填项并确认输入格式正确！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        Ext.Msg.wait("", "提示信息", {text: "程序正在上传，请稍候...."});
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add' || viewAction == 'addChild' || viewAction == 'addFirst') {
            url = ctx + this.refViews.addUrl;
            var imageUrls = "noHave";
            form_up.form.setValues({imageUrls:imageUrls});
        } else if (viewAction == 'update') {
            url = ctx + this.refViews.updateUrl;
            var formData = form_up.getValues();
            var imageUrls = "";
            var imgs = [];
            Ext.each(formData.partSparePartImageEntities,function(item){
                imgs.push(item.imageUrl);
            });
            if(imgs.length>0){
                imageUrls = imgs.join(',');
            }else{
                imageUrls = "noHave";
            }
            form_up.form.setValues({imageUrls:imageUrls});
        }
        form.submit({
            url: url,
            params: {
                userKey: window.sessionStorage['userKey']
            },
            success: function (a, action) {
                console.log(a)
                console.log(action)
            },
            failure: function (response, opts) {
                var responseData = opts.result;
                Ext.Msg.hide();
                if (responseData.result == 'success') {
                    if (viewAction == 'add' || viewAction == 'addChild' || viewAction == 'addFirst') {
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
                    btn.up('window').close();
                    me.clickSearch();
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
             
            }
        });
    },
    clickUpdate:function (btn) {
        var me = this;
        var records = me.getView().getSelection();
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            sparePartId: record.data.sparePartId
        };
        var config = {
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var w = Ext.create(me.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
                    var formData = result.data;
                    Ext.each(formData.partSparePartImageEntities,function (item,index) {
                        item.picUrl = item.fullImageUrl;
                    });
                    var imagesPanel = w.down('commonImagesLook');
                    imagesPanel.down('dataview').getStore().setData(formData.partSparePartImageEntities);
                    
                    w.down('form').getForm().setValues(formData);
                    w.show();
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
    ,
    clickImportDownload: function (btn) {
        var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
        if (!partTypeSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一个备件类型！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData:{
                    url:ctx+this.refViews.importUrl+"?sparePartTypeId="+partTypeSelect.data.sparePartTypeId,
                    downUrl:ctx+this.refViews.downUrl,
                    filename:"BeiJianMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    clickImportTypeDownload: function (btn) {
        var me = this;
        var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
        if (!partTypeSelect) {
            me = null;
        }
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
    clickExportUpload: function (btn) {
        var partTypeSelect = Ext.ComponentQuery.query('partTypeView')[0].getSelectionModel().getSelection()[0];
        if (!partTypeSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一个备件类型！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        tiaojian+="partName="+ formValues.partName;
        tiaojian+="&brand="+formValues.brand;
        tiaojian+="&status="+formValues.status;
        
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'sparePart/sparePartExport.do'+tiaojian);
        
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
        
        var operationInput = document.createElement("input");
        operationInput.setAttribute("id", Math.random());
        operationInput.setAttribute("type", "text");
        operationInput.setAttribute("name", "sparePartTypeId");
        operationInput.setAttribute("value",partTypeSelect.data.sparePartTypeId);
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
