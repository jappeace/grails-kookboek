import nl.jappieklooster.kook.security.Role
import nl.jappieklooster.kook.security.User
import nl.jappieklooster.kook.security.UserRole

class BootStrap {
    def springSecurityService
    def init = { servletContext ->

		def RoleAdmin = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save()
		def RoleProducer = Role.findByAuthority("ROLE_PRODUCER") ?: new Role(authority: "ROLE_PRODUCER").save()
		def RoleCustomer =  Role.findByAuthority("ROLE_CUSTOMER") ?: new Role(authority: "ROLE_CUSTOMER").save()

		def startUsers = [
			[username:"baal", role: RoleAdmin],
			[username:"jimmy", role: RoleCustomer],
			[username:"marvin", role: RoleProducer],
			[username:"jappie", role: RoleProducer]
		]

		def users = User.list() ?: []

		if(!users){
			startUsers.each{ i ->
				def user = new User(
					username: i['username'],
					password: springSecurityService.encodePassword("pass"),
					enabled: true
				)
				if(user.validate()){
					println "creating user ${i['username']}"
					user.save(flus:true)
					UserRole.create user, i['role']
				}
			}
		}
    }
    def destroy = {
    }
}
