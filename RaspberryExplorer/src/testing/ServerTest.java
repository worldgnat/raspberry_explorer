package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	ServerSocket serverSocket;
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	int port = 4444;
	public ServerTest() {
		//Start the server
		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("Got: " + line);
				out.write(line.replaceAll("a",""));
			}
		}
		catch (Exception er) {
			er.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerTest test = new ServerTest();
	}
	
}
