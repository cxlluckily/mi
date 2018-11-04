Ext.define('App.orgLineStation.orgStationAddWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
    height: 600,
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
    xtype: 'orgStationAddWindow',
    controller: 'orgStationController',
    viewAction: '{viewAction}',
    items: [{
        xtype: 'orgStationAddGrid'
    }],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});

Ext.define('App.orgLineStation.orgStationAddGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'orgStationAddGrid',
    store: {
        xclass: 'App.orgLineStation.orgStationAddStore'
    },
    height: 500,
    // columnLines: true,
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },

    // cancelBtnText: '取消',
    columns: [
        // {
        //     text: '序号',
        //     xtype: 'rownumberer',
        //     width: '10px',
        //     align: 'center',
        //     style: 'border-left: 1px solid #cfcfcf'
        // },
        {
            text: 'ID',
            dataIndex: 'id',
            align: 'left',
            flex: 1,
            hidden: true
        }, {
            text: '规则ID',
            dataIndex: 'reg_id',
            align: 'left',
            flex: 1,
            hidden: true
        }, {
            text: 'userKey',
            dataIndex: 'userKey',
            align: 'left',
            flex: 1,
            hidden: true
        }, {
            text: 'lineId',
            dataIndex: 'lineId',
            align: 'left',
            flex: 1,
            hidden: true
        }, {
            text: 'status',
            dataIndex: 'status',
            align: 'left',
            flex: 1,
            hidden: true
        }, {
            text: '<font color="red">*</font>站点编码',
            dataIndex: 'stationCode',
            align: 'left',
            flex: 1,
            stopSelection: false,
            editor: {
                allowBlank: false,
                maxLength: 8,
            }
            // editor: {
            //     completeOnEnter: false,
            //     field: {
            //         xtype: 'textfield',
            //         name: 'stationCode',
            //         allowBlank: false
            //     }
            // }
        }, {
            text: '<font color="red">*</font>站点名称',
            dataIndex: 'stationName',
            align: 'left',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'stationName',
                    allowBlank: false,
                    maxLength: 16,
                }
            }
        }, {
            text: '负责人',
            dataIndex: 'headPerson',
            align: 'left',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'headPerson',
                    maxLength: 16,
                }
            }
        }, {
            text: '电话',
            dataIndex: 'phone',
            align: 'left',
            flex: 1,
            editor: {
                completeOnEnter: false,
                field: {
                    xtype: 'textfield',
                    name: 'phone'
                }
            }
        },
        {
            text: '是否起始站',
            dataIndex: 'wasBegin',
            xtype: 'checkcolumn',
            align: 'center',
            editorAlign: 'center',
            flex: 1
        },
        {
            text: '是否终点站',
            dataIndex: 'wasEnd',
            xtype: 'checkcolumn',
            align: 'center',
            editorAlign: 'center',
            flex: 1
        },
        {
            text: '操作',
            xtype: 'actioncolumn',
            align: "center",
            width: 100,
            items: [
                {
                    tooltip: '删除',
                    icon: "app/resources/images/delete.png",
                    flag: 'delete',
                    handler: 'detailActionFn'
                }
            ]
        }
    ],
    tbar: [{
        xtype: 'button',
        text: '新增站点',
        action: 'addDetail'
    }],
    frame: true,
    selModel: {
        type: 'cellmodel'
    },
    fullscreen: true
});

Ext.define('App.orgLineStation.orgStationAddModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'lineId',
            type: 'string'
        },
        {
            name: 'stationCode',
            type: 'string'
        },
        {
            name: 'stationName',
            type: 'string'
        },
        {
            name: 'headPerson',
            type: 'string'
        },
        {
            name: 'phone',
            type: 'string'
        },
        {
            name: 'remark',
            type: 'string'
        },
        {
            name: 'pinyin',
            type: 'string'
        },
        {
            name: 'wasBegin',
            type: 'bool'
        },
        {
            name: 'wasEnd',
            type: 'bool'
        },
        {
            name: 'status',
            type: 'string'
        }
    ]
});

Ext.define('App.orgLineStation.orgStationAddStore', {
    extend: 'Ext.data.Store',
    model: "App.orgLineStation.orgStationAddModel"
});
