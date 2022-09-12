 import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	static int x1, y1, x2=0, y2=0, x3, y3;
	static Stack<Shape> stack;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color c = App.c;
		String string = App.s;
		if (x1 != x2 && x3 != 0) {
            if (string.equals("Rectangle")) {
                g.setColor(Color.WHITE);
                g.fillRect(x1,y1,x3-x1, y3-y1);
                g.setColor(Color.BLACK);
                g.drawRect(x1,y1,x3-x1,y3-y1);
                if(x2 != 0 && y2 != 0){
                    stack.push(new Rectangle(x1, y1, x2, y2, c));
                    System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
                    System.out.println(x3 + " " + x3);
                    x2 = 0;
                    y2 = 0;
                }
            } else if (string.equals("Circle")) {
                g.setColor(Color.WHITE);
                g.fillOval(x1,y1,x3-x1, x3-x1);
                g.setColor(Color.BLACK);
                g.drawOval(x1,y1,x3-x1,x3-x1);
                if(x2 != 0 && y2 != 0){
                stack.push(new Circle(x1, y1, x2, y2, c));
                    System.out.println(x2 + " " + y2);
                    x2 = 0;
                    y2 = 0;
                }
            } else if (string.equals("Arc")) {
            	int dy = Math.abs(y2-y1);
            	int dx = Math.abs(x2-x1);
            	int posX = Math.abs(x3-x1);
            	int posY = Math.abs(y3-y1);
                double radianAngle = Math.atan2(-(dy), dx);
                double length = Math.toDegrees(radianAngle);
                int z = (int) length;
                g.setColor(Color.WHITE);
                g.fillArc(x1,y1,dx,dy, 0 , z);
                g.setColor(Color.BLACK);
                g.drawArc(x1,y1,dx, dy, 0, z);
                if(x2 != 0 && y2 != 0){
                    stack.push(new Arc(x1, y1, x2, y2, c));
                    System.out.println(x2 + " " + y2);
                    System.out.println(x3 + " " + y3);
                    x2 = 0;
                    y2 = 0;
				}
			}
		}
		for(Shape s : stack) {
			if(s.getClass().equals(Rectangle.class)) {
				g.setColor(s.getColor());
				g.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
				g.setColor(Color.BLACK);
				g.drawRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
			}else if(s.getClass().equals(Circle.class)) {
				g.setColor(s.getColor());
				g.fillOval(s.getX(),  s.getY(),  s.getWidth(),  s.getWidth());
				g.setColor(Color.BLACK);
				g.drawOval(s.getX(),  s.getY(),  s.getWidth(),  s.getWidth());
			}else if(s.getClass().equals(Arc.class)) {
				g.setColor(s.getColor());
				int z = (int) s.getAngle();
				g.fillArc(s.getX(),  s.getY(),  s.getWidth(),  s.getHeight(),  0, z);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
	}
	
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		x3 = e.getX();
		y3 = e.getY();
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	
	public static void clear() {
		stack.clear();
	}
	
	public static void pop() {
		stack.pop();
	}
	
	public DrawPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		stack = new Stack<>();
	}
}
	