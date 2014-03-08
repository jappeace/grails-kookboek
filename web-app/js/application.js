if (typeof jQuery !== 'undefined') {
	(function($) {
		$(function() {
			// atach filter function to jquery
			jQuery.fn.filterByText = function(textbox) {
				return this.each(function() {
					var select = this;
					var valueHolder = $(this).clone();
					var options = [];
					$(select).find('option').each(function() {
							options.push({value: $(this).val(), text: $(this).text()});
						}
					);
					$(select).data('options', options);

					$(textbox).bind('change keyup', function() {
							var result = $(select).val();
							var candidates = $(valueHolder).val();
							if(candidates !== null){
								$(valueHolder).val().forEach(function(element){
									$(select).find('option[value="'+element+'"]').each(function(){
										// cant use the foreach index because the index will change with removal (yay lists)
										var index = candidates.indexOf(element);
										candidates.splice(index, 1);
									});
								});
							}
							if(result === null){
								valueHolder.val(candidates);
							}else{
								valueHolder.val(result.concat(candidates));
							}
							var options = $(select).empty().data('options');
							var search = $.trim($(this).val());
							var regex = new RegExp(search,"gi");

							$.each(options, function(i) {
								var option = options[i];
								if(option.text.match(regex) !== null) {
									$(select).append(
											$('<option>').text(option.text).val(option.value)
									);
								}
							});
							$(select).val(valueHolder.val());
						}
					);
				});
			};
			//atach the filter function to inputs with specific classes
			$('.filterable').filterByText($('.filter'));
			// on form submit, make sure to let the filter be empty so all selected values are sent
			$('.filterable').parents('form').submit(function(){
				$('.filter').val("");
				$('.filter').trigger('keyup');
				alert("submit");
			});

		});
		// check wether to aply default bootstrap styling
		// allows escaping of styling by adding the ignore-defaults class to a element
		var isDefaultElement = function(element){
			return !element.hasClass("ignore-defaults");
		};

		// some basic bootstrap fixes that make evrything look nicer
		// and reduce a lot of code
		$("table").each(function() {

			if(isDefaultElement($(this))){
				$(this).addClass("table-striped");
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

	})(jQuery);

}

