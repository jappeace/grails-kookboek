package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.Authored

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

    static constraints = {
    }
	String toString(){
		return prepend + data + ammend
	}

}
