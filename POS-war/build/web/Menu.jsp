<%-- 
    Document   : LoginScreen
    Created on : Jan 20, 2015, 12:30:00 PM
    Author     : 632794
--%>

<%
  if(session.getAttribute("empName") == null)
  {
      response.sendRedirect("LoginScreen.jsp?error=Session expired.");
      return;
  }
  else if(session.getAttribute("table") == null || session.getAttribute("tableNo") == null)
  {
      response.sendRedirect("Tables.jsp?error=Failed to load specified table.");
      return;
  }
  
  int seatNo = (Integer) session.getAttribute("seatNo");
  Table table = (Table)session.getAttribute("table");
  int tableNo = (Integer)session.getAttribute("tableNo");
  
  // get current view
  String selection = request.getParameter("view");
  // if view is null
  if(selection == null)
  { 
      // if there is a view stored
      if(session.getAttribute("mainView") != null)
          selection = (String)session.getAttribute("mainView");
      else
          selection = "0";
  }
  // if view not null, store in session
  else
        session.setAttribute("mainView",selection);
%>
<%@page import="containers.Menu"%>
<%@page import="containers.Table"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="MenuHelper.OrderHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="res/styles/main.css">
        <script type="text/javascript" src="res/jquery.min.js"></script>
        <script type="text/javascript" src="res/scripts/menu.js"></script>
        <script type="text/javascript" src="res/scripts/loginScreen.js"></script>
        <title>BrokenCity Menu</title>

        <jsp:useBean class="MenuHelper.OrderHelper" id="helper" scope="page"></jsp:useBean>
        <script>
 
        </script>
    </head>
    <body>
             <%= (request.getParameter("error")!=null)?"<div class='msg'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='msg warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='msg success'>"+request.getParameter("success")+"</div>":"" %>

        <header class="main">
            <div class="logo"></div>
            <div class="currentTime">3:30 PM</div>
            <div class="profile">
                <div class="username"><%= session.getAttribute("empName") %></div>
                <a class="exit" href="LoginController?logout">Logout</a>
            </div>
        </header>
        
        <div class="body <%= (tableNo==0)?"quicksale":"" %>">
             <aside class="main">
                 <div class="upper">
                     <div class="left">
                         <div class="sideheader">Seats</div>
                         <div class="seats">
                             <%= helper.loadSeats(table, seatNo) %>
                         </div>
                         <div class="seatOptions">
                             <a href="OrderController?addSeat" class="seat add"></a>
                             <a href="#" class="seat scrollUp">&uarr;</a>
                             <a href="#" class="seat scrollDown">&darr;</a>
                         </div>
                     </div>
                     <div class="right">
                         <div class="sideheader"><%= (tableNo==0)?"Quick Sale":"Table #"+tableNo %><a class='back' href="TableController?pTable">Tables</a></div>
                         <%= helper.getItemsForSeat(seatNo, table) %>
                     </div>
                 </div>
                 <div class="lower">
                     <div class="seatTotal total">
                         Seat #<%= seatNo %> Total: <span><%= helper.getSeatPrice(seatNo, table) %></span>
                     </div>
                     <div class="tableTotal total">
                         Table Total: <span><%= helper.getTablePrice(table) %></span>
                     </div>
                     <div class="options flex">
                         <a class="receipt" href="#">Receipt</a>
                         <a class="pay" href="#">Pay</a>
                         <a href="OrderController?close">Close</a>
                     </div>
                 </div>
             </aside>
             <div class="menu">
                 <div class="tabs">
                     <a href="Menu.jsp?view=0" class="<%= (selection.equals("0"))?"current":"" %>">Mains</a>
                     <a href="Menu.jsp?view=1" class="<%= (selection.equals("1"))?"current":"" %>">Appetizers</a>
                     <a href="Menu.jsp?view=2" class="<%= (selection.equals("2"))?"current":"" %>">Desserts</a>
                     <a href="Menu.jsp?view=3" class="<%= (selection.equals("3"))?"current":"" %>">Sides</a>
                     <a href="Menu.jsp?view=4" class="<%= (selection.equals("4"))?"current":"" %>">Other</a>
                     <a href="Menu.jsp?view=5" class="<%= (selection.equals("5"))?"current":"" %>">Drinks</a>
                 </div>
                 <div class="menuItems">
                        <%= helper.getMenuSelection(selection) %>
                 </div>
                 <div class="mainButtons ">
                     <a href="ReceiptController?sentFrom=Menu&sendToPrinter">Send Order</a>
                     <a class='needsItem comment linkToComment' href="#">Add Comment</a>
                     <a class='needsItem linkToVoidItem' href="#">Void Item</a>
                     <a class='needsItem linkToShowOptions' href="#">Options</a>
                 </div>
                 <div class="buttonsPopup">
                     <a class='linkToModifyOptions' href="#">Item Options</a>
                     <a class='linkToDiscount' href="#">Discount</a>
                     <a class='linkToSplitItem' href="#">Split Item</a> 
                     <a class='linkToMoveItem' href="#">Move Item</a>
                     <a class='close'></a>
                 </div>
             </div>
        </div>
        <div class="popup pay">
            <div class="slide slide1">
               <h3>Enter amount paid</h3>
               <form method="post" action="OrderController">
                   <div class="lock amountPaid">
                       <input name="amountPaid" class="amountPaid" value="">
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
                           <div class="submit main" data-text="."></div>
                       </div>
                   </div>
                   <div class="submitFlex">
                        <button class="submit" name="cash">Cash</button>
                        <button class="submit" name="debit">Debit</button>
                        <button class="submit" name="credit">Credit</button>
                   </div>
            </form>
            </div>
            <a href="#" class="close"></a>
        </div>
        <div class="popup receipt">
            <div class="slide slide1">
                <h1>How are they paying?</h1>
                <div class="flex">
                    <a href="ReceiptController?receipt=Together&sentFrom=menu" class="button">Together</a>
                    <a href="ReceiptController?receipt=Seperate&sentFrom=menu" class="button">Separate</a>
                    <div class="groupBy button">Groups</div>
                </div>
            </div>
            <div class="slide hidden slide2">
                <h1>Group Seats</h1>
                <form class="groupByForm" action="ReceiptController?sentFrom=menu" method="post">
                <div class="flex grid">
                    <%= helper.loadSeats(table) %>
                </div>
                    <input type="text" value="" name="selectedSeats" class="selectedSeats">
                <button name="groupBy" class="print">Print</button>
                </form>
            </div>
            <a href="#" class="close"></a>
        </div>

        <div class="popup moveItem">
            <div class="slide slide1">
                <h1>Select Seat to Move to:</h1>
                <form class="moveItemForm" action="OrderController" method="post">
                    <div class="flex grid">
                        <%= helper.loadSeats(table) %>
                    </div>
                    <input type="hidden" value="" name="itemOnOrder" class="itemOnOrder">
                    <input type="hidden" value="" name="newSeat" class="newSeat">
                    <button name="moveItem" class="submit">Move Item</button>
                </form>
            </div>
            <a href="#" class="close"></a>
        </div>      
                    
        <div class="popup splitItem">
            <div class="slide slide1">
                <h1>Select seats to split item:</h1>
                <form class="splitItemForm groupByForm" action="OrderController" method="post">
                    <div class="flex grid">
                        <%= helper.loadSeats(table, seatNo, true) %>
                    </div>
                    <input type="hidden" value="" name="itemOnOrder" class="itemOnOrder">
                    <input type="hidden" value="" name="selectedSeats" class="selectedSeats">
                    <button name="splitItem" class="submit">Split Item</button>
                </form>
            </div>
            <a href="#" class="close"></a>
        </div>   
          
        <div class="popup voidItem">
            <div class="slide slide1">
                <h3>Enter Administrator PIN:</h3>
                <form class="voidItemForm" action="OrderController" method="post">
                    <div class="lock">
                    <input name="adminPin" data-maxlen="4" class="currCombination" value="">
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
                    <input type="hidden" value="" name="itemNo" class="itemOnOrder">
            
                    <button name="void" class="submit">Void Item</button>
                </form>
            </div>
            <a href="#" class="close"></a>
        </div>       
                    
        <div class="popup discountItem">
            <form class="discountItemForm" action="OrderController" method="post">
                <input type="hidden" name="itemOnOrder" class="itemOnOrder" value="">
                <div class="slide slide1">
                        <h1>Discounted Pricing</h1>
                        <div class="lock discountPercent">
                            <input name="discountPercent" type="number" min="0" data-maxlen="3" max="100" class="currPercentage" value="">
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
                        <a class="button discountVerify">Verify</a>
                </div>
                <div class="slide hidden slide2">
                    <h3>Administrator PIN Required</h3>
                     <div class="lock">
                                <input name="adminPin" data-maxlen="4" class="currCombination" value="">
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
                    <button class="discountSubmit">Discount</button>
                </div>
            </form>
            <a href="#" class="close"></a>
        </div>
        
        <%= (request.getParameter("displayOptions") != null)?helper.getItemOptions(request.getParameter("displayOptions")):"" %>
        <%= (request.getParameter("modifyOptions") != null)?helper.getTableItemOptions(request.getParameter("modifyOptions"), seatNo, table):"" %>
        <%= (request.getParameter("displayComments") != null)?helper.displayComments(request.getParameter("displayComments"), seatNo, table):"" %>
        
        </body>
</html>
