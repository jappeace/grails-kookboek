package nl.jappieklooster.kook

class Product {
	Unit unit
	String description
    static constraints = {
    }
	static mapping = {
		description column: "description", sqlType: "MediumText"	
	}
}
