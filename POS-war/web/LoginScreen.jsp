<%-- 
    Document   : LoginScreen
    Created on : Jan 20, 2015, 12:30:00 PM
    Author     : 632794
--%>

<%@page import="containers.Menu"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="res/styles/loginScreen.css">
        <script type="text/javascript" src="res/jquery.min.js"></script>
         <script type="text/javascript" src="res/scripts/loginScreen.js"></script>
        <title>BrokenCity Login</title>
    </head>
    <% Menu.getInstance(); %>
    <body>
        <!-- coded by branclon -->
        <header class="main lock">
            <div class="logo"></div>
            <div class="currentTime">3:30 PM</div>
        </header>
        
        <div class="body login">
             <%= (request.getParameter("error")!=null)?"<div class='msg'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='msg warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='msg success'>"+request.getParameter("success")+"</div>":"" %>
            <form action="LoginController" method="GET">
                <div class="logo"></div>
                <div class="lock">
                    <input name="userID" class="currCombination" value="">
                    <div class="grid">
                        <div class="one" data-text="1"></div>
                        <div class="two" data-text="2"></div>
                        <div class="three" data-text="3"></div>
                        <div class="four" data-text="4"></div>
                        <div class="five" data-text="5"></div>
                        <div class="six" data-text="6"></div>
                        <div class="seven" data-text="7"></div>
                        <div class="eight" data-text="8"></div>
                        <div class="nine" data-text="9"></div>
                        <div class="clear main" data-text="Clear"></div>
                        <div class="zero" data-text="0"></div>
                        <div class="submit main" data-text="Backspace"></div>
                    </div>
                </div>
                <div class="buttons">
                    <button name="accessMenu" type="submit" class="b1"><span>Access Menu</span></button>
                    <button name="clockIn" type="submit" class="b2"><span>Clock In</span></button>
                    <button name="clockOut" type="submit" class="b3"><span>Clock Out</span></button>
                </div>
            </form>
        </div>
    </body>
</html>
