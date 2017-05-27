import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Serveur extends Thread {
	private int nbClients=0;
	public static boolean FIN;
	public static boolean TOUR_JOUEUR;
	public static int TOUR;
	public static int COMPTEUR;
	public static Grille grille1;
	public static Grille grille2;
	public static Service[] clients;
	@Override
	public void run() {
		try {
			COMPTEUR = 0;
			TOUR = 1;
			ServerSocket ss=new ServerSocket(27190);
			System.out.println("Le serveur est lancé sur le port 27190.");
			System.out.println("En attente de connexions...");
			while(!FIN){
				Socket s=ss.accept();
				nbClients++;
				new Service(s,nbClients).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Serveur().start();
	}

}