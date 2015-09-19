package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;

//Child of JFrame
public class NewDeckWindow extends JFrame {
	
	private static final long serialVersionUID = 1L; //SVU required for consistency amongst all compilers
	private JPanel userPanel;
	private JPanel viewingPanel;
	private DeckList deck;
	private JLabel cardLabels[];
	private JLabel confirmLabel; 
	private int deckLength;
	
	NewDeckWindow(){
		
		//Sets up the Window's attributes
		super("HearthDecker");  //Calls JFrame's constructor with HearthDecker as the argument.
		setSize(600, 690);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		
		//Instantiates a new DeckList object for storing the card information 
		//Will be serialized into a file 
		deck = new DeckList();  
		
		//Instantiates the Panels and JLabel Array
		userPanel = new JPanel(new MigLayout());
		viewingPanel = new JPanel(new MigLayout());
		cardLabels = new JLabel[30];	
		
		//Initializes deck length
		deckLength = 0;
		
		JLabel manaLabel = new JLabel("Mana:");
		JLabel quantityLabel = new JLabel("Quantity:");
		JLabel promptLabel = new JLabel("Type Card Name with Corresponding Mana & Quantity Below");
		confirmLabel = new JLabel("Deck Not Saved");
				
		//Creates a numerical spinner.  Value, minimum, maximum, step are the parameters.	
		final SpinnerModel manaModel = new SpinnerNumberModel(0, 0, 50, 1);
		JSpinner manaSpinner = new JSpinner(manaModel);
		final SpinnerModel quantityModel = new SpinnerNumberModel(0, 0, 50, 1);
		JSpinner quantitySpinner = new JSpinner(quantityModel);
		
		//Creates a text field for the card name	
		final JTextField textField = new JTextField("Type Card Name");
		textField.setPreferredSize(new Dimension(200, 24));
			textField.addFocusListener(new FocusListener(){
				@Override
				public void focusGained(FocusEvent e) {
					textField.setText("");
				}

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub					
				}									
			});
			
		//Creates a button to enter in the card name to the DeckList
		JButton cardButton = new JButton("Enter Card");
			cardButton.addActionListener(new ActionListener() {
	    
				public void actionPerformed(ActionEvent e){
					editCards(textField.getText(), (int)manaModel.getValue(), (int)quantityModel.getValue());
	            }
	        });
			
		//Creates a button to save the DeckList object to a file
		JButton saveButton = new JButton("Save Deck");
		saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	saveCards();
            }
        });
		
		JButton goBackButton = new JButton("Go Back to Menu");
		goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	dispose();
            	MenuWindow menuWindow = new MenuWindow();
            	menuWindow.setVisible(true);
            }
        });

		//Adds user interface to the userPanel
		userPanel.add(promptLabel, "wrap");
		userPanel.add(textField);
		userPanel.add(cardButton, "wrap");
		userPanel.add(manaLabel);
		userPanel.add(manaSpinner, "wrap");
		userPanel.add(quantityLabel);
		userPanel.add(quantitySpinner, "wrap");
		userPanel.add(saveButton, "wrap");
		userPanel.add(confirmLabel, "wrap");
		userPanel.add(goBackButton);
		
		//Sets up the labels for the deck list
		for(int i=0; i<30; i++){  
			cardLabels[i] = new JLabel("----------------"); 
			viewingPanel.add(cardLabels[i], "cell 0 " + Integer.toString(i)); //Adds label to panel
		}
		
		//Adds the panels to the window
		add(userPanel, "cell 0 0");
		add(viewingPanel, "cell 1 0");	
		
	}
	
	public void editCards(String text, int mana, int quantity){
		
		String labelText = Integer.toString(mana) + " " + text + " " + Integer.toString(quantity);
		for(int i=0; i<30; i++){
			if(cardLabels[i].getText().equals("----------------")){
				cardLabels[i].setText(labelText);
				deckLength++;
				if(deckLength > 1){
					sortCards();
				}
				confirmLabel.setText("Changes Made");
				break;
			}
		}
	}
	
	public void saveCards(){
		//Resets length to 0
		deck.reset();
		for(int i=0; i<deckLength; i++){
			String labelText = cardLabels[i].getText();
			int mana;
			int quantity;
			String name = "";
			String[] splitString = labelText.split("\\s+");
			mana = Integer.parseInt(splitString[0]);
			int lastElement = splitString.length - 1;
			quantity = Integer.parseInt(splitString[lastElement]);
			for(int j=1; j<lastElement; j++){
				name = name + " " + splitString[j];
			}
			name = name.substring(1);	
			deck.setCard(name, mana, quantity);
			deck.increase();
		}
		
		JTextField deckField = new JTextField("");
		JTextField directoryField = new JTextField("");
		File workingDirectory = new File(System.getProperty("user.dir"));
		JFileChooser c = new JFileChooser();
		c.setCurrentDirectory(workingDirectory);
		// Saves the DeckList
		int rVal = c.showSaveDialog(NewDeckWindow.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
		    deckField.setText(c.getSelectedFile().getName());
		    directoryField.setText(c.getCurrentDirectory().toString());
		    try{
		    	String directory = directoryField.getText();
		    	String deckName = deckField.getText();
		    	//Trims the file extension from deckName
		    	if(deckName.contains(".")){		    	
		    		deckName = deckName.substring(0, deckName.lastIndexOf('.'));
		    	}
		    	FileOutputStream fileOut = new FileOutputStream(directory + "\\" + deckName + ".ser", false);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(deck);
				out.close();
				fileOut.close();  //Closes the file that is being written into
				confirmLabel.setText("Deck Saved!");
				} catch(IOException i)
				{
					i.printStackTrace();
				}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
		    deckField.setText("You pressed cancel");
		    directoryField.setText("");
		}
	}
	
	public void sortCards(){
		for(int i=1; i<deckLength; i++){
			int pivot = i;
			while(pivot > 0 && cardLabels[pivot - 1].getText().charAt(0) > cardLabels[pivot].getText().charAt(0)){
				String tempHolder = cardLabels[pivot].getText();
				cardLabels[pivot].setText(cardLabels[pivot - 1].getText());
				cardLabels[pivot - 1].setText(tempHolder);
				pivot--;
			}
		}
	}


}	    
