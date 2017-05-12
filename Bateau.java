import java.io.Serializable;

public class Bateau implements Serializable{
	private String nom;
	private int taille;
	private boolean[] etat;
	private String[] coords;
	private boolean estVertical;
	
	public Bateau(String nom, int taille, boolean[] etat, String[] coords, boolean estVertical) {
		this.nom = nom;
		this.taille = taille;
		this.etat = etat;
		this.coords = coords;
		this.estVertical = estVertical;
	}
	
	public int getTaille() {
		return this.taille;
	}
	
	public boolean getOrientation() {
		return this.estVertical;
	}
	
	public String[] getPosition() {
		return this.coords;
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
		String toReturn =  "Le bateau est un "+this.nom+" de taille "+this.taille+", placé";
		if (this.estVertical) toReturn += " verticalement";
		else toReturn += " horizontalement";
		toReturn += ", avec "+this.casesTouchees()+" cases touchées. Il est situé en "+coords[0]+".";
		return toReturn;
	}

}
