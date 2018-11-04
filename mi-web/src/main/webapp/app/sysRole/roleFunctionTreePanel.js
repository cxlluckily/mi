Ext.define('App.sysRole.roleInfoModel',{
	extend: 'Ext.data.TreeModel',
    idProperty : 'functionTreeId',
    fields: [
        {
            name: 'dataNodeId',
            type: 'int'
        },
        {
            name: 'treeName',
            type: 'string'
        },
        {
            name: 'leaf',
            type: 'boolean'
        },
        {
            name: 'expanded',
            type: 'boolean'
        },
        {
            name: 'dataParentNodeId',
            type: 'int'
        },
        {
            name: 'treeType',
            type: 'string'
        },
        {
            name: 'checked',
            type: 'boolean'
        }
    ]
});

Ext.define('App.sysRole.roleInfoStore',{
	extend : 'Ext.data.TreeStore'
});

Ext.define('App.sysRole.roleFunctionTreePanel',{
	extend: 'Ext.tree.Panel',
    hideHeaders:true,
    height: 300,
    width: '100%',
    xtype : 'roleFunctionTree',
    rootVisible: false,
    checkPropagation: 'up', // none up down both
    store : {
    	xclass : 'App.sysRole.roleInfoStore'
    },
    style : {
    	'panel-header-line-height': '10px'
    },
    recursionParent:function(node){
        var me = this;
        if (node.id != 'root') {
            var check = false;
            Ext.each(node.childNodes, function (item) {
                if(item.data.checked){
                    check = true;
                }
            });
            node.data.checked = check;
            if (node.parentNode.id != 'root') {
                me.recursionParent(node.parentNode);
            }
        }
        node.commit();
    },
    recursionChildren:function(node,checked){
        var me = this;
        node.data.checked = checked;
        if (node.childNodes.length > 0) {
            Ext.each(node.childNodes, function (item) {
                me.recursionChildren(item, checked)
            });
        }
        node.commit();
    },
    listeners:{
        beforecellclick:function(component, td, cellIndex, record, tr, rowIndex, e, eOpts){
            record.data.beforeChecked = record.data.checked;
        },
        cellclick:function(component, td, cellIndex, record, tr, rowIndex, e, eOpts){
            var me = this;
            var change = record.data.beforeChecked !== record.data.checked;
            if(change){
                if(record.parentNode.id != 'root'){
                    me.recursionParent(record.parentNode);
                }
                if(record.childNodes.length > 0){
                    me.recursionChildren(record, record.data.checked);
                }
            }
        },
    }
});