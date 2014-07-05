package nl.jappieklooster.kook.quantification

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
@Transactional(readOnly = true)
class UnitController {

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
		def list = Unit.list(params)
		def listObject = [unitInstanceList: list, unitInstanceTotal: Unit.count()]
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

	def list() {
		redirect(action: "index", params: params)
	}

	def show(Long id) {
		def unitInstance = Unit.get(id)
		if (!unitInstance) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), id])
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
				unitInstance: unitInstance
			]}
			json {
				render unitInstance as JSON
			}
		}
	}

	def create() {
		[unitInstance: new Unit(params)]
	}

    @Transactional
	def save() {
		def unitInstance = new Unit(params)
		if (!unitInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							unitInstance: unitInstance
						]
					)
				}
				json {
					response.status = 400
					render unitInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: 'unit.label', default: 'Unit'), unitInstance.id])
				redirect(
					action: "show",
					id: unitInstance.id
				)
			}
			json {
				response.status = 201
				render unitInstance as JSON
			}
		}
	}

	def edit(Long id) {
		def unitInstance = Unit.get(id)
		if (!unitInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), id])
			redirect(action: "list")
			return
		}
		[unitInstance: unitInstance]
	}

    @Transactional
	def update(Long id, Long version) {
		def unitInstance = Unit.get(id)
		if (!unitInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (unitInstance.version > version) {

				
				unitInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: 'unit.label', default: 'Unit')] as Object[],
				"Another user has updated this Unit while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								unitInstance: unitInstance
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
		unitInstance.properties = params
		if (!unitInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							unitInstance: unitInstance
						]
					)
				}
				json {
					response.status = 400
					render unitInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'unit.label', default: 'Unit'), unitInstance.id])
				redirect(action: "show", id: unitInstance.id)
			}
			json {
				render unitInstance as JSON
			}
		}
	}

    @Transactional
	def delete(Long id) {
		def unitInstance = Unit.get(id)
			if (!unitInstance) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'unit.label', default: 'Unit'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			unitInstance.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), id])
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
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'unit.label', default: 'Unit'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}
}
