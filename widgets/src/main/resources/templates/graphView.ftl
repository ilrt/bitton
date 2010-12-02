<html><body>
<script type="text/javascript" src="${contextPath}/scripts/protovis/protovis-r3.2.js"></script>
<script type="text/javascript">
    var years = new Array();

    <#list results as result>
        years['${result['year']}'] = ${result['count']};
    </#list>

    var data = new Array();
    var labels = new Array();
    var min = ${results[0]['year']};
    var max = ${results[results?size-1]['year']};
    var items = max - min;
    for (i=min; i<max; i++)
    {
        labels[labels.length] = i;
        data[data.length] = years[i] ? years[i] : 0;
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
            x = pv.Scale.linear(0, data.length).range(0, w),
            y = pv.Scale.linear(0, pv.max(data)).range(0, h);

            /* The root panel. */
            var vis = new pv.Panel()
                .width(w)
                .height(h)
                .bottom(0)
                .left(0)
                .right(0)
                .top(0);
//                .def("i", -1); // define local var i used for mouseover colouring

            /* X-axis and ticks. */
            vis.add(pv.Rule)
                .data(x.ticks())
                .visible(function(d) d)
                .left(x)
                .bottom(-5)
                .height(5)
                .anchor("bottom").add(pv.Label)
                .text(x.tickFormat);


            /* The bars. */
            var bar = vis.add(pv.Bar)
                .data(data)
                .top(function() y(this.index))
                .height(y.range().band)
                .right(0)
                .width(x)
                .fillStyle(function() vis.i() == this.index ? "orange" : "steelblue")
                .event("mouseover", function() vis.i(this.index))
                .event("mouseout", function() vis.i(-1));

            /* The value label. */
            bar.anchor("left").add(pv.Label)
                .textStyle(function(d) (d > 0 ? "white" : "black"))
                .text(function() data[this.index])
                .textAlign(function(d) (d > 0 ? "left" : "right")); // align values less then 0 on the right

            /* The variable label.
            bar.anchor("right").add(pv.Label)
                .textMargin(5)
                .textAlign("left")
                .text(function() labels[this.index]); */
/*
                .cursor("pointer")
                .title("View facet")
                .textStyle(function() vis.i() == this.index ? "blue" : "black")
                .font(function() vis.i() == this.index ? "underline" : "none")
                .event("mouseover", function() self.status = "Go to \"http://stanford.edu\"")
                .event("mouseover", function() console.log(this))
                .event("mouseout", function() self.status = "")
                .event("click", function() self.location = "http://stanford.edu");
*/
                vis.render();
        </script>
    </div>

</div>
</body></html