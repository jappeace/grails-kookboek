package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.Authored

/**
 * a cookbook, that contains contents, like ingredients and recipes
*/
class Category extends Authored {
	Category parent
	static hasMany = [contents:Content]
    static constraints = {
		// when one starts with writing a book it can be empty
		contents nullable:true
		parent nullable:true
    }
}
