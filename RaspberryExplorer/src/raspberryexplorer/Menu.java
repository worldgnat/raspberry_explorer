package raspberryexplorer;

import java.util.ArrayList;

public class Menu {
	private static final int padding = 5;
	RaspberryExplorer parent;
	String name;
	ArrayList<Button> buttons;
	float height, width;
	int r, g, b;
	
	public Menu(RaspberryExplorer parent, String name) {
		this.parent = parent;
		this.name = name;
		this.buttons = new ArrayList<Button>();
	}
	
	public void addButton(Button button) {
		buttons.add(button);
		if (buttons.size() == 0) 
			height += button.getHeight();
		else height += button.getHeight() + padding;
		width = (width > button.getWidth() ? width : button.getWidth());
	}
	
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void draw(int x, int y) {
		parent.fill(r, g, b);
		parent.rect(x-15, y-5, width+20, height+20, 10, 10, 10, 10);
		int dy = y;
		parent.noStroke();
		for (Button button : buttons) {
			dy += button.getHeight();
			button.draw(x, dy);
			dy += padding;
		}
	}
	
	public float getHeight() {
		return height + 35;
	}
	public float getWidth() {
		return width+35;
	}
	
	public boolean isClicked(int x, int y) {
		for (Button button : buttons) {
			if (button.isClicked(x, y)) {
				button.action();
				return true;
			}
		}
		return false;
	}
}
