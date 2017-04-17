import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame {
	private JTabbedPane onglet;
	
	public Fenetre(Grille grille1, Grille grille2) {
		this.setTitle("Bataille navale");
		this.setSize(700, 750);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Panneau[] grilles = {new Panneau(grille1), new Panneau(grille2)};
		onglet = new JTabbedPane();
		onglet.add("Votre grille", grilles[0]);
		onglet.add("Grille adverse", grilles[1]);
		
		
		this.getContentPane().add(onglet);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		//Fenetre fen = new Fenetre();
	}

}

class Panneau extends JPanel {
	public Grille maGrille;
	
	public Panneau(Grille maGrille) {
		this.maGrille = maGrille;
	}
	public void paintComponent(Graphics g){
		
	    int taille_carre = 60;
	    int tailleLargeur = 600;
	    int tailleHauteur = 600;
	    int maxX = (tailleLargeur / taille_carre);
	    int maxY = (tailleHauteur / taille_carre);

	    for(int iX = 0; iX <= maxX; iX++){
	    	g.drawString(""+iX, (iX * taille_carre)-30, tailleHauteur+20);
	    	g.drawString(intToChar(iX), tailleLargeur+10, (iX * taille_carre)-30);    	
	        for(int iY = 0; iY <= maxY; iY++){
	            //---------
	            g.drawLine(0, (iY * taille_carre), tailleLargeur, (iY * taille_carre));  	     
	            //| | | | |
	            g.drawLine((iX * taille_carre), 0, (iX * taille_carre), tailleHauteur);
	        }
	    } Graphics2D g2d = (Graphics2D)g;
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	    for (int i = 0; i<maGrille.grille.length;i++) {
	    	for (int j = 0; j<maGrille.grille[0].length;j++) {
	            /*if (grille.getGrille()[i][j] == 'X') {
	            	g.setColor(Color.BLUE);
	            	g.drawRect((i*taille_carre)/2, j*taille_carre, taille_carre, taille_carre);
	            } */
	    		System.out.println(maGrille.grille[i][j]);
	    	}
	    }
	    /*g.setColor(Color.BLUE);
	    g.fillRect(0, 0, 60, 60);
	    g.fillRect(60, 0, 60, 60);
	    g.fillRect(120, 0, 60, 60);
	    
	    g.setColor(Color.RED);
	    g.fillRect(121, 121, 60, 60);
	    g.fillRect(181, 121, 60, 60);
	    
	    g.setColor(Color.BLUE);
	    g.fillRect(241, 121, 60, 60);
	    g.fillRect(301, 121, 60, 60); */
	    g.dispose();
	    
	}
	
	public String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
}

