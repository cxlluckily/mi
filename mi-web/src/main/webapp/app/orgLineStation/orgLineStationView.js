Ext.define('App.orgLineStation.orgLineStationView', {
    extend: 'Ext.container.Container',
    requires: [
        "App.orgLineStation.orgLineView",
        "App.orgLineStation.orgLineController",
        "App.orgLineStation.orgLineWindow",
        "App.orgLineStation.orgStationView",
        "App.orgLineStation.orgStationController",
        "App.orgLineStation.orgStationAddWindow",
        "App.orgLineStation.orgStationUpdateWindow"
    ],
    xtype: 'orgLineStationView',
    id: 'orgLineStationView',
    fullscreen: true,
    layout:'border',
    items:[
        {
            region: 'west',
            xtype:"orgLineView",
            width:235
        },
        {
            region: 'center',
            xtype:"orgStationView"
        }
    ]
});