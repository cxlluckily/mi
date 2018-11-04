Ext.define('App.orgWorkSection.orgWorkSectionStationForm', {
    extend: 'Ext.form.Panel',
    // fullscreen: true,
    // layout: 'column',
    width: '100%',
    height:'100%',
    bodyStyle:{
        background:'#f6f6f6'
    },
    xtype: 'orgWorkSectionStationForm',
    fieldDefaults: {
        anchor: '100%',
        labelAlign: 'right',
        labelWidth: 70,
        padding: '10,10,10,10',
        width: '100%'
    },
    items: [
        {
            xtype: 'fieldset',
            layout: 'form',
            marginTop:'-10px',
            defaults: {
                anchor: '100%'
            },
            title: '站点',
            items:[
                {
                    xtype: 'checkboxgroup',
                    columns: 5,
                    // vertical: true,
                    items: [

                    ]
                }
            ]
        }
    ]
});
