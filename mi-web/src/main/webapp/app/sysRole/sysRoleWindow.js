Ext.define('App.sysRole.sysRoleForm', {
    extend: 'Ext.form.Panel',
    xtype: 'sysRoleForm',
    fullScreen: true,
    layout: 'form',
    buttonAlign: 'center',
    requires: ["App.sysRole.roleFunctionTreePanel"],
    controller: {
        xclass: 'App.sysRole.sysRoleController'
    },
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 60,
        width: '100%'
    },
    items: [{
        xtype: 'fieldset',
        defaultType: 'textfield',
        fullscreen: true,
        anchor: '96%',
        items: [{
            xtype: 'fieldset',
            items: [{
                xtype: 'textfield',
                name: 'roleId',
                hidden: true
            }, {
                xtype: 'textfield',
                name: 'roleName',
                anchor: '96%',
                fieldLabel: '<font color="red">*</font>角色名称',
                labelAlign: 'right',
                labelWidth: 80,
                allowBlank: false,
                blankText: '请输入角色名称!',
                maxLength: 16,
                listeners: {
                    beforerender: function (aa) {
                        // var extWindow = this.up('window');
                        // if (extWindow.viewAction == undefined){
                        //     this.editable=false;
                        //     this.fieldStyle='background:#ddd';
                        // }
                    }
                }
            },
            ]
        }, {
            xtype: 'fieldset',
            layout: {
                type: 'hbox'
            },
            defaults: {
                labelWidth: 60,
                flex: 1
            },
            items: [
                {
                    xtype: 'radiogroup',
                    fieldLabel: '状态',
                    vertical: false,
                    items: [{
                        boxLabel: '可用',
                        name: 'status',
                        inputValue: 'start_using'
                    }, {
                        boxLabel: '不可用',
                        name: 'status',
                        inputValue: 'stop_using'
                    }]
                }]

        }]
    }, {
        xtype: 'fieldset',
        layout: {
            type: 'hbox'
        },
        defaults: {
            labelWidth: 60,
            flex: 1,
            style: {
                padding: '2px'
            }
        },
        items: [{
            xtype: 'roleFunctionTree',
            treeType: 'manage',
            id: 'tree',
            title: '角色权限分配',
            flag: 'roleFunctionTree',
            itemId: 'manageTree',
            cascadeCheck: false,
            listeners: {
                render: function () {
                    var me = this;
                    var params = me.up('window').params;
                    var action = params.viewAction;
                    var roleId = params.roleId;
                    me.up('window').setTitle(params.title);
                    var config = {
                        url: ctx + 'roleInfo/getRoleInfo.do',
                        data: {roleId: roleId, userKey: window.sessionStorage['userKey']},
                        success: function (response) {
                            var obj = Ext.decode(response.responseText);
                            if (obj.result == "success") {
                                var root = {
                                    expanded: true,
                                    marginTop: '-2px'
                                };
                                root.children = obj.data.treeInfoList;

                                me.getStore().setRoot(root);
                                me.expandAll();
                            } else if (obj.result == "failure") {
                                Ext.Msg.show({
                                    title: '提示',
                                    message: obj.message,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                            }
                        },
                        failure: function (response) {
                            console.log('server-side failure with status code ' + response.status);
                        }
                    };
                    App.common.Ajax.request(config);
                }
            }
        },
            {
                xtype: 'roleFunctionTree',
                treeType:
                    'app',
                title:
                    'app角色权限分配',
                flag:
                    'appRoleFunctionTree',
                itemId:
                    'appTree',
                listeners:
                    {
                        render: function () {
                            var me = this;
                            var params = me.up('window').params;
                            me.up('window').setTitle(params.title);
                            var action = params.viewAction;
                            var roleId = params.roleId;
                            var config = {
                                url: ctx + 'roleInfo/getRoleInfo.do',
                                data: {roleId: roleId, userKey: window.sessionStorage['userKey']},
                                success: function (response) {
                                    var obj = Ext.decode(response.responseText);
                                    if (obj.result == "success") {
                                        var root = {
                                            expanded: true,
                                            marginTop: '-2px'
                                        };
                                        root.children = obj.data.appTreeInfoList;
                                        me.getStore().setRoot(root);
                                        me.expandAll();
                                    } else if (obj.result == "failure") {
                                        Ext.Msg.show({
                                            title: '提示',
                                            message: obj.message,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.WARNING
                                        });
                                    }
                                },
                                failure: function (response) {
                                    console.log('server-side failure with status code ' + response.status);
                                }
                            };
                            App.common.Ajax.request(config);
                        }
                    }
            }
        ]
    }]
});

// 测试
Ext.define('App.sysRole.sysRoleWindow', {
    extend: 'Ext.window.Window',
    width: 850,
    // height: 600,
    //plain : false,
    iconCls: '{params.iconCls}',
    //resizable : true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    //autoRender : true,
    buttonAlign: "center",
    xtype: 'sysRoleWindow',
    params: '{params}',
    controller: {
        xclass: 'App.sysRole.sysRoleController'
    },
    items: [{
        xtype: 'sysRoleForm'
    }],
    buttons: [{
        text: '保存',
        action: 'save',
    }, {
        text: '取消',
        handler: function (a) {
            this.up('window').close();
        }
    }]
});
