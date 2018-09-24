import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Client {

	public static MediaPlayer mediaPlayer;
	public static JFXPanel panel;

	public static void main(String[] args) {
		panel = new JFXPanel();
		Scanner sc = new Scanner(System.in);
		try {
			String ip; int port = 27190;
			if (args.length == 1) {
				ip = args[0];
			} else {
				System.out.println("IP du serveur ?");
				ip = sc.nextLine();
			} System.out.println("Connexion au serveur en cours ...");
			Socket s=new Socket(ip, port);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectInputStream ois = new ObjectInputStream(is);
			int tour; boolean fin = false; boolean refairePartie = true; int joueur = (int)ois.readObject(); int joueurGagnant;
			Grille grille = new Grille(10, new char[10][10], new char[10][10], 5, new Bateau[5], joueur); Grille grilleAdverse;

			System.out.println("Bienvenue sur la Bataille navale créé par Mountasir Nassime.\n");
			System.out.println("Quelques petites instructions histoire de prendre très facilement en main le jeu :");
			System.out.print("- Le placement des bateaux se consid?re en prenant en compte la premi?re case dans le sens conventionnel d'?criture (de gauche ? droite, de haut en bas), ");
			System.out.println("donc placer un bateau de 2 cases en B2 ? l'horizontale placera le bateau sur les cases B2 et C2.");
			System.out.println("- Pour entrer les coordonn?es de vos bateaux, l'entr?e doit ?tre de type [LETTRE][chiffre], exemple : B9.");
			System.out.println("Voici ? quoi ressemble une grille : ");
			grille.afficherGrille(grille.getGrille());
			do {
				System.out.println("Tr?ve de blabla, passons au jeu ! Veuillez d?finir l'emplacement de vos bateaux...\n");
				jouerSon("music.mp3");
				Thread.sleep(2000);
				grille = grille.definitionBateau(grille.getJoueur());
				oos.writeObject(grille);
				System.out.println("Le jeu va bient?t commencer ! Bonne chance !");
				Thread.sleep(5000);
				Grille.effacerEcran();
				while (!fin) {
					tour = (Integer) ois.readObject();
					fin = (Boolean) ois.readObject();
					//System.out.println("Tour du joueur :"+tour);
					if (tour == grille.getJoueur() && !fin) {
						System.out.println("C'est ? votre tour.");
						System.out.println("Etat de votre grille :");
						grille = (Grille) ois.readObject();
						grille.afficherGrille(grille.getGrille());
						System.out.println();
						grilleAdverse = (Grille) ois.readObject();
						//grilleAdverse.afficherGrille();
						grilleAdverse.gestionCoups(grilleAdverse);
						oos.writeObject(grilleAdverse);
						//System.out.println("Grille envoy?e.");
						fin = (Boolean) ois.readObject();
						//System.out.println("Le jeu est termin? : "+fin);
					} else if (tour != grille.getJoueur() && !fin){
						System.out.println("En attente du joueur adverse...");
						Thread.sleep(2000);
					}
				} if (fin) {
					joueurGagnant = (Integer) ois.readObject();
					if (joueurGagnant == joueur) {
						System.out.println("F?licitations, vous avez remport? la partie !");
						arreterSon();
						jouerSon("victory.mp3");
					} else {
						System.out.println("Tous vos bateaux sont coul?s, vous perdez donc la partie !");
						arreterSon();
						jouerSon("defeat.mp3");
					} refairePartie = (Boolean) ois.readObject();
					if (refairePartie) {
						System.out.println("La partie va maintenant recommencer !");
						fin = false;
						arreterSon();
						Thread.sleep(5000);
						Grille.effacerEcran();
					}
				}
			} while (refairePartie);
			if (!refairePartie) {
				System.out.println("Merci d'avoir jou? !");
				arreterSon();
				s.close();
				System.exit(0);
			}
		} catch (SocketException e) {
			System.out.println("Connexion au serveur impossible. Veuillez v?rifier que le serveur est bien d?marr?.");
			System.exit(0);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void jouerSon(String url) {
		try {
			Media media = new Media(Client.class.getResource(url).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
		} catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public static void arreterSon() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

}
