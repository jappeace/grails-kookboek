package nl.jappieklooster

/** A simple string manipulation class */
class Echo{

	private Echo(){}
	/** also lowers evrything else */
	public static String UpperCaseFirst(String input){
		if(input.length() < 1){
			return input
		}
		return input.substring(0,1).toUpperCase()+input.toLowerCase().substring(1)
	}

}
