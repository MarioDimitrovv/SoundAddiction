<%--
  A Pen created at CodePen.io. You can find this one at http://codepen.io/motorlatitude/pen/JFkro.
  <!--
Copyright (c) 2014 by Lennart Hase (http://codepen.io/motorlatitude/pen/JFkro)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
-->
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/signUpFormStyle.css">
    <title>Sound Addiction SingUp</title>
    <script src="js/loginFormJS.js"></script>
</head>

<body>

<div class="body"></div>
<div class="header">
    <div>Sound<span><br>Addiction</span></div>
</div>
<br>
<div class="login">
    <h1><font size="6" face="cursive">&emsp;&emsp;Sign Up</font></h1><br><br>

    <form method="post" action="signUp">
    <label><font size="3">First name:</font></label><br>
    <input type="text" placeholder="First name..." name="firstname"
           pattern="/^[A-Z][a-z]{2,45}$/"
           title="First letter - capital. Only letters"
           required maxlength="45"><br><br><br>
    <label><font size="3">Last name:</font></label><br>
    <input type="text" placeholder="Last name..." name="lastname"
           pattern="/^[A-Z][a-z]{3,45}$/"
           title="First letter - capital. Only letters"
           required maxlength="45"><br><br><br>
    <label><font size="3">Email address:</font></label>
    <input type="email" placeholder="email@email.com" name="email"
           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
           required maxlength="45"><br><br><br>
    <label><font size="3">Password:</font></label><br>
    <p><font size="2" face="cursive" color="#f4bf42"><i>At least 6 characters: 1 uppercase, 1 lowercase letter and 1 number</i></font></p>
    <input type="password" placeholder="password" name="password1"
           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
           title="At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number"
           required maxlength="45">
    <input type="password" placeholder="repeat password" name="password2"
           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
           title="At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number"
           required maxlength="45"><br><br>
    <input type="submit" value="Sign Up">
    <p><a onclick="location.href='/'"><i>Back to Login</i></a></p>
    </form>
</div>

<script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>

</body>

</html>
