<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <style>
        p {
            margin: 0;
            color: blue;

        }

        div, p {
            margin-left: 10px;
        }

        span {
            color: red;
        }
    </style>
</head>
<body>
<span></span>
<br><br>
<form action="/user/login" method="post">
    <div class="container">
        <h4>Email : </h4>
        <br>
        <input type="email" placeholder="Enter Username" name="email" id="e1" class="form-control" required
              <%-- pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"--%>>

        <br><br>
        <h4>Password : </h4>
        <br>
        <input type="password" placeholder="Enter Password" name="password" id="p1" class="form-control" required
              <%-- pattern="^[A-Za-z0-9._%+@|!&*=/-]{8,}$"--%>>
        <br><br>

        <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">Login</button>
    </div>
</form>


<script>

    $("form").submit(function (event) {
        <c:forEach var="list" items="${list}">
        if ($("input:first").val() === "${list.email}") {
            if ($("#p1").val() === "${list.password}") {
                $("span").text("Submitted Successfully.").show();
                return;
            }
        }
        </c:forEach>
        $("span").text("Not Exit User!").show().fadeOut(2000);
        event.preventDefault();
    });

</script>
</body>
</html>
