import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Service extends Thread {
	private Socket socket;
	private int numClient;
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
			PrintWriter pw=new PrintWriter(os, true);
			pw.println("Vous etes le client n"+numClient);
			System.out.println("Connexion du client n° "+numClient);
			System.out.println("IP : "+socket.getRemoteSocketAddress());
			while(true){
				String req=br.readLine();
				pw.println(req.length());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
