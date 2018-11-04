Ext.define('App.server.serverController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.serverController',
    refViews: {
        view:'serverView',
        windowView:'serverWindow',
        window: 'App.server.serverWindow',
        addOrUpdateWindow: 'App.server.serverAddOrUpdateWindow',

    },
    control: {
        'serverView button[action="search"]': {
            click: 'clickSearch'
        },
        'serverView button[action="resets"]': {
            click: 'clickResets'
        },
        'serverView button[action="add"]': {
            click: 'clickAdd'
        },
        'serverView button[action="update"]': {
            click: 'clickUpdate'
        },
        'serverWindow button[action="search"]': {
            click: 'clickEquipmentSearch'
        },
        'serverWindow button[action="resets"]': {
            click: 'clickEquipmentResets'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'update') {
            var extWindow = Ext.create(me.refViews.window, {
                title: item.tooltip,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            // let grid = extWindow.down('grid');
            // grid.down('form').getForm().setValues({mqttCommandBatchId:record.data.mqttCommandBatchId});
            extWindow.show();
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function () {
        var form = Ext.getCmp('serverViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickAdd:function(btn){
        var extWindow = Ext.create(this.refViews.addOrUpdateWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.show();
    },
    clickUpdate:function(btn){
        var extWindow = Ext.create(this.refViews.addOrUpdateWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.show();
    },
    clickEquipmentSearch:function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.windowView)[0].down('grid');
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickEquipmentResets:function () {
        var form = Ext.getCmp('serverEquipmentGridFormId');
        form.form.reset();
        this.clickEquipmentSearch();
    },
});