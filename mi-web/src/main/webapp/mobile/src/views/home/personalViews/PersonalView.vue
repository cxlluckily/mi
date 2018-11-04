<template>
  <div id='PersonalCom'>
    <div class="content">
      <div class="content-information">
        <div class="portrait" v-if="photoUrl">
          <!--photoUrl不为null或undefined或者‘’时显示网络图像-->
          <img :src="photoUrl" alt="" style="height:100%">
        </div>
        <div v-if="!photoUrl">
          <!--photoUrl为null或undefined或者‘’时显示男女图像-->
          <div class="portrait" v-if="sixName=='男'">
            <img src="../../../assets/home/user_pic.png" alt="">
          </div>
          <div class="portrait" v-if="sixName=='女'">
            <img src="../../../assets/home/username.png" alt="">
          </div>
        </div>
        <div class="information">
          <p>{{realName}}</p>
          <p v-if="position">{{orgName}}<span v-if="orgName">--</span>{{position}}</p>
          <p v-else>{{orgName}}</p>
        </div>
      </div>
      <div class="content-column">
        <ul class="lists">
          <li @click="ressBook">
            <div class="lists-fun">
              <img src="../../../assets/home/percenter_addlist.png" alt="">
              <p>通讯录</p>
            </div>
            <div class=lists-go>
              <i></i>
            </div>
          </li>
          <li @click="passManerge">
            <div class="lists-fun">
              <img src="../../../assets/home/percenter_pwd.png" alt="">
              <p>密码修改</p>
            </div>
            <div class=lists-go>
              <i></i>
            </div>
          </li>
          <!-- <li @click="noticeAnnouncement">
                          <div class="lists-fun">
                              <img src="../../../assets/home/percenter__notice.png" alt="">
                              <p>通知公告</p>
                          </div>
                          <div class=lists-go>
                              <i></i>
                          </div>
                      </li> -->
          <!-- <li @click="ressBook1">
                      <div class="lists-fun">
                        <img src="../../../assets/home/percenter_addlist.png" alt="">-->
          <!-- <p>通讯录</p>
                      </div>
                      <div class=lists-go>
                        <i></i>
                      </div>
                    </li> -->
          <li @click="outLogin">
            <div class="lists-fun">
              <img src="../../../assets/home/percenter_record.png" alt="">
              <p>退出绑定</p>
            </div>
            <div class=lists-go>
              <i></i>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'PersonalCom',
    data() {
      return {
        realName: '',
        sixName: '',
        orgName: '',
        position: '',
        photoUrl: ''
      }
    },
    methods: { /* 方法 */
      /*密码管理*/
      passManerge() {
        $('title').text('数字运维 - 密码修改');
        this.$router.push({
          path: '/PersonalPassMannerge'
        })
      },
      ressBook() {
        $('title').text('数字运维 - 通讯录');
        this.$router.push({
          path: '/PersonalRessbook'
        })
      },
      //   ressBook1(){
      //     this.$router.push({
      //       path:'/PersonalBindAccount'
      //     })
      //   },
      // noticeAnnouncement(){
      //   this.$router.push({
      //     path:'/NoticeAnnouncement'
      //   })
      // },
      getUserInfoForPhone() {
        const _this = this;
        this.requests.getUserInfoForPhone().then(function(response) {
          if (response.result == 'success') {
            if (response.data != null) {
              _this.common.setLocalStorage('phone',response.data.phone);
              _this.realName = response.data.realName;
              _this.orgName = response.data.orgName;
              _this.sixName = response.data.sexText;
              _this.position = response.data.position;
              _this.photoUrl = response.data.photoUrl
            }
          }
        })
      },
      outLogin() {
        // 退出登录
        this.requests.loginOut().then(function(response) {
          if (response.result == 'success') {
            localStorage.setItem('userKey', '');
            window.location.href = "index.html";
          }
        })
      }
    },
    mounted: function() {
      this.getUserInfoForPhone()
    },
    watch: { /* 监听 */ }
  }
</script>

<style lang='scss'>
  @import "../../../style/global.scss";
  #PersonalCom {
    width: 100%;
    height: 100%;
    .content {
      padding-top: 39px;
      width: 100%;
      height: 605px;
      background: url('../../../assets/home/percenter_bg.jpg') no-repeat;
      background-size: 100% 100%;
      box-sizing: border-box;
      .content-information {
        display: flex;
        margin-bottom: 104px;
        .portrait {
          margin-left: 52px;
          margin-right: 33px;
          width: 167px;
          height: 166px;
          border-radius: 50%;
          box-sizing: border-box;
          overflow: hidden;
        }
        .information {
          padding-top: 41px;
          box-sizing: border-box;
          font-size: 0;
          p {
            color: #fff;
            &:nth-child(1) {
              font-size: $widthWeb30;
              line-height: $widthWeb30;
              margin-bottom: 14px;
            }
            &:nth-child(2) {
              margin-left: 2px;
              font-size: $widthWeb16;
              width: 360px;
              overflow: hidden;
              white-space: nowrap;
              text-overflow: ellipsis;
            }
          }
        }
      }
      .content-column {
        padding: 0 20px;
        box-sizing: border-box;
        .lists {
          background-color: #fff;
          border-radius: 10px;
          padding: 23px 40px 56px;
          li {
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 100%;
            height: 99px;
            border-bottom: 1px solid #dfdcdc;
            box-sizing: border-box;
            .lists-fun {
              display: flex;
              align-items: center;
              font-size: 0;
              img {
                margin-right: 25px;
                display: inline-block;
                width: 38px;
                height: 38px;
              }
              p {
                font-size: 25px;
                color: #333;
              }
            }
            .lists-go {
              width: 32px;
              height: 54px;
              display: flex;
              justify-content: flex-end;
              align-items: center;
              i {
                display: inline-block;
                width: 16px;
                height: 27px;
                background: url('../../../assets/home/arrow.png') no-repeat;
                background-size: 100% 100%;
              }
            }
          }
        }
      }
    }
  }
</style>
