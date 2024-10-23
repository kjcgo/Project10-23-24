package goFishGo;
/*
 * Class to create a Card object based on suit (hearts, clubs, diamonds, spades) 
 * and rank (Ace, 2, 3, ..., Jack, Queen, King) for the Go Fish project.
 * 
 * Card class will need the following:
 * * class variables suit and rank.
 * * an argumendt constructor passing suit and rank.
 * * accessors get suit and get rank.
 * * method to determine if called card matches another in a hand
 * * toString method to print an instantiated card object.
 */

public class Card {

    // class variables
	String suit;
	String rank;

    
    // arg constructor
    public Card(String suit, String rank) {
    	this.suit = suit;
    	this.rank = rank;		
    }
    
    // accessors
    public String getSuit() {
    	return suit;
    }
    
    public String getRank() {
    	return rank;
    }

    // print a card
    // @Override
    public String displayCard() {
    	return rank + " of " + suit;
    }
    
}