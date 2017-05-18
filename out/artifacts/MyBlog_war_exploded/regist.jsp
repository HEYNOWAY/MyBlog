<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 17-5-16
  Time: 下午11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ page import="com.luos.model.User" %>
<head>
    <title>用户注册</title>
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

    <script>
        function checkPswIsSame() {
            var username = document.getElementById("userName").value;
            var password = document.getElementById("passWord").value;
            var checkpassword = document.getElementById("checkPassWord").value;
            if (username == null || username == "") {
                document.getElementById("error").innerHTML = "用户名不能为空";
                return false;
            }
            if (password == null || password == "") {
                document.getElementById("error").innerHTML = "密码不能为空";
                return false;
            }
            if (checkpassword == null || checkpassword == ""){
                document.getElementById("error").innerHTML = "确认密码不能为空";
                return false;
            }
            if(!(password==checkpassword)){
                document.getElementById("error").innerHTML = "密码不一致";
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container">
    <form id="form" name="myForm" class="form-signin" action="regist" method="post" onsubmit="return checkPswIsSame()">
        <h2 class="form-signin-heading">用户注册</h2>
        <span class="input-group-addon">用户名</span>
        <input id="userName" name="userName"  type="text" class="form-control"
               placeholder="Username">
        <span class="input-group-addon">密码</span>
        <input id="passWord" name="passWord"  type="password" class="form-control"
               placeholder="PassWord">
        <span class="input-group-addon">确认密码</span>
        <input id="checkPassWord" name="checkPassWord"  type="password" class="form-control"
               placeholder="PassWord">

        <button class="btn btn-large btn-primary" type="submit">提交</button>
        <br/>
        <font id="error" style="color: red; ">${error}</font>
    </form>
</div>

</body>
</html>
