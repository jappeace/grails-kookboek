package nl.jappieklooster.kook.quantification

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class DimensionController {

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
		def list = Dimension.list(params)
		def listObject = [dimensionInstanceList: list, dimensionInstanceTotal: Dimension.count()]
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
		def dimensionInstance = Dimension.get(id)
		if (!dimensionInstance) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), id])
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
				dimensionInstance: dimensionInstance
			]}
			json {
				render dimensionInstance as JSON
			}
		}
	}

	def create() {
		[dimensionInstance: new Dimension(params)]
	}

    @Transactional
	def save() {
		def dimensionInstance = new Dimension(params)
		if (!dimensionInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							dimensionInstance: dimensionInstance
						]
					)
				}
				json {
					response.status = 400
					render dimensionInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimensionInstance.id])
				redirect(
					action: "show",
					id: dimensionInstance.id
				)
			}
			json {
				response.status = 201
				render dimensionInstance as JSON
			}
		}
	}

	def edit(Long id) {
		def dimensionInstance = Dimension.get(id)
		if (!dimensionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), id])
			redirect(action: "list")
			return
		}
		[dimensionInstance: dimensionInstance]
	}

    @Transactional
	def update(Long id, Long version) {
		def dimensionInstance = Dimension.get(id)
		if (!dimensionInstance) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (dimensionInstance.version > version) {

				
				dimensionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: 'dimension.label', default: 'Dimension')] as Object[],
				"Another user has updated this Dimension while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								dimensionInstance: dimensionInstance
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
		dimensionInstance.properties = params
		if (!dimensionInstance.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							dimensionInstance: dimensionInstance
						]
					)
				}
				json {
					response.status = 400
					render dimensionInstance.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimensionInstance.id])
				redirect(action: "show", id: dimensionInstance.id)
			}
			json {
				render dimensionInstance as JSON
			}
		}
	}

    @Transactional
	def delete(Long id) {
		def dimensionInstance = Dimension.get(id)
			if (!dimensionInstance) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			dimensionInstance.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), id])
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
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}
}
