<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="messages.app"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="app.title"/></title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<section>
    <h3><a href="../../index.html">Home</a></h3>
    <h2>Users</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Roles</th>
            <th>Active</th>
            <th>Created</th>
        </tr>
        </thead>
        <c:forEach items="${userList}" var="user">
            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.name}</td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td>${user.enabled}</td>
                <td><fmt:formatDate value="${user.created}" pattern="dd-MMMM-yyyy"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>