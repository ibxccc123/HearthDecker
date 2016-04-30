package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

//Child of JFrame
public class NewLoadWindow extends JFrame {
	
	private static final long serialVersionUID = 1L; //SVU required for consistency amongst all compilers
	private JPanel userPanel;
	private JPanel viewingPanel;
	private ArrayList<JLabel> cardLabels;

	
	NewLoadWindow(DeckList deck){
		
		//Sets up the Window's attributes
		super("HearthDecker");  //Calls JFrame's constructor with HearthDecker as the argument.
		setSize(700, 990);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
  		
		//TODO:  Display the name of the deck that has been loaded.
		
		//Instantiates the Panels and JLabel Array
		userPanel = new JPanel(new MigLayout());
		viewingPanel = new JPanel(new MigLayout());
		cardLabels = new ArrayList<JLabel>();	
		
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
			cardLabels.add(new JLabel("----------------")); 
			viewingPanel.add(cardLabels.get(i), "cell 0 " + Integer.toString(i)); //Adds label to panel on left column
		}
		
		int length = deck.getLength();
		
		//Sets up the spinner models for editing quantity left in the deck.
		//Creates numerical spinners.  Value, minimum, maximum, step are the parameters.
		for(int i=0; i<length; i++){
			Card card = deck.getCard(i);
			SpinnerModel quantityModel = new SpinnerNumberModel(card.getQuantity(), 0, 9, 1);
			JSpinner quantitySpinner = new JSpinner(quantityModel);
			viewingPanel.add(quantitySpinner, "cell 1 " + Integer.toString(i));  //Adds spinner to panel on right column
		}

		
		//Loads deck into labels
		for(int i=0; i<length; i++){
			Card card = deck.getCard(i);
			int mana = card.getMana();;
			String name = card.getName();
			String labelText = Integer.toString(mana) + " " + name;
			cardLabels.get(i).setText(labelText);
		}
		
		//Sets up the case where additional cards are added into the deck through in-game effects
		JLabel extraDeck = new JLabel("Extra cards added below: ");
		viewingPanel.add(extraDeck, "cell 0 30");
		
		for(int i=31; i<38; i++){  
			SpinnerModel quantityModel = new SpinnerNumberModel(0, 0, 9, 1);
			JSpinner quantitySpinner = new JSpinner(quantityModel);
			JTextField textField = new JTextField("");
			textField.setPreferredSize(new Dimension(200, 24));
			viewingPanel.add(textField, "cell 0 " + Integer.toString(i)); //Adds textField to panel on left column
			viewingPanel.add(quantitySpinner, "cell 1 " + Integer.toString(i)); //Adds quantitySpinner to panel on right column
		}

		
		
		//Adds the panels to the window
		add(userPanel, "cell 0 0");
		add(viewingPanel, "cell 1 0");	
		
	}

	
	



}	    
