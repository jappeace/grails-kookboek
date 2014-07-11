
<%@ page import="nl.jappieklooster.kook.book.Content" %>
<%@ page import="nl.jappieklooster.kook.quantification.Unit" %>
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
				<div class="col-md-8">
				<g:if test="${contentInstance?.ingredients}">
				<table>
						<g:each in="${contentInstance.ingredients}" var="i">
							<tr>
								<td><g:fieldValue bean="${i}" field="prepend"/></td>
								<td><g:fieldValue bean="${i}" field="quantity"/></td>
								<g:if test="${i.preferedUnit}">
									<td><g:fieldValue bean="${i.preferedUnit}" field="name"/></td>
								</g:if>
								<g:elseif test="${i.ingredient.unit}">
									<td><g:fieldValue bean="${i.ingredient.unit}" field="name"/></td>
								</g:elseif>
								<td><g:fieldValue bean="${i.ingredient}" field="name"/></td>
								<td><g:fieldValue bean="${i}" field="ammend"/></td>
							</tr>
						</g:each>
				</g:if>
				</table>
				</div>
				<div class="col-md-4 well">
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
					<sec:ifLoggedIn>
					<div class="panel panel-primary">
						<div class="panel-heading">

							<h3 class="panel-title">Kok opties</h3>

						</div>
						<div class="panel-body">

						<g:link action="edit" controller="content" id="${contentInstance.id}">
							Verander
						</g:link><br />
						<g:link action="delete" controller="content" id="${contentInstance.id}">
							Verwijder
						</g:link>
						<p>
							Doordat u bent ingelogd heeft u extra rechten. zoals deze inhoud veranderen of verwijderen
							Daarnaast kunt u een stukje text voor of achter ingredienten invoegen, en hun hoeveelheid veanderen.
						</p>
						</div>
					</div>
					</sec:ifLoggedIn>
				</div>
			</div>
			<div class="row description">
				<g:fieldValue bean="${contentInstance}" field="description"/>
			</div>
		</div>
	</body>
</html>
