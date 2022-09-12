import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Shape implements Serializable{
	int x1, y1, x2, y2;
	Color c;
	
	public int getX() {
		return x1;
	}
	
	public int getY() {
		return y1;
	}
	
	public int getWidth() {
		int w = x2-x1;
		return w;
	}
	
	public int getHeight() {
		int h = y2-y1;
		return h;
	}
	
	public Color getColor() {
		return c;
	}
	
	public double getAngle() {
		int dy = Math.abs(y2-y1);
		int dx = Math.abs(x2-x1);
		double radianAngle = Math.atan2(-(dy),  dx);
		double length = Math.toDegrees(radianAngle);
		return length;
	}
	
	public int getDiameter() {
		int d = x2-x1;
		return d;
	}
	
	public Shape(int w, int x, int y, int z, Color color) {
		x1 = w;
		y1 = x;
		x2 = y;
		y2 = z;
		c = color;
	}

}
