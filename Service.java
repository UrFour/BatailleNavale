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
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=socket.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			PrintWriter pw=new PrintWriter(os, true);
			pw.println(numClient);
			System.out.println("Connexion du client n° "+numClient);
			System.out.println("IP : "+socket.getRemoteSocketAddress());
			while(true){
				if (numClient == 1) {
					grille1 = (Grille) ois.readObject();
					System.out.println("Grille du joueur 1 :");
					grille1.afficherGrille();
				} if (numClient == 2) {
					grille2 = (Grille) ois.readObject();
					System.out.println("Grille du joueur 2:" );
					grille2.afficherGrille();
				} if(!ServeurMultiThreads.fin) {
				// déroulement du jeu
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}