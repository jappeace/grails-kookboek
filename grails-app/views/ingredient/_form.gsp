<%@ page import="nl.jappieklooster.kook.book.Ingredient" %>



<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'prepend', 'error')} ">
	<label for="prepend">
		<g:message code="ingredient.prepend.label" default="Prepend" />
		
	</label>
	<g:textField name="prepend" value="${ingredientInstance?.prepend}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'ammend', 'error')} ">
	<label for="ammend">
		<g:message code="ingredient.ammend.label" default="Ammend" />
		
	</label>
	<g:textField name="ammend" value="${ingredientInstance?.ammend}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'ingredient', 'error')} required">
	<label for="ingredient">
		<g:message code="ingredient.ingredient.label" default="Ingredient" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ingredient" name="ingredient.id" from="${nl.jappieklooster.kook.book.Content.list()}" optionKey="id" required="" value="${ingredientInstance?.ingredient?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'isShowingSubRecipe', 'error')} ">
	<label for="isShowingSubRecipe">
		<g:message code="ingredient.isShowingSubRecipe.label" default="Is Showing Sub Recipe" />
		
	</label>
	<g:checkBox name="isShowingSubRecipe" value="${ingredientInstance?.isShowingSubRecipe}" />
</div>

<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'recipe', 'error')} required">
	<label for="recipe">
		<g:message code="ingredient.recipe.label" default="Recipe" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="recipe" name="recipe.id" from="${nl.jappieklooster.kook.book.Content.list()}" optionKey="id" required="" value="${ingredientInstance?.recipe?.id}" class="many-to-one"/>
</div>

