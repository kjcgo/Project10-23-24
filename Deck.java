package goFishGo;
/*
 * Deck class to instantiate a deck of cards from the Card class for the Go Fish project.
 * 
 * Deck class will need the following:
 * * declared arraylist variable to hold 52 Cards.
 * * non-argument constructor for a Deck that will:
 * * * instantiate ArrayList deck
 * * * initialize ArrayList deck
 * * * shuffle the ArrayList deck
 * * method to initialize the array with cards (Ace of Hearts, ... King of Spades)
 * * method to shuffle the cards.
 * * method to return the top card of the deck.
 * * method to return the size of the deck.
 */

//imports
import java.util.ArrayList;

public class Deck {

   // class variable
	ArrayList<Card> deck = new ArrayList <Card>();

	
	//store ranks and suits
	public static String[] rk = {"two", "three", "four", "five", "six", "seven", "eight", "nine",
			"ten", "jack", "queen", "king", "ace"};
	public static String[] st = {"clubs", "spades", "hearts", "diamonds"};
	
	
   // non-arg constructor instantiate deck, initialize deck, shuffle deck
	public Deck() {
		deck = initDeck();
		shuffleDeck();
	}
	
   // initialize the deck with Cards
	public ArrayList<Card> initDeck() {
		
		
		//make a temporary deck to hold return value
		ArrayList<Card> makeDeck = new ArrayList<Card>();
		
		//fill temp deck with cards
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				makeDeck.add(new Card(st[j], rk[i]));
			}
		}
		
		//return fresh deck
		return makeDeck;
		
	}

   // shuffle the deck
   public void shuffleDeck() {
      // variable for size of deck
      int deckSize = deck.size();

      // temporary storage to swap cards with
      Card temp;

      // random value to swap cards location
      int rndLoc;

      // outer loop to get a good mix
      for (int i = 0; i < 4; i++) {
         // inner loop to shuffle card locations
         for (int j = 0; j < deckSize; j++) {
            rndLoc = (int) (Math.random() * deckSize);
            temp = deck.get(j);
            deck.set(j, deck.get(rndLoc));
            deck.set(rndLoc, temp);
         }
      }
   }

   // how many cards in the deck
   public int deckSize() {
	   return deck.size();
   }

   // return the top card and remove it from deck
   public Card dealCard() {
	   
	   //store card
	   Card dealt = deck.get(0);
	   
	   //remove card
	   deck.remove(0);
	   
	   //return card
	   return dealt;
   }   
}
