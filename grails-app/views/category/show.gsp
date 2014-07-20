
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<%@ page import="nl.jappieklooster.kook.book.Content" %>
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
			<table class="table table-striped table-hover">
			<tr><th>Naam</th></tr>
			<g:each in="${categoryInstance.contents}" status="i" var="content">
				<g:if test="${content.ingredients == null}">
				<!-- contents bound to a category without ingredients is suspicious, so highlight it for the user -->
				<tr class="warning">
				</g:if>
				<g:else>
				<tr>
				</g:else>
					<td>
						<g:link action="show" controller="content" id="${content.id}">
							<g:fieldValue bean="${content}" field="name" />
						</g:link>
					</td>
				</tr>
			</g:each>
			</table>

		</div>
	</body>
</html>
