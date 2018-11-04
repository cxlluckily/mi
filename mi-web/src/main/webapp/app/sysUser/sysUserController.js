Ext.define('App.sysUser.sysUserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.sysUserController',
    refViews: {
        window: 'App.sysUser.sysUserWindow',
        personalWindow: 'App.sysUser.sysUserPersonalWindow',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        view: 'sysUserView',
        addUrl: 'user/inserOrUpdateOne.do',
        updateUrl: 'user/inserOrUpdateOne.do',
        deleteUrl: 'user/batchUpdatePersonStopUse.do',
        initUrl: 'user/updatePassword.do',
        uploadUrl: 'user/inserOrUpdateOne.do',
        importUrl: 'user/importUserList.do',
        importUserinfoUrl: 'user/importUserinfoList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'sysUserView button[action="search"]': {
            click: 'clickSearch'
        },
        'sysUserView button[action="add"]': {
            click: 'clickAdd'
        },
        'sysUserView button[action="update"]': {
            click: 'clickUpdate'
        },
        'sysUserView button[action="delete"]': {
            click: 'clickDelete'
        },
        'sysUserView button[action="init"]': {
            click: 'clickInit'
        },
        'sysUserView button[action="personalAuthorization"]': {
            click: 'clickPersonal'
        },
        'sysUserView button[action="resets"]': {
            click: 'clickResets'
        },
        'sysUserWindow button[action="save"]': {
            click: 'clickUpload'
        },
        'sysUserPersonalWindow button[action="saveWarehouse"]': {
            click: 'clicksaveWarehouseUpload'
        },
        'sysUserView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
        'sysUserView button[action="importUserinfoDownload"]': {
            click: 'clickImportUserinfoDownload'
        },
        'sysUserView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        },
        // 'sysUserImportWindow button[action="importSave"]': {
        //     click: 'clickImportSave'
        // },
        // 'sysUserImportWindow button[action="downUploadfile"]': {
        //     click: 'clickdownUploadfile'
        // }
    },
    clickPersonal: function (btn) {
        var records = this.getView().getSelection();
        var record = records[0];
        var formValues = Ext.getCmp('sysUserViewFormId').getForm().getValues();
        Ext.apply(record.data, {
            userKey: window.sessionStorage['userKey'],
            operationSubjectName: formValues.operationSubjectName
        });
        var extWindow = Ext.create(this.refViews.personalWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls,
            recordData: record.data
        });
        extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    clickResets: function (btn) {
        var form = Ext.getCmp('sysUserViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickInit: function (btn) {
        var records = this.getView().getSelection();
        var me = this;
        if (records.length < 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行初始化密码！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }

        Ext.Msg.confirm('提示信息', '确定要初始化密码吗？', function (c) {
            if (c == 'yes') {
                var data = {
                    userKey: window.sessionStorage['userKey'],
                    userIds: []
                };
                Ext.each(records, function (item) {
                    data.userIds.push(item.data.userId);
                });
                var url = me.refViews.initUrl;
                var config = {
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var responseText = Ext.decode(response.responseText);
                        if (responseText.result == 'success') {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '初始化密码成功！<br>新密码为“123456”',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.INFO
                            });
                        }
                        else {
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
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('sysUserViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.refViews.window, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').getForm().setValues({
            operationSubjectName: formValues.operationSubjectName,
            operationSubjectId: window.sessionStorage['operationSubjectId'],
            status: 'start_using',
            userId: 0,
            sex: 'male',
            fullPhotoUrl: 'app/resources/images/userPhoto.png',
            userKey: window.sessionStorage['userKey']
        });
        extWindow.show();
    },
    clickUpdate: function (btn) {
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        }
        else {
            var record = records[0];
            var formValues = Ext.getCmp('sysUserViewFormId').getForm().getValues();
            Ext.apply(record.data, {
                userKey: window.sessionStorage['userKey'],
                operationSubjectName: formValues.operationSubjectName
            });
            if (record.data.photoUrl == '' || record.data.photoUrl == null) {
                Ext.apply(record.data, {fullPhotoUrl: 'app/resources/images/userPhoto.png'});
            }
            var extWindow = Ext.create(this.refViews.window, {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                recordData: record.data
            });
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickDelete: function (btn) {
        var records = this.getView().getSelection();
        var me = this;
        if (records.length < 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行删除！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }

        Ext.Msg.confirm('提示信息', '确定要删除吗？', function (c) {
            if (c == 'yes') {
                var data = {
                    userKey: window.sessionStorage['userKey'],
                    userIds: []
                };
                Ext.each(records, function (item) {
                    data.userIds.push(item.data.userId);
                });
                var url = me.refViews.deleteUrl;
                var config = {
                    btn: btn,
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var responseText = Ext.decode(response.responseText);
                        if (responseText.result == 'success') {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '删除成功！',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.INFO
                            });
                        }
                        else {
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
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
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
                }
                else {
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
        var values = form.getValues();
        if (!form_up.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        if(values.wasDefaultManager !== '1'){
            form.setValues({userName:values.phone});
        }
        Ext.Msg.wait("", "提示信息", {text: "程序正在上传，请稍候...."});
        var url = ctx + this.refViews.uploadUrl;
        var viewAction = btn.up('window').viewAction;
        form.submit({
            btn: btn,
            url: url,
            params: {
                orgId: !values.orgId ? '0' : values.orgId,
                userKey: window.sessionStorage['userKey']
            },
            success: function (a, action) {
                // var result = action.result.success;
                // var msg = action.result.msg;
                // if(result){
                //     form_up.up('window').close();
                //     var jarManagementList = Ext.ComponentQuery.query('jarManagementList');
                //     jarManagementList[0].getStore().reload();
                //     Ext.Msg.hide();
                //     Ext.Msg.show({
                //         title:'提示',
                //         message: '上传成功！',
                //         buttons: Ext.Msg.OK,
                //         icon: Ext.Msg.INFO
                //     });
                // }else{
                //     Ext.Msg.show({
                //         title:'错误',
                //         message: '上传失败！',
                //         buttons: Ext.Msg.OK,
                //         icon: Ext.Msg.ERROR
                //     });
                // }

            },
            failure: function (response, opts) {
                Ext.Msg.hide();
                var responseData = opts.result;
                if (responseData.result == 'success') {
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
                    btn.up('window').close();
                    me.clickSearch();
                }
                else {
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
    clicksaveWarehouseUpload: function (btn) {

        var userIds = [Ext.ComponentQuery.query('textfield[name="userId"]')[0].value];
        var warehousegrid = Ext.ComponentQuery.query('userAuthWarehouseList')[0];
        var roles = btn.ownerCt.ownerCt.down('checkboxgroup[name="roleIds"]').getValue()
        var roleIds = [];
        var me = this;
        for (var k in roles) {
            roleIds.push(k);
        }
        if (roleIds.length < 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请您选择角色！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var workSections = btn.ownerCt.ownerCt.down('checkboxgroup[name="workSectionIds"]').getValue();
        var workSectionIds = [];
        for (var k in workSections) {
            workSectionIds.push(k);
        }
        var warehouseIds = [];
        var datas = warehousegrid.getStore().getData().items;
        Ext.each(datas, function (item) {
            var rec = item.data;
            var isChecked = rec.isChecked;
            if (isChecked) {
                warehouseIds.push(rec.warehouseId);
            }
        });
        var params = {
            userKey: window.sessionStorage['userKey'],
            operationSubjectId: window.sessionStorage['operationSubjectId'],
            userIds: userIds,
            roleIds: roleIds,
            warehouseIds: warehouseIds,
            workSectionIds: workSectionIds
        };
        var config = {
            btn: btn,
            url: ctx + 'userPermission/insertBatchAuthorizationInfo.do',
            data: params,
            success: function (response, opts) {
                var responseData = Ext.decode(response.responseText);
                if (responseData.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '授权成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickSearch();
                }
                else {
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
                    filename: "YongHuMuBan.xls",
                    callbackComponent: me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    clickImportUserinfoDownload: function (btn) {
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData: {
                    url: ctx + this.refViews.importUserinfoUrl,
                    downUrl: ctx + this.refViews.downUrl,
                    filename: "YongHuDaoRuMuBan.xls",
                    callbackComponent: me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    //导出页面
    clickExportUpload: function (btn) {
        var formValues = btn.up('form').getForm().getValues();
        var tiaojian = "?";
        //tiaojian += "userName=" + btn.ownerCt.ownerCt.down('[name="userName"]').getValue();
        tiaojian += "&realName=" + btn.ownerCt.ownerCt.down('[name="realName"]').getValue();
        var internalNumber = btn.ownerCt.ownerCt.down('[name="internalNumber"]').getValue();
        if (internalNumber == null || internalNumber == "null") {
            internalNumber = "";
        }
        tiaojian += "&internalNumber=" + internalNumber;

        tiaojian += "&status=" + btn.ownerCt.ownerCt.down('[name="status"]').getValue();
        tiaojian += "&roleId=" + btn.ownerCt.ownerCt.down('[name="roleId"]').getValue();
        tiaojian += "&phone=" + btn.ownerCt.ownerCt.down('[name="phone"]').getValue();

        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'user/userExport.do' + tiaojian);

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