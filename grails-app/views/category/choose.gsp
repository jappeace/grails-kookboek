
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="" role="main">
			<header>
			<strong>
			Kies een kookboek:
			</strong>
			</header>
			<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
				<div class="col-md-6 btn">
					<g:link action="show" id="${categoryInstance.id}">${fieldValue(bean: categoryInstance, field: "name")}</g:link>
				</div>
			</g:each>
		</div>
	</body>
</html>
