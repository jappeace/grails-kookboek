
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="" role="main">
			<header>
			<strong>
			Kies een kookboek categorie:
			</strong>
			</header>
			<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
				<div class="col-md-2">
					<g:link action="show" class="btn btn-info btn-lg" id="${categoryInstance.id}">
							<g:fieldValue bean="${categoryInstance}" field="name" />
					</g:link>
				</div>
			</g:each>
		</div>
	</body>
</html>
