/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){

    var link = "[<span>+</span>]&nbsp;";
    if ($(".collapsible .content:hidden").length > 0)
    {
        $(".collapsible .heading").prepend("[<span class='indicator'>+</span>]&nbsp;");
    }
    else
    {
        $(".collapsible .heading").prepend("[<span class='indicator'>-</span>]&nbsp;");
    }
    $(".collapsible .heading").click(function(){

            var indicatorText = $(".collapsible .indicator").text();
            indicatorText = indicatorText == '+' ? '-' : '+';
            $(".collapsible .indicator").text(indicatorText);

            $(this).parent().children(".content").slideToggle().removeClass("collapsed");

    });


});