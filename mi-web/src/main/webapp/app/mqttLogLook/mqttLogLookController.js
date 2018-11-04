Ext.define('App.mqttLogLook.mqttLogLookController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.mqttLogLookController',
    refViews: {
        lookWindow: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentWindow',
        view: 'mqttLogLookView',
       
    },
    control: {
        'mqttLogLookView button[action="search"]': {
            click: 'clickSearch'
        },
        'mqttLogLookView button[action="resets"]': {
            click: 'clickResets'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
      
        if (flag == 'look') {
            var extWindow = Ext.create(me.refViews.lookWindow, {
                title: item.tooltip,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            let grid = extWindow.down('grid');
           
            grid.down('form').getForm().setValues({mqttCommandBatchId:record.data.mqttCommandBatchId});
            extWindow.show();
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickSearchDetail: function () {
        var pageList = Ext.ComponentQuery.query('mqttLogLookViewFormId')[0].down('grid');
        pageList.getStore().load();
    },
    clickResets: function (btn) {
        var form = Ext.getCmp('mqttLogLookViewFormId');
        form.form.reset();
        this.clickSearch();
    } ,
    loadGrid: function (postData) {
        var config = {
            url: ctx + 'mqttLogs/getExecuteCommandBatchList.do',
            data: postData,
            success: function (response, opts) {
                var responseText = Ext.decode(response.responseText);
                if (responseText.result == 'success') {
                    let grid = Ext.ComponentQuery.query("operationsEquipmentMonitoringWindow")[0].down('operationsEquipmentMonitoringCommandGrid');
                    grid.store.setData(responseText.data);
                }
                else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseText.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
            }
        };
        App.common.Ajax.request(config);
    },
    setMessage: function (json) {
        let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringWindow")[0].down('form').editor;
        let oldValue = messageEditor.getValue();
        if (json.type == 'post') {
//             var addValue = `发送：${json.time}
// ${json.json}
// `;
            var addValue = '发送：'+json.time+'\n'+json.json+'\n';
        } else {
            var addValue = '接收：'+json.time+'\n'+json.json+'\n';
        }
        if (oldValue) {
            var newValue = oldValue+'\n'+addValue;
        } else {
            var newValue = addValue;
        }
        messageEditor.setValue(newValue);
        messageEditor.scrollPageDown();
    },
    setDetailMessage: function (json) {
        let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringEquipmentWindow")[0].down('operationsEquipmentMonitoringEquipmentForm').editor;
//         var newValue = `发送：${json.sendJson.time}
// ${json.sendJson.message}
//
// 应答：${json.receiveJson.time}
// ${json.receiveJson.message}
//
// 完成：${json.resultJson.time}
// ${json.resultJson.message}`;
        var newValue = '发送：'+json.sendJson.time+'\n'+json.sendJson.message+'\n\n'+'应答：'+json.receiveJson.time+'\n'+json.receiveJson.message+'\n\n'+'完成：'+json.resultJson.time+'\n'
            +json.resultJson.message;
        messageEditor.setValue(newValue);
        messageEditor.scrollPageDown();
    },
    getTime: function (value) {
        if(value){
            var date = new Date(value);
        }else{
            var date = new Date();
        }
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = ('0' + date.getDate()).slice(-2) + ' ';
        h = ('0' + date.getHours()).slice(-2) + ':';
        m = ('0' + date.getMinutes()).slice(-2) + ':';
        s = ('0' + date.getSeconds()).slice(-2);
        return Y + M + D + h + m + s;
    }
});