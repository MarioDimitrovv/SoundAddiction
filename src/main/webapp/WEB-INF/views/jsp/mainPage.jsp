<%--
  Created by IntelliJ IDEA.
  User: Petko
  Date: 06/07/2018
  Time: 01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    Tvoeto ime e: ${ sessionScope.USER.firstName } ${ sessionScope.USER.lastName } ${ sessionScope.USER.email }
</body>
</html>
