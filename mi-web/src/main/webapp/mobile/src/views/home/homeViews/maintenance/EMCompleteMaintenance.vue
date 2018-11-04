<template>
  <div id='EMCompleteMaintenance'>
    <div class="EMCompleteMaintenance-main">
      <div class="EMCompleteMaintenance-site">
        <GlobalTroubleNumber :ticketNumberData="childTicketNumberData"></GlobalTroubleNumber>
      </div>
      <div class="EMCompleteMaintenance-process">
        <HomeProcess :processData="childProcessData"></HomeProcess>
      </div>
      <div class="EMCompleteMaintenance-situation">
        <div class="situation-title">
          <p class="EMCompleteMaintenance-GTitle">设备维修情况</p>
        </div>
        <div class="situation-parts">
          <div class="parts-replace">
            <div class="replace-title">
              <p>是否更换部件?</p>
              <p>具有唯一标识的部件需要扫描确认</p>
            </div>
            <div class="replace-btn">
              <button @click="btnParts">更换部件</button>
              <button @click="btnScanning($event)">扫码选件</button>
            </div>
          </div>
          <div class="parts-newOld">
            <!-- 更换备件 , 新旧 -->
            <ul>
              <!-- <li v-for="oldPartsItem in oldPartsData" :key="oldPartsItem.replaceCount">
                                    <div v-if="oldPartsItem.inventoryType == 'unique'" :class="[oldPartsItem.inventoryType == 'notUnique'?'newOld-replaceIcon2':'newOld-replaceIcon1']"></div>
                                    <div class="newOld-replace" :class="{'notUniqueMr':oldPartsItem.inventoryType == 'notUnique'}">
                                        <div class="replace-parts">
                                            <div class="parts-parts">
                                                <div class="parts-new">
                                                    <i></i>
                                                    <p>{{oldPartsItem.newPartName}}</p>
                                                </div>
                                                <p class="parts-num"><span>{{oldPartsItem.replaceCount}}</span>个</p>
                                            </div>
                                        </div>
                                        <div class="parts-only">
                                            <p>是否唯一标识:
                                                <span>{{oldPartsItem.inventoryType == "notUnique"?'否':'是'}}</span>
                                            </p>
                                            <p v-if="oldPartsItem.inventoryType == 'unique'">标识号:
                                                <span>{{oldPartsItem.newQrCode}}</span>
                                            </p>
                                        </div>
                                        <div class="parts-oldSweep" v-if="oldPartsItem.inventoryType == 'unique'">
                                            <div class="oldSweep-old">
                                                <i></i>
                                                <p>旧件标识</p>
                                            </div>
                                            <div class="oldSweep-sweep">
                                                <input type="text" unselectable="on" onfocus="this.blur()" :value="oldPartsItem.oldQrCode" readonly>
                                            </div>
                                        </div>
                                    </div>
                                </li> -->
              <li v-for="(newOldItem, index) in listDataArr" :key="newOldItem.userDeviceId">
                <div v-if="newOldItem.inventoryType == 'unique'" :class="[newOldItem.inventoryType == 'notUnique'?'newOld-replaceIcon2':'newOld-replaceIcon1']"></div>
                <div class="newOld-replace" :class="{'notUniqueMr':newOldItem.inventoryType == 'notUnique'}">
                  <div class="replace-parts">
                    <div class="parts-parts">
                      <div class="parts-new">
                        <i></i>
                        <p>{{newOldItem.partName}}</p>
                      </div>
                      <p class="parts-num"><span>{{newOldItem.replaceCount}}</span>个</p>
                    </div>
                    <div class="parts-delete" :data-userDeviceId="newOldItem.userDeviceId" :data-index="index" @click="partsDelete($event)">
                      <i></i><span>删除</span>
                    </div>
                  </div>
                  <div class="parts-only">
                    <p>是否唯一标识:
                      <span>{{newOldItem.inventoryType == "notUnique"?'否':'是'}}</span>
                    </p>
                    <p v-if="newOldItem.inventoryType == 'unique'">标识号:
                      <span>{{newOldItem.qrCode}}</span>
                    </p>
                  </div>
                  <div class="parts-oldSweep" v-if="newOldItem.inventoryType == 'unique'">
                    <div class="oldSweep-old">
                      <i></i>
                      <p>旧件标识</p>
                    </div>
                    <div class="oldSweep-sweep">
                      <!-- <input type="text" :value="newOldItem.oldQrCode"> -->
                      <input type="text" @input="qrCodeBinding($event)" :value="newOldItem.oldQrCode" :data-index="index">
                    </div>
                    <div class="sweep-icon" @click="QrCodngsweep($event)" :data-index="index">
                      <i></i>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div class="parts-fault">
          <p class="EMCompleteMaintenance-GTitle">
            故障现象</p>
          <div class="fault-check">
            <GlobalCheckbox :checkboxData="GlobalCheckboxData" @checkboxCurrentType="getCheckboxFault">
            </GlobalCheckbox>
          </div>
        </div>
        <div class="parts-faultSupplement">
          <p class="EMCompleteMaintenance-GTitle">
            故障现象补充</p>
          <div class="fault-check">
            <GlobalCheckbox :checkboxData="GlobalCheckboxSupplemenData" @checkboxCurrentType="getCheckboxFaultSupplement">
            </GlobalCheckbox>
          </div>
        </div>
        <div class="parts-description">
          <div class="description-title">
            <p class="EMCompleteMaintenance-GTitle">故障描述</p>
            <!-- <span>( 最多可输入200个汉子 )</span> -->
          </div>
          <textarea class="description-textBox" placeholder="请输入故障描述" v-model="faultTextTextBox"></textarea>
        </div>
        <div class="parts-results">
          <p class="EMCompleteMaintenance-GTitle">维修结果</p>
          <div class="results-radio">
            <label for="radioYes" class="radio-repair" @click="radio = 'radioRepair'">
                                                                                                                                                      <span :class="{'selectedRadio': radio == 'radioRepair'}"></span>
                                                                                                                                                      修复
                                                                                                                                                      <input type="radio"
                                                                                                                                                              name="repair"
                                                                                                                                                              id="radioYes"
                                                                                                                                                              value="yes"
                                                                                                                                                              v-model="repairText"
                                                                                                                                                              checked>
                                                                                                                                                  </label>
            <label for="radioNot" class="radio-repair" @click="radioNotRepair">
                                                                                                                                                      <span :class="{'selectedRadio': radio == 'radioNotRepair'}"></span>
                                                                                                                                                      未修复
                                                                                                                                                      <input type="radio"
                                                                                                                                                              name="repair"
                                                                                                                                                              value="no"
                                                                                                                                                              v-model="repairText"
                                                                                                                                                              id="radioNot">
                                                                                                                                                  </label>
          </div>
        </div>
        <div class="parts-measures" @click="measuresFun">
          <p class="EMCompleteMaintenance-GTitle">处理措施</p>
          <textarea v-model="measuresText" unselectable="on" onfocus="this.blur()" readonly></textarea>
        </div>
        <div class="parts-description">
          <div class="description-title">
            <p class="EMCompleteMaintenance-GTitle">处理描述</p>
            <!-- <span>( 最多可输入200个汉子 )</span> -->
          </div>
          <textarea class="description-textBox" v-model="descriptionText" placeholder="请输入处理描述">
                                                                                                                                                                                </textarea>
        </div>
        <div class="parts-uploadImg">
          <div class="uploadImg-title">
            <p class="EMCompleteMaintenance-GTitle">上传图片</p>
            <span></span>
          </div>
          <GlobalUploadImg :swtichWxUploadImage="goWxUploadImage" :swtichWxUploadData="childWxUploadData" @wxUploadImageData="getWxUploadImageData" @wxLocalImageData="getWxLocalImageData">
          </GlobalUploadImg>
        </div>
      </div>
    </div>
    <!-- 选择部件 -->
    <!-- switchChooseType -->
    <div class="parts-Gparts" v-show="switchChooseType">
      <div class="Gparts-absolute">
        <div class="Gparts-title">
          <p>选择部件</p>
          <div class="title-icon">
            <i @click="btnClose"></i>
          </div>
        </div>
        <div class="Gparts-main">
          <div class="chooseTypeMain">
            <div class="chooseTypeMain-ins">
              <router-link to="/UseApplyList?applyType=use" tag="span">申请备件</router-link>
            </div>
            <div class="chooseTypeMain-content">
              <ul>
                <li v-for="(chooseTypeItem, index) in titleDataArr" :key="index">
                  <div class="content-checkbox">
                    <label :for="'choose' + chooseTypeItem.userDeviceId">
                                          <i :class="{'checkBox-icon' : '-1' != chooseTypeText.indexOf(chooseTypeItem.userDeviceId)}"></i>
                                          <input type="checkbox"
                                                  class="checkNone"
                                                  :id="'choose' + chooseTypeItem.userDeviceId"
                                                  :value="chooseTypeItem.userDeviceId"
                                                  v-model="chooseTypeText"
                                                  :checked="chooseTypeItem.checked"
                                                  style="display: none;">
                                          <span class="content-left">部件名称:</span>
                                          <span class="content-right">{{chooseTypeItem.partName}}</span>
                                      </label>
                  </div>
                  <div class="content-identifier">
                    <span class="content-left">唯一标识:</span>
                    <span class="content-right">{{chooseTypeItem.inventoryType == "notUnique"?'否':'是'}}</span>
                  </div>
                  <div class="content-partsNum">
                    <span class="content-left">更换数量: </span>
                    <input v-if="chooseTypeItem.replaceCount == ''" class="content-inp" type="text" :data-chooseTypeItem="chooseTypeItem.userDeviceId" :data-count="chooseTypeItem.count" :placeholder="'可替换设备'+chooseTypeItem.count+'个'" :value="chooseTypeItem.count">
                    <input v-if="chooseTypeItem.replaceCount != ''" class="content-inp" type="text" :data-chooseTypeItem="chooseTypeItem.userDeviceId" :data-count="chooseTypeItem.count" :placeholder="'可替换设备'+chooseTypeItem.count+'个'" :value="chooseTypeItem.replaceCount">
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="gparts-btn">
            <button class="btn-close" @click="btnClose">取 消</button>
            <button class="btn-determine" @click="chooseTypeBtnDetermine">确 定</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 处理措施 -->
    <div class="parts-Gparts" v-show="switchMeasures">
      <div class="Gparts-absolute">
        <div class="Gparts-title">
          <p>处理措施</p>
          <div class="title-icon">
            <i @click="btnClose"></i>
          </div>
        </div>
        <div class="Gparts-main">
          <div class="measuresMain">
            <p class="measuresMain-title">请选择处理措施:</p>
            <div class="measuresMain-search">
              <input type="text" id="searchInp" v-model="measuresSearchText" placeholder="请输入查询的关键字">
              <label for="searchInp"></label>
            </div>
            <ul class="measuresMain-list" v-show="measuresData.length != 0">
              <li v-for="(measuresItem, index) in measuresData" :key="index">
                <label :for="'measuresMain' + measuresItem.repairInfoId">
                                        <i :class="{'checkBox-icon':'-1' != measuresNames.indexOf(measuresItem.repairInfoId)}"></i>
                                        <input type="checkbox"
                                                :id="'measuresMain' + measuresItem.repairInfoId"
                                                :value="measuresItem.repairInfoId"
                                                v-model="measuresNames"
                                                style="display: none;">
                                        <span>{{measuresItem.repairDescription}}</span>
                                    </label>
              </li>
            </ul>
            <div class="errorMessage" v-show="measuresData.length == 0">{{measuresSearchText == ''?'无 故 障 现 象 !':'暂 无 数 据 !'}}</div>
            <div class="measuresMain-textarea">
              <p>其他</p>
              <textarea placeholder="请输入故障描述" v-model="measuresMainText"></textarea>
            </div>
          </div>
        </div>
        <div class="gparts-btn">
          <button class="btn-close" @click="btnClose">取 消</button>
          <button class="btn-determine" @click="measuresBtnDetermine">确 定</button>
        </div>
      </div>
    </div>
    <div class="globalFooter">
      <button @click="completeBtnSubmit">维修完成</button>
    </div>
    <GlobalErrorInformation v-show="switchGlobalErrorInformation" @hideCurrentCom="getHideCurrentCom" :errorInformation="EMCompleteMaintenanceError" :errorInformationTitle="EMCompleteMaintenanceErrorTitle">
    </GlobalErrorInformation>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import GlobalTroubleNumber from "@/components/globalCom/GlobalTroubleNumber";
  import GlobalErrorInformation from "@/components/globalCom/GlobalErrorInformation";
  import HomeProcess from "@/components/home/homeCom/HomeProcess";
  import GlobalCheckbox from "@/components/globalCom/GlobalCheckbox";
  import GlobalUploadImg from "@/components/globalCom/GlobalUploadImg";
  export default {
    name: "EMCompleteMaintenance",
    data() {
      return {
        swtichLoading: true,
        radio: "radioRepair",
        childTicketNumberData: [],
        childProcessData: [],
        GlobalCheckboxData: [], // 故障现象数据
        partsMainCheckBoxData: [],
        switchParts: false,
        equipmentIdStr: "",
        switchChooseType: false,
        listDataArr: [],
        switchMeasures: false,
        measuresSearchText: "",
        measuresText: "",
        measuresParamObj: {
          errors: [],
          searchContent: ""
        },
        measuresData: [],
        measuresNames: [],
        measuresMainText: "",
        switchNotRepair: false,
        chooseTypeText: [],
        repairText: "",
        descriptionText: "",
        faultTextTextBox: "",
        errorsArr: [],
        solutionEntities: [],
        // 自定义数据
        titleDataArr: [],
        contentInpVal: [],
        goWxUploadImage: false,
        wxUploadImageData: [],
        wxQrCodeIdArr: [],
        switchGlobalErrorInformation: false,
        EMCompleteMaintenanceError: "",
        EMCompleteMaintenanceErrorTitle: "",
        childWxUploadData: 0,
        wxLocalImageData: [],
        // 旧备件的数据
        oldPartsData: [],
        // 故障现象补充储存的 ID
        GlobalCheckboxSupplemenId: [],
        // 故障现象补充数据
        GlobalCheckboxSupplemenData: [],
        // 选中的错误
        checkboxFault: [],
        checkboxFaultSupplement: [],
        mergeFault: [],
        ErrorSubmit: false
      };
    },
    methods: {
      /* 方法 */
      qrCodeBinding(event) {
        const THIS = $(event.currentTarget),
          INDEX = THIS.attr("data-index");
        this.listDataArr[INDEX].oldQrCode = THIS[0].value;
      },
      btnParts() {
        if (this.titleDataArr.length == 0) {
          this.switchGlobalErrorInformation = true;
          this.EMCompleteMaintenanceError = "请先去领用部件!";
          this.EMCompleteMaintenanceErrorTitle = "部件错误";
          return;
        }
        const THIS = this;
        let inpDOM = $(".oldSweep-sweep input");
        let dataIdArr = [];
        this.contentInpVal = [];
        for (let i = 0, II = inpDOM.length; i < II; i++) {
          this.contentInpVal.push(inpDOM.eq(i).val());
        }
        for (let k = 0, KK = this.listDataArr.length; k < KK; k++) {
          dataIdArr.push(this.listDataArr[k].userDeviceId);
        }
        if (dataIdArr.length > 0) {
          for (let v = 0, VV = this.listDataArr.length; v < VV; v++) {
            if (dataIdArr.indexOf(this.listDataArr[v].userDeviceId) != "-1") {
              this.listDataArr[v].checked = true;
            }
          }
        }
        this.switchChooseType = true;
      },
      chooseTypeBtnDetermine() {
        let partData = this.titleDataArr;
        let listData = this.listDataArr;
        let chooseIdArr = this.chooseTypeText.slice(0);
        let inpDOM = $(".checkBox-icon")
          .closest("li")
          .find(".content-inp");
        let inpValArr = [];
        let inpValId = [];
        let inpValCount = [];
        for (let inp = 0, INP = inpDOM.length; inp < INP; inp++) {
          inpValArr.push(inpDOM.eq(inp).val());
          inpValId.push(+inpDOM.eq(inp).attr("data-chooseTypeItem"));
          inpValCount.push(inpDOM.eq(inp).attr("data-count"));
        }
        for (
          let inpcou = 0, INPCOU = inpValCount.length; inpcou < INPCOU; inpcou++
        ) {
          console.log(inpValArr[inpcou]);
          if (inpValArr[inpcou] == 0) {
            this.switchGlobalErrorInformation = true;
            this.EMCompleteMaintenanceError = "不能超过已有数量!";
            this.EMCompleteMaintenanceErrorTitle = "数量错误";
            return;
          }
          if (!(+inpValCount[inpcou] >= inpValArr[inpcou])) {
            this.switchGlobalErrorInformation = true;
            this.EMCompleteMaintenanceError = "不能超过已有数量!";
            this.EMCompleteMaintenanceErrorTitle = "数量错误";
            return;
          }
        }
        console.log(inpValId);
        for (let inpId = 0, INPID = partData.length; inpId < INPID; inpId++) {
          if (inpValId.indexOf(partData[inpId].userDeviceId) != "-1") {
            this.titleDataArr[inpId].replaceCount = inpValArr.splice(0, 1).join();
          }
        }
        if (this.listDataArr.length == 0) {
          for (let i = 0, II = partData.length; i < II; i++) {
            if (chooseIdArr.indexOf(partData[i].userDeviceId) != "-1") {
              this.listDataArr.push(partData[i]);
              chooseIdArr.splice(
                chooseIdArr.indexOf(partData[i].userDeviceId),
                1
              );
            }
          }
        } else {
          let index = 0;
          console.log(this.listDataArr)
          for (let k = 0, KK = this.listDataArr.length; k < KK - index; k++) {
            if (chooseIdArr.indexOf(this.listDataArr[k].userDeviceId) != "-1") {
              chooseIdArr.splice(chooseIdArr.indexOf(this.listDataArr[k].userDeviceId), 1);
              continue;
            } else {
              this.listDataArr.splice(k, 1);
              k--;
              index++;
            }
          }
          for (let v = 0, VV = partData.length; v < VV; v++) {
            if (chooseIdArr.indexOf(partData[v].userDeviceId) != "-1") {
              this.listDataArr.push(partData[v]);
              chooseIdArr.splice(
                chooseIdArr.indexOf(partData[v].userDeviceId),
                1
              );
            }
          }
        }
        console.log(this.listDataArr)
        // 故障现象补充
        this.GlobalCheckboxSupplemenId = [];
        for (let a = 0, AA = this.listDataArr.length; a < AA; a++) {
          this.GlobalCheckboxSupplemenId.push(this.listDataArr[a].sparePartId)
        }
        this.GlobalCheckboxSupplemenId = [...new Set(this.GlobalCheckboxSupplemenId)];
        console.log(this.GlobalCheckboxSupplemenId)
        // 隐藏弹出框
        this.switchChooseType = false;
      },
      // 删除
      partsDelete(event) {
        const THIS = $(event.currentTarget),
          index = THIS.attr("data-index");
        this.GlobalCheckboxSupplemenId.splice(index, 1);
        this.chooseTypeText.splice(index, 1);
        this.wxQrCodeIdArr.splice(index, 1);
        this.listDataArr.splice(index, 1);
      },
      getWxLocalImageData(val) {
        this.wxLocalImageData = val;
      },
      initServerData() {
        const prompObj = {
          maintenanceApplyId: this.$route.params.id,
          wasFinished: this.repairText || "yes",
          processDescribe: this.descriptionText,
          noRepairReason: "",
          maintenanceDescribe: this.faultTextTextBox,
          errors: this.checkboxFaultSupplement,
          reportedErrors: this.checkboxFault,
          solutionEntities: this.solutionEntities,
          images: this.wxUploadImageData,
          changeSparePartEntities: this.listDataArr,
          equipmentId: this.equipmentIdStr
        };
        this.api.completeDataAll(prompObj).then(data => {
          this.swtichLoading = false;
          if (data.result == "success") {
            this.$router.push({
              path: "/EquipmentMaintenance"
            });
          } else {
            this.switchGlobalErrorInformation = true;
            this.EMCompleteMaintenanceError = data.message;
            this.EMCompleteMaintenanceErrorTitle = "扫码错误";
            this.ErrorSubmit = true;
          }
        });
      },
      getWxUploadImageData(val) {
        this.wxUploadImageData = val;
        this.initServerData();
      },
      // 维修完成
      completeBtnSubmit() {
        if (this.solutionEntities.length == 0) {
          this.switchGlobalErrorInformation = true;
          this.EMCompleteMaintenanceError = '请选择处理措施或其他措施!';
          this.EMCompleteMaintenanceErrorTitle = "信息错误";
          return;
        }
        this.swtichLoading = true;
        if (this.wxLocalImageData.length == 0 || this.ErrorSubmit) {
          this.initServerData();
        } else {
          this.goWxUploadImage = true;
          return;
        }
      },
      // 故障现象
      getCheckboxFault(val) {
        this.checkboxFault = val;
        this.mergeFault = [...this.checkboxFault, ...this.checkboxFaultSupplement];
      },
      // this.errorsArr = val;
      // this.measuresParamObj.errors = val;
      // 故障现象补充
      getCheckboxFaultSupplement(val) {
        this.checkboxFaultSupplement = val;
        this.mergeFault = [...this.checkboxFault, ...this.checkboxFaultSupplement];
      },
      getHideCurrentCom(val) {
        this.switchGlobalErrorInformation = val;
      },
      btnScanning() {
        this.wxApi.WxScanQRCode().then(data => {
          // 验证不能多次扫同一个二维码
          // alert(data)
          for (let i = 0, II = this.listDataArr.length; i < II; i++) {
            // alert(this.listDataArr[i].qrCode)
            if (this.listDataArr[i].qrCode) {
              if (this.listDataArr[i].qrCode == data) {
                this.switchGlobalErrorInformation = true;
                this.EMCompleteMaintenanceError = "已使用过的二维码!";
                this.EMCompleteMaintenanceErrorTitle = "扫码错误";
                return;
              }
            }
          }
          this.api.qrCodePartsDataAll(data).then(data => {
            const DATA = data.data;
            if (data.statusCode == '-201') {
              this.switchGlobalErrorInformation = true;
              this.EMCompleteMaintenanceError = data.message;
              this.EMCompleteMaintenanceErrorTitle = "扫码错误";
              return;
            }
            if (data.statusCode == '-202') {
              this.switchGlobalErrorInformation = true;
              this.EMCompleteMaintenanceError = data.message;
              this.EMCompleteMaintenanceErrorTitle = "扫码错误";
              return;
            }
            DATA[0].newStockId = DATA[0].stockId;
            DATA[0].newSparePartId = DATA[0].sparePartId;
            DATA[0].replaceCount = DATA[0].count;
            DATA[0].oldQrCode = "";
            DATA[0].newQrCode = DATA[0].qrCode;
            DATA[0].checked = false;
            DATA[0].parts = "";
            this.chooseTypeText.push(DATA[0].userDeviceId);
            this.listDataArr.push(DATA[0]);
            // 故障现象补充
            this.GlobalCheckboxSupplemenId.push(DATA[0].sparePartId)
            this.GlobalCheckboxSupplemenId = [...new Set(this.GlobalCheckboxSupplemenId)];
          });
        });
      },
      btnClose() {
        // 处理措施
        if (this.switchMeasures) {
          this.measuresNames = [];
          for (let i = 0, II = this.solutionEntities.length; i < II; i++) {
            this.measuresNames.push(this.solutionEntities[i].repairInfoId)
          }
        }
        this.switchParts = false;
        this.switchChooseType = false;
        this.switchMeasures = false;
        this.switchNotRepair = false;
      },
      measuresBtnDetermine() {
        // 处理描述
        this.measuresText = "";
        if (this.measuresMainText != "") {
          this.measuresText += this.measuresMainText + "\n";
          this.solutionEntities.push({
            repairInfoId: 0,
            processMode: this.measuresMainText
          });
        }
        for (let i = 0, II = this.measuresData.length; i < II; i++) {
          if (
            this.measuresNames.indexOf(this.measuresData[i].repairInfoId) !=
            "-1"
          ) {
            this.measuresText += this.measuresData[i].repairDescription + "\n";
            this.solutionEntities.push({
              repairInfoId: this.measuresData[i].repairInfoId,
              processMode: this.measuresData[i].repairDescription
            });
          }
        }
        this.switchMeasures = false;
      },
      radioNotRepair() {
        this.radio = "radioNotRepair";
        this.switchNotRepair = true;
      },
      // 处理措施
      measuresFun() {
        this.switchMeasures = true;
        this.measuresParamObj.errors = [...this.checkboxFault, ...this.checkboxFaultSupplement];
        if (this.measuresParamObj.errors.length == 0) {
          return;
        }
        this.api.measuresDataAll(this.measuresParamObj).then(data => {
          console.log(data);
          this.measuresData = data.data;
        });
      },
      QrCodngsweep(event) {
        const THIS = $(event.currentTarget),
          inpDOM = THIS.prev(".oldSweep-sweep").find("input"),
          INDEX = THIS.attr("data-index");
        this.wxApi.WxScanQRCode().then(data => {
          inpDOM.val(data);
          this.listDataArr[INDEX].oldQrCode = data;
        });
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      }
    },
    components: {
      /* 复用组件名称 */
      GlobalTroubleNumber,
      HomeProcess,
      GlobalCheckbox,
      GlobalUploadImg,
      GlobalErrorInformation
    },
    mounted: function() {
      /* 初始化数据 */
      let nameId = this.$route.params.id;
      this.api.SendSingleDetailsDataAll(nameId).then(data => {
        const DATA = data.data;
        if (!(DATA.logs[0].status == 'Dispatched' || DATA.logs[0].status == 'Repairing')) {
          this.$router.replace({
            path: '/RepairHistoryDetails/' + nameId
          });
          return;
        }
        this.childTicketNumberData.push({
          ticketNumber: DATA.applyNO, // applyNO
          maintenanceApplyId: DATA.maintenanceApplyId, // maintenanceApplyId
          listDetails: [{
              listTitle: DATA.categoryName, // location
              listVice: "设备类型"
            },
            {
              listTitle: DATA.equipmentNO, // location
              listVice: "设备编码"
            },
            {
              listTitle: DATA.lineName, // lineName
              listVice: "线路"
            },
            {
              listTitle: DATA.stationName, // stationName
              listVice: "车站"
            }
          ],
          faultText: DATA.breakDescribe, // breakDescribe
          faultTimer: DATA.createTime, // createTime
          stationId: DATA.stationId // 车站 ID
        });
        // 我的旧备件
        // this.oldPartsData = DATA.changeInfo;
        // 故障现象
        this.GlobalCheckboxData = DATA.reportErrorPhenomenon;
        // 故障现象补充 ID
        // for (let k = 0, KK = DATA.changeInfo.length; k < KK; k++) {
        //     this.GlobalCheckboxSupplemenId.push(DATA.changeInfo[k].sparePartId)
        // }
        // this.GlobalCheckboxSupplemenId = [...new Set(this.GlobalCheckboxSupplemenId)];
        // 状态
        for (var i = 0, LL = DATA.logs.length; i < LL; i++) {
          this.childProcessData.push({
            initiator: DATA.logs[i].initiator,
            targetPerson: DATA.logs[i].targetPerson,
            createTime: DATA.logs[i].createTime,
            modifyTime: DATA.logs[i].modifyTime,
            status: DATA.logs[i].status
          });
        }
        this.equipmentIdStr = DATA.equipmentId;
        this.$nextTick(function() {
          this.swtichLoading = false;
        });
      });
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
      // 我的备件
      this.api.sparePartsDataAll(nameId).then(data => {
        const DATA = data.data;
        for (var i = 0, II = data.data.length; i < II; i++) {
          DATA[i].newStockId = DATA[i].stockId;
          DATA[i].newSparePartId = DATA[i].sparePartId;
          DATA[i].replaceCount = "";
          DATA[i].oldQrCode = "";
          DATA[i].newQrCode = DATA[i].qrCode;
          DATA[i].checked = false;
          DATA[i].parts = "";
          this.titleDataArr.push(DATA[i]);
        }
      });
    },
    watch: {
      /* 监听 */
      measuresSearchText: function(val) {
        this.measuresParamObj.errors = [...this.checkboxFault, ...this.checkboxFaultSupplement];
        if (this.measuresParamObj.errors.length == 0) {
          return;
        }
        this.measuresParamObj.searchContent = val;
        this.api.measuresDataAll(this.measuresParamObj).then(data => {
          console.log(data);
          this.measuresData = data.data;
        });
      },
      // 监听错误现象，如有变化清空处理措施
      mergeFault: function(val) {
        this.measuresNames = [];
        this.measuresText = '';
      },
      // 监听故障现象补充
      GlobalCheckboxSupplemenId: function(val) {
        console.log(val)
        this.api.getBreakdownListDataAll(val).then(data => {
          this.GlobalCheckboxSupplemenData = data.data;
        });
      }
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #EMCompleteMaintenance {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 200;
    padding-top: 20px;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background-color: #faf6ec;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .mainHeight100 {
      height: 100% !important;
      width: 100%;
    }
    .EMCompleteMaintenance-main {
      padding-bottom: 20px;
      width: 100%;
      height: calc(100% - 70px);
      overflow: hidden;
      overflow-y: auto;
      box-sizing: border-box;
      .EMCompleteMaintenance-site {
        .list-siteDescription {
          li:nth-child(1) {
            flex: 20;
          }
          li:nth-child(2) {
            flex: 22;
          }
          li:nth-child(3) {
            flex: 30;
          }
          li:nth-child(4) {
            flex: 28;
          }
        }
      }
      .EMCompleteMaintenance-situation {
        margin: 20px 20px 0;
        padding: 20px 20px 60px;
        background-color: #fff;
        border: 1px solid #dfdcdc;
        box-sizing: border-box;
        border-radius: 15px; // 公共 左侧 title 样式
        .EMCompleteMaintenance-GTitle {
          font-size: $widthWeb22;
          color: #333;
          line-height: 48px;
        }
        .situation-title {
          padding-left: 84px;
          margin-bottom: 20px;
          border-bottom: 1px solid #dfdcdc;
          box-sizing: border-box;
          p {
            position: relative;
            display: inline-block;
            font-size: $widthWeb25;
            color: #333;
            line-height: 65px;
            &::before {
              content: " ";
              position: absolute;
              bottom: 0;
              left: 50%;
              transform: translateX(-50%);
              width: 46px;
              height: 4px;
              background-color: #fc6806;
            }
          }
        }
        .situation-parts {
          margin-bottom: 26px;
          .parts-replace {
            display: flex;
            justify-content: space-between;
            margin-bottom: 16px;
            .replace-title {
              p {
                &:nth-child(1) {
                  color: #333;
                  font-size: $widthWeb22;
                  line-height: 36px;
                }
                &:nth-child(2) {
                  color: #fd8521;
                  font-size: $widthWeb16;
                  line-height: 30px;
                }
              }
            }
            button {
              padding: 6px 16px;
              line-height: 35px;
              color: #fafafc;
              font-size: $widthWeb16;
              line-height: $widthWeb16;
              background-color: #fd8521;
              border-radius: 6px;
            }
          }
          .parts-newOld {
            ul {
              li {
                padding-left: 5px;
                padding-bottom: 16px;
                margin-bottom: 20px;
                display: flex;
                align-items: center;
                border-bottom: 1px solid #dfdcdc;
                &:nth-last-child(1) {
                  margin: 0;
                  border: 0;
                }
                .newOld-replaceIcon1 {
                  width: 63px;
                  height: 104px;
                  background-image: url("../../../../assets/home/cge_l.png");
                  background-size: 100% 100%;
                }
                .newOld-replaceIcon2 {
                  width: 63px;
                  height: 74px;
                  background-image: url("../../../../assets/home/cge_s.png");
                  background-size: 100% 100%;
                }
                .notUniqueMr {
                  margin-left: 66px !important;
                }
                .newOld-replace {
                  margin-left: 4px;
                  font-size: $widthWeb22;
                  color: #333;
                  width: 100%;
                  .replace-parts {
                    display: flex;
                    justify-content: space-between;
                    align-content: center;
                    width: 100%;
                    line-height: 36px;
                    .parts-parts {
                      display: flex;
                      justify-content: space-between;
                      &>p {
                        margin-left: 34px;
                        color: #fc6806;
                      }
                    }
                    .parts-new {
                      display: flex;
                      justify-content: center;
                      align-content: center;
                      i {
                        display: inline-block;
                        margin-right: 8px;
                        margin-top: 8px;
                        width: 22px;
                        height: 22px;
                        background-image: url("../../../../assets/home/icon_new.png");
                        background-size: 100% 100%;
                      }
                      p {
                        color: #333;
                        font-size: $widthWeb22;
                      }
                    }
                    .parts-delete {
                      display: inline-block;
                      margin-right: 34px;
                      padding: 6px 10px;
                      background-color: #fbe9db;
                      border: 1px solid #facaa6;
                      border-radius: 10px;
                      box-sizing: border-box;
                      span {
                        font-size: $widthWeb14;
                        line-height: $widthWeb14;
                      }
                      i {
                        margin-right: 6px;
                        display: inline-block;
                        width: 13px;
                        height: 13px;
                        background-image: url("../../../../assets/home/mini_colse1.png");
                        background-size: 100% 100%;
                      }
                    }
                  }
                  .parts-only {
                    padding-left: 30px;
                    line-height: 28px;
                    font-size: $widthWeb14;
                    margin-bottom: 10px;
                    color: #666;
                  }
                  .parts-oldSweep {
                    display: flex;
                    align-items: center;
                    font-size: $widthWeb22;
                    line-height: 36px;
                    .oldSweep-old {
                      display: flex;
                      align-items: center;
                      i {
                        display: inline-block;
                        margin-right: 8px;
                        width: 22px;
                        height: 22px;
                        background-image: url("../../../../assets/home/icon_old.png");
                        background-size: 100% 100%;
                      }
                    }
                    .oldSweep-sweep {
                      display: flex;
                      margin-left: 20px;
                      input {
                        border-bottom: 1px solid #dfdcdc;
                        margin-right: 10px;
                        text-align: center;
                        color: #fc6806;
                      }
                    }
                    .sweep-icon {
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      width: 60px;
                      height: 40px;
                      i {
                        display: inline-block;
                        width: 29px;
                        height: 28px;
                        background-image: url("../../../../assets/home/scan.png");
                        background-size: 100% 100%;
                      }
                    }
                  }
                }
              }
            }
          }
        }
        .parts-fault {
          // 故障现象补充
        }
        .parts-description {
          .description-title {
            display: flex;
            font-size: 0;
            line-height: 48px;
            span {
              margin-left: 10px;
              font-size: $widthWeb16;
              color: #bfbfbf;
            }
          }
          .description-textBox {
            margin-top: 9px;
            padding: 21px 18px;
            width: 100%;
            height: 154px;
            font-size: $widthWeb18;
            color: #333;
            resize: none;
            border: solid 1px #bfbfbf;
            box-sizing: border-box;
          }
        }
        .parts-results {
          display: flex; // align-items: center;
          .results-radio {
            margin-left: 32px;
            font-size: 0;
            line-height: 48px;
            .radio-repair {
              margin-right: 30px;
              font-size: $widthWeb22;
              color: #666;
              span {
                display: inline-block;
                width: 18px;
                height: 18px;
                background: url("../../../../assets/home/radio_no.png");
                background-size: 100% 100%;
              }
              .selectedRadio {
                background: url("../../../../assets/home/radio_sel.png");
                background-size: 100% 100%;
              }
              &:nth-last-child(1) {
                margin-right: 0px;
              }
            }
            input {
              display: none;
            }
          }
        }
        .parts-measures {
          display: flex;
          input {
            margin-left: 38px;
            width: 491px;
            height: 45px;
            border: solid 1px #bfbfbf;
            box-sizing: border-box;
          }
          textarea {
            margin-left: 30px;
            padding-left: 10px;
            width: 491px;
            border: solid 1px #bfbfbf;
            box-sizing: border-box;
            font-size: $widthWeb22;
            line-height: 45px;
            resize: none;
          }
        }
        .parts-uploadImg {
          .uploadImg-title {
            display: flex;
            font-size: 0;
            line-height: 48px;
            span {
              margin-left: 10px;
              font-size: $widthWeb16;
              color: #bfbfbf;
            }
          }
        }
      }
    }
    .parts-Gparts {
      z-index: 300;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      .Gparts-absolute {
        position: absolute;
        top: 20%;
        left: 50%;
        transform: translateX(-50%);
        width: 540px;
        background-color: #f6f6ec;
        border-radius: 15px;
        .Gparts-title {
          position: relative;
          background-color: #fce4bd;
          border-top-left-radius: 15px;
          border-top-right-radius: 15px;
          text-align: center;
          p {
            color: #f15510;
            font-size: $widthWeb25;
            line-height: 80px;
          }
          .title-icon {
            position: absolute;
            top: 50%;
            right: 16px;
            width: 70px;
            height: 70px;
            transform: translateY(-50%);
            text-align: center;
            i {
              display: inline-block;
              width: 35px;
              height: 35px;
              margin-top: 14px;
              background-image: url("../../../../assets/home/close.png");
              background-size: 100% 100%;
            }
          }
        }
        .Gparts-main {
          width: 100%; // 更换部件
          .partsMain {
            width: 100%;
            padding: 0 32px;
            box-sizing: border-box;
            p {
              color: #333;
              font-size: $widthWeb18;
              line-height: 50px;
            }
            .partsMain-bgc {
              padding: 4px 0 6px 20px;
              width: 100%;
              background-color: #fff;
              border-radius: 6px;
            }
            .partsMain-checkBox {
              width: 50%;
              float: left;
              line-height: 30px;
              label {
                display: inline-block;
                width: 100%;
                font-size: 0;
                i {
                  display: inline-block;
                  margin-right: 12px;
                  width: 18px;
                  height: 18px;
                  background: url("../../../../assets/home/checkbox_no.png") no-repeat;
                  background-size: 100% 100%;
                }
                .checkBox-icon {
                  background: url("../../../../assets/home/checkbox_sel.png") no-repeat;
                  background-size: 100% 100%;
                }
                span {
                  color: #666;
                  font-size: $widthWeb16;
                  line-height: 30px;
                }
              }
            }
          } // 选择部件
          .chooseTypeMain {
            width: 100%;
            padding: 17px 32px 0;
            box-sizing: border-box;
            .chooseTypeMain-ins {
              width: 100%;
              height: 34px;
              margin-bottom: 20px;
              text-align: right;
              span {
                padding: 9px 18px;
                color: #fff;
                font-size: $widthWeb16;
                background-color: #fd8521;
                border-radius: 6px;
              }
            }
            .chooseTypeMain-content {
              width: 100%;
              max-height: 400px;
              overflow: hidden;
              overflow-y: auto;
              li {
                width: 100%;
                padding: 7px 0 7px 20px;
                margin-top: 20px;
                background-color: #fff;
                border-radius: 6px;
                box-sizing: border-box;
                &:nth-child(1) {
                  margin-top: 0;
                }
                .content-left {
                  margin-right: 13px;
                  color: #333;
                  font-size: $widthWeb18;
                  line-height: 38px;
                }
                .content-right {
                  color: #666;
                  font-size: $widthWeb18;
                  line-height: 38px;
                }
                .content-checkbox {
                  width: 100%;
                  label {
                    display: inline-block;
                    width: 100%;
                    font-size: 0;
                    i {
                      display: inline-block;
                      margin-right: 19px;
                      width: 18px;
                      height: 18px;
                      background: url("../../../../assets/home/checkbox_no.png") no-repeat;
                      background-size: 100% 100%;
                    }
                    .checkBox-icon {
                      background: url("../../../../assets/home/checkbox_sel.png") no-repeat;
                      background-size: 100% 100%;
                    }
                  }
                }
                .content-identifier {
                  padding-left: 37px;
                  font-size: 0;
                  box-sizing: border-box;
                } // #dfdcdc;
                .content-partsNum {
                  padding-left: 37px;
                  box-sizing: border-box;
                }
                .content-inp {
                  width: 250px;
                  color: #ffa144;
                  padding-left: 22px;
                  font-size: $widthWeb18;
                  border-bottom: 1px solid #dfdcdc;
                  box-sizing: border-box;
                }
              }
            }
          } // 处理措施
          .measuresMain {
            width: 100%;
            padding-top: 24px;
            .measuresMain-title {
              padding-left: 42px;
              color: #333;
              font-size: $widthWeb22;
              line-height: 30px;
            }
            .measuresMain-search {
              position: relative;
              padding-left: 45px;
              height: 94px;
              line-height: 94px;
              #searchInp {
                padding-left: 38px;
                width: 452px;
                height: 40px;
                font-size: $widthWeb16;
                border: 1px solid #bfbfbf;
                box-sizing: border-box;
                border-radius: 15px;
              }
              label {
                position: absolute;
                top: 33px;
                right: 73px;
                width: 28px;
                height: 28px;
                background-image: url("../../../../assets/home/icon_search2.png");
                background-size: 100% 100%;
              }
            }
            .measuresMain-list {
              width: 100%;
              max-height: 200px;
              margin-bottom: 25px;
              padding-left: 45px;
              overflow: hidden;
              overflow-y: auto;
              box-sizing: border-box;
              li {
                text-align: left;
                width: 100%;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                label {
                  display: inline-block;
                  width: 100%;
                  i {
                    display: inline-block;
                    margin-right: 12px;
                    width: 18px;
                    height: 18px;
                    background: url("../../../../assets/home/checkbox_no.png") no-repeat;
                    background-size: 100% 100%;
                  }
                  .checkBox-icon {
                    background: url("../../../../assets/home/checkbox_sel.png") no-repeat;
                    background-size: 100% 100%;
                  }
                  span {
                    color: #666;
                    font-size: $widthWeb22;
                    line-height: 40px;
                  }
                }
              }
            }
            .errorMessage {
              margin-right: 20px;
              text-align: center;
              font-size: $widthWeb18; // color: #f15510;
              color: #666;
              line-height: 50px;
            }
            .measuresMain-textarea {
              padding: 0 42px;
              p {
                color: #333;
                font-size: $widthWeb22;
                line-height: 46px;
              }
              textarea {
                padding: 21px 18px;
                width: 100%;
                height: 115px;
                font-size: $widthWeb18;
                color: #333;
                resize: none;
                border: solid 1px #bfbfbf;
                box-sizing: border-box;
              }
            }
          }
        }
        .gparts-btn {
          padding: 32px 0 40px;
          width: 100%;
          text-align: center;
          font-size: 0;
          button {
            width: 124px;
            height: 40px;
            font-size: $widthWeb22;
            color: #fff;
          }
          .btn-close {
            background-color: #b9b7b4;
            margin-right: 26px;
          }
          .btn-determine {
            background-color: #fd8521;
          }
        }
      }
    }
  }
</style>
