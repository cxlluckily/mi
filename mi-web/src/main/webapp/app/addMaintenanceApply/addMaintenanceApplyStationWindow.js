Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'addMaintenanceApplyStationForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        // padding: '10,10,10,10',
        // width: '100%'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .23,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'searchContent',
                            emptyText: '输入检索内容'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .4,
                    items: [
                        {
                            xtype: 'button',
                            text: '查询',
                            action: 'search',
                            iconCls: 'fa-search',
                            handler: function (a) {
                                var extWindow = this.up('window');
                                extWindow.getSelectData();
                            }
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationTreeModel', {
    extend: 'Ext.data.TreeModel',
    fields: [
        {
            name: 'dataNodeId',
            type: 'int'
        },
        {
            name: 'code',
            type: 'string'
        },
        {
            name: 'text',
            type: 'string'
        },
        {
            name: 'leaf',
            type: 'boolean'
        },
        {
            name: 'expanded',
            type: 'boolean'
        },
        {
            name: 'dataParentNodeId',
            type: 'int'
        }
    ]
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationTreeStore', {
    extend: 'Ext.data.TreeStore',
    model: 'App.addMaintenanceApply.addMaintenanceApplyStationTreeModel'
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationTreePanel', {
    extend: 'Ext.tree.Panel',
    hideHeaders: true,
    height: 400,
    width: '100%',
    xtype: 'addMaintenanceApplyStationTreePanel',
    rootVisible: false,
    autoScroll: true,
    store: {
        xclass: 'App.addMaintenanceApply.addMaintenanceApplyStationTreeStore'
    }
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        extend: 'App.common.commonStore',
        pageSize: 0
    },
    height: 400,
    xtype: 'addMaintenanceApplyStationGrid',
    autoScroll: true,
    border: '1px solid #5fa2dd',
    fullscreen: true,
    // 数据列表
    columnLines: true,
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        }
        , {
            text: 'lineId',
            dataIndex: 'lineId',
            flex: 1,
            align: 'left',
            hidden: true
        },{
            text: 'stationId',
            dataIndex: 'stationId',
            flex: 1,
            align: 'left',
            hidden: true
        }, {
            text: '线路',
            dataIndex: 'lineName',
            flex: 1,
            align: 'left'
        }, {
            text: '站点',
            dataIndex: 'stationName',
            flex: 1,
            align: 'left'
        }
    ]
});

Ext.define('App.addMaintenanceApply.addMaintenanceApplyStationWindow', {
    extend: 'Ext.window.Window',
    xtype: 'addMaintenanceApplyStationWindow',
    title: '站点',
    fullscreen: true,
    layout: 'form',
    buttonAlign: 'center',
    width: 800,
    height: 600,
    modal: true,
    items: [
        {
            xtype: 'addMaintenanceApplyStationForm'
        },
        {
            xtype: 'addMaintenanceApplyStationTreePanel'
        },
        {
            xtype: 'addMaintenanceApplyStationGrid',
            hidden: true
        }
    ],
    recursion: function (data, key, children) {
        var me = this;
        Ext.each(data, function (item) {
            item.text = item[key];
            if (item[children] && item[children].length > 0) {
                item.children = item[children];
                item.leaf = false;
                me.recursion(item.children, key, children);
            } else {
                item.leaf = true;
            }
        });
        return data;
    },
    getSelectData: function () {
        var me = this;
        var formValues = me.down('form').getForm().getValues();
        var searchContent = formValues.searchContent.replace(/^\s*|\s*$/g, "");
        var data = {searchContent: searchContent};
        var config = {
            url: ctx + 'repairApply/getLineAndStationInfo.do',
            data: data,
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    if (searchContent.length == 0) {
                        me.down('treepanel').setHidden(false);
                        me.down('addMaintenanceApplyStationGrid').setHidden(true);
                        var root = {
                            expanded: true
                        };
                        var rootData = obj.data;
                        root.children = me.recursion(rootData, 'name', 'stations');
                        var p = me.down('treepanel');
                        p.getStore().setRoot(root);
                        // p.expandAll();//展开
                    } else {
                        me.down('treepanel').setHidden(true);
                        me.down('addMaintenanceApplyStationGrid').setHidden(false);
                        var p = me.down('addMaintenanceApplyStationGrid');
                        p.getStore().setData(obj.data);
                    }
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
    },
    listeners: {
        render: function () {
            var me = this;
            me.getSelectData();
        }
    },
    buttons: [
        {
            text: '确定',
            handler: function (a) {
                var me = this.up('window');
                var selection = a.up('window').down('treepanel').hidden ? a.up('window').down('addMaintenanceApplyStationGrid').selection : a.up('window').down('treepanel').selection;
                if (!selection) {
                    Ext.Msg.show({
                        title: '提示',
                        message: '请选择一条记录！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    return;
                }
                if (!a.up('window').down('treepanel').hidden){
                    var node = selection.raw;
                }else{
                    var node = selection.data;
                }
                var combo = me.component;
                if (!a.up('window').down('treepanel').hidden && !node.leaf) {
                    Ext.Msg.show({
                        title: '提示',
                        message: '请选择最下级节点！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    return;
                }
                if (!a.up('window').down('treepanel').hidden){
                    combo.setValue(node.code);
                    combo.setRawValue(node.name);
                }else{
                    combo.setValue(node.stationId);
                    combo.setRawValue(node.stationName);
                }
                this.up('window').close();
            }
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});