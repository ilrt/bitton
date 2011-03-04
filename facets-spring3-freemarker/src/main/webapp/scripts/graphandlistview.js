var minYear;
var maxYear;
var data = new Array();
var labels = new Array();
var currentSortOrder = null;

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

var sortOptions = new Array();
var obj = new Object();
obj.title = "Title (asc)";
obj.fun = sortResultsOnLabelAsc;
sortOptions['sortResultsOnLabelAsc'] = obj;
obj = new Object();
obj.title = "Title (desc)";
obj.fun = sortResultsOnLabelDesc;
sortOptions['sortResultsOnLabelDesc'] = obj;
obj = new Object();
obj.title = "Date (asc)";
obj.fun = sortResultsOnDateThenLabelAsc;
sortOptions['sortResultsOnDateThenLabelAsc'] = obj;
obj = new Object();
obj.title = "Date (desc)";
obj.fun = sortResultsOnDateThenLabelDesc;
sortOptions['sortResultsOnDateThenLabelDesc'] = obj;

function initArray()
{
    // group by year
    var years = new Array();
    for (obj in results)
    {
        years[years.length] = results[obj].year;
    }

    // sort list
    years.sort();

    minYear = years[0];
    maxYear = years[years.length-1];

    for (i=minYear; i<=maxYear; i++)
    {
        var count = 0;
        for (j in years) { if (years[j] == i) count++; }
        data[data.length] = count;
        labels[labels.length] = i;
    }

    // init the sort list
    for (var i in sortOptions)
    {
        var obj = sortOptions[i];
        if (currentSortOrder == null) currentSortOrder = obj.fun;
        $(".combiGraphAndList .ordering").append("<option value='" + i +"'>"+obj.title+"</option>");
    }
    $(".combiGraphAndList .ordering").change(function(){
        currentSortOrder = sortOptions[this.value].fun;
        showResults(currentPage);
    });
}

function showResults(page)
{
  if (page == null)
  {
      page = 0;
      currentPage = 0;
  }

  $(".combiGraphAndList .body").empty();

  var min = $(".combiGraphAndList .slider-container .startYear").val();
  var max = $(".combiGraphAndList .slider-container .endYear").val();

  var matchingResults = new Array();
  for (obj in results)
  {
      var year = results[obj].year;

      if (min <= year && year < max)
      {
          matchingResults[matchingResults.length] = results[obj];
      }
  }

  if (matchingResults.length > 0)
  {
    matchingResults = matchingResults.sort(currentSortOrder);

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
        $(".combiGraphAndList .body").append(record);
    }

    $(".combiGraphAndList .controls .prev").show();
    $(".combiGraphAndList .controls .next").show();
    if (page < 1) $(".combiGraphAndList .controls .prev").hide();
    if (start + maxPageSize >= matchingResults.length) $(".combiGraphAndList .controls .next").hide();
    $(".combiGraphAndList .resultstotal").html("Showing "+(start+1)+"-"+end+" of "+matchingResults.length+" ("+results.length+" total)");
  }
  else
  {
    // no results
    $(".combiGraphAndList .resultstotal").html("Showing 0 of 0 ("+results.length+" total)");
    $(".combiGraphAndList .body").append("No results");
  }
}


$(document).ready(function() {
     if ($(".combiGraphAndList").length > 0)
     {
        initArray();
        $(".combiGraphAndList .prev").click(function(){
            showResults(--currentPage);
        });
        $(".combiGraphAndList .next").click(function(){
          showResults(++currentPage);
        });

        $(window).resize(function() {
          $('.combiGraphAndList .fig').resize();
        });

        $( ".combiGraphAndList .slider-container .startYear" ).val( minYear ).change(function()
        {
          var val = this.value;
          if (isNaN(parseInt(val)) || val < minYear) val = minYear;
          else if (val > maxYear+1) val = maxYear+1;
          this.value = val;
          $( ".combiGraphAndList .slider-container .slider-range" ).slider("values", 0, val);
          showResults();
        });
        $( ".combiGraphAndList .slider-container .endYear" ).val( maxYear+1 ).change(function()
        {
          var val = this.value;
          if (isNaN(parseInt(val)) || val > maxYear+1) val = maxYear+1;
          else if (val < minYear) val = minYear;
          this.value = val;
          $( ".combiGraphAndList .slider-container .slider-range" ).slider("values", 1, val);
          showResults();
        });
        $( ".combiGraphAndList .slider-container .slider-range" ).slider({
                range: true,
                min: minYear,
                max: maxYear+1,
                values: [ minYear, maxYear+1 ],
                slide: function( event, ui ) {
                        $( ".combiGraphAndList .slider-container .startYear" ).val( ui.values[ 0 ] );
                        $( ".combiGraphAndList .slider-container .endYear" ).val( ui.values[ 1 ] );
                        showResults();
                }
        });
        
        showResults();
     }
});