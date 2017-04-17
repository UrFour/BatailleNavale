import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

public class Serveur {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ServerSocket ss=new ServerSocket(123);
			System.out.println("J'attends une connexion");
			Socket s=ss.accept();
			InputStream is=s.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=s.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			PrintWriter pw=new PrintWriter(os, true);
			Grille grille1;
			while (true) {
				grille1 = (Grille) ois.readObject();
				grille1.afficherGrille();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}