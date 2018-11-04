Ext.define('App.dataDict.dataDictController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.dataDictController',
	refViews:{
    	view:'dataDictView'
	},
    control: {
        'dataDictView button[action="add"]': {
            click: 'openAddWindow'
        },
        'dataDictView button[action="update"]': {
            click: 'openUpdateWindow'
        },
        'dataDictView button[action="delete"]': {
            click: 'clickDelete'
        },
        'dataDictWindow button[action="save"]': {
            click: 'clickSave'
        }
        
    },
    
    actionFn : function(grid, rowIndex, colIndex, item, e, record){
    	var flag = item.flag;
    	var me = this;
    	if(flag == 'update'){
            Ext.apply(record.data, {userKey: window.sessionStorage['userKey']});
            var extWindow = Ext.create('App.dataDict.dataDictWindow',{
            	viewAction: 'update',
        		title: '编辑'
        	});
            extWindow.down('form').getForm().setValues(record.data);
            extWindow.show();
    	}
    	if(flag == 'delete'){
    		Ext.Msg.confirm('提示信息', '确定要删除吗？', function(c) {
        		if(c == 'yes'){
        			var url = 'dataDict/deleteDataDict.do'; 
                	var data = {dataDictionaryId: record.data.dataDictionaryId, status:'stop_using'};
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
        var extWindow = Ext.create('App.dataDict.dataDictWindow',{
        	viewAction: 'add',
    		title: '新增'
    	});
        extWindow.down('form').down('radiogroup').setValue({status:'start_using'});
        extWindow.show();
    },
    
    openUpdateWindow: function(btn){
    	var records = Ext.ComponentQuery.query('dataDictView')[0].getSelectionModel().getSelection();
    	if(records.length>0){
            var record = records[0];
		}else{
            Ext.Msg.show({
                title : '提示',
                message : '没有选择修改的数据',
                buttons : Ext.Msg.OK,
                icon : Ext.Msg.INFO
            });
    		return;
		}
        Ext.apply(record.data, {userKey: window.sessionStorage['userKey']});
        var extWindow = Ext.create('App.dataDict.dataDictWindow',{
        	viewAction: 'update',
    		title: '编辑'
    	});
        extWindow.down('form').getForm().setValues(record.data);
        extWindow.down('form').down('radiogroup').setValue({status:record.data.status});
        extWindow.show();
    },

    clickSearch: function (btn) {
        var pageList = Ext.ComponentQuery.query(this.refViews.view)[0];
        pageList.down('pagingtoolbar').moveFirst();
        pageList.getStore().load();
    },
    clickResets:function(btn){
		btn.up('form').form.reset();
		this.clickSearch(btn);
	},
    clickSave: function (btn) {
    	var me = this;
		var form = btn.up('window').down('form');
		if (!form.getForm().isValid()) { // 验证表单 , 如果为空, 不让发送请求
			return;
		}
		var data = form.getValues();
		var url = 'dataDict/addDataDict.do'; // 默认新增
		var viewAction = btn.up('window').viewAction;
		if (viewAction == "update") {
			url = 'dataDict/updateDataDict.do'; // 则执行修改
		}
		data.userKey= window.sessionStorage['userKey'];
		data.operationSubjectId = window.sessionStorage['operationSubjectId'];
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
					return;
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
    			var records = Ext.ComponentQuery.query('dataDictView')[0].getSelectionModel().getSelection();
    	    	var ids = '';
    	    	for(var i = 0 ; i < records.length; i++){
    	    		var record = records[i];
    	    		var id = record.data.dataDictionaryId;
    	    		if(ids.length > 0){
    	    			ids += ',';
    	    		}
    	    		ids += id;
    	    	}
    	    	var url = 'dataDict/deleteBatch.do'; 
    	    	var data = {ids: ids, status:'stop_using'};
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
