Ext.define('App.warehouse.warehouseForm', {
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
    xtype: 'warehouseForm',
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
                fieldLabel: '所属工区',
                allowBlank: false,
                readOnly: true,
                listeners: {
                    beforerender: function (e) {
                        var parentWarehouseId = e.up('form').getForm().getValues().parentWarehouseId;
                        if (parentWarehouseId != -1)
                        {
                            e.readOnly = true;
                        }
                    }
                }
            },
            {
                xtype: 'textfield',
                name: 'parentWarehouseId',
                fieldLabel: '父仓库ID',
                labelAlign: 'right',
                autoHeight: true,
                // blankText: '请输入父节点ID!',
                hidden: true
            },
            {
                xtype: 'textfield',
                name: 'workSectionIdHidden',
                fieldLabel: 'workSectionIdHidden',
                labelAlign: 'right',
                autoHeight: true,
                // blankText: '请输入父节点ID!',
                hidden: true
            },
            {
                xtype: 'textfield',
                name: 'flag',
                fieldLabel: 'flag',
                labelAlign: 'right',
                autoHeight: true,
                // blankText: '请输入父节点ID!',
                hidden: true
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
            },
            {
                xtype: 'textfield',
                name: 'location',
                fieldLabel: '<font color="red">*</font>所在位置',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                blankText: '请输入机构名称!',
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
                regexText: '请输入正确的手机号格式',
                regex: /^1\d{10}$/,
                // listeners: {
                //     change: function (field, newValue, oldValue) {
                //         if (newValue)
                //         {
                //             var mobileTrue = /^1\d{10}$/.test(newValue);
                //             if (mobileTrue)
                //             {
                //                 return field.regex = /^1\d{10}$/
                //             }
                //             else
                //             {
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
            },
            // {
            //     xtype: 'textfield',
            //     name: 'internalNumber',
            //     fieldLabel: '内部编码',
            //     labelAlign: 'right',
            //     autoHeight: true,
            //     blankText: '请输入内部编码!',
            //     maxLength: 8
            // },
            {
                items: [{
                    xtype: 'checkboxgroup',
                    fieldLabel: '车站',
                    autoHeight:true,
                    margin: 0,
                    layout: {
                        type:'table',
                        columns:5
                    },
                    defaults: {
                        // width: 100,
                        padding: '0 10',
                        xtype: 'checkbox',
                        labelAlign: 'right',
                        autoHight: true,
                        column:1,
                        hidden: false,
                        name :'selectedStationIdList'
                    },
                    items: []
                }],
                listeners: {
                    beforerender: function (comp) {
                        var me = this;
                        var warehouseId = Ext.ComponentQuery.query("warehouseForm")[0].getForm().getValues().warehouseId;
                        var workSectionId = Ext.ComponentQuery.query("warehouseForm")[0].getForm().getValues().workSectionIdHidden;
                        var parentWarehouseId = Ext.ComponentQuery.query("warehouseForm")[0].getForm().getValues().parentWarehouseId;
                        var isReadOnly = false;
                        var flag = Ext.ComponentQuery.query("warehouseForm")[0].getForm().getValues().flag;
                        var config = {
                            url: ctx + 'warehouse/getWarehouseInfo.do',
                            data: {
                                warehouseId: warehouseId,
                                parentWarehouseId: parentWarehouseId,
                                workSectionId: workSectionId,
                                // userKey: window.sessionStorage['userKey']
                            },
                            success: function (response) {
                                var obj = Ext.decode(response.responseText);
                                if (obj.result == "success")
                                {
                                    var data = obj.data.stationList;
                                    var checkboxGroup = me.down('checkboxgroup');
                                    Ext.each(data, function (item) {
                                        var checkItem = {
                                            boxLabel: item.stationName,
                                            inputValue: item.stationId,
                                            checked: item.isChecked
                                        };
                                        // if (parentWarehouseId != -1 && flag !== 'add')
                                        // {
                                        //     checkItem.hidden = !item.isChecked;
                                        // }
                                        checkboxGroup.add([checkItem]);
                                    });
                                }
                                else if (obj.result == "failure")
                                {
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
                    id: 'warehouseStatusRadioOne'
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
                maxLengthText:'最多输入200个字'
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


Ext.define('App.warehouse.warehouseWindow', {
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
    xtype: 'warehouseWindow',
    controller: 'warehouseController',
    items: [{
        xtype: 'warehouseForm'
    }]
});