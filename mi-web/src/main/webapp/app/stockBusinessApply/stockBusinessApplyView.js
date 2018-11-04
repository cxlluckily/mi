Ext.define('App.stockBusinessApply.stockBusinessApplyView', {
    extend: 'Ext.tab.Panel',
    xtype: 'stockBusinessApplyView',
    id: 'stockBusinessApplyView',
    fullscreen: true,
    requires: [
        'App.stockBusinessApply.stockBusinessApplyGrid',
    ],
    items: [
        // {
        //     title:"领用申请",
        //     applyType:"use",
        //     xclass:"App.stockBusinessApply.stockBusinessApplyGrid"
        // },
        {
            title:"调拨申请",
            applyType:"transfer",
            xclass:"App.stockBusinessApply.stockBusinessApplyGrid"
        },
        {
            title:"返还申请",
            applyType:"return",
            xclass:"App.stockBusinessApply.stockBusinessApplyGrid"
        }
    ]
});