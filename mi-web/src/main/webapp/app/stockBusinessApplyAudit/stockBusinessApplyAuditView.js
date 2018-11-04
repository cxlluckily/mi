Ext.define('App.stockBusinessApplyAudit.stockBusinessApplyAuditView', {
    extend: 'Ext.tab.Panel',
    xtype: 'stockBusinessApplyAuditView',
    id: 'stockBusinessApplyAuditView',
    requires:[
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditStore',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditController',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditGrid',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditLookWindow',
        'App.stockBusinessApplyAudit.stockBusinessApplyAuditWindow'
    ],
    fullscreen: true,
    items: [
        {
            title:"领用申请",
            applyType:"use",
            xclass:"App.stockBusinessApplyAudit.stockBusinessApplyAuditGrid"
        },
        {
            title:"调拨申请",
            applyType:"transfer",
            xclass:"App.stockBusinessApplyAudit.stockBusinessApplyAuditGrid"
        },
        {
            title:"返还申请",
            applyType:"return",
            xclass:"App.stockBusinessApplyAudit.stockBusinessApplyAuditGrid"
        }
    ]
});