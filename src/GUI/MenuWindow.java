package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MenuWindow extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MenuWindow(){
		
		super("HearthDecker");
		setSize(350, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		JButton newDeckButton = new JButton("New Deck");
		newDeckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	dispose();
            	NewDeckWindow newDeckWindow = new NewDeckWindow();
            	newDeckWindow.setVisible(true);
            }
        });
		
		JButton loadButton = new JButton("Load Deck");
		loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	
        		JTextField deckField = new JTextField("");
        		JTextField directoryField = new JTextField("");
    
        		File workingDirectory = new File(System.getProperty("user.dir"));
        		JFileChooser c = new JFileChooser();
        		c.setCurrentDirectory(workingDirectory);
        		// Loads the DeckList
        		int rVal = c.showSaveDialog(MenuWindow.this);
        		if (rVal == JFileChooser.APPROVE_OPTION) {
        		    deckField.setText(c.getSelectedFile().getName());
        		    directoryField.setText(c.getCurrentDirectory().toString());
        		    DeckList deck = null;
        		    try{
        		    	String directory = directoryField.getText();
        		    	String deckName = deckField.getText();
        		    	FileInputStream fileIn = new FileInputStream(directory + "\\" + deckName);
        				ObjectInputStream in = new ObjectInputStream(fileIn);
        		        deck = (DeckList) in.readObject();  //Cast from Object return type into DeckList return type
        		        in.close();
        		        fileIn.close(); //Closes the file that is being read from
                    	dispose();
                    	NewLoadWindow newLoadWindow = new NewLoadWindow(deck);
                    	newLoadWindow.setVisible(true);
        		        } catch(IOException | ClassNotFoundException i)
        				{
        		        	i.printStackTrace();
        		        }
        		}
        		if (rVal == JFileChooser.CANCEL_OPTION) {
        		    deckField.setText("You pressed cancel");
        		    directoryField.setText("");
        		}
            }
        });
		
		JButton editButton = new JButton("Edit Deck");
		editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	
        		JTextField deckField = new JTextField("");
        		JTextField directoryField = new JTextField("");
    
        		File workingDirectory = new File(System.getProperty("user.dir"));
        		JFileChooser c = new JFileChooser();
        		c.setCurrentDirectory(workingDirectory);
        		// Loads the DeckList
        		int rVal = c.showSaveDialog(MenuWindow.this);
        		if (rVal == JFileChooser.APPROVE_OPTION) {
        		    deckField.setText(c.getSelectedFile().getName());
        		    directoryField.setText(c.getCurrentDirectory().toString());
        		    DeckList deck = null;
        		    try{
        		    	String directory = directoryField.getText();
        		    	String deckName = deckField.getText();
        		    	FileInputStream fileIn = new FileInputStream(directory + "\\" + deckName);
        				ObjectInputStream in = new ObjectInputStream(fileIn);
        		        deck = (DeckList) in.readObject();  //Cast from Object return type into DeckList return type
        		        in.close();
        		        fileIn.close(); //Closes the file that is being read from
                    	dispose();
                    	NewEditWindow newEditWindow = new NewEditWindow(deck, deckName);
                    	newEditWindow.setVisible(true);
        		        } catch(IOException | ClassNotFoundException i)
        				{
        		        	i.printStackTrace();
        		        }
        		}
        		if (rVal == JFileChooser.CANCEL_OPTION) {
        		    deckField.setText("You pressed cancel");
        		    directoryField.setText("");
        		}
            }
        });
		
		
		add(newDeckButton, "wrap");
		add(loadButton, "wrap");
		add(editButton, "wrap");
		
	}

}
