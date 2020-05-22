
public class Main {

	public static void main(String[] args) {
//		Clause clauseTest = new Clause("*er");
//		
//		System.out.println(clauseTest.doesMatch("manger"));
		
		Rule ruleTest = new Rule("*er => *eur");
		
		System.out.println(ruleTest.doesMatch("manger"));
	}

}
