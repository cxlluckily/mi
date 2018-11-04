Ext.define('App.supplier.supplierController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.supplierController',
    control: {
        'supplierView button[action="add"]': {
            click: 'openAddWindow'
        },
        'supplierView button[action="update"]': {
            click: 'openUpdateWindow'
        },
        'supplierView button[action="delete"]': {
            click: 'clickDelete'
        },
        'supplierView button[action="resets"]': {
            click: 'clickResets'
        },
        'supplierWindow button[action="save"]': {
            click: 'clickSave'
        }
        
    },
    
    actionFn : function(grid, rowIndex, colIndex, item, e, record){
    	var flag = item.flag;
    	var me = this;
    	if(flag == 'update'){
            Ext.apply(record.data, {userKey: window.sessionStorage['userKey']});
            var extWindow = Ext.create('App.supplier.supplierWindow',{
            	viewAction: item.flag,
        		title: item.tooltip,
                iconCls: item.iconCls
        	});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
    	}
    	if(flag == 'delete'){
    		Ext.Msg.confirm('提示信息', '确定要删除吗？', function(c) {
        		if(c == 'yes'){
        			var url = 'supplier/delete.do'; 
                	var data = {supplierId: record.data.supplierId, status:'stop_using'};
                	var config = { // 这一句是定义提交的内容 和返回值处理
                			url : url,
                			data : data,
                			success : function(response, opts) {
                				var responseData = Ext.decode(response.responseText);
                				if (responseData.result == "success") {
                						Ext.Msg.show({
                							title : '提示',
                							message : responseData.message,
                							buttons : Ext.Msg.OK,
                							icon : Ext.Msg.INFO
                						});
                				} else {
                					Ext.Msg.show({
                						title : '警告',
                						message : responseData.message,
                						buttons : Ext.Msg.OK,
                						icon : Ext.Msg.WARNING
                					});
                				}
                				me.clickSearch();
                			},
                			failure : function(response, opts) {
                				console.log('server-side failure with status code '
                						+ response.status);
                				Ext.Msg.show({
                					title : '错误',
                					message : response,
                					buttons : Ext.Msg.OK,
                					icon : Ext.Msg.error
                				});
                			}
                		};
                		// 这一句是提交
                		App.common.Ajax.request(config);
        		}
    		});
    	}
    	
    },
    
    openAddWindow: function(btn){
        var extWindow = Ext.create('App.supplier.supplierWindow',{
        	viewAction: 'add',
    		title: '新增'
    	});
        extWindow.down('form').down('radiogroup').setValue({status:'start_using'});
        extWindow.show();
    },
    
    openUpdateWindow: function(btn){
    	var records = Ext.ComponentQuery.query('supplierView')[0].getSelectionModel().getSelection();
    	var record = records[0];
        Ext.apply(record.data, {userKey: window.sessionStorage['userKey']});
        var extWindow = Ext.create('App.supplier.supplierWindow',{
        	viewAction: 'update',
    		title: '修改'
    	});
        extWindow.down('form').getForm().setValues(record.data);
        extWindow.down('form').down('radiogroup').setValue({status:record.data.status});
        extWindow.show();
    },

    clickSearch: function (btn) {
    	var searchList = Ext.ComponentQuery.query('supplierView')[0];
        searchList.down('pagingtoolbar').moveFirst();
        var store = searchList.getStore();
    	store.load();
    },
    clickResets:function(btn){
        btn.up('form').form.reset();
        this.clickSearch();
    },
    clickSave: function (btn) {
    	var me = this;
		var form = btn.up('window').down('form');
		if (!form.getForm().isValid()) {
            Ext.Msg.show({
                title : '提示',
                message : '有必填项未填写',
                buttons : Ext.Msg.OK,
                icon : Ext.Msg.INFO
            });
			return;
		}
		var data = form.getValues();
		var url = 'supplier/save.do'; // 默认新增
		var viewAction = btn.up('window').viewAction;
		if (viewAction == "update") {
			url = 'supplier/update.do'; // 则执行修改
		}
		data.userKey= window.sessionStorage['userKey'];
		data.operationSubjectId = window.sessionStorage['operationSubjectId'];
		data.status = form.down('radiogroup').getValue().status;
		var config = { // 这一句是定义提交的内容 和返回值处理
			btn:btn,
			url : url,
			data : data,
			success : function(response, opts) {
				var responseData = Ext.decode(response.responseText);
				if (responseData.result == "success") {
						Ext.Msg.show({
							title : '提示',
							message : responseData.message,
							buttons : Ext.Msg.OK,
							icon : Ext.Msg.INFO
						});
				} else {
					Ext.Msg.show({
						title : '警告',
						message : responseData.message,
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});
				}
				form.up('window').close();
				me.clickSearch();
			},
			failure : function(response, opts) {
				console.log('server-side failure with status code '
						+ response.status);
				Ext.Msg.show({
					title : '错误',
					message : response,
					buttons : Ext.Msg.OK,
					icon : Ext.Msg.error
				});
			}
		};
		// 这一句是提交
		App.common.Ajax.request(config);
    },
    
    clickDelete : function(btn){
    	var me = this;

    	Ext.Msg.confirm('提示信息', '确定要删除吗？', function(c) {
    		if(c == 'yes'){
    			var records = Ext.ComponentQuery.query('supplierView')[0].getSelectionModel().getSelection();
    	    	var record = records[0];
    	    	var url = 'supplier/delete.do'; 
    	    	var data = {supplierId: record.data.supplierId, status:'stop_using'};
    	    	var config = { // 这一句是定义提交的内容 和返回值处理
    	    			url : url,
    	    			data : data,
    	    			success : function(response, opts) {
    	    				var responseData = Ext.decode(response.responseText);
    	    				if (responseData.result == "success") {
    	    						Ext.Msg.show({
    	    							title : '提示',
    	    							message : responseData.message,
    	    							buttons : Ext.Msg.OK,
    	    							icon : Ext.Msg.INFO
    	    						});
    	    				} else {
    	    					Ext.Msg.show({
    	    						title : '警告',
    	    						message : responseData.message,
    	    						buttons : Ext.Msg.OK,
    	    						icon : Ext.Msg.WARNING
    	    					});
    	    				}
    	    				me.clickSearch();
    	    			},
    	    			failure : function(response, opts) {
    	    				console.log('server-side failure with status code '
    	    						+ response.status);
    	    				Ext.Msg.show({
    	    					title : '错误',
    	    					message : response,
    	    					buttons : Ext.Msg.OK,
    	    					icon : Ext.Msg.error
    	    				});
    	    			}
    	    		};
    	    		// 这一句是提交
    	    		App.common.Ajax.request(config);
    		}
    	});
    	
    }
});
