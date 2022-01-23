
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
<body >
<div>
    <form:form cssClass="p-3 m-3"  modelAttribute="customer" action="/customer/register" method="post">
        <table class="table table-bordered table-striped text-dark">
        <tr>
            <td>
                <form:label path="firstName">First Name :</form:label>
            </td>
            <td>
                <form:input path="firstName" name="firstName"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="firstName" cssClass="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="lastName">Last Name :</form:label>
            </td>
            <td>
                <form:input path="lastName" name="lastName"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="lastName" cssClass="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="email">Email :</form:label>
            </td>
            <td>
                <form:input path="email" name="email"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="email" cssClass="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password :</form:label>
            </td>
            <td>
                <form:input path="password" name="password"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="password" cssClass="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="phoneNumber">Phone Number :</form:label>
            </td>
            <td>
                <form:input path="phoneNumber" name="phoneNumber"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="phoneNumber" cssClass="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="credit">Credit :</form:label>
            </td>
            <td>
                <form:input path="credit" name="credit"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <form:errors path="credit" cssClass="text-danger"/>
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
    </form:form>
</div>



<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>