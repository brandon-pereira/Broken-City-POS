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
<%@page import="AdminHelper.ItemsHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
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
        <title>Item Manager</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > Item Manager<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Manage Items</h1>
            <div class="items grid">
                <jsp:useBean class="AdminHelper.ItemsHelper" id="helper" scope="page"></jsp:useBean>
                <%= helper.getItemsList() %>
            </div>
            <div class="footer">
                <input type="text" placeholder="Search" class="search">
                <div class="right">
                    <a href="addItem.jsp" class="button">Add</a>
                    <form class="itemNo" action="../ItemController" method="get">
                        <input id="itemNo" name="itemNo" type="hidden">
                        <button class="disabled" name="remove" type="submit">Remove</button>
                    </form>
                    <form class="itemNo" action="modifyItem.jsp" method="get">
                        <input id="itemNo" name="itemNo" type="hidden">
                        <button class="disabled" type="submit">Modify</button>
                    </form>
                </div>
        </div>
    </body>
</html>
