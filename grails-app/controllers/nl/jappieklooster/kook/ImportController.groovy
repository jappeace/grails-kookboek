package nl.jappieklooster.kook

import grails.converters.JSON
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import nl.jappieklooster.kook.book.Content
import nl.jappieklooster.kook.book.Ingredient
import nl.jappieklooster.kook.book.Category
import nl.jappieklooster.kook.quantification.Unit
import groovy.sql.Sql

@Secured(["ROLE_ADMIN"])
@Transactional(readOnly = true)
class ImportController {
	Sql sql

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

		sql = connect()
		List<Content> ingredRecipes = new LinkedList<>()
		Map<Integer, Content> ingredients = new HashMap<>()
		sql.eachRow(
			"SELECT ingred.id as id, recipe_id, names.name as name, units.name as unitname, description FROM ingredients ingred JOIN units ON unit_id = units.id JOIN names ON name_id = names.id  ORDER BY names.name"
		){ def row ->
			Content content = new Content(
				name: row.name,
				description: row.description,
				unit: Unit.findByName(row.unitname)
			)
			content.save()
			ingredients[row.id] = content // required to bind recipes to 'our' contents with the bridge beign their id
			if(row.recipe_id != null){
				ingredRecipes.add(content)
			}
		}

		List<Category> categories = readCategories()
		sql.eachRow(
			"SELECT recipes.id as id, names.name as name, description FROM recipes JOIN names ON name_id = names.id  ORDER BY names.name"
		){ def row ->
			Content content = ingredRecipes.find{it.name == row.name && it.description == row.description}
			if(content){
				attachIngredients(content, row.id, ingredients)
				bindCategories(content,categories)
				return
			}
			content = new Content(
				name: row.name,
				description: row.description,
			)
			content.save()
			attachIngredients(content, row.id, ingredients)
			bindCategories(content,categories)
		}


		redirect (action:"index", params:params)
	}
	private void bindCategories(Content content, List<Category> categories){
		content.save(
			flush:true
		)
		categories.each{
			it.addToContents(content)
		}
	}
	private void attachIngredients(Content content, int id, Map<Integer, Content> ingredients){
		sql.eachRow(
			"SELECT ingredient_id, addendum as ammend, amount as quantity FROM ingredients_in_recipes WHERE recipe_id='"+id+"'"
		){ def row ->
			new Ingredient(
				recipe: content,
				ingredient: ingredients[row.ingredient_id],
				ammend:row.ammend,
				quantity:row.quantity
			).save()
		}
	}

	private List<Category> readCategories(){
		List<Category> result = new LinkedList<>()
		if(!params.containsKey("recipeCategories")){
			return result
		}
		if(params["recipeCategories"] instanceof String){
			result.add(Category.get(params["recipeCategories"]))
			return result
		}
		params["recipeCategories"].each{
			result.add(Category.get(it))
		}
		return result
	}
	private Sql connect(){
		def driver = Class.forName("com.mysql.jdbc.Driver").newInstance();
		Properties properties = new Properties();
		properties.put("user",params["name"])
		properties.put("password", params["pass"])
		return new Sql(driver.connect("jdbc:mysql://"+params["uri"]+":3306/"+params["name"], properties));
	}
}
