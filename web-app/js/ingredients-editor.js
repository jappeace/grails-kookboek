if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			var urlPrepend = "/kook/api/";
			var units;
			$.getJSON(
					urlPrepend+"unit",
					{},
					function(result){
						alert(result);
						units = JSON.parse(result);
					}
				);

			var createRow = function(based){
			};
		});
	})(jQuery);

}
