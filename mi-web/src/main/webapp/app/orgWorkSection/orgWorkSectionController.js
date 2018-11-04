Ext.define('App.orgWorkSection.orgWorkSectionController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.orgWorkSectionController',
    refViews:{
        window: 'App.orgWorkSection.orgWorkSectionWindow',
        view: 'orgWorkSectionView',
        addUrl: 'workSection/insertOne.do',
        updateUrl: 'workSection/updateOne.do',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        importUrl: 'workSection/importWorkSectionLineStationList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'orgWorkSectionView button[action="search"]':{
            click: 'clickSearch'
        },
        'orgWorkSectionView button[action="add"]': {
            click: 'clickAdd'
        },
        'orgWorkSectionView button[action="update"]': {
            click: 'clickUpdate'
        },
        'orgWorkSectionView button[action="resets"]': {
            click:'clickResets'
        },
        'orgWorkSectionWindow button[action="save"]': {
            click: 'clickSave'
        }
        ,
        'orgWorkSectionView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
        'orgWorkSectionView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickAdd: function (btn) {
        var formValues = Ext.getCmp('orgWorkSectionViewFormId').getForm().getValues();
        var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.down('form').getForm().setValues({
            workSectionId: 0,
            status: 'start_using'
        });
        extWindow.show();
    },
    clickUpdate: function (btn) {
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
            var extWindow = Ext.create(this.refViews.window, {title: btn.text, viewAction: btn.action});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickResets:function(btn){
        Ext.getCmp('orgWorkSectionViewFormId').form.reset();
        this.clickSearch();
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        var tabs = btn.up('window').down('tabpanel').items.items;
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
        var stationEntityList = [];
        Ext.each(tabs, function (item) {
            var checkboxGroup = item.down('checkboxgroup');
            Ext.each(checkboxGroup.items.items, function (stationItem) {
                if (stationItem.value) {
                    var a = {
                        stationId: stationItem.name
                    }
                    stationEntityList.push(a);
                }
            });
        });
        Ext.apply(data, {userKey: window.sessionStorage['userKey']});
        var params = {
            orgWorkSectionEntity:data,
            stationEntityList:stationEntityList
        };
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
            data: params,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    if (viewAction == 'add') {
                        Ext.Msg.show({
                            title: '提示',
                            message: '新增成功',
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
                    filename: "GongQuCheZhanMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    }
});