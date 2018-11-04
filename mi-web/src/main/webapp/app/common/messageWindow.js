Ext.define('App.common.messageGrid', {
    extend: 'Ext.grid.Panel',
    store: {},
    maxHeight:500,
    xtype: 'messageGrid',
    fullscreen: true,
    // 数据列表
    autoScroll:true,
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        },
        {
            text: '错误位置',
            dataIndex: 'num',
            width: 100,
            align: 'left',
            renderer: function (value) {
                return '第' + value + '行'
            }
        },
        {
            text: '错误信息',
            dataIndex: 'message',
            flex:1,
            align: 'left'
        }
    ]
});

Ext.define('App.common.messageWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    myData: '{myData}',
    width: 1000,
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
    xtype: 'messageWindow',
    // controller: 'sysUserController',
    controller: {
        xclass:'App.common.importController'
    },
    requires:[
        'App.common.importController'
    ],
    items: [
        {
            xtype: 'messageGrid'
        }
    ],
    buttons: [
        {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});