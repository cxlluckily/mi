Ext.define('App.partSparePart.partForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: '99%',
    xtype: 'partForm',
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
            columnWidth: .99,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'partName',
                            fieldLabel: '<font color="red">*</font>备件名称',
                            labelAlign: 'right',
                            autoHeight: true,
                            allowBlank: false,
                            blankText: '请输入备件名称!',
                            maxLength: 60,
                            maxLengthText: '允许输入60个字',
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update') {
                                        this.fieldLabel = '备件名称';
                                        this.editable = false;
                                        this.fieldStyle = 'background:#ddd';
                                    }
                                }
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .99,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'operationSubjectId',
                            fieldLabel: 'operationSubjectId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartId',
                            fieldLabel: 'sparePartId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartTypeId',
                            fieldLabel: 'sparePartTypeId',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            name: 'sparePartPid',
                            fieldLabel: 'sparePartPid',
                            labelAlign: 'right',
                            autoHeight: true,
                            hidden: true
                        },

                        {
                            xtype: 'textfield',
                            name: 'brand',
                            fieldLabel: '品牌',
                            labelAlign: 'right',
                            autoHeight: true,
                            // allowBlank: false,
                            blankText: '请输入品牌!',
                            maxLength: 16,
                            maxLengthText: '允许输入16个字',
                            // listeners: {
                            //     beforerender: function () {
                            //         var extWindow = this.up('window');
                            //         if (extWindow.viewAction == 'update') {
                            //             this.fieldLabel = '品牌';
                            //             this.editable = false;
                            //             this.fieldStyle = 'background:#ddd';
                            //         }
                            //     }
                            // }
                        },
                        {
                            xtype: 'textfield',
                            name: 'specificationModel',
                            fieldLabel: '规格型号',
                            labelAlign: 'right',
                            autoHeight: true,
                            // allowBlank: false,
                            blankText: '请输入规格型号!',
                            maxLength: 60,
                            maxLengthText: '允许输入60个字',
                            // listeners: {
                            //     beforerender: function () {
                            //         var extWindow = this.up('window');
                            //         if (extWindow.viewAction == 'update') {
                            //             this.fieldLabel = '规格型号';
                            //             this.editable = false;
                            //             this.fieldStyle = 'background:#ddd';
                            //         }
                            //     }
                            // }
                        },
                        {
                            xtype: 'textfield',
                            name: 'material',
                            fieldLabel: '材质',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16
                        },
                        {
                            xtype: 'textfield',
                            name: 'upperLimit',
                            fieldLabel: '库存上限',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16,
                            maxLengthText: '允许输入16个字',
                            regex: /^[0-9]+$/,
                            regexText: '只能输入数字'
                        },
                        {
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
                                    inputValue: 'start_using'
                                },
                                {
                                    boxLabel: '不可用',
                                    name: 'status',
                                    inputValue: 'stop_using'
                                }
                            ],
                            listeners: {
                                beforerender: function () {
                                    var extWindow = this.up('window');
                                    if (extWindow.viewAction == 'update') {
                                        this.readOnly = true;
                                        this.fieldStyle = 'background:#ddd';
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
                            xtype: 'textfield',
                            name: 'materiaCoding',
                            fieldLabel: '物资编码',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 60,
                            maxLengthText: '允许输入60个字',
                        },
                        {
                            xtype: 'textfield',
                            name: 'size',
                            fieldLabel: '尺寸',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16,
                            maxLengthText: '允许输入16个字',
                        },
                        {
                            xtype: 'textfield',
                            name: 'units',
                            fieldLabel: '单位',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16,
                            maxLengthText: '允许输入16个字',
                        },
                        {
                            xtype: 'textfield',
                            name: 'lowerLimit',
                            fieldLabel: '库存下限',
                            labelAlign: 'right',
                            autoHeight: true,
                            maxLength: 16,
                            maxLengthText: '允许输入16个字',
                            regex: /^[0-9]+$/,
                            regexText: '只能输入数字'
                        },


                    ]
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .99,
            items: [
                {
                    xtype: 'textareafield',
                    name: 'remark',
                    fieldLabel: '备注',
                    labelAlign: 'right',
                    autoHeight: true,
                    maxLength: 200,
                    maxLengthText: '允许输入200个字',
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .99,
            items: [
                {
                    xtype: 'container',
                    columnWidth: .5,
                    items: [
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '是否保存头像',
                            defaultType: 'checkbox',
                            hidden: true,
                            defaults: {
                                width: 100,
                                padding: '0 10'
                            },
                            layout: 'hbox',
                            items: [
                                {
                                    boxLabel: '是',
                                    name: 'isHavePic',
                                    inputValue: true
                                }
                            ]
                        },
                        {
                            xtype: 'filefield',
                            fieldLabel: '图片',
                            name: 'photoUrl',
                            buttonText: '选择文件',
                            listeners: {
                                change: function (component) {
                                    var fileDom = component.getEl().down('input[type=file]');
                                    var files = fileDom.dom.files;
                                    var str = '';
                                    // if (files.length > 3) {
                                    //     Ext.Msg.show({
                                    //         title: '警告',
                                    //         message: '最多允许上传3张图片，请重新选择图片！',
                                    //         buttons: Ext.Msg.OK,
                                    //         icon: Ext.Msg.WARNING
                                    //     });
                                    //     component.up('form').getForm().setValues({isHavePic: false});
                                    //     component.setRawValue('');
                                    //     component.multipleFn(component);
                                    //     return;
                                    // }
                                    for (var i = 0; i < files.length; i++) {
                                        var picType = files[i].name.toLowerCase();
                                        str += picType;
                                        str += ' ';
                                    }
                                    var typeCheck = true;
                                    Ext.each(files, function (item) {
                                        var names = item.name.split('.');
                                        var type = names[names.length - 1];
                                        if (type != 'jpg' && type != 'jpeg' && type != 'png') {
                                            typeCheck = false;
                                        }
                                    });
                                    if (!typeCheck) {
                                        Ext.Msg.show({
                                            title: '提示',
                                            message: '仅支持jpg、jpeg、png格式的图片',
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.WARNING
                                        });
                                        component.up('form').getForm().setValues({isHavePic: false});
                                        component.setRawValue('');
                                        component.multipleFn(component);
                                        return;
                                    }
                                    if (files.length == 0) {
                                        component.up('form').getForm().setValues({isHavePic: false});
                                    }else{
                                        component.up('form').getForm().setValues({isHavePic: true});
                                    }
                                    component.setRawValue(str);
                                    component.multipleFn(component);
                                },
                                afterrender: function (component) {
                                    component.multipleFn(component);
                                }
                            },
                            multipleFn: function ($this) {
                                // var typeArray = ["application/x-shockwave-flash", "audio/MP3", "image/*", "flv-application/octet-stream"];
                                var typeArray = ["image/*"];
                                var fileDom = $this.getEl().down('input[type=file]');
                                fileDom.dom.setAttribute("multiple", "multiple");
                                fileDom.dom.setAttribute("accept", typeArray.join(","));
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'container',
            layout: 'column',
            columnWidth: .99,
            items: [
                {
                    xtype: 'container',
                    columnWidth: 1,
                    items: [
                        {
                            xtype: 'commonImagesLook',
                            title: '备件图片'
                        }
                        // {
                        //     xtype: 'combo',
                        //     name: 'imageUrls',
                        //     fieldLabel: 'partSparePartImageEntities',
                        //     labelAlign: 'right',
                        //     autoHeight: true,
                        //     hidden: true
                        // },
                        // {
                        //     xtype: 'combo',
                        //     name: 'partSparePartImageEntities',
                        //     fieldLabel: 'partSparePartImageEntities',
                        //     labelAlign: 'right',
                        //     autoHeight: true,
                        //     hidden: true
                        // },
                        // {
                        //     xtype: 'gridpanel',
                        //     name: 'partSparePartImageEntities',
                        //     border: 1,
                        //     store: {
                        //         extend: 'Ext.data.Store'
                        //     },
                        //     reserveScrollbar: true,
                        //     autoScroll: true,
                        //     height: 200,
                        //     bodyStyle: {
                        //         'overflow-y': 'auto',
                        //         'overflow-x': 'hidden'
                        //     },
                        //     listeners: {
                        //         beforerender: function (combo) {
                        //             var partSparePartImageEntities = this.up('form').getValues().partSparePartImageEntities;
                        //             if (partSparePartImageEntities.length == 0) {
                        //                 this.hidden = true;
                        //             }
                        //             combo.getStore().setData(partSparePartImageEntities);
                        //         }
                        //     },
                        //     columns:
                        //         [
                        //             {
                        //                 text: '图片',
                        //                 dataIndex: 'fullPhotoUrl',
                        //                 // width: 150,
                        //                 flex: 1,
                        //                 align: 'center',
                        //                 // hidden: true,
                        //                 renderer: function (value, metaData, record) {
                        //                     var img = '<img src=' + value + ' width="70" />';
                        //                     // var img = '<img src="app/resources/images/userPhoto.png" height="70" />';
                        //                     return img;
                        //                 }
                        //             },
                        //             {
                        //                 text: 'sparePartId',
                        //                 dataIndex: 'sparePartId',
                        //                 width: 150,
                        //                 hidden: true
                        //             },
                        //             {
                        //                 text: 'sparePartImageId',
                        //                 dataIndex: 'sparePartImageId',
                        //                 width: 150,
                        //                 hidden: true
                        //             },
                        //             {
                        //                 xtype: 'actioncolumn',
                        //                 text: '操作',
                        //                 width: 150,
                        //                 // flex:1,
                        //                 menuDisabled: true,
                        //                 // tooltip: 'Edit task',
                        //                 align: 'center',
                        //                 // iconCls: 'tree-grid-edit-task',
                        //                 handler: 'onEditRowAction',
                        //                 isDisabled: 'isRowEditDisabled',
                        //                 fieldDefaults: {
                        //                     style: {
                        //                         margin: "20px 10px"
                        //                     }
                        //                 },
                        //                 defaults: {
                        //                     margin: "0px 5px"
                        //                 },
                        //                 items: [
                        //                     {
                        //                         tooltip: '删除',
                        //                         iconCls: "fa-trash",
                        //                         flag: 'deleteImg',
                        //                         handler: 'deleteImg'
                        //                     }
                        //                 ]
                        //             }
                        //         ]
                        // }
                    ]
                }
            ]
        }
    ]
});

Ext.define('App.partSparePart.partWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 800,
    height: 600,
    // autoScroll:true,
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
    xtype: 'partWindow',
    controller: 'partController',
    bodyStyle: {
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
    },
    items: [
        {
            xtype: 'partForm'
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