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
    <h2 style="color: blue">please Enter new sub service for Add</h2>
    <br><br>
    <h2 style="color: darkcyan">${message}</h2>
    <br><br>
    <h2 style="color: red">${alert}</h2>
    <br><br>
    <table border="5" width="70%" cellpadding="2">
        <tr><td>name</td><td>price</td><td>Description</td></tr>
        <c:forEach var="list" items="${subServiceList}">
            <tr>
                <td>${list.name}</td>
                <td>${list.price}</td>
                <td>${list.description}</td>
            </tr>
        </c:forEach>
        <tr>

            <td></td>
            <td>   <button class="btn btn-outline-primary" onclick="ShowChangePassDiv()" id="show" value="change">New sub Service
            </button>
            <td></td>
            </td>
        </tr>
        <form:form action="/manager/newSubService" method="post" modelAttribute="subService">
        <tr>

                <td>
                    <div id="hide2" style="display: none">
                        <form:input path="name" placeholder="Enter New sub Service name"  class="form-control"  />
                    </div>
                </td>
                <td>
                    <div id="hide4" style="display: none">
                    <form:input path="price"  placeholder="Enter New sub Service price" class="form-control"/>
                    </div>
                </td>
                <td>
                    <div id="hide5" style="display: none">
                    <form:input path="description" placeholder="Enter New sub Service description" class="form-control"/>
                    </div>
                </td>

        </tr>
            <tr>

                <td></td>
            <td>
                <div id="hide3" style="display: none">
                <form:button name="submit" class="btn btn-primary" style="border-bottom: darkblue">register new sub service</form:button>
                </div>
            </td>
                <td></td>

            </tr>
        </form:form>
    </table>
</div>
<a href="<c:url value="/manager"/>" class="btn btn-outline-primary">manager page</a>
<script>
    function ShowChangePassDiv() {
        var show = document.getElementById("show");
        var hide2 = document.getElementById("hide2");
        var hide3 = document.getElementById("hide3");
        var hide4 = document.getElementById("hide4");
        var hide5 = document.getElementById("hide5");
        hide2.style.display = show.value === "change" ? "block" : "none";
        hide3.style.display = show.value === "change" ? "block" : "none";
        hide4.style.display = show.value === "change" ? "block" : "none";
        hide5.style.display = show.value === "change" ? "block" : "none";
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