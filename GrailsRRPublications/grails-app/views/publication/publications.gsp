<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.7/themes/base/jquery-ui.css" type="text/css" media="all" />

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.5/jquery-ui.min.js"></script>

    <g:javascript src="protovis-r3.2.js" />
  </head>
  <body>
<style type="text/css">
#selector .fig
{ 
    height: 100px;
    margin-left:80px;
    margin-right:80px;
    margin-bottom:1em;
}

#selector .slider-container .slider-outer
{
    width: 100%;
    float: left;
    margin-right: -80px;
}

#selector .slider-container .slider-inner
{
    margin-right: 80px;
}

#selector .slider-container .slider-range
{
    margin-left: 80px;
}

#selector .slider-container input
{
    width: 80px;
    text-align:center;
    color:#f6931f;
    font-weight:bold;
    border:0;
    padding:0px;
    margin:0px;
}

#selector .slider-container .valueLeft
{
    float: left;
}

#selector .slider-container .valueRight
{
    float: right;
}

.clearing
{
  height: 0;
  clear: both;
}

#selector .results .controls span
{
    color:blue;
    text-decoration:underline;
    padding:0.2em;
    cursor:pointer;
    border:1px solid lightblue;
}

#selector .results span
{
    font-style:italic;
}
</style>

<script type="text/javascript">
    var maxPageSize = 5;
    var currentPage = 0;

    var results = new Array();

<% results.each { result ->  %>
    var o = new Object();
    o.year = ${result['date']};
    o.label = "${result['label']}";
    results[results.length] = o;
<%}%>

    // group by year
    var years = new Array();
    for (obj in results)
    {
        years[years.length] = results[obj].year;
    }

    // sort list
    years.sort();

    var minYear = years[0];
    var maxYear = years[years.length-1];

    var data = new Array();
    var labels = new Array();
    for (i=minYear; i<=maxYear; i++)
    {
        var count = 0;
        for (j in years) { if (years[j] == i) count++; }
        data[data.length] = count;
        labels[labels.length] = i;
    }

    function showResults(page)
    {
      if (page == null)
      {
          page = 0;
          currentPage = 0;
      }

      $(".body").empty();

      var min = $("#selector .slider-container .startYear").val();
      var max = $("#selector .slider-container .endYear").val();

      var matchingResults = new Array();
      for (obj in results)
      {
          var year = results[obj].year;

          if (min <= year && year <= max)
          {
              matchingResults[matchingResults.length] = results[obj];
          }
      }

      var start = page *maxPageSize;
      var end = (start + maxPageSize) > matchingResults.length ? matchingResults.length : (start + maxPageSize);

      // now display the requested page
      for (var i = start; i < end; i++)
      {
          var record = "<p>"+matchingResults[i].label+" <span class='year'>("+matchingResults[i].year+")</span>"+"</p>";
          $("#selector .body").append(record);
      }

      $("#selector .controls .prev").show();
      $("#selector .controls .next").show();
      if (page < 1) $("#selector .controls .prev").hide();
      if (start + maxPageSize >= matchingResults.length) $("#selector .controls .next").hide();
    }
</script>
    <div id="selector">
        <div class="fig">
            <script type="text/javascript+protovis">
                /* Sizing and scales. */
                var w = $('#selector .fig').width(),
                h = $('#selector .fig').height(),
                x = pv.Scale.ordinal(labels).splitBanded(0, w),
                y = pv.Scale.linear(data).range(0, h);

                /* The root panel. */
                var vis = new pv.Panel()
                    .width(w)
                    .height(h)
                    .bottom(5)
                    .left(0)
                    .right(0)
                    .top(5);

                /* X-axis ticks. */
                vis.add(pv.Rule)
                    .data(labels)
                    .bottom(-10)
                    .height(15)
                    .left(function(d) x(d))
                    .strokeStyle("#000");

                /* The bars. */
                var bar = vis.add(pv.Bar)
                    .data(data)
                    .height(function(d) y(d))
                    .width(function() vis.width()/labels.length)
                    .left(function(d) x(this.index))
                    .fillStyle("#AAA")
                    .bottom(0);

                $('#selector .fig').resize(function() {
                  var w = $('#selector .fig').width();
                  var h = $('#selector .fig').height();
                  vis.width(w).height(h);
                  x.domain(labels).splitBanded(0, w);
                  y.range(0, h);
                  vis.render();
                });

                $('#selector .fig').resize();
            </script>

            <script type="text/javascript">
            $(window).resize(function() {
              $('#selector .fig').resize();
            });
            $(function() {
                    $( "#selector .slider-container .startYear" ).val( minYear );
                    $( "#selector .slider-container .endYear" ).val( maxYear+1 );
                    $( "#selector .slider-container .slider-range" ).slider({
                            range: true,
                            min: minYear,
                            max: maxYear+1,
                            values: [ minYear, maxYear+1 ],
                            slide: function( event, ui ) {

                                    $( "#selector .slider-container .startYear" ).val( ui.values[ 0 ] );
                                    $( "#selector .slider-container .endYear" ).val( ui.values[ 1 ] );
                                    showResults();
                            }
                    });
            });

            $(document).ready(function() { 
                showResults();
                $("#selector .prev").click(function(){
                    showResults(--currentPage);
                });
                $("#selector .next").click(function(){
                  showResults(++currentPage);
                });
            });
            </script>
        </div><!-- END #fig -->

        <div class="slider-container">
            <div class="slider-outer">
                <div class="slider-inner">
                    <div class="valueLeft">
                        <input type="text" class="startYear" />
                    </div>
                    <div class="slider-range"></div>
                </div>
            </div>
            <div class="valueRight">
                <input type="text" class="endYear" />
            </div>
        </div>

        <div class="clearing">&nbsp;</div>

  <div class="results">
      <div class="body"></div>
      <div class="controls">
        <span class="prev">Previous</span>
        <span class="next">Next</span>
      </div>
    </div>

</div>

  <p><h2>TODO</h2>
  <ul>
    <li>Order results</li>
    <li>Show more info e.g. impacts/citation</li>
  </ul>Show number of results</p>
</body></html>