<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<g:layoutHead/>
		<g:javascript library='jquery' />
		<g:javascript library="application"/>
		<r:layoutResources />
	</head>
	<body>
			<header class="navbar navbar-default navbar-fixed-top" role="banner">
				<div class="container">
				<div class="navbar-header">
					<a href="${createLink(uri: '/')}" class="navbar-brand">Awesome boek</a>
				</div>
				<ul class="nav navbar-nav">
					<g:each in="${Category.findRoots()}" var="category">
						<li>
						<g:link controller="category" action="show" id="${category.id}">
							<g:fieldValue bean="${category}" field="name" />
						</g:link>
						</li>
					</g:each>
				</ul>
				<sec:ifLoggedIn>
					<g:render contextPath="/cook" template="nav" />
				</sec:ifLoggedIn>
				<ul class="nav navbar-nav navbar-right">
					<sec:ifLoggedIn>
						<li>
							<g:link controller='logout'>
								Logout
							</g:link>
						</li>
					</sec:ifLoggedIn>
					<sec:ifNotLoggedIn>
							<li>
								<g:link controller='login'>
									Login
								</g:link>
							</li>
					</sec:ifNotLoggedIn>
				</ul>
				<sec:ifLoggedIn>
					<p class="navbar-text navbar-right">
						Logged in as <sec:username/>
					</p>
				</sec:ifLoggedIn>
				</div>
			</header>
		<div class="container layoutBody">
			<div class="col-md-12">
			<g:layoutBody/>
			</div>
			<div class="footer col-md-12">
				<small>Gemaakt door <a href="jappieklooster.nl">Jappie</a>
				<!--
					thanks to: 
					http://grails.org/ for the entire framework
					http://glyphicons.com/ for providing nice icons
				-->
				</small>
			</div>
			<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
			<r:layoutResources />
		</div>
	</body>
</html>
