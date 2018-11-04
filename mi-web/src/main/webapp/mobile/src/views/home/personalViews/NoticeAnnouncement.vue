<template>
  <div id='NoticeAnnouncement'>
      <div class="Notice">
        <div class="NoticeJpg"></div>
      </div>
  </div>
</template>

<script>
  export default {
    name: 'NoticeAnnouncement',
    data () {
      return {

      }
    },
    methods: { /* 方法 */ },
    components: { /* 复用组件名称 */ },
    mounted: function() { /* 初始化数据 */ },
    watch: { /* 监听 */ }
  }
</script>

<style lang='scss' type="text/scss" scope>
  @import "@/style/global.scss";
  #NoticeAnnouncement {
    .NoticeJpg{
      background: url("../../../assets/home/building.png");
      background-size: 100% 100%;
    }
  }
</style>
