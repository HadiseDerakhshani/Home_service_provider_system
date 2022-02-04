<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose Service Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<div class="container">
    <h2 style="color: floralwhite">${customer.firstName} ${customer.lastName} please select expert for order</h2>
    <br><br>
    <table border="5" width="70%" cellpadding="2">
        <tr>
            <td>score expert</td>
            <td>expert name</td>
            <td>proposed price</td>
            <td>duration of work</td>
            <td>start time</td>
            <td>reception number</td>
            <td> select</td>
        </tr>
        <c:forEach var="list" items="${suggestList}">
            <tr>
                <td>${list.expert.score}</td>
                <td>${list.expert.lastName}</td>
                <td>${list.proposedPrice}</td>
                <td>${list.durationOfWork}</td>
                <td>${list.startTime}</td>
                <td>${list.receptionNumber}</td>
                <td><a href="/expert/selectSuggest/${list.receptionNumber}">Select</a></td>
            </tr>
        </c:forEach>
    </table>

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