<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>payment_online Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<div class="container">
    <h1 style="color:red">${message}</h1>
    <form:form cssClass="p-3 m-3" action="/order/payment_online" method="post"> <%--todo--%>
        <table class="table table-bordered table-striped text-dark">
            <tr>
                <td>
                  number cart :
                </td>
                <td>
                    <input type="text" placeholder="****-****-****-****" name="phone" class="form-control" required>
                </td>
            </tr>
            <tr>
                <td>
                   cvv2 :
                </td>
                <td>
                    <input type="text" placeholder="****" name="cvv2" class="form-control" required>
                </td>
            </tr>
            <tr>
                <td>
                    Expiration date :
                </td>
                <td>
                    <input type="text" placeholder="yyyy/mm/dd" name="date" class="form-control" required>
                </td>
            </tr>
            <tr>
                <td>
                   Password :
                </td>
                <td>
                    <input type="password" placeholder="enter password" name="password" class="form-control" required>
                </td>
            </tr>
            <tr>
                <td>
                    Amount :
                </td>
                <td>
                    <input type="text" placeholder="enter password" name="amount" class="form-control" required>
                    <%// TODO: 2/6/2022  //validatio >=price order%>
                </td>
            </tr>


            <tr>
                <td>
                </td>
                <td>
                    <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">Change</button>
                </td>
            </tr>
        </table>
    </form:form>
</div>


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