public class Test {
	public static void main(String[] args) {
		Grille grille1 = new Grille(10, new char[10][10], 5, new Bateau[2][5], 1);
		Grille grille2 = new Grille(10, new char[10][10], 5, new Bateau[2][5], 2);
		grille1.definitionBateau(grille1.getJoueur());
		for (int i=0;i<grille1.grille.length;i++) {
			for (int j=0; j<grille1.grille[0].length;j++){
				if (grille1.bateauPresent(i, j)) {
					System.out.print(grille1.grille[i][j]);
				}
			}
		}
		//Fenetre fenetre = new Fenetre(grille1, grille2);
		//fenetre.repaint();
	}

}