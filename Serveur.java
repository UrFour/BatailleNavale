import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

public class Serveur {

	public static void main(String[] args) {
		try {
			ServerSocket ss=new ServerSocket(1234);
			System.out.println("J'attends une connexion");
			Socket s=ss.accept();
			InputStream is=s.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=s.getOutputStream();
			PrintWriter pw= new PrintWriter(os, true);
			System.out.println("J'attends une cha�ne de caract�res");
			String str=br.readLine();
			String rep= "Vous avez envoy� la cha�ne de caract�res :"+str;
			System.out.println("J'envoie la r�ponse");
			pw.println(rep);
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
