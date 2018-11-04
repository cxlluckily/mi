<template>
  <section id="InventoryDetaile">
    <section class="InventoryDetaile_form">
      <div class="custom-input">
        <label for="start_time">开始时间</label>
        <input id="start_time" readonly="readonly" type="text" placeholder="请输入起始时间" v-model="startTime">
      </div>
      <div class="custom-input">
        <label for="end_time">结束时间</label>
        <input id="end_time" name='end' readonly="readonly" type="text" placeholder="请选择结束时间" v-model="endTime">
      </div>
      <div class="custom-input">
        <label for="batchNo">批次号</label>
        <input type="text" id="batchNo" v-model="newArray.batchNo" readonly="readonly">
      </div>
      <div class="custom-input">
        <label for="warehouse">盘点负责人</label>
        <input type="text" id="headPerson" v-model="newArray.headPerson" readonly="readonly">
      </div>
      <div class="custom-input">
        <label for="warehouse">盘点仓库</label>
        <input type="text" id="warehouse" v-model="newArray.warehouseName" readonly="readonly">
      </div>
      <div class="custom-input" v-if="status=='before'">
        <label for="status1">盘点状态</label>
        <input type="text" id="status1" value="待盘点" readonly="readonly">
      </div>
      <div class="custom-input" v-if="status=='ongoing'">
        <label for="status2">盘点状态</label>
        <input type="text" id="status2" value="盘点中" readonly="readonly">
      </div>
      <div class="custom-input" v-if="status=='already'">
        <label for="status3">盘点状态</label>
        <input type="text" id="status3" value="盘点完成" readonly="readonly">
      </div>
      <div class="custom-input" v-if="status=='already'||lookType">
        <label for="profit">盈亏数量</label>
        <input type="text" id="profit" v-model="newArray.Count" readonly="readonly">
      </div>
      <div class="custom-input">
        <label for="founder">创建人</label>
        <input type="text" id="founder" v-model="newArray.createUser" readonly="readonly">
      </div>
      <div class="custom-input">
        <label for="Creation">创建时间</label>
        <input type="text" id="Creation" v-model="createTime" readonly="readonly">
      </div>
      <div class="custom-input">
        <label for="Creation">备注</label>
        <p class="text-remark">{{newArray.remark||'暂无备注！'}}</p>
        <!--<input type="text" class="text-remark" id="remark" v-model="newArray.remark" readonly="readonly">-->
      </div>
    </section>
    <section class="InventoryDetaile_table" v-if="newArray.listDetail!==undefined && newArray.listDetail.length>0">
      <table class="tables">
        <caption>盘点清单</caption>
        <thead>
          <tr>
            <th>类型</th>
            <th>名称</th>
            <th>状态</th>
            <th>库存</th>
            <th>盘点</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item,index) in newArray.listDetail" :key="index">
            <td>{{item.categoryName}}</td>
            <td>{{item.partName}}</td>
            <td>{{item.status=='normal'?'好件':'坏件'}}</td>
            <td>{{item.beforeAccount}}</td>
            <td class="operation" v-if="(status=='before'||status=='ongoing')&&!lookType">
              <em @click="minius(index)"> - </em>
              <input type="number" v-model.number="item.aftierAccount">
              <em @click="add(index)"> + </em>
            </td>
            <td v-if="status=='already'||lookType">
              {{item.aftierAccount}}
            </td>
          </tr>
        </tbody>
      </table>
    </section>
    <div class="globalFooter">
      <button class="end_btn" v-if="(status=='before'||status=='ongoing')&&!lookType" @click="update('already')">保存结束盘点</button>
      <button class="btn_save" v-if="(status=='before'||status=='ongoing')&&!lookType" @click="update('ongoing')">保存</button>
      <button @click="fallback" v-if="status=='already'||lookType">返回</button>
    </div>
    <!--日期选择器-->
    <mt-datetime-picker v-model="pickerVisible" ref="picker" type="date" year-format="{value} 年" month-format="{value} 月" date-format="{value} 日" @confirm="handleConfirm" :startDate="startdates">
    </mt-datetime-picker>
  </section>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  export default {
    name: 'InventoryDetaile',
    data() {
      return {
        lookType: '',
        status: '',
        startTime: '',
        endTime: '',
        newArray: {},
        pickerVisible: '',
        type: '',
        startdates: new Date('1970/1/1'),
        createTime: '',
        entitiesArr: []
      }
    },
    methods: {
      getInventoryDetailEntity() {
        const _this = this;
        let taskIds = this.$route.query.taskId;
        _this.lookType = _this.$route.query.look == 'look' ? true : false;
        this.requests.getInventoryDetailEntity(taskIds).then(function(respont) {
          if (respont.result == 'success') {
            // if (!_this.lookType&&respont.data.status == 'already') {
            //   _this.$router.replace({
            //     path: '/InventoryDetaile',
            //     query: {
            //       'taskId': taskIds
            //     }
            //   });
            //   return;
            // }
            _this.newArray = respont.data;
            _this.entitiesArr = respont.data.listDetail;
            _this.startTime = _this.$options.filters.initDate(respont.data.beginTIme, 'ymd');
            _this.endTime = _this.$options.filters.initDate(respont.data.endTIme, 'ymd');
            _this.createTime = _this.$options.filters.initDate(respont.data.createTime, 'ymd');
            _this.status = respont.data.status;
          } else {
            Toast(respont.message)
          }
        });
      },
      update(types) {
        const _this = this;
        const inventoryTaskId = this.newArray.inventoryTaskId;
        const entities = this.newArray.listDetail;
        let entitiesArr = [];
        const USERKEY = localStorage.getItem('userKey');
        for (let i = 0; i < entities.length; i++) {
          let entitiesObject = {};
          entitiesObject.inventoryDetailId = entities[i].inventoryDetailId;
          entitiesObject.beforeAccount = entities[i].beforeAccount;
          entitiesObject.aftierAccount = entities[i].aftierAccount;
          entitiesArr.push(entitiesObject);
        }
        if (types == 'before') {
          types = 'before'
        }
        if (types == 'ongoing') {
          types = 'ongoing'
        }
        const data = {
          "entities": entitiesArr,
          "inventoryTaskId": inventoryTaskId,
          "status": types,
          userKey: USERKEY
        };
        this.requests.updateInventoryTask(data).then(function(respont) {
          if (respont.result == 'success') {
            Toast('修改成功');
            _this.$router.push("/Inventory");
            return;
          } else {
            Toast('修改失败');
            return;
          }
        });
      },
      account(number) {
        this.purchaseQuantity = number;
      },
      openPicker(type) {
        this.type = type;
        this.$refs.picker.open();
      },
      handleConfirm(data) {
        let date = this.$options.filters.initDate(data);
        if (this.type == 'start') {
          this.startTime = date;
        }
        if (this.type == 'end') {
          date = this.$options.filters.initDate(data);
          this.startdates = new Date(date);
          this.endTime = date;
        }
      },
      add: function(index) {
        this.entitiesArr[index].aftierAccount++;
      },
      minius: function(index) {
        if (this.entitiesArr[index].aftierAccount > 1) {
          this.entitiesArr[index].aftierAccount--;
        }
      },
      goBackMyTask() {
        this.$router.push({
          path: '/MyTaskView'
        })
      },
      goBackMyList() {
        this.$router.push({
          path: '/NewInventoryList'
        })
      },
      fallback() {
        if(this.$route.query.look=='look'){
          this.goBackMyList();
          return;
        }
        if (this.$route.query.taskNotice == 'true') {
          this.goBackMyTask();
        } else {
          this.$router.push({
            path: '/Inventory',
          })
        }
      },
    },
    mounted: function() {
      this.getInventoryDetailEntity();
      if (this.$route.query.taskNotice == 'true') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyTask, false);
      }
      if (this.$route.query.look == 'look') {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBackMyList, false);
      }
    },
    update() {
      this.getInventoryDetailEntity();
    },
    beforeDestroy() {
      window.removeEventListener('popstate', this.goBackMyTask);
      window.removeEventListener('popstate', this.goBackMyList);
    }
  }
</script>

<style lang="scss" scope type="text/scss">
  @import "@/style/global.scss";
  #InventoryDetaile {
    width: 100%;
    overflow-y: auto;
    .InventoryDetaile_form {
      width: 94%;
      background: #fff;
      border-radius: 5px;
      border: 1px solid #e3e7e0;
      /*px*/
      margin: 10px auto;
      padding: 10px 0;
      .custom-input {
        margin: 0 15px;
        display: flex;
        align-items: start;
        input {
          width: 80%;
          line-height: 80px;
          text-align: right;
          font-size: $widthWeb25;
          border-bottom: 1px solid #e5e1e2;
        }
        .text-remark {
          text-align: left;
          width: 80%;
          line-height: 80px;
          font-size: $widthWeb25;
          border-bottom: 1px solid #e5e1e2;
          white-space: normal;
          word-break: break-all
        }
        label {
          white-space: nowrap;
          padding-right: 15px;
          color: #c6c6c6;
          line-height: 80px;
          font-size: $widthWeb25;
          width: 25%;
        }
      }
      .custom-input:last-child {
        border-bottom: none;
      }
    }
    .InventoryDetaile_table {
      @extend .InventoryDetaile_form;
      padding-bottom: 50px;
      .tables {
        width: 96%;
        margin: auto;
        font-size: $widthWeb25;
        caption {
          text-align: left;
          color: #fe8b21;
          text-indent: 10px;
          padding: 10px 0;
          font-size: $widthWeb25;
        }
        thead {
          background: #eee;
        }
        tr {
          th,
          td {
            text-align: center;
            border: none;
            padding: 10px 5px;
          }
          .operation {
            em {
              padding: 10px;
              color: #333333;
              font-size: $widthWeb14;
              display: inline-block;
              border: 1px solid #ddd;
              /*px*/
              border-radius: 5px;
            }
            input[type='number'] {
              width: 1rem;
              background: #eee;
              text-align: center;
              font-size: $widthWeb25;
            }
          }
        }
        tbody {
          tr {
            border-bottom: 1px solid #e8e5e6;
            /*px*/
          }
        }
      }
    }
    .end_btn {
      padding: 10px 15px;
      background: #f90706;
      margin-right: 10px;
      color: #fff;
      display: inline-block;
    }
    .btn_save {
      padding: 10px 30px;
      background: #fc6806;
      color: #fff;
      display: inline-block;
    }
    .btn_back {
      @extend .btn_save;
    }
    [v-cloak] {
      display: none;
    }
  }
</style>
