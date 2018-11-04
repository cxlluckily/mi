Ext.define('App.common.selectForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'selectForm',
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

Ext.define('App.common.selectTreeModel', {
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

Ext.define('App.common.selectTreeStore', {
    extend: 'Ext.data.TreeStore',
    model: 'App.common.selectTreeModel'
});

Ext.define('App.common.selectTreePanel', {
    extend: 'Ext.tree.Panel',
    hideHeaders: true,
    height: 500,
    width: '100%',
    xtype: 'deptTree',
    rootVisible: false,
    autoScroll: true,
    store: {
        xclass: 'App.common.selectTreeStore'
    }
});

Ext.define('App.common.selectGrid', {
    extend: 'Ext.grid.Panel',
    store: {
        extend: 'App.common.commonStore',
        pageSize: 0
    },
    height: 400,
    xtype: 'selectGrid',
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

Ext.define('App.common.selectWindow', {
    extend: 'Ext.window.Window',
    xtype: 'deptWindow',
    title: '{title}',
    fullscreen: true,
    layout: 'form',
    buttonAlign: 'center',
    width: 800,
    height: 600,
    // xtype: 'deptWin',
    modal: true,
    treeParams: '{treeParams}',
    items: [
        {
            xtype: 'selectForm',
            hidden: true
        },
        {
            xtype: 'deptTree'
        },
        {
            xtype: 'selectGrid',
            hidden: true
        }
    ],
    recursion: function (data, key, children) {
        var me = this;
        Ext.each(data, function (item) {
            item.text = item[key];
            if (item[children] && item[children].length > 0) {
                item.children = item[children];
                me.recursion(item.children, key, children);
            }
        });
        return data;
    },
    getSelectData: function () {
        var me = this;
        var data = me.treeParams.params;
        if (me.treeParams.searchContent) {
            var formValues = me.down('form').getForm().getValues();
            var searchContent = formValues[me.treeParams.searchContent].replace(/^\s*|\s*$/g, "");
            Ext.apply(data, {searchContent: searchContent});
        }
        var config = {
            url: ctx + me.treeParams.url,
            data: data,
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    if (!me.treeParams.searchContent || searchContent.length == 0) {
                        me.down('treepanel').setHidden(false);
                        me.down('selectGrid').setHidden(true);
                        var root = {
                            expanded: true
                        };
                        var rootArray = me.treeParams.rootProperty.split('.');
                        var rootData = obj;
                        Ext.each(rootArray, function (item) {
                            rootData = rootData[item];
                        });
                        if (!me.treeParams.children) {
                            me.treeParams.children = 'children';
                        }
                        root.children = me.recursion(rootData, me.treeParams.component.displayField, me.treeParams.children);
                        var p = me.down('treepanel');
                        p.getStore().setRoot(root);
                        p.expandAll();
                    } else {
                        me.down('treepanel').setHidden(true);
                        me.down('selectGrid').setHidden(false);
                        var p = me.down('selectGrid');
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
        beforerender: function () {
            var me = this;
            if (me.treeParams.searchContent) {
                me.down('form').hidden = false;
                me.down('form').down('textfield').name = me.treeParams.searchContent;
                // var firstCol = {
                //     text: me.treeParams.component.valueField,
                //     dataIndex: me.treeParams.component.valueField,
                //     flex: 1,
                //     align: 'left'
                //     // hidden: true
                // };
                // me.down('selectGrid').columns.push(firstCol);
                // Ext.each(me.treeParams.displayColumns,function (item) {
                //     // var col = {
                //     //     text: item.text,
                //     //     dataIndex: item.dataIndex,
                //     //     flex: 1,
                //     //     align: 'left'
                //     // };
                //     // me.down('selectGrid').columns.push(col);
                //     // me.down('selectGrid').reconfigure();
                // });
            }
        },
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
                var selection = a.up('window').down('treepanel').hidden ? a.up('window').down('selectGrid').selection : a.up('window').down('treepanel').selection;
                if (!selection) {
                    Ext.Msg.show({
                        title: '提示',
                        message: '请选择一条记录！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    return;
                }
                var node = selection.raw;
                var combo = me.treeParams.component;
                if (me.treeParams.selectLeaf && !node.leaf && !a.up('window').down('treepanel').hidden) {
                    Ext.Msg.show({
                        title: '提示',
                        message: '请选择最下级节点！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    return;
                }
                combo.setValue(node[me.treeParams.component.valueField]);
                combo.setRawValue(node[me.treeParams.component.displayField]);
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