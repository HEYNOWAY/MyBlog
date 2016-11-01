<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/10/29
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>博客日志主页</title>
    <link href="${pageContext.request.contextPath}/style/diary.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>

    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我的日记本</a>
        </div>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li role="presentation" class="active"><a href="#"> <span class="glyphicon glyphicon-home"></span>&nbsp;主页</a>
                </li>
                <li class="active"><a href="#"> <span class="glyphicon glyphicon-pencil"></span>&nbsp;写日记</a></li>
                <li class="active"><a href="#"> <span class="glyphicon glyphicon-th-list"></span>&nbsp;日记分类管理</a></li>
                <li class="active"><a href="#"> <span class="glyphicon glyphicon-user"></span>&nbsp;个人中心</a></li>
            </ul>
            <form name="myForm" class="navbar-form navbar-right" method="post" action="">
                <input class="form-control" id="s_title" name="s_title" type="text" placeholder="Search...">
                <button type="submit" class="btn btn-default">
                    <%--onkeydown="if(event.keyCode==13) myForm.submit()--%>
                    <span class="glyphicon glyphicon-search"></span>&nbsp;搜索日志
                </button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</div>
<div class="container">
    <div class="row-fluid">
        <div class="col-md-9">
            <jsp:include page="${mainPage}"></jsp:include>
        </div>

        <div class="col-md-3">
            <div class="data_list">
                <div class="data_list_title">
                    <span class="glyphicon glyphicon-user"></span>&nbsp;个人中心
                </div>
            </div>
            <div class="data_list">
                <div class="data_list_title">
                    <span class="glyphicon glyphicon-tags"></span>&nbsp;按日志类别
                </div>
            </div>
            <div class="data_list">
                <div class="data_list_title">
                    <span class="glyphicon glyphicon-calendar"></span>&nbsp;按日志日期
                </div>
            </div>
        </div>

    </div>
</div>


</body>
</html>
