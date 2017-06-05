import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
//import java.net.SocketException;

public class Service extends Thread{
	private Socket socket;
	private int numClient;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public Service(Socket socket, int numClient) {
		super();
		this.socket = socket;
		this.numClient = numClient;
		try {
			this.ois = new ObjectInputStream(this.socket.getInputStream());
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*	@Override
	public void run() {
		try {
			oos.writeObject(this.numClient);
			System.out.println("Connexion du client n° "+this.numClient);
			System.out.println("IP : "+this.socket.getRemoteSocketAddress());
			while (true) {
				while(Serveur.COMPTEUR < 2) {
					if (this.numClient == 1 && Serveur.COMPTEUR < 2) {
						Serveur.grille1 = this.readGrille();
						System.out.println("Grille du joueur 1 :");
						Serveur.COMPTEUR++;
						Serveur.grille1.afficherGrille();
					} else if (this.numClient == 2 && Serveur.COMPTEUR < 2) {
						Serveur.grille2 = this.readGrille();
						System.out.println("Grille du joueur 2 :");
						Serveur.COMPTEUR++;
						Serveur.grille2.afficherGrille();
					}
				} if (Serveur.COMPTEUR == 2) {
					if (Serveur.FIN) {
						System.out.println("Fin du jeu !");
						this.interrupt();
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Le joueur n°"+this.numClient+" s'est déconnecté du serveur.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} */
	
	@Override
	public void run() {
		try {
			oos.writeObject(this.numClient);
			System.out.println("Connexion du client n° "+this.numClient);
			System.out.println("IP : "+this.socket.getRemoteSocketAddress());
			while (true) {
				if (Serveur.FIN) {
					this.interrupt();
				} else {
					Thread.sleep(1000);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void writeSynchronizedObject (Object objet) {
		synchronized (this.oos) {
			try {
				this.oos.writeObject(objet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Grille readGrille() {
		Grille grille = new Grille(10, new char[10][10], new char[10][10], 5, new Bateau[5], 0);
		synchronized (this.ois) {
			while (grille.getBateaux()[0] == null) {
				try {
					grille = (Grille) this.ois.readObject();
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		} return grille;
	}
	
	public int getNumClient() {
		return this.numClient;
	}

}