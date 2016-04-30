package GUI;

public class Card implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int manaCost;
	private String cardName;
	private int quantity;
	
	public Card(String name, int mana, int q){
		setName(name);
		setMana(mana);
		setQuantity(q);
	}
	
	public int getMana(){
		return manaCost;
	}
	
	public String getName(){
		return cardName;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setMana(int mana){
		manaCost = mana;
	}
	
	public void setName(String name){
		cardName = name;
	}
	
	public void setQuantity(int q){
		quantity = q;
	}

}
