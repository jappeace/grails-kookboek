
<%@ page import="nl.jappieklooster.kook.book.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-category" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-category" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list category">
			
				<g:if test="${categoryInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="category.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${categoryInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.plural}">
				<li class="fieldcontain">
					<span id="plural-label" class="property-label"><g:message code="category.plural.label" default="Plural" /></span>
					
						<span class="property-value" aria-labelledby="plural-label"><g:fieldValue bean="${categoryInstance}" field="plural"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.contents}">
				<li class="fieldcontain">
					<span id="contents-label" class="property-label"><g:message code="category.contents.label" default="Contents" /></span>
					
						<g:each in="${categoryInstance.contents}" var="c">
						<span class="property-value" aria-labelledby="contents-label"><g:link controller="content" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="category.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="category" action="show" id="${categoryInstance?.parent?.id}">${categoryInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="category.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:link controller="user" action="show" id="${categoryInstance?.author?.id}">${categoryInstance?.author?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:categoryInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${categoryInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
