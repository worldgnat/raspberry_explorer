package raspberryexplorer;

import processing.core.PApplet;
import processing.core.PFont;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.google.gson.Gson;

public class RaspberryExplorer extends PApplet {
	/**
	 * Next Steps:
	 * -Restrict connections and signal object types
	 * -Add more modules
	 * -Build plug-in functionality
	 */
	private static final long serialVersionUID = 1L;
	private static final int padding = 5;
	ArrayList<Module> modules;
	Menu[] menus;
	PFont font;
	int r,g,b;
	
	boolean moving = false;
	Module movingModule;

	public void setup() {
		size(displayWidth, displayHeight);
		font = createFont("Arial",16,true);
		modules = new ArrayList<Module>();
		
		//Create Default menus
		Button newPulsator = new Button("Pulsator", this) {
			public void action() {
				parent.modules.add(new Pulsator(parent, 1000));
			}
		};
		Button newSplitter = new Button("Splitter", this) {
			public void action() {
				parent.modules.add(new Splitter(parent));
			}
		};
		Button newRandomColor = new Button("Random Color", this) {
			public void action() {
				parent.modules.add(new RandomColor(parent));
			}
		};
		newPulsator.setBackgroundColor(50, 200, 50);
		newSplitter.setBackgroundColor(200, 100, 100);
		newRandomColor.setBackgroundColor(20, 20, 250);
		
		Button load = new Button("Load", this) {
			@SuppressWarnings("unchecked")
			public void action() {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				if (chooser.getSelectedFile() != null) {
					try {
						ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
						parent.modules = (ArrayList<Module>)in.readObject();
						in.close();
					}
					catch (Exception er) {
						System.err.println("Problem saving file.");
						er.printStackTrace();
					}
				}
			}
		};
		load.setBackgroundColor(0,255,0);
		Button save = new Button("Save", this) {
			public void action() {
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(null);
				if (chooser.getSelectedFile() != null) {
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
						out.writeObject(parent.modules);
						out.close();
					}
					catch (Exception er) {
						System.err.println("Problem saving file.");
						er.printStackTrace();
					}
				}
			}
		};
		save.setBackgroundColor(0,255,0);
		
		
		menus = new Menu[2];
		menus[0] = new Menu(this, "New Module");
		menus[0].addButton(newPulsator);
		menus[0].addButton(newSplitter);
		menus[0].addButton(newRandomColor);
		menus[0].setColor(200, 50, 50);
		
		menus[1] = new Menu(this, "Files");
		menus[1].addButton(load);
		menus[1].addButton(save);
		r = 69;
		g = 137;
		b = 255;
	}

	public void draw() {
		background(r,g,b);
		for (Module mod : modules) {
			mod.draw();
		}
		textFont(font, 16);
		fill(200,100,100);
		
		//Draw Menus
		int dx = 20;
		for (Menu menu : menus) {
			menu.draw(dx, 10);
			dx += menu.getWidth() + padding;
		}
	}
	
	public boolean sketchFullScreen() {
		return true;
	}
	
	public void mousePressed() {
		for (Module mod : modules) {
			if (mod.isClicked(mouseX, mouseY)) {
				movingModule = mod;
				moving = true;
			}
		}
		for (Menu menu : menus) {
			menu.isClicked(mouseX, mouseY);
		}
	}
	
	public void mouseDragged() {
		if (moving) {
			movingModule.move(mouseX, mouseY);
		}
	}
	public void mouseReleased() {
		if (moving)
			movingModule.resetMoving();
		moving = false;
	}
	
	public void connect(Connector c, int x, int y) {
		for (Module mod : modules) {
			mod.connect(c, x, y);
		}
	}
	
	public PFont getGlobalFont() {
		return font;
	}
	
	public static void main(String args[]) {
		PApplet.main(new String[] { raspberryexplorer.RaspberryExplorer.class.getName() });
	}
	
}
