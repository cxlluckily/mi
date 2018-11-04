Ext.define('App.common.importForm', {
    extend: 'Ext.form.Panel',
    fullscreen: true,
    layout: 'column',
    buttonAlign: 'center',
    width: 800,
    heigth:500,
    xtype: 'importForm',
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
            items: [{
                xtype: 'textfield',
                name: 'importfileUrl',
               
                fieldLabel: '上传文件',
                labelAlign: 'right',
                autoHeight: true,
                hidden: true
            },
                {
                    xtype: 'filefield',
                    fieldLabel: '文件导入',
                    width:450,
                    name: 'importfileUrl',
                    buttonText: '选择文件',
                    listeners: {
                        change: function () {
                            var fileDom = this.getEl().down('input[type=file]');
                            var furl = fileDom.getValue();
                            var furlarr= furl.split('.');
                            var type=furlarr[furlarr.length-1].toLowerCase();
                            var files = fileDom.dom.files;
                            var str = '';
                            if (files.length > 1)
                            {
                                Ext.Msg.show({
                                    title: '警告',
                                    message: '最多允许上传1文件，请重新选择文件！',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                                return;
                            }
                           
                            //if (type != 'xls' && type != 'xlsx')
                            if (type != 'xls')
                            {
                                Ext.Msg.show({
                                    title: '提示',
                                   // message: '仅支持xls、xlsx格式的文件',
                                    message: '仅支持xls格式的文件',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                            }
                            else
                            {
                                for (var i = 0; i < files.length; i++)
                                {
                                    var picType = files[i].name.toLowerCase();
                                    str += picType;
                                    str += ' ';
                                }
                                this.setRawValue(str);
                                this.multipleFn(this);
                            }
                        },
                        afterrender: function () {
                            this.multipleFn(this);
                        }
                    },
                    multipleFn: function ($this) {
                        
                        // var typeArray = ["image/*"];
                        // var fileDom = $this.getEl().down('input[type=file]');
                        // fileDom.dom.setAttribute("multiple", "multiple");
                        // fileDom.dom.setAttribute("accept", typeArray.join(","));
                    }
                    
                },
                {
                    xtype: 'displayfield',
                    name: 'message',
                    fieldLabel: '错误提示',
                    labelAlign: 'right',
                    autoHeight: true,
                    hidden:true
                }
            ]
        }]
});


Ext.define('App.common.importWindow', {
    extend: 'Ext.window.Window',
    title: '{title}',
    width: 500,
    plain: false,
    iconCls: '{iconCls}',
    myData:'{myData}',
    resizable: true,
    draggable: true,
    collapsible: true,
    closeAction: 'destroy',
    closable: true,
    modal: true,
    autoRender: true,
    buttonAlign: "center",
    xtype: 'importWindow',
    // controller: 'importController',
    controller: {
        xclass:'App.common.importController'
    },
    requires:[
        'App.common.importController'
    ],
    items: [
        {
            xtype: 'importForm'
        }
    ],
    buttons: [
        {
            text: '下载导入模板',
            action: 'downUploadfile'
        },
        {
            text: '导入',
            action: 'importSave'
        }, {
            text: '取消',
            handler: function (a) {
                this.up('window').close();
            }
        }
    ]
});