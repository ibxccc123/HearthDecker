package GUI;

import java.io.*;

public class Driver {
	
	public static void main (String [] args){
		
		//Example of serialization.  Run the program and test through text in console.
		
		//Serializing a DeckList h into a .ser file.
		DeckList h = new DeckList();
		h.name = "ControlHunter";
		h.cardNames.add("Sludge Belcher");
		h.cardNames.add("Ysera");
		h.cardNames.add("Undertaker");
		h.manaCosts.add(5);
		h.manaCosts.add(9);
		h.manaCosts.add(2);
		h.numberOf.add(1);
		h.numberOf.add(2);
		h.numberOf.add(2);   		  
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream("Decks/ControlHunter.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(h);
			out.close();
			fileOut.close();  //Closes the file that is being written into
			System.out.println("Deck saved");
			} catch(IOException i)
			{
				i.printStackTrace();
			}
		
		
		//Deserializing the DeckList ControlHunter.ser into Decklist n
		
		DeckList n = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("Decks/ControlHunter.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
	        n = (DeckList) in.readObject();  //Cast from Object return type into DeckList return type
	        in.close();
	        fileIn.close(); //Closes the file that is being read from
	        } catch(IOException | ClassNotFoundException i)
			{
	        	i.printStackTrace();
	        }
		
		//Tests to see if file has been deserialized into an object by printing to console
		n.printCardInfo();
		
		
		
		
		
		//New experimental.  Tests spinners, which we can use with adding cards.
		//Check window.java for more info
		Window window = new Window("HearthDecker");
		window.setVisible(true);
	}
}
