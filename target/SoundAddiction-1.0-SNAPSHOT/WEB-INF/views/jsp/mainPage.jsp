
<%--
  Created by IntelliJ IDEA.
  User: Petko
  Date: 06/07/2018
  Time: 01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sound Addiction</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/mainPageStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <base href="http://localhost:8080/">
</head>
<body>

<!-- Include the header file via JSTL -->
<c:import url="header.jsp"></c:import>

<div class="mainContent">
    <div class="sidebar" style="width:25%;left:0;">
        <c:forEach var="genre" items="${sessionScope.genres}">
            <a href="/category/${genre.genreId}" >
                <c:out value="${genre.value}"></c:out>
            </a>
        </c:forEach>
    </div>
    <div class="main-right">
        <c:forEach var="song" items="${songs}">
            <img src="${song.imagePath}" alt="${song.name}">
            <button onclick="location.href='song/${song.songId}'">Info</button>
        </c:forEach>
    </div>
</div>
<div class="scrollup">
    <a href='mainPage.html' class='back-to-top'>

        <i class='fa fa-angle-up'></i>

    </a>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="../js/mainPageJS.js"></script>
</body>
</html>
