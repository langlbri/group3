/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The SHOE is used in a game of Blackjack to hold multiple decks of playing
 * cards. It is a plastic holder filled with cards, typically 6 decks of cards
 * (for Blackjack) The Shoe is used so that players cannot easily count how many
 * cards have been used from a deck, because that would give the player an
 * unfair advantage The Shoe class below accurately represents how a Show
 * functions for casino card games. When the BlackjackGame is instantiated, it
 * creates a Shoe object and passes int numberOfDecks. This allows the code to
 * be flexible. If the Shoe were to be used in another game that only requires 1
 * deck, then when that game is instantiated and creates a Shoe object, it would
 * be writtens as Shoe shoe = new Shoe(1)
 *
 * The Dealer is the only player that should interact with the Shoe. Players
 * cannot deal themselves cards.
 *
 * @author group 3
 */
import java.util.ArrayList;

public class Shoe {

    private final List<Deck> decks; //List of deck objects to be contained in the shoe 
    private int numCardsDealt; //Tracking how many cards have been dealt from the shoe 
    private int shoeResetThreshold; //Threshold value to reset the shoe 
    private final int numberOfDecks; // Number of deck objects to place in the shoe 

    public Shoe(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks; //passed from BlackjackGame - how many decks are in the Shoe
        decks = new ArrayList<>();
        //Add a deck to the Shoe for each decks in numberOfDecks
        for (int i = 0; i < numberOfDecks; i++) {
            decks.add(new Deck());
        }
        numCardsDealt = 0;
        //Call the generate threshold method which randomly calculates a number to set as a threshold to reset the shoe 
        shoeResetThreshold = generateThreshold();
        shuffle();
    }

    //creating a threshold value to reset the shoe.
    //since shoe has 6 decks of 52 cards, the shoe has 312 cards 
    //generating a number between 260 and 290 (inclusive) to determine when to reset the shoe, 
    //so most cards are used but it is less predictable than a static number each time  
    //this replicates how the game is played in real life
    private int generateThreshold() {
        return (int) (Math.random() * (290 - 260 + 1)) + 260;
    }

    //For each deck in the shoe, shuffle the deck 
    public void shuffle() {
        for (Deck deck : decks) {
            deck.shuffle();
        }
    }

    //Method for dealing cards     
    public Card dealCard() {
        //if the 6 decks in the shoe are gone, reset the shoe
        //this is a safety, since the shoe should be reset before reaching 0 from the threshold generated above
        if (decks.isEmpty()) {
            resetShoe();
        }

        //the current deck is the deck at index 0 in the list of decks in the shoe 
        Deck currentDeck = decks.get(0);

        //if that deck is empty, remove it and start the next deck
        if (currentDeck.getCards().isEmpty()) {
            decks.remove(0);
            return dealCard();
        }
        //the dealt card is given from the current deck 
        Card dealtCard = currentDeck.dealCard();
        // the number of cards dealt increases - this is for knowing when to reset the shoe 
        numCardsDealt++;

        //if total cards dealt reaches the shoe reset threshold, reset the shoe (see below resetShoe()
        if (numCardsDealt >= shoeResetThreshold) {
            resetShoe();
        }
        //return the dealt card 
        return dealtCard;
    }

    //logic for resetting the shoe when the threshold is reached 
    public void resetShoe() {
        System.out.println("**** Cut card reached, shoe change! ****");
        //clear the list of decks to remove any remaining cards 
        decks.clear();
        // add the number of deck objects passed (6) to the shoe
        for (int i = 0; i < numberOfDecks; i++) {
            decks.add(new Deck());
        }
        //shuffle all the decks within the shoe 
        shuffle();
        //reset the counter for how many cards have been dealt from the Shoe, since it is now reset and filled with new cards. 
        numCardsDealt = 0;
        //generate new threshold for when to reset the Shoe next
        shoeResetThreshold = generateThreshold();
    }

}
