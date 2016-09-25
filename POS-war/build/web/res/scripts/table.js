var disableMainButtons = function(){$('.actionButtons.main').addClass('disabled');}
var enableMainButtons = function(){$('.actionButtons.main').removeClass('disabled');}
var disableZero = function(){$('.grid div.zero').addClass('disabled');}
var enableZero = function(){$('.grid div.zero').removeClass('disabled');}
var disablePad = function(){$('.grid div:not(.main)').addClass('disabled');}
var enablePad = function(){$('.grid div:not(.main)').removeClass('disabled');}
var changeStatus = function(len){
    if(len == 0) {disableMainButtons();enablePad();disableZero();}
    else if(len == 1) {enableZero();enableMainButtons();enablePad();}
    if(len == 2) {disablePad();enableMainButtons();}
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
               
    $('a.merge').on('click',function(){$('.popup.mergeTables').addClass('visible');$('.popup.mergeTables .slide').addClass('hidden');$('.popup.mergeTables .slide1').removeClass('hidden')});
    $('a.close').on('click',function(){$(this).parent().removeClass('visible');});
    $('a.tableFrom').on('click',function(){
        $('a.tableFrom').removeClass('selected');
        $('a.tableTo').removeClass('hidden');
        $(this).addClass('selected');
        $('a.tableTo.t'+$(this).text()).addClass('hidden');
        $('input.tableFrom').attr('value',$(this).text());
    });
    $('a.tableTo').on('click',function(){
        $('a.tableTo').removeClass('selected');
        $(this).addClass('selected');
        $('input.tableTo').attr('value',$(this).text());
    });
    $('.popup button.next').on('click',function(){
       var popup = $(this).closest('.popup');
       popup.find('.slide').addClass('hidden');
       popup.find('.slide2').removeClass('hidden');
    });
    $('.grid div').on('click',function(){$(this).addClass('active');idAdd($(this).data('text'));setTimeout(function(){$('.grid div').removeClass('active')}, 100);});
    $('input.currCombination').on('change',function(){
            changeStatus($(this).val().length);
    });
    $('input.currCombination').change();
        var currentTime = jQuery('.currentTime');
  
    var currentTime = jQuery('.currentTime');
 
    function updateTime() {
        var now = new Date();
        var mins = (now.getMinutes()<10?'0':'') + now.getMinutes()
        if(now.getHours() < 12 && now.getHours() != 0)
            currentTime.text(now.getHours()+":"+mins+" AM"); 
        else if(now.getHours() == 0)
            currentTime.text("12:"+mins+" AM"); 
        else if(now.getHours() == 12)
            currentTime.text("12:"+mins+" PM"); 
        else
             currentTime.text(now.getHours()-12+":"+mins+" PM");
    }
    updateTime();
    setInterval(updateTime, 15000);
});