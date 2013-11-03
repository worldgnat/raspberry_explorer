package raspberryexplorer;

import java.lang.invoke.MethodType;

public class Splitter extends Module {
	public Splitter(RaspberryExplorer parent) {
		super(parent, "Splitter", 100, 100);
		inputs = new Connector[1];
		inputs[0] = new Connector(parent, false, "In", this);
		color[0] = 200;
		color[1] = 200;
		color[2] = 100;
		x= 100; 
		y = 100;
		try {
			/*
			 * 1) Make this a little easier for the programmer; put some methods in 
			 * Connector that do the try-catch stuff automatically.
			 * 2) Override connect() so that inputs[0].setMethod() happens at connection
			 * time, so that the correct version of split can be called.
			 */
			
			inputs[0].setMethodHandle("split", MethodType.methodType(void.class,Object[].class));
		}
		catch (Exception er) {
			er.printStackTrace();
		}
		outputs = new Connector[2];
		outputs[0] = new Connector(parent, true, "Output 1", this);
		outputs[1] = new Connector(parent, true, "Output 2", this);
	}
	
	public void split(Object... args) {
		if (outputs[0].isConnected()) outputs[0].call(args);
		if (outputs[1].isConnected()) outputs[1].call(args);
	}
}
