<%-- 
    Document   : report_index
    Created on : Jan 21, 2015, 11:38:21 AM
    Author     : 632794
--%>
<%
    if(session.getAttribute("isValid") == null)
    {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../res/styles/admin.css">
        <script type="text/javascript" src="../../res/jquery.min.js"></script>
        <title>Reporting</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="../index.jsp">Home</a> > Reports<div class="right"><a href="../../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
             <div class="homeScreenGrid">
                    <a href="emphours.jsp">Employee Hours</a>
                    <a href="getOrders.jsp">Get All Orders</a>
                    <a href="payments.jsp">Payments</a>
                    <a href="orderViewer.jsp">Order Viewer</a>
             </div>
        </div>
    </body>
</html>
