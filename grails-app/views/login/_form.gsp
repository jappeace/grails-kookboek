<sec:ifLoggedIn>
	<p>U bent al ingelogd als: <sec:username/>, <g:link controller='logout'>Loguit</g:link> </p>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>

<form method="POST" class="col-md-6 col-md-offset-3 form form-horizontal" action="/kook/j_spring_security_check">
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
</sec:ifNotLoggedIn>
