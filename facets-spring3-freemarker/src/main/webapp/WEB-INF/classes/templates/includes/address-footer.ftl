<!-- footer for the page -->
<div id="footer">
	<ul id="footernav">
		<li><a href="page?name=about">About</a></li>
		<li><a href="page?name=contact">Contact</a></li>
		<li><a href="#">Disclaimer</a></li>
		<li class="last"><a href="#">Copyright</a></li>
	</ul>

	<div id="credits">

		<a id="logo-jisc" href="http://www.jisc.ac.uk"><img src="${contextPath}/images/logo-jisc.png" alt="JISC" height="34" width="62" /></a>
		<a id="logo-ilrt" href="http://www.ilrt.bristol.ac.uk"><img src="${contextPath}/images/logo-ilrt.png" alt="ILRT" height="30" width="53" /></a>
		<p>Copyright 2011 University of Bristol. Funded by <a href="http://www.jisc.ac.uk">JISC</a><br />
		Designed and built by <a href="http://www.ilrt.bristol.ac.uk">ILRT</a></p>
	</div><!-- /credits -->


	<#if resource??><a style="float: right; display: block; color: #aaa; font-size: 11px;" href="${resource!'#'}">{{Debug link}}</a></#if>

</div><!-- /footer -->
