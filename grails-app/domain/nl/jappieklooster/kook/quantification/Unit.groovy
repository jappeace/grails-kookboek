package nl.jappieklooster.kook.quantification
import nl.jappieklooster.kook.Named
/** 
* Contains units of stuff, ie liters or cm^3,
* TODO: conversions from units to different units
*/
class Unit extends Named{
	String abbreviation
	static belongsTo = Dimension
    static constraints = {
		name blank:false, unique: true
    }
}
