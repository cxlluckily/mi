Ext.define('App.addMaintenanceApply.addMaintenanceApplyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.addMaintenanceApplyController',
    refViews: {
        window: 'App.addMaintenanceApply.addMaintenanceApplyWindow',
        view: 'addMaintenanceApplyView',
        uploadUrl: 'repairApply/addRepairApplyInfo.do',
        detailUrl: 'repairApply/getApplyInfo.do'
    },
    control: {
        'addMaintenanceApplyView button[action="search"]': {
            click: 'clickSearch'
        },
        'addMaintenanceApplyView button[action="resets"]': {
            click: 'clickResets'
        },
        'addMaintenanceApplyView button[action="add"]': {
            click: 'clickAdd'
        },
        'addMaintenanceApplyWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'look') {
            var url = me.refViews.detailUrl;
            var data = {
                maintenanceApplyId: record.data.maintenanceApplyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var eWindow = '';
                        switch (result.data.applyStatus) {
                            case 'toBeDispatch':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookOneWindow';
                                break;
                            case 'toBeRepair':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookThreeWindow';
                                break;
                            case 'maintenance':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookThreeWindow';
                                break;
                            case 'repaired':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookThreeWindow';
                                break;
                            case 'noRepair':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookThreeWindow';
                                break;
                            case 'rated':
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookFourWindow';
                                break;
                        }
                        var extWindow = Ext.create(eWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form');
                        var logGrid = extWindow.down('maintenanceApplyLogGrid');
                        var errors = '';
                        Ext.each(result.data.reportErrorPhenomenon, function (item, index) {
                            if (item.checked) {
                                if (errors != '') {
                                    errors += ',';
                                }
                                errors += item.breakAbbreviated;
                            }
                        });
                        result.data.errors = errors;
                        if (result.data.applyStatus !== 'toBeDispatch') {
                            var copyInfo = '';
                            if (result.data.copyInfo.length > 0) {
                                Ext.each(result.data.copyInfo, function (item, index) {
                                    if (index != 0) {
                                        copyInfo += ','
                                    }
                                    copyInfo += item.copyToUser;
                                })
                            }
                            result.data.copyInfo = copyInfo;

                            // 维修状况
                            var changeGrid = extWindow.down('maintenanceApplyChangeGrid');
                            changeGrid.getStore().setData(result.data.changeInfo);
                            var supplyErrors = '';
                            Ext.each(result.data.repairErrorPhenomenon, function (item, index) {
                                if (item.checked) {
                                    if (supplyErrors != '') {
                                        supplyErrors += ',';
                                    }
                                    supplyErrors += item.breakAbbreviated;
                                }
                            });
                            result.data.supplyErrors = supplyErrors;
                            var solution = '';
                            Ext.each(result.data.solution, function (item, index) {
                                if (item.checked) {
                                    if (solution != '') {
                                        solution += '<br>';
                                    }
                                    solution += item.processMode;
                                }
                            });
                            result.data.solutions = solution;
                            var repairImages = [];
                            Ext.each(result.data.images, function (item, index) {
                                if (item.type == 'repair') {
                                    repairImages.push(item);
                                }
                            });
                            var repairImagesPanel = extWindow.down('commonImagesLook[imgType="repair"]');
                            repairImagesPanel.down('dataview').getStore().setData(repairImages);
                        }
                        // if (result.data.applyStatus == 'repaired' || result.data.applyStatus == 'noRepair' || result.data.applyStatus == 'rated') {
                        // }
                        var reportImages = [];
                        Ext.each(result.data.images, function (item, index) {
                            if (item.type == 'reported') {
                                reportImages.push(item);
                            }
                        });
                        var reportImagesPanel = extWindow.down('commonImagesLook[imgType="report"]');
                        reportImagesPanel.down('dataview').getStore().setData(reportImages);
                        form.getForm().setValues(result.data);
                        logGrid.getStore().setData(result.data.logs);
                        extWindow.show();
                    } else {
                        Ext.Msg.show({
                            title: '警告',
                            message: result.message,
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                },

                failure: function (response, opts) {
                    console.log('server-side failure with status code '
                        + response.status);
                    Ext.Msg.show({
                        title: '错误',
                        message: response,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.error
                    });
                }
            };
            App.common.Ajax.request(config);
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(btn){
        btn.up('form').getForm().reset();
        this.clickSearch();
    },
    clickAdd: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
        extWindow.show();
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form').getForm();
        var form_up = btn.up('window').down('form');
        if (!form_up.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            // Ext.Msg.show({
            //     title: '警告',
            //     message: '请填写必填项并确认输入格式正确！',
            //     buttons: Ext.Msg.OK,
            //     icon: Ext.Msg.WARNING
            // });
            return;
        }
        var params = form.getValues();
        if(!Array.isArray(form.getValues().errors)){
            if(!form.getValues().errors){
                var newErrors = [];
            }else{
                var newErrors = [form.getValues().errors];
            }
            params.errors = newErrors;
        }
        params = JSON.stringify(params);
        Ext.Msg.wait("", "提示信息", {text: "程序正在上传，请稍候...."});
        var url = ctx + this.refViews.uploadUrl;
        form.submit({
            url: url,
            params: {
                formData:params
            },
            success: function (responseData, action) {
            },
            failure: function (response, opts) {
                Ext.Msg.hide();
                var responseData = opts.result;
                if (responseData.result == 'success') {
                    Ext.Msg.show({
                        title: '提示',
                        message: '报修成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    me.clickSearch();
                    btn.up('window').close();
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: responseData.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
            }
        });
    }
});