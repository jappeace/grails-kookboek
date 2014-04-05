<%=packageName ? "package ${packageName}\n\n" : ''%>import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON


class ${className}Controller {

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

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def list = ${className}.list(params)
		def listObject = [${propertyName}List: list, ${propertyName}Total: ${className}.count()]
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

	def create() {
		[${propertyName}: new ${className}(params)]
	}

	def save() {
		def ${propertyName} = new ${className}(params)
		if (!${propertyName}.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "create",
						model: [
							${propertyName}: ${propertyName}
						]
					)
				}
				json {
					response.status = 400
					render ${propertyName}.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
				redirect(
					action: "show",
					id: ${propertyName}.id
				)
			}
			json {
				response.status = 201
				render ${propertyName} as JSON
			}
		}
	}

	def show(Long id) {
		def ${propertyName} = ${className}.get(id)
		if (!${propertyName}) {
		withFormat {
			html {
				flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
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
				${propertyName}: ${propertyName}
			]}
			json {
				render ${propertyName} as JSON
			}
		}
	}

	def edit(Long id) {
		def ${propertyName} = ${className}.get(id)
		if (!${propertyName}) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
			redirect(action: "list")
			return
		}
		[${propertyName}: ${propertyName}]
	}

	def update(Long id, Long version) {
		def ${propertyName} = ${className}.get(id)
		if (!${propertyName}) {
			withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])
					redirect(action:"list")
				}
				json {
					response.sendError(404)
				}
			}
			return
		}
		if (version != null) {
			if (${propertyName}.version > version) {

				<% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
				${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure",
				[message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[],
				"Another user has updated this ${className} while you were editing")

				withFormat {
					html {
						render(
							view: "edit",
							model: [
								${propertyName}: ${propertyName}
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
		${propertyName}.properties = params
		if (!${propertyName}.save(flush: true)) {
			withFormat {
				html {
					render(
						view: "edit",
						model: [
							${propertyName}: ${propertyName}
						]
					)
				}
				json {
					response.status = 400
					render ${propertyName}.errors as JSON
				}
			}
			return
		}
		withFormat {
			html {
				flash.message = message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
				redirect(action: "show", id: ${propertyName}.id)
			}
			json {
				render ${propertyName} as JSON
			}
		}
	}

	def delete(Long id) {
		def ${propertyName} = ${className}.get(id)
			if (!${propertyName}) {
				withFormat {
				html {
					flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
					redirect(action: "list")
				}
				json {
					response.sendError(404)
				}
			}
		return
		}
		try {
			${propertyName}.delete(flush: true)
			withFormat {
				html {
					flash.message = message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
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
					flash.message = message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
					redirect(action: "show", id: id)
				}
				json {
					response.sendError(500)
				}
			}
		}
	}
}
