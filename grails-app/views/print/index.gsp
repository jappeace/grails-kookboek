
<%@ page import="nl.jappieklooster.kook.quantification.Dimension" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dimension.label', default: 'Dimension')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>

	<g:form method="get" controller="print" action="press" >
		<div class="row">
			Op deze paginas kunnen filters worden ingesteld zodat alleen de gewenste informatie kan worden geprint.
		</div>

		<fieldset class="filters">
			<legend>filters</legend>
			<div class="row">
				<label for="categories">
					Categorieen
				</label>
				<g:select name="categories" from="${nl.jappieklooster.kook.book.Category.list()}" multiple="multiple" optionKey="id" size="5" value="${contentInstance?.categories*.id}" class="many-to-many"/>
				<p>
					geen categorien betekent alles
				</p>
			</div>
			<div class="row">
				<label >
					<g:checkBox class="ignore-defaults" name="descriptionless" value="${false}" />
					Moeten recepten zonder beschrijving worden wegelaten?
				</label>
			</div>
			<div class="row">
				<label >
					<g:checkBox class="ignore-defaults" name="ingredientless" value="${false}" />
					Moeten recepten zonder ingredienten worden wegelaten?
				</label>
			</div>
		</fieldset>
		<fieldset class="buttons">
			<g:submitButton name="create" class="save" value="${message(code: 'default.button.print.label', default: 'print')}" />
		</fieldset>
	</g:form>
	</body>
</html>
