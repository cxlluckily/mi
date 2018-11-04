Ext.define('App.personalInfo.personalInfoController', {
    extend: 'Ext.app.ViewController',
    refViews:{
        updateUrl:'user/updateOnePerson.do',
        editpass:'personalInformation/modifyPassword.do',
        window:'App.personalInfo.personalInfoWindow',
        view:'personalInfoView'
    },
    control : {
        'personalInfoView button[action="editpass"]' : {
            click : 'clickEditPass'
        },
        'personalInfoView button[action="save"]' : {
            click : 'clickSave'
        },
        'personalInfoWindow button[action="savePass"]' : {
            click : 'clickSavePass'
        }
    },
    getFormValues :function(component){
        var config = {
            url: ctx + 'user/getOneUserInfo.do',
            data: {},
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == 'success') {
                    component.getForm().setValues(result.data);
                    if (result.data.fullPhotoUrl) {
                        var tx = component.down('textfield[name="fullPhotoUrl"]');
                        var b = Ext.getCmp('personalInfoImage');
                        if (b){
                            // tx.removeAll(true);
                            // tx.doLayout();
                            b.getEl().dom.src = result.data.fullPhotoUrl;
                        }else{
                            var a = Ext.create({
                                xtype: 'box',
                                id: 'personalInfoImage',
                                name: "fullPhotoUrl",
                                fieldLabel: "预览图片",
                                autoHeight: true,
                                width: 300,
                                height: 200,
                                autoEl: {
                                    tag: 'img',
                                    src: result.data.fullPhotoUrl,
                                    height: '150px',
                                    style: {
                                        margin: '0px 0px'
                                    }
                                }
                            });
                            tx.ownerCt.add([a]);
                        }

                    }
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
    clickEditPass:function(btn){
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.show();
    },
    clickSavePass:function(btn){
        var form = btn.up('form');
        if(!form.getForm().isValid()) {
            return;
        };
        var data = form.getValues();
        if(data.newPassword != data.password){
            Ext.Msg.show({
                title:'提示',
                message: '两次新密码不一致',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        };
        if(data.newPassword == data.password && data.newPassword == data.oldPassword){
            Ext.Msg.show({
                title:'提示',
                message: '新旧密码不能相同',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        };
        var url = this.refViews.editpass;
        var config = {
            btn:btn,
            url : url,
            data : data,
            success : function(response, opts) {
                var responseData = Ext.decode(response.responseText);
                if (responseData.result == "success") {
                    Ext.Msg.show({
                        title : '提示',
                        message : responseData.message,
                        buttons : Ext.Msg.OK,
                        icon : Ext.Msg.INFO
                    });
                    logout();
                } else {
                    Ext.Msg.show({
                        title : '警告',
                        message:responseData.message,
                        buttons : Ext.Msg.OK,
                        icon : Ext.Msg.WARNING
                    });
                    return;
                }
            },
            failure : function(response, opts) {
                Ext.Msg.show({
                    title : '错误',
                    message : response,
                    buttons : Ext.Msg.OK,
                    icon : Ext.Msg.error
                });
            }
        };
        // 这一句是提交
        App.common.Ajax.request(config);
    },
    clickSave:function(btn) {
        var me = this;
        var form = btn.up('form');
        var data = form.getValues();
        var url = this.refViews.updateUrl;
        // if(data.wasDefaultManager !== '1'){
        //     data.userName = data.phone;
        // }
        data.userKey = window.sessionStorage['userKey'];
        form.submit({
            btn:btn,
            url: url,
            params: data,
            failure: function (response, opts) {
                var responseData = opts.result;
                if (responseData.result == 'success') {
                    Ext.Msg.show({
                            title: '提示',
                            message: '修改保存成功！',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO
                    });
                    // form.getForm().reset();
                    me.getFormValues(form);
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
                // return;
            }
        });
    }
});