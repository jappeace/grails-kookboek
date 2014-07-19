
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dimension.label', default: 'Dimension')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body> 

	<g:form controller="import" action="begin" >
		<div class="row">
			Op deze pagina kan het importeer proces gebeuren
		</div>

		<fieldset>
			<legend>doel database</legend>
			<div class="row">
				<label for="uri">
					uri
				</label>
				<input name="uri" value="jappieklooster.nl"/>
			</div>
			<div class="row">
				<label for="name">
					naam
				</label>
				<input name="name" value="kloosped25_kook"/>
			</div>
			<div class="row">
				<label for="pass">
					wachtwoord
				</label>
				<input name="pass" type="password"/>
			</div>
		</fieldset>
		<fieldset>
			<legend>Extra opties</legend>
			<div class="row">
				<label for="categories">
					categorien
				</label>
				<g:select name="categories" from="${nl.jappieklooster.kook.book.Category.list()}" multiple="multiple" optionKey="id" size="5" value="${contentInstance?.categories*.id}" class="many-to-many"/>
				<p class="info">
					het oude systeem kende geen categorien, wijs hier de gewenste categorien toe
				</p>
			</div>
		</fieldset>
		<fieldset class="buttons">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:submitButton name="create" class="save" value="${message(code: 'default.button.print.label', default: 'print')}" />
		</fieldset>
	</g:form>
	</body>
</html>
