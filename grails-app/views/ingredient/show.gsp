
<%@ page import="nl.jappieklooster.kook.book.Ingredient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ingredient.label', default: 'Ingredient')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-ingredient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-ingredient" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list ingredient">
			
				<g:if test="${ingredientInstance?.prepend}">
				<li class="fieldcontain">
					<span id="prepend-label" class="property-label"><g:message code="ingredient.prepend.label" default="Prepend" /></span>
					
						<span class="property-value" aria-labelledby="prepend-label"><g:fieldValue bean="${ingredientInstance}" field="prepend"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.ammend}">
				<li class="fieldcontain">
					<span id="ammend-label" class="property-label"><g:message code="ingredient.ammend.label" default="Ammend" /></span>
					
						<span class="property-value" aria-labelledby="ammend-label"><g:fieldValue bean="${ingredientInstance}" field="ammend"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.ingredient}">
				<li class="fieldcontain">
					<span id="ingredient-label" class="property-label"><g:message code="ingredient.ingredient.label" default="Ingredient" /></span>
					
						<span class="property-value" aria-labelledby="ingredient-label"><g:link controller="content" action="show" id="${ingredientInstance?.ingredient?.id}">${ingredientInstance?.ingredient?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.isShowingSubRecipe}">
				<li class="fieldcontain">
					<span id="isShowingSubRecipe-label" class="property-label"><g:message code="ingredient.isShowingSubRecipe.label" default="Is Showing Sub Recipe" /></span>
					
						<span class="property-value" aria-labelledby="isShowingSubRecipe-label"><g:formatBoolean boolean="${ingredientInstance?.isShowingSubRecipe}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.recipe}">
				<li class="fieldcontain">
					<span id="recipe-label" class="property-label"><g:message code="ingredient.recipe.label" default="Recipe" /></span>
					
						<span class="property-value" aria-labelledby="recipe-label"><g:link controller="content" action="show" id="${ingredientInstance?.recipe?.id}">${ingredientInstance?.recipe?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:ingredientInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${ingredientInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
