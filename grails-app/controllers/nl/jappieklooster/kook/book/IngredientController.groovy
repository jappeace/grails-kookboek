package nl.jappieklooster.kook.book



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON
@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class IngredientController {
	/** gives an json object with 2 arrays one contains all the contents that are an ingredient of the
	* speciefied contentid the other one contains the rest.
	* Both arrays do not contain the member itself
	*/
	def listMemberSeperation(Content content){
		def result;
		if(content){
		 	result = [
				member: Content.where{		id in content.ingredients*.ingredient.id}.list(),
				other:  Content.where{!((	id in content.ingredients*.ingredient.id) || (id == content.id))}.list()
			] as JSON
		}else{
		 	result = [
				member: [],
				other:  Content.list()
			] as JSON
		}
		render result
		}
}
