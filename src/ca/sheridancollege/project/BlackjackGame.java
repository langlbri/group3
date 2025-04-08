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

public class BlackjackGame extends Game {

    private Shoe shoe;  //game has a shoe - shoe is made of 6 decks - see shoe class
    private Dealer dealer; // game has a dealer - only dealer handles shoe 
    private BlackjackPlayer player; //game has a BlackjackPlayer 
    private int playerScore; //keeping score of rounds won by player
    private int dealerScore;// keeping score of rounds won by dealer

    public BlackjackGame(String name) {
        super(name);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        player = new BlackjackPlayer(playerName);

        // Create a shoe with 6 decks and pass it to the dealer.
        Shoe shoe = new Shoe(6);
        dealer = new Dealer("Dealer", shoe);
        getPlayers().add(player);
        getPlayers().add(dealer);

        //initialize scores to 0 
        playerScore = 0;
        dealerScore = 0;
    }

    @Override
    //Logic for managing the gameplay with player and dealer logic delegated to their respective classes
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to ** " + super.getName() + " ** \nWould you like to play a round of Blackjack? (Y/N): ");
        String startResponse = scanner.nextLine().trim();

        //If input is NOT "Y" for yes, then print goodbye message 
        if (!startResponse.equalsIgnoreCase("Y")) {
            System.out.println("Thanks for playing " + super.getName() + ". Bye!");
            return;
        }

        //Flag to loop through game logic while continuePlaying = true, game ends when player input "N" for play another round and flag changes to false. 
        boolean continuePlaying = true;
        //While player wants to keep playing, repeat the loop
        while (continuePlaying) {
            startRound(scanner); //call start round method below
            declareWinner(); //after the round, declare the winner 
            //print the score 
            System.out.println("Current Score: Dealer " + dealerScore + " : "
                    + player.getName() + " " + playerScore);

            //Input Validation 
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Play another round? (Y/N): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true;
                    player.clearHand();
                    dealer.clearHand();
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true;
                    continuePlaying = false;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        }
        System.out.println("Bye! Thanks for playing " + super.getName()); //goodbye message
    }

    //logic for starting the round and playing the round
    private void startRound(Scanner scanner) {
        // Dealer deals initial cards: first to player, then to dealer (face up), then player's 2nd card, then dealer's 2nd (hidden) card.
        dealer.dealInitialCards(player);

        System.out.println(player.getName() + "'s hand: " + player.getHand()
                + " (Total: " + player.getHand().calculateHandValue() + ")");
        // Showing only the dealer's first card 
        System.out.println("Dealer shows: " + dealer.getHand().getCards().get(0));

        // Checking if the dealer has blackjack on initial deal 
        if (dealer.mightHaveBlackjack()) {
            System.out.println("Dealer is checking for Blackjack...");
            if (dealer.getHand().isBlackjack()) {
                System.out.println("**** Dealer has Blackjack! ****");
                System.out.println("Dealer's full hand: " + dealer.getHand()
                        + " (Total: " + dealer.getHand().calculateHandValue() + ")");

                //checking if player ALSO has blackjack on initial deal 
                if (player.getHand().isBlackjack()) {
                    //if yes, it's a tie (because both player and dealer have blackjack)
                    System.out.println("Both Dealer and Player have Blackjack! It's a PUSH.");
                    //if not, dealer wins (because player does not have blackjack)
                } else {
                    System.out.println("Dealer wins with Blackjack!");
                    //dealer score goes up for running total of rounds won 
                }
                return;
            }
        }

        // Check if the player has Blackjack on the initial deal 
        if (player.getHand().isBlackjack()) {
            System.out.println("**** Blackjack! " + player.getName() + " wins! ****");
            System.out.println("Dealer's full hand: " + dealer.getHand()
                    + " (Total: " + dealer.getHand().calculateHandValue() + ")");
            return;
        }

        // Calling player.play() for the player's turn logic, passing dealer and scanner
        player.play(dealer, scanner);

        // Checking if player's hand is over 21, if so, the round ends. 
        if (player.getHand().isBusted()) {
            //dealerScore++;
            return;
        }

        // Calling dealer.play() method for the Dealer's turn logic 
        dealer.play();
    }

    //Declaring the winner by comparing hand values, increasing the player or dealer score counter accordingly 
    @Override
    public void declareWinner() {
        //calculate the player hand value
        int playerValue = player.getHand().calculateHandValue();
        //calculate the dealer hand value
        int dealerValue = dealer.getHand().calculateHandValue();
        //check if player hand is over 21 by calling isBusted() method
        if (player.getHand().isBusted()) {
            System.out.println(player.getName() + " BUSTS. DEALER WINS.");
            //if player.getHand().isBusted returns true, then player is over 21, so dealer wins and dealer score increases. 
            dealerScore++;
            // Check if dealer hand has gone over 21
        } else if (dealer.getHand().isBusted()) {
            System.out.println("DEALER BUSTS. " + player.getName() + " WINS!");
            // If dealer hand is over 21, player wins and player score increases 
            playerScore++;
            // If neither hand isBusted(), Check if player's hand value is greater than Dealer's hand value
        } else if (playerValue > dealerValue) {
            System.out.println(player.getName() + " wins with " + playerValue
                    + " over dealer's " + dealerValue + ".");
            //If player hand > dealer hand, player wins and player score increases 
            playerScore++;
            //If dealer's hand value > player's hand value, dealer wins. 
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins with " + dealerValue
                    + " over " + player.getName() + "'s " + playerValue + ".");
            dealerScore++;

            // If none of the above are true, then it is a tie (called "PUSH")
        } else {
            System.out.println("PUSH. Both have " + playerValue + ".");
        }
    }
}
