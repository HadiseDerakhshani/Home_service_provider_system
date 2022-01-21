<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Title</title>
</head>
<h3>Login Form</h3>
<body>
<form:form action="doLogin" modelAttribute="loginData" method="get">
    Name: <form:input path="name"/>
    <br><br>
    Password: <form:input path="password"/>
    <br><br>
    <%--
        Food:
        BreakFast<form:checkbox path="food" value="BreakFast"/>
        Lunch<form:checkbox path="food" value="Lunch"/>
        Dinner<form:checkbox path="food" value="Dinner"/>--%>
    <br><br>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
