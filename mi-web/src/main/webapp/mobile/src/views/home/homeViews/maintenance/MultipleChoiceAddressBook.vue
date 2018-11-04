<template>
  <div id='MultipleChoiceAddressBook'>
    <GlobalAddressBook @pathRouter='getPathRouter'></GlobalAddressBook>
  </div>
</template>

<script>
import GlobalAddressBook from "@/components/globalCom/GlobalAddressBook";
export default {
  name: "MultipleChoiceAddressBook",
  data() {
    return {
    };
  },
  methods: {
    /* 方法 */

    getPathRouter(val) {
      const THIS = this;
      this.api.SetSendSingleDataAll(val).then(function(data) {
        THIS.$router.push({ path: "/RepairDispatching"});
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
#MultipleChoiceAddressBook {
}
</style>
