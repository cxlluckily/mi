Ext.define('App.repair.repairForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    // width: 800,
    xtype: 'repairForm',
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
                    name: 'repairInfoId',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'categoryName',
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>备件类型',
                    emptyText: '--双击选择--',
                    width: 210,
                    queryMode: 'local',
                    name: 'sparePartTypeId',
                    displayField: 'categoryName',
                    valueField: 'sparePartTypeId',
                    allowBlank: false,
                    editable: false,
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
                    name: 'partName',
                    hidden: true
                },
                {
                    fieldLabel: '<font color="red">*</font>备件名称',
                    name: 'sparePartId',
                    id: 'breakdownId',
                    xtype: 'combobox',
                    editable: true,
                    autoHeight: true,
                    emptyText: '--请选择--',
                    allowBlank: false,
                    width: 210,
                    queryMode: 'local',
                    store: {
                        xclass: 'App.common.getSparePartLList'
                    },
                    displayField: 'partName',
                    valueField: 'sparePartId',
                    listeners: {
                        beforerender: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                component.fieldLabel = '备件名称';
                                component.readOnly = true;
                                component.hideTrigger = true;
                                component.disable = true;
                            }
                            var formValues = component.up('form').getForm().getValues();
                            if (formValues.partName) {
                                component.setRawValue(formValues.partName);
                            } else {
                            }
                        },
                        change: function (component, newValue, oldValue, eOpts) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                return;
                            }
                            component.store.proxy.extraParams.partName = component.rawValue;
                            component.store.load();
                        },
                        focus: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                return;
                            }
                            component.expand();
                            var form = component.up('form');
                            if (form.down('combo[name="sparePartTypeId"]').value) {
                                component.store.proxy.extraParams.sparePartTypeId = form.down('combo[name="sparePartTypeId"]').value;
                            }
                            component.store.proxy.extraParams.partName = '';
                            component.store.load();
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'breakAbbreviated',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'breakdownInfoIdCopy',
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'breakdownKey',
                    hidden: true
                },
                {
                    fieldLabel: '<font color="red">*</font>故障简称',
                    name: 'breakdownInfoId',
                    width: 220,
                    xtype: 'combobox',
                    editable: true,
                    autoHeight: true,
                    emptyText: '--请选择--',
                    allowBlank: false,
                    store: {
                        xclass: 'App.common.getSparePartRepairDDLList'
                    },
                    displayField: 'breakAbbreviated',
                    valueField: 'breakdownInfoId',
                    queryMode: 'remote',
                    // selectOnFocus: true,
                    listeners: {
                        beforerender: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                component.fieldLabel = '故障简称';
                                component.readOnly = true;
                                // component.hideTrigger = true;
                                component.disable = true;
                            }
                            var formValues = component.up('form').getForm().getValues();
                            if (formValues.breakAbbreviated) {
                                var breakdownInfoId = component.getValue();
                                component.setRawValue(formValues.breakAbbreviated);
                                component.up('form').getForm().setValues({breakdownInfoIdCopy:breakdownInfoId});
                            } else {
                            }
                        },
                        change: function (component, nval, oval, opts) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                return;
                            }
                            component.store.proxy.extraParams.breakAbbreviated = component.rawValue;
                            component.store.proxy.extraParams.breakdownKey = component.rawValue;
                            component.store.load();
                        },
                        focus: function (component) {
                            var viewAction = component.up('window').viewAction;
                            if (viewAction == 'update') {
                                return;
                            }
                            component.expand();
                            var form = component.up('form');
                            if (form.down('combo[name="sparePartId"]').value) {
                                component.store.proxy.extraParams.sparePartId = form.down('combo[name="sparePartId"]').value;
                            }
                            component.store.proxy.extraParams.breakAbbreviated = '';
                            component.store.proxy.extraParams.breakdownKey = '';
                            component.store.load();
                        }
                    },
                    renderer: function (value, record) {
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'cueCode',
                    fieldLabel: '<font color="red">*</font>维修ID',
                    labelAlign: 'right',
                    autoHeight: true,
                    // allowBlank: false,
                    hidden:true,
                    width: 650,
                    blankText: '请输入维修ID!'

                },
                {
                    xtype: 'textarea',
                    name: 'reason',
                    fieldLabel: '原因',
                    labelAlign: 'right',
                    autoHeight: true,
                    // allowBlank: false,
                    width: 650,
                    blankText: '请输入原因!'

                }, {
                    xtype: 'textarea',
                    name: 'repairDescription',
                    fieldLabel: '<font color="red">*</font>维修描述',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    width: 650,
                    blankText: '请输入原因!'

                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
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
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.repair.repairWindow', {
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
    xtype: 'repairWindow',
    controller: 'repairController',
    items: [
        {
            xtype: 'repairForm'
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