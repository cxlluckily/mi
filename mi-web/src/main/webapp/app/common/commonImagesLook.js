Ext.define('App.common.commonImagesLook', {
    extend: 'Ext.panel.Panel',
    xtype: 'commonImagesLook',
    requires: [
        'Ext.toolbar.TextItem',
        'Ext.view.View',
        'Ext.ux.BoxReorderer',
        'Ext.ux.DataView.Animated'
    ],
    style: {
        'border': '1px solid #5fa2dd'
    },
    layout: 'fit',
    width: '99%',
    height: 250,
    tbar: {
        // plugins: {
        //     ptype: 'boxreorderer',
        //     listeners: {
        //         drop: 'updateStoreSorters'
        //     }
        // },

        // defaults: {
        //     listeners: {
        //         changeDirection: 'updateStoreSorters'
        //     }
        // },

        // items: [
        //     {
        //         xtype: 'button',
        //         text: '删除',
        //         action:'delete'
        //     }
        // ]
    },

    items: {
        xtype: 'dataview',
        reference: 'dataview',
        plugins: {
            ptype: 'ux-animated-dataview'
        },
        autoHeight: true,
        itemSelector: 'div.dataview-multisort-item',
        tpl: [
            '<tpl for=".">',
            '<div class="dataview-multisort-item">',
            '<img src="{picUrl}" width="150" height="150"/>',
            '</div>',
            '</tpl>',
        ],
        listeners: {
            beforerender: function (component) {
                let parent = component.up('commonImagesLook');
                let data = component.getStore().getData();
                if (data.length == 0) {
                    component.up('commonImagesLook').setHidden(true);
                } else {
                    let row = Math.ceil(data.length / 3);
                    let height = row * 186 + 58;
                    parent.height = height;
                }
            },
            afterrender: function (component) {
                component.el.dom.classList.add('commonImagesLook-img-box');
            }
        },
        store: {
            fields: ['picUrl'],
            data: []
        }
    }
});