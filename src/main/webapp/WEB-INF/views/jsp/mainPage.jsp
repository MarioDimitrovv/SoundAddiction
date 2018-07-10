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
</head>
<body>
<div class="header">
    <img alt="SoundAddiction Logo"  src="img/logo.png" class="logo" onclick="location.href='mainPage'">
    <div class="header-right">
        <section class="search">
            <form id="searchForm" action="">
                <input id="search-filter" type="text" maxlength="35" placeholder="Search your music..."/>
                <button type="button" onclick="dropDownFunction()" class="dropdownbutton">
                    <i class="fa fa-angle-down"></i>
                </button>
                <div id="myDropdown" class="dropdown-content">
                    <a href="#">Link 1</a>
                    <a href="#">Link 2</a>
                    <a href="#">Link 3</a>
                </div>
            </form>
        </section>

        <button class="signupButton" onClick="logInFunction()">
            <c:out value="${sessionScope.USER.firstName} ${sessionScope.USER.lastName}"></c:out>
        </button>
        <div id="secondDropdown" class="secondDropdown-content">
            <a href="#">My Profile</a>
            <a href="#">My Songs</a>
            <a href="/logout">Log out</a>
        </div>
    </div>
</div>

<div class="sidebar" style="width:25%;left:0;">
    <a href="#" >Rock</a>
    <a href="#" >Metal</a>
    <a href="#" >R&B</a>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="js/mainPageJS.js"></script>
</body>
</html>

