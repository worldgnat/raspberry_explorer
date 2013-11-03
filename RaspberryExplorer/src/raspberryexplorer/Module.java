package raspberryexplorer;

import java.io.Serializable;

public abstract class Module implements Serializable {
	String codePath; //Path to the code for this module
	String[] imports; //The import statements that this module needs
	String name; //The module's name
	int initParams; //Number of initialization parameters
	int x,y;
	int width, height;
	RaspberryExplorer parent;
	
	int[] color;
	
	private int outMoving = -1;
	
	private int xOffset, yOffset;
	
	Connector[] inputs;
	Connector[] outputs;
			
	public Module(RaspberryExplorer parent, String name, int width, int height) {
		this.parent = parent;
		color = new int[3];
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public void draw() {
		parent.stroke(1);
		parent.fill(color[0],color[1],color[2]);
		parent.rect(x, y, width, height, 10, 10, 10, 10);
		if (name != null) {
			parent.fill(255,255,255);
			parent.text(name, x+width/2, y+height/2);
		}
		drawConnections();
	}
	
	public void setLabel(String name) {
		this.name = name;
	}
	
	private void drawConnections() {
		if (outputs != null) {
			int outSpacing = height/(outputs.length+1);
			for (int i = 0; i < outputs.length; i++) {
				outputs[i].draw(x+width,  y+(i+1)*outSpacing);
			}
		}
		if (inputs != null) {
			int inSpacing = height/(inputs.length+1);
			for (int i = 0; i < inputs.length; i++) {
				inputs[i].draw(x, y+(i+1)*inSpacing);
			}
		}
	}
	
	public boolean isClicked(int x, int y) {
		//Have we clicked an output?
		if (outputs != null) {
			for (int i = 0; i < outputs.length; i++) {
				if (outputs[i].isClicked(x, y)) {
					outMoving = i;
					return true;
				}
			}
		}
		if ((x > this.x && x < this.x+width) && (y > this.y && y < this.y+height)) {
			xOffset = x-this.x;
			yOffset = y-this.y;
			return true;
		}
		return false;
	}
	
	public void move(int x, int y) {
		if (outMoving > -1) {
			outputs[outMoving].move(x,y);
		}
		else {
			this.x = x-xOffset;
			this.y = y-yOffset;
		}
	}
	public void resetMoving() {
		if (outMoving > -1) {
			int[] coords = outputs[outMoving].stopMoving();
			parent.connect(outputs[outMoving], coords[0], coords[1]);
		}
		outMoving = -1;
	}
	
	public void connect(Connector c, int x, int y) {
		if (inputs != null) 
		for (Connector in : inputs) {
			if (in.isClicked(x, y)) {
				//Make sure we don't connect things to the same module
				boolean sameModule = false;
				if (outputs != null) {
					for (Connector out : outputs) {
						sameModule = sameModule || (c.equals(out));
					}
				}
				if (!sameModule) {
					c.connect(in);
					in.connect(c);
				}
				break;
			}
		}
	}
	
	public String getCodePath() { return codePath; }
	public String[] getImports() { return imports; }
	public int getInitParams() { return initParams; }
}

