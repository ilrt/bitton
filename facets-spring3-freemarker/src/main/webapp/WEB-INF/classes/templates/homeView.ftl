<#include "includes/macro.ftl"/>
<#include "includes/header.ftl"/>

<!-- main content -->
<div id="content">

<div class="col1of2">
<div class="panel" id="mastercategories">
	<h1>Explore</h1>

	<div class="panelcontent">

        <ul>
            <#list facetView?keys as prop>
                <li><a href="./${prop}">${facetView[prop]}</a></li>
            </#list>
        </ul>

        
        <div id="home-outputs">
            <h2>Research Outputs</h2>
            <ul>
                <#list outputSummary as output>
                    <li><a href="pubs?pubtype=U${output['type']?url('utf-8')}%23${output['label']}">${output['label']}</a> (${output['scount']})</li>
                </#list>
            </ul>
        </div>

        <div>
            <h2>People (${peopleCount})</h2>
            <form action="item" method="get">
                <input id="people-complete" type="text" >
                <input id="people-complete-target" type="hidden" name="res">
                <input type="submit" value="go">
            </form>
        </div>
        
        <div>
            <h2>Grants &amp; funding bodies</h2>
            <ul>
                <#list grantSummary as grant>
                    <li><a href="pubs?funder=U${grant['s']?url('utf-8')}%23${grant['label']}">${grant['label']}</a> (${grant['gcount']})</li>
                </#list>
            </ul>
        </div>

        <div>
            <h2>Departments &amp; groups (${deptCount})</h2>
            <form action="item" method="get">
                <input id="dept-complete" type="text" >
                <input id="dept-complete-target" type="hidden" name="res">
                <input type="submit" value="go">
            </form>
        </div>

	</div><!-- /panelcontent -->
</div><!-- /panel -->
</div><!-- /col-->

				<div class="col2of2">
					<div class="panel">
						<h2>Another heading</h2>
						<div class="panelcontent">
					<blockquote>
						<p>Paragraph inside Blockquote: Nam libero leo, elementum
						in, dapibus a, suscipit vitae, purus. Duis arcu. Integer dignissim
						fermentum enim. Morbi convallis felis vel nibh. Sed scelerisque
						sagittis lorem.</p>
						<p>Paragraph inside Blockquote: Nam libero leo,
						elementum in, dapibus a, suscipit vitae, purus. Duis arcu. Integer
						dignissim fermentum enim. Morbi convallis felis vel nibh. Sed
						scelerisque sagittis lorem.</p>

					</blockquote>

					<ul>
						<li>Unordered list 01</li>
						<li>Unordered list 02</li>
						<li>Unordered list 03
						<ul>

							<li>Unordered list inside list level 2</li>

							<li>Unordered list inside list level 2
							<ul>
								<li>Unordered list inside list level 3</li>
								<li>Unordered list inside list level 3</li>
							</ul>
							</li>

						</ul>

						</li>

					</ul>
					<ol>
						<li>Ordered list 01</li>
						<li>Ordered list 02</li>
						<li>Ordered list 03
						<ol>
							<li>Ordered list inside list level 2</li>

							<li>Ordered list inside list level 2
							<ol>

								<li>Ordered list inside list level 3</li>
								<li>Ordered list inside list level 3</li>
							</ol>
							</li>
						</ol>

						</li>

					</ol>
					<dl>
						<dt>Description list title 01</dt>

						<dd>Description list description 01</dd>
						<dt>Description list title 02</dt>

						<dd>Description list description 02</dd>
						<dd>Description list description 03</dd>

					</dl>
					<table>
						<caption>Table Caption</caption>

						<thead>

							<tr>
								<th>Table head th</th>
								<td>Table head td</td>
							</tr>

						</thead>
						<tfoot>
							<tr>

								<th>Table foot th</th>
								<td>Table foot td</td>
							</tr>
						</tfoot>

						<tbody>
							<tr>
								<th>Table body th</th>

								<td>Table body td</td>
							</tr>
							<tr>
								<td>Table body td</td>

								<td>Table body td</td>
							</tr>

						</tbody>

					</table>
					<form action="#">
						<fieldset>
							<legend>Form legend</legend>
							<div><label for="f1">Text input:</label> <input id="f1" value="input text" type="text"></div>
							<div><label for="f2">Radio input:</label> <input id="f2" name="f2" type="radio"></div>

							<div><label for="f3">Checkbox input:</label> <input id="f3" type="checkbox"></div>
							<div><label for="f4">Select field:</label> <select id="f4"><option>Option 01</option><option>Option 02</option></select></div>

							<div><label for="f5">Textarea:</label><br><textarea id="f5" cols="30" rows="5">Textarea text</textarea></div>
							<div><label for="f6">Button:</label> <input id="f6" value="button text" type="button"></div>

						</fieldset>
					</form>
					<p>Sed scelerisque sagittis lorem. Phasellus sodales. Nulla urna justo,
					vehicula in, suscipit nec, molestie sed, tellus. Quisque justo. Nam
					libero leo, elementum in, dapibus a, suscipit vitae, purus. Duis arcu.
					Integer dignissim fermentum enim. Morbi convallis felis vel nibh. Etiam
					viverra augue non enim.</p>

						</div><!-- /panelcontent -->
					</div><!-- /panel -->
				</div><!-- /col1of2 -->




</div><!-- /content -->

<#include "includes/address-footer.ftl"/>
<#include "includes/footer.ftl"/>
