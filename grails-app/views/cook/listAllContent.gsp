
<%@ page import="nl.jappieklooster.kook.book.Content" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="content" role="main">
			<h1>Lijst van Inhoud</h1>
			<p>
				Op deze pagina ziet u een lijst van alle inhoud wilt u liever navigeren per categorie, klik dan op de links in het hoofd menu.
				U kunt op een recept klikken om deze aan te passen.
			</p>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'content.name.label', default: 'Naam')}" />
						<g:sortableColumn property="plural" title="${message(code: 'content.plural.label', default: 'Meervoud')}" />
						<th>Eenheid</th>
						<th><g:message code="content.author.label" default="Autheur" /></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${contentInstanceList}" status="i" var="contentInstance">
					<tr>
						<td><g:link controller="content" action="show" id="${contentInstance.id}">${fieldValue(bean: contentInstance, field: "name")}</g:link></td>
						<td><g:link controller="content" action="show" id="${contentInstance.id}">${fieldValue(bean: contentInstance, field: "plural")}</g:link></td>

						<td>${fieldValue(bean: contentInstance, field: "unit")}</td>
						<td>${fieldValue(bean: contentInstance, field: "author")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
