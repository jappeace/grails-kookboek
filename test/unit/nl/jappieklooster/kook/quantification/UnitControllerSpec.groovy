package nl.jappieklooster.kook.quantification



import grails.test.mixin.*
import spock.lang.*

@TestFor(UnitController)
@Mock(Unit)
class UnitControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.unitInstanceList
            model.unitInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.unitInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def unit = new Unit()
            unit.validate()
            controller.save(unit)

        then:"The create view is rendered again with the correct model"
            model.unitInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            unit = new Unit(params)

            controller.save(unit)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/unit/show/1'
            controller.flash.message != null
            Unit.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def unit = new Unit(params)
            controller.show(unit)

        then:"A model is populated containing the domain instance"
            model.unitInstance == unit
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def unit = new Unit(params)
            controller.edit(unit)

        then:"A model is populated containing the domain instance"
            model.unitInstance == unit
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/unit/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def unit = new Unit()
            unit.validate()
            controller.update(unit)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.unitInstance == unit

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            unit = new Unit(params).save(flush: true)
            controller.update(unit)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/unit/show/$unit.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/unit/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def unit = new Unit(params).save(flush: true)

        then:"It exists"
            Unit.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(unit)

        then:"The instance is deleted"
            Unit.count() == 0
            response.redirectedUrl == '/unit/index'
            flash.message != null
    }
}
