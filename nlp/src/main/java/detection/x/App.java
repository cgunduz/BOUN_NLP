package detection.x;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App extends JPanel implements java.awt.event.ActionListener {

	JLabel jlbLabel1, jlbLabel2, jlbLabel3;
	JButton button1;
	JTextField textField;
	Demo demo;
	
	private static String primary = "Primary Emotion : ";
	private static String tailing = "Tailing Emotion : ";
	
	public App() {

		demo = new Demo();
		
		setLayout(new GridLayout(2, 2));

		jlbLabel1 = new JLabel(primary);
		jlbLabel2 = new JLabel(tailing);
		jlbLabel2.setVerticalTextPosition(JLabel.BOTTOM);
		jlbLabel2.setHorizontalTextPosition(JLabel.CENTER);
		jlbLabel3 = new JLabel(); 		
		
		button1 = new JButton("Get Emotion");
		button1.addActionListener(this);
		
		textField = new JTextField();
		
		add(textField);
		add(button1);
		add(jlbLabel1);
		add(jlbLabel2);
		//add(jlbLabel3);
		
	}
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Demo");
		frame.addWindowListener(new WindowAdapter() {

			// Shows code to Add Window Listener
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setContentPane(new App());
		frame.pack();
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		
		List<String> answers = demo.initateSearchRequest(textField.getText());
		
		jlbLabel1.setText(primary + " " + answers.get(0));
		jlbLabel2.setText(tailing + " " + answers.get(1));
		//jlbLabel3.setText(answers.get(2));
		
	}
}

