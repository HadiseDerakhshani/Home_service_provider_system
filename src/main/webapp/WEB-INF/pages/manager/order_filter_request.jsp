<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> order filter request Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<div class="container">
    <h1 style="color:red">${message}</h1>
    <form:form cssClass="p-3 m-3" modelAttribute="orderFilter" action="/manager/userFilter" method="post">
        <table class="table table-bordered table-striped text-dark">
            <tr>
                <td>
                    <form:label path="beginDate">Begin Date :</form:label>
                </td>
                <td>
                    <form:input path="beginDate" name="beginDate" placeholder="yyyy/mm/dd"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="beginDate" cssClass="text-danger"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="endDate">End Date :</form:label>
                </td>
                <td>
                    <form:input path="endDate" name="endDate" placeholder="yyyy/mm/dd"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="endDate" cssClass="text-danger"/>
                </td>
            </tr>

            <tr>
                <td>
                   Order Status:
                </td>
                <td>
                    Expert<form:radiobutton path="status" value="WAITING_FOR_EXPERT_SUGGESTION"/>
                    Customer<form:radiobutton path="status" value="WAITING_FOR_EXPERT_SELECTION"/>
                    Customer<form:radiobutton path="status" value="WAITING_FOR_EXPERT_TO_COME"/>
                    Customer<form:radiobutton path="status" value="STARTED"/>
                    Customer<form:radiobutton path="status" value="DONE"/>
                    Customer<form:radiobutton path="status" value="PAID"/>
                </td>
            </tr>
            <tr>
                <td>
                    Service :
                </td>
                <td>
                    <select name="name">
                        <c:forEach items="${listServices}" var="list">
                            <option value="${list.name}">${list.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                   Sub Service :
                </td>
                <td>
                    <select name="nameSub">
                        <c:forEach items="${listSubServices}" var="list">
                            <option value="${list.name}">${list.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <input type="submit" value="Submit"/>
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