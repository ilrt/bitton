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

function initArray(id)
{
    graphData[id].data = new Array();
    graphData[id].labels = new Array();
    graphData[id].currentSortOrder = null;

    // group by yearcombiGraphAndList
    var years = new Array();
    for (obj in graphData[id].results)
    {
        years[years.length] = graphData[id].results[obj].year;
    }

    // sort list
    years.sort();

    graphData[id].minYear = years[0];
    graphData[id].maxYear = years[years.length-1];

    for (i=graphData[id].minYear; i<=graphData[id].maxYear; i++)
    {
        var count = 0;
        for (j in years) { if (years[j] == i) count++; }
        graphData[id].data[graphData[id].data.length] = count;
        graphData[id].labels[graphData[id].labels.length] = i;
    }

    // init the sort list
    for (var i in graphData[id].sortOptions)
    {
        var obj = graphData[id].sortOptions[i];
        if (graphData[id].currentSortOrder == null) graphData[id].currentSortOrder = obj.fun;
        $("#combiGraphAndList_"+id+" .ordering").append("<option value='" + i +"'>"+obj.title+"</option>");
    }
    $("#combiGraphAndList_"+id+" .ordering").change(function(){
        graphData[id].currentSortOrder = graphData[id].sortOptions[this.value].fun;
        showResults(id, graphData[id].currentPage);
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
          matchingResults[matchingResults.length] = graphData[id].results[obj];
      }
  }

  if (matchingResults.length > 0)
  {
    matchingResults = matchingResults.sort(graphData[id].currentSortOrder);

    var start = page * maxPageSize;
    var end = (start + maxPageSize) > matchingResults.length ? matchingResults.length : (start + maxPageSize);

    // now display the requested page
    for (var i = start; i < end; i++)
    {
        var record = "<p>";
        record += "<span class='title' property='dc:title'>"+matchingResults[i].label+"</span>"+ " ";
        record += "<span class='year' property='dc:date'>("+matchingResults[i].year+")</span>" + " ";
        record += "<span class='citation' property='dcterms:bibliographicCitation'>"+matchingResults[i].citation+"</span>";
        record += "</p>";
        $("#combiGraphAndList_"+id+" .body").append(record);
    }

    $("#combiGraphAndList_"+id+" .controls .prev").show();
    $("#combiGraphAndList_"+id+" .controls .next").show();
    if (page < 1) $("#combiGraphAndList_"+id+" .controls .prev").hide();
    if (start + maxPageSize >= matchingResults.length) $(".combiGraphAndList .controls .next").hide();
    $("#combiGraphAndList_"+id+" .resultstotal").html("Showing "+(start+1)+"-"+end+" of "+matchingResults.length+" ("+graphData[id].results.length+" total)");
  }
  else
  {
    // no results
    $("#combiGraphAndList_"+id+" .resultstotal").html("Showing 0 of 0 ("+graphData[id].results.length+" total)");
    $("#combiGraphAndList_"+id+" .body").append("No results");
  }
}


$(document).ready(function() {
     $(".combiGraphAndList").each(function()
     {
        var id = getGraphId(this.id);
        
        if (graphData[id].results.length > 0)
        {
            initArray(id);
            initEvents(id);
            showResults(id);
         }
     });
     
     $(".combiGraphAndList:eq(0) .fig").trigger("redraw");
});

function getGraphId(s)
{
    return s.substring(s.indexOf("_")+1);
}
