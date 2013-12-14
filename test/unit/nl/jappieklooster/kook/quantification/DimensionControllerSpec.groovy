package nl.jappieklooster.kook.quantification



import grails.test.mixin.*
import spock.lang.*

@TestFor(DimensionController)
@Mock(Dimension)
class DimensionControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.dimensionInstanceList
            model.dimensionInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.dimensionInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def dimension = new Dimension()
            dimension.validate()
            controller.save(dimension)

        then:"The create view is rendered again with the correct model"
            model.dimensionInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            dimension = new Dimension(params)

            controller.save(dimension)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/dimension/show/1'
            controller.flash.message != null
            Dimension.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def dimension = new Dimension(params)
            controller.show(dimension)

        then:"A model is populated containing the domain instance"
            model.dimensionInstance == dimension
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def dimension = new Dimension(params)
            controller.edit(dimension)

        then:"A model is populated containing the domain instance"
            model.dimensionInstance == dimension
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/dimension/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def dimension = new Dimension()
            dimension.validate()
            controller.update(dimension)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.dimensionInstance == dimension

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            dimension = new Dimension(params).save(flush: true)
            controller.update(dimension)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/dimension/show/$dimension.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/dimension/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def dimension = new Dimension(params).save(flush: true)

        then:"It exists"
            Dimension.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(dimension)

        then:"The instance is deleted"
            Dimension.count() == 0
            response.redirectedUrl == '/dimension/index'
            flash.message != null
    }
}
