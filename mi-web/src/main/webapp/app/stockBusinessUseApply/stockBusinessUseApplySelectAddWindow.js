/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

Ext.define('App.stockBusinessUseApply.stockBusinessUseApplySelectAddForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '100%',
    xtype: 'stockBusinessUseApplySelectAddForm',
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
                    columnWidth: 1,
                    layout: 'form',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'specificationModel',
                            fieldLabel: 'specificationModel',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'materiaCoding',
                            fieldLabel: 'materiaCoding',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'remark',
                            fieldLabel: 'remark',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'units',
                            fieldLabel: 'units',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'lowerLimit',
                            fieldLabel: 'lowerLimit',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'categoryName',
                            fieldLabel: 'categoryName',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'partName',
                            fieldLabel: 'partName',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'modifyUser',
                            fieldLabel: 'modifyUser',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartId',
                            fieldLabel: 'sparePartId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'modifyTime',
                            fieldLabel: 'modifyTime',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'size',
                            fieldLabel: 'size',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'material',
                            fieldLabel: 'material',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'createTime',
                            fieldLabel: 'createTime',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartPid',
                            fieldLabel: 'sparePartPid',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'upperLimit',
                            fieldLabel: 'upperLimit',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'createUser',
                            fieldLabel: 'createUser',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartTypeId',
                            fieldLabel: 'sparePartTypeId',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'brand',
                            fieldLabel: 'brand',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'status',
                            fieldLabel: 'status',
                            hidden: true
                        },
                        {
                            xtype: 'numberfield',
                            name: 'applyCount',
                            fieldLabel: '申请数量',
                            minValue:1,
                        }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.stockBusinessUseApply.stockBusinessUseApplySelectAddWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 300,
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
    xtype: 'stockBusinessUseApplySelectAddWindow',
    controller: 'stockBusinessUseApplyController',
    items: [
        {
            xtype: 'stockBusinessUseApplySelectAddForm'
        }
    ],
    buttons: [
        {
            text: '确认',
            action: 'save'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});