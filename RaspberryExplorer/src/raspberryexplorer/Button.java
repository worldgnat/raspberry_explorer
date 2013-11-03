package raspberryexplorer;

import processing.core.PFont;

public class Button {
	String text;
	float size;
	RaspberryExplorer parent;
	PFont font;
	int[] color; 
	int br, bg, bb;
	int x, y;
	float width;
	final static int pad = 5;
	
	public Button(String text, RaspberryExplorer parent) {
		this.text = text;
		this.font = parent.getGlobalFont();
		this.size = font.getSize();
		this.color = new int[3];
		this.parent = parent;
		parent.textFont(font);
		parent.textSize(size);
		width = parent.textWidth(text);
	}
	
	public void draw(int x, int y) {
		this.x = x;
		this.y = y;
		parent.textFont(font);
		parent.textSize(font.getSize());
		width = parent.textWidth(text);
		parent.fill(br, bg, bb);
		parent.rect(x-pad, y-size-pad, width+pad*2, size+pad*2, 10, 10, 10, 10);
		parent.fill(color[0], color[1], color[2]);
		parent.text(text, x, y);
	}
	
	public void setBackgroundColor(int r, int g, int b) {
		br = r;
		bg = g;
		bb = b;
	}
	
	public float getHeight() { return size+pad*2; }
	public float getWidth() { return width+pad*2; }
		
	public boolean isClicked(int x, int y) {
		return ((x > this.x-pad && x < this.x+width+pad*2) && (y > this.y-size-pad && y < this.y+pad));
	}
	
	public void action() {}
}
