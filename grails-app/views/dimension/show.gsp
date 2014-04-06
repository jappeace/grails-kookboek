
<%@ page import="nl.jappieklooster.kook.quantification.Dimension" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dimension.label', default: 'Dimension')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-dimension" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dimension">
			
				<g:if test="${dimensionInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="dimension.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${dimensionInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dimensionInstance?.plural}">
				<li class="fieldcontain">
					<span id="plural-label" class="property-label"><g:message code="dimension.plural.label" default="Plural" /></span>
					
						<span class="property-value" aria-labelledby="plural-label"><g:fieldValue bean="${dimensionInstance}" field="plural"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dimensionInstance?.units}">
				<li class="fieldcontain">
					<span id="units-label" class="property-label"><g:message code="dimension.units.label" default="Units" /></span>
					
						<g:each in="${dimensionInstance.units}" var="u">
						<span class="property-value" aria-labelledby="units-label"><g:link controller="unit" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:dimensionInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${dimensionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
