import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Grille implements Serializable {
	private int taille;
	private char[][] grille;
	private int nbrBateaux;
	private Bateau[][] bateaux;
	private int joueur;
	
	public Grille(int taille, char[][] grille, int nbrBateaux, Bateau[][] bateaux, int joueur) {
		this.taille = taille;
		this.grille = grille;
		this.nbrBateaux = nbrBateaux;
		this.bateaux = bateaux;
		this.joueur = joueur;
	}
		
	
	public Grille definitionBateau(int joueur) {
		Scanner sc = new Scanner(System.in);
		Grille grille = new Grille(10, new char[10][10], 5, new Bateau[2][5], joueur);
		// Définition de toutes les variables nécessaires
		int l = 0; 
		int c = 0; 
		boolean entreeCorrecte = false; 
		boolean bateauPosable = false; 
		boolean estVertical = false; 
		String placement = "";
		
		System.out.println("Définition des bateaux du joueur "+grille.joueur+".");
		/* Boucle pour initialiser les 5 bateaux du joueur (on peut techniquement en rajouter plus mais il faut ajouter
		 * le nom des bateaux correspondants aux différentes tailles dans la fonction en-dessous
		 */
		for (int i=0;i<nbrBateaux;i++) {
			while (!bateauPosable) { // Il faut que le bâteau ne dépasse pas la grille et qu'il n'y ait pas de bateau sur la case
				while (!entreeCorrecte) {
					System.out.println("Où placer votre "+nomBateau(i+1)+" ? ("+(i+1)+" cases) :");
					placement = sc.nextLine();
					if (((int)placement.charAt(0)) <= (int)'J' && ((int)placement.charAt(0) >= (int)'A')) { // On vérifie que l'utilisateur entre bien une lettre entre A et J
						if (((int)placement.charAt(1)) <= (int)'9' && ((int)placement.charAt(1) >= (int)'1')) { // et un nombre entre 0 et 10
							entreeCorrecte = true;
						}
					}
				} entreeCorrecte = false;
				l = placement.charAt(0) - 'A' + 1; // conversion d'une lettre en chiffre (A donne 1, B donne 2, etc...)
				if (placement.charAt(1) == '1' && placement.length() > 2) c=10; // cas particulier dans le cas où il y a plus de deux chiffres (pour le 10)
				else {
					c = Integer.parseInt(placement.replaceAll("\\D", "")); // permet d'extraire tous les chiffres d'une chaîne de caractères
				} System.out.println("Voulez-vous le placer à l'horizontale ou à la verticale ? (H/V)"); // on choisit l'orientation du bateau
				placement = sc.nextLine();
				if (placement.charAt(0) == 'H') {
					estVertical = false;
				} else {
					estVertical = true;
				} bateauPosable = grille.bateauPosable(l, c, i, estVertical);
			} grille.bateaux[grille.joueur-1][i] = new Bateau(nomBateau(i+1), i+1, new boolean[i+1], estVertical, grille.joueur, l, c);
			grille.placerBateau(l, c, grille.bateaux[grille.joueur-1][i]);
			bateauPosable = false;
		} return grille;
	}
	
	public String nomBateau(int taille) {
		String nomBateau = "";
		switch (taille) {
			case 5:
				nomBateau = "porte-avions";
				break;
			case 4:
				nomBateau = "croiseur";
				break;
			case 3:
				nomBateau = "sous-marin";
				break;
			case 2:
				nomBateau = "torpilleur";
				break;
			case 1:
				nomBateau = "contre-torpilleur";
				break;
		} return nomBateau;
	}
	
	public boolean bateauPosable(int l, int c, int taille, boolean estVertical) {
		boolean bateauPosable = true;
		if ((l+taille>10 && !estVertical) || (c+taille>10 && estVertical)) {
			bateauPosable = false;
		} if (estVertical) {
			for (int i=(c-1);i<taille+(c-1);i++) {
				if (grille[i][l-1] == 'X') {
					bateauPosable = false;
				}
			}
		} else {
			for (int i=(l-1);i<taille+(l-1);i++) {
				if (grille[c-1][i] == 'X') {
					bateauPosable = false;
				}
			}
		} if (!bateauPosable) System.out.println("Erreur ! Un bâteau est déjà présent sur cette case ou le bateau dépasse de la grille.");
		return bateauPosable;
	}
	
	public void placerBateau(int l, int c, Bateau b) {
		if (b.getOrientation()) {
			for (int i=(c-1);i<(c-1)+b.getTaille();i++) {
				this.grille[i][l-1] = 'X';
			}
		} else {
			for (int i=(l-1);i<(l-1)+b.getTaille();i++) {
				this.grille[c-1][i] = 'X';
			}
		} System.out.println("Emplacement des bateaux actuels :");
		this.afficherGrille();
	}
	
	public String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
	
	public void afficherGrille() {
		System.out.print("    ");
		for (int i=1;i<=this.grille.length;i++) {
			System.out.print(intToChar(i));
		} System.out.println();
		System.out.print("   +");
		for (int j=0;j<this.grille[0].length;j++) {
			System.out.print("-");
		} System.out.println("+");
		for (int i=0;i<this.grille.length;i++) {
			 if (i == 9) {
				 System.out.print(i+1+" ");
			 } else {
				 System.out.print(i+1+"  ");
			 } System.out.print("|");
			 for (int j=0;j<this.grille[0].length;j++) {
				 if (this.grille[i][j] == 'X' || this.grille[i][j] == '.') {
					 System.out.print(this.grille[i][j]);
				 } else {
					 System.out.print(" ");
				 }
			 } System.out.println("| ");
			 //System.out.println(intToChar(i+1));
		 }
		 System.out.print("   +");
		 for (int j=0;j<this.grille[0].length;j++) {
			 System.out.print("-");
		 } System.out.println("+");
	}
	
	public int getJoueur() {
		return this.joueur;
	}
}
