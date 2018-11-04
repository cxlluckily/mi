Ext.define('App.shelf.shelfModifyForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 600,
    xtype: 'shelfModifyForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 90,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [{
        xtype: 'container',
        layout: 'column',
        items: [
            {
                xtype: 'textfield',
                name: 'goodsShelvesId',
                hidden:true
            },
            {
                xtype: 'combo',
                emptyText: '所属仓库',
                queryMode: 'local',
                name: 'warehouseId',
                editable: false,
                readOnly: true,
                store: {
                    xclass: 'App.common.globalWarehouseListStore'
                },
                displayField: 'name',
                valueField: 'id',
                fieldLabel: '所属仓库',
                listeners: {
                    render: function (combo, name) { //渲染
                        var textID = combo.value;
                        var IDArr = combo.getStore().getData().items;
                        combo.getStore().on("load", function (s, r, o) {
                            for (var i = 0, LL = IDArr.length; i < LL; i++) {
                                if (IDArr[i].id == textID) {
                                    var firstValue = combo.getStore().getData().items[i].id;
                                    combo.setValue(firstValue);
                                    return;
                                }
                            }
                        });
                    },
                    beforerender: function () {
                        var extWindow = this.up('window');
                        // if (extWindow.viewAction == 'update') {
                        this.readOnly = true;
                        this.fieldStyle='background:#ddd';
                        // }
                    }
                }
                // xtype: 'textfield',
                // name: 'warehouseId',
                // fieldLabel: '所属仓库',
                // labelAlign: 'right',
                // autoHeight: true,
                // allowBlank: false,
                // blankText: '请输入所属仓库!',
            },
            {
                xtype: 'textfield',
                name: 'houseNo',
                fieldLabel: '房间号',
                labelAlign: 'right',
                autoHeight: true,
                allowBlank: false,
                blankText: '请输入房间号!',
                maxLength: 16,
                maxLengthText:'允许输入16个字',
                readOnly: true,
                fieldStyle:'background:#ddd',
                // beforerender: function () {
                //     var extWindow = this.up('window');
                //     // if (extWindow.viewAction == 'update') {
                //     this.readOnly = true;
                //     this.fieldStyle='background:#ddd';
                //     // }
                // }
            },
            {
                xtype: 'combo',
                fieldLabel: '类型',
                queryMode: 'local',
                name: 'containerType',
                editable: false,
                readOnly: true,
                store: {
                    data: [{
                        id: "Shelf",
                        name: "货架"
                    }, {
                        id: "Container",
                        name: "货柜"
                    }]
                },
                displayField: 'name',
                valueField: 'id',
                listeners: {
                    beforerender: function (combo) { //渲染
                        // combo.setValue(combo.name);
                        var extWindow = this.up('window');
                        this.readOnly = true;
                        this.fieldStyle='background:#ddd';
                    }
                }
            },
            {
                xtype: 'textfield',
                name: 'shelfNumber',
                fieldLabel: '货架/货柜编号',
                labelAlign: 'right',
                autoHeight: true,
                blankText: '请输入货架/货柜编号!',
                readOnly: true,
                fieldStyle:'background:#ddd'
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '状态',
                defaultType: 'radiofield',
                id: 'shelfStatusRadioBox',
                defaults: {
                    width: 100,
                    padding: '0 10'
                },
                layout: 'hbox',
                items: [{
                        boxLabel: '可用',
                        name: 'status',
                        inputValue: 'start_using',
                        id: 'shelfStatusRadioOne'
                    },
                    {
                        boxLabel: '不可用',
                        name: 'status',
                        inputValue: 'stop_using',
                        id: 'shelfStatusRadioTwo'
                    }
                ]
            }, {
                xtype: 'textareafield',
                name: 'remark',
                fieldLabel: '备注',
                labelAlign: 'right',
                autoHeight: true,
                maxLength: 200,
                maxLengthText:'最多允许输入200个字'
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


Ext.define('App.shelf.shelfModifyWindow', {
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
    xtype: 'shelfModifyWindow',
    controller: 'shelfController',
    items: [{
        xtype: 'shelfModifyForm'
    }]
});