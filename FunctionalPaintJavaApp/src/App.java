 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Stack;

public class App extends JFrame implements ActionListener{

	JPanel panel;
	JComboBox list;
	static Color c = Color.WHITE;
	static String s = "Rectangle";
	
	public void North() {
		JPanel drawPanel = new DrawPanel();
		panel.add(drawPanel, BorderLayout.CENTER);
	}
	
	public void South() {
		JPanel south = new JPanel();
		south.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		JButton undo = new JButton("UNDO");
		JButton erase = new JButton("ERASE");
		undo.addActionListener(this);
		erase.addActionListener(this);
		south.add(undo);
		south.add(erase);
		panel.add(south, BorderLayout.SOUTH);
	}
	
	public void West() {
		
		JPanel west = new JPanel(new GridLayout(7, 1));
		west.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		String[] colorChoices = new String[] {"Black", "Red", "Blue", "Green", "Yellow", "Orange", "Pink"};
        list = new JComboBox(colorChoices);
        list.addActionListener(this);
        JRadioButton b1 = new JRadioButton("Rectangle");
        JRadioButton b2 = new JRadioButton("Circle");
        JRadioButton b3 = new JRadioButton("Arc");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(b1);
        buttonGroup.add(b2);
        buttonGroup.add(b3);
        west.add(list);
        west.add(b1);
        west.add(b2);
        west.add(b3);
        panel.add(west, BorderLayout.WEST);
	}
	
	 public void MenuOptions(){
	        JMenuBar menu = new JMenuBar();
	        JMenu file = new JMenu("File");
	        JMenuItem save = new JMenuItem("Save");
	        save.addActionListener(this);
	        JMenuItem load = new JMenuItem("Load");
	        load.addActionListener(this);
	        file.add(save);
	        file.add(load);
	        JMenu edit = new JMenu("Edit");
	        JMenuItem undo = new JMenuItem("Undo");
	        undo.addActionListener(this);
	        JMenuItem erase = new JMenuItem("Erase");
	        erase.addActionListener(this);
	        edit.add(undo);
	        edit.add(erase);
	        menu.add(file);
	        menu.add(edit);
	        setJMenuBar(menu);
	    }
	 
	public App() {
		super("SHAPE DRAWING");
		this.panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.CYAN);
		North();
		West();
		MenuOptions();
		add(panel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("comboBoxChanged")) {
			if (list.getSelectedItem().equals("Black")){
                c = Color.BLACK;
            } else if(list.getSelectedItem().equals("Red")){
               c = Color.RED;
            } else if(list.getSelectedItem().equals("Blue")){
                c = Color.BLUE;
            } else if(list.getSelectedItem().equals("Green")){
                c = Color.GREEN;
            } else if(list.getSelectedItem().equals("Yellow")){
                c = Color.YELLOW;
            } else if(list.getSelectedItem().equals("Orange")){
                c = Color.ORANGE;
            } else if(list.getSelectedItem().equals("Pink")){
                c = Color.PINK;
            }
        } else if(e.getActionCommand().equals("Rectangle")){
            s = "Rectangle";
        } else if(e.getActionCommand().equals("Circle")){
            s = "Circle";
        } else if(e.getActionCommand().equals("Arc")){
            s = "Arc";
        } else if(e.getActionCommand().equals("Undo")){
            DrawPanel.pop();
            DrawPanel.x1 = 0;
            DrawPanel.x2 = 0;
            repaint();
        }
        else if(e.getActionCommand().equals("Erase")){
            DrawPanel.clear();
            DrawPanel.x1 = 0;
            DrawPanel.x2 = 0;
            repaint();
		}
        else if(e.getActionCommand().equals("Save")) {
        	try {
        		FileOutputStream fileOut = new FileOutputStream("shape.ser");
        		ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        		objectOut.writeObject(DrawPanel.stack);
        		objectOut.close();
        		System.out.println("Saved");
        	}catch(IOException f) {
        		System.out.println("Error");
        	}
        }
        else if(e.getActionCommand().equals("Load")) {
        	try {
        		FileInputStream fileIn = new FileInputStream("shape.ser");
            	ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            	Stack<Shape> shape = (Stack<Shape>) objectIn.readObject();
            	DrawPanel.stack = shape;
            	objectIn.close();
            	repaint();
        	}catch(IOException | ClassNotFoundException f) {
        		System.out.println("File not found!");
        	}
        }
	}
}
