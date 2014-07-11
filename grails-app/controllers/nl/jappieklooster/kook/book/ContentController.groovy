package nl.jappieklooster.kook.book

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.util.logging.*
import nl.jappieklooster.kook.quantification.Unit

@Log
@Secured(["ROLE_ADMIN"])
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
			'POST',
			'DELETE'
		]
	]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def list = Content.list(params)
		def listObject = [contentInstanceList: list, contentInstanceTotal: Content.count()]
		withFormat {
			html listObject
			json {
				if (list){
					render list as JSON
				}
				else {
					response.status = 204
					render ''
				}
			}
		}
	}

	def list() {
		redirect(action: "index", params: params)
	}
	def ingredientsList(Long id){
		render Content.get(id).ingredients as JSON
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
		println params
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
	private void attachIngredients(Content contentInstance){

		List<Ingredient> usrSelIngreds = new ArrayList<Ingredient>()

		String fieldName = "ingredientsChoice."
		// construct a sane data structure
		params[fieldName+"content.id"].eachWithIndex{ def id, int index ->
			usrSelIngreds.add(
				new Ingredient(
					recipe: contentInstance,
					ingredient: Content.findById(id),
					prepend:params[fieldName+"prepend"][index],
					ammend:params[fieldName+"ammend"][index],
					preferedUnit:Unit.findById(params[fieldName+"unit.id"][index]),
					quantity:params[fieldName+"quantity"][index]
				)
			);
		}
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
