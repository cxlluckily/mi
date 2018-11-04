Ext.define('App.sysUser.sysUserView', {
    extend: 'Ext.grid.Panel',
    requires: [
        'App.common.selectStore',
        'App.common.selectWindow',
        'App.common.importWindow',
        'App.common.messageWindow',
        'App.sysUser.sysUserStore',
        'App.sysUser.sysUserController',
        'App.sysUser.sysUserWindow',
        'App.sysUser.sysUserPersonalWindow'
    ],
    store: {
        xclass: 'App.sysUser.sysUserStore'
    },
    controller: {
        xclass: 'App.sysUser.sysUserController'
    },
    xtype: 'sysUserView',
    id: 'sysUserView',
    fullscreen: true,
    tbar: [
        {
            id: 'sysUserViewFormId',
            xtype: 'form',
            fullscreen: true,
            width: 1000,
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
                    // title: '作业跟踪',
                    items: [
                        // {
                        //     columnWidth: .25,
                        //     layout: 'form',
                        //     border: false,
                        //     items: [
                        //         {
                        //             xtype: 'textfield',
                        //             fieldLabel: '用户名',
                        //             name: 'userName'
                        //         }
                        //     ]
                        // },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '手机号',
                                    name: 'phone'
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
                                    fieldLabel: '姓名',
                                    name: 'realName'
                                }
                            ]
                        },
                        {
                            columnWidth: .33,
                            layout: 'form',
                            border: false,
                            labelWidth: 200,
                            hidden: true,
                            items: [
                                {
                                    xtype: 'textfield',
                                    fieldLabel: '公司名',
                                    // id:'operationSubjectName',
                                    name: 'operationSubjectName',
                                    hidden: true
                                }
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
                                    anchor: '90%',
                                    emptyText: '全部',
                                    queryMode: 'local',
                                    name: 'internalNumber',
                                    editable: false,
                                    displayField: 'text',
                                    valueField: 'code',
                                    listeners: {
                                        expand: function (cb, e, opt) {
                                            var config = {
                                                title: this.fieldLabel,
                                                treeParams: {
                                                    url: 'data/loadTree.do',
                                                    rootProperty: 'data',
                                                    component: cb,
                                                    params: {
                                                        type: "organization"
                                                    }
                                                }
                                            };
                                            var w = Ext.create('App.common.selectWindow', config);
                                            w.show();
                                        }
                                        // change: function (combo) {
                                        //     var ct = combo.up('grid').getController();
                                        //     ct.clickSearch();
                                        // }
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
                                    xtype: 'combo',
                                    fieldLabel: '状态',
                                    anchor: '90%',
                                    emptyText: '全部',
                                    value: 'all',
                                    store: {
                                        data: [
                                            {
                                                id: "all",
                                                name: "全部"
                                            },
                                            {
                                                id: "start_using",
                                                name: "可用"
                                            },
                                            {
                                                id: "stop_using",
                                                name: "不可用"
                                            }
                                        ]
                                    },
                                    queryMode: 'local',
                                    name: 'status',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    // multiSelect:true,
                                    listeners: {
                                        beforerender: function (combo) {//渲染
                                            var firstValue = combo.getStore().getData().items[0].id;
                                            combo.setValue(firstValue);
                                        },
                                        // change: function (combo) {
                                        //     var ct = combo.up('grid').getController();
                                        //     ct.clickSearch();
                                        // }
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
                                    xtype: 'combo',
                                    fieldLabel: '角色',
                                    anchor: '90%',
                                    emptyText: '全部',
                                    store: {
                                        xclass: 'App.common.roleListStore'
                                    },
                                    queryMode: 'local',
                                    name: 'roleId',
                                    editable: false,
                                    displayField: 'name',
                                    valueField: 'id',
                                    listeners: {
                                        render: function (combo) {
                                            combo.getStore().on("load", function (s, r, o) {
                                                var firstValue = combo.getStore().getData().items[0].id;
                                                combo.setValue(firstValue);
                                                var p = Ext.ComponentQuery.query('sysUserView')[0];
                                                p.getController().clickSearch();
                                            });
                                        },
                                        change: function (tf, nval, oval, opts) {
                                            if (!nval && nval != 0) {
                                                tf.up('form').getForm().setValues({roleId: 0});
                                            }
                                            // else {
                                            //     var ct = tf.up('grid').getController();
                                            //     ct.clickSearch();
                                            // }
                                            // tf.up('sysUserView').getController().clickSearch();
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: 1,
                            layout: 'column',
                            border: false,
                            items: [
                                {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '查询',
                                    action: 'search',
                                    iconCls: 'fa-search'
                                }, {
                                    margin: 10,
                                    xtype: 'button',
                                    text: '重置',
                                    action: 'resets',
                                    iconCls: 'fa-refresh'
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '新增',
                                    action: 'add',
                                    iconCls: 'fa-plus-circle'
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '修改',
                                    action: 'update',
                                    iconCls: 'fa-edit',
                                    disabled: true
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '删除',
                                    action: 'delete',
                                    iconCls: 'fa-minus-circle',
                                    hidden:true,
                                    disabled: true
                                }, {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '初始化密码',
                                    action: 'init',
                                    iconCls: 'fa-refresh',
                                    disabled: true
                                }
                                ,
                                {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '个人角色授权',
                                    action: 'personalAuthorization',
                                    iconCls: 'fa-user',
                                    disabled: true
                                }
                                , {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导入',
                                    action: 'importDownload',
                                    iconCls: 'fa-download'
                                }
                                // , {
                                //     margin: 10,
                                //     // columnWidth: .25,
                                //     xtype: 'button',
                                //     text: '导入',
                                //     action: 'importUserinfoDownload',
                                //     iconCls: 'fa-download'
                                // }
                                , {
                                    margin: 10,
                                    // columnWidth: .25,
                                    xtype: 'button',
                                    text: '导出',
                                    action: 'exportUpload',
                                    iconCls: 'fa-upload'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        plugins: 'ux-progressbarpager'
    },

    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        }, {
            text: 'userId',
            dataIndex: 'userId',
            width: 100,
            align: 'left',
            hidden: true
        },{
            text: 'wasDefaultManager',
            dataIndex: 'wasDefaultManager',
            width: 100,
            align: 'left',
            hidden: true
        },
        {
            text: '手机号',
            dataIndex: 'phone',
            width: 150,
            align: 'left'
        },
        {
            text: '用户名',
            dataIndex: 'userName',
            width: 150,
            align: 'left',
            hidden:true
        },
        {
            text: '姓名',
            dataIndex: 'realName',
            width: 100,
            align: 'left'
        }
    
        , {
            text: '工号',
            dataIndex: 'workNumber',
            width: 100,
            align: 'left'
        },
        
        {
            text: '性别',
            dataIndex: 'sex',
            width: 100,
            align: 'left',
            renderer: function (value, record) {
                if (value == 'male') {
                    return "男"
                } else if (value == 'female') {
                    return "女"
                }
            }
        },
        {
            text: '邮箱',
            dataIndex: 'email',
            width: 150,
            align: 'left'
        }
        ,
        // {
        //     text: '用户头像',
        //     dataIndex: 'photoUrl',
        //     width: 100,
        //     align: 'left',
        //     hidden: true
        // },
        /*{
            text: '用户头像',
            dataIndex: 'fullPhotoUrl',
            width: 100,
            align: 'center',
            // hidden: true,
            renderer: function (value, metaData, record) {
                var img = '<img src=' + value + ' width="80" />';
                // var img = '<img src="app/resources/images/userPhoto.png" width="60" />';
                return img;
                // return Ext.String.format('<div id="{0}"></div>', id);
            }
        },*/
         {
            text: '密码',
            dataIndex: 'password',
            width: 150,
            align: 'left',
            hidden: true
        }, {
            text: '角色',
            dataIndex: 'roleName',
            width: 150,
            align: 'left'
        }, {
            text: '状态',
            dataIndex: 'status',
            width: 150,
            align: 'left',
            sortable: true,
            renderer: function (value, record) {
                if (value == "start_using") {
                    return "可用"
                }
                if (value == "stop_using") {
                    return "不可用"
                }
            }
        }, {
            text: '部门',
            dataIndex: 'orgName',
            width: 150,
            align: 'left'
        }, {
            text: '职位',
            dataIndex: 'position',
            width: 150,
            align: 'left'
        }, {
            text: '备注',
            dataIndex: 'remark',
            width: 150,
            align: 'left',
            hidden: true
        }, {
            text: '完整Url',
            dataIndex: 'fullPhotoUrl',
            width: 150,
            align: 'left',
            hidden: true
        }
    ],
    selModel: {
        selType: 'checkboxmodel',
        // mode: 'SINGLE',
        allowDeselect: true,
        listeners: {
            selectionchange: {
                fn: function (sel, selected, eOpts) {
                    if (selected.length < 1) {
                        sel.view.ownerCt.down('button[action="delete"]').disable();
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="init"]').disable();
                        sel.view.ownerCt.down('button[action="personalAuthorization"]').disable();
                    } else if (selected.length == 1) {
                        sel.view.ownerCt.down('button[action="delete"]').enable();
                        sel.view.ownerCt.down('button[action="update"]').enable();
                        sel.view.ownerCt.down('button[action="init"]').enable();
                        sel.view.ownerCt.down('button[action="personalAuthorization"]').enable();
                    } else {
                        sel.view.ownerCt.down('button[action="delete"]').enable();
                        sel.view.ownerCt.down('button[action="update"]').disable();
                        sel.view.ownerCt.down('button[action="init"]').enable();
                        sel.view.ownerCt.down('button[action="personalAuthorization"]').disable();
                    }
                }
            }
        }
    }
});