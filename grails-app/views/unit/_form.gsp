<%@ page import="nl.jappieklooster.kook.quantification.Unit" %>



<div class="fieldcontain ${hasErrors(bean: unitInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="unit.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${unitInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: unitInstance, field: 'plural', 'error')} ">
	<label for="plural">
		<g:message code="unit.plural.label" default="Plural" />
		
	</label>
	<g:textField name="plural" value="${unitInstance?.plural}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: unitInstance, field: 'abbreviation', 'error')} ">
	<label for="abbreviation">
		<g:message code="unit.abbreviation.label" default="Abbreviation" />
		
	</label>
	<g:textField name="abbreviation" value="${unitInstance?.abbreviation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: unitInstance, field: 'dimension', 'error')} required">
	<label for="dimension">
		<g:message code="unit.dimension.label" default="Dimension" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="dimension" name="dimension.id" from="${nl.jappieklooster.kook.quantification.Dimension.list()}" optionKey="id" required="" value="${unitInstance?.dimension?.id}" class="many-to-one"/>
</div>

