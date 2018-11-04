Ext.define('App.partSparePart.partSparePartView', {
    extend: 'Ext.container.Container',
    requires: [
        "App.partSparePart.partView",
        "App.partSparePart.partStore",
        "App.partSparePart.partController",
        "App.partSparePart.partWindow",
        "App.partSparePart.partTypeView",
        "App.partSparePart.partTypeController"
    ],
    xtype: 'partSparePartView',
    id: 'partSparePartView',
    fullscreen: true,
    layout:'border',
    items:[
        {
            region: 'west',
            xtype:"partTypeView",
            width:220
        },
        {
            region: 'center',
            xtype:"partView"
        }
    ]
});