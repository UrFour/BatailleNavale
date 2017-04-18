import java.io.Serializable;

public class Bateau implements Serializable{
	private String nom;
	private int taille;
	private boolean[] etat;
	private boolean estVertical;
	private int ligne;
	private int colonne;
	
	public Bateau(String nom, int taille, boolean[] etat, boolean estVertical, int ligne, int colonne) {
		this.nom = nom;
		this.taille = taille;
		this.etat = etat;
		this.estVertical = estVertical;
		this.ligne = ligne;
		this.colonne = colonne;
	}
	
	public int getTaille() {
		return this.taille;
	}
	
	public boolean getOrientation() {
		return this.estVertical;
	}
	
	public String getPosition() {
		String toReturn = ""+this.ligne+""+this.colonne;
		return toReturn;
	}
	
	public boolean[] getEtat() {
		return this.etat;
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
		String toReturn =  "Le bateau est un "+this.nom+" de taille "+this.taille+", plac�";
		if (this.estVertical) toReturn += " verticalement";
		else toReturn += " horizontalement";
		toReturn += ", avec "+this.casesTouchees()+" cases touch�es. Il est situ� en "+(char)(this.ligne + (int)'A' - 1)+this.colonne+".";
		return toReturn;
	}

}
