package GUI;

import java.util.ArrayList;
import java.util.List;

public class DeckList implements java.io.Serializable {
	
	public String name;
	public List<String> cardNames = new ArrayList<String>();
	public List<Integer> manaCosts = new ArrayList<Integer>();
	public List<Integer> numberOf = new ArrayList<Integer>();
	
	public void printCardInfo(){
		
		System.out.println("In Deck: " + name);
		for(int i=0; i < cardNames.size(); i++){
			System.out.println(cardNames.get(i));
			System.out.println("Mana : " + manaCosts.get(i));
			System.out.println("Left : " + numberOf.get(i));
		}
		
	}
}