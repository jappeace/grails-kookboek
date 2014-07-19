package nl.jappieklooster.kook

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import nl.jappieklooster.kook.book.Content
import nl.jappieklooster.kook.book.Ingredient
import nl.jappieklooster.kook.book.Category
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		Connection connection = driver.connect("jdbc:mysql://"+params["uri"]+":3306/"+params["name"], properties);
		Sql sql = new Sql(connection);
		sql.eachRow(
			"SELECT * FROM ingredients ingred JOIN names ON name_id = names.id ORDER BY name"
		){
			if(it.recipe_id != null){
				return
			}
		}
		redirect (action:"index", params:params)
	}
}
