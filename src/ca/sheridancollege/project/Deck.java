/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author group 3
 */
import java.util.ArrayList;
import java.util.Collections;

public class Deck extends GroupOfCards {

    public static final int cardsPerDeck = 52; //used for setting the size in GroupOfCards abstract class
    private final ArrayList<Card> deck;

    // Constructing Deck from GroupOfCards with int size 52, populating the deck, shuffling the deck
    public Deck() {
        super(cardsPerDeck);
        deck = new ArrayList<>(cardsPerDeck);
        populateDeck();
        shuffle();
    }

    // Instatiate the StandardCard objects and populate the deck with them
    private void populateDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                getCards().add(new StandardCard(suit, rank));
            }
        }
    }

    // Deal a card from the deck
    public Card dealCard() {
        if (getCards().size() > 0) {
            return getCards().remove(0);
        }
        return null;
    }

    //Reset the deck with new StandardCard objects 
    public void resetDeck() {
        getCards().clear();
        populateDeck();
        shuffle();
    }

    @Override
    public ArrayList<Card> getCards() {
        return deck;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(deck);
    }

}
