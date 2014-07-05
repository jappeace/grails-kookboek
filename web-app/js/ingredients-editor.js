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
					var result = "<select name='"+name+"'>";
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
					result.forEach(function(jsonElement){
						$(".ingredients-choice").append("<li class='needs-callback'><button type='button' class='add-ingredient btn btn-default btn-xs select-button'>selecteer:</button>"+jsonElement.name+"</li>");
						$(".needs-callback .add-ingredient").on("click", function(){
							alert("clicked " + jsonElement.name);
						});
						$(".needs-callback").removeClass("needs-callback");
					});
				}
			);
		});
	})(jQuery);

}
