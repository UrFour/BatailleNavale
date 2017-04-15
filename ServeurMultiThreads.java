import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMultiThreads extends Thread {
	private int nbClients=0;
	@Override
	public void run() {
		try {
			ServerSocket ss=new ServerSocket(123);
			while(true){
				Socket s=ss.accept();
				nbClients++;
				new Service(s,nbClients).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ServeurMultiThreads().start();
	}
}
