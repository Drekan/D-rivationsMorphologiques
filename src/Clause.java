import java.util.ArrayList;

import requeterrezo.Mot;
import requeterrezo.Relation;
import requeterrezo.RequeterRezo;
import requeterrezo.RequeterRezoDump;
import requeterrezo.Resultat;
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
	
	public boolean isNatureConstraint() {
		return _value.contains("==");
	}
	
	
	public boolean doesMatch(String word) {
		boolean match = _value.isBlank();
		
		if(isSuffix() && word.length() >= _value.substring(1).length()) {
			String clauseSuffix = _value.substring(1);
			String wordSuffix = word.substring(word.length() - clauseSuffix.length());
			
			match = clauseSuffix.equals(wordSuffix);
		}
		
		if(isNatureConstraint()) {
			
			RequeterRezo rezo = new RequeterRezoDump();
			Resultat resultatRequete = rezo.requete(word);
			Mot mot = resultatRequete.getMot();
			
			if(mot != null) {
				ArrayList<Relation> voisins = mot.getRelationsSortantesTypees("r_pos");
				
				int poidsMax = 0;
				String nature = "";
				for(Relation voisin : voisins) {
					if(voisin.getPoids() > poidsMax) {
						poidsMax = voisin.getPoids();
						nature = voisin.getNomDestination();
					}
				}
				String natureFirstLetter = nature.substring(0, 1);
				match = natureFirstLetter.equals(_value.split("==")[1].strip());
			}


		}

		return match;
	}
	
	
	
	
	
}
