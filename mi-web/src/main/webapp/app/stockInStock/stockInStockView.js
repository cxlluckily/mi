Ext.define('App.stockInStock.stockInStockView', {
    extend: 'Ext.tab.Panel',
    xtype: 'stockInStockView',
    id: 'stockInStockView',
    fullscreen: true,
    requires: [
        'App.common.selectStore',
        'App.stockInStock.stockInStockController',
        'App.stockInStock.stockInStockStore',
        'App.stockInStock.stockInStockGrid',
        'App.stockInStock.stockInStockGridBorrow',
        'App.stockInStock.stockInStockGridNew',
        'App.stockInStock.stockInStockLookWindow',
        'App.stockInStock.stockInStockNewLookWindow',
        'App.stockInStock.stockInStockNewSelectWindow',
        'App.stockInStock.stockInStockNewWindow',
        'App.stockInStock.stockInStockOtherWindow',
        'App.stockInStock.stockInStockWindow'
    ],
    items: [
        {
            title:"新品入库",
            inStockType:"newIn",
            xclass:"App.stockInStock.stockInStockGridNew"
        },
        {
            title:"归还入库",
            inStockType:"useIn",
            xclass:"App.stockInStock.stockInStockGrid"
        },
        {
            title:"调拨入库",
            inStockType:"transferIn",
            xclass:"App.stockInStock.stockInStockGrid"
        },
        {
            title:"返还入库",
            inStockType:"returnIn",
            xclass:"App.stockInStock.stockInStockGrid"
        },
        {
            title:"借用入库",
            inStockType:"borrowIn",
            xclass:"App.stockInStock.stockInStockGridBorrow"
        }
    ]
});