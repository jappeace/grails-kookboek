package nl.jappieklooster.kook.security

import static org.springframework.http.HttpStatus.*
import nl.jappieklooster.Log
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import nl.jappieklooster.kook.book.Content

@Secured(["ROLE_ADMIN", "ROLE_CHEF"])
@Transactional(readOnly = true)
class CookController {

	/** the landing page which shows what you can do as an admin*/
	def index(){ }
	/** lists all recipes to allow them to be editet*/
	def listAllContent() {
		respond Content.list(params)
	}

}
