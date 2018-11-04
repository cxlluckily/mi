Ext.define('App.sysRole.sysRoleController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.sysRoleController',
    refViews:{
        addUrl : 'roleInfo/addRoleInfo.do', // 新增
        updateUrl : 'roleInfo/updateRoleInfo.do', // 修改
        deleteUrl : 'roleInfo/deleteRoleInfo.do' // 删除
    },
	control : {
		'sysRoleView button[action="search"]' : {
			click : 'clickSearch' // 搜索按钮
		},
		'sysRoleView button[action="add"]' : {
			click : 'clickAdd'
		},
		'sysRoleView button[action="update"]' : {
			click : 'clickUpdate'
		},
		'sysRoleView button[action="delete"]' : {
			click : 'clickDelete'
		},
        'sysRoleView button[action="look"]' : {
            click : 'clickLook'
        },
		'sysRoleView [actioncolumn]' : {
			click : 'actionFn'
		},
		'sysRoleWindow button[action="save"]' : {
			click : 'clickSave'
		}
	},
	clickSearch : function(btn) {
		var pageList = Ext.ComponentQuery.query('sysRoleView')[0];
		pageList.getStore().load();
	},
    clickResets:function(btn){
		btn.up('form').reset();
        this.clickSearch()
	},
	actionFn : function(grid, rowIndex, colIndex, item, e, record) {
		var flag = item.flag;
		if (flag == 'add') {
			var id = record.data.parentOrgId;
			var window = Ext.create('App.sysRole.sysRoleWindow', {
				title : item.tooltip,
				viewAction : flag
			});
			window.down('form').getForm().setValues({
				parentOrgId : id,
				status : 'start_using'
			});
			window.show();
		}
	},
	// 新增打开框写在这里
	clickAdd : function(btn) {
		var window1 = Ext.create('App.sysRole.sysRoleWindow', {
            params: {
                title : btn.text,
                viewAction : btn.action,
                roleId : 0,
                iconCls:btn.iconCls
            }
		});
		window1.down('form').down('radiogroup').setValue({status:'start_using'});
		window1.show();            
	},

	// 修改
	clickUpdate : function(btn) {
		// 主要在这里要区分出来才行
		var record = this.getView().getSelection()[0];
		if (record) {
			var window = Ext.create('App.sysRole.sysRoleWindow', {
                params: {
                    title : btn.text,
                    viewAction : btn.action,
                    roleId : record.data.roleId,
                    iconCls:btn.iconCls
                }
			});
			window.down('form').getForm().setValues(record.data);
			window.down('form').down('radiogroup').setValue({status:record.data.status});
			window.show();
		} else {
			Ext.Msg.show({
				title : '警告',
				message : '请选择一条记录进行修改！',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING
			});
		}
	},
	clickLook:function(btn){
        var record = this.getView().getSelection()[0];
        if (record) {
            var window = Ext.create('App.sysRole.sysRoleLookWindow', {
                params: {
                    title : btn.text,
                    viewAction : btn.action,
                    roleId : record.data.roleId,
                    iconCls:btn.iconCls
                }
            });
            window.down('form').getForm().setValues(record.data);
            window.show();
        } else {
            Ext.Msg.show({
                title : '警告',
                message : '请选择一条记录进行查看！',
                buttons : Ext.Msg.OK,
                icon : Ext.Msg.WARNING
            });
        }
	},
	// 删除
	clickDelete : function(grid, rowIndex, colIndex, item, e, record) {

		var record = this.getView().getSelection()[0];
		if (!record) {
			Ext.Msg.show({
				title : '警告',
				message : '请选择一条记录进行删除！', // 走到这了
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.INFO
			});
			return;
		}
		var me = this;
		var id = record.data.roleId; 
		Ext.Msg.confirm('提示信息', '确定要删除吗？', function(c) {
			if (c == 'yes') {
				var url11 = ctx
						+ 'roleInfo/deleteRoleInfo.do';
				var data = {
					"userKey" : window.sessionStorage['userKey'],
					"roleId" : id
				};
				var config = { // 这一句是定义提交的内容 和返回值处理
					url : url11,
					data : data,
					success : function(response, opts) {
						var responseData = Ext.decode(response.responseText);
						if (responseData.result == "success") {
							Ext.Msg.show({
								title : '提示',
								message : '删除成功！',
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.INFO
							});
							me.clickSearch();
						} else {
							Ext.Msg.show({
								title : '警告',
								message : responseData.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});
						}
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
				var result = App.common.Ajax.request(config);

			}
		});

	},
	clickSave : function(btn) {
		var me = this;
		var form = btn.up('window').down('form');
		/*var valid = form.down('textfield[name="valid"]').getValue();
		if(!valid){
			Ext.Msg.show({
				title : '警告',
				message : '验证的字段已存在!',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING
			});
		}*/
		if (!form.getForm().isValid()) {
			return;
		}
		var data = form.getValues();
		var url = 'roleInfo/addRoleInfo.do'; // 默认新增
		var viewAction = btn.up('window').params.viewAction;
		if (viewAction == "update") {
			url = 'roleInfo/updateRoleInfo.do'; // 则执行修改
		}
		var manageTree = btn.up('window').down('treepanel[itemId="manageTree"]');
		var manageCheckeds = manageTree.getChecked();
		var functionTreeIds = [];
		if(manageCheckeds.length > 0){
			for(var i = 0; i < manageCheckeds.length ; i++){
				var node = manageCheckeds[i];
				var nodeId = node.data.dataNodeId;
				functionTreeIds.push(nodeId);
			}
		}
		var appFunctionTreeids = [];
		var appTree = btn.up('window').down('treepanel[itemId="appTree"]');
		var appCheckeds = appTree.getChecked();
		if(appCheckeds.length > 0){
			for(var i = 0; i < appCheckeds.length ; i++){
				var node = appCheckeds[i];
				var nodeId = node.data.dataNodeId;
				functionTreeIds.push(nodeId);
			}
		}
		data.functionTreeIds = functionTreeIds;
		data.userKey= window.sessionStorage['userKey'];
		var config = {
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
                        message:responseData.message,
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
    recursion: function (data) {
        var me = this;
        var newChildren = [];
        Ext.each(data, function (item) {
            if(item.checked){
            	delete item.checked;
                if(item.children&&item.children.length>0){
                    item.children = me.recursion(item.children);
                }
                newChildren.push(item);
			};
        });
        return newChildren;
    }
});