
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<form method="POST" class="col-md-4 form form-horizontal" action="/kook/j_spring_security_check">
			<fieldset>
				<legend>Login</legend>
				<p>
				Bent u een kok en wenst u de recepten aan te passen, log dan in
				</p>
				<label for="j_username">Gebruikersnaam:</label>
				<input type="text" name="j_username"/>
				<label for="j_password">Wachtwoord</label>
				<input type="password" name="j_password" />
				<label>
				Herinner mij
				<input name="_spring_security_remember_me" id="remember_me" type="checkbox">
				</label>
			</fieldset>
			<input type="submit" value="Log in"/>
		</form>
	</body>
</html>
