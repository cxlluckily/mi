<template>
  <div id='GlobalUploadImg'>
    <ul class="GlobalUploadImg-list">
      <li v-for="uploadImgItem in uploadImgData"
          :key="uploadImgItem">
        <div class="list-img">
          <i class="icon"></i>
          <div class="Img">
            <img src="../../assets/home/pic2.png">
          </div>
        </div>
      </li>
      <li class="appendFile">
        <label for="addFileUpload"></label>
        <input type="file"
               name="fileUpload"
               id="addFileUpload"
               multiple=multiple
               accept="image/x-png,image/gif,image/jpeg,image/bmp,capture=camera"
               style="display:none"
               @change="fileImgData($event)">
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "GlobalUploadImg",
  data() {
    return {
      uploadImgData: []
    };
  },
  methods: {
    // 初始化 file
    initFile() {
      $("#addFileUpload").remove();
      $(".appendFile").append(`<input type="file"
               name="fileUpload"
               id="addFileUpload"
               multiple=multiple
               accept="image/x-png,image/gif,image/jpeg,image/bmp"
               style="display:none">`);
    },
    fileImgData(event) {
      const _THIS = this;
      let myFrom = new FormData();
      let uploadFiles = event.target.files;
      // 为零的时候不向下运行
      if (!uploadFiles.length) {
        return;
      }
      // 循环获取图片路径
      for (let i = 0, LL = uploadFiles.length; i < LL; i++) {
        myFrom.append(i, uploadFiles[i]);
      }
      // 请求数据
      this.api.updateDataAll(myFrom).then(function(data) {
        console.log(data);
        _THIS.uploadImgData = data.data;
        _THIS.initFile();
      });
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
    uploadImgData: function(val) {
      this.initFile();
    }
  }
};
</script>

<style lang='scss'>
@import "@/style/global.scss";
#GlobalUploadImg {
  margin-top: g-rem(19);
  .GlobalUploadImg-list {
    display: flex;
    li {
      width: g-rem(144);
      height: g-rem(144);
      position: relative;
      margin-right: g-rem(20);
      label {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: g-rem(100);
        height: g-rem(100);
        background: url("../../assets/home/upload_pic.png") no-repeat;
        background-size: 100% 100%;
      }
      .list-img {
        position: absolute;
        z-index: 100;
        .icon {
          position: absolute;
          top: g-rem(-12);
          right: g-rem(-12);
          width: g-rem(48);
          height: g-rem(48);
          background: url("../../assets/home/icon_delete.png") no-repeat;
          background-size: 100% 100%;
        }
        img {
          display: inline-block;
          width: g-rem(144);
          height: g-rem(144);
        }
      }
      &:nth-child(3) {
        margin-right: 0;
      }
    }
  }
}
</style>
