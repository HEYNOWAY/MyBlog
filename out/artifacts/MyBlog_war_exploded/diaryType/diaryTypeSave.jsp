<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/11/14
  Time: 7:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
    function checkForm(){
        var typeName=document.getElementById("typeName").value;
        if(typeName==null||typeName==""){
            document.getElementById("error").innerHTML="类别名称不能为空！";
            return false;
        }
        return true;
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        <c:choose>
            <c:when test="${diaryType.typeId!=null }">
                <div>
                    <span class="glyphicon glyphicon-pencil"></span>&nbsp;修改日记类别
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <span class="glyphicon glyphicon-pencil"></span>&nbsp;添加日记类别
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <form action="diaryType?action=Save" method="post" onsubmit="return checkForm()">
        <div class="diaryType_form" >
            <input type="hidden" id="typeId" name="typeId" value="${diaryType.typeId }"/>
            <table align="center">
                <tr>
                    <td>类别名称：</td>
                    <td><input type="text" id="typeName"  name="typeName" value="${diaryType.typeName }"  style="margin-top:5px;height:30px;" /></td>
                </tr>

                <tr>
                    <td><input type="submit" class="btn btn-primary" value="保存"/></td>
                    <td><button type="button" class="btn btn-primary"  onclick="javascript:history.back()">返回</button>&nbsp;&nbsp;
                        <font id="error" color="red">${error }</font>  </td>
                </tr>
            </table>
        </div>
    </form>
</div>
