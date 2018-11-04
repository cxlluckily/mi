Ext.define('App.mySparePart.mySparePartController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.mySparePartController',
    control: {
        'mySparePartView button[action="search"]': {
            click: 'clickSearch'
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.thisView)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(){
        Ext.getCmp('mySparePartViewFormId').form.reset();
    },
    clickAdd: function (btn) {
        var extWindow = Ext.create(this.thisWindow, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
        extWindow.down('form').getForm().setValues({status:'start_using'});
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
            var extWindow = Ext.create(this.thisWindow, {title: btn.text, viewAction: btn.action, iconCls:btn.iconCls,recordData: record.data});
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
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add') {
            url = me.thisAddUrl;
        }
        if (viewAction == 'update') {
            url = me.thisUpdateUrl;
        }
        var config = {
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
    }
});