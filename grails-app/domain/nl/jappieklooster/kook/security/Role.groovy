package nl.jappieklooster.kook.security

class Role {

	String authority
	String toString(){
		return authority
	}
	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
