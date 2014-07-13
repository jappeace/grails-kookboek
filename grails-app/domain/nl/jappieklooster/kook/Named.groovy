package nl.jappieklooster.kook
import nl.jappieklooster.Echo
import org.codehaus.groovy.grails.web.json.JSONObject
/**
* Al classes that require naming should extend from this one.
* Naming is somthing that happens a lot so I thought, why not pack the logic together
*/
abstract class Named implements Comparable<Named>{
	// if plural is null, add this to name to get a plural
	private static final String DEFAULT_PLURIFICATION = "en"
	/** the singular name of somthing (for example one apple) */
	String name
	/** the plural name of somthing (two apples)  */
	String plural
    static constraints = {
		name blank:false
		// plural defaults to name + DEFAULT_PLURIFICATION
		plural nullable:true, blank:false
    }
	static mapping = {
		sort "name"
	}
	Named(){
		name = ""
	}
	public String getPlural(){
		// on a create action the default value of plural would be nullable, which looked stupid
		if (name == null){
			return ""
		}
		return plural ?: name + DEFAULT_PLURIFICATION
	}
	String toString(){
		// make sure the first letter is capitalized by default
		return Echo.UpperCaseFirst(name)
	}
	@Override
	int compareTo(Named to){
		name.compareTo(to.name)
	}
}
