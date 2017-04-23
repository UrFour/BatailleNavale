import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;	

public class Affichage extends JFrame {
	private JTabbedPane onglet = new JTabbedPane();
	private JFrame fenetre = new JFrame();
	private JButton[][] boutons1;
	private JButton[][] boutons2;
	private JLabel[] lignes;
	private JLabel[] colonnes;
	
	public Affichage(Grille grille1, Grille grille2) {
		this.fenetre.setTitle("Bataille navale");
		this.fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.fenetre.setResizable(false);
		this.boutons1 = new JButton[10][10];
		this.boutons2 = new JButton[10][10];
		this.lignes = new JLabel[10];
		this.colonnes = new JLabel[10];
		
		//Panneau[] grilles = {new Panneau(), new Panneau()};
		JPanel[] grilles = {new JPanel(), new JPanel()};
		grilles[0].setLayout(null);
		grilles[1].setLayout(null);
		for (int i=0;i<10;i++) {
			this.lignes[i] = new JLabel(intToChar(i));
			this.lignes[i].setLocation(610, (i*60 - 30));
			this.colonnes[i] = new JLabel(Integer.toString(i));
			this.colonnes[i].setLocation(i*60-30, 620);
			grilles[0].add(this.lignes[i]);
			grilles[0].add(this.colonnes[i]);
			grilles[1].add(this.lignes[i]);
			grilles[1].add(this.colonnes[i]);
			for (int j=0;j<10;j++) {
				this.boutons1[i][j] = new JButton();
				this.boutons1[i][j].setBounds(i*60, j*60, 60, 60);
        		boutons1[i][j].setOpaque(false);
        		boutons1[i][j].setContentAreaFilled(false);
        		boutons1[i][j].setBorderPainted(true);
				grilles[0].add(this.boutons1[i][j]);
				this.boutons2[i][j] = new JButton();
				this.boutons2[i][j].setBounds(i*60, j*60, 60, 60);
        		boutons2[i][j].setOpaque(false);
        		boutons2[i][j].setContentAreaFilled(false);
        		boutons2[i][j].setBorderPainted(true);
				grilles[1].add(this.boutons2[i][j]);
			}
		} this.onglet.add("Votre grille", grilles[0]);
		this.onglet.add("Grille adverse", grilles[1]);
		this.fenetre.getContentPane().add(this.onglet);
		this.fenetre.pack();
		this.fenetre.setSize(700, 750);
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setVisible(true);
	}
	
	public static String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
}

class Panneau extends JPanel {
	public Grille maGrille;
	
	/*public Panneau(Grille maGrille) {
		this.maGrille = maGrille;
	} */
	public Panneau() {}
	
	public void paintComponent(Graphics g){
		
	    int taille_carre = 60;
	    int tailleLargeur = 600;
	    int tailleHauteur = 600;
	    int maxX = (tailleLargeur / taille_carre);

	   for(int iX = 0; iX <= maxX; iX++){
	    	g.drawString(""+iX, (iX * taille_carre)-30, tailleHauteur+20);
	    	g.drawString(Affichage.intToChar(iX), tailleLargeur+10, (iX * taille_carre)-30);
	   } /*
	        for(int iY = 0; iY <= maxY; iY++){
	            //---------
	            g.drawLine(0, (iY * taille_carre), tailleLargeur, (iY * taille_carre));  	     
	            //| | | | |
	            g.drawLine((iX * taille_carre), 0, (iX * taille_carre), tailleHauteur);
	        }
	    } Graphics2D g2d = (Graphics2D)g;
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	    BasicStroke ligne = new BasicStroke(3.0f);
	    for (int i = 0; i<10;i++) {
	    	for (int j = 0; j<10;j++) {
	            if (maGrille.getGrille()[i][j] == 'X') {
	            	g2d.setStroke(ligne);
	            	g2d.setColor(Color.BLUE);
	            	g2d.drawRect(j*taille_carre, i*taille_carre, taille_carre, taille_carre);
	            	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	            	g2d.fillRect(j*taille_carre, i*taille_carre, taille_carre, taille_carre);
	            } else if (maGrille.getGrille()[i][j] == '*') {
	            	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	            	g2d.setColor(Color.RED);
	            	g2d.setStroke(ligne);
	            	g2d.drawLine(j*taille_carre, i*taille_carre, (j+1)*taille_carre, (i+1)*taille_carre);
	            	g2d.drawLine((j+1)*taille_carre, i*taille_carre, j*taille_carre, (i+1)*taille_carre);
	            } else if (maGrille.getGrille()[i][j] == '.') {
	            	g2d.setColor(Color.BLACK);
	            	g2d.fillOval(j*taille_carre+(taille_carre/2)-10, i*taille_carre+(taille_carre/2)-10, taille_carre/3, taille_carre/3);
	            }
	    	}
	    } g.dispose(); 
	    g.setColor(Color.BLUE);
	    g.fillRect(0, 0, 60, 60);
	    g.fillRect(60, 0, 60, 60);
	    g.fillRect(120, 0, 60, 60);
	    
	    g.setColor(Color.RED);
	    g.fillRect(121, 121, 60, 60);
	    g.fillRect(181, 121, 60, 60);
	    
	    g.setColor(Color.BLUE);
	    g.fillRect(241, 121, 60, 60);
	    g.fillRect(301, 121, 60, 60); */
	}
}

