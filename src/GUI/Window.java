package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

//Child of JFrame
public class Window extends JFrame {
	
	JPanel panel;
	JLabel label;
	
	Window(String title){
		super(title);  //Calls JFrame's constructor with the title as the parameter.
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		//Creates a numerical spinner.
		SpinnerModel model1 = new SpinnerNumberModel();
		JSpinner spinner1 = new JSpinner(model1);
		//Places it within the window.
		label = new JLabel("Card Name");
		panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.WEST);
		panel.add(spinner1, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

		//Look up "spinners in java" for more info 
		//TODO: replace the label "Numbers" with a text field that the user can write in
		
	}


}
