package nl.jappieklooster.kook

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import nl.jappieklooster.kook.book.Content
import nl.jappieklooster.kook.book.Ingredient
import nl.jappieklooster.kook.book.Category
import groovy.sql.Sql

@Secured(["ROLE_ADMIN"])
@Transactional(readOnly = true)
class ImportController {

	/**
	* allows the user to set the db information and categorie
	*/
    def index() { }
	/**
	* begin the import proces
	*/
	def begin(){
		println params
		flash.message = "success!"

		def driver = Class.forName("com.mysql.jdbc.Driver").newInstance();
		Properties properties = new Properties();
		properties.put("user",params["name"])
		properties.put("password", params["pass"])
		Sql sql = new Sql(driver.connect("jdbc:mysql://"+params["uri"]+":3306/"+params["name"], properties));
		sql.eachRow(
			"SELECT * FROM ingredients ingred JOIN names ON name_id = names.id JOIN unit ON unit_id = unit.id ORDER BY names.name"
		){
			// is done in recipe table
			if(it.recipe_id != null){
				return
			}
			new Content(
				name: it.names.name,
				description: it.description,
				unit: Unit.findByName(it.unit.name)
			).save()

		}
		redirect (action:"index", params:params)
	}
}
