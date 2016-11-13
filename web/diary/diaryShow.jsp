<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/11/12
  Time: 7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
    function deleteDiary(diaryId) {
        if(confirm("是否要删除该日志")){
            window.location="diary?action=delete&diaryId="+diaryId;
        }
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        <span class="glyphicon glyphicon-list-alt"></span>&nbsp;日志详情
    </div>

    <div>
        <div class="diary_title"><h3>${diary.title }</h3></div>

        <div class="diary_info">
            发布时间:『<fmt:formatDate value="${diary.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/> 』&nbsp;&nbsp;日志类别:${diary.typeName}
        </div>

        <div class="diary_content">
            ${diary.content }
        </div>

        <div class="diary_action">
            <button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
            <button class="btn btn-primary" type="button" onclick="javascript:window.location='diary?action=preSave&diaryId=${diary.diaryId}'">修改日志</button>
            <button class="btn btn-danger" type="button" onclick="deleteDiary(${diary.diaryId})">删除日志</button>
        </div>
    </div>

</div>
