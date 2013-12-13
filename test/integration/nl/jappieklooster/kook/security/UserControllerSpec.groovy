package nl.jappieklooster.kook.security


import grails.test.mixin.*
import spock.lang.*

/**
 *
 */
@TestFor(UserController)
class UserControllerTests extends GroovyTestCase {

    def populateValidParams(params) {
        assert params != null
		params["username"] = "henk"
		params["password"] = "henks"
		params["enabled"] = "on" 
		params["_accountLocked"] = "" 
		params["_passwordExpired"] = "" 
		params["_enabled"] = "" 
		params["roles"] = [1, 3]
    }
    def setup() {
    }

    def cleanup() {
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            user = new User(params)
            controller.save(user)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/user/show/1'
            controller.flash.message != null
            User.count() == 1
    }

    void "Test the update action performs an update on a valid domain instance"() {

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            user = new User(params).save(flush: true)
            controller.update(user)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/user/show/$user.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def user = new User(params).save(flush: true)

        then:"It exists"
            User.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(user)

        then:"The instance is deleted"
            User.count() == 0
            response.redirectedUrl == '/user/index'
            flash.message != null
    }
}
