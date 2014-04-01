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

<fieldset>
<legend>Ingredienten</legend>
<p>Als het gaat om een basis ingredient zoals water, dan kan deze stap worden overgeslagen</p>
<div class="form-group ${hasErrors(bean: contentInstance, field: 'ingredients', 'has-error')} ">
	<div class="selected-ingredients row">
		<table>
		<tr><th>Voortext</th><th>Hoeveelheid</th><th>Eenheid</th><th>Naam</th><th>Achtertext</th></tr>

		<g:each in="${contentInstance.ingredients}" var="i"><tr>
			<td>
				<input name="ingredient.prepend" value="${i.prepend}"type="text" />
			</td>
			<td>
				<input name="ingredient.amount" value="${i.quantity}" type="number" />
			</td>
			<td>
				<g:select
					name="ingredient.preferedUnit"
					from="${nl.jappieklooster.kook.quantification.Unit.list()}"
					optionKey="id"
					value="${i?.preferedUnit?.id}"
					class="many-to-one"
					noSelection="['null': '']"
				/>
			</td>
			<td>
				${i?.encodeAsHTML()}
			</td>
			<td>
				<input name="ingredient.ammend" value="${i.ammend}"type="text" />
			</td>
			</tr></g:each>
		</table>
	</div>
	<div>

	<div class="row">
	<ul class="list-unstyled ingredients-choice">
		<g:each in="${Content.where{!((id in contentInstance.ingredients*.ingredient.id) || (id == contentInstance.id))}.list()}" var="c">

		<li>
			<span class="glyphicon glyphicon-plus-sign"></span>${c.encodeAsHTML()}
		</li>
		</g:each>
	</ul>
	</div>
</div>
</fieldset>
