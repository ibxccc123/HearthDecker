# HearthDecker
A Java window application that saves, edits, and loads Hearthstone decks.  A JRE instance must be installed on the user's computer.
The application opens a window which can create new decks of up to thirty cards and maintain track of the quantity and mana cost of each card.
It allows the user to load a deck so that they can manage quantity counts of their cards as a game is undergoing to provide an advantage.
The SpinnerModel class is utilized to increment or decrement the count of each card.
The decks are saved as .ser files, which are deserialized into objects when they are loaded.  The decks are represented as DeckList objects, which contain a ArrayList<> field that is populated by objects belonging to the Card class.
Each Card object represents a Hearthstone card and contains fields pertaining to that, such as mana costs and names.  To encapsulate the Card class further, they also contain an int value which stores the quantity of the card in a certain deck. 
 
Written in March 2016.
