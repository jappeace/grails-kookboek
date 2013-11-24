package nl.jappieklooster.kook

class Unit {
	String name
	String abbreviation
	String plural
    static constraints = {
		name blank:false, unique: true
    }
}
