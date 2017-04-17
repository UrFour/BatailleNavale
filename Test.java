public class Test {
	public static void main(String[] args) {
		Grille grille = new Grille(10, new char[10][10], 5, new Bateau[2][5], 1);
		grille.definitionBateau(grille.getJoueur());
		grille.afficherGrille();
	}

}