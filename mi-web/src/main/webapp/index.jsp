<%@page import="com.shankephone.mi.util.SessionMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8" %>
<% response.setStatus(HttpServletResponse.SC_OK);%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="icon" href="../app/resources/images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="../app/resources/images/favicon.ico" type="image/x-icon"/>
    <title>数字运维</title>
    <style type="text/css">
        body {
            overflow: hidden;
        }

        #loading {
            position: absolute;
            top: 50%;
            width: 100%;
            margin-top: -70px;
        }

        #loading .title {
            font-family: "Exo", sans-serif;
            font-size: 2em;
            color: gray;
            text-align: center;
            white-space: nowrap;
            display: block
        }

        #loading .logo {
            background: url(app/resources/images/loading.gif) no-repeat center;
            display: block;
            height: 120px
        }

        .x-action-col-icon {
            color: #3892d4 !important;
        }

        .commonImagesLook-img-box {
            max-width: 560px;
            width: 560px !important;
        }
        .myTabs .label {
            width: 100px;
            margin-right: -1px;
            border: 1px solid #5fa2dd;
            border-bottom: 0;
            color: #fff;
            padding-top: 5px;
            padding-bottom: 5px;
            background-color: #5fa2dd;
            text-align: center;
            float: left;
            cursor: pointer;
        }
        .myTabs .label:hover{
            background: #5795cb;
        }
        .myTabs .label.active{
            background: #477aa6;
        }

        .myTabs .tab {
            display: block;
        }

        .myTabs .clearBoth {
            clear: both;
        }

        .myTabs .box {
            height: 220px;
            border: 1px solid #5fa2dd;
            scroll-behavior: smooth;
            overflow: hidden;
        }

        .myTabs .content {
            height: 100%;
            /*padding: 0 20px;*/
            position: relative;
            overflow: hidden;
        }
        .myTabs .myButton{
            background: #5fa2dd;
            margin: 10px 0px 0px 10px;
            padding: 5px 10px;
            color: #fff;
            border: 0px;
        }
        .myTabs .myButton:active{
            background: #5795cb;
        }
        .myTabs .content > div {
            height: 100%;
            width: 100%;
        }

        .myTabs .box input {
            position: absolute;
            top: 0;
            height: 100%;
            width: 1px;
            border: 0;
            padding: 0;
            margin: 0;
            clip: rect(0 0 0 0);
        }
    </style>
</head>
<body>
<div id="loading"><span class="title">资源加载中，请稍后......</span><span class="logo"></span></div>
<jsp:include page="/resources.jsp"></jsp:include>
</body>
</html>