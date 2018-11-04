Ext.define('App.maintenanceApplyEvaluate.maintenanceApplyEvaluateController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.maintenanceApplyEvaluateController',
    refViews: {
        window: 'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateWindow',
        lookWindow: 'App.maintenanceApplyEvaluate.maintenanceApplyEvaluateLookWindow',
        view: 'maintenanceApplyEvaluateView',
        detailUrl: 'repairApply/getApplyInfo.do',
        saveUrl: 'repairApply/repairEvaluation.do'
    },
    control: {
        'maintenanceApplyEvaluateView button[action="search"]': {
            click: 'clickSearch'
        },
        'maintenanceApplyEvaluateView button[action="resets"]': {
            click: 'clickResets'
        },
        'maintenanceApplyEvaluateView button[action="update"]': {
            click: 'clickUpdate'
        },
        'maintenanceApplyEvaluateView button[action="look"]': {
            click: 'clickLook'
        },
        'maintenanceApplyEvaluateWindow button[action="save"]': {
            click: 'clickSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'evaluate') {
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
                        var extWindow = Ext.create(me.refViews.window, {title: item.tooltip, viewAction: item.flag, iconCls: item.iconCls});
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
                        }
                        if (result.data.applyStatus == 'repaired' || result.data.applyStatus == 'noRepair' || result.data.applyStatus == 'rated') {
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
                            Ext.each(result.data.solution,function (item,index) {
                                if (item.checked) {
                                    if (solution != '') {
                                        solution += '<br>';
                                    }
                                    solution += item.processMode;
                                }
                            });
                            result.data.solutions = solution;
                        }

                        var repairImages = [];
                        Ext.each(result.data.images,function (item,index) {
                            if(item.type == 'repair'){
                                repairImages.push(item);
                            }
                        });
                        var repairImagesPanel = extWindow.down('commonImagesLook[imgType="repair"]');
                        repairImagesPanel.down('dataview').getStore().setData(repairImages);
                        var reportImages = [];
                        Ext.each(result.data.images,function (item,index) {
                            if(item.type == 'reported'){
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
        else if (flag == 'look') {
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
        else if (flag == 'delete') {
            grid.getStore().remove(record);
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
    clickUpdate: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行派单！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            // maintenanceApplyEvaluateId: record.data.maintenanceApplyEvaluateId
            outStockApplyId: 60
        };
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.window, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
                    var form = extWindow.down('form');
                    var logGrid = extWindow.down('maintenanceApplyEvaluateGrid');
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
                    }
                    if (result.data.applyStatus == 'repaired' || result.data.applyStatus == 'noRepair' || result.data.applyStatus == 'rated') {
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
                        Ext.each(result.data.solution,function (item,index) {
                            if (solution != '') {
                                solution += '<br>';
                            }
                            solution += item.processMode;
                        });
                        result.data.solutions = solution;
                    }
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
    },
    clickLook: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行查看！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            // maintenanceApplyEvaluateId: record.data.maintenanceApplyEvaluateId
            outStockApplyId: 60
        };
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.lookWindow, {title: btn.text, viewAction: btn.action, iconCls: btn.iconCls});
                    var form = extWindow.down('form');
                    form.getForm().setValues(result.data.applyEntity);
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
    },
    clickSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('maintenanceApplyEvaluateForm');
        if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '提示',
                message: '请填写选择必选项！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var data = form.getValues();
        var url = me.refViews.saveUrl;
        var config = {
            btn:btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '评价成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    form.up('window').close();
                    me.clickSearch();
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
    },
    clickSelectSave: function (btn) {
        var selectGridData = btn.up('window').down('maintenanceApplyEvaluateSelectGridSelect').getStore().getData().items;
        var newOutGridData = [];
        Ext.each(selectGridData, function (item, index) {
            newOutGridData.push(item.data);
        });
        // var formValues = Ext.ComponentQuery.query("maintenanceApplyEvaluateWindow")[0].down('form').getForm().getValues();
        var copyToUserId = [];
        Ext.each(newOutGridData, function (item, index) {
            copyToUserId.push(item.userId);
        });
        Ext.ComponentQuery.query("maintenanceApplyEvaluateWindow")[0].down('form').getForm().setValues({copyToUserId:copyToUserId});
    }
});