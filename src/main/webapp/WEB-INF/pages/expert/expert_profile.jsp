<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>customer_profile Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

</head>
<body>
<%--<img src="@imgSrc" style="width: 100px"/>--%>

<div class="container">
    <h1 style="color: aqua">${message}</h1>
    <br><br>

    <table class="table table-bordered table-striped text-dark">
        <tr>
            <td>
                First Name :
            </td>
            <td>
                ${expert.firstName}
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                Last Name :
            </td>
            <td>
                ${expert.lastName}
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                Email :
            </td>
            <td>
                ${expert.email}
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                Password :
            </td>
            <td>
                ${expert.password}


            </td>
            <td>

                <button class="btn btn-outline-primary" onclick="ShowChangePassDiv()" id="show" value="change">Edit
                </button>
            </td>
        </tr>

        <tr>
            <form action="/customer/pass" method="post">
                <td>
                    <div id="hide1" style="display: none">
                        New password :
                    </div>
                </td>
                <td>
                    <div id="hide2" style="display: none">
                        <input type="password" placeholder="Enter Password" name="password" class="form-control"
                               required
                               pattern="^[A-Za-z0-9._%+@|!&*=/-]{8,}$">
                    </div>
                </td>
                <td>

                    <div id="hide3" style="display: none">
                        <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">Change</button>
                    </div>
                </td>
            </form>
        </tr>
        <tr>
            <td>
                Phone Number :
            </td>
            <td>
                ${expert.phoneNumber}
            </td>
            <td>
                <button class="btn btn-outline-primary" onclick="ShowChangePhoneDiv()" id="showItem" value="change">
                    Edit
                </button>
            </td>
        </tr>
        <tr>
            <form action="/customer/phone" method="post">
                <td>
                    <div id="hide4" style="display: none">
                        New password :
                    </div>
                </td>
                <td>
                    <div id="hide5" style="display: none">
                        <input type="text" placeholder="09*********" name="phone" class="form-control" required
                               pattern="^(\+98|0)?9\d{9}$">
                    </div>
                </td>
                <td>

                    <div id="hide6" style="display: none">
                        <button type="submit" class="btn btn-primary" style="border-bottom: darkblue">Change</button>
                    </div>
                </td>
            </form>
        </tr>
        <tr>
            <td>
                Credit :
            </td>
            <td>
                ${expert.credit}
            </td>
            <td>
            </td>
        </tr>

        <tr>
            <td>
                User Status :
            </td>
            <td>
                ${expert.userStatus}
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                Last Update Date :
            </td>
            <td>
                ${expert.dateUpdate}
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
                    <c:forEach items="${orderList}" var="list">
                        <option value="${list.receptionNumber}"> ${list.receptionNumber}
                            , ${list.service.name},${list.status}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                service :
            </td>
            <td>
                <select name="name">
                    <c:forEach items="${serviceList}" var="list">
                        <option value="${list.name}"> ${list.name}
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td>
                suggestion :
            </td>
            <td>
                <select name="name">
                    <c:forEach items="${suggestionDtoList}" var="list">
                        <option value="${list.receptionNumber}"> ${list.receptionNumber}
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
            </td>
        </tr>
    </table>

    <br><br>
    <a href="<c:url value="/"/>" class="btn btn-outline-primary">Main Menu</a>
    <a href="<c:url value="/expert/suggestion"/>" class="btn btn-outline-primary">new suggestion</a>
</div>
<script>
  /*  @{
        var: base64 = Convert.ToBase64String(${expert.photo}),
        var: imgSrc = String.Format("data:image/gif;base64,{0}", base64)
    }
*/
    function ShowChangePassDiv() {
        var show = document.getElementById("show");
        var hide1 = document.getElementById("hide1");
        var hide2 = document.getElementById("hide2");
        var hide3 = document.getElementById("hide3");
        hide1.style.display = show.value === "change" ? "block" : "none";
        hide2.style.display = show.value === "change" ? "block" : "none";
        hide3.style.display = show.value === "change" ? "block" : "none";
    }

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
