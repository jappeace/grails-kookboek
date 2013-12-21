package nl.jappieklooster.kook

import nl.jappieklooster.kook.security.User

/** somthing which was created by a user and thus has a relation with it
* extens named because somthing like this usualy gets a name
*/
abstract class Authored  extends Named{

	User author

	static constraints = {
	}
	transient springSecurityService

	String toString(){
		return name
	}
	def beforeValidate(){
		owner = springSecurityService.getCurrentUser()?.contact ?: owner
	}
}
