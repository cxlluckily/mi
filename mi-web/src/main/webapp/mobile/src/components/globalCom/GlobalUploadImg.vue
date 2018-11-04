<template>
    <div id='GlobalUploadImg'>
        <ul class="GlobalUploadImg-list">
            <!-- <li v-for="itemImg in uploadImgData" :key="itemImg">
                <div class="list-img">
                    <i class="icon" :data-imgUrl="itemImg" @click="colseImg($event)"></i>
                    <div class="Img">
                        <img :src="itemImg">
                    </div>
                </div>
            </li> -->
            <li v-for="itemImg in uploadImgData" :key="itemImg">
                <div class="list-img">
                    <i class="icon" :data-imgUrl="itemImg" @click="colseImg($event)"></i>
                    <div class="Img">
                        <img :src="itemImg">
                    </div>
                </div>
            </li>
            <li class="appendFile"><!-- v-show="switchAddBtn" -->
                <div class="appendFile-bgc" @click="qrCodingUpload"></div>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "GlobalUploadImg",
        data() {
            return {
                uploadImgData: [],
                switchAddBtn: true,
                initPage: 9,
                wxServerId: []
            };
        },
        props: ["swtichWxUploadImage", "swtichWxUploadData"],
        methods: {
            // 初始化 file
            qrCodingUpload() {
                // let pageNum = this.initPage - this.uploadImgData.length;
                let pageNum = 9;
                this.wxApi.WxChooseImage(pageNum).then(data => {
                    for (let i = 0, II = data.length; i < II; i++) {
                        this.uploadImgData.push(data[i] + "");
                    }
                    this.$emit("wxLocalImageData", this.uploadImgData);
                });
            },
            colseImg(event) {
                const THIS = $(event.currentTarget),
                    THISIMG = THIS.attr("data-imgUrl") + "";
                let dataInDex = this.uploadImgData.indexOf(THISIMG);
                this.uploadImgData.splice(dataInDex, 1);
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
                // if (val.length >= 9) {
                //     this.switchAddBtn = false;
                // } else {
                //     this.switchAddBtn = true;
                // }
            },
            swtichWxUploadImage: async function(val) {
                // console.log(val);
                // if (val) {
                //     this.wxServerId = [];
                //     await new Promise(resolve => {
                //         for (let i = 0, II = this.uploadImgData.length; i < II; i++) {
                //             this.wxApi
                //                 .WxUploadImage(this.uploadImgData[i].toString())
                //                 .then(data => {
                //                     this.wxServerId.push(data);
                //                     if (this.uploadImgData.length == this.wxServerId.length) {
                //                         resolve();
                //                         //  alert(1)
                //                     }
                //                 });
                //         }
                //     });
                //     // alert(2)
                //     this.$emit("wxUploadImageData", this.wxServerId);
                // }
                // alert(1);
                const THIS = this;
                if (val) {
                  console.log(val)
                    // alert(2);
                    THIS.wxServerId = [];
                    function WxUpload(Num) {
                        if (Num <= -1) return THIS.$emit("wxUploadImageData", THIS.wxServerId);
                        //  alert(3);
                        THIS.wxApi
                            .WxUploadImage(THIS.uploadImgData[Num].toString())
                            .then(data => {
                                THIS.wxServerId.push(data);
                                Num--;
                                WxUpload(Num);
                                // alert(Num);
                            });
                    }
                    WxUpload(THIS.uploadImgData.length-1);
                    // THIS.$emit("wxUploadImageData", THIS.wxServerId);

                }
            },
            swtichWxUploadData: function(val) {
                console.log(val);
                this.uploadImgData = [];
            }
        }
    };
</script>

<style lang='scss'>
    @import "@/style/global.scss";
    #GlobalUploadImg {
        margin-top: 19px;
        .GlobalUploadImg-list {
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            li {
                width: 33.333333%;
                height: 144px;
                position: relative;
                text-align: center;
                margin-bottom: 20px;
                img {
                    width: 144px;
                    height: 144px;
                }
                .list-img {
                    position: absolute;
                    z-index: 100;
                    .icon {
                        position: absolute;
                        top: -12px;
                        right: -12px;
                        width: 48px;
                        height: 48px;
                        background: url("../../assets/home/icon_delete.png") no-repeat;
                        background-size: 100% 100%;
                    }
                    img {
                        display: inline-block;
                        width: 144px;
                        height: 144px;
                    }
                }
                &:nth-child(3) {
                    margin-right: 0;
                }
            }
            .appendFile {
                display: flex;
                justify-content: center;
                align-items: center;
                // width: 144px;
                width: 33.333333%;
                height: 144px;
                margin-bottom: 0;
                .appendFile-bgc {
                    width: 100px;
                    height: 100px;
                    background: url("../../assets/home/upload_pic.png") no-repeat;
                    background-size: 100% 100%;
                }
            }
        }
    }
</style>
