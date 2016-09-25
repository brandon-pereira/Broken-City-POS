<%-- 
    Document   : listEmployees
    Created on : Jan 21, 2015, 11:38:21 AM
    Author     : 632794
--%>
<%@page import="businessLogic.EmployeeManager"%>
<%
    EmployeeManager.getInstance();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
        <script>
            $(function() {
               $('form').on('change',function(){$('button').removeClass('disabled');}) 
            });
        </script>
        <title>Login</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb">Login</div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Login</h1>
            <form class="login" action="../AdminLoginController" method="get">
                <div class="login grid">
                    <span>User ID:</span>
                    <input name="userID" type="text" required>
                    <span>Password:</span>
                    <input name="password" type="password" required>
                </div>
                <div class="footer">
                    <div class="right">
                        <button class="disabled" type="submit">Login</button>
                    </div>
                </div>
            </form>
            </div>
    </body>
</html>
