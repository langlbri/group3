/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;

/**
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
        this.numberOfDecks = numberOfDecks;
        decks = new ArrayList<>();
        //Add a deck to the Shoe 
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
        //clear the list of decks 
        decks.clear();
        // add the number of deck objects passed (6) to the shoe
        for (int i = 0; i < numberOfDecks; i++) {
            decks.add(new Deck());
        }
        //shuffle
        shuffle();
        //reset counter 
        numCardsDealt = 0;
        //generate new threshold
        shoeResetThreshold = generateThreshold();
    }

}
