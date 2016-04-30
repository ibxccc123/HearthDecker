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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
public class NewEditWindow extends JFrame {
	
	private static final long serialVersionUID = 1L; //SVU required for consistency amongst all compilers
	private JPanel userPanel;
	private JPanel viewingPanel;
	private DeckList deck;  //The class variable deck is modified upon in saveCards() before being serialized into an object.
	private ArrayList<JLabel> cardLabels;
	private ArrayList<JButton> removeButtons;
	private JLabel confirmLabel; 
	private int cardLabelsLength;
	
	NewEditWindow(DeckList passedDeck, String deckFileName){
		
		//Sets up the Window's attributes
		super("HearthDecker");  //Calls JFrame's constructor with HearthDecker as the argument.
		setSize(700, 990);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
				
		//Instantiates the Panels and JLabel Array
		userPanel = new JPanel(new MigLayout());
		viewingPanel = new JPanel(new MigLayout());
		cardLabels = new ArrayList<JLabel>();	
		removeButtons = new ArrayList<JButton>();
		
		//Initializes deck and deck's length
		deck = passedDeck;
		cardLabelsLength = deck.getLength();
		String exportedFileName = deckFileName.substring(0, deckFileName.lastIndexOf('.'));
		JLabel deckLabel = new JLabel("Deck Loaded: " + exportedFileName);  
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
		userPanel.add(deckLabel, "wrap");
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
			cardLabels.add(new JLabel("----------------")); 
			viewingPanel.add(cardLabels.get(i), "cell 0 " + Integer.toString(i)); //Adds label to panel
		}
		
		//Loads deck into labels
		for(int i=0; i<cardLabelsLength; i++){
			Card card = deck.getCard(i);
			int mana = card.getMana();;
			String name = card.getName();
			int quantity = card.getQuantity();
			String labelText = Integer.toString(mana) + " " + name + " " + quantity;
			cardLabels.get(i).setText(labelText);
		}
		
		//Sets up remove buttons for the deck list
		for(int i=0; i<30; i++){
			JButton removeButton = new JButton("Remove");
			removeButtons.add(removeButton);
			removeButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e){
	            	
	            	JButton sourceOfButton = (JButton) e.getSource();
	            	int indexOfSource = 0;
	            	for(int i=0; i<removeButtons.size(); i++){
	            		if(sourceOfButton == removeButtons.get(i)){
	            			indexOfSource = i;
	            			break;
	            		}
	            	}
	            	
	            	//Rather than hardcoding every button, create an algorithm to handle all buttons except the final one
	            	if(indexOfSource != 29){  //If the source of the ActionEvent comes from anywhere but the final button in removeButtons
	            		if(cardLabels.get(indexOfSource).getText().equals("----------------")){  //Corresponding label is empty
	            			confirmLabel.setText("There is nothing to remove.");
	            		}
	            		else if(cardLabels.get(indexOfSource + 1).getText().equals("----------------")){  //Corresponding label + 1 is empty
	            			cardLabels.get(indexOfSource).setText("----------------");
	            			cardLabelsLength--;
	            			confirmLabel.setText("Card removed.");
	            		}
	            		else{ //Corresponding label + 1 is not empty (must move all successive labels up 1)
	            				for(int i=indexOfSource; i<cardLabelsLength-1; i++){
	            					cardLabels.get(i).setText(cardLabels.get(i+1).getText());
	            					cardLabels.get(i+1).setText("----------------");
	            				}
            					cardLabelsLength--;
            					confirmLabel.setText("Card removed.");
	            			}	
	            	}
	            	
	            	//Final button is handled exceptionally, in order to prevent out-of-bounds runtime errors
	            	if(indexOfSource == 29){
	            		if(cardLabels.get(indexOfSource).getText().equals("----------------")){  //Corresponding final label is empty
	            			confirmLabel.setText("There is nothing to remove.");
	            		}
	            		else{  //Corresponding final label is non-empty
	            			cardLabels.get(indexOfSource).setText("----------------");
	            			cardLabelsLength--;
	            			confirmLabel.setText("Card removed.");
	            		}
	            	}      
	            }
	        });
			viewingPanel.add(removeButton, "cell 1 " + Integer.toString(i));
		}
		
		//Adds the panels to the window
		add(userPanel, "cell 0 0");
		add(viewingPanel, "cell 1 0");	
		
		//resets the deck so that when it is saved, the deck is built up with no pre-existing data
		deck.reset();
		deck.resetLength();
		
	}
	
	public void editCards(String text, int mana, int quantity){
		String labelText = Integer.toString(mana) + " " + text + " " + Integer.toString(quantity);
		if(cardLabelsLength == 30){
			confirmLabel.setText("Deck is full.");
		}
		else{
			for(int i=0; i<30; i++){
				if(cardLabels.get(i).getText().equals("----------------")){
					cardLabels.get(i).setText(labelText);
					cardLabelsLength++;
					if(cardLabelsLength > 1){
						sortCards();
					}
					confirmLabel.setText("Changes Made");		
					break;
				}
			}
		}
	}
	
	public void saveCards(){
		for(int i=0; i<cardLabelsLength; i++){
			String labelText = cardLabels.get(i).getText();
			int mana = 0;
			int quantity = 0;
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
		int rVal = c.showSaveDialog(NewEditWindow.this);
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
		    	
		    	//deletes any existing files (redundant, in regards to the false flag in the FileOutputStream arguments)
		    	Path path = FileSystems.getDefault().getPath(directory, deckName + ".ser");
		    	Files.deleteIfExists(path);
		    	
		    	FileOutputStream fileOut = new FileOutputStream(directory + "\\" + deckName + ".ser", false);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(deck);
				out.close();
				fileOut.close();  //Closes the file that is being written into
				confirmLabel.setText("Deck Saved!");
				deck.reset();  //Clears out the ArrayList in deck so that it may be populated with new cards the next time the deck is saved 
				deck.resetLength();
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
		for(int i=1; i<cardLabelsLength; i++){
			int pivot = i;
			while(pivot > 0 && cardLabels.get(pivot - 1).getText().charAt(0) > cardLabels.get(pivot).getText().charAt(0)){
				String tempHolder = cardLabels.get(pivot).getText();
				cardLabels.get(pivot).setText(cardLabels.get(pivot - 1).getText());
				cardLabels.get(pivot - 1).setText(tempHolder);
				pivot--;
			}
		}
	}


}	    
