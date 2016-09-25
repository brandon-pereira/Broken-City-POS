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

$(function() {
                  
    $('.lock:not(.amountPaid) .grid div').on('click',function(){console.log("here");$(this).addClass('active');idAdd($(this).data('text'));setTimeout(function(){$('.grid div').removeClass('active')}, 100);});
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