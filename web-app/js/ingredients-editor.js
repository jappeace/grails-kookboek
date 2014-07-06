if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			var urlPrepend = "/kook/";
			var units = function(){
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
							var fieldName = "ingredientsChoice.";

							result += "<td><input class='form-control' name='"+fieldName+"prepend' /></td>";
							result += "<td><input class='form-control' name='"+fieldName+"quantity' type='number' required/></td>";
							result += "<td>"+units.printElement(fieldName+"unit.id", jsonElement.unit.id)+"</td>";
							result += "<td><input class='form-control' name='"+fieldName+"content.id' type='hidden' value='"+jsonElement.id+"'>"+jsonElement.name+"</td>";
							result += "<td><input class='form-control' name='"+fieldName+"ammend' /></td>";
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
