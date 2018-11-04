Ext.define('App.orgLineStation.orgStationUpdateWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 800,
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
    xtype: 'orgStationUpdateWindow',
    controller: 'orgStationController',
    viewAction: '{viewAction}',
    items: [{
        xtype: 'orgStationForm'
    }],
    buttons: [
        {
            text: '保存',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});

Ext.define('App.orgLineStation.orgStationForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'orgStationForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'textfield',
                    name: 'lineId',
                    fieldLabel: 'lineId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'stationId',
                    fieldLabel: 'stationId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'stationName',
                    fieldLabel: '<font color="red">*</font>站点名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入站点名称!',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'stationCode',
                    fieldLabel: '<font color="red">*</font>站点编码',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入站点编码!',
                    maxLength: 8
                },
                {
                    xtype: 'textfield',
                    name: 'headPerson',
                    fieldLabel: '负责人',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'phone',
                    fieldLabel: '电话',
                    labelAlign: 'right',
                    autoHeight: true,
                    // regexText : '请输入正确的电话格式',
                    // regex: '',
                    // listeners: {
                    //     change: function (field, newValue, oldValue) {
                    //         if(newValue) {
                    //             var mobileTrue = /^1(3|4|5|7|8)\d{9}$/.test(newValue);
                    //             if(mobileTrue) {
                    //                 return field.regex = /^1(3|4|5|7|8)\d{9}$/
                    //             }else {
                    //                 return field.regex = /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/
                    //             }
                    //         }
                    //     }
                    // }
                },
                {
                    xtype: 'checkboxgroup',
                    fieldLabel: '起始终点站',
                    name: 'wasBeginEndGroup',
                    defaultType: 'checkbox',
                    cls: 'x-check-group-alt',
                    defaults: {
                        width: 120,
                        padding: '0 0 0 0',
                        style: {
                            paddingRight: '20px'
                        }
                    },
                    columns: 2,
                    items: [
                        {
                            boxLabel: '起始站',
                            name: 'wasBegin',
                            inputValue:true
                        },
                        {
                            boxLabel: '终点站',
                            name: 'wasEnd',
                            inputValue:true
                        }
                    ]
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    id: 'orgStationStatusRadioBox',
                    defaults: {
                        width: 100,
                        padding: '0 10'
                    },
                    layout: 'hbox',
                    items: [
                        {
                            boxLabel: '可用',
                            name: 'status',
                            inputValue: 'start_using',
                            id: 'orgStationStatusRadioOne'
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            id: 'orgStationStatusRadioTwo'
                        }
                    ]
                },
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200
                }
            ]
        }
    ]
});
