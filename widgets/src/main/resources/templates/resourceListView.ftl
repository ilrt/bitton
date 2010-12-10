<html><body>

<style type="text/css">
#fig, #slider-range
{
    width: 300px;
    margin-left:50px;
}

.slider
{
    width:400px;
    margin-top:0.5em;
    position:relative;
}

.slider input
{
    width: 40px;
    text-align:center;
    color:#f6931f;
    font-weight:bold;
    top:-0.3em;
    position:absolute;
    border:0;
}

#slider-range
{
    display:inline-block;
    margin-left:50px;
}

#endYear
{
    margin-left:12px;
}
</style>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.7/themes/base/jquery-ui.css" type="text/css" media="all" />

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.5/jquery-ui.min.js"></script>

<script type="text/javascript" src="${contextPath}/scripts/protovis/protovis-r3.2.js"></script>
<script type="text/javascript">
    var results = new Array();

    <#list results as result>
    var o = new Object();
    o.year = ${result['date']};
    o.label = "${result['label']}";
    results[results.length] = o;
    </#list>

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

    var yearData = new Array();
    for (i=minYear; i<=maxYear; i++)
    {
        var count = 0;
        for (j in years) { if (years[j] == i) count++; }
        yearData[yearData.length] = {x: i, y: count};
    }
</script>
    <div class="selector">
        <div id="fig">
            <script type="text/javascript+protovis">
                /* Sizing and scales. */
                var w = 300,
                h = 100,
                x = pv.Scale.linear(yearData, function(d) d.x).range(0, w),
                y = pv.Scale.linear(0, pv.max(yearData, function(d) d.y)).range(0, h);

                /* The root panel. */
                var vis = new pv.Panel()
                    .width(w)
                    .height(h)
                    .bottom(5)
                    .left(0)
                    .right(0)
                    .top(5);

                var barHeight = h/pv.max(yearData, function(d) d.y);
                var barWidth = w/yearData.length;

                /* X-axis ticks. */
                vis.add(pv.Rule)
                    .data(yearData)
                    .bottom(-10)
                    .height(15)
                    .left(function() this.index*barWidth)
                    .strokeStyle("#000");

                /* The bars. */
                var bar = vis.add(pv.Bar)
                    .data(yearData)
                    .height(function(d) d.y * barHeight)
                    .width(barWidth)
                    .left(function() this.index*barWidth)
                    .fillStyle("#AAA")
                    .bottom(0);

                vis.render();
            </script>

            <script>
            $(function() {
                    $( "#slider-range" ).slider({
                            range: true,
                            min: minYear,
                            max: maxYear,
                            values: [ minYear, maxYear ],
                            slide: function( event, ui ) {

                                    $( "#startYear" ).val( ui.values[ 0 ] );
                                    $( "#endYear" ).val( ui.values[ 1 ] );
                            }
                    });
                    $( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +
                            " - $" + $( "#slider-range" ).slider( "values", 1 ) );
            });
            </script>
        </div>

        <div class="slider">
            <input type="text" id="startYear" />
            <div id="slider-range"></div>
            <input type="text" id="endYear" />
        </div>
    </div>
</body></html>