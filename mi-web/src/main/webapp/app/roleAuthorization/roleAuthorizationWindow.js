Ext.define('App.roleAuthorization.authUserList', {
    extend: 'Ext.grid.Panel',
    requires: ["App.roleAuthorization.roleAuthorizationUserStore"],
    // buttonAlign: 'left',
    xtype: 'authUserList',
    store: {
        xclass: 'App.roleAuthorization.roleAuthorizationUserStore'
    },
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },
    selModel: {
        selType: 'checkboxmodel',
        mode: 'MULTI',
        allowDeselect: true
    },
    fullScreen: true,
    // 数据列表
    height:500,
    tbar: [
        {
            id: 'roleAuthForm',
            xtype: 'form',
            fullscreen: true,
            width: 800,
            items: [
                {
                    xtype: 'fieldset',
                    layout: 'column',
                    border: false,
                    style: {
                        backgroundColor: '#fff',
                        marginTop: '-1px',
                        marginBottom: '0'
                    },
                    items: [
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                // {
                                //     xtype: 'textfield',
                                //     name: 'userName',
                                //     fieldLabel: '用户名',
                                //     labelAlign: 'right',
                                //     blankText: '请输入用户名!',
                                //     maxLength: 16
                                // }
                                {
                                    xtype: 'textfield',
                                    name: 'phone',
                                    fieldLabel: '手机号',
                                    labelAlign: 'right',
                                    blankText: '请输入用户名!',
                                    maxLength: 16
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'realName',
                                    fieldLabel: '姓名',
                                    labelAlign: 'right',
                                    blankText: '请输入姓名!',
                                    maxLength: 16
                                },
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'workNumber',
                                    fieldLabel: '工号',
                                    labelAlign: 'right',
                                    blankText: '请输入工号!',
                                    maxLength: 8
                                },
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'position',
                                    fieldLabel: '职位',
                                    labelAlign: 'right',
                                    blankText: '请输入职位!',
                                    maxLength: 8
                                },
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'combo',
                                    fieldLabel: '部门',
                                    emptyText: '--双击选择--',
                                    queryMode: 'local',
                                    name: 'internalNumber',
                                    editable: false,
                                    displayField: 'text',
                                    valueField: 'code',
                                    listeners: {
                                        expand : function(cb, e, opt){
                                            var config = {
                                                title:this.fieldLabel,
                                                treeParams:{
                                                    url:'data/loadTree.do',
                                                    rootProperty:'data',
                                                    component:cb,
                                                    params:{
                                                        type:"organization"
                                                    }
                                                }
                                            };
                                            var w = Ext.create('App.common.selectWindow',config);
                                            w.show();
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'button',
                                    text: '查询',
                                    margin: '0 10 0 0',
                                    handler: 'clickSearch',
                                    iconCls: 'fa-search'
                                },
                                {
                                    xtype: 'button',
                                    text: '重置',
                                    margin: '0 10 0 0',
                                    handler: 'clickResets',
                                    iconCls: 'fa-refresh'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    columnLines: true,
    fullscreen: true,
    columns: [{
        text: '序号',
        xtype: 'rownumberer',
        width: '10px',
        align: 'center'
    },
        {
            text: '姓名',
            dataIndex: 'realName',
            align: 'left',
            flex: 1
        },
        {
            text: '手机号',
            dataIndex: 'phone',
            align: 'left',
            flex: 1
        },
        {
            text: '用户名',
            dataIndex: 'userName',
            align: 'left',
            flex: 1,
            hidden:true
        },
        {
            text: '工号',
            dataIndex: 'workNumber',
            align: 'left',
            flex: 1,
            itemId: 'colType'
        },
        {
            text: '职位',
            dataIndex: 'position',
            align: 'left',
            flex: 1
        },{
            text: '部门',
            dataIndex: 'orgName',
            align: 'left',
            flex: 1
        }
    ]
});


Ext.define('App.roleAuthorization.roleAuthorizationWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    iconCls: "fa-plus-circle",
    width: 850,
    height: 600,
    plain: false,
    resizable: false,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'roleAuthorizationWindow',
    controller: 'roleAuthorizationController',
    items: [
        // {
        //     xtype: 'fieldset',
        //     items: [{xtype: 'roleAuthorizationForm'}]
        // },
        {
            xtype: 'fieldset',
            items: [{xtype: 'authUserList'}]
        }
    ],
    buttons: [{
        text: '确定',
        action: 'confirm'
    }, {
        text: '取消',
        handler: function (a) {
            this.up('window').close();
        }
    }]

});