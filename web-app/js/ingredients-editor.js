if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			var urlPrepend = "/kook/";
			var units;
			$.getJSON(
				urlPrepend+"unit/list",
				{},
				function(result){
					alert(results);
					units = JSON.parse(result);
				}
			);

			var createRow = function(based){
			};
		});
	})(jQuery);

}
