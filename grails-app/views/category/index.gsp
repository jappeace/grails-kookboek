
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-category" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'category.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="plural" title="${message(code: 'category.plural.label', default: 'Plural')}" />
					
						<th><g:message code="category.parent.label" default="Parent" /></th>
					
						<th><g:message code="category.author.label" default="Author" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${categoryInstance.id}">${fieldValue(bean: categoryInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: categoryInstance, field: "plural")}</td>
					
						<td>${fieldValue(bean: categoryInstance, field: "parent")}</td>
					
						<td>${fieldValue(bean: categoryInstance, field: "author")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${categoryInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
