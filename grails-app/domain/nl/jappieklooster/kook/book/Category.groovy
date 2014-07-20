package nl.jappieklooster.kook.book
import nl.jappieklooster.kook.Authored

/**
 * a category is a user defined categorization of content.
 * It is used for navigation
*/
class Category extends Authored {
	Category parent
	SortedSet<Content> contents

	/** category canot exists without a content to be stored in 
	* this allows category selection from a content perspective	
	*/
	static belongsTo = Content

	static hasMany = [
		contents:Content
	]
    static constraints = {
		// when one starts with writing a book it can be empty
		contents nullable:true
		parent nullable:true
    }

	static List<Category> findRoots(){
		return Category.findAll{parent == null}
	}
}
