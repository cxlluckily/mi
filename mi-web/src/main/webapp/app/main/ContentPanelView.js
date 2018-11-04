Ext.define('App.main.ContentPanelView', {
    extend: 'Ext.panel.Panel',
    xtype: 'contentPanel',
    id: 'content-panel',
    layout: 'center',
    dockedItems: [{
        xtype: 'navigation-toolbar'
    }],
    header: {
        hidden: true
    },
    items: [{
        xtype: 'contentTabs'
    }],
    initComponent: function () {
        Ext.create('App.main.ThumbnailsStore', {
            storeId: 'Thumbnails'
        });
        this.callParent();
    }
});
Ext.define('App.main.Breadcrumb', {
    extend: 'Ext.toolbar.Toolbar',
    xtype: 'navigation-toolbar',
    reference: 'navigation-toolbar',
    hidden: true,
    items: [{
        xtype: 'tool',
        type: 'down',
        tooltip: '切换到完整模式',
        listeners: {
            click: 'showTreeNav'
        }
    }, {
        xtype: 'breadcrumb',
        id: 'navigation-breadcrumb',
        reference: 'breadcrumb',
        bind: {
            selection: '{selectedView}'
        },
        flex: 1,
        showIcons: true,
        store: 'navigation'
    }]
});

Ext.define('App.main.ThumbnailsStore', {
    extend: 'Ext.data.Store',
    // even though this is not a tree store, it uses a TreeModel because it contains
    // records from the navigation tree.
    model: 'Ext.data.TreeModel',
    proxy: 'memory'
});


Ext.define('App.main.Thumbnails', {
    extend: 'Ext.view.View',
    id: 'thumbnailsView',
    xtype: 'thumbnails',
    cls: 'thumbnails',
    reference: 'contentView',
    store: 'Thumbnails',
    itemSelector: '.thumbnail-item',
    initComponent: function () {
        var profileCookie = Ext.util.Cookies.get("profile");
        var backgrounds = {
            crisp: 'border-circle',
            'crisp-touch': 'circle',
            neptune: 'border-square',
            'neptune-touch': 'square',
            classic: 'rounded-square',
            gray: 'rounded-square',
            triton: 'square'
        };

        this.tpl =
            '<tpl for=".">' +
            '<div class="thumbnail-item">' +
            '<div class="thumbnail-icon-wrap icon-' + backgrounds[profileCookie] + '">' +
            '<div class="thumbnail-icon {iconCls}"></div>' +
            '</div>' +
            '<div class="thumbnail-text">{text}</div>' +
            '</div>' +
            '</tpl>';

        this.callParent();
    }
});
Ext.define('App.main.ContentTabs', {
    extend: 'Ext.tab.Panel',
    xtype: 'contentTabs',
    id: 'contentTabsView',
    reference: 'contentTabsView',
    width: '100%',
    height: '100%',
    defaults: {
        bodyCls: 'App-panel-body',
        closable: true,
        layout: 'center',
        scrollable: true,
    },
    plugins: ['tabreorderer', 'tabclosemenu_zh'],
    items: [{
        itemId: 'thumbnailsTab',
        reference: 'thumbnailsTab',
        items: [{
            selector: 'thumbnails',
            xtype: 'thumbnails',
            reference: 'thumbnails'
        }],
        closable: false,
        listeners: {
            render: function (tab) {
                var navStore = Ext.StoreMgr.get('navigation');
                var node = navStore.getNodeById('all');
                var thumbnailsStore = Ext.StoreMgr.get('Thumbnails');
                thumbnailsStore.removeAll();
                thumbnailsStore.add(node.childNodes);
                tab.setTitle(node.get('text'));
                tab.setIconCls(node.get('iconCls'));
                tab.treeNode = node;
            }
        }
    }]
});
// var funPanel = Ext.getCmp(record.get('id')); //这里的id千万不要跟其他组件相同
// if (!funPanel) {
//     funPanel = mainView.add({
//         id: record.get('id'),
//         title: record.get('text'),
//         closable: true,
//         xtype: funInfo.funViewXtype
//     });
// } else {
//     mainView.add(funPanel);
// }
// mainView.setActiveTab(funPanel);

Ext.define('App.main.TabCloseMenu', {
    extend: 'Ext.ux.TabCloseMenu',
    alias: 'plugin.tabclosemenu_zh',
    closeTabText: '关闭当前',
    closeOthersTabsText: '关闭其他',
    closeAllTabsText: '关闭所有',
    me:this,
    // enableRerfesh: true,
    // extraItemsTail: [
    //     '-',
    //     {
    //         text: '刷新',
    //         hideOnClick: true,
    //         handler: function (item) {
    //             // var tabName = item.el.id;//获取选项卡的id
    //             //
    //             // item.el.setLoading("加载中,如果加载的内容空白可以右击标题刷新...");//设置遮罩
    //             // return
    //             // window.frames[tabName].location.reload();//frames[tabName]这是iframe的name,设置成与tab id同名
    //             // setTimeout(function () {//这张持续时间5秒
    //             //     item.setLoading(false);
    //             // }, 500);
    //         }
    //     }
    // ],
    // listeners: {
    //     // aftermenu: function () {
    //     //     currentItem = null;
    //     // },
    //     // beforemenu: function (menu, item) {
    //     //     currentItem = item;
    //     // }
    // }
});