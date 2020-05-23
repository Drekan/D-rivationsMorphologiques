import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import requeterrezo.*;


public class DerivationEngine {
	private ArrayList<Rule> _ruleSet;

	private HashMap<String,ArrayList<String>> _derivations;

	public DerivationEngine() {
		_ruleSet = new ArrayList<>();
		_derivations = new HashMap<>();
	}
	
	public DerivationEngine(String path) {
		_ruleSet = new ArrayList<>();
		_derivations = new HashMap<>();
		
		loadRules(path);
	}

	public int nbRules() {
		return _ruleSet.size();
	}

	public void removeRule(int index) {
		_ruleSet.remove(index);
	}

	public void clearRules() {
		_ruleSet.clear();
	}

	public void clearDerivations() {
		_derivations.clear();
	}

	public ArrayList<String> getDerivations(String mot){
		return _derivations.get(mot);
	}

	public void loadRules(String path) {
		File ruleFile = new File(path);
		if(ruleFile.exists()) {
			try {
				Scanner reader = new Scanner(ruleFile);
				while (reader.hasNextLine()) {
					String rule = reader.nextLine();
					if(!rule.isBlank()) {
						_ruleSet.add(new Rule(rule));
					}
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
	
	public boolean exists(String word) {
		RequeterRezo rezo = new RequeterRezoDump();
		Resultat resultatRequete = rezo.requete(word);
		Mot mot = resultatRequete.getMot();
		
		return mot != null;
	}
	
	public void run(String word) {
		if(!_ruleSet.isEmpty()) {
			for(Rule rule : _ruleSet) {
				String applicationResult = rule.apply(word);
				if(!applicationResult.isBlank() && exists(applicationResult)) {
					
					if(!_derivations.containsKey(word)) {
						_derivations.put(word, new ArrayList<>());
					}
					
					if(!_derivations.get(word).contains(applicationResult)) {
						_derivations.get(word).add(applicationResult);
					}
				}
			}
		}
	}
	
	public void run(ArrayList<String> words) {
		for(String word : words) {
			run(word);
		}
	}
	
	public void clear() {
		clearRules();
		clearDerivations();
	}
	
	public String toString() {
		String display = "";
		
		display += "----Règles----\n";
		for(Rule r : _ruleSet) {
			display += r.toString() + "\n";
		}
		
		display += "\n----Dérivations----\n";
		for(String k : _derivations.keySet()) {
			display += k + " => " + _derivations.get(k) + "\n";
		}
		
		return display;
	}
}
