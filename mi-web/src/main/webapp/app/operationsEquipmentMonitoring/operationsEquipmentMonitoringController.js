Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.operationsEquipmentMonitoringController',
    refViews: {
        window: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringWindow',
        lookWindow: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringEquipmentWindow',
        commandWindow: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandWindow',
        outBindWindow: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringOutBindWindow',
        view: 'operationsEquipmentMonitoringGrid',
        gridOut: 'operationsEquipmentMonitoringGridOut'
    },
    control: {
        'operationsEquipmentMonitoringGrid button[action="search"]': {
            click: 'clickSearch'
        },
        'operationsEquipmentMonitoringGrid button[action="resets"]': {
            click: 'clickResets'
        },
        'operationsEquipmentMonitoringGrid button[action="execution"]': {
            click: 'clickExecution'
        },
        'operationsEquipmentMonitoringWindow button[action="save"]': {
            click: 'clickSave'
        },
        'operationsEquipmentMonitoringEquipmentWindow button[action="searchDetail"]': {
            click: 'clickSearchDetail'
        },
        'operationsEquipmentMonitoringCommandWindow button[action="searchCommand"]': {
            click: 'clickSearchCommand'
        },
        'operationsEquipmentMonitoringGridOut button[action="search"]': {
            click: 'clickSearchOut'
        },
        'operationsEquipmentMonitoringGridOut button[action="resets"]': {
            click: 'clickResetsOut'
        },
        'operationsEquipmentMonitoringOutBindWindow button[action="save"]': {
            click: 'clickSaveBind'
        },
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
        else if (flag == 'lookCommand') {
            var extWindow = Ext.create(me.refViews.commandWindow, {
                title: item.tooltip,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            let grid = extWindow.down('grid');
            grid.down('form').getForm().setValues({equipmentId:record.data.equipmentId});
            extWindow.show();
        }
        else if(flag == 'lookDetail'){
            let postData = {
                mqttCommandLogId:record.data.mqttCommandLogId
            };
            var config = {
                url: ctx + 'mqttLogs/getMqttLogsDeviceListDetail.do',
                data: postData,
                success: function (response, opts) {
                    var responseText = Ext.decode(response.responseText);
                    if (responseText.result == 'success') {
                        let eWindow = grid.up('window');
                        var messageJson = {
                            sendJson:{
                                time:me.getTime(responseText.data.createTime),
                                message:JSON.stringify(JSON.parse(responseText.data.sendJson),null,4)
                            },
                            receiveJson:{
                                time:me.getTime(responseText.data.responseTime),
                                message:JSON.stringify(JSON.parse(responseText.data.receiveJson),null,4),
                            },
                            resultJson:{
                                time:me.getTime(responseText.data.finishTime),
                                message:JSON.stringify(JSON.parse(responseText.data.resultJson),null,4),
                            },
                        };
                        let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringEquipmentWindow")[0].down('operationsEquipmentMonitoringEquipmentForm').editor;
                        me.setDetailMessage(messageJson,messageEditor);
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
        }
        else if(flag == 'lookDetailByEquipment'){
            let postData = {
                mqttCommandLogId:record.data.mqttCommandLogId
            };
            var config = {
                url: ctx + 'mqttLogs/getMqttLogsDeviceListDetail.do',
                data: postData,
                success: function (response, opts) {
                    var responseText = Ext.decode(response.responseText);
                    if (responseText.result == 'success') {
                        let eWindow = grid.up('window');
                        var messageJson = {
                            sendJson:{
                                time:me.getTime(responseText.data.createTime),
                                message:JSON.stringify(JSON.parse(responseText.data.sendJson),null,4)
                            },
                            receiveJson:{
                                time:me.getTime(responseText.data.responseTime),
                                message:JSON.stringify(JSON.parse(responseText.data.receiveJson),null,4),
                            },
                            resultJson:{
                                time:me.getTime(responseText.data.finishTime),
                                message:JSON.stringify(JSON.parse(responseText.data.resultJson),null,4),
                            },
                        };
                        let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringCommandWindow")[0].down('operationsEquipmentMonitoringCommandForm').editor;
                        me.setDetailMessage(messageJson,messageEditor);
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
        }
        else if(flag == 'bind'){
            var extWindow = Ext.create(me.refViews.outBindWindow, {
                title: item.tooltip,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            let form = extWindow.down('form');
            form.getForm().setValues(record.data);
            extWindow.show();
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickSearchDetail: function () {
        var pageList = Ext.ComponentQuery.query('operationsEquipmentMonitoringEquipmentWindow')[0].down('grid');
        pageList.getStore().load();
    },
    clickSearchCommand: function () {
        var pageList = Ext.ComponentQuery.query('operationsEquipmentMonitoringCommandWindow')[0].down('grid');
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
        var form = Ext.getCmp('operationsEquipmentMonitoringGridFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickSearchOut: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.gridOut)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResetsOut: function (btn) {
        var form = Ext.getCmp('operationsEquipmentMonitoringGridOutFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickExecution: function (btn) {
        var me = this;
        var records = Ext.ComponentQuery.query(me.refViews.view)[0].getSelectionModel().getSelection();
        let equipmentIds = [];
        // records.forEach(item => equipmentIds.push({
        //     equipmentId: item.data.equipmentId,
        //     deviceuId: item.data.deviceuId
        // }));
        records.forEach(function (item, index) {
            equipmentIds.push({
                equipmentId: item.data.equipmentId,
                deviceuId: item.data.deviceuId
            });
        });
        var extWindow = Ext.create(me.refViews.window, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').getForm().setValues({equipmentIds: JSON.stringify(equipmentIds)});
        extWindow.show();
    },
    clickSave: function (btn) {
        let formValue = btn.up('window').down('form').getForm().getValues();
        let myCommandBox = Ext.getCmp('myCommandBox');
        if (myCommandBox.activeTab == 'myTab1') {
            Ext.Msg.show({
                title: '提示',
                message: '请直接点击预设命令！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
        } else if (myCommandBox.activeTab == 'myTab2') {
            var editor = myCommandBox.editorShell;
            var commandType = 'shell';
        } else if (myCommandBox.activeTab == 'myTab3') {
            var editor = myCommandBox.editorDIY;
            var commandType = 'custom';
        }
        if (editor) {
            let commandContent = editor.getValue();
            if (commandContent == '') {
                return;
            }

            let json = {
                type: 'post',
                time: this.getTime(),
                json: commandContent
            };
            this.setMessage(json);
            let equipmentIds = JSON.parse(formValue.equipmentIds);
            let postData = {
                deviceFormBeans: equipmentIds,
                commandType: commandType,
                commandCategory: '',
                commandContent: commandContent
            };
            this.sendCommand(postData);
        }
        // 如果需要去掉空格，启用
        // let contentArray = editor.getSession().getDocument().$lines;
        // let content = '';
        // contentArray.forEach(function (item) {
        //     content += item.replace(/(^\s*)|(\s*$)/g, "");
        // });
        // console.log(content);
        // console.log(JSON.parse(content));
    },
    clickSaveBind:function(btn){
        var me = this;
        var form = btn.up('window').down('form');
        var formValues = form.getForm().getValues();
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var deviceuId = form.down('displayfield[name=deviceuId]').getValue();
        var deviceCode = form.down('displayfield[name=deviceCode]').getValue();
        var stationCode = form.down('displayfield[name=stationCode]').getValue();
        Ext.apply(formValues,{
            deviceuId:deviceuId,
            deviceCode:deviceCode,
            stationCode:stationCode
        });
        var config = {
            btn: btn,
            url: ctx + 'onlineEquipment/updateOperationsEquipment.do',
            data: formValues,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '绑定成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickSearchOut();
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: result.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
            }
        };
        App.common.Ajax.request(config);
    },
    sendCommand: function (postData) {
        let $this = this;
        let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringWindow")[0].down('form').editor;
        var config = {
            url: ctx + 'command/sendCommand.do',
            data: postData,
            success: function (response, opts) {
                var responseText = Ext.decode(response.responseText);
                if (responseText.result == 'success') {
                    let json = {
                        type: 'send',
                        time: responseText.data.sendTime,
                        json: JSON.stringify(JSON.parse(responseText.data.sendJson), null, 4)
                    };
                    $this.setMessage(json);
                    $this.addGrid(responseText.data);
                }
                else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseText.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
                // console.log(responseTextJson);
                // messageEditor.setValue(responseTextJson);
            }
        };
        App.common.Ajax.request(config);
    },
    addGrid: function (json) {
        let record = {
            // sendTime: json.sendTime,
            mqttCommandBatchId: json.mqttCommandBatchId
        };
        let grid = Ext.ComponentQuery.query("operationsEquipmentMonitoringWindow")[0].down('operationsEquipmentMonitoringCommandGrid');
        let gridData = [];
        grid.store.getData().items.forEach(function (item, index) {
            gridData.push(item.data);
        });
        gridData.unshift(record);
        grid.store.setData(gridData);
        // 滚动列表结尾
        // grid.body.dom.childNodes[0].scrollTop = grid.body.dom.childNodes[0].scrollHeight;
    },
    getGridStatus: function (grid) {
        let $this = this;
        grid.interval = setInterval(function () {
            if (grid.store.getData().length > 0) {
                let gridData = [];
                grid.store.getData().items.forEach(function (item, index) {
                    gridData.push(item.data.mqttCommandBatchId);
                });
                let postData = {
                    mqttCommandBatchIds: gridData
                };
                $this.loadGrid(postData);
            }
        }, 5000)
    },
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
            var addValue = '发送JSON：'+json.time+'\n'+json.json+'\n';
        }
        if (oldValue) {
            var newValue = oldValue+'\n'+addValue;
        } else {
            var newValue = addValue;
        }
        messageEditor.setValue(newValue);
        messageEditor.scrollPageDown();
    },
    setDetailMessage: function (json,editor) {
        // let messageEditor = Ext.ComponentQuery.query("operationsEquipmentMonitoringEquipmentWindow")[0].down('operationsEquipmentMonitoringEquipmentForm').editor;
        // let messageEditor = eWindow.down('form').editor;
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
        editor.setValue(newValue);
        // editor.scrollPageDown();
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