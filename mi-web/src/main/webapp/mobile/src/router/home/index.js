import Vue from 'vue'
import Router from 'vue-router'

// 首页路由
const HomeView = r => require.ensure([], () => r(require('../../views/home/homeViews/HomeView.vue')), 'HomeView')
// 设备报修
const EquipmentRepair = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/EquipmentRepair.vue')), 'EquipmentRepair')
// 报修派工
const RepairDispatching = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/RepairDispatching.vue')), 'RepairDispatching')
// 报修派工记录
const RepairDisHistory = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/RepairDisHistory.vue')), 'RepairDisHistory')
// 报修派工通讯录
const MultipleChoiceAddressBook = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/MultipleChoiceAddressBook.vue')), 'MultipleChoiceAddressBook')
// 报修派工详情
const RDSendSingle = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/RDSendSingle.vue')), 'RDSendSingle')
// 报修派工详情通讯录
const MultipleChoiceAddressBookDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/MultipleChoiceAddressBookDetails.vue')), 'MultipleChoiceAddressBookDetails')
// 设备维修
const EquipmentMaintenance = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/EquipmentMaintenance.vue')), 'EquipmentMaintenance')
// 设备维修接修
const EMAfterRepairing = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/EMAfterRepairing.vue')), 'EMAfterRepairing')
// 设备维修维修完成
const EMCompleteMaintenance = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/EMCompleteMaintenance.vue')), 'EMCompleteMaintenance')
// 回访评价
const ReviewEvaluation = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/ReviewEvaluation.vue')), 'ReviewEvaluation')
// 回访评价详情
const REDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/REDetails.vue')), 'REDetails');

// 回访评价详情
const RepairHistoryDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/RepairHistoryDetails.vue')), 'RepairHistoryDetails');
// 设备绑定
const EquipmentBinding = r => require.ensure([], () => r(require('../../views/home/homeViews/maintenance/EquipmentBinding.vue')), 'EquipmentBinding')
// 入库列表
const PutStorage = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/PutStorage.vue')), 'PutStorage')
// 入库操作页
const PutStorageEdit = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/PutStorageEdit.vue')), 'PutStorageEdit')
// 入库详情
const PutStorageDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/PutStorageDetails.vue')), 'PutStorageDetails')
// 入库新增入库
const NewLibrary = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/NewLibrary.vue')), 'NewLibrary')
// 新品入库选择备件
const NewLibrarySelect = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/NewLibrarySelect.vue')), 'NewLibrarySelect')
// 我的备件
const MySpareParts = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/MySpareParts.vue')), 'MySpareParts')
// 我的备件详情
const SparePartsDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/SparePartsDetails.vue')), 'SparePartsDetails')

//通讯录
const PersonalRessbook = r => require.ensure([], () => r(require('../../views/home/personalViews/PersonalRessbook.vue')), 'PersonalRessbook')

// 个人中心通知公告
const NoticeAnnouncement = r => require.ensure([], () => r(require('../../views/home/personalViews/NoticeAnnouncement.vue')), 'NoticeAnnouncement')

/*借用出库详情*/
const BorrowingOutDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/BorrowingOutDetails.vue')), 'BorrowingOutDetails');
/*借用出库选择列表*/
const BorrowOutboundSelectList = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/BorrowOutboundSelectList.vue')), 'BorrowOutboundSelectList');
// 库存管理
const InventoryManagement = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/InventoryManagement.vue')), 'InventoryManagement');
// 出库
const Outbound = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/Outbound.vue')), 'Outbound');
// 出库详情
const OutboundDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/OutboundDetails.vue')), 'OutboundDetails')
// 出库-出库
const OutboundThrow = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/OutboundThrow.vue')), 'OutboundThrow');
// 出库-物品添加
const OutboundAddParts = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/OutboundAddParts.vue')), 'OutboundAddParts');

const UseApplyList = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseApplyList.vue')), 'UseApplyList');

const UseApplyListEdit = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseApplyListEdit.vue')), 'UseApplyListEdit');

const Inventory = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/Inventory.vue')), 'Inventory');

const InventoryDetaile = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/InventoryDetaile.vue')), 'InventoryDetaile');

const NewInventoryInfo = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/NewInventoryInfo.vue')), 'NewInventoryInfo');

const EditInventoryInfo = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/EditInventoryInfo.vue')), 'EditInventoryInfo');

const NewInventoryList = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/NewInventoryList.vue')), 'NewInventoryList');

// 我的任务路由
const MyTaskView = r => require.ensure([], () => r(require('../../views/home/MyTaskViews/MyTaskView.vue')), 'MyTaskView');
const TaskProgressDetails = r => require.ensure([], () => r(require('../../views/home/MyTaskViews/TaskProgressDetails.vue')), 'TaskProgressDetails');

// 个人中心路由
const PersonalView = r => require.ensure([], () => r(require('../../views/home/personalViews/PersonalView.vue')), 'PersonalView');

/* 添加领用记录添加物品*/
const UseApplySelectPart = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseApplySelectPart.vue')), 'UseApplySelectPart');

/*查看页面*/
const UseApplylook = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseApplylook.vue')), 'UseApplylook');

/*调拨申请列表添加编辑*/
const TransferApplyEdit = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/TransferApplyEdit.vue')), 'TransferApplyEdit');
/*调拨申请查看*/

// 记录：
// 出入库记录
const InventoryOutboundRecords = r => require.ensure([], () => r(require('../../views/home/homeViews/record/InventoryOutboundRecords.vue')), 'InventoryOutboundRecords');
const InventoryOutboundDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/record/InventoryOutboundDetails.vue')), 'InventoryOutboundDetails');

// 领用,调拨，返还记录
const LingDaoFanRecords = r => require.ensure([], () => r(require('../../views/home/homeViews/record/LingDaoFanRecords.vue')), 'LingDaoFanRecords');
// 借用记录
const BorrowRecord = r => require.ensure([], () => r(require('../../views/home/homeViews/record/BorrowRecord.vue')), 'BorrowRecord');
// 审批记录
const ApprovalRecords = r => require.ensure([], () => r(require('../../views/home/homeViews/record/ApprovalRecords.vue')), 'ApprovalRecords');
// 归还记录
const ReturnGhRecords = r => require.ensure([], () => r(require('../../views/home/homeViews/record/ReturnGhRecords.vue')), 'ReturnGhRecords');
// 故障记录
const FaultRecord = r => require.ensure([], () => r(require('../../views/home/homeViews/record/FaultRecord.vue')), 'FaultRecord');
// 维修记录
const MaintenanceRecord = r => require.ensure([], () => r(require('../../views/home/homeViews/record/MaintenanceRecord.vue')), 'MaintenanceRecord');
// 维修记录 -- 详情
const MaintenanceRecordDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/record/MaintenanceRecordDetails.vue')), 'MaintenanceRecordDetails');
// 操作记录
const OperationRecords = r => require.ensure([], () => r(require('../../views/home/homeViews/record/OperationRecords.vue')), 'OperationRecords');

const TransferApplyLook = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/TransferApplyLook.vue')), 'TransferApplyLook');
/*返还申请添加编辑*/
const ReturnApplyEdit = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/ReturnApplyEdit.vue')), 'ReturnApplyEdit');
/*返还申请查看*/
const ReturnApplyLook = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/ReturnApplyLook.vue')), 'ReturnApplyLook');

/*审核管理添加编辑页面*/
const UseExamination = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseExamination.vue')), 'UseExamination');

const UseExaminationlook = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseExaminationlook.vue')), 'UseExaminationlook');

/*审核管理列表页面*/
const UseApplyAuditList = r => require.ensure([], () => r(require('../../views/home/homeViews/useApply/UseApplyAuditList.vue')), 'UseApplyAuditList');

/*个人中心里的密码管理*/
const PersonalPassMannerge = r => require.ensure([], () => r(require('../../views/home/personalViews/PersonalPassMannerge.vue')), 'PersonalPassMannerge');
/*出库绑定*/

const OutInventoryBindQrCode = r => require.ensure([], () => r(require('../../views/home/homeViews/OutInventoryBindQrCode.vue')), 'OutInventoryBindQrCode');

/*账号绑定*/
const PersonalBindAccount = r => require.ensure([], () => r(require('../../views/home/personalViews/PersonalBindAccount.vue')), 'PersonalBindAccount');
// 庫存管理詳情頁
const InventoryManagementDetails = r => require.ensure([], () => r(require('../../views/home/homeViews/inventory/InventoryManagementDetails.vue')), 'InventoryManagementDetails');
Vue.use(Router);

export default new Router({
  // mode: 'history',
  routes: [{
      path: '*',
      name: 'homeView',
      component: HomeView
    },
    // 首页路由
    {
      path: '/HomeView',
      name: 'homeView',
      component: HomeView
    },
    {
      path: '/EquipmentRepair',
      name: 'equipmentRepair',
      component: EquipmentRepair
    },
    {
      path: '/RDSendSingle/:id',
      name: 'RDSendSingle',
      component: RDSendSingle,
      children: [{
        path: '/MultipleChoiceAddressBookDetails/:nameIDArr',
        name: 'multipleChoiceAddressBookDetails',
        component: MultipleChoiceAddressBookDetails
      }]
    },
    {
      path: '/RepairDispatching',
      name: 'repairDispatching',
      component: RepairDispatching,
      children: [{
        path: '/MultipleChoiceAddressBook/:nameIDArr',
        name: 'MultipleChoiceAddressBook',
        component: MultipleChoiceAddressBook
      }]
    },
    {
      path: '/RepairDisHistory/:state',
      name: 'RepairDisHistory',
      component: RepairDisHistory
      // children: [{
      //   path: '/RepairHistoryDetails/:id',
      //   name: 'RepairHistoryDetails',
      //   component: RepairHistoryDetails
      // }]
    },
    {
      path: '/RepairHistoryDetails/:id',
      name: 'RepairHistoryDetails',
      component: RepairHistoryDetails
    },
    {
      path: '/EquipmentMaintenance',
      name: 'equipmentMaintenance',
      component: EquipmentMaintenance,
      children: [{
          path: '/EMAfterRepairing/:id',
          name: 'EMAfterRepairing',
          component: EMAfterRepairing
        },
        {
          path: '/EMCompleteMaintenance/:id',
          name: 'EMCompleteMaintenance',
          component: EMCompleteMaintenance
        }
      ]
    },
    {
      path: '/BorrowingOutDetails',
      name: 'BorrowingOutDetails',
      component: BorrowingOutDetails,
      children: [{
        path: '/BorrowOutboundSelectList',
        name: 'BorrowOutboundSelectList',
        component: BorrowOutboundSelectList
      }]
    },
    {
      path: '/ReviewEvaluation',
      name: 'reviewEvaluation',
      component: ReviewEvaluation,
      children: [{
        path: '/REDetails/:id',
        name: 'REDetails',
        component: REDetails
      }]
    },
    {
      path: '/EquipmentBinding',
      name: 'equipmentBinding',
      component: EquipmentBinding
    },
    {
      path: '/MySpareParts',
      name: 'mySpareParts',
      component: MySpareParts,
      children: [{
        path: '/SparePartsDetails/:id',
        name: 'sparePartsDetails',
        component: SparePartsDetails
      }]
    },
    {
      path: '/InventoryManagementDetails/:id',
      name: 'InventoryManagementDetails',
      component: InventoryManagementDetails,
      meta:{navShow:false,cname:'一级页面'}
    },
    {
      path: '/PutStorage',
      name: 'putStorage',
      component: PutStorage,
      children: [{
          path: '/PutStorageDetails',
          name: 'PutStorageDetails',
          component: PutStorageDetails
        },
        {
          path: '/PutStorageEdit',
          name: 'PutStorageEdit',
          component: PutStorageEdit
        },
        {
          path: '/NewLibrary',
          name: 'NewLibrary',
          component: NewLibrary
        }
      ]
    },
    {
      path: '/NewLibrarySelect',
      name: 'NewLibrarySelect',
      component: NewLibrarySelect,
    },
    {
      path: '/Outbound',
      name: 'outbound',
      component: Outbound,
      children: [{
        path: '/OutboundDetails/:id',
        name: 'outboundDetails',
        component: OutboundDetails
      }]
    },
    {
      path: '/OutboundThrow',
      name: 'outboundThrow',
      component: OutboundThrow,
      children: [{
        path: '/OutboundAddParts',
        name: 'outboundAddParts',
        component: OutboundAddParts
      }]
    },
    {
      path: '/UseApplyList',
      name: 'UseApplyList',
      component: UseApplyList
    },
    {
      path: '/InventoryManagement',
      name: 'inventoryManagement',
      component: InventoryManagement
    },
    {
      path: '/Inventory',
      name: 'inventory',
      component: Inventory
    },
    {
      path: '/NewInventoryList',
      name: 'NewInventoryList',
      component: NewInventoryList
    },
    {
      path: '/InventoryDetaile',
      name: 'InventoryDetaile',
      component: InventoryDetaile
    },
    {
      path: '/UseApplyListEdit',
      name: 'UseApplyListEdit',
      component: UseApplyListEdit
    },
    {
      path: '/NewInventoryInfo',
      name: 'NewInventoryInfo',
      component: NewInventoryInfo
    },
    {
      path: '/EditInventoryInfo',
      name: 'EditInventoryInfo',
      component: EditInventoryInfo
    },
    {
      path: '/MaintenanceRecord/:type',
      name: 'maintenanceRecord',
      component: MaintenanceRecord,
      children: [{
        path: '/MaintenanceRecordDetails/:id',
        name: 'maintenanceRecordDetails',
        component: MaintenanceRecordDetails
      }]
    },
    {
      path: '/OperationRecords',
      name: 'operationRecords',
      component: OperationRecords
    },
    {
      path: '/UseApplySelectPart',
      name: 'UseApplySelectPart',
      component: UseApplySelectPart
    },
    // 首页 --- 记录
    {
      path: '/InventoryOutboundRecords/:type',
      name: 'inventoryOutboundRecords',
      component: InventoryOutboundRecords
    },
    {
      path: '/InventoryOutboundDetails',
      name: 'InventoryOutboundDetails',
      component: InventoryOutboundDetails
    },

    {
      path: '/LingDaoFanRecords/:type',
      name: 'LingDaoFanRecords',
      component: LingDaoFanRecords
    },
    {
      path: '/ApprovalRecords',
      name: 'ApprovalRecords',
      component: ApprovalRecords
    },
    // 我的任务路由
    {
      path: '/MyTaskView',
      name: 'myTaskView',
      component: MyTaskView
    },
    {
      path: '/TaskProgressDetails',
      name: 'taskProgressDetails',
      component: TaskProgressDetails
    },
    {
      path: '/TransferApplyEdit',
      name: 'TransferApplyEdit',
      component: TransferApplyEdit
    },
    // 个人中心路由
    {
      path: '/PersonalView',
      name: 'personalView',
      component: PersonalView
    }, {
      path: '/UseApplylook',
      name: 'UseApplylook',
      component: UseApplylook
    },
    {
      path: '/TransferApplyLook',
      name: 'TransferApplyLook',
      component: TransferApplyLook
    },
    {
      path: '/ReturnApplyEdit',
      name: 'ReturnApplyEdit',
      component: ReturnApplyEdit
    },
    {
      path: '/ReturnApplyLook',
      name: 'ReturnApplyLook',
      component: ReturnApplyLook
    },
    {
      path: '/UseApplyAuditList',
      name: 'UseApplyAuditList',
      component: UseApplyAuditList
    },
    {
      path: '/UseExamination',
      name: 'UseExamination',
      component: UseExamination
    },
    {
      path: '/UseExaminationlook',
      name: 'UseExaminationlook',
      component: UseExaminationlook
    },
    {
      path: '/OutInventoryBindQrCode',
      name: 'OutInventoryBindQrCode',
      component: OutInventoryBindQrCode
    },
    {
      path: '/PersonalPassMannerge',
      name: 'PersonalPassMannerge',
      component: PersonalPassMannerge
    },
    {
      path: '/PersonalRessbook',
      name: 'PersonalRessbook',
      component: PersonalRessbook
    }, {
      path: '/PersonalBindAccount',
      name: 'PersonalBindAccount',
      component: PersonalBindAccount
    },
    {
      path: '/NoticeAnnouncement',
      name: 'NoticeAnnouncement',
      component: NoticeAnnouncement
    }
  ]
})
