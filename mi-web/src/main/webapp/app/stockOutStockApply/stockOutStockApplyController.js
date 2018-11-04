Ext.define('App.stockOutStockApply.stockOutStockApplyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockOutStockApplyController',
    refViews: {
        detailWindow: 'App.stockOutStockApply.stockOutStockApplyDetailWindow',
        selectWindow: 'App.stockOutStockApply.stockOutStockApplySelectWindow',
        selectAddWindow: 'App.stockOutStockApply.stockOutStockApplySelectAddWindow',
        bindWindow: 'App.stockOutStockApply.stockOutStockApplyBindWindow',
        lookWindow: 'App.stockOutStockApply.stockOutStockApplyLookWindow',
        borrowWindow: 'App.stockOutStockApply.stockOutStockApplyBorrowWindow',
        borrowSelectWindow: 'App.stockOutStockApply.stockOutStockApplyBorrowSelectWindow',
        borrowSelectAddWindow: 'App.stockOutStockApply.stockOutStockApplyBorrowSelectAddWindow',
        borrowLookWindow: 'App.stockOutStockApply.stockOutStockApplyBorrowLookWindow',
        view: 'stockOutStockApplyView',
        detailUrl: 'outStockApply/getApplyDetailInfoByoutStockInfo.do',
        addUrl: 'user/inserOrUpdateOne.do',
        updateUrl: 'user/inserOrUpdateOne.do',
        saveUrl: 'outStockApply/useAndTransfeOutStockAndReturn.do',
        bindUrl: 'outStockApply/updateStockQrCode.do',
        borrowAddUrl: 'outStockApply/insertBorrowOutStock.do',
    },
    control: {
        'stockOutStockApplyGrid button[action="search"]': {
            click: 'clickSearch'
        },
        'stockOutStockApplyGrid button[action="reset"]': {
            click: 'clickReset'
        },
        'stockOutStockApplyGrid button[action="out"]': {
            click: 'clickOut'
        },
        'stockOutStockApplyGrid button[action="bind"]': {
            click: 'clickBind'
        },
        'stockOutStockApplyGrid button[action="look"]': {
            click: 'clickLook'
        },
        'stockOutStockApplySelectWindow button[action="search"]': {
            click: 'clickSelectSearch'
        },
        'stockOutStockApplySelectWindow button[action="save"]': {
            click: 'clickSelectSave'
        },
        'stockOutStockApplySelectAddWindow button[action="save"]': {
            click: 'clickAddSave'
        },
        'stockOutStockApplyBindWindow button[action="save"]': {
            click: 'clickBindSave'
        },
        'stockOutStockApplyDetailWindow button[action="save"]': {
            click: 'clickSave'
        },
        'stockOutStockApplyGridBorrow button[action="search"]': {
            click: 'clickSearch'
        },
        'stockOutStockApplyGridBorrow button[action="reset"]': {
            click: 'clickReset'
        },
        'stockOutStockApplyGridBorrow button[action="out"]': {
            click: 'clickBorrowOut'
        },
        'stockOutStockApplyGridBorrow button[action="look"]': {
            click: 'clickBorrowLook'
        },
        'stockOutStockApplyGridBorrow button[action="bind"]': {
            click: 'clickBind'
        },
        'stockOutStockApplyBorrowWindow button[action="choose"]': {
            click: 'clickChoose'
        },
        'stockOutStockApplyBorrowWindow button[action="save"]': {
            click: 'clickBorrowSave'
        },
        'stockOutStockApplyBorrowSelectWindow button[action="search"]': {
            click: 'clickBorrowSearch'
        },
        'stockOutStockApplyBorrowSelectWindow button[action="save"]': {
            click: 'clickBorrowSelectSave'
        },
        'stockOutStockApplyBorrowSelectAddWindow button[action="save"]': {
            click: 'clickBorrowAddSave'
        },
        'stockOutStockApplyBorrowSelectWindow button[action="reset"]': {
            click: 'clickBorrowSelectReset'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'update') {
            var outGrid = grid.up('window').down('stockOutStockApplyOutGrid');
            var form = grid.up('window').down('form');
            var formValue = form.getForm().getValues();
            var data = {
                sparePartId: record.data.sparePartId,
                warehouseId: formValue.outWarehouseId,
                outCount: record.data.outCount,
                outStockApplyDetailId: record.data.outStockApplyDetailId,
                status: record.data.stockStatus
            };
            var extWindow = Ext.create(me.refViews.selectWindow, {
                title: item.tooltip,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            extWindow.down('form').getForm().setValues(data);
            var selectGridData = [];
            var outGridData = outGrid.store.getData().items;
            Ext.each(outGridData, function (item, index) {
                if (item.data.outStockApplyDetailId == record.data.outStockApplyDetailId) {
                    selectGridData.push(item.data);
                }
            });
            var selectGrid = extWindow.down('stockOutStockApplySelectGridSelect');
            selectGrid.store.setData(selectGridData);
            extWindow.show();
        }
        else if (flag == 'add') {
            var formValue = grid.up('window').down('form').getForm().getValues();
            var selectGrid = grid.up('window').down('stockOutStockApplySelectGridSelect');
            var selectGridData = selectGrid.store.getData().items;
            var checkOk = true;
            var checkIndex = 0;
            Ext.each(selectGridData, function (item, index) {
                if (item.data.stockId == record.data.stockId) {
                    checkOk = false;
                    checkIndex = index + 1;
                }
            });
            if (!checkOk) {
                Ext.Msg.show({
                    title: '警告',
                    message: '请在已选择列表中第' + checkIndex + '行中修改发放数量！',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
                return;
            }
            var data = Ext.apply({outCount: 1}, record.data);
            var store = selectGrid.getStore();
            var count = store.getCount();
            store.insert(count, data);
        }
        else if (flag == 'addBorrow') {
            var formValue = grid.up('window').down('form').getForm().getValues();
            var selectGrid = grid.up('window').down('stockOutStockApplyBorrowSelectGridSelect');
            var selectGridData = selectGrid.store.getData().items;
            var checkOk = true;
            var checkIndex = 0;
            Ext.each(selectGridData, function (item, index) {
                if (item.data.stockId == record.data.stockId) {
                    checkOk = false;
                    checkIndex = index + 1;
                }
            });
            if (!checkOk) {
                if (record.data.inventoryType == 'unique') {
                    Ext.Msg.show({
                        title: '警告',
                        message: '请在已选择列表中第' + checkIndex + '行中查看！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                } else {
                    Ext.Msg.show({
                        title: '警告',
                        message: '请在已选择列表中第' + checkIndex + '行中修改发放数量！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                }
                return;
            }
            var data = Ext.apply({outCount: 1}, record.data);
            var store = selectGrid.getStore();
            var count = store.getCount();
            store.insert(count, data);
        }
        else if (flag == 'delete') {
            grid.getStore().remove(record);
        }
        else if (flag == 'out') {
            var me = this;
            var url = me.refViews.detailUrl;
            var data = {
                outStockApplyId: record.data.outStockApplyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.detailWindow, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('stockOutStockApplyDetailGrid');
                        var outGrid = extWindow.down('stockOutStockApplyOutGrid');
                        var outForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.outStockApplyDetailEntities);
                        outGrid.store.setData(result.data.outStockDetailEntities);
                        outForm.getForm().setValues(result.data.applyEntity);
                        extWindow.show();
                    }
                    else {
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
            var me = this;
            var url = me.refViews.detailUrl;
            var data = {
                outStockApplyId: record.data.outStockApplyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.lookWindow, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var detailGrid = extWindow.down('stockOutStockApplyLookDetailGrid');
                        var outGrid = extWindow.down('stockOutStockApplyLookOutGrid');
                        var outForm = extWindow.down('form');
                        detailGrid.store.setData(result.data.outStockApplyDetailEntities);
                        outGrid.store.setData(result.data.outStockDetailEntities);
                        outForm.getForm().setValues(result.data.applyEntity);
                        if (result.data.stockBusinessApplyEntity.inWareHouseName == null) {
                            outForm.getForm().setValues({inWareHouseName: result.data.stockBusinessApplyEntity.outWareHouseName});
                        }
                        else {
                            outForm.getForm().setValues({inWareHouseName: result.data.stockBusinessApplyEntity.inWareHouseName});
                        }
                        outForm.getForm().setValues({applyRemark: result.data.stockBusinessApplyEntity.applyRemark});
                        extWindow.show();
                    }
                    else {
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
        else if (flag == 'bind') {
            var me = this;
            var extWindow = Ext.create(me.refViews.bindWindow, {
                title: grid.ownerCt.title,
                viewAction: item.flag,
                iconCls: item.iconCls
            });
            extWindow.down('form').form.setValues(record.data);
            extWindow.show();
        }
        else if(flag == 'borrowLook'){
            var me = this;
            var url = me.refViews.detailUrl;
            var data = {
                outStockApplyId: record.data.outStockApplyId
            };
            var config = {
                url: ctx + url,
                data: data,
                success: function (response, opts) {
                    var result = Ext.decode(response.responseText);
                    if (result.result == "success") {
                        var extWindow = Ext.create(me.refViews.borrowLookWindow, {
                            title: grid.ownerCt.title,
                            viewAction: item.flag,
                            iconCls: item.iconCls
                        });
                        var outGrid = extWindow.down('grid');
                        var outForm = extWindow.down('form');
                        outGrid.store.setData(result.data.outStockDetailEntities);
                        outForm.getForm().setValues(result.data.applyEntity);
                        extWindow.show();
                    }
                    else {
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
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0].getActiveTab();
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickReset: function (btn) {
        btn.up('form').getForm().reset();
    },
    clickBorrowSelectReset:function(btn){
        btn.up('form').getForm().reset();
        btn.up('window').down('stockOutStockApplyBorrowSelectGridAll').store.load();
    },
    clickSelectSearch: function (btn) {
        btn.up('window').down('stockOutStockApplySelectGridAll').store.load();
    },
    clickSelectSave: function (btn) {
        var formValue = btn.up('window').down('form').form.getValues();
        var selectGridData = btn.up('window').down('stockOutStockApplySelectGridSelect').getStore().getData().items;
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
        var outGridStore = Ext.ComponentQuery.query("stockOutStockApplyDetailWindow")[0].down('stockOutStockApplyOutGrid').store;
        var outGridData = outGridStore.getData().items;
        var detailGridStore = Ext.ComponentQuery.query("stockOutStockApplyDetailWindow")[0].down('stockOutStockApplyDetailGrid').store;
        var detailGridData = detailGridStore.getData().items;
        var saveGridData = [];
        Ext.each(outGridData, function (item, index) {
            if (item.data.outStockApplyDetailId !== formValue.outStockApplyDetailId) {
                // outGridStore.remove(item);
                saveGridData.push(item.data);
            }
        });
        Ext.each(newOutGridData, function (item, index) {
            item.outStockApplyDetailId = formValue.outStockApplyDetailId;
            saveGridData.push(item);
            // outGridStore.insert(index, item);
        });
        outGridStore.setData(saveGridData);
        Ext.each(detailGridData, function (item, index) {
            if (item.data.sparePartId == formValue.sparePartId) {
                item.data.alreadyOutCount = outCount;
                item.commit();
            }
        });
        btn.up('window').close();
    },
    clickBindSave: function (btn) {
        var me = this;
        var bindGridData = btn.up('window').down('stockOutStockApplyBindGrid').store.getData().items;
        var formValues = btn.up('window').down('form').getForm().getValues();
        var gridDataCopy = JSON.parse(formValues.gridDataCopy);
        var bindGridDataSave = [];
        Ext.each(bindGridData, function (item, index) {
            bindGridDataSave.push(item.data);
        });
        var check = true;
        var checkIndex = null;
        Ext.each(bindGridDataSave, function (fitem, findex) {
            Ext.each(bindGridDataSave, function (sitem, sindex) {
                if (!!fitem.qrCode && !!sitem.qrCode && fitem.qrCode == sitem.qrCode && findex != sindex) {
                    if (check) {
                        check = false;
                        checkIndex = sindex + 1;
                    }
                }
            });
        });

        if (!check) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行二维码已存在，请重新输入！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var copyCheck = true;
        var copyCheckText = '';
        Ext.each(bindGridDataSave, function (fitem, findex) {
            Ext.each(gridDataCopy, function (sitem, sindex) {
                if (!!fitem.qrCode && !!sitem.qrCode && fitem.qrCode != sitem.qrCode && findex == sindex) {
                    copyCheck = false;
                    if (copyCheckText != '') {
                        copyCheckText += '、'
                    }
                    copyCheckText += findex + 1;
                }
            });
        });
        var url = this.refViews.bindUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: {formBeans: bindGridDataSave},
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '绑定二维码成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickSearch();
                }
                else {
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
        if (!copyCheck) {
            Ext.Msg.confirm('提示信息', '第' + copyCheckText + '行已绑定二维码，您确认绑定新二维码吗？', function (c) {
                    if (c == 'yes') {
                        App.common.Ajax.request(config);
                    }
                }
            );
        }
        else {
            App.common.Ajax.request(config);
        }
    },
    clickAddSave: function (btn) {
        var formValue = btn.up('window').down('form').getForm().getValues();
        if (parseInt(formValue.outCount) > parseInt(formValue.account)) {
            Ext.Msg.show({
                title: '警告',
                message: '发放数量不可大于可用数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var selectWindow = Ext.ComponentQuery.query("stockOutStockApplySelectWindow")[0];
        var selectGrid = selectWindow.down('stockOutStockApplySelectGridSelect');
        var store = selectGrid.getStore();
        var count = store.getCount();
        store.insert(count, formValue);
        btn.up('window').close();
    },

    clickLook: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行出库！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            outStockApplyId: record.data.outStockApplyId
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
                    var detailGrid = extWindow.down('stockOutStockApplyLookDetailGrid');
                    var outGrid = extWindow.down('stockOutStockApplyLookOutGrid');
                    var outForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.outStockApplyDetailEntities);
                    outGrid.store.setData(result.data.outStockDetailEntities);
                    outForm.getForm().setValues(result.data.applyEntity);
                    if (result.data.stockBusinessApplyEntity.inWareHouseName == null) {
                        outForm.getForm().setValues({inWareHouseName: result.data.stockBusinessApplyEntity.outWareHouseName});
                    }
                    else {
                        outForm.getForm().setValues({inWareHouseName: result.data.stockBusinessApplyEntity.inWareHouseName});
                    }
                    outForm.getForm().setValues({applyRemark: result.data.stockBusinessApplyEntity.applyRemark});
                    extWindow.show();
                }
                else {
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
    clickOut: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行出库！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var record = records[0];
        var url = me.refViews.detailUrl;
        var data = {
            outStockApplyId: record.data.outStockApplyId
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.detailWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var detailGrid = extWindow.down('stockOutStockApplyDetailGrid');
                    var outGrid = extWindow.down('stockOutStockApplyOutGrid');
                    var outForm = extWindow.down('form');
                    detailGrid.store.setData(result.data.outStockApplyDetailEntities);
                    outGrid.store.setData(result.data.outStockDetailEntities);
                    outForm.getForm().setValues(result.data.applyEntity);
                    extWindow.show();
                }
                else {
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
    clickBorrowOut: function (btn) {
        var me = this;
        var extWindow = Ext.create(me.refViews.borrowWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var outOrderType = btn.up('grid').outOrderType;
        extWindow.down('form').getForm().setValues({outOrderType: outOrderType});
        extWindow.show();
    },
    clickBind: function (btn) {
        var me = this;
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择一条记录进行二维码绑定！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var record = records[0];
        var extWindow = Ext.create(me.refViews.bindWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        extWindow.down('form').form.setValues(record.data);
        extWindow.show();
    },
    clickSave: function (btn) {
        var me = this;
        var applyEntity = Ext.ComponentQuery.query("stockOutStockApplyDetailWindow")[0].down('form').getForm().getValues();
        var detailGridData = Ext.ComponentQuery.query("stockOutStockApplyDetailWindow")[0].down('stockOutStockApplyDetailGrid').store.getData().items;
        var outGridData = Ext.ComponentQuery.query("stockOutStockApplyDetailWindow")[0].down('stockOutStockApplyOutGrid').store.getData().items;
        var data = {
            applyEntity: applyEntity,
            outStockApplyDetailEntities: [],
            outStockDetailEntities: []
        };
        Ext.each(detailGridData, function (item, index) {
            data.outStockApplyDetailEntities.push(item.data);
        });
        Ext.each(outGridData, function (item, index) {
            data.outStockDetailEntities.push(item.data);
        });
        var checkType = 0;
        var checkIndex = null;
        Ext.each(data.outStockApplyDetailEntities, function (item, index) {
            if (Number(item.alreadyOutCount) > Number(item.outCount) && checkType == 0) {
                checkType = 1;
                checkIndex = index + 1;
            }
        });
        if (checkType == 1) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '条数据已选择数量不能大于待出库数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        if (data.outStockDetailEntities.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择出库备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
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
                        message: '出库成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickSearch();
                }
                else {
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
    clickChoose: function (btn) {
        var me = this;
        var detailGrid = btn.up('window').down('grid');
        var selectGridData = [];
        var detailGridData = detailGrid.store.getData().items;
        Ext.each(detailGridData, function (item, index) {
            selectGridData.push(item.data);
        });
        var extWindow = Ext.create(me.refViews.borrowSelectWindow, {
            title: btn.text,
            viewAction: btn.action,
            iconCls: btn.iconCls
        });
        var formValues = btn.up('window').down('form').getForm().getValues();
        extWindow.down('form').getForm().setValues({warehouseId: formValues.outWarehouseId});
        var selectGrid = extWindow.down('stockOutStockApplyBorrowSelectGridSelect');
        selectGrid.store.setData(selectGridData);
        extWindow.show();
    },
    clickBorrowSearch: function (btn) {
        btn.up('window').down('stockOutStockApplyBorrowSelectGridAll').store.load();
    },
    clickBorrowAddSave: function (btn) {
        var formValue = btn.up('window').down('form').getForm().getValues();
        if (Number(formValue.outCount) > Number(formValue.account)) {
            Ext.Msg.show({
                title: '警告',
                message: '发放数量不可大于可用数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        else if (Number(formValue.outCount) <= 0 || Number(formValue.outCount) % 1) {
            Ext.Msg.show({
                title: '警告',
                message: '发放数量必须为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var selectWindow = Ext.ComponentQuery.query("stockOutStockApplyBorrowSelectWindow")[0];
        var selectGrid = selectWindow.down('stockOutStockApplyBorrowSelectGridSelect');
        var store = selectGrid.getStore();
        var count = store.getCount();
        store.insert(count, formValue);
        btn.up('window').close();
    },

    clickBorrowSelectSave: function (btn) {
        var selectGridData = btn.up('window').down('stockOutStockApplyBorrowSelectGridSelect').getStore().getData().items;
        var newOutGridData = [];
        var checkOK = 0;
        var checkIndex = 0;
        Ext.each(selectGridData, function (item, index) {
            if (parseInt(item.data.outCount) > parseInt(item.data.account)) {
                if (checkOK == 0) {
                    checkOK = 1;
                    checkIndex = index + 1;
                }
            }
            else if (Number(item.data.outCount) <= 0 || Number(item.data.outCount) % 1) {
                if (checkOK == 0) {
                    checkOK = 2;
                    checkIndex = index + 1;
                }
            }
            newOutGridData.push(item.data);
        });
        if (checkOK == 1) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行发放数量不可大于可用数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        else if (checkOK == 2) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行发放数量必须为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }

        var outGridStore = Ext.ComponentQuery.query("stockOutStockApplyBorrowWindow")[0].down('grid').store;
        outGridStore.setData(newOutGridData);
        btn.up('window').close();
    },

    clickBorrowSave: function (btn) {
        var me = this;
        var formValues = btn.up('window').down('form').getForm().getValues();
        var gridData = btn.up('window').down('grid').getStore().getData();
        var outData = [];
        Ext.each(gridData.items, function (item, index) {
            outData.push(item.data);
        });

        var newOutGridData = [];
        var checkOK = 0;
        var checkIndex = 0;
        Ext.each(outData, function (item, index) {
            if (parseInt(item.outCount) > parseInt(item.account)) {
                if (checkOK == 0) {
                    checkOK = 1;
                    checkIndex = index + 1;
                }
            } else if (Number(item.outCount) <= 0 || Number(item.outCount) % 1) {
                if (checkOK == 0) {
                    checkOK = 2;
                    checkIndex = index + 1;
                }
            }
            newOutGridData.push(item);
        });
        if (checkOK == 1) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行发放数量不可大于可用数量！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        } else if (checkOK == 2) {
            Ext.Msg.show({
                title: '警告',
                message: '第' + checkIndex + '行发放数量必须为正整数！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var data = {
            applyFormBean: formValues,
            detailFormBeans: outData
        };
        if (data.detailFormBeans.length == 0) {
            Ext.Msg.show({
                title: '警告',
                message: '请选择出库备件！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        var url = me.refViews.borrowAddUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '出库成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickSearch();
                }
                else {
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
    clickBorrowLook: function (btn) {
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
            outStockApplyId: record.data.outStockApplyId
        };
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    var extWindow = Ext.create(me.refViews.borrowLookWindow, {
                        title: btn.text,
                        viewAction: btn.action,
                        iconCls: btn.iconCls
                    });
                    var outGrid = extWindow.down('grid');
                    var outForm = extWindow.down('form');
                    outGrid.store.setData(result.data.outStockDetailEntities);
                    outForm.getForm().setValues(result.data.applyEntity);
                    extWindow.show();
                }
                else {
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
});