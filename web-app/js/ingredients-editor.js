if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			var urlPrepend = "/kook/";
			var units = function(){
				var unitRows = [];
				this.addUnit = function(id, name){
					unitRows.push("<option value='"+id+"'>"+name+"</option>");
				};

				this.printElement = function(name){
					var result = "<select  class='form-control' name='"+name+"'>";
					unitRows.forEach(function(row){
						result += row;
					});
					result += "</select>";
					return result;
				};
				return this;
			}();
			$.getJSON(
				urlPrepend+"unit/list",
				{},
				function(result){
					result.forEach(function(jsonElement){
						units.addUnit(jsonElement.id, jsonElement.name);
					});
				}
			);

			$.getJSON(
				urlPrepend+"content/list",
				{},
				function(result){
					function ingredientSelect(jsonElement){

						var addCallbackString = "needs-add-callback";
						$(".ingredients-choice").append("<li class='"+addCallbackString+"'>"+
							"<span class='glyphicon glyphicon-plus-sign button-symbol add-ingredient'></span>"+jsonElement.name+"</li>");

						$("."+addCallbackString+" .add-ingredient").on("click", function(){
							$(this).parent().remove();

							var removeCallbackString = "needs-remove-callback";
							var result = "<tr class='"+removeCallbackString+"'>";

							result += "<td><input class='form-control' name='ingredients.prepend' /></td>";
							result += "<td><input class='form-control' name='ingredients.quantity' type='number' /></td>";
							result += "<td>"+units.printElement("ingredients.unit.id")+"</td>";
							result += "<td><input class='form-control' name='ingredients.conent.id' type='hidden' value='"+jsonElement.id+"'>"+jsonElement.name+"</td>";
							result += "<td><input class='form-control' name='ingredients.append' /></td>";
							result += "<td><span class='button-symbol glyphicon glyphicon-remove-sign remove-ingredient'></span></td>";
							$(".edit-ingredients").append(result+"</tr>");

							$("."+removeCallbackString +" .remove-ingredient").on("click", function(){
								$(this).parents("tr").remove();
								ingredientSelect(jsonElement);
							});
							$("."+removeCallbackString).removeClass(removeCallbackString);

						});
						$("."+addCallbackString).removeClass(addCallbackString);
					}
					result.forEach(ingredientSelect);
				}
			);
		});
	})(jQuery);

}
