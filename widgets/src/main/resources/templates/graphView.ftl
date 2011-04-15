<html><body>
<script type="text/javascript" src="${contextPath}/scripts/protovis/protovis-r3.2.js"></script>
<script type="text/javascript">
    var dataHash = new Array();

    var types = new Array();
    <#list types as type>
        types[types.length] = '${type}';
    </#list>

    for (type in types) { dataHash[types[type]] = new Array(); }

    <#list results as result>
        var o = new Object();
        o.year = ${result['year']};
        o.count = ${result['count']};
        dataHash['${result['type']}'].push(o);
    </#list>

    var data = new Array();
    var minX = ${results[0]['year']};
    var maxX = ${results[results?size-1]['year']};

    var maxCount = 0;

    var items = maxX - minX;
    for (type in types)
    {
        yearForType = dataHash[types[type]];
        data[type] = new Array();

        for (i=minX; i<maxX; i++)
        {
            var o = new Object();
            o.x = i;
            var value = 0;
            for (j in yearForType) { if (yearForType[j].year == i) value = yearForType[j].count; }
            o.y = value;
            data[type][data[type].length] = o;
        }
    }

    // calculate year totals
    for (i=minX; i<maxX; i++)
    {
        var yearCount = 0;

        for (type in types)
        {
            yearForType = dataHash[types[type]];
            for (j in yearForType) { if (yearForType[j].year == i) yearCount += yearForType[j].count; }
        }

        if (yearCount > maxCount) maxCount = yearCount;
    }

    // work out the colours
    var index = 0;
    var colours = new Array();
    for (type in types)
    {
         colours[type] =  pv.ramp("#aad", "#556").by(Math.random);
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

#legend p
{
    margin:1px;
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
                .top(20);

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

            /* The line.
            var line = vis.add(pv.Line)
                .data(data)
                .left(function(d) x(d.x))
                .bottom(function(d) y(d.y))
                .lineWidth(3);
*/

            /* The stack layout. */
            vis.add(pv.Layout.Stack)
                .layers(data)
                .x(function(d) x(d.x))
                .y(function(d) y(d.y))
                .layer.add(pv.Area)
                .fillStyle(colours[index++]);

            /* The floating dot */
            var i = -1;
            var dot = line.add(pv.Dot)
                .visible(function() i >= 0)
                .data(function() [data[i]])
                .fillStyle(function() line.strokeStyle())
                .strokeStyle("#000")
                .size(20)
                .lineWidth(1)
                .anchor(function(){return data[i].y > data[i-1].y ? "left" : "right"}).add(pv.Label)
                .textShadow("0.1em 0.1em 0.1em #4B1")
                .font("bold 11px")
                .textAngle("0.785398163")
                .text(function(d) d.y);

            /*Lastly, we need to specify event handlers to wire everything up.
            Ideally, we could add those directly to the root panel and be done with it,
            but there are some minor flickering issues caused by child elements.
            So for now, we use an invisible bar to capture the events flicker-free. */
            vis.add(pv.Bar)
                .fillStyle("rgba(0,0,0,.001)")
                .event("mouseout", function() {
                    i = -1;
                    return vis;
                  })
                .event("mousemove", function() {
                    var mx = x.invert(vis.mouse().x);
                    i = pv.search(data.map(function(d) d.x), mx);
                    i = i < 0 ? (-i - 2) : i;
                    return vis;
                  });


            vis.render();
            var div = document.getElementById("legend");

            for (type in types)
            {
                var colour = colours[type]().color;
                var pubType = types[type];
                div.innerHTML += "<p style=\'color:"+ colour +";\'>"+pubType.substring(pubType.lastIndexOf('/')+1)+"</p>";
            }
        </script>
    </div>

    <div id="legend"><p><u>Legend:</u></p></div>

</div>
</body></html>