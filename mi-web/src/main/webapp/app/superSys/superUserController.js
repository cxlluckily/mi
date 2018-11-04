Ext.define('App.superSys.superUserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.superUserController',
    refViews:{
        window: 'App.superSys.superUserWindow',
        view: 'superUserView',
        addUrl: 'superAdmin/insertSuperAdmin.do',
        updateUrl: 'superAdmin/updateSuperAdmin.do',
        initUrl: 'superAdmin/initPassword.do'
    },
    control: {
        'superUserView button[action="search"]': {
            click: 'clickSearch'
        },
        'superUserView button[action="add"]': {
            click: 'clickAdd'
        },
        'superUserView button[action="update"]': {
            click: 'clickUpdate'
        },
        'superUserView button[action="init"]': {
            click: 'clickInit'
        },
        'superUserView button[action="resets"]': {
            click: 'clickResets'
        },
        'superUserWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(){
        Ext.getCmp('superUserViewFormId').form.reset();
        this.clickSearch();
    },
    clickInit:function(btn){
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
                    adminIds:[]
                };
                Ext.each(records,function (item) {
                    data.adminIds.push(item.data.adminId);
                });
                var url = me.refViews.initUrl;
                var config = {
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var result = Ext.decode(response.responseText);
                        if (result.result=='success') {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '初始化密码成功！<br>新密码为“123456”',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.INFO
                            });
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
        })
    },
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('superUserViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
        extWindow.down('form').getForm().setValues({operationSubjectName:formValues.operationSubjectName,operationSubjectId:formValues.operationSubjectId,status:'start_using',sex:'none',fullPhotoUrl:'app/resources/images/userPhoto.png',userKey:window.sessionStorage['userKey']});
        extWindow.show();
    },
    clickUpdate: function (btn) {
        var records = this.getView().getSelection();
        if(records.length!==1){
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行修改！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        }else{
            var record = records[0];
            var formValues = Ext.getCmp('superUserViewFormId').getForm().getValues();
            Ext.apply(record.data,{userKey:window.sessionStorage['userKey'],operationSubjectName:formValues.operationSubjectName});
            if(record.data.photoUrl==''||record.data.photoUrl==null){
                Ext.apply(record.data,{fullPhotoUrl:'app/resources/images/userPhoto.png'});
            }
            var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls:btn.iconCls,recordData: record.data});
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
                    userIds:[]
                };
                Ext.each(records,function (item) {
                    data.userIds.push(item.data.userId);
                });
                var url = me.thisDeleteUrl;
                var config = {
                    btn:btn,
                    url: ctx + url,
                    data: data,
                    success: function (response, opts) {
                        var responseText = Ext.decode(response.responseText);
                        if (responseText.result=='success') {
                            me.clickSearch();
                            Ext.Msg.show({
                                title: '提示',
                                message: '删除成功！',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.INFO
                            });
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
        })
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) {
            return;
        }
        var data = form.getValues();
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
    clickUpload: function(btn) {
        var me = this;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        if(!form_up.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        Ext.Msg.wait("","提示信息",{text:"程序正在上传，请稍候...."});
        var url = ctx +  this.refViews.uploadUrl;
        var viewAction = btn.up('window').viewAction;
        form.submit({
            url: url,
            params:{
                userKey: window.sessionStorage['userKey']
            },
            success: function(a, action) {
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
            failure : function(response, opts) {
                Ext.Msg.hide();
                var responseData = opts.result;
                if(responseData.result=='success'){
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
                }else{
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
                // console.log('server-side failure with status code '
                //     + opts);
                // Ext.Msg.hide();
                // Ext.Msg.show({
                //     title:'错误',
                //     message: '上传失败！服务器无响应。',
                //     buttons: Ext.Msg.OK,
                //     icon: Ext.Msg.ERROR
                // });
            }
        });
    },
    clicksaveWarehouseUpload: function(btn) {
        
        var userIds = [Ext.ComponentQuery.query('textfield[name="userId"]')[0].value];
        
    	var warehousegrid = Ext.ComponentQuery.query('authWarehouseList')[0];
    	var roles = btn.ownerCt.ownerCt.down('checkboxgroup[name="roleIds"]').getValue()
    	var roleIds = [];
    	for(var k in roles){
    		roleIds.push(k);
    	}
    	var workSections = btn.ownerCt.ownerCt.down('checkboxgroup[name="workSectionIds"]').getValue()
    	var workSectionIds = [];
    	for(var k in workSections){
    		workSectionIds.push(k);
    	}
    	var warehouseIds = [];
        var datas = warehousegrid.getStore().getData().items;
    	Ext.each(datas, function(item){
            var rec = item.data;
    		var isChecked = rec.isChecked;
    		if(isChecked){
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
    	        btn:btn,
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
                    } else {
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