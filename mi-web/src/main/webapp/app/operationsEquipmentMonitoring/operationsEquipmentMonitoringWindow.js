Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentMonitoringAddForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        // padding: '10,10,10,10',
        width: '100%',
        margin: '0px',
        padding: '0px',
        borderSpacing: '0px',
    },
    listeners: {
        // beforerender: function (component) {
        //     var viewAction = component.up('window').viewAction;
        // }
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'textfield',
            name: 'equipmentIds',
            hidden: true
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: .59,
                    items: [
                        {
                            columnWidth: 1,
                            layout: 'form',
                            border: false,
                            padding: '0',
                            borderSpacing: '0px',
                            items: [
                                {
                                    id: 'myCommandBox',
                                    activeTab: 'myTab1',
                                    html:
                                    '<div class="myTabs" id="myTab">' +
                                    '<div class="tab">\n' +
                                    '    <label class="label active" for="myTab1">预设</label>\n' +
                                    '    <label class="label" for="myTab2">shell</label>\n' +
                                    '    <label class="label" for="myTab3">自定义</label>\n' +
                                    '<div class="clearBoth"></div>' +
                                    '</div>\n' +
                                    '<div class="box">\n' +
                                    '    <div class="content"><input id="myTab1">\n' +
                                    '    </div>\n' +
                                    '    <div class="content"><input id="myTab2">\n' +
                                    '        <div id="myAceEditorShell" style="width: 100%;height: 100%"></div>' +
                                    '    </div>\n' +
                                    '    <div class="content"><input id="myTab3">\n' +
                                    '        <div id="myAceEditorDIY" style="width: 100%;height: 100%"></div>' +
                                    '    </div>\n' +
                                    '</div>' +
                                    '</div>',
                                    listeners: {
                                        afterrender: function (component) {
                                            //启用提示菜单
                                            ace.require("ace/ext/language_tools");
                                            component.preinstall = $('#myTab1').parent('.content');
                                            component.editorShell = ace.edit("myAceEditorShell");
                                            component.editorShell.setTheme("ace/theme/monokai");
                                            // component.editorShell.setOption("wrap", "free");
                                            component.editorShell.setFontSize(16);
                                            component.editorDIY = ace.edit("myAceEditorDIY");
                                            component.editorDIY.setTheme("ace/theme/monokai");
                                            // component.editorDIY.setOption("wrap", "free");
                                            component.editorDIY.setFontSize(16);
                                            let controller = component.up('window').controller;
                                            $('#myTab .label').click(function () {
                                                let $this = $(this);
                                                component.activeTab = $this.attr('for');
                                                $this.siblings().removeClass('active');
                                                $this.addClass('active');
                                            });
                                            var config = {
                                                url: ctx + 'command/getPreinstallCommandDDL.do',
                                                data: {},
                                                success: function (response, opts) {
                                                    var responseText = Ext.decode(response.responseText);
                                                    var buttons = responseText.data;
                                                    buttons.forEach(function (item, index) {
                                                        // let str = `<button class="myButton" btnIndex="${index}">${item.commandName}</button>`;
                                                        let str = '<button class="myButton" btnIndex="' + index + '">' + item.commandName + '</button>';
                                                        component.preinstall.append(str);
                                                    });
                                                    component.preinstall.find('.myButton').click(function () {
                                                        let controller = component.up('window').controller;
                                                        let nowDate = controller.getTime();
                                                        let index = $(this).attr('btnIndex');
                                                        let type = component.activeTab;
                                                        let commandType = '';
                                                        let json = {
                                                            type: 'post',
                                                            time: nowDate,
                                                            json: buttons[index].commandContext
                                                        };
                                                        controller.setMessage(json);
                                                        switch (type) {
                                                            case 'myTab1':
                                                                commandType = 'preinstall';
                                                                break;
                                                            case 'myTab2':
                                                                commandType = 'shell';
                                                                break;
                                                            case 'myTab3':
                                                                commandType = 'custom';
                                                                break;
                                                        }
                                                        let equipmentIds = JSON.parse(component.up('form').getForm().getValues().equipmentIds);
                                                        let postData = {
                                                            deviceFormBeans: equipmentIds,
                                                            commandType: commandType,
                                                            commandCategory: buttons[index].commandName,
                                                            commandContent: buttons[index].commandContext
                                                        };
                                                        controller.sendCommand(postData);
                                                    })
                                                }
                                            };
                                            App.common.Ajax.request(config);

                                            // component.preinstall.append()
                                            // console.log(btns);
                                            // console.log(component.up('form').editor);
                                            // editor.getSession().setMode("ace/mode/javascript");
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            columnWidth: 1,
                            layout: 'form',
                            border: false,
                            padding: '0',
                            borderSpacing: '0px',
                            items: [
                                {xclass: 'App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandGrid'}
                            ]
                        }
                    ]
                },
                {
                    columnWidth: .4,
                    layout: 'form',
                    border: false,
                    padding: '0',
                    borderSpacing: '0px',
                    items: [
                        {
                            // xtype: 'container',
                            html: '<div id="myAceEditor2" style="height: 500px;width: 450px"></div>',
                            listeners: {
                                afterrender: function (component) {
                                    component.up('form').editor = ace.edit("myAceEditor2");
                                    // console.log(Editor);
                                    component.up('form').editor.setTheme("ace/theme/monokai");
                                    component.up('form').editor.setReadOnly(true);
                                    // component.up('form').editor.setOption("wrap", "free");
                                    // component.up('form').editor.getSession().setMode("ace/mode/json");
                                    component.up('form').editor.setFontSize(16);
                                }
                            },
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringCommandGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'operationsEquipmentMonitoringCommandGrid',
    fullscreen: true,
    // 数据列表
    columnLines: true,
    height: 220,
    border: '1px solid #5fa2dd',
    // width: 550,
    listeners: {
        afterrender: function (component) {
            let controller = component.up('window').controller;
            controller.getGridStatus(component);
        }
    },
    columns: [
        {
            text: '序号',
            xtype: 'rownumberer',// 多选框
            width: '10px',
            align: 'center'
        },
        {
            text: 'mqttCommandBatchId',
            dataIndex: 'mqttCommandBatchId',
            flex: 1,
            hidden: true
        },
        {
            text: '时间',
            dataIndex: 'createTime',
            width: 150,
            renderer: function (value) {
                if (value) {
                    function timetrans(date) {
                        var date = new Date(date);
                        Y = date.getFullYear() + '-';
                        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                        D = ('0' + date.getDate()).slice(-2) + ' ';
                        h = ('0' + date.getHours()).slice(-2) + ':';
                        m = ('0' + date.getMinutes()).slice(-2) + ':';
                        s = ('0' + date.getSeconds()).slice(-2);
                        return Y + M + D + h + m + s;
                    }

                    return timetrans(value)
                }
                return '';
            }
        },
        {
            text: '批次号',
            dataIndex: 'batchNo',
            width: 150,
        },
        {
            text: '下发',
            dataIndex: 'sendCount',
            flex: 1
        },
        {
            text: '应答',
            dataIndex: 'receiveCount',
            flex: 1
        },
        {
            text: '完成',
            dataIndex: 'resultCount',
            flex: 1
        },
        {
            xtype: 'actioncolumn',
            text: '操作',
            flex: 1,
            menuDisabled: true,
            align: 'center',
            // handler: 'onEditRowAction',
            isDisabled: 'isRowEditDisabled',
            fieldDefaults: {
                style: {
                    margin: "20px 10px"
                }
            },
            defaults: {
                bodyStyle: 'padding:0px 10px'
                // margin: "0px 10px"
            },
            items: [
                {
                    tooltip: '查看',
                    iconCls: "fa-eye",
                    flag: 'look',
                    handler: 'actionFn'
                }
            ]
        }
    ],
});

Ext.define('App.operationsEquipmentMonitoring.operationsEquipmentMonitoringWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1200,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    // autoScroll:true,
    // bodyStyle: {
    //     'overflow-y': 'auto',
    //     'overflow-x': 'hidden'
    // },
    listeners: {
        beforeclose: function (panel, eOpts) {
            let grid = panel.down('operationsEquipmentMonitoringCommandGrid');
            clearInterval(grid.interval);
            grid.store.setData([]);
            grid.interval = '';
        }
    },
    buttonAlign: "center",
    xtype: 'operationsEquipmentMonitoringWindow',
    controller: 'operationsEquipmentMonitoringController',
    items: [
        {
            xtype: 'operationsEquipmentMonitoringAddForm'
        }
    ],
    buttons: [
        {
            text: '发送命令',
            action: 'save'
        }, {
            text: '关闭',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});