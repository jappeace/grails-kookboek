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
					alert(units.printElement("unit.id"));
				}
			);

			var createRow = function(based){
			};
		});
	})(jQuery);

}
