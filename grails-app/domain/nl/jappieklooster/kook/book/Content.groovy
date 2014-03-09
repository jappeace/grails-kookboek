package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.quantification.Unit
import nl.jappieklooster.kook.Authored
import nl.jappieklooster.Echo
/**
* things inside a cookbook, recipies and ingredients. an ingredient can become a recipe by adding ingredients to it.
*/
class Content extends Authored {
	String description
	SortedSet<Ingredient> ingredients
	static hasMany = [ingredients: Ingredient, categories:Category]
	/** unit of measurement (kg or l)
	* when set to null it will use itself als a value.
	* ie a pie would be measured in pies instead of grams. (which could also work, but the option is open)
	*/
	Unit unit
	Content(){
		ingredients = new TreeSet<Ingredient>();
	}
    static constraints = {
		unit nullable:true
		/**
		* When ingerdients == null it is considerd a base ingredient
		*/
		ingredients nullable:true
		description nullable:true
    }
	static mapping = {
		description type:'text'
	}
	String toString(){
		// filetring out instances that are created with new
		if(name == null && unit == null){
			return ""
		}
		// if unit == null, return the plural as a unit
		String u = unit ?: plural
		// return with first word capitalized, decapitlize the rest
		return Echo.UpperCaseFirst(name)+" "+ u.toString().toLowerCase()
	}
	static mappedBy = [ingredients:'recipe']
}
