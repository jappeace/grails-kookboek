import nl.jappieklooster.kook.quantification.*
import nl.jappieklooster.kook.security.Role
import nl.jappieklooster.kook.security.User
import nl.jappieklooster.kook.security.UserRole
import nl.jappieklooster.kook.book.Category
import nl.jappieklooster.kook.book.Content
import nl.jappieklooster.kook.book.Ingredient
import nl.jappieklooster.Log
class BootStrap {
    def springSecurityService
    def init = { servletContext ->
		def roles = [
			admin:Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save(),
			chef:Role.findByAuthority("ROLE_CHEF") ?: new Role(authority: "ROLE_CHEF").save(),
			guest:Role.findByAuthority("ROLE_GUEST") ?: new Role(authority: "ROLE_GUEST").save() 
		]
		def users = [
			admin:
				new User(
					username: "admin",
					password: "pass",
					enabled: true
				),
			chef: 
				new User(
					username: "chef",
					password: "pass",
					enabled: true
				),
			visitor:
				new User(
					username: "bezoeker",
					password: "pass",
					enabled: true
				)
		]
		def startUsers = [
			[
				user: users.admin, 
				role: roles.admin
			], [
				user: users.chef, 
				role: roles.chef
			], [
				user: users.visitor, 
				role: roles.guest
			],
		]
		if(!User.list()){
			startUsers.each{ i ->
				if(i.user.validate()){
					Log.write "creating user {0}", i.user['username']
					i.user.save(flush:true)
					UserRole.create i.user, i.role
				}
			
			}
		}
		def dimensions =  [
			weight: new Dimension(name: "Gewicht"),
			volume: new Dimension(name: "Volume", plural: "Volumes"),
			things: new Dimension(name: "Ding")
		]
		storeIfNoTypeElements(dimensions, Dimension)
		def units = [
			gr: new Unit(
				name: "gram", 
				plural:"grammen", 
				abbreviation:"gr.", 
				dimension: dimensions.weight
			),
			ml: new Unit(
				name: "mililiter", 
				plural:"mililiters", 
				abbreviation:"ml.", 
				dimension: dimensions.volume
			),
			cl: new Unit(
				name: "centiliter", 
				plural:"centiliters", 
				abbreviation:"cl.", 
				dimension: dimensions.volume
			),
			dl: new Unit(
				name: "deciliter", 
				plural:"deciliters", 
				abbreviation:"dl.", 
				dimension: dimensions.volume
			),
			l: new Unit(
				name: "liter", 
				plural:"liters", 
				abbreviation:"l.", 
				dimension: dimensions.volume
			),
			toes: new Unit(
				name: "teen", 
				plural:"tenen",  
				dimension: dimensions.things
			),
			leaf: new Unit(
				name: "blad", 
				plural:"blaadjes",  
				dimension: dimensions.things
			),
			stick: new Unit(
				name: "stok", 
				plural:"stokken",  
				dimension: dimensions.things
			),
			package: new Unit(
				name: "verpakking", 
				dimension: dimensions.things
			)
		]	
		storeIfNoTypeElements(units, Unit)
		// add some categories
		def categories = [
			koud: new Category(name: "koud", author: users.admin),
			warm: new Category(name: "warm", author: users.admin),
		]
		categories.dessert = new Category(name: "dessert", parent: categories.koud, author: users.admin)
		categories.voor = new Category(name: "voorgerecht", parent: categories.koud, author: users.admin)
		storeIfNoTypeElements(categories, Category)

		// add some content
		def contents =[
			aardbei:	new Content(
				name: "aardbei",
				unit: units.gr
			),
			crfr:		new Content(
				name: "cr\u00E8me fra\u00CEche",
				unit: units.gr,
				plural:"cr\u00E8me fra\u00CEches" 
			),
			eidooi:		new Content(
				name: "eidooier",
				unit: units.gr,
				description: "Ei dooier of ei geel"
			),
			frdebois:	new Content(
				name: "fraiche de bois",
				unit: units.dl
			),
			gelatine:	new Content(
				name: "gelantine",
				unit: units.leaf,
				plural:"gelantines"
			),
			sugar:		new Content(
				name: "suiker",
				unit: units.gr,
				plural: "suikers"
			)
		]
		contents.aardbeienTaart = new Content(
			name: "aardbeientaart",
			description:"harde wener bodem.  900 gram aardbeien, 50 gram frac\u00CEhe de bois.  Verwarmen en hierin 10 blaadjes gelatine oplossen.  Als dit koud is mengen met de eidooier, crm\u00E8e frac\u00CEhe en suiker.  Dit storten op de harde wener bodem en afbakken op 90 graden 1,5 uur."
		)
		contents.each{ key, content ->
			content.author = users.chef
		}
		storeIfNoTypeElements(contents, Content)

		[
			new Ingredient(
				data: contents.aardbei,
				prepend: "diepvries"
			), 
			new Ingredient(
				data: contents.crfr,
			), 
			new Ingredient(
				data: contents.eidooi,
			), 
			new Ingredient(
				data: contents.frdebois,
				prepend: "scheutje"
			), 
			new Ingredient(
				data: contents.gelatine,
			), 
			new Ingredient(
				data: contents.sugar,
			) 
		].each{
			contents.aardbeienTaart.addToIngredients(it)
		}
		categories.koud.addToContents(contents.aardbeienTaart)
    }
    def destroy = {
    }
    private boolean storeIfNoTypeElements(LinkedHashMap list, Class type){
        Log.write "Started with adding the {0} classes.", type.getName()
        
        if(type.list()){
			Log.write "this type already contains elements, exiting"
			return
        }

        int saved = 0;
        int failed = 0;
        
		list.each{ key, i ->
			if(i.validate()){
				i.save(flush:true, failOnError: true)
				saved++
			}
			else{
				Log.panic "'{0}' could not be created!", i
				i.errors.allErrors.each {
					Log.panic "because of {0}", it
				}
				failed++
			}
		}
		//if one fails, let the message appear more clearly
		def resultStr ="When saving the {0} classes: {1} were saved, {2} failed to be saved."
		if(failed > 0){
			Log.panic resultStr, type.getName(), saved, failed
		}else{
			Log.write resultStr, type.getName(), saved, failed
		}
		return failed == 0
    }
}
