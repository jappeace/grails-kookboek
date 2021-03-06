package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.Authored
import nl.jappieklooster.Echo
import nl.jappieklooster.kook.quantification.Unit
/**
* acts as an ingredient in a recipe
* Ingredients are contents with possible prepends and amends, to describe the state of the ingredient
*/
class Ingredient implements Comparable<Ingredient>{
	/** allows a small amount of text to be prepended to the ingredient*/
	String prepend
	/** allows a small amount of text to be ammended (insert after) the ingredient */
	String ammend

	/** allows the user to override the default unit that is attached to contents
	* for example some recipes contain water, which can be measured with weight or with volume
	* the default is volume in centi-liters but if you want weight you can use this field for overwriting
	*/
	Unit preferedUnit

	/** the actual amount of this ingredient*/
	double quantity

	/** should this ingredient show what the ingredient is made of (if its made of anything)*/
	boolean isShowingSubRecipe

	static belongsTo = [
		recipe: Content,
		ingredient: Content
	]
    static constraints = {
		prepend nullable:true
		ammend nullable:true
		preferedUnit nullable:true
    }
	static mapping = {
		sort "ingredient"
	}
	Unit getPreferedUnit(){
		if(preferedUnit){
			return preferedUnit
		}
		return ingredient.unit
	}
	@Override
	String toString(){
		if(ingredient == null){
			return ""
		}
		String result = ""
		if(prepend != null){
			result += Echo.UpperCaseFirst(prepend)
		}
		if(result == ""){
			result += Echo.UpperCaseFirst(ingredient.name)
		}else{
			result += " " + ingredient.name
		}
		if(ammend != null){
			result += " " + ammend
		}
		return result
	}
	Ingredient(){
		isShowingSubRecipe = false
	}
	@Override
	int compareTo(Ingredient to){
		ingredient.compareTo(to.ingredient)
	}
}
