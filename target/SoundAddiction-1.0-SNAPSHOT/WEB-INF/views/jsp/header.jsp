<%--
  Created by IntelliJ IDEA.
  User: Mario
  Date: 11.7.2018 г.
  Time: 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<link rel="stylesheet" href="../css/mainPageStyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<base href="http://localhost:8080/">

<div class="header">
    <img alt="SoundAddiction Logo"  src="img/logo.png" class="logo" onclick="location.href='mainPage'">
    <div class="header-right">
        <section class="search">
            <form id="searchForm" action="search" method="GET">
                <input id="search-filter" type="search" maxlength="35" required
                       placeholder="Search your music..." name="word"/>
                <button type="button" onclick="dropDownFunction()" class="dropdownbutton">
                    <i class="fa fa-angle-down"></i>
                </button>
                <div id="myDropdown" class="dropdown-content">
                    <p><input type="radio" id="test1" name="searchChoice" value="name" checked>
                        <label for="test1">By name</label></p>
                    <p><input type="radio" id="test2" name="searchChoice" value="performer">
                        <label for="test2">By performer</label></p>
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
