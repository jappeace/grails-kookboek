import nl.jappieklooster.kook.quantification.*
import nl.jappieklooster.kook.security.Role
import nl.jappieklooster.kook.security.User
import nl.jappieklooster.kook.security.UserRole
import nl.jappieklooster.Log
class BootStrap {
    def springSecurityService
    def init = { servletContext ->

		def RoleAdmin = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save()
		def RoleProducer = Role.findByAuthority("ROLE_CHEF") ?: new Role(authority: "ROLE_CHEF").save()
		def RoleCustomer =  Role.findByAuthority("ROLE_CUSTOMER") ?: new Role(authority: "ROLE_CUSTOMER").save()

		def startUsers = [
			[username:"admin", role: RoleAdmin],
			[username:"chef", role:RoleProducer],
			[username:"bezoeker", role: RoleCustomer],
		]

		def users = User.list() ?: []

		if(!users){
			startUsers.each{ i ->
				def user = new User(
					username: i['username'],
					password: "pass",
					enabled: true
				)
				if(user.validate()){
					Log.write "creating user {0}", i['username']
					user.save(flush:true)
					UserRole.create user, i['role']
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
