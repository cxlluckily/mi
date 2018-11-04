Ext.define('App.common.importController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.importController',
    refViews: {
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow'
    },
    control: {
        'importWindow button[action="importSave"]': {
            click: 'clickImportSave'
        },
        'importWindow button[action="downUploadfile"]': {
            click: 'clickdownUploadfile'
        }
    },
    //下载模板
    clickdownUploadfile: function (btn) {
        var myData = btn.up('window').myData;
        var form = btn.up('window').down('form').getForm();
        form.submit({
            url: myData.downUrl,
            params: {
                userKey: window.sessionStorage['userKey'],
                filename: myData.filename
            },
            success: function (a, action) {
            
            },
            failure: function (response, opts) {
            
                
            }
        });
        
    },
    //导入模板信息
    clickImportSave: function (btn) {
        var myData = btn.up('window').myData
        var me = this;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        
        if (!form_up.getForm().isValid())
        { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var fileDom = form_up.down('filefield').getEl().down('input[type=file]');
        var files = fileDom.dom.files;
        var furl = fileDom.getValue();
        var furlarr = furl.split('.');
        var type = furlarr[furlarr.length - 1].toLowerCase();
        if (files.length > 1)
        {
            Ext.Msg.show({
                title: '警告',
                message: '只允许上传1个文件，请重新选择文件！',
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
                //message: '仅支持xls、xlsx格式的文件',
                message: '仅支持xls格式的文件',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        
        Ext.Msg.wait("", "提示信息", {text: "程序正在上传，请稍候...."});
        form.submit({
            url: myData.url,
            params: {
                userKey: window.sessionStorage['userKey']
            },
            success: function (a, action) {
            
            },
            failure: function (response, opts) {
                Ext.Msg.hide();
                var responseData = opts.result;
                if (responseData.result == 'failure')
                {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
                else if (responseData.result == 'success' && responseData.data == 'xianluchezhandaoru' )
                {
                    Ext.Msg.show({
                        title: '提示',
                        message: '导入成功，请选择线路查看车站信息！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    if(myData.callbackComponent)
                    {
                        myData.callbackComponent.clickResets();
                    }
                    btn.up('window').close();
                }
                else if (responseData.result == 'success')
                {
                    Ext.Msg.show({
                        title: '提示',
                        message: '导入成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    if(myData.callbackComponent)
                    {
                        myData.callbackComponent.clickResets();
                    }
                    btn.up('window').close();
                }
                else
                {
                    var extWindow = Ext.create(me.refViews.messageWindow, {title: responseData.message});
                    extWindow.down('grid').getStore().setData(responseData.data);
                    extWindow.show();
                    
                    // Ext.Msg.show({
                    //     title: '警告',
                    //     message: responseData.message,
                    //     buttons: Ext.Msg.OK,
                    //     icon: Ext.Msg.WARNING
                    // });
                }
            }
        });
        
    }
});