package goFishGo;
/*
* This will be the game of Go Fish.
*
* Object: The goal is to win the most "books" of cards. A book is any four of a kind, such as four kings, four aces, and so on.
*
* Rules: Four people are playing 1 human and 3 cpu, so each player receives five cards.
* The remainder of the pack is placed face down on the table to form the stock.
*
* The player with the lowest card rank (2) of the lowest suit (alphabetical; clubs, diamonds, hearts, spades) plays first.
* The active player will select any opponent and say, for example, "Give me your kings," usually addressing the opponent by name
* and specifying the rank that they want, from ace down to two. The player who is "fishing “must have at least one card of the
* rank that was asked for in their hand. The player who is addressed must hand over all the cards requested.
* If the player has none, they say, "Go fish!" and the player who made the request draws the top card of the stock and places it
* in their hand.
*
* If a player gets one or more cards of the named rank that was asked for, they are entitled to ask the same or another player for a card.
* The player can ask for the same card or a different one. So long as the player succeeds in getting cards (makes a catch),
* their turn continues. When a player makes a catch, they must reveal the card so that the catch is verified.
* If a player gets the fourth card of a book, the player shows all four cards, places them on the table face up in front of everyone,
* and plays again.
*
* If the player goes fishing without "making a catch" (does not receive a card he asked for), the turn passes to the left.
*
* The game ends when all thirteen books have been won. The winner is the player with the most books.
* During the game, if a player is left without cards, they may (when it's their turn to play),
* draw from the stock and then ask for cards of that rank. If there are no cards left in the stock, they are out of the game.
*
* This file will contain the logic necessary to play the Go Fish game with 3 CPU opponents.  You will need to do the following:
* * declare global variables
* * * three (3) CPU players and a human
* * * constant for TOTALBOOKS at 13
* * * integer to hold the scoredbooks
* * * deck based on Deck class
* * * array to hold all the players
* * * integer for selected opponent
* * * String for selected Rank
* * * instantiated variable for first player as "unknown"
* * * and a Scanner object
*
* * main method
* * * ask for the user's name and store it in a variable
* * * loop to allow for play again
* * * * instantiate the deck, set the scoredbooks to zero, instantiate the three cpu players and the human objects
* * * * instante the players array
* * * * ask if they need instructions on how to play, display or not, based on their response
* * * * tell them who they are playing against, call the play game method, and ask if they want to play again
*
* * play game method
* * * deal cards to players, check for book on opening hand, determine first player and announce it
* * * loop to continue the game until total scored books equals TOTALBOOKS (13)
* * * * if acting player does not have a card, they should draw one if there are cards in the deck
* * * * call cpu play method or human play method based on who's turn it is
* * * * cycle players' index from 0 to 3 and back to 0.
*
* * human play method
* * * show the player their hand, ask them to select a rank in their hand, verify their selection, 
* * * and select an opponent, verify their selection in a loop in case of bad inputs
* * * call findMatch method of Player to see if that player has the rank selected
* * * * they either receive go fish and draw a card (if there are cards), or they receive the card from their opponent, and check for book match either way
* * * if they received their selected card, they score a book, and they get to go again
*
* * cpu play method
* * * select a random rank in the hand, select a random opponent, check for match
* * * * they either receive go fish and draw a card (if there are cards), or they receive the card from their opponent, and check for book match either way
* * * if they received their selected card, they score a book, and they get to go again
* 
* * verify rank boolean return method
* * * verifies that the rank selected by player exists in their hand using find match and either makes them select again or returns boolean
* 
* * verify opponent boolean return method
* * * verifies that the opponent selected by player exists using error handling and eithe rmakes them select again or returns boolean
*
* * argument method accepting players array, and selected player to return an integer of the index for selected player
* 
* * argument method accepting players array to determine starting player and returning their name
*
* * display score method
*
* * display hand method
* 
* * display rules method
*/



/*
 * TODO: Fix the time delay
 * Look display each computer's hand each time to see if it is working
 * Check if the scoredBooks variable is updating
 * Check if the findMatch functions are removing the right cards
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;

public class GoFishGo {
	
	//global variables
	private static Player cpu1;
	private static Player cpu2;
	private static Player cpu3;
	private static Player human;
	
	//variables
	private final static int TOTALBOOKS = 13;
	private static int scoredBooks = 0;
	private static Deck deck;
	
	//player cycle
	static Player actors[] = new Player[4];
	
	//opponent and rank selection
	int selOpp;
	String selRank;
	
	//set first player
	String firstPlayer = "unknown";	
	
	//make a scanner
	static Scanner scan = new Scanner(System.in);
	
	//store player's name
	static String name;
	
	//keeps track of opponents
	static String[] opponents = {"Alvin", "Simon", "Theodore"};
	
   // main method
   public static void main(String args[]) throws IOException, InterruptedException {
	   
	   //instantiate cpus with names
	   cpu1 = new Player("Alvin");
	   cpu2 = new Player("Simon");
	   cpu3 = new Player("Theodore");
	   
	   //ask user for their name and create their object
	   System.out.print("Welcome to the game of Go Fish, please tell us your name: ");
	   name = scan.nextLine();
	   human = new Player(name);
	   
	   //instantiate actors
	   actors[0] = cpu1;
	   actors[1] = cpu2;
	   actors[2] = cpu3;
	   actors[3] = human;
	   
	   //display rules if needed
	   System.out.print("\nDo you know how to play Go Fish, " + name + "? (y/n) ");
	   String resp = scan.nextLine();
	   if(resp.equals("n")) {
		   displayRules();
	   }
		  
	   //display opponents
	   System.out.println("\nYour opponents are 1. " + cpu1.getName() + ", 2. " + cpu2.getName() + 
			   ", and, 3. " + cpu3.getName() + "\n");
	  
	   //make a deck
	   deck = new Deck();
	   playGame();
	   //ask to play again
	   System.out.println("Would you like to play again? (y/n) ");
	   
      //close the scanner object
      scan.close();
   }

   //setup for other methods
   
   /*
    * * play game method
    * * * deal cards to players, check for book on opening hand, determine first player and announce it
    * * * loop to continue the game until total scored books equals TOTALBOOKS (13)
    * * * * if acting player does not have a card, they should draw one if there are cards in the deck
    * * * * call cpu play method or human play method based on who's turn it is
    * * * * cycle players' index from 0 to 3 and back to 0.
     */
   // the play game engine
   public static void playGame() throws InterruptedException {
	   
	   
	   //DEAL CARDS TO PLAYERS
	   
	   //loop through each player
	   for(Player actor : actors) {
		   
		   //give 5 cards to each
		   for(int j = 0; j < 5; j++) {
			   
			   //give the player a card from the deck
			   actor.drawCard(deck.dealCard());
		   }
	   }
	   
	   //CHECK FOR BOOK ON OPENING HAND
	   
	   //look for matches with each actor
	   for(Player actor : actors) {
		   if(actor.findMatch()) {
			   scoredBooks++;
		   };
	   }
	   
	   //DETERMINE FIRST PLAYER AND ANNOUNCE
	   String firstPlayer = findFirstPlayer(actors);
	   System.out.println(firstPlayer + " will go first!");
	   
	   //rearrange actors so that the player who goes first is index 0
	   if(findPlayerIndex(actors, firstPlayer) != 0) {
		   
		   //find the index of the player who will go first
		   final int first = findPlayerIndex(actors, firstPlayer);
		   
		   //store original player at index 0
		   Player temp = actors[0];

		   //switch around the players
		   actors[0] = actors[first];
		   actors[first] = temp;
	   }
	 
	   //LOOP TO CONTINUE GAME UNTIL TOTAL SCORE IS 13
	   
	   //while the totoal score isn't totalBooks
	   while(scoredBooks != TOTALBOOKS) {
		   
		   for(int i = 0; i < 4; i++) {
			   //see if the current player is a human
			   if(actors[i].getName() == name) {
				   playerAct();
			   }
			   //else call cpu move function
			   else {
				   cpuAct(i);
			   }
		   
		   } 
	   }
	   
	   //Display the final scores
	   System.out.println("\n ~~~~ The game is finished! ~~~~ \n\n ~~~~ FINAL SCORES ~~~~");
	   displayScore();
	   System.out.println("4. " + human.getName() + "\t\tbooks: " + human.getBooks() + "\thand: " + human.countHand()
            + " cards");
	   
	   //determine the winners
	   ArrayList<Integer> scores = new ArrayList<Integer>();
	   ArrayList<String> winners = new ArrayList<String>();
	   
	   //get all scores
	   for(Player player : actors) {
		   scores.add(player.getBooks());
	   }
	   
	   //sort scores and find winning score
	   Collections.sort(scores);
	   int highest = scores.get(scores.size()-1);
	   
	   //determine who has the winning score
	   for(Player player: actors) {
		   if(player.getBooks() == highest) {
			   winners.add(player.getName());
		   }
	   }
	   
	   // print out winners 
	   if(winners.size() == 1) {
		   System.out.println("\nThe winner is " + winners.get(0) + "!!!");
	   }
	   else if(winners.size() == 2) {
		   System.out.println("\nThe winners are " + winners.get(0) + " & " + winners.get(1) + "!!!");
	   }
	   else if(winners.size() == 3) {
		   System.out.println("\nThe winners are " + winners.get(0) + ", " + winners.get(1) + ", and " + winners.get(2) + "!!!");
	   }
   }
   
   // player activity game logic
   /*\
    * human play method
* * * show the player their hand, ask them to select a rank in their hand, verify their selection, 
* * * and select an opponent, verify their selection in a loop in case of bad inputs
* * * call findMatch method of Player to see if that player has the rank selected
* * * * they either receive go fish and draw a card (if there are cards), or they receive the card from their opponent, and check for book match either way
* * * if they received their selected card, they score a book, and they get to go again
    */
   public static void playerAct() throws InputMismatchException{
	   	   
	   //stores target and rank
	   int target;
	   String cardRank;
	   
	 //if the player has no cards and the deck is not empty, draw a card
	   if(human.hand.size() == 0 && deck.deckSize() > 0) {
		   human.drawCard(deck.dealCard());
	   }
	   
	   //if the player has no cards and the deck is finished, skip turn
	   else if(human.hand.size() == 0 && deck.deckSize() == 0) {
		   return;
	   }
		
	   //normal play
	   else {
	   //SHOW HAND AND PROMPT
	   displayHand();
	   System.out.println("\nPlease select a card rank in your hand (Ace, 2, 3, etc.), and an opponent number: "); 
	   displayScore();
	 
	   //prompt for card rank and see if valid
	   while(true){
		   System.out.println("\nSelect card rank: ");
	   		cardRank = scan.nextLine();
	   		if(verifyRank(human, cardRank.strip())) {
	   			break;
	   		}
	   		else {
	   			System.out.print("Enter a valid card rank");
	   		}

	   }
	   
	   //prompt for opponent and see if valid
	   while(true) {
		   System.out.println("\nSelect an opponent: ");
		   
		   //try catch in case they don't plug in an int
		   while(true)
		   try {
			   
			   //try to determine target
			   target = scan.nextInt();
			   scan.nextLine();
			   break;
		   }
		   
		   //remidn user of correct use 
		   catch(InputMismatchException e) {
			   System.out.println("Enter a number");
			   scan.nextLine();
		   }
		   
			if(verifyOpp(target)) {
				break;
			}
			else {
				System.out.print("Enter 1, 2, or 3.");
			}
	   }
	   
	   //find the opponent and call findmatch
	   
	   //call findMatch, using the players index using findPlayerIndex, and the cardRank
	   if(human.findMatch(actors[findPlayerIndex(actors, opponents[target - 1])], cardRank)) {
		   System.out.println("Yes, " + opponents[target - 1] + " has the card");
		   
	   }
		   
	   //else, display message and add a card from the deck
	   else {
		   System.out.println("No, go fish!");
			   
		   //adds another card to the human's hand
		   if(deck.deckSize() != 0){
			   Card toAdd = deck.dealCard();
			   human.hand.add(toAdd);
			   if(human.findMatch(toAdd.getRank())) {
				   scoredBooks++;
			   }
			   
		   }
			
	   }
	   if(human.findMatch()) {
		   scoredBooks++;
	   }
	   
   }
   }
  
   // CPU activity game logic
   public static void cpuAct(int compIndex) throws InterruptedException {
	   
	   //delay
	  try {
		  TimeUnit.SECONDS.sleep(1);
	  }
	  catch(InterruptedException e) {
		  throw e;
	  }
	  
	   Player myPlayer = actors[compIndex];
	   
	   //if the player has no cards and the deck is not empty, draw a card
	   if(myPlayer.hand.size() == 0 && deck.deckSize() > 0) {
		   myPlayer.drawCard(deck.dealCard());
	   }
	   
	   //if the player has no cards and the deck is finished, skip turn
	   else if(myPlayer.hand.size() == 0 && deck.deckSize() == 0) {
		   return;
	   }
		
	   //normal play
	   else {
		   //create an arraylist of all the players
		   ArrayList<String> players = new ArrayList<String>();
		   players.add("Alvin");
		   players.add("Simon");
		   players.add("Theodore");
		   players.add(human.getName());
	   
		   //remove the current CPU's own name from the list
		   players.remove(myPlayer.getName());
	   
		   //pick random number from 0-12
	   
		   Random rand = new Random();
		   int selectRank = rand.nextInt(myPlayer.hand.size());

		   //get a rank of a random card in the hand
		   String myRank = myPlayer.hand.get(selectRank).getRank();
	   
		   //pick random number from 0-2
		   int selectOpp = rand.nextInt(3);
	   
		   //get a player from the arraylist
		   Player myOpp = actors[findPlayerIndex(actors, players.get(selectOpp))];
	   
		   //actors[i] is the current CPU
		   System.out.println("\n ~~~~ It is " + myPlayer.getName() + "'s turn! ~~~~\n");
	   
		   System.out.println(myPlayer.getName() + " chose " + myRank + 
				   " from " + myOpp.getName() + "\n");
	   
		   //find if the CPU makes a catch
		   if(myPlayer.findMatch(myOpp, myRank)) {
		   		System.out.println(myOpp.getName() + " had the card!");
		   }
		   else {
		   
			   //add card from pile if not
			   System.out.println("No, go fish!");
		   
			   if(deck.deckSize() != 0) {
				   Card myNewCard = deck.dealCard(); 
				   myPlayer.hand.add(myNewCard);
			   
				   //see if the new card creates a book
				   if(myPlayer.findMatch(myNewCard.getRank())) {
					   scoredBooks++;
				   }
			   	}
		   
		   }
	   
		   //see if the CPU scored any books
		   if(myPlayer.findMatch()) {
			   scoredBooks++;
		   }
	   
		   displayScore();
	   }
	   
	   
   
   }
  
   // verify user input rank exists in their hand
   public static boolean verifyRank(Player plyr, String rank) {
	   for(Card card : plyr.hand) {
		   if(rank.equals(card.getRank())) {
			   return true;
		   }
	   }
	   return false;
   }
   
   // verify user input target opponent exists
   public static boolean verifyOpp(int target) {
	   if(target > 0 && target < 4) {
		   return true;
	   }
	   return false;
   }

   // determine index of player
   public static int findPlayerIndex(Player[] arr, String str) {
	   
	   //loop through all players
	   for(int i = 0; i < 4; i++) {
		   
		   //find a matching name and return index
		   if(arr[i].getName() == str){
			   return i;
		   }
	   }
	   return -1;
   }

   // determine the first player by looking through each actor's hand to find the 2
   // of Spades, Clubs, Diamonds, Hearts, 3 of Clubs, Diamonds, etc.
   public static String findFirstPlayer(Player[] arr) {
	   String[] suits = {"clubs", "spades", "hearts", "diamonds"};
	   String[] ranks = {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
	   
	   for(int i = 0; i < 13; i++) {
		   for(int j = 0; j < 4; j++) {
			   for(Player player : actors) {
				   if(player.findMatch(ranks[i], suits[j])) {
					   return player.getName();
				   }
			   }
			   
		   }
	   }
	   
	   return "Error in findFirstPlayer";
   }

   // display player's hands and score
   private static void displayHand() {
      System.out.println("\n " + "~~~~ It is " + human.getName() + "'s turn! ~~~~" + "\n\nYou have "  + human.books + " book(s)."+ "\n\t\t\t\t~~~~ Your Hand: ~~~\n\n| " + human.showHand());

      /* ***TESTING USE ONLY***       
      System.out.println(cpu1.getName() + "\t\tbooks: " + cpu1.getBooks() + "\thand: | " + cpu1.showHand());
      System.out.println(cpu2.getName() + "\tbooks: " + cpu2.getBooks() + "\thand: | " + cpu2.showHand());
      System.out.println(cpu3.getName() + "\t\tbooks: " + cpu3.getBooks() + "\thand: | " + cpu3.showHand());
       ***TESTING USE ONLY*** */
   }
   
   /*
   private static void displayOpps() {
	   System.out.println("\n" + cpu1.getName() + "\tbooks: " + cpu1.getBooks() + " | " + cpu1.showHand());
	   System.out.println("\n" + cpu2.getName() + "\tbooks: " + cpu2.getBooks() + " | " + cpu2.showHand());
	   System.out.println("\n" + cpu3.getName() + "\tbooks: " + cpu3.getBooks() + " | " + cpu3.showHand());
   }
   
  */

   // display score
   private static void displayScore() {
      System.out.print("\n1. " + cpu1.getName() + "\tbooks: " + cpu1.getBooks() + "\thand: " + cpu1.countHand()
            + " cards"
            + "\n2. " + cpu2.getName() + "\tbooks: " + cpu2.getBooks() + "\thand: " + cpu2.countHand()
            + " cards"
            + "\n3. " + cpu3.getName() + "\tbooks: " + cpu3.getBooks() + "\thand: " + cpu3.countHand()
            + " cards\n");
   }

   // display rules
   private static void displayRules() {
      System.out.print("\nThe rules of Go Fish are simple."
            + "\nEach player is trying to make a book of four cards of the same rank (4 Kings, 4 Fives, etc.)."
            + "\nDuring your turn, select a card in your hand, select an opponent, and ask them if they have that card rank."
            + "\nYour selected opponent must give you all of the cards they have matching that rank."
            + "\nIf the opponent does not have any cards of that rank, you must take a card from the draw pile."
            + "\nOnce you have all four cards of that rank, you put them on the table face up to score a book."
            + "\nIf you called for a card and received it from a player or the draw pile, then you get to go again."
            + "\nThe game ends when all ranks have been scored in books, and the winner is the player with the most books."
            + "\nThe first player is the one holding the 2 of Spades, Clubs, Diamonds, Hearts, 3 of Clubs, Diamonds, etc.\n");
   }
}