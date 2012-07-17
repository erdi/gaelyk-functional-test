<html>
<head>
	<title>User Info Page</title>
</head>
<body>
    <% include '/WEB-INF/includes/userInfoData.gtpl' %>
	<% params.each { %>
		<input type="hidden" name="${it.key}" value="${it.value}">
	<% } %>
</body>
</html>