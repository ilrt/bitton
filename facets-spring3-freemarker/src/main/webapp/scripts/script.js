/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){ 
	$('ul.facet-list').each(function(){ 
		var $this = $(this), list = $this.find('li:gt(4)').hide(); 
		if(list.length>0){ 
			$this.append('<li><a class="more" href="#">See more</a></li>');
			$('a.more', $this).click(function(){ 
				list.toggle(); 
				$(this).text($(this).text() === 'See more' ? 'See less' : 'See more'); 
			}); 
		} 
	}); 
}); 

var slider_uID = 1;

$(document).ready(function(){
    //Put in placeholders for browsers that don't do it natively:
    $( "input, textarea" ).placeholder();

    $('select.autocomplete').select_autocomplete();
    $('#pdept').parent().submit(function(){
        if ($('#pdept').val() == '')
            {
                alert('No department selected');
                return false; 
            }
    });
    
    hideDepartments();

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
        $(this).load(encodeURI(url), function(response, status, xhr) {
            if (status != 'success' || xhr.status == '404')
            {
                handleLoadError(this);
            }
            else
            {
                try
                {
                    // call any initalisation functions
                    init();
                }
                catch (err)
                {
                    handleLoadError(this);
                }
            }
        });
    });

    // enhance date range selectors
    if ($("h4:contains(Start Year)").parent().find("li").length > 1)
    {
        var dateFacet = $("h4:contains(Start Year)").parent();
        enableSlider(dateFacet);
    }
    if ($("h4:contains(End Year)").parent().find("li").length > 1)
    {
        var dateFacet = $("h4:contains(End Year)").parent();

        enableSlider(dateFacet);  
    }
});

function enableSlider(div)
{
        var min = $("li:first a", div).text();
        var max =$("li:last a", div).text();

	var ticks = new Array();
	for(i=min; i<= max; i++)
	{
		ticks.push(""+i);
	}
        var name = "slider_"+(slider_uID++);
	var selectHtml = "<select name='"+name+"' id='"+name+"' style='display:none'>";
	for(var i in ticks)
	{
		selectHtml += "<option value='"+ticks[i]+"'>"+ticks[i]+"</option>";
	}

	selectHtml += "</select><br/><br/><a id='"+name+"_applybtn' href='#'>Apply</a>";

	$("ul:last-child", div).after(selectHtml);

	$("#"+name+"_applybtn", div.parent()).click(function(){
            console.log($(this).parent());
                var url = $(this).parent().find(".facet-list li:last a").attr("href");
                var max = parseInt($(this).parent().find(".facet-list li:last a", div).text());
		var value = parseInt($(this).parent().find("select option:selected").val());
                url = url.replace(max, value);
                url = url.replace((max+1), (value+1));
//                alert('Value is ' + url +  value);
		window.location = url;
	});

        $("ul", div).hide().parent().find("h4").css({marginBottom: '1em'});
        $('#'+name).selectToUISlider().next();
}

function handleLoadError(obj)
{
    $(obj).addClass("loadError").html("Error");//.delay("slow").fadeOut("slow");
}

function hideDepartments()
{
    if ($("h2.facet-title:contains(Department)+p:contains(x)").length > 0)
    {
        $("h3.facet-title:contains(popular)").parent().hide();
    }
    else if ($("h3.facet-title:contains(popular)+*:contains(x)").length > 0)
   {
       var text = $("h3.facet-title:contains(popular)").parent().html();
        $("h2.facet-title:contains(Department)+form").hide().append(text);
        $("h3.facet-title:contains(popular)").hide();
   }
}