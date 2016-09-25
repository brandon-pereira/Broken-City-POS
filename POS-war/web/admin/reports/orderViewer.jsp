<%-- 
    Document   : report_empHours
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
        <title>Get Orders</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="../index.jsp">Home</a> > <a href="index.jsp">Reports</a> > View Order<div class="right"><a href="../../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
             <form class="login" method="get" action="../../ReportController">
                <div class="grid">
                    <input type="hidden" value="e4" name="sent">
                    <table class="employeeEdit">
                        <tr>
                            <td>Order #:</td>
                            <td><input type="text" name="orderNo"></td>
                        </tr>
                    </table>
                </div>
                <div class="footer">
                    <div class="right"> 
                        <button type="submit" name="orderViewer">Export</button>
                    </div>
                </div>
             </form>
        </div>
    </body>
</html>
