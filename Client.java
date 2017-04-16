import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		try {
			Socket s=new Socket("localhost",1234);
			InputStream is=s.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			OutputStream os=s.getOutputStream();
			PrintWriter pw= new PrintWriter(os, true);
			Scanner clavier=new Scanner(System.in);
			System.out.print("Ecrire une chaîne de caractères : ");
			String str=clavier.nextLine();
			pw.println(str);
			String res = br.readLine();
			System.out.println(res);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}