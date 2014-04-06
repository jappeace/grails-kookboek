package nl.jappieklooster.kook.security

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
@Transactional(readOnly = true)
class UserController {

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
		def list = User.list(params)
		def listObject = [userInstanceList: list, userInstanceTotal: User.count()]
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

	def show(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
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
				userInstance: userInstance
			]}
			json {
				render userInstance as JSON
			}
		}
	}

	def create() {
		[userInstance: new User(params)]
	}

    @Transactional
	def save() {
		def userInstance = new User(params)
		if (!userInstance.saveAndBindRoles(params.roles)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							userInstance: userInstance
						]
					)
				}
				json {
					response.status = 400
					render userInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
				redirect(
					action: "show",
					id: userInstance.id
				)
			}
			json {
				response.status = 201
				render userInstance as JSON
			}
		}
	}

	def edit(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}
		[userInstance: userInstance]
	}

    @Transactional
	def update(Long id, Long version) {
		def userInstance = User.get(id)
		if (!userInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (userInstance.version > version) {

				
				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: 'user.label', default: 'User')] as Object[],
				"Another user has updated this User while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								userInstance: userInstance
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
		userInstance.properties = params
		if (!userInstance.saveAndBindRoles(params.roles)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							userInstance: userInstance
						]
					)
				}
				json {
					response.status = 400
					render userInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
				redirect(action: "show", id: userInstance.id)
			}
			json {
				render userInstance as JSON
			}
		}
	}

    @Transactional
	def delete(Long id) {
		def userInstance = User.get(id)
			if (!userInstance) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			userInstance.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
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
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}
}
