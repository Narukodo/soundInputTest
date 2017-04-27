package soundInputTest;

import java.awt.*;
import java.awt.event.*;
import sun.audio.*;
import java.io.*;

public class KeyboardTest{
	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;
	private TextField textField;
	private boolean flag = true;

	public KeyboardTest(){
		prepareGUI();
	}

	public static void main(String[] args){
		KeyboardTest kt = new KeyboardTest();
		kt.showKeyListenerDemo();
	}

	public void prepareGUI(){
		mainFrame = new Frame("Java AWT Examples");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});

		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350, 100);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.addKeyListener(new CustomKeyListener());
		mainFrame.setVisible(true);
	}

	class CustomKeyListener implements KeyListener{

		public void keyTyped(KeyEvent e){
		} 

		public void keyPressed(KeyEvent e){
			try{
				System.out.println("aaaaa");
				if(flag){
					flag = false;
					SoundCapture.record();
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}  

		public void keyReleased(KeyEvent e){
			try{
				flag = true;
				System.out.println("released");
			}
			catch(Exception ex){
				System.out.println(ex);
			}

		}
	}
	private void showKeyListenerDemo(){
		headerLabel.setText("Listener in action: KeyListener");

		textField = new TextField(10);

		textField.addKeyListener(new CustomKeyListener());
	}
}
