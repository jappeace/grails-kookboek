
<%@ page import="nl.jappieklooster.kook.book.Content" %>
<!DOCTYPE html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
  		<asset:stylesheet src="print.css"/>
	</head>
	<body>
		<div class="content" role="main">
			<h1>Kookboek</h1>
			<g:each in="${contentInstanceList}" status="i" var="contentInstance">
			<article class="recipe">
				<h2><g:fieldValue bean="${contentInstance}" field="name" /></h2>

				<table class="ingredients">
					<g:each in="${contentInstance.ingredients}" var="ingredient">
						<tr class="ingredient">
							<td><g:fieldValue bean="${ingredient}" field="prepend" /></td>
							<td><g:fieldValue bean="${ingredient}" field="quantity" /></td>
							<td><g:fieldValue bean="${ingredient.preferedUnit}" field="abbreviation" /></td>
							<td><g:fieldValue bean="${ingredient.ingredient}" field="name" /></td>
							<td><g:fieldValue bean="${ingredient}" field="ammend" /></td>
						</tr>
					</g:each>
				</table>
				<p class="description">
					<g:fieldValue bean="${contentInstance}" field="description" />
				</p>

			</article>
			</g:each>
		</div>
	</body>
</html>
