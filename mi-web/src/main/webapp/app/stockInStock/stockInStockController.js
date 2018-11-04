Ext.define('App.stockInStock.stockInStockController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockInStockController',
    refViews: {
        window: 'App.stockInStock.stockInStockWindow',
        lookWindow: 'App.stockInStock.stockInStockLookWindow',
        borrowLookWindow: 'App.stockInStock.stockInStockBorrowLookWindow',
        newWindow: 'App.stockInStock.stockInStockNewWindow',
        otherWindow: 'App.stockInStock.stockInStockOtherWindow',
        newSelectWindow: 'App.stockInStock.stockInStockNewSelectWindow',
        newLookWindow: 'App.stockInStock.stockInStockNewLookWindow',
        view: 'stockInStockView',
        detailUrl: 'stockIn/getStockInInfoById.do',
        lookDetailUrl: 'stockIn/getStockInInfoById.do',
        addUrl: 'stockIn/addNewInApply.do',
        saveUrl: 'stockIn/commitStockIn.do',
        // addUrl: 'applyInfo/addApplyInfo.do',
        // updateUrl: 'applyInfo/updateApplyInfo.do',
    },
    control: {
        'stockInStockGrid button[action="search"]': {
            click: 'clickSearch'
        },
        'stockInStockGrid button[action="in"]': {
            click: 'clickIn'
        },
        'stockInStockGrid button[action="look"]': {
            click: 'clickLook'
        },
        'stockInStockGrid button[action="resets"]': {
            click: 'clickResets'
        },
        'stockInStockGridNew button[action="resets"]': {
            click: 'clickResets'
        },
        'stockInStockGridBorrow button[action="resets"]': {
            click: 'clickResets'
        },
        'stockInStockGridBorrow button[action="search"]': {
            click: 'clickSearch'
        },
        'stockInStockGridBorrow button[action="in"]': {
            click: 'clickIn'
        },
        'stockInStockGridBorrow button[action="look"]': {
            click: 'clickLook'
        },
        'stockInStockWindow button[action="save"]': {
            click: 'clickSave'
        },
        'stockInStockOtherWindow button[action="save"]': {
            click: 'clickSave'
        },
        'stockInStockNewSelectWindow button[action="actionsave"]': {
            click: 'clickActionSave'
        },
        'stockInStockNewSelectWindow button[action="reset"]': {
            click: 'clickNewSelectReset'
        },
        'stockInStockGridNew button[action="search"]': {
            click: 'clickSearch'
        },
        'stockInStockGridNew button[action="in"]': {
            click: 'clickNewIn'
        },
        'stockInStockGridNew button[action="look"]': {
            click: 'clickNewLook'
        },
        'stockInStockNewWindow button[action="add"]': {
            click: 'clickNewAdd'
        },
        'stockInStockNewWindow button[action="save"]': {
            click: 'clickNewSave'
        },
        'stockInStockNewSelectWindow button[action="search"]': {
            click: 'clickNewSelectSearch'
        }

    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'add') {
            var selectGrid = grid.up('window').down('stockInStockNewDetail');
            var selectGridData = selectGrid.store.getData().items;
            var checkOk = true;
            var checkIndex = 0;
            Ext.each(selectGridData, function (item, index) {
                if (item.data.sparePartId == record.data.sparePartId) {
                    checkOk = false;
                    checkIndex = index + 1;
                }
            });
            var id = Math.random() * new Date().getTime();
            var data = Ext.apply({}, record.data);
            Ext.apply(data, {id: id, alreadyInCount: 1, status: 'normal', inventoryType: 'notUnique'});
            var store = selectGrid.getStore();
            var count = store.getCount();
            store.insert(count, data);
        }
        else if (flag == 'copy') {
            var copyRecord = Ext.apply({}, record.data);
            copyRecord.id = Math.random();
            copyRecord.goodsShelvesId = null;
            copyRecord.alreadyInCount = 1;
            copyRecord.aDelete = true;
            var gridStore = grid.getStore();
            var count = rowIndex + 1;
            gridStore.insert(count, copyRecord);
        }
        else if (flag == 'delete') {
            grid.getStore().remove(record);
        }
        else if (flag == 'in') {
            var url = me.refViews.detailUrl;
            var data = {
                inStockId: record.data.inStockId,
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        Ext.each(result.data.details, function (item, index) {
                            item.goodsShelvesId = null;
                        });
                        var w = '';
                        if (grid.ownerCt.inStockType == 'useIn') {
                            w = me.refViews.window;
                        } else {
                            w = me.refViews.otherWindow;
                        }
                        var extWindow = Ext.create(w, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('grid');
                        var outForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.details);
                        outForm.getForm().setValues(result.data);
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
            var url = me.refViews.lookDetailUrl;
            var data = {
                inStockId: record.data.inStockId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var w = '';
                        if (grid.ownerCt.inStockType == 'borrowIn') {
                            w = me.refViews.borrowLookWindow;
                        } else {
                            w = me.refViews.lookWindow;
                        }
                        var extWindow = Ext.create(w, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailTab = extWindow.down('tabpanel');
                        var outForm = extWindow.down('form');
                        var inStock = detailTab.items.items[0];
                        var inStockDetail = detailTab.items.items[1];
                        Ext.each(result.data.splitDetails, function (item, index) {
                            item.inStockAcount = item.count;
                        });
                        inStock.store.setData(result.data.details);
                        inStockDetail.store.setData(result.data.splitDetails);
                        outForm.getForm().setValues(result.data);
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
        else if (flag == 'newIn') {
            var extWindow = Ext.create(me.refViews.newWindow, {
                title: grid.ownerCt.title,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            extWindow.show();
        }
        else if (flag == 'newLook') {
            var url = me.refViews.lookDetailUrl;
            var data = {
                inStockId: record.data.inStockId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.newLookWindow, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('grid');
                        var outForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.splitDetails);
                        outForm.getForm().setValues(record.data);
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
    clickActionSave: function (btn) {
        var selectGridData = btn.up('window').down('stockInStockNewDetail').getStore().getData().items;
        var newSelectGridData = [];
        Ext.each(selectGridData, function (item) {
            item.data.inventoryType = '';
            newSelectGridData.push(item.data);
        });
        var detailGridStore = Ext.ComponentQuery.query("stockInStockNewWindow")[0].down('stockInStockNewDetailGrid').store;
        // return;
        detailGridStore.setData(newSelectGridData);
        btn.up('window').close();
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0].getActiveTab();
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets: function (btn) {
        btn.up('form').getForm().reset();
        btn.up('form').getForm().setValues({warehouseId: '0', inStockStatus: "all"});
        this.clickSearch();
    },
    clickNewSelectReset: function (btn) {
        btn.up('form').getForm().reset();
        btn.up('window').down('grid').store.load();
        // btn.up('window').down('stockInStockNewDetail').store.load();
    },
    clickNewSelectSearch: function (btn) {
        btn.up('window').down('grid').store.load();
    },
    clickSelectSave: function (btn) {
        var formValue = btn.up('window').down('form').form.getValues();
        var selectGridData = btn.up('window').down('stockInStockSelectGridSelect').getStore().getData().items;
        var outCount = 0;
        var newOutGridData = [];
        var checkOK = 0;
        var checkIndex = 0;
        Ext.each(selectGridData, function (item, index) {
            if (Number(item.data.outCount) <= 0 || Number(item.data.outCount) % 1) {
                if (checkOK == 0) {
                    checkOK = 1;
                    checkIndex = index + 1;
                }
            } else if (parseInt(item.data.outCount) > parseInt(item.data.account)) {
                if (checkOK == 0) {
                    checkOK = 2;
                    checkIndex = index + 1;
                }
            }
            newOutGridData.push(item.data);
            outCount += parseInt(item.data.outCount);
        });

        if (checkOK == 1) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '发放数量必须为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (checkOK == 2) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '发放数量不可大于可用数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (outCount > parseInt(formValue.outCount)) {
            Ext.Msg.show({
                title: '警告',
                message: '发放数量不可大于待发放数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var outGridStore = Ext.ComponentQuery.query("stockInStockDetailWindow")[0].down('stockInStockOutGrid').store;
        var outGridData = outGridStore.getData().items;
        var detailGridStore = Ext.ComponentQuery.query("stockInStockDetailWindow")[0].down('stockInStockDetailGrid').store;
        var detailGridData = detailGridStore.getData().items;
        Ext.each(outGridData, function (item, index) {
            if (item.data.outStockApplyDetailId == formValue.outStockApplyDetailId) {
                outGridStore.remove(item);
            }
        });
        Ext.each(newOutGridData, function (item, index) {
            item.outStockApplyDetailId = formValue.outStockApplyDetailId;
            outGridStore.insert(index, item);
        });
        Ext.each(detailGridData, function (item, index) {
            if (item.data.sparePartId == formValue.sparePartId) {
                item.data.alreadyOutCount = outCount;
                item.commit();
            }
        });
        btn.up('window').close();
    },
    clickIn: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行入库！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            inStockId: record.data.inStockId,
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.each(result.data.details, function (item, index) {
                        item.goodsShelvesId = null;
                    });
                    var w = '';
                    if (btn.up('grid').inStockType == 'useIn') {
                        w = me.refViews.window;
                    } else {
                        w = me.refViews.otherWindow;
                    }
                    var extWindow = Ext.create(w, {
                        title: btn.up('grid').title,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('grid');
                    var outForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.details);
                    outForm.getForm().setValues(record.data);
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
        //  维修人员领用，归还入库时，需要可以更改备件，
        var me = this;
        var formValues = btn.up('window').down('form').getForm().getValues();
        if (!btn.up('window').down('form').getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var grid = btn.up('window').down('grid');
        var gridData = grid.getStore().getData();
        var saveGridData = [];
        Ext.each(gridData.items, function (item, index) {
            saveGridData.push(item.data);
        });
        var data = formValues;
        var checkIndex = null;
        var checkType = null;
        var spareParts = grid.spareParts;
        var newSpareParts = {};
        var checkSaveGridData = [];
        var checkQrCode = true;
        Ext.each(saveGridData, function (firstItem, firstIndex) {
            Ext.each(saveGridData, function (secondItem, secondIndex) {
                if (checkQrCode && firstItem.qrCode && secondItem.qrCode && firstItem.stockId != secondItem.stockId && firstItem.qrCode == secondItem.qrCode) {
                    checkQrCode = false;
                } else {
                    checkSaveGridData.push(firstItem);
                }
            });
        });
        if (!checkQrCode) {
            Ext.Msg.show({
                title: '警告',
                message: '有二维码重复，不能绑定相同的二维码！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        Ext.each(saveGridData, function (item, firstIndex) {
            if (item.alreadyInCount == undefined) {
                item.alreadyInCount = 0;
            }
            if (newSpareParts[item.sparePartId] == undefined) {
                newSpareParts[item.sparePartId] = {
                    sparePartId: item.sparePartId,
                    partName: item.partName,
                    alreadyInCount: item.alreadyInCount,
                    inventoryType:item.inventoryType
                }
            } else {
                newSpareParts[item.sparePartId].alreadyInCount += item.alreadyInCount;
            }
        });
        for (var key in newSpareParts) {
            if (checkIndex == null && (newSpareParts[key].alreadyInCount != spareParts[key].inStockAcount && spareParts[key].inventoryType == 'unique')) {
                checkType = 'unique';
                checkIndex = newSpareParts[key].partName;
            }
            if (checkIndex == null && (newSpareParts[key].alreadyInCount > spareParts[key].inStockAcount && spareParts[key].inventoryType == 'notUnique')) {
                checkType = 'notUnique';
                checkIndex = newSpareParts[key].partName;
            }
        }
        if (checkIndex != null && checkType == 'unique') {
            Ext.Msg.show({
                title: '警告',
                message: checkIndex + '已选择数量需等于待入库数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (checkIndex != null && checkType == 'notUnique') {
            Ext.Msg.show({
                title: '警告',
                message: checkIndex + '已选择数量需小于等于待入库数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var date = new Date();
        var hms = ' '+('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':'+('0' + date.getSeconds()).slice(-2);
        data.inDate += hms;
        var s = data.inDate.replace(/-/g,"/");
        var newData = new Date(s);
        data.inDate = newData.getTime();
        data.detailEntities = saveGridData;
        var url = me.refViews.saveUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '入库成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
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
        var url = me.refViews.lookDetailUrl;
        var data = {
            inStockId: record.data.inStockId
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
                    var detailTab = extWindow.down('stockInStockLookDetailTab');
                    var outForm = extWindow.down('form');
                    var inStock = detailTab.items.items[0];
                    var inStockDetail = detailTab.items.items[1];
                    Ext.each(result.data.splitDetails, function (item, index) {
                        item.inStockAcount = item.count;
                    });
                    inStock.store.setData(result.data.details);
                    inStockDetail.store.setData(result.data.splitDetails);
                    outForm.getForm().setValues(record.data);
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
    clickNewSave: function (btn) {
        var me = this;
        var formValues = btn.up('window').down('form').getForm().getValues();
        if (!btn.up('window').down('form').getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
            return;
        }
        var gridData = btn.up('window').down('grid').getStore().getData();
        if (gridData.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        formValues.inStockType = 'newIn';
        var detailEntities = [];
        var checkIndex = null;
        var checkType = null;
        Ext.each(gridData.items, function (item, index) {
            detailEntities.push(item.data);
            if (item.data.alreadyInCount == undefined || item.data.alreadyInCount == '请选择') {
                item.data.alreadyInCount = 0;
            }
            if (!checkIndex && (!item.data.inventoryType || item.data.inventoryType == '请选择')) {
                checkIndex = index + 1;
                checkType = 'inventoryType';
            }
            // if (!checkIndex && item.data.inventoryType == 'unique' && (Number(item.data.alreadyInCount) != 1)) {
            //     checkIndex = index + 1;
            //     checkType = 'unique';
            // }
            if (!checkIndex && (Number(item.data.alreadyInCount) <= 0 || Number(item.data.alreadyInCount) % 1)) {
                checkIndex = index + 1;
                checkType = 'alreadyInCount';
            }
            if (item.data.price && !checkIndex && !App.common.regExpValidator.plus.test(item.data.price)) {
                checkIndex = index + 1;
                checkType = 'price';
            }
        });

        if (checkType == 'inventoryType') {
            Ext.Msg.show({
                title: '警告',
                message: '请选择第' + checkIndex + '行库存类型！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        // if (checkType == 'unique') {
        //     Ext.Msg.show({
        //         title: '警告',
        //         message: '第' + checkIndex + '行库存类型为唯一标识，入库数量只能为1！',
        //         buttons: Ext.Msg.OK,
        //         icon: Ext.Msg.WARNING
        //     });
        //     return;
        // }
        if (checkType == 'alreadyInCount') {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行入库数量错误！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (checkType == 'price') {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行单价必须大于0！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var data = formValues;
        var date = new Date();
        var hms = ' '+('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':'+('0' + date.getSeconds()).slice(-2);
        data.inDate += hms;
        var s = data.inDate.replace(/-/g,"/");
        var newData = new Date(s);
        data.inDate = newData.getTime();
        data.detailEntities = detailEntities;
        var url = me.refViews.addUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '入库成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
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
    clickNewIn: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.newWindow, {
            title: btn.up('grid').title,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.show();
    },
    clickNewAdd: function (btn) {
        var me = this;
        var detailGrid = btn.up('window').down('grid');
        var selectGridData = [];
        var detailGridData = detailGrid.store.getData().items;
        Ext.each(detailGridData, function (item, index) {
            item.status = "normal";
            selectGridData.push(item.data);
        });
        var extWindow = Ext.create(me.refViews.newSelectWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var selectGrid = extWindow.down('stockInStockNewDetail');
        selectGrid.store.setData(selectGridData);
        extWindow.show();
    },
    clickNewLook: function (btn) {
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
        var url = me.refViews.lookDetailUrl;
        var data = {
            inStockId: record.data.inStockId
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.newLookWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('grid');
                    var outForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.splitDetails);
                    outForm.getForm().setValues(record.data);
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
});