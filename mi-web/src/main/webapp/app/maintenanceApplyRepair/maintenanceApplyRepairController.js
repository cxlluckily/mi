Ext.define('App.maintenanceApplyRepair.maintenanceApplyRepairController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.maintenanceApplyRepairController',
    refViews: {
        window: 'App.maintenanceApplyRepair.maintenanceApplyRepairWindow',
        maintainWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairMaintainWindow',
        selectWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairSelectWindow',
        chooseWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairChooseWindow',
        changeWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairChangeWindow',
        lookWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairLookWindow',
        confirmWindow: 'App.maintenanceApplyRepair.maintenanceApplyRepairConfirmWindow',
        view: 'maintenanceApplyRepairView',
        detailUrl: 'repairApply/getApplyInfo.do',
        repairUrl: 'repairApply/repairCheckIn.do',
        chooseUrl: 'repairApply/getPartPairInInEquipment.do',
        changeUrl: 'repairApply/getSparePartsInBag.do',
        uploadUrl: 'repairApply/repairEquipment.do'
    },
    control: {
        'maintenanceApplyRepairView button[action="search"]': {
            click: 'clickSearch'
        },
        'maintenanceApplyRepairView button[action="resets"]': {
            click: 'clickResets'
        },
        'maintenanceApplyRepairWindow button[action="save"]': {
            click: 'clickRepairSave'
        },
        'maintenanceApplyRepairSelectWindow button[action="save"]': {
            click: 'clickSelectSave'
        },
        'maintenanceApplyRepairMaintainWindow button[action="choose"]': {
            click: 'clickChoose'
        },
        'maintenanceApplyRepairMaintainWindow button[action="save"]': {
            click: 'clickMaintainSave'
        },
        'maintenanceApplyRepairMeasureWindow button[action="save"]': {
            click: 'clickMeasureSave'
        },
        'maintenanceApplyRepairChooseWindow button[action="save"]': {
            click: 'clickChooseSave'
        },
        'maintenanceApplyRepairChangeWindow button[action="save"]': {
            click: 'clickChangeSave'
        },
        'maintenanceApplyRepairConfirmWindow button[action="save"]': {
            click: 'clickConfirmSave'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'repair') {
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
                        var extWindow = Ext.create(me.refViews.window, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form');
                        var logGrid = extWindow.down('maintenanceApplyRepairGrid');
                        var errors = '';
                        Ext.each(result.data.reportErrorPhenomenon, function (item, index) {
                            if (item.checked) {
                                if (errors != '') {
                                    errors += ',';
                                }
                                errors += item.breakAbbreviated;
                            }
                        });
                        //维修情况
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

                        result.data.addErrors = errors;
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
        else if (flag == 'maintain') {
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
                        var extWindow = Ext.create(me.refViews.maintainWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var form = extWindow.down('form');

                        var changeGrid = extWindow.down('maintenanceApplyRepairMaintainChangeGrid');
                        changeGrid.getStore().setData(result.data.changeInfo);

                        var errorsCheckGroup = extWindow.down('checkboxgroup[name="errorsCheckGroup"]');
                        var errorsCheckGroupItems = [];
                        Ext.each(result.data.repairErrorPhenomenon, function (item, index) {
                            var checkItem = {
                                boxLabel: item.breakAbbreviated,
                                inputValue: item.breakdownInfoId,
                                checked: item.checked
                            };
                            errorsCheckGroupItems.push(checkItem);
                        });
                        errorsCheckGroup.add(errorsCheckGroupItems);

                        var solutionsCheckGroup = extWindow.down('checkboxgroup[name="solutionsCheckGroup"]');
                        var solutionsCheckGroupItems = [];
                        Ext.each(result.data.solution, function (item, index) {
                            if (item.repairInfoId == 0) {
                                result.data.other = item.processMode;
                            } else {
                                var checkItem = {
                                    boxLabel: item.processMode,
                                    inputValue: item.repairInfoId,
                                    checked: item.checked
                                };
                                solutionsCheckGroupItems.push(checkItem);
                            }
                        });
                        solutionsCheckGroup.add(solutionsCheckGroupItems);

                        var logGrid = extWindow.down('maintenanceApplyRepairMaintainGrid');
                        var reportErrorGroup = extWindow.down('checkboxgroup[name="reportErrorsGroup"]');
                        var reportErrorGroupItems = [];
                        var errors = '';
                        Ext.each(result.data.reportErrorPhenomenon, function (item, index) {
                            if (item.checked) {
                                if (errors != '') {
                                    errors += ',';
                                }
                                errors += item.breakAbbreviated;
                            }
                            var checkItem = {
                                boxLabel: item.breakAbbreviated,
                                inputValue: item.breakdownInfoId,
                                checked: item.checked
                            };
                            reportErrorGroupItems.push(checkItem);
                        });
                        reportErrorGroup.add(reportErrorGroupItems);

                        result.data.addErrors = errors;
                        var reportImages = [];
                        Ext.each(result.data.images, function (item, index) {
                            if (item.type == 'reported') {
                                reportImages.push(item);
                            }
                        });
                        var reportImagesPanel = extWindow.down('commonImagesLook[imgType="report"]');
                        reportImagesPanel.down('dataview').getStore().setData(reportImages);
                        if (result.data.wasFinished == 'none') {
                            result.data.wasFinished = 'yes';
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
                                eWindow = 'App.maintenanceApply.maintenanceApplyLookOneWindow';
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
                        if (result.data.applyStatus !== 'toBeDispatch' && result.data.applyStatus !== 'toBeRepair') {
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
        else if (flag == 'change') {
            var deviceComposeId = record.data.deviceComposeId;
            var selectData = [];
            Ext.each(grid.dataSource.data.items, function (item) {
                if (item.data.deviceComposeId == deviceComposeId) {
                    selectData.push(item.data);
                }
            });
            var url = me.refViews.changeUrl;
            var data = {
                sparePartTypeId: record.data.oldSparePartId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.changeWindow, {
                            title: item.tooltip,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var grid = extWindow.down('grid');
                        extWindow.down('form').getForm().setValues({
                            oldSparePartId: record.data.oldSparePartId,
                            oldPartName: record.data.oldPartName
                        });
                        grid.getStore().setData(result.data);
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
            me.datachanged();
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
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
            // maintenanceApplyRepairId: record.data.maintenanceApplyRepairId
            outStockApplyId: 60
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.window, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
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

    clickMaintain: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行维修！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            // maintenanceApplyRepairId: record.data.maintenanceApplyRepairId
            outStockApplyId: 60
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.maintainWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
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
            // maintenanceApplyRepairId: record.data.maintenanceApplyRepairId
            outStockApplyId: 60
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.lookWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
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
    clickRepairSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('form');
        var data = {
            maintenanceApplyId: form.getValues().maintenanceApplyId
        };
        var url = me.refViews.repairUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '接修成功！',
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
        var selectGridData = btn.up('window').down('maintenanceApplyRepairSelectGridSelect').getStore().getData().items;
        var newOutGridData = [];
        Ext.each(selectGridData, function (item, index) {
            newOutGridData.push(item.data);
        });
        // var formValues = Ext.ComponentQuery.query("maintenanceApplyRepairWindow")[0].down('form').getForm().getValues();
        var copyToUserId = [];
        Ext.each(newOutGridData, function (item, index) {
            copyToUserId.push(item.userId);
        });
        Ext.ComponentQuery.query("maintenanceApplyRepairWindow")[0].down('form').getForm().setValues({copyToUserId: copyToUserId});
    },
    clickChoose: function (btn) {
        var me = this;
        var formValues = btn.up('maintenanceApplyRepairMaintainForm').getForm().getValues();
        var detailGrid = btn.up('grid');
        // 已选择
        var selectGridData = [];
        var detailGridData = detailGrid.getStore().getData().items;
        Ext.each(detailGridData, function (item, index) {
            selectGridData.push(item.data);
        });
        var url = me.refViews.changeUrl;
        var data = {};
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.changeWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var grid = extWindow.down('grid');
                    var allData = [];
                    Ext.each(result.data, function (item) {
                        var checkOk = true;
                        Ext.each(selectGridData, function (selectItem) {
                            if (selectItem.userDeviceId == item.userDeviceId) {
                                checkOk = false;
                            }
                        });
                        if (checkOk) {
                            item.replaceCount = item.count;
                            allData.push(item);
                        }
                    });
                    grid.getStore().setData(allData);
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
    clickChooseOld: function (btn) {
        var me = this;
        var formValues = btn.up('maintenanceApplyRepairMaintainForm').getForm().getValues();
        var detailGrid = btn.up('grid');
        var selectGridData = [];
        var detailGridData = detailGrid.getStore().getData().items;
        Ext.each(detailGridData, function (item, index) {
            selectGridData.push(item.data);
        });
        var checkData = [];
        Ext.each(selectGridData, function (selectItem, index) {
            var check = true;
            Ext.each(checkData, function (item, index) {
                if (selectItem.deviceComposeId == item.deviceComposeId) {
                    check = false;
                }
            });
            if (check) {
                checkData.push(selectItem);
            }
        });
        // return
        var extWindow = Ext.create(me.refViews.chooseWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var config = {
            btn: btn,
            url: ctx + this.refViews.chooseUrl,
            data: {
                equipmentId: formValues.equipmentId
            },
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj.result == "success") {
                    var root = {
                        expanded: true
                    };
                    root.children = me.recursionChoose(obj.data, checkData, 'part');
                    var p = extWindow.down('maintenanceApplyRepairChooseTree');
                    p.getStore().setRoot(root);
                    p.expandAll();
                    extWindow.show();
                } else if (obj.result == "failure") {
                    Ext.Msg.show({
                        title: '提示',
                        message: obj.message,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
            },
            failure: function (response) {
                console.log('server-side failure with status code ' + response.status);
            }
        };
        App.common.Ajax.request(config);
    },
    recursionChoose: function (data, checkedData, key) {
        var me = this;
        Ext.each(data, function (item) {
            // item.text = item[key];
            if (!key) {
                var children = 'children';
            } else {
                var children = key;
            }
            if (item[children] && item[children].length > 0) {
                me.recursionChoose(item[children], checkedData, children);
            } else {
                item.checked = false;
                item.leaf = true;
                Ext.each(checkedData, function (checkedDataItem, index) {
                    if (checkedDataItem.deviceComposeId == item.deviceComposeId) {
                        item.checked = true;
                    }
                });
            }
            item.children = item[children];
        });
        return data;
    },
    clickChooseSave: function (btn) {
        var tree = btn.up('window').down('maintenanceApplyRepairChooseTree');
        var treeChecked = tree.getChecked();
        if (treeChecked.length == 0) {
            Ext.Msg.show({
                title: '提示',
                message: '请至少选择一个备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var treeCheckedData = [];
        Ext.each(treeChecked, function (item, index) {
            var itemData = {
                oldSparePartId: item.data.sparePartId,
                oldSparePartTypeId: item.data.sparePartTypeId,
                oldPartName: item.data.partName,
                oldQrCode: item.data.qrCode,
                deviceComposeId: item.data.deviceComposeId
            };
            treeCheckedData.push(itemData);
        });
        var maintainWindow = Ext.ComponentQuery.query("maintenanceApplyRepairMaintainWindow")[0];
        var gridStore = maintainWindow.down('maintenanceApplyRepairMaintainChangeGrid').store;

        var gridItems = gridStore.getData().items;
        var gridData = [];
        Ext.each(gridItems, function (item) {
            gridData.push(item.data);
        });
        var insertData = [];
        Ext.each(treeCheckedData, function (item) {
            var check = true;
            Ext.each(gridData, function (gridItem) {
                if (item.deviceComposeId == gridItem.deviceComposeId) {
                    check = false;
                }
            });
            if (check) {
                insertData.push(item);
            }
        });
        var deleteData = [];
        Ext.each(gridData, function (item) {
            var check = true;
            Ext.each(treeCheckedData, function (gridItem) {
                if (item.deviceComposeId == gridItem.deviceComposeId) {
                    check = false;
                }
            });
            if (check) {
                gridStore.remove(item);
                // insertData.push(item);
            }
        });
        // Ext.each(insertData, function (item) {
        //     gridStore.insert(0, item);
        // });
        gridStore.insert(0, insertData);
        btn.up('window').close();
    },
    clickChangeSave: function (btn) {
        var me = this;
        var grid = btn.up('window').down('grid');
        var gridSelection = grid.getSelection();
        if (gridSelection.length == 0) {
            Ext.Msg.show({
                title: '提示',
                message: '请至少选择一个备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.INFO
            });
            return;
        }
        var getSelectionData = [];
        var checkIndex = null;
        var checkType = null;
        Ext.each(gridSelection, function (item, index) {
            Ext.apply(item.data, {
                newStockId: item.data.stockId,
                newSparePartId: item.data.sparePartId,
                newQrCode: item.data.qrCode,
                newPartName: item.data.partName,
                canDelete: true
            });
            getSelectionData.push(item.data);
            if (!checkIndex) {
                if (item.data.inventoryType == "unique") {
                    if (Number(item.data.replaceCount) != 1) {
                        checkIndex = index + 1;
                        checkType = 1;
                    }
                } else {
                    var checkOk = App.common.regExpValidator.natural.test(item.data.replaceCount);
                    if (!checkOk) {
                        checkIndex = index + 1;
                        checkType = 2;
                    }
                    if (item.data.replaceCount > item.data.count) {
                        checkIndex = index + 1;
                        checkType = 3;
                    }
                }
            }
        });
        if (checkIndex) {
            if (checkType == 1) {
                Ext.Msg.show({
                    title: '提示',
                    message: '已选择第' + checkIndex + '条数据为唯一标识管理，数量只能为1！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            } else if (checkType == 2) {
                Ext.Msg.show({
                    title: '提示',
                    message: '已选择第' + checkIndex + '条数据数量必须为大于0的整数！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            } else if (checkType == 3) {
                Ext.Msg.show({
                    title: '提示',
                    message: '已选择第' + checkIndex + '条数据替换数量不能大于背包中数量！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.INFO
                });
                return;
            }
        }
        var maintainWindow = Ext.ComponentQuery.query('maintenanceApplyRepairMaintainWindow')[0];
        var changeGridStore = maintainWindow.down('maintenanceApplyRepairMaintainChangeGrid').getStore();
        var count = changeGridStore.getCount();
        changeGridStore.insert(count, getSelectionData);
        me.datachanged();
        btn.up('window').close();
    },
    clickMaintainSave: function (btn) {
        var me = this;
        var form = btn.up('window').down('maintenanceApplyRepairMaintainForm').getForm();
        var formValues = form.getValues();
        var form_up = btn.up('window').down('form');
        var other = formValues.other.replace(App.common.regExpValidator.trim, "");
        if (!form_up.getForm().isValid()) { // 验证表单, 如果为空, 不让发送请求
            Ext.Msg.show({
                title: '警告',
                message: '请填写必填项并确认输入格式正确！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        // if (!form.getValues().errors || form.getValues().errors.length == 0) {
        //     Ext.Msg.show({
        //         title: '警告',
        //         message: '请选择故障现象！',
        //         buttons: Ext.Msg.OK,
        //         icon: Ext.Msg.WARNING
        //     });
        //     return;
        // }
        if ((!form.getValues().solutions || form.getValues().solutions.length == 0) && other.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择处理措施或填写其他措施！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var changeSparePartCheckOkIndex = null;
        var changeSparePartCheckOkType = null;
        var changeSparePartEntities = [];
        var changeGrid = btn.up('window').down('maintenanceApplyRepairMaintainChangeGrid').getStore().getData().items;
        Ext.each(changeGrid, function (item, index) {
            if (item.data.canDelete) {
                if (changeSparePartCheckOkIndex == null) {
                    if (!App.common.regExpValidator.natural.test(item.data.replaceCount) || item.data.replaceCount > item.data.count) {
                        changeSparePartCheckOkIndex = index + 1;
                        changeSparePartCheckOkType = 'count';
                    }
                }
                if (changeSparePartCheckOkIndex == null) {
                    if (item.data.newQrCode&&item.data.oldQrCode&&item.data.newQrCode == item.data.oldQrCode) {
                        changeSparePartCheckOkIndex = index + 1;
                        changeSparePartCheckOkType = 'qrCode';
                    }
                }
                changeSparePartEntities.push(item.data);
            }
        });
        if (changeSparePartCheckOkIndex != null && changeSparePartCheckOkType == 'count') {
            Ext.Msg.show({
                title: '警告',
                message: '第' + changeSparePartCheckOkIndex + '条更换备件更换数量必须为正整数且不能大于背包数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (changeSparePartCheckOkIndex != null && changeSparePartCheckOkType == 'qrCode') {
            Ext.Msg.show({
                title: '警告',
                message: '第' + changeSparePartCheckOkIndex + '条更换备件新旧二维码不能相同！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var checkboxgroup = form_up.down('checkboxgroup[name="solutionsCheckGroup"]');
        var solutionEntities = [];
        Ext.each(checkboxgroup.items.items, function (item) {
            if (item.checked) {
                var solution = {
                    repairInfoId: item.inputValue,
                    processMode: item.boxLabel
                };
                solutionEntities.push(solution);
            }
        });

        if (other.length > 0) {
            var solution = {
                repairInfoId: 0,
                processMode: other
            };
            solutionEntities.push(solution);
        }
        var newErrors = [];
        if (Array.isArray(formValues.errors)) {
            newErrors = formValues.errors
        } else if (formValues.errors == null) {
            newErrors = [];
        } else {
            newErrors = [formValues.errors]
        }
        var reportedErrors = [];
        if (Array.isArray(formValues.reportedErrors)) {
            reportedErrors = formValues.reportedErrors
        } else if (!formValues.reportedErrors) {
            reportedErrors = [];
        } else {
            reportedErrors = [formValues.reportedErrors]
        }
        var params = {
            errors: newErrors,
            reportedErrors: reportedErrors,
            solutionEntities: solutionEntities,
            changeSparePartEntities: changeSparePartEntities
        };
        Ext.apply(formValues, params);
        var formValuesString = JSON.stringify(formValues);
        me.upload(form, formValuesString);
    },
    clickConfirmSave: function (btn) {
        var formValues = btn.up('window').down('form').getForm().getValues();
        console.log(formValues);
    },
    upload: function (form, params) {
        var me = this;
        Ext.Msg.wait("", "提示信息", {text: "程序正在上传，请稍候...."});
        var url = ctx + me.refViews.uploadUrl;
        form.submit({
            url: url,
            params: {formData: params},
            success: function (responseData, action) {
            },
            failure: function (response, opts) {
                Ext.Msg.hide();
                var responseData = opts.result;
                if (responseData.result == 'success') {
                    Ext.Msg.show({
                        title: '提示',
                        message: '维修完成！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    me.clickSearch();
                    Ext.ComponentQuery.query("maintenanceApplyRepairMaintainWindow")[0].close();
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
    },
    clickMeasureSave: function (btn) {
        var grid = btn.up('window').down('grid');
        var form = btn.up('window').down('form');
        var formValues = form.getForm().getValues();
        var selection = grid.getSelection();
        var selectData = [];
        Ext.each(selection, function (item, index) {
            selectData.push(item.data.repairInfoId);
        });
        Ext.ComponentQuery.query("maintenanceApplyRepairMaintainWindow")[0].down('maintenanceApplyRepairMaintainForm').getForm().setValues({solutions: selectData});
        btn.up('window').close();
    },
    setSolutions: function () {
        var form = Ext.ComponentQuery.query('maintenanceApplyRepairMaintainForm')[0];
        var formValues = form.getForm().getValues();
        var errors = !formValues.errors ? [] : Array.isArray(formValues.errors) ? formValues.errors : [formValues.errors] || [];
        var reportedErrors = !formValues.reportedErrors ? [] : Array.isArray(formValues.reportedErrors) ? formValues.reportedErrors : [formValues.reportedErrors] || [];
        var errorsData = [];
        Ext.each(errors, function (item, index) {
            errorsData.push(item);
        });
        Ext.each(reportedErrors, function (reportItem, index) {
            var check = true;
            Ext.each(errors, function (item, index) {
                if (reportItem == item) {
                    check = false;
                }
            });
            if (check) {
                errorsData.push(reportItem);
            }
        });
        var solutionsCheckGroup = form.down('checkboxgroup[name="solutionsCheckGroup"]');
        if (errorsData.length == 0) {
            solutionsCheckGroup.removeAll();
            return;
        }
        var url = 'repairApply/getTreatmentInfoList.do';
        var data = {
            errors: errorsData,
            searchContent: ''
        };
        var config = {
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    solutionsCheckGroup.removeAll();
                    var items = [];
                    Ext.each(result.data, function (item) {
                        var checkItem = {
                            boxLabel: item.repairDescription,
                            inputValue: item.repairInfoId
                        };
                        items.push(checkItem);
                    });
                    solutionsCheckGroup.add(items);
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
    datachanged: function () {
        var me = this;
        var store = Ext.ComponentQuery.query('maintenanceApplyRepairMaintainChangeGrid')[0].getStore();
        var data = store.data.items;
        var sparePartIds = [];
        Ext.each(data, function (item) {
            if (sparePartIds.indexOf(item.data.sparePartId) == -1) {
                sparePartIds.push(item.data.sparePartId);
            }
        });
        var form = Ext.ComponentQuery.query('maintenanceApplyRepairMaintainForm')[0];
        var errorsCheckGroup = form.down('checkboxgroup[name="errorsCheckGroup"]');
        // var solutionsCheckGroup = form.down('checkboxgroup[name="solutionsCheckGroup"]');
        if (sparePartIds.length == 0) {
            errorsCheckGroup.removeAll();
            // solutionsCheckGroup.removeAll();
            // return;
            me.setSolutions();
        } else {
            var config = {
                url: ctx + 'repairApply/getBreakdownList.do',
                data: {
                    sparePartIds: sparePartIds
                },
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        errorsCheckGroup.removeAll();
                        // solutionsCheckGroup.removeAll();
                        var items = [];
                        Ext.each(result.data, function (item, index) {
                            var checkItem = {
                                boxLabel: item.breakAbbreviated,
                                inputValue: item.breakdownInfoId,
                                checked: false
                            };
                            items.push(checkItem);
                        });
                        errorsCheckGroup.add(items);
                        me.setSolutions();
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

    }
});