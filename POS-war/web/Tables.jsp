<%-- 
    Document   : LoginScreen
    Created on : Jan 20, 2015, 12:30:00 PM
    Author     : 632794
--%>
<%
  if(session.getAttribute("empName") == null || session.getAttribute("serverNo") == null)
  {
      response.sendRedirect("LoginScreen.jsp?error=Session expired.");
      return;
  }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="AdminHelper.TablesHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="res/styles/loginScreen.css">
        <script type="text/javascript" src="res/jquery.min.js"></script>
         <script type="text/javascript" src="res/scripts/table.js"></script>
         <link rel="stylesheet" href="res/styles/main.css">
         <link rel="stylesheet" href="res/styles/table.css">
         <jsp:useBean class="AdminHelper.TablesHelper" id="helper" scope="page"></jsp:useBean>
        <title>BrokenCity Select Table</title>
    </head>
    <body>
        <header class="main lock">
            <div class="logo"></div>
            <div class="currentTime"></div>
            <div class="profile">
                <div class="username"><%= session.getAttribute("empName") %></div>
                <a class="exit" href="LoginController?logout">Logout</a>
            </div>
        </header>
               
        <%= (request.getParameter("error")!=null)?"<div class='msg'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='msg warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='msg success'>"+request.getParameter("success")+"</div>":"" %>
             
        <div class="body tableView">
    
            <form action="TableController" method="GET">
                <div class="lock">
                    <input name="tableNo" class="currCombination" value="">
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
                <div class="actionButtons main">
                    <button>Access Table</button>
                </div>
            </form>
                <div class="actionButtons secondary">
                    <a href="TableController?tableNo=0">Quick Sale</a>
                    <a href="#" class="merge">Merge Tables</a>
                </div>
                <div class="tables">
                    <div class="flex grid">
                        <%= helper.getAllTables((Integer)session.getAttribute("serverNo"), true) %>
                    </div>
                </div>
                
                <div class="popup mergeTables">
                    <div class="slide slide1">
                        <h1>Merge From</h1>
                        <div class="flex grid">
                            <%= helper.getServerTables((Integer)session.getAttribute("serverNo")) %>
                        </div>
                        <button class='next bottom'>Next</button>
                    </div>
                    <div class="slide hidden slide2">
                        <h1>Merge To</h1>
                        <div class="flex grid tables">
                            <%= helper.getAllTables((Integer)session.getAttribute("serverNo"), false) %>
                        </div>
                        <form method="get" action="TableController">
                            <input type="hidden" name="tableFrom" class="tableFrom">
                            <input type="hidden" name="tableTo" class="tableTo">
                            <button name="mergeTable" class='bottom'>Submit</button>
                        </form>
                    </div>
                    <a href="#" class="close"></a>
                </div>
                <!--
                    Merging Tables:
                    - click merge table
                    - popup will show only current tables of server and will allow select 1
                    - popup will show ALL tables and allow to select merge to
                -->
        </div>
    </body>
</html>
