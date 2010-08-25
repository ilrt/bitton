<!-- main navigation links and search -->
<div id="nav">
    <ul id="nav-left">
        <li id="nav-home"><a href="${contextPath}${servletPath}/">Home</a></li>
        <li id="nav-explore"><a href="${contextPath}${servletPath}/">Explore</a></li>
    </ul>
    <form id="search" method="get" action="">
        <p><input type="text" id="search-text" value="Search everything ..."/>
            <input type="button" id="search-button" disabled="disabled" value="Search"/></p>
    </form>
    <ul id="nav-right">
        <li id="nav-my-dept"><a href="${contextPath}${servletPath}/profile">My Profile</a></li>
    </ul>
</div>