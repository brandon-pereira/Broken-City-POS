<%-- 
    Document   : LoginScreen
    Created on : Jan 20, 2015, 12:30:00 PM
    Author     : 632794
--%>

<%@page import="containers.Table"%>
<%
  if(session.getAttribute("empName") == null)
  {
      response.sendRedirect("LoginScreen.jsp?error=Session expired.");
      return;
  }
  else if(session.getAttribute("table") == null)
  {
      response.sendRedirect("LoginScreen.jsp?error=Failed to access speed bar.");
      return;
  }
  Table table = (Table)session.getAttribute("table");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="MenuHelper.SpeedBarHelper" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="res/styles/main.css">
        <script type="text/javascript" src="res/jquery.min.js"></script>
        <title>BrokenCity SpeedBar</title>

        <jsp:useBean class="MenuHelper.SpeedBarHelper" id="helper" scope="page"></jsp:useBean>
        <script>
            $(function() {
                // Inactivity Timing
                var inactivityTime = function () {
                   var t;
                   window.onload = resetTimer;
                   document.onmousemove = resetTimer;
                   document.onkeypress = resetTimer;

                   function logout() {
                       location.href = 'LoginController?logout&msg=Session expired due to inactivity.';
                   }

                   function resetTimer() {
                       clearTimeout(t);
                       t = setTimeout(logout, 120000);
                   }
               };
               inactivityTime();
               
                // On left side receipt view click
               $('.section li').on('click',function(){
                   $('.section li').not(this).removeClass('current');
                   $(this).toggleClass('current');
                   if($('.section li.current').length == 1) {
                        var itemNo = $('.section li.current').data('itemno');
                        $('.popup input.itemOnOrder').attr('value', itemNo);
                        $('.mainButtons').addClass('itemSelected');
                   }
                   else
                       $('.mainButtons').removeClass('itemSelected');
               });
               
               // Handling Popups
               $('.popup a.next').on('click',function(){
                    var popup = $(this).closest('.popup');
                    popup.find('.slide').addClass('hidden');
                    popup.find('.slide2').removeClass('hidden');
                    popup.find('.slide2 input').attr('value','');
               });
               $('a.close').on('click',function(){$(this).parent().removeClass('visible');});
               $('a.linkToVoidItem').on('click',function(){$('.popup.voidItem').addClass('visible');});
               $('a.linkToDiscount').on('click',function(){$('.popup.discountItem').addClass('visible');});
               $('a.linkToPay').on('click',function(){$('.popup.popupToPay').addClass('visible');});
               // Discount Percent Form
               $('.lock.discountPercent .grid div').on('click',function(){
                    if($(this).data('text') == 'Clear')
                    {
                        $('.discountPercent input').attr('value', '');
                        return;
                    }
                    if($(this).data('text') == 'Backspace')
                    {
                        var curr =  $('.discountPercent input').val();
                        curr =  curr.substr(0, curr.length-1); 
                        $('.discountPercent input').attr('value', curr);
                        return;
                    }
                    var curr = $('.discountPercent input').attr('value');
                    if(curr.length != 2)
                        $('.discountPercent input').attr('value', curr+$(this).data('text'));
                    $('.lock:not(.discountPercent) input').attr('value','');
                });
                
                // Administrator Pin Form(s)
                var disableMainButtons = function(){$('.buttons button').addClass('disabled');}
                var enableMainButtons = function(){$('.buttons button').removeClass('disabled');}
                var disablePad = function(){$('.grid div:not(.main)').addClass('disabled');}
                var enablePad = function(){$('.grid div:not(.main)').removeClass('disabled');}
                var changeStatus = function(len){
                    if(len == 4) {enableMainButtons();disablePad();}
                    else {disableMainButtons();enablePad();}
                }
                var idAdd = function(toAdd){
                    if(toAdd == "Backspace")
                    {
                        var curr = $('input.currCombination').val();
                        curr =  curr.substr(0, curr.length-1); 
                        $('input.currCombination').attr('value',curr);
                    }
                    else if(toAdd == "Clear")
                        $('input.currCombination').attr('value','');
                    else
                        $('input.currCombination').attr('value',$('input.currCombination').val() + toAdd);
                    $('input.currCombination').change();
                }
                var amountAdd = function(toAdd){
                    console.log("here" + toAdd)
                    if(toAdd == "Clear")
                        $('input.amountPaid').attr('value','');
                    else
                        $('input.amountPaid').attr('value',$('input.amountPaid').val() + toAdd);
                    $('input.amountPaid').change();
                }
                $('.lock:not(.amountPaid) .grid div').on('click',function(){$(this).addClass('active');idAdd($(this).data('text'));setTimeout(function(){$('.grid div').removeClass('active')}, 100);});
                $('.lock.amountPaid .grid div').on('click',function(){$(this).addClass('active');amountAdd($(this).data('text'));setTimeout(function(){$('.grid div').removeClass('active')}, 100);});
                $('input.currCombination').on('change',function(){
                        changeStatus($(this).val().length);
                });
            });
            
            
        </script>
    </head>
    <body class="speedBar">
             <%= (request.getParameter("error")!=null)?"<div class='msg'>"+request.getParameter("error")+"</div>":"" %>
             <%= (request.getParameter("warning")!=null)?"<div class='msg warning'>"+request.getParameter("warning")+"</div>":"" %>
             <%= (request.getParameter("success")!=null)?"<div class='msg success'>"+request.getParameter("success")+"</div>":"" %>

        <header class="main">
            <div class="logo"></div>
            <div class="currentTime"></div>
            <div class="profile">
                <div class="username"><%= session.getAttribute("empName") %></div>
                <a class="exit" href="LoginController?logout">Logout</a>
            </div>
        </header>
        
        <div class="body">
             <aside class="main">
                <div class="upper">
                    <ul class="section">
                        <%= helper.getReceipt(table) %>
                    </ul>
                </div>
             
                <div class="lower">
                    <div class="tableTotal total">
                        Order Total: <span><%= helper.getTotal(table) %></span>
                    </div>
                </div>
             </aside>
             <div class="menu">
                 <div class="menuItems">
                     <%= helper.getAllItems() %>
                 </div>
                 <div class="mainButtons ">
                     <a href="ReceiptController?receipt=together&sentFrom=bar">Receipt</a>
                     <a class="linkToPay" href="#">Pay</a>
                     <a class='needsItem linkToVoidItem' href="#">Void</a>
                     <a class='needsItem linkToDiscount' href="#">Discount</a>
                     <a href="BarController?close">Close</a>
                 </div>
             </div>
        </div>
        
        <div class="popup discountItem">
            <form class="discountItemForm" action="BarController" method="get">
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
                        <a class="button next discountVerify">Verify</a>
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
                 
            <div class="popup voidItem">
                <div class="slide slide1">
                    <h3>Enter Administrator PIN:</h3>
                    <form class="voidItemForm" action="BarController" method="post">
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
                 
                 <div class="popup popupToPay">
                     <div class="slide slide1">
                         <h1>Amount Paid</h1>
                         <div class="flex grid">
                             <a href="BarController?amountPaid=5">$5</a>
                             <a href="BarController?amountPaid=10">$10</a>
                             <a href="BarController?amountPaid=20">$20</a>
                             <a href="BarController?amountPaid=40">$40</a>
                             <a href="BarController?amountPaid=50">$50</a>
                             <a href="BarController?amountPaid=100">$100</a>
                         </div>
                        <a class="button next">Custom...</a>
                     </div>
                     
                     <div class="slide hidden slide2">
                        <h3>Enter a custom amount:</h3>
                        <form method="post" action="BarController">
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
                        <button class="submit">Charge</button>
                     </form>
                     </div>
                     <a href="#" class="close"></a>
                 </div>
                 
        </body>
</html>
