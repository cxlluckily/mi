Ext.define('App.breakdown.breakdownController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.breakdownController',
    refViews:{
        window: 'App.breakdown.breakdownWindow',
        view: 'breakdownView',
        addUrl: 'partBreakdownInfo/insertOne.do',
        updateUrl: 'partBreakdownInfo/updateOne.do',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        importUrl: 'partBreakdownInfo/importPartBreakdownList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'breakdownView button[action="search"]': {
            click: 'clickSearch'
        },
        'breakdownView button[action="add"]': {
            click: 'clickAdd'
        },
        'breakdownView button[action="update"]': {
            click: 'clickUpdate'
        },
        'breakdownView button[action="resets"]': {
            click: 'clickResets'
        },
        'breakdownWindow button[action="save"]': {
            click: 'clickSave'
        }
        ,
        'breakdownView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(){
        Ext.getCmp('breakdownViewFormId').form.reset();
       this.clickSearch();
    },
    clickAdd: function (btn) {
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action,iconCls:btn.iconCls});
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
            var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls:btn.iconCls,recordData: record.data});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        if (!form.getForm().isValid()) {
            return;
        }
        var data = form.getForm().getValues();
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
                    filename: "GuZhangJianCheng.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    }
});