import java.io.Serializable;
import java.util.Scanner;

public class Grille implements Serializable {

	private static final long serialVersionUID = -948087220809624571L;
	private char[][] grille;
	private char[][] affichageTirsAdverses;
	private int nbrBateaux;
	private Bateau[] bateaux;
	private int joueur;
	
	public Grille(int taille, char[][] grille, char[][] affichageTirsAdverses, int nbrBateaux, Bateau[] bateaux, int joueur) {
		this.grille = grille;
		this.affichageTirsAdverses = affichageTirsAdverses;
		this.nbrBateaux = nbrBateaux;
		this.bateaux = bateaux;
		this.joueur = joueur;
	}
		
	
	public Grille definitionBateau(int joueur) {
		Scanner sc = new Scanner(System.in);
		Grille grille = new Grille(10, new char[10][10], new char[10][10], 5, new Bateau[5], joueur);
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
					if ((((placement.length() == 2)) || ((placement.length() == 3) && (placement.charAt(1) == '1') && (placement.charAt(2) == '0'))) && ((int)placement.charAt(0)) <= (int)'J' && ((int)placement.charAt(0) >= (int)'A')) { // On vérifie que l'utilisateur entre bien une lettre entre A et J
						if (((int)placement.charAt(1)) <= (int)'9' && ((int)placement.charAt(1) >= (int)'1')) { // et un nombre entre 0 et 10
							entreeCorrecte = true;
						}
					} else {
						System.out.println("Erreur de saisie. L'entrée doit être de type [LETTRE][chiffre], exemple : B9.");
					}
				} entreeCorrecte = false;
				l = placement.charAt(0) - 'A' + 1; // conversion d'une lettre en chiffre (A donne 1, B donne 2, etc...)
				//if (placement.charAt(1) == '1' && placement.length() > 2) c=10; // cas particulier dans le cas où il y a plus de deux chiffres (pour le 10)
				c = Integer.parseInt(placement.replaceAll("\\D", "")); // permet d'extraire tous les chiffres d'une chaîne de caractères
				while (!entreeCorrecte) {
					System.out.println("Voulez-vous le placer à l'horizontale ou à la verticale ? (H/V)"); // on choisit l'orientation du bateau
					placement = sc.nextLine();
					if (placement.charAt(0) == 'H' || placement.charAt(0) == 'V') {
						entreeCorrecte = true;
					} else {
						System.out.println("Erreur de saisie. Vous devez entrer H (pour horizontal) ou V (pour vertical).");
					}
				} entreeCorrecte = false;
				if (placement.charAt(0) == 'H') {
					estVertical = false;
				} else {
					estVertical = true;
				} bateauPosable = grille.bateauPosable(l, c, i, estVertical);
			} String[] coords = new String[i+1];
			for (int j=0;j<coords.length;j++) {
				if (estVertical) {
					coords[j] = intToChar(l)+""+(c+j);
					//System.out.println(coords[j]);
				} else {
					coords[j] = intToChar(l+j)+""+c;
					//System.out.println(coords[j]);
				}
			} grille.bateaux[i] = new Bateau(nomBateau(i+1), i+1, new boolean[i+1], coords, estVertical);
			grille.placerBateau(l, c, grille.bateaux[i]);
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
		this.afficherGrille(this.grille);
	}
	
	public String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
	
	public void afficherGrille(char[][] grille) {
		System.out.print("    ");
		for (int i=1;i<=grille.length;i++) {
			System.out.print(intToChar(i));
		} System.out.println();
		System.out.print("   +");
		for (int j=0;j<grille[0].length;j++) {
			System.out.print("-");
		} System.out.println("+");
		for (int i=0;i<grille.length;i++) {
			 if (i == 9) {
				 System.out.print(i+1+" ");
			 } else {
				 System.out.print(i+1+"  ");
			 } System.out.print("|");
			 for (int j=0;j<grille[0].length;j++) {
				 if (grille[i][j] == 'X' || grille[i][j] == '.' || grille[i][j] == '*') {
					 System.out.print(this.grille[i][j]);
				 } else {
					 System.out.print(" ");
				 }
			 } System.out.println("| ");
		 }
		 System.out.print("   +");
		 for (int j=0;j<this.grille[0].length;j++) {
			 System.out.print("-");
		 } System.out.println("+");
	}
	
	public void gestionCoups(Grille grille) {
		Scanner sc = new Scanner(System.in);
		boolean entreeCorrecte = false; boolean touche = false;
		String coup = "";
		int indice = 0;
		while (!entreeCorrecte) {
			System.out.println("Quelle case voulez-vous attaquer ?");
			coup = sc.nextLine();
			if ((((coup.length() == 2) || ((coup.length() == 3) && (coup.charAt(1) == '1') && (coup.charAt(2)) == '0'))) && ((int)coup.charAt(0)) <= (int)'J' && ((int)coup.charAt(0) >= (int)'A')) { // On vérifie que l'utilisateur entre bien une lettre entre A et J
				if (((int)coup.charAt(1)) <= (int)'9' && ((int)coup.charAt(1) >= (int)'1')) { // et un nombre entre 0 et 10
					if (this.affichageTirsAdverses[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] != '.' && this.affichageTirsAdverses[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] != '*') {
						entreeCorrecte = true;
					} else {
						System.out.println("Erreur. Vous avez déjà tiré sur cette case, merci d'en sélectionner une autre.");
					}
				} else {
					System.out.println("Erreur de saisie. L'entrée doit être de type [LETTRE][chiffre], exemple : B9.");
				}
			} else {
				System.out.println("Erreur de saisie. L'entrée doit être de type [LETTRE][chiffre], exemple : B9.");
			}
		} for (int i=0;i<5;i++) {
			for (int j=0;j<this.bateaux[i].getTaille();j++) {
				if (coup.charAt(0) == this.bateaux[i].getPosition()[j].charAt(0) && (coup.charAt(1) == this.bateaux[i].getPosition()[j].charAt(1))) {
					this.bateaux[i].setEtat(j);
					touche = true;
					indice = i;
				}
			}
		} if (touche && this.bateaux[indice].bateauCoule()) {
			this.grille[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '*';
			this.affichageTirsAdverses[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '*';
			System.out.println("Touché coulé !");
		} else if (touche) {
			this.grille[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '*';
			this.affichageTirsAdverses[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '*';
			System.out.println("Touché !");
		} else {
			this.grille[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '.';
			this.affichageTirsAdverses[Integer.parseInt(coup.replaceAll("\\D", ""))-1][coup.charAt(0)- 'A'] = '.';
			System.out.println("Raté !");
		} System.out.println("Etat de la grille adverse : ");
		this.afficherGrille(this.affichageTirsAdverses);
	}
	
	public boolean jeuTermine() {
		boolean jeuTermine = true;
		for (int i=0;i<this.bateaux.length;i++) {
			if (!this.bateaux[i].bateauCoule()) {
				jeuTermine = false;
			}
		} return jeuTermine;
	}
	
	public int getJoueur() {
		return this.joueur;
	}
	
	public char[][] getGrille() {
		return this.grille;
	}
	
	public Bateau[] getBateaux() {
		return this.bateaux;
	}
}
