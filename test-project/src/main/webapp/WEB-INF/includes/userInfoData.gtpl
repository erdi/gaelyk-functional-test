<div id="logged-in">${users.isUserLoggedIn()}</div>
<% if (users.isUserLoggedIn()) { %>
	<div id="username">${user.email}</div>
	<div id="is-admin">${users.isUserAdmin()}</div>
<% } %>