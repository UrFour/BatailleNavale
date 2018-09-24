import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Serveur extends Thread {
	private int nbClients = 0;
	private Service[] clients;
	public static boolean FIN = false;
	public static boolean refairePartie = true;
	public static int TOUR = 1;
	public static int COMPTEUR = 0;
	public static Grille grille1;
	public static Grille grille2;
	public static int joueurGagnant;
	@Override
	public void run() {
		try {
			this.clients = new Service[2];
			ServerSocket ss=new ServerSocket(27190);
			System.out.println("Le serveur est lanc? sur le port 27190.");
			System.out.println("En attente de connexions...");
			while (this.nbClients != 2) {
				Socket s=ss.accept();
				this.clients[nbClients] = new Service(s,(this.nbClients+1));
				this.clients[nbClients].start();
				this.nbClients++;
			} while (Serveur.refairePartie) {
				if (this.nbClients == 2) {
				while(Serveur.COMPTEUR < 2) {
					for (int i=0;i<2;i++) {
						if (this.clients[i].getNumClient() == 1) {
							Serveur.grille1 = this.clients[i].readGrille();
							System.out.println("Grille du joueur 1 :");
							Serveur.COMPTEUR++;
							Serveur.grille1.afficherGrille(Serveur.grille1.getGrille());
						} else if (this.clients[i].getNumClient() == 2) {
						Serveur.grille2 = this.clients[i].readGrille();
							System.out.println("Grille du joueur 2 :");
							Serveur.COMPTEUR++;
							Serveur.grille2.afficherGrille(Serveur.grille2.getGrille());
						}
					}
				} if (Serveur.COMPTEUR == 2) {
					System.out.println("Les deux grilles ont ?t? re?ues. D?but du jeu.");
					while (!FIN) {
						this.clients[0].writeSynchronizedObject(Serveur.TOUR);
						this.clients[1].writeSynchronizedObject(Serveur.TOUR);
						this.clients[0].writeSynchronizedObject(Serveur.FIN);
						this.clients[1].writeSynchronizedObject(Serveur.FIN);
						if (Serveur.TOUR == 1) {
							this.clients[0].writeSynchronizedObject(Serveur.grille1);
							System.out.println("D?but du tour du joueur 1.");
							this.clients[0].writeSynchronizedObject(Serveur.grille2);
							System.out.println("Grille envoy?e au joueur 1. En attente...");
							Serveur.grille2 = this.clients[0].readGrille();
							System.out.println("Tour du joueur 1 termin?.");
							System.out.println("Grille actuelle du joueur 1 :");
							Serveur.grille2.afficherGrille(Serveur.grille2.getGrille());
							Serveur.FIN = Serveur.grille2.jeuTermine();
							if (!Serveur.FIN) {
								Serveur.TOUR = 2;
							} else {
								Serveur.joueurGagnant = Serveur.TOUR;
							} this.clients[0].writeSynchronizedObject(Serveur.FIN);
						} else if (Serveur.TOUR == 2) {
							this.clients[1].writeSynchronizedObject(Serveur.grille2);
							System.out.println("D?but du tour du joueur 2.");
							this.clients[1].writeSynchronizedObject(Serveur.grille1);
							Thread.sleep(500);
							System.out.println("Grille envoy?e au joueur 2. En attente...");
							Serveur.grille1 = this.clients[1].readGrille();
							System.out.println("Tour du joueur 2 termin?.");
							System.out.println("Grille actuelle du joueur 2 :");
							Serveur.grille1.afficherGrille(Serveur.grille1.getGrille());
							Serveur.FIN = Serveur.grille1.jeuTermine();
							if (!Serveur.FIN) {
								Serveur.TOUR = 1;
							} else {
								Serveur.joueurGagnant = Serveur.TOUR;
							} this.clients[1].writeSynchronizedObject(Serveur.FIN);
						}
					} if (Serveur.FIN) {
						if (Serveur.joueurGagnant == 1) {
							this.clients[1].writeSynchronizedObject(Serveur.TOUR);
							this.clients[1].writeSynchronizedObject(Serveur.FIN);
							this.clients[0].writeSynchronizedObject(Serveur.joueurGagnant);
							this.clients[1].writeSynchronizedObject(Serveur.joueurGagnant);
						} else {
							this.clients[0].writeSynchronizedObject(Serveur.TOUR);
							this.clients[0].writeSynchronizedObject(Serveur.FIN);
							this.clients[1].writeSynchronizedObject(Serveur.joueurGagnant);
							this.clients[0].writeSynchronizedObject(Serveur.joueurGagnant);
						} System.out.println("Le jeu est termin?. Le joueur "+Serveur.joueurGagnant+" remporte la partie !");
						Scanner sc = new Scanner(System.in);
						System.out.println("Voulez-vous refaire une partie ? (O/N)");
						char refairePartie = sc.nextLine().charAt(0);
						if (refairePartie == 'O') {
							System.out.println("La partie va maintenant recommencer !");
							Serveur.refairePartie = true;
							this.clients[0].writeSynchronizedObject(Serveur.refairePartie);
							this.clients[1].writeSynchronizedObject(Serveur.refairePartie);
							Serveur.TOUR = 1;
							Serveur.FIN = false;
							Serveur.COMPTEUR = 0;
							Serveur.joueurGagnant = 0;
							Thread.sleep(1000);
							Grille.effacerEcran();
						} else {
							System.out.println("Merci d'avoir jou? !");
							Serveur.refairePartie = false;
							this.clients[0].writeSynchronizedObject(Serveur.refairePartie);
							this.clients[1].writeSynchronizedObject(Serveur.refairePartie);
							ss.close();
							this.interrupt();
							System.exit(0);
							}
						}
					} else {
						Thread.sleep(1000);
					}
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Serveur().start();
	}

}
