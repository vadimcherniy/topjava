<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<%--<jsp:include page="fragments/headTag.jsp"/>--%>
<body>
<%--<jsp:include page="fragments/bodyHeader.jsp"/>--%>
<section>
    <form method="post" action="users">
        <select name="userId">
        <option value="100001" selected>User</option>
        <option value="100000">Admin</option>
    </select>
        <button type="submit">Submit</button>
    </form>
    <ul>
        <li><a href="users">users</a></li>
        <li><a href="meals">meals</a></li>
    </ul>
</section>
<%--<jsp:include page="fragments/footer.jsp"/>--%>
</body>
</html>