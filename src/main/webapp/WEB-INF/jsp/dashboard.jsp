<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>hello_jsp</title>
</head>
<body>
<p>${loginData.name} </p>
<a href="/">Logout</a>
Food:
<ul>
    <c:forEach var="each_food" items="${loginData.food}">
        <li>${each_food}</li>
    </c:forEach>
</ul>
</body>
</html>
