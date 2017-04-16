public class Bateau {
	private String nom;
	private int taille;
	private boolean[] etat;
	private boolean estVertical;
	private int joueur;
	private int ligne;
	private int colonne;
	
	public Bateau(String nom, int taille, boolean estVertical, int joueur, int ligne, int colonne) {
		this.nom = nom;
		this.taille = taille;
		this.etat = new boolean[taille];
		for (int i=0;i<taille;i++) {
			this.etat[i] = false;
		} this.estVertical = estVertical;
		this.joueur = joueur;
		this.ligne = ligne;
		this.colonne = colonne;
	}
	
	public int getTaille() {
		return this.taille;
	}
	
	public boolean getOrientation() {
		return this.estVertical;
	}
	
	public int getJoueur() {
		return this.joueur;
	}
	
	public String getPosition() {
		String toReturn = ""+this.ligne+""+this.colonne;
		return toReturn;
	}
	
	public void setEtat(int indice) {
		this.etat[indice] = true;
	}
	
	public boolean bateauCoule() {
		boolean etat = true;
		for (int i=0;i<taille;i++) {
			if (!this.etat[i]) {
				etat = false;
			}
		} return etat;
	}
	
	public int casesTouchees() {
		int casesTouchees = 0;
		for (int i=0;i<taille;i++) {
			if (this.etat[i]) {
				casesTouchees++;
			}
		} return casesTouchees;
	}
	
	public String toString() {
		String toReturn =  "Le bateau est un "+this.nom+" de taille "+this.taille+", placé";
		if (this.estVertical) toReturn += " verticalement";
		else toReturn += " horizontalement";
		toReturn += ", avec "+this.casesTouchees()+" cases touchées. Il est situé en "+(char)(this.ligne + (int)'A' - 1)+this.colonne+" et appartient au joueur "+joueur+".";
		return toReturn;
	}

}
