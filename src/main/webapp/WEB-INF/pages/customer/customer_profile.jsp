<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register_customer Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<div>
<form:form modelAttribute="customerDto" method="get">
        <table class="table table-bordered table-striped text-dark">
            <tr>
                <td>
                   First Name :
                </td>
                <td>
                  ${customerDto.firstName}
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                  Last Name :
                </td>
                <td>
                    ${customerDto.lastName}
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                  Email :
                </td>
                <td>
                    ${customerDto.email}
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                    Password :
                </td>
                <td>
                    ${customerDto.password}
                </td>
                <td>
                    <a href="">Edit</a>
                </td>
            </tr>

            <tr>
                <td>
                   Phone Number :
                </td>
                <td>
                    ${customerDto.phoneNumber}
                </td>
                <td>
                    <a href="">Edit</a>
                </td>
            </tr>
            <tr>
                <td>
                Credit :
                </td>
                <td>
                    ${customerDto.credit}
                </td>
                <td>
                    <a href="">Edit</a>
                </td>
            </tr>

            <tr>
                <td>
                   User Status :
                </td>
                <td>
                    ${customerDto.userStatus}
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                  Last Update Date :
                </td>
                <td>
                    ${customerDto.dateUpdate}
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    Orders :
                </td>
                <td>
                    <select name="name">
                        <c:forEach items="${order}" var="list">
                            <option value="${list.receptionNumber}"> ${list.receptionNumber} , ${list.service.name},${list.status}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
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
