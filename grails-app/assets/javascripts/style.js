//= require jquery
//= require_self
if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			// check wether to aply default bootstrap styling
			// allows escaping of styling by adding the ignore-defaults class to a element
			var isDefaultElement = function(element){
				return !element.hasClass("ignore-defaults");
			};

			// some basic bootstrap fixes that make evrything look nicer
			// and reduce a lot of code
			$("table").each(function() {

				if(isDefaultElement($(this))){
					$(this).addClass("table-striped table table-hover");
				}

			});
			$("img").each(function() {

				if(isDefaultElement($(this))){
					$(this).addClass("img-thumbnail");
				}

			});
			$("fieldset").each(function() {

				if(isDefaultElement($(this))){
					$(this).addClass("form-group");
				}

			});
			$("input, textarea, select").each(function() {

				if(isDefaultElement($(this))){
					$(this).addClass("form-control");
				}

			});
			$("input[type=submit], input[type=button], button").each(function() {

				if(isDefaultElement($(this))){

					$(this).removeClass("form-control");
					$(this).addClass("btn btn-default");

				}
			});
			// nice icons will be automaticly atached for certain paths
			// I can't imagine why somoene does not want this so I did not
			// create an ignore posibility
			var atachIcon = function(linkPath, iconName, operator){
				operator = typeof operator !== 'undefined' ? operator : "=";
				$("a[href"+operator+"\""+linkPath+"\"]").each(function(){
					$(this).prepend("<span class='glyphicon glyphicon-"+iconName+"'></span>");
				});
			};
			atachIcon("/kook/logout/index", "off");
			atachIcon("/kook/login/index", "play");
			atachIcon("create", "plus", "$=");
		});

	})(jQuery);

}

