<template>
  <div id='GlobalCheckbox'>
    <div class="CheckboxList clearfix">
      <label v-for="itemcheck in checkboxData" :for="itemcheck.breakdownInfoId" :key="itemcheck.breakdownInfoId">
            <i class="icon"
               :class="{'iconSelected' : '-1' != checkedNames.indexOf(itemcheck.breakdownInfoId)}"></i>
            <input type="checkbox"
                   class="checkNone"
                   :id="itemcheck.breakdownInfoId"
                   :value="itemcheck.breakdownInfoId"
                   v-model="checkedNames">
            <span class="explainTxt">{{itemcheck.breakAbbreviated}}</span>
          </label>
    </div>
  </div>
</template>

<script>
  export default {
    name: "GlobalCheckbox",
    data() {
      return {
        checkedNames: [] // 多选数据储存
      };
    },
    props: ["checkboxData"],
    methods: {
      /* 方法 */
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function() {
      /* 初始化数据 */
    },
    updated: function() {
    },
    watch: {
      /* 监听 */
      checkedNames: function(val) {
        // let sparePartIdArr = [];
        // for(let i = 0, II = this.checkboxData.length; i < II; i ++) {
        //   if(this.checkedNames.indexOf(this.checkboxData[i].breakdownInfoId) != '-1') {
        //     sparePartIdArr.push(this.checkboxData[i].sparePartId);
        //   }
        // }
        // sparePartIdArr = [...new Set(sparePartIdArr)];
        // console.log(sparePartIdArr)
        this.$emit("checkboxCurrentType", val);
      },
      checkboxData: function(val) {
        if(!val || val.length == 0 ) {
          this.checkedNames = [];
          return;
        }
        for (var i = 0, II = val.length; i < II; i++) {
          if (val[i].checked) {
            this.checkedNames.push(val[i].breakdownInfoId);
          }
        }
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #GlobalCheckbox {
    .CheckboxList {
      label {
        height: 44px;
        font-size: 0;
        float: left;
        min-width: 50%;
        .icon {
          display: inline-block;
          margin-right: 18px;
          width: 18px;
          height: 18px;
          background: url("../../assets/home/checkbox_no.png") no-repeat;
          background-size: 100% 100%;
        }
        .iconSelected {
          background: url("../../assets/home/checkbox_sel.png") no-repeat;
          background-size: 100% 100%;
        }
        .checkNone {
          display: none;
        }
        .explainTxt {
          font-size: $widthWeb18;
          color: #333;
          line-height: 44px;
        }
      }
    }
  }
</style>
