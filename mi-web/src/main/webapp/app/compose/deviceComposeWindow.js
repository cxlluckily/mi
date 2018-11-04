Ext.define('App.compose.deviceComposeForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'deviceComposeForm',
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
            items: [
                {
                    xtype: 'textfield',
                    name: 'deviceComposeId',
                    fieldLabel: 'deviceComposeId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'partName',
                    fieldLabel: 'partName',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'categoryName',
                    fieldLabel: 'categoryName',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'composePid',
                    fieldLabel: 'composePid',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'composePids',
                    fieldLabel: 'composePids',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'parentPartId',
                    fieldLabel: 'parentPartId',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'textfield',
                    name: 'parentPartIds',
                    fieldLabel: 'parentPartIds',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden: true
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>备件类型',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.getSparePartByCompose',
                    },
                    queryMode: 'local',
                    name: 'sparePartTypeId',
                    editable: false,
                    displayField: 'categoryName',
                    valueField: 'sparePartTypeId',
                    allowBlank: false,
                    blankText: '请选择备件类型!',
                    listeners: {
                        beforerender: function (component) {
                            var formValues = component.up('form').getForm().getValues();
                            var new_params = {parentPartId: -1};
                            if (formValues.composePid != -1 && formValues.composePid != '-1')
                            {
                                new_params = {parentPartId: formValues.parentPartId};
                            }
                            var store = component.getStore();
                            Ext.apply(store.proxy.extraParams, new_params);
                        },
                        select: function (component) {
                            var formValues = component.up('form').getForm().getValues();
                            if (formValues.composePid == -1 || formValues.composePid == '-1')
                            {
                                component.up('form').getForm().setValues({sparePartId: ''});
                                var store = component.up('form').down('combo[name="sparePartId"]').getStore();
                                Ext.apply(store.proxy.extraParams, {sparePartTypeId: formValues.sparePartTypeId})
                                store.load();
                            }
                        },
                        render: function (component) {//渲染
                            var formValues = component.up('form').getForm().getValues();
                            var store = component.up('form').down('combo[name="sparePartId"]').getStore();
                            Ext.apply(store.proxy.extraParams, {sparePartTypeId: formValues.sparePartTypeId})
                            store.load();
                        }
                    }
                },
                {
                    xtype: 'combo',
                    fieldLabel: '<font color="red">*</font>备件',
                    emptyText: '--请选择--',
                    store: {
                        xclass: 'App.common.getPartListBySparePartTypeId',
                    },
                    queryMode: 'local',
                    name: 'sparePartId',
                    editable: false,
                    displayField: 'partName',
                    valueField: 'sparePartId',
                    allowBlank: false,
                    blankText: '请选择备件!',
                    listeners: {
                        beforerender: function (component) {
                            var formValues = component.up('form').getForm().getValues();
                            if (formValues.composePid != -1 && formValues.composePid != '-1')
                            {
                                component.hidden = true;
                                component.allowBlank = true;
                            }
                        },
                        render: function (component) {//渲染
                            component.getStore().on("load", function (s, r, o) {
                                var form = component.up('form');
                                var value = form.getForm().getValues();
                                if (!value[component.name]&&r.length>0)
                                {
                                    component.setValue(r[0].get(component.valueField));
                                }
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    name: 'name',
                    fieldLabel: '<font color="red">*</font>名称',
                    labelAlign: 'right',
                    autoHeight: true,
                    allowBlank: false,
                    blankText: '请输入名称!',
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'brand',
                    fieldLabel: '品牌',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 16
                },
                {
                    xtype: 'textfield',
                    name: 'composeModel',
                    fieldLabel: '型号',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 16
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '状态',
                    defaultType: 'radiofield',
                    id: 'superUserStatusRadioBox',
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
                            id: 'superUserStatusRadioOne'
                        },
                        {
                            boxLabel: '不可用',
                            name: 'status',
                            inputValue: 'stop_using',
                            id: 'superUserStatusRadioTwo'
                        }
                    ]
                }
            ]
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


Ext.define('App.compose.deviceComposeWindow', {
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
    xtype: 'deviceComposeWindow',
    controller: 'deviceComposeController',
    items: [
        {
            xtype: 'deviceComposeForm'
        }
    ]
});