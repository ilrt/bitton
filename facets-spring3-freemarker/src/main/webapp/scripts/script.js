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

    $(".autoselect").each(function(){
        var title = $(this).attr("title");
        var submit = $(this).attr("submit");
        var resource = $(this).attr("resource");
        var url = COMPLETOR_URL + '?title='+title + '&submit=' + submit + '&resource=' + resource;
        $(this).load(encodeURI(url), function() {
            // call any initalisation functions
            init();
        });
    });

});