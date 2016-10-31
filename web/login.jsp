<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/10/29
  Time: 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<html lang="zh">
<%@ page import="com.luos.model.User" %>
<%
    String userName = null;
    String passWord = null;

    Cookie[] cookies = request.getCookies();
    for (int i = 0; cookies != null && i < cookies.length; i++) {
        if (cookies[i].getName().equals("user")) {
            String user = cookies[i].getValue();
            userName = user.split("-")[0];
            passWord = user.split("-")[1];
        }
    }

    if (userName == null) {
        userName = "";
    }

    if (passWord == null) {
        passWord = "";
    }

    pageContext.setAttribute("user", new User(userName, passWord));
%>
<head>
    <title>个人博客日志</title>
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <style type="text/css">
        body {
            padding-top: 180px;
            padding-bottom: 40px;
            background-size: 100%;
            background-image: url("images/background.jpg");
        }

        .form-signin-heading {
            text-align: center;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 0px;
            margin: 0 auto 40px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
            margin-left: 10px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
    </style>

    <script type="text/javascript">
        function checkFrom() {
            var username = document.getElementById("userName").value;
            var password = document.getElementById("passWord").value;
            if (username == null || username == "") {
                document.getElementById("error").innerHTML = "用户名不能为空";
                return false;
            }
            if (password == null || password == "") {
                document.getElementById("error").innerHTML = "密码不能为空";
                return false;
            }
            return true;
        }

    </script>
</head>

<body>
<div class="container">
    <form id="form" name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkFrom()">
        <h2 class="form-signin-heading">博客日志</h2>
        <span class="input-group-addon">用户名</span>
        <input id="userName" name="userName" value="${user.userName}" type="text" class="form-control"
               placeholder="Username">
        <span class="input-group-addon">密码</span>
        <input id="passWord" name="passWord" value="${user.passWord }" type="password" class="form-control"
               placeholder="PassWord">
        <div class="checkbox">
            <label>
                <input id="remember" name="remember" type="checkbox" value="remember-me" > 记住账号
            </label>
            <br/>
            <font id="error" style="color: red; ">${error}</font>
        </div>
        <button class="btn btn-large btn-primary" type="submit">登录</button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-large btn-primary" type="button" onclick="reset() ">重置</button>
        <p align="center" style="padding-top: 15px;">版权所有 菊花</p>
    </form>
</div>
</body>

</html>
