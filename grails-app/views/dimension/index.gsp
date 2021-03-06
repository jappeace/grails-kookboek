
<%@ page import="nl.jappieklooster.kook.quantification.Dimension" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dimension.label', default: 'Dimension')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-dimension" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'dimension.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="plural" title="${message(code: 'dimension.plural.label', default: 'Plural')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dimensionInstanceList}" status="i" var="dimensionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dimensionInstance.id}">${fieldValue(bean: dimensionInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: dimensionInstance, field: "plural")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dimensionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
