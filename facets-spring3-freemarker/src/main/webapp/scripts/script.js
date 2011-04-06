/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var slider_uID = 1;

$(document).ready(function(){ 
	initLoadingIndicator();
	
	applyTabs();

	renderImpacts();

	shrink($('#pubtype-facet ul'),5);
	shrink($('#pubyear-facet ul'),5);
	shrink($('#home-publist'),5);

	$('#people-complete').
		autocomplete('http://resrev.ilrt.bris.ac.uk/Completor/resources/complete/a/person').
		result(function(event, data, formatted) {
				if (data) $('#people-complete-target').val(data[1]);
				else $('#people-complete-target').find('input.target').val('');
		});
	$('#dept-complete').
		autocomplete('http://resrev.ilrt.bris.ac.uk/Completor/resources/complete/a/org').
		result(function(event, data, formatted) {
				if (data) $('#dept-complete-target').val(data[1]);
				else $('#dept-complete-target').find('input.target').val('');
		});

	//Put in placeholders for browsers that don't do it natively:
	$( "input, textarea" ).placeholder();

	// convert select lists to autocomplete
	$('select.autocomplete').select_autocomplete();
	
	// catch case where select is converted to autoselect and no department has been selected
	$('#pdept').parent().submit(function(){
		if ($('#pdept').val() == '')
			{
				alert('No department selected');
				return false; 
			}
	});
	
	hideDepartments();

//	var link = "[<span>+</span>]&nbsp;";
//	if ($(".collapsible .content:hidden").length > 0)
//	{
//		$(".collapsible .heading").prepend("[<span class='indicator'>+</span>]&nbsp;");
//	}
//	else
//	{
//		$(".collapsible .heading").prepend("[<span class='indicator'>-</span>]&nbsp;");
//	}
//	$(".collapsible .heading").click(function(){
//			var indicatorText = $(".collapsible .indicator").text();
//			indicatorText = indicatorText == '+' ? '-' : '+';
//			$(".collapsible .indicator").text(indicatorText);
//			$(this).parent().children(".content").slideToggle().removeClass("collapsed");
//	});

	// code to automatically pull in completor snippet and initalise it
//	$(".autoselect").each(function(){
//		var title = $(this).attr("title");
//		var submit = $(this).attr("submit");
//		var resource = $(this).attr("resource");
//		var url = COMPLETOR_URL + '?title='+title + '&submit=' + submit + '&resource=' + resource;
//		$(this).load(encodeURI(url), function(response, status, xhr) {
//			if (status != 'success' || xhr.status == '404')
//			{
//				handleLoadError(this);
//			}
//			else
//			{
//				try
//				{
//					// call any initalisation functions
//					init();
//				}
//				catch (err)
//				{
//					handleLoadError(this);
//				}
//			}
//		});
//	});
}); // END $(document).ready(function(){...});

function shrink(list,n) {
	/* Takes a list and hides elements after the nth.
	 * Inserts 'show more' link with count
	 * Sets up toggle to show/hide
	 */

	/* Get the bit at the end we want to hide: */
	var surplus = list.find('li:gt(' + (n-1) + ')');
	/* Hide it: */
	surplus.hide();

	/* If we've hidden anything, add a link to show/hide it */
	if(surplus.length>0) {
		list.append('<li><a class="more" href="#">' + surplus.length + ' more</a></li>');
		$('a.more',list).click(function() {

			var link = $(this);

			surplus.slideToggle();

			if(link.hasClass('more')) {
				link.text(surplus.length + ' less');
				link.removeClass('more').addClass('less');
			}
			else {
				link.text(surplus.length + ' more');
				link.removeClass('less').addClass('more');
			}

			link.blur();
			return false;
		});
	}
}

function handleLoadError(obj)
{
	$(obj).addClass("loadError").html("Error");//.delay("slow").fadeOut("slow");
}

function hideDepartments()
{
	if ($(".alldept .selected").length > 0)
	{
		$(".popdept").hide();
	}
	else if ($(".popdept .selected").length > 0)
   {
	   var text = $("h3.facet-title:contains(popular)").parent().html();
		$(".alldept form").hide().append(text);
		$(".popdept  h3").hide();
   }
}

function applyTabs()
{
	// hide all content
	$(".tabbedcontent > div:gt(0)").hide();
	
	// enable action on each tab header
	$("ul.tabs li a").click(function(){
		$(this).parent().addClass("current").siblings(".current").removeClass("current");
		var location = $(this).attr("href");
		$(location).parent(".tabbedcontent").children().hide();
		$(location).show(0, function(){ $(this).find(".fig").trigger("redraw")});
		return false;
	})
}

function renderImpacts()
{

	$('.show_impacts').unbind('click').click(function() {
		$(this).toggleClass('open').blur();

		/* Switch the wording: */
		$stem = $(this).text().substr(5);
		if($(this).hasClass('open')) {
			$(this).text('hide ' + $stem);
		} else {
			$(this).text('show ' + $stem);
		}

		$(this).next().toggleClass('hide');
		
		return false;
	});
}

function initLoadingIndicator()
{
	$('#loading').hide();
	
	window.onbeforeunload = function()  {
		// add loading indicator after 2 second delay
		$('#loading').delay(2000).show(0);

		// allow user to cancel popup window
		$(document).keyup(function(e)
		{
			if (e.keyCode == 27) { $('#loading').hide(); }   // esc
		});
	};
}