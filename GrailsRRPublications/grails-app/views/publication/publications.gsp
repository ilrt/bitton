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

    <link rel='stylesheet' href='${createLinkTo(dir:'css',file:'publications.css')}' />
  </head>
  <body>

    <script type="text/javascript">
        var maxPageSize = 5;
        var currentPage = 0;

        var results = new Array();

        <% results.each { result ->  %>
        var o = new Object();
        o.year = ${result['date']};
        o.label = "${result['label']}";
        o.citation = "${result['citation']}";
        results[results.length] = o;
        <%}%>

        results.sort();

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

          $("#selector .body").empty();

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

          if (matchingResults.length > 0)
          {
            matchingResults = matchingResults.sort(sortResults);

            var start = page *maxPageSize;
            var end = (start + maxPageSize) > matchingResults.length ? matchingResults.length : (start + maxPageSize);

            // now display the requested page
            for (var i = start; i < end; i++)
            {
                var record = "<p>";
                record += "<span class='title' property='dc:title'>"+matchingResults[i].label+"</span>"+ " ";
                record += "<span class='year' property='dc:date'>("+matchingResults[i].year+")</span>" + " ";
                record += "<span class='citation' property='dcterms:bibliographicCitation'>"+matchingResults[i].citation+"</span>";
                record += "</p>";
                $("#selector .body").append(record);
            }

            $("#selector .controls .prev").show();
            $("#selector .controls .next").show();
            if (page < 1) $("#selector .controls .prev").hide();
            if (start + maxPageSize >= matchingResults.length) $("#selector .controls .next").hide();
            $("#selector .resultstotal").html("Showing "+(start+1)+"-"+end+" of "+matchingResults.length+" ("+results.length+" total)");
          }
          else
          {
            // no results
            $("#selector .resultstotal").html("Showing 0 of 0 ("+results.length+" total)");
            $("#selector .body").append("No results");
          }
        }

        function sortResults(a, b)
        {
          return a.label >= b.label;
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
      <div class="body"
           xmlns:dc="http://purl.org/dc/elements/1.1/"
           xmlns:dcterms="http://purl.org/dc/terms/"
           about="http://www.example.com/books/wikinomics">
           <!--  
             <p>
               <span class='title' property='dc:title'>[title]</span>
               <span class='year' property='dc:date'>([year])</span>
               <span class='citation' property='dcterms:bibliographicCitation'>[citation]</span>
             </p>
           -->
      </div>
      
      <div class="controls">
        <span class="prev">Previous</span>
        <span class="resultstotal"></span>
        <span class="next">Next</span>
      </div>
    </div>

</div>

</body></html>