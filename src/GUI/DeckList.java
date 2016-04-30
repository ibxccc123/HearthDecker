package GUI;

import java.util.ArrayList;
import java.util.List;

public class DeckList implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Card> cards;
	private int length;
	
	public DeckList(){
		 cards = new ArrayList<Card>();
		 length = 0;		
	}
	
	public void printCardInfo(){
		
		System.out.println("In Deck: ");
		for(int i=0; i < cards.size(); i++){
			Card currentCard = cards.get(i);
			System.out.println(currentCard.getName());
			System.out.println("Mana : " + currentCard.getMana());
			System.out.println("Left : " + currentCard.getQuantity());
		}
		
	}
	
	public void setCard(String name, int mana, int quantity){
		Card card = new Card(name, mana, quantity);
		cards.add(card);		
	}
	
	public Card getCard(int i){
		return cards.get(i);
	}
	
	public void increase(){
		length++;
	}
	
	public void decrease(){
		length--;
	}
	
	public void resetLength(){
		length = 0;
	}
	
	public int getLength(){
		return length;
	}
	
	public void reset(){
		cards.clear();
	}
	
}