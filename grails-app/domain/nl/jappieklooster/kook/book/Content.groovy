package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.quantification.Unit
import nl.jappieklooster.kook.Authored
/**
* things inside a cookbook, recipies and ingredients. an ingredient can become a recipe by adding ingredients to it.
*/
class Content extends Authored {
	String description
	/** content canot exists without a book to be stored in */
	static belongsTo = Category
	static hasMany = [ingredients: Ingredient, books:Category]
	/** unit of measurement (kg or l) */
	Unit unit
    static constraints = {
		ingredients nullable:true
    }
	static mapping = {
		description type:'text'
	}
	String toString(){
		return name + unit
	}
}
