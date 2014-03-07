
<%@ page import="nl.jappieklooster.kook.book.Content" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-content" class="content" role="main">
			<header class="page-header row">
			<h1>
				<g:fieldValue bean="${contentInstance}" field="name"/>
			</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			</header>
			<div class="row">
				<div class="col-md-6">
				<g:if test="${contentInstance?.ingredients}">
					<ul>
						<g:each in="${contentInstance.ingredients}" var="i">
							<li aria-labelledby="ingredients-label">
								<g:link controller="content" action="show" id="${i.ingredient.id}">
									${i?.encodeAsHTML()}
								</g:link>
							</li>
						</g:each>
					</ul>
				</g:if>
				</div>
				<div class="col-md-6 well">
					<dl>
						<g:if test="${contentInstance?.unit}">
						<dt>
							Eenheid
						</dt>
						<dd>
							<g:link controller="unit" action="show" id="${contentInstance?.unit?.id}">${contentInstance?.unit?.encodeAsHTML()}</g:link>
						</dd>
						</g:if>
						<dt>
							Autheur
						</dt>
						<dd>
							<g:link controller="user" action="show" id="${contentInstance?.author?.id}">
								${contentInstance?.author?.encodeAsHTML()}
							</g:link>
						</dd>
						<g:if test="${contentInstance.categories.size() > 0}">
							<dt>
								<g:if test="${contentInstance.categories.size() > 1}">
								Categorieen
								</g:if>
								<g:else>
								Categorie
								</g:else>
							</dt>
							<g:each in="${contentInstance.categories}" var="c">
								<dd>
									<g:link controller="category" action="show" id="${c.id}">
										${c?.encodeAsHTML()}
									</g:link>
								</dd>
							</g:each>
						</g:if>
					</dl>
				</div>
			</div>
			<div class="row description">
				<g:fieldValue bean="${contentInstance}" field="description"/>
			</div>
		</div>
	</body>
</html>
