package nl.jappieklooster.kook.book

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
@Transactional(readOnly = true)
class CategoryController {

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
		def list = Category.list(params)
		def listObject = [categoryInstanceList: list, categoryInstanceTotal: Category.count()]
		withFormat {
			html listObject
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
	}
	@Secured(["permitAll"])
	def choose(){
		def listObject = [categoryInstanceList: Category.list()]
		withFormat{
			html listObject
		}
	}

	def list() {
		redirect(action: "index", params: params)
	}

	def show(Long id) {
		def categoryInstance = Category.get(id)
		if (!categoryInstance) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
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
				categoryInstance: categoryInstance
			]}
			json {
				render categoryInstance as JSON
			}
		}
	}

	def create() {
		[categoryInstance: new Category(params)]
	}


    @Transactional
	def save() {
		def categoryInstance = new Category(params)
		if (!categoryInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							categoryInstance: categoryInstance
						]
					)
				}
				json {
					response.status = 400
					render categoryInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
				redirect(
					action: "show",
					id: categoryInstance.id
				)
			}
			json {
				response.status = 201
				render categoryInstance as JSON
			}
		}
	}

	def edit(Long id) {
		def categoryInstance = Category.get(id)
		if (!categoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "list")
			return
		}
		[categoryInstance: categoryInstance]
	}

    @Transactional
	def update(Long id, Long version) {
		def categoryInstance = Category.get(id)
		if (!categoryInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (categoryInstance.version > version) {

				
				categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: 'category.label', default: 'Category')] as Object[],
				"Another user has updated this Category while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								categoryInstance: categoryInstance
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
		categoryInstance.properties = params
		if (!categoryInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							categoryInstance: categoryInstance
						]
					)
				}
				json {
					response.status = 400
					render categoryInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
				redirect(action: "show", id: categoryInstance.id)
			}
			json {
				render categoryInstance as JSON
			}
		}
	}

    @Transactional
	def delete(Long id) {
		def categoryInstance = Category.get(id)
			if (!categoryInstance) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			categoryInstance.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
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
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}
}
