/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author group 3
 */
public class StandardCard extends Card {

    private final Suit suit;
    private final Rank rank;

    public StandardCard(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Return the suit of the StandardCard
    public Suit getSuit() {
        return suit;
    }

    // Return the Rank of the StandardCard
    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
