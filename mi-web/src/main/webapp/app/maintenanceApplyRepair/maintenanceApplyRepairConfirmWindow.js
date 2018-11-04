Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairConfirmForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    buttonAlign: 'center',
    padding: 10,
    width: '100%',
    xtype: 'maintenanceApplyRepairConfirmForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 100,
        padding: '10px 10px 0px 0px',
        width: '100%',
        margin: '0'
    },
    items: [
        {
            xtype: 'container',
            columnWidth: 1,
            items:[
                {
                    xtype: 'textfield',
                    name: 'form',
                    fieldLabel: 'form',
                    hidden:true
                },
                {
                    xtype: 'textfield',
                    name: 'params',
                    fieldLabel: 'params',
                    hidden:true
                },
                {
                    xtype: 'radiogroup',
                    fieldLabel: '未修复原因',
                    columns: 2,
                    vertical: true,
                    defaults: {
                        padding: '0 0 0 0'
                    },
                    items: [
                        {boxLabel: '备件不足', name: 'wasFinished', inputValue: '备件不足',checked:true},
                        {boxLabel: '软件升级', name: 'wasFinished', inputValue: '软件升级'},
                        {boxLabel: '故障复杂处理中', name: 'wasFinished', inputValue: '故障复杂处理中'},
                        {boxLabel: '其他', name: 'wasFinished', inputValue: '其他'}
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairConfirmWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 600,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    // autoScroll:true,
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    buttonAlign: "center",
    xtype: 'maintenanceApplyRepairConfirmWindow',
    controller: 'maintenanceApplyRepairController',
    items: [
        {
            xtype: 'maintenanceApplyRepairConfirmForm'
        }
    ],
    buttons: [
        {
            text: '确定',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});