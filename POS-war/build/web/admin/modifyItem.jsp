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
    else if(request.getParameter("itemNo") == null)
    {
       response.sendRedirect("itemManager.jsp?error=Unexpected error, missing item number.");
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
         <jsp:useBean class="AdminHelper.ItemsHelper" id="helper" scope="page"></jsp:useBean>
        <script>
            $(function() {
                $('.more').on('click',function(){
                    $('table.expand').addClass('expanded');
                    $('form.employeeEdit').removeClass('disabled');
                    $('.more').remove();
                });
                $('form.employeeEdit').on('change',function(){$('.footer button').removeClass('disabled')});
                $('select#position').on('change',hasAdminPassword);
                $('.specials tr input').on('click',function(){console.log('clicked input')});
                $('.specials input[type=checkbox').on('change',updateSpecials);                
                $('.specials tr').on('click',setChecked);
                $('#extras').on('change',updateExtras);
                $('.button.add.extras').on('click',addExtra);
                $('select').on('change',function(){
                   if($(this).val() == 'Speed Bar')
                       $('tr.notSpeedBar').addClass('hidden');
                   else
                       $('tr.notSpeedBar').removeClass('hidden');
                });
                $('input.extraAdd').keydown(function(event){
                    if(event.keyCode == 13) {
                      event.preventDefault();
                      return false;
                    }
                });
                $('input.extras').on('change',updateExtraList);
                updateExtras();   
                $.each($('input.extras'),function(){
                    $(this).change();
                });
            });
            
            function setChecked(){
                $(this).find('input[type=checkbox]').click();
            }
            
            var hasAdminPassword = function(){
                if($("select#position option:selected").text() == 'Admin')
                    $('tr.adminPass').removeClass('hidden');
                else
                    $('tr.adminPass').addClass('hidden');
            }
            
            // Method for getting all checked specials and storing in input
            function updateSpecials(){
                var delimiter = "";
                $('.specials input[type=checkbox]:checked').each(function() {
                    delimiter += this.value + ";";
                });
                delimiter = delimiter.substring(0, delimiter.length - 1);
                console.log("Specials: " + delimiter);
                $('input#delimiter').attr('value',delimiter);
            }
            
            // Method for adding or removing extras divs based on # of extras field.
            function updateExtras(){
                var noOfExtras = $('#extras').val();
                $('.extrasView tr').addClass('hidden');
                for(var i = 0; i < noOfExtras;i++)
                    $('.extrasView tr[data-id='+i+']').removeClass('hidden');
            }
            
            // Method for adding extra from add field into hidden text field containing all extras.
            function addExtra(){
                var current =  $(this).closest('tr').find('input.extras').val();
                var string = $(this).closest('td').find('input.extraAdd').val();
                if(current != '')
                    current += ";"+string;
                else
                    current = string;
                $(this).closest('tr').find('input.extras').attr('value',current);
                $(this).closest('td').find('input.extraAdd').val('');
                $(this).closest('tr').find('input.extras').change();
            }
            
            // Takes hidden input text and builds list of current extras.
            function updateExtraList(){
                if($(this).val() == '')
                    return false;
                var array = $(this).val().split(";");
                html = "";
                $.each(array,function(i){
                    html += "<li>"+array[i]+"</li>";
                 });
                 $(this).closest('td').find('.currentExtras').html(html);
                 $('.extrasView li').on('click',removeExtra);
            }
            
            function removeExtra(){
                var curr = $(this).closest('tr').data('id');
                $(this).remove();
                updateExtraInput(curr);
            }
           
            // Builds hidden input by getting li's and making string
            function updateExtraInput(curr){ 
                var html = "";
                $.each($('tr[data-id='+curr+'] .currentExtras li'),function(i){
                    html += $(this).text()+";";
                });
                html = html.substring(0, html.length - 1);
                $('tr[data-id='+curr+'] td input.extras').attr('value',html);
            }
            
        </script>
        <title>Modify Item</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="breadcrumb"><a href="index.jsp">Home</a> > <a href="itemManager.jsp">Item Manager</a> > Modify Item<div class="right"><a href="../AdminLoginController?logout">Logout</a></div></div>
                <div class="logo">Broken City<span>Administration Portal</span></div>
            </div>
             <%= (request.getParameter("error")!=null)?"<div class='error'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='error warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='error success'>"+request.getParameter("success")+"</div>":"" %>
            <h1>Modify Item</h1>
            <div class="grid employeeEdit">
                <form class="employeeEdit" action="../ItemController" method="get">
                    <table class="employeeEdit">
                    <input type="hidden" id="itemNo" value="<%= request.getParameter("itemNo") %>" name="itemNo"></td>
                        <tr>
                            <td><label for="itemName">Item Name:</label></td>
                            <td><input type="text" id="itemName" value="<%= helper.getItemName(request.getParameter("itemNo")) %>" name="itemName"></td>
                        </tr>
                        <tr>
                            <td><label for="itemDesc">Description:</label></td>
                            <td><input type="text" id="itemDesc" value="<%= helper.getItemDesc(request.getParameter("itemNo")) %>" name="itemDesc"></td>
                        </tr>
                    </table>
                    <table class="employeeEdit">
                        <tr>
                            <td>Base Price:</td>
                            <td clas="nopadding">
                                <table class="inner">
                                    <tr>
                                        <td><input type="number" name="basePrice" min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional" value="<%= helper.getCost(request.getParameter("itemNo")) %>"></td>
                                        <td><label for="cost">Cost: </label></td>
                                        <td><input type="number" id="cost" name="cost" min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional" value="<%= helper.getCost(request.getParameter("itemNo")) %>"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr class="notSpeedBar">
                            <td># of sides:</td>
                            <td clas="nopadding">
                                <table class="inner">
                                    <tr>
                                       <td><input type="number" id="numberOfSides" min="0" max="3" value="<%= helper.getNoSides(request.getParameter("itemNo")) %>" name="numberOfSides"></td>
                                        <td><label for="cost"># of extras: </label></td>
                                        <td><input type="number" id="extras" name="extras" min="0" max="3" step="1" title="" value="<%= helper.getNoExtras(request.getParameter("itemNo")) %>"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <div class="">
                        <table class="employeeEdit extrasView nopadding">
                            <tr data-id='0' class='hidden'>
                                <td>Name: <p>Price: <p> Add Extra:</td>
                                <td width=250px>
                                    <input type='text' class='extraName' name='extra1Name' placeholder='Extra Name' value='<%= helper.getExtraName(request.getParameter("itemNo"), 1) %>'>
                                    <input type='number' class='extraPrice' min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional"  name='extra1Price' placeholder='Extra Price' value='<%= helper.getExtraPrice(request.getParameter("itemNo"),1) %>'>
                                    <input class='extraAdd' type=text>
                                    <div class='extras button add'>Add</div>
                                </td>
                                <td>
                                    <span class="currentExtrasText">Current Extras (click to remove):</span>
                                    <div class='currentExtras'></div>
                                    <input type='hidden' name='extras1' class='extras' value='<%= helper.getExtra(request.getParameter("itemNo"), 1) %>'>
                                </td>
                            </tr>
                            <tr data-id='1' class='hidden'>
                                <td>Name: <p>Price: <p> Add Extra:</td>
                                <td width=250px>
                                    <input type='text' class='extraName' name='extra2Name' placeholder='Extra Name' value='<%= helper.getExtraName(request.getParameter("itemNo"), 2) %>'>
                                    <input type='number' min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional"  class='extraPrice' name='extra2Price' placeholder='Extra Price' value='<%= helper.getExtraPrice(request.getParameter("itemNo"),2) %>'>
                                    <input class='extraAdd' type=text>
                                    <div class='extras button add'>Add</div>
                                </td>
                                <td>
                                    <span class="currentExtrasText">Current Extras (click to remove):</span>
                                    <div class='currentExtras'></div>
                                    <input type='hidden' name='extras2' class='extras' value='<%= helper.getExtra(request.getParameter("itemNo"), 2) %>'>
                                </td>
                            </tr>
                            <tr data-id='2' class='hidden'>
                               <td>Name: <p>Price: <p> Add Extra:</td>
                                <td width=250px>
                                    <input type='text' class='extraName' name='extra3Name' placeholder='Extra Name' value='<%= helper.getExtraName(request.getParameter("itemNo"), 3) %>'>
                                    <input type='number' min="0" max="9999" step="0.01" size="4" title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional"  class='extraPrice' name='extra3Price' placeholder='Extra Price' value='<%= helper.getExtraPrice(request.getParameter("itemNo"),3) %>'>
                                    <input class='extraAdd' type=text>
                                    <div class='extras button add'>Add</div>
                                </td>
                                <td>
                                    <span class="currentExtrasText">Current Extras (click to remove):</span>
                                    <div class='currentExtras'></div>
                                    <input type='hidden' name='extras3' class='extras' value='<%= helper.getExtra(request.getParameter("itemNo"), 3) %>'>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table class="employeeEdit">
                        <tr>
                            <td><label for="itemType">Item Type:</label></td>
                            <td><div class="select"><%= helper.getItemType(request.getParameter("itemNo")) %></div></td>
                        </tr>
                        <tr>
                            <td><label for="isActive">Enabled:</label></td>
                            <td><input type="checkbox" id="isActive" name="isActive" <%= helper.isEnabled(request.getParameter("itemNo")) %>></td>
                        </tr>
                    </table>
              
            </div>
            <div class="footer">
                <div class="right">
                    <a href="itemManager.jsp" class="button">Cancel</a>
                    <button class="disabled" name="modify" type="submit">Modify</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

