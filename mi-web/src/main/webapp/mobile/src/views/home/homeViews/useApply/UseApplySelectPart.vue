<template>
  <section id="AddListApply">
    <div class="itemsAdd_contain" ref="bscroll">
      <div class="itemsAdd_header">
        <div class="itemsAdd_seach">
          <i class="search_title">搜索</i>
          <input class="search_input" type="text" placeholder="物品名称/型号" v-model="searchContent">
          <i class="search_icon" @click="search"></i>
        </div>
      </div>
      <div class="itemsAdd_list" v-show="applyArray.length">
        <ul class="itemsAddInfor_ul">
          <li v-for="(item,index) in applyArray">
            <div class="flex_text_title">
             <span class="checkbox">
               <input type="checkbox" :value="item.sparePartId" :id="item.sparePartId" v-model="checkedNames">
                <label :for="item.sparePartId"></label>
              </span>
            </div>
            <div class="flex_text_right">
              <div class="checked_span">{{item.partName}}</div>
              <div class="li_list">
                <div class="flex_pic">
                  <span class="pic_img" v-if="item.imageUrl!=='noImage'"><img :src="item.imageUrl" alt=""></span>
                  <span class="pic_img" v-if="item.imageUrl=='noImage'"><img src="../../../../assets/home/pic2.png"
                                                                             alt=""></span>
                </div>
                <div class="flex_text">
                  <div class="flex_text_con">
                    <div class="flex_text_p">
                      <div class="flex_p">
                        <label>分类</label>
                        <span class="flex_text_num" type="number">{{item.categoryName}}</span>
                      </div>
                      <div class="flex_p">
                        <label>品牌</label>
                        <span class="flex_text_num">{{item.brand}}</span>
                      </div>
                    </div>
                    <div class="flex_text_p">
                      <div class="flex_p">
                        <label>型号</label>
                        <span class="flex_text_num">{{item.specificationModel}}</span>
                      </div>
                      <div class="flex_p">
                        <label>状态</label>
                        <span class="flex_text_num">{{applyType=='return'?'坏件':'好件'}}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="foot_flex">
      <span class="btn_enter" @click="enterFunction">选择</span>
    </div>
    <div class="gloobalEmptyPrompt" v-show="!applyArray.length" style="margin-top:100px">
      <div class="emptyPrompt">暂 无 数 据 !</div>
    </div>
  </section>
</template>
<script>
  export default {
    name: "AddListApply",
    data() {
      return {
        searchContent: '',
        applyArray: [],
        applyId: '',
        applyType:'',
        outWarehouseId: null,
        checkedNames: [],
        allArray: []
      }
    },
    methods: {
      getSparePartList(flag) {
        let userApplyEditEntity = JSON.parse(sessionStorage.getItem("userApplyEditEntity"));
        let applyType = sessionStorage.getItem("applyType");
        this.applyType = applyType;
        if (userApplyEditEntity != null) {
          this.outWarehouseId = userApplyEditEntity.outWarehouseId;
        }
        const _this = this;
        const USERKEY = localStorage.getItem('userKey');
        let data = {
          searchContent: this.searchContent,
          userKey: USERKEY,
          status: "normal",
          outWarehouseId: this.outWarehouseId
        };
        if (applyType == 'return') {
          data.status = 'bad';
        }
        this.requests.getSparePartList(data).then(function (responseble) {
          if (responseble.result == 'success') {
            _this.applyArray = responseble.data;

            if (flag == 1) {
              _this.allArray = responseble.data;
            }

          }
          else {
            return;
          }
        })
      },
      search() {
        this.getSparePartList();
      },
      enterFunction() {
        let arrayDate = [];
        const _this = this;
        let lenGth = this.applyArray.length;
        for (let inject = 0; inject < lenGth; inject++) {
          if (this.checkedNames.indexOf(this.applyArray[inject].sparePartId) >= 0) {
            this.applyArray[inject].applyCount = 1;
            arrayDate.push(this.applyArray[inject]);
          }
        }
        let arrayStr = encodeURIComponent(JSON.stringify(arrayDate));
        let applyType = sessionStorage.getItem("applyType");
        let applyId = sessionStorage.getItem('applyId');
        if (this.checkedNames.length == 0) {
          sessionStorage.setItem("dataObj", '');
        } else {
          sessionStorage.setItem("dataObj", arrayStr);
        }
        switch (applyType) {
          case "use":
            this.$router.push({
              path: 'UseApplyListEdit',
              name: 'UseApplyListEdit',
              query: {applyId: applyId, applyType: "use"}
            });
            break;
          case "transfer":
            this.$router.push({
              path: 'TransferApplyEdit',
              name: 'TransferApplyEdit',
              query: {applyId: applyId, applyType: "transfer"}
            });
            break;
          case "return":
            this.$router.push({
              path: 'ReturnApplyEdit',
              name: 'ReturnApplyEdit',
              query: {applyId: applyId, applyType: "return"}
            });
            break;
        }
      },
      getChecked() {
        let getListArray = [];
        if (sessionStorage.getItem("dataObj")) {
          getListArray = JSON.parse(decodeURIComponent(sessionStorage.getItem("dataObj")));
        } else {
          getListArray = [];
        }
        if (getListArray.length) {
          for (let i = 0; i < getListArray.length; i++) {
            this.checkedNames.push(getListArray[i].sparePartId);
          }
        }
      }
    },
    mounted() {
      this.getSparePartList(1);
      this.applyId = sessionStorage.getItem('applyId');
      this.getChecked();
    }
  }
</script>
<style lang="scss" scope type="text/scss">
  @import "@/style/global.scss";

  #AddListApply {
    overflow: scroll;
    .itemsAdd_contain {
      background: #fff;
      width: 94%;
      margin: auto;
      border: 2px solid #e0dbdc; /*px*/
      .itemsAdd_header {
        display: flex;
        .itemsAdd_seach {
          border: 2px solid #dedede; /*px*/
          border-radius: 20px;
          text-align: center;
          width: 90%;
          margin: 20px auto;
          background: #fff;
          .search_input {
            font-size: $widthWeb16;
            line-height: 80px;
            width: 66%;
          }
          .search_title {
            font-size: $widthWeb16;
            display: inline-block;
          }
          .search_icon {
            background: url('../../../../assets/home/icon_search.png') no-repeat center;
            width: 30px;
            height: 30px;
            background-size: contain;
            display: inline-block;
            vertical-align: middle;
          }
        }
      }
      .extend {
        background: url('../../../../assets/home/arrow.png') no-repeat center;
        width: 30px;
        height: 30px;
        background-size: contain;
        display: inline-block;
      }
      .itemsAdd_list {
        width: 94%;
        margin: auto;
        .itemsAddInfor_ul {
          padding: 10px 0 50px;
          li {
            display: flex;
            justify-content: center;
            .flex_text_title {
              .checkbox {
                input {
                  vertical-align: middle;
                  visibility: hidden;
                }
                input[type='checkbox']:checked + label:after {
                  background: url("../../../../assets/home/checkbox_sel.png") no-repeat center;
                  background-size: contain;
                  width: 22px;
                  height: 22px;
                }
                label {
                  position: relative;
                  font-size: $widthWeb25;
                  &:after {
                    position: absolute;
                    top: 4px;
                    left: -8px;
                    width: 22px;
                    height: 22px;
                    background: url("../../../../assets/home/checkbox_no.png") no-repeat center;
                    background-size: contain;
                    content: '';
                  }
                }
              }
            }
            .flex_text_right {
              width: 94%;
              border-bottom: 2px solid #dedcdd; /*px*/
              margin-bottom: 15px;
              padding-bottom: 15px;
              .checked_span {
                margin-bottom: 15px;
              }
              .li_list {
                display: flex;
                align-items: center;
                .flex_pic {
                  /*flex: 2;*/
                  margin-right: 20px;
                  .pic_img {
                    width: 70px;
                    display: inline-block;
                    img {
                      display: inline-block;
                      width: 100%;
                    }
                  }
                }
                .flex_text {
                  flex: 9;
                  font-size: $widthWeb16;
                  .flex_text_con {
                    .flex_text_p {
                      display: flex;
                      padding: 5px 0;
                      .flex_p {
                        width: 50%;
                        display: inline-block;
                      }
                      label {
                        color: #8f8f94;
                        white-space: nowrap;
                      }
                      .flex_text_num {
                        display: inline-block;
                        padding: 0 15px;
                        width: 120px;
                        overflow: hidden;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                        vertical-align: middle;
                      }
                      input {
                        border: 2px solid #ddd; /*px*/
                        margin-left: 15px;
                        border-radius: 15px;
                      }
                    }
                  }
                }
              }
            }

          }
        }
        .pagesNum {
          margin-bottom: 15px;
          span {
            display: inline-block;
            padding: 15px 30px;
            border: 1px solid #8f8f94; /*px*/
            border-radius: 10px
          }
        }
      }
    }
    .foot_flex {
      width: 100%;
      height: 80px;
      position: fixed;
      bottom: 0;
      left: 0;
      z-index: 1;
      text-align: right;
      background-color: #eee;
      .btn_enter {
        width: 220px;
        height: 100%;
        font-size: $widthWeb25;
        display: inline-block;
        text-align: center;
        line-height: 80px;
        color: #fff;
        background-color: #fc6806;
      }
    }
  }
</style>
