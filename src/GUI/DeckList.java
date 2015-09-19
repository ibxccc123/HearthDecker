package GUI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DeckList implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<String> cardNames;
	public List<Integer> manaCosts;
	public List<Integer> numberOf;
	private int length;
	
	public DeckList(){
		 cardNames = new ArrayList<String>();
		 manaCosts = new ArrayList<Integer>();
		 numberOf = new ArrayList<Integer>();
		 length = 0;
		
	}
	
	public void printCardInfo(){
		
		System.out.println("In Deck: ");
		for(int i=0; i < cardNames.size(); i++){
			System.out.println(cardNames.get(i));
			System.out.println("Mana : " + manaCosts.get(i));
			System.out.println("Left : " + numberOf.get(i));
		}
		
	}
	
	public void setCard(String name, int mana, int quantity){
		
		cardNames.add(name);
		manaCosts.add(mana);
		numberOf.add(quantity);		
	}
	
	public void saveList(String name){
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream("Decks/" + name + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();  //Closes the file that is being written into
			System.out.println("Deck saved");
			} catch(IOException i)
			{
				i.printStackTrace();
			}
	}
	
	public void increase(){
		length++;
	}
	
	public void decrease(){
		length--;
	}
	
	public void reset(){
		length = 0;
	}
	
	public int getLength(){
		return length;
	}
	
}