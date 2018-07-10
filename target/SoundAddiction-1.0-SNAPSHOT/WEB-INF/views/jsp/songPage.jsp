<%--
  Created by IntelliJ IDEA.
  User: Mario
  Date: 11.7.2018 г.
  Time: 1:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${song.name}"></c:out></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/mainPageStyle.css">
    <link rel="stylesheet" href="../css/songPageStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <base href="http://localhost:8080/">
</head>
<body>

<!-- Include the header file via JSTL -->
<c:import url="header.jsp"></c:import>

<br><br>
<h1><c:out value="${song.name}"></c:out></h1>
<div class="mainDiv">

    <div class="imagePart">
        <img src="${song.imagePath}" alt="${song.name}">
    </div>

    <div class="songInfo">
        <h3>Genre: <c:out value="${song.genre}"></c:out></h3>
        <h3>Artist/Group: <c:out value="${song.singer}"></c:out></h3>
        <h3>Rating: <c:out value="${song.rating}"></c:out></h3>
        <h3>Publish date: <c:out value="${song.publishDate}"></c:out></h3>
        <c:if test="${ not empty song.album}">
            <h3>Album: <c:out value="${song.album}"></c:out></h3>
        </c:if>
    </div>
</div>

<div class="buttonPart">
    <div class="price">
        <h2><c:out value="${song.price}"></c:out>$</h2>
    </div>

    <div class="buy">
        <button type="button" class="buyButton">
            BUY
            <i class="fa fa-shopping-cart"></i>
        </button>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="../js/mainPageJS.js"></script>
</body>
</html>
