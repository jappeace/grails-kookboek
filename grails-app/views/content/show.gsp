
<%@ page import="nl.jappieklooster.kook.book.Content" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-content" class="content scaffold-show" role="main">
			<header class="page-header">
			<h1><g:fieldValue bean="${contentInstance}" field="name"/></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			</header>
			<ol class="property-list content">
				<g:if test="${contentInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="content.name.label" default="Name" /></span>
						<span class="property-value" aria-labelledby="name-label"></span>
				</li>
				</g:if>
				<g:if test="${contentInstance?.unit}">
				<li class="fieldcontain">
					<span id="unit-label" class="property-label"><g:message code="content.unit.label" default="Unit" /></span>
						<span class="property-value" aria-labelledby="unit-label"><g:link controller="unit" action="show" id="${contentInstance?.unit?.id}">${contentInstance?.unit?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
				<g:if test="${contentInstance?.ingredients}">
				<li class="fieldcontain">
					<span id="ingredients-label" class="property-label"><g:message code="content.ingredients.label" default="Ingredients" /></span>
						<g:each in="${contentInstance.ingredients}" var="i">
						<span class="property-value" aria-labelledby="ingredients-label"><g:link controller="ingredient" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
				</li>
				</g:if>
				<g:if test="${contentInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="content.description.label" default="Description" /></span>
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${contentInstance}" field="description"/></span>
				</li>
				</g:if>
				<g:if test="${contentInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="content.author.label" default="Author" /></span>
						<span class="property-value" aria-labelledby="author-label"><g:link controller="user" action="show" id="${contentInstance?.author?.id}">${contentInstance?.author?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
				<g:if test="${contentInstance?.categories}">
				<li class="fieldcontain">
					<span id="categories-label" class="property-label"><g:message code="content.categories.label" default="Categories" /></span>
						<g:each in="${contentInstance.categories}" var="c">
						<span class="property-value" aria-labelledby="categories-label"><g:link controller="category" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
				</li>
				</g:if>
			</ol>
			<g:form url="[resource:contentInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${contentInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
