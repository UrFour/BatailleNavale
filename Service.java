import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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

	@Override
	public void run() {
		try {
			oos.writeObject(this.numClient);
			System.out.println("Connexion du client n° "+this.numClient);
			System.out.println("IP : "+this.socket.getRemoteSocketAddress());
			while (true) {
				if (Serveur.FIN && !Serveur.refairePartie) {
					this.interrupt();
				} else {
					Thread.sleep(1000);
				}
			}
		} catch (SocketException e) {
			System.out.println("Le joueur n°"+this.numClient+" s'est déconnecté du serveur.");
			this.interrupt();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void writeSynchronizedObject (Object objet) {
		synchronized (this.oos) {
			try {
				this.oos.writeObject(objet);
			} catch (IOException e) {

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

				}
			}
		} return grille;
	}

	public int getNumClient() {
		return this.numClient;
	}

}
