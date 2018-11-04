Ext.define('App.operationsEquipment.operationsEquipmentAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    // layout: 'auto',
    buttonAlign: 'center',
    xtype: 'operationsEquipmentAddForm',
    // width:580,
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%',
        margin: '0'
    },
    listeners: {
        beforerender: function (component) {
            var viewAction = component.up('window').viewAction;
        }
    },
    // autoScroll: true,
    items: [
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: 1,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: 'equipmentId',
                            name: 'equipmentId',
                            hidden:true
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'stationName',
                            name: 'stationName',
                            hidden:true
                        },
                        {
                            xtype: 'combo',
                            fieldLabel: '<font style="color: red">*</font>设备类型',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.getEquipmentTypeFirst'
                            },
                            queryMode: 'local',
                            name: 'sparePartTypeId',
                            editable: false,
                            displayField: 'categoryName',
                            valueField: 'sparePartTypeId',
                            allowBlank: false,
                            blankText: '请选择设备类型！',
                            listeners: {
                                render: function (component) {
                                    var form = component.up('form');
                                    var sparePartCombo = form.down('combo[name="sparePartId"]');
                                    if(component.getValue()){
                                        sparePartCombo.getStore().proxy.extraParams.sparePartTypeId = component.getValue();
                                        sparePartCombo.getStore().load();
                                    }
                                },
                                change: function (component, newValue, oldValue, eOpts) {
                                    var form = component.up('form');
                                    var sparePartCombo = form.down('combo[name="sparePartId"]');
                                    sparePartCombo.getStore().proxy.extraParams.sparePartTypeId = newValue;
                                    sparePartCombo.getStore().load();
                                },
                                select: function (component) {
                                    var form = component.up('form');
                                    var sparePartCombo = form.down('combo[name="sparePartId"]');
                                    sparePartCombo.setValue('');
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '<font style="color: red">*</font>备件名称',
                            emptyText: '--请选择--',
                            store: {
                                xclass: 'App.common.getPartListBySparePartTypeId'
                            },
                            queryMode: 'local',
                            name: 'sparePartId',
                            editable: false,
                            displayField: 'partName',
                            valueField: 'sparePartId',
                            allowBlank: false,
                            blankText: '请选择备件名称！',
                            listeners: {
                                render: function (combo) {
                                    // combo.getStore().on("load", function (s, r, o) {
                                    //     if (r.length > 0) {
                                    //         if (!combo.getValue()) {
                                    //             combo.setValue(r[0].data[combo.valueField]);
                                    //         }
                                    //     } else {
                                    //         combo.setValue('');
                                    //     }
                                    // });
                                },
                                change: function (component, newValue, oldValue, eOpts) {
                                    var data = component.getStore().getData().items;
                                    if (data.length > 0) {
                                        Ext.each(data, function (item, index) {
                                            if (item.data.sparePartId == newValue) {
                                                var formValues = {
                                                    materiaCoding: item.data.materiaCoding,
                                                    specificationModel: item.data.specificationModel,
                                                    units: item.data.units,
                                                    brand: item.data.brand,
                                                    size: item.data.size,
                                                    material: item.data.material
                                                };
                                                component.up('form').getForm().setValues(formValues);
                                            }
                                        })
                                    }
                                }
                            }
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'combo',
                            fieldLabel: '<font style="color: red">*</font>站点',
                            anchor: '90%',
                            value: 'all',
                            rawValue: '--双击选择--',
                            emptyText: '--双击选择--',
                            queryMode: 'local',
                            name: 'stationId',
                            editable: false,
                            displayField: 'name',
                            valueField: 'code',
                            allowBlank: false,
                            blankText: '请选择站点!',
                            listeners: {
                                beforerender:function( component, eOpts){
                                    if(component.getValue()){
                                        component.setRawValue(component.up('form').getForm().getValues().stationName);
                                    }
                                },
                                expand: function (cb, e, opt) {
                                    var w = Ext.create('App.addMaintenanceApply.addMaintenanceApplyStationWindow', {component: cb});
                                    w.show();
                                },
                                // change: function (component) {
                                //     component.up('form').getForm().setValues({equipmentId: ''})
                                // }
                            }
                        },
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'equipmentName',
                            fieldLabel: '<font style="color: red">*</font>设备名称',
                            maxLength: 64,
                            allowBlank: false
                        },
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'equipmentNo',
                            fieldLabel: '<font style="color: red">*</font>设备编号',
                            maxLength: 64,
                            allowBlank: false
                        },
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'qrCode',
                            fieldLabel: '二维码',
                            maxLength: 64
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'serialNumber',
                            fieldLabel: '序列号',
                            maxLength: 64
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'location',
                            fieldLabel: '所在位置',
                            maxLength: 128
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'radiogroup',
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
                                    inputValue: 'start_using'
                                },
                                {
                                    boxLabel: '不可用',
                                    name: 'status',
                                    inputValue: 'stop_using'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'textareafield',
                            name: 'remark',
                            fieldLabel: '备注',
                            maxLength: 250
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'materiaCoding',
                            fieldLabel: '物资编码'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'units',
                            fieldLabel: '单位'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'brand',
                            fieldLabel: '品牌'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'specificationModel',
                            fieldLabel: '规格型号'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'size',
                            fieldLabel: '尺寸'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'material',
                            fieldLabel: '材质'
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.operationsEquipment.operationsEquipmentWindow', {
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
    // autoScroll:true,
    // bodyStyle: {
    //     'overflow-y': 'auto',
    //     'overflow-x': 'hidden'
    // },
    buttonAlign: "center",
    xtype: 'operationsEquipmentWindow',
    controller: 'operationsEquipmentController',
    items: [
        {
            xtype: 'operationsEquipmentAddForm'
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