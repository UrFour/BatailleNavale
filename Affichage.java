import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JLabel[] lignes1;
	private JLabel[] lignes2;
	private JLabel[] colonnes1;
	private JLabel[] colonnes2;
	
	public Affichage(Grille grille1, Grille grille2) {
		this.fenetre.setTitle("Bataille navale");
		this.fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.fenetre.setResizable(false);
		this.boutons1 = new JButton[10][10];
		this.boutons2 = new JButton[10][10];
		this.lignes1 = new JLabel[11];
		this.lignes2 = new JLabel[11];
		this.colonnes1 = new JLabel[11];
		this.colonnes2 = new JLabel[11];
		
		//Panneau[] grilles = {new Panneau(), new Panneau()};
		JPanel[] grilles = {new JPanel(), new JPanel()};
		grilles[0].setLayout(null);
		grilles[1].setLayout(null);
		for (int i=0;i<10;i++) {
			this.lignes1[i] = new JLabel(""+(char)(i + (int)'A' - 1));
			this.lignes1[i].setBounds(i*60 - 30, 620, 10, 10);
			this.colonnes1[i] = new JLabel(Integer.toString(i));
			this.colonnes1[i].setBounds(610, i*60 - 30, 10, 10);
			this.lignes2[i] = new JLabel(""+(char)(i + (int)'A' - 1));
			this.lignes2[i].setBounds(i*60 - 30, 620, 10, 10);
			this.colonnes2[i] = new JLabel(Integer.toString(i));
			this.colonnes2[i].setBounds(610, i*60 - 30, 10, 10);
			grilles[0].add(this.lignes1[i]);
			grilles[0].add(this.colonnes1[i]);
			grilles[1].add(this.lignes2[i]);
			grilles[1].add(this.colonnes2[i]);
			for (int j=0;j<10;j++) {
				this.boutons1[i][j] = new JButton();
				this.boutons1[i][j].setBounds(i*60, j*60, 60, 60);
        		boutons1[i][j].setOpaque(false);
        		boutons1[i][j].setContentAreaFilled(false);
        		boutons1[i][j].setBorderPainted(true);
				grilles[0].add(this.boutons1[i][j]);
				boutons1[i][j].addActionListener(new GrilleListener(i+1, j+1, 1));
				this.boutons2[i][j] = new JButton();
				this.boutons2[i][j].setBounds(i*60, j*60, 60, 60);
        		boutons2[i][j].setOpaque(false);
        		boutons2[i][j].setContentAreaFilled(false);
        		boutons2[i][j].setBorderPainted(true);
				grilles[1].add(this.boutons2[i][j]);
				boutons2[i][j].addActionListener(new GrilleListener(i+1, j+1, 2));
			}
		} this.lignes1[10] = new JLabel(""+(char)(10 + (int)'A' - 1));
		this.lignes1[10].setBounds(570, 620, 10, 10);
		this.colonnes1[10] = new JLabel("1");
		this.colonnes1[10].setBounds(610, 570, 10, 10);
		JLabel a = new JLabel("0");
		a.setBounds(617, 570, 10, 10);
		this.lignes2[10] = new JLabel(""+(char)(10 + (int)'A' - 1));
		this.lignes2[10].setBounds(570, 620, 10, 10);
		this.colonnes2[10] = new JLabel("1");
		this.colonnes2[10].setBounds(610, 570, 10, 10);
		JLabel b = new JLabel("0");
		b.setBounds(617, 570, 10, 10);
		grilles[0].add(this.lignes1[10]);
		grilles[0].add(this.colonnes1[10]);
		grilles[0].add(a);
		grilles[1].add(this.lignes2[10]);
		grilles[1].add(this.colonnes2[10]);
		grilles[1].add(b);
		
		this.onglet.add("Votre grille", grilles[0]);
		this.onglet.add("Grille adverse", grilles[1]);
		this.fenetre.getContentPane().add(this.onglet);
		this.fenetre.pack();
		this.fenetre.setSize(700, 750);
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setVisible(true);
	}
	
/*	class Panneau extends JPanel {	
		public Panneau() {}
	
		public void paintComponent(Graphics g){
		
			int taille_carre = 60;
			int tailleLargeur = 600;
			int tailleHauteur = 600;

	    	for(int iX = 0; iX <= tailleLargeur/taille_carre; iX++){
	    		g.drawString(""+iX, (iX * taille_carre)-30, tailleHauteur+20);
	    		g.drawString(""+(char)(iX + (int)'A' - 1), tailleLargeur+10, (iX * taille_carre)-30);
	    	} 
		}
	} */
	
	private final class GrilleListener implements ActionListener {
		private final int ligne;
		private final int colonne;
		private final int numGrille;
		private GrilleListener(int l, int c, int n) {
			this.ligne = l;
			this.colonne = c;
			this.numGrille = n;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(""+((char)(this.ligne + (int)'A' - 1))+this.colonne+", grille n°"+this.numGrille);
		}
	}
}

