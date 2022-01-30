<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<div>
    <h2 style="color: red">${message}</h2>
    <br><br>
    Email: <br>
    <input type="password" id="email" placeholder="name@gmail.com">
    <br><br>
    Password: <br>
    <input type="password" id="password" placeholder="Password">
    <br><br>
    <button type="submit" value="/" name="login" style="border-bottom: darkslategray ">Login</button>
</div>
<body>

</body>
</html>
