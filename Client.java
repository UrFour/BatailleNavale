import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
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
			System.out.println("Connexion au serveur en cours ...");
			ObjectInputStream ois = new ObjectInputStream(is); 
			Scanner sc=new Scanner(System.in);
			int tour; boolean fin = false; int joueur = (int)ois.readObject();
			Grille grille = new Grille(10, new char[10][10], 5, new Bateau[5], joueur); Grille grilleAdverse;
			grille = grille.definitionBateau(grille.getJoueur());
			oos.writeObject(grille);
			System.out.println("En attente du joueur adverse...");
			while (!fin) {
				tour = (Integer) ois.readObject();
				System.out.println("Tour du joueur :"+tour);
				if (tour == grille.getJoueur()) {
					System.out.println("C'est à votre tour.");
					grilleAdverse = (Grille) ois.readObject();
					grilleAdverse.gestionCoups(grilleAdverse);
					oos.writeObject(grilleAdverse);
					fin = (Boolean) ois.readObject();
					System.out.println("Le jeu est terminé : "+fin);
				} else {
					Thread.sleep(20000);
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}