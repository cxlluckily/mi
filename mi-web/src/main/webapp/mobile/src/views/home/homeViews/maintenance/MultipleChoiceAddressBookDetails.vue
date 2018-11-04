<template>
  <div id='MultipleChoiceAddressBookDetails'>
    <GlobalAddressBook @pathRouter='getPathRouter'></GlobalAddressBook>
  </div>
</template>

<script>
import GlobalAddressBook from "@/components/globalCom/GlobalAddressBook";
export default {
  name: "MultipleChoiceAddressBookDetails",
  data() {
    return {
    };
  },
  methods: {
    /* 方法 */
    getPathRouter(val) {
      const THIS = this;
      this.api.SetSendSingleDataAll(val).then(function(data) {
        THIS.$router.push({path: '/RDSendSingle/' + val.maintenanceApplyId});
      });
    }
  },
  components: {
    /* 复用组件名称 */
    GlobalAddressBook
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
#MultipleChoiceAddressBookDetails {
}
</style>
