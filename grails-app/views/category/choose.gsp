
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="jumbotron" role="main">
			<h1>Welkom op Awesome Boek</h1>
			<p>
			Dit is de recepten collectie van Restaurant de Huiskamer.
			</p>
			<p>
			Kies een kookboek categorie om de recepten daarvan te bekijken:
			<div class="row">
			<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
				<div class="col-md-3">
					<g:link action="show" class="btn btn-info btn-lg" id="${categoryInstance.id}">
							<g:fieldValue bean="${categoryInstance}" field="name" />
					</g:link>
				</div>
			</g:each>
			</p>
			</div>
		</div>
		<g:render template="form" contextPath="/login"/>
	</body>
</html>
