import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		System.out.println("Connexion au serveur en cours ...");
		try {
			String ip; int port;
			if (args.length <= 1) {
				ip = "localhost";
				port = 27190;
			}
			else {
				ip = args[0];
				port = Integer.parseInt(args[1]);
			}
			Socket s=new Socket(ip, port);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectInputStream ois = new ObjectInputStream(is); 
			Scanner sc=new Scanner(System.in);
			int tour; boolean fin = false; int joueur = (int)ois.readObject(); int joueurGagnant;
			Grille grille = new Grille(10, new char[10][10], new char[10][10], 5, new Bateau[5], joueur); Grille grilleAdverse;
			
			System.out.println("Bienvenue sur la Bataille navale créé par Mountasir Nassime.\n");
			System.out.println("Note : pour entrez les coordonnées de vos bateaux, l'entrée doit être de type [LETTRE][chiffre], exemple : B9.");
			System.out.println("Le jeu va maintenant commencer...\n");
			Thread.sleep(2000);
			grille = grille.definitionBateau(grille.getJoueur());
			oos.writeObject(grille);
			System.out.println("Le jeu va bientôt commencer ! Bonne chance !");
			Thread.sleep(5000);
			effacerEcran();
			while (!fin) {
				tour = (Integer) ois.readObject();
				fin = (Boolean) ois.readObject();
				//System.out.println("Tour du joueur :"+tour);
				if (tour == grille.getJoueur() && !fin) {
					System.out.println("C'est à votre tour.");
					grilleAdverse = (Grille) ois.readObject();
					//grilleAdverse.afficherGrille();
					grilleAdverse.gestionCoups(grilleAdverse);
					oos.writeObject(grilleAdverse);
					//System.out.println("Grille envoyée.");
					fin = (Boolean) ois.readObject();
					//System.out.println("Le jeu est terminé : "+fin);
				} else {
					System.out.println("En attente du joueur adverse...");
					Thread.sleep(2000);
				} 
			} if (fin) {
				joueurGagnant = (Integer) ois.readObject();
				if (joueurGagnant == joueur) {
					System.out.println("Félicitations, vous avez remporté la partie !");
				} else {
					System.out.println("Tous vos bateaux sont coulés, vous perdez donc la partie !");
				} s.close();
			}
		} catch (SocketException e) {
			System.out.println("Connexion au serveur impossible. Veuillez vérifier que le serveur est bien démarré.");
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void effacerEcran(){
	    try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException e) {}
	}

}
