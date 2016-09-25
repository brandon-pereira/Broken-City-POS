<%-- 
    Document   : listEmployees
    Created on : Jan 21, 2015, 11:38:21 AM
    Author     : 632794
--%>
<%
    if(session.getAttribute("isValid") == null)
    {
        response.sendRedirect("index.jsp");
        return;
    }
    else if(request.getParameter("empNo") == null)
    {
       response.sendRedirect("listEmployees.jsp?error=Unexpected error, missing employee number.");
       return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="AdminHelper.EmployeesHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
         <jsp:useBean class="AdminHelper.EmployeesHelper" id="helper" scope="page"></jsp:useBean>
        <script>
            $(function() {
                $('.more').on('click',function(){
                    $('table.expand').addClass('expanded');
                    $('form.employeeEdit').removeClass('disabled');
                    $('.more').remove();
                });
                $('form.employeeEdit').on('change',function(){$('.footer button').removeClass('disabled')});
                $('select#position').on('change',hasAdminPassword);
                $('#isActive').on('change',hasBeenDisabled);
                
            });
            
            var hasAdminPassword = function(){
                if($("select#position option:selected").text() == 'Admin')
                    $('tr.adminPass').removeClass('hidden');
                else
                    $('tr.adminPass').addClass('hidden');
            }
            
            var hasBeenDisabled = function(){
                if($("#isActive:checked").length == 1)
                    $('#endDate').attr('required',false);
                else
                    $('#endDate').attr('required',true);
            }
        </script>
        <title>Modify Employee</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > <a href="listEmployees.jsp">Manage Employees</a> > Modify Employee<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Modify Employee</h1>
            <div class="grid">
                <form class="employeeEdit" action="../EmployeeController" method="get">
                    <table class="employeeEdit">
                         <input type="hidden" id="empNo" value="<%= request.getParameter("empNo") %>" name="empNo"></td>
                        <tr>
                            <td><label for="fname">Employee #:</label></td>
                            <td><%= request.getParameter("empNo") %></td>
                        </tr>
                        <tr>
                            <td><label for="fname">First Name:</label></td>
                            <td><input type="text" id="fname" value="<%= helper.getFirstName(request.getParameter("empNo")) %>" name="firstName"></td>
                        </tr>
                        <tr>
                            <td><label for="lname">Last Name:</label></td>
                            <td><input type="text" id="lname" value="<%= helper.getLastName(request.getParameter("empNo")) %>" name="lastName"></td>
                        </tr>
                        <tr>
                            <td><label for="phoneNumber">Phone Number:</label></td>
                            <td><input type="text" id="phoneNumber" value="<%= helper.getPhone(request.getParameter("empNo")) %>" name="phoneNumber"></td>
                        </tr>
                    </table>
                    <table class="employeeEdit">
                         <tr>
                            <td><label for="address">Address:</label></td>
                            <td><input type="text" id="address" value="<%= helper.getAddress(request.getParameter("empNo")) %>" name="address"></td>
                        </tr>
                        <tr>
                            <td><label for="sin">SIN:</label></td>
                            <td><input type="number" min="100000000" max="999999999" id="sin" value="<%= helper.getSIN(request.getParameter("empNo")) %>" name="SIN"></td>
                        </tr>
                        <tr>
                            <td><label for="hireDate">Hire Date:</label></td>
                            <td><input type="date" id="hireDate" value="<%= helper.getHireDate(request.getParameter("empNo")) %>" name="hireDate"></td>
                        </tr>
                        <tr>
                            <td><label for="endDate">End Date:</label></td>
                            <td><input type="date" id="endDate" value="<%= helper.getEndDate(request.getParameter("empNo")) %>" name="endDate"></td>
                        </tr>
                        <tr>
                            <td><label for="position">Position:</label></td>
                            <td>       
                                <%= helper.getPosition(request.getParameter("empNo")) %>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="address">Enabled:</label></td>
                            <td><input type="checkbox" id="isActive" name="isActive" <%= helper.isEnabled(request.getParameter("empNo")) %>></td>
                        </tr>
                    </table>
            </div>
            <div class="footer">
                <%= helper.changeAdminPass(request.getParameter("empNo")) %>
                <div class="right">
                    <a href="listEmployees.jsp" class="button">Cancel</a>
                    <button class="disabled" name="modify" type="submit">Modify</button>
                    </form>
                </div>
            </div>
        </div>
        
        <div class='popup' id='changeAdminPass'>
            <article>
                <form action='../EmployeeController' method='get'>
                    <input type="hidden" id="empNo" value="<%= request.getParameter("empNo") %>" name="empNo">
                    <h1>Change Administrator Password</h1>
                    <label for='currPass'>Current Password:</label>
                    <input type='password' id='currPass' name='currentPassword'>
                    <label for='newPassword'>New Password:</label>
                    <input type='password' id='newPassword' name='newPassword'>
                    <label for='verifyPassword'>Verify New Password:</label>
                    <input type='password' id='verifyPassword' name='verifyPassword'>
                    <button>Submit</button>
                </form>
            </article>
            <a href='#' class='close'></a>
        </div>
    </body>
</html>
