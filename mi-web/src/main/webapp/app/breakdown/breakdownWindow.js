Ext.define('App.breakdown.breakdownForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    xtype: 'breakdownForm',
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
                    name: 'breakdownInfoId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'categoryName',
                    id: 'categoryNameText',
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>备件类型',
                    emptyText: '--双击选择--',
                    width: 320,
                    queryMode: 'local',
                    name: 'sparePartTypeId',
                    editable: false,
                    allowBlank: false,
                    displayField: 'categoryName',
                    valueField: 'sparePartTypeId',
                    listeners: {
                        beforerender: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                component.fieldLabel = '备件类型';
                                component.readOnly = true;
                                component.hideTrigger = true;
                                component.disable = true;
                            }
                            var formValues = component.up('form').getForm().getValues();
                            if (formValues.categoryName) {
                                component.setRawValue(formValues.categoryName);
                            } else {
                            }
                        },
                        expand: function (cb, e, opt) {
                            var viewAction = cb.up('window').viewAction;
                            if(viewAction == 'update'){
                                return;
                            }
                            var config = {
                                title: this.fieldLabel,
                                treeParams: {
                                    url: 'sparePartType/getSparePartTypeInfo.do',
                                    rootProperty: 'data',
                                    component: cb,
                                    params: {
                                        status: "start_using"
                                    }
                                }
                            };
                            var w = Ext.create('App.common.selectWindow', config);
                            w.show();
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    id: 'partNametext',
                    name: 'partName',
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '备件',
                    emptyText: '--请选择--',
                    width: 320,
                    editable: false,
                    store: {
                        xclass: 'App.common.getSparePartDDLList'
                    },
                    queryMode: 'remote',
                    name: 'sparePartId',
                    displayField: 'partName',
                    allowBlank: false,
                    valueField: 'sparePartId',
                    listeners: {
                        beforerender: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                component.fieldLabel = '备件';
                                component.readOnly = true;
                                component.hideTrigger = true;
                                component.disable = true;
                            }
                            var formValues = component.up('form').getForm().getValues();
                            component.store.proxy.extraParams.partName = '';
                            component.store.proxy.extraParams.sparePartTypeId = formValues.sparePartTypeId;
                            component.store.load();
                        },
                        change: function (component, newValue, oldValue, eOpts) {
                            var viewAction = component.up('window').viewAction;
                            if(viewAction == 'update'){
                                return;
                            }
                            component.store.proxy.extraParams.partName = component.rawValue;
                            component.store.load();
                        },
                        focus: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if(viewAction == 'update'){
                                return;
                            }
                            var form = component.up('form');
                            component.store.proxy.extraParams.sparePartTypeId = form.down('combo[name="sparePartTypeId"]').value;
                            component.store.proxy.extraParams.partName = '';
                            component.store.load();
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'breakdownKey',
                    fieldLabel: '<font color="red">*</font>故障ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    // allowBlank: false,
                    hidden:true,
                    width: 650,
                    blankText: '请输入故障ID'
                },
                {
                    xtype: 'textfield',
                    name: 'breakAbbreviated',
                    fieldLabel: '<font color="red">*</font>故障简称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    width: 650,
                    blankText: '请输入故障分类!',
                    maxLength: 15,
                    maxLengthText: '最多输入15个字'
                },
                {
                    xtype: 'textarea',
                    name: 'breakDescription',
                    fieldLabel: '故障描述',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    width: 650,
                    blankText: '请输入故障描述!',
                    maxLength: 200,
                    maxLengthText: '最多输入200个字'

                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    // id: 'superUserStatusRadioBox',
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
                            // id: 'superUserStatusRadioOne'
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            // id: 'superUserStatusRadioTwo'
                        }
                    ]
                }
            ]
        }
    ]
});


Ext.define('App.breakdown.breakdownWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    recordData: '{recordData}',
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
    xtype: 'breakdownWindow',
    controller: 'breakdownController',
    items: [
        {
            xtype: 'breakdownForm'
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