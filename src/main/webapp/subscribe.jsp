<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/style/main.css"/>
    <title>Ark Update Notifier</title>
</head>

<body>
	<h1>Ark Update Notifier</h1>
	<%if(request.getParameter("unsubscribe") != null) { %>
		<div class="error">You have been successfully unsubscribed.</div>
	<%} %>
	<div class="instructions">Please subscribe below to recieve emails when an update is announced, when the ETA changes, 
	and/or when the update is (finally) released.</div>

	<form action="/subscribe" method="post">
		<br/>
		Please enter an email address:
		<%if(request.getParameter("invalid") != null) {%>
			<br/> <div class="error">The email address you entered is invalid.  Please try again.</div>
		<%} %>
		<br/>
		<input type="email" name="email" /><br/>
		
		<input type="checkbox" name="subscriptionChoices" value="upcoming" checked/> Notify me when an update is <b>announced</b><br/>
		<input type="checkbox" name="subscriptionChoices" value="eta" checked /> Notify me if an update's ETA changes<br/>
		<input type="checkbox" name="subscriptionChoices" value="available" checked /> Notify me when the update is <b>available</b> for download<br/>
		<input type="submit" value="Subscribe" />
	</form>
	
	<br/> <hr/> <br/>

	<div class="footer">Email addresses are encrypted before being stored.  
	Your information will not be shared or used for any other purpose than sending emails regarding Ark updates.
	All emails sent will have a link to allow you to unsubscribe at any point.  This service is offered free of charge.
	Special thanks for an API provided by <a href="http://ark.bar">http://ark.bar</a> that makes this service possible.</div>

</body>
</html>