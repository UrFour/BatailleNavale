import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		try {
			Socket s=new Socket("localhost",123);
			InputStream is=s.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			PrintWriter pw= new PrintWriter(os, true);
			Scanner sc=new Scanner(System.in);
			Grille grille = new Grille(10, new char[10][10], 5, new Bateau[2][5], Integer.parseInt(br.readLine()));
			grille = grille.definitionBateau(grille.getJoueur());
			{
				oos.writeObject(grille);
				sc.next();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}