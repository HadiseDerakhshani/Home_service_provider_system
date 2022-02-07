<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>order Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/order.css"/>">

</head>
<body>
<div class="container">
    <c:if test="${sessionScope.customer == null}">
        <h2 style="color: red"> customer not login</h2>
        <a href="<c:url value="/customer/register"/>" class="btn btn-outline-primary">customer login</a>
        <a href="<c:url value="/"/>" class="btn btn-outline-primary">Main menu</a>

    </c:if>
    <form:form cssClass="p-3 m-3" modelAttribute="order" action="/order/registerOrder" method="post">

        <table class="table table-bordered table-striped text-dark">
            <tr>
                <td>
                    <select name="name">
                        <c:forEach items="${subServiceList}" var="list">

                            <option value="${list.name}">${list.name} , ${list.price} T ,(${list.description})</option>

                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="proposedPrice">Proposed Price :</form:label>
                </td>
                <td>
                    <form:input path="proposedPrice" name="proposedPrice"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="proposedPrice" cssClass="text-danger"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="jobDescription">jobDescription :</form:label>
                </td>
                <td>
                    <form:input path="jobDescription" name="jobDescription" placeholder="Description for work"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="jobDescription" cssClass="text-danger"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="doDate">Do Date :</form:label>
                </td>
                <td>
                    <form:input path="doDate" name="doDate" placeholder="yyyy/mm/dd"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="doDate" cssClass="text-danger"/>
                </td>
            </tr>

            <table class="table table-bordered table-striped text-dark">
                <tr>
                    <td>
                        <form:label path="address.city"> City :</form:label>
                    </td>
                    <td>
                        <form:input path="address.city" name="address.city" placeholder="Enter your city"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <form:errors path="address.city" cssClass="text-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="address.street">Street :</form:label>
                    </td>
                    <td>
                        <form:input path="address.street" name="address.street" placeholder="Enter your street"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <form:errors path="address.street" cssClass="text-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="address.plaque">Plaque :</form:label>
                    </td>
                    <td>
                        <form:input path="address.plaque" name="address.plaque" placeholder=" Enter your plaque"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <form:errors path="address.plaque" cssClass="text-danger"/>
                    </td>
                </tr>

                <tr>
                    <td>
                    </td>
                    <td>
                        <form:button name="register">Register</form:button>
                    </td>
                </tr>
            </table>
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