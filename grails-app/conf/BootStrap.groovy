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
    }
    def destroy = {
    }
}
