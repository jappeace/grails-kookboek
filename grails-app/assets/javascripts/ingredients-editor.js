//= require jquery
//= require_self
if (typeof jQuery !== 'undefined') {
	(function($) {
		"use strict";
		$(function() {
			var urlPrepend = "/";
			function Units(){
				var unitRows = [];
				this.addUnit = function(id, name){
					unitRows.push({id: id, name:name});
				};

				this.printElement = function(name, selectedId){
					var result = "<select  class='form-control' name='"+name+"'>";
					unitRows.forEach(function(row){
						var selected = "";
						if(selectedId == row.id){
							selected = "selected";
						}
						result += "<option value='"+row.id+"' " + selected +">"+row.name+"</option>";
					});
					result += "</select>";
					return result;
				};
			}
			var units = new Units();
			JSON.parse($("#available-units").html()).forEach(function(jsonElement){
				units.addUnit(jsonElement.id, jsonElement.name);
			});

			// make a mt string from null or undefined
			function format(string){
				if(string === null){
					return "";
				}
				if(string === 'undefined'){
					return "";
				}
				return string;
			}
			function createIngredientForm(ingredientInstance){
				var removeCallbackString = "needs-remove-callback";
				var result = "<tr class='"+removeCallbackString+"'>";
				var fieldName = "ingredientsChoice.";

				result += "<td><input class='form-control' name='"+fieldName+"prepend' "+
					"value='"+format(ingredientInstance.prepend)+"'/></td>";

				result += "<td><input class='form-control' name='"+fieldName+"quantity' type='number' "+
					"value='"+format(ingredientInstance.quantity)+"' required/></td>";

				if(ingredientInstance.preferedUnit === null){
					ingredientInstance.preferedUnit = {id: 0};
				}
				result += "<td>"+units.printElement(fieldName+"unit.id", ingredientInstance.preferedUnit.id)+"</td>";

				result += "<td><input class='form-control' name='"+fieldName+"content.id' type='hidden' "+
					"value='"+ingredientInstance.ingredient.id+"'>"+ingredientInstance.ingredient.name+"</td>";

				result += "<td><input class='form-control' name='"+fieldName+"ammend' "+
					"value='"+format(ingredientInstance.ammend)+"' /></td>";

				result += "<td><span class='big-font button-symbol glyphicon glyphicon-remove-sign remove-ingredient'></span></td>";

				$(".edit-ingredients").append(result+"</tr>");

				$("."+removeCallbackString +" .remove-ingredient").on("click", function(){
					$(this).parents("tr").remove();
					createIngredientLI(ingredientInstance.ingredient);
				});
				$("."+removeCallbackString).removeClass(removeCallbackString);
			}
			function createIngredientLI(contentInstance){

				var addCallbackString = "needs-add-callback";
				$(".ingredients-choice").append("<li class='"+addCallbackString+"'>"+
					"<span class='glyphicon glyphicon-plus-sign button-symbol add-ingredient'></span>"+contentInstance.name+"</li>");

				$("."+addCallbackString+" .add-ingredient").on("click", function(){
					$(this).parent().remove();
					createIngredientForm(
						{
							prepend:"",
							ammend:"",
							quantity:1,
							preferedUnit:contentInstance.unit,
							ingredient:contentInstance
						});
				});
				$("."+addCallbackString).removeClass(addCallbackString);
			}
			var instanceId = $("#contentInstance-id").html();
			var inform = [];
			$.getJSON(
				urlPrepend+"content/ingredientsList/"+instanceId,{},
				function(ingredients){
					ingredients.forEach(function(ingredient){
						inform.push(ingredient.ingredient.id);
						$.getJSON(
							urlPrepend+"content/show/"+ingredient.ingredient.id,
							{},
							function(contentInstance){
								ingredient.ingredient = contentInstance;
								createIngredientForm(ingredient);
							}
						);
					});

				}
			);
			console.debug(inform);
			$.getJSON(
				urlPrepend+"content/list/"+format(instanceId),
				{},
				function(allContents){

					allContents.forEach(function(content){
						if(inform.indexOf(content.id) === -1){
							createIngredientLI(content);
						}
					});
				}
			);
		});
	})(jQuery);

}
