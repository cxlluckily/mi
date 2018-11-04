<template>
  <div id='OutInventoryBindQrCode'>
    <!--<div class="fault-btn">-->
      <!--&lt;!&ndash;<span @click.stop="addbtnStorage('add','0')">新增</span>&ndash;&gt;-->
      <!--&lt;!&ndash;<span  @click="searchSubmit"><i class="query_icon"></i>查询</span>&ndash;&gt;-->
    <!--</div>-->
    <ul class="FaultInformation-list" v-if="tableDate.length>0">
      <li  v-for="(item , index) in tableDate">
        <section class="link_a" @click="addbtnStorage('edit',item)"  >
          <div class="list-title">
            <i></i>
            <p>
              单据号:{{item.applyNo}}
            </p>
            <div class="state-ling" v-if="item.applyType=='use'"></div>
            <div class="state-bo" v-if="item.applyType=='transfer'"></div>
            <div class="state-back" v-if="item.applyType=='return'"></div>
          </div>
          <div class="list-fault">
            <div class="fault-describe">
              <p><span class="label">领取人：</span>
                <span class="fault-timer">{{item.applyUser}}</span>
              </p>
            </div>
            <div class="fault-describe">
              <p><span class="label">出库人：</span>
                <span class="fault-timer">{{item.account}}</span>
              </p>
            </div>
          </div>
          <div class="list-fault">
            <div class="fault-describe">
              <p><span class="label">出库时间：</span>
                <span class="fault-timer">{{item.applyTime|initDate}}</span>
              </p>
            </div>
            <div class="fault-describe">
              <p><span class="label">状态：</span>
                <span v-if="item.applyStatus=='toBeReview'">
                  待审核
            </span>
                <span v-if="item.applyStatus=='reviewPass'">
                  审核通过
            </span>
                <span v-if="item.applyStatus=='reviewNoPass'">
                  审核不通过
            </span>
                <span v-if="item.applyStatus=='over'">
                  完成
            </span>
                <span v-if="item.applyStatus=='toBeOut'">
                  待出库
            </span>
                <span v-if="item.applyStatus=='toBeIn'">
                  待入库
            </span>
              </p>
            </div>
          </div>
          <div class="list-fault">
            <div class="fault-describe">
              <p><span class="label">出库仓库：</span>
                <span class="fault-timer">{{item.applyUser}}</span>
              </p>
            </div>
            <div class="fault-describe">
              <p><span class="label">出库数量：</span>
                <span class="fault-timer">{{item.account}}</span>
              </p>
            </div>
          </div>
        </section>
      </li>
    </ul>
    <!--侧边栏右滑-->
    <Search :isSearch="isSearchCom" @switchSearchCom=getSwitchSearchCom @SearchCom="SearchCom">
    </Search>
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  import Search from '@/components/home/homeCom/Recipients'
  import Toast from 'mint-ui'
  export default {
    name: 'OutInventoryBindQrCode',
    data () {
      return {
        swtichLoading:true,
        applyType:'return',
        isSearchCom: false,
        tableDate:[],
        seacher:{},
        applyId:'',
      }
    },
    methods: { /* 方法 */
      addbtnStorage(type,item){
        let applyType=this.$route.query.applyType;
        sessionStorage.setItem('applyType',applyType);
        switch(applyType){
          case "use":
            if(type=='add'){
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({name:'UseApplyListEdit',query:{ applyId:0}});
            }else{
              if(item.applyStatus==='toBeReview'){
                sessionStorage.setItem("showType", "edit");
                this.$router.push({name:'UseApplyListEdit',query:{ applyId:item.applyId}});
              }
              else
              {
                this.$router.push({name:'UseApplylook',query:{ applyId:item.applyId}});
              }
            }
            break;
          case "transfer":
            if(type=='add'){
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({name:'TransferApplyEdit',query:{ applyId:0}});
            }else{
              if(item.applyStatus==='toBeReview'){
                sessionStorage.setItem("showType", "edit");
                this.$router.push({name:'TransferApplyEdit',query:{ applyId:item.applyId}});
              }
              else
              {
                this.$router.push({name:'TransferApplyLook',query:{ applyId:item.applyId}});
              }
            }
            break;
          case "return":
            if(type=='add'){
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({name:'ReturnApplyEdit',query:{ applyId:0}});
            }else{
              if(item.applyStatus==='toBeReview'){
                sessionStorage.setItem("showType", "edit");
                this.$router.push({name:'ReturnApplyEdit',query:{ applyId:item.applyId}});
              }
              else
              {
                this.$router.push({name:'ReturnApplyLook',query:{ applyId:item.applyId}});
              }
            }
            break;
          case "approval":
            if(type=='add'){
              sessionStorage.setItem("dataObj", "");
              sessionStorage.setItem("listArray", "");
              sessionStorage.setItem("showType", "add");
              this.$router.push({name:'UseExamination',query:{ applyId:0}});
            }else{
              if(item.applyStatus==='toBeReview'){
                sessionStorage.setItem("showType", "edit");
                this.$router.push({name:'UseExamination',query:{ applyId:item.applyId}});
              }
              else
              {
                this.$router.push({name:'UseExaminationlook',query:{ applyId:item.applyId}});
              }
            }
            break;
        }
      },
      getSwitchSearchCom(is) {
        this.isSearchCom = is;
      },
      SearchCom(val) {
        const USERKEY = localStorage.getItem('userKey');
        const _this=this;
        const applyType=this.$route.query.applyType;
        let data = {
          userKey: USERKEY,
          applyStatus: val.status,
          applyType: applyType,
          beginTime: val.start_time,
          documentNo: val.troubleNum,
          endTime: val.end_time,
          start: 0,
          limit: 20
        };
        this.requests.requisitionInfor(data).then(function (respont) {
          if (respont.result == 'success') {
            _this.tableDate = respont.data.rows;
          } else {
            return;
          }
        })
      },
      searchSubmit(){
        this.isSearchCom = !this.isSearchCom;
      },
      initData() {
        const USERKEY = localStorage.getItem('userKey');
        sessionStorage.setItem("isFirst","yes");
        const beginTime = new Date();
        const endTime = new Date();
        beginTime.setDate(beginTime.getDate() - 30);
        const _this = this;
        const applyType=this.$route.query.applyType;
        let data = {
          userKey: USERKEY,
          applyStatus: "all",
          applyNo: "",
          applyUser: "",
          outOrderType:"all",
          outStockApplyNO:"",
          outWarehouseId: "0",
          applyType: applyType,
          beginTime: this.formatDate(beginTime)+" 00:00:00",
          documentNo: '',
          endTime: this.formatDate(endTime.getTime())+" 23:59:59",
          start: 0,
          limit: 9999
        };
        this.requests.getOutStockApplyInfo(data).then(function (respont) {
          if (respont.result == 'success') {
            _this.tableDate = respont.data.rows;
          } else {
            return;
          }
        })
      },
      formatDate(time){
        const date = new Date(time);
        let year = date.getFullYear(),
          month = date.getMonth() + 1,
          day = date.getDate();
        let newTime = year + '-' +
          month + '-' +
          day + ' ' ;
        return newTime;
      },
      checked(index){
        this.applyId=this.tableDate[index].applyId;
      }
    },
    components:{
      Search
    },
    mounted: function() {
      this.initData();
      sessionStorage.setItem("userApplyEditEntity",  null);
      sessionStorage.setItem("isFirst","yes");
      this.$nextTick(function() {
        this.swtichLoading = false;
      })
    },
    update(){
      this.initData();
    },
    watch: { /* 监听 */ }
  }
</script>

<style lang='scss' scope type="text/scss">
  @import "@/style/global.scss";
  #OutInventoryBindQrCode {
    width: 100%;
    height: 100%;
    overflow:auto;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .fault-btn {
      align-self: center;
      text-align: right;
      width:94%;
      margin:20px auto;
      span {
        padding: 9px 22px;
        font-size: $widthWeb16;
        border-radius: 6px;
        background:#fd8723;
        color:#fff;
        .query_icon{
          background:url('../../../assets/home/icon_search1.png')no-repeat center;
          background-size:contain;
          display:inline-block;
          width:30px;
          height:30px;
          vertical-align: bottom;
          padding-right:20px;
        }
      }
    }
    .FaultInformation-list {
      width:96%;
      margin:5px auto;
      & > li {
        position: relative;
        padding: 8px 30px 25px;
        background-color: #fff;
        border: solid 1px #dfdcdc;/*px*/
        box-sizing: border-box;
        border-radius: 15px;
        margin:15px 0;
        .checkbox {
          position: absolute;
          bottom: 0px;
          right: 15px;
          padding:10px 20px;
          border-radius:5px;
          border:1px solid #ddd;
          background:#fb7204;
          color:#fff;
        }
        .link_a{
          display:inline-block;
          color:#000;
          width: 100%;
          .list-title {
            display: flex;
            align-items: center;
            margin-bottom: 18px;
            width: 100%;
            height: 74px;
            font-size: $widthWeb16;
            border-bottom: 1px solid #dfdcdc;/*px*/
            i {
              display: inline-block;
              margin-right: 13px;
              width: 30px;
              height: 33px;
              background: url("../../../assets/home/sheet.png") no-repeat center;
              background-size: 100%;
            }
            p {
              color: #333;
              font-size:$widthWeb16;
              font-weight: 700;
            }
            .title-state {
              position: absolute;
              top: 0;
              right: 0;
              width: 130px;
              height:90px;
              background-image: url("../../../assets/home/label_putintostorage.png");
              background-size: 100% 100%;
            }
            .state-back {
              position: absolute;
              top: 0;
              right: 0;
              width: 130px;
              height:90px;
              background-image: url("../../../assets/home/label_back.png");
              background-size: 100% 100%;
            }
            .state-ling {
              position: absolute;
              top: 0;
              right: 0;
              width: 130px;
              height:90px;
              background-image: url("../../../assets/home/label_ling.png");
              background-size: contain;
            }
            .state-bo {
              position: absolute;
              top: 0;
              right: 0;
              width: 130px;
              height:90px;
              background-image: url("../../../assets/home/label_bo.png");
              background-size: 100% 100%;
            }
          }
        }
        .list-content {
          & > ul li {
            display: flex;
            padding:15px 0;
            .content-txt {
              flex: 1;
              padding-right: 42px;
              display: flex;
              justify-content: space-between;
              border-bottom: solid 1px #dfdcdc;/*px*/
              box-sizing: border-box;
              padding-bottom:20px;
              p {
                align-self: center;
                font-size: $widthWeb16;
                color: #333;

              }
              .txt-num {
                color: #fd8521;
              }
            }
          }
        }
        .list-fault {
          padding-right:42px;
          display: flex;
          justify-content: space-between;
          align-content: center;
          margin-bottom: 12px;
          .fault-describe {
            p {
              font-size: $widthWeb16;
              line-height: 60px;
              white-space:normal;
              .label{
                display:inline-block;
              }
            }
          }
          .fault-btn {
            align-self: center;
            text-align: right;
            width:96%;
            margin:10px auto;
            span {
              display:inline-block;
              padding: 9px 20px;
              font-size:$widthWeb16;
              color: #fafafc;
              background-color: #fd8521;
              border-radius: 6px;
            }
          }
        }

      }
    }
  }
</style>
