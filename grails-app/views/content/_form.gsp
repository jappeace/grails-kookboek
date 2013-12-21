<%@ page import="nl.jappieklooster.kook.book.Content" %>



<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="content.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${contentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'plural', 'error')} ">
	<label for="plural">
		<g:message code="content.plural.label" default="Plural" />
		
	</label>
	<g:textField name="plural" value="${contentInstance?.plural}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'unit', 'error')} ">
	<label for="unit">
		<g:message code="content.unit.label" default="Unit" />
		
	</label>
	<g:select id="unit" name="unit.id" from="${nl.jappieklooster.kook.quantification.Unit.list()}" optionKey="id" value="${contentInstance?.unit?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<g:if test="${contentInstance.ingredients != null}">

	<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'ingredients', 'error')} ">

		<label for="ingredients">
			<g:message code="content.ingredients.label" default="Ingredients" />
		</label>

		<ul class="one-to-many">
			<g:each in="${contentInstance?.ingredients?}" var="i">
				<li><g:link controller="ingredient" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
			</g:each>
			<li class="add">
			<g:link controller="ingredient" action="create" params="['content.id': contentInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'ingredient.label', default: 'Ingredient')])}</g:link>
			</li>
		</ul>
	</div>
</g:if>

</div>
<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="content.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${contentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="content.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="author" name="author.id" from="${nl.jappieklooster.kook.security.User.list()}" optionKey="id" required="" value="${contentInstance?.author?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contentInstance, field: 'categories', 'error')} ">
	<label for="categories">
		<g:message code="content.categories.label" default="Categories" />
		
	</label>
	<g:select name="categories" from="${nl.jappieklooster.kook.book.Category.list()}" multiple="multiple" optionKey="id" size="5" value="${contentInstance?.categories*.id}" class="many-to-many"/>
</div>

