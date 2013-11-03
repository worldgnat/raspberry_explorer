package testing;

import java.net.Socket;
import java.io.*;

public class ClientTest {
	public ClientTest() {
		try {
			Socket socket = new Socket("127.0.0.1", 4444);
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.write("This a is a taest.\n");
			System.out.println(in.readLine());
		}
		catch(Exception er){
			er.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		ClientTest test = new ClientTest();
	}
}
