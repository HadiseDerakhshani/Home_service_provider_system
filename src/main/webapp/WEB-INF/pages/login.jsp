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
<body>
<h2>${message}</h2>
<%--<form action="/user/login" method="post">
    <div class="container">
        <b>Username</b>
        <br>
        <input type="email" placeholder="Enter Username" name="email" id="e1" class="form-control" required>
        <br><br>
        <b>Password</b>
        <br>
        <input type="password" placeholder="Enter Password" name="password" class="form-control" required>
        <br><br>
        <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">Login</button>

    </div>
</form>--%>
<div class="container">
    <h1 class="label">User Login</h1>
    <form class="login_form" action="login.html" method="post" name="form" onsubmit="return validated()">
        <div class="font">Email or Phone</div>
        <input autocomplete="off" type="text" name="email">
        <div id="email_error">Please fill up your Email or Phone</div>
        <div class="font font2">Password</div>
        <input type="password" name="password">
        <div id="pass_error">Please fill up your Password</div>
        <button type="submit">submit</button>
    </form>
</div>
<script src="project.js"></script>
</body>
</html>
