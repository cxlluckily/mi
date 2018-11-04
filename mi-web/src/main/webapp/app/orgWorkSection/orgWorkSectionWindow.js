Ext.define('App.orgWorkSection.orgWorkSectionForm', {
    extend: 'Ext.panel.Panel',
    xtype: 'orgWorkSectionForm',
    requires: [
        'App.orgWorkSection.orgWorkSectionStore'
    ],
    layout: 'fit',
    viewModel: true,
    fullscreen: true,
    width: 1000,
    height: 500,
    tbar: [
        {
            xtype: 'form',
            fullscreen: true,
            layout: 'column',
            buttonAlign: 'center',
            width: '100%',
            fieldDefaults: {
                anchor: '100%',
                labelAlign: 'right',
                labelWidth: 70,
                padding: '10,10,10,10',
                width: '100%'
            },
            items: [
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: 1,
                            items: [
                                // {
                                //     xtype: 'displayfield',
                                //     name: 'operationSubjectName',
                                //     fieldLabel: '公司',
                                //     labelAlign: 'right',
                                //     autoHight: true,
                                //     fieldStyle: {
                                //         margin: 0,
                                //         padding: "8px 0px"
                                //     }
                                // },
                                // {
                                //     xtype: 'textfield',
                                //     name: 'operationSubjectId',
                                //     fieldLabel: 'operationSubjectId',
                                //     labelAlign: 'right',
                                //     autoHight: true,
                                //     hidden: true
                                // },
                                {
                                    xtype: 'textfield',
                                    name: 'workSectionId',
                                    fieldLabel: 'workSectionId',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    hidden: true
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: .33,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'sectionCode',
                                    fieldLabel: '<font color="red">*</font>工区编码',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    allowBlank: false,
                                    blankText: '请输入工区编码!',
                                    maxLength: 16,
                                    regex: App.common.regExpValidator.noChinese,
                                    regexText : '不能输入汉字',
                                    validator:'',
                                    invalidText:'该工区编码已经存在',
                                    listeners: {
                                        // checkIsExist: function () {
                                        //     var number = this.ownerCt.find('name', 'sectionCode')[0].getValue();
                                        //     Ext.Ajax.request({
                                        //         url : 'ContractAction_checkExist.do',
                                        //         params : {
                                        //             contractNum : number
                                        //         },
                                        //         method : 'POST',
                                        //         scope : this.ownerCt,
                                        //         success : function(response, options) {
                                        //             var responseArray = Ext.util.JSON
                                        //                 .decode(response.responseText);
                                        //             if (responseArray.success == true){
                                        //                 this.bIsExist = false;
                                        //             }else{
                                        //                 this.bIsExist = true;
                                        //             }
                                        //         }
                                        //     });
                                        //     return this.ownerCt.bIsExist;
                                        // }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: .33,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'sectionName',
                                    fieldLabel: '<font color="red">*</font>工区名称',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    allowBlank: false,
                                    blankText: '请输入工区名称!',
                                    maxLength: 16
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: .33,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'headPerson',
                                    fieldLabel: '负责人',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    maxLength: 16
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: .33,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'phone',
                                    fieldLabel: '手机号',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    // regex: /^1\d{10}$/,
                                    // regexText: '请输入正确的手机号格式',
                                    // listeners: {
                                    //     change: function (field, newValue, oldValue) {
                                    //         if (newValue) {
                                    //             var mobileTrue = /^1(3|4|5|7|8)\d{9}$/.test(newValue);
                                    //             if (mobileTrue) {
                                    //                 return field.regex = /^1(3|4|5|7|8)\d{9}$/
                                    //             } else {
                                    //                 return field.regex = /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/
                                    //             }
                                    //         }
                                    //     }
                                    // }
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: .66,
                            items: [
                                {
                                    xtype: 'fieldcontainer',
                                    fieldLabel: '状态',
                                    defaultType: 'radiofield',
                                    id: 'orgWorkSectionRadioBox',
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
                                            id: 'orgWorkSectionRadioOne'
                                        },
                                        {
                                            boxLabel: '不可用',
                                            name: 'status',
                                            inputValue: 'stop_using',
                                            id: 'orgWorkSectionRadioTwo'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            layout: 'column',
                            columnWidth: 1,
                            items: [
                                {
                                    xtype: 'textfield',
                                    name: 'remark',
                                    fieldLabel: '备注',
                                    labelAlign: 'right',
                                    autoHight: true,
                                    maxLength: 200,
                                    maxLengthText:'最多输入200个字'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    layout: 'column',
                    columnWidth: 1,
                    items: []
                }
            ]
        }
    ],
    items: [
        {
            xtype: 'tabpanel',
            // reference: 'tabpanel',
            border: false,
            width: 100,
            height: 300,
            store: {
                xclass: 'App.orgWorkSection.orgWorkSectionStationStore'
            },
            defaults: {
                bodyPadding: 10,
                scrollable: true,
                closable: false,
                border: false
            },
            tabPosition: 'left',
            tabRotation: 0,
            listeners: {
                beforerender: function (combo) {
                    var formData = combo.up('window').down('form').getForm().getValues();
                    var config = {
                        url: ctx + 'workSection/getLineAndStationInfo.do',
                        data: {
                            workSectionId: formData.workSectionId
                        },
                        success: function (response) {
                            var obj = Ext.decode(response.responseText);
                            if (obj.result == "success") {
                                var tabs = obj.data;
                                Ext.each(tabs, function (item) {
                                    item.title = item.lineName;
                                    item.xclass = 'App.orgWorkSection.orgWorkSectionStationForm';
                                    combo.add(item);
                                });
                                var tabs = combo.items.items;
                                Ext.each(tabs, function (item) {
                                    var checkboxGroup = item.down('checkboxgroup');
                                    if (checkboxGroup.items.length == 0) {
                                        Ext.each(item.stationList, function (stationItem) {
                                            stationItem.isCheck = stationItem.isCheck == 'true';
                                            var checkItem = {
                                                xtype: 'checkbox',
                                                boxLabel: stationItem.stationName,
                                                labelAlign: 'right',
                                                name: stationItem.stationId,
                                                autoHight: true,
                                                checked: stationItem.isCheck
                                            };
                                            checkboxGroup.add(checkItem);
                                        });
                                    }
                                });
                                combo.setActiveTab(0);
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
            },
            items: []
        }]

});

Ext.define('App.orgWorkSection.orgWorkSectionWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 1000,
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
    xtype: 'orgWorkSectionWindow',
    controller: 'orgWorkSectionController',
    items: [
        {
            xtype: 'orgWorkSectionForm'
        }
    ],
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
