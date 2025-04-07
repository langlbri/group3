/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author britt
 */
import java.util.ArrayList;
import java.util.Collections;


public class Deck extends GroupOfCards {


    public static final int cardsPerDeck = 52;
    private final ArrayList<Card> deck;

    public Deck() {
        super(cardsPerDeck);
        deck = new ArrayList<>(cardsPerDeck);
        populateDeck();
        shuffle();
    }

    private void populateDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                getCards().add(new StandardCard(suit, rank));
            }
        }
    }

    public Card dealCard() {
        if (getCards().size() > 0) {
            return getCards().remove(0);
        }
        return null;
    }

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

