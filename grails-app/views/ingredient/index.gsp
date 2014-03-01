
<%@ page import="nl.jappieklooster.kook.book.Ingredient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ingredient.label', default: 'Ingredient')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ingredient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ingredient" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="prepend" title="${message(code: 'ingredient.prepend.label', default: 'Prepend')}" />
					
						<g:sortableColumn property="ammend" title="${message(code: 'ingredient.ammend.label', default: 'Ammend')}" />
					
						<th><g:message code="ingredient.ingredient.label" default="Ingredient" /></th>
					
						<g:sortableColumn property="isShowingSubRecipe" title="${message(code: 'ingredient.isShowingSubRecipe.label', default: 'Is Showing Sub Recipe')}" />
					
						<th><g:message code="ingredient.recipe.label" default="Recipe" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ingredientInstanceList}" status="i" var="ingredientInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ingredientInstance.id}">${fieldValue(bean: ingredientInstance, field: "prepend")}</g:link></td>
					
						<td>${fieldValue(bean: ingredientInstance, field: "ammend")}</td>
					
						<td>${fieldValue(bean: ingredientInstance, field: "ingredient")}</td>
					
						<td><g:formatBoolean boolean="${ingredientInstance.isShowingSubRecipe}" /></td>
					
						<td>${fieldValue(bean: ingredientInstance, field: "recipe")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ingredientInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
