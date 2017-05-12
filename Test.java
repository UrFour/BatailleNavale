public class Test {
	public static void main(String[] args) {
		Grille grille1 = new Grille(10, new char[10][10], 5, new Bateau[5], 1);
		grille1 = grille1.definitionBateau(grille1.getJoueur());
		//grille1.setGrille(8, 8, '*');
		new Affichage(grille1);
	}

}