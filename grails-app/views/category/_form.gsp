<%@ page import="nl.jappieklooster.kook.book.Category" %>



<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="category.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${categoryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'plural', 'error')} ">
	<label for="plural">
		<g:message code="category.plural.label" default="Plural" />
		
	</label>
	<g:textField name="plural" value="${categoryInstance?.plural}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'contents', 'error')} ">
	<label for="contents">
		<g:message code="category.contents.label" default="Contents" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="category.parent.label" default="Parent" />
		
	</label>
	<g:select id="parent" name="parent.id" from="${nl.jappieklooster.kook.book.Category.list()}" optionKey="id" value="${categoryInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="category.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="author" name="author.id" from="${nl.jappieklooster.kook.security.User.list()}" optionKey="id" required="" value="${categoryInstance?.author?.id}" class="many-to-one"/>
</div>

