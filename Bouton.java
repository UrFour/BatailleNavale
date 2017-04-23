import javax.swing.JButton; 	
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class Bouton{
 
    public static void main(String[] args) {
        JFrame fenetre = new JFrame("Bouton");
        JPanel panel = new JPanel();
        JButton[][] boutons = new JButton[10][10];
        JLabel[] lignes = new JLabel[10];
         
        fenetre.setSize(800, 800);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        for (int i=0;i<10;i++) {
        	lignes[i] = new JLabel();
        	lignes[i].setText(Integer.toString(i));
			lignes[i].setLocation(i*60-30, 620);
			panel.add(lignes[i]);
        	for (int j=0;j<10;j++) {
        		boutons[i][j] = new JButton();
        		panel.add(boutons[i][j]);
        		boutons[i][j].setBounds(i*60, j*60, 60, 60);
        		boutons[i][j].setOpaque(false);
        		boutons[i][j].setContentAreaFilled(false);
        		boutons[i][j].setBorderPainted(true);
        	}
        }        
        fenetre.setContentPane(panel);
        fenetre.setVisible(true);
    }
}
 