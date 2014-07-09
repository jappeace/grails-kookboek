package nl.jappieklooster

/** A simple string manipulation class */
class Echo{

	private Echo(){}
	/** also lowers evrything else */
	public static String UpperCaseFirst(String input){
		return input.substr(0,1).toUpperCase()+input.toLowerCase().substr(1)
	}

}
