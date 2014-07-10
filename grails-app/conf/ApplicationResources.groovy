modules = {
	glyph {
		resource url:'/fonts/glyphicons-halflings-reqular.eot'
		resource url:'/fonts/glyphicons-halflings-reqular.svg'
		resource url:'/fonts/glyphicons-halflings-reqular.ttf'
		resource url:'/fonts/glyphicons-halflings-reqular.woff'
	}
    style {
		dependsOn 'jquery glyph'
		resource url:'/js/style.js'
		resource url:'/css/main.css'
		resource url:'/css/bootstrap.css'
    }
}
