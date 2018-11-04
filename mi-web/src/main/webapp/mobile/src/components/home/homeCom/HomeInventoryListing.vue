<template>
    <div id='HomeInventoryListing'>
        <ul class="HomeInventoryListing-listing">
            <li class="listing-list">
                <p>硬币单元 ( XTC802 )<span>10个</span></p>
                <div class="list-parts">
                    <div class="parts-img">
                        <img src="../../../assets/home/pic3.png" alt="">
                    </div>
                    <ul>
                        <li>
                            <p>是</p>
                            <p>唯一标识</p>
                        </li>
                        <li>
                            <p>301</p>
                            <p>房间号</p>
                        </li>
                        <li>
                            <p>3-1</p>
                            <p>货架号</p>
                        </li>
                    </ul>
                </div>
                <div class="list-num">
                    <button>+</button>
                    <input type="text">
                    <button>-</button>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "HomeInventoryListing",
        data() {
            return {};
        },
        methods: {
            /* 方法 */
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
    #HomeInventoryListing {
        width: 100%;
        height: 100%;
        background-color: #fff;
        .HomeInventoryListing-listing {
            width: 100%;
            padding-left: 18px;
            font-size: 22px;
            color: #fd8521;
            line-height: 42px;
            box-sizing: border-box;
        }
        .listing-list {
            padding-left: 18px;
            box-sizing: border-box;
            &:nth-last-child(1) {
                border: 0;
            }
            p {
                font-size: 18px;
                color: #333;
                line-height: 58px;
            }
            .list-parts {
                width: 100%;
                display: flex;
                .parts-img {
                    margin-right: 14px;
                    width: 70px;
                    height: 70px;
                    img {
                        display: inline-block;
                        width: 70px;
                        height: 70px;
                    }
                }
                &>ul {
                    display: flex;
                    width: 100%;
                    padding-bottom: 20px;
                    border-bottom: solid 1px #dfdcdc;
                    box-sizing: border-box;
                    li {
                        flex: 1;
                        text-align: center;
                        border-right: solid 1px #dfdcdc;
                        box-sizing: border-box;
                        &:nth-last-child(1) {
                            border: 0;
                        }
                        p {
                            font-size: 18px;
                            color: #333;
                            line-height: 36px;
                            &:nth-last-child(1) {
                                margin-top: 4px;
                                font-size: 14px;
                                color: #bfbfbf;
                                line-height: 26px;
                            }
                        }
                    }
                    .list-num {
                        margin-top: 18px;
                        width: 100%;
                        height: 34px;
                        font-size: 0;
                        text-align: right;
                        button {
                            padding: 0;
                            width: 42px;
                            height: 34px;
                            color: #666;
                            font-size: 22px;
                            text-align: center;
                            line-height: 34px;
                            background-color: #fff;
                            border: solid 1px #eee;
                            box-sizing: border-box;
                        }
                        input {
                            width: 77px;
                            height: 34px;
                            font-size: 22px;
                            color: #fd8521;
                            text-align: center;
                            background-color: #eee;
                        }
                    }
                }
            }
        }
    }
</style>
