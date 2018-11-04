Ext.define('App.roleAuthorization.roleAuthorizationWarehouseModel',{
	extend: 'Ext.data.Model',
	fields : [ 
		{
			name : 'warehouseName',
			type : 'string'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'sectionName',
			type : 'string'
		}, {
			name : 'warehouseManager',
			type : 'string'
		}, {
			name : 'headPerson',
			type : 'string'
		}, {
			name : 'contactNumber',
			type : 'string'
		}, {
			name : 'location',
			type : 'string'
		}
	]
});

Ext.define('App.roleAuthorization.roleAuthorizationWarehouseList', {
    extend: 'Ext.tree.Panel',
    xtype: 'authWarehouseList',
    requires: [
        'Ext.grid.column.Check',
        'App.common.selectStore',
        'App.roleAuthorization.roleAuthorizationController',
        'App.roleAuthorization.roleAuthorizationWarehouseModel'
    ],
    controller: {
        xclass: 'App.roleAuthorization.roleAuthorizationController'
    },
    store: {
    	model: 'App.roleAuthorization.roleAuthorizationWarehouseModel'
    },
    rowLines: true,
    lines: true,
    height: 200,
    reserveScrollbar: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    columns:
         [ {
		xtype : 'treecolumn',
		text : '仓库名称',
		dataIndex : 'warehouseName',
		sortable : true,
		width: 200
	}, {
		xtype: 'checkcolumn',
		header : '授权',
		width : 100,
		dataIndex: 'verified',
        headerCheckbox: true,
        stopSelection: false
	}/*, {
		text : '仓库等级',
		dataIndex : 'description',
		flex : 1
	}*/, {
		text : '工区',
		dataIndex : 'sectionName',
		width : 100
	}, {
		text : '管理员',
		dataIndex : 'warehouseManager',
		width : 100
	}, {
		text : '负责人',
		dataIndex : 'headPerson',
		width : 150,
		align : 'center'
	// formatter: 'this.formatHours'
	}, {
		text : '联系电话',
		dataIndex : 'contactNumber',
		width : 150
	}/*, {
		text : '位置',
		dataIndex : 'location',
		width : 100
	}*/],
    listeners : {
    	/*beforerender : function(me){
	        var config = {
        		url: ctx + 'warehouse/getPagerInfo.do',
        		data:{
                    operationSubjectId:window.sessionStorage['operationSubjectId'],
                    userKey:window.sessionStorage['userKey'],
                    start:0,
                    limit: 1000,
                    status: 'start_using'
                },
	            success: function (response) {
	                var obj = Ext.decode(response.responseText);
	                if (obj.result == "success") {
	                    var root = {
	                        expanded: true
	                    };
	                    root.children = obj.data;
//	                    var p = Ext.ComponentQuery.query('authWarehouseList')[0];
	                    me.getStore().setRoot(root);
	                    me.expandAll();
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
    	}*/
    }
})
;