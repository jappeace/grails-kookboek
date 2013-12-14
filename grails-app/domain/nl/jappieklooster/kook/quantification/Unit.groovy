package nl.jappieklooster.kook.quantification
import nl.jappieklooster.kook.Named
/** 
* Contains units of stuff, ie liters or cm^3,
* TODO: conversions from units to different units
*/
class Unit extends Named{
	String abbreviation
	Dimension dimension
	static belongsTo = Dimension
    static constraints = {
		name blank:false, unique: true
		abbreviation nullable:true
    }
	String toString(){
		return dimension.toString() + ": " + plural
	}
	String getAbbreviation(){
		if (abbreviation == null){
			return plural
		}
		return abbreviation
	}
}
