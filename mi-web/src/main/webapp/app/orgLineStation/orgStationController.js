Ext.define('App.orgLineStation.orgStationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.orgStationController',
    refViews:{
        addWindow: 'App.orgLineStation.orgStationAddWindow',
        updateWindow: 'App.orgLineStation.orgStationUpdateWindow',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        view: 'orgStationView',
        addUrl: 'station/batchInsert.do',
        updateUrl: 'station/updateOne.do',
        importUrl: 'station/importStationList.do',
        importLineUrl: 'station/importLineStationList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'orgStationView button[action="search"]': {
            click: 'clickSearch'
        },
        'orgStationView button[action="add"]': {
            click: 'clickAdd'
        },
        'orgStationView button[action="update"]': {
            click: 'clickUpdate'
        },
        'orgStationView button[action="resets"]': {
            click: 'clickResets'
        },
        // 'orgStationView button[action="delete"]': {
        //     click: 'clickDelete'
        // },
        'orgStationUpdateWindow button[action="save"]': {
            click: 'clickSave'
        },
        'orgStationAddWindow button[action="save"]': {
            click: 'clickSave'
        },
        'orgStationAddWindow button[action="addDetail"]': {
            click: 'addColReg'
        },
        'orgStationView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
        'orgStationView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        },
        'orgStationView button[action="importLineDownload"]': {
            click: 'clickImportLineDownload'
        },

    },
    addColReg: function (btn) {
        var grid = btn.up('window').down('grid');
        var store = grid.getStore();
        var count = store.getCount();
        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        store.insert(count, {
            lineId: orgLineSelect.data.lineId,
            userKey: window.sessionStorage['userKey'],
            status: 'start_using'
        });
    },
    detailActionFn : function(grid, rowIndex, colIndex, item, e, record){
        grid.getStore().remove(record);
    },
    clickSearch: function (btn) {
        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        var pagingTool = Ext.getCmp("orgStationViewPaging");
        if (!orgLineSelect) {
            var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
            pageList.getStore().setData([]);
            return;
        }
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pagingTool.moveFirst();
        pageList.getStore().load();
    },
    clickAdd: function (btn) {
        // var me = this;
        // var p = Ext.ComponentQuery.query('orgLineView')[0].getController();
        // console.log(me.clickResets)
        // console.log(p.clickResets)
        // return

        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        if (!orgLineSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一条线路！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var extWindow = Ext.create(this.refViews.addWindow, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
        extWindow.show();
    },
    clickUpdate: function (btn) {
        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        if (!orgLineSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一条线路！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '只能对一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        } else {
            var record = records[0];
            Ext.apply(record.data, {lineId:orgLineSelect.data.lineId});
            var extWindow = Ext.create(this.refViews.updateWindow, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickResets:function(btn){
        var form = Ext.getCmp('orgStationViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickSave: function (btn) {
        var viewAction = btn.up('window').viewAction;
        var me = this;
        if(viewAction == 'add'){
            var grid = btn.up('window').down('grid');
            var addData = [];
            var hasEmpty = false;
            var emptyKey = '';
            var emptyIndex = '';
            if(grid.store.data.items.length == 0){
                Ext.Msg.show({
                    title: '提示',
                    message: '尚未新增站点，不能保存！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            }
            Ext.each(grid.store.data.items, function (item, index) {
                addData.push(item.data);
                if (item.data.stationCode == null || item.data.stationCode == undefined || item.data.stationCode == '') {
                    if (!hasEmpty) {
                        hasEmpty = true;
                        emptyKey = '站点编码';
                        emptyIndex = index + 1;
                    }
                }
                if (item.data.stationName == null || item.data.stationName == undefined || item.data.stationName == '') {
                    if (!hasEmpty) {
                        hasEmpty = true;
                        emptyKey = '站点名称';
                        emptyIndex = index + 1;
                    }
                }
            });
            if (hasEmpty) {
                Ext.Msg.show({
                    title: '提示',
                    message: '请填写第' + emptyIndex + '行' + emptyKey + '！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            };
            var url = me.refViews.addUrl;
            var data = {
                stationList: addData
            };
            var config = {
                btn:btn,
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                        grid.up('window').close();
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
        }else if(viewAction == 'update'){
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
            var url = me.refViews.updateUrl;
            var config = {
                btn:btn,
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        Ext.Msg.show({
                            title: '提示',
                            message: '修改成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                        });
                        btn.up('window').close();
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


    },
    clickImportDownload: function (btn) {
        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        if (!orgLineSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一条线路！',
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
                    url:ctx + this.refViews.importUrl+"?lineId="+orgLineSelect.data.lineId,
                    downUrl:ctx+this.refViews.downUrl,
                    filename:"CheZhanMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    //导出页面
    clickExportUpload: function (btn) {
        var orgLineSelect = Ext.ComponentQuery.query('orgLineView')[0].getSelectionModel().getSelection()[0];
        if (!orgLineSelect) {
            Ext.Msg.show({
                title: '警告',
                message: '请先选择一条线路！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
    
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian="?";
        tiaojian+="stationCode="+ formValues.stationCode;
        tiaojian+="&stationName="+formValues.stationName;
        tiaojian+="&headPerson="+formValues.headPerson;
        tiaojian+="&status="+formValues.status;
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'station/stationExport.do'+tiaojian);
    
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
        operationInput.setAttribute("name", "lineId");
        operationInput.setAttribute("value",orgLineSelect.data.lineId);
        form.append(operationInput);
        
        
        document.getElementsByTagName("body")[0].append(form);
        setTimeout(function () {
            form.submit();
            setTimeout(function () {
                form.remove();
            }, 1000);
        }, 100);
    },
    clickImportLineDownload: function (btn) {
        var me = this;
        var p = Ext.ComponentQuery.query('orgLineView')[0].getController();
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
                    callbackComponent:p
                }
            }
        );
        extWindow.show();
    },
});