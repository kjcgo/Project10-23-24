package goFishGo;
/*
* This class will be an object creator for Go Fish players.  It will extend Deck and need the following:
 * * declared variables to store name, books (score).
 * * instantiated ArrayList for hand of cards.
 * * argument constructor passing the name of the player and set initial books to 0.
 * * name, rank, and books accessors
 * * books mutator
 * * argument method to add passed drawn card to hand
 * * non-argument method returns String to display a player's hand
 * * non-argument method returns int counting cards in a player's hand
 * * non-argument method returns boolean to find a book in player's hand, also increasing their score
 * * argument method returns boolean to find a rank match passing opponent and card rank
 * * argument method returns boolean to find a rank and suit passed to the method
 * * argument method returns boolean to find a rank passed to the method
 */

import java.util.ArrayList;
import java.util.Collections;

public class Player extends Deck {
   // class variables
	String name;
	int books = 0;
	ArrayList<Card> hand = new ArrayList<Card>();

   // arg constructor with name and set books to 0
	public Player(String name) {
		this.name = name;
		books = 0;
	}
	
   // name getter
	public String getName() {
		return name;
	}

   // rank getter TODO
	
   // books getter
	public int getBooks() {
		return books;
	}

   // books setter
	public void setBooks(int books) {
		this.books = books;
	}

   // add card to hand
	public void drawCard(Card myCard) {
		hand.add(myCard);
	}

   // display hand for a player
	//TODO: sort by rank
	
	public String showHand() {
		
		//empty string for storage
		String showing = "";
		
		int i = 1;
		//iterate over each card in hand
		for(Card e : hand) {
			
			//store displayCard return and newline
			showing += e.displayCard();
			showing += " | ";
			if(i % 5 == 0) {
				showing += "\n\n ";
			}
			i++;
		}
		
		//return completed print statement
		return showing;
	}

   // count the number of cards in hand
	public int countHand() {
		return hand.size();
	}
   
   /*-------------------Code in Project 2-----------------------*/

	//Setup for methods, all return false as placeholder
	
	//targets own hand, no particular combo
	// find a book in hand (four cards with the same rank)
	
	//TODO: try to simplify, make less convoluted
	//TODO take another look at, doesn't always remove all cards
	
	/*
	 *System.out.println(getName());
		//keep track of how many matched
		int matching = 0;
			
		//check if 4 or more cards in hand 
		if(countHand() > 3) {
				
			//get ranks of of each hand
			ArrayList<String> cardNames = new ArrayList<String>();
			for(Card card : hand) {
				cardNames.add(card.getRank());
			}
			
			//sort alphabetically
			Collections.sort(cardNames);
			
			//loops for each set of 4 consecutive cards
			for(int i = 0; i < countHand() - 3; i++) {
					

				//loops through each card in set
				for(int j = 0; j < 3; j++) {
					
					//if there are 4 paired
					if(matching == 4) {
						
						//start looking for cards to remove
						for(int k = 0; k < countHand(); k++) {
							
							//if the rank of your hand matches the rank that needs to be removed, remove
							if(hand.get(k).getRank().equals(cardNames.get(i - 1))){
								hand.remove(k);
							}
						}
						
						//increase score and exit
						books++;
						
						//if this player is the human, display they scored a book
						if(!getName().equals("Alvin") && !getName().equals("Simon") && !getName().equals("Theodore")) {
							System.out.println("You scored a book!");
						}
						
						//if this player is a CPU, change the display
						else {
							System.out.println(getName() + " scored a book!");
						}
						return true;
					}
					
					//if consecutive ranks within set are not equal, break
					if(cardNames.get(i) != cardNames.get(i + 1)) {
						System.out.println(cardNames.get(i));
						matching = 0;
					}
					else {
						matching++;
					}
				}
			}	
		}		
			
		return false;
	 * */
	public boolean findMatch() {
		
		//arraylists needed
		ArrayList<String> allRanks = new ArrayList<String>();
		ArrayList<String> unique = new ArrayList<String>();
		ArrayList<Card> toRemove = new ArrayList<Card>();
		
		//store all of the cards's ranks in an arraylist
		for(Card card : hand) {
			allRanks.add(card.getRank());
		}
		
		//create an arraylist without duplicates
		for(String rank1 : allRanks) {
			if(!unique.contains(rank1)){
				unique.add(rank1);
			}
		}
		
		//use collections to find how many occurences of each element
		//if 4 occurences, remove the book, increase score counter, and display a win

		for(String rank1 : unique) {
			
			if(Collections.frequency(allRanks, rank1) == 4) {
				
				books++;
				
				//display that they scored a book
				if(!getName().equals("Alvin") && !getName().equals("Simon") && !getName().equals("Theodore")) {
					System.out.println("You scored a book!");
				}
				else {
					System.out.println(getName() + " scored a book!");
				}
				
				//finds cards that need to be removed
				for(Card card : hand) {
					if(card.getRank().equals(rank1)){
						toRemove.add(card);
					}
				}
				
				//removes the cards from the hand
				for(Card card : toRemove) {
					hand.remove(card);
				}
				
				return true;
				
			}
		}
		
		return false;
	}
	
	
   // find card match, rank, in defender player's hand and move all that match
	public boolean findMatch(Player plyr, String str) {
		
		ArrayList<Card> toRemove = new ArrayList<Card>();
		
		boolean matched = false;
		//loop through each card in player's hand
		for(Card card : plyr.hand) {

			//if card matches target, add to arraylist of card to remove and add to own hand
			if(card.getRank().equals(str)) {
				toRemove.add(card);
				matched = true;
			}
		}
		
		//prevents "concurrent modification exception"
		for(Card card : toRemove) {
			if(plyr.hand.contains(card)) {
				drawCard(card);
				plyr.hand.remove(card);
			}
		}
		
		return matched;
	}
	
	// find card match for first player rank in player's hand; 2 Spades, Clubs, etc.
	//str1 is the rank, str2 is the suit
	public boolean findMatch(String str1, String str2) {
		
		//loop through each card in hand
		for(Card card : hand) {
			
			//check if card's rank and suit match arguments
			if(card.getRank() == str1 && card.getSuit() == str2) {
				return true;
			}
		}
		
		//if none found return false
		return false;
	}
	
   // find card match on rank
	public boolean findMatch(String str) {
		
		//count how many match the rank
		int counter = 0;
		
		//store indexes of cards that match the rank
		ArrayList<Card> toRemove = new ArrayList<Card>();
		
		//loop through the cards in hand
		for(Card card : hand) {
			
			//if matches rank, keep track of index and increase counter
			if(card.getRank().equals(str)){
				toRemove.add(card);
			}
		}
		
		//if there are 4 of each
		if(counter == 4) {
			books++;
			//remove all cards in book and return true
			for(Card card : toRemove) {
				hand.remove(card);
			}
			
			//if this player is the human, display they scored a book
			if(!getName().equals("Alvin") && !getName().equals("Simon") && !getName().equals("Theodore")) {
				System.out.println("You scored a book!");
			}
			
			//if this player is a CPU, change the display
			else {
				System.out.println(getName() + " scored a book!");
			}
			
			return true;
				
		}
		
		return false;
	}
	
	
}
