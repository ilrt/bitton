<html><body>
<script type="text/javascript" src="${contextPath}/scripts/protovis/protovis-r3.2.js"></script>
<script type="text/javascript">
    var data = [<#list results as result>${result['count']},</#list>];
    var labels = [<#list results as result>'${result['label']}',</#list>];
    var actions = [<#list results as result>'${result['uri']}',</#list>];
</script>

<style type="text/css">
#fig
{
    width: 400px;
    height: 250px;
    position:absolute;
    top:0px;
}

#main-content
{
    position:absolute;
    left:105px;
    top:0px;
}

#main-content ul
{
    margin:0px;
    padding-left:0px;
    list-style-type:none;
}

#main-content li
{
    vertical-align: text-bottom;
    line-height:${250/results?size}px;
    font-size:11px;
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
            var w = 100,
            h = 250,
            x = pv.Scale.linear(0, pv.max(data)).range(0, w),
            y = pv.Scale.ordinal(pv.range(data.length)).splitBanded(0, h, 4/5);

            /* The root panel. */
            var vis = new pv.Panel()
                .width(w)
                .height(h)
                .bottom(0)
                .left(0)
                .right(0)
                .top(0)
                .def("i", -1); // define local var i used for mouseover colouring

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

    <!-- top facet list -->
    <div id="main-content">
        <ul>
            <#list results as result>
                <li><a href="./${result['uri']}">${result['label']}</a></li>
            </#list>
        </ul>
    </div>
</div>
</body></html