Ext.define('App.stockOutStockApply.stockOutStockApplyView', {
    extend: 'Ext.tab.Panel',
    xtype: 'stockOutStockApplyView',
    id: 'stockOutStockApplyView',
    fullscreen: true,
    requires:[
        "App.stockOutStockApply.stockOutStockApplyGrid",
        "App.stockOutStockApply.stockOutStockApplyGridBorrow",
        "App.stockOutStockApply.stockOutStockApplyBindWindow",
        "App.stockOutStockApply.stockOutStockApplyBorrowLookWindow",
        "App.stockOutStockApply.stockOutStockApplyBorrowSelectAddWindow",
        "App.stockOutStockApply.stockOutStockApplyBorrowSelectWindow",
        "App.stockOutStockApply.stockOutStockApplyBorrowWindow",
        "App.stockOutStockApply.stockOutStockApplyController",
        "App.stockOutStockApply.stockOutStockApplyDetailWindow",
        "App.stockOutStockApply.stockOutStockApplyGrid",
        "App.stockOutStockApply.stockOutStockApplyGridBorrow",
        "App.stockOutStockApply.stockOutStockApplyLookWindow",
        "App.stockOutStockApply.stockOutStockApplySelectAddWindow",
        "App.stockOutStockApply.stockOutStockApplySelectWindow",
        "App.stockOutStockApply.stockOutStockApplyStore"
    ],
    items: [
        {
            title:"领用出库",
            outOrderType:"useOut",
            xclass:"App.stockOutStockApply.stockOutStockApplyGrid"
        },
        {
            title:"调拨出库",
            outOrderType:"transferOut",
            xclass:"App.stockOutStockApply.stockOutStockApplyGrid"
        },
        {
            title:"返还出库",
            outOrderType:"returnOut",
            xclass:"App.stockOutStockApply.stockOutStockApplyGrid"
        },
        {
            title:"借用出库",
            outOrderType:"borrowOut",
            xclass:"App.stockOutStockApply.stockOutStockApplyGridBorrow"
        }
    ]
});