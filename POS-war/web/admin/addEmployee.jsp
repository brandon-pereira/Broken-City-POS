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
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="AdminHelper.EmployeesHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:useBean class="AdminHelper.EmployeesHelper" id="helper" scope="page"></jsp:useBean>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
        <script>
            $(function() {
                $('.more').on('click',function(){
                    $('table.expand').addClass('expanded');
                    $('form.employeeEdit').removeClass('disabled');
                    $('.more').remove();
                });
                $('form.employeeEdit').on('change',function(){$('.footer button').removeClass('disabled')});
                $('input#hireDate').attr('value',setCurrentDate);
                $('select#position').on('change',hasAdminPassword);
            });
            
            var hasAdminPassword = function(){
                if($("select#position option:selected").text() == 'Admin')
                    $('tr.adminPass').removeClass('hidden');
                else
                    $('tr.adminPass').addClass('hidden');
            }
            
            var setCurrentDate = function(){
                var d = new Date();

            var month = d.getMonth()+1;
            var day = d.getDate();

             var output = d.getFullYear() + '-' +
            ((''+month).length<2 ? '0' : '') + month + '-' +
            ((''+day).length<2 ? '0' : '') + day;
    
            return output;
            }
        </script>
        <title>Add Employee</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > <a href="listEmployees.jsp">Manage Employees</a> > Add Employee<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Add Employee</h1>
            <div class="grid">
                <form class="employeeEdit" action="../EmployeeController" method="get">
                    <table class="employeeEdit">
                         <tr>
                            <td><label for="fname">Employee #:</label></td>
                            <td><input type="number" min="1000" max="9999" id="fname" value="<%= helper.getNextEmpNo() %>" name="empNo" required></td>
                        </tr>
                        <tr>
                            <td><label for="fname">First Name:</label></td>
                            <td><input type="text" id="fname" name="firstName" required></td>
                        </tr>
                        <tr>
                            <td><label for="lname">Last Name:</label></td>
                            <td><input type="text" id="lname" name="lastName" required></td>
                        </tr>
                        <tr>
                            <td><label for="phoneNumber">Phone Number:</label></td>
                            <td><input type="text" id="phoneNumber" placeholder="123-456-7891" name="phoneNumber" required></td>
                        </tr>
                         <tr>
                            <td><label for="address">Address:</label></td>
                            <td><input type="text" id="address" name="address" required></td>
                        </tr>
                        <tr>
                            <td><label for="sin">SIN:</label></td>
                            <td><input type="number" min="100000000" max="999999999" placeholder="123456789" id="sin" name="SIN" required></td>
                        </tr>
                        <tr>
                            <td><label for="position">Position:</label></td>
                            <td>       
                                <select name="position" id="position" required>
                                  <option>Server</option>
                                  <option>Bartender</option>
                                  <option>Admin</option>
                                  <option>Other</option>
                                </select>
                            </td>
                        </tr>
                        <tr class="adminPass hidden">
                            <td><label for="adminPass">Admin Password:</label></td>
                            <td><input type="password" id="adminPass" name="adminPass" value=""></td>
                        </tr>
                        <tr class="adminPass hidden">
                            <td><label for="adminPass2">Verify Password:</label></td>
                            <td><input type="password" id="adminPass2" name="adminPass2" value=""></td>
                        </tr>
                        <tr>
                            <td><label for="hireDate">Hire Date:</label></td>
                            <td><input type="date" id="hireDate" name="hireDate" value="" required></td>
                        </tr>
                    </table>
            </div>
            <div class="footer">
                <div class="right">
                    <a href="listEmployees.jsp" class="button">Cancel</a>
                    <button class="disabled" name="add" type="submit">Add</button>
                    </form>
                </div>
        </div>
    </body>
</html>
