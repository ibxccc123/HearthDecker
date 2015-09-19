package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//Child of JFrame
public class NewLoadWindow extends JFrame {
	
	private static final long serialVersionUID = 1L; //SVU required for consistency amongst all compilers
	private JPanel userPanel;
	private JPanel viewingPanel;
	private JLabel cardLabels[];

	
	NewLoadWindow(DeckList deck){
		
		//Sets up the Window's attributes
		super("HearthDecker");  //Calls JFrame's constructor with HearthDecker as the argument.
		setSize(600, 690);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
  		
		//Instantiates the Panels and JLabel Array
		userPanel = new JPanel(new MigLayout());
		viewingPanel = new JPanel(new MigLayout());
		cardLabels = new JLabel[30];	
	
		JButton goBackButton = new JButton("Go Back to Menu");
		goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	dispose();
            	MenuWindow menuWindow = new MenuWindow();
            	menuWindow.setVisible(true);
            }
        });

		//Adds user interface to the userPanel
		userPanel.add(goBackButton);
		
		//Sets up the labels for the deck list
		for(int i=0; i<30; i++){  
			cardLabels[i] = new JLabel("----------------"); 
			viewingPanel.add(cardLabels[i], "cell 0 " + Integer.toString(i)); //Adds label to panel
		}
		
		int length = deck.getLength();
		for(int i=0; i<length; i++){
			
		}
		
		deck.printCardInfo();
		//Adds the panels to the window
		add(userPanel, "cell 0 0");
		add(viewingPanel, "cell 1 0");	
		
	}

	
	



}	    
