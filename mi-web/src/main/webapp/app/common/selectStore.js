Ext.define('App.common.selectStore', {
    extend: 'Ext.data.Store'
});
// 运营主体，公司
Ext.define('App.common.operationSubjectListStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: 'operation_subject'
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

//  货架管理下拉列表( 所属仓库 )
Ext.define('App.common.globalWarehouseListStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: 'warehouse',
                operationSubjectId: 1
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            var shelfArray = [];
            if (store.includeAll) {
                for (var i = 0; i < records.length; i++) {
                    shelfArray.push(records[i].id);
                }
                records.unshift({id: shelfArray, name: '全部'});
                store.setData(records);
            }
        }
    }
});


//  仓库管理下拉列表( 所属工区 )
Ext.define('App.common.globalWorkAreaListStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: 'work_section'
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                records.unshift({id: 0, name: '全部'});
                store.setData(records);
            }
        }
    }
});
// 角色
Ext.define('App.common.roleListStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: 'role'
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            records.unshift({id: 0, name: '全部'});
            store.setData(records);
        }
    }
});


// 个人信息
Ext.define('App.common.getOneUserInfo', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'user/getOneUserInfo.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {};
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});


// 机构,部门
Ext.define('App.common.orgOrganizationListStore', {
    extend: 'Ext.data.TreeStore',
    proxy: {
        type: 'ajax',
        url: ctx + 'data/loadTree.do',
        actionMethods: {
            read: 'POST',
            create: 'POST',
            update: 'POST',
            destroy: 'POST'
        },
        paramsAsJson: true,
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    },
    autoLoad: true,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                userKey: window.sessionStorage['userKey'],
                operationSubjectId: window.sessionStorage['operationSubjectId'],
                type: 'organization'
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

Ext.define('App.common.orgOrganizationListModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'code',
            type: 'string'
        },
        {
            name: 'text',
            type: 'string'
        }
    ]
});

// 备件，需要检索的下拉列表
// 使用时需要设置 查询内容 searchContent
Ext.define('App.common.getSparePartList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'baseData/getSparePartList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    searchContent: "",
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                searchContent: store.searchContent
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
//维修信息下的弹出窗口获取备件信息
Ext.define('App.common.getSparePartLList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'partBreakdownRepairInfo/getSparePartDDLList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    searchContent: "",
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                searchContent: store.searchContent
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});


//故障信息下的弹出窗口获取备件信息
Ext.define('App.common.getSparePartDDLList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'partBreakdownRepairInfo/getSparePartDDLList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    searchContent: "",
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                searchContent: store.searchContent
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

//  备件，需要检索的下拉列表
//  获取备件下拉框数据
// Ext.define('App.common.getSparePartDDLList',{
//     extend: 'App.common.commonStore',
//     proxy: {
//         url: ctx + 'partBreakdownInfo/getSparePartDDLList.do',
//         reader: {
//             rootProperty: 'data'
//         },
//         extraParams:{
//             partName: ''
//         }
//     },
//     searchContent:"",
//     autoLoad: false,
//     listeners: {
//         beforeload: function (store, options) {
//         }
//     }
// });

//  备件，需要检索的下拉列表
//  获取备件下拉框数据getSparePartRepairDDLList
Ext.define('App.common.getSparePartRepairDDLList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'partBreakdownRepairInfo/getSparePartRepairDDLList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    searchContent: "",
    autoLoad: false
});


// 仓库
Ext.define('App.common.stockWarehouse', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: 'warehouse'
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

// 用户数据范围内仓库 列表
Ext.define('App.common.userStockWarehouseListModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'id',
            type: 'string'
        },
        {
            name: 'text',
            type: 'string'
        }
    ]
});

Ext.define('App.common.userStockWarehouseListStore', {
    extend: 'App.common.commonStore',
    model: "App.common.userStockWarehouseListModel",//字段限定类型，需要新增行数据时使用
    proxy: {
        url: ctx + 'data/getWarehousesByUserId.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: "warehouse",
                range: "warehouse",
                showMode: "list"
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                if (records.length > 0) {
                    var shelfArray = [];
                    for (var i = 0; i < records.length; i++) {
                        shelfArray.push(records[i].id);
                    }
                    var shelfString = shelfArray.join(',');
                    records.unshift({id: shelfString, text: '全部'});
                    store.setData(records);
                }
            }
        }
    }
});

// 根据仓库ID返回仓库父节点 列表
// 使用请设置store.proxy.extraParams.entityId
Ext.define('App.common.stockWarehouseParentListModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'id',
            type: 'string'
        },
        {
            name: 'text',
            type: 'string'
        }
    ]
});

Ext.define('App.common.stockWarehouseParentListStore', {
    extend: 'App.common.commonStore',
    model: "App.common.stockWarehouseParentListModel",//字段限定类型，需要新增行数据时使用
    proxy: {
        url: ctx + 'data/loadUpTree.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: "warehouse",
                range: "warehouse",
                showMode: "list"
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

// 获取供应商下拉列表
Ext.define('App.common.stockSupplierListStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/load.do',
        reader: {
            rootProperty: 'data'
        }
    },
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: "supplier"
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});

// 根据输入内容返回货架信息
Ext.define('App.common.getShelvesByContentStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'shelves/getShelvesInfoBySearchContent.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var searchContent = this.searchContent;
            if (searchContent == '请选择') {
                searchContent = '';
            }
            var new_params = {
                warehouseId: this.warehouseId,
                searchContent: searchContent
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});
// 区域
Ext.define('App.common.regionDDLStore', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'subject/getAllRegion.do',
        reader: {
            rootProperty: 'data'
        }
    }
});

// 获取一级设备类型
Ext.define('App.common.getEquipmentTypeFirst', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'repairApply/getEquipmentType.do',
        reader: {
            rootProperty: 'data'
        }
    },
    listeners: {
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                records.unshift({sparePartTypeId: 'all', categoryName: '全部'});
                store.setData(records);
            }
        }
    }
});

// 站点下某类型设备
Ext.define('App.common.getEquipmentListByStationAndSparePartType', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'repairApply/getEquipmentList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false
});

// 获取联系人列表
Ext.define('App.common.getConcatPeopleList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'repairApply/getConcatPeopleList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false
});

// 获取站点维修员列表
Ext.define('App.common.getRepairerInfo', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'repairApply/getRepairerInfo.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false
});

// 根据故障现象获取处理措施信息
Ext.define('App.common.getTreatmentInfoList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'repairApply/getTreatmentInfoList.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false
});

// 根据备件类型pid返回备件下拉框数据
Ext.define('App.common.getSparePartByCompose', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'deviceCompose/getSparePartByCompose.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true
});

// 根据备件类型id返回备件下拉框数据
Ext.define('App.common.getPartListBySparePartTypeId', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'deviceCompose/getPartListBySparePartTypeId.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false
});

// 根据userKey返回可盘库的用户列表
Ext.define('App.common.findUserByPermission', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'userPermission/findUserByPermission.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: true
});

// 根据userId返回用户负责仓库列表
Ext.define('App.common.getWarehousesByUserinfo', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'data/getWarehousesByUserinfo.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            var new_params = {
                type: "warehouse",
                range: "warehouse",
                showMode: "list"
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                if (records.length > 0) {
                    var shelfArray = [];
                    for (var i = 0; i < records.length; i++) {
                        shelfArray.push(records[i].id);
                    }
                    var shelfString = shelfArray.join(',');
                    records.unshift({id: shelfString, text: '全部'});
                    store.setData(records);
                }
            }
        }
    }
});

// 根据仓库ID返回有该仓库权限且有盘库节点的用户
Ext.define('App.common.findUserByWarehousePermission', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'userPermission/findUserByWarehousePermission.do',
        reader: {
            rootProperty: 'data'
        }
    },
    autoLoad: false,
    listeners: {
        beforeload: function (store, options) {
            // var new_params = {
            //     type: "warehouse",
            //     range: "warehouse",
            //     showMode: "list"
            // };
            // Ext.apply(store.proxy.extraParams, new_params);
        },
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                if (records.length > 0) {
                    var shelfArray = [];
                    for (var i = 0; i < records.length; i++) {
                        shelfArray.push(records[i].id);
                    }
                    var shelfString = shelfArray.join(',');
                    records.unshift({id: shelfString, text: '全部'});
                    store.setData(records);
                }
            }
        }
    }
});

// 获取线路（在线设备的线路，现在为所有线路，后续修改为该用户所属线路）
Ext.define('App.common.getAllLine', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'onlineEquipment/getAllLine.do',
        reader: {
            rootProperty: 'data'
        }
    },
    listeners: {
        load: function (store, records, successful, operation, eOpts) {
            if (store.includeAll) {
                records.unshift({lineId: '-1', lineName: '全部'});
                store.setData(records);
            }
        }
    }
});

// 根据线路获取站点（在线设备的站点，现在为所有站点，后续修改为该用户所属站点）
Ext.define('App.common.getStationListByLineId', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'onlineEquipment/getStationListByLineId.do',
        reader: {
            rootProperty: 'data'
        }
    }
});


// 根据线路获取站点（在线设备的站点，现在为所有站点，后续修改为该用户所属站点）
Ext.define('App.common.getPreinstallCommandDDL', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'command/getPreinstallCommandDDL.do',
        reader: {
            rootProperty: 'data'
        }
    }
});

// 获取线上设备（registType 1：未绑定 0||null 已绑定）
Ext.define('App.common.getOnlineEquipmentList', {
    extend: 'App.common.commonStore',
    proxy: {
        url: ctx + 'onlineEquipment/getOnlineEquipmentList.do',
        reader: {
            rootProperty: 'data.rows'
        }
    }
});
