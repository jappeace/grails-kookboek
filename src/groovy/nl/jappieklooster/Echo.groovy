package nl.jappieklooster
import org.apache.commons.lang3.text.WordUtils

/** A simple string manipulation class */
class Echo{

	private Echo(){}
	/** also lowers evrything else */
	public static String UpperCaseFirst(String input){
		return WordUtils.capitalize(input.toLowerCase())
	}	

}
