import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Affichage extends JFrame {
	private JTabbedPane onglet = new JTabbedPane();
	private JFrame fenetre = new JFrame();
	private Grille grille1;
	private Grille grille2;

	public Affichage(Grille grille) {
		this.grille1 = grille;
		this.grille2 = new Grille(10, new char[10][10], new char[10][10], 5, new Bateau[5], 2);
		this.fenetre.setTitle("Bataille navale");
		this.fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.fenetre.setResizable(false);

		Onglet[] grilles = {new Onglet(1, this.grille1), new Onglet(2, this.grille2)};

		this.onglet.add("Votre grille", grilles[0]);
		this.onglet.add("Grille adverse", grilles[1]);
		this.fenetre.getContentPane().add(this.onglet);
		this.fenetre.pack();
		this.fenetre.setSize(700, 750);
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setVisible(true);
	}



	class Onglet extends JPanel {
		private JButton[][] boutons;
		private JLabel[] lignes;
		private JLabel[] colonnes;
		private Grille grille;
		public Onglet(int numGrille, Grille grille) {
			boolean definitionBateau = true;
			int compteur = 0;
			this.boutons = new JButton[10][10];
			this.lignes = new JLabel[11];
			this.colonnes = new JLabel[11];
			this.grille = grille;

			this.setLayout(null);
			for (int i=0;i<10;i++) {
				this.lignes[i] = new JLabel(""+(char)(i + (int)'A' - 1));
				this.lignes[i].setBounds(i*60 - 30, 620, 10, 10);
				this.colonnes[i] = new JLabel(Integer.toString(i));
				this.colonnes[i].setBounds(610, i*60 - 30, 10, 10);
				this.add(this.lignes[i]);
				this.add(this.colonnes[i]);
				for (int j=0;j<10;j++) {
					this.boutons[i][j] = new JButton();
					this.boutons[i][j].setBounds(i*60, j*60, 60, 60);
	        		this.boutons[i][j].setOpaque(false);
	        		this.boutons[i][j].setContentAreaFilled(false);
	        		this.boutons[i][j].setBorderPainted(true);
					this.add(this.boutons[i][j]);
					this.boutons[i][j].addActionListener(new GrilleListener(i+1, j+1, numGrille));
				}
			} this.lignes[10] = new JLabel(""+(char)(10 + (int)'A' - 1));
			this.lignes[10].setBounds(570, 620, 10, 10);
			this.colonnes[10] = new JLabel("1");
			this.colonnes[10].setBounds(610, 570, 10, 10);
			JLabel label = new JLabel("0");
			label.setBounds(617, 570, 10, 10);
			this.add(this.lignes[10]);
			this.add(this.colonnes[10]);
			this.add(label);
			this.update();
		}

		public void update() {
			for (int i=0;i<10;i++) {
				for (int j=0;j<10;j++) {
					if (grille.getGrille()[i][j] == 'X') {
						this.boutons[j][i].setText(""+grille.getGrille()[i][j]);
					}
				}
			}
		}
	}

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
