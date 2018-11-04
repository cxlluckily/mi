Ext.define('App.stockStock.stockStockController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stockStockController',
    refViews: {
        detailWindow: 'App.stockStock.stockStockDetailWindow',
        updateWindow: 'App.stockStock.stockStockUpdateWindow',
        importWindow: 'App.common.importWindow',
        messageWindow: 'App.common.messageWindow',
        view: 'stockStockView',
        updateUrl: 'stockInfo/updateStockInfo.do',
        importUrl: 'stockInfo/importStockList.do',
        downUrl: 'method/downfileUrl.do'
    },
    control: {
        'stockStockView button[action="search"]': {
            click: 'clickSearch'
        },
        'stockStockView button[action="look"]': {
            click: 'clickLook'
        },
        'stockStockView button[action="reset"]': {
            click: 'clickResets'
        },
        'stockStockDetailWindow button[action="search"]': {
            click: 'clickDetailSearch'
        },
        'stockStockUpdateWindow button[action="save"]': {
            click: 'clickUpdateSave'
        },
        'stockStockView button[action="importDownload"]': {
            click: 'clickImportDownload'
        },
        'stockStockView button[action="exportUpload"]': {
            click: 'clickExportUpload'
        }
    },
    actionFn: function (grid, rowIndex, colIndex, item, e, record) {
        var me = this;
        var flag = item.flag;
        if (flag == 'update') {
            var formValues = grid.up('window').down('form').getForm().getValues();
            var extWindow = Ext.create(this.refViews.updateWindow, {
                title: item.tooltip,
                viewAction: item.action,
                iconCls: item.iconCls
            });
            record.data.houseNo = !record.data.houseNo?'':record.data.houseNo;
            record.data.shelfNumber = !record.data.shelfNumber?'':record.data.shelfNumber;
            extWindow.down('grid').getStore().setData([record.data]);
            extWindow.down('form').getForm().setValues(formValues);
            extWindow.show();
        } else if (flag == 'delete') {
            grid.getStore().remove(record);
        } else if (flag == 'copy') {
            var copyRecord = Ext.apply({}, record.data);
            copyRecord.id = Math.random();
            copyRecord.aDelete = true;
            // copyRecord.goodsShelvesId = null;
            copyRecord.account = 1;
            var gridStore = grid.getStore();
            var count = rowIndex + 1;
            gridStore.insert(count, copyRecord);
        }
    },
    clickSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(btn){
        var form = Ext.getCmp('stockStockViewFormId');
        form.form.reset();
        this.clickSearch();
    },
    clickDetailSearch: function () {
        var pageList = Ext.ComponentQuery.query(this.refViews.detailWindow.split('.')[2])[0].down('grid');
        pageList.getStore().load();
    },
    clickLook: function (btn) {
        var records = this.getView().getSelection();
        if (records.length !== 1) {
            Ext.Msg.show({
                title: '警告',
                message: '只能对一条记录进行查看！',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
        } else {
            var record = records[0];
            var formValues = Ext.getCmp('stockStockViewFormId').getForm().getValues();
            var extWindow = Ext.create(this.refViews.detailWindow, {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls
            });
            extWindow.down('form').getForm().setValues({
                inventoryType: record.data.inventoryType,
                status: record.data.status,
                sparePartId: record.data.sparePartId,
                warehouseId: formValues.warehouseId
            });
            extWindow.show();
        }
    },
    clickUpdateSave: function (btn) {
        var me = this;
        var formValues = btn.up('window').down('form').getForm().getValues();
        var grid = btn.up('window').down('grid');
        var gridData = grid.getStore().getData();
        var saveGridData = [];
        Ext.each(gridData.items, function (item, index) {
            saveGridData.push(item.data);
        });
        var data = {};
        var checkIndex = null;
        var spareParts = grid.spareParts;
        var newSpareParts = {};
        Ext.each(saveGridData, function (item, firstIndex) {
            if (item.account == undefined) {
                item.account = 0;
            }
            if (newSpareParts[item.sparePartId] == undefined) {
                newSpareParts[item.sparePartId] = {
                    sparePartId: item.sparePartId,
                    partName: item.partName,
                    account: item.account
                }
            } else {
                newSpareParts[item.sparePartId].account += item.account;
            }
        });
        for (var key in newSpareParts) {
            if (checkIndex == null && (newSpareParts[key].account != spareParts.account)) {
                checkIndex = newSpareParts[key].partName;
            }
        }
        if (checkIndex != null) {
            Ext.Msg.show({
                title: '警告',
                message: '修改前后数量需相等！总数量' + spareParts.account + '!',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.WARNING
            });
            return;
        }
        data.stockId = spareParts.stockId;
        data.stockEntities = saveGridData;
        var url = me.refViews.updateUrl;
        var config = {
            btn: btn,
            url: ctx + url,
            data: data,
            success: function (response, opts) {
                var result = Ext.decode(response.responseText);
                if (result.result == "success") {
                    Ext.Msg.show({
                        title: '提示',
                        message: '修改成功！',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                    btn.up('window').close();
                    me.clickDetailSearch();
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
    clickImportDownload: function (btn) {
        var me = this;
        var extWindow = Ext.create(
            this.refViews.importWindow,
            {
                title: btn.text,
                viewAction: btn.action,
                iconCls: btn.iconCls,
                myData: {
                    url: ctx + this.refViews.importUrl,
                    downUrl: ctx + this.refViews.downUrl,
                    filename: "KuCunMuBan.xls",
                    callbackComponent:me
                }
            }
        );
        //extWindow.down('form').getForm().setValues(record.data);
        extWindow.show();
    },
    //导出页面
    clickExportUpload: function (btn) {
    
    
        var formValues = btn.up('form').getForm().getValues();
        
        var tiaojian="?";
        tiaojian+="warehouseId="+  formValues.warehouseId;
        tiaojian+="sparePartTypeId="+  formValues.sparePartTypeId;
        tiaojian+="partName="+  formValues.partName;
        tiaojian+="houseNo="+  formValues.houseNo;
        tiaojian+="inventoryType="+  formValues.inventoryType;
        tiaojian+="status="+  formValues.status;
      
        
        var form = document.createElement("form");
        form.setAttribute("id", Math.random());
        form.setAttribute("method", "post");
        form.setAttribute("target", "_self");
        form.setAttribute("accept-charset", "UTF-8");
        form.setAttribute("action", ctx + 'stockInfo/stockInfoExport.do'+tiaojian);
        
        var userKeyInput = document.createElement("input");
        userKeyInput.setAttribute("id", Math.random());
        userKeyInput.setAttribute("type", "text");
        userKeyInput.setAttribute("name", "userKey");
        userKeyInput.setAttribute("value", window.sessionStorage['userKey']);
        form.append(userKeyInput);
        
        var operationInput = document.createElement("input");
        operationInput.setAttribute("id", Math.random());
        operationInput.setAttribute("type", "text");
        operationInput.setAttribute("name", "operationSubjectId");
        operationInput.setAttribute("value", window.sessionStorage['operationSubjectId']);
        form.append(operationInput);
        document.getElementsByTagName("body")[0].append(form);
        setTimeout(function () {
            form.submit();
            setTimeout(function () {
                form.remove();
            }, 1000);
        }, 100);
    }
});