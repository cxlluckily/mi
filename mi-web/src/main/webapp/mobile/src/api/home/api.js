  import axios from 'axios'
  import qs from 'qs';

  const USERKEY = localStorage.getItem('userKey');

  export default {
    // 首页 -- 站点 -- 初始化站点和关键字查询
    siteListDataAll(site) {
      const url = HOST + "/repairApply/getLineAndStationInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          searchContent: site
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 设备类型
    deviceTypeDataAll() {
      const url = HOST + "/repairApply/getEquipmentType.do";
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
    // 首页 -- 设备编码 -- 站点ID 线路ID 类型ID
    deviceCodingDataAll(paramObj) {
      const url = HOST + "/repairApply/getEquipmentList.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 故障现象 -- 编码ID
    faultPhenomenonDataAll(equipmentId) {
      const url = HOST + "/repairApply/getErrorPhenomenonByEquipmentId.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          equipmentId: equipmentId
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 设备报修提交
    repairSubmitDataAll(fromData) {
      const url = HOST + "/repairApply/addRepairApplyInfoPhone.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...fromData
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 根据扫描设备编码获取站点及类型信息
    scanningQrCodeDataAll(fromStr) {
      const url = HOST + "/operationsEquipment/getEquipmentEntity.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          qrCode: fromStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 全局图片上传
    updateDataAll(param) {
      const url = HOST + "/repairApply/fileUpload.do";
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }; //添加请求头
      return new Promise(function (resolve) {
        axios.post(url,
          param, config).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 通讯录
    addressBookDataAll(paramStr) {
      const url = HOST + "/repairApply/getConcatPeopleList.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          type: "phone",
          searchContent: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 派工
    dispatchingDataAll(paramObj) {
      const url = HOST + "/repairApply/getRepairApplyPageInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 派工记录
    dispatchingRecordDataAll(paramObj) {
      const url = HOST + "/repairApply/getPhoneRepairApplyPageInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 首页 -- 派工记录 -- 维修人员
    maintenancePersonnelDataAll(paramString) {
      const url = HOST + "/repairApply/getRepairerInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          stationId: paramString
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 获取单个派单详情
    SendSingleDetailsDataAll(paramStr) {
      console.log(HOST + "/repairApply/getApplyInfo.do")
      const url = HOST + "/repairApply/getApplyInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          maintenanceApplyId: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 发起派单
    SendSingleDataAll(paramObj) {
      const url = HOST + "/repairApply/appointRepairInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 修改派单人员
    SetSendSingleDataAll(paramObj) {
      const url = HOST + "/repairApply/setConcatPeople.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修初始化数据
    maintenanceInitDataAll(paramObj) {
      const url = HOST + "/repairApply/getRepairInfoPager.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          type: "phone",
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修接单
    orderDataAll(paramStr) {
      const url = HOST + "/repairApply/repairCheckIn.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          maintenanceApplyId: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修维修完成 更换部件
    PartsDataAll(paramStr) {
      const url = HOST + "/repairApply/getPartPairInInEquipment.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          equipmentId: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    qrCodePartsDataAll(paramStr) {
      const url = HOST + "/repairApply/getDrviceInforByQrCode.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          qrCode: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修维修完成 故障处理信息
    getBreakdownListDataAll(paramArr) {
      const url = HOST + "/repairApply/getBreakdownList.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          sparePartIds: paramArr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修维修完成 处理措施信息
    measuresDataAll(paramObj) {
      const url = HOST + "/repairApply/getTreatmentInfoList.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备维修维修完成 更换设别备
    replaceEquipmentDataAll(paramStr) {
      const url = HOST + "/repairApply/getSparePartsInBag.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          sparePartTypeId: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 评价
    starDataAll(paramObj) {
      const url = HOST + "/repairApply/repairEvaluation.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...paramObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 根据id获取背包备件
    sparePartsDataAll(paramStr) {
      const url = HOST + "/repairApply/getSparePartsInBag.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          sparePartTypeId: paramStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 维修完成
    completeDataAll(prompObj) {
      const url = HOST + "/repairApply/repairEquipmentForPhone.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 设备绑定 -- 绑定
    bindingDeviceDataAll(prompObj) {
      const url = HOST + "/onlineequipment/updateOnlineequipment.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    bindingQrCodeDataAll(prompStr) {
      const url = HOST + "/code/getQrCodeByQrCode.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          qrCode: prompStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 我的备件
    mySparePartsDataAll(prompObj) {
      const url = HOST + "/mySparePart/getMySparePartList.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 我的备件详情页
    mySparePartsDetailsDataAll(prompObj) {
      const url = HOST + "/stockInfo/GetMyPartPagerDetailInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 库存管理
    inventoryManagementDataAll(prompObj) {
      const url = HOST + "/stockInfo/getPagerInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 管理权限
    getPhoneTreeDataAll() {
      const url = HOST + "/user/getPhoneTree.do";
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
    // 入库查询仓库名称
    warehouseTreeDataAll() {
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
    // 入库初始页
    storageInitDataAllprompObj(prompObj) {
      const url = HOST + "/stockIn/getPagerInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    storageDetailsDataAllprompObj(prompObj) {
      const url = HOST + "/stockIn/getStockInInfoById.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 出库起始页
    outboundInitDataAllprompObj(prompObj) {
      const url = HOST + "/outStockApply/getOutStockApplyInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 出库详情
    outboundDetailsDataAllprompObj(prompObj) {
      const url = HOST + "/outStockApply/getApplyDetailInfoByoutStockInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 出库-物品添加
    addPartsDataAllprompObj(prompObj) {
      const url = HOST + "/outStockApply/getCanSendGoodsInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 出库-出库
    goOutboundDataAllprompObj(prompObj) {
      const url = HOST + "/outStockApply/useAndTransfeOutStockAndReturn.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    outboundQrCodeDataAllprompObj(prompObj) {
      const url = HOST + "/outStockApply/updateStockOutQrCode.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    validationQrCodeDataAllprompObj(prompStr) {
      const url = HOST + "/code/getQrCodeByQrCode.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          qrCode: prompStr
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    getCode(data)
    {
      const url = HOST + "/phoneCode.do";
      return new Promise(function (resolve) {
        axios.post(url, data).then(function (response) {
          resolve(response)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    bindWechatOpenIdByPhone(data)
    {
      const url = HOST + "/wechat/bindWechatOpenIdByPhone.do";
      return new Promise(function (resolve) {
        axios.post(url, data).then(function (response) {
          resolve(response)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
    // 库存查看详情获取备件详情
    getPagerstockPartDetailInfo(prompObj) {
      const url = HOST + "/stockInfo/getPagerstockPartDetailInfo.do";
      return new Promise(function (resolve) {
        axios.post(url, {
          userKey: USERKEY,
          ...prompObj
        }).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          console.log(error);
        });
      })
    },
  }
