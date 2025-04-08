/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author group 3
 */
import java.util.Scanner;

public class BlackjackPlayer extends Player {

    //Blackjack player has a hand of cards 
    private Hand hand;

    //Instantiate the BlackjackPlayer
    public BlackjackPlayer(String name) {
        super(name);
        hand = new Hand();
    }

    //Method to return the hand of cards 
    public Hand getHand() {
        return hand;
    }

    //Method to clear the hand of cards before a new round 
    public void clearHand() {
        hand.clearHand();
    }

    //The logic for the player's turn
    //Passing Dealer and Scanner 
    public void play(Dealer dealer, Scanner scanner) {
        while (true) {
            System.out.print(getName() + ", would you like to HIT or STAND? (H/S): ");
            String action = scanner.nextLine();
            // Logic for Hit
            if (action.equalsIgnoreCase("H")) {
                dealer.hitPlayer(this);
                // Checking if the hand has gone over 21 after being dealt another card 
                if (hand.isBusted()) {
                    System.out.println(getName() + " busts with hand: " + hand);
                    return;
                }
                //Logic for Stand
            } else if (action.equalsIgnoreCase("S")) {
                System.out.println(getName() + " stands with a total of " + hand.calculateHandValue());
                return;
                //If input is invalid (not H or S)
            } else {
                System.out.println("Invalid input. Please enter H or S.");
            }
        }
    }

    @Override
    public void play() {
        // This subclass now uses play(Dealer, Scanner).
    }
}
