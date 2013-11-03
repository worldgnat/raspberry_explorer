package raspberryexplorer;

public class Pulsator extends Module {
	float prevTime;
	boolean high = false;
	int delay;
	
	public Pulsator(RaspberryExplorer parent, int delay) {
		super(parent, "Pulsator", 100, 100);
		color[0] = 230;
		color[1] = 230;
		color[2] = 230;
		x = 100;
		y = 100;
		outputs = new Connector[1];
		outputs[0] = new Connector(parent, true, "Pulse", this);
		prevTime=parent.millis();
		this.delay=delay;
	}
	
	public void draw() {
		super.draw();
		if (parent.millis()-prevTime > delay) {
			prevTime=parent.millis();
			high = !high;
			if (high) {
				color[0] = 230;
				color[1] = 230;
				color[2] = 230;
				Object out = (Object)(new Integer(5));
				System.out.println("My Type is: "+ out.getClass());
				if (outputs[0].connected) outputs[0].call(out);
			}
			else {
				color[0] = 100;
				color[1] = 100;
				color[2] = 100;
			}
		}
	}
}
