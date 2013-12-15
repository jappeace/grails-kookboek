package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.Authored
import org.apache.commons.lang3.text.WordUtils
import nl.jappieklooster.Echo
/**
* acts as an ingredient in a recipe
* Ingredients are contents with possible prepends and amends, to describe the state of the ingredient
*/
class Ingredient {
	// the actual information resides in the content class	
	Content data
	
	/** allows a small amount of text to be prepended to the ingredient*/
	String prepend
	/** allows a small amount of text to be ammended (insert after) the ingredient */
	String ammend
	
	/** should this ingredient show what the ingredient is made of (if its made of anything)*/
	boolean isShowingSubRecipe

	static belongsTo = [content: Content]
    static constraints = {
		prepend nullable:true
		ammend nullable:true
    }
	String toString(){
		String result = ""
		if(prepend != null){
			result += Echo.UpperCaseFirst(prepend)
		}
		if(result == ""){
			result += Echo.UpperCaseFirst(data.name)
		}else{
			result += " " + data.name	
		}
		if(ammend != null){
			result += " " + ammend
		}
		return result 
	}
	Ingredient(){
		isShowingSubRecipe = false
	}

}
