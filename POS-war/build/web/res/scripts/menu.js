            $(function() {
                
                                
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
               
                $('a.receipt').on('click',function(){
                    $('.popup.receipt').addClass('visible');
                    $('.slide').addClass('hidden');
                    $('.slide1').removeClass('hidden');
                });
                
                $('a.pay').on('click',function(){
                    $('.popup.pay').addClass('visible');
                    $('.slide1').removeClass('hidden');
                });
                
                $('a.comment').on('click',function(){
                    $('.popup.comment').addClass('visible');
                });
                
                $('a.close').on('click',function(){
                   $(this).parent().removeClass('visible'); 
                   $('.slide').addClass('hidden');
                   $('.slide1').removeClass('hidden');
                });
                
                $('.button.groupBy').on('click',function(){
                    $('.slide').toggleClass('hidden');
                });
                
                
                
                $('a.seat.scrollDown').on('click',function(){
                    console.log('Scrolling down');
                    var y = $('.seats').scrollTop();  //your current y position on the page
                    $('.seats').scrollTop(y+60); 
                });
                
                $('a.seat.scrollUp').on('click',function(){
                    console.log('Scrolling up');
                    var y = $('.seats').scrollTop();  //your current y position on the page
                    $('.seats').scrollTop(y-60); 
                });
                
                $('form.groupByForm .flex.grid a').on('click',function(){
                   $(this).toggleClass('selected');
                   var selected = "";
                   $( "form.groupByForm .flex.grid a.selected" ).each(function() {
                        selected += $(this).text() + ";";
                   });
                   selected = selected.substring(0, selected.length - 1);
                   console.log(selected);
                   $('form.groupByForm input.selectedSeats').attr('value',selected);
                });
                
                $('.main .upper .right ul li').on('click',function(){
                   var itemNo = $(this).data('id');
                   $('.main .upper .right ul li').not(this).removeClass('current');
                   $(this).toggleClass('current'); 
                   if($('.main .upper .right ul li.current').length == 1)
                   {
                       $('.mainButtons').addClass('itemSelected');
                       $('a.linkToComment').attr('href','Menu.jsp?displayComments='+itemNo);
                       $('a.linkToModifyOptions').attr('href','Menu.jsp?modifyOptions='+itemNo);
                       
                       $('.moveItem input.itemOnOrder').attr('value',itemNo);
                       $('.splitItem input.itemOnOrder').attr('value',itemNo);
                       $('.voidItem input.itemOnOrder').attr('value',itemNo);
                       $('.discountItem input.itemOnOrder').attr('value',itemNo);
                   }
                   else $('.mainButtons').removeClass('itemSelected');
                   
                });
                
                $('.selectSides a').on('click',function(){
                   var maxSides = $(this).parent().data('maxsides');
                   var selected = "";
                   console.log(maxSides)
                   if($(this).hasClass('selected') == false)
                   {
                        if(maxSides > $('.selectSides a.selected').length)
                            $(this).addClass('selected');
                        else
                            console.log('max sides' + maxSides);
                   }
                   else
                       $(this).removeClass('selected');

                   $('.selectSides a.selected').each(function() {
                        selected += $(this).data('id') + ";";
                   });
                   selected = selected.substring(0, selected.length - 1);
                   $(this).parent().find('input').attr('value',selected);
                });
                
                $('.flex.grid.extra a').on('click',function(){
                    $(this).toggleClass('selected');
                    $(this).parent().find('a').not(this).removeClass('selected');
                    $(this).parent().find('input').attr('value',$(this).parent().find('a.selected').data('id'));
                });
                
                $('a.linkToShowOptions').on('click',function(){
                   $('.buttonsPopup').addClass('visible'); 
                });
                
                $('a.linkToMoveItem').on('click',function(){
                   $('.popup.moveItem').addClass('visible'); 
                });
                
                $('a.linkToSplitItem').on('click',function(){
                   $('.popup.splitItem').addClass('visible'); 
                });
                
                $('a.linkToVoidItem').on('click',function(){
                   $('.popup.voidItem').addClass('visible'); 
                });
                
                $('a.linkToDiscount').on('click',function(){
                   $('.popup.discountItem').addClass('visible'); 
                });
                
                $('.moveItemForm .flex.grid a').on('click',function(){
                   $('.moveItemForm .flex.grid a').removeClass('selected');
                   $(this).addClass('selected');
                   $('.moveItemForm input.newSeat').attr('value',$(this).text());
                });
                $('.discountVerify').on('click',function(){
                    $('.discountItem .slide').toggleClass('hidden');
                    $('.lock:not(.discountPercent) input').attr('value', '');
                });
                
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
                
                $('.lock.amountPaid .grid div').on('click',function(){
                    console.log("here" + $(this).data('text'));
                    if($(this).data('text') == 'Clear')
                    {
                        $('.lock input.amountPaid').attr('value', '');
                        return;
                    }
                    var curr = $('.lock input.amountPaid').attr('value');
                    $('.lock input.amountPaid').attr('value', curr+$(this).data('text'));
                });
                
                
             });