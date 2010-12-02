<html><body>
<script type="text/javascript" src="${contextPath}/scripts/protovis/protovis-r3.2.js"></script>
<script type="text/javascript">
    var dataHash = new Array();

    <#list results as result>
        dataHash['${result['year']}'] = ${result['count']};
    </#list>

    var data = new Array();
    var minX = ${results[0]['year']};
    var maxX = ${results[results?size-1]['year']};

    var items = maxX - minX;
    for (i=minX; i<maxX; i++)
    {
        var o = new Object();
        o.x = i;
        o.y = dataHash[i] ? dataHash[i] : 0;
        data[data.length] = o;
    }
</script>

<style type="text/css">
#fig
{
    width: 400px;
    height: 250px;
    position:absolute;
    top:0px;
}

#center
{
    position:relative;
}
</style>


<p>${facet}</p>

<div id="center">

    <div id="fig">
        <script type="text/javascript+protovis">
            /* Sizing and scales. */
            var w = 400,
            h = 250,
            x = pv.Scale.linear(data, function(d) d.x).range(0, w),
            y = pv.Scale.linear(data, function(d) d.y).range(0, h);

            /* The root panel. */
            var vis = new pv.Panel()
                .width(w)
                .height(h)
                .bottom(20)
                .left(40)
                .right(10)
                .top(5);

            /* X-axis ticks. */
            vis.add(pv.Rule)
                .data(x.ticks())
                .visible(function(d) d > 0)
                .left(x)
                .strokeStyle("#eee")
                .add(pv.Rule)
                .bottom(-5)
                .height(5)
                .strokeStyle("#000")
                .anchor("bottom").add(pv.Label)
                .text(function(d) d.toFixed());

            /* Y-axis ticks. */
            vis.add(pv.Rule)
                .data(y.ticks(5))
                .bottom(y)
                .strokeStyle(function(d) d ? "#eee" : "#000")
                .anchor("left").add(pv.Label)
                .text(y.tickFormat);

            /* The line. */
            vis.add(pv.Line)
                .data(data)
                .left(function(d) x(d.x))
                .bottom(function(d) y(d.y))
                .lineWidth(3);

            vis.render();
        </script>
    </div>

</div>
</body></html