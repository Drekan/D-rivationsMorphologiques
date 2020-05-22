/* Cette classe représente les clauses présentes dans les règles.
 * La clause est stoquée sous forme de chaîne de carractères dans l'attribut _value.
 * 
 * Exemple de clause :
 * Soit la règle *er => *eur
 * alors "*er" et "*eur" sont les clauses de la règle.
 */
public class Clause {
	
	//-----ATTRIBUTES------
	private String _value;
	
	
	//-----METHODS---------
	public Clause(String v) {
		this._value = v.strip();
	}
	
	public String getValue() {
		return _value;
	}
	
	public void setValue(String v) {
		if(!v.isBlank()) {
			this._value = v.strip();
		}
	}
	
	public boolean isSuffix(){
		return !_value.isBlank() && _value.charAt(0) == '*';
	}
	
	public boolean doesMatch(String word) {
		boolean match = false;
		
		if(isSuffix() && word.length() >= _value.substring(1).length()) {
			String clauseSuffix = _value.substring(1);
			String wordSuffix = word.substring(word.length() - clauseSuffix.length());
			
			match = clauseSuffix.equals(wordSuffix);
		}
		
		return match;
	}
	
	
	
	
	
}
