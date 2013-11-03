package raspberryexplorer;

import processing.core.PApplet;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class Connector implements Serializable {
	PApplet parent;
	boolean out; //Output or input?
	String label;
	int x, y;
	boolean moving = false;
	
	boolean connected = false;
	
	private int xOffset, yOffset;
	
	final static int w = 20;
	final static int h = 20;
	final static int r = 30;
	
	MethodHandle methodHandle;
	Module module;
	
	//This connector has an...
	Connector input; //input connector if it's an output connector or...
	Connector output; //an output connector if it's an input connector.
	/*
	 * That is, if this connector IS an output connector, then it feeds to some input connector.
	 * If this is an input connector, then it is fed output from some output connector.
	 * The input and output connector variables record this relationship. A connector can 
	 * have one or none of these variables defined, but not both.
	 */
	
	public Connector(PApplet parent, boolean out, String label, Module module) {
		this.parent = parent;
		this.out = out;
		this.label = label;
		this.module = module;
	}
	
	public void connect(Connector con) {
		if (out) {
			input = con;
			//System.out.println("Connected.");
		}
		else output = con;
		connected = true;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void draw(int mx, int my) {
		if (!moving) {
			this.x = mx;
			this.y = my;
		}
		//Arbitrarily, lines are drawn by output nodes
		parent.stroke(5);
		if (moving) parent.line(mx, my, this.x, this.y);
		else if (input != null) {
			parent.line(x, y, input.getX(), input.getY());
		}
		parent.noStroke();
		if (out) {
			parent.fill(50,50,250);
			parent.rect(x, y-h/2, w, h, 0, r, r, 0);
			parent.fill(255,255,255);
			parent.text(label, x+10, y-h/2);
		}
		else {
			parent.fill(0,0,0);
			parent.rect(x+w, y-h/2, -w, h, 0, r, r,0);
			parent.text(label, x-parent.textWidth(label), y-h/2);
		}
	}
	
	public void setMethodHandle(String name, MethodType type) {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		try {
			methodHandle = lookup.findVirtual(module.getClass(), name, type);
		}
		catch (Exception er) {
			er.printStackTrace();
		}
	}
	/*
	 * Invokes this connector's method.
	 */
	public void call(Object... args) {
		try {
			if (out) input.call(args);
			else {
				if (methodHandle == null) System.out.println("methodHandle is null.");
				if (args == null) System.out.println("arrrrgs be null.");
				try {
					methodHandle.invokeWithArguments(module, args);
				}
				catch (Throwable er) {
					er.printStackTrace();
				}
				System.out.println("Invoked.");
			}
		}
		catch (IllegalArgumentException er) {
			for (Object o : args) {
				System.out.println(o.getClass());
			}
			System.out.println(module.name);
			er.printStackTrace();
			//We need better error handling here.
			//The Processing UI will know nothing about the error, and that's not good.
		}
		catch(Exception er) {
			er.printStackTrace();
		}
	}
	
	/*
	 * Checks to see if this connector is at a specific place, especially for the purposes
	 * of detecting whether this connector has been clicked.
	 */
	public boolean isClicked(int x, int y) {
		if ((x > this.x && x < this.x+w) && (y+h/2 > this.y && y+h/2 < this.y+h)) {
			xOffset = x-this.x;
			yOffset = y-this.y;
			return true;
		}
		return false;
	}
	
	public boolean isConnected() { return connected; }
	
	public int[] stopMoving() { 
		int[] temp = {x+xOffset, y+yOffset};
		moving = false;
		return temp;
	}
	
	public void move(int x, int y) {
		moving = true;
		this.x = x-xOffset;
		this.y = y-yOffset;
	}
}
