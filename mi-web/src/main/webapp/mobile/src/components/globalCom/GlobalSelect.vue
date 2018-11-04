<template>
  <div id='GlobalSelect'>
    <div class="GlobalSelect-upload" @click="uploadToggle" :style="titleStyle">下拉框</div>
    <transition name="fade">
      <div class="GlobalSelect-content" v-show="isContent">
        <ul class="Content-lists">
          <li @click="listsCurrent($event)">你好 11</li>
          <li @click="listsCurrent($event)">你好 21</li>
          <li @click="listsCurrent($event)">你好 31</li>
          <li @click="listsCurrent($event)">你好 41</li>
          <li @click="listsCurrent($event)">你好 51</li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script>
  export default {
    name: "GlobalSelect",
    data() {
      return {
        isContent: true,
        uploadStyle: {},
        listsLiStyle: {}
      };
    },
    props: ["titleStyle"],
    methods: {
      uploadToggle() {
        this.isContent = !this.isContent;
      },
      listsCurrent(event) {
        const upload = document.querySelector(".GlobalSelect-upload");
        upload.innerHTML = event.currentTarget.innerHTML;
        this.isContent = false;
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
  #GlobalSelect {
    margin: 100px;
    .GlobalSelect-upload {
      width: 200px;
      height: 30px;
      border: 1px solid #000;
      line-height: 30px;
      border-radius: 4px;
      cursor: pointer;
    }
    .GlobalSelect-content {
      margin-top: 2px;
      width: 200px;
      border: 1px solid #000;
      border-radius: 4px;
      cursor: pointer;
      .Content-lists li {
        height: 30px;
        line-height: 30px;
        &:hover {
          background-color: green;
        }
      }
    }
  }
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.5s;
  }
  .fade-enter,
  .fade-leave-to {
    opacity: 0;
  }
</style>
