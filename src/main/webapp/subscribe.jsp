<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/style/main.css"/>
    <title>Ark Update Notifier</title>
</head>

<body>
	<div class="content">
		<h1>Ark Update Notifier</h1>
		<%if(request.getParameter("unsubscribe") != null) { %>
			<div class="notification">You have been successfully unsubscribed.</div>
		<%} %>
		<div class="instructions">Subscribe below to receive emails when an update is announced, the ETA changes, 
	and/or the update is (finally) released for Ark: Survival Evolved!</div>
		
		<form action="/subscribe" method="post" class="subscriptionForm">
			<%if(request.getParameter("invalid") != null) {%>
				<div class="error">The email address you entered is invalid.  Please re-enter your email.</div>
			<%} %>
			<div class="emailLabel">Please enter an email address:</div>
			<input type="email" name="email" class="emailInput"/>
			
			<br class="clearFix" /><br />
			
			<input type="checkbox" name="subscriptionChoices" value="upcoming" checked/> Notify me when an update is <b>announced</b><br/>
			<input type="checkbox" name="subscriptionChoices" value="eta" checked /> Notify me if an update's ETA changes<br/>
			<input type="checkbox" name="subscriptionChoices" value="available" checked /> Notify me when the update is <b>available</b> for download<br/>
			<input type="submit" value="Subscribe" class="submit" />
		</form>

		<hr class="pageBreak" />
	
		<div class="footer">Email addresses are encrypted before they are stored.  
		Your information will not be shared or used for any purpose other than sending Ark update notifications.
		All emails will have a link to unsubscribe.  This service is offered free of charge.
		Special thanks for an API provided by <a href="http://ark.bar">http://ark.bar</a> that makes this service possible.</div>
	</div>
</body>
</html>