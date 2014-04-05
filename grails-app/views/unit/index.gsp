
<%@ page import="nl.jappieklooster.kook.quantification.Unit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-unit" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'unit.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="plural" title="${message(code: 'unit.plural.label', default: 'Plural')}" />
					
						<g:sortableColumn property="abbreviation" title="${message(code: 'unit.abbreviation.label', default: 'Abbreviation')}" />
					
						<th><g:message code="unit.dimension.label" default="Dimension" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${unitInstanceList}" status="i" var="unitInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${unitInstance.id}">${fieldValue(bean: unitInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: unitInstance, field: "plural")}</td>
					
						<td>${fieldValue(bean: unitInstance, field: "abbreviation")}</td>
					
						<td>${fieldValue(bean: unitInstance, field: "dimension")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${unitInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
