<%-- 
    Document   : specialManager
    Created on : March 20th, 2015
    Author     : 632794
--%>
<%@page import="businessLogic.SpecialManager"%>
<%
    if(session.getAttribute("isValid") == null)
    {
        response.sendRedirect("login.jsp");
        return;
    }
    else
    {
        SpecialManager.getInstance();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
        <title>Specials Manager</title>
        <script>
            var setClicked = function(){
                $('.grid ul li').removeClass('active');
                $('.footer button').removeClass('disabled');
                $(this).addClass('active');
                var item = $(this).data('id');
                $('form.itemNo input#itemNo').attr('value',item);
                
            };
            
            var search = function(){
                toSearch = $('input.search').val();
                if(toSearch != '')
                {
                    $('.grid ul li').addClass('hidden');
                    $('.grid ul li:contains("'+toSearch+'")').removeClass('hidden');
                    $('.grid').scrollTop(0);
                }
                else $('.grid ul li').removeClass('hidden')
                reset(toSearch);
            };
            
            var reset = function(toSearch){
                $('.grid ul li').removeClass('odd');
                if(toSearch != null && toSearch != '')
                    $('.grid ul li:contains("'+toSearch+'")').filter(":odd").addClass('odd');
                else $('.grid ul li').filter(':odd').addClass('odd');  
            };
            
            $(function() {
               $('.grid ul li').on('click',setClicked);
               $('.grid ul li').filter(":odd").addClass('odd');
               $('input.search').on('keyup',search);
               $.expr[":"].contains = $.expr.createPseudo(function(arg) {
                   return function( elem ) {
                    return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
                };
                reset(null);
               });
            });
        </script>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > Special Manager<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Special Manager</h1>
            <div class="specials grid">
                <jsp:useBean class="AdminHelper.SpecialsHelper" id="helper" scope="page"></jsp:useBean>
                <ul class="employeeList">
                   <%= helper.listAllSpecials() %>
                </ul>
            </div>
            <div class="footer">
                <input type="text" placeholder="Search" class="search">
                <div class="right">
                    <a href="addSpecial.jsp" class="button">Add</a>
                    <form class="itemNo" action="../SpecialController" method="get">
                        <input id="itemNo" name="specialNo" type="hidden">
                        <button class="disabled" name="remove" type="submit">Remove</button>
                    </form>
                    <form class="itemNo" action="modifySpecial.jsp" method="get">
                        <input id="itemNo" name="specialNo" type="hidden">
                        <button class="disabled" type="submit">Modify</button>
                    </form>
                </div>
        </div>
        </div>
    </body>
</html>
