import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		DerivationEngine engine = new DerivationEngine("rules.txt");
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Veuillez entrer les mots à dériver, tous séparés d'un ';'.");
		System.out.println("Exemple : manger;papillon;tartine;océan  (Les espaces ne sont pas importants)");
		System.out.print("$ ");
		
		String mots = sc.nextLine();
		
		for(String word : mots.split(";")) {
			engine.run(word.strip());
		}
		
		System.out.println(engine);
	}

}
