<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="jumbotron" role="main">
			<h1>Kies een actie</h1>
			<p>
			Hier kunt u kiezen wat u wilt veranderen aan het kookboek, wilt u niks veranderen, <g:link controller='logout'>log dan uit</g:link>
			</p>
			<p>
			U kunt kiezen tussen:
			<g:render template="nav" />
			</p>
		</div>
	</body>
</html>
