/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author group 3 
 */
public class Dealer extends BlackjackPlayer {

    private Shoe shoe;

    public Dealer(String name, Shoe shoe) {
        super(name);
        this.shoe = shoe;
    }

    // Returns the next card from the shoe.
    public Card dealCard() {
        return shoe.dealCard();
    }

    /**
     * Initial deal
     * Dealer reveals only one card
     */
    public void dealInitialCards(BlackjackPlayer player) {
        // Deal first card to player.
        Card card = dealCard();
        player.getHand().addCard(card);
        System.out.println(player.getName() + " receives: " + card);

        // Deal first card to dealer (face up).
        card = dealCard();
        getHand().addCard(card);
        System.out.println(getName() + " receives: " + card + " (face up)");

        // Deal second card to player.
        card = dealCard();
        player.getHand().addCard(card);
        System.out.println(player.getName() + " receives: " + card);

        // Deal second card to dealer (hidden).
        card = dealCard();
        getHand().addCard(card);
        System.out.println(getName() + " receives a hidden card.");
    }

    // Called by the player when choosing to hit.
    public void hitPlayer(BlackjackPlayer player) {
        Card card = dealCard();
        player.getHand().addCard(card);
        System.out.println(player.getName() + " hits and receives: " + card);
        System.out.println(player.getName() + "'s hand: " + player.getHand()
                + " (Total: " + player.getHand().calculateHandValue() + ")");
    }

    // Dealer takes a hit on itself.
    public void hitSelf() {
        Card card = dealCard();
        getHand().addCard(card);
        System.out.println(getName() + " hits and receives: " + card);
        System.out.println(getName() + "'s hand: " + getHand()
                + " (Total: " + getHand().calculateHandValue() + ")");
    }

    // Reveals the dealer's hidden (second) card.
    public void revealHiddenCard() {
        if (getHand().getCards().size() >= 2) {
            Card hidden = getHand().getCards().get(1);
            System.out.println(getName() + " reveals hidden card: " + hidden);
        }
    }

    /**
     * Dealer's turn
     */
    @Override
    public void play() {
        revealHiddenCard();
        System.out.println(getName() + "'s hand: " + getHand()
                + " (Total: " + getHand().calculateHandValue() + ")");
        //while Dealer's hand value is under 17, Dealer calls hitSelf(); to receive a card. 
        while (getHand().calculateHandValue() < 17) {
            hitSelf();
        }
        System.out.println(getName() + " stands with total: " + getHand().calculateHandValue());
    }

    // Checks the dealer's face-up card to determine if a Blackjack check is warranted.
    public boolean mightHaveBlackjack() {
        if (!getHand().getCards().isEmpty()) {
            Card faceUp = getHand().getCards().get(0);
            if (faceUp instanceof StandardCard) {
                Rank rank = ((StandardCard) faceUp).getRank();
                return rank == Rank.ACE || rank == Rank.KING || rank == Rank.QUEEN
                        || rank == Rank.JACK || rank == Rank.TEN;
            }
        }
        return false;
    }

    @Override
    public void clearHand() {
        getHand().clearHand();
    }
}

