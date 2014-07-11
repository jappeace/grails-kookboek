package nl.jappieklooster.kook.book



import grails.test.mixin.*
import spock.lang.*

@TestFor(IngredientController)
@Mock(Ingredient)
class IngredientControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.ingredientInstanceList
            model.ingredientInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.ingredientInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def ingredient = new Ingredient()
            ingredient.validate()
            controller.save(ingredient)

        then:"The create view is rendered again with the correct model"
            model.ingredientInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            ingredient = new Ingredient(params)

            controller.save(ingredient)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/ingredient/show/1'
            controller.flash.message != null
            Ingredient.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def ingredient = new Ingredient(params)
            controller.show(ingredient)

        then:"A model is populated containing the domain instance"
            model.ingredientInstance == ingredient
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def ingredient = new Ingredient(params)
            controller.edit(ingredient)

        then:"A model is populated containing the domain instance"
            model.ingredientInstance == ingredient
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/ingredient/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def ingredient = new Ingredient()
            ingredient.validate()
            controller.update(ingredient)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.ingredientInstance == ingredient

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            ingredient = new Ingredient(params).save(flush: true)
            controller.update(ingredient)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/ingredient/show/$ingredient.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/ingredient/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def ingredient = new Ingredient(params).save(flush: true)

        then:"It exists"
            Ingredient.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(ingredient)

        then:"The instance is deleted"
            Ingredient.count() == 0
            response.redirectedUrl == '/ingredient/index'
            flash.message != null
    }
}
