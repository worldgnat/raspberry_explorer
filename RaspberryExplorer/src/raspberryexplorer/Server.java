package raspberryexplorer;

public class Server extends Module {
	/*
	 * A module that sends arbitrary objects to a client module over RMI
	 */
	public Server(RaspberryExplorer parent) {
		super(parent, "Server", 200, 100);
	}
}
