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
                    <input type="text" placeholder="${amount}" name="price" id="price" class="form-control" required>

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
                </td>
                <td>
                    <button type="submit" class="btn btn-primary" name="submit" style="border-bottom: darkblue">payment</button>
                </td>
            </tr>
        </table>
    </form:form>
</div>
<script>

    $("form").submit(function (event) {

        if ($("#price").val() < "${amount}" || $("#price").val() < "${amount}") {

            $("span").text(" Not valid Price!").show();
            event.preventDefault();
        }
        return;
    });






        var IdealTimeOut = 10; //10 seconds
        var idleSecondsTimer = null;
        var idleSecondsCounter = 0;
        document.onclick = function () { idleSecondsCounter = 0; };
        document.onmousemove = function () { idleSecondsCounter = 0; };
        document.onkeypress = function () { idleSecondsCounter = 0; };
        idleSecondsTimer = window.setInterval(CheckIdleTime, 1000);

        function CheckIdleTime() {
        idleSecondsCounter++;
        var oPanel = document.getElementById("timeOut");
        if (oPanel) {
        oPanel.innerHTML = (IdealTimeOut - idleSecondsCounter);
    }
        if (idleSecondsCounter >= IdealTimeOut) {
        window.clearInterval(idleSecondsTimer);
        alert("Your Session has expired. Please login again.");
        window.location = "https://www.aspsnippets.com/";
    }
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