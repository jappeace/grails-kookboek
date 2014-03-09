<%@ page import="nl.jappieklooster.kook.book.Content" %>
<fieldset>
<legend>Text</legend>
<div class="form-group ${hasErrors(bean: contentInstance, field: 'name', 'has-error')} required">
	<label for="name">
		Naam
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${contentInstance?.name}"/>
</div>

<div class="form-group ${hasErrors(bean: contentInstance, field: 'plural', 'has-error')} ">
	<label for="plural">
		Meervoud
	</label>
	<g:textField name="plural" value="${contentInstance?.plural}"/>
	<p>
	Laat leeg voor naam + en
	</p>
</div>

<div class="form-group ${hasErrors(bean: contentInstance, field: 'description', 'has-error')} ">
	<label for="description">
		Bereidingswijze
	</label>
	<g:textArea name="description" value="${contentInstance?.description}"/>
	<p>
		Hierin dient stapsgewijz uit te worden gelegd hoe de ingredienten in het recept moeten worden bereid.
		Kan worden overgeslagen voor ingredienten.
	</p>
</div>

</fieldset>
<fieldset>
<legend>Relaties</legend>

<div class="form-group ${hasErrors(bean: contentInstance, field: 'ingredients', 'has-error')} ">

	<label for="ingredients">
		Ingredienten
	</label>
	<div class="row">
		<div class="col-md-9">
		<!-- break convention naming because the way this is handled is to excotic for grails to do right
			The ingredients model is never showed to user
		-->
		<g:select
			name="ingredientChoice"
			from="${Content.list()}"
			multiple="multiple"
			optionKey="id"
			size="5"
			value="${contentInstance?.ingredients*.ingredient?.id}"
			class="many-to-many filterable"
		/>
		</div>
		<div class="col-md-3">
			<label>
				<span class="glyphicon glyphicon-search"></span>
				Zoeken
			</label>
			<input type="text" class="filter" />
		</div>
	</div>
	<ul>
		<li>Houd control ('ctrl' links onderin) ingedrukt terwijl je klikt voor meerderen</li>
		<li>Laat leeg om dit als ingredient te laten rekenen.</li>
		<li>Recepten kunnen worden gekozen als ingredient.</li>
	</ul>
</div>

<div class="form-group ${hasErrors(bean: contentInstance, field: 'unit', 'has-error')} ">
	<label for="unit">
		Eenheid
	</label>
	<div class="row">
		<div class="col-md-11">
			<g:select id="unit" name="unit.id" from="${nl.jappieklooster.kook.quantification.Unit.list()}" optionKey="id" value="${contentInstance?.unit?.id}" class="many-to-one" noSelection="['null': '']"/>
		</div>
		<div class="col-md-1">
		<g:link action="create" controller="unit"></g:link>
		</div>
	</div>
</div>




<div class="form-group ${hasErrors(bean: contentInstance, field: 'categories', 'has-error')} ">
	<label for="categories">
		Categorieen
	</label>
	<div class="row">
		<div class="col-md-11">
		<g:select name="categories" from="${nl.jappieklooster.kook.book.Category.list()}" multiple="multiple" optionKey="id" size="5" value="${contentInstance?.categories*.id}" class="many-to-many"/>
		</div>
		<div class="col-md-1">
		<g:link action="create" controller="category"></g:link>
		</div>
	</div>
	<p>
		Onder welke categorieen valt dit recept?
		Dit is niet nodig voor ingredienten.
	</p>
</div>

</fieldset>
