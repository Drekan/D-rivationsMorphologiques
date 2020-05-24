import java.util.ArrayList;

public class Rule {
	private ArrayList<Clause> _leftClauseSet;
	private ArrayList<Clause> _rightClauseSet;
	
	
	private void fillClauseSet(ArrayList<Clause> set,String rulePart) {
		String clauses[] = rulePart.strip().split("&");
		for(String c : clauses) {
			set.add(new Clause(c.strip()));
		}
	}
	
	public Rule(String rule) {
		_leftClauseSet = new ArrayList<Clause>();
		_rightClauseSet = new ArrayList<Clause>();
		
		String[] splittedRule = rule.strip().split("=>");
		
		if(splittedRule.length == 2) {
			fillClauseSet(_leftClauseSet,splittedRule[0]);
			fillClauseSet(_rightClauseSet,splittedRule[1]);
		}
	}
	
	public ArrayList<Clause> getLeftPart(){
		return _leftClauseSet;
	}
	
	public ArrayList<Clause> getRightPart(){
		return _rightClauseSet;
	}
	
	public Clause getLeftClause(int i){
		return _leftClauseSet.get(i);
	}
	
	public Clause getRightClause(int i){
		return _rightClauseSet.get(i);
	}
	
	
	public String toString() {
		String ruleString = "";
		
		for(Clause c : _leftClauseSet) {
			ruleString += "(" + c.getValue() + ") ";
		}
		
		ruleString += " =>  ";
		
		for(Clause c : _rightClauseSet) {
			ruleString += "(" + c.getValue() + ") ";
		}
		
		return ruleString;
	}
	
	public boolean doesMatch(String word,String nature) {
		boolean match = !_leftClauseSet.isEmpty();
		
		for(Clause c : _leftClauseSet) {
			if(!c.doesMatch(word,nature)) {
				match = false;
			}
		}

		return match;
	}
	
	public String apply(String word, String nature) {
		String application = "";
		
		if(doesMatch(word,nature)) {
			String leftSuffix = "";
			for(Clause c : _leftClauseSet) {
				if(c.isSuffix()) {
					leftSuffix = c.getValue().substring(1);
				}
			}
			
			String rightSuffix = "";
			for(Clause c : _rightClauseSet) {
				if(c.isSuffix()) {
					rightSuffix = c.getValue().substring(1);
				}
			}
			
			application = word.substring(0,word.length() - leftSuffix.length()) + rightSuffix;
			//System.out.println("application : " + application);
		}
		
		return application;
	}
	
}
