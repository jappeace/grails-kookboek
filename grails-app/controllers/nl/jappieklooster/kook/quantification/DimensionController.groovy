package nl.jappieklooster.kook.quantification



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DimensionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Dimension.list(params), model:[dimensionInstanceCount: Dimension.count()]
    }

    def show(Dimension dimensionInstance) {
        respond dimensionInstance
    }

    def create() {
        respond new Dimension(params)
    }

    @Transactional
    def save(Dimension dimensionInstance) {
        if (dimensionInstance == null) {
            notFound()
            return
        }

        if (dimensionInstance.hasErrors()) {
            respond dimensionInstance.errors, view:'create'
            return
        }

        dimensionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dimensionInstance.label', default: 'Dimension'), dimensionInstance.id])
                redirect dimensionInstance
            }
            '*' { respond dimensionInstance, [status: CREATED] }
        }
    }

    def edit(Dimension dimensionInstance) {
        respond dimensionInstance
    }

    @Transactional
    def update(Dimension dimensionInstance) {
        if (dimensionInstance == null) {
            notFound()
            return
        }

        if (dimensionInstance.hasErrors()) {
            respond dimensionInstance.errors, view:'edit'
            return
        }

        dimensionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Dimension.label', default: 'Dimension'), dimensionInstance.id])
                redirect dimensionInstance
            }
            '*'{ respond dimensionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Dimension dimensionInstance) {

        if (dimensionInstance == null) {
            notFound()
            return
        }

        dimensionInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Dimension.label', default: 'Dimension'), dimensionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dimensionInstance.label', default: 'Dimension'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
