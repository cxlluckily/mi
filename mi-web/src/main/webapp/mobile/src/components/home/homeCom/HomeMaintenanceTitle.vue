<template>
 <div id='HomeMaintenanceTitle'>
   <p>
     <i></i>
     <span @click="JumpRouting">{{maintenanceTitle}}</span>
    </p>
 </div>
</template>

<script>
export default {
  name: "HomeMaintenanceTitle",
  data() {
    return {};
  },
  props: ["maintenanceTitle",'maintenancePath'],
  methods: {
    /* 方法 */
    JumpRouting(){
      this.$router.push({path:'RepairDisHistory/' + this.maintenancePath})
    }
  },
  components: {
    /* 复用组件名称 */
  },
  mounted: function() {
    /* 初始化数据 */
  },
  watch: {
    /* 监听 */
  }
};
</script>

<style lang='scss'>
#HomeMaintenanceTitle {
  height: 66px;
  padding-right: 20px;
  box-sizing: border-box;
  p {
    margin-top: 16px;
    float: right;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 0px;
    color: #f56301;
    line-height: 66px;
    padding: 6px 18px;
    border: 1px solid #f56301;
    border-radius: 15px;
    span {
      font-size: .32rem;
      line-height: .32rem;
    }
    i {
      display: inline-block;
      margin-right: 7px;
      width: 14px;
      height: 16px;
      background: url("../../../assets/home/record.png") no-repeat;
      background-size: 100% 100%;
    }
  }
}
</style>
