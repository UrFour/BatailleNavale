import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame {
	private Panel panneau;
	public Fenetre() {
		JOptionPane jop = new JOptionPane();
		this.setTitle("Grille");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new Panel());
		this.setVisible(true);
		
		String nom = jop.showInputDialog(null, "Où poser votre bateau ?", "Placement du bâteau", JOptionPane.QUESTION_MESSAGE);
		}

}

class Panel extends JPanel {
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
	    }
	    g.dispose();
	    
	    
	}
	
	public String intToChar(int valeur) {
		String toReturn = ""+(char)(valeur + (int)'A' - 1);
		return toReturn;
	}
}

