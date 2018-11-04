<template>
  <div id='GlobalAddressBook'>
    <div class="GlobalAddressBook-main">
      <div class="main-search">
        <label for="sea"></label>
        <input type="text" id="sea" class="search-inp" placeholder="请输入姓名搜索" v-model="searchInp">
      </div>
      <div class="main-indexList">
        <mt-index-list>
          <mt-index-section v-for="itemList in addressBookData" :key="itemList.index" :index="itemList.index">
            <li class="main-list" v-for="item in itemList.children" :key=item.userId>
              <div class="list-checkIconImg">
                <div class="checkIconImg-icon" v-show="panelShow">
                  <label>
                                        <i :class="{'checkIconImg-selected' : '-1' != checkedNames.indexOf(item.userId)}"
                                           :data-index="checkedNames.indexOf(item.userId)"></i>
                                        {{checkedNames.indexOf(item.userId)}}
                                        <input type="checkbox"
                                               class="checkNone"
                                               :value="item.userId"
                                               v-model="checkedNames">
                                      </label>
                </div>
                <div class="checkIconImg-img" @click="nameinformation($event)" :data-sex="item.sex" :data-photoUrl="item.photoUrl" :data-realName="item.realName" :data-orgName="item.orgName" :data-phone="item.phone" :data-email="item.email" :data-position="item.position">
                  <img v-if="item.photoUrl" :src="item.photoUrl" alt="头像">
                  <img v-else-if="item.sex == 'female'" src="../../assets/home/username.png" alt="头像">
                  <img v-else src="../../assets/home/user_pic.png" alt="头像">
                </div>
              </div>
              <div class="list-nameDepartment">
                <div class="nameDepartment-name">
                  <p>{{item.realName}}</p>
                  <p>{{item.orgName}}</p>
                </div>
                <div class="nameDepartment-phone">
                  <a :href="'tel:' + item.phone">
                    <i></i>
                  </a>
                </div>
              </div>
            </li>
          </mt-index-section>
        </mt-index-list>
      </div>
    </div>
    <!-- 遮罩层 -->
    <div class="GlobalAddressBook-mask" v-show="switchDetails"></div>
    <!-- 个人详情弹出框 -->
    <div class="GlobalAddressBook-details" v-show="switchDetails" v-for="(itemDetails, index) in detailsData" :key="index">
      <div class="details-title">
        <div class="title-portrait">
          <img :src="itemDetails.photoUrl" alt="头像">
        </div>
        <div class="portait-close" @click="closeDetails">
          <i></i>
        </div>
        <p class="tilte-name">{{itemDetails.realName}}</p>
        <p class="title-position">{{itemDetails.orgName}}</p>
      </div>
      <div class="details-detailss">
        <p>
          <span class="detailss-column">部门 :</span>
          <span>{{itemDetails.orgName}}</span>
        </p>
        <div class="detailss-phone">
          <p>
            <span class="detailss-column">手机 :</span>
            <span>{{itemDetails.phone}}</span>
          </p>
          <a :href="'tel:' + itemDetails.phone">
            <i></i>
          </a>
        </div>
        <p>
          <span class="detailss-column">邮箱 :</span>
          <span class="detailss-email">{{itemDetails.email || '暂无邮箱!'}}</span>
        </p>
        <!-- <p>
                        <span class="detailss-column">地址 :</span>
                        <span>{{itemDetails.position || '没有填写地址!没有填写地址没有填写地址'}}</span>
                    </p> -->
      </div>
    </div>
    <!-- 派工多选底部确定 -->
    <div class="globalFooter" v-show="panelShow">
      <button @click="btnSubmit">确 定</button>
    </div>
    <!-- loading -->
    <div class="gloobalLoading" v-show="swtichLoading">
      <div class="loadingGif"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "GlobalAddressBook",
    data() {
      return {
        swtichLoading: true,
        switchDetails: false, // 个人信息弹出框切换
        addressBookData: [], // 储存通讯录数据
        checkedNames: [], // 多选数据储存
        detailsData: [{}], // 弹框数据储存
        maintenanceApplyId: null,
        searchInp: ""
      };
    },
    props: {
      panelShow: {
        type: Boolean
      }
    },
    methods: {
      /* 方法 */
      nameinformation(event) {
        const THISDOM = $(event.currentTarget);
        this.detailsData = [{}];
        this.detailsData[0].photoUrl = THISDOM.attr("data-photoUrl");
        this.detailsData[0].sex = THISDOM.attr("data-sex");
        if (this.detailsData[0].photoUrl == '') {
          if(this.detailsData[0].sex == 'female') {
            this.detailsData[0].photoUrl = require('../../assets/home/username.png')
          }else {
            this.detailsData[0].photoUrl = require('../../assets/home/user_pic.png')
          }
        }
        this.detailsData[0].realName = THISDOM.attr("data-realName");
        this.detailsData[0].orgName = THISDOM.attr("data-orgName");
        this.detailsData[0].email = THISDOM.attr("data-email");
        this.detailsData[0].phone = THISDOM.attr("data-phone");
        this.detailsData[0].position = THISDOM.attr("data-position");
        this.switchDetails = true;
      },
      closeDetails() {
        this.switchDetails = false;
      },
      initData(val) {
        this.api.addressBookDataAll(val).then(data => {
          this.addressBookData = data.data;
          console.log(this.addressBookData);
          this.$nextTick(function() {
            this.swtichLoading = false;
          });
        });
      },
      btnSubmit() {
        const paramObj = {
          maintenanceApplyId: this.maintenanceApplyId,
          userIds: this.checkedNames
        };
        console.log(paramObj);
        this.$emit("pathRouter", paramObj);
      }
    },
    components: {
      /* 复用组件名称 */
    },
    mounted: function() {
      /* 初始化数据 */
      this.initData();
      if (this.panelShow) {
        let userIdArr = this.$route.params.nameIDArr.split(",");
        this.maintenanceApplyId = userIdArr.splice(0, 1).join();
        for (let i = 0, II = userIdArr.length; i < II; i++) {
          this.checkedNames.push(+userIdArr[i]);
        }
      }
    },
    watch: {
      /* 监听 */
      searchInp: function(val) {
        this.initData(val);
      }
    }
  };
</script>

<style lang='scss'>
  @import "@/style/global.scss";
  #GlobalAddressBook {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: #faf6ec;
    .gloobalLoading {
      height: 100%;
      .loadingGif {
        background: url("../../assets/home/loading.gif");
        background-size: 100% 100%;
      }
    }
    .GlobalAddressBook-main {
      width: 100%;
      height: calc(100% - 70px);
      .main-search {
        position: relative;
        width: 100%;
        height: 78px;
        padding: 20px 24px 0;
        box-sizing: border-box;
        label {
          position: absolute;
          top: 31px;
          right: 37px;
          width: 39px;
          height: 39px;
          background: url("../../assets/home/icon_search.png") no-repeat;
          background-size: 100% 100%;
        }
        .search-inp {
          width: 100%;
          height: 58px;
          font-size: $widthWeb18;
          color: #333;
          text-align: center;
          border-radius: 15px;
          border: 1px solid #dfdcdc;
          box-sizing: border-box;
        }
      }
      .main-indexList {
        width: 100%;
        height: calc(100% - 78px);
        padding-top: 14px;
        box-sizing: border-box;
        .mint-indexlist {
          width: 100%;
          height: 100%;
          .mint-indexlist-content {
            width: 100%;
            height: 100% !important;
            .mint-indexsection {
              .mint-indexsection-index {
                padding: 0 0 0 25px;
                color: #666;
                font-size: $widthWeb22;
                line-height: 50px;
                background-color: transparent;
              }
              ul {
                background-color: #fff;
                .main-list {
                  display: flex;
                  padding: 27px 24px 0 24px;
                  font-size: 0;
                  box-sizing: border-box;
                  &:nth-last-child(1) {
                    .list-nameDepartment {
                      border: 0;
                    }
                  }
                  .list-checkIconImg {
                    display: flex;
                    align-items: center;
                    padding-bottom: 24px;
                    box-sizing: border-box;
                    .checkIconImg-icon {
                      width: 36px;
                      height: 72px;
                      line-height: 90px;
                      margin-right: 22px;
                      label {
                        display: inline-block;
                        width: 36px;
                        height: 72px;
                        i {
                          display: inline-block;
                          width: 18px;
                          height: 18px;
                          background: url("../../assets/home/checkbox_no.png") no-repeat;
                          background-size: 100% 100%;
                        }
                        .checkIconImg-selected {
                          background: url("../../assets/home/checkbox_sel.png") no-repeat;
                          background-size: 100% 100%;
                        }
                        .checkNone {
                          display: none;
                        }
                      }
                    }
                    .checkIconImg-img {
                      width: 73px;
                      height: 73px;
                      img {
                        width: 100%;
                        height: 100%;
                        border-radius: 50%;
                      }
                    }
                  }
                  .list-nameDepartment {
                    display: flex;
                    justify-content: space-between;
                    margin-left: 32px;
                    padding-bottom: 24px;
                    padding-right: 30px;
                    box-sizing: border-box;
                    width: 527px;
                    border-bottom: 1px solid #dfdcdc;
                    .nameDepartment-name {
                      color: #333;
                      font-size: $widthWeb22;
                      line-height: 42px;
                      p:nth-child(2) {
                        color: #999;
                      }
                    }
                    .nameDepartment-phone {
                      width: 72px;
                      height: 100%;
                      a {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        width: 100%;
                        height: 100%;
                        i {
                          display: inline-block;
                          width: 48px;
                          height: 42px;
                          background: url("../../assets/home/call.png") no-repeat;
                          background-size: 100% 100%;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          .mint-indexlist-nav {
            border: 0;
            background-color: transparent;
            .mint-indexlist-navlist {
              .mint-indexlist-navitem {
                padding: 7px 24px;
                color: #333;
                font-size: $widthWeb22;
              }
            }
          }
        }
      }
    } // 遮罩层
    .GlobalAddressBook-mask {
      z-index: 300;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.3);
    } //个人详情弹出框
    .GlobalAddressBook-details {
      z-index: 400;
      position: absolute;
      top: 44%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 540px;
      .details-title {
        width: 100%;
        position: relative;
        top: 0;
        left: 0;
        padding-bottom: 16px;
        text-align: center;
        background: linear-gradient( #fce4bd, #faf0dd); // border-bottom: 1px solid #e6c197;
        border-radius: 15px 15px 0 0;
        &:before {
          z-index: -100;
          position: absolute;
          top: -50px;
          left: 50%;
          transform: translateX(-50%);
          content: " ";
          background: url("../../assets/home/Personal_tit.png") no-repeat;
          background-size: 100% 100%;
          width: 127px;
          height: 53px;
        }
        .title-portrait {
          width: 100%;
          height: 73px;
          img {
            margin-top: -26px;
            width: 73px;
            height: 100%;
            border-radius: 50%;
          }
        }
        .tilte-name {
          color: #f15510;
          font-size: $widthWeb30;
          line-height: 48px;
        }
        .title-position {
          color: #999;
          font-size: $widthWeb16;
          line-height: 34px;
        }
        .portait-close {
          position: absolute;
          top: 2px;
          right: 19px;
          width: 70px;
          height: 70px;
          text-align: center;
          line-height: 70px;
          i {
            display: inline-block;
            width: 35px;
            height: 35px;
            background: url("../../assets/home/close.png") no-repeat;
            background-size: 100% 100%;
          }
        }
      }
      .details-detailss {
        padding: 26px 62px 82px 42px;
        background-color: #faf6ec;
        border-top: 1px solid #e6c197;
        border-radius: 0 0 15px 15px;
        p {
          display: flex;
          color: #333;
          font-size: $widthWeb22;
          line-height: 58px;
          .detailss-column {
            display: inline-block;
            width: 98px;
            flex-shrink: 1;
          }
          .detailss-email {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
          span {
            font-size: $widthWeb22;
            &:nth-last-child(1) {
              flex: 1;
            }
          }
        }
        .detailss-phone {
          position: relative;
          a {
            position: absolute;
            top: 0;
            right: 0;
            width: 72px;
            height: 62px;
            text-align: right;
            line-height: 62px;
            i {
              display: inline-block;
              margin-top: 8px;
              width: 48px;
              height: 42px;
              background: url("../../assets/home/call.png") no-repeat;
              background-size: 100% 100%;
            }
          }
        }
      }
    }
  }
</style>
