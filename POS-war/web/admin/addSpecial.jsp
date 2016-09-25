<%-- 
    Document   : specialManager
    Created on : March 20th, 2015
    Author     : 632794
--%>
<%@page import="AdminHelper.ItemsHelper" %>
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
        <link rel="stylesheet" href="../res/styles/admin.css">
        <script type="text/javascript" src="../res/jquery.min.js"></script>
        <title>Specials Manager</title>
        <jsp:useBean class="AdminHelper.ItemsHelper" id="helper" scope="page"></jsp:useBean>
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
            
            var updateDOWInput = function(){
                var selected = ""; 
                $.each($('.tab.pill div.selected'),function(){
                    selected += $(this).data('no') + ";";
                });
                selected = selected.substring(0, selected.length - 1);
                $('input#dow').attr('value',selected);
            };
            
            var updateDOWDisplay = function(){
                var days = $('input#dow').attr('value').split(";");
                for(var i = 0; i < days.length; i++)
                    $('.pill.tab div[data-no='+days[i]+']').addClass('selected');
            };
            
            var updateSelectedItems = function(){
                var selected = ""; 
                $.each($('.specials.right.grid li'),function(){
                    selected += $(this).data('id') + ";";
                });
                selected = selected.substring(0, selected.length - 1);
                $('input#selectedItems').attr('value',selected);
            };
            
            $(function() {
                updateSelectedItems();
               $('.tab.pill div').on('click',function(){$(this).toggleClass('selected');updateDOWInput();});
               $('.grid ul li').on('click',setClicked);
               $('.grid ul li').filter(":odd").addClass('odd');
               $('input.search').on('keyup',search);
               updateDOWDisplay();
               $('.grid.left').on('dblclick','li',function(){
                   var item = $(this);
                   $(this).removeClass('active');
                   $('.grid.right span.help').remove();
                   $('.grid.right ul.foodList').append(item[0].outerHTML);
                   item.remove();
                   reset();
                   updateSelectedItems();
               });
                $('.grid.right').on('dblclick','li',function(){
                       var item = $(this);
                       $(this).removeClass('active');
                       $('.grid.left ul.foodList').prepend(item[0].outerHTML);
                       item.remove();
                       reset();
                        updateSelectedItems();
                });
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
                <div class="breadcrumb"><a href="index.jsp">Home</a> > <a href="specialManager.jsp">Special Manager</a> > Add Special<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Modify Special</h1>
            <div class="grid specialEdit">
                <form class="employeeEdit" action="../SpecialController" method="get">
                    <table class="employeeEdit">
                        <tr>
                            <td><label for="specialName">Special Name:</label></td>
                            <td><input type="text" id="specialName" value="" name="specialName"></td>
                        </tr>
                        <tr>
                            <td><label for="discountedPrice">Discounted Price:</label></td>
                            <td><input type="number" id="discountedPrice" min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional" value="" name="discountPrice"></td>
                     
                        </tr>
                        <tr>
                            <td>Days Of Week:</td>
                            <td>
                                <input type="hidden" name="dow" id="dow" value=""> 
                                <div class="pill tab">
                                    <div data-no="0">Mon</div>
                                    <div data-no="1">Tue</div>
                                    <div data-no="2">Wed</div>
                                    <div data-no="3">Thu</div>
                                    <div data-no="4">Fri</div>
                                    <div data-no="5">Sat</div>
                                    <div data-no="6">Sun</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>Start Time:</td>
                            <td clas="nopadding">
                                <table class="inner">
                                    <tr>
                                        <td><input type="time" name="startTime" value=""></td>
                                        <td><label for="cost">End Time: </label></td>
                                        <td><input type="time" name="endTime" value=""></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                          </table>

            </div>
            <div class="split">
                <h1>Items</h1>
                <h1>Items for Special</h1>
            </div>
            <div class="specials grid half left">
                <%= helper.getItemsList() %>
            </div>
            <div class="specials grid half right">
                <ul class='foodList'>
                    
                </ul>
               <span class='help'>Double click items on the left to add to specials.</span>
            </div>
            <input type="hidden" name="selectedItems" id="selectedItems">
            <div class="footer">
                <input type="text" placeholder="Search" class="search">
                <div class="right">
                        <a href="specialManager.jsp" class="button">Cancel</a>
                        <button class="" name="add" type="submit">Add</button>
                </div>
            </form>
        </div>
        </div>
    </body>
</html>
