<%--
  Created by IntelliJ IDEA.
  User: luos
  Date: 2016/10/31
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="data_list">
    <div class="data_list_title">
        <span class="glyphicon glyphicon-list"></span>&nbsp;日志列表
    </div>

    <div class="diary_datas">
        <ul>
            <c:forEach var="diary" items="${diaryList}">
                <li>
                    <fmt:formatDate value="${diary.releaseDate}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate>
                    <span>&nbsp;&nbsp;<a href="#">${diary.title}</a></span>
                </li>

            </c:forEach>
        </ul>
    </div>

</div>
