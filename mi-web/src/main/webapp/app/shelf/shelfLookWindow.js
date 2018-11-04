Ext.define('App.shelf.shelfLookForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'shelfLookForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 90,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [{
        xtype: 'container',
        layout: 'column',
        items: [
            {
                xtype: 'displayfield',
                fieldLabel: '所属仓库',
                name: 'warehouseName'
            },
            {
                xtype: 'displayfield',
                fieldLabel: '房间号',
                name: 'houseNo'
            },
            {
                xtype: 'displayfield',
                fieldLabel: '类型',
                name: 'containerType',
                renderer:function (value) {
                    if(value == 'Container'){
                        return '货柜'
                    }else{
                        return '货架'
                    }
                }
            },
            {
                xtype: 'displayfield',
                fieldLabel: '货架/货柜编号',
                name: 'shelfNumber'
            },
            {
                xtype: 'displayfield',
                fieldLabel: '状态',
                name: 'status',
                renderer:function (value) {
                    if(value == 'start_using'){
                        return '可用'
                    }else{
                        return '不可用'
                    }
                }
            },
            {
                xtype: 'displayfield',
                fieldLabel: '备注',
                name: 'remark'
            }
        ]
    }],
    buttons: [{
        text: '关闭',
        handler: function (a) {
            this.up('window').close();
        }
    }]
});


Ext.define('App.shelf.shelfLookWindow', {
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
    buttonAlign: "center",
    xtype: 'shelfLookWindow',
    controller: 'shelfController',
    items: [{
        xtype: 'shelfLookForm'
    }]
});