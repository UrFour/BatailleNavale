import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Service extends Thread{
	private Socket socket;
	private int numClient;
	public Service(Socket socket, int numClient) {
		super();
		this.socket = socket;
		this.numClient = numClient;
	}
	
	@Override
	public void run() {
		try {
			InputStream is=this.socket.getInputStream();
			OutputStream os=this.socket.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(this.numClient);
			System.out.println("Connexion du client n° "+this.numClient);
			System.out.println("IP : "+this.socket.getRemoteSocketAddress());
			while(true){
				do {
					if (this.numClient == 1) {
						Serveur.grille1 = (Grille) ois.readObject();
						System.out.println("Grille du joueur 1 :");
						Serveur.COMPTEUR++;
						Serveur.grille1.afficherGrille();
					} else if (this.numClient == 2) {
						Serveur.grille2 = (Grille) ois.readObject();
						System.out.println("Grille du joueur 2 :");
						Serveur.COMPTEUR++;
						Serveur.grille2.afficherGrille();
					}
				} while (Serveur.COMPTEUR != 2);
					System.out.println("Les deux grilles ont été reçues. Début du jeu.");
					while (!Serveur.FIN) {
					oos.writeObject(Serveur.TOUR);
					if (Serveur.TOUR == 1 && this.numClient == 1) {
						System.out.println("Début du tour du joueur 1");
						oos.writeObject(Serveur.grille2);
						System.out.println("Grille envoyée au joueur 1. En attente du joueur 1...");
						Serveur.grille2 = (Grille) ois.readObject();
						System.out.println("Tour du joueur 1 terminé.");
						System.out.println("Grille actuelle :");
						Serveur.grille2.afficherGrille();
						Serveur.FIN = Serveur.grille2.jeuTermine();
						System.out.println("Fin du jeu :"+Serveur.FIN);
						Serveur.TOUR = 2;
						oos.writeObject(Serveur.FIN);
					} else if (Serveur.TOUR == 2 && this.numClient == 2) {
						System.out.println("Début du tour du joueur 2");
						oos.writeObject(Serveur.grille1);
						System.out.println("Grille envoyée au joueur 2. En attente du joueur 2...");
						Serveur.grille2 = (Grille) ois.readObject();
						System.out.println("Tour du joueur 2 terminé.");
						System.out.println("Grille actuelle :");
						Serveur.grille1.afficherGrille();
						Serveur.FIN = Serveur.grille2.jeuTermine();
						System.out.println("Fin du jeu :"+Serveur.FIN);
						Serveur.TOUR = 1;
						oos.writeObject(Serveur.FIN);
					}
				} 
			}
		} catch (SocketException e) {
			System.out.println("Le joueur n°"+this.numClient+" s'est déconnecté du serveur.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}