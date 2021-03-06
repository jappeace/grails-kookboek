package nl.jappieklooster.kook.book

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.util.logging.*
import nl.jappieklooster.kook.quantification.Unit

@Log
@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class ContentController {

	static allowedMethods = [
		list:'GET',
		show:'GET',
		edit:[
			'GET',
			'POST'
		],
		save:'POST',
		update:[
			'POST',
			'PUT'
		],
		delete:[
			'GET', // less inconvenient, but weird
			'POST',
			'DELETE'
		]
	]

	def index(Integer max) {
		def list = Content.list(sort:"name")
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
	* lists all the contents that are not a ingredient or the specified id self
	* if no id is specified all contents will be listed, as is done with index
	*/
	def list(Long id) {
		if(id){
			def contentInstance = Content.get(id)
			def list = Content.where{!((id in contentInstance.ingredients*.ingredient.id) || (id == contentInstance.id))}.list()

			withFormat {
				html {
					render(view:'index', model:[contentInstanceList: list, contentInstanceTotal: list.size()])
				}
				json {
					if (list){
						render list as JSON
					}
					else {
						response.status = 204
						render '[]'
					}
				}
			}
			return
		}
		redirect(action: "index", params: params)
	}
	@Secured(["permitAll"])
	def ingredientsList(Long id){
		def result = Content.get(id)
		if(result){
			render(result.ingredients as JSON)
			return
		}
		response.status = 204
		render '[]'
	}
	@Secured(["permitAll"])
	def show(Long id) {
		def contentInstance = Content.get(id)
		if (!contentInstance) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), id])
				redirect(action: "list")
			}
			json {
				response.sendError(404)
			}
		}
		return
		}
		withFormat {
			html {[
				contentInstance: contentInstance
			]}
			json {
				render contentInstance as JSON
			}
		}
	}

	def create() {
		[contentInstance: new Content(params)]
	}

    @Transactional
	def save() {
		def contentInstance = new Content(params)
		if (!contentInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							contentInstance: contentInstance
						]
					)
				}
				json {
					response.status = 400
					render contentInstance.errors as JSON
				}
			}
			return
		}
		attachIngredients(contentInstance);
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: 'content.label', default: 'Content'), contentInstance.id])
				redirect(
					action: "show",
					id: contentInstance.id
				)
			}
			json {
				response.status = 201
				render contentInstance as JSON
			}
		}
	}

	def edit(Long id) {
		def contentInstance = Content.get(id)
		if (!contentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), id])
			redirect(action: "list")
			return
		}
		[
			contentInstance: contentInstance,
		]
	}

    @Transactional
	def update(Long id, Long version) {
		def contentInstance = Content.get(id)
		if (!contentInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (contentInstance.version > version) {

				contentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: 'content.label', default: 'Content')] as Object[],
				"Another user has updated this Content while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								contentInstance: contentInstance
							]
						)
					}
					json {
						response.sendError(409)
					}
				}
				return
			}
		}
		contentInstance.properties = params
		if (!contentInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							contentInstance: contentInstance
						]
					)
				}
				json {
					response.status = 400
					render contentInstance.errors as JSON
				}
			}
			return
		}
		attachIngredients(contentInstance);
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'content.label', default: 'Content'), contentInstance.id])
				redirect(action: "show", id: contentInstance.id)
			}
			json {
				render contentInstance as JSON
			}
		}
	}

    @Transactional
	def delete(Long id) {
		def contentInstance = Content.get(id)
			if (!contentInstance) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			contentInstance.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: 'content.label', default: 'Content'), id])
					redirect(action: "list")
				}
				json {
					response.status = 204
					render ''
				}
			}
		}
		catch (DataIntegrityViolationException e) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'content.label', default: 'Content'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}

	/**
	* there is no easy way to put a bunch of values in the same array and sent it trough post
	* to deal with this this function finds the ingredient values and puts it in the same object
	* from the param data
	*/
	private List<Ingredient> parseParamsToIngredientsList(Content contentInstance){

		List<Ingredient> usrSelIngreds = new ArrayList<Ingredient>()

		String fieldName = "ingredientsChoice."
		// construct a sane data structure

		if(params[fieldName+"prepend"] instanceof String){
			// in this case only one ingredient is selected
			usrSelIngreds.add(new Ingredient(
				recipe: contentInstance,
				ingredient: Content.findById(params[fieldName+"content.id"]),
				prepend: params[fieldName+"prepend"],
				ammend: params[fieldName+"ammend"],
				preferedUnit: Unit.findById(params[fieldName+"unit.id"]),
				quantity: params[fieldName+"quantity"]
			));
			return usrSelIngreds
		}
		params[fieldName+"content.id"].eachWithIndex{ def id, int index ->
			usrSelIngreds.add(new Ingredient(
				recipe: contentInstance,
				ingredient: Content.findById(id),
				prepend: params[fieldName+"prepend"][index],
				ammend: params[fieldName+"ammend"][index],
				preferedUnit: Unit.findById(params[fieldName+"unit.id"][index]),
				quantity: params[fieldName+"quantity"][index]
			));
		}
		return usrSelIngreds
	}

	/**
	* this function attaches ingredients to a content in a logical way
	*/
	private void attachIngredients(Content contentInstance){

		List<Ingredient> usrSelIngreds = parseParamsToIngredientsList(contentInstance)

		// delete evrything thats not in params from content instance (allows user to delete ingredients)
		// otherwise delete the param (user input, because the old one contains more info)
		List<Ingredient> ingredients = new ArrayList<Ingredient>(contentInstance.ingredients)

		contentInstance.ingredients.each{
			if(!usrSelIngreds.remove(it)){
				ingredients.remove(it)
			}
		}

		// add new ones
		ingredients.addAll(usrSelIngreds)

		// delete the deselected
		contentInstance.ingredients.each{
			if(!ingredients.contains(it)){
				// don't care when
				it.delete()
			}
		}
		// overwrite with the correct ingredients
		contentInstance.ingredients = new TreeSet(ingredients)
	}
}
