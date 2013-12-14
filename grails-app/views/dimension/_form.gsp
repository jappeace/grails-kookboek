<%@ page import="nl.jappieklooster.kook.quantification.Dimension" %>



<div class="fieldcontain ${hasErrors(bean: dimensionInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="dimension.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${dimensionInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dimensionInstance, field: 'plural', 'error')} ">
	<label for="plural">
		<g:message code="dimension.plural.label" default="Plural" />
		
	</label>
	<g:textField name="plural" value="${dimensionInstance?.plural}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dimensionInstance, field: 'units', 'error')} ">
	<label for="units">
		<g:message code="dimension.units.label" default="Units" />
		
	</label>
	<g:select name="units" from="${nl.jappieklooster.kook.quantification.Unit.list()}" multiple="multiple" optionKey="id" size="5" value="${dimensionInstance?.units*.id}" class="many-to-many"/>
</div>

