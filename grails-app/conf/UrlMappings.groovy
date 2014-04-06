class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
			// apply constraints here
			}
		}
		name api0: "/api/$controller/$id"(parseRequest:true){
			action = [GET: "show", PUT: "update", DELETE: "delete"]
			constraints {
				id(matches:/\d+/)
			}
		}
		name api1: "/api/$controller"(parseRequest:true){
			action = [GET: "list", POST: "save"]
		}
		name api2: "/api/$controller/$action"(parseRequest:true)
		name api3: "/api/$controller/$action/$id"(parseRequest:true)
		"/"(view:"/category/choose")
		"500"(view:'/error')
	}
}
