package nl.jappieklooster.kook

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import nl.jappieklooster.kook.book.Content
import nl.jappieklooster.kook.book.Category

@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class PrintController {

	/**
	* allows the user to select what he wants to print
	*/
    def index() { }
	/**
	* like a printing press, this actions allows the user to print
	* the result, without any style so that no inkt is wasted
	*/
	def press(){
		println params
		List<Category> desiredCategories = retrieveDesriredCategories()

		Set<Content> contents = new TreeSet<>()
		desiredCategories.each{
			contents.addAll(it.contents)
		}
		def list = contents.findAll{
			boolean result = true
			if(params["descriptionless"] == "on"){
				result = result && it.description != ""
			}
			if(params["ingredientless"] == "on"){
				result = result && !it.ingredients.isEmpty()
			}
			return result
		}
		def listObject = [contentInstanceList: list, contentInstanceTotal: Content.count()]
		withFormat {
			html listObject
			json {
				println list
				if (list){
					render list as JSON
				}
				else {
					response.status = 204
					render '[]'
				}
			}
		}
	}
	/**
	* there are 3 posibilities for the categories parameter,
	* the key either does not exist, is a single string or a collection
	* this method will parse the parameter and always return a list
	*/
	private List<Category> retrieveDesriredCategories(){
		List<Category> result = new LinkedList<>()
		def key = "categories"
		if(!params.containsKey(key)){
			return result
		}
		def categories = params[key]
		if(categories  instanceof Collection){
			categories.each{
				result.add(Category.get(it))
			}
			return result
		}
		result.add(Category.get(categories))
		return result
	}
}
