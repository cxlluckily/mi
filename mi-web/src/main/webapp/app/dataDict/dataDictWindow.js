Ext.define('App.dataDict.dataDictForm', {
	extend : 'Ext.form.Panel',
	xtype : 'dataDictForm',
	fullscreen : true,
	layout : 'form',
	buttonAlign : 'center',
	controller : {
		xclass : 'App.dataDict.dataDictController'
	},
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth: 70,
		anchor: '90%'
	},
	items : [{
		xtype : 'fieldset',
	    items: [{
	    	xtype : 'textfield',
			name : 'dataDictionaryId',
			hidden: true
		}, {
	    	xtype : 'textfield',
			name : 'dataLabel',
			fieldLabel : '<font color="red">*</font>标签类型',
			labelAlign : 'right',
			allowBlank : false,
            listeners: {
                beforerender: function () {//渲染
                    var extWindow = this.up('window');
                    if (extWindow.viewAction == 'update') {
                        this.readOnly = true;
                        this.fieldLabel = '标签类型';
                    }
                }
            }
		}, {
	    	xtype : 'textfield',
			name : 'code',
			fieldLabel : '<font color="red">*</font>编码',
			labelAlign : 'right',
			allowBlank : false,
            listeners: {
                beforerender: function () {//渲染
                    var extWindow = this.up('window');
                    if (extWindow.viewAction == 'update') {
                        this.readOnly = true;
                        this.fieldLabel = '用户名';
                    }
                }
            }
		}, {
	    	xtype : 'textfield',
			name : 'name',
			fieldLabel : '<font color="red">*</font>名称',
			labelAlign : 'right',
			allowBlank : false,
            listeners: {
                beforerender: function () {//渲染
                    var extWindow = this.up('window');
                    if (extWindow.viewAction == 'update') {
                        this.readOnly = true;
                        this.fieldLabel = '用户名';
                    }
                }
            }
			
		}, {
	        xtype: 'radiogroup',
	        fieldLabel: '状态',
	        columns: 2,
	        vertical: true,
	        items: [
	            { boxLabel: '可用', name: 'status', inputValue: 'start_using'},
	            { boxLabel: '不可用', name: 'status', inputValue: 'stop_using'}
	        ]
		}, {
	    	xtype : 'textfield',
			name : 'sort',
			fieldLabel : '<font color="red">*</font>排序号',
			labelAlign : 'right',
			allowBlank : false,
            regex:/^\d+$/,
            regexText:'只能输入数字'
		}, {
	    	xtype : 'textareafield',
			name : 'remark',
			fieldLabel : '描述',
			labelAlign : 'right',
			allowBlank : true,
			maxLength:200,
			maxLengthText:'最多输入200个字'
		}]
	}]
});

// 测试
Ext.define('App.dataDict.dataDictWindow', {
	extend : 'Ext.window.Window',
	width : 450,
	height: 450,
	title : '{title}',
	viewAction: '{viewAction}',
	iconCls : '{optAction == "add"?"fa-plus-circle":"fa-edit"}',
	//resizable : true,
	draggable : true,
	collapsible : true,
	closeAction : 'destroy',
	closable : true,
	modal : true,
	//autoRender : true,
	buttonAlign : "center",
	xtype : 'dataDictWindow',
	controller : {
		xclass : 'App.dataDict.dataDictController'
	},
	items : [ {
		xtype : 'dataDictForm'
	} ],
	buttons : [ {
		text : '保存',
		action : 'save'
	}, {
		text : '取消',
		handler : function(a) {
			this.up('window').close();
		}
	} ]
});
