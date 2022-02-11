<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>payment Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<div class="container">
    <h1>${message}</h1>
    <h2>${customer.firstName} ${customer.lastName} </h2>
    <h3>${order.service.name} ,${order.status}</h3>
    <p> choose select option of payment</p>
    <h3>${amount}</h3>
    <div>

        <form action="/expert/registerScore" method="post">
            Score to expert :
            <br>
            <input type="text" placeholder="enter a number between 1-10" name="score" class="form-control" require>

            <br><br>
            Comment :
            <br>
            <input type="text" placeholder="enter a number between 1-10" name="comment" class="form-control">
            <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">register</button>
        </form>
        <br><br>
        <div id="hide4" style="display: none">
            <a href="<c:url value="/order/paymentPage_online"/>" class="btn btn-outline-primary">online</a>
            <c:if test="${customer.credit >= amount}">
                <a href="<c:url value="/order/paymentPage_cash"/>" class="btn btn-outline-primary">cash</a>
            </c:if>
        </div>
    </div>

</div>
<script>
    function ShowChangePhoneDiv() {
        var showItem = document.getElementById("showItem");
        var hide4 = document.getElementById("hide4");
        var hide5 = document.getElementById("hide5");
        var hide6 = document.getElementById("hide6");
        hide4.style.display = showItem.value === "change" ? "block" : "none";
        hide5.style.display = showItem.value === "change" ? "block" : "none";
        hide6.style.display = showItem.value === "change" ? "block" : "none";
    }
</script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>