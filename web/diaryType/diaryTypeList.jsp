<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/11/14
  Time: 7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    function diaryTypeDelete(typeId) {
        if(confirm("您确定要删除这个日志类别吗？")){
            window.location="diaryType?action=delete&typeId="+typeId;
        }
    }
</script>

<div class="data_list">
    <div class="data_list_title">
        <span class="glyphicon glyphicon-th-list"></span>&nbsp;日志分类列表
        <span class="diaryType_add">
			<button class="btn btn-mini btn-success" type="button"
                    onclick="javascript:window.location='diaryType?action=preSave'">添加日志类别</button>
		</span>
    </div>

    <div>
        <table class="table table-hover table-striped">
            <tr>
                <th>编号</th>
                <th>类别名称</th>
                <th>操作</th>
            </tr>
            <c:forEach var="diaryType" items="${diaryTypeList }">
                <tr>
                    <td>${diaryType.typeId }</td>
                    <td>${diaryType.typeName }</td>
                    <td>
                        <button class="btn btn-mini btn-info" type="button"
                                onclick="javascript:window.location='diaryType?action=preSave&typeId=${diaryType.typeId}'">
                            修改
                        </button>
                        &nbsp;
                        <button class="btn btn-mini btn-danger" type="button" onclick="diaryTypeDelete(${diaryType.typeId })">删除</button>
                    </td>
                </tr>
            </c:forEach>

        </table>
        <div align="center"><font color="red">${error }</font></div>
    </div>

</div>