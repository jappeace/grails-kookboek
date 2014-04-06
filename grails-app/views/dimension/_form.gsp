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
	
<ul class="one-to-many">
<g:each in="${dimensionInstance?.units?}" var="u">
    <li><g:link controller="unit" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="unit" action="create" params="['dimension.id': dimensionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'unit.label', default: 'Unit')])}</g:link>
</li>
</ul>

</div>

