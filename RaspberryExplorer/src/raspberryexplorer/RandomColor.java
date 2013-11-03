package raspberryexplorer;

import java.lang.invoke.MethodType;

public class RandomColor extends Module {
	public RandomColor(RaspberryExplorer parent) {
		super(parent, "Random Color", 100, 100);
		x=400;
		y=300;
		
		color[0] = 20;
		color[1] = 20;
		color[2] = 250;
		
		inputs = new Connector[1];
		inputs[0] = new Connector(parent, false, "input 1", this);
		inputs[0].setMethodHandle("changeBackground", MethodType.methodType(void.class, Object[].class));
		
		setLabel(null);
	}
	public void changeBackground(Object... in) {
		color[0] = (int)parent.random(255);
		color[1] = (int)parent.random(255);
		color[2] = (int)parent.random(255);
	}
}
