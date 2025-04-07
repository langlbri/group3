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

public class Hand extends GroupOfCards {

    private ArrayList<Card> hand;

    public Hand() {
        super(0);
        hand = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        getCards().add(card); 
    }

    // This method calculates the value of the hand
    public int calculateHandValue() {
        int value = 0; // total hand value 
        int aceCount = 0; // tracking how many aces in a hand 

        // For each card in the hand
        for (Card card : getCards()) {
            // If card is a StandardCard object 
            if (card instanceof StandardCard) {
                StandardCard stc = (StandardCard) card;
                value += stc.getRank().getCardValue(); // Add the card value
                if (stc.getRank() == Rank.ACE) {
                    aceCount++; // Count aces
                }
            }
        }

        // While the hand value is over 21, and the ace counter is over 0
        while (value > 21 && aceCount > 0) {
            value -= 10; // Adjust ace value from 11 to 1
            aceCount--;
        }

        return value; // Return the total value of the hand 
    }

    public boolean isBusted() {
        return calculateHandValue() > 21; // Check if hand value exceeds 21
    }

    public boolean isBlackjack() {
        return getCards().size() == 2 && calculateHandValue() == 21; // Check if hand is a blackjack
    }

    public void clearHand() {
        getCards().clear(); // Clear all cards from the hand
    }

    @Override
    public ArrayList<Card> getCards() {
        return hand; // Return the hand
    }

    @Override
    public String toString() {
        String result = "";
        for (Card card : getCards()) {
            result += card.toString() + ", ";
        }
        result += "Total Value: " + calculateHandValue(); // Add hand value to string representation
        return result;
    }
}
