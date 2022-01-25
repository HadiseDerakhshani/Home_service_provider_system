<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>list sub service form</title>
</head>
<body>


<form action="/expert/register" method="post">
    ${expert.firstName} ${expert.lastName} Select a service :&nbsp;
    <%-- <select name="nameService">
         <c:forEach items="${serviceList}" var="list">
             <option value="${list.name}">${list.name}, ${list.price} T ,(${list.description})</option>
         </c:forEach>
     </select>
     <br/><br/>
     <input type="submit" value="Submit" />--%>
</form>

</body>
</html>
