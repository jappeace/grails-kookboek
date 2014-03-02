
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div role="main">
			<header class="page-header">
			<h1><g:fieldValue bean="${categoryInstance}" field="name"/></h1>
			<g:if test="${flash.message}">
			<p class="message lead" role="status">${flash.message}</p>
			</g:if>
			</header>
			<g:each in="${categoryInstance.contents}" status="i" var="content">
				<g:fieldValue bean="${content}" field="name" />
			</g:each>

		</div>
	</body>
</html>
