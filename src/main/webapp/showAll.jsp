<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <table border="2px" style="border: 2px solid black;text-align: center">
        <tr>
            <td>诗人</td>
            <td>标题</td>
            <td>诗句</td>
        </tr>
        <c:forEach items="${requestScope.poetry}" var="poetry">
            <tr>
            <td>${poetry.poets.name}</td>
            <td>${poetry.title}</td>
            <td>${poetry.content}</td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${requestScope.page!=1}">
    <a href="${pageContext.request.contextPath}/poetry/showAll?pageNow=${requestScope.page-1}&art=${requestScope.art}" style="color: deeppink">上一页</a>
    </c:if>

    <c:if test="${requestScope.counts > requestScope.page}">
    <a href="${pageContext.request.contextPath}/poetry/showAll?pageNow=${requestScope.page+1}&art=${requestScope.art}" style="color: deeppink">下一页</a>
    </c:if>
        <span style="color: blue">总页数:${requestScope.counts}</span>
    </body>
</html>
