package nl.jappieklooster.kook.book

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

import nl.jappieklooster.Log
@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class ContentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Content.list(params), model:[contentInstanceCount: Content.count()]
    }

	@Secured(["permitAll"])
    def show(Content contentInstance) {
        respond contentInstance
    }

    def create() {
        respond new Content(params)
    }
	private attachIngredients(Content contentInstance){

		List<Ingredient> usrSelIngreds = new ArrayList<Ingredient>()

		// construct a sane data structure
		params["ingredientChoice"].each{
			usrSelIngreds.add(
				new Ingredient(
					recipe: contentInstance,
					ingredient: Content.findById(it)
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

    @Transactional
    def save(Content contentInstance) {
        if (contentInstance == null) {
            notFound()
            return
        }

        if (contentInstance.hasErrors()) {
            respond contentInstance.errors, view:'create'
            return
        }

		attachIngredients(contentInstance)
        contentInstance.save flush:true
        request.withFormat {

            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contentInstance.label', default: 'Content'), contentInstance.id])
                redirect contentInstance
            }
            '*' { respond contentInstance, [status: CREATED] }
        }
    }

    def edit(Content contentInstance) {
        respond contentInstance
    }

    @Transactional
    def update(Content contentInstance) {
        if (contentInstance == null) {
            notFound()
            return
        }

        if (contentInstance.hasErrors()) {
            respond contentInstance.errors, view:'edit'
            return
        }

		attachIngredients(contentInstance)
        contentInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Content.label', default: 'Content'), contentInstance.id])
                redirect contentInstance
            }
            '*'{ respond contentInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Content contentInstance) {

        if (contentInstance == null) {
            notFound()
            return
        }

        contentInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Content.label', default: 'Content'), contentInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contentInstance.label', default: 'Content'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
