import axios from 'axios'
import qs from 'qs';

const USERKEY = localStorage.getItem('userKey');

export default {
  // 获取盘库列表
  getInventoryTaskList(data) {
    const url = HOST + "/inventoryTask/getInventoryTaskList.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //新增盘库任务
  insertOne(data) {
    const url = HOST + "/inventoryTask/insertOne.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //新增盘库任务
  inventoryTaskUpdate(data) {
    const url = HOST + "/inventoryTask/updateOne.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  //获取库存详细信息
  getInventoryDetailEntity(id) {
    const url = HOST + "/inventoryTask/getInventoryDetailEntity.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        inventoryTaskId: id,
        userKey: USERKEY
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  //修改库存详细信息
  updateInventoryTask(data) {
    const url = HOST + "/inventoryTask/updateInventoryTask.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  //获取查询仓库详细信息
  getWarehousesByUser() {
    const url = HOST + "/data/getWarehousesByUserId.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
        type: "warehouse",
        range: "warehouse",
        showMode: "list"
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //查询领用信息
  requisitionInfor(data) {
    const url = HOST + "/applyInfo/getPagerInfoByUser.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //获取添加物品列表
  getSparePartList(data) {
    const url = HOST + "/baseData/getSparePartList.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //获取借用出库添加物品列表
  getSparePartInWarehouse(data) {
    const url = HOST + "/baseData/getSparePartInWarehouse.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //添加申请列表
  addApplyInfo(data) {
    const url = HOST + "/applyInfo/addApplyInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  updateApplyInfo(data) {
    const url = HOST + "/applyInfo/updateApplyInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //查看数据列表详情信息
  getApplyInfoByApplyId(data) {
    const url = HOST + "/applyInfo/getApplyInfoByApplyId.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //获取该用户所拥有权限的仓库
  deviceTypeDataAll(data) {
    const url = HOST + "/data/getWarehousesByUserId.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  /*获取该运营主体下的所有仓库*/
  getload(data) {
    const url = HOST + "/data/load.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  /*获取该运营主体下的所有仓库*/
  getloadUpTree(data) {
    const url = HOST + "/data/loadUpTree.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  /*获取审核信息列表*/
  getPagerInfoForAudit(data) {
    const url = HOST + "/applyInfo/getPagerInfoForAudit.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  //审核信息
  auditApplyInfo(data) {
    const url = HOST + "/applyInfo/auditApplyInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 我的任务信息列表
  getPendingTaskList(data) {
    const url = HOST + "/pendingTask/getPendingTaskList.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 更新我的任务查看状态
  updateTaskSatuts(data) {
    const url = HOST + "/pendingTask/updateTaskSatuts.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  /*出库管理*/
  getOutStockApplyInfo(data) {
    const url = HOST + "/outStockApply/getOutStockApplyInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },

  /*修改个人密码*/
  modifyPassword(data) {
    const url = HOST + "/personalInformation/modifyPassword.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  /*手机号验证码修改个人密码*/
  modifyPasswordbyphone(data) {
    const url = HOST + "/modifyPasswordbyphone.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  getUserInfoForPhone() {
    const url = HOST + "/user/getUserInfoForPhone.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 退出登录
  loginOut() {
    const url = HOST + "/wechat/loginOut.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 账号绑定
  bindWechatOpenId(data) {
    const url = HOST + "/wechat/bindWechatOpenId.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  /*库存查询列表*/
  getPagerDetailInfo(data) {
    const url = HOST + "/stockInfo/getPagerDetailInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
        ...data
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  /*获取备件类型信息*/
  getSparePartTypeInfo() {
    const url = HOST + "/sparePartType/getSparePartTypeInfo.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
        status: 'start_using'
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  getSparePartTypeKongGeList() {
    const url = HOST + "/sparePartType/getSparePartTypeKongGeList.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY,
        status: 'start_using'
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 获取供应商
  getSupplierList() {
    const url = HOST + "/data/load.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        type: "supplier",
        userKey: USERKEY,
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 获取货架列表
  getShelfNumberList(data) {
    const url = HOST + "/shelves/getShelvesInfoBySearchContent.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 新品入库保存
  addNewStockInApply(data) {
    const url = HOST + "/stockIn/addNewInApply.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 获取仓库下房间号
  getShelvesHouseList(data) {
    const url = HOST + "/shelves/getShelvesHouseList.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 获取房间号货柜货架
  getShelvesMap(data) {
    const url = HOST + "/shelves/getShelvesMap.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 借用出库操作
  insertBorrowOutStock(data) {
    const url = HOST + "/outStockApply/insertBorrowOutStock.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 根据备件类型ID获取备件列表
  getPartListBySparePartTypeId(data) {
    const url = HOST + "/deviceCompose/getPartListBySparePartTypeId.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 其他入库保存
  commitStockIn(data) {
    const url = HOST + "/stockIn/commitStockIn.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  // 根据二维码获取库存
  getStockByQrCode(data) {
    const url = HOST + "/outStockApply/getStockSparePartByqrCode.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //获取有盘库节点，用户列表
  findUserByPermission() {
    const url = HOST + "/userPermission/findUserByPermission.do";
    return new Promise(function (resolve) {
      axios.post(url, {
        userKey: USERKEY
      }).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
  //根据userId返回用户负责仓库
  getWarehousesByUserinfo(data) {
    const url = HOST + "/data/getWarehousesByUserinfo.do";
    return new Promise(function (resolve) {
      axios.post(url, data).then(function (response) {
        resolve(response.data)
      }).catch(function (error) {
        console.log(error);
      });
    })
  },
    // 记录 -- 领用，调拨，返还
    requisitionRecordInfor(data) {
      const url = HOST + "/applyInfo/getPagerInfoByWarehouse.do";
      return new Promise(function (resolve) {
        axios.post(url, data).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    //根据仓库ID返回有该仓库权限且有盘库节点的用户列表
    findUserByWarehousePermission(data) {
      const url = HOST + "/userPermission/findUserByWarehousePermission.do";
      return new Promise(function (resolve) {
        axios.post(url, data).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    }
}
