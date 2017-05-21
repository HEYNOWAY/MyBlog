<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/11/12
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function checkForm() {
        var title = document.getElementById("title").value;
        var content = CKEDITOR.instances.content.getData();
        var typeId = document.getElementById("typeId").value;
        if (title == null || title == "") {
            document.getElementById("error").innerHTML = "标题不能为空！";
            return false;
        }
        if (content == null || content == "") {
            document.getElementById("error").innerHTML = "内容不能为空！";
            return false;
        }
        if (typeId == null || typeId == "") {
            document.getElementById("error").innerHTML = "请选择日志类别！";
            return false;
        }
        return true;
    }
</script>

<div class="data_list">
    <c:choose>
        <c:when test="${diary.diaryId!=null}">
            <div class="data_list_title">
                <span class="glyphicon glyphicon-pencil"></span>&nbsp;修改日志
            </div>
        </c:when>
        <c:otherwise>
            <div class="data_list_title">
                <span class="glyphicon glyphicon-pencil"></span>&nbsp;写日志
            </div>
        </c:otherwise>
    </c:choose>


    <form action="diary?action=save" method="post" onsubmit="return checkForm()">
        <div>
            <div class="diary_title">
                <input type="text" id="title" name="title" value="${diary.title }" class="input-lg"
                       placeholder="日志标题..."/>
            </div>

            <div>
                <textarea class="ckeditor" id="content" name="content">${diary.content }</textarea>
            </div>
            <div class="diary_type">
                <select id="typeId" name="typeId">
                    <option value="">请选择日志类别...</option>

                    <c:forEach var="diaryType" items="${diaryTypeList}">
                        <option value="${diaryType.typeId }" ${diaryType.typeId==diary.typeId ? 'selected' : ''}>${diaryType.typeName}</option>
                    </c:forEach>

                </select>
            </div>
            <input type="hidden" id="diaryId" name="diaryId" value="${diary.diaryId }"/>
            <input type="submit" class="btn btn-primary" value="保存"/>
            <button type="button" class="btn btn-primary" onclick="javascript:history.back()">返回</button>
            <font id="error" color="red">${error }</font>
        </div>

    </form>
</div>