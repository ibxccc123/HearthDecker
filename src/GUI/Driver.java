package GUI;

import java.io.*;

public class Driver {
	
	public static void main (String [] args){
		
		//Example of serialization.  Run the program and test through text in console.
		
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
			FileOutputStream fileOut = new FileOutputStream("Decks/HunterDeck.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(h);
			out.close();
			fileOut.close();
			System.out.println("Deck saved");
			} catch(IOException i)
			{
				i.printStackTrace();
			}
      
		h.printCardInfo();
      
	}
}
