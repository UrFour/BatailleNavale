import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Service extends Thread {
	private Socket socket;
	private int numClient;
	private Grille grille1;
	private Grille grille2;
	public Service(Socket socket, int numClient) {
		super();
		this.socket = socket;
		this.numClient = numClient;
	}
	
	@Override
	public void run() {
		try {
			InputStream is=this.socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=this.socket.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			PrintWriter pw=new PrintWriter(os, true);
			pw.println(this.numClient);
			System.out.println("Connexion du client n° "+this.numClient);
			System.out.println("IP : "+this.socket.getRemoteSocketAddress());
			int compteur = 0;
			while(true){
				while (compteur != 2) {
					if (this.numClient == 1) {
						this.grille1 = (Grille) ois.readObject();
						System.out.println("Grille du joueur 1 :");
						compteur++;
						this.grille1.afficherGrille();
					} if (this.numClient == 2) {
						this.grille2 = (Grille) ois.readObject();
						System.out.println("Grille du joueur 2:" );
						this.grille2.afficherGrille();
						compteur++;
					}
				} if(!ServeurMultiThreads.FIN) {
				// déroulement du jeu
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}