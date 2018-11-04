Ext.define('App.warehouseAdd.warehouseAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    height:550,
    // autoScroll:true,
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    xtype: 'warehouseAddForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [{
        xtype: 'container',
        layout: 'column',
        items: [{
            xtype: 'textfield',
            name: 'warehouseId',
            fieldLabel: '当前仓库ID',
            labelAlign: 'right',
            autoHeight: true,
            hidden: true
        },
            {
                xtype: 'textfield',
                name: 'parentWarehouseId',
                fieldLabel: '父仓库ID',
                labelAlign: 'right',
                autoHeight: true,
                hidden: true
            },
            {
                xtype: 'combo',
                emptyText: '所属工区',
                queryMode: 'local',
                name: 'workSectionId',
                editable: false,
                store: {
                    xclass: 'App.common.globalWorkAreaListStore'
                },
                displayField: 'name',
                valueField: 'id',
                allowBlank: false,
                fieldLabel: '所属工区',
                listeners: {
                    render: function (combo) {
                        combo.getStore().on("load", function (s, r, o) {
                            var firstValue = combo.getStore().getData().items[0].id;
                            combo.setValue(firstValue);
                            var p = Ext.ComponentQuery.query('warehouseView')[0];
                            p.getController().loadList(p);
                            var config = {
                                url: ctx + 'warehouse/getStationInfo.do',
                                data: {
                                    "workSectionId": firstValue,
                                    "parentWarehouseId": '-1'
                                },
                                success: function (response) {
                                    var obj = Ext.decode(response.responseText);
                                    if (obj.result == "success") {
                                        // var data = obj.data.stationList;
                                        var data = obj.data;
                                        var checkboxGroup = Ext.ComponentQuery.query('checkboxgroup')[0];
                                        checkboxGroup.removeAll();
                                        var items = [];
                                        Ext.each(data, function (item) {
                                            var checkItem = {
                                                boxLabel: item.stationName,
                                                inputValue: item.stationId
                                            };
                                            items.push(checkItem);
                                        });
                                        checkboxGroup.add(items);
                                    } else if (obj.result == "failure") {
                                        Ext.Msg.show({
                                            title: '提示',
                                            message: obj.message,
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.WARNING
                                        });
                                    }
                                },
                                failure: function (response) {
                                    console.log('server-side failure with status code ' + response.status);
                                }
                            };
                            App.common.Ajax.request(config);
                        });

                    },
                    change: function (field, newValue, oldValue) {
                        var config = {
                            url: ctx + 'warehouse/getStationInfo.do',
                            data: {
                                "workSectionId": field.value,
                                "parentWarehouseId": '-1',
                                userKey: window.sessionStorage['userKey']
                            },
                            success: function (response) {
                                var obj = Ext.decode(response.responseText);
                                if (obj.result == "success") {
                                    // var data = obj.data.stationList;
                                    var data = obj.data;
                                    var checkboxGroup = Ext.ComponentQuery.query('checkboxgroup')[0];
                                    checkboxGroup.removeAll();
                                    if (!data.length) {
                                        Ext.Msg.show({
                                            title: '提示',
                                            message: '当前工区没有站点, 请选择其他工区!',
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.INFO
                                        });
                                        return;
                                    }
                                    var items = [];
                                    Ext.each(data, function (item) {
                                        var checkItem = {
                                            boxLabel: item.stationName,
                                            inputValue: item.stationId
                                        };
                                        items.push(checkItem);
                                        checkboxGroup.add([checkItem])
                                    });
                                    // checkboxGroup.add(items);
                                } else if (obj.result == "failure") {
                                    Ext.Msg.show({
                                        title: '提示',
                                        message: obj.message,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                }
                            },
                            failure: function (response) {
                                console.log('server-side failure with status code ' + response.status);
                            }
                        };
                        App.common.Ajax.request(config);
                    }
                }
            },
            {
                xtype: 'textfield',
                name: 'warehouseName',
                fieldLabel: '<font color="red">*</font>仓库名称',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                blankText: '请输入机构名称!',
                maxLength: 16
                // emptyText: '请输入机构名称(1--16位)'
            },
            {
                xtype: 'textfield',
                name: 'location',
                fieldLabel: '<font color="red">*</font>所在位置',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                blankText: '请输入物品位置!',
                maxLength: 16
            },
            {
                xtype: 'textfield',
                name: 'headPerson',
                fieldLabel: '负责人',
                labelAlign: 'right',
                autoHeight: true,
                blankText: '请输入负责人!',
                maxLength: 16
            },
            {
                xtype: 'textfield',
                name: 'contactNumber',
                fieldLabel: '联系电话',
                labelAlign: 'right',
                autoHeight: true,
                regexText: '请输入正确的电话格式',
                regex: /^1\d{10}$/,
                // listeners: {
                //     change: function (field, newValue, oldValue) {
                //         if (newValue) {
                //             var mobileTrue = /^1(3|4|5|7|8)\d{9}$/.test(newValue);
                //             if (mobileTrue) {
                //                 return field.regex = /^1(3|4|5|7|8)\d{9}$/
                //             } else {
                //                 return field.regex = /([0-9]{3,4}-)?[0-9]{7,8}/
                //             }
                //         }
                //     }
                // }
            }, {
                xtype: 'textfield',
                name: 'description',
                fieldLabel: '仓库等级',
                labelAlign: 'right',
                autoHeight: true,
                blankText: '请输入仓库等级!',
                maxLength: 512,
                editable: false
            }, {
                xtype: 'textfield',
                name: 'internalNumber',
                fieldLabel: '内部编码',
                labelAlign: 'right',
                autoHeight: true,
                blankText: '请输入编码!',
                maxLength: 8
            }, {
                xtype: 'checkboxgroup',
                fieldLabel: '车站',
                margin: 0,
                layout: {
                    type: 'table',
                    columns: 5
                },
                defaults: {
                    xtype: 'checkbox',
                    name: 'selectedStationIdList',
                    labelAlign: 'right',
                    autoHight: true,
                    padding: '0 10',
                    column: 1
                },
                items: []
            },
            {
                xtype: 'fieldcontainer',
                fieldLabel: '状态',
                defaultType: 'radiofield',
                id: 'warehouseStatusRadioBox',
                defaults: {
                    width: 100,
                    padding: '0 10'
                },
                layout: 'hbox',
                items: [{
                    boxLabel: '可用',
                    name: 'status',
                    inputValue: 'start_using',
                    id: 'warehouseStatusRadioOne',
                    checked: true
                },
                    {
                        boxLabel: '不可用',
                        name: 'status',
                        inputValue: 'stop_using',
                        id: 'warehouseStatusRadioTwo'
                    }
                ]
            }, {
                xtype: 'textareafield',
                name: 'remark',
                fieldLabel: '备注',
                labelAlign: 'right',
                autoHeight: true,
                maxLength: 200,
                maxLengthText: '最多允许输入200个字'
            }
        ]
    }],
    buttons: [{
        text: '保存',
        action: 'save'
    }, {
        text: '取消',
        handler: function (a) {
            this.up('window').close();
        }
    }]
});


Ext.define('App.warehouse.warehouseAddWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 600,
    plain: false,
    iconCls: '{iconCls}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'warehouseAddWindow',
    controller: 'warehouseController',
    items: [{
        xtype: 'warehouseAddForm'
    }]
});