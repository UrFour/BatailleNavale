import java.util.Scanner;

public class Grille {
	private int lignes;
	private int colonnes;
	private boolean[][]	grille;
	private int nbrBateaux;
	private Bateau[][] bateaux;
	
	public Grille() {
		this.lignes = 10;
		this.colonnes = 10;
		this.grille = new boolean[this.lignes][this.colonnes];
		this.nbrBateaux = 5;
		this.bateaux = new Bateau[2][this.nbrBateaux];
	}
	
	public void definitionBateau(int joueur) {
		Scanner sc = new Scanner(System.in);
		// Définition de toutes les variables nécessaires
		int l = 0; 
		int c = 0; 
		boolean entreeCorrecte = false; 
		boolean bateauPosable = false; 
		boolean estVertical = false; 
		String placement = ""; 
		
		System.out.println("Définition des bateaux du joueur "+joueur+".");
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
				c = placement.charAt(0) - 'A' + 1; // conversion d'une lettre en chiffre (A donne 1, B donne 2, etc...)
				if (placement.length()>2) l=10; // cas particulier dans le cas où il y a plus de deux chiffres (pour le 10)
				else l = Character.getNumericValue(placement.charAt(1));
				System.out.println(""+l+""+c);
				System.out.println("Voulez-vous le placer à l'horizontale ou à la verticale ? (H/V)"); // on choisit l'orientation du bateau
				placement = sc.nextLine();
				if (placement.charAt(0) == 'H') {
					estVertical = false;
				} else if (placement.charAt(0) == 'V') {
					estVertical = true;
				} bateauPosable = this.bateauPosable(l, c, i, estVertical);
			} this.bateaux[joueur-1][i] = new Bateau(nomBateau(i+1), i, estVertical, joueur, l, c);
			this.placerBateau(l, c, this.bateaux[joueur-1][i]);
			bateauPosable = false;
			placement = "";
		} System.out.println("Les bâteaux ont été définis."); // les bâteaux sont stockés dans un tableau 2D de bâteaux pour faciliter la vie
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
		if ((l+taille>10) || (c+taille>10)) {
			bateauPosable = false;
		} if (estVertical) {
			for (int i=l-1;i<taille+l;i++) {
				if (grille[i][c]) {
					bateauPosable = false;
				}
			}
		} else if (!estVertical){
			for (int i=c-1;i<taille+c;i++) {
				if (grille[l][i]) {
					bateauPosable = false;
				}
			}
		} if (!bateauPosable) System.out.println("Erreur ! Un bâteau est déjà présent sur cette case ou le bateau dépasse de la grille.");
		return bateauPosable;
	}
	
	public void placerBateau(int l, int c, Bateau b) {
		if (b.getOrientation()) {
			for (int i=l+1;i<l+b.getTaille()-1;i++) {
				this.grille[i-1][c-1] = true;
				System.out.println("Le "+nomBateau(b.getTaille())+" a bien été placé en "+intToChar(c).charAt(0)+""+i+".");
			}	
		} else {
			for (int i=c+1;i<c+b.getTaille()-1;i++) {
				this.grille[l-1][i-1] = true;
				System.out.println("Le "+nomBateau(b.getTaille())+" a bien été placé en "+intToChar(i).charAt(0)+""+l+".");
			}
		}
	}
	
	public String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
	
	public void afficherGrille() {
		 System.out.print("+");
		 for (int j=0;j<this.grille[0].length;j++) {
			 System.out.print("-");
		 } System.out.println("+");
		 for (int i=0;i<this.grille.length;i++) {
			 System.out.print("|");
			 for (int j=0;j<this.grille[0].length;j++) {
				 if (this.grille[i][j]) {
					 System.out.print("X");
				 } else {
					 System.out.print(" ");
				 }
			 } System.out.println("|");
		 }
		 System.out.print("+");
		 for (int j=0;j<this.grille[0].length;j++) {
			 System.out.print("-");
		 } System.out.println("+");
	}
	
	

}
