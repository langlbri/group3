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

    private Hand hand;

    public BlackjackPlayer(String name) {
        super(name);
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clearHand();
    }

    public void play(Dealer dealer, Scanner scanner) {
        while (true) {
            System.out.print(getName() + ", would you like to HIT or STAND? (H/S): ");
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("H")) {
                dealer.hitPlayer(this);
                if (hand.isBusted()) {
                    System.out.println(getName() + " busts with hand: " + hand);
                    return;
                }
            } else if (action.equalsIgnoreCase("S")) {
                System.out.println(getName() + " stands with a total of " + hand.calculateHandValue());
                return;
            } else {
                System.out.println("Invalid input. Please enter H or S.");
            }
        }
    }

    @Override
    public void play() {
        // Not used because this subclass now uses play(Dealer, Scanner).
    }
}

