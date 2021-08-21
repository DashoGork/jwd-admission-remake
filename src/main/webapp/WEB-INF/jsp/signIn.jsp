<%--
  Created by IntelliJ IDEA.
  User: Юзер
  Date: 05.04.2021
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title>Sign up</title>
    <style>
        <%@include file="../css/style.css" %>
    </style>
    <style>
        <%@include file="../css/normalize.css" %>
    </style>
</head>
<body>

<div align="center">
    <form action="<%= request.getContextPath() %>/home?command=login" method="post">

            <label for="login"><b><fmt:message key="registration.login"/></b></label>
            <input type="text" name="login" id="login" value="${login}" placeholder="Enter Login" required>

        <label for="password"><b><fmt:message key="registration.password"/></b></label>
        <input type="password" name="password" id="password" value="${password}"
               placeholder="Enter password" required>

        <input type="submit" name="conf"/>
    </form>


</div>

</body>
</html>

