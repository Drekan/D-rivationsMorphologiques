public class Main {

	public static void main(String[] args) {
		DerivationEngine engine = new DerivationEngine("rules.txt");
		
		engine.run("mang");
		
		System.out.println(engine);
	}

}
