<template>
    <div id='HomeFaultDetails'>
        <div class="HomeFaultDetails-main">
            <div class="main-title">
                <div class="title-Tab" @click="tabContent == 'fault'">
                    <p>故障情况</p>
                    <span></span>
                </div>
                <div class="title-Tab" @click="tabContent == 'evaluation'">
                    <p>故障情况</p>
                    <span></span>
                </div>
            </div>
            <div class="main-content" v-show="tabContent == 'fault'">
                <ul class="content-Tab" v-for="(itemMain, index) in faultDetailsData" :key="index">
                    <li>
                        <p class="Tab-ins">故障现象 :</p>
                        <ul class="Tab-text">
                            <li v-for="(itemText, index) in itemMain.faultPhenomenon" :key="index">{{itemText}}</li>
                        </ul>
                    </li>
                    <li>
                        <p class="Tab-ins">故障编码 :</p>
                        <span>{{itemMain.faultCode}}</span>
                    </li>
                    <li>
                        <p class="Tab-ins">故障描述 :</p>
                        <span>{{itemMain.faultDescription}}</span>
                    </li>
                    <li>
                        <p class="Tab-ins">故障图片 :</p>
                        <ul class="Tab-Img">
                            <li @click="previewFault($event)" v-for="(itemImg,index) in itemMain.faultImaegs" :key="index" :data-index="index">
                                <img :src="itemImg.picUrl" alt="现场图片">
                            </li>
                        </ul>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">维修结果 :</p>
                        <span>{{itemMain.maintenanceResults == 'yes'?'已修复':'未修复'}}</span>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">问题描述 :</p>
                        <span>{{itemMain.maintenanceDescriptio}}</span>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">维修图片 :</p>
                        <ul class="Tab-Img">
                            <li @click="previewFault($event)" v-for="(itemImg, index) in itemMain.maintenanceImages" :key="index" :data-index="index">
                                <img :src="itemImg.picUrl" alt="现场图片">
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="main-content" v-show="tabContent == 'evaluation'">
                <ul class="content-Tab" v-for="(itemMain, index) in faultDetailsData" :key="index">
                    <li>
                        <p class="Tab-ins">故障现象1 :</p>
                        <ul class="Tab-text">
                            <li v-for="(itemText, index) in itemMain.faultPhenomenon" :key="index">{{itemText}}</li>
                        </ul>
                    </li>
                    <li>
                        <p class="Tab-ins">故障编码 :</p>
                        <span>{{itemMain.faultCode}}</span>
                    </li>
                    <li>
                        <p class="Tab-ins">故障描述 :</p>
                        <span>{{itemMain.faultDescription}}</span>
                    </li>
                    <li>
                        <p class="Tab-ins">故障图片 :</p>
                        <ul class="Tab-Img">
                            <li @click="previewFault($event)" v-for="(itemImg,index) in itemMain.faultImaegs" :key="index" :data-index="index">
                                <img :src="itemImg.picUrl" alt="现场图片">
                            </li>
                        </ul>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">维修结果 :</p>
                        <span>{{itemMain.maintenanceResults == 'yes'?'已修复':'未修复'}}</span>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">问题描述 :</p>
                        <span>{{itemMain.maintenanceDescriptio}}</span>
                    </li>
                    <li v-show="itemMain.swtichMaintenance">
                        <p class="Tab-ins">维修图片 :</p>
                        <ul class="Tab-Img">
                            <li @click="previewFault($event)" v-for="(itemImg, index) in itemMain.maintenanceImages" :key="index" :data-index="index">
                                <img :src="itemImg.picUrl" alt="现场图片">
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="HomeFaultDetails-previewImg" v-if="switchPreviewImg">
            <div class="previewImg-swipe">
                <mt-swipe :auto="0" :defaultIndex="previewSwipeImgIndex">
                    <mt-swipe-item v-for="(itemSwipe, index) in previewSwipeImg" :key="index">
                        <img :src="itemSwipe">
                    </mt-swipe-item>
                </mt-swipe>
                <div class="closeIcon" @click="closePreviewImg">
                    <i></i>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HomeFaultDetails",
        data() {
            return {
                switchPreviewImg: false,
                previewSwipeImgIndex: 0,
                previewSwipeImg: [],
                tabContent: 'fault'
            };
        },
        props: ["faultDetailsData"],
        methods: {
            /* 方法 */
            previewFault(event) {
                this.previewSwipeImg = [];
                const THIS = $(event.currentTarget),
                    LISTINDEX = THIS.attr("data-index"),
                    LISTARR = THIS.closest(".Tab-Img").find("li"),
                    IMGARR = [],
                    SWIPEINDEX = $('.previewImg-swipe').find('.mint-swipe-item'),
                    INDICATOR = $('.previewImg-swipe').find('.mint-swipe-indicator');
                for (let i = 0, II = LISTARR.length; i < II; i++) {
                    IMGARR.push(LISTARR.eq(i).find("img")[0].src);
                }
                this.previewSwipeImg = IMGARR;
                this.previewSwipeImgIndex = +LISTINDEX;
                this.switchPreviewImg = true;
            },
            closePreviewImg() {
                this.switchPreviewImg = false;
            }
        },
        components: {
            /* 复用组件名称 */
        },
        mounted: function() {
            /* 初始化数据 */
            console.log(this.faultDetailsData);
        },
        watch: {
            /* 监听 */
            faultDetailsData: function(val) {
                console.log(val);
            }
        }
    };
</script>

<style lang='scss'>
    @import "@/style/global.scss";
    #HomeFaultDetails {
        width: 100%;
        height: 100%;
        padding: 0 20px;
        box-sizing: border-box;
        .HomeFaultDetails-main {
            width: 100%;
            height: 100%;
            padding: 12px 30px 38px;
            border: solid 1px #dfdcdc;
            box-sizing: border-box;
            border-radius: 15px;
            background-color: #fff;
            .main-title {
                display: flex;
                border-bottom: 1px solid #dfdcdc;
                p {
                    color: #333;
                    font-size: $widthWeb25;
                    line-height: 65px;
                }
                span {
                    position: absolute;
                    bottom: 0;
                    left: 50%;
                    transform: translateX(-50%);
                    width: 26px;
                    height: 4px;
                    background-color: #fd8521;
                }
                .title-Tab {
                    margin-left: 110px;
                    position: relative;
                }
            }
            .main-content {
                padding-top: 16px;
                .content-Tab {
                    &>li {
                        display: flex;
                        p {
                            font-size: $widthWeb18;
                            color: #999;
                            line-height: 54px;
                        }
                        span {
                            font-size: $widthWeb18;
                            color: #999;
                            line-height: 26px;
                            padding: 12px 0 12px;
                        }
                        .Tab-ins {
                            flex-shrink: 0;
                            margin-right: 32px;
                            color: #333;
                        }
                        .Tab-text {
                            padding-top: 10px;
                            font-size: $widthWeb18;
                            color: #999;
                            line-height: 34px;
                        }
                        .Tab-Img {
                            display: flex;
                            padding-top: 18px;
                            font-size: 0;
                            li {
                                margin-right: 20px;
                                width: 150px;
                                height: 150px;
                                &:nth-last-child(1) {
                                    margin: 0;
                                }
                                img {
                                    width: 100%;
                                    height: 100%;
                                }
                            }
                        }
                    }
                }
            }
        }
        .HomeFaultDetails-previewImg {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 300;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4);
            .previewImg-swipe {
                margin-top: 50%;
                width: 100%;
                padding: 0 40px;
                height: 380px;
                box-sizing: border-box;
                .closeIcon {
                    display: flex;
                    justify-content: center;
                    align-items: flex-end;
                    width: 100%;
                    height: 70px;
                    border-radius: 18px;
                    i {
                        display: inline-block;
                        width: 35px;
                        height: 35px;
                        background-image: url("../../../assets/home/close.png");
                        background-size: 100% 100%;
                    }
                }
                img {
                    width: 100%;
                    height: 100%;
                    border-radius: 18px;
                }
            }
        }
    }
</style>
