Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringView', {
    extend: 'Ext.tab.Panel',
    xtype: 'operationsEquipmentMonitoringView',
    id: 'operationsEquipmentMonitoringView',
    requires: [
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGrid',
        'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOut',
    ],
    fullscreen: true,
    items: [
        {
            title:"线上设备监控",
            applyType:"online",
            xclass:"App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGrid"
        },
        {
            title:"未注册成功设备",
            applyType:"outline",
            xclass:"App.operationsEquipmentMonitoring.operationsEquipmentMonitoringGridOut"
        }
    ]
});