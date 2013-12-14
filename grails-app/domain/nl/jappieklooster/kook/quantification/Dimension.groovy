package nl.jappieklooster.kook.quantification
import nl.jappieklooster.kook.Named
/**
* Stores informations about dimensions, for example mass or content.
* Binds these dimensions to their units. so mass could get kg or pounds and content could get liters or cm^3
*/
class Dimension extends Named{
	static hasMany = [units: Unit]
    static constraints = {
		units nullable:true
		name unique: true
    }
	String toString(){
		return name
	}
}
