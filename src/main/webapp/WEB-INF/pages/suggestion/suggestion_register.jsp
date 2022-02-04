<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Expert Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</head>

<body>
<div>
    <form:form cssClass="p-1 my-5 mx-5" modelAttribute="suggest"
               action="/expert/registerSuggestion" method="post">
        <table class="table table-bordered table-striped text-dark">
            <tr>name service :${orderDto.service.name} , price service :${orderDto.service.price} , reception number
                : ${orderDto.receptionNumber}</tr>
            <tr>
                <td>
                    <form:label path="proposedPrice">proposed Price :</form:label>
                </td>
                <td>
                    <form:input path="proposedPrice" name="proposedPrice" placeholder="enter proposed Price"
                                id="price"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <span style="color: red"></span>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="durationOfWork">duration Of Work:</form:label>
                </td>
                <td>
                    <form:input path="durationOfWork" name="durationOfWork" placeholder="enter time do work"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="durationOfWork" cssClass="text-danger"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="startTime">startTime :</form:label>
                </td>
                <td>
                    <form:input path="startTime" name="startTime" placeholder="enter time for start work"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:errors path="startTime" cssClass="text-danger"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <form:button name="submit">Register</form:button>
                </td>
            </tr>
        </table>
    </form:form>
</div>
<script>

    $("form").submit(function (event) {

        if ($("#price").val() < "${orderDto.service.price}") {

            $("span").text(" Not valid proposed Price!").show();
            event.preventDefault();
        }
        return;
    });

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
