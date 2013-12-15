package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.quantification.Unit
import nl.jappieklooster.kook.Authored
import org.apache.commons.lang3.text.WordUtils
/**
* things inside a cookbook, recipies and ingredients. an ingredient can become a recipe by adding ingredients to it.
*/
class Content extends Authored {
	String description
	/** content canot exists without a book to be stored in */
	static belongsTo = Category
	static hasMany = [ingredients: Ingredient, categories:Category]
	/** unit of measurement (kg or l) */
	Unit unit
    static constraints = {
		ingredients nullable:true
    }
	static mapping = {
		description type:'text'
	}
	String toString(){
		// filetring out instances that are created with new
		if(name == null && unit == null){
			return ""
		}
		// return with first word capitalized, decapitlize the rest
		return WordUtils.capitalize(name.toString().toLowerCase())+(" "+ unit.toString()).toLowerCase()
	}
}
