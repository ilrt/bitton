// sort functions
function sortResultsOnLabelAsc(a, b)
{
  return a.label >= b.label;
}

function sortResultsOnLabelDesc(a, b)
{
  return !sortResultsOnLabelAsc(a, b);
}

function sortResultsOnDateThenLabelAsc(a, b)
{
	if (a.year == b.year) return a.label >= b.label;
	else return a.year >= b.year;
}

function sortResultsOnDateThenLabelDesc(a, b)
{
  return !sortResultsOnDateThenLabelAsc(a, b);
}

function initGraphData(id)
{
	graphData[id].data = new Array();
	graphData[id].labels = new Array();
	graphData[id].currentSortOrder = null;
	graphData[id].results = new Array();
    graphData[id].maxPageSize = 10;
    graphData[id].currentPage = 0;
                        
	var years = new Array();
	
	$("#data_"+id).children().each(function() {
		var year = $(this).attr("date");
		
		if (typeof year != "undefined")
		{
			year = parseInt(year);
			var o = new Object();
			o.year = year;
			o.label = $(this).outerHTML();
			o.searchable = $(this).text().toLowerCase();
			graphData[id].results[graphData[id].results.length] = o;
			years[years.length] = year;
		}
	});

	// sort list
	years.sort();

	graphData[id].minYear = years[0];
	graphData[id].maxYear = years[years.length-1];
	
	if (years.length == 0) 
	{
		graphData[id].minYear = graphData[id].maxYear = 0;
		return;
	}

	for (i=graphData[id].minYear; i<=graphData[id].maxYear; i++)
	{
		var count = 0;
		for (j in years) {if (years[j] == i) count++;}
		graphData[id].data[graphData[id].data.length] = count;
		graphData[id].labels[graphData[id].labels.length] = i;
	}
	
	// force lower data range to be 0
	graphData[id].data[graphData[id].data.length] = 0;

    // apply sort options to all lists
    var sortOptions = new Array();
    var obj = new Object();
    obj = new Object();
    obj.title = "Newest first";
    obj.fun = sortResultsOnDateThenLabelDesc;
    sortOptions['sortResultsOnDateThenLabelDesc'] = obj;
    obj = new Object();
    obj.title = "Oldest first";
    obj.fun = sortResultsOnDateThenLabelAsc;
    sortOptions['sortResultsOnDateThenLabelAsc'] = obj;
    obj.title = "Title (A-Z)";
    obj.fun = sortResultsOnLabelAsc;
    sortOptions['sortResultsOnLabelAsc'] = obj;
    obj = new Object();
    obj.title = "Title (Z-A)";
    obj.fun = sortResultsOnLabelDesc;
    sortOptions['sortResultsOnLabelDesc'] = obj;
                            
	// init the sort list
	for (var i in sortOptions)
	{
		var obj = sortOptions[i];
		if (graphData[id].currentSortOrder == null) graphData[id].currentSortOrder = obj.fun;
		$("#combiGraphAndList_"+id+" .ordering").append("<option value='" + i +"'>"+obj.title+"</option>");
	}
	$("#combiGraphAndList_"+id+" .ordering").change(function(){
		graphData[id].currentSortOrder = sortOptions[this.value].fun;
		showResults(id, graphData[id].currentPage);
	});
	
	years = null; // freeup
		
	$("#combiGraphAndList_"+id+" .resultfilter").keyup(function()
	{
		showResults(id, 0, this.value);
	});
}

function initGraph(id)
{
	/* Sizing and scales. */
	graphData[id].w = $('#combiGraphAndList_'+id+' .fig').width();
	graphData[id].h = $('#combiGraphAndList_'+id+' .fig').height();
	graphData[id].x = pv.Scale.ordinal(graphData[id].labels).splitBanded(0, graphData[id].w);
	graphData[id].y = pv.Scale.linear(graphData[id].data).range(0, graphData[id].h);

	/* The root panel. */
	graphData[id].vis.width(graphData[id].w)
		.height(graphData[id].h)
		.bottom(5)
		.left(0)
		.right(0)
		.top(5);

	/* The bars. */
	graphData[id].bar = graphData[id].vis.add(pv.Bar)
		.data(graphData[id].data)
		.height(function(d) {return graphData[id].y(d)} )
		.width(function() {return (graphData[id].vis.width()/graphData[id].labels.length-1)} )
		.left(function(d) {return graphData[id].x(this.index)} )
		.fillStyle("#bfbfbf")
		.bottom(0);

	$('#combiGraphAndList_'+id+' .fig').bind("redraw",function() {
	  graphData[id].w = $('#combiGraphAndList_'+id+' .fig').width();
	  graphData[id].h = $('#combiGraphAndList_'+id+' .fig').height();
	  graphData[id].x = pv.Scale.ordinal(graphData[id].labels).splitBanded(0, graphData[id].w);
	  graphData[id].y = pv.Scale.linear(graphData[id].data).range(0, graphData[id].h);
	  graphData[id].vis.width(graphData[id].w).height(graphData[id].h);
	  graphData[id].vis.render();
	});
}

function initEvents(id)
{
	$("#combiGraphAndList_"+id+" .prev").click(function(){
		showResults(id, graphData[id].currentPage-1);
	});
	$("#combiGraphAndList_"+id+" .next").click(function(){
	  showResults(id, graphData[id].currentPage+1);
	});

	$(window).resize(function() {
	  $("#combiGraphAndList_"+id+" .fig").trigger("redraw");
	});

	$( "#combiGraphAndList_"+id+" .slider-container .startYear" ).val( graphData[id].minYear ).change(function()
	{
	  var val = this.value;
	  if (isNaN(parseInt(val)) || val < graphData[id].minYear) val = graphData[id].minYear;
	  else if (val > graphData[id].maxYear+1) val = graphData[id].maxYear+1;
	  this.value = val;
	  $( "#combiGraphAndList_"+id+" .slider-container .slider-range" ).slider("values", 0, val);
	  showResults(id);
	});
	$( "#combiGraphAndList_"+id+" .slider-container .endYear" ).val( graphData[id].maxYear+1 ).change(function()
	{
	  var val = this.value;
	  if (isNaN(parseInt(val)) || val > graphData[id].maxYear+1) val = graphData[id].maxYear+1;
	  else if (val < graphData[id].minYear) val = graphData[id].minYear;
	  this.value = val;
	  $( "#combiGraphAndList_"+id+" .slider-container .slider-range" ).slider("values", 1, val);
	  showResults(id);
	});
	$( "#combiGraphAndList_"+id+" .slider-container .slider-range" ).slider({
			range: true,
			min: graphData[id].minYear,
			max: graphData[id].maxYear+1,
			values: [ graphData[id].minYear, graphData[id].maxYear+1 ],
			slide: function( event, ui ) {
					$( "#combiGraphAndList_"+id+" .slider-container .startYear" ).val( ui.values[ 0 ] );
					$( "#combiGraphAndList_"+id+" .slider-container .endYear" ).val( ui.values[ 1 ] );
					showResults(id);
			}
	});
}

function showResults(id, page)
{
  var maxPageSize = graphData[id].maxPageSize;
  
  if (page == null)
  {
	  page = 0;
  }
  
  searchtext = $("#combiGraphAndList_"+id+" .resultfilter").val();
  
  graphData[id].currentPage = page;

  $("#combiGraphAndList_"+id+" .body").empty();

  var min = $("#combiGraphAndList_"+id+" .slider-container .startYear").val();
  var max = $("#combiGraphAndList_"+id+" .slider-container .endYear").val();

  var matchingResults = new Array();
  for (obj in graphData[id].results)
  {
	  var year = graphData[id].results[obj].year;
	  if (min <= year && year < max)
	  {
		if (searchtext != null && graphData[id].results[obj].searchable.indexOf(searchtext) != -1)
		{
			matchingResults[matchingResults.length] = graphData[id].results[obj];
		}
		else if (searchtext == null)
		{
			matchingResults[matchingResults.length] = graphData[id].results[obj];
		}
	  }
  }

  if (matchingResults.length > 0)
  {
	matchingResults = matchingResults.sort(graphData[id].currentSortOrder);

	var start = page * maxPageSize;
	var end = (start + maxPageSize) > matchingResults.length ? matchingResults.length : (start + maxPageSize);

	var records = "<ul class='results'>";
	
	// now display the requested page
	for (var i = start; i < end; i++)
	{
		records += matchingResults[i].label;		
	}
	records += "</ul>";
	$("#combiGraphAndList_"+id+" .body").append(records);
    
    renderImpacts();

	$("#combiGraphAndList_"+id+" .controls .button").addClass("active");
	if (page < 1) $("#combiGraphAndList_"+id+" .controls .prev").removeClass("active");
	if (start + maxPageSize >= matchingResults.length) $("#combiGraphAndList_"+id+" .controls .next").removeClass("active");
	$("#combiGraphAndList_"+id+" .resultstotal").html(
        "Showing <b>"+(start+1)+"-"+end+"</b> of <b>"+matchingResults.length+"</b> "+graphData[id].type/*+" between <b>"+min+"</b> and <b>"+max+"</b>"*/
    );

	// create Search Box
	$(".resultfilter").change(function()
	{
		showResults(id,graphData[id].currentPage);
	});
  
    showPaginationControls(id, page, Math.ceil(matchingResults.length/maxPageSize));
  }
  else
  {
	// no results
	$("#combiGraphAndList_"+id+" .resultstotal").html("Showing 0 of 0 ("+graphData[id].results.length+" total)");
	$("#combiGraphAndList_"+id+" .body").append("No results");
  }
}

function showPaginationControls(id, current, total)
{
    var window = 2;
    var start = current - window;
    start = start < 0 ? 0 : start;
    
    var end = current + window;
    end = end >= total ? total-1 : end;
    
    var totalToShow = (window * 2) + 1;
    totalToShow = totalToShow > total ? total : totalToShow;
    
    var content="";
    
    if (start > 0)
    {
        content += "<a href='' onclick='showResults("+id+", 0); return false;'>1</a>";
        content += (start > 1) ? "&nbsp;&nbsp;...&nbsp;&nbsp;" : "&nbsp;&nbsp;|&nbsp;&nbsp;";
    }
    
    for (var i = start; i <= end; i++)
    {
        if (i == current) content += (i+1);
        else content += "<a href='' onclick='showResults("+id+", "+i+"); return false;'>"+(i+1)+"</a>";
        
        if (i+1 <= end) content += "&nbsp;&nbsp;|&nbsp;&nbsp;";
    }

    if (end+1 < totalToShow) content += "&nbsp;&nbsp;|&nbsp;&nbsp;";

    for (var j = i; j < totalToShow; j++)
    {
        content += "<a href='' onclick='showResults("+id+", "+j+"); return false;'>"+(j+1)+"</a>";
        
        if (j+1 < totalToShow) content += "&nbsp;&nbsp;|&nbsp;&nbsp;";
    }

    if (end+1 < total)
    {
        content += (end+1 < total-1) ? "&nbsp;&nbsp;...&nbsp;&nbsp;" : "&nbsp;&nbsp;|&nbsp;&nbsp;";
        content += "<a href='' onclick='showResults("+id+", "+(total-1)+"); return false;'>"+total+"</a>";
    }
    $("#combiGraphAndList_"+id+" .pages").html(content);
}

$(document).ready(function() {
	 $(".combiGraphAndList").each(function()
	 {
		var id = getGraphId(this.id);
		initGraphData(id);
	 });
});

function getGraphId(s)
{
	return s.substring(s.indexOf("_")+1);
}
