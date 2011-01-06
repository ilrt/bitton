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
    <g:javascript src="publications.js" />

    <link rel='stylesheet' href='${createLinkTo(dir:'css',file:'publications.css')}' />
  </head>
  <body>

    <script type="text/javascript">
        // global vars
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

      <select class="ordering"></select>
    </div>

</div>

</body></html>