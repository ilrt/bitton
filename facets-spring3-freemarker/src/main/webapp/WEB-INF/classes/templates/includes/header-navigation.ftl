<!-- main navigation links and search -->

<#assign curr = { viewcontext:"current" }>

<ul id="mainnav">
					<li id="nav-home"><a class="${(curr.home)!}" href=".">Home</a></li>
					<li id="nav-outputs"><a class="${(curr.pubs)!}" href="pubs">Research outputs</a></li>
					<li id="nav-grants"><a class="${(curr.grants)!}" href="grants">Grants and funding</a></li>
					<li id="nav-people"><a class="${(curr.people)!}" href="people">People</a></li>
					<li id="nav-departments"><a class="${(curr.organisations)!}" href="organisations">Departments and groups</a></li>
				</ul>

				<ul id="researchernav">
					<li id="nav-myresearch"><a class="${(curr.profile)!}" href="profile">My research</a></li>
					<li id="nav-mydepartment"><a class="${(curr.department)!}" href="mydepartment">My department</a></li>
				</ul>
