package nl.jappieklooster.kook.security

import groovy.mock.interceptor.*
import grails.test.mixin.*
import spock.lang.*

@TestFor(UserController)
@Mock([User, UserRole])
class UserControllerSpec extends Specification {

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

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.userInstanceList
            model.userInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.userInstance!= null
    }

    void "Test the save action outer values"() {

        when:"The save action is executed with an invalid instance"
            def user = new User()
            user.validate()
            controller.save(user)

        then:"The create view is rendered again with the correct model"
            model.userInstance!= null
            view == 'create'
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def user = new User(params)
            controller.show(user)

        then:"A model is populated containing the domain instance"
            model.userInstance == user
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def user = new User(params)
            controller.edit(user)

        then:"A model is populated containing the domain instance"
            model.userInstance == user
    }

    void "Test the update action rederict correctly"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/user/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def user = new User()
            user.validate()
            controller.update(user)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.userInstance == user
    }

    void "Test that the delete action rederict correctly with wrong input"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/user/index'
            flash.message != null
    }
}
class SecurityServiceStub{
	String encodePassword(String password){
		return password
	}
}
