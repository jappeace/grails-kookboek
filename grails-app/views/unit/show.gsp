
<%@ page import="nl.jappieklooster.kook.quantification.Unit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'unit.label', default: 'Unit')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-unit" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list unit">
			
				<g:if test="${unitInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="unit.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${unitInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${unitInstance?.plural}">
				<li class="fieldcontain">
					<span id="plural-label" class="property-label"><g:message code="unit.plural.label" default="Plural" /></span>
					
						<span class="property-value" aria-labelledby="plural-label"><g:fieldValue bean="${unitInstance}" field="plural"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${unitInstance?.abbreviation}">
				<li class="fieldcontain">
					<span id="abbreviation-label" class="property-label"><g:message code="unit.abbreviation.label" default="Abbreviation" /></span>
					
						<span class="property-value" aria-labelledby="abbreviation-label"><g:fieldValue bean="${unitInstance}" field="abbreviation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${unitInstance?.dimension}">
				<li class="fieldcontain">
					<span id="dimension-label" class="property-label"><g:message code="unit.dimension.label" default="Dimension" /></span>
					
						<span class="property-value" aria-labelledby="dimension-label"><g:link controller="dimension" action="show" id="${unitInstance?.dimension?.id}">${unitInstance?.dimension?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:unitInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${unitInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
