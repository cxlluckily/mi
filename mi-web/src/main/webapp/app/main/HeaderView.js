Ext.define('App.main.HeaderView', {
	extend: 'Ext.container.Container',
	xtype: 'appHeader',
	id: 'app-header',
	title: '数字运维',
	height: 52,
	layout: {
		type: 'hbox',
		align: 'middle'
	},
	initComponent: function () {
		document.title = this.title;
		this.items = [{
			xtype: 'component',
			id: 'app-header-logo'
		}, {
			xtype: 'component',
			id: 'app-header-title',
			html: this.title,
			flex: 1
		}, {
			xtype: 'component',
			id: 'app-header-user',
			html: '<p style = ' +
					'"color: white; cursor: pointer; font-size: 14px; font-weight: 700;">' +
					'用户名&nbsp;:&nbsp;'+ window.sessionStorage['realName'] +'</p>',
			style: {
				textAlign: 'right',
				marginRight: '14px',
				float: 'right'
			}
		},{
			xtype: 'component',
			id: 'app-header-exit',
			html: '<a href="javascript:void();" onclick="logout()" style = ' +
					'"color: white; cursor: pointer; font-size: 14px; font-weight: 700; text-decoration:none;">' +
					'退出登录</a>',
			style: {
				textAlign: 'right',
				marginRight: '14px',
				float: 'right'
			}
		}, {
			xtype: 'profileSwitcher'
		}];
		this.callParent();
	}
});