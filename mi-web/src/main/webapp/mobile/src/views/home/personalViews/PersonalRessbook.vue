<template>
      <section>
        <GlobalAddressBook :panelShow="panelShow">

        </GlobalAddressBook>
      </section>
</template>
<script>
  import GlobalAddressBook from '@/components/globalCom/GlobalAddressBook.vue'
  export default {
    name: 'PersonalCom',
    data() {
      return {
        panelShow: false
      }
    },
    methods: { /* 方法 */
      /*密码管理*/
      passManerge(){
        this.$router.push({
          path:'/PersonalPassMannerge'
        })
      },
      ressBook(){
        this.$router.push({
          path:'/PersonalRessbook'
        })
      }
    },
    components: { /* 复用组件名称 */
      GlobalAddressBook
    },
    mounted: function() { /* 初始化数据 */ },
    watch: { /* 监听 */ }
  }
</script>
