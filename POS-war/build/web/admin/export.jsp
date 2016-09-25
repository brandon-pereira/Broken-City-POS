<%-- 
    Document   : export
    Created on : Jan 21, 2015, 11:38:21 AM
    Author     : 632794
--%>
<%
    if(session.getAttribute("isValid") == null)
    {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
        <title>Export</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > Export<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Export to Sage 50</h1>
            <form class="exportSage login" action="../ExportController" method="get">
                <div class="grid">

                        <table class="employeeEdit">
                            <tr>
                                <td><label for="name">File Name:</label></td>
                                <td><input type="text" id="name" name="fileName" required></td>
                            </tr>
                            <tr>
                                <td><label for="from">From:</label></td>
                                <td><input type="date" id="from" name="dateFrom" required></td>
                            </tr>
                            <tr>
                                <td><label for="to">To:</label></td>
                                <td><input type="date" id="to" name="dateTo" required></td>
                            </tr>
                        </table>
                </div>
                <div class="footer">
                    <div class="right">
                        <a href="index.jsp" class="button">Cancel</a>
                        <button type="submit">Export</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>

