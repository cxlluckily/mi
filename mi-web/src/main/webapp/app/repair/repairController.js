Ext.define('App.repair.repairController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.repairController',
    refViews:{
        window: 'App.repair.repairWindow',
        view: 'listView',
        addUrl: 'partBreakdownRepairInfo/insertOne.do',
        updateUrl: 'partBreakdownRepairInfo/updateOne.do',
    },
    control: {
        'listView button[action="search"]': {
            click: 'clickSearch'
        },
        'listView button[action="add"]': {
            click: 'clickAdd'
        },
        'listView button[action="update"]': {
            click: 'clickUpdate'
        },
        'listView button[action="reset"]': {
            click: 'clickReset'
        },
        'repairWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickReset:function(){
        Ext.getCmp('repairViewFormId').form.reset();
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
        var data = form.getValues();
        var url = '';
        var viewAction = btn.up('window').viewAction;
        if (viewAction == 'add') {
            url = me.refViews.addUrl;
        }
        if (viewAction == 'update') {
            url = me.refViews.updateUrl;
            data.breakdownInfoId = data.breakdownInfoIdCopy;
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
});