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
		Connection con = connect()
		ResultSet result = con.prepareStatement(
			"SELECT * FROM ingredients ingred JOIN names ON name_id == id"
		).executeQuery()

		while(result.next()){
			println result.getString("name")
		}
		redirect (action:"index", params:params)
	}

	/**
	* connec to the target db
	*/
	private Connection connect(){
		Class.forName("com.mysql.jdbc.Driver");
		DriverManager.getConnection("jdbc:mysql://"+
		params["uri"]+"/"+
		params["name"]+"?" +
		"user="+params["name"]+
		"&password="+params["pass"]);
	}
}
